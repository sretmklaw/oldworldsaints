package com.cimeliarchium.service.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
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
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Day;
import com.cimeliarchium.model.dao.Month;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.CalendarMonthDayDto;
import com.cimeliarchium.service.BaseStaticMultiMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class CalendarMonthDayService extends SessionAttributeHelperService
		implements BaseSearchableDTOServiceIF<Calendar,Month>,
				BaseStaticMultiMapServiceIF<Calendar,Month> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CalendarMonthDayService.class);
	private static final String STORED_PROCEDURE_NAME = "CalendarMonthDayMappingQuery";
	private static final String SESSION_ATTRIBUTE_BY_CALENDAR_NAME = SessionAttributeEnum.MONTH_MAP_BY_CALENDAR.getValue();
	private static final String SESSION_ATTRIBUTE_BY_MONTH_NAME = SessionAttributeEnum.DAY_MAP_BY_MONTH.getValue();
	private static final String SESSION_ATTRIBUTE_BY_ID = SessionAttributeEnum.DAY_OF_MONTH_MAP_BY_ID.getValue();

	private List<CalendarMonthDayDto> calendarMonthDayQueryResults;
	private Map<Calendar,List<Month>> monthsByCalendarMap;
	private Map<Month,List<Day>> daysByMonthMap;
	private Map<Long,Day> daysByIdMap;

	@Autowired
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public void query() throws AppException {

		calendarMonthDayQueryResults = (List<CalendarMonthDayDto>) super.throwIfMissing(
				entityManager.createNamedStoredProcedureQuery(STORED_PROCEDURE_NAME).getResultList(), 
				mapMissingResultList);
		LOGGER.info("{} returned {} results", STORED_PROCEDURE_NAME, calendarMonthDayQueryResults.size());
	}

	@Override
	public void map() throws AppException {

		this.mapMonthsByCalendar();
		this.mapDaysByMonth();
		this.mapDaysById();
	}

	/**
	 * Method used to initialize mappings of Months by Calendar
	 * 
	 * @throws AppException
	 */
	public void mapMonthsByCalendar() throws AppException {

		monthsByCalendarMap = calendarMonthDayQueryResults.stream()
						.collect(Collectors.groupingBy(e -> e.getCalendar(), // Key
								Collectors.mapping(e -> e.getMonth(), Collectors.toList()))); // Value
	}

	/**
	 * Method used to initialize mappings of Days by Month
	 * 
	 * @throws AppException
	 */
	public void mapDaysByMonth() throws AppException {

		daysByMonthMap = calendarMonthDayQueryResults.stream()
						.collect(Collectors.groupingBy(e -> e.getMonth(), // Key
								Collectors.mapping(e -> e.getDay(), Collectors.toList()))); // Value
	}

	/**
	 * Method used to initialize mappings of Days by Month
	 * 
	 * @throws AppException
	 */
	public void mapDaysById() throws AppException {

		daysByIdMap = new TreeMap<>();
		for (CalendarMonthDayDto dto : calendarMonthDayQueryResults) {
			final Day day = dto.getDay();
			final Long dayId = day.getDayId();
			if (dayId != null) {
				daysByIdMap.put(dayId, day);
			}
		}
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		List<CalendarMonthDayDto> resultList = null;

		// Initialize mapping of Months by Calendar
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_BY_CALENDAR_NAME)) {
			hasUpdate = true;
			if (resultList == null) {
				resultList = calendarMonthDayQueryResults;
			}
			session.setAttribute(SESSION_ATTRIBUTE_BY_CALENDAR_NAME, monthsByCalendarMap);
			LOGGER.debug(
					SESSION_ATTRIBUTE_ADDED,
					SESSION_ATTRIBUTE_BY_CALENDAR_NAME,
					resultList.size());
		}
		// Initialize mapping of Days by Month
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_BY_MONTH_NAME)) {
			hasUpdate = true;
			if (resultList == null) {
				resultList = calendarMonthDayQueryResults;
			}
			session.setAttribute(SESSION_ATTRIBUTE_BY_MONTH_NAME, daysByMonthMap);
			LOGGER.debug(
					SESSION_ATTRIBUTE_ADDED,
					SESSION_ATTRIBUTE_BY_MONTH_NAME,
					resultList.size());
		}
		// Initialize mapping of Days by ID
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_BY_ID)) {
			hasUpdate = true;
			if (resultList == null) {
				resultList = calendarMonthDayQueryResults;
			}
			session.setAttribute(SESSION_ATTRIBUTE_BY_ID, daysByIdMap);
			LOGGER.debug(
					SESSION_ATTRIBUTE_ADDED,
					SESSION_ATTRIBUTE_BY_ID,
					resultList.size());
		}
		return hasUpdate;
	}

	@Deprecated
	@Override
	public Map<Calendar, List<Month>> getCachedValues(@NotNull HttpSession session) throws AppException {
		return null; // Intentionally, do nothing.
	}

	public Map<Calendar, List<Month>> getCachedValuesByCalendar(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_BY_CALENDAR_NAME,
				getCachedValuesByCalendarMissingSession,
				getCachedValuesByCalendarNotFound);
	}

	public Map<Month, List<Day>> getCachedValuesByMonth(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_BY_MONTH_NAME,
				getCachedValuesByMonthMissingSession,
				getCachedValuesByMonthNotFound);
	}

	public Map<Long, Day> getCachedValuesById(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_BY_ID,
				getCachedValuesByIdMissingSession,
				getCachedValuesByIdNotFound);
	}

	/**
	 * Used exclusively to initialize the Calendar list for navigation bar drop-down selection.
	 * 
	 * @throws AppException 
	 */
	@Override
	public void initModel(
			@NotNull HttpSession session, 
			Model model, 
			User user) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		final Set<Calendar> cachedCalendarList = this.getCachedValuesByCalendar(session).keySet();
		final List<Calendar> calendarList = new LinkedList<>();
		for (Calendar calendar : cachedCalendarList) {
			// All Commemorations and Julian are explicitly suppressed as unique entries in the listing.
			if (!calendar.getIsAllCommemorations() 
					&& !calendar.getIsJulian()) {
				calendarList.add(calendar);
			} 

		}
		model.addAttribute(ModelAttributeEnum.CALENDAR_LIST.getValue(), calendarList);
	}

	@Deprecated
	@Override
	public List<Calendar> listKeys(Map<Calendar, List<Month>> entityMap) throws AppException {
		return null; // Intentionally, do nothing.
	}

	@Deprecated
	@Override
	public List<Month> lookupById(HttpSession session, Long lookupId) throws AppException {
		return null; // Intentionally, do nothing.
	}

	/**
	 * Method used to determine if Year/Month/Day Input row should be displayed for the specified Creed.
	 * 
	 * @param session the HttpSession
	 * @param calendarId the Calendar ID
	 * @param creedId the Creed ID
	 * @return isCalendarApplicable
	 * @throws AppException
	 */
	public Boolean isCalendarApplicableToCreed(
			HttpSession session, 
			Long calendarId, 
			Long creedId) throws AppException {

		return lookupCalendarsByCreed(session, creedId).stream()
				.anyMatch(c -> c.getCalendarId() == calendarId);
	}

	/**
	 * Method used to obtain a list of Calendars for a given Creed
	 * 
	 * @param session the HttpSession
	 * @param creedId the Creed ID
	 * @return orderedCalendars
	 * @throws AppException
	 */
	public List<Calendar> lookupCalendarsByCreed(
			@NotNull HttpSession session,
			@NotNull Long creedId) throws AppException {

		super.throwIfMissing(session, lookupCalendarsByCreedMissingSession);
		super.throwIfMissing(creedId, lookupCalendarsByCreedMissingCreedId);

		Map<Calendar,List<Month>> entityMap = this.getCachedValuesByCalendar(session);
		super.throwIfMissing(entityMap, lookupMonthsByCalendarMissingEntityMap);

		final Map<Long,Calendar> calendarMap = entityMap.keySet().stream()
				.collect(Collectors.toMap(
						c -> c.getCalendarId(), // Key
						cal -> cal)); // Value

		final Set<Calendar> orderedCalendars = new TreeSet<>();
		if (creedId == creedJewishId) {
			orderedCalendars.add(calendarMap.get(calendarHebrewId));
		} else if (creedId == creedIslamicId 
				|| creedId == creedSunniId 
				|| creedId == creedShiiteId) {
			orderedCalendars.add(calendarMap.get(calendarHijriId));
		} else if (creedId == creedOrthodoxId 
				|| creedId == creedChalcedonianId) {
			orderedCalendars.add(calendarMap.get(calendarGregorianId));
			orderedCalendars.add(calendarMap.get(calendarJulianId));
		} else if (creedId == creedFilioquistId
				|| creedId == creedCatholicId
				|| creedId == creedProtestantId) {
			orderedCalendars.add(calendarMap.get(calendarGregorianId));
		} else if (creedId == creedApostolicId) {
			orderedCalendars.add(calendarMap.get(calendarGregorianId));
			orderedCalendars.add(calendarMap.get(calendarJulianId));
			orderedCalendars.add(calendarMap.get(calendarHijriId));
		} else {
			orderedCalendars.addAll(calendarMap.values());
		}
		return new LinkedList<>(orderedCalendars);
	}

	/**
	 * Method used to obtain a list of Months for a given Calendar
	 * 
	 * @param session the HttpSession
	 * @param calendarId the Calendar ID
	 * @return orderedMonths
	 * @throws AppException
	 */
	public List<Month> lookupMonthsByCalendar(
			@NotNull HttpSession session,
			Long calendarId) throws AppException {

		super.throwIfMissing(session, lookupMonthsByCalendarMissingSession);
		Map<Calendar,List<Month>> entityMap = this.getCachedValuesByCalendar(session);
		super.throwIfMissing(entityMap, lookupMonthsByCalendarMissingEntityMap);

		// Julian Calendar uses Gregorian Calendar month names, so simply convert where found.
		final Long expandedCalendarId = (calendarId == calendarJulianId) 
				? Optional.ofNullable((Long) calendarGregorianId).orElse(null) 
				: calendarId;
		final Set<Month> orderedMonths = new TreeSet<>();
		for (Map.Entry<Calendar,List<Month>> entry : entityMap.entrySet()) {
			final Calendar calendar = entry.getKey();
			final List<Month> monthList = entry.getValue();
			if (calendar.getCalendarId() == expandedCalendarId) {
				for (Month month : monthList) {
					// Explicitly exclude variable feasts from the month drop-down
					if (month.getMonthId() != monthVariableId) {
						orderedMonths.add(month);
					}
				}
			}
		}
		return new LinkedList<>(orderedMonths);
	}

	/**
	 * Method used to obtain a list of Days for a given Month
	 * 
	 * @param session the HttpSession
	 * @param monthId the Month ID
	 * @return orderedDays
	 * @throws AppException
	 */
	public List<Day> lookupDaysByMonth(
			@NotNull HttpSession session,
			Long monthId) throws AppException {

		super.throwIfMissing(session, lookupDaysByMonthMissingSession);
		Map<Month, List<Day>> entityMap = this.getCachedValuesByMonth(session);

		super.throwIfMissing(entityMap, lookupDaysByMonthMissingEntityMap);
		final Set<Day> orderedDays = new TreeSet<>();
		for (Entry<Month, List<Day>> entry : entityMap.entrySet()) {
			final Month month = entry.getKey();
			final List<Day> days = entry.getValue();
			if (month.getMonthId() == monthId) {
				orderedDays.addAll(days);
			}
		}
		return new LinkedList<>(orderedDays);
	}

	/**
	 * Method used to obtain a Month for a given Day
	 * 
	 * @param session the HttpSession
	 * @param monthId the Month ID
	 * @return orderedDays
	 * @throws AppException
	 */
	public List<Day> lookupMonthByDay(
			@NotNull HttpSession session,
			Long dayId) throws AppException {

		super.throwIfMissing(session, lookupMonthByDayMissingSession);
		Map<Long, Day> entityMap = this.getCachedValuesById(session);
		super.throwIfMissing(entityMap, lookupMonthByDayMissingEntityMap);
		final Day targetDay = entityMap.get(dayId);
		final Set<Day> orderedDays = new TreeSet<>();
		if (targetDay != null) {
			for (Day day : entityMap.values()) {
				if (day.getMonthId() == targetDay.getMonthId()) {
					orderedDays.add(day);
				}
			}
		}
		return new LinkedList<>(orderedDays);
	}

	@Value("${calendarmonthdayservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${calendarmonthdayservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${calendarmonthdayservice.getcachedvaluesbycalendar.session}")
	private String getCachedValuesByCalendarMissingSession;

	@Value("${calendarmonthdayservice.getcachedvaluesbycalendar.notfound}")
	private String getCachedValuesByCalendarNotFound;

	@Value("${calendarmonthdayservice.getcachedvaluesbyid.session}")
	private String getCachedValuesByIdMissingSession;

	@Value("${calendarmonthdayservice.getcachedvaluesbyid.notfound}")
	private String getCachedValuesByIdNotFound;

	@Value("${calendarmonthdayservice.getcachedvaluesbymonth.session}")
	private String getCachedValuesByMonthMissingSession;

	@Value("${calendarmonthdayservice.getcachedvaluesbymonth.notfound}")
	private String getCachedValuesByMonthNotFound;

	@Value("${calendarmonthdayservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${calendarmonthdayservice.initmodel.request}")
	private String initModelMissingRequest;

	@Value("${calendarmonthdayservice.lookupcalendarsbycreed.session}")
	private String lookupCalendarsByCreedMissingSession;

	@Value("${calendarmonthdayservice.lookupcalendarsbycreed.creedid}")
	private String lookupCalendarsByCreedMissingCreedId;

	@Value("${calendarmonthdayservice.lookupcalendarsbycreed.entitymap}")
	private String lookupCalendarsByCreedMissingEntityMap;

	@Value("${calendarmonthdayservice.lookupmonthsbycalendar.session}")
	private String lookupMonthsByCalendarMissingSession;

	@Value("${calendarmonthdayservice.lookupmonthsbycalendar.entitymap}")
	private String lookupMonthsByCalendarMissingEntityMap;

	@Value("${calendarmonthdayservice.lookupdaysbymonth.session}")
	private String lookupDaysByMonthMissingSession;

	@Value("${calendarmonthdayservice.lookupdaysbymonth.entitymap}")
	private String lookupDaysByMonthMissingEntityMap;

	@Value("${calendarmonthdayservice.lookupmonthbyday.session}")
	private String lookupMonthByDayMissingSession;

	@Value("${calendarmonthdayservice.lookupmonthbyday.entitymap}")
	private String lookupMonthByDayMissingEntityMap;

	@Value("${calendar.gregorian.id}")
	private Long calendarGregorianId;

	@Value("${calendar.julian.id}")
	private Long calendarJulianId;

	@Value("${calendar.hijri.id}")
	private Long calendarHijriId;

	@Value("${calendar.hebrew.id}")
	private Long calendarHebrewId;

	@Value("${creed.apostolic.id}")
	private Long creedApostolicId;

	@Value("${creed.chalcedonian.id}")
	private Long creedChalcedonianId;

	@Value("${creed.filioquist.id}")
	private Long creedFilioquistId;

	@Value("${creed.orthodox.id}")
	private Long creedOrthodoxId;

	@Value("${creed.islamic.id}")
	private Long creedIslamicId;

	@Value("${creed.sunni.id}")
	private Long creedSunniId;

	@Value("${creed.shiite.id}")
	private Long creedShiiteId;

	@Value("${creed.jewish.id}")
	private Long creedJewishId;

	@Value("${creed.catholic.id}")
	private Long creedCatholicId;

	@Value("${creed.protestant.id}")
	private Long creedProtestantId;

	@Value("${month.variable.id}")
	private Long monthVariableId;

}
