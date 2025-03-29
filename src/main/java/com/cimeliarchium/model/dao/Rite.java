package com.cimeliarchium.model.dao;

import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dto.DayCommemorationDto;

@Entity
@Table(name = "rite")
public class Rite extends BaseEntity implements Comparable<Rite> {

	@Id
	@NotNull
	@Column(name = "rite_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long riteId;

	@Column(name = "calendar_id")
	private Long calendarId;

	@Column(name = "rite_name")
	private String riteName;

	@Column(name = "rite_detail")
	private String riteDetail;

	@Column(name = "rite_code")
	private String riteCode;

	@Column(name = "calendar_name")
	private String calendarName;

	@Transient
	private List<Long> creedIds;

	private Rite() {}

	public static class Builder {

		private Long riteId;
		private Long calendarId;
		private String riteName;
		private String riteDetail;
		private String riteCode;
		private String calendarName;
		private List<Long> creedIds;

		public Builder withRiteId(Long riteId) {
			this.riteId = riteId;
			return this;
		}

		public Builder withCalendarId (Long calendarId) {
			this.calendarId = calendarId;
			return this;
		}

		public Builder withRiteName (String riteName) {
			this.riteName = riteName;
			return this;
		}

		public Builder withRiteDetail (String riteDetail) {
			this.riteDetail = riteDetail;
			return this;
		}

		public Builder withRiteCode (String riteCode) {
			this.riteCode = riteCode;
			return this;
		}

		public Builder withCalendarName (String calendarName) {
			this.calendarName = calendarName;
			return this;
		}

		public Builder withCreedIds (List<Long> creedIds) {
			this.creedIds = creedIds;
			return this;
		}

		public Rite build() {
			Rite rite = new Rite();
			rite.riteId = this.riteId;
			rite.calendarId = this.calendarId;
			rite.riteName = this.riteName;
			rite.riteDetail = this.riteDetail;
			rite.riteCode = this.riteCode;
			rite.calendarName = this.calendarName;
			rite.creedIds = this.creedIds;
			return rite;
		}
	}

	public Long getRiteId() {
		return riteId;
	}

	public Long getCalendarId() {
		return calendarId;
	}

	public String getRiteName() {
		return riteName;
	}

	public String getRiteDetail() {
		return riteDetail;
	}

	public String getRiteCode() {
		return riteCode;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public List<Long> getCreedIds() {
		return creedIds;
	}

	@Override
	public String toString() {
		return "Rite [riteId=" + riteId + ", calendarId=" + calendarId + ", riteName=" + riteName + ", riteDetail="
				+ riteDetail + ", riteCode=" + riteCode + ", creedIds=" + creedIds + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendarId == null) ? 0 : calendarId.hashCode());
		result = prime * result + ((creedIds == null) ? 0 : creedIds.hashCode());
		result = prime * result + ((riteCode == null) ? 0 : riteCode.hashCode());
		result = prime * result + ((riteDetail == null) ? 0 : riteDetail.hashCode());
		result = prime * result + ((riteId == null) ? 0 : riteId.hashCode());
		result = prime * result + ((riteName == null) ? 0 : riteName.hashCode());
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
		Rite other = (Rite) obj;
		if (calendarId == null) {
			if (other.calendarId != null)
				return false;
		} else if (!calendarId.equals(other.calendarId))
			return false;
		if (creedIds == null) {
			if (other.creedIds != null)
				return false;
		} else if (!creedIds.equals(other.creedIds))
			return false;
		if (riteCode == null) {
			if (other.riteCode != null)
				return false;
		} else if (!riteCode.equals(other.riteCode))
			return false;
		if (riteDetail == null) {
			if (other.riteDetail != null)
				return false;
		} else if (!riteDetail.equals(other.riteDetail))
			return false;
		if (riteId == null) {
			if (other.riteId != null)
				return false;
		} else if (!riteId.equals(other.riteId))
			return false;
		if (riteName == null) {
			if (other.riteName != null)
				return false;
		} else if (!riteName.equals(other.riteName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Rite other) {
		return Comparator
				.comparing(Rite::getRiteName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}

	/**
	 * Method used to check whether a given Creed is mapped on the current Rite
	 * 
	 * @param dto
	 * @return boolean
	 */
	public boolean hasCreed(Long creedId) {
		return (this.creedIds.contains(creedId));
	}

	/**
	 * Method used to determine whether the Rite is applicable to a given DayCommemorationDto
	 * based on the category of the corresponding database-determined calendarAltType: 
	 * - Those which are 'Replaced by' are applicable only to Tridentine Rite Catholicism
	 * - Those which 'Replace' are applicable to Novus Ordo Rite Catholicism
	 * - Always hide 'All Commemorations' from the list of Rites
	 * 
	 * @param dto the DayCommemorationDto
	 * @return boolean
	 */
	public boolean isSuppressedForCommemoration(DayCommemorationDto dto) {

		final CalendarAltType calendarAltType = dto.getCalendarAltType();
		if (calendarAltType != null) {
			final String calendarAltTypeName = calendarAltType.getCalendarAltTypeName();
			final boolean hasAltTypeName = (calendarAltTypeName != null
					&& !calendarAltTypeName.isEmpty());
			final boolean isReplacedTridentine = (hasAltTypeName 
					&& this.riteId == RITE_TRIDENTINE_ID && calendarAltTypeName.startsWith("Replaces"));
			final boolean isReplacesNovusOrdo = (hasAltTypeName && 
					this.riteId == RITE_NOVUSORDO_ID && calendarAltTypeName.startsWith("Replaced by"));
			final boolean isAllCommemorations = (this.riteId == RITE_ALL_ID);
			if (isReplacedTridentine || isReplacesNovusOrdo || isAllCommemorations) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Long getCurrentOrOverrideRiteId(Boolean showAll) {
		return ((showAll != null && showAll) || this.riteId == RITE_ALL_ID) 
				? RITE_ALL_ID
				: this.riteId;
	}

	public Long getCurrentOrOverrideCalendarId(Boolean showAll) {
		return ((showAll != null && showAll) || this.calendarId == CALENDAR_ALL_ID) 
				? CALENDAR_ALL_ID
				: this.calendarId;
	}

	public String getCalendarRiteNameFormatted(Boolean showAll) {
		return ((showAll != null && showAll) || this.riteId == RITE_ALL_ID) 
				? "All Commemorations"
				: this.riteName;
	}

	public String getCalendarRiteCodeFormatted(Boolean showAll) {
		return ((showAll != null && showAll) || this.riteId == RITE_ALL_ID) 
				? "ALL"
				: this.riteCode;
	}
}