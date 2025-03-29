package com.cimeliarchium.model.dao;

import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.enums.ReferenceTitleEnum;
import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "star")
public class Star extends BaseEntity implements Comparable<Star> {

	@Id
	@NotNull
	@Column(name = "star_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long starId;

	@Column(name = "star_name")
	private String starName;

	@Column(name = "star_name_alt")
	private String starNameAlt;

	@Column(name = "star_type_id")
	private Long starTypeId;

	@Column(name = "star_detail")
	private String starDetail;

	@Column(name = "constellation_id")
	private Long constellationId;

	@Column(name = "culmination_day_id")
	private Long culminationDayId;

	@Column(name = "point_x")
	private Double pointX;

	@Column(name = "point_y")
	private Double pointY;

	@Column(name = "reference_start")
	private Long referenceStart;

	@Column(name = "reference_end")
	private Long referenceEnd;

	@Column(name = "last_star_id")
	private Long lastStarId;

	@Column(name = "next_star_id")
	private Long nextStarId;

	private String lastStarName;

	private String nextStarName;

	private String culminationDayName;

	private String starTypeCode;

	private Long allCommemorationCount;

	private Long catholicCommemorationCount;

	private Long protestantCommemorationCount;

	private Long orthodoxCommemorationCount;

	private Long sunniCommemorationCount;

	private Long shiiteCommemorationCount;

	private Long jewishCommemorationCount;

	public static class Builder {

		private Long starId;
		private String starName;
		private String starNameAlt;
		private Long starTypeId;
		private String starDetail;
		private Long constellationId;
		private Long culminationDayId;
		private Double pointX;
		private Double pointY;
		private Long referenceStart;
		private Long referenceEnd;
		private Long lastStarId;
		private String lastStarName;
		private Long nextStarId;
		private String nextStarName;
		private String culminationDayName;
		private String starTypeCode;
		private Long allCommemorationCount;
		private Long catholicCommemorationCount;
		private Long protestantCommemorationCount;
		private Long orthodoxCommemorationCount;
		private Long sunniCommemorationCount;
		private Long shiiteCommemorationCount;
		private Long jewishCommemorationCount;

		public Builder withStarId (Long starId) {
			this.starId = starId;
			return this;
		}

		public Builder withStarName (String starName) {
			this.starName = starName;
			return this;
		}

		public Builder withStarNameAlt (String starNameAlt) {
			this.starNameAlt = starNameAlt;
			return this;
		}

		public Builder withStarTypeId (Long starTypeId) {
			this.starTypeId = starTypeId;
			return this;
		}

		public Builder withStarDetail (String starDetail) {
			this.starDetail = starDetail;
			return this;
		}

		public Builder withConstellationId (Long constellationId) {
			this.constellationId = constellationId;
			return this;
		}

		public Builder withCulminationDayId (Long culminationDayId) {
			this.culminationDayId = culminationDayId;
			return this;
		}

		public Builder withPointX (Double pointX) {
			this.pointX = pointX;
			return this;
		}

		public Builder withPointY (Double pointY) {
			this.pointY = pointY;
			return this;
		}

		public Builder withReferenceStart (Long referenceStart) {
			this.referenceStart = referenceStart;
			return this;
		}

		public Builder withReferenceEnd (Long referenceEnd) {
			this.referenceEnd = referenceEnd;
			return this;
		}

		public Builder withLastStarId (Long lastStarId) {
			this.lastStarId = lastStarId;
			return this;
		}

		public Builder withLastStarName (String lastStarName) {
			this.lastStarName = lastStarName;
			return this;
		}

		public Builder withNextStarId (Long nextStarId) {
			this.nextStarId = nextStarId;
			return this;
		}

		public Builder withNextStarName (String nextStarName) {
			this.nextStarName = nextStarName;
			return this;
		}

		public Builder withCulminationDayName (String culminationDayName) {
			this.culminationDayName = culminationDayName;
			return this;
		}

		public Builder withStarTypeCode (String starTypeCode) {
			this.starTypeCode = starTypeCode;
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

		public Star build() {
			Star star = new Star();
			star.starId = this.starId;
			star.starName = this.starName;
			star.starNameAlt = this.starNameAlt;
			star.starDetail = this.starDetail;
			star.starTypeId = this.starTypeId;
			star.starTypeCode = this.starTypeCode;
			star.constellationId = this.constellationId;
			star.culminationDayId = this.culminationDayId;
			star.culminationDayName = this.culminationDayName;
			star.pointX = this.pointX;
			star.pointY = this.pointY;
			star.referenceStart = this.referenceStart;
			star.referenceEnd = this.referenceEnd;
			star.lastStarId = this.lastStarId;
			star.lastStarName = this.lastStarName;
			star.nextStarId = this.nextStarId;
			star.nextStarName = this.nextStarName;
			star.allCommemorationCount = this.allCommemorationCount;
			star.catholicCommemorationCount = this.catholicCommemorationCount;
			star.protestantCommemorationCount = this.protestantCommemorationCount;
			star.orthodoxCommemorationCount = this.orthodoxCommemorationCount;
			star.sunniCommemorationCount = this.sunniCommemorationCount;
			star.shiiteCommemorationCount = this.shiiteCommemorationCount;
			star.jewishCommemorationCount = this.jewishCommemorationCount;
			return star;
		}
	}

	public Long getStarId() {
		return starId;
	}

	public String getStarName() {
		return starName;
	}

	public String getStarNameAlt() {
		return starNameAlt;
	}

	public String getStarNameFormatted() {
		return (starNameAlt != null && !starNameAlt.isEmpty()) 
				? starName + " (" + starNameAlt + ")"
				: starName;
	}

	public String getStarDetail() {
		return starDetail;
	}

	public Long getStarTypeId() {
		return starTypeId;
	}

	public String getStarTypeCode() {
		return starTypeCode;
	}

	public Long getConstellationId() {
		return constellationId;
	}

	public Long getCulminationDayId() {
		return culminationDayId;
	}

	public String getCulminationDayName() {
		return culminationDayName;
	}

	public Double getPointX() {
		return pointX;
	}

	public Double getPointY() {
		return pointY;
	}

	public Long getReferenceStart() {
		return referenceStart;
	}

	public Long getReferenceEnd() {
		return referenceEnd;
	}

	public String getReferenceFormatted() {
		final StringBuilder referenceTitle = new StringBuilder().append(ReferenceTitleEnum.ALLEN_STAR_NAMES.getTitle());
		if (referenceStart != null) {
			referenceTitle.append(", p. ");
			if (referenceEnd != null) {
				return referenceTitle.append(new StringJoiner("-")
						.add(String.valueOf(referenceStart))
						.add(String.valueOf(referenceEnd)).toString())
						.toString();
			} else {
				return referenceTitle.append(String.valueOf(referenceStart)).toString();
			}
		} else {
			return referenceTitle.toString();
		}
	}

	public Long getLastStarId() {
		return lastStarId;
	}

	public Long getNextStarId() {
		return nextStarId;
	}

	public String getLastStarName() {
		return lastStarName;
	}

	public String getNextStarName() {
		return nextStarName;
	}

	public Long getAllCommemorationCount() {
		return allCommemorationCount;
	}

	public Long getCatholicCommemorationCount() {
		return catholicCommemorationCount;
	}

	public Long getProtestantCommemorationCount() {
		return protestantCommemorationCount;
	}

	public Long getOrthodoxCommemorationCount() {
		return orthodoxCommemorationCount;
	}

	public Long getSunniCommemorationCount() {
		return sunniCommemorationCount;
	}

	public Long getShiiteCommemorationCount() {
		return shiiteCommemorationCount;
	}

	public Long getJewishCommemorationCount() {
		return jewishCommemorationCount;
	}

	@Override
	public String toString() {
		return "Star [starId=" + starId + ", starName=" + starName + ", starNameAlt=" + starNameAlt + ", starTypeId="
				+ starTypeId + ", starDetail=" + starDetail + ", constellationId=" + constellationId
				+ ", culminationDayId=" + culminationDayId + ", pointX=" + pointX + ", pointY=" + pointY
				+ ", referenceStart=" + referenceStart + ", referenceEnd=" + referenceEnd + ", lastStarId=" + lastStarId
				+ ", nextStarId=" + nextStarId + ", lastStarName=" + lastStarName + ", nextStarName=" + nextStarName
				+ ", culminationDayName=" + culminationDayName + ", starTypeCode=" + starTypeCode + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(constellationId, culminationDayId, lastStarId, nextStarId, pointX, pointY, referenceEnd,
				referenceStart, starDetail, starId, starName, starNameAlt, starTypeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Star other = (Star) obj;
		return Objects.equals(constellationId, other.constellationId)
				&& Objects.equals(culminationDayId, other.culminationDayId)
				&& Objects.equals(lastStarId, other.lastStarId) && Objects.equals(nextStarId, other.nextStarId)
				&& Objects.equals(pointX, other.pointX) && Objects.equals(pointY, other.pointY)
				&& Objects.equals(referenceEnd, other.referenceEnd)
				&& Objects.equals(referenceStart, other.referenceStart) && Objects.equals(starDetail, other.starDetail)
				&& Objects.equals(starId, other.starId) && Objects.equals(starName, other.starName)
				&& Objects.equals(starNameAlt, other.starNameAlt) && Objects.equals(starTypeId, other.starTypeId);
	}

	@Override
	public int compareTo(Star other) {
		return Comparator
				.comparing(Star::getStarName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}
