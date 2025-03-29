package com.cimeliarchium.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Collections;

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

import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.Route;
import com.cimeliarchium.model.dao.UserSessionInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
@ContextConfiguration
public class SearchControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String SEARCH_PATH = "/search";
	private static final String COMMEMORATION_PATH = "/commemoration";
	private static final String NAME_PATH = "/name";
	private static final String NATION_PATH = "/nation";
	private static final String LOCATION_PATH = "/location";
	private static final String CENTURY_PATH = "/century";
	private static final String DAY_PATH = "/day";
	private static final String TAG_PATH = "/tag";
	private static final String STAR_PATH = "/star";
	private static final String PATRONAGE_PATH = "/patronage";
	private static final String MAP_BACKGROUND_PATH = "/map-background";
	private static final String LOCATION_MAP_POINTS_PATH = "/location-map-points";
	private static final String PATRONAGE_MAP_POINTS_PATH = "/patronage-map-points";
	private static final String MAP_ROUTES_PATH = "/map-routes";
	private static final String REFERENCE_EXCERPT_PATH = "/reference-excerpt";
	private static final String SEARCH_VIEW = "/jsp/search.jsp";
	private static final String BY_ID_PARAM = "byId";
	private static final String WITH_INSET_PARAM = "withInset";
	private static final String FOR_COMMEMORATION_PARAM = "forCommemoration";
	private static final ResultMatcher PNG_MATCHER = content().contentType(MediaType.IMAGE_PNG_VALUE);
	private static final ResultMatcher PDF_MATCHER = content().contentType(MediaType.APPLICATION_PDF_VALUE);

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchCommemoration_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + COMMEMORATION_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(2)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchCommemoration_byId_thenOk() throws Exception {
		this.whenStandardUserRequests(SEARCH_PATH + COMMEMORATION_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(3)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchCommemoration_byId_thenOk() throws Exception {
		this.whenAdminUserRequests(SEARCH_PATH + COMMEMORATION_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(4)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchName_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + NAME_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(5)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchName_byId_thenOk() throws Exception {
		this.whenStandardUserRequests(SEARCH_PATH + NAME_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(6)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchName_byId_thenOk() throws Exception {
		this.whenAdminUserRequests(SEARCH_PATH + NAME_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(7)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchNation_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + NATION_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(8)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchNation_byId_thenOk() throws Exception {
		this.whenStandardUserRequests(SEARCH_PATH + NATION_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(9)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchNation_byId_thenOk() throws Exception {
		this.whenAdminUserRequests(SEARCH_PATH + NATION_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(10)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchLocation_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + LOCATION_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(11)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchLocation_byId_thenOk() throws Exception {
		this.whenStandardUserRequests(SEARCH_PATH + LOCATION_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(12)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchLocation_byId_thenOk() throws Exception {
		this.whenAdminUserRequests(SEARCH_PATH + LOCATION_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(13)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchCentury_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + CENTURY_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(14)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchCentury_byId_thenOk() throws Exception {
		doReturn(new UserSessionInfo.Builder()
				.withCalendar(new Calendar.Builder()
						.withCalendarId(1L).build()).build())
			.when(mockUserSessionInfoService).getCachedValue(any(HttpSession.class));
		this.whenStandardUserRequests(SEARCH_PATH + CENTURY_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(15)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchCentury_byId_thenOk() throws Exception {
		doReturn(new UserSessionInfo.Builder()
				.withCalendar(new Calendar.Builder()
						.withCalendarId(1L).build()).build())
			.when(mockUserSessionInfoService).getCachedValue(any(HttpSession.class));
		this.whenAdminUserRequests(SEARCH_PATH + CENTURY_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(16)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchDay_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + DAY_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(17)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchDay_byId_thenOk() throws Exception {
		this.whenStandardUserRequests(SEARCH_PATH + DAY_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(18)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchDay_byId_thenOk() throws Exception {
		this.whenAdminUserRequests(SEARCH_PATH + DAY_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(19)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchTag_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + TAG_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(20)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchTag_byId_thenOk() throws Exception {
		this.whenStandardUserRequests(SEARCH_PATH + TAG_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(21)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchTag_byId_thenOk() throws Exception {
		this.whenAdminUserRequests(SEARCH_PATH + TAG_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(22)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchStar_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + STAR_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(23)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchStar_byId_thenOk() throws Exception {
		this.whenStandardUserRequests(SEARCH_PATH + STAR_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(24)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchStar_byId_thenOk() throws Exception {
		this.whenAdminUserRequests(SEARCH_PATH + STAR_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(25)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_SearchPatronage_byId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(SEARCH_PATH + PATRONAGE_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(26)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_SearchPatronage_byId_thenOk() throws Exception {
		this.whenStandardUserRequests(SEARCH_PATH + PATRONAGE_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(27)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_SearchPatronage_byId_thenOk() throws Exception {
		this.whenAdminUserRequests(SEARCH_PATH + PATRONAGE_PATH)
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(28)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_MapBackground_thenOk() throws Exception {
		this.whenAnonymousUserRequests(MAP_BACKGROUND_PATH)
				.withImageContent()
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Test
	@Order(29)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_MapBackground_withInset_thenOk() throws Exception {
		this.whenAnonymousUserRequests(MAP_BACKGROUND_PATH)
				.withInsetNameParam()
				.withImageContent()
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Test
	@Order(30)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_MapBackground_withInset_thenOk() throws Exception {
		this.whenStandardUserRequests(MAP_BACKGROUND_PATH)
				.withInsetNameParam()
				.withImageContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(31)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_MapBackground_withInset_thenOk() throws Exception {
		this.whenAdminUserRequests(MAP_BACKGROUND_PATH)
				.withInsetNameParam()
				.withImageContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(32)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_MapPoints_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(LOCATION_MAP_POINTS_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(33)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_MapPoints_thenOk() throws Exception {
		this.whenStandardUserRequests(LOCATION_MAP_POINTS_PATH)
				.withSessionAttrLocationMapById()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(34)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_MapPoints_thenOk() throws Exception {
		this.whenAdminUserRequests(LOCATION_MAP_POINTS_PATH)
				.withSessionAttrLocationMapById()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(35)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_MapPoints_withInset_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(LOCATION_MAP_POINTS_PATH)
				.withInsetIdParam()
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(36)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_MapPoints_withInset_thenOk() throws Exception {
		this.whenStandardUserRequests(LOCATION_MAP_POINTS_PATH)
				.withInsetIdParam()
				.withSessionAttrLocationMapById()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(37)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_MapPoints_withInset_thenOk() throws Exception {
		this.whenAdminUserRequests(LOCATION_MAP_POINTS_PATH)
				.withInsetIdParam()
				.withSessionAttrLocationMapById()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(38)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_MapRoutes_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(MAP_ROUTES_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(39)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_MapRoutes_thenOk() throws Exception {
		this.whenStandardUserRequests(MAP_ROUTES_PATH)
				.withSessionAttrRouteMapByLocationId()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(40)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_MapRoutes_thenOk() throws Exception {
		this.whenAdminUserRequests(MAP_ROUTES_PATH)
				.withSessionAttrRouteMapByLocationId()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(41)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_MapRoutes_withInset_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(MAP_ROUTES_PATH)
				.withInsetIdParam()
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(42)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_MapRoutes_withInset_thenOk() throws Exception {
		this.whenStandardUserRequests(MAP_ROUTES_PATH)
				.withInsetIdParam()
				.withSessionAttrRouteMapByLocationId()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(43)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_MapRoutes_withInset_thenOk() throws Exception {
		this.whenAdminUserRequests(MAP_ROUTES_PATH)
				.withInsetIdParam()
				.withSessionAttrRouteMapByLocationId()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(44)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_ReferenceExcerpt_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(REFERENCE_EXCERPT_PATH)
				.withForCommemorationParam()
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Ignore
	@Test
	@Order(45)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_ReferenceExcerpt_thenOk() throws Exception {
		this.whenStandardUserRequests(REFERENCE_EXCERPT_PATH)
				.withForCommemorationParam()
				.withPdfContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Ignore
	@Test
	@Order(46)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_ReferenceExcerpt_thenOk() throws Exception {
		this.whenAdminUserRequests(REFERENCE_EXCERPT_PATH)
				.withForCommemorationParam()
				.withPdfContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(47)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_PatronageMapPoints_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(PATRONAGE_MAP_POINTS_PATH)
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(48)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_PatronageMapPoints_thenOk() throws Exception {
		this.whenStandardUserRequests(PATRONAGE_MAP_POINTS_PATH)
				.withSessionAttrPatronageMapById()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(49)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_PatronageMapPoints_thenOk() throws Exception {
		this.whenAdminUserRequests(PATRONAGE_MAP_POINTS_PATH)
				.withSessionAttrPatronageMapById()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(50)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_PatronageMapPoints_withInset_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(PATRONAGE_MAP_POINTS_PATH)
				.withInsetIdParam()
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(51)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_PatronageMapPoints_withInset_thenOk() throws Exception {
		this.whenStandardUserRequests(PATRONAGE_MAP_POINTS_PATH)
				.withInsetIdParam()
				.withSessionAttrPatronageMapById()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(52)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_PatronageMapPoints_withInset_thenOk() throws Exception {
		this.whenAdminUserRequests(PATRONAGE_MAP_POINTS_PATH)
				.withInsetIdParam()
				.withSessionAttrPatronageMapById()
				.withJsonContent()
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Override
	public SearchControllerTestRunnerBuilder whenAnonymousUserRequests(String pathSuffix) {
		return (SearchControllerTestRunnerBuilder) new SearchControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withParams(Collections.singletonMap(BY_ID_PARAM, TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH));
	}

	@Override
	public SearchControllerTestRunnerBuilder whenStandardUserRequests(String pathSuffix) {
		return (SearchControllerTestRunnerBuilder) new SearchControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR)
				.withParams(Collections.singletonMap(BY_ID_PARAM, TEST_ID_PARAM))
				.withMatcher(forwardedUrl(SEARCH_VIEW));
	}

	@Override
	public SearchControllerTestRunnerBuilder whenAdminUserRequests(String pathSuffix) {
		return (SearchControllerTestRunnerBuilder) new SearchControllerTestRunnerBuilder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withParams(Collections.singletonMap(BY_ID_PARAM, TEST_ID_PARAM))
				.withMatcher(forwardedUrl(SEARCH_VIEW));
	}

	private class SearchControllerTestRunnerBuilder extends ControllerTestRunner.Builder {

		private SearchControllerTestRunnerBuilder withInsetNameParam() {
			return (SearchControllerTestRunnerBuilder) this
					.withParams(Collections.singletonMap(WITH_INSET_PARAM, TEST_STR_PARAM));
		}

		private SearchControllerTestRunnerBuilder withInsetIdParam() {
			return (SearchControllerTestRunnerBuilder) this
					.withParams(Collections.singletonMap(WITH_INSET_PARAM, TEST_ID_PARAM));
		}

		private SearchControllerTestRunnerBuilder withForCommemorationParam() {
			return (SearchControllerTestRunnerBuilder) this
					.withParams(Collections.singletonMap(FOR_COMMEMORATION_PARAM, TEST_ID_PARAM));
		}

		private SearchControllerTestRunnerBuilder withSessionAttrLocationMapById() {
			return (SearchControllerTestRunnerBuilder) this
					.withAttributes(Collections.singletonMap(
							SessionAttributeEnum.LOCATION_MAP_BY_ID.getValue(), 
							Collections.singletonMap(1L, new Location.Builder().build())));
		}

		private SearchControllerTestRunnerBuilder withSessionAttrPatronageMapById() {
			return (SearchControllerTestRunnerBuilder) this
					.withAttributes(Collections.singletonMap(
							SessionAttributeEnum.PATRONAGE_MAP_BY_ID.getValue(), 
							Collections.singletonMap(1L, new Patronage.Builder().build())));
		}

		private SearchControllerTestRunnerBuilder withSessionAttrRouteMapByLocationId() {
			return (SearchControllerTestRunnerBuilder) this
					.withAttributes(Collections.singletonMap(
							SessionAttributeEnum.ROUTE_MAP_BY_LOCATION_ID.getValue(), 
							Collections.singletonMap(1L, Collections.singletonList(new Route.Builder().build()))));
		}

		private SearchControllerTestRunnerBuilder withJsonContent() {
			return (SearchControllerTestRunnerBuilder) this
					.withMediaType(MediaType.APPLICATION_JSON)
					.withMatcher(JSON_MATCHER);
		}

		private SearchControllerTestRunnerBuilder withImageContent() {
			return (SearchControllerTestRunnerBuilder) this
					.withMediaType(MediaType.IMAGE_PNG)
					.withMatcher(PNG_MATCHER);
		}

		private SearchControllerTestRunnerBuilder withPdfContent() {
			return (SearchControllerTestRunnerBuilder) this
					.withMediaType(MediaType.APPLICATION_PDF)
					.withMatcher(PDF_MATCHER);
		}
	}
}
