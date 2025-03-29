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
@Table(name = "constellation")
public class Constellation implements Comparable<Constellation> {

	@Id
	@NotNull
	@Column(name = "constellation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long constellationId;

	@Column(name = "constellation_name_latin")
	private String constellationNameLatin;

	@Column(name = "constellation_name_greek")
	private String constellationNameGreek;

	@Column(name = "constellation_name_arabic")
	private String constellationNameArabic;

	@Column(name = "constellation_name_hebrew")
	private String constellationNameHebrew;

	@Column(name = "constellation_code")
	private String constellationCode;

	@Column(name = "constellation_detail")
	private String constellationDetail;

	@Column(name = "constellation_detail_jewish")
	private String constellationDetailJewish;

	@Column(name = "constellation_detail_christian")
	private String constellationDetailChristian;

	@Column(name = "constellation_detail_islamic")
	private String constellationDetailIslamic;

	@Column(name = "commemoration_id_1")
	private Long commemorationId1;

	@Column(name = "commemoration_name_1")
	private String commemorationName1;

	@Column(name = "commemoration_id_2")
	private Long commemorationId2;

	@Column(name = "commemoration_name_2")
	private String commemorationName2;

	@Column(name = "commemoration_id_3")
	private Long commemorationId3;

	@Column(name = "commemoration_name_3")
	private String commemorationName3;

	@Column(name = "commemoration_id_4")
	private Long commemorationId4;

	@Column(name = "commemoration_name_4")
	private String commemorationName4;

	@Column(name = "commemoration_id_5")
	private Long commemorationId5;

	@Column(name = "commemoration_name_5")
	private String commemorationName5;

	@Column(name = "star_id_primary")
	private Long starIdPrimary;

	@Column(name = "star_name_primary")
	private String starNamePrimary;

	@Column(name = "star_id_secondary")
	private Long starIdSecondary;

	@Column(name = "star_name_secondary")
	private String starNameSecondary;

	@Column(name = "constellation_display_name")
	private String constellationDisplayName;

	@Column(name = "constellation_display_detail")
	private String constellationDisplayDetail;

	@Column(name = "is_zodiac_sign")
	private Boolean isZodiacSign;

	@Column(name = "asterism_star_ids")
	private String asterismStarIds;

	@Column(name = "asterism_points")
	private String asterismPoints;

	private Constellation() {}

	public static class Builder {

		private Long constellationId;
		private String constellationNameLatin;
		private String constellationNameGreek;
		private String constellationNameArabic;
		private String constellationNameHebrew;
		private String constellationCode;
		private String constellationDetail;
		private String constellationDetailJewish;
		private String constellationDetailChristian;
		private String constellationDetailIslamic;
		private Long commemorationId1;
		private String commemorationName1;
		private Long commemorationId2;
		private String commemorationName2;
		private Long commemorationId3;
		private String commemorationName3;
		private Long commemorationId4;
		private String commemorationName4;
		private Long commemorationId5;
		private String commemorationName5;
		private Long starIdPrimary;
		private String starNamePrimary;
		private Long starIdSecondary;
		private String starNameSecondary;
		private Boolean isZodiacSign;
		private String asterismStarIds;
		private String asterismPoints;

		public Builder withConstellationId(Long constellationId) {
			this.constellationId = constellationId;
			return this;
		}

		public Builder withConstellationNameLatin(String constellationNameLatin) {
			this.constellationNameLatin = constellationNameLatin;
			return this;
		}

		public Builder withConstellationNameGreek(String constellationNameGreek) {
			this.constellationNameGreek = constellationNameGreek;
			return this;
		}

		public Builder withConstellationNameArabic(String constellationNameArabic) {
			this.constellationNameArabic = constellationNameArabic;
			return this;
		}

		public Builder withConstellationNameHebrew(String constellationNameHebrew) {
			this.constellationNameHebrew = constellationNameHebrew;
			return this;
		}

		public Builder withConstellationCode(String constellationCode) {
			this.constellationCode = constellationCode;
			return this;
		}

		public Builder withConstellationDetail(String constellationDetail) {
			this.constellationDetail = constellationDetail;
			return this;
		}

		public Builder withConstellationDetailJewish(String constellationDetailJewish) {
			this.constellationDetailJewish = constellationDetailJewish;
			return this;
		}

		public Builder withConstellationDetailChristian(String constellationDetailChristian) {
			this.constellationDetailChristian = constellationDetailChristian;
			return this;
		}

		public Builder withConstellationDetailIslamic(String constellationDetailIslamic) {
			this.constellationDetailIslamic = constellationDetailIslamic;
			return this;
		}

		public Builder withCommemorationId1(Long commemorationId1) {
			this.commemorationId1 = commemorationId1;
			return this;
		}

		public Builder withCommemorationName1(String commemorationName1) {
			this.commemorationName1 = commemorationName1;
			return this;
		}

		public Builder withCommemorationId2(Long commemorationId2) {
			this.commemorationId2 = commemorationId2;
			return this;
		}

		public Builder withCommemorationName2(String commemorationName2) {
			this.commemorationName2 = commemorationName2;
			return this;
		}

		public Builder withCommemorationId3(Long commemorationId3) {
			this.commemorationId3 = commemorationId3;
			return this;
		}

		public Builder withCommemorationName3(String commemorationName3) {
			this.commemorationName3 = commemorationName3;
			return this;
		}

		public Builder withCommemorationId4(Long commemorationId4) {
			this.commemorationId4 = commemorationId4;
			return this;
		}

		public Builder withCommemorationName4(String commemorationName4) {
			this.commemorationName4 = commemorationName4;
			return this;
		}

		public Builder withCommemorationId5(Long commemorationId5) {
			this.commemorationId5 = commemorationId5;
			return this;
		}

		public Builder withCommemorationName5(String commemorationName5) {
			this.commemorationName5 = commemorationName5;
			return this;
		}

		public Builder withStarIdPrimary(Long starIdPrimary) {
			this.starIdPrimary = starIdPrimary;
			return this;
		}

		public Builder withStarNamePrimary(String starNamePrimary) {
			this.starNamePrimary = starNamePrimary;
			return this;
		}

		public Builder withStarIdSecondary(Long starIdSecondary) {
			this.starIdSecondary = starIdSecondary;
			return this;
		}

		public Builder withStarNameSecondary(String starNameSecondary) {
			this.starNameSecondary = starNameSecondary;
			return this;
		}

		public Builder withIsZodiacSign(Boolean isZodiacSign) {
			this.isZodiacSign = isZodiacSign;
			return this;
		}

		public Builder withAsterismStarIds(String asterismStarIds) {
			this.asterismStarIds = asterismStarIds;
			return this;
		}

		public Builder withAsterismPoints(String asterismPoints) {
			this.asterismPoints = asterismPoints;
			return this;
		}

		public Constellation build() {
			Constellation constellation = new Constellation();
			constellation.constellationId = this.constellationId;
			constellation.constellationNameLatin = this.constellationNameLatin;
			constellation.constellationNameGreek = this.constellationNameGreek;
			constellation.constellationNameArabic = this.constellationNameArabic;
			constellation.constellationNameHebrew = this.constellationNameHebrew;
			constellation.constellationCode = this.constellationCode;
			constellation.constellationDetail = this.constellationDetail;
			constellation.constellationDetailJewish = this.constellationDetailJewish;
			constellation.constellationDetailChristian = this.constellationDetailChristian;
			constellation.constellationDetailIslamic = this.constellationDetailIslamic;
			constellation.commemorationId1 = this.commemorationId1;
			constellation.commemorationName1 = this.commemorationName1;
			constellation.commemorationId2 = this.commemorationId2;
			constellation.commemorationName2 = this.commemorationName2;
			constellation.commemorationId3 = this.commemorationId3;
			constellation.commemorationName3 = this.commemorationName3;
			constellation.commemorationId4 = this.commemorationId4;
			constellation.commemorationName4 = this.commemorationName4;
			constellation.commemorationId5 = this.commemorationId5;
			constellation.commemorationName5 = this.commemorationName5;
			constellation.starIdPrimary = this.starIdPrimary;
			constellation.starNamePrimary = this.starNamePrimary;
			constellation.starIdSecondary = this.starIdSecondary;
			constellation.starNameSecondary = this.starNameSecondary;
			constellation.isZodiacSign = this.isZodiacSign;
			constellation.asterismStarIds = this.asterismStarIds;
			constellation.asterismPoints = this.asterismPoints;
			return constellation;
		}
	}

	public Long getConstellationId() {
		return constellationId;
	}

	public String getConstellationNameLatin() {
		return constellationNameLatin;
	}

	public String getConstellationNameGreek() {
		return constellationNameGreek;
	}

	public String getConstellationNameArabic() {
		return constellationNameArabic;
	}

	public String getConstellationNameHebrew() {
		return constellationNameHebrew;
	}

	public String getConstellationCode() {
		return constellationCode;
	}

	public String getConstellationDetail() {
		return constellationDetail;
	}

	public String getConstellationDetailJewish() {
		return constellationDetailJewish;
	}

	public String getConstellationDetailChristian() {
		return constellationDetailChristian;
	}

	public String getConstellationDetailIslamic() {
		return constellationDetailIslamic;
	}

	public Long getCommemorationId1() {
		return commemorationId1;
	}

	public String getCommemorationName1() {
		return commemorationName1;
	}

	public Long getCommemorationId2() {
		return commemorationId2;
	}

	public String getCommemorationName2() {
		return commemorationName2;
	}

	public Long getCommemorationId3() {
		return commemorationId3;
	}

	public String getCommemorationName3() {
		return commemorationName3;
	}

	public Long getCommemorationId4() {
		return commemorationId4;
	}

	public String getCommemorationName4() {
		return commemorationName4;
	}

	public Long getCommemorationId5() {
		return commemorationId5;
	}

	public String getCommemorationName5() {
		return commemorationName5;
	}

	public Long getStarIdPrimary() {
		return starIdPrimary;
	}

	public String getStarNamePrimary() {
		return starNamePrimary;
	}

	public Long getStarIdSecondary() {
		return starIdSecondary;
	}

	public String getStarNameSecondary() {
		return starNameSecondary;
	}

	public Boolean getIsZodiacSign() {
		return isZodiacSign;
	}

	public void setConstellationDisplayName(String constellationDisplayName) {
		this.constellationDisplayName = constellationDisplayName;
	}

	public String getConstellationDisplayName() {
		return constellationDisplayName;
	}

	public void setConstellationDisplayDetail(String constellationDisplayDetail) {
		this.constellationDisplayDetail = constellationDisplayDetail;
	}

	public String getConstellationDisplayDetail() {
		return constellationDisplayDetail;
	}

	public String getAsterismStarIds() {
		return asterismStarIds;
	}

	public String getAsterismPoints() {
		return asterismPoints;
	}

	@Override
	public String toString() {
		return "Constellation [constellationId=" + constellationId + ", constellationNameLatin="
				+ constellationNameLatin + ", constellationNameGreek=" + constellationNameGreek
				+ ", constellationNameArabic=" + constellationNameArabic + ", constellationNameHebrew="
				+ constellationNameHebrew + ", constellationCode=" + constellationCode + ", constellationDetail="
				+ constellationDetail + ", constellationDetailJewish=" + constellationDetailJewish
				+ ", constellationDetailChristian=" + constellationDetailChristian + ", constellationDetailIslamic="
				+ constellationDetailIslamic + ", commemorationId1=" + commemorationId1 + ", commemorationName1="
				+ commemorationName1 + ", commemorationId2=" + commemorationId2 + ", commemorationName2="
				+ commemorationName2 + ", commemorationId3=" + commemorationId3 + ", commemorationName3="
				+ commemorationName3 + ", commemorationId4=" + commemorationId4 + ", commemorationName4="
				+ commemorationName4 + ", commemorationId5=" + commemorationId5 + ", commemorationName5="
				+ commemorationName5 + ", starIdPrimary=" + starIdPrimary + ", starNamePrimary=" + starNamePrimary
				+ ", starIdSecondary=" + starIdSecondary + ", starNameSecondary=" + starNameSecondary
				+ ", constellationDisplayName=" + constellationDisplayName + ", constellationDisplayDetail="
				+ constellationDisplayDetail + ", isZodiacSign=" + isZodiacSign + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asterismPoints == null) ? 0 : asterismPoints.hashCode());
		result = prime * result + ((commemorationId1 == null) ? 0 : commemorationId1.hashCode());
		result = prime * result + ((commemorationId2 == null) ? 0 : commemorationId2.hashCode());
		result = prime * result + ((commemorationId3 == null) ? 0 : commemorationId3.hashCode());
		result = prime * result + ((commemorationId4 == null) ? 0 : commemorationId4.hashCode());
		result = prime * result + ((commemorationId5 == null) ? 0 : commemorationId5.hashCode());
		result = prime * result + ((commemorationName1 == null) ? 0 : commemorationName1.hashCode());
		result = prime * result + ((commemorationName2 == null) ? 0 : commemorationName2.hashCode());
		result = prime * result + ((commemorationName3 == null) ? 0 : commemorationName3.hashCode());
		result = prime * result + ((commemorationName4 == null) ? 0 : commemorationName4.hashCode());
		result = prime * result + ((commemorationName5 == null) ? 0 : commemorationName5.hashCode());
		result = prime * result + ((constellationCode == null) ? 0 : constellationCode.hashCode());
		result = prime * result + ((constellationDetail == null) ? 0 : constellationDetail.hashCode());
		result = prime * result
				+ ((constellationDetailChristian == null) ? 0 : constellationDetailChristian.hashCode());
		result = prime * result + ((constellationDetailIslamic == null) ? 0 : constellationDetailIslamic.hashCode());
		result = prime * result + ((constellationDetailJewish == null) ? 0 : constellationDetailJewish.hashCode());
		result = prime * result + ((constellationDisplayDetail == null) ? 0 : constellationDisplayDetail.hashCode());
		result = prime * result + ((constellationDisplayName == null) ? 0 : constellationDisplayName.hashCode());
		result = prime * result + ((constellationId == null) ? 0 : constellationId.hashCode());
		result = prime * result + ((constellationNameArabic == null) ? 0 : constellationNameArabic.hashCode());
		result = prime * result + ((constellationNameGreek == null) ? 0 : constellationNameGreek.hashCode());
		result = prime * result + ((constellationNameHebrew == null) ? 0 : constellationNameHebrew.hashCode());
		result = prime * result + ((constellationNameLatin == null) ? 0 : constellationNameLatin.hashCode());
		result = prime * result + ((asterismStarIds == null) ? 0 : asterismStarIds.hashCode());
		result = prime * result + ((isZodiacSign == null) ? 0 : isZodiacSign.hashCode());
		result = prime * result + ((starIdPrimary == null) ? 0 : starIdPrimary.hashCode());
		result = prime * result + ((starIdSecondary == null) ? 0 : starIdSecondary.hashCode());
		result = prime * result + ((starNamePrimary == null) ? 0 : starNamePrimary.hashCode());
		result = prime * result + ((starNameSecondary == null) ? 0 : starNameSecondary.hashCode());
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
		Constellation other = (Constellation) obj;
		if (asterismPoints == null) {
			if (other.asterismPoints != null)
				return false;
		} else if (!asterismPoints.equals(other.asterismPoints))
			return false;
		if (commemorationId1 == null) {
			if (other.commemorationId1 != null)
				return false;
		} else if (!commemorationId1.equals(other.commemorationId1))
			return false;
		if (commemorationId2 == null) {
			if (other.commemorationId2 != null)
				return false;
		} else if (!commemorationId2.equals(other.commemorationId2))
			return false;
		if (commemorationId3 == null) {
			if (other.commemorationId3 != null)
				return false;
		} else if (!commemorationId3.equals(other.commemorationId3))
			return false;
		if (commemorationId4 == null) {
			if (other.commemorationId4 != null)
				return false;
		} else if (!commemorationId4.equals(other.commemorationId4))
			return false;
		if (commemorationId5 == null) {
			if (other.commemorationId5 != null)
				return false;
		} else if (!commemorationId5.equals(other.commemorationId5))
			return false;
		if (commemorationName1 == null) {
			if (other.commemorationName1 != null)
				return false;
		} else if (!commemorationName1.equals(other.commemorationName1))
			return false;
		if (commemorationName2 == null) {
			if (other.commemorationName2 != null)
				return false;
		} else if (!commemorationName2.equals(other.commemorationName2))
			return false;
		if (commemorationName3 == null) {
			if (other.commemorationName3 != null)
				return false;
		} else if (!commemorationName3.equals(other.commemorationName3))
			return false;
		if (commemorationName4 == null) {
			if (other.commemorationName4 != null)
				return false;
		} else if (!commemorationName4.equals(other.commemorationName4))
			return false;
		if (commemorationName5 == null) {
			if (other.commemorationName5 != null)
				return false;
		} else if (!commemorationName5.equals(other.commemorationName5))
			return false;
		if (constellationCode == null) {
			if (other.constellationCode != null)
				return false;
		} else if (!constellationCode.equals(other.constellationCode))
			return false;
		if (constellationDetail == null) {
			if (other.constellationDetail != null)
				return false;
		} else if (!constellationDetail.equals(other.constellationDetail))
			return false;
		if (constellationDetailChristian == null) {
			if (other.constellationDetailChristian != null)
				return false;
		} else if (!constellationDetailChristian.equals(other.constellationDetailChristian))
			return false;
		if (constellationDetailIslamic == null) {
			if (other.constellationDetailIslamic != null)
				return false;
		} else if (!constellationDetailIslamic.equals(other.constellationDetailIslamic))
			return false;
		if (constellationDetailJewish == null) {
			if (other.constellationDetailJewish != null)
				return false;
		} else if (!constellationDetailJewish.equals(other.constellationDetailJewish))
			return false;
		if (constellationDisplayDetail == null) {
			if (other.constellationDisplayDetail != null)
				return false;
		} else if (!constellationDisplayDetail.equals(other.constellationDisplayDetail))
			return false;
		if (constellationDisplayName == null) {
			if (other.constellationDisplayName != null)
				return false;
		} else if (!constellationDisplayName.equals(other.constellationDisplayName))
			return false;
		if (constellationId == null) {
			if (other.constellationId != null)
				return false;
		} else if (!constellationId.equals(other.constellationId))
			return false;
		if (constellationNameArabic == null) {
			if (other.constellationNameArabic != null)
				return false;
		} else if (!constellationNameArabic.equals(other.constellationNameArabic))
			return false;
		if (constellationNameGreek == null) {
			if (other.constellationNameGreek != null)
				return false;
		} else if (!constellationNameGreek.equals(other.constellationNameGreek))
			return false;
		if (constellationNameHebrew == null) {
			if (other.constellationNameHebrew != null)
				return false;
		} else if (!constellationNameHebrew.equals(other.constellationNameHebrew))
			return false;
		if (constellationNameLatin == null) {
			if (other.constellationNameLatin != null)
				return false;
		} else if (!constellationNameLatin.equals(other.constellationNameLatin))
			return false;
		if (asterismStarIds == null) {
			if (other.asterismStarIds != null)
				return false;
		} else if (!asterismStarIds.equals(other.asterismStarIds))
			return false;
		if (isZodiacSign == null) {
			if (other.isZodiacSign != null)
				return false;
		} else if (!isZodiacSign.equals(other.isZodiacSign))
			return false;
		if (starIdPrimary == null) {
			if (other.starIdPrimary != null)
				return false;
		} else if (!starIdPrimary.equals(other.starIdPrimary))
			return false;
		if (starIdSecondary == null) {
			if (other.starIdSecondary != null)
				return false;
		} else if (!starIdSecondary.equals(other.starIdSecondary))
			return false;
		if (starNamePrimary == null) {
			if (other.starNamePrimary != null)
				return false;
		} else if (!starNamePrimary.equals(other.starNamePrimary))
			return false;
		if (starNameSecondary == null) {
			if (other.starNameSecondary != null)
				return false;
		} else if (!starNameSecondary.equals(other.starNameSecondary))
			return false;
		return true;
	}

	@Override
	public int compareTo(Constellation other) {
		return Comparator
				.comparing(Constellation::getConstellationNameLatin, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}