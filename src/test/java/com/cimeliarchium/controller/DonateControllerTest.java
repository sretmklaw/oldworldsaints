package com.cimeliarchium.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.net.URI;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.PayPalOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
@ContextConfiguration
public class DonateControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String LOGIN_PATH = "/login";
	private static final String DONATE_PATH = "/donate";
	private static final String PROCESS_PATH = "/process";
	private static final String CONFIRM_PATH = "/confirm";
	private static final String SUBMIT_PATH = "/submit";
	private static final String MAIN_PATH = "main";
	private static final String DONATE_VIEW = "/jsp/donate.jsp";
	private static final String TOKEN = "token";
	private static final PayPalOrder TEST_PAYPAL_ORDER = new PayPalOrder().withApprovalLink(URI.create("test"));

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Donate_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(DONATE_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(2)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Donate_withFlagActive_thenOk() throws Exception {
		super.withActiveFeatureFlagOrAdmin(true);
		super.withAdminUser(false);
		this.whenAdminUserRequests(DONATE_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(3)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Donate_withFlagDisabled_thenRedirect() throws Exception {
		super.withActiveFeatureFlagOrAdmin(false);
		super.withAdminUser(false);
		this.whenStandardUserRequests(DONATE_PATH)
				.withMatcher(redirectedUrl(MAIN_PATH))
				.doGet()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(4)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Donate_thenOk() throws Exception {
		super.withActiveFeatureFlagOrAdmin(true);
		super.withAdminUser(true);
		this.whenAdminUserRequests(DONATE_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(5)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_POST_DonateProcess_withValidPayPalOrder_thenRedirect() throws Exception {
		this.whenStandardUserRequests(DONATE_PATH + PROCESS_PATH)
				.withValidPayPalOrder(TEST_PAYPAL_ORDER)
				.withMatcher(redirectedUrl(TEST_PAYPAL_ORDER.getApprovalLink().toString()))
				.doPost()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(6)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_POST_DonateProcess_withInvalidPayPalOrder_thenOk() throws Exception {
		this.whenStandardUserRequests(DONATE_PATH + PROCESS_PATH)
				.withValidPayPalOrder(null)
				.doPost()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(7)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_POST_DonateProcess_withValidPayPalOrder_thenRedirect() throws Exception {
		this.whenAdminUserRequests(DONATE_PATH + PROCESS_PATH)
				.withValidPayPalOrder(TEST_PAYPAL_ORDER)
				.withMatcher(redirectedUrl(TEST_PAYPAL_ORDER.getApprovalLink().toString()))
				.doPost()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(8)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_POST_DonateProcess_withInvalidPayPalOrder_thenOk() throws Exception {
		this.whenAdminUserRequests(DONATE_PATH + PROCESS_PATH)
				.withValidPayPalOrder(null)
				.doPost()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(9)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_DonateConfirm_thenRedirect() throws Exception {
		this.whenStandardUserRequests(DONATE_PATH + CONFIRM_PATH)
				.withPayPalConfirmOrSubmit(TEST_PAYPAL_ORDER)
				.withToken()
				.withMatcher(redirectedUrl(DONATE_PATH))
				.doGet()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(10)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_DonateConfirm_thenRedirect() throws Exception {
		this.whenAdminUserRequests(DONATE_PATH + CONFIRM_PATH)
				.withPayPalConfirmOrSubmit(TEST_PAYPAL_ORDER)
				.withToken()
				.withMatcher(redirectedUrl(DONATE_PATH))
				.doGet()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(11)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_DonateSubmit_thenRedirect() throws Exception {
		this.whenStandardUserRequests(DONATE_PATH + SUBMIT_PATH)
				.withPayPalConfirmOrSubmit(TEST_PAYPAL_ORDER)
				.withMatcher(redirectedUrl(DONATE_PATH))
				.doGet()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(12)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_DonateSubmit_thenRedirect() throws Exception {
		this.whenAdminUserRequests(DONATE_PATH + SUBMIT_PATH)
				.withPayPalConfirmOrSubmit(TEST_PAYPAL_ORDER)
				.withMatcher(redirectedUrl(DONATE_PATH))
				.doGet()
				.andExpectAuthenticatedRedirect();
	}

	@Override
	public DonateControllerTestRunnerBuilder whenAnonymousUserRequests(String pathSuffix) {
		return (DonateControllerTestRunnerBuilder) new DonateControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH));
	}

	@Override
	public DonateControllerTestRunnerBuilder whenStandardUserRequests(String pathSuffix) {
		return (DonateControllerTestRunnerBuilder) new DonateControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR)
				.withMatcher(forwardedUrl(DONATE_VIEW));
	}

	@Override
	public DonateControllerTestRunnerBuilder whenAdminUserRequests(String pathSuffix) {
		return (DonateControllerTestRunnerBuilder) new DonateControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withMatcher(forwardedUrl(DONATE_VIEW));
	}

	private class DonateControllerTestRunnerBuilder extends ControllerTestRunner.Builder {

		private DonateControllerTestRunnerBuilder withToken() {
			return (DonateControllerTestRunnerBuilder) this
					.withParams(Collections.singletonMap(TOKEN, TOKEN));
		}

		private DonateControllerTestRunnerBuilder withJsonContent() {
			return (DonateControllerTestRunnerBuilder) this
					.withMediaType(MediaType.APPLICATION_JSON)
					.withContent(new PayPalOrder().toString());
		}

		private DonateControllerTestRunnerBuilder withValidPayPalOrder(
				PayPalOrder testPayPalOrder) throws AppException {

			doReturn(testPayPalOrder == null).when(mockPayPalOrderValidationService)
				.beforeProcessHasErrors(any(PayPalOrder.class), any(BindingResult.class));
			if (testPayPalOrder != null) {
				doReturn(mockPayPalService).when(mockPayPalService)
					.init(any(PayPalOrder.class), any(HttpServletRequest.class));
				doReturn(testPayPalOrder).when(mockPayPalService)
					.process();
			}
			return this.withJsonContent();
		}

		private DonateControllerTestRunnerBuilder withPayPalConfirmOrSubmit(
				PayPalOrder testPayPalOrder) throws AppException {

			doReturn(testPayPalOrder).when(mockPayPalService)
				.confirm(anyString());
			doReturn(testPayPalOrder).when(mockPayPalService)
				.submit(any(HttpSession.class));
			return this;
		}
	}
}