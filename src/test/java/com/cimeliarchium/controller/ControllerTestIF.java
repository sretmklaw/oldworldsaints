package com.cimeliarchium.controller;

import com.cimeliarchium.controller.ControllerTestRunner.Builder;

public interface ControllerTestIF {

	public Builder whenAnonymousUserRequests(String pathSuffix);

	public Builder whenStandardUserRequests(String pathSuffix);

	public Builder whenAdminUserRequests(String pathSuffix);
}
