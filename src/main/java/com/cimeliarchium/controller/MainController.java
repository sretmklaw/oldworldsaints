package com.cimeliarchium.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.CycleDay;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dao.UserSessionInfo;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.SearchCriteriaValidationService;
import com.cimeliarchium.service.dto.CycleDayLiturgyTypeService;
import com.cimeliarchium.service.dto.HourReadingService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.web.SessionManagementService;

@Controller
@PropertySource("classpath:/application.properties")
public class MainController {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private static final String REDIRECT_TO_MAIN = ModelAttributeEnum.REDIRECT_TO.getValue() + ModelAttributeEnum.MAIN_VIEW.getValue();
	
	private ModelAttributeHelperService modelAttributeHelperService;
	private CycleDayLiturgyTypeService cycleDayLiturgyTypeService;
	private HourReadingService hourReadingService;
	private SearchCriteriaValidationService searchCriteriaValidationService;
	private UserSessionInfoService userSessionInfoService;
	private FeatureFlagService featureFlagService;
	private SessionManagementService sessionManagementService;

	@Autowired
	public MainController(ModelAttributeHelperService modelAttributeHelperService,
			CycleDayLiturgyTypeService cycleDayLiturgyTypeService, HourReadingService hourReadingService,
			SearchCriteriaValidationService searchCriteriaValidationService,
			UserSessionInfoService userSessionInfoService, FeatureFlagService featureFlagService, 
			SessionManagementService sessionManagementService) {
		this.modelAttributeHelperService = modelAttributeHelperService;
		this.cycleDayLiturgyTypeService = cycleDayLiturgyTypeService;
		this.hourReadingService = hourReadingService;
		this.searchCriteriaValidationService = searchCriteriaValidationService;
		this.userSessionInfoService = userSessionInfoService;
		this.featureFlagService = featureFlagService;
		this.sessionManagementService = sessionManagementService;
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String displayEntriesForDate(
			@RequestParam(value = "byDate", required = false) Date byDate,
			Model model, 
			HttpSession session,
			HttpServletRequest request
		) throws AppException {

		final User user = modelAttributeHelperService
				.populateNavbarMenuOptions(model, session, request);

		// Attempt to display date search when feature flag is active, or user is Administrator
		Date targetDate = null;
		sessionManagementService.maybeInitializeFeatureFlagSessionVariable(session);
		if (byDate != null 
				&& featureFlagService.isFeatureFlagActiveForUser(featureFlagDateSearchId, session, user)) {
			if (searchCriteriaValidationService.isValidDate(byDate)) {
				targetDate = byDate;
				model.addAttribute(ModelAttributeEnum.SHOW_FOR_DATE.getValue(), 
						DATE_FORMAT.format(targetDate));
			} else {
				model.addAttribute(ModelAttributeEnum.SHOW_FOR_DATE_ERROR.getValue(), 
						DisplayKeyEnum.INVALID_DATE_RANGE.getValue());
			}
		}

		// Initialize Cycle Day, Commemorations, and Celestial Date fields
		final CycleDay cycleDay = cycleDayLiturgyTypeService.initModelForDate(
				session, 
				model, 
				user,
				targetDate);

		// Initialize Hour and Reading fields, based on returned Cycle Day
		hourReadingService.initModelForDate(
				session, 
				model, 
				cycleDay,
				targetDate);

		return ModelAttributeEnum.MAIN_VIEW.getValue();
	}

	@RequestMapping(value = "/hide-maintenance-notification", method = RequestMethod.GET)
	public String hideMaintenanceNotification(HttpSession session) throws AppException {

		final UserSessionInfo userSessionInfo = userSessionInfoService.getCachedValue(session);
		final User currentUser = userSessionInfo.getUser();
		if (currentUser != null && !currentUser.getHasClearedCache()) {
			userSessionInfoService.resetHasClearedCache(currentUser);
		}
		userSessionInfo.setShowNotification(false);
		return REDIRECT_TO_MAIN;
	}

	/**
	 * Custom date format for byDate picker
	 * 
	 * @param binder the WebDataBinder
	 */
	@InitBinder
	public final void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(DATE_FORMAT, true));
	}

	@Value("${featureflag.datesearch.id}")
	private Long featureFlagDateSearchId;

	@Value("${featureflag.audio.id}")
	private Long featureFlagAudioId;
}
