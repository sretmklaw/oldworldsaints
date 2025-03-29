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
@Table(name = "nation")
public class Nation implements Comparable<Nation> {

	@Id
	@NotNull
	@Column(name = "nation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nationId;

	@Column(name = "nation_name")
	private String nationName;

	@Column(name = "nation_code")
	private String nationCode;

	@Column(name = "alt_nation_code")
	private String altNationCode;

	@Column(name = "related_patronage_id")
	private Long relatedPatronageId;

	@Column(name = "is_old_world")
	private Boolean isOldWorld;

	private Nation() {
	}

	public static class Builder {

		private Long nationId;
		private String nationName;
		private String nationCode;
		private String altNationCode;
		private Long relatedPatronageId;
		private Boolean isOldWorld;

		public Builder withNationId(Long nationId) {
			this.nationId = nationId;
			return this;
		}

		public Builder withNationName(String nationName) {
			this.nationName = nationName;
			return this;
		}

		public Builder withNationCode(String nationCode) {
			this.nationCode = nationCode;
			return this;
		}

		public Builder withAltNationCode(String altNationCode) {
			this.altNationCode = altNationCode;
			return this;
		}

		public Builder withRelatedPatronageId(Long relatedPatronageId) {
			this.relatedPatronageId = relatedPatronageId;
			return this;
		}

		public Builder withIsOldWorld(Boolean isOldWorld) {
			this.isOldWorld = isOldWorld;
			return this;
		}

		public Nation build() {
			Nation nation = new Nation();
			nation.nationId = this.nationId;
			nation.nationName = this.nationName;
			nation.nationCode = this.nationCode;
			nation.altNationCode = this.altNationCode;
			nation.relatedPatronageId = this.relatedPatronageId;
			nation.isOldWorld = this.isOldWorld;
			return nation;
		}
	}

	public Long getNationId() {
		return nationId;
	}

	public String getNationName() {
		return nationName;
	}

	public String getNationCode() {
		return nationCode;
	}

	public String getAltNationCode() {
		return altNationCode;
	}

	public Long getRelatedPatronageId() {
		return relatedPatronageId;
	}

	public Boolean getIsOldWorld() {
		return isOldWorld;
	}

	@Override
	public String toString() {
		return "Nation [nationId=" + nationId + ", nationName=" + nationName + ", nationCode=" + nationCode
				+ ", altNationCode=" + altNationCode + ", relatedPatronageId=" + relatedPatronageId + ", isOldWorld="
				+ isOldWorld + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altNationCode == null) ? 0 : altNationCode.hashCode());
		result = prime * result + ((isOldWorld == null) ? 0 : isOldWorld.hashCode());
		result = prime * result + ((nationCode == null) ? 0 : nationCode.hashCode());
		result = prime * result + ((nationId == null) ? 0 : nationId.hashCode());
		result = prime * result + ((nationName == null) ? 0 : nationName.hashCode());
		result = prime * result + ((relatedPatronageId == null) ? 0 : relatedPatronageId.hashCode());
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
		Nation other = (Nation) obj;
		if (altNationCode == null) {
			if (other.altNationCode != null)
				return false;
		} else if (!altNationCode.equals(other.altNationCode))
			return false;
		if (isOldWorld == null) {
			if (other.isOldWorld != null)
				return false;
		} else if (!isOldWorld.equals(other.isOldWorld))
			return false;
		if (nationCode == null) {
			if (other.nationCode != null)
				return false;
		} else if (!nationCode.equals(other.nationCode))
			return false;
		if (nationId == null) {
			if (other.nationId != null)
				return false;
		} else if (!nationId.equals(other.nationId))
			return false;
		if (nationName == null) {
			if (other.nationName != null)
				return false;
		} else if (!nationName.equals(other.nationName))
			return false;
		if (relatedPatronageId == null) {
			if (other.relatedPatronageId != null)
				return false;
		} else if (!relatedPatronageId.equals(other.relatedPatronageId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Nation other) {
		return Comparator
				.comparing(Nation::getNationName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}