package com.cimeliarchium.model.dao;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "location")
public class Location extends BaseEntity implements Comparable<Location>, Cloneable {

	@Id
	@NotNull
	@Column(name = "location_id")
	@GenericGenerator(name = "AppIdentityGenerator", strategy = "com.cimeliarchium.config.AppIdentityGenerator")
	@GeneratedValue(generator = "AppIdentityGenerator")
	private Long locationId;

	@Column(name = "location_override_id")
	private String locationOverrideId;

	@Column(name = "alt_inset_id")
	private Long altInsetId;

	@Column(name = "inset_id")
	private Long insetId;

	@Column(name = "label_name")
	private String labelName;

	@Column(name = "last_update_time")
	private LocalDate lastUpdateTime;

	@Column(name = "location_type_id")
	private Long locationTypeId;

	@Column(name = "nation_id")
	private Long nationId;

	@Column(name = "next_location_id")
	private Long nextLocationId;

	@Column(name = "point_x")
	private Double pointX;

	@Column(name = "point_y")
	private Double pointY;

	@Column(name = "prev_location_id")
	private Long prevLocationId;

	@Column(name = "related_patronage_id")
	private Long relatedPatronageId;

	@Column(name = "route_a_id")
	private Long routeAId;

	@Column(name = "route_b_id")
	private Long routeBId;

	@Column(name = "route_c_id")
	private Long routeCId;

	@Column(name = "route_d_id")
	private Long routeDId;

	@Column(name = "route_e_id")
	private Long routeEId;

	/**
	 * All fields below are virtual,i.e. those values mapped from DTOs, but not persisted in the database.
	 * Hence, we do not consider these values in toString(), hashCode(), or equals() methods.
	 */
	
	@Column(name = "all_commemoration_count")
	private Long allCommemorationCount;

	@Column(name = "alt_label_name")
	private String altLabelName;

	@Column(name = "catholic_commemoration_count")
	private Long catholicCommemorationCount;

	@Column(name = "jewish_commemoration_count")
	private Long jewishCommemorationCount;

	@Column(name = "location_type_code")
	private String locationTypeCode;

	@Column(name = "location_type_name")
	private String locationTypeName;

	@Column(name = "next_location_label_name")
	private String nextLocationLabelName;

	@Column(name = "next_location_pointx")
	private Double nextLocationPointX;

	@Column(name = "next_location_pointy")
	private Double nextLocationPointY;

	@Column(name = "next_nation_id")
	private Long nextNationId;

	@Column(name = "orthodox_commemoration_count")
	private Long orthodoxCommemorationCount;

	@Column(name = "prev_location_label_name")
	private String prevLocationLabelName;

	@Column(name = "prev_location_pointx")
	private Double prevLocationPointX;

	@Column(name = "prev_location_pointy")
	private Double prevLocationPointY;

	@Column(name = "prev_nation_id")
	private Long prevNationId;

	@Column(name = "protestant_commemoration_count")
	private Long protestantCommemorationCount;

	@Column(name = "related_patronage_name")
	private String relatedPatronageName;

	@Column(name = "related_patronage_subtype_id")
	private Long relatedPatronageSubtypeId;

	@Column(name = "related_patronage_subtype_name")
	private String relatedPatronageSubtypeName;

	@Column(name = "routeaname")
	private String routeAName;

	@Column(name = "routebname")
	private String routeBName;

	@Column(name = "routecname")
	private String routeCName;

	@Column(name = "routedname")
	private String routeDName;

	@Column(name = "routeename")
	private String routeEName;

	@Column(name = "shiite_commemoration_count")
	private Long shiiteCommemorationCount;

	@Column(name = "sunni_commemoration_count")
	private Long sunniCommemorationCount;

	private Location() {}

	public static class Builder {

