package com.cimeliarchium.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.web.RedirectServletRequestDetails;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.UserService;
import com.cimeliarchium.service.dao.UserValidationService;
import com.cimeliarchium.service.dto.CalendarRiteService;
import com.cimeliarchium.service.dto.NationRegionService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.web.CaptchaService;
import com.cimeliarchium.service.web.SecurityService;
import com.cimeliarchium.service.web.SessionManagementService;

@Controller
@PropertySource("classpath:/application.properties")
public class RegisterController {

	private CalendarRiteService calendarRiteService;
	private CaptchaService captchaService;
	private NationRegionService nationRegionService;
	private SecurityService securityService;
	private SessionManagementService sessionManagementService;
	private UserService userService;
	private UserValidationService userValidation;
	private FeatureFlagService featureFlagService;
	private UserSessionInfoService userSessionInfoService;

	@Autowired
	public RegisterController(CalendarRiteService calendarRiteService, CaptchaService captchaService,
			NationRegionService nationRegionService, SecurityService securityService,
			SessionManagementService sessionManagementService, UserService userService,
			UserValidationService userValidation, FeatureFlagService featureFlagService,
			UserSessionInfoService userSessionInfoService) {
		this.calendarRiteService = calendarRiteService;
		this.captchaService = captchaService;
		this.nationRegionService = nationRegionService;
		this.securityService = securityService;
		this.sessionManagementService = sessionManagementService;
		this.userService = userService;
		this.userValidation = userValidation;
		this.featureFlagService = featureFlagService;
		this.userSessionInfoService = userSessionInfoService;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegistration(
			Model model, 
			HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session) throws AppException, IOException {

		// Check for existing user and ensure User Registration feature is not locked down by Administrator
		final User foundUser = userService.findByUsername(securityService.getAuthenticatedUserCredential());
		final User foundOrNewUser = (foundUser != null) ? foundUser : new User.Builder().build();
		foundOrNewUser.setPassword(null);
		sessionManagementService.maybeInitializeFeatureFlagSessionVariable(session);
		if (!featureFlagService.isFeatureFlagActiveForUser(featureFlagUpdateId, session, foundOrNewUser)) {
			return RedirectServletRequestDetails.redirectHomeWithMessage(request, response, 
					DisplayKeyEnum.ACCOUNT_UPDATE_DISABLED.getValue());
		}
		// Initialize mappings of entities required for drop-down menus and CAPTCHA
		sessionManagementService.maybeInitializeBasicSessionVariables(session);
		calendarRiteService.initModel(session, model, foundOrNewUser);
		nationRegionService.initModel(session, model, foundOrNewUser);
		captchaService.initModel(session, model);

		// If applicable, add Update Form Header and pre-populate current User details on the form input fields
		if (foundOrNewUser != null && foundOrNewUser.getUsername() != null) {
			model.addAttribute(ModelAttributeEnum.FORM_HEADER.getValue(), 
					ModelAttributeEnum.UPDATE_VIEW.getValue());
			if (userSessionInfoService.isAuthorizedRequestForCurrentUser(session, foundOrNewUser.getUsername())) {
				userService.syncExistingUserDetails(model, foundOrNewUser);
			} else {
				return ModelAttributeEnum.HOME_VIEW.getValue();
			}
		}
		// Otherwise, add Registration Form Header
		else {
			model.addAttribute(ModelAttributeEnum.FORM_HEADER.getValue(), 
					ModelAttributeEnum.REGISTER_VIEW.getValue());
		}
		model.addAttribute(ModelAttributeEnum.USER.getValue(), foundOrNewUser);
		return ModelAttributeEnum.REGISTER_VIEW.getValue();
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegistration(
			@ModelAttribute("user") User user,
			BindingResult result, 
			Model model, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session) throws AppException, IOException {

		// Initialize feature flags and mappings of entities required for drop-down menus
		sessionManagementService.maybeInitializeBasicSessionVariables(session);
		calendarRiteService.initModel(session, model, user);
		nationRegionService.initModel(session, model, user);

		// If applicable, add Update Form Header and Validate as such
		// Ensure User Registration feature is not locked down by Administrator
		final User existingUser = userService.findByUsername(securityService.getAuthenticatedUserCredential());
		sessionManagementService.maybeInitializeFeatureFlagSessionVariable(session);
		if (!featureFlagService.isFeatureFlagActiveForUser(featureFlagUpdateId, session, existingUser)) {
			return RedirectServletRequestDetails.redirectHomeWithMessage(request, response, 
					DisplayKeyEnum.ACCOUNT_UPDATE_DISABLED.getValue());
		}
		if (existingUser != null) {
			model.addAttribute(ModelAttributeEnum.FORM_HEADER.getValue(), 
					ModelAttributeEnum.UPDATE_VIEW.getValue());
			// On Validate failure, return to current View to display field-level errors
			userValidation.beforeUpdate(session, user, result);
			if (result.hasErrors()) {
				captchaService.maybeRegenerateCaptchaForUser(model, session, true);
				user.setCaptcha(null);
				model.addAttribute(ModelAttributeEnum.USER.getValue(), user);
				return ModelAttributeEnum.REGISTER_VIEW.getValue();
			}
			userService.update(user);
			// On Update success, close current session and redirect to Login with Confirm Message
			securityService.resetSession(request);
			return new RedirectServletRequestDetails.Builder()
					.withRequest(request)
					.withResponse(response)
					.withSuccessView("/" + ModelAttributeEnum.LOGIN_VIEW.getValue())
					.withSuccessMessage(DisplayKeyEnum.ACCOUNT_UPDATE_SUCCESS.getValue())
					.withHasFailures(false)
					.build();
		}

		// Otherwise, add Registration Form Header and Validate as such
		else {
			model.addAttribute(ModelAttributeEnum.FORM_HEADER.getValue(), 
					ModelAttributeEnum.REGISTER_VIEW.getValue());
			// On Validate failure, return to current View to display field-level errors
			userValidation.beforeCreate(session, user, result);
			if (result.hasErrors()) {
				captchaService.maybeRegenerateCaptchaForUser(model, session, true);
				user.setCaptcha(null);
				model.addAttribute(ModelAttributeEnum.USER.getValue(), user);
				return ModelAttributeEnum.REGISTER_VIEW.getValue();
			}
			// On Validate success, redirect to Login with Confirm Message
			else {
				userService.create(session, user);
				return new RedirectServletRequestDetails.Builder()
						.withRequest(request)
						.withResponse(response)
						.withSuccessView("/" + ModelAttributeEnum.LOGIN_VIEW.getValue())
						.withSuccessMessage(DisplayKeyEnum.ACCOUNT_UPDATE_SUCCESS.getValue())
						.withHasFailures(false)
						.build();
			}
		}
	}

	@RequestMapping(value = "/captcha", 
			method = RequestMethod.GET, 
			produces = "text/plain")
	public @ResponseBody String regenerateCaptchaImage(HttpSession session) throws AppException {

		return captchaService.generateImage(session);
	}

	@Value("${featureflag.update.id}")
	private Long featureFlagUpdateId;
}