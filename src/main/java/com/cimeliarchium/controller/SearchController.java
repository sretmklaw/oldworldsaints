package com.cimeliarchium.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Century;
import com.cimeliarchium.model.dao.Constellation;
import com.cimeliarchium.model.dao.Day;
import com.cimeliarchium.model.dao.Inset;
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.Nation;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.Route;
import com.cimeliarchium.model.dao.SearchCriteria;
import com.cimeliarchium.model.dao.Star;
import com.cimeliarchium.model.dao.Tag;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.service.dao.CenturyService;
import com.cimeliarchium.service.dao.DayService;
import com.cimeliarchium.service.dao.LocationService;
import com.cimeliarchium.service.dao.NationService;
import com.cimeliarchium.service.dao.PatronageService;
import com.cimeliarchium.service.dao.RouteService;
import com.cimeliarchium.service.dao.SearchCriteriaValidationService;
import com.cimeliarchium.service.dao.StarService;
import com.cimeliarchium.service.dao.TagService;
import com.cimeliarchium.service.dto.ConstellationStarService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.web.InsetMapBackgroundService;
import com.cimeliarchium.service.web.PdfService;
import com.cimeliarchium.service.web.SessionManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

@Controller
public class SearchController {

	private ModelAttributeHelperService modelAttributeHelperService;
	private UserSessionInfoService userSessionInfoService;
	private NationService nationService;
	private LocationService locationService;
	private RouteService routeService;
	private CenturyService centuryService;
	private ConstellationStarService constellationStarService;
	private DayService dayService;
	private TagService tagService;
	private StarService starService;
	private PatronageService patronageService;
	private PdfService pdfService;
	private InsetMapBackgroundService insetMapBackgroundService;
	private SearchCriteriaValidationService searchCriteriaValidationService;
	private SessionManagementService sessionManagementService;
	private ServletContext context;
	private ObjectMapper jsonMapper;

