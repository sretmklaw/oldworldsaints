package com.cimeliarchium.model.dao;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GregorianChronology;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.chrono.JulianChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimeliarchium.enums.HijriMonthNameEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.util.HebrewDate;

public class CalendarDates {

	private static final Logger LOGGER = LoggerFactory.getLogger(CalendarDates.class);

	private static final DateTimeFormatter DAY_ID_PART = DateTimeFormat.forPattern("MMdd");
	private static final DateTimeFormatter MONTH_PART = DateTimeFormat.forPattern("MMMM");
	private static final DateTimeFormatter DAY_PART = DateTimeFormat.forPattern("dd");

	public static final String GREGORIAN_SUFFIX = " (Gregorian)";
	public static final String JULIAN_SUFFIX = " (Julian)";

	private String gregorianDateString;

	private String julianDate;

	private String julianDateString;

	private String hijriDateString;

	private Integer hijriDayOfMonth;

	private String hebrewDateString;

	public CalendarDates(DateTime targetDateTime) throws AppException {
		setGregorianDateStringForDateTime(targetDateTime);
		setJulianDateStringForDateTime(targetDateTime);
		setHijriDateStringForDateTime(targetDateTime);
		setHebrewDateStringForDateTime(targetDateTime);
	};

	public static Long getDayIdForDateTime(DateTime targetDateTime) {
		return Long.parseLong(DAY_ID_PART.print(targetDateTime));
	}

	public void setGregorianDateStringForDateTime(DateTime targetDateTime) {

		final LocalDate gregorianTargetDate = new LocalDate(targetDateTime, GregorianChronology.getInstance());
		final String gregorianDateString = DAY_PART.print(gregorianTargetDate) + " " 
				+ MONTH_PART.print(gregorianTargetDate) + GREGORIAN_SUFFIX;
		LOGGER.debug("Parsed Gregorian Date of '{}'", gregorianDateString);
		this.gregorianDateString = gregorianDateString;
	}

	public String getGregorianDateString() {
		return this.gregorianDateString;
	}

	/**
	 * Since the introduction of the Gregorian calendar in October 1582, the difference between Gregorian 
	 * and Julian calendar dates has been increasing at a rate of roughly 1 day per century. Thus, the 
	 * discrepancy will remain 13 days until 01 March 2100, at which point it will increase to 14 days.
	 * 
	 * @param targetDateTime DateTime
	 */
	public void setJulianDateStringForDateTime(DateTime targetDateTime) {

		final LocalDate julianTargetDate = new LocalDate(targetDateTime, JulianChronology.getInstanceUTC());
		final String julianDate = DAY_PART.print(julianTargetDate) 
				+ " " + MONTH_PART.print(julianTargetDate);
		final String julianDateString = julianDate + JULIAN_SUFFIX;
		LOGGER.debug("Parsed Julian Date of '{}'", julianDateString);
		this.julianDate = julianDate;
		this.julianDateString = julianDateString;
	}

	public String getJulianDate() {
		return this.julianDate;
	}

	public String getJulianDateString() {
		return this.julianDateString;
	}

	/**
	 * As of JodaTime v2.10.10, IslamicChronology monthPart displays only ordinal values,
	 * so HijriMonthNameEnum has been implemented to convert these values to Hijri month names.
	 * Note that whereas month ordinal values are 1-12, the array of enumeration values is 0-11,
	 * so we must always subtract 1 from the ordinal value when converting to Hijri month name.
	 * 
	 * @param targetDateTime DateTime
	 */
	public void setHijriDateStringForDateTime(DateTime targetDateTime) {

		final LocalDate hijriTargetDate = new LocalDate(targetDateTime, IslamicChronology.getInstanceUTC());
		final Integer hijriMonthOrdinal = Integer.parseInt(MONTH_PART.print(hijriTargetDate));
		hijriDayOfMonth = Integer.parseInt(DAY_PART.print(hijriTargetDate));
		final String hijriDateString = DAY_PART.print(hijriTargetDate) 
				+ " " + HijriMonthNameEnum.values()[hijriMonthOrdinal-1].getValue();
		LOGGER.debug("Parsed Hijri Date of '{}'", hijriDateString);
		this.hijriDateString = hijriDateString;
	}

	public String getHijriDateString() {
		return this.hijriDateString;
	}

	/**
	 * Calculate Lunar Phase based on Hijri calendar day of month
	 * 
	 * @return hijriDayOfMonth
	 */
	public Integer getHijriDayOfMonth() {
		return this.hijriDayOfMonth;
	}

	/** 
	 * XXX: In the interest of standardization, the following code block should be replaced 
	 * by Joda-Time's HebrewChronology implementation, if and when this becomes available.
	 * com.cimeliarchium.service.web.HebrewDate is adapted directly from a Java port of
	 * C++ code originally made available at http://www.bayt.org/calendar/hebdate.html
	 * 
	 * @param targetDateTime DateTime
	 * @throws AppException 
	 */
	public void setHebrewDateStringForDateTime(DateTime targetDateTime) throws AppException {

		final HebrewDate hebrewChrono = new HebrewDate(
				targetDateTime.getMonthOfYear(),
				targetDateTime.getDayOfMonth(),
				targetDateTime.getYear(), Locale.ENGLISH);
		final String hebrewDateString = hebrewChrono.getHebrewDay() 
				+ " " + hebrewChrono.getHebrewMonthAsString();
		LOGGER.debug("Parsed Hebrew Date of '{}'", hebrewDateString);
		this.hebrewDateString = hebrewDateString;
	}

	public String getHebrewDateString() {
		return this.hebrewDateString;
	}

	public boolean hasMatchingGregorianOrKadmiSolarDateString(String dateString, Boolean isGregorianOrKadmiSolarDateApplicable) {
		return ((dateString + GREGORIAN_SUFFIX).equals(gregorianDateString) && isGregorianOrKadmiSolarDateApplicable);
	}

	public boolean hasMatchingJulianSolarDateString(String dateString, Boolean isJulianSolarDateApplicable) {
		return ((dateString + JULIAN_SUFFIX).equals(julianDateString) && isJulianSolarDateApplicable);
	}

	public boolean hasMatchingDateString(String dateString, 
			Boolean isGregorianOrKadmiSolarDateApplicable, 
			Boolean isJulianSolarDateApplicable) {

		return (hasMatchingGregorianOrKadmiSolarDateString(dateString, isGregorianOrKadmiSolarDateApplicable) 
				|| hasMatchingJulianSolarDateString(dateString, isJulianSolarDateApplicable) 
				|| dateString.equals(hijriDateString) 
				|| dateString.equals(hebrewDateString));
	}

	@Override
	public String toString() {
		return "TargetDate [gregorianDateString=" + gregorianDateString + ", julianDateString=" + julianDateString
				+ ", hijriDateString=" + hijriDateString + ", hebrewDateString=" + hebrewDateString + "]";
	}

}
