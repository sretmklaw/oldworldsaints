package com.cimeliarchium.service.web;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.service.dao.UserService;

@Service
public class LoginFailureService extends SimpleUrlAuthenticationFailureHandler {

	private static final Long MAX_FAILED_ATTEMPTS = 5L; // 5 failed attempts allowed
	private static final Long LOCK_DURATION_SECONDS = 30L * 60L; // 30 minutes since last failed attempt

	private UserService userService;

	@Autowired
	public LoginFailureService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request, 
			HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		final String username = request.getParameter("username");
		final FlashMap flashMap = new FlashMap();

		try {
			final User user = userService.findByUsername(username);
			if (user != null && getUserLockStatus(user, request) == 3) {
				flashMap.put(ModelAttributeEnum.ERROR_FLASH_ATTRIBUTE.getValue(), 
						DisplayKeyEnum.ACCOUNT_LOCKED.getValue());
			} else {
				userService.updateLastLoginFailure(user, request.getSession());
				flashMap.put(ModelAttributeEnum.ERROR_FLASH_ATTRIBUTE.getValue(), 
						DisplayKeyEnum.LOGIN_FAILED_INVALID.getValue());
			}
		}
		// Any server-side failure should display the message 'Problem logging in'
		catch (AppException e) {
			flashMap.put(ModelAttributeEnum.ERROR_FLASH_ATTRIBUTE.getValue(), 
					DisplayKeyEnum.LOGIN_FAILED_ERROR.getValue());
		}
		// Add the error message as a flash attribute to display, and then return.
		final FlashMapManager flashMapManager = new SessionFlashMapManager();
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		response.sendRedirect(request.getHeader("referer"));
		return;
	}

	/**
	 * Method used to determine which login failure message should be displayed to the user:
	 * 1 - Update Last Login Failure
	 * 2 - Reset Login Attempts
	 * 3 - Has Lock
	 * 
	 * @param user the User
	 * @param request the HttpServletRequest
	 * @return hasLock
	 * @throws AppException
	 */
	public int getUserLockStatus(User user, HttpServletRequest request) throws AppException {

		throwIfMissing(user);
		throwIfMissing(request);

		// Anonymous Users or those with Administrator-imposed lockout should always show the 'Account Locked' message.
		if (user == null || user.getHasAdminLock()) {
			return 3;

		} else {

			// Handle initial login attempt after registration
			final Instant lastLoginAttemptDate = user.getLastLoginAttemptDate();
			if (lastLoginAttemptDate == null) {
				return 1;
			}
			// Otherwise, proceed to evaluate for temporary lockout criteria
			final Instant currentUtcDate = Instant.now();
			throwIfMissing(currentUtcDate);
			final Boolean hasFailedAttemptWithinWindow = currentUtcDate
					.isBefore(lastLoginAttemptDate.plusSeconds(LOCK_DURATION_SECONDS));
			final Boolean hasReachedMaxFailedAttempts = user.getFailedLoginCount() > MAX_FAILED_ATTEMPTS - 1;
			// Users outside the temporary lockout window should have their failed attempts reset.
			if (!hasFailedAttemptWithinWindow && hasReachedMaxFailedAttempts) {
				return 2;
			} 
			// Users inside a temporary lockout window exceeding maximum failed attempts are locked.
			else if (hasFailedAttemptWithinWindow && hasReachedMaxFailedAttempts) {
				return 3;
			}
			// Users with neither a temporary lockout nor exceeding maximum failed attempts are not locked,
			// but they should increment the login failure count and set the last attempt date.
			else {
				return 1;
			}
		}
	}

	/**
	 * This method will be used in a manner similar to Java's Object.requireNotNull(), 
	 * but it adds a predefined error code and other details to assist in troubleshooting
	 * 
	 * @param obj the Object to null-check
	 * @throws AppException
	 */
	@SuppressWarnings({ "rawtypes" })
	protected static Object throwIfMissing(Object obj) throws AppException {

		if (obj == null 
				|| (obj instanceof String && ((String) obj).isEmpty())
				|| (obj instanceof Collection && ((Collection) obj).isEmpty())
				|| (obj instanceof List && ((List) obj).isEmpty())
				|| (obj instanceof Set && ((Set) obj).isEmpty())
				|| (obj instanceof Map && ((Map) obj).isEmpty())) {
			throw new AppException("");
		}
		return obj;
	}
}
