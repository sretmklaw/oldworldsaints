package com.cimeliarchium.service.dao;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.ValidationEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.service.helper.ValidationHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class UserValidationService extends ValidationHelperService implements Validator {

	private static final Integer MIN_LENGTH_USER_INPUT = 7;
	private static final Integer MAX_LENGTH_USER_INPUT = 24;
	private static final Integer MAX_LENGTH_USER_EMAIL = 32;
	private static final String ALPHANUMERIC_ASCII_SYMBOLS_REGEX = "[a-zA-Z0-9 \\/|/'\",.!?:;\\-\\(\\)\\[\\]{}@#$%^&*_+=~<>]+";

	private HttpSession session;
	private UserService userService;

	@Autowired
	public UserValidationService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> aClass) {

		return User.class.equals(aClass);
	}

	/**
	 * Method used to run default User validations
	 * 
	 * @param obj
	 *            the User
	 * @param errors
	 *            the validation result
	 */
	@Override
	public void validate(Object obj, Errors errors) {

		User user = (User) obj;
		this.validateUsername(user.getUsername(), errors);
		this.validateEmail(user.getEmail(), errors);
		this.validatePassword(user, errors);
		this.validateCalendarAndRiteId(user, errors);
		this.validateNationAndRegionId(user, errors);
		super.validateCaptcha(session, user.getCaptcha(), errors);
	}

	/**
	 * Method used to confirm username exists on create
	 * 
	 * @param session
	 *            the HttpSession
	 * @param obj
	 *            the User
	 * @param errors
	 *            the validation result
	 * @throws AppException 
	 */
	public void beforeCreate(
			HttpSession session, 
			Object obj, 
			Errors errors) throws AppException {

		super.throwIfMissing(session, beforeCreateMissingSession);
		this.session = session;
		User user = (User) obj;

		// Default validation
		this.validate(obj, errors);

		// Creation-specific validation
		if (this.hasExistingUsername(user)) {
			errors.rejectValue(ModelAttributeEnum.USERNAME.getValue(), ValidationEnum.DUPLICATE.getValue());
		}
		if (this.hasExistingEmail(user)) {
			errors.rejectValue(ModelAttributeEnum.EMAIL.getValue(), ValidationEnum.DUPLICATE.getValue());
		}
	}

	/**
	 * Method used to confirm username exists on update
	 * 
	 * @param session
	 *            the HttpSession
	 * @param obj
	 *            the User
	 * @param errors
	 *            the validation result
	 * @throws AppException 
	 */
	public void beforeUpdate(
			HttpSession session, 
			Object obj, 
			Errors errors) throws AppException {

		super.throwIfMissing(session, beforeUpdateMissingSession);
		User user = (User) obj;
		this.session = session;

		// Default validation
		this.validate(obj, errors);

		// Update-specific validations
		final String password = user.getPassword();
		final String oldPassword = user.getOldPassword();
		if (oldPassword == null || oldPassword.isEmpty()) {
			errors.rejectValue(ModelAttributeEnum.OLD_PASSWORD.getValue(), ValidationEnum.MISSING.getValue());
		} else if ((password == null 
				|| oldPassword == null) 
				|| (password != null && password.equals(oldPassword))) {
			errors.rejectValue(ModelAttributeEnum.OLD_PASSWORD.getValue(), ValidationEnum.DIFFERENT.getValue());
		}
	}

	private void validateUsername(String username, Errors errors) {
		if (username == null || username.isEmpty()) {
			errors.rejectValue(ModelAttributeEnum.USERNAME.getValue(), ValidationEnum.MISSING.getValue());
		} else {
			if (!(username.matches(ALPHANUMERIC_ASCII_SYMBOLS_REGEX))) {
				errors.rejectValue(ModelAttributeEnum.USERNAME.getValue(), ValidationEnum.IMPROPER_FORMAT.getValue());
			}
			if (!super.isValidLength(username, MIN_LENGTH_USER_INPUT, MAX_LENGTH_USER_INPUT)) {
				errors.rejectValue(ModelAttributeEnum.USERNAME.getValue(), ValidationEnum.IMPROPER_LENGTH.getValue());
			}
		}
	}

	private void validateEmail(String email, Errors errors) {
		if (email == null || email.isEmpty()) {
			errors.rejectValue(ModelAttributeEnum.EMAIL.getValue(), ValidationEnum.MISSING.getValue());
		} else {
			if (!(email.matches(ALPHANUMERIC_ASCII_SYMBOLS_REGEX))) {
				errors.rejectValue(ModelAttributeEnum.EMAIL.getValue(), ValidationEnum.IMPROPER_FORMAT.getValue());
			}
			if (!email.contains("@") || !email.contains(".")) {
				errors.rejectValue(ModelAttributeEnum.EMAIL.getValue(), ValidationEnum.IMPROPER_DELIMIT.getValue());
			}
			if (!super.isValidLength(email, MIN_LENGTH_USER_INPUT, MAX_LENGTH_USER_EMAIL)) {
				errors.rejectValue(ModelAttributeEnum.EMAIL.getValue(), ValidationEnum.IMPROPER_LENGTH.getValue());
			}
		}
	}

	private void validatePassword(User user, Errors errors) {
		final String password = user.getPassword();
		final String passwordConfirm = user.getPasswordConfirm();
		// Password
		if (password == null || password.isEmpty()) {
			errors.rejectValue(ModelAttributeEnum.PASSWORD.getValue(), ValidationEnum.MISSING.getValue());
		} else if (passwordConfirm == null || passwordConfirm.isEmpty()) {
			errors.rejectValue(ModelAttributeEnum.PASSWORD_CONFIRM.getValue(), ValidationEnum.MISSING.getValue());
		} else {
			if (password != null && !(password.matches(ALPHANUMERIC_ASCII_SYMBOLS_REGEX))) {
				errors.rejectValue(ModelAttributeEnum.PASSWORD.getValue(), ValidationEnum.IMPROPER_FORMAT.getValue());
			}
			if (password != null && !(passwordConfirm.matches(ALPHANUMERIC_ASCII_SYMBOLS_REGEX))) {
				errors.rejectValue(ModelAttributeEnum.PASSWORD_CONFIRM.getValue(), ValidationEnum.IMPROPER_FORMAT.getValue());
			}
			if ((password == null 
					|| passwordConfirm == null) 
					|| (password != null && !password.equals(passwordConfirm))) {
				errors.rejectValue(ModelAttributeEnum.PASSWORD_CONFIRM.getValue(), ValidationEnum.DIFFERENT.getValue());
			}
			if (!super.isValidLength(password, MIN_LENGTH_USER_INPUT, MAX_LENGTH_USER_INPUT)) {
				errors.rejectValue(ModelAttributeEnum.PASSWORD.getValue(), ValidationEnum.IMPROPER_LENGTH.getValue());
			}
		}
	}

	private Boolean hasExistingUsername(User user) throws AppException {
		final String username = user.getUsername();
		Boolean hasExistingUsername = false;
		if (username != null && !username.isEmpty()) {
			final User foundUser = Optional.ofNullable((User) userService.findByUsername(username)).orElse(null);
			hasExistingUsername = (foundUser != null);
		}
		return hasExistingUsername;
	}

	private Boolean hasExistingEmail(User user) throws AppException {
		final String email = user.getEmail();
		Boolean hasExistingEmail = false;
		if (email != null && !email.isEmpty()) {
			final User foundUser = Optional.ofNullable((User) userService.findByEmail(email)).orElse(null);
			hasExistingEmail = (foundUser != null);
		}
		return hasExistingEmail;
	}

	private void validateCalendarAndRiteId(User user, Errors errors) {
		final Long calendarId = user.getCalendarId();
		final Long riteId = user.getRiteId();
		if (calendarId == null) {
			errors.rejectValue(ModelAttributeEnum.CALENDAR_ID.getValue(), ValidationEnum.MISSING.getValue());
		} else if (riteId == null) {
			errors.rejectValue(ModelAttributeEnum.RITE_ID.getValue(), ValidationEnum.MISSING.getValue());
		}
	}

	private void validateNationAndRegionId(User user, Errors errors) {
		final Long nationId = user.getNationId();
		final Long regionId = user.getRegionId();
		if (nationId == null) {
			errors.rejectValue(ModelAttributeEnum.NATION_ID.getValue(), ValidationEnum.MISSING.getValue());
		} else if (regionId == null) {
			errors.rejectValue(ModelAttributeEnum.REGION_ID.getValue(), ValidationEnum.MISSING.getValue());
		}
	}

	@Value("${uservalidationservice.beforecreate.session}")
	private String beforeCreateMissingSession;

	@Value("${uservalidationservice.beforeupdate.session}")
	private String beforeUpdateMissingSession;
}