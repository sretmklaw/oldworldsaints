package com.cimeliarchium.model.dao;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "day")
public class Day implements Comparable<Day> {

	@Id
	@NotNull
	@Column(name = "day_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dayId;

	@Column(name = "day_of_month")
	private String dayOfMonth;

	@Column(name="month_id")
	private Long monthId;

	@Column(name="month_name")
	private String monthName;

	@Column(name="calendar_id")
	private Long calendarId;

	@Column(name="calendar_code")
	private String calendarCode;

	public static class Builder {

		private Long dayId;
		private String dayOfMonth;
		private Long monthId;
		private String monthName;
		private Long calendarId;
		private String calendarCode;

		public Builder withDayId(Long dayId) {
			this.dayId = dayId;
			return this;
		}

		public Builder withDayOfMonth(String dayOfMonth) {
			this.dayOfMonth = dayOfMonth;
			return this;
		}

		public Builder withMonthId(Long monthId) {
			this.monthId = monthId;
			return this;
		}

		public Builder withMonthName(String monthName) {
			this.monthName = monthName;
			return this;
		}

		public Builder withCalendarId(Long calendarId) {
			this.calendarId = calendarId;
			return this;
		}

		public Builder withCalendarCode(String calendarCode) {
			this.calendarCode = calendarCode;
			return this;
		}

		public Day build() {
			Day day = new Day();
			day.dayId = this.dayId;
			day.dayOfMonth = this.dayOfMonth;
			day.monthId = this.monthId;
			day.monthName = this.monthName;
			day.calendarId = this.calendarId;
			day.calendarCode = this.calendarCode;
			return day;
		}
	}

	public Long getDayId() {
		return dayId;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public Long getMonthId() {
		return monthId;
	}

	public String getMonthName() {
		return monthName;
	}

	public Long getCalendarId() {
		return calendarId;
	}

	public String getCalendarCode() {
		return calendarCode;
	}

	/**
	 * Display name values follow
	 */

	public Long getDayOfMonthNumeric() {
		return (dayOfMonth != null && dayOfMonth.length() <= 2) 
				? Long.valueOf(dayOfMonth) 
				: null;
	}

	public String getMonthAndDayFormattedIndicateJulian(String julianDateString) {
		final String monthAndDayFormatted = getMonthAndDayFormatted();
		return (julianDateString != null && julianDateString.equals(monthAndDayFormatted)) 
				? monthAndDayFormatted + CalendarDates.JULIAN_SUFFIX
				: monthAndDayFormatted;
	}

	public String getMonthAndDayFormatted() {
		return (dayOfMonth != null && dayOfMonth.length() > 2)
				? dayOfMonth
				: (monthName != null && dayOfMonth != null) 
					? dayOfMonth + " " + monthName
					: null;
	}

	@Override
	public String toString() {
		return "Day [dayId=" + dayId + ", dayOfMonth=" + dayOfMonth + ", monthId=" + monthId + ", monthName="
				+ monthName + ", calendarId=" + calendarId + ", calendarCode=" + calendarCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((calendarId == null) ? 0 : calendarId.hashCode());
		result = prime * result + ((dayId == null) ? 0 : dayId.hashCode());
		result = prime * result + ((dayOfMonth == null) ? 0 : dayOfMonth.hashCode());
		result = prime * result + ((monthId == null) ? 0 : monthId.hashCode());
		result = prime * result + ((monthName == null) ? 0 : monthName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Day other = (Day) obj;
		/** 
		 * Intentionally, exclude calendar comparison 
		 * so that AD Gregorian and Julian days return the same result.
		 **/
		//if (calendarId == null) {
		//	if (other.calendarId != null)
		//		return false;
		//} else if (!calendarId.equals(other.calendarId))
		//	return false;
		if (dayId == null) {
			if (other.dayId != null)
				return false;
		} else if (!dayId.equals(other.dayId))
			return false;
		if (dayOfMonth == null) {
			if (other.dayOfMonth != null)
				return false;
		} else if (!dayOfMonth.equals(other.dayOfMonth))
			return false;
		if (monthId == null) {
			if (other.monthId != null)
				return false;
		} else if (!monthId.equals(other.monthId))
			return false;
		if (monthName == null) {
			if (other.monthName != null)
				return false;
		} else if (!monthName.equals(other.monthName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Day other) {
		return Comparator
				.comparing(Day::getDayId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}
