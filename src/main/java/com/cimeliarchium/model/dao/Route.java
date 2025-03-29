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

@Entity
@Table(name = "route")
public class Route implements Comparable<Route> {

	@Id
	@NotNull
	@Column(name = "route_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long routeId;

	@Column(name = "route_name")
	private String routeName;

	@Column(name = "route_detail")
	private String routeDetail;

	@Column(name = "route_points")
	private String routePoints;

	@Column(name = "start_location_id")
	private Long startLocationId;

	@Column(name = "start_location_label_name")
	private String startLocationLabelName;

	@Column(name = "end_location_id")
	private Long endLocationId;

	@Column(name = "end_location_label_name")
	private String endLocationLabelName;

	@Column(name = "location_ids")
	private String locationIds;

	private Route() {}

	public static class Builder {

		private Long routeId;
		private String routeName;
		private String routeDetail;
		private String routePoints;
		private Long startLocationId;
		private String startLocationLabelName;
		private Long endLocationId;
		private String endLocationLabelName;
		private String locationIds;

		public Builder withRouteId (Long routeId) {
			this.routeId = routeId;
			return this;
		}

		public Builder withRouteName (String routeName) {
			this.routeName = routeName;
			return this;
		}

		public Builder withRouteDetail (String routeDetail) {
			this.routeDetail = routeDetail;
			return this;
		}

		public Builder withRoutePoints (String routePoints) {
			this.routePoints = routePoints;
			return this;
		}

		public Builder withStartLocationId (Long startLocationId) {
			this.startLocationId = startLocationId;
			return this;
		}

		public Builder withStartLocationLabelName (String startLocationLabelName) {
			this.startLocationLabelName = startLocationLabelName;
			return this;
		}

		public Builder withEndLocationId (Long endLocationId) {
			this.endLocationId = endLocationId;
			return this;
		}

		public Builder withEndLocationLabelName (String endLocationLabelName) {
			this.endLocationLabelName = endLocationLabelName;
			return this;
		}

		public Builder withLocationIds (String locationIds) {
			this.locationIds = locationIds;
			return this;
		}

		public Route build() {
			Route route = new Route();
			route.routeId = this.routeId;
			route.routeName = this.routeName;
			route.routeDetail = this.routeDetail;
			route.routePoints = this.routePoints;
			route.startLocationId = this.startLocationId;
			route.startLocationLabelName = this.startLocationLabelName;
			route.endLocationId = this.endLocationId;
			route.endLocationLabelName = this.endLocationLabelName;
			route.locationIds = this.locationIds;
			return route;
		}
	}

	public Long getRouteId() {
		return routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public String getRouteDetail() {
		return routeDetail;
	}

	public String getRoutePoints() {
		return routePoints;
	}

	public Long getStartLocationId() {
		return startLocationId;
	}

	public String getStartLocationLabelName() {
		return startLocationLabelName;
	}

	public Long getEndLocationId() {
		return endLocationId;
	}

	public String getEndLocationLabelName() {
		return endLocationLabelName;
	}

	public String getLocationIds() {
		return locationIds;
	}

	public void appendToLocationIds(String locationId) {
		if (locationId != null && !locationId.isEmpty()) {
			StringJoiner sj = new StringJoiner(",");
			this.locationIds = sj.add(locationIds).add(locationId).toString();
		}
	}

	@Override
	public String toString() {
		return "Route [routeId=" + routeId + ", routeName=" + routeName + ", routeDetail=" + routeDetail
				+ ", routePoints=" + routePoints + ", startLocationId=" + startLocationId + ", startLocationLabelName="
				+ startLocationLabelName + ", endLocationId=" + endLocationId + ", endLocationLabelName="
				+ endLocationLabelName + ", locationIds=" + locationIds + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endLocationId == null) ? 0 : endLocationId.hashCode());
		result = prime * result + ((endLocationLabelName == null) ? 0 : endLocationLabelName.hashCode());
		result = prime * result + ((locationIds == null) ? 0 : locationIds.hashCode());
		result = prime * result + ((routeDetail == null) ? 0 : routeDetail.hashCode());
		result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
		result = prime * result + ((routeName == null) ? 0 : routeName.hashCode());
		result = prime * result + ((routePoints == null) ? 0 : routePoints.hashCode());
		result = prime * result + ((startLocationId == null) ? 0 : startLocationId.hashCode());
		result = prime * result + ((startLocationLabelName == null) ? 0 : startLocationLabelName.hashCode());
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
		Route other = (Route) obj;
		if (endLocationId == null) {
			if (other.endLocationId != null)
				return false;
		} else if (!endLocationId.equals(other.endLocationId))
			return false;
		if (endLocationLabelName == null) {
			if (other.endLocationLabelName != null)
				return false;
		} else if (!endLocationLabelName.equals(other.endLocationLabelName))
			return false;
		if (locationIds == null) {
			if (other.locationIds != null)
				return false;
		} else if (!locationIds.equals(other.locationIds))
			return false;
		if (routeDetail == null) {
			if (other.routeDetail != null)
				return false;
		} else if (!routeDetail.equals(other.routeDetail))
			return false;
		if (routeId == null) {
			if (other.routeId != null)
				return false;
		} else if (!routeId.equals(other.routeId))
			return false;
		if (routeName == null) {
			if (other.routeName != null)
				return false;
		} else if (!routeName.equals(other.routeName))
			return false;
		if (routePoints == null) {
			if (other.routePoints != null)
				return false;
		} else if (!routePoints.equals(other.routePoints))
			return false;
		if (startLocationId == null) {
			if (other.startLocationId != null)
				return false;
		} else if (!startLocationId.equals(other.startLocationId))
			return false;
		if (startLocationLabelName == null) {
			if (other.startLocationLabelName != null)
				return false;
		} else if (!startLocationLabelName.equals(other.startLocationLabelName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Route other) {
		return Comparator
				.comparing(Route::getRouteName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}