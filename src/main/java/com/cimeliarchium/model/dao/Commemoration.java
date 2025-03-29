package com.cimeliarchium.model.dao;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "commemoration")
public class Commemoration extends BaseEntity {

	@Id
	@NotNull
	@Column(name = "commemoration_id")
	@GenericGenerator(name = "AppIdentityGenerator", strategy = "com.cimeliarchium.config.AppIdentityGenerator")
	@GeneratedValue(generator = "AppIdentityGenerator")
	private Long commemorationId;

	@Column(name = "commemoration_override_id")
	private String commemorationOverrideId;

	@Column(name = "day_id")
	private Long dayId;

	@Column(name = "alt_day_ad_id")
	private Long altDayAdId;

	@Column(name = "alt_day_ah_id")
	private Long altDayAhId;

	@Column(name = "alt_day_am_id")
	private Long altDayAmId;

	@Column(name = "creed_id")
	private Long creedId;

	@Column(name = "calendar_alt_type_id")
	private Long calendarAltTypeId;

	@Column(name = "calendar_alt_reason_id")
	private Long calendarAltReasonId;

	@Column(name = "commemoration_type_id")
	private Long commemorationTypeId;

	@Column(name = "commemoration_name")
	private String commemorationName;

	@Column(name = "commemoration_alt_name")
	private String commemorationAltName;

	@Column(name = "commemoration_year_ad")
	private String commemorationYearAd;

	@Column(name = "commemoration_year_ah")
	private String commemorationYearAh;

	@Column(name = "commemoration_year_am")
	private String commemorationYearAm;

	@Column(name = "commemoration_detail")
	private String commemorationDetail;

	@Column(name = "location_id")
	private Long locationId;

	@Column(name = "alt_location_id")
	private Long altLocationId;

	@Column(name = "patronage_id")
	private Long patronageId;

	@Column(name = "century_id")
	private Long centuryId;

	@Column(name = "reading_id")
	private Long readingId;

	@Column(name = "tag_a_id")
	private Long tagAId;

	@Column(name = "tag_b_id")
	private Long tagBId;

	@Column(name = "tag_c_id")
	private Long tagCId;

	@Column(name = "tag_d_id")
	private Long tagDId;

	@Column(name = "tag_e_id")
	private Long tagEId;

	@Column(name = "reference_id")
	private Long referenceId;

	@Column(name = "reference_start")
	private Long referenceStart;

	@Column(name = "reference_end")
	private Long referenceEnd;

	@Column(name = "star_id")
	private Long starId;

	@Column(name = "contributors")
	private String contributors;

	@Column(name = "last_update_time")
	private LocalDate lastUpdateTime;

	@Column(name = "has_last_contributor_been_notified")
	private Boolean hasLastContributorBeenNotified;

	private Commemoration() {}

	public static class Builder {

		private Long commemorationId;
		private String commemorationOverrideId;
		private Long dayId;
		private Long altDayAdId;
		private Long altDayAhId;
		private Long altDayAmId;
		private Long creedId;
		private Long calendarAltTypeId;
		private Long calendarAltReasonId;
		private Long commemorationTypeId;
		private String commemorationName;
		private String commemorationAltName;
		private String commemorationYearAd;
		private String commemorationYearAh;
		private String commemorationYearAm;
		private String commemorationDetail;
		private Long locationId;
		private Long altLocationId;
		private Long patronageId;
		private Long centuryId;
		private Long readingId;
		private Long tagAId;
		private Long tagBId;
		private Long tagCId;
		private Long tagDId;
		private Long tagEId;
		private Long referenceId;
		private Long referenceStart;
		private Long referenceEnd;
		private Long starId;
		private String contributors;
		private LocalDate lastUpdateTime;
		private Boolean hasLastContributorBeenNotified;

		public Builder withCommemorationId(Long commemorationId) {
			this.commemorationId = commemorationId;
			return this;
		}

		public Builder withCommemorationOverrideId(String commemorationOverrideId) {
			this.commemorationOverrideId = commemorationOverrideId;
			return this;
		}

		public Builder withDayId(Long dayId) {
			this.dayId = dayId;
			return this;
		}

		public Builder withAltDayAdId(Long altDayAdId) {
			this.altDayAdId = altDayAdId;
			return this;
		}

		public Builder withAltDayAhId(Long altDayAhId) {
			this.altDayAhId = altDayAhId;
			return this;
		}

		public Builder withAltDayAmId(Long altDayAmId) {
			this.altDayAmId = altDayAmId;
			return this;
		}

		public Builder withCreedId(Long creedId) {
			this.creedId = creedId;
			return this;
		}

		public Builder withCalendarAltTypeId(Long calendarAltTypeId) {
			this.calendarAltTypeId = calendarAltTypeId;
			return this;
		}

