package com.cimeliarchium.service.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.service.dao.CalendarAltReasonService;
import com.cimeliarchium.service.dao.CalendarAltTypeService;
import com.cimeliarchium.service.dao.CenturyService;
import com.cimeliarchium.service.dao.CommemorationTypeService;
import com.cimeliarchium.service.dao.CreedService;
import com.cimeliarchium.service.dao.DayService;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.InsetService;
import com.cimeliarchium.service.dao.LocationService;
import com.cimeliarchium.service.dao.LocationTypeService;
import com.cimeliarchium.service.dao.NationService;
import com.cimeliarchium.service.dao.PatronageService;
import com.cimeliarchium.service.dao.ReferenceService;
import com.cimeliarchium.service.dao.RequestService;
import com.cimeliarchium.service.dao.StarService;
import com.cimeliarchium.service.dao.TagService;
import com.cimeliarchium.service.dao.UserService;
import com.cimeliarchium.service.dto.CalendarMonthDayService;
import com.cimeliarchium.service.dto.ConstellationStarService;
import com.cimeliarchium.service.dto.CycleDayLiturgyTypeService;
import com.cimeliarchium.service.dto.DayCommemorationService;
import com.cimeliarchium.service.dto.HourReadingService;
import com.cimeliarchium.service.dto.NationLocationService;
import com.cimeliarchium.service.dto.NationRegionService;
import com.cimeliarchium.service.dto.PatronageSubtypeService;
import com.cimeliarchium.service.dto.PatronageTypeService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

/**
 * This class is a "Service of Services" which is used to
 * centralize and organize all method calls required to
 * cache service-mapped values as Session Attributes.
 * 
 * Methods are defined in order of sequential initialization 
 * based on a user's progression through the application,
 * i.e. complete Registration, navigate to Main, view Administration.
 * 
 * Each method call includes conditional logic to minimize 
 * the number of queries required per session, including recycling 
 * previously-stored values from multiple-entries-per-key type DAOs.
 */
@Service
@PropertySource("classpath:/errorcode.properties")
public class SessionManagementService extends SessionAttributeHelperService {

	private CalendarAltTypeService calendarAltTypeService;
	private CalendarAltReasonService calendarAltReasonService;
	private CalendarMonthDayService calendarMonthDayService;
	private CenturyService centuryService;
	private CommemorationTypeService commemorationTypeService;
	private ConstellationStarService constellationStarService;
	private CreedService creedService;
	private CycleDayLiturgyTypeService cycleDayLiturgyTypeService;
	private DayCommemorationService dayCommemorationService;
	private DayService dayService;
	private FeatureFlagService featureFlagService;
	private HourReadingService hourReadingService;
	private InsetService insetService;
	private LocationService locationService;
	private LocationTypeService locationTypeService;
	private NationLocationService nationLocationService;
	private NationRegionService nationRegionService;
	private NationService nationService;
	private PatronageService patronageService;
	private PatronageSubtypeService patronageSubtypeService;
	private PatronageTypeService patronageTypeService;
	private ReferenceService referenceService;
	private RequestService requestService;
	private StarService starService;
	private TagService tagService;
	private UserService userService;
	private UserSessionInfoService userSessionInfoService;

	@Autowired DayCommemorationService dayCommemorationInitializer;

