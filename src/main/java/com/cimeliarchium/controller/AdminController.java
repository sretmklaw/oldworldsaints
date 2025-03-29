package com.cimeliarchium.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.FeatureFlag;
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.model.dao.SearchCriteria;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.UserSessionInfoDto;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.RequestService;
import com.cimeliarchium.service.dao.UserService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.web.SessionManagementService;

@Controller
@PropertySource("classpath:/application.properties")
public class AdminController {

	private SessionManagementService sessionManagementService;
	private UserSessionInfoService userSessionInfoService;
	private UserService userService;
	private RequestService requestService;
	private FeatureFlagService featureFlagService;

	@Autowired
	public AdminController(SessionManagementService sessionManagementService,
			UserSessionInfoService userSessionInfoService, UserService userService, RequestService requestService,
			FeatureFlagService featureFlagService) {
		this.sessionManagementService = sessionManagementService;
		this.userSessionInfoService = userSessionInfoService;
		this.userService = userService;
		this.requestService = requestService;
		this.featureFlagService = featureFlagService;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(
			HttpSession session,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		return initializeAdminView(session, model, request, response);
	}

	@RequestMapping(value = "/admin/update-users", method = RequestMethod.GET)
	public @ResponseBody List<Long> updateUsers(
			@RequestParam(value = "byIds", required = true) List<Long> idArray,
			@RequestParam(value = "withStatus", required = true) String statusCode,
			@NotNull HttpSession session,
			HttpServletResponse response) throws AppException {

		return (userSessionInfoService.isAdminUser(session, response)) 
				? userService.updateUsersStatus(idArray, statusCode, session)
				: null;
	}

	@RequestMapping(value = "/admin/update-reqs", method = RequestMethod.GET)
	public @ResponseBody List<Long> updateRequests(
			@RequestParam(value = "byIds", required = true) List<Long> idArray,
			@NotNull HttpSession session,
			HttpServletResponse response) throws AppException {

		return (userSessionInfoService.isAdminUser(session, response)) 
				? requestService.closeRequests(idArray, session)
				: null;
	}

	@RequestMapping(value = "/admin/update-flags", method = RequestMethod.GET)
	public @ResponseBody List<Long> updateFlags(
			@RequestParam(value = "byIds", required = true) List<Long> idArray,
			@RequestParam(value = "withBoolean", required = true) String newValue,
			@RequestParam(value = "withString", required = false) String newString,
			@NotNull HttpSession session,
			HttpServletResponse response) throws AppException {

		return (userSessionInfoService.isAdminUser(session, response)) 
				? featureFlagService.updateFlagsStatus(idArray, newValue, newString, session) 
				: null;
	}

	/**
	 * Method used to initialize the Administrator console on load
	 * 
	 * @param session the HttpSession
	 * @param model the Model
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * @return model
	 * @throws AppException 
	 */
	private String initializeAdminView(
			HttpSession session,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		// First ensure that basic and user session variables are always populated.
		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		User requestingUser = userSessionInfoService.getCachedValue(session).getUser();
		if (!requestingUser.getHasAdminRole()) {
			return ModelAttributeEnum.REDIRECT_TO.getValue() + ModelAttributeEnum.MAIN_VIEW.getValue();
		}
		// Initialize any unavailable Session Variables
		sessionManagementService.maybeInitializeAdminSessionVariables(session);

		// Initialize navigation bar resources for Administrator
		model.addAttribute(ModelAttributeEnum.SEARCH_CRITERIA.getValue(), new SearchCriteria());
		model.addAttribute(ModelAttributeEnum.FEATURE_FLAG.getValue(), new FeatureFlag());

		// Feature Flag menu options should always be visible to Administrator
		model.addAttribute(ModelAttributeEnum.ADMIN_BUTTON_IS_VISIBLE.getValue(), true);
		model.addAttribute(ModelAttributeEnum.UPDATE_BUTTON_IS_VISIBLE.getValue(), true);
		model.addAttribute(ModelAttributeEnum.REQUEST_BUTTON_IS_VISIBLE.getValue(), true);
		model.addAttribute(ModelAttributeEnum.DONATE_BUTTON_IS_VISIBLE.getValue(), true);

		// Populate the listing of User Session Info from database
		final List<UserSessionInfoDto> userSessionInfoList = userSessionInfoService.listAll();
		model.addAttribute(ModelAttributeEnum.USER_SESSION_INFO_LIST.getValue(), userSessionInfoList); 

		// Populate the mapping of Requests by Type from database
		// and store as separate lists to iterate within the View
		final Map<Long, List<Request>> requestMap = requestService.map();
		model.addAttribute(ModelAttributeEnum.BUGFIX_REQUEST_LIST.getValue(), requestMap.get(requestBugfixId)); 

		// Populate the listing of Feature Flags from database
		final List<FeatureFlag> featureFlagList = featureFlagService.list();
		model.addAttribute(ModelAttributeEnum.FEATURE_FLAG_LIST.getValue(), featureFlagList); 

		return ModelAttributeEnum.ADMIN_VIEW.getValue();
	}

	@Value("${request.bugfix.id}")
	private Long requestBugfixId;

	@Value("${request.entry.id}")
	private Long requestEntryId;
}