		public Builder withCalendarAltReasonId(Long calendarAltReasonId) {
			this.calendarAltReasonId = calendarAltReasonId;
			return this;
		}

		public Builder withCommemorationTypeId(Long commemorationTypeId) {
			this.commemorationTypeId = commemorationTypeId;
			return this;
		}

		public Builder withCommemorationName(String commemorationName) {
			this.commemorationName = commemorationName;
			return this;
		}

		public Builder withCommemorationAltName(String commemorationAltName) {
			this.commemorationAltName = commemorationAltName;
			return this;
		}

		public Builder withCommemorationYearAd(String commemorationYearAd) {
			this.commemorationYearAd = commemorationYearAd;
			return this;
		}

		public Builder withCommemorationYearAh(String commemorationYearAh) {
			this.commemorationYearAh = commemorationYearAh;
			return this;
		}

		public Builder withCommemorationYearAm(String commemorationYearAm) {
			this.commemorationYearAm = commemorationYearAm;
			return this;
		}

		public Builder withCommemorationDetail(String commemorationDetail) {
			this.commemorationDetail = commemorationDetail;
			return this;
		}

		public Builder withLocationId(Long locationId) {
			this.locationId = locationId;
			return this;
		}

		public Builder withAltLocationId(Long altLocationId) {
			this.altLocationId = altLocationId;
			return this;
		}

		public Builder withPatronageId(Long patronageId) {
			this.patronageId = patronageId;
			return this;
		}

		public Builder withCenturyId(Long centuryId) {
			this.centuryId = centuryId;
			return this;
		}

		public Builder withReadingId(Long readingId) {
			this.readingId = readingId;
			return this;
		}

		public Builder withTagAId(Long tagAId) {
			this.tagAId = tagAId;
			return this;
		}

		public Builder withTagBId(Long tagBId) {
			this.tagBId = tagBId;
			return this;
		}

		public Builder withTagCId(Long tagCId) {
			this.tagCId = tagCId;
			return this;
		}

		public Builder withTagDId(Long tagDId) {
			this.tagDId = tagDId;
			return this;
		}

		public Builder withTagEId(Long tagEId) {
			this.tagEId = tagEId;
			return this;
		}

		public Builder withReferenceId(Long referenceId) {
			this.referenceId = referenceId;
			return this;
		}

		public Builder withReferenceStart(Long referenceStart) {
			this.referenceStart = referenceStart;
			return this;
		}

		public Builder withReferenceEnd(Long referenceEnd) {
			this.referenceEnd = referenceEnd;
			return this;
		}

		public Builder withStarId(Long starId) {
			this.starId = starId;
			return this;
		}

		public Builder withContributors(String contributors) {
			this.contributors = contributors;
			return this;
		}

		public Builder withLastUpdateTime(LocalDate lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
			return this;
		}

		public Builder withHasLastContributorBeenNotified(Boolean hasLastContributorBeenNotified) {
			this.hasLastContributorBeenNotified = hasLastContributorBeenNotified;
			return this;
		}

		public Commemoration build() {
			Commemoration cm = new Commemoration();
			cm.commemorationId = this.commemorationId;
			cm.commemorationOverrideId = this.commemorationOverrideId;
			cm.dayId = this.dayId;
			cm.altDayAdId = this.altDayAdId;
			cm.altDayAhId = this.altDayAhId;
			cm.altDayAmId = this.altDayAmId;
			cm.creedId = this.creedId;
			cm.calendarAltTypeId = this.calendarAltTypeId;
			cm.calendarAltReasonId = this.calendarAltReasonId;
			cm.commemorationTypeId = this.commemorationTypeId;
			cm.commemorationName = this.commemorationName;
			cm.commemorationAltName = this.commemorationAltName;
			cm.commemorationYearAd = this.commemorationYearAd;
			cm.commemorationYearAh = this.commemorationYearAh;
			cm.commemorationYearAm = this.commemorationYearAm;
			cm.commemorationDetail = this.commemorationDetail;
			cm.locationId = this.locationId;
			cm.altLocationId = this.altLocationId;
			cm.patronageId = this.patronageId;
			cm.centuryId = this.centuryId;
			cm.readingId = this.readingId;
			cm.tagAId = this.tagAId;
			cm.tagBId = this.tagBId;
			cm.tagCId = this.tagCId;
			cm.tagDId = this.tagDId;
			cm.tagEId = this.tagEId;
			cm.referenceId = this.referenceId;
			cm.referenceStart = this.referenceStart;
			cm.referenceEnd = this.referenceEnd;
			cm.starId = this.starId;
			cm.contributors = this.contributors;
			cm.lastUpdateTime = this.lastUpdateTime;
			cm.hasLastContributorBeenNotified = this.hasLastContributorBeenNotified;
			return cm;
		}
	}

