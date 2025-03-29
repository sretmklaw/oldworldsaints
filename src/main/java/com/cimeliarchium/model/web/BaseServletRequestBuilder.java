package com.cimeliarchium.model.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cimeliarchium.enums.ModelAttributeEnum;

@SuppressWarnings("unchecked")
public class BaseServletRequestBuilder<Val> {

	public static final String REDIRECT_PATH = ModelAttributeEnum.REDIRECT_TO.getValue();
	public static final String ERROR_ATTR = ModelAttributeEnum.ERROR_FLASH_ATTRIBUTE.getValue();

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	protected String successView;
	protected String successMessage;

	protected Boolean hasFailures;
	protected String failureView;
	protected String failureMessage;

	public Val withRequest(HttpServletRequest request) {
		this.request = request;
		return (Val) this;
	}

	public Val withResponse(HttpServletResponse response) {
		this.response = response;
		return (Val) this;
	}

	public Val withSuccessView(String successView) {
		this.successView = successView;
		return (Val) this;
	}

	public Val withSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
		return (Val) this;
	}

	public Val withHasFailures(Boolean hasFailures) {
		this.hasFailures = hasFailures;
		return (Val) this;
	}

	public Val withFailureView(String failureView) {
		this.failureView = failureView;
		return (Val) this;
	}

	public Val withFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
		return (Val) this;
	}
}
