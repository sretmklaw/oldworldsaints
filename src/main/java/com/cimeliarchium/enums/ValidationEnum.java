package com.cimeliarchium.enums;

public enum ValidationEnum {

	DIFFERENT(
			"different"),
	
	DUPLICATE(
			"duplicate"),

	FAILURE(
			"failure"),

	IMPROPER_DELIMIT(
			"improperDelimit"),

	IMPROPER_FORMAT(
			"improperFormat"),

	IMPROPER_LENGTH(
			"improperLength"),

	GREATER_THAN_MAX(
			"greaterThanMax"),

	MISSING(
			"missing");

	private final String value;

	ValidationEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
