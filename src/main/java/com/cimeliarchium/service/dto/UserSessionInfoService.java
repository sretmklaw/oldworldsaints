package com.cimeliarchium.service.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dao.UserSessionInfo;
import com.cimeliarchium.model.dto.UserSessionInfoDto;
import com.cimeliarchium.service.BaseServiceIF;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.UserService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class UserSessionInfoService extends SessionAttributeHelperService
	implements BaseServiceIF<String,User> {

	@Autowired private EntityManager entityManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionInfoService.class);
	public static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.CURRENT_USER_DETAILS.getValue();

	private UserService userService;
	private FeatureFlagService featureFlagService;

	@Autowired
	public UserSessionInfoService(UserService userService,
			FeatureFlagService featureFlagService) {
		this.userService = userService;
		this.featureFlagService = featureFlagService;
	}

	@SuppressWarnings("unchecked")
	public List<UserSessionInfoDto> queryResults(Long userId) throws AppException {

		StoredProcedureQuery query = null;
		if (userId != null) {
			query = this.entityManager
					.createNamedStoredProcedureQuery("UserSessionInfoMappingQuery");
			query.setParameter("_user_id", userId);
			query.execute();
		} else {
			query = this.entityManager
					.createNamedStoredProcedureQuery("UserSessionInfoAdminMappingQuery");
			query.execute();
		}
		List<UserSessionInfoDto> rs = (List<UserSessionInfoDto>) query.getResultList();
		LOGGER.debug("Query returned {} results", rs.size());
		return rs;
	}

	/**
	 * Method used to obtain listing of User Session Info values
	 * 
	 * @return mapping
	 * @throws AppException 
	 */
	public List<UserSessionInfoDto> listAll() throws AppException {

		return queryResults(null);
	}

	public Boolean initCache(
			@NotNull HttpSession session,
			User user) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);

		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			if (user == null) {
				user = userService.queryForCurrentUser();
			}
			super.throwIfMissing(user, initCacheMissingUser);
			// Reset non-zero failed Login attempts for authenticated Users.
			userService.resetLoginAttempts(user, 0L, Instant.now());
			final Long userId = user.getUserId();
			final List<UserSessionInfoDto> resultsList = this.queryResults(userId);
			final UserSessionInfoDto dto = (resultsList != null && !resultsList.isEmpty()) 
					? resultsList.get(0) : null;
			final UserSessionInfo userSessionInfo = new UserSessionInfo.Builder()
					.withUser(dto.getUser())
					.withCalendar(dto.getCalendar())
					.withRite(dto.getRite())
					.withNation(dto.getNation())
					.withRegion(dto.getRegion())
					.build();
			session.setAttribute(SESSION_ATTRIBUTE_NAME, userSessionInfo);
			LOGGER.debug("Added attribute '{}' for user: {}", SESSION_ATTRIBUTE_NAME, 
					userSessionInfo.getUser().getUsername());
		}
		return hasUpdate;
	}

	@Deprecated
	@Override
	public Boolean initCache(HttpSession session) {
		return null; // Intentionally, do nothing
	}

	public UserSessionInfo getCachedValues(
			@NotNull HttpSession session) throws AppException {

		SessionAttributeHelperService.validateSessionAttributeParams(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession, 
				getCachedValuesNotFound);
		return (UserSessionInfo) session.getAttribute(SESSION_ATTRIBUTE_NAME);
	}

	@Override
	public void initModel(
			@NotNull HttpSession session, 
			Model model, 
			User user) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		final UserSessionInfo userSessionInfo = this.getCachedValues(session);

		// Username
		final User currentUser = userSessionInfo.getUser();
		BaseDisplayableDtoServiceHelper.addModelAttribute(
				model, 
				currentUser.getUsername(),
				ModelAttributeEnum.USERNAME.getValue());
		// User Nation entity
		BaseDisplayableDtoServiceHelper.addModelAttribute(
				model, 
				userSessionInfo.getNation(), 
				ModelAttributeEnum.USER_NATION.getValue());
		// User Region entity
		BaseDisplayableDtoServiceHelper.addModelAttribute(
				model, 
				userSessionInfo.getRegion(), 
				ModelAttributeEnum.USER_REGION.getValue());
		// User Calendar entity
		BaseDisplayableDtoServiceHelper.addModelAttribute(
				model, 
				userSessionInfo.getCalendar(), 
				ModelAttributeEnum.USER_CALENDAR.getValue());
		// User Rite entity
		BaseDisplayableDtoServiceHelper.addModelAttribute(
				model, 
				userSessionInfo.getRite(), 
				ModelAttributeEnum.USER_RITE.getValue());
		// User Notification status
		BaseDisplayableDtoServiceHelper.addModelAttribute(
				model, 
				userSessionInfo.getShowNotification(), 
				ModelAttributeEnum.USER_SHOW_NOTIFICATION.getValue());
		// Determine whether user has reached maximum number of requests
		if (currentUser != null) {
			BaseDisplayableDtoServiceHelper.addModelAttribute(
					model, 
					featureFlagService.hasMaxOpenRequests(session, currentUser), 
					ModelAttributeEnum.REQUEST_BUTTON_IS_DISABLED.getValue());
		}
	}

	/**
	 * Method used to obtain current date and time at UTC
	 * 
	 * @return offsetDateTime
	 */
	public OffsetDateTime getUtcOffsetDateTime() {

		return OffsetDateTime.of(
				LocalDateTime.now(ZoneId.of("UTC")), 
				ZoneOffset.UTC);
	}

	/**
	 * Method used to calculate Date with Time-Zone from UTC with User DST Offset
	 * 
	 * @param session the HttpSession
	 * @return OffsetDateTime
	 * @throws AppException 
	 */
	public OffsetDateTime getCurrentZonedDateTimeForUser(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, getCurrentZonedDateTimeForUserMissingSession);
		final UserSessionInfo userSessionInfo = this.getCachedValue(session);
		final ZoneOffset userZoneOffset = ZoneOffset.of(userSessionInfo
				.getRegion()
				.getUtcOffset());
		final OffsetDateTime utcDateTimeWithZoneOffset = this
				.getUtcOffsetDateTime()
				.withOffsetSameInstant(userZoneOffset);
		LOGGER.debug("Current Offset Datetime for User '{}' is {}", 
				userSessionInfo.getUser().getUsername(), 
				utcDateTimeWithZoneOffset);
		return utcDateTimeWithZoneOffset;
	}

	/**
	 * Method used to calculate Date from UTC with User DST Offset
	 * 
	 * @param session the HttpSession
	 * @return Date
	 * @throws AppException 
	 */
	public Date getCurrentDateForUser(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, getCurrentDateForUserMissingSession);
		final Date userDate = new Date(this
				.getCurrentZonedDateTimeForUser(session)
				.toInstant()
				.toEpochMilli());
		return userDate;
	}

	/**
	 * Method used to obtain previously-initialized session variables, or else throw error code
	 * 
	 * @param session the HttpSession
	 * @return sessionAttribute
	 * @throws AppException
	 */
	public UserSessionInfo getCachedValue(@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, getCachedValuesNotFound);
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			throw new AppException(getCachedValuesNotFound);
		} else {
			return (UserSessionInfo) session.getAttribute(SESSION_ATTRIBUTE_NAME);
		}
	}

	/**
	 * Method used to determine current user Administrator access to restricted locations
	 * 
	 * @param session the HttpSession
	 * @param response the HttpServletResponse
	 * @return Boolean
	 * @throws AppException
	 */
	public Boolean isAdminUser(HttpSession session, HttpServletResponse response) throws AppException {

		User requestingUser = this.getCachedValue(session).getUser();
		if (!requestingUser.getHasAdminRole()) {
			if (response != null) {
				response.setStatus(HttpStatus.SC_FORBIDDEN);
			}
			return false;
		}
		return true;
	}

	/**
	 *  Method used to remove previously-entered request from count against specific user.
	 *  Note that bugfix requests do not count toward the request per user limit, and so do not decrement.
	 * 
	 * @param session the HttpSession
	 * @param request the Request
	 * @return hasMaxOpenRequests
	 * @throws AppException 
	 */
	public void decrementUserRequestCount(HttpSession session, Request request) throws AppException {
		if (request.getRequestTypeId() == requestEntryId) {
			final User user = getCachedValue(session).getUser();
			userService.updateEntryRequestCount(user, user.getRequestCount()-1);
		}
	}

	/**
	 *  Method used to record a new request submission for specific user.
	 *  Note that bugfix requests do not count toward the request per user limit, and so do not increment.
	 * 
	 * @param session the HttpSession
	 * @param request the Request
	 * @return hasMaxOpenRequests
	 * @throws AppException 
	 */
	public Boolean maybeIncrementUserRequestCount(HttpSession session, Request request) throws AppException {
		final User user = getCachedValue(session).getUser();
		Boolean hasMaxOpenRequests = featureFlagService.hasMaxOpenRequests(session, user);
		// Only increment if maximum has not been reached
		if (!hasMaxOpenRequests && request.getRequestTypeId() == requestEntryId) {
			userService.updateEntryRequestCount(user, user.getRequestCount()+1);
		}
		return hasMaxOpenRequests;
	}

	/**
	 * Method used to prevent unauthorized access to sensitive resources
	 * 
	 * @param session the HttpSession
	 * @param username the current username
	 * @return boolean
	 * @throws AppException 
	 */
	public Boolean isAuthorizedRequestForCurrentUser(@NotNull HttpSession session, String username) throws AppException {
	
		super.throwIfMissing(session, isAuthorizedRequestForCurrentUserMissingSession);
		User user = (getCachedValue(session)).getUser();
		String authUsername = user.getUsername();
		return (username != null && authUsername.equals(username));
	}

	/**
	 * Method used to reset clear cache reminder flag for user
	 * 
	 * @param username the current user
	 * @throws AppException 
	 */
	public void resetHasClearedCache(@NotNull User user) throws AppException {

		super.throwIfMissing(user, resetHasClearedCacheMissingUser);
		userService.resetHasClearedCache(user);
	}

	@Value("${usersessioninfoservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${usersessioninfoservice.initcache.user}")
	private String initCacheMissingUser;

	@Value("${usersessioninfoservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${usersessioninfoservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${usersessioninfoservice.getcurrentzoneddatetimeforuser.session}")
	private String getCurrentZonedDateTimeForUserMissingSession;

	@Value("${usersessioninfoservice.getcurrentdateforuser.session}")
	private String getCurrentDateForUserMissingSession;

	@Value("${usersessioninfoservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${usersessioninfoservice.initmodel.user}")
	private String initModelMissingUser;

	@Value("${usersessioninfoservice.isauthorizedrequestforcurrentuser.session}")
	private String isAuthorizedRequestForCurrentUserMissingSession;

	@Value("${usersessioninfoservice.resethasclearedcache.user}")
	private String resetHasClearedCacheMissingUser;

	@Value("${request.entry.id}")
	private Long requestEntryId;

}
