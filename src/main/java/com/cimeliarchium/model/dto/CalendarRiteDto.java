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

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Rite;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "CalendarRiteMappingQuery", 
		procedureName = "select_rites_by_calendar", 
		resultClasses = { CalendarRiteDto.class })
	})
public class CalendarRiteDto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "calendar_rite_dto_id")
	private Long calendarRiteDtoId;

	@Column(name = "calendar_id")
	private Long calendarId;

	@Column(name = "calendar_name")
	private String calendarName;

	@Column(name = "calendar_detail")
	private String calendarDetail;

	@Column(name = "calendar_code")
	private String calendarCode;

	@Column(name = "rite_id")
	private Long riteId;

	@Column(name = "rite_name")
	private String riteName;

	@Column(name = "rite_detail")
	private String riteDetail;

	@Column(name = "rite_code")
	private String riteCode;

	@Column(name = "creed_a_id")
	private Long creedAId;

	@Column(name = "creed_b_id")
	private Long creedBId;

	@Column(name = "creed_c_id")
	private Long creedCId;

	@Column(name = "creed_d_id")
	private Long creedDId;

	@Column(name = "creed_e_id")
	private Long creedEId;

	@Column(name = "creed_f_id")
	private Long creedFId;

	@Column(name = "creed_g_id")
	private Long creedGId;

	@Column(name = "creed_h_id")
	private Long creedHId;

	@Column(name = "creed_i_id")
	private Long creedIId;

	public Long getCalendarId() {
		return calendarId;
	}

	public Calendar getCalendar() {
		return new Calendar.Builder()
				.withCalendarId(calendarId)
				.withCalendarName(calendarName)
				.withCalendarDetail(calendarDetail)
				.withCalendarCode(calendarCode)
				.build();
	}

	public Long getRiteId() {
		return riteId;
	}

	public Rite getRite() {

		List<Long> creedIds = new ArrayList<>();
		creedIds.add(creedAId);
		creedIds.add(creedBId);
		creedIds.add(creedCId);
		creedIds.add(creedDId);
		creedIds.add(creedEId);
		creedIds.add(creedFId);
		creedIds.add(creedGId);
		creedIds.add(creedHId);
		creedIds.add(creedIId);

		return new Rite.Builder()
				.withRiteId(riteId)
				.withCalendarId(calendarId)
				.withRiteName(riteName)
				.withRiteDetail(riteDetail)
				.withRiteCode(riteCode)
				.withCalendarName(calendarName)
				.withCreedIds(creedIds)
				.build();
	}
}
