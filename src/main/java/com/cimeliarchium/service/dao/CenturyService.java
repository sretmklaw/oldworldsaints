package com.cimeliarchium.service.dao;

import java.util.ArrayList;
import java.util.LinkedList;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Century;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.repository.dao.CenturyRepository;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;
import com.google.common.collect.ImmutableList;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class CenturyService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,Century>,
				BaseSearchableDAOServiceIF<Long,Century> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CenturyService.class);

	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.CENTURY_MAP_BY_ID.getValue();

	private Map<Long,Century> centuryMap;

	private CenturyRepository repo;

	@Autowired
	public CenturyService(CenturyRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Century> query() {

		final List<Century> queryResults = this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "centuryId"));
		LOGGER.info("CenturyRepository returned {} results", queryResults.size());
		return queryResults;
	}

	@SuppressWarnings("unchecked")
	public void map() throws AppException {

		this.centuryMap = ((List<Century>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								Century::getCenturyId, // Key
								century -> century)); // Value
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
					centuryMap, 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Long, Century> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	/**
	 * User-specific Model initializer
	 * 
	 * @param session the HttpSession
	 * @param model the Model
	 * @param user the User
	 * @throws AppException
	 */
	public void initModelForUser(
			@NotNull HttpSession session, 
			Model model, 
			@NotNull User user) throws AppException {

		if (model != null && model.containsAttribute(ModelAttributeEnum.CENTURY_LIST.getValue())) {
			return;
		}
		super.throwIfMissing(user, initModelMissingUser);
		initModel(session, model, user.getCalendarId());
	}

	private void initModel(
			@NotNull HttpSession session, 
			Model model,
			Long calendarId) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		if (calendarId != null) {
			model.addAttribute(
					ModelAttributeEnum.CENTURY_LIST.getValue(),
					new LinkedList<Century>(this.getCachedValues(session).entrySet().stream()
							.map(e -> {
								Century century = e.getValue();
								century.getCenturyNameFormatted(calendarId);
								return century;
							}).collect(Collectors.toList())));
		} else {
			model.addAttribute(ImmutableList.of());
		}
	}

	@Deprecated
	public void initModel(HttpSession session, Model model, User user) { }

	public Long getSearchOrRandomId(
			Long id,
			Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap,
			Long riteId) {

		// When random ID is specified, filter the mapping of all Commemoration DTOs by Century ID down
		// to a list of available Century IDs for the specified Rite ID and then pick a random entry.
		if (id == ID_SEARCH_RANDOM_INDICATOR) {
			final List<Long> centuryIdsForRiteId = new ArrayList<>();
			for (Entry<Long, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final Long centuryId = entry.getKey();
				final List<Long> commemorationRiteIds = entry.getValue().entrySet()
						.iterator().next().getValue().stream()
						.map(Rite::getRiteId)
						.collect(Collectors.toList());
				if (riteId == riteAllId || commemorationRiteIds.contains(riteId)) {
					centuryIdsForRiteId.add(centuryId);
				}
			}
			final Long[] eligibleCenturyIds = centuryIdsForRiteId.toArray(Long[]::new);
			return eligibleCenturyIds[new Random().nextInt(eligibleCenturyIds.length)];
		} 
		// Otherwise, simply return the passed-in ID with no change.
		else {
			return id;
		}
	}

	@Override
	public void populateSearchHeader(
			Model model, 
			Long id,
			Map<Long,Century> entityMap,
			Long calendarId) throws AppException {

		String searchTerm = null;
		if (id != null && entityMap != null) {
			final Century century = entityMap.get(id);
			if (century != null) {
				searchTerm = century.getCenturyNameFormatted(calendarId);
			}
			// Add the list of all available Centuries for browse links.
			// Ensure that display name is initialized correctly for current calendar.
			model.addAttribute(ModelAttributeEnum.CENTURY_LIST.getValue(),
					entityMap.values().stream().map(c -> {
						c.setCenturyDisplayName(c.getCenturyNameFormatted(calendarId).replaceAll("Century ", ""));
						return c;
					}).collect(Collectors.toList()));
		}
		ModelAttributeHelperService.addSearchTypeAndTerm(model, null, searchTerm);
	}

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${centuryservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${centuryservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${centuryservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${centuryservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${centuryservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${centuryservice.initmodel.user}")
	private String initModelMissingUser;

	@Value("${centuryservice.initmodel.request}")
	private String initModelMissingRequest;

	@Value("${calendar.gregorian.id}")
	private Long calendarGregorianId;

	@Value("${calendar.julian.id}")
	private Long calendarJulianId;

	@Value("${calendar.hijri.id}")
	private Long calendarHijriId;

	@Value("${calendar.hebrew.id}")
	private Long calendarHebrewId;

	@Value("${century.timeimmemorial.id}")
	private Long centuryTimeImmemorialId;
}
