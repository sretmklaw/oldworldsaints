package com.cimeliarchium.service.dao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.enums.StatusCodeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.UserRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;
import com.cimeliarchium.service.web.SecurityService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class UserService extends SessionAttributeHelperService
		implements BaseCreateableUpdateableDAOServiceIF<Long,User>,
				BaseOneToOneMapServiceIF<Long,User> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.USER_MAP.getValue();

	private ApplicationContext applicationContext;
	private UserRepository repo;
	private SecurityService securityService;

	@Autowired
	public UserService(ApplicationContext applicationContext, UserRepository repo, SecurityService securityService) {
		this.applicationContext = applicationContext;
		this.repo = repo;
		this.securityService = securityService;
	}

	@Override
	public List<User> query() {

		return this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "username"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, User> map() throws AppException {

		final List<User> resultList = query();
		throwIfMissing(resultList, mapMissingResultList);

		return new TreeMap<Long, User>(
				// Obtain non-null results list by querying
				((List<User>) super.throwIfMissing(
						this.query(), mapMissingResultList)).stream()
				// Restrict results to those with non-null last login time
				.filter(e -> e.getLastLoginSuccessDate() != null)
				// Sort by last login time
				.sorted((e1, e2) -> e1.getLastLoginSuccessDate()
						.compareTo(e2.getLastLoginSuccessDate()))
				// Map by ID
				.collect(Collectors.toMap(
						User::getUserId, // Key
						req -> req))); // Value
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					this.map(), 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Long, User> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_NAME,
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	/**
	 * Method used to conditionally query for an existing user in the database.
	 * Where available from the session, use that value instead to minimize database calls.
	 * 
	 * @param session the HttpSession
	 * @return user
	 * @throws AppException 
	 */
	public User queryForCurrentUser() throws AppException {

		final User currentUser;
		currentUser = findByUsername(
				securityService.getAuthenticatedUserCredential());
		return currentUser;
	}

	/**
	 * Method used to find user by userId
	 * 
	 * @param userId
	 *            the User ID
	 * @return User
	 * @throws AppException 
	 */
	public User findById(@NotNull Long userId) throws AppException {

		super.throwIfMissing(userId, findByIdMissingUserId);
		return repo.findByUserId(userId);
	}

	/**
	 * Method used to find user by username
	 * 
	 * @param username
	 *            the username
	 * @return User
	 */
	public User findByUsername(String username) {

		return repo.findByUsername(username);
	}

	/**
	 * Method used to find user by email
	 * 
	 * @param userEmail
	 *            the User email
	 * @return User
	 * @throws AppException 
	 */
	public User findByEmail(@NotNull String userEmail) throws AppException {

		super.throwIfMissing(userEmail, findByEmailMissingUserEmail);
		return repo.findByEmail(userEmail);
	}

	/**
	 * Method used to save new user registration
	 * 
	 * @param session the HttpSession
	 * @param user the User
	 * @return User
	 * @throws AppException 
	 */
	@Override
	public User create(HttpSession session, User user) throws AppException {

		final BCryptPasswordEncoder passwordEncoder = applicationContext.getBean(BCryptPasswordEncoder.class);
		super.throwIfMissing(user, createMissingUser);
		user.setUserId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		user.setEmail(user.getEmail());
		user.setUsername(user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setPasswordConfirm(null);
		user.setOldPassword(null);
		user.setCalendarId(user.getCalendarId());
		user.setRiteId(user.getRiteId());
		user.setNationId(user.getNationId());
		user.setRegionId(user.getRegionId());
		user.setCreationDate(Instant.now());
		user.setLastLoginAttemptDate(null);
		user.setFailedLoginCount(0L);
		user.setLastLoginSuccessDate(null);
		user.setTotalLoginCount(0L);
		user.setHasAdminRole(false); // New users aren't administrators, by default
		user.setHasAdminLock(false);
		user.setHasAdminLimit(false);
		user.setCaptcha(null); // Reset CAPTCHA before saving
		return repo.save(user);
	}

	/**
	 * Method used to update existing user registration
	 * 
	 * @param user
	 *            the User
	 * @return user
	 * @throws AppException 
	 */
	@Override
	public User update(User user) throws AppException {

		final BCryptPasswordEncoder passwordEncoder = applicationContext.getBean(BCryptPasswordEncoder.class);
		super.throwIfMissing(user, updateMissingUser);
		final String username = user.getUsername();
		final String oldPassword = user.getOldPassword();
		final String newPassword = passwordEncoder.encode(user.getPassword());
		final User existingUser = this.findByUsername(username);
		if (passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(newPassword);
			existingUser.setCalendarId(user.getCalendarId());
			existingUser.setRiteId(user.getRiteId());
			existingUser.setNationId(user.getNationId());
			existingUser.setRegionId(user.getRegionId());
			final User updatedUser = repo.save(existingUser);
			LOGGER.info("Updated user ID {} '{}' at {}", 
					updatedUser.getUserId(), 
					updatedUser.getUsername(), 
					Instant.now());
			return updatedUser;
		}
		return existingUser;
	}

	/**
	 * Method used to update user login information on failed authentication attempt
	 * 
	 * @param user the User
	 * @param session the HttpSession
	 * @throws AppException
	 */
	public void updateLastLoginFailure(
			@NotNull User user,
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(user, updateLastLoginFailureMissingUser);
		super.throwIfMissing(session, updateLastLoginFailureMissingSession);

		// Record the last login attempt date and increment the failed login count
		repo.setLastLoginAttemptDateAndFailedLoginCountForUser(
				Instant.now(), 
				user.getFailedLoginCount() + 1,
				user.getUserId());
	}

	/**
	 * Method used to update user login information on successful authentication attempt
	 * 
	 * @param user the User
	 * @param session the HttpSession
	 * @param request the HttpServletRequest
	 * @throws AppException
	 */
	public void updateLastLoginSuccess(
			@NotNull HttpSession session,
			@NotNull HttpServletRequest request,
			@NotNull User user) throws AppException {

		super.throwIfMissing(session, updateLastLoginSuccessMissingSession);
		super.throwIfMissing(request, updateLastLoginSuccessMissingRequest);
		super.throwIfMissing(user, updateLastLoginSuccessMissingUser);

		final Instant loginSuccessDate = Instant.now();

		// First, update Login Success, Request Origin, and Total Login count
		repo.setLastLoginSuccessDateAndLastLoginIpAndTotalLoginCountForUser(
				loginSuccessDate, 
				securityService.parseRequestOrigin(request), 
				user.getTotalLoginCount() + 1, 
				user.getUserId());

		// Log the successful login attempt
		LOGGER.info("Login for user ID {} '{}' at {}", 
				user.getUserId(), 
				user.getUsername(), 
				loginSuccessDate);

		// Finally, reset login attempt count
		this.resetLoginAttempts(user, 0L, loginSuccessDate);
	}

	/**
	 * Method used to reset last login failure
	 * 
	 * @param count the failed login attempt count
	 * @param user the User
	 * @param asOfDate the UTC OffsetDateTime
	 */
	public void resetLoginAttempts(
			User user,
			Long count,
			Instant asOfDate) {

		repo.setLastLoginAttemptDateAndFailedLoginCountForUser(
				asOfDate, 
				count, 
				user.getUserId());
	}

	/**
	 * Method used to reset user cache status
	 * 
	 * @param user the User
	 */
	public void resetCacheStatusForAllUsers() {
		repo.resetHasClearedCacheForAllUsers();
	}

	/**
	 * Method used to reset user cache status
	 * 
	 * @param user the User
	 */
	public void resetHasClearedCache(User user) {
		repo.setHasClearedCacheForUser(
				true, 
				user.getUserId());
	}

	/**
	 * Method used to perform batch update User statuses
	 * 
	 * @param ids the listing of entity IDs
	 * @param val the update value
	 * @param session the HttpSession
	 * @return message
	 * @throws AppException
	 */
	public List<Long> updateUsersStatus(
			@NotNull List<Long> ids, 
			@NotNull String val, 
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(ids, updateStatusMissingIds);
		super.throwIfMissing(val, updateStatusMissingValue);
		super.throwIfMissing(session, updateStatusMissingSession);

		// Parse Administrator-determined lock or limit statuses from the passed string value.
		Boolean hasAdminLock = val.equals(StatusCodeEnum.RED.getValue());
		Boolean hasAdminLimit = val.equals(StatusCodeEnum.YELLOW.getValue());
		final List<Long> updateIds = new ArrayList<>();
		if (!ids.isEmpty()) {
			for (Long id : ids) {
				LOGGER.info("Setting User ID {} to status: {}", id, val);
				try {
					repo.setHasAdminLockAndAdminLimitAndFailedLoginCountForUser(hasAdminLock, hasAdminLimit, 0L, id);
					LOGGER.info(DisplayKeyEnum.REQUEST_SUCCESS.getValue());
					updateIds.add(id);
				} catch (Exception e) {
					LOGGER.error(DisplayKeyEnum.REQUEST_FAILURE.getValue());
				}
			}
		}
		return updateIds;
	}

	public void removeUser(@NotNull User user) throws AppException {

		super.throwIfMissing(user, removeUserMissingUser);
		repo.delete(user);
	}

	@Deprecated
	@Override
	public void initModel(HttpSession session, Model model, User user) throws AppException {
		// Intentionally, do nothing.
	}

	/**
	 * Method used to ensure the update User entity is initialized with all existing User details
	 * 
	 * @param model the Model
	 * @param existingUser the User
	 */
	public void syncExistingUserDetails(Model model, User existingUser) {
		model.addAttribute(ModelAttributeEnum.USER.getValue(), new User.Builder()
				.withUserId(existingUser.getUserId())
				.withUsername(existingUser.getUsername())
				.withEmail(existingUser.getEmail())
				.withCalendarId(existingUser.getCalendarId())
				.withRiteId(existingUser.getRiteId())
				.withNationId(existingUser.getNationId())
				.withRegionId(existingUser.getRegionId())
				.build());
	}

	/**
	 * Method used to increment request count for user on entry request submission
	 * 
	 * @param count the new request count
	 * @param user the target User
	 * @throws AppException 
	 */
	public void updateEntryRequestCount(
			User user,
			Long count) throws AppException {

		// Update the value in the database.
		repo.setRequestCountForUser(count, user.getUserId());
		// Also update the request count for the cached user within current session.
		user.setRequestCount(count);
	}

	@Value("${userservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${userservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${userservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${userservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${userservice.create.user}")
	private String createMissingUser;

	@Value("${userservice.update.user}")
	private String updateMissingUser;

	@Value("${userservice.updatelastloginfailure.user}")
	private String updateLastLoginFailureMissingUser;

	@Value("${userservice.updatelastloginfailure.session}")
	private String updateLastLoginFailureMissingSession;

	@Value("${userservice.updatelastloginsuccess.user}")
	private String updateLastLoginSuccessMissingUser;

	@Value("${userservice.updatelastloginsuccess.session}")
	private String updateLastLoginSuccessMissingSession;

	@Value("${userservice.updatelastloginsuccess.request}")
	private String updateLastLoginSuccessMissingRequest;

	@Value("${userservice.updatestatus.ids}")
	private String updateStatusMissingIds;

	@Value("${userservice.updatestatus.value}")
	private String updateStatusMissingValue;

	@Value("${userservice.updatestatus.session}")
	private String updateStatusMissingSession;

	@Value("${userservice.findbyid.userid}")
	private String findByIdMissingUserId;

	@Value("${userservice.findbyusername.username}")
	private String findByUsernameMissingUsername;

	@Value("${userservice.findbyemail.useremail}")
	private String findByEmailMissingUserEmail;

	@Value("${userservice.setuserlock.userid}")
	private String setUserLockMissingUserId;

	@Value("${userservice.setuserlock.haslock}")
	private String setUserLockMissingHasLock;

	@Value("${userservice.removeuser.user}")
	private String removeUserMissingUser;
}
