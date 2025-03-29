package com.cimeliarchium.enums;

public enum ModelAttributeEnum {

	// Views

	HOME_VIEW(
			"/"),

	ABOUT_VIEW(
			"about"),

	DONATE_VIEW(
			"donate"),

	LOGIN_VIEW(
			"login"),

	REGISTER_VIEW(
			"register"),

	UPDATE_VIEW(
			"update"),

	MAIN_VIEW(
			"main"),

	SEARCH_VIEW(
			"search"),

	MAP_VIEW(
			"map"),

	ADMIN_VIEW(
			"admin"),

	ERROR_VIEW(
			"error"),

	// Controller fields

	ADMIN_BUTTON_IS_VISIBLE(
			"adminButtonIsVisible"),

	ALT_SEARCH_ICON(
			"altSearchIcon"),

	BUGFIX_REQUEST_LIST(
			"bugfixRequestList"),

	BY_DATE(
			"byDate"),

	BY_ID(
			"byId"),

	BY_TEXT(
			"byText"),

	BY_VALUE(
			"byValue"),

	CACHED_IMAGES_LIST(
			"cachedImagesList"),

	CALENDAR_ALT_REASON_LIST(
			"calendarAltReasonList"),

	CALENDAR_ALT_TYPE_LIST(
			"calendarAltTypeList"),

	CALENDAR_ID(
			"calendarId"),

	CALENDAR_LIST(
			"calendarList"),

	CAPTCHA_ENABLED(
			"captchaEnabled"),

	CAPTCHA_IMAGE(
			"captchaImage"),

	CAPTCHA_TEXT(
			"captcha"),

	CENTURY(
			"century"),

	CENTURY_LIST(
			"centuryList"),

	COMMEMORATION_ID(
			"commemorationId"),

	COMMEMORATION_ID_LIST(
			"commemorationIdList"),

	COMMEMORATION_TITLE_LIST(
			"commemorationTitleList"),

	COMMEMORATION_TYPE_LIST(
			"commemorationTypeList"),

	COMMIT_BUTTON_IS_VISIBLE(
			"commitButtonIsVisible"),

	CONFIRM(
			"confirm"),

	CONSTELLATION_LIST(
			"constellationList"),

	STAR_ASTERISM_LIST(
			"starAsterismList"),

	CONTRIBUTORS(
			"contributors"),

	CREED_LIST(
			"creedList"),

	CULMINATING_STAR(
			"culminatingStar"),

	CURRENT_PAGE_NUMBER(
			"currentPageNumber"),

	CURRENT_USER_ACTIVE_ENTRY_REQUEST_LIST(
			"currentUserActiveEntryRequestList"),

	DATE_SEARCH_BUTTON_IS_VISIBLE(
			"dateSearchButtonIsVisible"),

	DAY_OF_MONTH_LIST(
			"dayOfMonthList"),

	DONATE_BUTTON_IS_VISIBLE(
			"donateButtonIsVisible"),

	EMAIL(
			"email"),

	ERROR_FLASH_ATTRIBUTE(
			"errorFlashAttribute"),

	FEATURE_FLAG(
			"featureFlag"),

	FEATURE_FLAG_LIST(
			"featureFlagList"),

	FORM_ENABLED(
			"formEnabled"),

	FORM_HEADER(
			"formHeader"),

	HOUR_TAB_MAP(
			"hourTabMap"),

	JULIAN_DATE(
			"julianDate"),

	LITURGY_TYPE_LIST(
			"liturgyTypeList"),

	LOCATION_IMAGE_ID(
			"locationImageId"),

	LOCATION_IMAGE_MAP(
			"locationImageMap"),

	LOCATION_PATRONAGE_LIST(
			"locationPatronageList"),

	LOCATION_ROUTE_LIST(
			"locationRouteList"),

	LOCATION_TYPE_LIST(
			"locationTypeList"),

	LUNAR_PHASE(
			"lunarPhase"),

	MAINTENANCE_NOTIFICATION(
			"maintenanceNotification"),

	MAP_INSET(
			"mapInset"),

	NATION_ID(
			"nationId"),

	NATION_LIST(
			"nationList"),

	NEXT_LOCATION(
			"nextLocation"),

	OLD_PASSWORD(
			"oldPassword"),

	OPEN_REQUEST_COUNT(
			"openRequestCount"),

	PAGE_ERROR_MESSAGE(
			"pageErrorMessage"),

	PAGE_NUMBER_LIST(
			"pageNumberList"),

	PASSWORD(
			"password"),

	PASSWORD_CONFIRM(
			"passwordConfirm"),

	PATRONAGE_COORDINATES(
			"patronageCoordinates"),

	PATRONAGE_IMAGE_ID(
			"patronageImageId"),

	PATRONAGE_TYPE_LIST(
			"patronageTypeList"),

	PAYPAL_ORDER(
			"payPalOrder"),

	PAYPAL_ORDER_AMOUNT(
			"paypalOrderAmount"),

	PAYPAL_ORDER_ID(
			"paypalOrderId"),

	PREV_LOCATION(
			"prevLocation"),

	REDIRECT_TO(
			"redirect:"),

	REFERENCE_LINK_IS_VISIBLE(
			"referenceLinkIsVisible"),

	RELATED_SEARCH_LINK(
			"relatedSearchLink"),

	REGION(
			"region"),

	REGION_ID(
			"regionId"),

	REGION_LIST(
			"regionList"),

	REQUEST(
			"request"),

	REQUEST_BUTTON_IS_VISIBLE(
			"requestButtonIsVisible"),

	REQUEST_BUTTON_IS_DISABLED(
			"requestButtonIsDisabled"),

	RESULT_MAP(
			"resultMap"),

	RESULT_SIZE(
			"resultSize"),

	RITE_ID(
			"riteId"),

	RITE_LIST(
			"riteList"),

	ROUTE_LIST(
			"routeList"),

	SEARCH_CRITERIA(
			"searchCriteria"),

	SEARCH_DETAIL(
			"searchDetail"),

	SEARCH_ICON(
			"searchIcon"),

	SEARCH_ID(
			"searchId"),

	SEARCH_PATH(
			"searchPath"),

	SHOW_ALL(
			"showAll"),

	SHOW_AUDIO(
			"showAudio"),

	SHOW_FOR_DATE(
			"showForDate"),

	SHOW_FOR_DATE_ERROR(
			"showForDateError"),

	STAR_COORDINATES(
			"starCoordinates"),

	STAR_LIST(
			"starList"),

	TAG(
			"tag"),

	TAG_LIST(
			"tagList"),

	THIS_LOCATION(
			"thisLocation"),

	THIS_PATRONAGE(
			"thisPatronage"),

	THIS_STAR(
			"thisStar"),

	UPDATE_BUTTON_IS_VISIBLE(
			"updateButtonIsVisible"),

	USER(
			"user"),

	USERNAME(
			"username"),

	USER_CALENDAR(
			"userCalendar"),

	USER_NATION(
			"userNation"),

	USER_REGION(
			"userRegion"),

	USER_RITE(
			"userRite"),

	USER_SESSION_INFO_LIST(
			"userSessionInfoList"),

	USER_SHOW_NOTIFICATION(
			"userShowNotification"),

	ZODIAC_SIGN(
			"zodiacSign");

	private final String value;

	ModelAttributeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
