package com.cimeliarchium.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dao.UserSessionInfo;
import com.cimeliarchium.service.ApplicationReadyEventListenerService;
import com.cimeliarchium.service.dao.CalendarAltReasonService;
import com.cimeliarchium.service.dao.CalendarAltTypeService;
import com.cimeliarchium.service.dao.CenturyService;
import com.cimeliarchium.service.dao.CommemorationService;
import com.cimeliarchium.service.dao.CommemorationTitleService;
import com.cimeliarchium.service.dao.CommemorationTypeService;
import com.cimeliarchium.service.dao.CreedService;
import com.cimeliarchium.service.dao.DayService;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.InsetService;
import com.cimeliarchium.service.dao.LocationService;
import com.cimeliarchium.service.dao.LocationTypeService;
import com.cimeliarchium.service.dao.LunarPhaseService;
import com.cimeliarchium.service.dao.NationService;
import com.cimeliarchium.service.dao.PatronageService;
import com.cimeliarchium.service.dao.PayPalOrderValidationService;
import com.cimeliarchium.service.dao.ReadingService;
import com.cimeliarchium.service.dao.ReferenceService;
import com.cimeliarchium.service.dao.RequestService;
import com.cimeliarchium.service.dao.RequestValidationService;
import com.cimeliarchium.service.dao.RouteService;
import com.cimeliarchium.service.dao.SearchCriteriaValidationService;
import com.cimeliarchium.service.dao.TagService;
import com.cimeliarchium.service.dao.UserService;
import com.cimeliarchium.service.dao.UserValidationService;
import com.cimeliarchium.service.dto.CalendarMonthDayService;
import com.cimeliarchium.service.dto.CalendarRiteService;
import com.cimeliarchium.service.dto.CycleDayLiturgyTypeService;
import com.cimeliarchium.service.dto.DayCommemorationService;
import com.cimeliarchium.service.dto.HourReadingService;
import com.cimeliarchium.service.dto.NationLocationService;
import com.cimeliarchium.service.dto.NationRegionService;
import com.cimeliarchium.service.dto.PatronageSubtypeService;
import com.cimeliarchium.service.dto.PatronageTypeService;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.web.CaptchaService;
import com.cimeliarchium.service.web.LoginFailureService;
import com.cimeliarchium.service.web.PayPalService;
import com.cimeliarchium.service.web.PdfService;
import com.cimeliarchium.service.web.SecurityService;
import com.cimeliarchium.service.web.SessionManagementService;

@Profile("controller-test")
@Configuration
public class ControllerTestHelper {

	private static final Long getRandomId() {
		return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
	}
	private static final Long CALENDAR_ALL_ID = 1L;
	private static final Long RITE_ALL_ID = 1L;
	
	protected static final String TEST_USERNAME = "test-user";
	protected static final User TEST_USER = new User.Builder()
			.withUserId(getRandomId())
			.withUsername(TEST_USERNAME)
			.withCalendarId(CALENDAR_ALL_ID)
			.withRiteId(RITE_ALL_ID)
			.withHasAdminRole(false)
			.build();
	protected static final String ADMIN_USERNAME = "admin-user";
	protected static final User ADMIN_USER = new User.Builder()
			.withUserId(getRandomId())
			.withUsername(ADMIN_USERNAME)
			.withCalendarId(CALENDAR_ALL_ID)
			.withRiteId(RITE_ALL_ID)
			.withHasAdminRole(true)
			.build();
	
	protected static final RequestPostProcessor TEST_POST_PROCESSOR = user(TEST_USERNAME)
			.roles("public").authorities(new SimpleGrantedAuthority("public"));
	protected static final RequestPostProcessor ADMIN_POST_PROCESSOR = user(ADMIN_USERNAME)
			.roles("admin").authorities(new SimpleGrantedAuthority("admin"));
	protected static final String TEST_ID_PARAM = "1";
	protected static final String TEST_STR_PARAM = "test";
	protected static final String LOGIN_PATH = "/login";
	protected static final String MAIN_PATH = "/main";
	protected static final ResultMatcher JSON_MATCHER = content().contentType(MediaType.APPLICATION_JSON);


	@Autowired private WebApplicationContext context;

