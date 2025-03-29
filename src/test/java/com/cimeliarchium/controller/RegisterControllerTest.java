package com.cimeliarchium.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import javax.servlet.http.HttpSession;

import org.junit.Ignore;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.BindingResult;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.model.dao.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
@ContextConfiguration
public class RegisterControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String CAPTCHA_PATH = "/captcha";
	private static final String REGISTER_PATH = "/register";
	private static final String REGISTER_VIEW = "/jsp/register.jsp";
	private static final String FORM_HEADER_UPDATE = "?formHeader=update";
	private static final ResultMatcher CAPTCHA_MATCHER = content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1"));

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Register_withFlagActive_thenOk() throws Exception {
		this.whenAnonymousUserRequests(REGISTER_PATH)
				.withIsAuthorized(false)
				.withIsFeatureFlagActive(true)
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Test
	@Order(2)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Register_withFlagDisabled_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(REGISTER_PATH)
				.withIsAuthorized(false)
				.withIsFeatureFlagActive(false)
				.withMatcher(redirectedUrl("/"))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(3)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Register_withFlagActive_thenOk() throws Exception {
		this.whenStandardUserRequests(REGISTER_PATH)
				.withIsAuthorized(true)
				.withIsFeatureFlagActive(true)
				.withAuthenticatedUserCredential(TEST_USERNAME)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(4)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Register_withFlagDisabled_thenRedirect() throws Exception {
		this.whenStandardUserRequests(REGISTER_PATH)
				.withIsAuthorized(true)
				.withIsFeatureFlagActive(false)
				.withAuthenticatedUserCredential(TEST_USERNAME)
				.withMatcher(redirectedUrl("/"))
				.doGet()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(5)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Register_withFlagActive_thenOk() throws Exception {
		this.whenAdminUserRequests(REGISTER_PATH)
				.withIsAuthorized(true)
				.withIsFeatureFlagActive(true)
				.withAuthenticatedUserCredential(ADMIN_USERNAME)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(6)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Register_withFlagDisabled_thenOk() throws Exception {
		this.whenAdminUserRequests(REGISTER_PATH)
				.withIsAuthorized(true)
				.withIsFeatureFlagActive(true)
				.withAuthenticatedUserCredential(ADMIN_USERNAME)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(7)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_POST_Register_withFlagActive_withPassedValidation_thenRedirect() throws Exception {
		this.whenStandardUserRequests(REGISTER_PATH)
				.withIsAuthorized(true)
				.withIsFeatureFlagActive(true)
				.withAuthenticatedUserCredential(TEST_USERNAME)
				.withRequestPassedValidation()
				.withMediaType(MediaType.APPLICATION_JSON)
				.withContent(new Request.Builder().build().toString())
				.withMatcher(redirectedUrl(LOGIN_PATH+FORM_HEADER_UPDATE))
				.doPost()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(8)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_POST_Register_withFlagActive_withPassedValidation_thenRedirect() throws Exception {
		this.whenStandardUserRequests(REGISTER_PATH)
				.withIsAuthorized(true)
				.withIsFeatureFlagActive(true)
				.withAuthenticatedUserCredential(ADMIN_USERNAME)
				.withRequestPassedValidation()
				.withMediaType(MediaType.APPLICATION_JSON)
				.withContent(new Request.Builder().build().toString())
				.withMatcher(redirectedUrl(LOGIN_PATH+FORM_HEADER_UPDATE))
				.doPost()
				.andExpectAuthenticatedRedirect();
	}

	@Ignore //FIXME
	@Test
	@Order(9)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Captcha_thenOk() throws Exception {
		this.whenAnonymousUserRequests(CAPTCHA_PATH)
				.withMatcher(CAPTCHA_MATCHER)
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Ignore //FIXME
	@Test
	@Order(10)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Captcha_thenOk() throws Exception {
		this.whenStandardUserRequests(CAPTCHA_PATH)
				.withMatcher(CAPTCHA_MATCHER)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Ignore //FIXME
	@Test
	@Order(11)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Captcha_thenOk() throws Exception {
		this.whenStandardUserRequests(CAPTCHA_PATH)
				.withMatcher(CAPTCHA_MATCHER)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Override
	public RegisterControllerTestRunnerBuilder whenAnonymousUserRequests(String pathSuffix) {
		return (RegisterControllerTestRunnerBuilder) new RegisterControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withMatcher(forwardedUrl(REGISTER_VIEW));
	}

	@Override
	public RegisterControllerTestRunnerBuilder whenStandardUserRequests(String pathSuffix) {
		return (RegisterControllerTestRunnerBuilder) new RegisterControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR)
				.withMatcher(forwardedUrl(REGISTER_VIEW));
	}

	@Override
	public RegisterControllerTestRunnerBuilder whenAdminUserRequests(String pathSuffix) {
		return (RegisterControllerTestRunnerBuilder) new RegisterControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withMatcher(forwardedUrl(REGISTER_VIEW));
	}

	private class RegisterControllerTestRunnerBuilder extends ControllerTestRunner.Builder {

		private RegisterControllerTestRunnerBuilder withIsAuthorized(Boolean isAuthorized) throws AppException {
			doReturn(isAuthorized).when(mockUserSessionInfoService)
				.isAuthorizedRequestForCurrentUser(any(HttpSession.class), nullable(String.class));
			return this;
		}

		private RegisterControllerTestRunnerBuilder withIsFeatureFlagActive(Boolean isActive) throws AppException {
			doReturn(isActive).when(mockFeatureFlagService)
				.isFeatureFlagActiveForUser(anyLong(), any(HttpSession.class), nullable(User.class));
			return this;
		}

		private RegisterControllerTestRunnerBuilder withAuthenticatedUserCredential(String username) {
			doReturn(username).when(mockSecurityService)
				.getAuthenticatedUserCredential();
			return this;
		}

		private RegisterControllerTestRunnerBuilder withRequestPassedValidation() throws AppException {
			doNothing().when(mockUserValidationService)
				.beforeUpdate(any(HttpSession.class), nullable(Request.class), nullable(BindingResult.class));
			return this;
		}

	}
}