	@Autowired
	public SearchController(ModelAttributeHelperService modelAttributeHelperService, UserSessionInfoService userSessionInfoService, 
			NationService nationService, LocationService locationService, RouteService routeService, CenturyService centuryService, 
			ConstellationStarService constellationStarService, DayService dayService, TagService tagService,
			StarService starService, PatronageService patronageService, PdfService pdfService, InsetMapBackgroundService mapInsetService,
			SearchCriteriaValidationService searchCriteriaValidationService, SessionManagementService sessionManagementService, 
			ServletContext context, ObjectMapper jsonMapper) {
		this.modelAttributeHelperService = modelAttributeHelperService;
		this.userSessionInfoService = userSessionInfoService;
		this.nationService = nationService;
		this.locationService = locationService;
		this.routeService = routeService;
		this.centuryService = centuryService;
		this.constellationStarService = constellationStarService;
		this.dayService = dayService;
		this.tagService = tagService;
		this.starService = starService;
		this.patronageService = patronageService;
		this.pdfService = pdfService;
		this.insetMapBackgroundService = mapInsetService;
		this.searchCriteriaValidationService = searchCriteriaValidationService;
		this.sessionManagementService = sessionManagementService;
		this.context = context;
		this.jsonMapper = jsonMapper;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/commemoration", method = RequestMethod.GET)
	public String findCommemorationById(
			@RequestParam(value = "byId", required = true) Long id, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session,
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<Long, Map<DayCommemorationDto, List<Rite>>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_ID.getValue());
		final Map<Long, List<Long>> idsByRiteMap = (Map<Long, List<Long>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_IDS_BY_RITE.getValue());

		// Initialize values required to display search results - includes commemoration specific random ID logic.
		model.addAttribute("searchTerm", ModelAttributeEnum.COMMEMORATION_ID.getValue());
		modelAttributeHelperService.populateSearchResultsById(
				"commemoration?byId=",
				model, 
				id, 
				showAll, 
				page,
				user, 
				idsByRiteMap, 
				dtoMap);

		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/name", method = RequestMethod.GET)
	public String findCommemorationsByName(
			@ModelAttribute(value = "searchCriteria") SearchCriteria searchCriteria,
			BindingResult result, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session,
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Map<String, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<String, Map<DayCommemorationDto, List<Rite>>>) session.getAttribute(
				SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_NAME.getValue());
		final Map<Long, List<String>> namesByRiteMap = (Map<Long, List<String>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_NAMES_BY_RITE.getValue());

		// On Validate error, redirect to Main View
		String searchTerm = null;
		searchCriteriaValidationService.validateByText(searchCriteria, result);
		if (result.hasErrors()) {
			ModelAttributeHelperService.addSearchTypeAndTerm(model, null, null);
			model.addAttribute(ModelAttributeEnum.PAGE_ERROR_MESSAGE.getValue(), 
					DisplayKeyEnum.INVALID_SEARCH_TERM.getValue());
		} else {
			searchTerm = searchCriteria.getByText();
			ModelAttributeHelperService.addSearchTypeAndTerm(model, "name", searchTerm);
		}

		// Initialize values required to display search results - includes name-specific random ID logic.
		modelAttributeHelperService.populateSearchResultsByName(
				"name?byText=",
				model, 
				searchTerm, 
				showAll, 
				page,
				user, 
				namesByRiteMap,
				dtoMap);

		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/nation", method = RequestMethod.GET)
	public String findCommemorationsByNation(
			@RequestParam(value = "byId", required = true) Long id, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session,
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Long displayRiteId = modelAttributeHelperService.getDisplayRiteId(showAll, user);
		final Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<Long, Map<DayCommemorationDto, List<Rite>>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_NATION.getValue());

		// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
		final Long searchById = nationService.getSearchOrRandomId(id, dtoMap, displayRiteId);

		// Initialize values required for search header.
		nationService.populateSearchHeader(
				model, 
				searchById, 
				(Map<Long,Nation>) session.getAttribute(
						SessionAttributeEnum.NATION_MAP_BY_ID.getValue()),
				null);

		// Initialize values required to display search results.
		modelAttributeHelperService.populateSearchResults(
				"nation?byId=",
				model, 
				searchById, 
				showAll, 
				page,
				displayRiteId, 
				dtoMap);

		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/location", method = RequestMethod.GET)
	public String findCommemorationsByLocation(
			@RequestParam(value = "byId", required = true) Long id, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session, 
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Long displayRiteId = modelAttributeHelperService.getDisplayRiteId(showAll, user);
		final Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<Long, Map<DayCommemorationDto, List<Rite>>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_LOCATION.getValue());

		// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
		final Long searchById = locationService.getSearchOrRandomId(id, dtoMap, displayRiteId);

		// Initialize values required for search header: Location and Route listing.
		final Location location = locationService.populateSearchHeader(
				model, 
				searchById, 
				(Map<Long,Location>) session.getAttribute(
						SessionAttributeEnum.LOCATION_MAP_BY_ID.getValue()));
		routeService.populateSearchHeader(
				model, 
				id, 
				(Map<Long,List<Route>>) session.getAttribute(
						SessionAttributeEnum.ROUTE_MAP_BY_LOCATION_ID.getValue()));

		// Initialize values required to display search results.
		modelAttributeHelperService.populateSearchResults(
				"location?byId=",
				model, 
				searchById, 
				showAll, 
				page,
				displayRiteId, 
				dtoMap);

		// Lastly, obtain and pass the targeted map Inset from cached value, where applicable
		if (location != null && location.getInsetId() != null) {
			final Map<Long,Inset> insetMapByName = (Map<Long,Inset>) session.getAttribute(
					SessionAttributeEnum.INSET_MAP_BY_ID.getValue());
			final Inset cacheInset = insetMapByName.get(location.getInsetId());
			model.addAttribute(ModelAttributeEnum.MAP_INSET.getValue(), cacheInset);
		}
		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/century", method = RequestMethod.GET)
	public String findCommemorationsByCentury(
			@RequestParam(value = "byId", required = true) Long id, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session, 
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Long displayRiteId = modelAttributeHelperService.getDisplayRiteId(showAll, user);
		final Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<Long, Map<DayCommemorationDto, List<Rite>>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_CENTURY.getValue());

		// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
		final Long searchById = centuryService.getSearchOrRandomId(id, dtoMap, displayRiteId);

		// Initialize values required for search header.
		model.addAttribute(ModelAttributeEnum.CENTURY.getValue(), new Century.Builder().build());
		centuryService.populateSearchHeader(
				model, 
				searchById, 
				(Map<Long,Century>) session.getAttribute(
						SessionAttributeEnum.CENTURY_MAP_BY_ID.getValue()),
				(showAll != null && showAll) 
					? null 
					: userSessionInfoService.getCachedValue(session).getCalendar().getCalendarId());

		// Initialize values required to display search results.
		modelAttributeHelperService.populateSearchResults(
				"century?byId=",
				model, 
				searchById, 
				showAll, 
				page,
				displayRiteId, 
				dtoMap);

		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/day", method = RequestMethod.GET)
	public String findCommemorationsByDay(
			@RequestParam(value = "byId", required = true) Long id, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session, 
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Long displayRiteId = modelAttributeHelperService.getDisplayRiteId(showAll, user);
		final Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<Long, Map<DayCommemorationDto, List<Rite>>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_DAY.getValue());

		// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
		final Long searchById = dayService.getSearchOrRandomId(id, dtoMap, displayRiteId);

		// Initialize values required for search header.
		dayService.populateSearchHeaderForDay(
				model,
				searchById,
				(Map<Long,Day>) session.getAttribute(
						SessionAttributeEnum.DAY_MAP_BY_ID.getValue()),
				session);

		// Initialize values required to display search results.
		modelAttributeHelperService.populateSearchResults(
				"day?byId=",
				model, 
				searchById, 
				showAll, 
				page,
				displayRiteId, 
				dtoMap);

		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/tag", method = RequestMethod.GET)
	public String findCommemorationsByTag(
			@RequestParam(value = "byId", required = true) Long id, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session, 
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Long displayRiteId = modelAttributeHelperService.getDisplayRiteId(showAll, user);

		// Determine whether to select a random result or use the passed-in ID.
		final Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<Long, Map<DayCommemorationDto, List<Rite>>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_TAG.getValue());

		// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
		final Long searchById = tagService.getSearchOrRandomId(id, dtoMap, displayRiteId);

		// Initialize values required for search header.
		tagService.populateSearchHeader(
				model,
				searchById,
				(Map<Long,Tag>) session.getAttribute(
						SessionAttributeEnum.TAG_MAP_BY_ID.getValue()),
				null);

		// Initialize values required to display search results.
		modelAttributeHelperService.populateSearchResults(
				"tag?byId=",
				model, 
				searchById, 
				showAll, 
				page,
				displayRiteId, 
				dtoMap);

		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/patronage", method = RequestMethod.GET)
	public String findCommemorationsByPatronage(
			@RequestParam(value = "byId", required = true) Long id, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session, 
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Long displayRiteId = modelAttributeHelperService.getDisplayRiteId(showAll, user);

		// Determine whether to select a random result or use the passed-in ID.
		final Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<Long, Map<DayCommemorationDto, List<Rite>>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_PATRONAGE.getValue());

		// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
		final Long searchById = patronageService.getSearchOrRandomId(id, dtoMap, displayRiteId);

		// Initialize values required for search header.
		patronageService.populateSearchHeader(
				model,
				searchById,
				(Map<Long,Patronage>) session.getAttribute(
						SessionAttributeEnum.PATRONAGE_MAP_BY_ID.getValue()));

		// Initialize values required to display search results.
		modelAttributeHelperService.populateSearchResults(
				"patronage?byId=",
				model, 
				searchById, 
				showAll, 
				page,
				displayRiteId, 
				dtoMap);

		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@RequestMapping(value = "/map-background", 
			method = RequestMethod.GET, 
			produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody void getInsetMapBackground(
			@RequestParam(value = "withInset", required = false) String insetName,
			@NotNull HttpServletResponse response) throws AppException, IOException {

		insetMapBackgroundService.buildInsetMapBackground(insetName, response, context);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/star", method = RequestMethod.GET)
	public String findCommemorationsByStar(
			@RequestParam(value = "byId", required = true) Long id, 
			@RequestParam(value = "showAll", required = false) Boolean showAll,
			@RequestParam(value = "page", required = false) Long page,
			HttpSession session,
			HttpServletRequest request,
			Model model) throws AppException {

		// Initialize values required for navigation bar.
		final User user = modelAttributeHelperService.populateNavbarMenuOptions(model, session, request);
		final Long displayRiteId = modelAttributeHelperService.getDisplayRiteId(showAll, user);

		// Determine whether to select a random result or use the passed-in ID.
		final Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap = (Map<Long, Map<DayCommemorationDto, List<Rite>>>) session
				.getAttribute(SessionAttributeEnum.DAY_COMMEMORATION_MAP_BY_STAR.getValue());

		// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
		final Long searchById = starService.getSearchOrRandomId(id, dtoMap, displayRiteId);

		// Initialize values required for search header.
		starService.populateSearchHeader(
				model, 
				searchById, 
				(Map<Long,Star>) session.getAttribute(
						SessionAttributeEnum.STAR_MAP_BY_ID.getValue()));
		constellationStarService.populateSearchHeader(
				session,
				model, 
				searchById, 
				(Map<Long,List<Constellation>>) session.getAttribute(
						SessionAttributeEnum.ASTERISM_MAP_BY_STAR_ID.getValue()));

		// Initialize values required to display search results.
		modelAttributeHelperService.populateSearchResults(
				"star?byId=",
				model, 
				id, 
				showAll, 
				page,
				displayRiteId, 
				dtoMap);

		return ModelAttributeEnum.SEARCH_VIEW.getValue();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/location-map-points", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getLocationMapPoints(
			@RequestParam(value = "withInset", required = false) Long insetId,
			@NotNull HttpSession session,
			HttpServletRequest request) throws IOException, AppException {

		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		final Map<Long,Location> locationMapById = (Map<Long,Location>) session.getAttribute(
				SessionAttributeEnum.LOCATION_MAP_BY_ID.getValue());
		final List<Location> locationList = new ArrayList<Location>();
		for (Location loc : locationMapById.values()) {
			// Return only those points without an Inset ID, where undefined
			if (insetId == null && loc.getInsetId() == null) {
				locationList.add(loc);
			}
			// Otherwise, return only those points with a matching Inset ID
			else if (loc.getInsetId() != null && loc.getInsetId().equals(insetId)) {
				locationList.add(loc);
			}
		}
		final String json = jsonMapper.writeValueAsString(locationList);
		return json;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/patronage-map-points", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getPatronageMapPoints(
			@RequestParam(value = "withInset", required = false) Long insetId,
			@NotNull HttpSession session,
			HttpServletRequest request) throws IOException, AppException {

		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		final Map<Long,Patronage> patronageMapById = (Map<Long,Patronage>) session.getAttribute(
				SessionAttributeEnum.PATRONAGE_MAP_BY_ID.getValue());
		final List<Patronage> patronageList = new ArrayList<Patronage>();
		if (insetId == null) {
			for (Patronage pat : patronageMapById.values()) {
				if (pat.getPointX() != null && pat.getPointY() != null) {
					patronageList.add(pat);
				}
			}
		}
		final String json = jsonMapper.writeValueAsString(patronageList);
		return json;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/star-map-points", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getStarMapPoints(
			@NotNull HttpSession session,
			HttpServletRequest request) throws IOException, AppException {

		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		final Map<Long,Star> starMapById = (Map<Long,Star>) session.getAttribute(
				SessionAttributeEnum.STAR_MAP_BY_ID.getValue());
		final List<Star> starList = new ArrayList<Star>();
		for (Star star : starMapById.values()) {
			starList.add(star);
		}
		final String json = jsonMapper.writeValueAsString(starList);
		return json;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/map-routes", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getMapRoutes(
			@RequestParam(value = "withInset", required = false) Long insetId,
			@NotNull HttpSession session,
			HttpServletRequest request) throws IOException, AppException {

		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		List<Route> routeList = ImmutableList.of();
		// Skip adding routes to inset type maps
		if (insetId == null) {
			final Map<Long,List<Route>> routeMapByLocationId = (Map<Long,List<Route>>) session.getAttribute(
					SessionAttributeEnum.ROUTE_MAP_BY_LOCATION_ID.getValue());
			routeList = (List<Route>) routeMapByLocationId.values().stream()
							.flatMap(Collection::stream)
							.distinct()
							.collect(Collectors.toList());
		}
		final String json = new Gson().toJson(routeList);
		return json;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/map-asterisms", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getMapAsterisms(
			@NotNull HttpSession session,
			HttpServletRequest request) throws IOException, AppException {

		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		List<Constellation> asterismList = ImmutableList.of();
		final Map<Long,List<Constellation>> asterismMapByStarId = (Map<Long,List<Constellation>>) session.getAttribute(
				SessionAttributeEnum.ASTERISM_MAP_BY_STAR_ID.getValue());
		asterismList = (List<Constellation>) asterismMapByStarId.values().stream()
				.flatMap(Collection::stream)
				.distinct()
				.collect(Collectors.toList());
		final String json = new Gson().toJson(asterismList);
		return json;
	}

	@RequestMapping(value = "/reference-excerpt", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody void getReferenceExcerpt(
			@RequestParam(value = "forCommemoration", required = false) Long commemorationId,
			@RequestParam(value = "forStar", required = false) Long starId,
			@NotNull HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {

		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		if (commemorationId != null) {
			pdfService.buildCommemorationPDF(commemorationId, session, response, context);
		} else if (starId != null) {
			pdfService.buildStarPDF(starId, session, response, context);
		}
	}
}
