package com.cimeliarchium.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
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
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.repository.dao.LocationRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.dto.NationLocationService;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/application.properties",
	"classpath:/errorcode.properties"
})
public class LocationService extends SessionAttributeHelperService
		implements BaseCacheableDAOServiceIF<Long,Location>,
				BaseOneToOneMapServiceIF<Long,Location> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

	private static final String SESSION_ATTRIBUTE_BY_ID_NAME = SessionAttributeEnum.LOCATION_MAP_BY_ID.getValue();

	private HttpSession session;

	private LocationRepository repo;
	private NationLocationService nationLocationService;

	@Autowired
	public LocationService(LocationRepository repo, 
			NationLocationService nationLocationService) {
		this.repo = repo;
		this.nationLocationService = nationLocationService;

	}

	public Boolean hasMatchForId(Long id) {
		return this.repo.findByLocationId(id) != null;
	}

	@Override
	public List<Location> list() throws AppException {
		return BaseCacheableDAOSeviceHelper.listFromValues(
				nationLocationService.getCachedValues(this.session).values());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, Location> map() throws AppException {

		return new TreeMap<Long, Location>(
				// Obtain non-null results list by querying
				((List<Location>) super.throwIfMissing(
						this.list(), mapMissingResultList)).stream()
				// Restrict results to those with non-null name
				.filter(e -> e.getLabelName() != null)
				// Sort by name
				.sorted((e1, e2) -> e1.getLabelName().compareTo(e2.getLabelName()))
				// Map by ID
				.collect(Collectors.toMap(
						Location::getLocationId, // Key
						loc -> loc))); // Value
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
	public Map<Long, Location> getCachedValues(
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

		// When random ID is specified, filter the mapping of all Commemoration DTOs by Location ID down
		// to a list of available Location IDs for the specified Rite ID and then pick a random entry.
		if (id == ID_SEARCH_RANDOM_INDICATOR) {
			final List<Long> locationIdsWithRiteId = new ArrayList<>();
			for (Entry<Long, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final Long locationId = entry.getKey();
				final List<Long> commemorationRiteIds = entry.getValue().entrySet()
						.iterator().next().getValue().stream()
						.map(Rite::getRiteId)
						.collect(Collectors.toList());
				if (riteId == riteAllId || commemorationRiteIds.contains(riteId)) {
					locationIdsWithRiteId.add(locationId);
				}
			}
			final Long[] eligibleLocationIds = locationIdsWithRiteId.toArray(Long[]::new);
			return eligibleLocationIds[new Random().nextInt(eligibleLocationIds.length)];
		} 
		// Otherwise, simply return the passed-in ID with no change.
		else {
			return id;
		}
	}

	public Location populateSearchHeader(
			Model model, 
			@NotNull Long id,
			@NotNull Map<Long,Location> entityMap) throws AppException {

		String searchTerm = null;
		Location location = null;
		if (id != null && entityMap != null) {
			location = entityMap.get(id);
			if (location != null) {
				// Add previous Location data
				model.addAttribute(ModelAttributeEnum.PREV_LOCATION.getValue(), 
						location.getPrevLocation());
				// Add current Location icon data
				model.addAttribute(ModelAttributeEnum.THIS_LOCATION.getValue(), 
						location);
				// Add next Location data
				model.addAttribute(ModelAttributeEnum.NEXT_LOCATION.getValue(), 
						location.getNextLocation());
				// Add Location image ID
				final var locationImageId = location.getLocationId();
				if (locationImageId != null) {
					model.addAttribute(ModelAttributeEnum.LOCATION_IMAGE_ID.getValue(), locationImageId);
				}
				// Add Related Search Link, where applicable
				final Long relatedPatronageId = location.getRelatedPatronageId();
				if (relatedPatronageId != null) {
					model.addAttribute(ModelAttributeEnum.RELATED_SEARCH_LINK.getValue(), 
							RELATED_PATRONAGE_SEARCH_PATH + relatedPatronageId);
				}
				searchTerm = location.getLabelName();
			}
		}
		ModelAttributeHelperService.addSearchTypeAndTerm(model, "location of", searchTerm);
		return location;
	}

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${locationservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${locationservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${locationservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${locationservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;
}
