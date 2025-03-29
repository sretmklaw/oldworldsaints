package com.cimeliarchium.enums;

public enum DisplayKeyEnum {

	ACCOUNT_LOCKED(
			"Locked: Please try again later."),

	ACCOUNT_LOCK_SUCCESS(
			"Successfully updated lock status for User ID "),

	ACCOUNT_LOCK_SKIPPED(
			"Skipped update lock status for User ID "),

	ACCOUNT_NOT_FOUND(
			"Not found: verify credentials."),

	ACCOUNT_UPDATE_SUCCESS(
			"Success: Please login."),

	ACCOUNT_UPDATE_DISABLED(
			"New account creation disabled. Please try again later."),

	INVALID_DATE_RANGE(
			"Must be in range: 2000-2099 A.D."),

	INVALID_SEARCH_TERM(
			"Search terms must be 3-24 characters (non-numeric)."),

	LOGIN_FAILED_ERROR(
			"Issue logging in. Try again later."),

	LOGIN_FAILED_INVALID(
			"Invalid or missing credentials."),

	LOGOUT_SUCCESS(
			"Logged out successfully."),

	RANDOM(
			"RANDOM"),

	REQUEST_FAILURE(
			"Problem submitting request"),

	REQUEST_SUCCESS(
			"Request submitted successfully"),

	SERVER_RESTART(
			"Server restart since last login. Please clear browser cache to view latest updates."),

	COMMIT_SUCCESS(
			"Committed Commemoration ID: "),

	SESSION_TIMEOUT(
			"Session expired due to inactivity."),

	UNKNOWN(
			"UNKNOWN");

	private final String value;

	DisplayKeyEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
