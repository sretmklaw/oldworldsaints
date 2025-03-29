package com.cimeliarchium.enums;

public enum SessionAttributeEnum {

	NATION_REGION_MAP(
			"_nationRegionMap"),

	NATION_LOCATION_MAP(
			"_nationLocationMap"),

	CAPTCHA(
			"_captcha"),

	CURRENT_LUNAR_PHASE(
			"_currentLunarPhase"),

	CURRENT_USER_DETAILS(
			"_currentUserDetails"),

	CYCLE_DAY_LITURGY_TYPE_MAP(
			"_cycleDayLiturgyTypeMap"),

	DAY_COMMEMORATION_MAP(
			"_dayCommemorationMap"),

	DAY_COMMEMORATION_MAP_IDS_BY_RITE(
			"_dayCommemorationMapIdsByRite"),

	DAY_COMMEMORATION_MAP_NAMES_BY_RITE(
			"_dayCommemorationMapNamesByRite"),

	DAY_COMMEMORATION_MAP_BY_ID(
			"_dayCommemorationMapById"),

	DAY_COMMEMORATION_MAP_BY_DAY(
			"_dayCommemorationMapByDay"),

	DAY_COMMEMORATION_MAP_BY_NATION(
			"_dayCommemorationMapByNation"),

	DAY_COMMEMORATION_MAP_BY_CENTURY(
			"_dayCommemorationMapByCentury"),

	DAY_COMMEMORATION_MAP_BY_PATRONAGE(
			"_dayCommemorationMapByPatronage"),

	DAY_COMMEMORATION_MAP_BY_STAR(
			"_dayCommemorationMapByStar"),

	DAY_COMMEMORATION_MAP_BY_TAG(
			"_dayCommemorationMapByTag"),

	DAY_COMMEMORATION_MAP_BY_LOCATION(
			"_dayCommemorationMapByLocation"),

	DAY_COMMEMORATION_MAP_BY_NAME(
			"_dayCommemorationMapByName"),

	DAY_COMMEMORATION_MAP_BY_ENTITY_CODE_ID(
			"_dayCommemorationMapByEntityCodeId"),

	COMMEMORATION_READING_MAP_BY_TITLE(
			"_commemorationReadingMapByTitle"),

	COMMEMORATION_READING_MAP_BY_ID(
			"_commemorationReadingMapById"),

	CONSTELLATION_STAR_MAP(
			"_constellationStarMap"),

	ENTITY_UPDATE_HISTORY_MAP_BY_CODE_ID_KEY(
			"_entityUpdateHistoryMapByCodeIdKey"),

	ENTITY_UPDATE_HISTORY_LIST_FOR_USER(
			"_entityUpdateHistoryMapForUserByCommemorationId"),

	HOUR_READING_MAP(
			"_hourReadingMap"),

	LUNAR_PHASE_MAP_BY_ID(
			"_lunarPhaseMapById"),

	PATRONAGE_TYPE_MAP(
			"_patronageTypeMap"),

	PATRONAGE_SUBTYPE_MAP(
			"_patronageSubtypeMap"),

	USER_MAP(
			"_userMap"),

	REQUEST_MAP_BY_TYPE(
			"_requestMapByType"),

	REQUEST_MAP_BY_ID(
			"_requestMapById"),

	FEATURE_FLAG_MAP_BY_ID(
			"_featureFlagMapById"),

	NATION_MAP_BY_ID(
			"_nationMapById"),

	LOCATION_MAP_BY_ID(
			"_locationMapById"),

	LOCATION_TYPE_MAP_BY_ID(
			"_locationTypeMapById"),

	INSET_MAP_BY_ID(
			"_insetMapById"),

	DAY_MAP_BY_ID(
			"_dayMapById"),

	DAY_OF_MONTH_MAP_BY_ID(
			"_dayOfMonthMapById"),

	DAY_MAP_BY_MONTH(
			"_dayMapByMonth"),

	MONTH_MAP_BY_CALENDAR(
			"_monthMapByCalendar"),

	CENTURY_MAP_BY_ID(
			"_centuryMapById"),

	COMMEMORATION_TYPE_MAP_BY_ID(
			"_commemorationTypeMapById"),

	CREED_MAP_BY_ID(
			"_creedMapById"),

	CALENDAR_ALT_TYPE_MAP_BY_ID(
			"_calendarAltTypeMapById"),

	CALENDAR_ALT_REASON_MAP_BY_ID(
			"_calendarAltReasonMapById"),

	PATRONAGE_MAP_BY_ID(
			"_patronageMapById"),

	REFERENCE_MAP_BY_TITLE(
			"_referenceMapByTitle"),

	REFERENCE_MAP_BY_ID(
			"_referenceMapById"),

	ROUTE_MAP_BY_ID(
			"_routeMapById"),

	ROUTE_MAP_BY_LOCATION_ID(
			"_routeMapByLocationId"),

	ASTERISM_MAP_BY_STAR_ID(
			"_asterismMapByStarId"),

	STAR_MAP_BY_ID(
			"_starMapById"),

	TAG_MAP_BY_ID(
			"_tagMapById"),

	USER_IS_AUTHENTICATED(
			"_userIsAuthenticated");

	private final String value;

	SessionAttributeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
