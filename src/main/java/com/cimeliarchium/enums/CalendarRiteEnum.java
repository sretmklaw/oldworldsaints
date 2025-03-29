package com.cimeliarchium.enums;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Rite;

/**
 * This enumeration provides static copies of rows stored in the database-backed 'calendar' and 'rite' tables.
 * Its purpose is to allow unauthenticated visitors to view a static listing of Calendar and Rite 
 * from the 'About' view, without having to perform extraneous calls to the database.
 */
public enum CalendarRiteEnum {

	ALL(
		new Calendar.Builder()
			.withCalendarId(1L)
			.withCalendarName("All Commemorations")
			.withCalendarDetail("All dates according to the Gregorian, Julian, Hijri, and Hebrew calendars")
			.withCalendarCode("ALL")
			.build(),
		new Rite.Builder()
			.withRiteId(1L)
			.withRiteName("All Commemorations")
			.withRiteDetail(null)
			.withRiteCode("ALL")
			.withCreedIds(List.of(1L,2L,3L,4L,5L,6L,7L,8L,9L))
			.build()),

	GRE_TRI(
		new Calendar.Builder()
			.withCalendarId(2L)
			.withCalendarName("Western Christian")
			.withCalendarDetail("Gregorian solar calendar dates ('Anno Domini')")
			.withCalendarCode("GRE")
			.build(),
		new Rite.Builder()
			.withRiteId(2L)
			.withRiteName("Tridentine Catholic")
			.withRiteDetail("using the Pre-Vatican II General Roman Calendar, to 1969")
			.withRiteCode("TRI")
			.withCreedIds(List.of(1L,2L,3L,4L,5L))
			.build()),

	GRE_NOV(
		new Calendar.Builder()
			.withCalendarId(2L)
			.withCalendarName("Western Christian")
			.withCalendarDetail("Gregorian solar calendar dates ('Anno Domini')")
			.withCalendarCode("GRE")
			.build(),
		new Rite.Builder()
			.withRiteId(3L)
			.withRiteName("Novus Ordo Catholic")
			.withRiteDetail("using the General Roman Calendar of 1969")
			.withRiteCode("NOV")
			.withCreedIds(List.of(1L,2L,3L,4L,5L))
			.build()),

	GRE_ANG(
		new Calendar.Builder()
			.withCalendarId(2L)
			.withCalendarName("Western Christian")
			.withCalendarDetail("Gregorian solar calendar dates ('Anno Domini')")
			.withCalendarCode("GRE")
			.build(),
		new Rite.Builder()
			.withRiteId(4L)
			.withRiteName("Anglican Christian")
			.withRiteDetail("using the Lambeth Calendar of 1958")
			.withRiteCode("ANG")
			.withCreedIds(List.of(1L,2L,3L,4L,6L))
			.build()),

	GRE_LUTH(
		new Calendar.Builder()
			.withCalendarId(2L)
			.withCalendarName("Western Christian")
			.withCalendarDetail("Gregorian solar calendar dates ('Anno Domini')")
			.withCalendarCode("GRE")
			.build(),
		new Rite.Builder()
			.withRiteId(5L)
			.withRiteName("Lutheran Christian")
			.withRiteDetail("using the Lutheran Book of Worship of 1978")
			.withRiteCode("LUTH")
			.withCreedIds(List.of(1L,2L,3L,4L,6L))
			.build()),

	JUL_MEL(
		new Calendar.Builder()
			.withCalendarId(3L)
			.withCalendarName("Eastern Christian")
			.withCalendarDetail("Julian solar calendar dates ('Anno Domini')")
			.withCalendarCode("JUL")
			.build(),
		new Rite.Builder()
			.withRiteId(6L)
			.withRiteName("Orthodox Christian")
			.withRiteDetail("using the Meletian Revised Calendar of 1923")
			.withRiteCode("MEL")
			.withCreedIds(List.of(1L,2L,3L,7L))
			.build()),

