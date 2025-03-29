package com.cimeliarchium.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AboutControllerTest.class,
	AdminControllerTest.class,
	DropdownControllerTest.class,
	ErrorInfoControllerTest.class,
	LoginControllerTest.class,
	MainControllerTest.class,
	DonateControllerTest.class,
	RegisterControllerTest.class,
	SearchControllerTest.class
})
public class ControllerTestSuite { }
