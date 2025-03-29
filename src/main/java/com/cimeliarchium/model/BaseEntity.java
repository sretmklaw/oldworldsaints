package com.cimeliarchium.model;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.StringJoiner;

public class BaseEntity {

	private static final Properties PROP = new Properties();
	protected static final Long CALENDAR_GREGORIAN_ID;
	protected static final Long CALENDAR_JULIAN_ID;
	protected static final Long CALENDAR_HIJRI_ID;
	protected static final Long CALENDAR_HEBREW_ID;
	protected static final Long CALENDAR_ALL_ID;
	protected static final String CALENDAR_GREGORIAN_CODE;
	protected static final String CALENDAR_JULIAN_CODE;
	protected static final String CALENDAR_HIJRI_CODE;
	protected static final String CALENDAR_HEBREW_CODE;
	protected static final String CALENDAR_ALL_CODE;
	protected static final Long CENTURY_TIMEIMMEMORIAL_ID;
	protected static final Long CENTURY_FIRST_ID;
	protected static final Long CENTURY_LAST_ID;
	protected static final Long CREED_ABRAHAMIC_ID;
	protected static final Long CREED_APOSTOLIC_ID;
	protected static final Long CREED_CHALCEDONIAN_ID;
	protected static final Long CREED_FILIOQUIST_ID;
	protected static final Long CREED_CATHOLIC_ID;
	protected static final Long CREED_PROTESTANT_ID;
	protected static final Long CREED_ORTHODOX_ID;
	protected static final Long CREED_ISLAMIC_ID;
	protected static final Long CREED_SUNNI_ID;
	protected static final Long CREED_SHIITE_ID;
	protected static final Long CREED_JEWISH_ID;
	protected static final Long MONTH_VARIABLE_ID;
	protected static final Long PATRONAGETYPE_AFFLICTION_ID;
	protected static final Long PATRONAGETYPE_VICE_ID;
	protected static final Long RITE_TRIDENTINE_ID;
	protected static final Long RITE_NOVUSORDO_ID;
	protected static final Long RITE_ALL_ID;
	protected static final String NEW_ = "NEW ";
	protected static final String FIELDS_DELIM = "|";
	public static final ZoneId ZONE_ID = ZoneId.of("UTC");

	/**
	 * Provide for static initialization of values from applications.properties file,
	 * in order to be able to access these values from model-specific helper methods.
	 */
	static {
		try (InputStream inputStream = BaseEntity.class.getResourceAsStream("/application.properties")) {
			PROP.load(inputStream);
		} catch (IOException | NumberFormatException ex) {
			throw new RuntimeException("Error loading properties", ex);
		}
		// String Properties
		CALENDAR_GREGORIAN_CODE = PROP.getProperty("calendar.gregorian.code");
		CALENDAR_JULIAN_CODE = PROP.getProperty("calendar.julian.code");
		CALENDAR_HIJRI_CODE = PROP.getProperty("calendar.hijri.code");
		CALENDAR_HEBREW_CODE = PROP.getProperty("calendar.hebrew.code");
		CALENDAR_ALL_CODE = PROP.getProperty("calendar.all.code");

		// ID Properties
		CALENDAR_GREGORIAN_ID = parseIdProperty("calendar.gregorian.id");
		CALENDAR_JULIAN_ID = parseIdProperty("calendar.julian.id");
		CALENDAR_HIJRI_ID = parseIdProperty("calendar.hijri.id");
		CALENDAR_HEBREW_ID = parseIdProperty("calendar.hebrew.id");
		CALENDAR_ALL_ID = parseIdProperty("calendar.all.id");
		CENTURY_TIMEIMMEMORIAL_ID = parseIdProperty("century.timeimmemorial.id");
		CENTURY_FIRST_ID = parseIdProperty("century.first.id");
		CENTURY_LAST_ID = parseIdProperty("century.last.id");
		CREED_ABRAHAMIC_ID = parseIdProperty("creed.abrahamic.id");
		CREED_APOSTOLIC_ID = parseIdProperty("creed.apostolic.id");
		CREED_CHALCEDONIAN_ID = parseIdProperty("creed.chalcedonian.id");
		CREED_FILIOQUIST_ID = parseIdProperty("creed.filioquist.id");
		CREED_CATHOLIC_ID = parseIdProperty("creed.catholic.id");
		CREED_PROTESTANT_ID = parseIdProperty("creed.protestant.id");
		CREED_ORTHODOX_ID = parseIdProperty("creed.orthodox.id");
		CREED_ISLAMIC_ID = parseIdProperty("creed.islamic.id");
		CREED_SUNNI_ID = parseIdProperty("creed.sunni.id");
		CREED_SHIITE_ID = parseIdProperty("creed.shiite.id");
		CREED_JEWISH_ID = parseIdProperty("creed.jewish.id");
		MONTH_VARIABLE_ID = parseIdProperty("month.variable.id");
		PATRONAGETYPE_AFFLICTION_ID = parseIdProperty("patronagetype.affliction.id");
		PATRONAGETYPE_VICE_ID = parseIdProperty("patronagetype.vice.id");
		RITE_TRIDENTINE_ID = parseIdProperty("rite.tridentine.id");
		RITE_NOVUSORDO_ID = parseIdProperty("rite.novusordo.id");
		RITE_ALL_ID = parseIdProperty("rite.all.id");
	}

	private static Long parseIdProperty(String propertyName) {
		try {
			return Long.valueOf(PROP.getProperty(propertyName));
		} catch (NumberFormatException ex) {
			throw new RuntimeException("Property not found: " + propertyName, ex);
		}
	}

	/**
	 * Method used to format date time based on Administrator's current UTC offset
	 * 
	 * @param offset
	 *            the zone offset
	 * @return instant
	 */
	public static DateTimeFormatter format(String offset) {
		return DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm")
				.withLocale(Locale.US)
				.withZone(ZoneId.of(offset));
	}

	/**
	 * Method used to obtain an MD5 hash of a passed-in string value
	 * to facilicate comparison during Request Review.
	 * 
	 * @param input the input string
	 * @return MD5 hash
	 */
	public static String hash(String input) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			// Intentionally, do nothing.
		}
		md5.update(StandardCharsets.UTF_8.encode(String.valueOf(input)));
		return String.format("%032x", new BigInteger(1, md5.digest()));
	}

	/*
	 * The following methods are used to conditionally trim all elements of passed-in string values
	 * to facilitate comparison during Request Review.
	 */
	public String delim(String delimiter, String e1, String e2) {
		return this.delim(delimiter, e1, e2, null);
	}
	public String delim(String delimiter, String e1, String e2, String e3) {
		final StringJoiner sj = new StringJoiner(delimiter);
		if (e1 != null && !e1.isEmpty()) {
			sj.add(e1.trim());
		}
		if (e2 != null && !e2.isEmpty()) {
			sj.add(e2.trim());
		}
		if (e3 != null && !e3.isEmpty()) {
			sj.add(e3.trim());
		}
		return sj.toString();
	}
	public String delim(String delimiter, List<String> elements) {
		final StringJoiner sj = new StringJoiner(delimiter);
		for (String e : elements) {
			if (e != null && !e.isEmpty()) {
				sj.add(e.trim());
			}
		}
		return sj.toString();
	}

	protected String getNewEntityIndicatorForId(Long entityId) {
		return (entityId == null || (entityId != null && entityId == 0L)) ? NEW_ : "";
	}
}
