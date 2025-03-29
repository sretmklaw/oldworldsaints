package com.cimeliarchium.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.ImmutablePair;
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
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.repository.dao.PatronageRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.dto.PatronageSubtypeService;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/application.properties",
	"classpath:/errorcode.properties"
})
public class PatronageService extends SessionAttributeHelperService
		implements BaseCacheableDAOServiceIF <Long,Patronage>,
				BaseOneToOneMapServiceIF<Long,Patronage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatronageService.class);
	private static final String SESSION_ATTRIBUTE_BY_ID_NAME = SessionAttributeEnum.PATRONAGE_MAP_BY_ID.getValue();

	private HttpSession session;
	private PatronageRepository repo;
	private PatronageSubtypeService patronageSubtypeService;

	@Autowired
	public PatronageService(PatronageRepository repo, PatronageSubtypeService patronageSubtypeService) {
		this.repo = repo;
		this.patronageSubtypeService = patronageSubtypeService;
	}

	public Boolean hasMatchForId(Long id) {

		return this.repo.findByPatronageId(id) != null;
	}

	@Override
	public List<Patronage> list() throws AppException {

		return BaseCacheableDAOSeviceHelper.listFromValues(
				patronageSubtypeService.getCachedValues(this.session).values());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, Patronage> map() throws AppException {

		return ((List<Patronage>) super.throwIfMissing(
				this.list(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								Patronage::getPatronageId, // Key
								val -> val)); // Value
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		// Must initialize class variable before calling this.map()
		this.session = (HttpSession) super.throwIfMissing(
				session, 
				initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_BY_ID_NAME)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					this.map(), 
					session, 
					SESSION_ATTRIBUTE_BY_ID_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Long, Patronage> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_BY_ID_NAME, 
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

		// When random ID is specified, filter the mapping of all Commemoration DTOs by Patronage ID down
		// to a list of available Patronage IDs for the specified Rite ID and then pick a random entry.
		if (id == ID_SEARCH_RANDOM_INDICATOR) {
			final List<Long> patronageIdsForRiteId = new ArrayList<>();
			for (Entry<Long, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final Long patronageId = entry.getKey();
				final List<Long> commemorationRiteIds = entry.getValue().entrySet()
						.iterator().next().getValue().stream()
						.map(Rite::getRiteId)
						.collect(Collectors.toList());
				if (riteId == riteAllId || commemorationRiteIds.contains(riteId)) {
					patronageIdsForRiteId.add(patronageId);
				}
			}
			final Long[] eligiblePatronageIds = patronageIdsForRiteId.toArray(Long[]::new);
			return eligiblePatronageIds[new Random().nextInt(eligiblePatronageIds.length)];
		} 
		// Otherwise, simply return the passed-in ID with no change.
		else {
			return id;
		}
	}

	public void populateSearchHeader(
			Model model, 
			@NotNull Long id,
			@NotNull Map<Long,Patronage> entityMap) throws AppException {

		String searchTerm = null;
		if (id != null && entityMap != null) {
			Patronage patronage = entityMap.get(id);
			if (patronage != null) {
				// Add current Patronage icon data
				model.addAttribute(ModelAttributeEnum.THIS_PATRONAGE.getValue(), 
						patronage);
				searchTerm = patronage.getPatronageSubtypeName() 
						+ " " + patronage.getPatronageName();
				// Add Patronage image and coordinates, where available (location type only)
				final var patronageImageId = patronage.getPatronageId();
				final var patronagePointX = patronage.getPointX();
				final var patronagePointY = patronage.getPointY();
				if (patronageImageId != null && patronagePointX != null && patronagePointY != null) {
					model.addAttribute(ModelAttributeEnum.PATRONAGE_IMAGE_ID.getValue(), patronageImageId);
					model.addAttribute(ModelAttributeEnum.PATRONAGE_COORDINATES.getValue(), new ImmutablePair<>(patronagePointX, patronagePointY));
				}
				// Add related search link, where available
				final String relatedSearchLink = patronage.getRelatedSearchLink();
				if (relatedSearchLink != null && !relatedSearchLink.isEmpty()) {
					model.addAttribute(ModelAttributeEnum.RELATED_SEARCH_LINK.getValue(), relatedSearchLink);
				}
			}
		}
		ModelAttributeHelperService.addSearchTypeAndTerm(model, "patronage of", searchTerm);
	}

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${patronageservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${patronageservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${patronageservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${patronageservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${patronageservice.buildcommemorationdtomapping.session}")
	private String buildCommemorationDtoMappingMissingSession;

	@Value("${patronageservice.buildcommemorationdtomapping.notfound}")
	private String buildCommemorationDtoMappingNotFound;
}
