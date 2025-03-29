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
@Table(name = "month")
public class Month implements Comparable<Month> {

	@Id
	@NotNull
	@Column(name = "month_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long monthId;

	@Column(name = "month_name")
	private String monthName;

	@Column(name = "calendar_id")
	private Long calendarId;

	public static class Builder {

		private Long monthId;
		private String monthName;
		private Long calendarId;

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

		public Month build() {
			Month month = new Month();
			month.monthId = this.monthId;
			month.monthName = this.monthName;
			month.calendarId = this.calendarId;
			return month;
		}
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

	@Override
	public String toString() {
		return "Month [monthId=" + monthId + ", monthName=" + monthName + ", calendarId=" + calendarId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendarId == null) ? 0 : calendarId.hashCode());
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
		Month other = (Month) obj;
		if (calendarId == null) {
			if (other.calendarId != null)
				return false;
		} else if (!calendarId.equals(other.calendarId))
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
	public int compareTo(Month other) {
		return Comparator
				.comparing(Month::getMonthId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}