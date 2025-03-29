package com.cimeliarchium.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "hour_type")
public class HourType {

	@Id
	@NotNull
	@Column(name = "hour_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hourTypeId;

	@Column(name = "hour_type_code")
	private String hourTypeCode;

	@Column(name = "hour_name_latin")
	private String hourNameLatin;

	@Column(name = "hour_name_greek")
	private String hourNameGreek;

	@Column(name = "hour_name_arabic")
	private String hourNameArabic;

	@Column(name = "hour_name_hebrew")
	private String hourNameHebrew;

	@Column(name = "hour_start")
	private Integer hourStart;

	@Column(name = "hour_end")
	private Integer hourEnd;

	private HourType() {}

	public static class Builder {

		private Long hourTypeId;
		private String hourTypeCode;
		private String hourNameLatin;
		private String hourNameGreek;
		private String hourNameArabic;
		private String hourNameHebrew;
		private Integer hourStart;
		private Integer hourEnd;

		public Builder withHourTypeId(Long hourTypeId) {
			this.hourTypeId = hourTypeId;
			return this;
		}

		public Builder withHourTypeCode(String hourTypeCode) {
			this.hourTypeCode = hourTypeCode;
			return this;
		}

		public Builder withHourNameLatin(String hourNameLatin) {
			this.hourNameLatin = hourNameLatin;
			return this;
		}

		public Builder withHourNameGreek(String hourNameGreek) {
			this.hourNameGreek = hourNameGreek;
			return this;
		}

		public Builder withHourNameArabic(String hourNameArabic) {
			this.hourNameArabic = hourNameArabic;
			return this;
		}

		public Builder withHourNameHebrew(String hourNameHebrew) {
			this.hourNameHebrew = hourNameHebrew;
			return this;
		}

		public Builder withHourStart(Integer hourStart) {
			this.hourStart = hourStart;
			return this;
		}

		public Builder withHourEnd(Integer hourEnd) {
			this.hourEnd = hourEnd;
			return this;
		}

		public HourType build() {
			HourType hourType = new HourType();
			hourType.hourTypeId = this.hourTypeId;
			hourType.hourTypeCode = this.hourTypeCode;
			hourType.hourNameLatin = this.hourNameLatin;
			hourType.hourNameGreek = this.hourNameGreek;
			hourType.hourNameArabic = this.hourNameArabic;
			hourType.hourNameHebrew = this.hourNameHebrew;
			hourType.hourStart = this.hourStart;
			hourType.hourEnd = this.hourEnd;
			return hourType;
		}
	}

	public Long getHourTypeId() {
		return hourTypeId;
	}

	public String getHourTypeCode() {
		return hourTypeCode;
	}

	public String getHourNameLatin() {
		return hourNameLatin;
	}

	public String getHourNameGreek() {
		return hourNameGreek;
	}

	public String getHourNameArabic() {
		return hourNameArabic;
	}

	public String getHourNameHebrew() {
		return hourNameHebrew;
	}

	public Integer getHourStart() {
		return hourStart;
	}

	public Integer getHourEnd() {
		return hourEnd;
	}

	@Override
	public String toString() {
		return "HourType [hourTypeId=" + hourTypeId + ", hourTypeCode=" + hourTypeCode + ", hourNameLatin="
				+ hourNameLatin + ", hourNameGreek=" + hourNameGreek + ", hourNameArabic=" + hourNameArabic
				+ ", hourNameHebrew=" + hourNameHebrew + ", hourStart=" + hourStart + ", hourEnd=" + hourEnd + "]";
	}
}