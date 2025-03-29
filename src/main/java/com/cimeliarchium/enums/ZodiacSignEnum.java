package com.cimeliarchium.enums;

import java.util.LinkedList;
import java.util.List;

/**
 * This enumeration provides static copies of rows stored in the database-backed 'constellation' table.
 * Its purpose is to allow unauthenticated visitors to view a static listing of zodiac Constellations 
 * from the 'About' view, without having to perform extraneous calls to the database.
 */
public enum ZodiacSignEnum {

	ARIES(1,
		"ZS-AR",
		"Centered on the star Hamal, "
				+ "named for the celestial shepherd Tammuz in ancient Near Eastern mythology.",
		"Aries",
		"Krios",
		"Hamal",
		"Taleh"),

	TAURUS(2,
		"ZS-TR",
		"Centered on the royal star Aldebaran ('Follower' of the Pleides, the 'Seven Sisters'), "
			+ "identified in ancient Near Eastern mythology as a ferocious bull which left unchecked "
			+ "inflicts seven years of famine, and whose appearance during the Vernal Equinox "
			+ "marks the beginning of Northern Hemisphere Spring and the start of the sailing season "
			+ "in the Eastern Mediterranean.",
		"Taurus",
		"Tauros",
		"Sawar",
		"Shor"),

	GEMINI(3,
		"ZS-GM",
		"Comprising two of the brightest stars in the night sky, Castor and Pollux, "
				+ "identified with the seafaring twins of the same name in ancient Near Eastern mythology.",
		"Gemini",
		"Didymoi",
		"Jawza",
		"Teomim"),

	CANCER(4,
		"ZS-CN",
		"Identified in ancient Near Eastern mythology as a crab or beetle "
				+ "rolling the sun to its highest point in the sky, "
				+ "and namesake of the northern tropic in which the sun rises "
				+ "during the Summer Solstice.",
		"Cancer",
		"Karkinos",
		"Saratan",
		"Sarton"),

	LEO(5,
		"ZS-LE",
		"Identified as a lion centered on the royal star Regulus, "
				+ "having special significance in ancient Egypt given its appearance "
				+ "at the onset of the Nile floods.",
		"Leo",
		"Leon",
		"Asad",
		"Aryeh"),

	VIRGO(6,
		"ZS-VR",
		"Centered on the bright star Spica ('Sheaf') "
				+ "identified as a young maiden signifying purity in ancient Near Eastern mythology, "
				+ "and traditionally coinciding with the wheat harvest in the Northern Hemisphere.",
		"Virgo",
		"Parthenos",
		"Sunbula",
		"Betulah"),

	LIBRA(7,
		"ZS-LB",
		"Identified as scales or claws corresponding to the twin stars "
				+ "Zubenashamali and Zubenalgenubi in Near Eastern mythology, "
				+ "in which the sun rises during the Autumnal Equinox.",
		"Libra",
		"Zygos",
		"Maizan",
		"Moznayim"),

	SCORPIO(8,
		"ZS-SC",
		"Identified as a scorpion centered on the royal star Antares ('Rival of Mars') "
				+ "in ancient Near Eastern mythology, reflecting its Babylonian name Mulgirtab, "
				+ "'one of the burning sting'.",
		"Scorpio",
		"Skorpios",
		"Aqrab",
		"Akrab"),

	SAGITTARIUS(9,
		"ZS-SG",
		"Identified as a human-horse hybrid or archer in Near Eastern mythology.",
		"Sagittarius",
		"Toxotes",
		"Quos",
		"Keshet"),

	CAPRICORN(10,
		"ZS-CP",
		"Identified as a horned sea-goat centered on the star Denebalgedi ('Tail of the Goat'), "
				+ "thought to bestow blessings in ancient Near Eastern mythology, "
				+ "and namesake of the southern tropic in which the sun rises "
				+ "during the Winter Solstice.",
		"Capricorn",
		"Aigokeros",
		"Jadi",
		"Gedi"),

	AQUARIUS(11,
		"ZS-AQ",
		"Centered on the star Sadolmalik ('Kingly Luck'), "
				+ "identified as the water-bearer in ancient Near Eastern mythology, "
				+ "likewise sometimes associated with the wild horse Pegasus "
				+ "based on its proximity to the neighboring constellation.",
		"Aquarius",
		"Hydrokhoos",
		"Dalwa",
		"Dali"),

	PISCES(9,
		"ZS-PI",
		"Centered on the star Al-Risha ('Fish-Cord'), "
				+ "identified as a pair of fishes in Near Eastern mythology, "
				+ "in which the sun has risen during the Vernal Equinox since the 1st Century A.D., "
				+ "and whose appearance has therefore been used to fix the date of "
				+ "the spring religious festivals of Jewish Passover, Christian Easter, "
				+ "and Persian Nowruz.",
		"Pisces",
		"Ikhthyes",
		"Hud",
		"Dagim");

	private final int rank;

	private final String code;

	private final String detail;

	private final String latinName;

	private final String greekName;

	private final String arabicName;

	private final String hebrewName;

	private ZodiacSignEnum(
			int rank, 
			String code, 
			String detail,
			String latinName, 
			String greekName, 
			String arabicName,
			String hebrewName) {

		this.rank = rank;
		this.code = code;
		this.detail = detail;
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

	public String getDetail() {
		return detail;
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

	public static List<ZodiacSignEnum> getZodiacSigns() {
		final List<ZodiacSignEnum> resultList = new LinkedList<>();
		resultList.add(ZodiacSignEnum.ARIES);
		resultList.add(ZodiacSignEnum.TAURUS);
		resultList.add(ZodiacSignEnum.GEMINI);
		resultList.add(ZodiacSignEnum.CANCER);
		resultList.add(ZodiacSignEnum.LEO);
		resultList.add(ZodiacSignEnum.VIRGO);
		resultList.add(ZodiacSignEnum.LIBRA);
		resultList.add(ZodiacSignEnum.SCORPIO);
		resultList.add(ZodiacSignEnum.SAGITTARIUS);
		resultList.add(ZodiacSignEnum.CAPRICORN);
		resultList.add(ZodiacSignEnum.AQUARIUS);
		resultList.add(ZodiacSignEnum.PISCES);
		return resultList;
	}
}
