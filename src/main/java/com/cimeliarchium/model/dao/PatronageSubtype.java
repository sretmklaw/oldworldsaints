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
@Table(name = "patronage_subtype")
public class PatronageSubtype implements Comparable<PatronageSubtype> {

	@Id
	@NotNull
	@Column(name = "patronage_subtype_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patronageSubtypeId;

	@Column(name = "patronage_type_id")
	private Long patronageTypeId;

	@Column(name = "patronage_type_name")
	private String patronageTypeName;

	@Column(name = "patronage_subtype_name")
	private String patronageSubtypeName;

	@Column(name = "patronage_subtype_code")
	private String patronageSubtypeCode;

	private PatronageSubtype() {}

	public static class Builder {

		private Long patronageSubtypeId;
		private Long patronageTypeId;
		private String patronageTypeName;
		private String patronageSubtypeName;
		private String patronageSubtypeCode;

		public Builder withPatronageSubtypeId(Long patronageSubtypeId) {
			this.patronageSubtypeId = patronageSubtypeId;
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

		public Builder withPatronageSubtypeName(String patronageSubtypeName) {
			this.patronageSubtypeName = patronageSubtypeName;
			return this;
		}

		public Builder withPatronageSubtypeCode(String patronageSubtypeCode) {
			this.patronageSubtypeCode = patronageSubtypeCode;
			return this;
		}

		public PatronageSubtype build() {
			PatronageSubtype pst = new PatronageSubtype();
			pst.patronageSubtypeId = this.patronageSubtypeId;
			pst.patronageTypeId = this.patronageTypeId;
			pst.patronageTypeName = this.patronageTypeName;
			pst.patronageSubtypeName = this.patronageSubtypeName;
			pst.patronageSubtypeCode = this.patronageSubtypeCode;
			return pst;
		}
	}

	public Long getPatronageSubtypeId() {
		return patronageSubtypeId;
	}

	public Long getPatronageTypeId() {
		return patronageTypeId;
	}

	public String getPatronageTypeName() {
		return patronageTypeName;
	}

	public String getPatronageSubtypeName() {
		return patronageSubtypeName;
	}

	public String getPatronageSubtypeCode() {
		return patronageSubtypeCode;
	}

	@Override
	public String toString() {
		return "PatronageSubtype [patronageSubtypeId=" + patronageSubtypeId + ", patronageTypeId=" + patronageTypeId
				+ ", patronageTypeName=" + patronageTypeName + ", patronageSubtypeName=" + patronageSubtypeName
				+ ", patronageSubtypeCode=" + patronageSubtypeCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patronageSubtypeCode == null) ? 0 : patronageSubtypeCode.hashCode());
		result = prime * result + ((patronageSubtypeId == null) ? 0 : patronageSubtypeId.hashCode());
		result = prime * result + ((patronageSubtypeName == null) ? 0 : patronageSubtypeName.hashCode());
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
		PatronageSubtype other = (PatronageSubtype) obj;
		if (patronageSubtypeCode == null) {
			if (other.patronageSubtypeCode != null)
				return false;
		} else if (!patronageSubtypeCode.equals(other.patronageSubtypeCode))
			return false;
		if (patronageSubtypeId == null) {
			if (other.patronageSubtypeId != null)
				return false;
		} else if (!patronageSubtypeId.equals(other.patronageSubtypeId))
			return false;
		if (patronageSubtypeName == null) {
			if (other.patronageSubtypeName != null)
				return false;
		} else if (!patronageSubtypeName.equals(other.patronageSubtypeName))
			return false;
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
	public int compareTo(PatronageSubtype other) {
		return Comparator
				.comparing(PatronageSubtype::getPatronageSubtypeName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}
