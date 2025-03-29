package com.cimeliarchium.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.Nation;
import com.cimeliarchium.model.dao.Region;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "NationRegionMappingQuery", 
		procedureName = "select_regions_by_nation", 
		resultClasses = { NationRegionDto.class })
	})
public class NationRegionDto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nation_region_dto_id")
	private Long nationRegionDtoId;

	@Column(name = "nation_id")
	private Long nationId;

	@Column(name = "nation_name")
	private String nationName;

	@Column(name = "nation_code")
	private String nationCode;

	@Column(name = "alt_nation_code")
	private String altNationCode;

	@Column(name = "is_old_world")
	private Boolean isOldWorld;

	@Column(name = "region_id")
	private Long regionId;

	@Column(name = "region_name")
	private String regionName;

	@Column(name = "region_code")
	private String regionCode;

	@Column(name = "utc_offset")
	private String utcOffset;

	@Column(name = "is_northern_hemisphere")
	private Boolean isNorthernHemisphere;

	@Column(name = "dst_start_month")
	private Integer dstStartMonth;

	@Column(name = "dst_start_day")
	private Integer dstStartDay;

	@Column(name = "dst_start_dow")
	private Integer dstStartDow;

	@Column(name = "dst_start_week")
	private Integer dstStartWeek;

	@Column(name = "dst_end_month")
	private Integer dstEndMonth;

	@Column(name = "dst_end_day")
	private Integer dstEndDay;

	@Column(name = "dst_end_dow")
	private Integer dstEndDow;

	@Column(name = "dst_end_week")
	private Integer dstEndWeek;

	public Nation getNation() {
		return new Nation.Builder()
				.withNationId(nationId)
				.withNationName(nationName)
				.withNationCode(nationCode)
				.withAltNationCode(altNationCode)
				.withIsOldWorld(isOldWorld)
				.build();
	}

	public Region getRegion() {
		return new Region.Builder()
				.withRegionId(regionId)
				.withNationId(nationId)
				.withRegionName(regionName)
				.withRegionCode(regionCode)
				.withUtcOffset(utcOffset)
				.withIsNorthernHemisphere(isNorthernHemisphere)
				.withDstStartDay(dstStartDay)
				.withDstStartDow(dstStartDow)
				.withDstStartMonth(dstStartMonth)
				.withDstStartWeek(dstStartWeek)
				.withDstEndDay(dstEndDay)
				.withDstEndDow(dstEndDow)
				.withDstEndMonth(dstEndMonth)
				.withDstEndWeek(dstEndWeek)
				.build();
	}
}
