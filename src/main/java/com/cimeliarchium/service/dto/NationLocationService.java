package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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

import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.Nation;
import com.cimeliarchium.model.dao.Route;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.NationLocationDto;
import com.cimeliarchium.service.BaseStaticMultiMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

/**
 * For performance reasons, this service is used to perform one-time static initialization of 
 * Nation Location mappings on application startup. Note that any interactions with the database 
 * done after startup will need to be performed as discreet updates to these class-level mappings 
 * in order for them to get reflected in an already-running instance of the application.
 */
@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class NationLocationService extends SessionAttributeHelperService
		implements BaseSearchableDTOServiceIF<Nation,Location>,
				BaseStaticMultiMapServiceIF<Nation,Location> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NationLocationService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.NATION_LOCATION_MAP.getValue();
	private static final String SESSION_ATTRIBUTE_NAME_BY_LOCATION_ID = SessionAttributeEnum.ROUTE_MAP_BY_LOCATION_ID.getValue();
	private static final String SESSION_ATTRIBUTE_NAME_BY_ID = SessionAttributeEnum.ROUTE_MAP_BY_ID.getValue();
	private static final String STORED_PROCEDURE_NAME = "NationLocationMappingQuery";

	private List<NationLocationDto> nationLocationQueryResults;
	private Map<Nation, List<Location>> locationsByNationMap;
	private Map<Long, List<Route>> routesByLocationIdMap;
	private Map<Long, Route> routesByIdMap;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	public NationLocationService() { }

	@Override
	@SuppressWarnings("unchecked")
	public void query() throws AppException {

		nationLocationQueryResults = (List<NationLocationDto>) super.throwIfMissing(
				entityManager.createNamedStoredProcedureQuery(STORED_PROCEDURE_NAME).getResultList(), 
				mapMissingResultList);
		LOGGER.info("{} returned {} results", STORED_PROCEDURE_NAME, nationLocationQueryResults.size());
	}

	@Override
	public void map() throws AppException {

		this.mapLocationsByNation();
		this.mapRoutesByLocationId();
		this.mapRoutesById();
	}

	/**
	 * Custom method to map Locations by Nation from cached query results.
	 * 
	 * @param resultList the list of NationLocationDto entries returned from query
	 * @return locationsByNation
	 * @throws AppException
	 */
	private void mapLocationsByNation() throws AppException {

		locationsByNationMap = new TreeMap<Nation, List<Location>>(
				// Obtain non-null results list by querying
				nationLocationQueryResults.stream()
				// Restrict results to those with non-null ID
				.filter(e -> e.getNation() != null)
				// Sort by ID
				.sorted((e1, e2) -> e1.getNation().compareTo(e2.getNation()))
				// Partition into sub-lists by Nation
				.collect(Collectors.groupingBy(grp -> grp.getNation(), 
						Collectors.mapping(
								map -> map.getLocation(), // Key
								Collectors.toList())))); // Value
	}

	/**
	 * Custom method to map Locations by Route from cached query results.
	 * 
	 * @param resultList the list of NationLocationDto entries returned from query
	 * @return routesByLocation
	 * @throws AppException
	 */
	private void mapRoutesByLocationId() throws AppException {

		routesByLocationIdMap = new TreeMap<>();
		for (NationLocationDto dto : nationLocationQueryResults) {
			final Long locationId = dto.getLocation().getLocationId();
			if (locationId != null) {
				maybeAddRouteForLocation(routesByLocationIdMap, locationId, dto.getRouteA());
				maybeAddRouteForLocation(routesByLocationIdMap, locationId, dto.getRouteB());
				maybeAddRouteForLocation(routesByLocationIdMap, locationId, dto.getRouteC());
				maybeAddRouteForLocation(routesByLocationIdMap, locationId, dto.getRouteD());
				maybeAddRouteForLocation(routesByLocationIdMap, locationId, dto.getRouteE());
			}
		}
	}

	/**
	 * Method used to conditionally add Route object to a mapped Location.
	 * Likewise, list each associated Location ID for the given Route.
	 * 
	 * @param resultMap the Map of Routes by Location ID
	 * @param locationId the Location ID
	 * @param route the Route
	 * @return resultMap
	 */
	private void maybeAddRouteForLocation(
			Map<Long, List<Route>> resultMap, 
			Long locationId, 
			Route route) {

		if (route.getRouteId() != null) {
			resultMap.computeIfAbsent(locationId, n -> {
				return new LinkedList<>();
			}).add(route);
		}
	}

	/**
	 * Custom method to map Route by ID from cached query results.
	 * 
	 * @param resultList the list of NationLocationDto entries returned from query
	 * @throws AppException
	 */
	private void mapRoutesById() throws AppException {

		routesByIdMap = new TreeMap<>();
		for (NationLocationDto dto : nationLocationQueryResults) {
			maybeAddRouteForId(routesByIdMap, dto.getRouteA());
			maybeAddRouteForId(routesByIdMap, dto.getRouteB());
			maybeAddRouteForId(routesByIdMap, dto.getRouteC());
			maybeAddRouteForId(routesByIdMap, dto.getRouteD());
			maybeAddRouteForId(routesByIdMap, dto.getRouteE());
		}
	}

	/**
	 * Method used to conditionally add Route object to a mapped Location.
	 * Likewise, list each associated Location ID for the given Route.
	 * 
	 * @param resultMap the Map of Routes by Location ID
	 * @param locationId the Location ID
	 * @param route the Route
	 * @return resultMap
	 */
	private void maybeAddRouteForId(
			Map<Long, Route> resultMap, 
			Route route) {

		if (route != null 
				&& route.getRouteId() != null) {
			resultMap.put(route.getRouteId(), route);
		}
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
					locationsByNationMap, 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME_BY_LOCATION_ID)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					routesByLocationIdMap, 
					session, 
					SESSION_ATTRIBUTE_NAME_BY_LOCATION_ID);
		}
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME_BY_ID)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					routesByIdMap, 
					session, 
					SESSION_ATTRIBUTE_NAME_BY_ID);
		}
		return hasUpdate;
	}

	@Override
	public Map<Nation, List<Location>> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	@Override
	public List<Nation> listKeys(
			@NotNull Map<Nation, List<Location>> entityMap) throws AppException {

		return entityMap.keySet().stream()
				.collect(Collectors.toList());
	}

	public List<Nation> listAltKeys(
			@NotNull Map<Nation, List<Location>> entityMap) throws AppException {

		final Set<Nation> rs = new HashSet<>();
		for (Map.Entry<Nation, List<Location>> e : entityMap.entrySet()) {
			for (Location loc : e.getValue()) {
				if (loc.getLocationId() != null 
						&& (loc.getInsetId() != null || loc.getLocationId().equals(locationMazarSharifId))) {
					rs.add(e.getKey());
				}
			}
		}
		final List<Nation> sortedList = new ArrayList<>(rs);
		Collections.sort(sortedList);
		return sortedList;
	}


	public List<Location> listValues(
			@NotNull Map<Nation, List<Location>> entityMap) throws AppException {

		return entityMap.values().stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}

	/**
	 * Method used to find only nations having one or more associated locations.
	 * 
	 * @param entityMap mapped lists of locations by nation
	 * @return nationsWithLocations
	 * @throws AppException
	 */
	public List<Nation> listNationsWithLocations(
			@NotNull Map<Nation, List<Location>> entityMap) throws AppException {

		final List<Nation> nationsWithLocations = new ArrayList<>();
		for (Map.Entry<Nation, List<Location>> entry : entityMap.entrySet()) {
			final Location firstLocation = entry.getValue().get(0);
			if (firstLocation.getLocationId() != null) {
				nationsWithLocations.add(entry.getKey());
			}
		}
		return nationsWithLocations;
	}

	@SuppressWarnings("unchecked")
	public Location lookupByLocationId(
			@NotNull HttpSession session,
			Long entityId) throws AppException {

		super.throwIfMissing(session, lookupByLocationIdMissingSession);
		final Map<Nation, List<Location>> entityMap = this.getCachedValues(session);
		// Obtain mapped value by entity key, after first matching on ID
		final Location rs = ((List<Location>) super.throwIfMissing(
				this.listValues(entityMap), lookupByLocationIdMissingEntityList)).stream()
				// Restrict results to non-Inset locations with matching Nation ID
				.filter(e -> e.getLocationId() != null 
						&& entityId.equals(e.getLocationId()))
				.findFirst()
				.orElse(null);
		return rs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Location> lookupById(
			@NotNull HttpSession session,
			Long entityId) throws AppException {

		super.throwIfMissing(session, lookupByIdMissingSession);
		final Map<Nation, List<Location>> entityMap = this.getCachedValues(session);
		// Nation ID can be null on page load, before selection is made.
		if (entityId != null) {
			// Obtain mapped value by entity key, after first matching on ID
			final List<Location> rs = ((List<Location>) super.throwIfMissing(
					this.listValues(entityMap), lookupByIdMissingEntityList)).stream()
					// Restrict results to non-Inset locations with matching Nation ID
					.filter(e -> e.getLocationId() != null 
							&& entityId.equals(e.getNationId()) 
							&& e.getInsetId() == null)
					.collect(Collectors.toList());
			Collections.sort(rs);
			return rs;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Location> lookupInsetLocationsById(
			@NotNull HttpSession session,
			Long entityId,
			Boolean showSingleResultOnSpecialAltLocationDropdown) throws AppException {

		super.throwIfMissing(session, lookupInsetLocationsByIdMissingSession);
		final Map<Nation, List<Location>> entityMap = this.getCachedValues(session);
		// Nation ID can be null on page load, before selection is made.
		if (entityId != null) {
			// Obtain mapped value by entity key, after first matching on ID
			final List<Location> rs = ((List<Location>) super.throwIfMissing(this.listValues(entityMap), lookupInsetLocationsByIdMissingEntityList));
			final List<Location> sortedList = new ArrayList<>();
			// In the specific case of Afghanistan Alt Location drop-down, we display only Mazar-i-Sharif
			if (showSingleResultOnSpecialAltLocationDropdown && entityId.equals(nationAfghanistanId)) {
				sortedList.addAll(rs.stream()
						.filter(loc -> loc.getLocationId() != null && loc.getLocationId().equals(locationMazarSharifId))
						.collect(Collectors.toList()));
			} else {
				for (Location loc : rs) {
					if (loc.getLocationId() != null && entityId.equals(loc.getNationId())
								// Restrict results to Inset locations with matching Nation ID
								&& ((loc.getInsetId() != null) 
										// Mazar-i-Sharif is a special Alt Location having no parent Inset in Afghanistan, 
										// so in that specific instance just display Afghanistan Locations on the drop-down.
										|| (entityId.equals(nationAfghanistanId)))) {
						sortedList.add(loc);
					}
				}
			}
			Collections.sort(sortedList);
			return sortedList;
		} else {
			return null;
		}
	}

	@Override
	@Deprecated
	public void initModel(
			HttpSession session, 
			Model model, 
			User user) throws AppException {
		// Intentionally, do nothing.
	}

	@Value("${nationlocationservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${nationlocationservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${nationlocationservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${nationlocationservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${nationlocationservice.lookupbylocationid.session}")
	private String lookupByLocationIdMissingSession;
	
	@Value("${nationlocationservice.lookupbylocationid.entitylist}")
	private String lookupByLocationIdMissingEntityList;
	
	@Value("${nationlocationservice.lookupbyid.session}")
	private String lookupByIdMissingSession;

	@Value("${nationlocationservice.lookupbyid.entitylist}")
	private String lookupByIdMissingEntityList;

	@Value("${nationlocationservice.lookupinsetlocationsbyid.session}")
	private String lookupInsetLocationsByIdMissingSession;

	@Value("${nationlocationservice.lookupbyinsetlocationsbyid.entitylist}")
	private String lookupInsetLocationsByIdMissingEntityList;

	@Value("${location.mazarsharif.id}")
	private Long locationMazarSharifId;

	@Value("${nation.afghanistan.id}")
	private Long nationAfghanistanId;
}
