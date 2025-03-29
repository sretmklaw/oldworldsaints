package com.cimeliarchium.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = 8088063508319441883L;

	public AppException(String message) {
		super(message); // Stores numeric Code to expose in Error Info
	}

}
