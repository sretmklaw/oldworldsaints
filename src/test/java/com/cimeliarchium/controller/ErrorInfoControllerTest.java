package com.cimeliarchium.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Collections;

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
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dao.UserSessionInfo;
import com.cimeliarchium.service.dto.UserSessionInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
@ContextConfiguration
public class ErrorInfoControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String ERROR_PATH = "/error";
	private static final String ERROR_VIEW = "/jsp/error.jsp";

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Error_thenOk() throws Exception {
		this.whenAnonymousUserRequests(ERROR_PATH)
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Test
	@Order(2)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Error_thenOk() throws Exception {
		this.whenStandardUserRequests(ERROR_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(3)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Error_thenOk() throws Exception {
		this.whenAdminUserRequests(ERROR_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(4)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_POST_Error_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(ERROR_PATH)
				.withMatcher(redirectedUrl("/"))
				.withMediaType(MediaType.TEXT_PLAIN)
				.withContent(TEST_STR_PARAM)
				.doPost()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(5)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_POST_Error_withFlagActive_withPassedValidation_thenOk() throws Exception {
		this.whenStandardUserRequests(ERROR_PATH)
				.withIsFeatureFlagActive(true)
				.withUserSessionInfo(TEST_USER)
				.withRequestPassedValidation()
				.withMediaType(MediaType.APPLICATION_JSON)
				.withContent(new Request.Builder().build().toString())
				.doPost()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(6)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_POST_Error_withFlagActive_withPassedValidation_thenOk() throws Exception {
		this.whenAdminUserRequests(ERROR_PATH)
				.withIsFeatureFlagActive(true)
				.withUserSessionInfo(ADMIN_USER)
				.withRequestPassedValidation()
				.withMediaType(MediaType.APPLICATION_JSON)
				.withContent(new Request.Builder().build().toString())
				.doPost()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(7)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_POST_Error_withFlagInactive_thenRedirect() throws Exception {
		this.whenStandardUserRequests(ERROR_PATH)
				.withMatcher(redirectedUrl("/"))
				.withMediaType(MediaType.TEXT_PLAIN)
				.withContent(TEST_STR_PARAM)
				.doPost()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(8)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_POST_Error_withFlagInactive_thenRedirect() throws Exception {
		this.whenAdminUserRequests(ERROR_PATH)
				.withMatcher(redirectedUrl("/"))
				.withMediaType(MediaType.TEXT_PLAIN)
				.withContent(TEST_STR_PARAM)
				.doPost()
				.andExpectAuthenticatedRedirect();
	}

	@Override
	public ErrorInfoControllerTestRunnerBuilder whenAnonymousUserRequests(String pathSuffix) {
		return (ErrorInfoControllerTestRunnerBuilder) new ErrorInfoControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withMatcher(forwardedUrl(ERROR_VIEW));
	}

	@Override
	public ErrorInfoControllerTestRunnerBuilder whenStandardUserRequests(String pathSuffix) {
		return (ErrorInfoControllerTestRunnerBuilder) new ErrorInfoControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR)
				.withMatcher(forwardedUrl(ERROR_VIEW));
	}

	@Override
	public ErrorInfoControllerTestRunnerBuilder whenAdminUserRequests(String pathSuffix) {
		return (ErrorInfoControllerTestRunnerBuilder) new ErrorInfoControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withMatcher(forwardedUrl(ERROR_VIEW));
	}

	private class ErrorInfoControllerTestRunnerBuilder extends ControllerTestRunner.Builder {

		private ErrorInfoControllerTestRunnerBuilder withUserSessionInfo(User user) throws AppException {
			this.withAttributes(Collections.singletonMap(
					UserSessionInfoService.SESSION_ATTRIBUTE_NAME, 
					new UserSessionInfo.Builder().withUser(user).build()));
			return this;
		}

		private ErrorInfoControllerTestRunnerBuilder withIsFeatureFlagActive(Boolean isActive) throws AppException {
			doReturn(isActive).when(mockFeatureFlagService)
				.isFeatureFlagActiveForUser(anyLong(), any(HttpSession.class), nullable(User.class));
			return this;
		}

		private ErrorInfoControllerTestRunnerBuilder withRequestPassedValidation() throws AppException {
			doNothing().when(mockRequestValidationService)
				.beforeCreate(any(HttpSession.class), nullable(Request.class), nullable(BindingResult.class));
			return this;
		}
	}
}
