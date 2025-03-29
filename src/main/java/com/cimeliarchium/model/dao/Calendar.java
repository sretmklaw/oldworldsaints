package com.cimeliarchium.model.dao;

import java.util.Comparator;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.model.BaseEntity;

/**
 * NOTE: Any updates to Calendar IDs in the database must be accompanied by
 * an update to the static values enumerated in applications.properties, since
 * these references are hard-coded at various places throughout the application.
 */
@Entity
@Table(name = "calendar")
public class Calendar extends BaseEntity implements Comparable<Calendar> {

	@Id
	@NotNull
	@Column(name = "calendar_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long calendarId;

	@Column(name = "calendar_name")
	private String calendarName;

	@Column(name = "calendar_detail")
	private String calendarDetail;

	@Column(name = "calendar_code")
	private String calendarCode;

	public static class Builder {

		private Long calendarId;
		private String calendarName;
		private String calendarDetail;
		private String calendarCode;

		public Builder withCalendarId (Long calendarId) {
			this.calendarId = calendarId;
			return this;
		}

		public Builder withCalendarName (String calendarName) {
			this.calendarName = calendarName;
			return this;
		}

		public Builder withCalendarDetail (String calendarDetail) {
			this.calendarDetail = calendarDetail;
			return this;
		}

		public Builder withCalendarCode (String calendarCode) {
			this.calendarCode = calendarCode;
			return this;
		}

		public Calendar build() {
			Calendar calendar = new Calendar();
			calendar.calendarId = this.calendarId;
			calendar.calendarName = this.calendarName;
			calendar.calendarDetail = this.calendarDetail;
			calendar.calendarCode = this.calendarCode;
			return calendar;
		}
	}

	public Long getCalendarId() {
		return calendarId;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public String getCalendarDetail() {
		return calendarDetail;
	}

	public String getCalendarCode() {
		return calendarCode;
	}

	/**
	 * The following methods are used to format commemorations results on Main and Search views.
	 */

	public Long getUserCalendarOrAllCommemorations(Boolean showAll) {
		return (showAll != null) 
				? CALENDAR_ALL_ID 
				: Optional.ofNullable((Long) calendarId).orElse(null);
	}

	public Boolean getIsAllCommemorations(Boolean showAll) {
		return (showAll != null) 
				? true 
				: this.getIsAllCommemorations();
	}

	public Boolean getIsAllCommemorations() {
		return calendarId == CALENDAR_ALL_ID;
	}

	public Boolean getIsGregorian() {
		return calendarId == CALENDAR_GREGORIAN_ID;
	}

	public Boolean getIsJulian() {
		return calendarId == CALENDAR_JULIAN_ID;
	}

	/**
	 * Used to prepend the Bismillah onto Psalm Reading content for users 
	 * that have a Rite applicable to 'Islamic' or 'All Commemorations' calendars,
	 * and likewise for any Islamic commemoration Reading content.
	 * 
	 * @param userCalendarId the current User's Calendar
	 * @param cmCreedId the current Commemoration's Creed - for Hour Reading, this will be null
	 * @return
	 */
	public static Boolean isIslamicCreedApplicable(Long userCalendarId, Long cmCreedId) {
		final Boolean isIslamicUser = (userCalendarId != null) 
				&& (userCalendarId == CALENDAR_HIJRI_ID);
		final Boolean shouldPrependBismillahToHourReading = (cmCreedId == null) 
				&& (isIslamicUser || userCalendarId == CALENDAR_ALL_ID);
		final Boolean shouldPrependBismillahToCalendarDateReading = (cmCreedId != null) 
				&& (isIslamicUser || Creed.isIslamicCreedExclusive(cmCreedId));
		return shouldPrependBismillahToHourReading || shouldPrependBismillahToCalendarDateReading; 
	}

	/**
	 * Used to append the Doxology onto any Christian commemoration Reading,
	 * and likewise onto Psalm Reading content for users having 
	 * a Rite applicable to Christian calendars.
	 * 
	 * @param userCalendarId the current User's Calendar
	 * @param cmCreedId the current Commemoration's Creed - for Hour Reading, this will be null
	 * @return
	 */
	public static Boolean isTrinitarianCreedApplicable(Long userCalendarId, Long cmCreedId) {
		final Boolean isChristianUser = (userCalendarId != null) 
				&& (userCalendarId == CALENDAR_GREGORIAN_ID 
						|| userCalendarId == CALENDAR_JULIAN_ID);
		final Boolean shouldAppendDoxologyToHourReading = (cmCreedId == null) 
				&& (isChristianUser || userCalendarId == CALENDAR_ALL_ID);
		final Boolean shouldAppendDoxologyToCalendarDateReading = (cmCreedId != null) 
				&& (isChristianUser || (Creed.isChristianCreedExclusive(cmCreedId)));
		return shouldAppendDoxologyToHourReading || shouldAppendDoxologyToCalendarDateReading; 
	}

	@Override
	public String toString() {
		return "Calendar [calendarId=" + calendarId + ", calendarName=" + calendarName + ", calendarDetail="
				+ calendarDetail + ", calendarCode=" + calendarCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendarCode == null) ? 0 : calendarCode.hashCode());
		result = prime * result + ((calendarDetail == null) ? 0 : calendarDetail.hashCode());
		result = prime * result + ((calendarId == null) ? 0 : calendarId.hashCode());
		result = prime * result + ((calendarName == null) ? 0 : calendarName.hashCode());
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
		Calendar other = (Calendar) obj;
		if (calendarCode == null) {
			if (other.calendarCode != null)
				return false;
		} else if (!calendarCode.equals(other.calendarCode))
			return false;
		if (calendarDetail == null) {
			if (other.calendarDetail != null)
				return false;
		} else if (!calendarDetail.equals(other.calendarDetail))
			return false;
		if (calendarId == null) {
			if (other.calendarId != null)
				return false;
		} else if (!calendarId.equals(other.calendarId))
			return false;
		if (calendarName == null) {
			if (other.calendarName != null)
				return false;
		} else if (!calendarName.equals(other.calendarName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Calendar other) {
		return Comparator
				.comparing(Calendar::getCalendarName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}