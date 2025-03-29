package com.cimeliarchium.enums;

import java.util.Arrays;
import java.util.List;

public enum RequestFieldEnum {

	/**
	 * Commemoration
	 */

	CREED_ID(
			"creedId",
			"*Creed",
			1,
			10,
			"*Creed"),

	COMMEMORATION_TYPE_ID(
			"commemorationTypeId",
			"*Type",
			1,
			10,
			"int"),

	CENTURY_ID(
			"centuryId",
			"*Century",
			1,
			10,
			"int"),

	CALENDAR_ID(
			"calendarId",
			null,
			1,
			10,
			"int"),

	MONTH_ID(
			"monthId",
			null,
			1,
			10,
			"int"),

	DAY_ID(
			"dayId",
			"*Primary_Day",
			1,
			10,
			"int"),

	COMMEMORATION_YEAR_AD(
			"commemorationYearAd",
			"Year_A.D.",
			1,
			4,
			"int"),

	ALT_DAY_AD_ID(
			"altDayAdId",
			"Alt_Day_A.D.",
			1,
			10,
			"int"),

	COMMEMORATION_YEAR_AH(
			"commemorationYearAh",
			"Year_A.H.",
			1,
			4,
			"int"),

	ALT_DAY_AH_ID(
			"altDayAhId",
			"Alt_Day_A.H.",
			1,
			10,
			"int"),

	COMMEMORATION_YEAR_AM(
			"commemorationYearAm",
			"Year_A.M.",
			1,
			4,
			"int"),

	ALT_DAY_AM_ID(
			"altDayAmId",
			"Alt_Day_A.M.",
			1,
			10,
			"int"),

	CALENDAR_ALT_TYPE_ID(
			"calendarAltTypeId",
			"Cal_Alt_Type",
			1,
			10,
			"int"),

	CALENDAR_ALT_REASON_ID(
			"calendarAltReasonId",
			"Cal_Alt_Reason",
			1,
			10,
			"int"),

	COMMEMORATION_ID(
			"commemorationId",
			null,
			1,
			10,
			"int"),

	COMMEMORATION_OVERRIDE_ID(
			"commemorationOverrideId",
			null,
			1,
			10,
			"int"),

	CONTRIBUTORS(
			"contributors",
			"Contributors",
			1,
			500,
			"alphaNumAscii"),

	COMMEMORATION_NAME(
			"commemorationName",
			"*Name",
			1,
			50,
			"alphaLtd"),

	COMMEMORATION_ALT_NAME(
			"commemorationAltName",
			"Alt_Name",
			1,
			50,
			"alphaLtd"),

	COMMEMORATION_DETAIL(
			"commemorationDetail",
			"*Detail",
			1,
			500,
			"alphaFull"),

	/**
	 * Tag
	 */

	TAG_A_ID(
			"tagAId",
			"Tag_A",
			1,
			10,
			"int"),

	TAG_B_ID(
			"tagBId",
			"Tag_B",
			1,
			10,
			"int"),

	TAG_C_ID(
			"tagCId",
			"Tag_C",
			1,
			10,
			"int"),

	TAG_D_ID(
			"tagDId",
			"Tag_D",
			1,
			10,
			"int"),

	TAG_E_ID(
			"tagEId",
			"Tag_E",
			1,
			10,
			"int"),

	/**
	 * Location
	 */

	NATION_ID(
			"nationId",
			null,
			1,
			10,
			"int"),

	LOCATION_ID(
			"locationId",
			null,
			1,
			10,
			"int"),

	LOCATION_OVERRIDE_ID(
			"locationOverrideId",
			null,
			1,
			10,
			"int"),

	LOCATION_LABEL_NAME(
			"locationLabelName",
			"*Name",
			1,
			50,
			"alphaLtd"),

	LOCATION_TYPE_ID(
			"locationTypeId",
			"*Type",
			1,
			10,
			"int"),

	LOCATION_LATITUDE(
			"locationPointX",
			"*Latitude",
			1,
			10,
			"float"),

	LOCATION_LONGITUDE(
			"locationPointY",
			"*Longitude",
			1,
			10,
			"float"),

	LOCATION_RELATED_PATRONAGE_SUBTYPE_ID(
			"locationRelatedPatronageSubtypeId",
			"Patron_Type",
			1,
			10,
			"int"),

	LOCATION_RELATED_PATRONAGE_ID(
			"locationRelatedPatronageId",
			"Patron_Name",
			1,
			10,
			"int"),

	LOCATION_IMAGE(
			"locationImage",
			"Image_ID",
			1,
			33,
			"alphaNumAscii"),

	LOCATION_ALT_INSET_ID(
			"locationAltInsetId",
			"Inset_ID",
			1,
			10,
			"int"),

