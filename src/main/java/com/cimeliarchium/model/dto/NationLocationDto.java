package com.cimeliarchium.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.Nation;
import com.cimeliarchium.model.dao.Route;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "NationLocationMappingQuery", 
		procedureName = "select_locations_by_nation", 
		resultClasses = { NationLocationDto.class })
	})
public class NationLocationDto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nation_location_dto_id")
	private Long nationLocationDtoId;

	@Column(name = "nation_id")
	private Long nationId;

	@Column(name = "nation_name")
	private String nationName;

	@Column(name = "nation_code")
	private String nationCode;

	@Column(name = "alt_nation_code")
	private String altNationCode;

	@Column(name = "nation_related_patronage_id")
	private Long nationRelatedPatronageId;

	@Column(name = "is_old_world")
	private Boolean isOldWorld;

	@Column(name = "location_id")
	private Long locationId;

	@Column(name = "alt_label_name")
	private String altLabelName;

	@Column(name = "alt_inset_id")
	private Long altInsetId;

	@Column(name = "point_x")
	private Double pointX;

	@Column(name = "point_y")
	private Double pointY;

	@Column(name = "label_name")
	private String labelName;

	@Column(name = "location_type_id")
	private Long locationTypeId;

	@Column(name = "location_type_code")
	private String locationTypeCode;

	@Column(name = "prev_nation_id")
	private Long prevNationId;

	@Column(name = "prev_location_id")
	private Long prevLocationId;

	@Column(name = "prev_location_label_name")
	private String prevLocationLabelName;

	@Column(name = "prev_location_point_x")
	private Double prevLocationPointX;

	@Column(name = "prev_location_point_y")
	private Double prevLocationPointY;

	@Column(name = "next_nation_id")
	private Long nextNationId;

	@Column(name = "next_location_id")
	private Long nextLocationId;

	@Column(name = "next_location_label_name")
	private String nextLocationLabelName;

	@Column(name = "next_location_point_x")
	private Double nextLocationPointX;

	@Column(name = "next_location_point_y")
	private Double nextLocationPointY;

	@Column(name = "inset_id")
	private Long insetId;

	@Column(name = "location_related_patronage_subtype_id")
	private Long locationRelatedPatronageSubtypeId;

	@Column(name = "location_related_patronage_id")
	private Long locationRelatedPatronageId;

	@Column(name = "all_commemoration_count")
	private Long allCommemorationCount;

	@Column(name = "catholic_commemoration_count")
	private Long catholicCommemorationCount;

	@Column(name = "protestant_commemoration_count")
	private Long protestantCommemorationCount;

	@Column(name = "orthodox_commemoration_count")
	private Long orthodoxCommemorationCount;

	@Column(name = "sunni_commemoration_count")
	private Long sunniCommemorationCount;

	@Column(name = "shiite_commemoration_count")
	private Long shiiteCommemorationCount;

	@Column(name = "jewish_commemoration_count")
	private Long jewishCommemorationCount;

	@Column(name = "route_a_id")
	private Long routeAId;

	@Column(name = "route_a_name")
	private String routeAName;

	@Column(name = "route_a_detail")
	private String routeADetail;

	@Column(name = "route_a_points")
	private String routeAPoints;

	@Column(name = "route_a_start_location_id")
	private Long routeAStartLocationId;

	@Column(name = "route_a_start_location_label_name")
	private String routeAStartLocationLabelName;

	@Column(name = "route_a_end_location_id")
	private Long routeAEndLocationId;

	@Column(name = "route_a_end_location_label_name")
	private String routeAEndLocationLabelName;

	@Column(name = "route_a_location_ids")
	private String routeALocationIds;

	@Column(name = "route_b_id")
	private Long routeBId;

	@Column(name = "route_b_name")
	private String routeBName;

	@Column(name = "route_b_detail")
	private String routeBDetail;

	@Column(name = "route_b_points")
	private String routeBPoints;

	@Column(name = "route_b_start_location_id")
	private Long routeBStartLocationId;

	@Column(name = "route_b_start_location_label_name")
	private String routeBStartLocationLabelName;

	@Column(name = "route_b_end_location_id")
	private Long routeBEndLocationId;

	@Column(name = "route_b_end_location_label_name")
	private String routeBEndLocationLabelName;

	@Column(name = "route_b_location_ids")
	private String routeBLocationIds;

	@Column(name = "route_c_id")
	private Long routeCId;

	@Column(name = "route_c_name")
	private String routeCName;

	@Column(name = "route_c_detail")
	private String routeCDetail;

	@Column(name = "route_c_points")
	private String routeCPoints;

	@Column(name = "route_c_start_location_id")
	private Long routeCStartLocationId;

	@Column(name = "route_c_start_location_label_name")
	private String routeCStartLocationLabelName;

	@Column(name = "route_c_end_location_id")
	private Long routeCEndLocationId;

	@Column(name = "route_c_end_location_label_name")
	private String routeCEndLocationLabelName;

	@Column(name = "route_c_location_ids")
	private String routeCLocationIds;

	@Column(name = "route_d_id")
	private Long routeDId;

	@Column(name = "route_d_name")
	private String routeDName;

	@Column(name = "route_d_detail")
	private String routeDDetail;

	@Column(name = "route_d_points")
	private String routeDPoints;

	@Column(name = "route_d_start_location_id")
	private Long routeDStartLocationId;

	@Column(name = "route_d_start_location_label_name")
	private String routeDStartLocationLabelName;

	@Column(name = "route_d_end_location_id")
	private Long routeDEndLocationId;

	@Column(name = "route_d_end_location_label_name")
	private String routeDEndLocationLabelName;

	@Column(name = "route_d_location_ids")
	private String routeDLocationIds;

	@Column(name = "route_e_id")
	private Long routeEId;

	@Column(name = "route_e_name")
	private String routeEName;

	@Column(name = "route_e_detail")
	private String routeEDetail;

	@Column(name = "route_e_points")
	private String routeEPoints;

	@Column(name = "route_e_start_location_id")
	private Long routeEStartLocationId;

	@Column(name = "route_e_start_location_label_name")
	private String routeEStartLocationLabelName;

	@Column(name = "route_e_end_location_id")
	private Long routeEEndLocationId;

	@Column(name = "route_e_end_location_label_name")
	private String routeEEndLocationLabelName;

	@Column(name = "route_e_location_ids")
	private String routeELocationIds;

	public Nation getNation() {
		return new Nation.Builder()
				.withNationId(nationId)
				.withNationName(nationName)
				.withNationCode(nationCode)
				.withAltNationCode(altNationCode)
				.withRelatedPatronageId(nationRelatedPatronageId)
				.withIsOldWorld(isOldWorld)
				.build();
	}

	public Location getLocation() {
		return new Location.Builder()
				.withLocationId(locationId)
				.withAltLabelName(altLabelName)
				.withNationId(nationId)
				.withPointX(pointX)
				.withPointY(pointY)
				.withLabelName(labelName)
				.withLocationTypeId(locationTypeId)
				.withLocationTypeCode(locationTypeCode)
				//.withLocationTypeName(locationTypeName)
				.withPrevNationId(prevNationId)
				.withPrevLocationId(prevLocationId)
				.withPrevLocationLabelName(prevLocationLabelName)
				.withPrevLocationPointX(prevLocationPointX)
				.withPrevLocationPointY(prevLocationPointY)
				.withNextNationId(nextNationId)
				.withNextLocationId(nextLocationId)
				.withNextLocationLabelName(nextLocationLabelName)
				.withNextLocationPointX(nextLocationPointX)
				.withNextLocationPointY(nextLocationPointY)
				.withInsetId(insetId)
				.withAltInsetId(altInsetId)
				.withRelatedPatronageSubtypeId(locationRelatedPatronageSubtypeId)
				.withRelatedPatronageId(locationRelatedPatronageId)
				.withRouteAId(routeAId)
				.withRouteAName(routeAName)
				.withRouteBId(routeBId)
				.withRouteBName(routeBName)
				.withRouteCId(routeCId)
				.withRouteCName(routeCName)
				.withRouteDId(routeDId)
				.withRouteEName(routeDName)
				.withRouteEId(routeEId)
				.withRouteEName(routeEName)
				.withAllCommemorationCount(allCommemorationCount)
				.withCatholicCommemorationCount(catholicCommemorationCount)
				.withProtestantCommemorationCount(protestantCommemorationCount)
				.withOrthodoxCommemorationCount(orthodoxCommemorationCount)
				.withSunniCommemorationCount(sunniCommemorationCount)
				.withShiiteCommemorationCount(shiiteCommemorationCount)
				.withJewishCommemorationCount(jewishCommemorationCount)
				.build();
	}

	public Route getRouteA() {
		return new Route.Builder()
				.withRouteId(routeAId)
				.withRouteName(routeAName)
				.withRouteDetail(routeADetail)
				.withRoutePoints(routeAPoints)
				.withStartLocationId(routeAStartLocationId)
				.withStartLocationLabelName(routeAStartLocationLabelName)
				.withEndLocationId(routeAEndLocationId)
				.withEndLocationLabelName(routeAEndLocationLabelName)
				.withLocationIds(routeALocationIds)
				.build();
	}

	public Route getRouteB() {
		return new Route.Builder()
				.withRouteId(routeBId)
				.withRouteName(routeBName)
				.withRouteDetail(routeBDetail)
				.withRoutePoints(routeBPoints)
				.withStartLocationId(routeBStartLocationId)
				.withStartLocationLabelName(routeBStartLocationLabelName)
				.withEndLocationId(routeBEndLocationId)
				.withEndLocationLabelName(routeBEndLocationLabelName)
				.withLocationIds(routeBLocationIds)
				.build();
	}

	public Route getRouteC() {
		return new Route.Builder()
				.withRouteId(routeCId)
				.withRouteName(routeCName)
				.withRouteDetail(routeCDetail)
				.withRoutePoints(routeCPoints)
				.withStartLocationId(routeCStartLocationId)
				.withStartLocationLabelName(routeCStartLocationLabelName)
				.withEndLocationId(routeCEndLocationId)
				.withEndLocationLabelName(routeCEndLocationLabelName)
				.withLocationIds(routeCLocationIds)
				.build();
	}

	public Route getRouteD() {
		return new Route.Builder()
				.withRouteId(routeDId)
				.withRouteName(routeDName)
				.withRouteDetail(routeDDetail)
				.withRoutePoints(routeDPoints)
				.withStartLocationId(routeDStartLocationId)
				.withStartLocationLabelName(routeDStartLocationLabelName)
				.withEndLocationId(routeDEndLocationId)
				.withEndLocationLabelName(routeDEndLocationLabelName)
				.withLocationIds(routeDLocationIds)
				.build();
	}

	public Route getRouteE() {
		return new Route.Builder()
				.withRouteId(routeEId)
				.withRouteName(routeEName)
				.withRouteDetail(routeEDetail)
				.withRoutePoints(routeEPoints)
				.withStartLocationId(routeEStartLocationId)
				.withStartLocationLabelName(routeEStartLocationLabelName)
				.withEndLocationId(routeEEndLocationId)
				.withEndLocationLabelName(routeEEndLocationLabelName)
				.withLocationIds(routeELocationIds)
				.build();
	}
}
