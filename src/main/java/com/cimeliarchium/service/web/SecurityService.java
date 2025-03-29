package com.cimeliarchium.service.web;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
public class SecurityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

	/**
	 * Method used to obtain current user credential from SecurityContext
	 * 
	 * @return username
	 */
	public String getAuthenticatedUserCredential() {

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (principal instanceof org.springframework.security.core.userdetails.User) 
				? ((org.springframework.security.core.userdetails.User) principal).getUsername() : null;
	}

	/**
	 * Method used to clear the current user credential in SecurityContext
	 * 
	 * @return isCredentialReset
	 */
	public Boolean resetAuthenticatedUserCredential() {

		SecurityContextHolder.clearContext();
		return SecurityContextHolder.getContext() == null;
	}

	/**
	 * Method used to parse error codes from generic Exception
	 * 
	 * @param ex the Exception
	 * @return errorCode
	 */
	public String parseErrorCode(Exception ex, HttpStatus statusCode) {

		StringBuilder errorCodeBuilder = new StringBuilder();
		String errorCode = ex.getMessage();

		// Ensure stack trace is logged, since this method now handles all Exceptions
		LOGGER.error("ERROR: ", ex);

		if (errorCode != null && statusCode.is5xxServerError()) {
			errorCodeBuilder.append(errorCode);
		}
		return SessionAttributeHelperService.filterInput(errorCodeBuilder.toString());
	}

	/**
	 * Method used to parse resource requests
	 * 
	 * @param request
	 *            the HttpServletRequest
	 * @param response
	 *            the HttpServletResponse
	 * @return ResourceRequest
	 */
	public String parseResourceRequest(
			HttpServletRequest request, 
			HttpServletResponse response) {

		// Obtain the request URI and response HTTPStatusCode and filter null values
		final String filteredUri = SessionAttributeHelperService.filterInput(request.getServletPath());

		// Parse missing resource request URI
		if (filteredUri.equals(DisplayKeyEnum.UNKNOWN.getValue())) {
			return filteredUri;
		}
		// Parse non-null resource request URI
		final String prefix = "/";
		StringBuilder parsedRequestBuilder = new StringBuilder();

		// Root resource indicates failure on Login, so show that as path
		if (filteredUri.equals(prefix)) {
			parsedRequestBuilder.append("login");
		} 
		// Always remove the path prefix for readability
		else if (filteredUri.startsWith(prefix)) {
			parsedRequestBuilder.append(filteredUri.replaceFirst(prefix, ""));
		}
		// Append any available request parameters where not error
		if (!parsedRequestBuilder.toString().equals(ModelAttributeEnum.ERROR_VIEW.getValue())) {
			final String query = request.getQueryString();
			//final String query = (String) request.getAttribute("javax.servlet.error.request_uri");
			if (query != null && !query.equals(prefix)) {
				parsedRequestBuilder.append("?");
				// Trim really long query strings to prevent overflow errors
				if (query.length() > 30) {
					parsedRequestBuilder.append(query.subSequence(0, 30)).append("...");
				} else {
					parsedRequestBuilder.append(query);
				}
			}
		}
		return SessionAttributeHelperService.filterInput(parsedRequestBuilder.toString());
	}

	/**
	 * Method used to parse HTTP Status from the Exception
	 * 
	 * @param ex
	 *            the Exception
	 * @return HttpStatus
	 */
	public HttpStatus parseErrorType(Exception ex) {

		HttpStatus statusCode = null;
		if (ex != null && ex instanceof AppException) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		} else {
			statusCode = HttpStatus.BAD_REQUEST;
		}
		return statusCode;
	}

	/**
	 * Method used to parse the originating IP address
	 * 
	 * @param request
	 *            the HttpServletRequest
	 * @return RequestOrigin
	 */
	public String parseRequestOrigin(HttpServletRequest request) {

		return SessionAttributeHelperService.filterInput(request.getRemoteAddr());
	}

	public void resetSession(HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session != null) {
			// Destroy all existing session variables
			for (String attributeName : Collections.list(session.getAttributeNames())) {
				session.removeAttribute(attributeName);
			}
			// Prevent session fixation based on cached security context
			this.resetAuthenticatedUserCredential();
			// Finally, invalidate the existing session
			session.invalidate();
		}
	}
}