	LOCATION_PREV_LOCATION_ID(
			"prevLocationId",
			"*Prev_Loc",
			1,
			10,
			"int"),

	LOCATION_PREV_NATION_ID(
			"prevNationId",
			"*Prev_Nat",
			1,
			10,
			"int"),

	LOCATION_NEXT_LOCATION_ID(
			"nextLocationId",
			"*Next_Loc",
			1,
			10,
			"int"),

	LOCATION_NEXT_NATION_ID(
			"nextNationId",
			"*Next_Nat",
			1,
			10,
			"int"),

	ROUTE_A_ID(
			"routeAId",
			"Route_A",
			1,
			10,
			"int"),

	ROUTE_B_ID(
			"routeBId",
			"Route_B",
			1,
			10,
			"int"),

	ROUTE_C_ID(
			"routeCId",
			"Route_C",
			1,
			10,
			"int"),

	ROUTE_D_ID(
			"routeDId",
			"Route_D",
			1,
			10,
			"int"),

	ROUTE_E_ID(
			"routeEId",
			"Route_E",
			1,
			10,
			"int"),

	ALT_LOCATION_ID(
			"altLocationId",
			null,
			1,
			10,
			"int"),

	ALT_LOCATION_OVERRIDE_ID(
			"altLocationOverrideId",
			null,
			1,
			10,
			"int"),

	ALT_LOCATION_LABEL_NAME(
			"altLocationLabelName",
			"*Name",
			1,
			50,
			"alphaLtd"),

	ALT_LOCATION_TYPE_ID(
			"altLocationTypeId",
			"*Type",
			1,
			10,
			"int"),

	ALT_LOCATION_LATITUDE(
			"altLocationPointX",
			"*Latitude",
			1,
			10,
			"float"),

	ALT_LOCATION_LONGITUDE(
			"altLocationPointY",
			"*Longitude",
			1,
			10,
			"float"),

	ALT_LOCATION_PREV_LOCATION_ID(
			"altPrevLocationId",
			"*Prev_Loc",
			1,
			10,
			"int"),

	ALT_LOCATION_NEXT_LOCATION_ID(
			"altNextLocationId",
			"*Next_Loc",
			1,
			10,
			"int"),

	ALT_LOCATION_RELATED_PATRONAGE_SUBTYPE_ID(
			"altLocationRelatedPatronageSubtypeId",
			"Patron_Type",
			1,
			10,
			"int"),

	ALT_LOCATION_RELATED_PATRONAGE_ID(
			"altLocationRelatedPatronageId",
			"Patron_Name",
			1,
			10,
			"int"),

	ALT_LOCATION_IMAGE(
			"altLocationImage",
			"Image_ID",
			1,
			33,
			"alphaNumAscii"),

	ALT_LOCATION_INSET_ID(
			"altLocationInsetId",
			"Inset_ID",
			1,
			10,
			"int"),

	/**
	 * Patronage
	 */

	PATRONAGE_ID(
			"patronageId",
			null,
			1,
			10,
			"int"),

	PATRONAGE_OVERRIDE_ID(
			"patronageOverrideId",
			null,
			1,
			10,
			"int"),

	PATRONAGE_TYPE_ID(
			"patronageTypeId",
			"*Type",
			1,
			10,
			"int"),

	PATRONAGE_SUBTYPE_ID(
			"patronageSubtypeId",
			"*Subtype",
			1,
			10,
			"int"),

	PATRONAGE_NAME(
			"patronageName",
			"*Name",
			1,
			50,
			"alphaLtd"),

	PATRONAGE_RELATED_SEARCH_LINK(
			"patronageRelatedSearchLink",
			"Search_Link",
			1,
			33,
			"alphaNumAscii"),

	PATRONAGE_LATITUDE(
			"patronagePointX",
			"*Latitude",
			1,
			10,
			"float"),

	PATRONAGE_LONGITUDE(
			"patronagePointY",
			"*Longitude",
			1,
			10,
			"float"),

	/**
	 * Reading
	 */

	READING_ID(
			"readingId",
			null,
			1,
			10,
			"int"),

	READING_OVERRIDE_ID(
			"readingOverrideId",
			null,
			1,
			10,
			"int"),

	READING_TITLE(
			"readingTitle",
			null,
			1,
			50,
			"alphaNumLtd"),

	READING_NAME(
			"readingName",
			"*Name",
			1,
			50,
			"alphaNumLtd"),

	READING_CHAPTER_VERSE(
			"readingNameAlt",
			"*Chapter/Verse",
			1,
			50,
			"alphaNumFull"),

	READING_CONTENT(
			"readingContent",
			"*Content",
			1,
			10000,
			"alphaNumFull"),

	/**
	 * Reference
	 */

