package com.cimeliarchium.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.PayPalOrder;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.web.RedirectServletRequestDetails;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.PayPalOrderValidationService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.web.PayPalService;
import com.cimeliarchium.service.web.SessionManagementService;

@Controller
@PropertySource("classpath:/application.properties")
public class DonateController {

	private PayPalService payPalService;
	private SessionManagementService sessionManagementService;
	private PayPalOrderValidationService payPalOrderValidationService;
	private FeatureFlagService featureFlagService;
	private UserSessionInfoService userSessionInfoService;

	@Autowired
	public DonateController(PayPalService payPalService, SessionManagementService sessionManagementService,
			PayPalOrderValidationService payPalOrderValidationService, FeatureFlagService featureFlagService,
			UserSessionInfoService userSessionInfoService) {
		this.payPalService = payPalService;
		this.sessionManagementService = sessionManagementService;
		this.payPalOrderValidationService = payPalOrderValidationService;
		this.featureFlagService = featureFlagService;
		this.userSessionInfoService = userSessionInfoService;
	}

	@RequestMapping(value = "/donate", method = RequestMethod.GET)
	public String initOrder(
			@NotNull HttpSession session,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		sessionManagementService.maybeInitializeUserSessionVariables(session, request, null);
		final User user = userSessionInfoService.getCachedValue(session).getUser();
		if (!featureFlagService.isFeatureFlagActiveForUser(featureFlagDonateId, session, user)) {
			return new RedirectServletRequestDetails.Builder()
					.withRequest(request)
					.withResponse(response)
					.withHasFailures(false)
					.withSuccessView(ModelAttributeEnum.MAIN_VIEW.getValue())
					.build();
		}
		model.addAttribute(ModelAttributeEnum.PAYPAL_ORDER.getValue(), new PayPalOrder());
		// Add a descriptive message based on current status
		RedirectServletRequestDetails.addFlashMessageAsModelAttribute(model, request);
		return ModelAttributeEnum.DONATE_VIEW.getValue();
	}

	@RequestMapping(value = "/donate/process", method = RequestMethod.POST)
	public String processOrder(
			@Valid @ModelAttribute("payPalOrder") PayPalOrder payPalOrder, 
			BindingResult result,
			HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		if (!payPalOrderValidationService.beforeProcessHasErrors(payPalOrder, result)) {
			final PayPalOrder createdOrder = payPalService.init(payPalOrder, request).process();
			return new RedirectServletRequestDetails.Builder()
					.withRequest(request)
					.withResponse(response)
					.withHasFailures(false)
					.withSuccessView(createdOrder.getApprovalLink().toString())
					.build();
		} else {
			return ModelAttributeEnum.DONATE_VIEW.getValue();
		}
	}

	@RequestMapping(value = "/donate/confirm", method = RequestMethod.GET)
	public String confirmOrder(
			@NotNull @RequestParam String token,
			RedirectAttributes redirect,
			HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		// On PayPal transaction success, redirect to Donate with Confirm Message
		PayPalOrder paypalOrder = null;
		try {
			paypalOrder = payPalService.confirm(token);
			redirect.addFlashAttribute(ModelAttributeEnum.PAYPAL_ORDER_AMOUNT.getValue(), paypalOrder.getAmount());
			redirect.addFlashAttribute(ModelAttributeEnum.PAYPAL_ORDER_ID.getValue(), paypalOrder.getOrderId());
		} 
		// Otherwise, simply redirect to Donate
		catch (Exception ex) { }
		// Note that the success or failure message will be appended dynamically via donate-onchange.js
		return new RedirectServletRequestDetails.Builder()
				.withRequest(request)
				.withResponse(response)
				.withHasFailures(paypalOrder == null)
				.withFailureView("/" + ModelAttributeEnum.DONATE_VIEW.getValue())
				.withSuccessView("/" + ModelAttributeEnum.DONATE_VIEW.getValue())
				.build();
	}

	@RequestMapping(value = "/donate/submit", method = RequestMethod.GET)
	public String submitOrder(
			@NotNull HttpSession session,
			RedirectAttributes redirect,
			HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		// On PayPal transaction success, redirect to Donate with Confirm Message
		PayPalOrder paypalOrder = null;
		try {
			paypalOrder = payPalService.submit(session);
			redirect.addFlashAttribute(ModelAttributeEnum.PAYPAL_ORDER_AMOUNT.getValue(), paypalOrder.getAmount());
		} 
		// Otherwise, simply redirect to Donate
		catch (Exception ex) { }
		// Note that the success or failure message will be appended dynamically via donate-onchange.js
		return new RedirectServletRequestDetails.Builder()
				.withRequest(request)
				.withResponse(response)
				.withHasFailures(paypalOrder == null)
				.withFailureView("/" + ModelAttributeEnum.DONATE_VIEW.getValue())
				.withSuccessView("/" + ModelAttributeEnum.DONATE_VIEW.getValue())
				.build();
	}

	@Value("${featureflag.donate.id}")
	private Long featureFlagDonateId;
}