		private Long locationId;
		private String locationOverrideId;
		private String altLabelName;
		private Long altInsetId;
		private Long nationId;
		private Double pointX;
		private Double pointY;
		private String labelName;
		private Long locationTypeId;
		private String locationTypeCode;
		private String locationTypeName;
		private Long prevNationId;
		private Long prevLocationId;
		private String prevLocationLabelName;
		private Double prevLocationPointX;
		private Double prevLocationPointY;
		private Long nextNationId;
		private Long nextLocationId;
		private String nextLocationLabelName;
		private Double nextLocationPointX;
		private Double nextLocationPointY;
		private Long insetId;
		private Long allCommemorationCount;
		private Long catholicCommemorationCount;
		private Long protestantCommemorationCount;
		private Long orthodoxCommemorationCount;
		private Long sunniCommemorationCount;
		private Long shiiteCommemorationCount;
		private Long jewishCommemorationCount;
		private Long routeAId;
		private String routeAName;
		private Long routeBId;
		private String routeBName;
		private Long routeCId;
		private String routeCName;
		private Long routeDId;
		private String routeDName;
		private Long routeEId;
		private String routeEName;
		private Long relatedPatronageSubtypeId;
		private String relatedPatronageSubtypeName;
		private Long relatedPatronageId;
		private String relatedPatronageName;
		private LocalDate lastUpdateTime;

		public Builder withLocationId (Long locationId) {
			this.locationId = locationId;
			return this;
		}

		public Builder withLocationOverrideId (String locationOverrideId) {
			this.locationOverrideId = locationOverrideId;
			return this;
		}

		public Builder withAltLabelName (String altLabelName) {
			this.altLabelName = altLabelName;
			return this;
		}

		public Builder withAltInsetId (Long altInsetId) {
			this.altInsetId = altInsetId;
			return this;
		}

		public Builder withNationId (Long nationId) {
			this.nationId = nationId;
			return this;
		}

		public Builder withPointX (Double pointX) {
			this.pointX = pointX;
			return this;
		}

		public Builder withPointY (Double pointY) {
			this.pointY = pointY;
			return this;
		}

		public Builder withLabelName (String labelName) {
			this.labelName = labelName;
			return this;
		}

		public Builder withLocationTypeId (Long locationTypeId) {
			this.locationTypeId = locationTypeId;
			return this;
		}

		public Builder withLocationTypeCode(String locationTypeCode) {
			this.locationTypeCode = locationTypeCode;
			return this;
		}

		public Builder withLocationTypeName(String locationTypeName) {
			this.locationTypeName = locationTypeName;
			return this;
		}

		public Builder withPrevNationId (Long prevNationId) {
			this.prevNationId = prevNationId;
			return this;
		}

		public Builder withPrevLocationId (Long prevLocationId) {
			this.prevLocationId = prevLocationId;
			return this;
		}

		public Builder withPrevLocationLabelName(String prevLocationLabelName) {
			this.prevLocationLabelName = prevLocationLabelName;
			return this;
		}

		public Builder withPrevLocationPointX(Double prevLocationPointX) {
			this.prevLocationPointX = prevLocationPointX;
			return this;
		}

		public Builder withPrevLocationPointY(Double prevLocationPointY) {
			this.prevLocationPointY = prevLocationPointY;
			return this;
		}

		public Builder withNextNationId (Long nextNationId) {
			this.nextNationId = nextNationId;
			return this;
		}

		public Builder withNextLocationId (Long nextLocationId) {
			this.nextLocationId = nextLocationId;
			return this;
		}

		public Builder withNextLocationLabelName(String nextLocationLabelName) {
			this.nextLocationLabelName = nextLocationLabelName;
			return this;
		}

		public Builder withNextLocationPointX(Double nextLocationPointX) {
			this.nextLocationPointX = nextLocationPointX;
			return this;
		}

		public Builder withNextLocationPointY(Double nextLocationPointY) {
			this.nextLocationPointY = nextLocationPointY;
			return this;
		}

		public Builder withInsetId(Long insetId) {
			this.insetId = insetId;
			return this;
		}

		public Builder withAllCommemorationCount(Long allCommemorationCount) {
			this.allCommemorationCount = allCommemorationCount;
			return this;
		}

