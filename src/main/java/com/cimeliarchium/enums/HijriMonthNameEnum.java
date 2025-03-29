package com.cimeliarchium.enums;

/**
 * As of JodaTime v2.10.10, IslamicChronology#monthPart displays only ordinal values,
 * not Strings for Hijri month names. This enumeration is therefore used to handle
 * conversion from a given ordinal value to the appropriate String value.
 * 
 * Also, given that the wide variance in transliteration standards from Arabic to English,
 * this enumeration serves as the standard for all Hijri month names within the application.
 */
public enum HijriMonthNameEnum {

	MUHARRAM(
			"Muharram"),

	SAFAR(
			"Safar"),

	RABI_I(
			"Rabi al-Awwal"),

	RABI_II(
			"Rabi at-Thani"),

	JUMADA_I(
			"Jumada al-Awwal"),

	JUMADA_II(
			"Jumada at-Thani"),

	RAJAB(
			"Rajab"),

	SHABAN(
			"Shaban"),

	RAMADAN(
			"Ramadan"),

	SHAWWAL(
			"Shawwal"),

	ZHU_AL_QIDAH(
			"Zhu al-Qidah"),

	ZHU_AL_HIJJAH(
			"Zhu al-Hijjah");

	private final String value;

	HijriMonthNameEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
