package com.cimeliarchium.model.dao;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "patronage")
public class Patronage extends BaseEntity implements Comparable<Patronage> {

	@Id
	@NotNull
	@Column(name = "patronage_id")
	@GenericGenerator(name = "AppIdentityGenerator", strategy = "com.cimeliarchium.config.AppIdentityGenerator")
	@GeneratedValue(generator = "AppIdentityGenerator")
	private Long patronageId;

	@Column(name = "patronage_override_id")
	private String patronageOverrideId;

	@Column(name = "last_update_time")
	private LocalDate lastUpdateTime;

	@Column(name = "point_x")
	private Double pointX;

	@Column(name = "point_y")
	private Double pointY;

	@Column(name = "patronage_name")
	private String patronageName;

	@Column(name = "patronage_subtype_id")
	private Long patronageSubtypeId;

	@Column(name = "related_search_link")
	private String relatedSearchLink;

	/**
	 * All fields below are virtual,i.e. those values mapped from DTOs, but not persisted in the database.
	 * Hence, we do not consider these values in toString(), hashCode(), or equals() methods.
	 */
	@Column(name = "patronage_subtype_name")
	private String patronageSubtypeName;

	@Column(name = "patronage_subtype_code")
	private String patronageSubtypeCode;

	@Column(name = "patronage_type_id")
	private Long patronageTypeId;

	@Column(name = "patronage_type_name")
	private String patronageTypeName;

	@Column(name = "all_commemoration_count")
	private Long allCommemorationCount;

	@Column(name = "catholic_commemoration_count")
	private Long catholicCommemorationCount;

	@Column(name = "jewish_commemoration_count")
	private Long jewishCommemorationCount;

	@Column(name = "orthodox_commemoration_count")
	private Long orthodoxCommemorationCount;

	@Column(name = "protestant_commemoration_count")
	private Long protestantCommemorationCount;

	@Column(name = "shiite_commemoration_count")
	private Long shiiteCommemorationCount;

	@Column(name = "sunni_commemoration_count")
	private Long sunniCommemorationCount;

	private Patronage() {}

	public static class Builder {

		private Long patronageId;
		private String patronageOverrideId;
		private String patronageName;
		private Long patronageSubtypeId;
		private String patronageSubtypeName;
		private String patronageSubtypeCode;
		private Long patronageTypeId;
		private String patronageTypeName;
		private Double pointX;
		private Double pointY;
		private String relatedSearchLink;
		private LocalDate lastUpdateTime;
		private Long allCommemorationCount;
		private Long catholicCommemorationCount;
		private Long protestantCommemorationCount;
		private Long orthodoxCommemorationCount;
		private Long sunniCommemorationCount;
		private Long shiiteCommemorationCount;
		private Long jewishCommemorationCount;

		public Builder withPatronageId(Long patronageId) {
			this.patronageId = patronageId;
			return this;
		}

		public Builder withPatronageOverrideId(String patronageOverrideId) {
			this.patronageOverrideId = patronageOverrideId;
			return this;
		}

		public Builder withPatronageName(String patronageName) {
			this.patronageName = patronageName;
			return this;
		}

		public Builder withPatronageSubtypeId(Long patronageSubtypeId) {
			this.patronageSubtypeId = patronageSubtypeId;
			return this;
		}

		public Builder withPatronageSubtypeName(String patronageSubtypeName) {
			this.patronageSubtypeName = patronageSubtypeName;
			return this;
		}

		public Builder withPatronageSubtypeCode(String patronageSubtypeCode) {
			this.patronageSubtypeCode = patronageSubtypeCode;
			return this;
		}

		public Builder withPatronageTypeId(Long patronageTypeId) {
			this.patronageTypeId = patronageTypeId;
			return this;
		}

		public Builder withPatronageTypeName(String patronageTypeName) {
			this.patronageTypeName = patronageTypeName;
			return this;
		}

		public Builder withPointX(Double pointX) {
			this.pointX = pointX;
			return this;
		}

		public Builder withPointY(Double pointY) {
			this.pointY = pointY;
			return this;
		}

		public Builder withRelatedSearchLink(String relatedSearchLink) {
			this.relatedSearchLink = relatedSearchLink;
			return this;
		}

		public Builder withLastUpdateTime(LocalDate lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
			return this;
		}

