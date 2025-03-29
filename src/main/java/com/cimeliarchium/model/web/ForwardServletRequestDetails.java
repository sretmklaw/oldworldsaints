package com.cimeliarchium.model.web;

import java.util.Map;

import org.springframework.ui.Model;

import com.cimeliarchium.enums.ModelAttributeEnum;

/**
 * Class used to add success or failure messages as flash attributes for display on redirect.
 */
public class ForwardServletRequestDetails {

	private ForwardServletRequestDetails() {}

	public static class Builder 
			extends BaseServletRequestBuilder<ForwardServletRequestDetails.Builder> 
			implements ServletRequestDetailsIF {

		protected Model model;
		protected Map<String,Object> attributes;

		public ForwardServletRequestDetails.Builder withModel(Model model) {
			this.model = model;
			return this;
		}

		public ForwardServletRequestDetails.Builder withAttributes(Map<String,Object> attributes) {
			this.attributes = attributes;
			return this;
		}

		public String build() {
			// Failure action
			if (hasFailures != null && hasFailures == true && model != null) {
				model.addAttribute(ModelAttributeEnum.PAGE_ERROR_MESSAGE.getValue(), failureMessage);
				return failureView;
			} 
			// Success action
			else if (attributes != null && model != null) {
				for (Map.Entry<String,Object> attribute : attributes.entrySet()) {
					model.addAttribute(attribute.getKey(), attribute.getValue());
				}
				model.addAttribute(ModelAttributeEnum.CONFIRM.getValue(), successMessage);
				return successView;
			}
			return null;
		}
	}
}
