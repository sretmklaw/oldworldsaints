package com.cimeliarchium.enums;

import java.util.EnumSet;

public enum CommemorationTitleEnum {

	/**
	 * Christian Commemoration Titles follow
	 */

	BLESSED(
			"Blessed"),

	CHRIST(
			"Christ"),

	HOLY(
			"Holy"),

	PROPHET(
			"Prophet"),

	REVEREND(
			"Reverend"),

	SAINT(
			"Saint"),

	SERVANT_OF_GOD(
			"Servant of God"),

	VENERABLE(
			"Venerable"),

	/**
	 * Islamic Commemoration Titles follow
	 */

	AMIROLMOMENIN(
			"Amirolmomenin"),

	IMAM(
			"Imam"),

	KHWAJA(
			"Khwaja"),
	
	NABI(
			"Nabi"),

	RAYISULMALAKIYA(
			"Rayisulmalakiya"),

	RASUL(
			"Rasul"),

	SAYYIDA(
			"Sayyida"),

	SHAYKH(
			"Shaykh"),

	SULTAN(
			"Sultan"),

	UMMOLMOMENIN(
			"Ummolmomenin"),

	/**
	 * Jewish Commemoration Titles follow
	 */

	AMORA(
			"Amora"),

	GAON(
			"Gaon"),

	HAKHAM(
			"Hakham"),

	KADDOSH(
			"Kaddosh"),

	KEDOSHIM(
			"Kedoshim"),

	MAGEFAT(
			"Magefat"),

	NAGID(
			"Nagid"),

	NASI(
			"Nasi"),

	SHALIACH(
			"Shaliach"),

	TANNA(
			"Tanna"),

	TZADIKES(
			"Tzadikes"),

	YEZIAT(
			"Yeziat");

	private final String value;

	CommemorationTitleEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static EnumSet<CommemorationTitleEnum> getChristianTitles() {
		return EnumSet.of(
				BLESSED,
				CHRIST,
				HOLY,
				PROPHET,
				REVEREND,
				SAINT,
				SERVANT_OF_GOD,
				VENERABLE);
	}

	public static EnumSet<CommemorationTitleEnum> getIslamicTitles() {
		return EnumSet.of(
				AMIROLMOMENIN,
				IMAM,
				KHWAJA,
				NABI,
				RASUL,
				SAYYIDA,
				SHAYKH,
				SULTAN,
				UMMOLMOMENIN);
	}

	public static EnumSet<CommemorationTitleEnum> getJewishTitles() {
		return EnumSet.of(
				AMORA,
				GAON,
				HAKHAM,
				KADDOSH,
				KEDOSHIM,
				MAGEFAT,
				NASI,
				NAGID,
				SHALIACH,
				TANNA,
				TZADIKES,
				YEZIAT);
	}
}
