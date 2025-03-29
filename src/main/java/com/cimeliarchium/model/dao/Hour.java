package com.cimeliarchium.model.dao;

import java.util.List;

public class Hour {

	private Long hourId;

	private HourType hourType;

	private List<Reading> readings;

	private String hourDisplayName;

	private Boolean isHourActive;

	private Hour() {}

	public static class Builder {

		private Long hourId;
		private HourType hourType;
		private List<Reading> readings;
		private String hourDisplayName;
		Boolean isHourActive;

		public Builder withHourId(Long hourId) {
			this.hourId = hourId;
			return this;
		}

		public Builder withHourType(HourType hourType) {
			this.hourType = hourType;
			return this;
		}

		public Builder withReadings(List<Reading> readings) {
			this.readings = readings;
			return this;
		}

		public Builder withHourDisplayName(String hourDisplayName) {
			this.hourDisplayName = hourDisplayName;
			return this;
		}

		public Builder withIsHourActive(Boolean isHourActive) {
			this.isHourActive = isHourActive;
			return this;
		}

		public Hour build() {
			Hour hour = new Hour();
			hour.hourId = this.hourId;
			hour.hourType = this.hourType;
			hour.readings = this.readings;
			hour.hourDisplayName = this.hourDisplayName;
			hour.isHourActive = this.isHourActive;
			return hour;
		}
	}

	public Long getHourId() {
		return hourId;
	}

	public void setHourId(Long hourId) {
		this.hourId = hourId;
	}

	public HourType getHourType() {
		return hourType;
	}

	public void setHourType(HourType hourType) {
		this.hourType = hourType;
	}

	public List<Reading> getReadings() {
		return readings;
	}

	public void setReadings(List<Reading> readings) {
		this.readings = readings;
	}

	public String getHourDisplayName() {
		return hourDisplayName;
	}

	public void setHourDisplayName(String hourDisplayName) {
		this.hourDisplayName = hourDisplayName;
	}

	public Boolean getIsHourActive() {
		return isHourActive;
	}

	public void setIsHourActive(Boolean isHourActive) {
		this.isHourActive = isHourActive;
	}

	@Override
	public String toString() {
		return "Hour [hourId=" + hourId + ", hourType=" + hourType + ", readings=" + readings + ", hourDisplayName="
				+ hourDisplayName + ", isHourActive=" + isHourActive + "]";
	}
}