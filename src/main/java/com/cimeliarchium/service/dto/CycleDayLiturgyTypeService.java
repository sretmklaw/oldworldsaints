package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.CalendarRiteEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.CalendarDates;
import com.cimeliarchium.model.dao.Constellation;
import com.cimeliarchium.model.dao.CycleDay;
import com.cimeliarchium.model.dao.LiturgyType;
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.Star;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.CycleDayLiturgyTypeDto;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.service.BaseServiceIF;
import com.cimeliarchium.service.dao.LunarPhaseService;
import com.cimeliarchium.service.dao.StarService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties",
})
public class CycleDayLiturgyTypeService extends SessionAttributeHelperService
		implements BaseDTOServiceIF<CycleDay,LiturgyType>,
				BaseServiceIF<CycleDay,LiturgyType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CycleDayLiturgyTypeService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.CYCLE_DAY_LITURGY_TYPE_MAP.getValue();
	private static final String STORED_PROCEDURE_NAME = "CycleDayLiturgyTypeMappingQuery";

	private HttpSession session;
	private DayCommemorationService dayCommemorationService;
	private LunarPhaseService lunarPhaseService;
	private UserSessionInfoService userSessionInfoService;
	private ConstellationStarService constellationStarService;
	private StarService starService;

	@Autowired
	public CycleDayLiturgyTypeService(DayCommemorationService dayCommemorationService, 
			LunarPhaseService lunarPhaseService, UserSessionInfoService userSessionInfoService, 
			ConstellationStarService constellationStarService, StarService starService) {
		this.dayCommemorationService = dayCommemorationService;
		this.lunarPhaseService = lunarPhaseService;
		this.userSessionInfoService = userSessionInfoService;
		this.constellationStarService = constellationStarService;
		this.starService = starService;
	}

	public CycleDayLiturgyTypeDto query(Date targetDate) throws AppException {

		super.throwIfMissing(targetDate, queryMissingTargetDate);
		final List<QueryParams> params = new ArrayList<>();
		params.add(new QueryParams.Builder<Date>()
				.withParamName("_target_date")
				.withParamVal(targetDate)
				.build());
		return (CycleDayLiturgyTypeDto) super.queryForDtoWithParams(
				STORED_PROCEDURE_NAME,
				params,
				CycleDayLiturgyTypeDto.class);
	}

	public CycleDayLiturgyTypeZodiacSignMapper map(Date targetDate) throws AppException {

		CycleDayLiturgyTypeDto dto = null;
		CalendarDates calendarDates = null;
		DateTime targetDateTime = null;
		if (targetDate != null) {
			dto = (CycleDayLiturgyTypeDto) super.throwIfMissing(
					this.query(targetDate), 
					mapMissingDto);
			targetDateTime = new DateTime(targetDate);
			calendarDates = (CalendarDates) super.throwIfMissing(
					new CalendarDates(targetDateTime), 
					mapMissingCalendarDates);
			lunarPhaseService.initCache(session); // Skip caching Lunar Phase
		} else {
			dto = (CycleDayLiturgyTypeDto) super.throwIfMissing(
					this.query(userSessionInfoService.getCurrentDateForUser(session)), 
					mapMissingDto);
			targetDateTime = new LocalDate().toDateTimeAtStartOfDay();
			calendarDates = (CalendarDates) super.throwIfMissing(
					new CalendarDates(targetDateTime), 
					mapMissingCalendarDates);
			lunarPhaseService.initCacheForCurrentDate(session, calendarDates);
		}

		final Long userCalendarId = (Long) super.throwIfMissing(
				userSessionInfoService.getCachedValue(session).getCalendar().getCalendarId(), 
				mapMissingUserCalendarId);

		HashSet<LiturgyType> liturgyTypes = new HashSet<>();
		for (Map.Entry<Calendar, List<Rite>> ritesByCalendar : CalendarRiteEnum.getCalendarRiteMap().entrySet()) {

			// Select only LiturgyType applicable to the User-specific Calendar, 
			// when not 'All Commemorations'. Otherwise aggregate all LiturgyTypes,
			// regardless of the User Calendar
			final Calendar calendar = ritesByCalendar.getKey();
			if (calendar.getCalendarId() == userCalendarId || userCalendarId == calendarAllId) {
				final LiturgyType liturgyTypeForCalendar = this.buildLiturgyTypeForCalendar(
						dto, 
						ritesByCalendar.getKey(), 
						calendarDates);
				// Conditionally add to list, null-safe to prevent addition of 
				// the catch-all LiturgyType
				if (liturgyTypeForCalendar != null) {
					liturgyTypes.add(liturgyTypeForCalendar);
				}
			}
		}

		// Sort LiturgyType alphabetically by Calendar name.
		ArrayList<LiturgyType> liturgyTypeList = new ArrayList<>(liturgyTypes);
		Collections.sort(liturgyTypeList, LiturgyType.compareByCalendarCode());

		// Add Culminating Star for the current calendar day.
		final Star culminatingStar = dto.getCulminatingStar();

		// Map the target CalendarDates onto the CycleDay for later use.
		CycleDay cycleDay = dto.getCycleDay();
		cycleDay.setCalendarDates(calendarDates);
		return new CycleDayLiturgyTypeZodiacSignMapper(
				cycleDay, 
				culminatingStar, 
				dto.getZodiacSign(), 
				liturgyTypeList);
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		// Must initialize class variable before calling this.map()
		this.session = (HttpSession) super.throwIfMissing(
				session, 
				initCacheMissingSession);

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			session.setAttribute(
					SESSION_ATTRIBUTE_NAME, 
					this.map(null)); // Pass null Target Date to use Current Date for User
			LOGGER.debug(
					SESSION_ATTRIBUTE_ADDED,
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	@Deprecated
	public Map<CycleDay, List<LiturgyType>> getCachedValues(HttpSession session) {
		// Intentionally, do nothing.
		return null;
	}

	public CycleDayLiturgyTypeZodiacSignMapper getCachedValueForCurrentDate(
			@NotNull HttpSession session) throws AppException {

		super.validateSessionAttributeParams(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession, 
				getCachedValuesNotFound);
		return (CycleDayLiturgyTypeZodiacSignMapper) session.getAttribute(SESSION_ATTRIBUTE_NAME);
	}

	public CycleDay initModelForDate(
			@NotNull HttpSession session, 
			Model model, 
			@NotNull User user,
			Date targetDate) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		super.throwIfMissing(session, initModelMissingUser);

		// Attempt to retrieve for specified target date, where applicable.
		// Note that each call to this operation will require 1 additional query of the stored procedure.
		CycleDayLiturgyTypeZodiacSignMapper dtoMapper = null;
		if (targetDate != null) {
			dtoMapper = this.map(targetDate);
			lunarPhaseService.initModel(session, model, user, new CalendarDates(new DateTime(targetDate)));
		}
		// Otherwise, retrieve the cached current CycleDay with applicable LiturgyTypes,
		// together with the cached current Lunar Phase and Zodiac Sign.
		else {
			dtoMapper = this.getCachedValueForCurrentDate(session);
			lunarPhaseService.initModel(session, model, user, null);
		}
		// To initialize Zodiac Sign and Culminating Star, simply pull these from the mapped DTO response.
		starService.initModel(session, model, dtoMapper.getCulminatingStar());
		constellationStarService.initModel(session, model, user, dtoMapper.getZodiacSign());

		// Filter results for display specific to the target date
		final CycleDay cycleDay = dtoMapper.getCycleDay();
		model.addAttribute(ModelAttributeEnum.JULIAN_DATE.getValue(), cycleDay.getCalendarDates().getJulianDate());

		// First use the Cycle Day key to select Day Commemorations for display in tab contents.
		// Also display any relevant location patronage flags on the tab header.
		final Pair<Map<DayCommemorationDto, List<Rite>>, List<Patronage>> resultPair = dayCommemorationService.findDayCommemorationsForTargetDate(
				session, 
				cycleDay, 
				user);
		final Map<DayCommemorationDto, List<Rite>> resultMap = resultPair.getLeft();
		model.addAttribute(ModelAttributeEnum.RESULT_MAP.getValue(), resultMap);
		model.addAttribute(ModelAttributeEnum.LOCATION_IMAGE_MAP.getValue(), getImageGalleryLocationMap(resultMap));
		model.addAttribute(ModelAttributeEnum.LOCATION_PATRONAGE_LIST.getValue(), 
				resultPair.getRight().stream().limit(8L).collect(Collectors.toList())); // Prevent overflow of patronage icons on the screen

		// Then use the mapped values to populate the LiturgyType listing on the tab header
		model.addAttribute(ModelAttributeEnum.LITURGY_TYPE_LIST.getValue(), 
				dtoMapper.getLiturgyTypeList());

		return cycleDay;
	}

	private Map<String,Location> getImageGalleryLocationMap(Map<DayCommemorationDto, List<Rite>> resultMap) {
		final List<Long> uniqueLocationIds = new ArrayList<>();
		final Map<String,Location> locationImageMap = new HashMap<>();
		for (DayCommemorationDto dto : resultMap.keySet()) {
			final Location location = dto.getLocation();
			final Long locationId = location.getLocationId();
			final Location altLocation = dto.getAltLocation();
			final Long altLocationId = altLocation.getLocationId();
			final String commemorationName = dto.getCommemorationName();
			if (altLocationId != null) {
				if (!uniqueLocationIds.contains(altLocationId)) {
					uniqueLocationIds.add(altLocationId);
					final String altLocationLabel = new StringJoiner(" - ")
							.add(altLocation.getLabelNameFormatted())
							.add(commemorationName)
							.toString();
					locationImageMap.put(altLocationLabel, altLocation);
				}
			} else if (locationId != null) {
				if (!uniqueLocationIds.contains(locationId)) {
					uniqueLocationIds.add(locationId);
					final String locationLabel = new StringJoiner(" - ")
							.add(location.getLabelNameFormatted())
							.add(commemorationName)
							.toString();
					locationImageMap.put(locationLabel, location);
				}
			}
		}
		return locationImageMap;
	}

	@Override
	@Deprecated
	public void initModel(HttpSession session, Model model, User user) {
		// Intentionally, do nothing.
	}

	/**
	 * Method used to populate applicable Liturgy Types for display 
	 * while traversing all available Calendar-Rite mappings 
	 * within the loop to populate DayCommemorationDto on matching date string.
	 * 
	 * @param dto the HourReadingDto
	 * @param calendar the Calendar
	 * @param calendarDates the CalendarDates
	 * @throws AppException 
	 */
	private LiturgyType buildLiturgyTypeForCalendar(
			@NotNull CycleDayLiturgyTypeDto dto,
			@NotNull Calendar calendar,
			@NotNull CalendarDates calendarDates) throws AppException {

		super.throwIfMissing(dto, buildLiturgyTypeForCalendarMissingDto);
		super.throwIfMissing(calendar, buildLiturgyTypeForCalendarMissingCalendar);
		super.throwIfMissing(calendarDates, buildLiturgyTypeForCalendarMissingCalendarDates);

		LiturgyType liturgyType = null;
		if (dto != null) {
			final Long calendarId = calendar.getCalendarId();
			final String calendarName = calendar.getCalendarName();
			if (calendarId == calendarGregorianId) {
				liturgyType = dto.getLiturgyTypeCatholic();
				liturgyType.setCalendarDateString(calendarDates.getGregorianDateString());
			}
			else if (calendarId == calendarJulianId) {
				liturgyType = dto.getLiturgyTypeOrthodox();
				liturgyType.setCalendarDateString(calendarDates.getJulianDateString());
			}
			else if (calendarId == calendarHijriId) {
				liturgyType = dto.getLiturgyTypeIslamic();
				liturgyType.setCalendarDateString(calendarDates.getHijriDateString());
			}
			else if (calendarId == calendarHebrewId) {
				liturgyType = dto.getLiturgyTypeJewish();
				liturgyType.setCalendarDateString(calendarDates.getHebrewDateString());
			}
			if (liturgyType != null) {
				liturgyType.setCalendarName(calendarName);
				liturgyType.setCalendarCode(calendar.getCalendarCode());
				LOGGER.trace("- Adding Liturgy Type for '{}'", calendarName);
			} else {
				LOGGER.trace("- Skipped adding Liturgy Type for '{}'", calendarName);
			}
		} else {
			LOGGER.warn("cycleDayLiturgyTypeDto not found, skipping add Liturgy Type.");
		}
		return liturgyType;
	}

	/**
	 * Inner class used exclusively to pass DTO-initialized entities within this service.
	 */
	public static class CycleDayLiturgyTypeZodiacSignMapper {

		private CycleDay cycleDay;
		private Star culminatingStar;
		private Constellation zodiacSign;
		private List<LiturgyType> liturgyTypeList;

		public CycleDayLiturgyTypeZodiacSignMapper(
				CycleDay cycleDay, 
				Star culminatingStar,
				Constellation zodiacSign,
				List<LiturgyType> liturgyTypeList) {
			super();
			this.cycleDay = cycleDay;
			this.culminatingStar = culminatingStar;
			this.zodiacSign = zodiacSign;
			this.liturgyTypeList = liturgyTypeList;
		}
		public CycleDay getCycleDay() {
			return cycleDay;
		}
		public Star getCulminatingStar() {
			return culminatingStar;
		}
		public Constellation getZodiacSign() {
			return zodiacSign;
		}
		public List<LiturgyType> getLiturgyTypeList() {
			return liturgyTypeList;
		}
	}


	@Value("${cycledayliturgytypeservice.query.targetdate}")
	private String queryMissingTargetDate;

	@Value("${cycledayliturgytypeservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${cycledayliturgytypeservice.map.dto}")
	private String mapMissingDto;

	@Value("${cycledayliturgytypeservice.map.calendardates}")
	private String mapMissingCalendarDates;

	@Value("${cycledayliturgytypeservice.map.usercalendarid}")
	private String mapMissingUserCalendarId;

	@Value("${cycledayliturgytypeservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${cycledayliturgytypeservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${cycledayliturgytypeservice.buildliturgytypeforcalendar.dto}")
	private String buildLiturgyTypeForCalendarMissingDto;

	@Value("${cycledayliturgytypeservice.buildliturgytypeforcalendar.calendar}")
	private String buildLiturgyTypeForCalendarMissingCalendar;

	@Value("${cycledayliturgytypeservice.buildliturgytypeforcalendar.calendardates}")
	private String buildLiturgyTypeForCalendarMissingCalendarDates;

	@Value("${cycledayliturgytypeservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${cycledayliturgytypeservice.initmodel.user}")
	private String initModelMissingUser;

	@Value("${calendar.gregorian.id}")
	private Long calendarGregorianId;

	@Value("${calendar.julian.id}")
	private Long calendarJulianId;

	@Value("${calendar.hijri.id}")
	private Long calendarHijriId;

	@Value("${calendar.hebrew.id}")
	private Long calendarHebrewId;

	@Value("${calendar.all.id}")
	private Long calendarAllId;
}
