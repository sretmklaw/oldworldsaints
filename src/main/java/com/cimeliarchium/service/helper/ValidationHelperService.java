package com.cimeliarchium.service.helper;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.Errors;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.enums.ValidationEnum;
import com.cimeliarchium.exception.AppException;

@PropertySource({ "classpath:/errorcode.properties" })
public class ValidationHelperService extends SessionAttributeHelperService {

	private static final Integer MIN_LENGTH_1 = 1;
	private static final Integer MAX_LENGTH_10 = 10;
	private static final String NUMERIC_INTEGER_REGEX = "[0-9]+";

	protected void validateCaptcha(HttpSession session, String captcha, Errors errors) {
		String cachedCaptcha = null;
		try {
			cachedCaptcha = super.getSessionAttribute(session, 
					SessionAttributeEnum.CAPTCHA.getValue(), 
					validateCaptchaMissingSession, 
					validateCaptchaNotFound);
		} catch (AppException e) {
			// Intentionally, do nothing. Simply reject on validation.
		}
		if (captcha == null 
				|| cachedCaptcha == null
				|| (captcha != null && captcha.isEmpty())) {
			errors.rejectValue(ModelAttributeEnum.CAPTCHA_TEXT.getValue(), ValidationEnum.MISSING.getValue());
		} else if (!captcha.equals(cachedCaptcha)) {
			errors.rejectValue(ModelAttributeEnum.CAPTCHA_TEXT.getValue(), ValidationEnum.DIFFERENT.getValue());
		}
	}

	protected Boolean isRejectedAsImproperFormatOrMissingAndRequired(String input, String attr, Integer minLength, Integer maxLength, String regex, Errors errors) {
		if (!this.isRejectedAsMissingAndRequired(input, attr, errors)) {
			return this.isRejectedAsImproperFormatAndRequired(input, attr, minLength, maxLength, regex, errors);
		} else {
			return true;
		}
	}

	protected void isRejectedAsImproperFormatForNumericIdOrMissingAndRequired(String id, String attr, Errors errors) {
		if (!this.isRejectedAsMissingAndRequired(id, attr, errors)) {
			this.isRejectedAsImproperFormatForNumericIdAndRequired(id, attr, errors);
		}
	}

	protected Boolean isRejectedAsImproperFormatForNumericIdWhereNotNull(String id, String attr, Errors errors) {
		return (this.isNotNullOrEmpty(id)) 
				? this.isRejectedAsImproperFormatForNumericIdAndRequired(id, attr, errors) 
				: false;
	}

	protected Boolean isRejectedAsImproperFormatForNumericIdAndRequired(String id, String attr, Errors errors) {
		return !this.isRejectedAsImproperFormatAndRequired(id, attr, MIN_LENGTH_1, MAX_LENGTH_10, NUMERIC_INTEGER_REGEX, errors);
	}

	protected Boolean isRejectedAsImproperFormatWhereNotNull(String input, String attr, Integer minLength, Integer maxLength, String regex, Errors errors) {
		return (this.isNotNullOrEmpty(input)) 
				? this.isRejectedAsImproperFormatAndRequired(input, attr, minLength, maxLength, regex, errors) 
				: false;
	}

	protected Boolean isValidLength(String input, Integer minLength, Integer maxLength) {
		return (input.length() >= minLength && input.length() <= maxLength);
	}

	protected Boolean isNotNullOrEmpty(String input) {
		return (input != null && !input.equals("") && !input.equals("null"));
	}

	protected Boolean isRejectedAsMissingAndRequired(String input, String attr, Errors errors) {
		if (!this.isNotNullOrEmpty(input)) {
			errors.rejectValue(attr, ValidationEnum.MISSING.getValue());
			return true;
		} else {
			return false;
		}
	}

	protected Boolean isRejectedAsImproperFormatAndRequired(String input, String attr, Integer minLength, Integer maxLength, String regex, Errors errors) {
		if (!isValidInput(input, minLength, maxLength, regex)) {
			errors.rejectValue(attr, ValidationEnum.IMPROPER_FORMAT.getValue());
			return true;
		}
		return false;
	}

	protected Boolean isValidIdInput(Long id) {
		return this.isValidInput(String.valueOf(id), MIN_LENGTH_1, MAX_LENGTH_10, NUMERIC_INTEGER_REGEX);
	}

	protected Boolean isValidInput(String input, Integer minLength, Integer maxLength, String regex) {
		return this.isValidLength(input, minLength, maxLength) && (regex != null && input.matches(regex));
	}

	@Value("${validationhelperservice.validatecaptcha.session}")
	private String validateCaptchaMissingSession;

	@Value("${validationhelperservice.validatecaptcha.notfound}")
	private String validateCaptchaNotFound;
}
