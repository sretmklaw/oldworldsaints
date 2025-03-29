package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.CycleDay;
import com.cimeliarchium.model.dao.Hour;
import com.cimeliarchium.model.dao.HourType;
import com.cimeliarchium.model.dao.Reading;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.HourReadingDto;
import com.cimeliarchium.service.BaseServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class HourReadingService extends SessionAttributeHelperService
		implements BaseDTOServiceIF<Hour,Reading>,
				BaseServiceIF<Hour,Reading> {

	private static final Logger LOGGER = LoggerFactory.getLogger(HourReadingService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.HOUR_READING_MAP.getValue();
	private static final String STORED_PROCEDURE_NAME = "HourReadingMappingQuery";

	CycleDayLiturgyTypeService cycleDayLiturgyTypeService;
	UserSessionInfoService userSessionInfoService;

	@Autowired
	public HourReadingService(CycleDayLiturgyTypeService cycleDayLiturgyTypeService,
			UserSessionInfoService userSessionInfoService) {
		this.cycleDayLiturgyTypeService = cycleDayLiturgyTypeService;
		this.userSessionInfoService = userSessionInfoService;
	}

	public HourReadingDto query(
			@NotNull CycleDay cycleDay,
			@NotNull Long riteId) throws AppException {

		super.throwIfMissing(cycleDay, queryMissingCycleDay);
		super.throwIfMissing(riteId, queryMissingRiteId);
		final List<QueryParams> params = new ArrayList<>();
		params.add(new QueryParams.Builder<Long>()
				.withParamName("_cycle_day_type_id")
				.withParamVal(cycleDay.getCycleDayTypeId())
				.build());
		params.add(new QueryParams.Builder<Long>()
				.withParamName("_rite_id")
				.withParamVal(riteId)
				.build());
		params.add(new QueryParams.Builder<Boolean>()
				.withParamName("_is_cycle_day_liturgy_special")
				.withParamVal(cycleDay.hasSpecialLiturgy())
				.build());
		return (HourReadingDto) super.queryForDtoWithParams(
				STORED_PROCEDURE_NAME,
				params,
				HourReadingDto.class);
	}

	public Map<Hour, List<Reading>> map(
			CycleDay cycleDay, 
			Long riteId) throws AppException {

		final HourReadingDto dto = (HourReadingDto) throwIfMissing(
				this.query(cycleDay, riteId), 
				mapMissingDto);

		Map<Hour, List<Reading>> resultsMap = new LinkedHashMap<>();
		resultsMap.put(dto.getHour1(), dto.getHour1Readings());
		resultsMap.put(dto.getHour2(), dto.getHour2Readings());
		resultsMap.put(dto.getHour3(), dto.getHour3Readings());
		resultsMap.put(dto.getHour4(), dto.getHour4Readings());
		resultsMap.put(dto.getHour5(), dto.getHour5Readings());
		return resultsMap;
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		final CycleDay cycleDay = (CycleDay) super.throwIfMissing(
				cycleDayLiturgyTypeService.getCachedValueForCurrentDate(session).getCycleDay(), 
				initCacheMissingCycleDay);
		final Long riteId = (Long) super.throwIfMissing(
				userSessionInfoService.getCachedValue(session)
						.getUser()
						.getRiteId(),
				initCacheMissingRiteId);

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			BaseMultiMapServiceHelper.addSessionAttribute(
					LOGGER,
					this.map(cycleDay, riteId),
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Hour, List<Reading>> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseMultiMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	public void initModelForDate(
			@NotNull HttpSession session, 
			Model model, 
			@NotNull CycleDay cycleDay,
			Date targetDate) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		super.throwIfMissing(session, initModelMissingCycleDay);

		final Map<Hour, List<Reading>> dtoMap = (targetDate != null) 
				// Attempt to retrieve for specified target date, where applicable.
				// Note that each call to this operation requires an additional query via the stored procedure.
				? this.map(
						cycleDay, 
						(Long) super.throwIfMissing(
								userSessionInfoService.getCachedValue(session)
								.getUser()
								.getRiteId(),
						initCacheMissingRiteId)) 
				// Otherwise, retrieve the cached current HourReadings
				: this.getCachedValues(session);
	
		final Map<Long, Hour> hourTabMap = new LinkedHashMap<>();
		final Integer currentUserHour = userSessionInfoService
				.getCurrentZonedDateTimeForUser(session)
				.getHour();
		final Long currentUserCalendarId = userSessionInfoService
				.getCachedValue(session)
				.getCalendar()
				.getCalendarId();

		Long activeHourId = null;
		for (Map.Entry<Hour, List<Reading>> entry : dtoMap.entrySet()) {
			final Hour hour = entry.getKey();
			hour.setReadings(entry.getValue());
			// Map Hour by Hour ID, for retrieval after iteration
			// in order to set 'Active' Hour Tab.
			hourTabMap.put(hour.getHourId(), hour);
			// Ensure that we only mark a single Hour Tab as active,
			// namely the first found within whose time range
			// the active User's current hour falls.
			final HourType hourType = hour.getHourType();
			if (activeHourId == null 
					&& hourType.getHourStart() <= currentUserHour
					&& hourType.getHourEnd() > currentUserHour) {
				activeHourId = hour.getHourId();
			}
			// Set Hour Display Name based on User Calendar
			if (currentUserCalendarId == calendarHebrewId) {
				hour.setHourDisplayName(hourType.getHourNameHebrew());
			} else if (currentUserCalendarId == calendarHijriId) {
				hour.setHourDisplayName(hourType.getHourNameArabic());
			} else if (currentUserCalendarId == calendarJulianId) {
				hour.setHourDisplayName(hourType.getHourNameGreek());
			} else {
				hour.setHourDisplayName(hourType.getHourNameLatin());
			}
			// Prevent more than one active tab in sessions spanning multiple hours
			hour.setIsHourActive(false);
		}
		// Set 'Active' Hour Tab based on last observed hour start and end times
		Hour activeHourTab = null;
		if (activeHourId != null) {
			activeHourTab = hourTabMap.get(activeHourId);
		} 
		// Otherwise, default to first Hour Tab where not defined
		else {
			activeHourTab = hourTabMap.values().stream().findFirst().get();
		}
		activeHourTab.setIsHourActive(true);
		model.addAttribute(ModelAttributeEnum.HOUR_TAB_MAP.getValue(), hourTabMap);
	}

	@Override
	@Deprecated
	public void initModel(HttpSession session, Model model, User user) {
		// Intentionally, do nothing
	}

	@Value("${hourreadingservice.query.cycleday}")
	private String queryMissingCycleDay;

	@Value("${hourreadingservice.query.riteid}")
	private String queryMissingRiteId;

	@Value("${hourreadingservice.map.dto}")
	private String mapMissingDto;

	@Value("${hourreadingservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${hourreadingservice.initcache.cycleday}")
	private String initCacheMissingCycleDay;

	@Value("${hourreadingservice.initcache.riteid}")
	private String initCacheMissingRiteId;

	@Value("${hourreadingservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${hourreadingservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${hourreadingservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${hourreadingservice.initmodel.cycleday}")
	private String initModelMissingCycleDay;

	@Value("${calendar.gregorian.id}")
	private Long calendarGregorianId;

	@Value("${calendar.julian.id}")
	private Long calendarJulianId;

	@Value("${calendar.hijri.id}")
	private Long calendarHijriId;

	@Value("${calendar.hebrew.id}")
	private Long calendarHebrewId;

}