	public Long getCommemorationId() {
		return commemorationId;
	}

	public String getCommemorationOverrideId() {
		return commemorationOverrideId;
	}

	public Long getDayId() {
		return dayId;
	}

	public Long getAltDayAdId() {
		return altDayAdId;
	}

	public Long getAltDayAhId() {
		return altDayAhId;
	}

	public Long getAltDayAmId() {
		return altDayAmId;
	}

	public Long getCreedId() {
		return creedId;
	}

	public Long getCalendarAltTypeId() {
		return calendarAltTypeId;
	}

	public Long getCalendarAltReasonId() {
		return calendarAltReasonId;
	}

	public Long getCommemorationTypeId() {
		return commemorationTypeId;
	}

	public String getCommemorationName() {
		return commemorationName;
	}

	public String getCommemorationAltName() {
		return commemorationAltName;
	}

	public String getCommemorationNameFormatted(Long calendarId) {
		if (calendarId == CALENDAR_ALL_ID && commemorationAltName != null) {
			return delim(" / ", commemorationName, commemorationAltName);
		} else if (calendarId == CALENDAR_HIJRI_ID) {
			return (commemorationAltName != null) 
					? commemorationAltName 
					: commemorationName;
		} else {
			return commemorationName;
		}
	}

	public String getCommemorationYearAd() {
		return commemorationYearAd;
	}

	public String getCommemorationYearAh() {
		return commemorationYearAh;
	}

	public String getCommemorationYearAm() {
		return commemorationYearAm;
	}

	/**
	 * Conditionally return different values based on User Calendar
	 * 
	 * @param claendarId the Calendar ID
	 * @return commemorationYear
	 */
	public String getCommemorationYearFormatted(Long calendarId) {

		// Show All and All Commemorations show all later Centuries
		if (calendarId == CALENDAR_ALL_ID) {
			return delim(" / ", commemorationYearAd, commemorationYearAh, commemorationYearAm);
		} else if (calendarId == CALENDAR_GREGORIAN_ID || calendarId == CALENDAR_JULIAN_ID) {
			return commemorationYearAd;
		} else if (calendarId == CALENDAR_HIJRI_ID) {
			return commemorationYearAh;
		} else if (calendarId == CALENDAR_HEBREW_ID) {
			return commemorationYearAm;
		}
		return null;
	}

	public String getCommemorationDetail() {
		return commemorationDetail;
	}

	public Long getLocationId() {
		return locationId;
	}

	public Long getAltLocationId() {
		return altLocationId;
	}

	public Long getPatronageId() {
		return patronageId;
	}

	public Long getCenturyId() {
		return centuryId;
	}

	public Long getReadingId() {
		return readingId;
	}

	public Long getTagAId() {
		return tagAId;
	}

	public Long getTagBId() {
		return tagBId;
	}

	public Long getTagCId() {
		return tagCId;
	}

	public Long getTagDId() {
		return tagDId;
	}