		public Builder withCatholicCommemorationCount(Long catholicCommemorationCount) {
			this.catholicCommemorationCount = catholicCommemorationCount;
			return this;
		}

		public Builder withProtestantCommemorationCount(Long protestantCommemorationCount) {
			this.protestantCommemorationCount = protestantCommemorationCount;
			return this;
		}

		public Builder withOrthodoxCommemorationCount(Long orthodoxCommemorationCount) {
			this.orthodoxCommemorationCount = orthodoxCommemorationCount;
			return this;
		}

		public Builder withSunniCommemorationCount(Long sunniCommemorationCount) {
			this.sunniCommemorationCount = sunniCommemorationCount;
			return this;
		}

		public Builder withShiiteCommemorationCount(Long shiiteCommemorationCount) {
			this.shiiteCommemorationCount = shiiteCommemorationCount;
			return this;
		}

		public Builder withJewishCommemorationCount(Long jewishCommemorationCount) {
			this.jewishCommemorationCount = jewishCommemorationCount;
			return this;
		}

		public Builder withRouteAId(Long routeAId) {
			this.routeAId = routeAId;
			return this;
		}

		public Builder withRouteAName(String routeAName) {
			this.routeAName = routeAName;
			return this;
		}

		public Builder withRouteBId(Long routeBId) {
			this.routeBId = routeBId;
			return this;
		}

		public Builder withRouteBName(String routeBName) {
			this.routeBName = routeBName;
			return this;
		}

		public Builder withRouteCId(Long routeCId) {
			this.routeCId = routeCId;
			return this;
		}

		public Builder withRouteCName(String routeCName) {
			this.routeCName = routeCName;
			return this;
		}

		public Builder withRouteDId(Long routeDId) {
			this.routeDId = routeDId;
			return this;
		}

		public Builder withRouteDName(String routeDName) {
			this.routeDName = routeDName;
			return this;
		}

		public Builder withRouteEId(Long routeEId) {
			this.routeEId = routeEId;
			return this;
		}

		public Builder withRouteEName(String routeEName) {
			this.routeEName = routeEName;
			return this;
		}

		public Builder withRelatedPatronageSubtypeId(Long relatedPatronageSubtypeId) {
			this.relatedPatronageSubtypeId = relatedPatronageSubtypeId;
			return this;
		}

		public Builder withRelatedPatronageSubtypeName(String relatedPatronageSubtypeName) {
			this.relatedPatronageSubtypeName = relatedPatronageSubtypeName;
			return this;
		}

		public Builder withRelatedPatronageId(Long relatedPatronageId) {
			this.relatedPatronageId = relatedPatronageId;
			return this;
		}

		public Builder withRelatedPatronageName(String relatedPatronageName) {
			this.relatedPatronageName = relatedPatronageName;
			return this;
		}

		public Builder withLastUpdateTime(LocalDate lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
			return this;
		}

