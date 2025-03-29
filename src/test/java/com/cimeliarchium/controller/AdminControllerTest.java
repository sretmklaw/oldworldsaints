package com.cimeliarchium.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cimeliarchium.exception.AppException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
@ContextConfiguration
public class AdminControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String ADMIN_PATH = "/admin";
	private static final String ADMIN_VIEW = "/jsp/admin.jsp";
	private static final String UPDATE_USER_PATH = "/update-users";
	private static final String UPDATE_REQS_PATH = "/update-reqs";
	private static final String UPDATE_FLAGS_PATH = "/update-flags";
	private static final String EXPORT_DIFFS_PATH = "/export-diffs";
	private static final String UPDATE_HISTORY_PATH = "/update-history";
	private static final String BY_IDS_PARAM = "byIds";
	private static final String WITH_BOOLEAN_PARAM = "withBoolean";
	private static final String WITH_STATUS_PARAM = "withStatus";

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Admin_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(ADMIN_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(2)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Admin_thenForbidden() throws Exception {
		super.withAdminUser(false);
		this.whenStandardUserRequests(ADMIN_PATH)
				.doGet()
				.andExpectAuthenticatedForbidden();
	}

	@Test
	@Order(3)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Admin_thenOk() throws Exception {
		super.withAdminUser(true);
		this.whenAdminUserRequests(ADMIN_PATH)
				.withMatcher(forwardedUrl(ADMIN_VIEW))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(4)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_AdminUpdateUsers_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(ADMIN_PATH + UPDATE_USER_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(5)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_AdminUpdateUsers_thenForbidden() throws Exception {
		super.withAdminUser(false);
		this.whenStandardUserRequests(ADMIN_PATH + UPDATE_USER_PATH)
				.withUpdatedUsers()
				.doGet()
				.andExpectAuthenticatedForbidden();
	}

	@Test
	@Order(6)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_AdminUpdateUsers_thenOk() throws Exception {
		super.withAdminUser(true);
		this.whenAdminUserRequests(ADMIN_PATH + UPDATE_USER_PATH)
				.withUpdatedUsers()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(7)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_AdminUpdateRequests_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(ADMIN_PATH + UPDATE_REQS_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(8)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_AdminUpdateRequests_thenForbidden() throws Exception {
		super.withAdminUser(false);
		this.whenStandardUserRequests(ADMIN_PATH + UPDATE_REQS_PATH)
				.withClosedRequests()
				.doGet()
				.andExpectAuthenticatedForbidden();
	}

	@Test
	@Order(9)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_AdminUpdateRequests_thenOk() throws Exception {
		super.withAdminUser(true);
		this.whenAdminUserRequests(ADMIN_PATH + UPDATE_REQS_PATH)
				.withClosedRequests()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(10)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_AdminUpdateFlags_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(ADMIN_PATH + UPDATE_FLAGS_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(11)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_AdminUpdateFlags_thenForbidden() throws Exception {
		super.withAdminUser(false);
		this.whenStandardUserRequests(ADMIN_PATH + UPDATE_FLAGS_PATH)
				.withUpdatedFlags()
				.doGet()
				.andExpectAuthenticatedForbidden();
	}

	@Test
	@Order(12)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_AdminUpdateFlags_thenOk() throws Exception {
		super.withAdminUser(true);
		this.whenAdminUserRequests(ADMIN_PATH + UPDATE_FLAGS_PATH)
				.withUpdatedFlags()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(13)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_AdminExportDiffs_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(ADMIN_PATH + EXPORT_DIFFS_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(17)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_AdminUpdateHistory_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(ADMIN_PATH + UPDATE_HISTORY_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Override
	public AdminControllerTestRunnerBuilder whenAnonymousUserRequests(String pathSuffix) {
		return (AdminControllerTestRunnerBuilder) new AdminControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH));
	}

	@Override
	public AdminControllerTestRunnerBuilder whenStandardUserRequests(String pathSuffix) {
		return (AdminControllerTestRunnerBuilder) new AdminControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR);
	}

	@Override
	public AdminControllerTestRunnerBuilder whenAdminUserRequests(String pathSuffix) {

		return (AdminControllerTestRunnerBuilder) new AdminControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withContent(TEST_STR_PARAM)
				.withMatcher(JSON_MATCHER);
	}

	private class AdminControllerTestRunnerBuilder extends ControllerTestRunner.Builder {

		@SuppressWarnings("unchecked")
		private AdminControllerTestRunnerBuilder withUpdatedUsers() throws AppException {
			doReturn(Collections.singletonList(Long.valueOf(TEST_ID_PARAM)))
				.when(mockUserService)
				.updateUsersStatus(any(List.class), anyString(), any(HttpSession.class));
			Map<String,String> params = new HashMap<>();
			params.put(BY_IDS_PARAM, TEST_ID_PARAM);
			params.put(WITH_STATUS_PARAM, TEST_STR_PARAM);
			this.withParams(params);
			return this;
		}

		@SuppressWarnings("unchecked")
		private AdminControllerTestRunnerBuilder withClosedRequests() throws AppException {
			doReturn(Collections.singletonList(Long.valueOf(TEST_ID_PARAM)))
				.when(mockRequestService)
				.closeRequests(any(List.class), any(HttpSession.class));
			this.withParams(Collections.singletonMap(BY_IDS_PARAM, TEST_ID_PARAM));
			return this;
		}

		@SuppressWarnings("unchecked")
		private AdminControllerTestRunnerBuilder withUpdatedFlags() throws AppException {
			doReturn(Collections.singletonList(Long.valueOf(TEST_ID_PARAM)))
				.when(mockFeatureFlagService)
				.updateFlagsStatus(any(List.class), anyString(), anyString(), any(HttpSession.class));
			Map<String,String> params = new HashMap<>();
			params.put(BY_IDS_PARAM, TEST_ID_PARAM);
			params.put(WITH_BOOLEAN_PARAM, TEST_STR_PARAM);
			this.withParams(params);
			return this;
		}
	}
}
