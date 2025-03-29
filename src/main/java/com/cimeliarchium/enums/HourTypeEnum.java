package com.cimeliarchium.enums;

import java.util.LinkedList;
import java.util.List;

/**
 * This enumeration provides static copies of rows stored in the database-backed 'hour_type' table.
 * Its purpose is to allow unauthenticated visitors to view a static listing of HourTypes 
 * from the 'About' view, without having to perform extraneous calls to the database.
 */
public enum HourTypeEnum {

	DAWN(1,
		"DAWN",
		"Matins",
		"Mesonyktikon",
		"Fajr",
		"Musaf"),

	MORNING(2,
		"MORN",
		"Lauds",
		"Orthros",
		"Zuhr",
		"Shacharit"),

	NOON(3,
		"NOON",
		"Prime",
		"Imeri",
		"Asr",
		"Mincha"),

	EVENING(4,
		"EVEN",
		"Vespers",
		"Hespera",
		"Maghrib",
		"Maariv"),

	NIGHT(5,
		"NITE",
		"Nocturns",
		"Apodeipnon",
		"Isha",
		"Musaf");

	private final int rank;

	private final String code;

	private final String latinName;

	private final String greekName;

	private final String arabicName;

	private final String hebrewName;

	private HourTypeEnum(
			int rank, 
			String code, 
			String latinName, 
			String greekName, 
			String arabicName,
			String hebrewName) {

		this.rank = rank;
		this.code = code;
		this.latinName = latinName;
		this.greekName = greekName;
		this.arabicName = arabicName;
		this.hebrewName = hebrewName;
	}

	public int getRank() {
		return rank;
	}

	public String getCode() {
		return code;
	}

	public String getLatinName() {
		return latinName;
	}

	public String getGreekName() {
		return greekName;
	}

	public String getArabicName() {
		return arabicName;
	}

	public String getHebrewName() {
		return hebrewName;
	}

	public static List<HourTypeEnum> getHourTypes() {
		final List<HourTypeEnum> resultList = new LinkedList<>();
		resultList.add(HourTypeEnum.DAWN);
		resultList.add(HourTypeEnum.MORNING);
		resultList.add(HourTypeEnum.NOON);
		resultList.add(HourTypeEnum.EVENING);
		resultList.add(HourTypeEnum.NIGHT);
		return resultList;
	}
}