	REFERENCE_ID(
			"referenceId",
			null,
			1,
			10,
			"int"),

	REFERENCE_OVERRIDE_ID(
			"referenceOverrideId",
			null,
			1,
			10,
			"int"),
	
	REFERENCE_TITLE(
			"referenceTitle",
			null,
			1,
			50,
			"alphaNumFull"),

	REFERENCE_NAME(
			"referenceName",
			"*Name",
			1,
			100,
			"alphaNumFull"),

	REFERENCE_VOLUME(
			"referenceVolume",
			"*Volume",
			1,
			10,
			"int"),

	REFERENCE_START(
			"referenceStart",
			"*FromPage",
			1,
			10,
			"int"),

	REFERENCE_END(
			"referenceEnd",
			"*ToPage",
			1,
			10,
			"int"),

	REFERENCE_PDF(
			"referencePdf",
			"PDF",
			1,
			50,
			"alphaNumAscii"),

	REFERENCE_PDF_LINK(
			"referencePdfLink",
			"Link",
			1,
			100,
			"alphaNumAscii"),

	/**
	 * Star
	 */

	CONSTELLATION_ID(
			"constellationId",
			null,
			1,
			10,
			"int"),

	CONSTELLATION_NAME(
			"constellationName",
			"Constellation",
			1,
			50,
			"alphaLtd"),

	STAR_ID(
			"starId",
			null,
			1,
			10,
			"int"),

	STAR_NAME(
			"starName",
			"Star",
			1,
			50,
			"alphaLtd");

	private final String fieldName;
	private final String displayName;
	private final Integer minLength;
	private final Integer maxLength;
	private final String regex;

	RequestFieldEnum(String fieldName, String displayName, 
			Integer minLength, Integer maxLength, String regex) {
		this.fieldName = fieldName;
		this.displayName = displayName;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.regex = translateRegexType(regex);
	}

	private static String translateRegexType(String regex) {
		switch(regex) {
			case "int" : return RegexType.NUMERIC_INTEGER_REGEX;
			case "float" : return RegexType.NUMERIC_FLOAT_REGEX;
			case "alphaFull" : return RegexType.ALPHABETIC_FULL_SYMBOLS_REGEX;
			case "alphaLtd" : return RegexType.ALPHABETIC_LIMITED_SYMBOLS_REGEX;
			case "alphaNumFull" : return RegexType.ALPHANUMERIC_FULL_SYMBOLS_REGEX;
			case "alphaNumLtd" : return RegexType.ALPHANUMERIC_LIMITED_SYMBOLS_REGEX;
			case "alphaNumAscii" : return RegexType.ALPHANUMERIC_ASCII_SYMBOLS_REGEX;
			default : return ".*";
		}
	}

	private static class RegexType {
		private static final String NUMERIC_INTEGER_REGEX = "[0-9]+";
		private static final String NUMERIC_FLOAT_REGEX = "[\\-0-9]+\\.[0-9]+";
		private static final String ALPHABETIC_FULL_SYMBOLS_REGEX = "[a-zA-Z \\/',.!?:;\\-\\(\\)\\[\\]&]+";
		private static final String ALPHABETIC_LIMITED_SYMBOLS_REGEX = "[a-zA-Z \\/',.\\-]+";
		private static final String ALPHANUMERIC_FULL_SYMBOLS_REGEX = "[a-zA-Z0-9 \\/',.!?:;\\-\\(\\)\\[\\]&]+";
		private static final String ALPHANUMERIC_LIMITED_SYMBOLS_REGEX = "[a-zA-Z0-9 \\/',.\\-]+";
		private static final String ALPHANUMERIC_ASCII_SYMBOLS_REGEX = "[a-zA-Z0-9 \\/|'\",.!?:;\\-\\(\\)\\[\\]@#$%^&*_+=~<>]+";
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public String getRegex() {
		return regex;
	}

