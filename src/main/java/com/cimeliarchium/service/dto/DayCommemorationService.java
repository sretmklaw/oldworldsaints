package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cimeliarchium.enums.CalendarRiteEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.CalendarDates;
import com.cimeliarchium.model.dao.Commemoration;
import com.cimeliarchium.model.dao.CycleDay;
import com.cimeliarchium.model.dao.Day;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.Reading;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

/**
 * For performance reasons, this service is used to perform one-time static initialization of 
 * Day Commemoration mappings on application startup. Note that any interactions with the database 
 * done after startup will need to be performed as discreet updates to these class-level mappings 
 * in order for them to get reflected in an already-running instance of the application.
 */
@Service
public class DayCommemorationService extends SessionAttributeHelperService {

	@Autowired
	private EntityManager entityManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(DayCommemorationService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.DAY_COMMEMORATION_MAP.getValue();
	public static final String SESSION_ATTRIBUTE_BY_ID_NAME = SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_ID.getValue();
	private static final String STORED_PROCEDURE_NAME = "DayCommemorationMappingQuery";

	private List<DayCommemorationDto> dayCommemorationQueryResults;

	private Map<DayCommemorationDto, List<Rite>> dtoMap = new ConcurrentHashMap<>();
	private Map<Long, DayCommemorationDto> dtoMapById = new ConcurrentHashMap<>();
	private Map<Long, Map<DayCommemorationDto, List<Rite>>> mapById = new ConcurrentHashMap<>();
	private Map<Long, Map<DayCommemorationDto, List<Rite>>> mapByDay = new ConcurrentHashMap<>();
	private Map<Long, Map<DayCommemorationDto, List<Rite>>> mapByNation = new ConcurrentHashMap<>();
	private Map<Long, Map<DayCommemorationDto, List<Rite>>> mapByCentury = new ConcurrentHashMap<>();
	private Map<Long, Map<DayCommemorationDto, List<Rite>>> mapByPatronage = new ConcurrentHashMap<>();
	private Map<Long, Map<DayCommemorationDto, List<Rite>>> mapByStar = new ConcurrentHashMap<>();
	private Map<Long, Map<DayCommemorationDto, List<Rite>>> mapByTag = new ConcurrentHashMap<>();
	private Map<Long, Map<DayCommemorationDto, List<Rite>>> mapByLocation = new ConcurrentHashMap<>();
	private Map<String, Map<DayCommemorationDto, List<Rite>>> mapByName = new ConcurrentHashMap<>();
	private Map<String, Set<Reading>> mapReadingByTitle = new ConcurrentHashMap<>();
	private Map<Long, Reading> mapReadingById = new ConcurrentHashMap<>();
	private Map<Long, List<Long>> mapIdsByRite = new ConcurrentHashMap<>();
	private Map<Long, List<String>> mapNamesByRite = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public void query() throws AppException {

		dayCommemorationQueryResults = (List<DayCommemorationDto>) super.throwIfMissing(
				entityManager.createNamedStoredProcedureQuery(STORED_PROCEDURE_NAME).getResultList(), 
				mapMissingDtoList);
		LOGGER.info("{} returned {} results", STORED_PROCEDURE_NAME, dayCommemorationQueryResults.size());
	}

	public Map<DayCommemorationDto, List<Rite>> map() throws AppException {

		// Loop over entries of the Calendar-Rite mapping once for each DayCommemorationDto to map by Rite.
		// For performance reasons, populate all within a single loop over the list of DTOs.
		// Conditionally initialize Administrator console values at this point as well.
		Map<DayCommemorationDto, List<Rite>> flatMapWithRites = new ConcurrentHashMap<>();
		Map<Long, List<Long>> flatMapIdsByRite = new ConcurrentHashMap<>();
		Map<Long, List<String>> flatMapNamesByRite = new ConcurrentHashMap<>();
		for (DayCommemorationDto dto : dayCommemorationQueryResults) {

			// Map all the Rites applicable to each DTO, based on Creed ID
			final List<Rite> riteList = new ArrayList<>();
			final Commemoration commemoration = dto.getCommemoration();
			final Long commemorationId = commemoration.getCommemorationId();
			// Always add each commemoration to 'All Commemorations' entry
			flatMapIdsByRite.computeIfAbsent(riteAllId, n -> new ArrayList<Long>()).add(commemorationId);
			final String commemorationName = commemoration.getCommemorationName();
			final var creedId = dto.getCreed().getCreedId();
			LOGGER.debug("Found {} '{}' with creed {}", commemorationId, commemorationName, creedId);
			for (Map.Entry<Calendar, List<Rite>> ritesByCalendar : CalendarRiteEnum.getCalendarRiteMap().entrySet()) {
				for (Rite rite : ritesByCalendar.getValue()) {
					String riteName = rite.getRiteName();
					if (rite.hasCreed(creedId) && !rite.isSuppressedForCommemoration(dto)) {
						LOGGER.trace("- Adding {} '{}' as {}", commemorationId, commemorationName, riteName);
						flatMapWithRites.computeIfAbsent(dto, n -> new ArrayList<Rite>()).add(rite);
						riteList.add(rite);
						flatMapIdsByRite.computeIfAbsent(rite.getRiteId(), n -> new ArrayList<Long>()).add(commemorationId);
						flatMapNamesByRite.computeIfAbsent(rite.getRiteId(), n -> new ArrayList<String>()).add(commemoration.getCommemorationName());
					} else {
						LOGGER.trace("- Skipped {} '{}' as {}", commemorationId, commemorationName, riteName);
					}
				}
			}
			this.buildMappingsForEntry(dto, riteList);
			this.mapIdsByRite = flatMapIdsByRite;
			this.mapNamesByRite = flatMapNamesByRite;
		}
		dtoMap = flatMapWithRites;
		return flatMapWithRites;
	}

	/**
	 * Method used to populate mappings of DayCommemorationDto by various entities
	 * 
	 * @param dto the DayCommemorationDto
	 * @param riteList the list of Rites
	 * @throws AppException 
	 */
	private void buildMappingsForEntry(
			@NotNull DayCommemorationDto dto,
			@NotNull List<Rite> riteList) throws AppException {

		SessionAttributeHelperService.throwIfMissing(dto, buildMappingsForEntryMissingDto);
		SessionAttributeHelperService.throwIfMissing(riteList, buildMappingsForEntryMissingRiteList);

		final var commemorationId = dto.getCommemoration().getCommemorationId();
		dtoMapById.put(commemorationId, dto);
		this.safeAddDtoWithRitesToMap(mapById, dto, riteList,
				"Commemoration ID", Long.class, commemorationId);
		this.safeAddDtoWithRitesToMap(mapByDay, dto, riteList,
				"Day ID", Long.class, dto.getDay().getDayId());
		this.safeAddDtoWithRitesToMap(mapByDay, dto, riteList,
				"Alt Day AD ID", Long.class, dto.getAltDayAd().getDayId());
		this.safeAddDtoWithRitesToMap(mapByDay, dto, riteList,
				"Alt Day AH ID", Long.class, dto.getAltDayAh().getDayId());
		this.safeAddDtoWithRitesToMap(mapByDay, dto, riteList,
				"Alt Day AM ID", Long.class, dto.getAltDayAm().getDayId());
		this.safeAddDtoWithRitesToMap(mapByNation, dto, riteList,
				"Nation ID", Long.class, dto.getNation().getNationId());
		this.safeAddDtoWithRitesToMap(mapByCentury, dto, riteList,
				"Century ID", Long.class, dto.getCentury().getCenturyId());
		this.safeAddDtoWithRitesToMap(mapByPatronage, dto, riteList,
				"Patronage ID", Long.class, dto.getPatronage().getPatronageId());
		this.safeAddDtoWithRitesToMap(mapByStar, dto, riteList,
				"Star ID", Long.class, dto.getStar().getStarId());
		this.safeAddDtoWithRitesToMap(mapByTag, dto, riteList,
				"Tag A ID", Long.class, dto.getTagA().getTagId());
		this.safeAddDtoWithRitesToMap(mapByTag, dto, riteList,
				"Tag B ID", Long.class, dto.getTagB().getTagId());
		this.safeAddDtoWithRitesToMap(mapByTag, dto, riteList,
				"Tag C ID", Long.class, dto.getTagC().getTagId());
		this.safeAddDtoWithRitesToMap(mapByTag, dto, riteList,
				"Tag D ID", Long.class, dto.getTagD().getTagId());
		this.safeAddDtoWithRitesToMap(mapByTag, dto, riteList,
				"Tag E ID", Long.class, dto.getTagE().getTagId());
		this.safeAddDtoWithRitesToMap(mapByLocation, dto, riteList,
				"Location ID", Long.class, dto.getLocation().getLocationId());
		this.safeAddDtoWithRitesToMap(mapByLocation, dto, riteList,
				"Alt Location ID", Long.class, dto.getAltLocation().getLocationId());
		this.safeAddDtoWithRitesToMap(mapByName, dto, riteList,
				"Name", String.class, dto.getCommemoration().getCommemorationName());
		this.safeAddDtoWithRitesToMap(mapByName, dto, riteList,
				"Alt Name", String.class, dto.getCommemoration().getCommemorationAltName());

		final Reading dtoReading = dto.getReading();
		if (dtoReading != null && dtoReading.getReadingId() != null) {
			this.safeAddValToMapOfLists(mapReadingByTitle, Reading.class, dtoReading, 
					String.class, "ReadingTitle", dto.getReadingName());
			this.safeAddValToMap(mapReadingById, dtoReading.getReadingId(), Reading.class, dtoReading);
		}
	}

	/**
	 * Method used to insert unique key and value pair
	 * 
	 * @param targetMap the mapping of values by ID
	 * @param key the ID
	 * @param valType the mapped field Type
	 * @param val the entity to be added
	 */
	private <T> void safeAddValToMap(
			Map<Long, T> targetMap, 
			Long key,
			Class<T> valType,
			T val) {

		if (key != null) {
			LOGGER.trace("- Adding new entry for ID '{}'", key);
			targetMap.put(key, val);
		} else {
			LOGGER.trace("- Skipped mapping new key for ID '{}'", key);
		}
	}

	/**
	 * Method used to aggregate by unique key to existing list of values, or insert with new key
	 * 
	 * @param targetMap the mapping of values by ID
	 * @param valType the mapped field Type
	 * @param val the mapped field value
	 * @param keyType the Type of the Key
	 * @param keyName the String description of the mapped field
	 * @param key the ID to be added
	 */
	private <T,K> void safeAddValToMapOfLists(
			Map<K, Set<T>> targetMap, 
			Class<T> valType, 
			T val,
			Class<K> keyType, 
			String keyName,
			K key) {

		if (key != null) {
			targetMap.computeIfAbsent(key, n -> { 
				// Initialize an entryMap for each new key, where not already existing
				LOGGER.trace("- Adding new entry for {} ID '{}'", keyName, key);
				return new TreeSet<T>();
			}).add(val);
		} else {
			LOGGER.trace("- Skipped mapping new key for {} '{}'", keyName, key);
		}
	}

	/**
	 * Method used to aggregate by unique key to existing list of values, or insert with new key
	 * 
	 * @param targetMap the mapping of values by keyType
	 * @param dto the DayCommemorationDto to be added
	 * @param riteList the list of Rites for the DayCommemorationDto being added
	 * @param keyName the String description of the mapped field
	 * @param keyType the mapped field Type
	 * @param key the mapped field value
	 */
	private <T> void safeAddDtoWithRitesToMap(
			Map<T, Map<DayCommemorationDto, List<Rite>>> targetMap, 
			DayCommemorationDto dto, 
			List<Rite> riteList,
			String keyName, 
			Class<T> keyType, 
			T key) {

		if (key != null) {
			targetMap.computeIfAbsent(key, n -> { 
				// Initialize an entryMap for each new key, where not already existing
				LOGGER.trace("- Adding new entry for {} '{}'", keyName, key);
				return new TreeMap<DayCommemorationDto, List<Rite>>();
			}).put(dto, riteList);
		} else {
			LOGGER.trace("- Skipped mapping new key for {} '{}'", keyName, key);
		}
	}

	/**
	 * Method used to obtain dynamic mapping of Day Commemorations with Rites for a target date,
	 * plus a listing of unique Location-specific Patronage.
	 * 
	 * @param session the HttpSession
	 * @param cycleDay the CycleDay
	 * @return resultPair
	 * @throws AppException 
	 * @throws ParseDateException 
	 */
	public ImmutablePair<Map<DayCommemorationDto, List<Rite>>,List<Patronage>> findDayCommemorationsForTargetDate(
			@NotNull HttpSession session,
			@NotNull CycleDay cycleDay, 
			@NotNull User user) throws AppException {

		SessionAttributeHelperService.throwIfMissing(session, findDayCommemorationsForTargetDateMissingSession);
		SessionAttributeHelperService.throwIfMissing(cycleDay, findDayCommemorationsForTargetDateMissingCycleDay);
		SessionAttributeHelperService.throwIfMissing(user, findDayCommemorationsForTargetDateMissingUser);

		// Map all DTOs applicable to the target CalendarDate
		Map<DayCommemorationDto, List<Rite>> resultMap = new TreeMap<>();
		Map<Long,Patronage> locationPatronagesById = new HashMap<>();
		final Long cycleDayLiturgyTypeDtoId = cycleDay.getCycleDayLiturgyTypeDtoId();
		final Long userCalendarId = user.getCalendarId();
		final Long userRiteId = user.getRiteId();
		final CalendarDates calendarDates = cycleDay.getCalendarDates();

		for (Map.Entry<DayCommemorationDto, List<Rite>> entry : dtoMap.entrySet()) {

			final DayCommemorationDto dto = entry.getKey();
			final List<Rite> riteList = entry.getValue();

			// Determine whether or not this Commemoration should display based on the User's Rite.
			final UserDisplayIndicators display = this.getUserDisplayIndicators(userCalendarId, userRiteId, riteList);
			final String commemorationName = dto.getCommemoration().getCommemorationName();
			if (display.isVisibleForUser) {

				// Then check for matching fixed date Commemoration by date string for given Calendar
				final Day day = dto.getDay();
				Boolean isVisibleForTargetDate = false;
				if (day.getMonthId() != monthVariableId 
						&& day.getMonthName() != null 
						&& day.getDayOfMonth() != null) {

					final String dateString = day.getDayOfMonth() + " " + day.getMonthName();
					if (calendarDates.hasMatchingDateString(dateString, 
							display.isGregorianOrKadmiSolarDateApplicable, 
							display.isJulianSolarDateApplicable)) {
						LOGGER.trace("- Found '{}' matching date {}", commemorationName, dateString);
						isVisibleForTargetDate = true;
						resultMap.put(dto, riteList);
					}
				}
				// Otherwise, check for matching moveable-date Commemoration by composite key
				else if (cycleDayLiturgyTypeDtoId.equals(day.getDayId())) {
					LOGGER.trace("- Found '{}' matching cycle day {}", commemorationName, cycleDayLiturgyTypeDtoId);
					isVisibleForTargetDate = true;
					resultMap.put(dto, riteList);
				}
				// Aggregate any location-specific Patronage having non-null image for display
				if (isVisibleForTargetDate) {
					final Patronage dtoPatronage = dto.getPatronage();
					final Long patronageId = dtoPatronage.getPatronageId();
					final Long patronageTypeId = dtoPatronage.getPatronageTypeId();
					final Long patronageSubtypeId = dtoPatronage.getPatronageSubtypeId();
					final Double patronagePointX = dtoPatronage.getPointX();
					final Double patronagePointY = dtoPatronage.getPointY();
					if (((patronageTypeId != null && patronageTypeId == patronageTypePlaceId) 
							|| (patronageSubtypeId != null && patronageSubtypeId == patronageSubtypeReligiousId))
						&& patronagePointX != null
						&& patronagePointY != null
						&& !locationPatronagesById.containsKey(patronageId)) {
						locationPatronagesById.put(patronageId, dtoPatronage);
					}
					final Patronage locationRelatedPatronage = dto.getLocationRelatedPatronage();
					final Long locationRelatedPatronageId = locationRelatedPatronage.getPatronageId();
					final Double locationRelatedPatronagePointX = locationRelatedPatronage.getPointX();
					final Double locationRelatedPatronagePointY = locationRelatedPatronage.getPointY();
					if (locationRelatedPatronagePointX != null 
							&& locationRelatedPatronagePointY != null 
							&& !locationPatronagesById.containsKey(locationRelatedPatronageId)) {
						locationPatronagesById.put(locationRelatedPatronageId, locationRelatedPatronage);
					}
					final Patronage altLocationRelatedPatronage = dto.getAltLocationRelatedPatronage();
					final Long altLocationRelatedPatronageId = altLocationRelatedPatronage.getPatronageId();
					final Double altLocationRelatedPatronagePointX = altLocationRelatedPatronage.getPointX();
					final Double altLocationRelatedPatronagePointY = altLocationRelatedPatronage.getPointY();
					if (altLocationRelatedPatronagePointX != null 
							&& altLocationRelatedPatronagePointY != null 
							&& !locationPatronagesById.containsKey(altLocationRelatedPatronageId)) {
						locationPatronagesById.put(altLocationRelatedPatronageId, altLocationRelatedPatronage);
					}
				}
			} else {
				LOGGER.trace("- Skipped adding '{}' for Rite ID {}", commemorationName, user.getRiteId());
			}
		}
		return new ImmutablePair<>(resultMap, new ArrayList<>(locationPatronagesById.values()));
	}

	/**
	 * Determine whether or not this Commemoration should display based on the User's Rite.
	 * This will evaluate as true for all, if User's Calendar is 'All Commemorations'.
	 * 
	 * @param userCalendarId the Calendar ID
	 * @param userRiteId the Rite ID
	 * @param riteList the list of Rite
	 * @return UserDisplayIndicators
	 */
	private UserDisplayIndicators getUserDisplayIndicators(Long userCalendarId, Long userRiteId, List<Rite> riteList) {

		Boolean isVisibleForUser = false;
		Boolean isGregorianOrKadmiSolarDateApplicable = false;
		Boolean isJulianSolarDateApplicable = false;
		for (Rite rite : riteList) {
			final Long riteId = rite.getRiteId();
			if (userCalendarId == calendarAllId || userRiteId == riteId) {
				// Always visible for user, if matching Rite.
				isVisibleForUser = true;
				final Boolean isGregorianSolarDateApplicable = (riteId == riteTridentineId 
						|| riteId == riteNovusOrdoId 
						|| riteId == riteLutheranId 
						|| riteId == riteAnglicanId);
				final Boolean isKadmiSolarDateApplicable = (riteId == riteSunniId || riteId == riteShiiteId);
				// Set the calendar indicators if any rites match, in order
				// to ensure that we do not flip back to false once they have been set.
				if (isGregorianSolarDateApplicable || isKadmiSolarDateApplicable) {
					isGregorianOrKadmiSolarDateApplicable = true;
				}
				if (riteId == riteMeletianId) {
					isJulianSolarDateApplicable = true;
				}
			}
		}
		return new UserDisplayIndicators(
				isVisibleForUser, 
				isGregorianOrKadmiSolarDateApplicable, 
				isJulianSolarDateApplicable);
	}

	/**
	 * Helper class used to pass calendar-based display indicators for a given user.
	 */
	private static class UserDisplayIndicators {

		protected Boolean isVisibleForUser = false;
		protected Boolean isGregorianOrKadmiSolarDateApplicable = false;
		protected Boolean isJulianSolarDateApplicable = false;

		protected UserDisplayIndicators(
				Boolean isVisibleForUser, 
				Boolean isGregorianOrKadmiSolarDateApplicable,
				Boolean isJulianSolarDateApplicable) {
			this.isVisibleForUser = isVisibleForUser;
			this.isGregorianOrKadmiSolarDateApplicable = isGregorianOrKadmiSolarDateApplicable;
			this.isJulianSolarDateApplicable = isJulianSolarDateApplicable;
		}
	}

	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);

		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			BaseMultiMapServiceHelper.addSessionAttribute(
					LOGGER,
					this.map(), 
					session, 
					SESSION_ATTRIBUTE_NAME);
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					this.mapById, 
					session, 
					SESSION_ATTRIBUTE_BY_ID_NAME);
		}
		this.safeAddDtoWithRitesMapSessionAttribute(session, 
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_DAY.getValue(),
				this.mapByDay, Long.class);
		this.safeAddDtoWithRitesMapSessionAttribute(session, 
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_NATION.getValue(), 
				this.mapByNation, Long.class);
		this.safeAddDtoWithRitesMapSessionAttribute(session, 
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_CENTURY.getValue(), 
				this.mapByCentury, Long.class);
		this.safeAddDtoWithRitesMapSessionAttribute(session, 
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_PATRONAGE.getValue(), 
				this.mapByPatronage, Long.class);
		this.safeAddDtoWithRitesMapSessionAttribute(session, 
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_STAR.getValue(), 
				this.mapByStar, Long.class);
		this.safeAddDtoWithRitesMapSessionAttribute(session, 
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_TAG.getValue(), 
				this.mapByTag, Long.class);
		this.safeAddDtoWithRitesMapSessionAttribute(session, 
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_LOCATION.getValue(), 
				this.mapByLocation, Long.class);
		this.safeAddDtoWithRitesMapSessionAttribute(session, 
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_NAME.getValue(), 
				this.mapByName, String.class);

		if (!super.isInitialized(session, SessionAttributeEnum.COMMEMORATION_READING_MAP_BY_TITLE.getValue())) {
			hasUpdate = true;
			session.setAttribute(SessionAttributeEnum.COMMEMORATION_READING_MAP_BY_TITLE.getValue(), 
					this.mapReadingByTitle);
		}
		if (!super.isInitialized(session, SessionAttributeEnum.COMMEMORATION_READING_MAP_BY_ID.getValue())) {
			hasUpdate = true;
			session.setAttribute(SessionAttributeEnum.COMMEMORATION_READING_MAP_BY_ID.getValue(), 
					this.mapReadingById);
		}
		if (!super.isInitialized(session, SessionAttributeEnum.DAY_COMMEMORATION_MAP_IDS_BY_RITE.getValue())) {
			hasUpdate = true;
			session.setAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_IDS_BY_RITE.getValue(), 
					this.mapIdsByRite);
		}
		if (!super.isInitialized(session, SessionAttributeEnum.DAY_COMMEMORATION_MAP_NAMES_BY_RITE.getValue())) {
			hasUpdate = true;
			session.setAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_NAMES_BY_RITE.getValue(), 
					this.mapNamesByRite);
		}
		return hasUpdate;
	}

	/**
	 * Method used to initialize session attributes for the given map type
	 * 
	 * @param session the HttpSession
	 * @param attribute the Attribute name
	 * @param map the mapping of values by mapType
	 * @param mapType the Type of mapped values
	 * @throws AppException 
	 * @return hasUpdate
	 */
	private <T> Boolean safeAddDtoWithRitesMapSessionAttribute(
			HttpSession session, 
			String attribute, 
			Map<T, Map<DayCommemorationDto, List<Rite>>> map, 
			Class<T> mapType) throws AppException {

		Boolean hasUpdate = false;
		if (!super.isInitialized(session, attribute)) {
			hasUpdate = true;
			session.setAttribute(attribute, new TreeMap<T, Map<DayCommemorationDto, List<Rite>>>(map));
		}
		return hasUpdate;
	}

	public Map<DayCommemorationDto, List<Rite>> getDtoMap() {
		return dtoMap;
	}

	public Map<Long, DayCommemorationDto> getDtoMapById() {
		return dtoMapById;
	}

	public Map<Long, Map<DayCommemorationDto, List<Rite>>> getMapById() {
		return mapById;
	}

	public Map<Long, Map<DayCommemorationDto, List<Rite>>> getMapByDay() {
		return mapByDay;
	}

	public Map<Long, Map<DayCommemorationDto, List<Rite>>> getMapByNation() {
		return mapByNation;
	}

	public Map<Long, Map<DayCommemorationDto, List<Rite>>> getMapByCentury() {
		return mapByCentury;
	}

	public Map<Long, Map<DayCommemorationDto, List<Rite>>> getMapByPatronage() {
		return mapByPatronage;
	}

	public Map<Long, Map<DayCommemorationDto, List<Rite>>> getMapByTag() {
		return mapByTag;
	}

	public Map<Long, Map<DayCommemorationDto, List<Rite>>> getMapByLocation() {
		return mapByLocation;
	}

	public Map<String, Map<DayCommemorationDto, List<Rite>>> getMapByName() {
		return mapByName;
	}

	public Map<String, Set<Reading>> getMapReadingByTitle() {
		return mapReadingByTitle;
	}

	public Map<Long, Reading> getMapReadingById() {
		return mapReadingById;
	}

	public Map<Long, List<Long>> getMapIdsByRite() {
		return mapIdsByRite;
	}

	public Map<Long, List<String>> getMapNamesByRite() {
		return mapNamesByRite;
	}

	@Value("${daycommemorationservice.map.dtolist}")
	private String mapMissingDtoList;

	@Value("${daycommemorationservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${daycommemorationservice.buildmappingsforentry.dto}")
	private String buildMappingsForEntryMissingDto;

	@Value("${daycommemorationservice.buildmappingsforentry.ritelist}")
	private String buildMappingsForEntryMissingRiteList;

	@Value("${daycommemorationservice.addmappingstosession.session}")
	private String findDayCommemorationsForTargetDateMissingSession;

	@Value("${daycommemorationservice.addmappingstosession.cycleday}")
	private String findDayCommemorationsForTargetDateMissingCycleDay;

	@Value("${daycommemorationservice.addmappingstosession.user}")
	private String findDayCommemorationsForTargetDateMissingUser;

	@Value("${month.variable.id}")
	private Long monthVariableId;

	@Value("${patronagetype.place.id}")
	private Long patronageTypePlaceId;

	@Value("${patronagesubtype.religious.id}")
	private Long patronageSubtypeReligiousId;

	@Value("${calendar.all.id}")
	private Long calendarAllId;

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${rite.tridentine.id}")
	private Long riteTridentineId;

	@Value("${rite.novusordo.id}")
	private Long riteNovusOrdoId;

	@Value("${rite.anglican.id}")
	private Long riteAnglicanId;

	@Value("${rite.lutheran.id}")
	private Long riteLutheranId;

	@Value("${rite.meletian.id}")
	private Long riteMeletianId;

	@Value("${rite.sunni.id}")
	private Long riteSunniId;

	@Value("${rite.shiite.id}")
	private Long riteShiiteId;

}
