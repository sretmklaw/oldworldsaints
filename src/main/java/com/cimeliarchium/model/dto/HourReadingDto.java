package com.cimeliarchium.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import com.cimeliarchium.model.dao.Hour;
import com.cimeliarchium.model.dao.HourType;
import com.cimeliarchium.model.dao.Reading;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "HourReadingMappingQuery", 
		procedureName = "select_hour_readings_for_cycle_day_by_rite", 
		resultClasses = { HourReadingDto.class }, 
		parameters = { 
			@StoredProcedureParameter(
				mode = ParameterMode.IN, 
				name = "_cycle_day_type_id", 
				type = Long.class),
			@StoredProcedureParameter(
				mode = ParameterMode.IN, 
				name = "_rite_id", 
				type = Long.class),
			@StoredProcedureParameter(
				mode = ParameterMode.IN, 
				name = "_is_cycle_day_liturgy_special", 
				type = Boolean.class)
		}) 
	})
public class HourReadingDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hour_reading_dto_id")
	private Long hourReadingDtoId;

	@Column(name = "hour_1_id")
	private Long hour1Id;

	@Column(name = "hour_1_type_id")
	private Long hour1TypeId;

	@Column(name = "hour_1_type_code")
	private String hour1TypeCode;

	@Column(name = "hour_1_start")
	private Integer hour1Start;

	@Column(name = "hour_1_end")
	private Integer hour1End;

	@Column(name = "hour_1_name_latin")
	private String hour1NameLatin;

	@Column(name = "hour_1_name_greek")
	private String hour1NameGreek;

	@Column(name = "hour_1_name_arabic")
	private String hour1NameArabic;

	@Column(name = "hour_1_name_hebrew")
	private String hour1NameHebrew;

	@Column(name = "hour_1_reading_a_id")
	private Long hour1ReadingAId;

	@Column(name = "hour_1_reading_a_name")
	private String hour1ReadingAName;

	@Column(name = "hour_1_reading_a_name_alt")
	private String hour1ReadingANameAlt;

	@Column(name = "hour_1_reading_a_content")
	private String hour1ReadingAContent;

	@Column(name = "hour_1_reading_b_id")
	private Long hour1ReadingBId;

	@Column(name = "hour_1_reading_b_name")
	private String hour1ReadingBName;

	@Column(name = "hour_1_reading_b_name_alt")
	private String hour1ReadingBNameAlt;

	@Column(name = "hour_1_reading_b_content")
	private String hour1ReadingBContent;

	@Column(name = "hour_1_reading_c_id")
	private Long hour1ReadingCId;

	@Column(name = "hour_1_reading_c_name")
	private String hour1ReadingCName;

	@Column(name = "hour_1_reading_c_name_alt")
	private String hour1ReadingCNameAlt;

	@Column(name = "hour_1_reading_c_content")
	private String hour1ReadingCContent;

	@Column(name = "hour_2_id")
	private Long hour2Id;

	@Column(name = "hour_2_type_id")
	private Long hour2TypeId;

	@Column(name = "hour_2_type_code")
	private String hour2TypeCode;

	@Column(name = "hour_2_start")
	private Integer hour2Start;

	@Column(name = "hour_2_end")
	private Integer hour2End;

	@Column(name = "hour_2_name_latin")
	private String hour2NameLatin;

	@Column(name = "hour_2_name_greek")
	private String hour2NameGreek;

	@Column(name = "hour_2_name_arabic")
	private String hour2NameArabic;

	@Column(name = "hour_2_name_hebrew")
	private String hour2NameHebrew;

	@Column(name = "hour_2_reading_a_id")
	private Long hour2ReadingAId;

	@Column(name = "hour_2_reading_a_name")
	private String hour2ReadingAName;

	@Column(name = "hour_2_reading_a_name_alt")
	private String hour2ReadingANameAlt;

	@Column(name = "hour_2_reading_a_content")
	private String hour2ReadingAContent;

	@Column(name = "hour_2_reading_b_id")
	private Long hour2ReadingBId;

	@Column(name = "hour_2_reading_b_name")
	private String hour2ReadingBName;

	@Column(name = "hour_2_reading_b_name_alt")
	private String hour2ReadingBNameAlt;

	@Column(name = "hour_2_reading_b_content")
	private String hour2ReadingBContent;

	@Column(name = "hour_2_reading_c_id")
	private Long hour2ReadingCId;

	@Column(name = "hour_2_reading_c_name")
	private String hour2ReadingCName;

	@Column(name = "hour_2_reading_c_name_alt")
	private String hour2ReadingCNameAlt;

	@Column(name = "hour_2_reading_c_content")
	private String hour2ReadingCContent;

	@Column(name = "hour_3_id")
	private Long hour3Id;

	@Column(name = "hour_3_type_id")
	private Long hour3TypeId;

	@Column(name = "hour_3_type_code")
	private String hour3TypeCode;

	@Column(name = "hour_3_start")
	private Integer hour3Start;

	@Column(name = "hour_3_end")
	private Integer hour3End;

	@Column(name = "hour_3_name_latin")
	private String hour3NameLatin;

	@Column(name = "hour_3_name_greek")
	private String hour3NameGreek;

	@Column(name = "hour_3_name_arabic")
	private String hour3NameArabic;

	@Column(name = "hour_3_name_hebrew")
	private String hour3NameHebrew;

	@Column(name = "hour_3_reading_a_id")
	private Long hour3ReadingAId;

	@Column(name = "hour_3_reading_a_name")
	private String hour3ReadingAName;

	@Column(name = "hour_3_reading_a_name_alt")
	private String hour3ReadingANameAlt;

	@Column(name = "hour_3_reading_a_content")
	private String hour3ReadingAContent;

	@Column(name = "hour_3_reading_b_id")
	private Long hour3ReadingBId;

	@Column(name = "hour_3_reading_b_name")
	private String hour3ReadingBName;

	@Column(name = "hour_3_reading_b_name_alt")
	private String hour3ReadingBNameAlt;

	@Column(name = "hour_3_reading_b_content")
	private String hour3ReadingBContent;

	@Column(name = "hour_3_reading_c_id")
	private Long hour3ReadingCId;

	@Column(name = "hour_3_reading_c_name")
	private String hour3ReadingCName;

	@Column(name = "hour_3_reading_c_name_alt")
	private String hour3ReadingCNameAlt;

	@Column(name = "hour_3_reading_c_content")
	private String hour3ReadingCContent;

	@Column(name = "hour_4_id")
	private Long hour4Id;

	@Column(name = "hour_4_type_id")
	private Long hour4TypeId;

	@Column(name = "hour_4_type_code")
	private String hour4TypeCode;

	@Column(name = "hour_4_start")
	private Integer hour4Start;

	@Column(name = "hour_4_end")
	private Integer hour4End;

	@Column(name = "hour_4_name_latin")
	private String hour4NameLatin;

	@Column(name = "hour_4_name_greek")
	private String hour4NameGreek;

	@Column(name = "hour_4_name_arabic")
	private String hour4NameArabic;

	@Column(name = "hour_4_name_hebrew")
	private String hour4NameHebrew;

	@Column(name = "hour_4_reading_a_id")
	private Long hour4ReadingAId;

	@Column(name = "hour_4_reading_a_name")
	private String hour4ReadingAName;

	@Column(name = "hour_4_reading_a_name_alt")
	private String hour4ReadingANameAlt;

	@Column(name = "hour_4_reading_a_content")
	private String hour4ReadingAContent;

	@Column(name = "hour_4_reading_b_id")
	private Long hour4ReadingBId;

	@Column(name = "hour_4_reading_b_name")
	private String hour4ReadingBName;

	@Column(name = "hour_4_reading_b_name_alt")
	private String hour4ReadingBNameAlt;

	@Column(name = "hour_4_reading_b_content")
	private String hour4ReadingBContent;

	@Column(name = "hour_4_reading_c_id")
	private Long hour4ReadingCId;

	@Column(name = "hour_4_reading_c_name")
	private String hour4ReadingCName;

	@Column(name = "hour_4_reading_c_name_alt")
	private String hour4ReadingCNameAlt;

	@Column(name = "hour_4_reading_c_content")
	private String hour4ReadingCContent;

	@Column(name = "hour_5_id")
	private Long hour5Id;

	@Column(name = "hour_5_type_id")
	private Long hour5TypeId;

	@Column(name = "hour_5_type_code")
	private String hour5TypeCode;

	@Column(name = "hour_5_start")
	private Integer hour5Start;

	@Column(name = "hour_5_end")
	private Integer hour5End;

	@Column(name = "hour_5_name_latin")
	private String hour5NameLatin;

	@Column(name = "hour_5_name_greek")
	private String hour5NameGreek;

	@Column(name = "hour_5_name_arabic")
	private String hour5NameArabic;

	@Column(name = "hour_5_name_hebrew")
	private String hour5NameHebrew;

	@Column(name = "hour_5_reading_a_id")
	private Long hour5ReadingAId;

	@Column(name = "hour_5_reading_a_name")
	private String hour5ReadingAName;

	@Column(name = "hour_5_reading_a_name_alt")
	private String hour5ReadingANameAlt;

	@Column(name = "hour_5_reading_a_content")
	private String hour5ReadingAContent;

	@Column(name = "hour_5_reading_b_id")
	private Long hour5ReadingBId;

	@Column(name = "hour_5_reading_b_name")
	private String hour5ReadingBName;

	@Column(name = "hour_5_reading_b_name_alt")
	private String hour5ReadingBNameAlt;

	@Column(name = "hour_5_reading_b_content")
	private String hour5ReadingBContent;

	@Column(name = "hour_5_reading_c_id")
	private Long hour5ReadingCId;

	@Column(name = "hour_5_reading_c_name")
	private String hour5ReadingCName;

	@Column(name = "hour_5_reading_c_name_alt")
	private String hour5ReadingCNameAlt;

	@Column(name = "hour_5_reading_c_content")
	private String hour5ReadingCContent;

	public Long getHourReadingDtoId() {
		return hourReadingDtoId;
	}

	public Hour getHour1() {
		return new Hour.Builder()
				.withHourId(hour1Id)
				.withHourType(new HourType.Builder()
						.withHourTypeId(hour1TypeId)
						.withHourTypeCode(hour1TypeCode)
						.withHourStart(hour1Start)
						.withHourEnd(hour1End)
						.withHourNameLatin(hour1NameLatin)
						.withHourNameGreek(hour1NameGreek)
						.withHourNameArabic(hour1NameArabic)
						.withHourNameHebrew(hour1NameHebrew)
						.build())
				.build();
	}

	public List<Reading> getHour1Readings() {
		List<Reading> hour1Readings = new ArrayList<>();
		hour1Readings.add(new Reading.Builder()
				.withReadingId(hour1ReadingAId)
				.withReadingName(hour1ReadingAName)
				.withReadingNameAlt(hour1ReadingANameAlt)
				.withReadingContent(hour1ReadingAContent)
				.build());
		hour1Readings.add(new Reading.Builder()
				.withReadingId(hour1ReadingBId)
				.withReadingName(hour1ReadingBName)
				.withReadingNameAlt(hour1ReadingBNameAlt)
				.withReadingContent(hour1ReadingBContent)
				.build());
		hour1Readings.add(new Reading.Builder()
				.withReadingId(hour1ReadingCId)
				.withReadingName(hour1ReadingCName)
				.withReadingNameAlt(hour1ReadingCNameAlt)
				.withReadingContent(hour1ReadingCContent)
				.build());
		return hour1Readings;
	}

	public Hour getHour2() {
		return new Hour.Builder()
				.withHourId(hour2Id)
				.withHourType(new HourType.Builder()
						.withHourTypeId(hour2TypeId)
						.withHourTypeCode(hour2TypeCode)
						.withHourStart(hour2Start)
						.withHourEnd(hour2End)
						.withHourNameLatin(hour2NameLatin)
						.withHourNameGreek(hour2NameGreek)
						.withHourNameArabic(hour2NameArabic)
						.withHourNameHebrew(hour2NameHebrew)
						.build())
				.build();
	}

	public List<Reading> getHour2Readings() {
		List<Reading> hour2Readings = new ArrayList<>();
		hour2Readings.add(new Reading.Builder()
				.withReadingId(hour2ReadingAId)
				.withReadingName(hour2ReadingAName)
				.withReadingNameAlt(hour2ReadingANameAlt)
				.withReadingContent(hour2ReadingAContent)
				.build());
		hour2Readings.add(new Reading.Builder()
				.withReadingId(hour2ReadingBId)
				.withReadingName(hour2ReadingBName)
				.withReadingNameAlt(hour2ReadingBNameAlt)
				.withReadingContent(hour2ReadingBContent)
				.build());
		hour2Readings.add(new Reading.Builder()
				.withReadingId(hour2ReadingCId)
				.withReadingName(hour2ReadingCName)
				.withReadingNameAlt(hour2ReadingCNameAlt)
				.withReadingContent(hour2ReadingCContent)
				.build());
		return hour2Readings;
	}

	public Hour getHour3() {
		return new Hour.Builder()
				.withHourId(hour3Id)
				.withHourType(new HourType.Builder()
						.withHourTypeId(hour3TypeId)
						.withHourTypeCode(hour3TypeCode)
						.withHourStart(hour3Start)
						.withHourEnd(hour3End)
						.withHourNameLatin(hour3NameLatin)
						.withHourNameGreek(hour3NameGreek)
						.withHourNameArabic(hour3NameArabic)
						.withHourNameHebrew(hour3NameHebrew)
						.build())
				.build();
	}

	public List<Reading> getHour3Readings() {
		List<Reading> hour3Readings = new ArrayList<>();
		hour3Readings.add(new Reading.Builder()
				.withReadingId(hour3ReadingAId)
				.withReadingName(hour3ReadingAName)
				.withReadingNameAlt(hour3ReadingANameAlt)
				.withReadingContent(hour3ReadingAContent)
				.build());
		hour3Readings.add(new Reading.Builder()
				.withReadingId(hour3ReadingBId)
				.withReadingName(hour3ReadingBName)
				.withReadingNameAlt(hour3ReadingBNameAlt)
				.withReadingContent(hour3ReadingBContent)
				.build());
		hour3Readings.add(new Reading.Builder()
				.withReadingId(hour3ReadingCId)
				.withReadingName(hour3ReadingCName)
				.withReadingNameAlt(hour3ReadingCNameAlt)
				.withReadingContent(hour3ReadingCContent)
				.build());
		return hour3Readings;
	}

	public Hour getHour4() {
		return new Hour.Builder()
				.withHourId(hour4Id)
				.withHourType(new HourType.Builder()
						.withHourTypeId(hour4TypeId)
						.withHourTypeCode(hour4TypeCode)
						.withHourStart(hour4Start)
						.withHourEnd(hour4End)
						.withHourNameLatin(hour4NameLatin)
						.withHourNameGreek(hour4NameGreek)
						.withHourNameArabic(hour4NameArabic)
						.withHourNameHebrew(hour4NameHebrew)
						.build())
				.build();
	}

	public List<Reading> getHour4Readings() {
		List<Reading> hour4Readings = new ArrayList<>();
		hour4Readings.add(new Reading.Builder()
				.withReadingId(hour4ReadingAId)
				.withReadingName(hour4ReadingAName)
				.withReadingNameAlt(hour4ReadingANameAlt)
				.withReadingContent(hour4ReadingAContent)
				.build());
		hour4Readings.add(new Reading.Builder()
				.withReadingId(hour4ReadingBId)
				.withReadingName(hour4ReadingBName)
				.withReadingNameAlt(hour4ReadingBNameAlt)
				.withReadingContent(hour4ReadingBContent)
				.build());
		hour4Readings.add(new Reading.Builder()
				.withReadingId(hour4ReadingCId)
				.withReadingName(hour4ReadingCName)
				.withReadingNameAlt(hour4ReadingCNameAlt)
				.withReadingContent(hour4ReadingCContent)
				.build());
		return hour4Readings;
	}

	public Hour getHour5() {
		return new Hour.Builder()
				.withHourId(hour5Id)
				.withHourType(new HourType.Builder()
						.withHourTypeId(hour5TypeId)
						.withHourTypeCode(hour5TypeCode)
						.withHourStart(hour5Start)
						.withHourEnd(hour5End)
						.withHourNameLatin(hour5NameLatin)
						.withHourNameGreek(hour5NameGreek)
						.withHourNameArabic(hour5NameArabic)
						.withHourNameHebrew(hour5NameHebrew)
						.build())
				.build();
	}

	public List<Reading> getHour5Readings() {
		List<Reading> hour5Readings = new ArrayList<>();
		hour5Readings.add(new Reading.Builder()
				.withReadingId(hour5ReadingAId)
				.withReadingName(hour5ReadingAName)
				.withReadingNameAlt(hour5ReadingANameAlt)
				.withReadingContent(hour5ReadingAContent)
				.build());
		hour5Readings.add(new Reading.Builder()
				.withReadingId(hour5ReadingBId)
				.withReadingName(hour5ReadingBName)
				.withReadingNameAlt(hour5ReadingBNameAlt)
				.withReadingContent(hour5ReadingBContent)
				.build());
		hour5Readings.add(new Reading.Builder()
				.withReadingId(hour5ReadingCId)
				.withReadingName(hour5ReadingCName)
				.withReadingNameAlt(hour5ReadingCNameAlt)
				.withReadingContent(hour5ReadingCContent)
				.build());
		return hour5Readings;
	}
}