		public Builder withAllCommemorationCount(Long allCommemorationCount) {
			this.allCommemorationCount = allCommemorationCount;
			return this;
		}

		public Builder withCatholicCommemorationCount(Long catholicCommemorationCount) {
			this.catholicCommemorationCount = catholicCommemorationCount;
			return this;
		}

		public Builder withProtestantCommemorationCount(Long protestantCommemorationCount) {
			this.protestantCommemorationCount = protestantCommemorationCount;
			return this;
		}

		public Builder withOrthodoxCommemorationCount(Long orthodoxCommemorationCount) {
			this.orthodoxCommemorationCount = orthodoxCommemorationCount;
			return this;
		}

		public Builder withSunniCommemorationCount(Long sunniCommemorationCount) {
			this.sunniCommemorationCount = sunniCommemorationCount;
			return this;
		}

		public Builder withShiiteCommemorationCount(Long shiiteCommemorationCount) {
			this.shiiteCommemorationCount = shiiteCommemorationCount;
			return this;
		}

		public Builder withJewishCommemorationCount(Long jewishCommemorationCount) {
			this.jewishCommemorationCount = jewishCommemorationCount;
			return this;
		}

		public Patronage build() {
			Patronage patronage = new Patronage();
			patronage.patronageId = this.patronageId;
			patronage.patronageOverrideId = this.patronageOverrideId;
			patronage.patronageName = this.patronageName;
			patronage.patronageSubtypeId = this.patronageSubtypeId;
			patronage.patronageSubtypeName = this.patronageSubtypeName;
			patronage.patronageSubtypeCode = this.patronageSubtypeCode;
			patronage.patronageTypeId = this.patronageTypeId;
			patronage.patronageTypeName = this.patronageTypeName;
			patronage.pointX = this.pointX;
			patronage.pointY = this.pointY;
			patronage.relatedSearchLink = this.relatedSearchLink;
			patronage.lastUpdateTime = this.lastUpdateTime;
			patronage.allCommemorationCount = this.allCommemorationCount;
			patronage.catholicCommemorationCount = this.catholicCommemorationCount;
			patronage.protestantCommemorationCount = this.protestantCommemorationCount;
			patronage.orthodoxCommemorationCount = this.orthodoxCommemorationCount;
			patronage.sunniCommemorationCount = this.sunniCommemorationCount;
			patronage.shiiteCommemorationCount = this.shiiteCommemorationCount;
			patronage.jewishCommemorationCount = this.jewishCommemorationCount;
			return patronage;
		}
	}

	public Long getPatronageId() {
		return patronageId;
	}

	public String getPatronageOverrideId() {
		return patronageOverrideId;
	}

	public String getPatronageName() {
		return patronageName;
	}

	public Long getPatronageSubtypeId() {
		return patronageSubtypeId;
	}

	public String getPatronageSubtypeName() {
		return patronageSubtypeName;
	}

	public Long getPatronageTypeId() {
		return patronageTypeId;
	}

	public String getPatronageTypeName() {
		return patronageTypeName;
	}

	public String getPatronageSubtypeCode() {
		return patronageSubtypeCode;
	}

	public String getPatronageNameFormatted() {
		final String patSubtypeNamePrefix = 
				(patronageTypeId == PATRONAGETYPE_AFFLICTION_ID || patronageTypeId == PATRONAGETYPE_VICE_ID) 
						? "Against" 
						: "";
		return new StringJoiner(" ")
				.add(patSubtypeNamePrefix)
				.add(patronageSubtypeName)
				.add(patronageName)
				.toString();
	}

	public void setPatronageId(Long patronageId) {
		this.patronageId = patronageId;
	}

	public void setPatronageName(String patronageName) {
		this.patronageName = patronageName;
	}

	public void setPatronageSubtypeId(Long patronageSubtypeId) {
		this.patronageSubtypeId = patronageSubtypeId;
	}

	public Double getPointX() {
		return pointX;
	}

	public void setPointX(Double pointX) {
		this.pointX = pointX;
	}

	public Double getPointY() {
		return pointY;
	}

	public void setPointY(Double pointY) {
		this.pointY = pointY;
	}

	public void setRelatedSearchLink(String relatedSearchLink) {
		this.relatedSearchLink = relatedSearchLink;
	}