	public static List<String> getAdministratorSpecificFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.CONTRIBUTORS.getDisplayName());
	}

	public static List<String> getCommemorationRequiredFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.CREED_ID.getDisplayName(),
				RequestFieldEnum.COMMEMORATION_TYPE_ID.getDisplayName(),
				RequestFieldEnum.CENTURY_ID.getDisplayName(),
				RequestFieldEnum.DAY_ID.getDisplayName(),
				RequestFieldEnum.COMMEMORATION_YEAR_AD.getDisplayName(),
				RequestFieldEnum.ALT_DAY_AD_ID.getDisplayName(),
				RequestFieldEnum.COMMEMORATION_NAME.getDisplayName(),
				RequestFieldEnum.COMMEMORATION_DETAIL.getDisplayName());
	}

	public static List<String> getCommemorationOptionalFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.COMMEMORATION_YEAR_AH.getDisplayName(),
				RequestFieldEnum.ALT_DAY_AH_ID.getDisplayName(),
				RequestFieldEnum.COMMEMORATION_YEAR_AM.getDisplayName(),
				RequestFieldEnum.ALT_DAY_AM_ID.getDisplayName(),
				RequestFieldEnum.CALENDAR_ALT_TYPE_ID.getDisplayName(),
				RequestFieldEnum.CALENDAR_ALT_REASON_ID.getDisplayName(),
				RequestFieldEnum.COMMEMORATION_ALT_NAME.getDisplayName(),
				RequestFieldEnum.STAR_NAME.getDisplayName());
	}

	public static List<String> getTagRequiredFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.TAG_A_ID.getDisplayName());
	}

	public static List<String> getTagOptionalFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.TAG_B_ID.getDisplayName(),
				RequestFieldEnum.TAG_C_ID.getDisplayName(),
				RequestFieldEnum.TAG_D_ID.getDisplayName(),
				RequestFieldEnum.TAG_E_ID.getDisplayName());
	}

	public static List<String> getLocationRequiredFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.LOCATION_LABEL_NAME.getDisplayName());
	}

	public static List<String> getLocationOptionalFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.LOCATION_TYPE_ID.getDisplayName(),
				RequestFieldEnum.LOCATION_LATITUDE.getDisplayName(),
				RequestFieldEnum.LOCATION_LONGITUDE.getDisplayName(),
				RequestFieldEnum.LOCATION_RELATED_PATRONAGE_SUBTYPE_ID.getDisplayName(),
				RequestFieldEnum.LOCATION_RELATED_PATRONAGE_ID.getDisplayName(),
				RequestFieldEnum.LOCATION_IMAGE.getDisplayName(),
				RequestFieldEnum.LOCATION_PREV_LOCATION_ID.getDisplayName(),
				RequestFieldEnum.LOCATION_NEXT_LOCATION_ID.getDisplayName(),
				RequestFieldEnum.ROUTE_A_ID.getDisplayName(),
				RequestFieldEnum.ROUTE_B_ID.getDisplayName(),
				RequestFieldEnum.ROUTE_C_ID.getDisplayName(),
				RequestFieldEnum.ROUTE_D_ID.getDisplayName(),
				RequestFieldEnum.ROUTE_E_ID.getDisplayName(),
				RequestFieldEnum.LOCATION_ALT_INSET_ID.getDisplayName());
	}

	public static List<String> getAltLocationRequiredFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.ALT_LOCATION_LABEL_NAME.getDisplayName());
	}

	public static List<String> getAltLocationOptionalFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.ALT_LOCATION_TYPE_ID.getDisplayName(),
				RequestFieldEnum.ALT_LOCATION_LATITUDE.getDisplayName(),
				RequestFieldEnum.ALT_LOCATION_LONGITUDE.getDisplayName(),
				RequestFieldEnum.ALT_LOCATION_RELATED_PATRONAGE_SUBTYPE_ID.getDisplayName(),
				RequestFieldEnum.ALT_LOCATION_RELATED_PATRONAGE_ID.getDisplayName(),
				RequestFieldEnum.ALT_LOCATION_IMAGE.getDisplayName(),
				RequestFieldEnum.ALT_LOCATION_PREV_LOCATION_ID.getDisplayName(),
				RequestFieldEnum.ALT_LOCATION_NEXT_LOCATION_ID.getDisplayName(),
				RequestFieldEnum.ALT_LOCATION_INSET_ID.getDisplayName());
	}

	public static List<String> getPatronageOptionalFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.PATRONAGE_TYPE_ID.getDisplayName(),
				RequestFieldEnum.PATRONAGE_SUBTYPE_ID.getDisplayName(),
				RequestFieldEnum.PATRONAGE_NAME.getDisplayName(),
				RequestFieldEnum.PATRONAGE_RELATED_SEARCH_LINK.getDisplayName(),
				RequestFieldEnum.PATRONAGE_LATITUDE.getDisplayName(),
				RequestFieldEnum.PATRONAGE_LONGITUDE.getDisplayName());
	}

	public static List<String> getReadingOptionalFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.READING_NAME.getDisplayName(),
				RequestFieldEnum.READING_CHAPTER_VERSE.getDisplayName(),
				RequestFieldEnum.READING_CONTENT.getDisplayName());
	}

	
	public static List<String> getReferenceRequiredFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.REFERENCE_NAME.getDisplayName(),
				RequestFieldEnum.REFERENCE_VOLUME.getDisplayName(),
				RequestFieldEnum.REFERENCE_START.getDisplayName());
	}

	public static List<String> getReferenceOptionalFieldDisplayNames() {
		return Arrays.asList(
				RequestFieldEnum.REFERENCE_END.getDisplayName());
	}
}