	@Autowired
	public SessionManagementService(CalendarAltTypeService calendarAltTypeService, 
			CalendarAltReasonService calendarAltReasonService, CalendarMonthDayService calendarMonthDayService, 
			CenturyService centuryService, CommemorationTypeService commemorationTypeService, ConstellationStarService constellationStarService,
			CreedService creedService, CycleDayLiturgyTypeService cycleDayLiturgyTypeService, 
			DayCommemorationService dayCommemorationService, DayService dayService, FeatureFlagService featureFlagService, 
			HourReadingService hourReadingService, InsetService insetService, LocationService locationService, 
			LocationTypeService locationTypeService, NationLocationService nationLocationService, NationRegionService nationRegionService,
			NationService nationService, PatronageService patronageService, PatronageSubtypeService patronageSubtypeService, 
			PatronageTypeService patronageTypeService, ReferenceService referenceService, RequestService requestService, StarService starService,
			TagService tagService, UserService userService, UserSessionInfoService userSessionInfoService) {
		this.calendarAltTypeService = calendarAltTypeService;
		this.calendarAltReasonService = calendarAltReasonService;
		this.calendarMonthDayService = calendarMonthDayService;
		this.centuryService = centuryService;
		this.commemorationTypeService = commemorationTypeService;
		this.constellationStarService = constellationStarService;
		this.creedService = creedService;
		this.cycleDayLiturgyTypeService = cycleDayLiturgyTypeService;
		this.dayService = dayService;
		this.dayCommemorationService = dayCommemorationService;
		this.featureFlagService = featureFlagService;
		this.hourReadingService = hourReadingService;
		this.insetService = insetService;
		this.locationService = locationService;
		this.locationTypeService = locationTypeService;
		this.nationLocationService = nationLocationService;
		this.nationRegionService = nationRegionService;
		this.nationService = nationService;
		this.patronageService = patronageService;
		this.patronageSubtypeService = patronageSubtypeService;
		this.patronageTypeService = patronageTypeService;
		this.referenceService = referenceService;
		this.requestService = requestService;
		this.starService = starService;
		this.tagService = tagService;
		this.userService = userService;
		this.userSessionInfoService = userSessionInfoService;
	}

	private Boolean hasUpdate;

