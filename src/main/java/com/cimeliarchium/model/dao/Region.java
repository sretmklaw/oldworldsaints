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
@Table(name = "region")
public class Region implements Comparable<Region> {

	@Id
	@NotNull
	@Column(name = "region_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long regionId;

	@Column(name = "nation_id")
	private Long nationId;

	@Column(name = "region_name")
	private String regionName;

	@Column(name = "region_code")
	private String regionCode;

	@Column(name = "utc_offset")
	private String utcOffset;

	@Column(name = "is_northern_hemisphere")
	private Boolean isNorthernHemisphere;

	@Column(name = "dst_end_day")
	private Integer dstEndDay;

	@Column(name = "dst_end_dow")
	private Integer dstEndDow;

	@Column(name = "dst_end_month")
	private Integer dstEndMonth;

	@Column(name = "dst_end_week")
	private Integer dstEndWeek;

	@Column(name = "dst_start_day")
	private Integer dstStartDay;

	@Column(name = "dst_start_dow")
	private Integer dstStartDow;

	@Column(name = "dst_start_month")
	private Integer dstStartMonth;

	@Column(name = "dst_start_week")
	private Integer dstStartWeek;

	private Region() {}

	public static class Builder {

		private Long regionId;
		private Long nationId;
		private String regionName;
		private String regionCode;
		private String utcOffset;
		private Boolean isNorthernHemisphere;
		private Integer dstEndDay;
		private Integer dstEndDow;
		private Integer dstEndMonth;
		private Integer dstEndWeek;
		private Integer dstStartDay;
		private Integer dstStartDow;
		private Integer dstStartMonth;
		private Integer dstStartWeek;

		public Builder withRegionId (Long regionId) {
			this.regionId = regionId;
			return this;
		}

		public Builder withNationId (Long nationId) {
			this.nationId = nationId;
			return this;
		}

		public Builder withRegionName (String regionName) {
			this.regionName = regionName;
			return this;
		}

		public Builder withRegionCode (String regionCode) {
			this.regionCode = regionCode;
			return this;
		}

		public Builder withUtcOffset (String utcOffset) {
			this.utcOffset = utcOffset;
			return this;
		}

		public Builder withIsNorthernHemisphere (Boolean isNorthernHemisphere) {
			this.isNorthernHemisphere = isNorthernHemisphere;
			return this;
		}

		public Builder withDstEndDay (Integer dstEndDay) {
			this.dstEndDay = dstEndDay;
			return this;
		}

		public Builder withDstEndDow (Integer dstEndDow) {
			this.dstEndDow = dstEndDow;
			return this;
		}

		public Builder withDstEndMonth (Integer dstEndMonth) {
			this.dstEndMonth = dstEndMonth;
			return this;
		}

		public Builder withDstEndWeek (Integer dstEndWeek) {
			this.dstEndWeek = dstEndWeek;
			return this;
		}

		public Builder withDstStartDay (Integer dstStartDay) {
			this.dstStartDay = dstStartDay;
			return this;
		}

		public Builder withDstStartDow (Integer dstStartDow) {
			this.dstStartDow = dstStartDow;
			return this;
		}

		public Builder withDstStartMonth (Integer dstStartMonth) {
			this.dstStartMonth = dstStartMonth;
			return this;
		}

		public Builder withDstStartWeek (Integer dstStartWeek) {
			this.dstStartWeek = dstStartWeek;
			return this;
		}

		public Region build() {
			Region region = new Region();
			region.regionId = this.regionId;
			region.nationId = this.nationId;
			region.regionName = this.regionName;
			region.regionCode = this.regionCode;
			region.utcOffset = this.utcOffset;
			region.isNorthernHemisphere = this.isNorthernHemisphere;
			region.dstEndDay = this.dstEndDay;
			region.dstEndDow = this.dstEndDow;
			region.dstEndMonth = this.dstEndMonth;
			region.dstEndWeek = this.dstEndWeek;
			region.dstStartDay = this.dstStartDay;
			region.dstStartDow = this.dstStartDow;
			region.dstStartMonth = this.dstStartMonth;
			region.dstStartWeek = this.dstStartWeek;
			return region;
		}
	}

	public Long getRegionId() {
		return regionId;
	}

	public Long getNationId() {
		return nationId;
	}

	public String getRegionName() {
		return regionName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public String getUtcOffset() {
		return (utcOffset != null) 
				? (utcOffset.startsWith("-")) 
						? utcOffset 
						: "+" + utcOffset
		: "";
	}

	public String getUtcOffsetFormatted() {
		return (utcOffset != null) 
				? (utcOffset.startsWith("-")) 
						? " (UTC" + utcOffset + ")"
						: " (UTC+" + utcOffset + ")"
				: "";
	}

	public Boolean getIsNorthernHemisphere() {
		return isNorthernHemisphere;
	}

	public Integer getDstEndDay() {
		return dstEndDay;
	}

	public Integer getDstEndDow() {
		return dstEndDow;
	}

	public Integer getDstEndMonth() {
		return dstEndMonth;
	}

	public Integer getDstEndWeek() {
		return dstEndWeek;
	}

	public Integer getDstStartDay() {
		return dstStartDay;
	}

	public Integer getDstStartDow() {
		return dstStartDow;
	}

	public Integer getDstStartMonth() {
		return dstStartMonth;
	}

	public Integer getDstStartWeek() {
		return dstStartWeek;
	}

	@Override
	public String toString() {
		return "Region [regionId=" + regionId + ", nationId=" + nationId + ", regionName=" + regionName
				+ ", regionCode=" + regionCode + ", utcOffset=" + utcOffset + ", isNorthernHemisphere="
				+ isNorthernHemisphere + ", dstEndDay=" + dstEndDay + ", dstEndDow=" + dstEndDow + ", dstEndMonth="
				+ dstEndMonth + ", dstEndWeek=" + dstEndWeek + ", dstStartDay=" + dstStartDay + ", dstStartDow="
				+ dstStartDow + ", dstStartMonth=" + dstStartMonth + ", dstStartWeek=" + dstStartWeek + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dstEndDay == null) ? 0 : dstEndDay.hashCode());
		result = prime * result + ((dstEndDow == null) ? 0 : dstEndDow.hashCode());
		result = prime * result + ((dstEndMonth == null) ? 0 : dstEndMonth.hashCode());
		result = prime * result + ((dstEndWeek == null) ? 0 : dstEndWeek.hashCode());
		result = prime * result + ((dstStartDay == null) ? 0 : dstStartDay.hashCode());
		result = prime * result + ((dstStartDow == null) ? 0 : dstStartDow.hashCode());
		result = prime * result + ((dstStartMonth == null) ? 0 : dstStartMonth.hashCode());
		result = prime * result + ((dstStartWeek == null) ? 0 : dstStartWeek.hashCode());
		result = prime * result + ((isNorthernHemisphere == null) ? 0 : isNorthernHemisphere.hashCode());
		result = prime * result + ((nationId == null) ? 0 : nationId.hashCode());
		result = prime * result + ((regionCode == null) ? 0 : regionCode.hashCode());
		result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
		result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
		result = prime * result + ((utcOffset == null) ? 0 : utcOffset.hashCode());
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
		Region other = (Region) obj;
		if (dstEndDay == null) {
			if (other.dstEndDay != null)
				return false;
		} else if (!dstEndDay.equals(other.dstEndDay))
			return false;
		if (dstEndDow == null) {
			if (other.dstEndDow != null)
				return false;
		} else if (!dstEndDow.equals(other.dstEndDow))
			return false;
		if (dstEndMonth == null) {
			if (other.dstEndMonth != null)
				return false;
		} else if (!dstEndMonth.equals(other.dstEndMonth))
			return false;
		if (dstEndWeek == null) {
			if (other.dstEndWeek != null)
				return false;
		} else if (!dstEndWeek.equals(other.dstEndWeek))
			return false;
		if (dstStartDay == null) {
			if (other.dstStartDay != null)
				return false;
		} else if (!dstStartDay.equals(other.dstStartDay))
			return false;
		if (dstStartDow == null) {
			if (other.dstStartDow != null)
				return false;
		} else if (!dstStartDow.equals(other.dstStartDow))
			return false;
		if (dstStartMonth == null) {
			if (other.dstStartMonth != null)
				return false;
		} else if (!dstStartMonth.equals(other.dstStartMonth))
			return false;
		if (dstStartWeek == null) {
			if (other.dstStartWeek != null)
				return false;
		} else if (!dstStartWeek.equals(other.dstStartWeek))
			return false;
		if (isNorthernHemisphere == null) {
			if (other.isNorthernHemisphere != null)
				return false;
		} else if (!isNorthernHemisphere.equals(other.isNorthernHemisphere))
			return false;
		if (nationId == null) {
			if (other.nationId != null)
				return false;
		} else if (!nationId.equals(other.nationId))
			return false;
		if (regionCode == null) {
			if (other.regionCode != null)
				return false;
		} else if (!regionCode.equals(other.regionCode))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		if (regionName == null) {
			if (other.regionName != null)
				return false;
		} else if (!regionName.equals(other.regionName))
			return false;
		if (utcOffset == null) {
			if (other.utcOffset != null)
				return false;
		} else if (!utcOffset.equals(other.utcOffset))
			return false;
		return true;
	}

	@Override
	public int compareTo(Region other) {
		return Comparator
				.comparing(Region::getRegionName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}