		public Location build() {
			Location location = new Location();
			location.locationId = this.locationId;
			location.locationOverrideId = this.locationOverrideId;
			location.altLabelName = this.altLabelName;
			location.altInsetId = this.altInsetId;
			location.nationId = this.nationId;
			location.pointX = this.pointX;
			location.pointY = this.pointY;
			location.labelName = this.labelName;
			location.locationTypeId = this.locationTypeId;
			location.locationTypeCode = this.locationTypeCode;
			location.locationTypeName = this.locationTypeName;
			location.prevNationId = this.prevNationId;
			location.prevLocationId = this.prevLocationId;
			location.prevLocationLabelName = this.prevLocationLabelName;
			location.prevLocationPointX = this.prevLocationPointX;
			location.prevLocationPointY = this.prevLocationPointY;
			location.nextNationId = this.nextNationId;
			location.nextLocationId = this.nextLocationId;
			location.nextLocationLabelName = this.nextLocationLabelName;
			location.nextLocationPointX = this.nextLocationPointX;
			location.nextLocationPointY = this.nextLocationPointY;
			location.insetId = this.insetId;
			location.allCommemorationCount = this.allCommemorationCount;
			location.catholicCommemorationCount = this.catholicCommemorationCount;
			location.protestantCommemorationCount = this.protestantCommemorationCount;
			location.orthodoxCommemorationCount = this.orthodoxCommemorationCount;
			location.sunniCommemorationCount = this.sunniCommemorationCount;
			location.shiiteCommemorationCount = this.shiiteCommemorationCount;
			location.jewishCommemorationCount = this.jewishCommemorationCount;
			location.routeAId = this.routeAId;
			location.routeAName = this.routeAName;
			location.routeBId = this.routeBId;
			location.routeBName = this.routeBName;
			location.routeCId = this.routeCId;
			location.routeCName = this.routeCName;
			location.routeDId = this.routeDId;
			location.routeDName = this.routeDName;
			location.routeEId = this.routeEId;
			location.routeEName = this.routeEName;
			location.relatedPatronageSubtypeId = this.relatedPatronageSubtypeId;
			location.relatedPatronageSubtypeName = this.relatedPatronageSubtypeName;
			location.relatedPatronageId = this.relatedPatronageId;
			location.relatedPatronageName = this.relatedPatronageName;
			location.lastUpdateTime = this.lastUpdateTime;
			return location;
		}
	}

	public Long getLocationId() {
		return locationId;
	}

	public String getLocationOverrideId() {
		return locationOverrideId;
	}

	public String getAltLabelName() {
		return altLabelName;
	}

	public Long getAltInsetId() {
		return altInsetId;
	}

	public Long getNationId() {
		return nationId;
	}

	public Double getPointX() {
		return pointX;
	}

	public Double getPointY() {
		return pointY;
	}

	public String getLabelName() {
		// Always strip left and right arrow unicode characters
		// that get passed from the input field on request.
		return (labelName != null) 
				? labelName.replaceAll("\\u2190\\s", "").replaceAll("\\u2192\\s", "")
				: null;
	}

	public String getLabelNameFormatted() {
		final String strLabelName = this.getLabelName();
		return (altLabelName != null) 
				? new StringJoiner(", ").add(altLabelName).add(strLabelName).toString()
				: strLabelName;
	}

	public Long getLocationTypeId() {
		return locationTypeId;
	}

	public String getLocationTypeCode() {
		return locationTypeCode;
	}

	public String getLocationTypeName() {
		return locationTypeName;
	}

	/**
	 * 'JSON Ignore' annotation required to prevent recursive calls on response,
	 * given bi-directional relationship between Locations implicit in this response.
	 */
	@JsonIgnore
	public Location getPrevLocation() {
		return new Location.Builder()
				.withNationId(prevNationId)
				.withLocationId(prevLocationId)
				.withLabelName(prevLocationLabelName)
				.withNextLocationId(locationId)
				.withPointX(prevLocationPointX)
				.withPointY(prevLocationPointY)
				.build();
	}

	/**
	 * 'JSON Ignore' annotation required to prevent recursive calls on response,
	 * given bi-directional relationship between Locations implicit in this response.
	 */
	@JsonIgnore
	public Location getNextLocation() {
		return new Location.Builder()
				.withNationId(nextNationId)
				.withLocationId(nextLocationId)
				.withPrevLocationId(locationId)
				.withLabelName(nextLocationLabelName)
				.withPointX(nextLocationPointX)
				.withPointY(nextLocationPointY)
				.build();
	}

	public Long getPrevNationId() {
		return prevNationId;
	}

	public Long getNextNationId() {
		return nextNationId;
	}

	public Long getPrevLocationId() {
		return prevLocationId;
	}

	public Long getNextLocationId() {
		return nextLocationId;
	}

	public String getPrevLocationLabelName() {
		return prevLocationLabelName;
	}

	public String getNextLocationLabelName() {
		return nextLocationLabelName;
	}

