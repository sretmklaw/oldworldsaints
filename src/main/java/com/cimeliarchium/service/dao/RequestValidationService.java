package com.cimeliarchium.service.dao;

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
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.helper.ValidationHelperService;

@Service
@PropertySource({
	"classpath:/application.properties",
	"classpath:/errorcode.properties"
})
public class RequestValidationService extends ValidationHelperService implements Validator {

	private HttpSession session;

	UserSessionInfoService userSessionInfoService;

	@Autowired
	public RequestValidationService(UserSessionInfoService userSessionInfoService) {
		this.userSessionInfoService = userSessionInfoService;
	}

	@Override
	public boolean supports(Class<?> aClass) {

		return Request.class.equals(aClass);
	}

	/**
	 * Method used to validate before submit
	 * 
	 * @param session the HttpSession
	 * @param obj the Request
	 * @param errors the validation result
	 * @throws AppException 
	 */
	public void beforeCreate(
			HttpSession session, 
			Object obj, 
			Errors errors) throws AppException {

		super.throwIfMissing(session, beforeSubmitMissingSession);
		this.session = session;
		final Request request = (Request) obj;
		if (request != null) {
			super.validateCaptcha(session, request.getCaptcha(), errors);
			this.validate(obj, errors);
		}
	}

	/**
	 * Method used to run default Request validations
	 * 
	 * @param obj
	 *            the Request
	 * @param errors
	 *            the validation result
	 */
	@Override
	public void validate(Object obj, Errors errors) {

		final Request request = (Request) obj;
		Boolean userHasMaxOpenRequests = false;
		try {
			throwIfMissing(request, null);
			userHasMaxOpenRequests = userSessionInfoService.maybeIncrementUserRequestCount(session, request);
		} catch (AppException ex) {
			errors.rejectValue(ModelAttributeEnum.CAPTCHA_TEXT.getValue(),
					ValidationEnum.FAILURE.getValue());
		}
		if (userHasMaxOpenRequests) {
			errors.rejectValue(ModelAttributeEnum.CAPTCHA_TEXT.getValue(), 
					ValidationEnum.GREATER_THAN_MAX.getValue());
		}
	}

	@Value("${requestvalidationservice.beforesubmit.session}")
	private String beforeSubmitMissingSession;

	@Value("${requestvalidationservice.beforecommit.session}")
	private String beforeCommitMissingSession;

	@Value("${requestvalidationservice.beforecommit.request}")
	private String beforeCommitMissingRequest;

	@Value("${request.entry.id}")
	private Long requestEntryId;
}