	@LocalServerPort private int port;

	@MockBean protected ApplicationReadyEventListenerService mockApplicationReadyEventListenerService;
	@MockBean protected BCryptPasswordEncoder mockBCryptPasswordEncoder;
	@MockBean protected CalendarAltReasonService mockCalendarAltReasonService;
	@MockBean protected CalendarAltTypeService mockCalendarAltTypeService;
	@MockBean protected CalendarMonthDayService mockCalendarMonthDayService;
	@MockBean protected CalendarRiteService mockCalendarRiteService;
	@MockBean protected CaptchaService mockCaptchaService;
	@MockBean protected CenturyService mockCenturyService;
	@MockBean protected CommemorationService mockCommemorationService;
	@MockBean protected CommemorationTitleService mockCommemorationTitleService;
	@MockBean protected CommemorationTypeService mockCommemorationTypeService;
	@MockBean protected CreedService mockCreedService;
	@MockBean protected CycleDayLiturgyTypeService mockCycleDayService;
	@MockBean protected DayCommemorationService mockDayCommemorationStaticInitializer;
	@MockBean protected DayService mockDayService;
	@MockBean protected FeatureFlagService mockFeatureFlagService;
	@MockBean protected HourReadingService mockHourReadingService;
	@MockBean protected InsetService mockInsetService;
	@MockBean protected LocationService mockLocationService;
	@MockBean protected LocationTypeService mockLocationTypeService;
	@MockBean protected LoginFailureService mockLoginFailureService;
	@MockBean protected LunarPhaseService mockLunarPhaseService;
	@MockBean protected ModelAttributeHelperService mockModelAttributeHelperService;
	@MockBean protected NationService mockNationService;
	@MockBean protected NationLocationService mockNationLocationService;
	@MockBean protected NationRegionService mockNationRegionService;
	@MockBean protected PatronageService mockPatronageService;
	@MockBean protected PatronageSubtypeService mockPatronageSubtypeService;
	@MockBean protected PatronageTypeService mockPatronageTypeService;
	@MockBean protected PayPalOrderValidationService mockPayPalOrderValidationService;
	@MockBean protected PayPalService mockPayPalService;
	@MockBean protected PdfService mockPdfService;
	@MockBean protected ReadingService mockReadingService;
	@MockBean protected ReferenceService mockReferenceService;
	@MockBean protected RequestService mockRequestService;
	@MockBean protected RequestValidationService mockRequestValidationService;
	@MockBean protected RouteService mockRouteService;
	@MockBean protected SearchCriteriaValidationService mockSearchCriteriaValidationService;
	@MockBean protected SecurityService mockSecurityService;
	@MockBean protected SessionManagementService mockSessionManagementService;
	@MockBean protected TagService mockTagService;
	@MockBean protected UserService mockUserService;
	@MockBean protected UserSessionInfoService mockUserSessionInfoService;
	@MockBean protected UserValidationService mockUserValidationService;

	protected MockMvc mockMvc;
	protected String testPath;

	@Before
	public void setup() throws AppException {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		testPath = "https://localhost:" + port;
		MockitoAnnotations.openMocks(this);
		doNothing().when(mockApplicationReadyEventListenerService).init();
		doReturn(TEST_USER).when(mockUserService).findByUsername(TEST_USERNAME);
		doReturn(ADMIN_USER).when(mockUserService).findByUsername(ADMIN_USERNAME);
	}

	protected MockMvc withActiveFeatureFlagOrAdmin(Boolean isActive) throws AppException {
		doReturn(isActive).when(mockFeatureFlagService)
			.isFeatureFlagActiveForUser(anyLong(), any(HttpSession.class), nullable(User.class));
		return this.mockMvc;
	}

	protected MockMvc withAdminUser(Boolean isAdmin) throws AppException {
		doReturn(new UserSessionInfo.Builder().withUser(isAdmin ? ADMIN_USER : TEST_USER).build())
			.when(mockUserSessionInfoService)
			.getCachedValue(any(HttpSession.class));
		doReturn(isAdmin)
			.when(mockUserSessionInfoService)
			.isAdminUser(any(HttpSession.class), any(HttpServletResponse.class));
		return this.mockMvc;
	}
}
