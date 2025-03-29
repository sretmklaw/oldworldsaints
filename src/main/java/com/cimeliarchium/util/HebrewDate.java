package com.cimeliarchium.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.cimeliarchium.exception.AppException;

/**
 * Hebcal - A Jewish Calendar Generator
 * Copyright (C) 1994-2006  Danny Sadinoff
 * http://sourceforge.net/projects/hebcal
 * 
 * Adapted from HebDate Avrom Finkelstein's Java port of the Reingold C++
 * algorithm. http://www.bayt.org/calendar/hebdate.html
 * 
 * The HebrewDate class allows one to maintain an instance of a Gregorian date
 * along with the corresponding hebrew date.
 * This class can use the standard Java Date and Calendar classes for setting
 * it, but does not subclass these classes or use them internally to any
 * extensive use. This class also does not have a concept of a time (which the
 * Date class does). If you are looking for a class that implements a hebrew
 * calendar version of the Calendar class, one is available from <A HREF=
 * "http://oss.software.ibm.com/developerworks/opensource/icu4j/">developerWorks</A>
 * by IBM.
 * The Java code which is contained in this class was translated from my C++
 * code. Some of that C++ code was translated or taken from other C/C++ code in
 * "Calendrical Calculations" by Nachum Dershowitz and Edward M. Reingold,
 * Software-- Practice &amp; Experience, vol. 20, no. 9 (September, 1990), pp.
 * 899- 928.
 * Available at <A HREF=
 * "http://emr.cs.uiuc.edu/~reingold/calendar.ps">http://emr.cs.uiuc.edu/~reingold/calendar.ps</A><BR>
 * Original C++ source: <A HREF=
 * "http://emr.cs.uiuc.edu/~reingold/calendar.C">http://emr.cs.uiuc.edu/~reingold/calendar.C</A>
 * 
 * @author Nachum Dershowitz
 * @author Edward M. Reingold
 * @author Avrom Finkelstein
 * @author Danny Sadinoff
 */
@PropertySource("classpath:/errorcode.properties")
public class HebrewDate implements Comparable<HebrewDate> {

	@Value("${hebrewdate.constructor.gmonth}")
	private String constructorHasInvalidGMonth;

	@Value("${hebrewdate.constructor.gdayofmonth}")
	private String constructorHasInvalidGDayOfMonth;

	@Value("${hebrewdate.constructor.gyear}")
	private String constructorHasInvalidGYear;

	@Value("${hebrewdate.setdate.gmonth}")
	private String setDateHasInvalidGMonth;

	@Value("${hebrewdate.setdate.gdayofmonth}")
	private String setDateHasInvalidGDayOfMonth;

	@Value("${hebrewdate.setdate.gyear}")
	private String setDateHasInvalidGYear;

	public static final int CURRENT_MONTH = 0;

	public static final int CURRENT_DAY = 0;

	public static final int CURRENT_YEAR = 0;

	private static final int HEBREW_EPOCH = -1373429;

	private static final String[] hebrewMonthProp = { 
			"hmonth.NISAN", 
			"hmonth.IYAR", 
			"hmonth.SIVAN", 
			"hmonth.TAMMUZ",
			"hmonth.AV", 
			"hmonth.ELUL", 
			"hmonth.TISHREI", 
			"hmonth.CHESHVAN", 
			"hmonth.KISLEV", 
			"hmonth.TEVET",
			"hmonth.SHVAT", 
			"hmonth.ADAR", 
			"hmonth.ADARII" 
	};

	private ResourceBundle bundle = null;

	protected int hebrewMonth;
	protected int hebrewDay;
	protected int hebrewYear;
	protected int gMonth;
	protected int gDayOfMonth;
	protected int gYear;
	protected int absDate;

	public HebrewDate(int gMonth, int gDayOfMonth, int gYear, Locale loc) throws AppException {

		if (gMonth == CURRENT_MONTH) {
			throw new AppException(constructorHasInvalidGMonth);
		}
		if (gDayOfMonth == CURRENT_DAY) {
			throw new AppException(constructorHasInvalidGDayOfMonth);
		}
		if (gYear == CURRENT_YEAR) {
			throw new AppException(constructorHasInvalidGYear);
		}
		setDate(gMonth, gDayOfMonth, gYear);
		setLocale(loc);
	}

