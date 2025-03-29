package com.cimeliarchium.service.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.SearchCriteria;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.service.dao.CenturyService;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.RequestService;
import com.cimeliarchium.service.dao.TagService;
import com.cimeliarchium.service.dto.CalendarMonthDayService;
import com.cimeliarchium.service.dto.ConstellationStarService;
import com.cimeliarchium.service.dto.NationRegionService;
import com.cimeliarchium.service.dto.PatronageTypeService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.web.SessionManagementService;
import com.google.common.collect.ImmutableList;

@Service
@PropertySource({
	"classpath:/application.properties"
})
public class ModelAttributeHelperService {

	private static final Integer MAX_RESULTS_PER_PAGE_COUNT = 10;
	private static final String NAME_SEARCH_RANDOM_INDICATOR = "000";
	private static final Long ID_SEARCH_RANDOM_INDICATOR = 000L;

	@Autowired private CalendarMonthDayService calendarMonthDayService;
	@Autowired private CenturyService centuryService;
	@Autowired private ConstellationStarService constellationStarService;
	@Autowired private FeatureFlagService featureFlagService;
	@Autowired private NationRegionService nationRegionService;
	@Autowired private PatronageTypeService patronageTypeService;
	@Autowired private RequestService requestService;
	@Autowired private SessionManagementService sessionManagementService;
	@Autowired private TagService tagService;
	@Autowired private UserSessionInfoService userSessionInfoService;

	/**
	 * Method used to obtain current user and 
	 * initialize any unavailable Session Attributes.
	 * Required for all views that display the navigation bar.
	 * 
	 * @param model the Model
	 * @param session the HttpSession
	 * @param request the HttpServletRequest
	 * @throws AppException 
	 */
	public User populateNavbarMenuOptions(
			Model model, 
			HttpSession session,
			HttpServletRequest request) throws AppException {

		// Obtain current user and initialize any unavailable Session Variables for Navigation Bar
		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		final User user = userSessionInfoService.getCachedValue(session).getUser();
		model.addAttribute(ModelAttributeEnum.SEARCH_CRITERIA.getValue(), new SearchCriteria());
		userSessionInfoService.initModel(session, model, null);

		// Determine visibility for dynamic menu options
		featureFlagService.initModel(session, model, user);
		nationRegionService.populateOldWorldNationMenuOptions(session, model);
		constellationStarService.populateConstellationMenuOptions(session, model);
		centuryService.initModelForUser(session, model, user);
		tagService.initModel(session, model, user);
		patronageTypeService.initModel(session, model, null);
		calendarMonthDayService.initModel(session, model, null);
		// Preview the current number of open requests (Administrator only)
		if (user.getHasAdminRole()) {
			final Map<Long, Request> requestMap = requestService.getCachedValuesById(session);
			if (requestMap != null && requestMap.size() > 0) {
				model.addAttribute(ModelAttributeEnum.OPEN_REQUEST_COUNT.getValue(), requestMap.size());
			}
		}
		return user;
	}

	/**
	 * Method used to initialize required Model attributes 
	 * for name-specific Search-type views.
	 * 
	 * @param model the Model
	 * @param name the name being searched
	 * @param showAll the flag to ignore User Rite when filtering Search results
	 * @param user the current User
	 * @param namesByRiteMap filtered mapping of Commemoration names by Rite
	 * @param dtoMap the mapping of DTO type results
	 */
	public void populateSearchResultsByName(
			@NotNull String path,
			Model model,
			String name,
			Boolean showAll,
			Long page,
			@NotNull User user,
			@NotNull Map<Long, List<String>> namesByRiteMap,
			@NotNull Map<String, Map<DayCommemorationDto, List<Rite>>> dtoMap) {

		// Conditionally build a mapping of formatted results, 
		// where an ID has been provided.
		if (name != null) {

			// When "Show All" option is selected, temporarily grant the User "All Commemorations" access.
			// Otherwise, obtain the current user Rite to use for filtering the results which get displayed.
			Boolean shouldShowAll = (user == null || (showAll != null && showAll));
			final Long userRiteId = shouldShowAll ? riteAllId : user.getRiteId();
			Map<DayCommemorationDto, List<Rite>> resultMap = new TreeMap<>();

			// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
			final List<String> namesForRite = namesByRiteMap.get(getDisplayRiteId(showAll, user));
			final String[] orderedKeys = (namesForRite != null) 
					? namesForRite.toArray(String[]::new) 
					: Collections.emptyList().toArray(String[]::new);
			final String searchByName = (NAME_SEARCH_RANDOM_INDICATOR).equals(name) 
					? orderedKeys[new Random().nextInt(orderedKeys.length)] 
					: name;

			// Select DayCommemorations with name or alternate name matching the request
			for (Map.Entry<String, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final String commemorationName = entry.getKey();
				for (Map.Entry<DayCommemorationDto, List<Rite>> dtoEntry : entry.getValue().entrySet()) {
					final DayCommemorationDto dto = dtoEntry.getKey();
					final List<Long> dtoRiteIdList = dtoEntry.getValue().stream()
							.map(Rite::getRiteId)
							.collect(Collectors.toList());
					// Consider only those Commemorations whose name contains the search term
					if ((commemorationName != null && commemorationName.toLowerCase()
							.contains(((String) searchByName).toLowerCase()))) {
						// When not "Show All" and User Rite is not "All", only consider rows matching User Rite
						if (userRiteId == riteAllId) {
							resultMap.put(dto, dtoEntry.getValue());
						} else if (dtoRiteIdList.contains(userRiteId)) {
							resultMap.put(dtoEntry.getKey(), ImmutableList.of());
						}
					}
				}
			}
			this.addPagination(path, model, searchByName, shouldShowAll, page, resultMap);
		}
		// Otherwise, populate empty results.
		else {
			model.addAttribute(ModelAttributeEnum.RESULT_MAP.getValue(), new HashMap<>());
		}
	}

