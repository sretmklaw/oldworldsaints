package com.cimeliarchium.enums;

public enum StatusCodeEnum {

	RED(
			"RED"),

	YELLOW(
			"YELLOW"),

	GREEN(
			"GREEN");

	private final String value;

	StatusCodeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
