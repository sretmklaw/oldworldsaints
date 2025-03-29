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
@Table(name = "star_type")
public class StarType implements Comparable<StarType> {

	@Id
	@NotNull
	@Column(name = "star_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long starTypeId;

	@Column(name = "star_type_name")
	private String starTypeName;

	@Column(name = "star_type_code")
	private String starTypeCode;

	private StarType() {}

	public static class Builder {

		private Long starTypeId;
		private String starTypeName;
		private String starTypeCode;

		public Builder withStarTypeId (Long starTypeId) {
			this.starTypeId = starTypeId;
			return this;
		}

		public Builder withStarTypeName(String starTypeName) {
			this.starTypeName = starTypeName;
			return this;
		}

		public Builder withStarTypeCode(String starTypeCode) {
			this.starTypeCode = starTypeCode;
			return this;
		}

		public StarType build() {
			StarType st = new StarType();
			st.starTypeId = this.starTypeId;
			st.starTypeName = this.starTypeName;
			st.starTypeCode = this.starTypeCode;
			return st;
		}
	}

	public Long getStarTypeId() {
		return starTypeId;
	}

	public String getStarTypeName() {
		return starTypeName;
	}

	public String getStarTypeCode() {
		return starTypeCode;
	}

	@Override
	public String toString() {
		return "StarType [starTypeId=" + starTypeId + ", starTypeName=" + starTypeName
				+ ", starTypeCode=" + starTypeCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((starTypeCode == null) ? 0 : starTypeCode.hashCode());
		result = prime * result + ((starTypeId == null) ? 0 : starTypeId.hashCode());
		result = prime * result + ((starTypeName == null) ? 0 : starTypeName.hashCode());
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
		StarType other = (StarType) obj;
		if (starTypeCode == null) {
			if (other.starTypeCode != null)
				return false;
		} else if (!starTypeCode.equals(other.starTypeCode))
			return false;
		if (starTypeId == null) {
			if (other.starTypeId != null)
				return false;
		} else if (!starTypeId.equals(other.starTypeId))
			return false;
		if (starTypeName == null) {
			if (other.starTypeName != null)
				return false;
		} else if (!starTypeName.equals(other.starTypeName))
			return false;
		return true;
	}

	@Override
	public int compareTo(StarType other) {
		return Comparator
				.comparing(StarType::getStarTypeName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}