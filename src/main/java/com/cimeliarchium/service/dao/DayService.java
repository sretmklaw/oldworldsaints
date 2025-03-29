package com.cimeliarchium.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
import com.cimeliarchium.model.dao.Day;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.dto.CalendarMonthDayService;
import com.cimeliarchium.service.dto.DayCommemorationService;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/application.properties",
	"classpath:/errorcode.properties"
})
public class DayService extends SessionAttributeHelperService
		implements BaseCacheableDAOServiceIF <Long,Day>,
				BaseOneToOneMapServiceIF<Long,Day>,
				BaseSearchableDAOServiceIF<Long,Day> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DayService.class);
	
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.DAY_MAP_BY_ID.getValue();

	private DayCommemorationService dayCommemorationStaticInitializer;
	private CalendarMonthDayService calendarMonthDayService;

	@Autowired
	public DayService(DayCommemorationService dayCommemorationStaticInitializer,
			CalendarMonthDayService calendarMonthDayService) {
		this.dayCommemorationStaticInitializer = dayCommemorationStaticInitializer;
		this.calendarMonthDayService = calendarMonthDayService;
	}

	@Override
	public List<Day> list() throws AppException {

		final List<Day> rs = new ArrayList<>();
		for (DayCommemorationDto dto : dayCommemorationStaticInitializer.getDtoMapById().values()) {
			final Day day = dto.getDay();
			if (day.getMonthAndDayFormatted() != null && !rs.contains(day)) {
				rs.add(day);
			}
			final Day altDayAd = dto.getAltDayAd();
			if (altDayAd.getMonthAndDayFormatted() != null && !rs.contains(altDayAd)) {
				rs.add(altDayAd);
			}
			final Day altDayAh = dto.getAltDayAd();
			if (altDayAh.getMonthAndDayFormatted() != null && !rs.contains(altDayAh)) {
				rs.add(altDayAh);
			}
			final Day altDayAm = dto.getAltDayAm();
			if (altDayAm.getMonthAndDayFormatted() != null && !rs.contains(altDayAm)) {
				rs.add(altDayAm);
			}
		}
		return rs.stream().sorted().collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long,Day> map() throws AppException {

		return ((List<Day>) super.throwIfMissing(
				this.list(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								Day::getDayId, // Key
								day -> day)); // Value
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					this.map(), 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Long, Day> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_NAME,
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	@Deprecated
	@Override
	public void initModel(
			@NotNull HttpSession session, 
			Model model, 
			@NotNull User user) throws AppException {

		// Intentionally, do nothing.
	}

	@Override
	public Long getSearchOrRandomId(
			Long id,
			Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap,
			Long riteId) {

		// When random ID is specified, filter the mapping of all Commemoration DTOs by Day ID down
		// to a list of available Day IDs for the specified Rite ID and then pick a random entry.
		if (id == ID_SEARCH_RANDOM_INDICATOR) {
			final List<Long> dayIdsWithRiteId = new ArrayList<>();
			for (Entry<Long, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final Long dayId = entry.getKey();
				final List<Long> commemorationRiteIds = entry.getValue().entrySet()
						.iterator().next().getValue().stream()
						.map(Rite::getRiteId)
						.collect(Collectors.toList());
				if (riteId == riteAllId || commemorationRiteIds.contains(riteId)) {
					dayIdsWithRiteId.add(dayId);
				}
			}
			final Long[] eligibleDayIds = dayIdsWithRiteId.toArray(Long[]::new);
			return eligibleDayIds[new Random().nextInt(eligibleDayIds.length)];
		} 
		// Otherwise, simply return the passed-in ID with no change.
		else {
			return id;
		}
	}

	@Override
	@Deprecated
	public void populateSearchHeader(
			Model model, 
			Long id,
			Map<Long,Day> entityMap,
			Long calendarId) { 
		// Intentionally, do nothing.
	}

	public void populateSearchHeaderForDay(
			Model model, 
			Long id,
			Map<Long,Day> entityMap,
			HttpSession session) throws AppException {

		String searchTerm = null;
		if (id != null && entityMap != null) {
			final Day day = entityMap.get(id);
			if (day != null) {
				searchTerm = day.getMonthAndDayFormatted();
			}
		}
		// Add the list of all available Days for current Month browse links.
		model.addAttribute(ModelAttributeEnum.DAY_OF_MONTH_LIST.getValue(), 
				calendarMonthDayService.lookupMonthByDay(session, id));

		ModelAttributeHelperService.addSearchTypeAndTerm(model, "calendar date of", searchTerm);
	}

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${dayservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${dayservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${dayservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${dayservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;
}