	/**
	 * Method used to set the Gregorian date, and update the Hebrew date accordingly.
	 * 
	 * @param gMonth
	 * @param gDayOfMonth
	 * @param gYear
	 * @throws AppException 
	 */
	public void setDate(final int gMonth, final int gDayOfMonth, final int gYear) throws AppException {

		if (gMonth > 12 || gMonth < 0) {
			throw new AppException(setDateHasInvalidGMonth);
		}
		if (gDayOfMonth < 0 || gDayOfMonth > getLastDayOfMonth(gMonth, gYear)) {
			throw new AppException(setDateHasInvalidGDayOfMonth);
		}
		if (gYear < 0) {
			throw new AppException(setDateHasInvalidGYear);
		}
		// Initialize the Gregorian Month, Day, and Year
		if (gMonth != CURRENT_MONTH)
			this.gMonth = gMonth;
		if (gDayOfMonth != CURRENT_DAY)
			this.gDayOfMonth = gDayOfMonth;
		if (gYear != CURRENT_YEAR)
			this.gYear = gYear;

		// Initialize the Hebrew Date
		absDate = calculateAbsDateFromGregorianDate(this.gMonth, this.gDayOfMonth, this.gYear);
		calculateHebrewDateFromAbsDate(this.gDayOfMonth, this.gYear);
	}

	/**
	 * Method used to determine how many days are in a month
	 * 
	 * @param month the Month
	 * @param gYear the Gregorian Year
	 */
	public int getLastDayOfMonth(final int month, int gYear) {
		switch (month) {
		case 2:
			if ((((gYear % 4) == 0) && ((gYear % 100) != 0)) || ((gYear % 400) == 0))
				return 29;
			else
				return 28;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default:
			return 31;
		}
	}

	/**
	 * Method used to set the locale
	 * 
	 * @param loc
	 * @throws IllegalArgumentException
	 */
	public void setLocale(final Locale loc) throws IllegalArgumentException {
		bundle = ResourceBundle.getBundle("com.cimeliarchium.hebcal.Calendar", loc);
	}

	/**
	 * Method used to compute the absolute date from Gregorian date
	 * 
	 * @param month
	 * @param days
	 * @param year
	 * @return absoluteDate
	 */
	private int calculateAbsDateFromGregorianDate(int month, int days, int year) {

		for (int m = month - 1; m > 0; m--) {
			// days in prior months this year
			days = days + getLastDayOfMonth(m, year);
		}
		return (days // days this year
				+ 365 * (year - 1) // days in previous years ignoring leap days
				+ (year - 1) / 4 // Julian leap days before this year
				- (year - 1) / 100 // minus prior century years
				+ (year - 1) / 400); // plus prior years divisible by 400
	}

	/**
	 * Returns last day of a hebrew month.
	 */
	public int getLastDayOfHebrewMonth(int hMonth, int hYear) {
		if ((hMonth == 2) || (hMonth == 4) || (hMonth == 6) || ((hMonth == 8) && !(isCheshvanLong(hYear)))
				|| ((hMonth == 9) && isKislevShort(hYear)) || (hMonth == 10)
				|| ((hMonth == 12) && !(isHebrewLeapYear(hYear))) || (hMonth == 13))
			return 29;
		else
			return 30;
	}

	private boolean isKislevShort(int hYear) {
		return (getDaysInHebrewYear(hYear) % 10) == 3;
	}

	private boolean isCheshvanLong(int hYear) {
		return (getDaysInHebrewYear(hYear) % 10) == 5;
	}

	public int getLastMonthOfHebrewYear(int hYear) {
		return isHebrewLeapYear(hYear) ? 13 : 12;
	}
	
	public boolean isHebrewLeapYear(int year) {
		return (((7 * year) + 1) % 19) < 7;
	}

	private int getDaysInHebrewYear(int hYear) {
		return ((getHebrewCalendarElapsedDays(hYear + 1)) - (getHebrewCalendarElapsedDays(hYear)));
	}