	HIJ_SNI(
		new Calendar.Builder()
			.withCalendarId(4L)
			.withCalendarName("Muslim")
			.withCalendarDetail("Hijri lunar calendar dates ('Anno Hegirae')")
			.withCalendarCode("HIJ")
			.build(),
		new Rite.Builder()
			.withRiteId(7L)
			.withRiteName("Sunni Muslim")
			.withRiteDetail("using the Kuwaiti Tabular Calendar")
			.withRiteCode("SNI")
			.withCreedIds(List.of(1L,2L,8L,9L))
			.build()),

	HIJ_SHI(
		new Calendar.Builder()
			.withCalendarId(4L)
			.withCalendarName("Muslim")
			.withCalendarDetail("Hijri lunar calendar dates ('Anno Hegirae')")
			.withCalendarCode("HIJ")
			.build(),
		new Rite.Builder()
			.withRiteId(8L)
			.withRiteName("Shiite Muslim")
			.withRiteDetail("using the Misri Tabular Calendar")
			.withRiteCode("SHI")
			.withCreedIds(List.of(1L,2L,8L,10L))
			.build()),

	HEB_RAB(
		new Calendar.Builder()
			.withCalendarId(5L)
			.withCalendarName("Jewish")
			.withCalendarDetail("Hebrew lunisolar calendar dates ('Anno Mundi')")
			.withCalendarCode("HEB")
			.build(),
		new Rite.Builder()
			.withRiteId(9L)
			.withRiteName("Rabbinic Jewish")
			.withRiteDetail("using the Knesset Calendar")
			.withRiteCode("RAB")
			.withCreedIds(List.of(1L,11L))
			.build());
	
	private final Calendar calendar;

	private final Rite rite;

	private CalendarRiteEnum(Calendar calendar, Rite rite) {

		this.calendar = calendar;
		this.rite = rite;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public Rite getRite() {
		return rite;
	}

	public static List<CalendarRiteEnum> getCalendarRites() {
		final List<CalendarRiteEnum> resultList = new LinkedList<>();
		resultList.add(CalendarRiteEnum.ALL);
		resultList.add(CalendarRiteEnum.GRE_TRI);
		resultList.add(CalendarRiteEnum.GRE_NOV);
		resultList.add(CalendarRiteEnum.GRE_ANG);
		resultList.add(CalendarRiteEnum.GRE_LUTH);
		resultList.add(CalendarRiteEnum.JUL_MEL);
		resultList.add(CalendarRiteEnum.HIJ_SNI);
		resultList.add(CalendarRiteEnum.HIJ_SHI);
		resultList.add(CalendarRiteEnum.HEB_RAB);
		return resultList;
	}

	public static Map<Calendar, List<Rite>> getCalendarRiteMap() {
		final Map<Calendar, List<Rite>> map = new HashMap<>();
		map.put(CalendarRiteEnum.ALL.calendar, 
				List.of(CalendarRiteEnum.ALL.rite));
		map.put(CalendarRiteEnum.GRE_TRI.calendar, 
				List.of(CalendarRiteEnum.GRE_TRI.rite, 
						CalendarRiteEnum.GRE_NOV.rite, 
						CalendarRiteEnum.GRE_ANG.rite, 
						CalendarRiteEnum.GRE_LUTH.rite));
		map.put(CalendarRiteEnum.JUL_MEL.calendar, 
				List.of(CalendarRiteEnum.JUL_MEL.rite));
		map.put(CalendarRiteEnum.HIJ_SNI.calendar, 
				List.of(CalendarRiteEnum.HIJ_SNI.rite,
						CalendarRiteEnum.HIJ_SHI.rite));
		map.put(CalendarRiteEnum.HEB_RAB.calendar, 
				List.of(CalendarRiteEnum.HEB_RAB.rite));
		return map;
	}
}
