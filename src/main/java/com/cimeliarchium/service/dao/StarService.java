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
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.Star;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.dto.ConstellationStarService;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/application.properties",
	"classpath:/errorcode.properties"
})
public class StarService extends SessionAttributeHelperService
		implements BaseCacheableDAOServiceIF<Long,Star>, 
				BaseOneToOneMapServiceIF<Long,Star> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StarService.class);

	private static final String SESSION_ATTRIBUTE_BY_ID_NAME = SessionAttributeEnum.STAR_MAP_BY_ID.getValue();

	private ConstellationStarService constellationStarService;
	private HttpSession session;

	@Autowired
	public StarService(ConstellationStarService constellationStarService) {
		this.constellationStarService = constellationStarService;
	}

	@Override
	public void initModel(
			HttpSession session, 
			Model model,
			User user) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		model.addAttribute(ModelAttributeEnum.STAR_LIST.getValue(), getCachedValues(session).values());
	}

	public void initModel(
			@NotNull HttpSession session, 
			Model model,
			Star star) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		model.addAttribute(ModelAttributeEnum.CULMINATING_STAR.getValue(), star);
	}

	@Override
	public Long getSearchOrRandomId(
			Long id,
			Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap,
			Long riteId) {

		// When random ID is specified, filter the mapping of all Commemoration DTOs by Star ID down
		// to a list of available Star IDs for the specified Rite ID and then pick a random entry.
		if (id == ID_SEARCH_RANDOM_INDICATOR) {
			final List<Long> starIdsForRiteId = new ArrayList<>();
			for (Entry<Long, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final Long starId = entry.getKey();
				final List<Long> commemorationRiteIds = entry.getValue().entrySet()
						.iterator().next().getValue().stream()
						.map(Rite::getRiteId)
						.collect(Collectors.toList());
				if (riteId == riteAllId || commemorationRiteIds.contains(riteId)) {
					starIdsForRiteId.add(starId);
				}
			}
			final Long[] eligibleStarIds = starIdsForRiteId.toArray(Long[]::new);
			return eligibleStarIds[new Random().nextInt(eligibleStarIds.length)];
		} 
		// Otherwise, simply return the passed-in ID with no change.
		else {
			return id;
		}
	}

	public void populateSearchHeader(Model model, 
			@NotNull Long id,
			@NotNull Map<Long,Star> entityMap) throws AppException {

		String searchTerm = null;
		if (id != null && entityMap != null) {
			Star star = entityMap.get(id);
			if (star != null) {
				// Add current Star data
				model.addAttribute(ModelAttributeEnum.THIS_STAR.getValue(), star);
				searchTerm = star.getStarNameFormatted();
				// Add Star coordinates
				final var starPointX = star.getPointX();
				final var starPointY = star.getPointY();
				if (starPointX != null && starPointY != null) {
					model.addAttribute(ModelAttributeEnum.STAR_COORDINATES.getValue(), new ImmutablePair<>(starPointX, starPointY));
				}
			}
		}
		ModelAttributeHelperService.addSearchTypeAndTerm(model, "", searchTerm);
	}

	@Override
	public List<Star> list() throws AppException {

		return BaseCacheableDAOSeviceHelper.listFromValues(
				constellationStarService.getCachedValues(this.session).values());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, Star> map() throws AppException {
	
		return ((List<Star>) super.throwIfMissing(
				this.list(), mapMissingResultList)).stream()
						.filter(e -> e.getStarId() != null)
						.collect(Collectors.toMap(
								Star::getStarId, // Key
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
	public Map<Long, Star> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_BY_ID_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${starservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${starservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${starservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${starservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${starservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;
}
