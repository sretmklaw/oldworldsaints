package com.cimeliarchium.model.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "request")
public class Request extends BaseEntity {

	private static final int MAX_PATH_LENGTH = 30;

	@Id
	@NotNull
	@Column(name = "request_id")
	@GenericGenerator(name = "AppIdentityGenerator", strategy = "com.cimeliarchium.config.AppIdentityGenerator")
	@GeneratedValue(generator = "AppIdentityGenerator")
	private Long requestId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "username")
	private String username;

	@Column(name = "captcha")
	private String captcha;

	@Column(name = "request_type_id")
	private Long requestTypeId;

	@Column(name = "request_status_code")
	private String requestStatusCode;

	@Column(name = "request_detail")
	private String requestDetail;

	@Column(name = "error_path")
	private String errorPath;

	@Column(name = "error_ip")
	private String errorIp;

	@Column(name = "request_timestamp")
	private String requestTimestamp;

	@Column(name = "error_type")
	private String errorType;

	@Column(name = "error_code")
	private String errorCode;

	private Request() {}

	/**
	 * Used for pre-constructed Bugfix requests only
	 */
	public static class Builder {

		private Long requestId;
		private Long userId;
		private String username;
		private String captcha;
		private Long requestTypeId;
		private String requestStatusCode;
		private String errorPath;
		private String errorIp;
		private String requestTimestamp;
		private String requestDetail;
		private String errorType;
		private String errorCode;

		public Builder withRequestId(Long requestId) {
			this.requestId = requestId;
			return this;
		}

		public Builder withUserId(Long userId) {
			this.userId = userId;
			return this;
		}

		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder withCaptcha(String captcha) {
			this.captcha = captcha;
			return this;
		}

		public Builder withRequestTypeId(Long requestTypeId) {
			this.requestTypeId = requestTypeId;
			return this;
		}

		public Builder withRequestStatusCode(String requestStatusCode) {
			this.requestStatusCode = requestStatusCode;
			return this;
		}

		public Builder withRequestDetail(String requestDetail) {
			this.requestDetail = requestDetail;
			return this;
		}

		public Builder withErrorPath(String errorPath) {
			this.errorPath = errorPath;
			return this;
		}

		public Builder withErrorIp(String errorIp) {
			this.errorIp = errorIp;
			return this;
		}

		public Builder withRequestTimestamp(String requestTimestamp) {
			this.requestTimestamp = requestTimestamp;
			return this;
		}

		public Builder withErrorType(String errorType) {
			this.errorType = errorType;
			return this;
		}

		public Builder withErrorCode(String errorCode) {
			this.errorCode = errorCode;
			return this;
		}

		public Request build() {
			Request request = new Request();
			request.requestId = this.requestId;
			request.userId = this.userId;
			request.username = this.username;
			request.captcha = this.captcha;
			request.requestTypeId = this.requestTypeId;
			request.requestStatusCode = this.requestStatusCode;
			request.errorPath = this.errorPath;
			request.errorIp = this.errorIp;
			request.requestTimestamp = this.requestTimestamp;
			request.requestDetail = this.requestDetail;
			request.errorType = this.errorType;
			request.errorCode = this.errorCode;
			return request;
		}
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Long getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(Long requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

	public String getRequestStatusCode() {
		return requestStatusCode;
	}

	public void setRequestStatusCode(String requestStatusCode) {
		this.requestStatusCode = requestStatusCode;
	}

	public String getErrorPath() {
		return (errorPath != null && errorPath.length() > MAX_PATH_LENGTH) 
				? errorPath.substring(0, MAX_PATH_LENGTH) + "..."
				: errorPath;
	}

	public String getErrorPathRelative() {
		final String errorPathRelative = (errorPath != null && !errorPath.isEmpty()) 
				? errorPath.substring(0, errorPath.indexOf("?"))
				: errorPath;
		return (errorPathRelative != null && errorPathRelative.length() > MAX_PATH_LENGTH) 
				? errorPathRelative.substring(0, MAX_PATH_LENGTH) + "..."
				: errorPathRelative;
	}

	public List<String> getErrorPathParams() {
		// Obtain a null-safe list of parameters from the error path
		final String errorPathParams = (errorPath != null && !errorPath.isEmpty()) 
				? errorPath.substring(errorPath.indexOf("?") + 1, errorPath.length())
				: "";
		// Then, trim each parameter to maximum field display length
		final List<String> paramsList = new ArrayList<>();
		for (String param : Arrays.asList(errorPathParams.split("&"))) {
			final String trimmedParam = (param.length() > MAX_PATH_LENGTH) 
				? param.substring(0, MAX_PATH_LENGTH) + "..."
				: param;
			paramsList.add(trimmedParam);
		}
		return paramsList;
	}

	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

	public String getErrorIp() {
		return errorIp;
	}

	public void setErrorIp(String errorIp) {
		this.errorIp = errorIp;
	}

	public String getRequestTimestamp() {
		return requestTimestamp;
	}

	public void setRequestTimestamp(String requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}

	public String getRequestDetail() {
		return requestDetail;
	}

	public void setRequestDetail(String requestDetail) {
		this.requestDetail = requestDetail;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}