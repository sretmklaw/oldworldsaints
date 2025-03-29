package com.cimeliarchium.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cimeliarchium.controller.ControllerTestRunner.Builder;
import com.cimeliarchium.enums.RequestFieldEnum;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Century;
import com.cimeliarchium.model.dao.Day;
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.Month;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.PatronageSubtype;
import com.cimeliarchium.model.dao.Reading;
import com.cimeliarchium.model.dao.Reference;
import com.cimeliarchium.model.dao.Region;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.SearchCriteria;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
@ContextConfiguration
@PropertySource("classpath:/application.properties")
public class DropdownControllerTest extends ControllerTestHelper implements ControllerTestIF {

	private static final String CENTURY_PATH = "/century";
	private static final String CALENDAR_PATH = "/calendar";
	private static final String MONTH_PATH = "/month";
	private static final String DAY_PATH = "/day";
	private static final String RITE_PATH = "/rite";
	private static final String REGION_PATH = "/region";
	private static final String LOCATION_PATH = "/location";
	private static final String LOCATION_INSET_PATH = "/location-inset";
	private static final String PATRONAGE_SUBTYPE_PATH = "/patronage-subtype";
	private static final String PATRONAGE_PATH = "/patronage";
	private static final String TAG_PATH = "/tag";
	private static final String READING_TITLE_PATH = "/reading-title";
	private static final String READING_PATH = "/reading";
	private static final String REFERENCE_PATH = "/reference";
	private static final String COMMEMORATION_TITLE_PATH = "/commemoration-title";

