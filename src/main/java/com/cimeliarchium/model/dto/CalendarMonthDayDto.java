package com.cimeliarchium.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Day;
import com.cimeliarchium.model.dao.Month;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "CalendarMonthDayMappingQuery", 
		procedureName = "select_days_months_by_calendar", 
		resultClasses = { CalendarMonthDayDto.class })
	})
public class CalendarMonthDayDto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "calendar_month_day_dto_id")
	private Long nationLocationDtoId;

	@Column(name = "calendar_id")
	private Long calendarId;

	@Column(name = "calendar_name")
	private String calendarName;

	@Column(name = "calendar_detail")
	private String calendarDetail;

	@Column(name = "calendar_code")
	private String calendarCode;

	@Column(name = "month_id")
	private Long monthId;

	@Column(name = "month_name")
	private String monthName;

	@Column(name = "day_id")
	private Long dayId;

	@Column(name = "day_of_month")
	private String dayOfMonth;

	public Calendar getCalendar() {
		return new Calendar.Builder()
				.withCalendarId(calendarId)
				.withCalendarName(calendarName)
				.withCalendarDetail(calendarDetail)
				.withCalendarCode(calendarCode)
				.build();
	}

	public Month getMonth() {
		return new Month.Builder()
				.withMonthId(monthId)
				.withMonthName(monthName)
				.withCalendarId(calendarId)
				.build();
	}

	public Day getDay() {
		return new Day.Builder()
				.withDayId(dayId)
				.withDayOfMonth(dayOfMonth)
				.withMonthId(monthId)
				.withMonthName(monthName)
				.withCalendarId(calendarId)
				.build();
	}
}
