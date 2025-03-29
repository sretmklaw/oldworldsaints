package com.cimeliarchium.model.dao;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

public class SearchCriteria implements Comparable<SearchCriteria> {

	private Long byId;
	private String byText;
	private Boolean byValue;
	private Date byDate;

	public SearchCriteria() {}

	public static class Builder {

		private Long byId;
		private String byText;
		private Boolean byValue;
		private Date byDate;

		public Builder withById(Long byId) {
			this.byId = byId;
			return this;
		}

		public Builder withByText(String byText) {
			this.byText = byText;
			return this;
		}

		public Builder withByValue(Boolean byValue) {
			this.byValue = byValue;
			return this;
		}

		public Builder withByDate(Date byDate) {
			this.byDate = byDate;
			return this;
		}

		public SearchCriteria build() {
			SearchCriteria criteria = new SearchCriteria();
			criteria.byId = this.byId;
			criteria.byText = this.byText;
			criteria.byValue = this.byValue;
			criteria.byDate = this.byDate;
			return criteria;
		}
	}

	public Long getById() {
		return byId;
	}

	public void setById(Long byId) {
		this.byId = byId;
	}

	public String getByText() {
		return (byText != null) 
				? byText.strip() 
				: Optional.ofNullable((String) byText).orElse(null);
	}

	public void setByText(String byText) {
		this.byText = byText;
	}

	public Boolean getByValue() {
		return byValue;
	}

	public void setByValue(Boolean byValue) {
		this.byValue = byValue;
	}

	public Date getByDate() {
		return byDate;
	}

	public void setByDate(Date byDate) {
		this.byDate = byDate;
	}

	@Override
	public String toString() {
		return "SearchCriteria [byId=" + byId + ", byText=" + byText + ", byValue=" + byValue + ", byDate=" + byDate
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((byDate == null) ? 0 : byDate.hashCode());
		result = prime * result + ((byId == null) ? 0 : byId.hashCode());
		result = prime * result + ((byText == null) ? 0 : byText.hashCode());
		result = prime * result + ((byValue == null) ? 0 : byValue.hashCode());
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
		SearchCriteria other = (SearchCriteria) obj;
		if (byDate == null) {
			if (other.byDate != null)
				return false;
		} else if (!byDate.equals(other.byDate))
			return false;
		if (byId == null) {
			if (other.byId != null)
				return false;
		} else if (!byId.equals(other.byId))
			return false;
		if (byText == null) {
			if (other.byText != null)
				return false;
		} else if (!byText.equals(other.byText))
			return false;
		if (byValue == null) {
			if (other.byValue != null)
				return false;
		} else if (!byValue.equals(other.byValue))
			return false;
		return true;
	}

	@Override
	public int compareTo(SearchCriteria other) {
		return Comparator
				.comparing(SearchCriteria::getByText, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}
