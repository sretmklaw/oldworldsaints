package com.cimeliarchium.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lunar_phase")
public class LunarPhase {

	@Id
	@NotNull
	@Column(name = "lunar_phase_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lunarPhaseId;

	@Column(name = "lunar_phase_name_latin")
	private String lunarPhaseNameLatin;

	@Column(name = "lunar_phase_name_greek")
	private String lunarPhaseNameGreek;

	@Column(name = "lunar_phase_name_arabic")
	private String lunarPhaseNameArabic;

	@Column(name = "lunar_phase_name_hebrew")
	private String lunarPhaseNameHebrew;

	@Column(name = "lunar_phase_code")
	private String lunarPhaseCode;

	@Column(name = "lunar_phase_detail")
	private String lunarPhaseDetail;

	@Column(name = "start_day_of_month")
	private Integer startDayOfMonth;

	@Column(name = "end_day_of_month")
	private Integer endDayOfMonth;

	@Column(name = "lunar_phase_display_name")
	private String lunarPhaseDisplayName;

	private LunarPhase() {}

	public static class Builder {

		private Long lunarPhaseId;
		private String lunarPhaseNameLatin;
		private String lunarPhaseNameGreek;
		private String lunarPhaseNameArabic;
		private String lunarPhaseNameHebrew;
		private String lunarPhaseDetail;
		private String lunarPhaseCode;
		private Integer startDayOfMonth;
		private Integer endDayOfMonth;

		public Builder withLunarPhaseId(Long lunarPhaseId) {
			this.lunarPhaseId = lunarPhaseId;
			return this;
		}

		public Builder withLunarPhaseNameLatin(String lunarPhaseNameLatin) {
			this.lunarPhaseNameLatin = lunarPhaseNameLatin;
			return this;
		}

		public Builder withLunarPhaseNameGreek(String lunarPhaseNameGreek) {
			this.lunarPhaseNameGreek = lunarPhaseNameGreek;
			return this;
		}

		public Builder withLunarPhaseNameArabic(String lunarPhaseNameArabic) {
			this.lunarPhaseNameArabic = lunarPhaseNameArabic;
			return this;
		}

		public Builder withLunarPhaseNameHebrew(String lunarPhaseNameHebrew) {
			this.lunarPhaseNameHebrew = lunarPhaseNameHebrew;
			return this;
		}

		public Builder withLunarPhaseDetail(String lunarPhaseDetail) {
			this.lunarPhaseDetail = lunarPhaseDetail;
			return this;
		}

		public Builder withLunarPhaseCode(String lunarPhaseCode) {
			this.lunarPhaseCode = lunarPhaseCode;
			return this;
		}

		public Builder withStartDayId(Integer startDayId) {
			this.startDayOfMonth = startDayId;
			return this;
		}

		public Builder withEndDayId(Integer endDayId) {
			this.endDayOfMonth = endDayId;
			return this;
		}

		public LunarPhase build() {
			LunarPhase lunarPhase = new LunarPhase();
			lunarPhase.lunarPhaseId = this.lunarPhaseId;
			lunarPhase.lunarPhaseNameLatin = this.lunarPhaseNameLatin;
			lunarPhase.lunarPhaseNameGreek = this.lunarPhaseNameGreek;
			lunarPhase.lunarPhaseNameArabic = this.lunarPhaseNameArabic;
			lunarPhase.lunarPhaseNameHebrew = this.lunarPhaseNameHebrew;
			lunarPhase.lunarPhaseDetail = this.lunarPhaseDetail;
			lunarPhase.lunarPhaseCode = this.lunarPhaseCode;
			lunarPhase.startDayOfMonth = this.startDayOfMonth;
			lunarPhase.endDayOfMonth = this.endDayOfMonth;
			return lunarPhase;
		}
	}

	public Long getLunarPhaseId() {
		return lunarPhaseId;
	}

	public String getLunarPhaseNameLatin() {
		return lunarPhaseNameLatin;
	}

	public String getLunarPhaseNameGreek() {
		return lunarPhaseNameGreek;
	}

	public String getLunarPhaseNameArabic() {
		return lunarPhaseNameArabic;
	}

	public String getLunarPhaseNameHebrew() {
		return lunarPhaseNameHebrew;
	}

	public String getLunarPhaseDetail() {
		return lunarPhaseDetail;
	}

	public String getLunarPhaseCode() {
		return lunarPhaseCode;
	}

	public Integer getStartDayId() {
		return startDayOfMonth;
	}

	public Integer getEndDayId() {
		return endDayOfMonth;
	}

	public void setLunarPhaseDisplayName(String lunarPhaseDisplayName) {
		this.lunarPhaseDisplayName = lunarPhaseDisplayName;
	}

	public String getLunarPhaseDisplayName() {
		return lunarPhaseDisplayName;
	}

	public Boolean isLunarPhaseApplicableToHijriDayOfMonth(Integer hijriDayOfMonth) {
		return (hijriDayOfMonth >= this.startDayOfMonth && hijriDayOfMonth <= this.endDayOfMonth);
	}

	@Override
	public String toString() {
		return "LunarPhase [lunarPhaseId=" + lunarPhaseId + ", lunarPhaseDetail=" + lunarPhaseDetail + ", lunarPhaseNameLatin=" + lunarPhaseNameLatin
				+ ", lunarPhaseNameGreek=" + lunarPhaseNameGreek + ", lunarPhaseNameArabic=" + lunarPhaseNameArabic
				+ ", lunarPhaseNameHebrew=" + lunarPhaseNameHebrew + ", lunarPhaseCode=" + lunarPhaseCode + ", startDayId=" + startDayOfMonth
				+ ", endDayId=" + endDayOfMonth + "]";
	}
}