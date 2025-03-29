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
@Table(name = "patronage_type")
public class PatronageType implements Comparable<PatronageType> {

	@Id
	@NotNull
	@Column(name = "patronage_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patronageTypeId;

	@Column(name = "patronage_type_name")
	private String patronageTypeName;

	private PatronageType() {}

	public static class Builder {

		private Long patronageTypeId;
		private String patronageTypeName;

		public Builder withPatronageTypeId(Long patronageTypeId) {
			this.patronageTypeId = patronageTypeId;
			return this;
		}

		public Builder withPatronageTypeName(String patronageTypeName) {
			this.patronageTypeName = patronageTypeName;
			return this;
		}

		public PatronageType build() {
			PatronageType patronageType = new PatronageType();
			patronageType.patronageTypeId = this.patronageTypeId;
			patronageType.patronageTypeName = this.patronageTypeName;
			return patronageType;
		}
	}

	public Long getPatronageTypeId() {
		return patronageTypeId;
	}

	public String getPatronageTypeName() {
		return patronageTypeName;
	}

	@Override
	public String toString() {
		return "PatronageType [patronageTypeId=" + patronageTypeId + ", patronageTypeName=" + patronageTypeName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patronageTypeId == null) ? 0 : patronageTypeId.hashCode());
		result = prime * result + ((patronageTypeName == null) ? 0 : patronageTypeName.hashCode());
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
		PatronageType other = (PatronageType) obj;
		if (patronageTypeId == null) {
			if (other.patronageTypeId != null)
				return false;
		} else if (!patronageTypeId.equals(other.patronageTypeId))
			return false;
		if (patronageTypeName == null) {
			if (other.patronageTypeName != null)
				return false;
		} else if (!patronageTypeName.equals(other.patronageTypeName))
			return false;
		return true;
	}

	@Override
	public int compareTo(PatronageType other) {
		return Comparator
				.comparing(PatronageType::getPatronageTypeName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}
