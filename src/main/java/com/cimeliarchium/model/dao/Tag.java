package com.cimeliarchium.model.dao;

import java.util.Comparator;
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
@Table(name = "tag")
public class Tag extends BaseEntity implements Comparable<Tag> {

	@Id
	@NotNull
	@Column(name = "tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;

	@Column(name = "tag_creed_id")
	private Long tagCreedId;

	@Column(name = "tag_name")
	private String tagName;

	@Column(name = "tag_detail")
	private String tagDetail;

	@Column(name = "related_patronage_id")
	private Long relatedPatronageId;

	private Tag() {}

	public static class Builder {

		private Long tagId;
		private Long tagCreedId;
		private String tagName;
		private String tagDetail;
		private Long relatedPatronageId;

		public Builder withTagId(Long tagId) {
			this.tagId = tagId;
			return this;
		}

		public Builder withTagCreedId(Long tagCreedId) {
			this.tagCreedId = tagCreedId;
			return this;
		}

		public Builder withTagName(String tagName) {
			this.tagName = tagName;
			return this;
		}

		public Builder withTagDetail(String tagDetail) {
			this.tagDetail = tagDetail;
			return this;
		}

		public Builder withRelatedPatronageId(Long relatedPatronageId) {
			this.relatedPatronageId = relatedPatronageId;
			return this;
		}

		public Tag build() {
			Tag tag = new Tag();
			tag.tagId = this.tagId;
			tag.tagCreedId = this.tagCreedId;
			tag.tagName = this.tagName;
			tag.tagDetail = this.tagDetail;
			tag.relatedPatronageId = this.relatedPatronageId;
			return tag;
		}
	}

	public Long getTagId() {
		return tagId;
	}

	public Long getTagCreedId() {
		return tagCreedId;
	}

	public String getTagName() {
		return tagName;
	}

	public String getTagDetail() {
		return tagDetail;
	}

	public Long getRelatedPatronageId() {
		return relatedPatronageId;
	}

	public String getTagFields() {
		return new StringJoiner(FIELDS_DELIM)
				.add(String.valueOf(tagId))
				.add(String.valueOf(tagName))
				.toString();
	}

	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", tagCreedId=" + tagCreedId + ", tagName=" + tagName + ", tagDetail="
				+ tagDetail + ", relatedPatronageId=" + relatedPatronageId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((relatedPatronageId == null) ? 0 : relatedPatronageId.hashCode());
		result = prime * result + ((tagCreedId == null) ? 0 : tagCreedId.hashCode());
		result = prime * result + ((tagDetail == null) ? 0 : tagDetail.hashCode());
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
		Tag other = (Tag) obj;
		if (relatedPatronageId == null) {
			if (other.relatedPatronageId != null)
				return false;
		} else if (!relatedPatronageId.equals(other.relatedPatronageId))
			return false;
		if (tagCreedId == null) {
			if (other.tagCreedId != null)
				return false;
		} else if (!tagCreedId.equals(other.tagCreedId))
			return false;
		if (tagDetail == null) {
			if (other.tagDetail != null)
				return false;
		} else if (!tagDetail.equals(other.tagDetail))
			return false;
		if (tagId == null) {
			if (other.tagId != null)
				return false;
		} else if (!tagId.equals(other.tagId))
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Tag other) {
		return Comparator
				.comparing(Tag::getTagName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}