package com.cimeliarchium.model.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.cimeliarchium.enums.ModelAttributeEnum;

/**
 * Class used to add success or failure messages as flash attributes for display on redirect.
 */
public class RedirectServletRequestDetails {

	private RedirectServletRequestDetails() {}

	public static class Builder 
			extends BaseServletRequestBuilder<RedirectServletRequestDetails.Builder> 
			implements ServletRequestDetailsIF {

		public String build() {
			final FlashMap flashMap = new FlashMap();
			String viewName = successView;
			// Failure action
			if (hasFailures != null && hasFailures == true) {
				flashMap.put(ModelAttributeEnum.ERROR_FLASH_ATTRIBUTE.getValue(), failureMessage);
				viewName = failureView;
			} 
			// Success action
			else if (successMessage != null && !successMessage.isEmpty()) {
				flashMap.put(ModelAttributeEnum.CONFIRM.getValue(), successMessage);
			}
			// Populate the redirect flash attributes
			new SessionFlashMapManager().saveOutputFlashMap(flashMap, request, response);
			return REDIRECT_PATH + viewName;
		}
	}

	public static String redirectHomeWithMessage(HttpServletRequest request, HttpServletResponse response, String message) {
		return new RedirectServletRequestDetails.Builder()
				.withRequest(request)
				.withResponse(response)
				.withHasFailures(true)
				.withFailureView("/")
				.withFailureMessage(message)
				.build();
	}

	/**
	 * Method used to display a descriptive flash attribute message to the user on redirect.
	 * This handler is necessary because we must leverage overridden methods
	 * which does not allow us to directly add attributes on the Model object.
	 * 
	 * @param model the Model
	 * @param request the HttpServletRequest
	 */
	public static void addFlashMessageAsModelAttribute(
			Model model, 
			HttpServletRequest request) {

		final Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			final Object errorFlashAttribute = flashMap.get(ModelAttributeEnum.ERROR_FLASH_ATTRIBUTE.getValue());
			if (errorFlashAttribute != null) {
				model.addAttribute(ModelAttributeEnum.PAGE_ERROR_MESSAGE.getValue(), String.valueOf(errorFlashAttribute));
			}
			final Object confirmFlashAttribute = flashMap.get(ModelAttributeEnum.CONFIRM.getValue());
			if (confirmFlashAttribute != null) {
				model.addAttribute(ModelAttributeEnum.CONFIRM.getValue(), String.valueOf(confirmFlashAttribute));
			}
		}
	}

}
