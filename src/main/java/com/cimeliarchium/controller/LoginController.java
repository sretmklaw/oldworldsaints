package com.cimeliarchium.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimeliarchium.enums.CacheAttributeEnum;
import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.web.RedirectServletRequestDetails;
import com.cimeliarchium.service.dao.UserService;
import com.cimeliarchium.service.web.LoginFailureService;
import com.cimeliarchium.service.web.SecurityService;

@Controller
public class LoginController {

	private ApplicationContext applicationContext;
	private UserService userService;
	private SecurityService securityService;

	public LoginController(ApplicationContext applicationContext, UserService userService, SecurityService securityService) {
		this.applicationContext = applicationContext;
		this.userService = userService;
		this.securityService = securityService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(
			Model model, 
			HttpServletRequest request) throws AppException {

		// Add a descriptive message based on current status
		RedirectServletRequestDetails.addFlashMessageAsModelAttribute(model, request);
		// Initialize static images cache
		model.addAttribute(ModelAttributeEnum.CACHED_IMAGES_LIST.getValue(), CacheAttributeEnum.getLoginCacheAttributes());
		// Initialize User for form submission
		model.addAttribute(ModelAttributeEnum.USER.getValue(), new User.Builder().build());
		return ModelAttributeEnum.LOGIN_VIEW.getValue();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String authenticate(
			HttpServletRequest request, 
			HttpServletResponse response) throws AppException, IOException, ServletException {

		final LoginFailureService loginFailureService = applicationContext.getBean(LoginFailureService.class);
		final HttpSession session = request.getSession(false);
		// Obtain current user and initialize any unavailable Session Variables.
		final User user = userService.queryForCurrentUser();
		final int userLockStatus = loginFailureService.getUserLockStatus(user, request);
		if (userLockStatus < 3) {
			// Only increment failed Login attempts on landing page navigation for anonymous Users.
			final Boolean isUserAuthenticated = session.getAttribute(SessionAttributeEnum.USER_IS_AUTHENTICATED.getValue()) != null;
			if (userLockStatus == 1 && !isUserAuthenticated) {
				userService.updateLastLoginFailure(user, request.getSession());
			}
		}
		return new RedirectServletRequestDetails.Builder()
				.withRequest(request)
				.withResponse(response)
				.withHasFailures(userLockStatus == 3)
				.withSuccessView("/" + ModelAttributeEnum.MAIN_VIEW.getValue())
				.withFailureView("/" + ModelAttributeEnum.LOGIN_VIEW.getValue())
				.withFailureMessage(DisplayKeyEnum.ACCOUNT_LOCKED.getValue())
				.build();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam(value = "hasTimedOut", required = false) Boolean hasTimedOut) throws IOException {

		securityService.resetSession(request);
		return new RedirectServletRequestDetails.Builder()
				.withRequest(request)
				.withResponse(response)
				.withHasFailures(hasTimedOut != null)
				.withSuccessView("/" + ModelAttributeEnum.LOGIN_VIEW.getValue())
				.withSuccessMessage(DisplayKeyEnum.LOGOUT_SUCCESS.getValue())
				.withFailureView("/" + ModelAttributeEnum.LOGIN_VIEW.getValue())
				.withFailureMessage(DisplayKeyEnum.SESSION_TIMEOUT.getValue())
				.build();
	}
}