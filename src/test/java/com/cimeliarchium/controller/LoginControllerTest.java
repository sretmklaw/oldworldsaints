package com.cimeliarchium.controller;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cimeliarchium.controller.ControllerTestRunner.Builder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
@ContextConfiguration
public class LoginControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String LOGIN_PATH = "/login";
	private static final String LOGIN_VIEW = "/jsp/login.jsp";
	private static final String LOGOUT_PATH = "/logout";

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_LandingPage_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests("/")
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(2)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Login_thenOk() throws Exception {
		this.whenAnonymousUserRequests(LOGIN_PATH)
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Test
	@Order(3)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Login_thenOk() throws Exception {
		this.whenStandardUserRequests(LOGIN_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(4)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Login_thenOk() throws Exception {
		this.whenAdminUserRequests(LOGIN_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(5)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Logout_thenRedirect() throws Exception {
		this.whenStandardUserRequests(LOGOUT_PATH)
				.withMatcher(redirectedUrl(LOGIN_PATH))
				.doGet()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(6)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Logout_thenRedirect() throws Exception {
		this.whenAdminUserRequests(LOGOUT_PATH)
				.withMatcher(redirectedUrl(LOGIN_PATH))
				.doGet()
				.andExpectAuthenticatedRedirect();
	}

	@Test
	@Order(7)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Logout_thenRedirect() throws Exception {
		new ControllerTestRunner.Builder()
				.withMatcher(redirectedUrl(LOGIN_PATH + "?logout"))
				.doActions(mockMvc.perform(logout()))
				.andExpectUnauthenticatedRedirect();
	}

	@Override
	public Builder whenAnonymousUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withMatcher(forwardedUrl(LOGIN_VIEW));
	}

	@Override
	public Builder whenStandardUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR)
				.withMatcher(forwardedUrl(LOGIN_VIEW));
	}

	@Override
	public Builder whenAdminUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withMatcher(forwardedUrl(LOGIN_VIEW));
	}
}
