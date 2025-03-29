package com.cimeliarchium.model.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import com.cimeliarchium.model.dao.CycleDay;
import com.cimeliarchium.model.dao.LiturgyType;
import com.cimeliarchium.model.dao.Star;
import com.cimeliarchium.model.dao.Constellation;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "CycleDayLiturgyTypeMappingQuery", 
		procedureName = "select_cycle_day_liturgy_type_for_date", 
		resultClasses = { CycleDayLiturgyTypeDto.class }, 
		parameters = { 
			@StoredProcedureParameter(
				mode = ParameterMode.IN, 
				name = "_target_date", 
				type = Date.class)
		}) 
	})
public class CycleDayLiturgyTypeDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cycle_day_liturgy_type_dto_id")
	private Long cycleDayLiturgyTypeDtoId;

	@Column(name = "cycle_day_type_id")
	private Long cycleDayTypeId;

	@Column(name = "is_cycle_day_liturgy_special")
	private Boolean isCycleDayLiturgySpecial;

	@Column(name = "cycle_day_name_latin")
	private String cycleDayNameLatin;

	@Column(name = "cycle_day_name_greek")
	private String cycleDayNameGreek;

	@Column(name = "cycle_day_name_arabic")
	private String cycleDayNameArabic;

	@Column(name = "cycle_day_name_hebrew")
	private String cycleDayNameHebrew;

	@Column(name = "liturgy_type_id_catholic")
	private Long liturgyTypeIdCatholic;

	@Column(name = "liturgy_name_catholic")
	private String liturgyNameCatholic;

	@Column(name = "liturgy_details_catholic")
	private String liturgyDetailsCatholic;

	@Column(name = "liturgy_type_id_orthodox")
	private Long liturgyTypeIdOrthodox;

	@Column(name = "liturgy_name_orthodox")
	private String liturgyNameOrthodox;

	@Column(name = "liturgy_details_orthodox")
	private String liturgyDetailsOrthodox;

	@Column(name = "liturgy_type_id_islamic")
	private Long liturgyTypeIdIslamic;

	@Column(name = "liturgy_name_islamic")
	private String liturgyNameIslamic;

	@Column(name = "liturgy_details_islamic")
	private String liturgyDetailsIslamic;

	@Column(name = "liturgy_type_id_jewish")
	private Long liturgyTypeIdJewish;

	@Column(name = "liturgy_name_jewish")
	private String liturgyNameJewish;

	@Column(name = "liturgy_details_jewish")
	private String liturgyDetailsJewish;

	@Column(name = "zodiac_sign_name_latin")
	private String zodiacSignNameLatin;

	@Column(name = "zodiac_sign_name_greek")
	private String zodiacSignNameGreek;

	@Column(name = "zodiac_sign_name_arabic")
	private String zodiacSignNameArabic;

	@Column(name = "zodiac_sign_name_hebrew")
	private String zodiacSignNameHebrew;

	@Column(name = "zodiac_sign_code")
	private String zodiacSignCode;

	@Column(name = "zodiac_sign_detail")
	private String zodiacSignDetail;

	@Column(name = "zodiac_sign_detail_jewish")
	private String zodiacSignDetailJewish;

	@Column(name = "zodiac_sign_detail_christian")
	private String zodiacSignDetailChristian;

	@Column(name = "zodiac_sign_detail_islamic")
	private String zodiacSignDetailIslamic;

	@Column(name = "commemoration_id_1")
	private Long commemorationId1;

	@Column(name = "commemoration_name_1")
	private String commemorationName1;

	@Column(name = "commemoration_id_2")
	private Long commemorationId2;

	@Column(name = "commemoration_name_2")
	private String commemorationName2;

	@Column(name = "commemoration_id_3")
	private Long commemorationId3;

	@Column(name = "commemoration_name_3")
	private String commemorationName3;

	@Column(name = "commemoration_id_4")
	private Long commemorationId4;

	@Column(name = "commemoration_name_4")
	private String commemorationName4;

	@Column(name = "commemoration_id_5")
	private Long commemorationId5;

	@Column(name = "commemoration_name_5")
	private String commemorationName5;

	@Column(name = "star_id_primary")
	private Long starIdPrimary;

	@Column(name = "star_name_primary")
	private String starNamePrimary;

	@Column(name = "star_id_secondary")
	private Long starIdSecondary;

	@Column(name = "star_name_secondary")
	private String starNameSecondary;

	@Column(name = "culminating_star_constellation_id")
	private Long culminatingStarConstellationId;

	@Column(name = "culminating_star_id")
	private Long culminatingStarId;

	@Column(name = "culminating_star_name")
	private String culminatingStarName;

	@Column(name = "culminating_star_name_alt")
	private String culminatingStarNameAlt;

	@Column(name = "culminating_star_detail")
	private String culminatingStarDetail;

	public CycleDay getCycleDay() {
		return new CycleDay.Builder()
				.withCycleDayLiturgyTypeDtoId(cycleDayLiturgyTypeDtoId)
				.withCycleDayTypeId(cycleDayTypeId)
				.withCycleDayName(cycleDayNameLatin)
				.withIsCycleDayLiturgySpecial(isCycleDayLiturgySpecial)
				.build();
	}

	public LiturgyType getLiturgyTypeCatholic() {
		return new LiturgyType.Builder()
				.withLiturgyTypeId(liturgyTypeIdCatholic)
				.withLiturgyName(liturgyNameCatholic)
				.withLiturgyDetails(liturgyDetailsCatholic)
				.withCycleDayName(cycleDayNameLatin)
				.build();
	}

	public LiturgyType getLiturgyTypeOrthodox() {
		return new LiturgyType.Builder()
				.withLiturgyTypeId(liturgyTypeIdOrthodox)
				.withLiturgyName(liturgyNameOrthodox)
				.withLiturgyDetails(liturgyDetailsOrthodox)
				.withCycleDayName(cycleDayNameGreek)
				.build();
	}

	public LiturgyType getLiturgyTypeIslamic() {
		return new LiturgyType.Builder()
				.withLiturgyTypeId(liturgyTypeIdIslamic)
				.withLiturgyName(liturgyNameIslamic)
				.withLiturgyDetails(liturgyDetailsIslamic)
				.withCycleDayName(cycleDayNameArabic)
				.build();
	}

	public LiturgyType getLiturgyTypeJewish() {
		return new LiturgyType.Builder()
				.withLiturgyTypeId(liturgyTypeIdJewish)
				.withLiturgyName(liturgyNameJewish)
				.withLiturgyDetails(liturgyDetailsJewish)
				.withCycleDayName(cycleDayNameHebrew)
				.build();
	}

	public Constellation getZodiacSign() {
		return new Constellation.Builder()
				.withConstellationNameLatin(zodiacSignNameLatin)
				.withConstellationNameGreek(zodiacSignNameGreek)
				.withConstellationNameArabic(zodiacSignNameArabic)
				.withConstellationNameHebrew(zodiacSignNameHebrew)
				.withConstellationCode(zodiacSignCode)
				.withConstellationDetail(zodiacSignDetail)
				.withConstellationDetailJewish(zodiacSignDetailJewish)
				.withConstellationDetailChristian(zodiacSignDetailChristian)
				.withConstellationDetailIslamic(zodiacSignDetailIslamic)
				.withCommemorationId1(commemorationId1)
				.withCommemorationName1(commemorationName1)
				.withCommemorationId2(commemorationId2)
				.withCommemorationName2(commemorationName2)
				.withCommemorationId3(commemorationId3)
				.withCommemorationName3(commemorationName3)
				.withCommemorationId4(commemorationId4)
				.withCommemorationName4(commemorationName4)
				.withCommemorationId5(commemorationId5)
				.withCommemorationName5(commemorationName5)
				.withStarIdPrimary(starIdPrimary)
				.withStarNamePrimary(starNamePrimary)
				.withStarIdSecondary(starIdSecondary)
				.withStarNameSecondary(starNameSecondary)
				.withIsZodiacSign(true)
				.build();
	}

	public Star getCulminatingStar() {
		return new Star.Builder()
				.withConstellationId(culminatingStarConstellationId)
				.withStarId(culminatingStarId)
				.withStarName(culminatingStarName)
				.withStarNameAlt(culminatingStarNameAlt)
				.withStarDetail(culminatingStarDetail)
				.build();
	}
}
