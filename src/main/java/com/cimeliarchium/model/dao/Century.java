package com.cimeliarchium.model.dao;

import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "century")
public class Century extends BaseEntity {

	@Id
	@NotNull
	@Column(name = "century_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long centuryId;

	@Column(name = "century_name_ad")
	private String centuryNameAd;

	@Column(name = "century_name_am")
	private String centuryNameAm;

	@Column(name = "century_name_ah")
	private String centuryNameAh;

	@Column(name = "century_display_name")
	private String centuryDisplayName;

	private Century() {}

	public static class Builder {

		private Long centuryId;
		private String centuryNameAd;
		private String centuryNameAm;
		private String centuryNameAh;

		public Builder withCenturyId(Long centuryId) {
			this.centuryId = centuryId;
			return this;
		}

		public Builder withCenturyNameAd(String centuryNameAd) {
			this.centuryNameAd = centuryNameAd;
			return this;
		}

		public Builder withCenturyNameAm(String centuryNameAm) {
			this.centuryNameAm = centuryNameAm;
			return this;
		}

		public Builder withCenturyNameAh(String centuryNameAh) {
			this.centuryNameAh = centuryNameAh;
			return this;
		}

		public Century build() {
			Century century = new Century();
			century.centuryId = this.centuryId;
			century.centuryNameAd = this.centuryNameAd;
			century.centuryNameAm = this.centuryNameAm;
			century.centuryNameAh = this.centuryNameAh;
			return century;
		}
	}

	public Long getCenturyId() {
		return centuryId;
	}

	public String getCenturyNameAd() {
		return centuryNameAd;
	}

	public String getCenturyNameAm() {
		return centuryNameAm;
	}

	public String getCenturyNameAh() {
		return centuryNameAh;
	}

	public String getCenturyDisplayName() {
		return centuryDisplayName;
	}

	public void setCenturyDisplayName(String centuryDisplayName) {
		this.centuryDisplayName = centuryDisplayName;
	}

	/**
	 * Method used to format Century display name applicable to all Calendars.
	 * 
	 * @return centuryName
	 */
	public String getCenturyShowAllName() {
		if (this.centuryId == null || this.centuryId == CENTURY_TIMEIMMEMORIAL_ID) {
			return this.centuryNameAd;
		} else {
			StringJoiner sj = new StringJoiner(" / ");
			if (this.getCenturyNameAd() != null) {
				sj.add(this.getCenturyNameAd());
			}
			if (this.getCenturyNameAh() != null) {
				sj.add(this.getCenturyNameAh());
			}
			if (this.getCenturyNameAm() != null) {
				sj.add(this.getCenturyNameAm());
			}
			return sj.toString().replace("Century ", "");
		}
	}

	/**
	 * Method used to format Century display name applicable to a specific Calendar.
	 * 
	 * @param century the Century
	 * @param calendarId the User Calendar ID
	 * @return centuryName
	 */
	public String getCenturyNameFormatted(Long calendarId) {
		if (calendarId == null || calendarId == CALENDAR_ALL_ID) {
			return this.getCenturyShowAllName();
		} else if (this.centuryId == CENTURY_TIMEIMMEMORIAL_ID) {
			return "Time Immemorial";
		} else if (calendarId == CALENDAR_GREGORIAN_ID || calendarId == CALENDAR_JULIAN_ID) {
			return this.centuryNameAd;
		} else if (calendarId == CALENDAR_HIJRI_ID) {
			return this.centuryNameAh;
		} else if (calendarId == CALENDAR_HEBREW_ID) {
			return this.centuryNameAm;
		} else {
			return null;
		}
	}

	public static Pair<Long,Long> getPrevNextCenturyIds(Long centuryId) {
		if (centuryId == CENTURY_TIMEIMMEMORIAL_ID) {
			return new ImmutablePair<Long,Long>(CENTURY_LAST_ID, CENTURY_FIRST_ID);
		} else if (centuryId <= CENTURY_FIRST_ID) {
			return new ImmutablePair<Long,Long>(CENTURY_LAST_ID, CENTURY_FIRST_ID+1);
		} else if (centuryId >= CENTURY_LAST_ID) {
			return new ImmutablePair<Long,Long>(CENTURY_LAST_ID-1, CENTURY_FIRST_ID);
		} else {
			return new ImmutablePair<Long,Long>(centuryId-1, centuryId+1);
		}
	}

	public static Double getCenturyTimelineMarkerPosition(Long centuryId) {
		if (centuryId == CENTURY_TIMEIMMEMORIAL_ID || centuryId <= CENTURY_FIRST_ID) {
			return 0.0;
		} else if (centuryId >= CENTURY_LAST_ID) {
			return 95.0;
		} else {
			return Double.valueOf(centuryId-1) * 3.17;
		}
	}

	@Override
	public String toString() {
		return "Century [centuryId=" + centuryId + ", centuryNameAd=" + centuryNameAd + ", centuryNameAm="
				+ centuryNameAm + ", centuryNameAh=" + centuryNameAh + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centuryId == null) ? 0 : centuryId.hashCode());
		result = prime * result + ((centuryNameAd == null) ? 0 : centuryNameAd.hashCode());
		result = prime * result + ((centuryNameAh == null) ? 0 : centuryNameAh.hashCode());
		result = prime * result + ((centuryNameAm == null) ? 0 : centuryNameAm.hashCode());
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
		Century other = (Century) obj;
		if (centuryId == null) {
			if (other.centuryId != null)
				return false;
		} else if (!centuryId.equals(other.centuryId))
			return false;
		if (centuryNameAd == null) {
			if (other.centuryNameAd != null)
				return false;
		} else if (!centuryNameAd.equals(other.centuryNameAd))
			return false;
		if (centuryNameAh == null) {
			if (other.centuryNameAh != null)
				return false;
		} else if (!centuryNameAh.equals(other.centuryNameAh))
			return false;
		if (centuryNameAm == null) {
			if (other.centuryNameAm != null)
				return false;
		} else if (!centuryNameAm.equals(other.centuryNameAm))
			return false;
		return true;
	}
}