	/**
	 * Method used to initialize required Model attributes 
	 * for ID-specific Search-type views.
	 * 
	 * @param model the Model
	 * @param id the entity ID
	 * @param showAll the flag to ignore User Rite when filtering Search results
	 * @param user the current User
	 * @param idsByRiteMap filtered mapping of Commemoration IDs by Rite
	 * @param dtoMap the mapping of DTO type results
	 */
	public void populateSearchResultsById(
			@NotNull String path,
			Model model,
			Object id,
			Boolean showAll,
			Long page,
			@NotNull User user,
			@NotNull Map<Long, List<Long>> idsByRiteMap,
			@NotNull Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap) {

		// Conditionally build a mapping of formatted results, 
		// where an ID has been provided.
		if (id != null) {

			Map<DayCommemorationDto, List<Rite>> resultMap = new TreeMap<>();

			// Random ID indicator will search for a random entry from the mapping instead of passed-in value of 0
			final List<Long> keysForRite = idsByRiteMap.get(getDisplayRiteId(showAll, user));
			final Long[] orderedKeys = (keysForRite != null) 
					? keysForRite.toArray(Long[]::new) 
					: Collections.emptyList().toArray(Long[]::new);
			final Long searchById = (id == ID_SEARCH_RANDOM_INDICATOR) 
					? orderedKeys[new Random().nextInt(orderedKeys.length)] 
					: (Long) id;

			final Map<DayCommemorationDto, List<Rite>> rs = ((Map<Long, Map<DayCommemorationDto, List<Rite>>>) dtoMap).get(searchById);
			if (rs != null) {
				final DayCommemorationDto dto = rs.keySet().stream()
						.findFirst()
						.orElse(null);
				final List<Rite> riteList = rs.values().stream()
						.findFirst()
						.orElse(ImmutableList.of());
				resultMap.put(dto, riteList);
			}
			// Always show ID-specific search results for All Rites, regardless of User Rite.
			// Also, pass 'true' for ShowAll parameter to ensure that Rite list is always shown for single results.
			this.addPagination(path, model, searchById, true, page, resultMap);
		}
		// Otherwise, populate empty results.
		else {
			model.addAttribute(ModelAttributeEnum.RESULT_MAP.getValue(), new HashMap<>());
		}
	}

