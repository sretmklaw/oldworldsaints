package com.cimeliarchium.controller;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.enums.StatusCodeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dao.UserSessionInfo;
import com.cimeliarchium.model.web.ForwardServletRequestDetails;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.RequestService;
import com.cimeliarchium.service.dao.RequestValidationService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.web.CaptchaService;
import com.cimeliarchium.service.web.SecurityService;
import com.cimeliarchium.service.web.SessionManagementService;

@Controller
@ControllerAdvice
@PropertySource("classpath:/application.properties")
public class ErrorInfoController implements ErrorController {

	private CaptchaService captchaService;
	private RequestService requestService;
	private RequestValidationService requestValidationService;
	private SecurityService securityService;
	private FeatureFlagService featureFlagService;
	private SessionManagementService sessionManagementService;

	@Autowired
	public ErrorInfoController(CaptchaService captchaService, RequestService requestService,
			RequestValidationService requestValidationService, SecurityService securityService,
			FeatureFlagService featureFlagService, SessionManagementService sessionManagementService) {
		this.captchaService = captchaService;
		this.requestService = requestService;
		this.requestValidationService = requestValidationService;
		this.securityService = securityService;
		this.featureFlagService = featureFlagService;
		this.sessionManagementService = sessionManagementService;
	}

	private String errorCode;
	private String errorPath;
	private HttpStatus errorType;

	@ExceptionHandler(value = { Throwable.class })
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String displayErrorInfo(
			HttpServletRequest servletRequest, 
			HttpServletResponse response,
			HttpSession session,
			Model model,
			Exception ex) throws AppException {

		// Initialize error information from the Exception and Request URI
		this.errorType = securityService.parseErrorType(ex);
		this.errorCode = securityService.parseErrorCode(ex, errorType);
		this.errorPath = securityService.parseResourceRequest(servletRequest, response);

		// Determine whether to display option for Error ticket submission based on valid User ID
		final UserSessionInfo userSessionInfo = (UserSessionInfo) session
				.getAttribute(SessionAttributeEnum.CURRENT_USER_DETAILS.getValue());

		// Display CAPTCHA and Form Submission option, only if valid User
		Long userId = null;
		String username = null;
		if (userSessionInfo != null) {
			final User user = Optional.ofNullable((User) userSessionInfo.getUser()).orElse(null);
			if (user != null) {
				userId = Optional.ofNullable((Long) user.getUserId()).orElse(null);
				username = Optional.ofNullable((String) user.getUsername()).orElse(null);
			}
		}
		final Boolean hasErrorCode = userId != null && CaptchaService.hasErrorCode(errorCode);
		captchaService.maybeRegenerateCaptchaForUser(model, session, hasErrorCode);

		// Pre-populate the Error ticket field values for display and (possibly) user submission
		final Request request = new Request.Builder()

				// Hidden input field values on the View
				.withRequestId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
				.withUserId(userId)
				.withUsername(username)
				.withRequestTypeId(requestBugfixId)
				.withRequestStatusCode(StatusCodeEnum.RED.getValue())

				// Read-Only input field values on the View
				.withErrorCode(errorCode) // Code
				.withErrorType(errorType != null ? errorType.toString() : "") // Message
				.withErrorPath(errorPath) // Path
				.withErrorIp(securityService.parseRequestOrigin(servletRequest)) // Origin
				.withRequestTimestamp(OffsetDateTime.now(ZoneOffset.UTC).toString()) // Time

				.build();

		model.addAttribute(ModelAttributeEnum.REQUEST.getValue(), request);

		return ModelAttributeEnum.ERROR_VIEW.getValue();
	}

	@RequestMapping(value = "/error", method = RequestMethod.POST)
	public String submitErrorInfo(
			@Valid @ModelAttribute("request") Request request,
			BindingResult result,
			HttpSession session,
			Model model) throws AppException {

		final UserSessionInfo userSessionInfo = (UserSessionInfo) session
				.getAttribute(UserSessionInfoService.SESSION_ATTRIBUTE_NAME);
		final User existingUser = (userSessionInfo != null) 
				? userSessionInfo.getUser() 
				: null;
		sessionManagementService.maybeInitializeFeatureFlagSessionVariable(session);
		if (existingUser == null 
				|| !featureFlagService.isFeatureFlagActiveForUser(featureFlagRequestId, session, existingUser)) {
			return ModelAttributeEnum.REDIRECT_TO.getValue() + ModelAttributeEnum.HOME_VIEW.getValue();
		}
		// Validate failure returns current View with field-level errors
		requestValidationService.beforeCreate(session, request, result);
		if (result.hasErrors()) {
			final Boolean hasErrorCode = request.getUserId() != null && CaptchaService.hasErrorCode(errorCode);
			captchaService.maybeRegenerateCaptchaForUser(model, session, hasErrorCode);
			request.setCaptcha(null);
			model.addAttribute(ModelAttributeEnum.REQUEST.getValue(), request);
			return ModelAttributeEnum.ERROR_VIEW.getValue();
		}
		// Failure during commit returns current View with page-level error
		else {
			final Boolean hasFailures = requestService.create(session, request) == null;
			return new ForwardServletRequestDetails.Builder()
					.withModel(model)
					.withHasFailures(hasFailures)
					.withAttributes(Collections.singletonMap(ModelAttributeEnum.REQUEST.getValue(), request))
					.withSuccessMessage(DisplayKeyEnum.REQUEST_SUCCESS.getValue())
					.withSuccessView(ModelAttributeEnum.ERROR_VIEW.getValue())
					.withFailureMessage(DisplayKeyEnum.REQUEST_FAILURE.getValue())
					.withFailureView(ModelAttributeEnum.ERROR_VIEW.getValue())
					.build();
		}
	}

	@Value("${request.bugfix.id}")
	private Long requestBugfixId;

	@Value("${featureflag.request.id}")
	private Long featureFlagRequestId;
}