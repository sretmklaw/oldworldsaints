package com.cimeliarchium.enums;

import java.util.LinkedList;
import java.util.List;

/**
 * This enumeration provides static copies of rows stored in the database-backed 'hour_type' table.
 * Its purpose is to allow unauthenticated visitors to view a static listing of HourTypes 
 * from the 'About' view, without having to perform extraneous calls to the database.
 */
public enum LocationTypeEnum {

	/**
	 * Standard Location Types follow
	 */

	GOTHIC(0,
			"GOT-MON",
			"GOT-MAJ",
			"GOT-MIN",
			"Gothic",
			null,
			null),

	LATINATE(0,
			"LAT-MON",
			"LAT-MAJ",
			"LAT-MIN",
			"Latinate",
			null,
			null),

	LEVANTINE(0,
			"LEV-MON",
			"LEV-MAJ",
			"LEV-MIN",
			"Levantine",
			null,
			null),

	PERSIANATE(0,
			"PER-MON",
			"PER-MAJ",
			"PER-MIN",
			"Persianate",
			null,
			null),

	ORIENTAL(0,
			"ORI-MON",
			"ORI-MAJ",
			"ORI-MIN",
			"Oriental",
			null,
			null),

	/**
	 * Special Location Types follow
	 */

	PARIS_NOTRE_DAME(1,
			"GOT-MON-PAR",
			null,
			null,
			"Notre Dame",
			"Paris",
			"FR"),

	COLOGNE_SAINT_PETERS(2,
			"GOT-MON-COL",
			null,
			null,
			"Sankt Petrus",
			"Cologne",
			"DE"),

	COMPOSTELA_SAINT_JAMES(3,
			"GOT-MON-COM",
			null,
			null,
			"Santiago",
			"Compostela",
			"ES"),

	CANTERBURY_CHRISTCHURCH(4,
			"PER-MON-BAG",
			null,
			null,
			"Christchurch",
			"Canterbury",
			"GB"),

	ROME_SAINT_PETERS(5,
			"LAT-MON-VAT",
			null,
			null,
			"Saint Peters",
			"Vatican City",
			"VA"),

	ROME_COLOSSEUM(6,
			"LAT-MON-COL",
			null,
			null,
			"Colosseum",
			"Rome",
			"IT"),

	VENICE_SAINT_MARKS(7,
			"LAT-MON-VEN",
			null,
			null,
			"Saint Mark's",
			"Venice",
			"IT"),

	MOSCOW_POKROVSKY(8,
			"LEV-MON-MOS",
			null,
			null,
			"Saint Basil's",
			"Moscow",
			"RU"),

	ISTANBUL_HAGIA_SOPHIA(9,
			"LEV-MON-IST",
			null,
			null,
			"Hagia Sophia",
			"Istanbul",
			"TR"),

	EGYPT_AL_AZHAR(10,
			"LEV-MON-CAI",
			null,
			null,
			"Masjid al-Azhar",
			"Cairo",
			"EG"),

	DAMASCUS_AL_UMAYYA(11,
			"LEV-MON-DAM",
			null,
			null,
			"Masjid al-Umayya",
			"Damascus",
			"SY"),

	JERUSALEM_QUBBAT_AS_SAKHRA(12,
			"LEV-MON-JER",
			null,
			null,
			"Qubbat as-Sakhra",
			"Jerusalem",
			"IL"),

	BAGHDAD_KAZIMIYYA(13,
			"PER-MON-BAG",
			null,
			null,
			"Masjid al-Kazimiyya",
			"Baghdad",
			"IQ"),

	MECCA_HARAM(14,
			"PER-MON-MEC",
			null,
			null,
			"Masjid al-Haram",
			"Mecca",
			"SA"),

	DELHI_JEHANUMA(15,
			"PER-MON-DEL",
			null,
			null,
			"Masjid al-Jehanuma",
			"Delhi",
			"IN");

	private final int rank;

	private final String monumentalEdificeCode;

	private final String majorEdificeCode;

	private final String minorEdificeCode;

	private final String name;

	private final String cityName;

	private final String nationCode;

	private LocationTypeEnum(
			int rank,
			String monumentalEdificeCode, 
			String majorEdificeCode, 
			String minorEdificeCode,
			String name,
			String cityName,
			String nationCode) {

		this.rank = rank;
		this.monumentalEdificeCode = monumentalEdificeCode;
		this.majorEdificeCode = majorEdificeCode;
		this.minorEdificeCode = minorEdificeCode;
		this.name = name;
		this.cityName = cityName;
		this.nationCode = nationCode;
	}

	public int getRank() {
		return rank;
	}

	public String getMonumentalEdificeCode() {
		return monumentalEdificeCode;
	}

	public String getMajorEdificeCode() {
		return majorEdificeCode;
	}

	public String getMinorEdificeCode() {
		return minorEdificeCode;
	}

	public String getName() {
		return name;
	}

	public String getCityName() {
		return cityName;
	}

	public String getNationCode() {
		return nationCode;
	}

	public static List<LocationTypeEnum> getStandardLocationTypes() {
		final List<LocationTypeEnum> resultList = new LinkedList<>();
		resultList.add(LocationTypeEnum.GOTHIC);
		resultList.add(LocationTypeEnum.LATINATE);
		resultList.add(LocationTypeEnum.LEVANTINE);
		resultList.add(LocationTypeEnum.PERSIANATE);
		resultList.add(LocationTypeEnum.ORIENTAL);
		return resultList;
	}

	public static List<LocationTypeEnum> getSpecialLocationTypes() {
		final List<LocationTypeEnum> resultList = new LinkedList<>();
		resultList.add(LocationTypeEnum.PARIS_NOTRE_DAME);
		resultList.add(LocationTypeEnum.ROME_COLOSSEUM);
		resultList.add(LocationTypeEnum.VENICE_SAINT_MARKS);
		resultList.add(LocationTypeEnum.MOSCOW_POKROVSKY);
		resultList.add(LocationTypeEnum.ISTANBUL_HAGIA_SOPHIA);
		resultList.add(LocationTypeEnum.EGYPT_AL_AZHAR);
		resultList.add(LocationTypeEnum.DAMASCUS_AL_UMAYYA);
		resultList.add(LocationTypeEnum.JERUSALEM_QUBBAT_AS_SAKHRA);
		resultList.add(LocationTypeEnum.BAGHDAD_KAZIMIYYA);
		resultList.add(LocationTypeEnum.DELHI_JEHANUMA);
		return resultList;
	}
}
