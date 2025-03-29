package com.cimeliarchium.model.dao;

import java.time.LocalDate;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.cimeliarchium.model.BaseEntity;
@Entity
@Table(name = "reading")
public class Reading extends BaseEntity implements Comparable<Reading> {

	@Id
	@NotNull
	@Column(name = "reading_id")
	@GenericGenerator(name = "AppIdentityGenerator", strategy = "com.cimeliarchium.config.AppIdentityGenerator")
	@GeneratedValue(generator = "AppIdentityGenerator")
	private Long readingId;

	@Column(name = "reading_override_id")
	private String readingOverrideId;

	@Column(name = "reading_name")
	private String readingName;

	@Column(name = "reading_name_alt")
	private String readingNameAlt;

	@Column(name = "reading_content")
	private String readingContent;

	@Column(name = "last_update_time")
	private LocalDate lastUpdateTime;

	private Reading() {}

	public static class Builder {

		private Long readingId;
		private String readingOverrideId;
		private String readingName;
		private String readingNameAlt;
		private String readingContent;
		private LocalDate lastUpdateTime;

		public Builder withReadingId(Long readingId) {
			this.readingId = readingId;
			return this;
		}

		public Builder withReadingOverrideId(String readingOverrideId) {
			this.readingOverrideId = readingOverrideId;
			return this;
		}

		public Builder withReadingName(String readingName) {
			this.readingName = readingName;
			return this;
		}

		public Builder withReadingNameAlt(String readingNameAlt) {
			this.readingNameAlt = readingNameAlt;
			return this;
		}

		public Builder withReadingContent(String readingContent) {
			this.readingContent = readingContent;
			return this;
		}

		public Builder withLastUpdateTime(LocalDate lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
			return this;
		}

		public Reading build() {
			Reading reading = new Reading();
			reading.readingId = this.readingId;
			reading.readingOverrideId = this.readingOverrideId;
			reading.readingName = this.readingName;
			reading.readingNameAlt = this.readingNameAlt;
			reading.readingContent = this.readingContent;
			reading.lastUpdateTime = this.lastUpdateTime;
			return reading;
		}
	}

	public Long getReadingId() {
		return readingId;
	}

	public String getReadingOverrideId() {
		return readingOverrideId;
	}

	public String getReadingName() {
		return readingName;
	}

	public String getReadingNameAlt() {
		return readingNameAlt;
	}

	public String getReadingContent() {
		return readingContent;
	}

	public String getReadingNameFormatted() {
		return (readingNameAlt != null && !readingNameAlt.isEmpty()) 
				? readingName + " - " + readingNameAlt 
				: readingName;
	}

	public void setReadingId(Long readingId) {
		this.readingId = readingId;
	}

	public void setReadingName(String readingName) {
		this.readingName = readingName;
	}

	public void setReadingNameAlt(String readingNameAlt) {
		this.readingNameAlt = readingNameAlt;
	}

	public void setReadingContent(String readingContent) {
		this.readingContent = readingContent;
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "Reading [readingId=" + readingId + ", readingOverrideId=" + readingOverrideId + ", readingName="
				+ readingName + ", readingNameAlt=" + readingNameAlt + ", readingContent=" + readingContent
				+ ", lastUpdateTime=" + lastUpdateTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		result = prime * result + ((readingContent == null) ? 0 : readingContent.hashCode());
		result = prime * result + ((readingId == null) ? 0 : readingId.hashCode());
		result = prime * result + ((readingName == null) ? 0 : readingName.hashCode());
		result = prime * result + ((readingNameAlt == null) ? 0 : readingNameAlt.hashCode());
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
		Reading other = (Reading) obj;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (readingContent == null) {
			if (other.readingContent != null)
				return false;
		} else if (!readingContent.equals(other.readingContent))
			return false;
		if (readingId == null) {
			if (other.readingId != null)
				return false;
		} else if (!readingId.equals(other.readingId))
			return false;
		if (readingName == null) {
			if (other.readingName != null)
				return false;
		} else if (!readingName.equals(other.readingName))
			return false;
		if (readingNameAlt == null) {
			if (other.readingNameAlt != null)
				return false;
		} else if (!readingNameAlt.equals(other.readingNameAlt))
			return false;
		return true;
	}

	@Override
	public int compareTo(Reading other) {
		return Comparator
				.comparing(Reading::getReadingId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Reading::getReadingName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Reading::getReadingNameAlt, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Reading::getReadingContent, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Reading::getLastUpdateTime, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}