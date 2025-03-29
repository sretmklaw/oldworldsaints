package com.cimeliarchium.controller;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
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
public class MainControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String LOGIN_PATH = "/login";
	private static final String MAIN_PATH = "/main";
	private static final String MAIN_VIEW = "/jsp/main.jsp";

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Main_thenRedirected() throws Exception {
		this.whenAnonymousUserRequests(MAIN_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(2)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Main_thenOk() throws Exception {
		this.whenStandardUserRequests(MAIN_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(3)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Main_thenOk() throws Exception {
		this.whenAdminUserRequests(MAIN_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Override
	public Builder whenAnonymousUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH));
	}

	@Override
	public Builder whenStandardUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR)
				.withMatcher(forwardedUrl(MAIN_VIEW));
	}

	@Override
	public Builder whenAdminUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withMatcher(forwardedUrl(MAIN_VIEW));
	}
}
