package com.cimeliarchium.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
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
import com.cimeliarchium.model.dao.Nation;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.dto.NationLocationService;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/application.properties",
	"classpath:/errorcode.properties"
})
public class NationService extends SessionAttributeHelperService
		implements BaseCacheableDAOServiceIF<Long,Nation>,
				BaseOneToOneMapServiceIF<Long,Nation>,
				BaseSearchableDAOServiceIF<Long,Nation> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NationService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.NATION_MAP_BY_ID.getValue();

	private HttpSession session;

	NationLocationService nationLocationService;

	@Autowired
	public NationService(NationLocationService nationLocationService) {
		this.nationLocationService = nationLocationService;
	}

	@Override
	public List<Nation> list() throws AppException {

		return BaseCacheableDAOSeviceHelper.listFromKeySet(
				nationLocationService.getCachedValues(this.session).keySet());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, Nation> map() throws AppException {

		return new TreeMap<Long, Nation>(
				// Obtain non-null results list by querying
				((List<Nation>) super.throwIfMissing(
						this.list(), mapMissingResultList)).stream()
				// Restrict results to those with non-null name
				.filter(e -> e.getNationName() != null)
				// Sort by name
				.sorted((e1, e2) -> e1.getNationName().compareTo(e2.getNationName()))
				// Map by ID
				.collect(Collectors.toMap(
						Nation::getNationId, // Key
						nation -> nation))); // Value
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		// Must initialize class variable before calling this.map()
		this.session = (HttpSession) super.throwIfMissing(
				session, 
				initCacheMissingSession);
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
	public Map<Long, Nation> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_NAME,
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	@Deprecated
	@Override
	public void initModel(HttpSession session, Model model, User user) throws AppException {
		// Intentionally, do nothing.
	}

	@Override
	public Long getSearchOrRandomId(
			Long id,
			Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap,
			Long riteId) {

		// When random ID is specified, filter the mapping of all Commemoration DTOs by Nation ID down
		// to a list of available Nation IDs for the specified Rite ID and then pick a random entry.
		if (id == ID_SEARCH_RANDOM_INDICATOR) {
			final List<Long> nationIdsForRiteId = new ArrayList<>();
			for (Entry<Long, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final Long nationId = entry.getKey();
				final List<Long> commemorationRiteIds = entry.getValue().entrySet()
						.iterator().next().getValue().stream()
						.map(Rite::getRiteId)
						.collect(Collectors.toList());
				if (riteId == riteAllId || commemorationRiteIds.contains(riteId)) {
					nationIdsForRiteId.add(nationId);
				}
			}
			final Long[] eligibleNationIds = nationIdsForRiteId.toArray(Long[]::new);
			return eligibleNationIds[new Random().nextInt(eligibleNationIds.length)];
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
			Map<Long,Nation> entityMap,
			Long calendarId) throws AppException {

		String searchTerm = null;
		if (id != null && entityMap != null) {
			Nation nation = entityMap.get(id);
			if (nation != null) {
				model.addAttribute(ModelAttributeEnum.SEARCH_ICON.getValue(), nation.getNationCode());
				if (nation.getAltNationCode() != null) {
					model.addAttribute(ModelAttributeEnum.ALT_SEARCH_ICON.getValue(), nation.getAltNationCode());
				}
				searchTerm = nation.getNationName();

				// Add Related Search Link, where applicable
				final Long relatedPatronageId = nation.getRelatedPatronageId();
				if (relatedPatronageId != null) {
					model.addAttribute(ModelAttributeEnum.RELATED_SEARCH_LINK.getValue(), 
							RELATED_PATRONAGE_SEARCH_PATH + relatedPatronageId);
				}
			}
		}
		ModelAttributeHelperService.addSearchTypeAndTerm(model, "nation of", searchTerm);
	}

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${nationservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${nationservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${nationservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${nationservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;
}