	public Long getInsetId() {
		return insetId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setAltLabelName(String altLabelName) {
		this.altLabelName = altLabelName;
	}

	public void setAltInsetId(Long altInsetId) {
		this.altInsetId = altInsetId;
	}

	public void setNationId(Long nationId) {
		this.nationId = nationId;
	}

	public void setPointX(Double pointX) {
		this.pointX = pointX;
	}

	public void setPointY(Double pointY) {
		this.pointY = pointY;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setLocationTypeId(Long locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public void setLocationTypeCode(String locationTypeCode) {
		this.locationTypeCode = locationTypeCode;
	}

	public void setLocationTypeName(String locationTypeName) {
		this.locationTypeName = locationTypeName;
	}

	public void setPrevLocationId(Long prevLocationId) {
		this.prevLocationId = prevLocationId;
	}

	public void setPrevNationId(Long prevNationId) {
		this.prevNationId = prevNationId;
	}

	public void setNextLocationId(Long nextLocationId) {
		this.nextLocationId = nextLocationId;
	}

	public void setNextNationId(Long nextNationId) {
		this.nextNationId = nextNationId;
	}

	public void setInsetId(Long insetId) {
		this.insetId = insetId;
	}

	public Long getRouteAId() {
		return routeAId;
	}

	public void setRouteAId(Long routeAId) {
		this.routeAId = routeAId;
	}

	public Long getRouteBId() {
		return routeBId;
	}

	public void setRouteBId(Long routeBId) {
		this.routeBId = routeBId;
	}

	public Long getRouteCId() {
		return routeCId;
	}

	public void setRouteCId(Long routeCId) {
		this.routeCId = routeCId;
	}

	public Long getRouteDId() {
		return routeDId;
	}

	public void setRouteDId(Long routeDId) {
		this.routeDId = routeDId;
	}

	public Long getRouteEId() {
		return routeEId;
	}

	public void setRouteEId(Long routeEId) {
		this.routeEId = routeEId;
	}

	public String getRouteAName() {
		return routeAName;
	}

	public void setRouteAName(String routeAName) {
		this.routeAName = routeAName;
	}

	public String getRouteBName() {
		return routeBName;
	}

	public void setRouteBName(String routeBName) {
		this.routeBName = routeBName;
	}

	public String getRouteCName() {
		return routeCName;
	}

	public void setRouteCName(String routeCName) {
		this.routeCName = routeCName;
	}

	public String getRouteDName() {
		return routeDName;
	}

	public void setRouteDName(String routeDName) {
		this.routeDName = routeDName;
	}

	public String getRouteEName() {
		return routeEName;
	}

	public void setRouteEName(String routeEName) {
		this.routeEName = routeEName;
	}

	public Long getRelatedPatronageSubtypeId() {
		return relatedPatronageSubtypeId;
	}

	public void setRelatedPatronageSubtypeId(Long relatedPatronageSubtypeId) {
		this.relatedPatronageSubtypeId = relatedPatronageSubtypeId;
	}

	public String getRelatedPatronageSubtypeName() {
		return relatedPatronageSubtypeName;
	}

	public void setRelatedPatronageSubtypeName(String relatedPatronageSubtypeName) {
		this.relatedPatronageSubtypeName = relatedPatronageSubtypeName;
	}

	public Long getRelatedPatronageId() {
		return relatedPatronageId;
	}

	public void setRelatedPatronageId(Long relatedPatronageId) {
		this.relatedPatronageId = relatedPatronageId;
	}

	public String getRelatedPatronageName() {
		return relatedPatronageName;
	}

	public void setRelatedPatronageName(String relatedPatronageName) {
		this.relatedPatronageName = relatedPatronageName;
	}

	public Long getAllCommemorationCount() {
		return (allCommemorationCount != null) 
				? Optional.of((Long) allCommemorationCount).orElse(null) 
				: 0L;
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Double getNextLocationPointX() {
		return nextLocationPointX;
	}

	public Double getNextLocationPointY() {
		return nextLocationPointY;
	}

	public Double getPrevLocationPointX() {
		return prevLocationPointX;
	}

	public Double getPrevLocationPointY() {
		return prevLocationPointY;
	}

	public Long getCatholicCommemorationCount() {
		return catholicCommemorationCount;
	}

	public Long getJewishCommemorationCount() {
		return jewishCommemorationCount;
	}

	public Long getOrthodoxCommemorationCount() {
		return orthodoxCommemorationCount;
	}

	public Long getProtestantCommemorationCount() {
		return protestantCommemorationCount;
	}

	public Long getShiiteCommemorationCount() {
		return shiiteCommemorationCount;
	}

	public Long getSunniCommemorationCount() {
		return sunniCommemorationCount;
	}

	public void setLocationOverrideId(String locationOverrideId) {
		this.locationOverrideId = locationOverrideId;
	}

	public void setAllCommemorationCount(Long allCommemorationCount) {
		this.allCommemorationCount = allCommemorationCount;
	}

	public void setCatholicCommemorationCount(Long catholicCommemorationCount) {
		this.catholicCommemorationCount = catholicCommemorationCount;
	}

	public void setJewishCommemorationCount(Long jewishCommemorationCount) {
		this.jewishCommemorationCount = jewishCommemorationCount;
	}

	public void setNextLocationLabelName(String nextLocationLabelName) {
		this.nextLocationLabelName = nextLocationLabelName;
	}

	public void setNextLocationPointX(Double nextLocationPointX) {
		this.nextLocationPointX = nextLocationPointX;
	}

	public void setNextLocationPointY(Double nextLocationPointY) {
		this.nextLocationPointY = nextLocationPointY;
	}

	public void setOrthodoxCommemorationCount(Long orthodoxCommemorationCount) {
		this.orthodoxCommemorationCount = orthodoxCommemorationCount;
	}

	public void setPrevLocationLabelName(String prevLocationLabelName) {
		this.prevLocationLabelName = prevLocationLabelName;
	}

	public void setPrevLocationPointX(Double prevLocationPointX) {
		this.prevLocationPointX = prevLocationPointX;
	}

	public void setPrevLocationPointY(Double prevLocationPointY) {
		this.prevLocationPointY = prevLocationPointY;
	}

	public void setProtestantCommemorationCount(Long protestantCommemorationCount) {
		this.protestantCommemorationCount = protestantCommemorationCount;
	}

	public void setShiiteCommemorationCount(Long shiiteCommemorationCount) {
		this.shiiteCommemorationCount = shiiteCommemorationCount;
	}

	public void setSunniCommemorationCount(Long sunniCommemorationCount) {
		this.sunniCommemorationCount = sunniCommemorationCount;
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", insetId=" + insetId + ", altInsetId=" + altInsetId
				+ ", labelName=" + labelName + ", lastUpdateTime=" + lastUpdateTime
				+ ", locationTypeId=" + locationTypeId + ", nationId=" + nationId + ", nextLocationId=" + nextLocationId
				+ ", pointX=" + pointX + ", pointY=" + pointY + ", prevLocationId=" + prevLocationId
				+ ", relatedPatronageId=" + relatedPatronageId + ", routeAId=" + routeAId + ", routeBId=" + routeBId
				+ ", routeCId=" + routeCId + ", routeDId=" + routeDId + ", routeEId=" + routeEId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altInsetId == null) ? 0 : altInsetId.hashCode());
		result = prime * result + ((insetId == null) ? 0 : insetId.hashCode());
		result = prime * result + ((labelName == null) ? 0 : labelName.hashCode());
		result = prime * result + ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((locationTypeId == null) ? 0 : locationTypeId.hashCode());
		result = prime * result + ((nationId == null) ? 0 : nationId.hashCode());
		result = prime * result + ((nextLocationId == null) ? 0 : nextLocationId.hashCode());
		result = prime * result + ((pointX == null) ? 0 : pointX.hashCode());
		result = prime * result + ((pointY == null) ? 0 : pointY.hashCode());
		result = prime * result + ((prevLocationId == null) ? 0 : prevLocationId.hashCode());
		result = prime * result + ((relatedPatronageId == null) ? 0 : relatedPatronageId.hashCode());
		result = prime * result + ((routeAId == null) ? 0 : routeAId.hashCode());
		result = prime * result + ((routeBId == null) ? 0 : routeBId.hashCode());
		result = prime * result + ((routeCId == null) ? 0 : routeCId.hashCode());
		result = prime * result + ((routeDId == null) ? 0 : routeDId.hashCode());
		result = prime * result + ((routeEId == null) ? 0 : routeEId.hashCode());
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
		Location other = (Location) obj;
		if (altInsetId == null) {
			if (other.altInsetId != null)
				return false;
		} else if (!altInsetId.equals(other.altInsetId))
			return false;
		if (insetId == null) {
			if (other.insetId != null)
				return false;
		} else if (!insetId.equals(other.insetId))
			return false;
		if (labelName == null) {
			if (other.labelName != null)
				return false;
		} else if (!labelName.equals(other.labelName))
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (locationTypeId == null) {
			if (other.locationTypeId != null)
				return false;
		} else if (!locationTypeId.equals(other.locationTypeId))
			return false;
		if (nationId == null) {
			if (other.nationId != null)
				return false;
		} else if (!nationId.equals(other.nationId))
			return false;
		if (nextLocationId == null) {
			if (other.nextLocationId != null)
				return false;
		} else if (!nextLocationId.equals(other.nextLocationId))
			return false;
		if (pointX == null) {
			if (other.pointX != null)
				return false;
		} else if (!pointX.equals(other.pointX))
			return false;
		if (pointY == null) {
			if (other.pointY != null)
				return false;
		} else if (!pointY.equals(other.pointY))
			return false;
		if (prevLocationId == null) {
			if (other.prevLocationId != null)
				return false;
		} else if (!prevLocationId.equals(other.prevLocationId))
			return false;
		if (relatedPatronageId == null) {
			if (other.relatedPatronageId != null)
				return false;
		} else if (!relatedPatronageId.equals(other.relatedPatronageId))
			return false;
		if (routeAId == null) {
			if (other.routeAId != null)
				return false;
		} else if (!routeAId.equals(other.routeAId))
			return false;
		if (routeBId == null) {
			if (other.routeBId != null)
				return false;
		} else if (!routeBId.equals(other.routeBId))
			return false;
		if (routeCId == null) {
			if (other.routeCId != null)
				return false;
		} else if (!routeCId.equals(other.routeCId))
			return false;
		if (routeDId == null) {
			if (other.routeDId != null)
				return false;
		} else if (!routeDId.equals(other.routeDId))
			return false;
		if (routeEId == null) {
			if (other.routeEId != null)
				return false;
		} else if (!routeEId.equals(other.routeEId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Location other) {
		return Comparator
				.comparing(Location::getLabelName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getLocationId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getNationId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getAltInsetId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getInsetId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getLocationTypeId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getPointX, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getPointY, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getPrevLocationId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getNextLocationId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getRouteAId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getRouteBId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getRouteCId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getRouteDId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getRouteEId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getRelatedPatronageId, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(Location::getLastUpdateTime, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}

	public Long getLocationIdOnDiffRouteAId(Location other) {
		return (this.routeAId != other.routeAId) ? this.locationId : null;
	}

	public Long getLocationIdOnDiffRouteBId(Location other) {
		return (this.routeBId != other.routeBId) ? this.locationId : null;
	}

	public Long getLocationIdOnDiffRouteCId(Location other) {
		return (this.routeCId != other.routeCId) ? this.locationId : null;
	}

	public Long getLocationIdOnDiffRouteDId(Location other) {
		return (this.routeDId != other.routeDId) ? this.locationId : null;
	}

	public Long getLocationIdOnDiffRouteEId(Location other) {
		return (this.routeEId != other.routeEId) ? this.locationId : null;
	}

	@Override
	@Deprecated
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Location cloneLocation() throws AppException {
		try {
			return (Location) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new AppException("");
		}
	}
}