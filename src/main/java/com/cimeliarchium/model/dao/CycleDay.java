package com.cimeliarchium.model.dao;

public class CycleDay {

	private Long cycleDayLiturgyTypeDtoId;

	private Long cycleDayTypeId;

	private String cycleDayName;

	private Boolean isCycleDayLiturgySpecial;

	private CalendarDates calendarDates;

	private CycleDay() { }

	public static class Builder {

		private Long cycleDayLiturgyTypeDtoId;
		private Long cycleDayTypeId;
		private String cycleDayName;
		private Boolean isCycleDayLiturgySpecial;

		public Builder withCycleDayLiturgyTypeDtoId(Long cycleDayLiturgyTypeDtoId) {
			this.cycleDayLiturgyTypeDtoId = cycleDayLiturgyTypeDtoId;
			return this;
		}

		public Builder withCycleDayTypeId(Long cycleDayTypeId) {
			this.cycleDayTypeId = cycleDayTypeId;
			return this;
		}

		public Builder withCycleDayName(String cycleDayName) {
			this.cycleDayName = cycleDayName;
			return this;
		}

		public Builder withIsCycleDayLiturgySpecial(Boolean isCycleDayLiturgySpecial) {
			this.isCycleDayLiturgySpecial = isCycleDayLiturgySpecial;
			return this;
		}

		public CycleDay build() {
			CycleDay cycleDay = new CycleDay();
			cycleDay.cycleDayLiturgyTypeDtoId = this.cycleDayLiturgyTypeDtoId;
			cycleDay.cycleDayTypeId = this.cycleDayTypeId;
			cycleDay.cycleDayName = this.cycleDayName;
			cycleDay.isCycleDayLiturgySpecial = this.isCycleDayLiturgySpecial;
			return cycleDay;
		}
	}

	public Long getCycleDayLiturgyTypeDtoId() {
		return cycleDayLiturgyTypeDtoId;
	}

	public Long getCycleDayTypeId() {
		return cycleDayTypeId;
	}

	public String getCycleDayName() {
		return cycleDayName;
	}

	public Boolean hasSpecialLiturgy() {
		return isCycleDayLiturgySpecial;
	}

	public void setCalendarDates(CalendarDates calendarDates) {
		this.calendarDates = calendarDates;
	}

	public CalendarDates getCalendarDates() {
		return calendarDates;
	}
}
