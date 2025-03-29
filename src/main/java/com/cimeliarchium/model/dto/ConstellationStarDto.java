package com.cimeliarchium.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.Constellation;
import com.cimeliarchium.model.dao.Star;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "ConstellationStarMappingQuery", 
		procedureName = "select_stars_by_constellation", 
		resultClasses = { ConstellationStarDto.class })
	})
public class ConstellationStarDto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "constellation_star_dto_id")
	private Long constellationStarDtoId;

	@Column(name = "constellation_id")
	private Long constellationId;

	@Column(name = "constellation_name")
	private String constellationName;
	
	@Column(name = "star_id_primary")
	private Long starIdPrimary;

	@Column(name = "asterism_star_ids")
	private String asterismStarIds;

	@Column(name = "asterism_points")
	private String asterismPoints;

	@Column(name = "is_zodiac_sign")
	private Boolean isZodiacSign;

	@Column(name = "star_id")
	private Long starId;

	@Column(name = "star_name")
	private String starName;

	@Column(name = "star_name_alt")
	private String starNameAlt;

	@Column(name = "star_culmination_day_id")
	private Long starCulminationDayId;

	@Column(name = "star_culmination_day_name")
	private String starCulminationDayName;

	@Column(name = "star_point_x")
	private Double starPointX;

	@Column(name = "star_point_y")
	private Double starPointY;

	@Column(name = "star_type_id")
	private Long starTypeId;

	@Column(name = "star_type_code")
	private String starTypeCode;

	@Column(name = "star_detail")
	private String starDetail;

	@Column(name = "star_reference_start")
	private Long starReferenceStart;

	@Column(name = "star_reference_end")
	private Long starReferenceEnd;

	@Column(name = "last_star_id")
	private Long lastStarId;

	@Column(name = "last_star_name")
	private String lastStarName;

	@Column(name = "next_star_id")
	private Long nextStarId;

	@Column(name = "next_star_name")
	private String nextStarName;

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

	public Constellation getConstellation() {
		return new Constellation.Builder()
				.withConstellationId(constellationId)
				.withConstellationNameLatin(constellationName)
				.withStarIdPrimary(starIdPrimary)
				.withAsterismStarIds(asterismStarIds)
				.withAsterismPoints(asterismPoints)
				.withIsZodiacSign(isZodiacSign)
				.build();
	}

	public Star getStar() {
		return new Star.Builder()
				.withConstellationId(constellationId)
				.withStarId(starId)
				.withStarName(starName)
				.withStarNameAlt(starNameAlt)
				.withCulminationDayId(starCulminationDayId)
				.withCulminationDayName(starCulminationDayName)
				.withPointX(starPointX)
				.withPointY(starPointY)
				.withStarTypeId(starTypeId)
				.withStarTypeCode(starTypeCode)
				.withStarDetail(starDetail)
				.withReferenceStart(starReferenceStart)
				.withReferenceEnd(starReferenceEnd)
				.withLastStarId(lastStarId)
				.withLastStarName(lastStarName)
				.withNextStarId(nextStarId)
				.withNextStarName(nextStarName)
				.withAllCommemorationCount(allCommemorationCount)
				.withCatholicCommemorationCount(catholicCommemorationCount)
				.withProtestantCommemorationCount(protestantCommemorationCount)
				.withOrthodoxCommemorationCount(orthodoxCommemorationCount)
				.withSunniCommemorationCount(sunniCommemorationCount)
				.withShiiteCommemorationCount(shiiteCommemorationCount)
				.withJewishCommemorationCount(jewishCommemorationCount)
				.build();
	}
}