	/**
	 * Method used to determine number of days elapsed from the Sunday prior to the start of
	 * the Hebrew calendar to the mean conjunction of Tishri in the current Hebrew year
	 * 
	 * @param year
	 * @return alternativeDay
	 */
	private int getHebrewCalendarElapsedDays(int year) {

		int monthsElapsed = (235 * ((year - 1) / 19)) // Months in complete cycles so far
				+ (12 * ((year - 1) % 19)) // Regular months in this cycle
				+ (7 * ((year - 1) % 19) + 1) / 19; // Leap months this cycle
		int partsElapsed = 204 + 793 * (monthsElapsed % 1080);
		int hoursElapsed = 5 + 12 * monthsElapsed + 793 * (monthsElapsed / 1080) + partsElapsed / 1080;
		int conjunctionDay = 1 + 29 * monthsElapsed + hoursElapsed / 24;
		int conjunctionParts = 1080 * (hoursElapsed % 24) + partsElapsed % 1080;
		int alternativeDay;
		if ((conjunctionParts >= 19440) // If new moon is at or after midday
				|| (((conjunctionDay % 7) == 2) // or is on a Tuesday
						&& (conjunctionParts >= 9924) // at 9 hours, 204 parts or later
						&& !isHebrewLeapYear(year)) // of a common year,
				|| (((conjunctionDay % 7) == 1) // or is on a Monday at
						&& (conjunctionParts >= 16789) // 15 hours, 589 parts or later
						&& (isHebrewLeapYear(year - 1)))) // at the end of a leap year
			// Then postpone Rosh HaShanah one day
			alternativeDay = conjunctionDay + 1;
		else
			alternativeDay = conjunctionDay;
		if (((alternativeDay % 7) == 0) // If Rosh HaShanah would occur on Sunday
				|| ((alternativeDay % 7) == 3) // or Wednesday
				|| ((alternativeDay % 7) == 5)) // or Friday
			// Then postpone it one (more) day
			return (1 + alternativeDay);
		else
			return alternativeDay;
	}

	/**
	 * Method used to compute the Hebrew date from the absolute date
	 * 
	 * @param gDayOfMonth
	 * @param gYear
	 */
	private void calculateHebrewDateFromAbsDate(int gDayOfMonth, int gYear) {
		hebrewYear = (absDate + HEBREW_EPOCH) / 366; // Approximation from below
		// Search forward for year from the approximation
		while (absDate >= hebrewDateToAbsDate(7, 1, hebrewYear + 1)) {
			hebrewYear++;
		}
		// Search forward for month from either Tishri or Nisan.
		if (absDate < hebrewDateToAbsDate(1, 1, hebrewYear)) {
			hebrewMonth = 7; // Start at Tishri 
		} else {
			hebrewMonth = 1; // Start at Nisan
		}
		while (absDate > hebrewDateToAbsDate(hebrewMonth, getLastDayOfHebrewMonth(gDayOfMonth, gYear), hebrewYear)) {
			hebrewMonth++;
		}
		// Calculate the day by subtraction
		hebrewDay = absDate - hebrewDateToAbsDate(hebrewMonth, 1, hebrewYear) + 1;
	}

	/**
	 * Method used to compute the absolute date of the Hebrew Date
	 * 
	 * @param hMonth
	 * @param hDayOfMonth
	 * @param hYear
	 * @return hebrewDate
	 */
	private int hebrewDateToAbsDate(int hMonth, int hDayOfMonth, int hYear) {
		int m;
		// Before Tishri, so add days in prior months
		if (hMonth < 7) {
			// this year before and after Nisan
			for (m = 7; m <= getLastMonthOfHebrewYear(hYear); m++)
				hDayOfMonth = hDayOfMonth + getLastDayOfHebrewMonth(m, hYear);
			for (m = 1; m < hMonth; m++)
				hDayOfMonth = hDayOfMonth + getLastDayOfHebrewMonth(m, hYear);
		}
		// Add days in prior months this year
		else {
			for (m = 7; m < hMonth; m++)
				hDayOfMonth = hDayOfMonth + getLastDayOfHebrewMonth(m, hYear);
		}
		return (hDayOfMonth + getHebrewCalendarElapsedDays(hYear) // Days in prior years
				+ HEBREW_EPOCH); // Days elapsed before absolute date 1
	}


	public String getHebrewMonthAsString() {
		return (isHebrewLeapYear(this.hebrewYear) && hebrewMonth == 12) 
				? bundle.getString("hmonth.ADARI") 
						: bundle.getString(hebrewMonthProp[hebrewMonth-1]);
	}

	public int getHebrewDay() {
		return hebrewDay;
	}

	public String toString() {
		return getHebrewMonthAsString() + " " + hebrewDay + ", " + hebrewYear;
	}

	@Override
	public boolean equals(Object object) {
		HebrewDate hebDate = (HebrewDate) object;
		if (absDate != hebDate.absDate)
			return false;
		else
			return true;
	}

	@Override
	public int compareTo(HebrewDate o) {
		HebrewDate hebDate = (HebrewDate) o;
		if (absDate < hebDate.absDate)
			return -1;
		else if (absDate > hebDate.absDate)
			return 1;
		else
			return 0;
	}
}
