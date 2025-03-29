package com.cimeliarchium.enums;

import java.util.LinkedList;
import java.util.List;

/**
 * This enumeration provides static copies of rows stored in the database-backed 'star_type' table.
 * Its purpose is to allow unauthenticated visitors to view a static listing of StarTypes 
 * from the 'About' view, without having to perform extraneous calls to the database.
 */
public enum StarTypeEnum {

	MAG2(0,
			"MAG2",
			"Named Star - a not otherwise classified celestial body belonging to one of the 48 classically-defined constellations"),

	LM_IN(4,
			"LM_IN",
			"Nakshatra - one of 28 stars lying near the ecliptic identified by Indian astronomers as a waypoint occupied by the moon in its monthly transit of the night sky"),

	LM_AR(5,
			"LM_AR",
			"Manzil - one of 28 stars lying near the ecliptic identified by Arabic astronomers as a waypoint occupied by the moon in its monthly transit of the night sky"),

	LM_BOTH(6,
			"LM_BOTH",
			"Manzil & Nakshatra - one of 28 stars lying near the ecliptic identified by both Indian and Arabic astronomers as a waypoint occupied by the moon in its monthly transit of the night sky"),

	BHN(7,
			"BHN",
			"Behenian Fixed Star - one of 15 stars identified by Near Eastern astronomy as possessing some particularily important celestial power"),

	BHN_LM(8,
			"BHN_LM",
			"Behenian Fixed Star, Manzil, & Nakshatra - a celestial body serving as both a lunar mansion and also thought to possess some particularily important celestial power in ancient Near Eastern astronomy");

	private final int rank;

	private final String code;

	private final String name;

	private StarTypeEnum(
			int rank,
			String code,
			String name) {

		this.rank = rank;
		this.code = code;
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static List<StarTypeEnum> getStarTypes() {
		final List<StarTypeEnum> resultList = new LinkedList<>();
		resultList.add(StarTypeEnum.MAG2);
		resultList.add(StarTypeEnum.LM_IN);
		resultList.add(StarTypeEnum.LM_AR);
		resultList.add(StarTypeEnum.LM_BOTH);
		resultList.add(StarTypeEnum.BHN);
		resultList.add(StarTypeEnum.BHN_LM);
		return resultList;
	}
}
