package com.cimeliarchium.model.dao;

import java.util.Comparator;

public class LiturgyType {

	public static Comparator<LiturgyType> compareByCalendarCode() {
		return Comparator.comparing(LiturgyType::getCalendarCode, 
				Comparator.nullsFirst(Comparator.naturalOrder()));
	}

	private Long liturgyTypeId;

	private String liturgyName;

	private String liturgyDetails;

	private String cycleDayName;

	private String calendarDateString;

	private String calendarCode;

	private String calendarName;

	private LiturgyType() {}

	public static class Builder {

		private Long liturgyTypeId;
		private String liturgyName;
		private String liturgyDetails;
		private String cycleDayName;

		public Builder withLiturgyTypeId(Long liturgyTypeId) {
			this.liturgyTypeId = liturgyTypeId;
			return this;
		}

		public Builder withLiturgyName(String liturgyName) {
			this.liturgyName = liturgyName;
			return this;
		}

		public Builder withLiturgyDetails(String liturgyDetails) {
			this.liturgyDetails = liturgyDetails;
			return this;
		}

		public Builder withCycleDayName(String cycleDayName) {
			this.cycleDayName = cycleDayName;
			return this;
		}

		public LiturgyType build() {
			LiturgyType liturgyType = new LiturgyType();
			liturgyType.liturgyTypeId = this.liturgyTypeId;
			liturgyType.liturgyName = this.liturgyName;
			liturgyType.liturgyDetails = this.liturgyDetails;
			liturgyType.cycleDayName = this.cycleDayName;
			return liturgyType;
		}
	}

	public Long getLiturgyTypeId() {
		return liturgyTypeId;
	}

	public String getLiturgyName() {
		return liturgyName;
	}

	public String getLiturgyDetails() {
		return liturgyDetails;
	}

	public String getCycleDayName() {
		return cycleDayName;
	}

	public String getCalendarDateString() {
		return calendarDateString;
	}

	public void setCalendarDateString(String calendarDateString) {
		this.calendarDateString = calendarDateString;
	}

	public String getCalendarCode() {
		return calendarCode;
	}

	public void setCalendarCode(String calendarCode) {
		this.calendarCode = calendarCode;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	@Override
	public String toString() {
		return "LiturgyType [liturgyTypeId=" + liturgyTypeId + ", liturgyName=" + liturgyName + ", liturgyDetails="
				+ liturgyDetails + ", calendarDateString=" + calendarDateString + ", cycleDayName=" + cycleDayName 
				+ ", calendarCode=" + calendarCode + ", calendarName=" + calendarName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendarCode == null) ? 0 : calendarCode.hashCode());
		result = prime * result + ((calendarName == null) ? 0 : calendarName.hashCode());
		result = prime * result + ((cycleDayName == null) ? 0 : cycleDayName.hashCode());
		result = prime * result + ((calendarDateString == null) ? 0 : calendarDateString.hashCode());
		result = prime * result + ((liturgyDetails == null) ? 0 : liturgyDetails.hashCode());
		result = prime * result + ((liturgyName == null) ? 0 : liturgyName.hashCode());
		result = prime * result + ((liturgyTypeId == null) ? 0 : liturgyTypeId.hashCode());
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
		LiturgyType other = (LiturgyType) obj;
		if (calendarCode == null) {
			if (other.calendarCode != null)
				return false;
		} else if (!calendarCode.equals(other.calendarCode))
			return false;
		if (calendarName == null) {
			if (other.calendarName != null)
				return false;
		} else if (!calendarName.equals(other.calendarName))
			return false;
		if (cycleDayName == null) {
			if (other.cycleDayName != null)
				return false;
		} else if (!cycleDayName.equals(other.cycleDayName))
			return false;
		if (calendarDateString == null) {
			if (other.calendarDateString != null)
				return false;
		} else if (!calendarDateString.equals(other.calendarDateString))
			return false;
		if (liturgyDetails == null) {
			if (other.liturgyDetails != null)
				return false;
		} else if (!liturgyDetails.equals(other.liturgyDetails))
			return false;
		if (liturgyName == null) {
			if (other.liturgyName != null)
				return false;
		} else if (!liturgyName.equals(other.liturgyName))
			return false;
		if (liturgyTypeId == null) {
			if (other.liturgyTypeId != null)
				return false;
		} else if (!liturgyTypeId.equals(other.liturgyTypeId))
			return false;
		return true;
	}
}