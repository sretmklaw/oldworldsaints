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

import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "reference")
public class Reference extends BaseEntity implements Comparable<Reference> {

	@Id
	@NotNull
	@Column(name = "reference_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long referenceId;

	@Column(name = "reference_name")
	private String referenceName;

	@Column(name = "reference_volume")
	private Long referenceVolume;

	@Column(name = "reference_start")
	private Long referenceStart;

	@Column(name = "reference_end")
	private Long referenceEnd;

	private Reference() {}

	public static class Builder {

		private Long referenceId;
		private String referenceName;
		private Long referenceVolume;
		private Long referenceStart;
		private Long referenceEnd;

		public Builder withReferenceId(Long referenceId) {
			this.referenceId = referenceId;
			return this;
		}

		public Builder withReferenceName(String referenceName) {
			this.referenceName = referenceName;
			return this;
		}

		public Builder withReferenceVolume(Long referenceVolume) {
			this.referenceVolume = referenceVolume;
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

		public Reference build() {
			Reference ref = new Reference();
			ref.referenceId = this.referenceId;
			ref.referenceName = this.referenceName;
			ref.referenceVolume = this.referenceVolume;
			ref.referenceStart = this.referenceStart;
			ref.referenceEnd = this.referenceEnd;
			return ref;
		}
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public Long getReferenceVolume() {
		return referenceVolume;
	}

	public Long getReferenceStart() {
		return referenceStart;
	}

	public Long getReferenceEnd() {
		return referenceEnd;
	}

	public String getReferenceDisplayName() {
		final StringBuilder referenceDisplayName = new StringBuilder()
				.append(referenceName) 
				.append(" - Vol. ").append(referenceVolume)
				.append(", p. ").append(referenceStart);
		return (referenceEnd != null) 
				? new StringJoiner("-").add(referenceDisplayName.toString()).add(String.valueOf(referenceEnd)).toString() 
				: referenceDisplayName.toString();
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	@Override
	public String toString() {
		return "Reference [referenceId=" + referenceId + ", referenceName=" + referenceName + ", referenceVolume="
				+ referenceVolume + ", referenceStart=" + referenceStart
				+ ", referenceEnd=" + referenceEnd + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(referenceEnd, referenceId, referenceName, referenceStart, referenceVolume);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reference other = (Reference) obj;
		return Objects.equals(referenceEnd, other.referenceEnd) && Objects.equals(referenceId, other.referenceId)
				&& Objects.equals(referenceName, other.referenceName)
				&& Objects.equals(referenceStart, other.referenceStart)
				&& Objects.equals(referenceVolume, other.referenceVolume);
	}

	@Override
	public int compareTo(Reference other) {
		return Comparator
				.comparing(Reference::getReferenceName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}