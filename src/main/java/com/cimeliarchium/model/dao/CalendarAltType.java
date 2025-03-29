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
@Table(name = "calendar_alt_type")
public class CalendarAltType implements Comparable<CalendarAltType> {

	@Id
	@NotNull
	@Column(name = "calendar_alt_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long calendarAltTypeId;

	@Column(name = "calendar_alt_type_name")
	private String calendarAltTypeName;

	private CalendarAltType() {}

	public static class Builder {

		private Long calendarAltTypeId;
		private String calendarAltTypeName;

		public Builder withCalendarAltTypeId(Long calendarAltTypeId) {
			this.calendarAltTypeId = calendarAltTypeId;
			return this;
		}

		public Builder withCalendarAltTypeName(String calendarAltTypeName) {
			this.calendarAltTypeName = calendarAltTypeName;
			return this;
		}

		public CalendarAltType build() {
			CalendarAltType creed = new CalendarAltType();
			creed.calendarAltTypeId = this.calendarAltTypeId;
			creed.calendarAltTypeName = this.calendarAltTypeName;
			return creed;
		}
	}

	public Long getCalendarAltTypeId() {
		return calendarAltTypeId;
	}

	public String getCalendarAltTypeName() {
		return calendarAltTypeName;
	}

	@Override
	public String toString() {
		return "Creed [calendarAltTypeId=" + calendarAltTypeId + ", calendarAltTypeName=" + calendarAltTypeName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendarAltTypeId == null) ? 0 : calendarAltTypeId.hashCode());
		result = prime * result + ((calendarAltTypeName == null) ? 0 : calendarAltTypeName.hashCode());
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
		CalendarAltType other = (CalendarAltType) obj;
		if (calendarAltTypeId == null) {
			if (other.calendarAltTypeId != null)
				return false;
		} else if (!calendarAltTypeId.equals(other.calendarAltTypeId))
			return false;
		if (calendarAltTypeName == null) {
			if (other.calendarAltTypeName != null)
				return false;
		} else if (!calendarAltTypeName.equals(other.calendarAltTypeName))
			return false;
		return true;
	}

	@Override
	public int compareTo(CalendarAltType other) {
		return Comparator
				.comparing(CalendarAltType::getCalendarAltTypeName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}