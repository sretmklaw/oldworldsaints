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
@Table(name = "calendar_alt_reason")
public class CalendarAltReason implements Comparable<CalendarAltReason> {

	@Id
	@NotNull
	@Column(name = "calendar_alt_reason_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long calendarAltReasonId;

	@Column(name = "calendar_alt_reason_name")
	private String calendarAltReasonName;

	private CalendarAltReason() {}

	public static class Builder {

		private Long calendarAltReasonId;
		private String calendarAltReasonName;

		public Builder withCalendarAltReasonId(Long calendarAltReasonId) {
			this.calendarAltReasonId = calendarAltReasonId;
			return this;
		}

		public Builder withCalendarAltReasonName(String calendarAltReasonName) {
			this.calendarAltReasonName = calendarAltReasonName;
			return this;
		}

		public CalendarAltReason build() {
			CalendarAltReason creed = new CalendarAltReason();
			creed.calendarAltReasonId = this.calendarAltReasonId;
			creed.calendarAltReasonName = this.calendarAltReasonName;
			return creed;
		}
	}

	public Long getCalendarAltReasonId() {
		return calendarAltReasonId;
	}

	public String getCalendarAltReasonName() {
		return calendarAltReasonName;
	}

	@Override
	public String toString() {
		return "Creed [calendarAltReasonId=" + calendarAltReasonId + ", calendarAltReasonName=" + calendarAltReasonName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendarAltReasonId == null) ? 0 : calendarAltReasonId.hashCode());
		result = prime * result + ((calendarAltReasonName == null) ? 0 : calendarAltReasonName.hashCode());
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
		CalendarAltReason other = (CalendarAltReason) obj;
		if (calendarAltReasonId == null) {
			if (other.calendarAltReasonId != null)
				return false;
		} else if (!calendarAltReasonId.equals(other.calendarAltReasonId))
			return false;
		if (calendarAltReasonName == null) {
			if (other.calendarAltReasonName != null)
				return false;
		} else if (!calendarAltReasonName.equals(other.calendarAltReasonName))
			return false;
		return true;
	}

	@Override
	public int compareTo(CalendarAltReason other) {
		return Comparator
				.comparing(CalendarAltReason::getCalendarAltReasonName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}