package com.cimeliarchium.enums;

import java.util.LinkedList;
import java.util.List;

/**
 * This enumeration stores info about fields shown inside the 'How To' article portion of the 'About' view.
 * Note that Request-specific fields are stored separately, under a separate dedicated enumeration. 
 */
public enum InputFieldEnum {

	/**
	 * Registration input fields follow
	 */

	REGISTER_USERNAME(
			"Username",
			"Required. An 8-24 character personal identifier.",
			false),

	REGISTER_EMAIL(
			"Email",
			"Required. A valid email address of no more than 32 characters.",
			false),

	REGISTER_PASSWORD(
			"Password",
			"Required. An 8-24 character word or phrase known only to you.",
			false),

	REGISTER_CALENDAR(
			"Calendar",
			"Required. Displays a subset of all available commemorations.",
			true),

	REGISTER_RITE(
			"Rite",
			"Applicable only to calendars used by multiple sects. Filters commemorations shown on main and search pages.",
			true),

	REGISTER_NATION(
			"Nation",
			"Required. Determines user location and time-zone.",
			true),

	REGISTER_REGION(
			"Region",
			"Applicable only to nations with multiple time-zones. Determines time of day for main page hour selection.",
			true),

	/**
	 * Search input fields follow
	 */

	SEARCH_BY_NAME(
			"Name",
			"Lookup by title or name containing a given search term.",
			false),

	SEARCH_BY_NATIONALITY(
			"Nationality",
			"Lookup by occurrence within the territory of a given country.",
			true),

	SEARCH_BY_CENTURY(
			"Century",
			"Lookup by occurrence within a specified number of years.",
			true),

	SEARCH_BY_TAG(
			"Tag",
			"Lookup by membership within a specific predefined group.",
			true),

	SEARCH_BY_CALENDAR_MONTH_DAY(
			"Day",
			"Lookup by day of month for a given calendar.",
			true),

	SEARCH_BY_PATRONAGE_TYPE_SUBTYPE(
			"Patronage",
			"Lookup by affiliation with specific categories and subcategories.",
			true),

	SEARCH_BY_CONSTELLATION_STAR(
			"Star",
			"Lookup by star within a given constellation.",
			true);

	private final String fieldName;

	private final String detail;

	private final Boolean isDropdownField;

	InputFieldEnum(String fieldName, String detail, Boolean isDropdownField) {
		this.fieldName = fieldName;
		this.detail = detail;
		this.isDropdownField = isDropdownField;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getDetail() {
		return detail;
	}

	public Boolean getIsDropdownField() {
		return isDropdownField;
	}

	public static List<InputFieldEnum> getRegisterInputFields() {
		final List<InputFieldEnum> resultList = new LinkedList<>();
		resultList.add(REGISTER_USERNAME);
		resultList.add(REGISTER_EMAIL);
		resultList.add(REGISTER_PASSWORD);
		resultList.add(REGISTER_CALENDAR);
		resultList.add(REGISTER_RITE);
		resultList.add(REGISTER_NATION);
		resultList.add(REGISTER_REGION);
		return resultList;
	}

	public static List<InputFieldEnum> getSearchInputFields() {
		final List<InputFieldEnum> resultList = new LinkedList<>();
		resultList.add(SEARCH_BY_NAME);
		resultList.add(SEARCH_BY_NATIONALITY);
		resultList.add(SEARCH_BY_CENTURY);
		resultList.add(SEARCH_BY_TAG);
		resultList.add(SEARCH_BY_CALENDAR_MONTH_DAY);
		resultList.add(SEARCH_BY_PATRONAGE_TYPE_SUBTYPE);
		resultList.add(SEARCH_BY_CONSTELLATION_STAR);
		return resultList;
	}
}