	/**
	 * Method used to organize queries for values needed to determine whether
	 * the Register view is accessible prior to Authentication.
	 * 
	 * Total Cost: 1 query
	 * 
	 * @param session the HttpSession
	 * @throws AppException
	 */
	public void maybeInitializeFeatureFlagSessionVariable(@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, maybeInitializeFeatureFlagSessionVariableMissingSession);
		featureFlagService.initCache(session);
	}

	/**
	 * Method used to organize queries for values needed on navigate to 
	 * the Register view prior to Authentication.
	 * 
	 * Total Cost, assuming no cached values were previously initialized: 
	 * 3 queries.
	 * 
	 * @param session the HttpSession
	 * @throws AppException
	 */
	public void maybeInitializeBasicSessionVariables(@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, maybeInitializeBasicSessionVariablesMissingSession);

		// Initialize mappings required for Register menu options.
		maybeInitializeFeatureFlagSessionVariable(session);
		nationRegionService.initCache(session);
	}

	/**
	 * Method used to organize queries for values needed on navigate to 
	 * any page following successful Authentication.
	 * 
	 * Total Cost, assuming dependent cached values were previously initialized: 
	 * 16 queries.
	 * 
	 * @param session the HttpSession
	 * @param request the HttpServletRequest
	 * @param user the User
	 * @throws AppException 
	 * @return hasUpdate
	 */
	public Boolean maybeInitializeUserSessionVariables(
			@NotNull HttpSession session,
			HttpServletRequest request, 
			User user) throws AppException {

		super.throwIfMissing(session, maybeInitializeUserSessionVariablesMissingSession);
		session.setAttribute(SessionAttributeEnum.USER_IS_AUTHENTICATED.getValue(), true);

		this.hasUpdate = false;
		// First, ensure that basic session variables are always populated.
		this.maybeInitializeBasicSessionVariables(session);

		// Then ensure that current user session info 
		// is obtained either from session cache or from query.
		// Also initialize a snapshot list of active Requests.
		this.setHasUpdate(userSessionInfoService.initCache(session, user));
		this.setHasUpdate(requestService.initCacheById(session));

		// Initialize DTO-type mappings required for Main Commemorations and Readings.
		// Constellation & Star cache mapping must be populated prior to initializing CycleDay.
		this.setHasUpdate(constellationStarService.initCache(session)); // This is static initialized on server startup.
		this.setHasUpdate(starService.initCache(session));
		this.setHasUpdate(cycleDayLiturgyTypeService.initCache(session)); // Includes additional query to obtain Lunar Phase
		this.setHasUpdate(hourReadingService.initCache(session));

		/*
		 * NOTE: The following mappings are now static initialized on server startup, not on login.
		 */

		// Multiple-entries-per-key type DAOs mappings required for Navigation bar drop-down menus:
		this.setHasUpdate(calendarMonthDayService.initCache(session));
		this.setHasUpdate(dayCommemorationService.initCache(session));
		this.setHasUpdate(nationLocationService.initCache(session));
		this.setHasUpdate(patronageTypeService.initCache(session));
		this.setHasUpdate(patronageSubtypeService.initCache(session));

		// One-to-one entity mapping type DAOs mappings required for Navigation bar drop-down menus:
		// - a. Query-sourced
		this.setHasUpdate(centuryService.initCache(session));
		this.setHasUpdate(tagService.initCache(session));
		this.setHasUpdate(referenceService.initCache(session));
		// - b. Cache-sourced - depends on query-sourced and/or DTO-type mappings
		this.setHasUpdate(dayService.initCache(session));
		this.setHasUpdate(locationService.initCache(session));
		this.setHasUpdate(nationService.initCache(session));
		this.setHasUpdate(patronageService.initCache(session));

		// Other required mappings
		this.setHasUpdate(insetService.initCache(session));

		// Log latest user authentication information on cached value initialization
		if (this.hasUpdate) {
			userService.updateLastLoginSuccess(session, request, userSessionInfoService.getCachedValue(session).getUser());
		}

		return this.hasUpdate;
	}

	private void setHasUpdate(Boolean updatedVal) {
		Boolean hasUpdate = this.hasUpdate;
		this.hasUpdate = hasUpdate || updatedVal;
	}

	/**
	 * Method used to organize queries for values needed on navigate to 
	 * the Request view following successful Authentication.
	 * 
	 * Total Cost, assuming dependent cached values were previously initialized: 
	 * 6 queries.
	 * 
	 * @param session the HttpSession
	 * @param request the HttpServletRequest
	 * @throws AppException 
	 */
	public void maybeInitializeRequestSessionVariables(
			@NotNull HttpSession session,
			HttpServletRequest request) throws AppException {

		// First, ensure that basic and user session variables are always populated.
		this.maybeInitializeUserSessionVariables(session, request, null);

		// Then initialize DTO-type mappings required for drop-down menus:
		creedService.initCache(session);
		commemorationTypeService.initCache(session);
		calendarAltTypeService.initCache(session);
		calendarAltReasonService.initCache(session);
		locationTypeService.initCache(session);
	}

	/**
	 * Method used to organize queries for values needed on navigate to 
	 * the Administration view following successful Authentication.
	 * 
	 * Total Cost, assuming dependent cached values were previously initialized: 
	 * 2 queries.
	 * 
	 * @param session the HttpSession
	 * @throws AppException 
	 */
	public void maybeInitializeAdminSessionVariables(
			@NotNull HttpSession session) throws AppException {

		// Initialize the mappings needed to populate the Administrator consoles.
		// Note that both queries will be re-executed EVERY time the Administration view gets called:
		// 1. User Accounts
		userService.initCache(session);
		// 2. Bugfix Requests and Entry Requests
		requestService.initCacheByType(session);
	}

	@Value("${sessionmanagementservice.maybeinitializefeatureflagsessionvariable.session}")
	private String maybeInitializeFeatureFlagSessionVariableMissingSession;

	@Value("${sessionmanagementservice.maybeinitializebasicsessionvariables.session}")
	private String maybeInitializeBasicSessionVariablesMissingSession;

	@Value("${sessionmanagementservice.maybeinitializeusersessionvariables.session}")
	private String maybeInitializeUserSessionVariablesMissingSession;
}