	public String getRelatedSearchLink() {
		return relatedSearchLink;
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getAllCommemorationCount() {
		return allCommemorationCount;
	}

	public void setAllCommemorationCount(Long allCommemorationCount) {
		this.allCommemorationCount = allCommemorationCount;
	}

	public Long getCatholicCommemorationCount() {
		return catholicCommemorationCount;
	}

	public void setCatholicCommemorationCount(Long catholicCommemorationCount) {
		this.catholicCommemorationCount = catholicCommemorationCount;
	}

	public Long getJewishCommemorationCount() {
		return jewishCommemorationCount;
	}

	public void setJewishCommemorationCount(Long jewishCommemorationCount) {
		this.jewishCommemorationCount = jewishCommemorationCount;
	}

	public Long getOrthodoxCommemorationCount() {
		return orthodoxCommemorationCount;
	}

	public void setOrthodoxCommemorationCount(Long orthodoxCommemorationCount) {
		this.orthodoxCommemorationCount = orthodoxCommemorationCount;
	}

	public Long getProtestantCommemorationCount() {
		return protestantCommemorationCount;
	}

	public void setProtestantCommemorationCount(Long protestantCommemorationCount) {
		this.protestantCommemorationCount = protestantCommemorationCount;
	}

	public Long getShiiteCommemorationCount() {
		return shiiteCommemorationCount;
	}

	public void setShiiteCommemorationCount(Long shiiteCommemorationCount) {
		this.shiiteCommemorationCount = shiiteCommemorationCount;
	}

	public Long getSunniCommemorationCount() {
		return sunniCommemorationCount;
	}

	public void setSunniCommemorationCount(Long sunniCommemorationCount) {
		this.sunniCommemorationCount = sunniCommemorationCount;
	}

	@Override
	public String toString() {
		return "Patronage [patronageId=" + patronageId + ", patronageOverrideId=" + patronageOverrideId
				+ ", lastUpdateTime=" + lastUpdateTime + ", pointX=" + pointX + ", pointY=" + pointY
				+ ", patronageName=" + patronageName + ", patronageSubtypeId=" + patronageSubtypeId
				+ ", relatedSearchLink=" + relatedSearchLink + ", patronageSubtypeName=" + patronageSubtypeName
				+ ", patronageSubtypeCode=" + patronageSubtypeCode + ", patronageTypeId=" + patronageTypeId
				+ ", patronageTypeName=" + patronageTypeName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		result = prime * result + ((patronageId == null) ? 0 : patronageId.hashCode());
		result = prime * result + ((pointX == null) ? 0 : pointX.hashCode());
		result = prime * result + ((pointY == null) ? 0 : pointY.hashCode());
		result = prime * result + ((patronageName == null) ? 0 : patronageName.hashCode());
		result = prime * result + ((patronageSubtypeId == null) ? 0 : patronageSubtypeId.hashCode());
		result = prime * result + ((relatedSearchLink == null) ? 0 : relatedSearchLink.hashCode());
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
		Patronage other = (Patronage) obj;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (patronageId == null) {
			if (other.patronageId != null)
				return false;
		} else if (!patronageId.equals(other.patronageId))
			return false;
		if (pointX == null) {
			if (other.pointX != null)
				return false;
		} else if (!pointX.equals(other.pointX))
			return false;
		if (pointY == null) {
			if (other.pointY != null)
				return false;
		} else if (!pointY.equals(other.pointY))
			return false;
		if (patronageName == null) {
			if (other.patronageName != null)
				return false;
		} else if (!patronageName.equals(other.patronageName))
			return false;
		if (patronageSubtypeId == null) {
			if (other.patronageSubtypeId != null)
				return false;
		} else if (!patronageSubtypeId.equals(other.patronageSubtypeId))
			return false;
		if (relatedSearchLink == null) {
			if (other.relatedSearchLink != null)
				return false;
		} else if (!relatedSearchLink.equals(other.relatedSearchLink))
			return false;
		return true;
	}

	@Override
	public int compareTo(Patronage other) {
		return Comparator
				.comparing(Patronage::getPatronageName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Patronage::getPatronageId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Patronage::getPatronageSubtypeId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Patronage::getPointX, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Patronage::getPointY, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Patronage::getRelatedSearchLink, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Patronage::getLastUpdateTime, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}
