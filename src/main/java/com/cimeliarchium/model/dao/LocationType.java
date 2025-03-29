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
@Table(name = "location_type")
public class LocationType implements Comparable<LocationType> {

	@Id
	@NotNull
	@Column(name = "location_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationTypeId;

	@Column(name = "location_type_name")
	private String locationTypeName;

	@Column(name = "location_type_code")
	private String locationTypeCode;

	private LocationType() {}

	public static class Builder {

		private Long locationTypeId;
		private String locationTypeName;
		private String locationTypeCode;

		public Builder withLocationTypeId (Long locationTypeId) {
			this.locationTypeId = locationTypeId;
			return this;
		}

		public Builder withLocationTypeName(String locationTypeName) {
			this.locationTypeName = locationTypeName;
			return this;
		}

		public Builder withLocationTypeCode(String locationTypeCode) {
			this.locationTypeCode = locationTypeCode;
			return this;
		}

		public LocationType build() {
			LocationType lt = new LocationType();
			lt.locationTypeId = this.locationTypeId;
			lt.locationTypeName = this.locationTypeName;
			lt.locationTypeCode = this.locationTypeCode;
			return lt;
		}
	}

	public Long getLocationTypeId() {
		return locationTypeId;
	}

	public String getLocationTypeName() {
		return locationTypeName;
	}

	public String getLocationTypeCode() {
		return locationTypeCode;
	}

	@Override
	public String toString() {
		return "LocationType [locationTypeId=" + locationTypeId + ", locationTypeName=" + locationTypeName
				+ ", locationTypeCode=" + locationTypeCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locationTypeCode == null) ? 0 : locationTypeCode.hashCode());
		result = prime * result + ((locationTypeId == null) ? 0 : locationTypeId.hashCode());
		result = prime * result + ((locationTypeName == null) ? 0 : locationTypeName.hashCode());
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
		LocationType other = (LocationType) obj;
		if (locationTypeCode == null) {
			if (other.locationTypeCode != null)
				return false;
		} else if (!locationTypeCode.equals(other.locationTypeCode))
			return false;
		if (locationTypeId == null) {
			if (other.locationTypeId != null)
				return false;
		} else if (!locationTypeId.equals(other.locationTypeId))
			return false;
		if (locationTypeName == null) {
			if (other.locationTypeName != null)
				return false;
		} else if (!locationTypeName.equals(other.locationTypeName))
			return false;
		return true;
	}

	@Override
	public int compareTo(LocationType other) {
		return Comparator
				.comparing(LocationType::getLocationTypeName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}