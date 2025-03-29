package com.cimeliarchium.model.dao;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "creed")
public class Creed extends BaseEntity implements Comparable<Creed> {

	@Id
	@NotNull
	@Column(name = "creed_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long creedId;

	@Column(name = "creed_name")
	private String creedName;

	@Column(name = "creed_detail")
	private String creedDetail;

	private Creed() {}

	public static class Builder {

		private Long creedId;
		private String creedName;
		private String creedDetail;

		public Builder withCreedId(Long creedId) {
			this.creedId = creedId;
			return this;
		}

		public Builder withCreedName(String creedName) {
			this.creedName = creedName;
			return this;
		}

		public Builder withCreedDetail(String creedDetail) {
			this.creedDetail = creedDetail;
			return this;
		}

		public Creed build() {
			Creed creed = new Creed();
			creed.creedId = this.creedId;
			creed.creedName = this.creedName;
			creed.creedDetail = this.creedDetail;
			return creed;
		}
	}

	public Long getCreedId() {
		return creedId;
	}

	public String getCreedName() {
		return creedName;
	}

	public String getCreedDetail() {
		return creedDetail;
	}

	public static Long getPrimaryCalendarForCreed(Long creedId) {
		if (creedId == CREED_JEWISH_ID) {
			return CALENDAR_HEBREW_ID;
		} else if (creedId == CREED_ISLAMIC_ID 
				|| creedId == CREED_SUNNI_ID 
				|| creedId == CREED_SHIITE_ID) {
			return CALENDAR_HIJRI_ID;
		} else if (creedId == CREED_ORTHODOX_ID 
				|| creedId == CREED_FILIOQUIST_ID
				|| creedId == CREED_CATHOLIC_ID
				|| creedId == CREED_PROTESTANT_ID
				|| creedId == CREED_CHALCEDONIAN_ID
				|| creedId == CREED_APOSTOLIC_ID) {
			return CALENDAR_GREGORIAN_ID;
		} else {
			return CALENDAR_ALL_ID;
		}
	}

	public static Boolean isIslamicCreedInclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ABRAHAMIC_ID 
						|| creedId == CREED_APOSTOLIC_ID 
						|| creedId == CREED_ISLAMIC_ID
						|| creedId == CREED_SUNNI_ID
						|| creedId == CREED_SHIITE_ID));
	}

	public static Boolean isIslamicCreedExclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ISLAMIC_ID
						|| creedId == CREED_SUNNI_ID
						|| creedId == CREED_SHIITE_ID));
	}

	public static Boolean isJewishCreedInclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ABRAHAMIC_ID 
						|| creedId == CREED_JEWISH_ID));
	}

	public static Boolean isJewishCreedExclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_JEWISH_ID));
	}

	public static Boolean isChristianCreedInclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ABRAHAMIC_ID 
						|| creedId == CREED_APOSTOLIC_ID 
						|| creedId == CREED_CHALCEDONIAN_ID 
						|| creedId == CREED_FILIOQUIST_ID 
						|| creedId == CREED_CATHOLIC_ID
						|| creedId == CREED_PROTESTANT_ID));
	}

	public static Boolean isChristianCreedExclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_CHALCEDONIAN_ID 
						|| creedId == CREED_FILIOQUIST_ID 
						|| creedId == CREED_CATHOLIC_ID
						|| creedId == CREED_PROTESTANT_ID
						|| creedId == CREED_ORTHODOX_ID));
	}

	public static Boolean isCatholicCreedInclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ABRAHAMIC_ID 
						|| creedId == CREED_APOSTOLIC_ID 
						|| creedId == CREED_CHALCEDONIAN_ID 
						|| creedId == CREED_FILIOQUIST_ID 
						|| creedId == CREED_CATHOLIC_ID));
	}

	public static Boolean isOrthodoxCreedInclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ABRAHAMIC_ID 
						|| creedId == CREED_APOSTOLIC_ID 
						|| creedId == CREED_CHALCEDONIAN_ID 
						|| creedId == CREED_ORTHODOX_ID));
	}

	public static Boolean isProtestantCreedInclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ABRAHAMIC_ID 
						|| creedId == CREED_APOSTOLIC_ID 
						|| creedId == CREED_CHALCEDONIAN_ID 
						|| creedId == CREED_FILIOQUIST_ID 
						|| creedId == CREED_PROTESTANT_ID));
	}

	public static Boolean isShiiteCreedInclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ABRAHAMIC_ID 
						|| creedId == CREED_APOSTOLIC_ID 
						|| creedId == CREED_ISLAMIC_ID
						|| creedId == CREED_SHIITE_ID));
	}

	public static Boolean isSunniCreedInclusive(Long creedId) {
		return (creedId != null 
				&& (creedId == CREED_ABRAHAMIC_ID 
						|| creedId == CREED_APOSTOLIC_ID 
						|| creedId == CREED_ISLAMIC_ID
						|| creedId == CREED_SUNNI_ID));
	}

	@Override
	public String toString() {
		return "Creed [creedId=" + creedId + ", creedName=" + creedName + ", creedDetail=" + creedDetail + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creedDetail == null) ? 0 : creedDetail.hashCode());
		result = prime * result + ((creedId == null) ? 0 : creedId.hashCode());
		result = prime * result + ((creedName == null) ? 0 : creedName.hashCode());
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
		Creed other = (Creed) obj;
		if (creedDetail == null) {
			if (other.creedDetail != null)
				return false;
		} else if (!creedDetail.equals(other.creedDetail))
			return false;
		if (creedId == null) {
			if (other.creedId != null)
				return false;
		} else if (!creedId.equals(other.creedId))
			return false;
		if (creedName == null) {
			if (other.creedName != null)
				return false;
		} else if (!creedName.equals(other.creedName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Creed other) {
		return Comparator
				.comparing(Creed::getCreedName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}