	/**
	 * Method used to initialize required Model attributes 
	 * for generic Search results on Search-type views.
	 * 
	 * @param model the Model
	 * @param id the entity ID
	 * @param showAll the flag to ignore User Rite when filtering Search results
	 * @param user the current User
	 * @param dtoMap the mapping of DTO type results
	 */
	@SuppressWarnings("unchecked")
	public void populateSearchResults(
			@NotNull String path,
			Model model,
			Object id,
			Boolean showAll,
			Long page,
			@NotNull Long userRiteId,
			@NotNull Object dtoMap) {

		// Conditionally build a mapping of formatted results, 
		// where an ID has been provided.
		if (id != null) {

			Map<DayCommemorationDto, List<Rite>> resultMap = new TreeMap<>();

			// Select DayCommemorations with Day ID matching the request
			Map<DayCommemorationDto, List<Rite>> rs = ((Map<Long, Map<DayCommemorationDto, List<Rite>>>) dtoMap).get(id);
			if (rs != null && !rs.isEmpty()) {
				for (Map.Entry<DayCommemorationDto, List<Rite>> entry : rs.entrySet()) {
					final List<Long> dtoRiteIdList = entry.getValue().stream()
							.map(Rite::getRiteId)
							.collect(Collectors.toList());
					// When not "Show All" and User Rite is not "All", 
					// only consider rows matching User Rite
					if (userRiteId == riteAllId) {
						resultMap.put(entry.getKey(), entry.getValue());
					} else if (dtoRiteIdList.contains(userRiteId)) {
						resultMap.put(entry.getKey(), ImmutableList.of());
					}
				}
			}
			this.addPagination(path, model, id, showAll, page, resultMap);
		}
		// Otherwise, populate empty results.
		else {
			model.addAttribute(ModelAttributeEnum.RESULT_MAP.getValue(), new HashMap<>());
		}
	}

	/**
	 * When "Show All" option is selected, temporarily grant the User "All Commemorations" access.
	 * Otherwise, obtain the current user Rite to use for filtering the results which get displayed.
	 */
	public Long getDisplayRiteId(
			Boolean shouldShowAll,
			User user) {

		final var riteId = (user != null) ? user.getRiteId() : null;
		return (riteId == null || (shouldShowAll != null && shouldShowAll)) ? riteAllId : riteId;
	}

	/**
	 * Method used to support pagination across all Search-type views.
	 * 
	 * @param model the Model
	 * @param id the entity ID
	 * @param showAll the flag to ignore User Rite when filtering Search results
	 * @param user the current User
	 * @param dtoMap the mapping of DTO type results
	 */
	public void addPagination(
			@NotNull String path,
			Model model,
			Object id,
			Boolean showAll,
			Long page,
			@NotNull Map<DayCommemorationDto, List<Rite>> resultMap) {
		
		// Select a Subset of results to implement pagination
		final int pageCount = (resultMap.size() > 0) 
				? (int) Math.ceil(Double.valueOf(resultMap.size()) / MAX_RESULTS_PER_PAGE_COUNT) 
				: 1;
		Long skipResults;
		if (page != null && pageCount > 0) {
			skipResults = (page - 1) * MAX_RESULTS_PER_PAGE_COUNT;
		} else {
			skipResults = 0L;
		}
		model.addAttribute(ModelAttributeEnum.RESULT_MAP.getValue(), new TreeMap<>(resultMap.entrySet().stream()
				.skip(skipResults)
				.limit(MAX_RESULTS_PER_PAGE_COUNT)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
		model.addAttribute(ModelAttributeEnum.RESULT_SIZE.getValue(), resultMap.size());
		if (pageCount > 1) {
			model.addAttribute(ModelAttributeEnum.PAGE_NUMBER_LIST.getValue(), IntStream.range(1, pageCount+1).toArray());
			model.addAttribute(ModelAttributeEnum.CURRENT_PAGE_NUMBER.getValue(), page);
		}

		// Add fields required to re-run search across all Rites
		model.addAttribute(ModelAttributeEnum.SEARCH_PATH.getValue(), "search/" + path);
		model.addAttribute(ModelAttributeEnum.SEARCH_ID.getValue(), id);
		if (showAll != null && showAll) {
			model.addAttribute(ModelAttributeEnum.SHOW_ALL.getValue(), showAll);
		}
	}

	/**
	 * Method used to initialize the search type and term.
	 * 
	 * @param model the Model
	 * @param searchType the search type
	 * @param searchTerm the search term
	 * @return ModelAndView
	 */
	public static Model addSearchTypeAndTerm(
			Model model, 
			String searchType, 
			String searchTerm) {

		final String modelName = ModelAttributeEnum.SEARCH_VIEW.getValue();
		if (searchType != null) {
			model.addAttribute(modelName + "Type", searchType);
		}
		model.addAttribute(modelName + "Term", 
				(searchTerm != null && !searchTerm.isEmpty()) 
						? (NAME_SEARCH_RANDOM_INDICATOR.equals(searchTerm)) 
								? DisplayKeyEnum.RANDOM.getValue() 
								: searchTerm
						: DisplayKeyEnum.UNKNOWN.getValue());

		return model;
	}

	@Value("${rite.all.id}")
	private Long riteAllId;
}
