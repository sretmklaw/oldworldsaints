package com.cimeliarchium.service.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.PayPalOrder;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.service.dao.UserService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties",
})
public class PayPalService extends SessionAttributeHelperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayPalService.class);

	private static final String APPROVE_LINK_REL = "approve";
	private static final String CAPTURE = "CAPTURE";
	private static final String LIVE_MODE = "LIVE";
	private static final String USD = "USD";
	private static final String DONATION = "Donation to Old World Saints";
	private static final String NO_SHIPPING = "NO_SHIPPING";

	private PayPalHttpClient payPalHttpClient;
	private PayPalOrder payPalOrder;
	private URI returnUrl;

	private SecurityService securityService;
	private UserService userService;
	private UserSessionInfoService userSessionInfoService;

	@Autowired
	public PayPalService(SecurityService securityService, UserService userService,
			UserSessionInfoService userSessionInfoService) {
		this.securityService = securityService;
		this.userService = userService;
		this.userSessionInfoService = userSessionInfoService;
	}

	public PayPalService init(PayPalOrder payPalOrder, HttpServletRequest request) {
		LOGGER.info("Calling PayPal {} to initiate new order in the amount of USD {}", 
				payPalMode,
				payPalOrder.getAmount());
		this.payPalHttpClient = new PayPalHttpClient(
				(LIVE_MODE.equals(payPalMode)) 
				? new PayPalEnvironment.Live(payPalClientId, payPalClientSecret)
		 		: new PayPalEnvironment.Sandbox(payPalClientId, payPalClientSecret));
		this.payPalOrder = payPalOrder;
		this.returnUrl = this.buildReturnUrl(request);
		return this;
	}

	/**
	 * Method used to initiate a donation request via the PayPal API.
	 * The PayPalOrder and return URL must have been initialized prior to calling this end-point.
	 * 
	 * @return
	 * @throws AppException
	 */
	public PayPalOrder process() throws AppException {
		
		super.throwIfMissing(payPalOrder, payPalServiceProcessMissingPayPalOrder);
		super.throwIfMissing(returnUrl, payPalServiceProcessMissingReturnUrl);

		final OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest()
				.requestBody(createPayPalOrderRequest());
		try {
			final HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
			final Order order = orderHttpResponse.result();
			final LinkDescription approveUri = extractApprovalLink(order);
			final URI approvalLink = URI.create(approveUri.href());
			final PayPalOrder createdPayPalOrder = payPalOrder
				.withOrderId(order.id())
				.withUsername(securityService.getAuthenticatedUserCredential())
				.withApprovalLink(approvalLink);
			LOGGER.info("Redirecting to process PayPal order ID {} for user '{}' in the amount of USD {}", 
					createdPayPalOrder.getOrderId(),
					createdPayPalOrder.getUsername(),
					createdPayPalOrder.getAmount());
		} catch (IOException ex) {
			throw new AppException(payPalServiceProcessError);
		}
		return payPalOrder;
	}

	private URI buildReturnUrl(HttpServletRequest request) {
		try {
			URI requestUri = URI.create(request.getRequestURL().toString());
			return new URI(
					requestUri.getScheme(), 
					requestUri.getUserInfo(), 
					requestUri.getHost(), 
					requestUri.getPort(),
					"/donate/confirm", null, null);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private OrderRequest createPayPalOrderRequest() {

		final OrderRequest payPalOrderRequest = new OrderRequest();
		payPalOrderRequest.checkoutPaymentIntent(CAPTURE);
		payPalOrderRequest.purchaseUnits(Arrays.asList(new PurchaseUnitRequest()
				.amountWithBreakdown(new AmountWithBreakdown()
						.currencyCode(USD)
						.value(payPalOrder.getAmount().toString()))
				.description(DONATION)));
		payPalOrderRequest.applicationContext(new ApplicationContext()
				.returnUrl(returnUrl.toString())
				.shippingPreference(NO_SHIPPING));
		return payPalOrderRequest;
	}

	private LinkDescription extractApprovalLink(Order order) {
		LinkDescription approveUri = order.links().stream()
				.filter(link -> APPROVE_LINK_REL.equals(link.rel()))
				.findFirst().orElseThrow(NoSuchElementException::new);
		return approveUri;
	}

	/**
	 * When PayPal-returned Order ID (token) matches expected value, prompt the user for confirmation.
	 * 
	 * @param token the PayPal Order ID
	 * @return payPalOrder the PayPalOrder
	 * @throws AppException 
	 */
	public PayPalOrder confirm(String token) throws AppException {

		super.throwIfMissing(payPalOrder, payPalServiceConfirmMissingPayPalOrder);

		final String orderId = payPalOrder.getOrderId();
		if (orderId.equals(token)) {
			return payPalOrder;
		} else {
			throw new AppException(payPalServiceConfirmError);
		}
	}

	/**
	 * When PayPal-returned Order ID (token) matches expected value, complete the transaction.
	 * 
	 * @param session the HttpSession
	 * @return payPalOrder the PayPalOrder
	 * @throws AppException
	 */
	public PayPalOrder submit(@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(payPalOrder, payPalServiceSubmitMissingPayPalOrder);
		final String orderId = payPalOrder.getOrderId();
		final Double orderAmount = Double.valueOf(payPalOrder.getAmount());
		final User existingUser = userService.findByUsername(payPalOrder.getUsername());
		if (userSessionInfoService.isAuthorizedRequestForCurrentUser(session, existingUser.getUsername())) {
			final OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(orderId);
			try {
				final HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
				LOGGER.info("PayPal order ID {} submitted for user '{}' in the amount USD {}: {}", 
						orderId, 
						payPalOrder.getUsername(),
						orderAmount,
						httpResponse.result().status());
				return payPalOrder;
			} catch (IOException ex) {
				throw new AppException(payPalServiceSubmitError);
			}
		} else {
			throw new AppException(payPalServiceSubmitError);
		}
	}

	@Value("${paypalservice.process.paypalorder}")
	private String payPalServiceProcessMissingPayPalOrder;

	@Value("${paypalservice.process.returnurl}")
	private String payPalServiceProcessMissingReturnUrl;

	@Value("${paypalservice.process.error}")
	private String payPalServiceProcessError;

	@Value("${paypalservice.confirm.paypalorder}")
	private String payPalServiceConfirmMissingPayPalOrder;

	@Value("${paypalservice.confirm.error}")
	private String payPalServiceConfirmError;

	@Value("${paypalservice.submit.paypalorder}")
	private String payPalServiceSubmitMissingPayPalOrder;

	@Value("${paypalservice.submit.error}")
	private String payPalServiceSubmitError;

	@Value("${paypal.mode}")
	private String payPalMode;

	@Value("${paypal.client.id}")
	private String payPalClientId;

	@Value("${paypal.client.secret}")
	private String payPalClientSecret;
}