	public Long getTagEId() {
		return tagEId;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public Long getReferenceStart() {
		return referenceStart;
	}

	public Long getReferenceEnd() {
		return referenceEnd;
	}

	public Long getStarId() {
		return starId;
	}

	public String getContributors() {
		return contributors;
	}

	public String getLastContributor() {
		if (contributors != null && !contributors.isEmpty()) {
			final int subStartIndex = contributors.lastIndexOf(",");
			if (subStartIndex > 0) {
				return (contributors.substring(subStartIndex + 1, contributors.length())).trim();
			} else {
				return contributors;
			}
		} else {
			return null;
		}
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setCommemorationId(Long commemorationId) {
		this.commemorationId = commemorationId;
	}

	public void setDayId(Long dayId) {
		this.dayId = dayId;
	}

	public void setAltDayAdId(Long altDayAdId) {
		this.altDayAdId = altDayAdId;
	}

	public void setAltDayAhId(Long altDayAhId) {
		this.altDayAhId = altDayAhId;
	}

	public void setAltDayAmId(Long altDayAmId) {
		this.altDayAmId = altDayAmId;
	}

	public void setCreedId(Long creedId) {
		this.creedId = creedId;
	}

	public void setCalendarAltTypeId(Long calendarAltTypeId) {
		this.calendarAltTypeId = calendarAltTypeId;
	}

	public void setCalendarAltReasonId(Long calendarAltReasonId) {
		this.calendarAltReasonId = calendarAltReasonId;
	}

	public void setCommemorationTypeId(Long commemorationTypeId) {
		this.commemorationTypeId = commemorationTypeId;
	}

	public void setCommemorationName(String commemorationName) {
		this.commemorationName = commemorationName;
	}

	public void setCommemorationAltName(String commemorationAltName) {
		this.commemorationAltName = commemorationAltName;
	}

	public void setCommemorationYearAd(String commemorationYearAd) {
		this.commemorationYearAd = commemorationYearAd;
	}

	public void setCommemorationYearAh(String commemorationYearAh) {
		this.commemorationYearAh = commemorationYearAh;
	}

	public void setCommemorationYearAm(String commemorationYearAm) {
		this.commemorationYearAm = commemorationYearAm;
	}

	public void setCommemorationDetail(String commemorationDetail) {
		this.commemorationDetail = commemorationDetail;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setAltLocationId(Long altLocationId) {
		this.altLocationId = altLocationId;
	}

	public void setPatronageId(Long patronageId) {
		this.patronageId = patronageId;
	}

	public void setCenturyId(Long centuryId) {
		this.centuryId = centuryId;
	}

	public void setReadingId(Long readingId) {
		this.readingId = readingId;
	}

	public void setTagAId(Long tagAId) {
		this.tagAId = tagAId;
	}

	public void setTagBId(Long tagBId) {
		this.tagBId = tagBId;
	}

	public void setTagCId(Long tagCId) {
		this.tagCId = tagCId;
	}

	public void setTagDId(Long tagDId) {
		this.tagDId = tagDId;
	}

	public void setTagEId(Long tagEId) {
		this.tagEId = tagEId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public void setReferenceStart(Long referenceStart) {
		this.referenceStart = referenceStart;
	}

	public void setReferenceEnd(Long referenceEnd) {
		this.referenceEnd = referenceEnd;
	}

	public void setStarId(Long starId) {
		this.starId = starId;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public Boolean getHasLastContributorBeenNotified() {
		return (hasLastContributorBeenNotified != null) 
				? hasLastContributorBeenNotified 
				: false;
	}

	public void setHasLastContributorBeenNotified(Boolean hasLastContributorBeenNotified) {
		this.hasLastContributorBeenNotified = hasLastContributorBeenNotified;
	}

	/**
	 * Method used to determine whether to show a request notification for a specific user or Administrator.
	 * Conditions to display a user-specific request notification are: 
	 * - Commemoration must have non-null Last Update Time
	 * - Commemoration must have Last Contributor matching current username, or else null if Administrator
	 * - Commemoration must not have Last Contributor Notification flag set to true
	 * 
	 * @param currentUsername the current username
	 * @param isAdminUser whether the current user is Administrator
	 * @return hasUserSpecificNotification
	 */
	public Boolean hasUserSpecificNotification(String currentUsername, Boolean isAdminUser) {
		if (this.getLastUpdateTime() != null) {
			final String lastContributor = this.getLastContributor();
			final Boolean hasLastContributorBeenNotified = this.getHasLastContributorBeenNotified();
			if (isAdminUser && lastContributor == null) {
				return (!hasLastContributorBeenNotified);
			} else if (lastContributor != null && !lastContributor.isEmpty()) {
				return (!hasLastContributorBeenNotified && currentUsername.equals(lastContributor));
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Commemoration [commemorationId=" + commemorationId + ", commemorationOverrideId="
				+ commemorationOverrideId + ", dayId=" + dayId + ", altDayAdId=" + altDayAdId + ", altDayAhId="
				+ altDayAhId + ", altDayAmId=" + altDayAmId + ", creedId=" + creedId + ", calendarAltTypeId="
				+ calendarAltTypeId + ", calendarAltReasonId=" + calendarAltReasonId + ", commemorationTypeId="
				+ commemorationTypeId + ", commemorationName=" + commemorationName + ", commemorationAltName="
				+ commemorationAltName + ", commemorationYearAd=" + commemorationYearAd + ", commemorationYearAh="
				+ commemorationYearAh + ", commemorationYearAm=" + commemorationYearAm + ", commemorationDetail="
				+ commemorationDetail + ", locationId=" + locationId + ", altLocationId=" + altLocationId
				+ ", patronageId=" + patronageId + ", centuryId=" + centuryId + ", readingId=" + readingId + ", tagAId="
				+ tagAId + ", tagBId=" + tagBId + ", tagCId=" + tagCId + ", tagDId=" + tagDId + ", tagEId=" + tagEId
				+ ", referenceId=" + referenceId + ", referenceStart=" + referenceStart + ", referenceEnd="
				+ referenceEnd + ", starId=" + starId + ", contributors=" + contributors + ", lastUpdateTime=" + lastUpdateTime
				+ ", hasLastContributorBeenNotified=" + hasLastContributorBeenNotified + "]";
	}
}
