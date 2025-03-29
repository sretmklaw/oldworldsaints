package com.cimeliarchium.enums;

import java.util.Arrays;
import java.util.List;

public enum EpochEnum {

	AD(
			"A.D."),

	BC(
			"B.C."),

	AH(
			"A.H."),

	BH(
			"B.H."),

	AM(
			"A.M.");

	private final String value;

	EpochEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static List<EpochEnum> getChristianEpochs() {
		return Arrays.asList(AD,BC);
	}

	public static List<EpochEnum> getIslamicEpochs() {
		return Arrays.asList(AH,BH);
	}

	public static List<EpochEnum> getJewishEpochs() {
		return Arrays.asList(AM);
	}
}
