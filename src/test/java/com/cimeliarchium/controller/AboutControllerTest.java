package com.cimeliarchium.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.Collections;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
@ContextConfiguration
public class AboutControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String ABOUT_PATH = "/about";
	private static final String ABOUT_VIEW = "/jsp/about.jsp";
	private static final String BY_TEXT_PARAM = "byText";
	private static final String ACTIVE_PARAGRAPH_ATTR = "activeParagraph";

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_About_thenOk() throws Exception {
		this.whenAnonymousUserRequests(ABOUT_PATH)
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Test
	@Order(2)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_About_thenOk() throws Exception {
		this.whenStandardUserRequests(ABOUT_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(3)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_About_thenOk() throws Exception {
		this.whenAdminUserRequests(ABOUT_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(4)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_About_withParagraph_thenOk() throws Exception {
		this.whenAnonymousUserRequests(ABOUT_PATH)
				.withValidParagraphParameter()
				.doGet()
				.andExpectUnauthenticatedOk()
				.andExpectModelContainsAttribute(ACTIVE_PARAGRAPH_ATTR);
	}

	@Test
	@Order(5)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_About_withParagraph_thenOk() throws Exception {
		this.whenStandardUserRequests(ABOUT_PATH)
				.withValidParagraphParameter()
				.doGet()
				.andExpectAuthenticatedOk()
				.andExpectModelContainsAttribute(ACTIVE_PARAGRAPH_ATTR);
	}

	@Test
	@Order(6)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_About_withParagraph_thenOk() throws Exception {
		this.whenAdminUserRequests(ABOUT_PATH)
				.withValidParagraphParameter()
				.doGet()
				.andExpectAuthenticatedOk()
				.andExpectModelContainsAttribute(ACTIVE_PARAGRAPH_ATTR);
	}

	@Override
	public AboutControllerTestRunnerBuilder whenAnonymousUserRequests(String pathSuffix) {
		return (AboutControllerTestRunnerBuilder) new AboutControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withMatcher(forwardedUrl(ABOUT_VIEW));
	}

	@Override
	public AboutControllerTestRunnerBuilder whenStandardUserRequests(String pathSuffix) {
		return (AboutControllerTestRunnerBuilder) new AboutControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR)
				.withMatcher(forwardedUrl(ABOUT_VIEW));
	}

	@Override
	public AboutControllerTestRunnerBuilder whenAdminUserRequests(String pathSuffix) {
		return (AboutControllerTestRunnerBuilder) new AboutControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withMatcher(forwardedUrl(ABOUT_VIEW));
	}

	private class AboutControllerTestRunnerBuilder extends ControllerTestRunner.Builder {

		private AboutControllerTestRunnerBuilder withValidParagraphParameter() {
			doReturn(true).when(mockSearchCriteriaValidationService)
				.isValidText(anyString());
			this.withParams(Collections.singletonMap(BY_TEXT_PARAM, TEST_STR_PARAM));
			return this;
		}
	}
}