	@Test
	@Order(1)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Century_withCreedId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(CENTURY_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(2)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Century_withCreedId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(1L, new Century.Builder().build())).when(mockCenturyService)
			.getCachedValues(any(HttpSession.class));
		this.whenStandardUserRequests(CENTURY_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(3)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Century_withCreedId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(1L, new Century.Builder().build())).when(mockCenturyService)
			.getCachedValues(any(HttpSession.class));
		this.whenAdminUserRequests(CENTURY_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(4)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Calendar_withCreedId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(CALENDAR_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(5)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Calendar_withCreedId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(new Calendar.Builder().build(), Arrays.asList(new Month.Builder().build())))
			.when(mockCalendarMonthDayService).getCachedValuesByCalendar(any(HttpSession.class));
		this.whenStandardUserRequests(CALENDAR_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(6)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Calendar_withCreedId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(new Calendar.Builder().build(), Arrays.asList(new Month.Builder().build())))
			.when(mockCalendarMonthDayService).getCachedValuesByCalendar(any(HttpSession.class));
		this.whenAdminUserRequests(CALENDAR_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(7)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Month_withCalendarId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(MONTH_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CALENDAR_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(8)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Month_withCalendarId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(new Month.Builder().build(), Arrays.asList(new Day.Builder().build())))
			.when(mockCalendarMonthDayService).getCachedValuesByMonth(any(HttpSession.class));
		this.whenStandardUserRequests(MONTH_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CALENDAR_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(9)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Month_withCalendarId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(new Month.Builder().build(), Arrays.asList(new Day.Builder().build())))
			.when(mockCalendarMonthDayService).getCachedValuesByMonth(any(HttpSession.class));
		this.whenAdminUserRequests(MONTH_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CALENDAR_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(10)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Day_withMonthId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(DAY_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.MONTH_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(11)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Day_withMonthId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(1L, new Day.Builder().build())).when(mockCalendarMonthDayService)
			.getCachedValuesById(any(HttpSession.class));
		this.whenStandardUserRequests(DAY_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.MONTH_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(12)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Day_withMonthId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(1L, new Day.Builder().build())).when(mockCalendarMonthDayService)
			.getCachedValuesById(any(HttpSession.class));
		this.whenAdminUserRequests(DAY_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.MONTH_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(13)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Rite_withCalendarId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Rite.Builder().build())).when(mockCalendarRiteService)
			.lookupById(anyLong());
		this.whenAnonymousUserRequests(RITE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CALENDAR_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Test
	@Order(14)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Rite_withCalendarId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Rite.Builder().build())).when(mockCalendarRiteService)
			.lookupById(anyLong());
		this.whenStandardUserRequests(RITE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CALENDAR_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(15)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Rite_withCalendarId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Rite.Builder().build())).when(mockCalendarRiteService)
			.lookupById(anyLong());
		this.whenAdminUserRequests(RITE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CALENDAR_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(16)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Region_withNationId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Region.Builder().build())).when(mockNationRegionService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenAnonymousUserRequests(REGION_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectUnauthenticatedOk();
	}

	@Test
	@Order(17)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Region_withNationId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Region.Builder().build())).when(mockNationRegionService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenStandardUserRequests(REGION_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(18)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Region_withNationId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Region.Builder().build())).when(mockNationRegionService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenAdminUserRequests(REGION_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(19)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Location_withNationId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(LOCATION_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(20)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Location_withNationId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Location.Builder().build())).when(mockNationLocationService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenStandardUserRequests(LOCATION_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(21)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Location_withNationId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Location.Builder().build())).when(mockNationLocationService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenAdminUserRequests(LOCATION_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(22)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_LocationInset_withNationId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(LOCATION_INSET_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(23)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_LocationInset_withNationId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Location.Builder().build())).when(mockNationLocationService)
			.lookupInsetLocationsById(any(HttpSession.class), anyLong(), anyBoolean());
		this.whenStandardUserRequests(LOCATION_INSET_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(24)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_LocationInset_withNationId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Location.Builder().build())).when(mockNationLocationService)
			.lookupInsetLocationsById(any(HttpSession.class), anyLong(), anyBoolean());
		this.whenAdminUserRequests(LOCATION_INSET_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.NATION_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(25)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_PatronageSubtype_withPatronageTypeId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(PATRONAGE_SUBTYPE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.PATRONAGE_TYPE_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(26)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_PatronageSubtype_withPatronageTypeId_thenOk() throws Exception {
		doReturn(Arrays.asList(new PatronageSubtype.Builder().build())).when(mockPatronageTypeService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenStandardUserRequests(PATRONAGE_SUBTYPE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.PATRONAGE_TYPE_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(27)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_PatronageSubtype_withPatronageTypeId_thenOk() throws Exception {
		doReturn(Arrays.asList(new PatronageSubtype.Builder().build())).when(mockPatronageTypeService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenAdminUserRequests(PATRONAGE_SUBTYPE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.PATRONAGE_TYPE_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(28)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Patronage_withPatronageSubtypeId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(PATRONAGE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.PATRONAGE_SUBTYPE_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(29)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Patronage_withPatronageSubtypeId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Patronage.Builder().build())).when(mockPatronageSubtypeService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenStandardUserRequests(PATRONAGE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.PATRONAGE_SUBTYPE_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(30)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Patronage_withPatronageSubtypeId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Patronage.Builder().build())).when(mockPatronageSubtypeService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenAdminUserRequests(PATRONAGE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.PATRONAGE_SUBTYPE_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(31)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Tag_withCreedId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(TAG_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(32)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Tag_withCreedId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Patronage.Builder().build())).when(mockPatronageSubtypeService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenStandardUserRequests(TAG_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(33)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Tag_withCreedId_thenOk() throws Exception {
		doReturn(Arrays.asList(new Patronage.Builder().build())).when(mockPatronageSubtypeService)
			.lookupById(any(HttpSession.class), anyLong());
		this.whenAdminUserRequests(TAG_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(34)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_ReadingTitle_withCreedId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(READING_TITLE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(35)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_ReadingTitle_withCreedId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(TEST_STR_PARAM, TEST_STR_PARAM)).when(mockReadingService)
			.lookupTitlesByCreedId(anyLong());
		this.whenStandardUserRequests(READING_TITLE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(36)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_ReadingTitle_withCreedId_thenOk() throws Exception {
		doReturn(Collections.singletonMap(TEST_STR_PARAM, TEST_STR_PARAM)).when(mockReadingService)
			.lookupTitlesByCreedId(anyLong());
		this.whenAdminUserRequests(READING_TITLE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(37)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Reading_withTitle_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(READING_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.READING_TITLE.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(38)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Reading_withTitle_thenOk() throws Exception {
		doReturn(Arrays.asList(new Reading.Builder().build())).when(mockReadingService)
			.lookupByTitle(any(HttpSession.class), anyString());
		this.whenStandardUserRequests(READING_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.READING_TITLE.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(39)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Reading_withTitle_thenOk() throws Exception {
		doReturn(Arrays.asList(new Reading.Builder().build())).when(mockReadingService)
			.lookupByTitle(any(HttpSession.class), anyString());
		this.whenAdminUserRequests(READING_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.READING_TITLE.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(40)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_Reference_withTitle_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(REFERENCE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.REFERENCE_TITLE.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(41)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_Reference_withTitle_thenOk() throws Exception {
		doReturn(Arrays.asList(new Reference.Builder().build())).when(mockReferenceService)
			.lookupByTitle(any(HttpSession.class), anyString());
		this.whenStandardUserRequests(REFERENCE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.REFERENCE_TITLE.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(42)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_Reference_withTitle_thenOk() throws Exception {
		doReturn(Arrays.asList(new Reference.Builder().build())).when(mockReferenceService)
			.lookupByTitle(any(HttpSession.class), anyString());
		this.whenAdminUserRequests(REFERENCE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.REFERENCE_TITLE.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(43)
	@Tag("RunAsAnonymousUser")
	public void whenAnonymousUser_GET_CommemorationTitle_withCreedId_thenRedirect() throws Exception {
		this.whenAnonymousUserRequests(COMMEMORATION_TITLE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.withMatcher(redirectedUrl(testPath + LOGIN_PATH))
				.doGet()
				.andExpectUnauthenticatedRedirect();
	}

	@Test
	@Order(44)
	@Tag("RunAsStandardUser")
	public void whenStandardUser_GET_CommemorationTitle_withCreedId_thenOk() throws Exception {
		doReturn(Arrays.asList(new SearchCriteria())).when(mockCommemorationTitleService)
			.lookupById(anyLong());
		this.whenStandardUserRequests(COMMEMORATION_TITLE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Test
	@Order(45)
	@Tag("RunAsAdminUser")
	public void whenAdminUser_GET_CommemorationTitle_withCreedId_thenOk() throws Exception {
		doReturn(Arrays.asList(new SearchCriteria())).when(mockCommemorationTitleService)
			.lookupById(anyLong());
		this.whenAdminUserRequests(COMMEMORATION_TITLE_PATH)
				.withParams(Collections.singletonMap(RequestFieldEnum.CREED_ID.getFieldName(), TEST_ID_PARAM))
				.doGet()
				.andExpectAuthenticatedOk();
	}

	@Override
	public Builder whenAnonymousUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(anonymous())
				.withContent(TEST_ID_PARAM)
				.withMatcher(JSON_MATCHER);
	}

	@Override
	public Builder whenStandardUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(TEST_POST_PROCESSOR)
				.withContent(TEST_ID_PARAM)
				.withMatcher(JSON_MATCHER);
	}

	@Override
	public Builder whenAdminUserRequests(String pathSuffix) {
		return new ControllerTestRunner.Builder()
				.withMockMvc(mockMvc)
				.withPath(testPath + pathSuffix)
				.withProcessor(ADMIN_POST_PROCESSOR)
				.withContent(TEST_ID_PARAM)
				.withMatcher(JSON_MATCHER);
	}
}
