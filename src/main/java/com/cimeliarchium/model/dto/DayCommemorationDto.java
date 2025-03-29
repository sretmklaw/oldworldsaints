package com.cimeliarchium.model.dto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import com.cimeliarchium.enums.EpochEnum;
import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.CalendarAltReason;
import com.cimeliarchium.model.dao.CalendarAltType;
import com.cimeliarchium.model.dao.Century;
import com.cimeliarchium.model.dao.Commemoration;
import com.cimeliarchium.model.dao.CommemorationType;
import com.cimeliarchium.model.dao.Constellation;
import com.cimeliarchium.model.dao.Creed;
import com.cimeliarchium.model.dao.Day;
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.LocationType;
import com.cimeliarchium.model.dao.Nation;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.PatronageSubtype;
import com.cimeliarchium.model.dao.Reading;
import com.cimeliarchium.model.dao.Reference;
import com.cimeliarchium.model.dao.Route;
import com.cimeliarchium.model.dao.Star;
import com.cimeliarchium.model.dao.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "DayCommemorationMappingQuery", 
		procedureName = "select_day_commemorations", 
		resultClasses = { DayCommemorationDto.class })
	})
public class DayCommemorationDto extends BaseEntity implements Comparable<DayCommemorationDto> {

	private static final String TIME_IMMEMORIAL = "Time Immemorial";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "day_commemoration_dto_id")
	private Long dayCommemorationDtoId;

	/**
	 * Map by Nation key fields
	 */
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

	@Column(name = "nation_is_old_world")
	private Boolean nationIsOldWorld;

	/**
	 * Map by Century key fields
	 */
	@Column(name = "century_id")
	private Long centuryId;

	@Column(name = "century_name_ad")
	private String centuryNameAd;

	@Column(name = "century_name_ah")
	private String centuryNameAh;

	@Column(name = "century_name_am")
	private String centuryNameAm;

	/**
	 * Map by Patronage key fields
	 */
	@Column(name = "patronage_id")
	private Long patronageId;

	@Column(name = "patronage_name")
	private String patronageName;

	@Column(name = "patronage_related_search_link")
	private String patronageRelatedSearchLink;

	@Column(name = "patronage_point_x")
	private Double patronagePointX;

	@Column(name = "patronage_point_y")
	private Double patronagePointY;

	@Column(name = "patronage_last_update_time")
	private LocalDate patronageLastUpdateTime;

	/**
	 * Map by Location key fields
	 */
	@Column(name = "location_id")
	private Long locationId;

	@Column(name = "location_label_name")
	private String locationLabelName;

	@Column(name = "location_type_id")
	private Long locationTypeId;

	@Column(name = "location_type_name")
	private String locationTypeName;

	@Column(name = "location_type_code")
	private String locationTypeCode;

	@Column(name = "location_point_x")
	private Double locationPointX;

	@Column(name = "location_point_y")
	private Double locationPointY;

	@Column(name = "location_related_patronage_subtype_id")
	private Long locationRelatedPatronageSubtypeId;

	@Column(name = "location_related_patronage_subtype_name")
	private String locationRelatedPatronageSubtypeName;

	@Column(name = "location_related_patronage_id")
	private Long locationRelatedPatronageId;

	@Column(name = "location_related_patronage_name")
	private String locationRelatedPatronageName;

	@Column(name = "location_related_patronage_point_x")
	private Double locationRelatedPatronagePointX;

	@Column(name = "location_related_patronage_point_y")
	private Double locationRelatedPatronagePointY;

	@Column(name = "location_alt_inset_id")
	private Long locationAltInsetId;

	@Column(name = "location_alt_label_name")
	private String locationAltLabelName;

	@Column(name = "location_all_commemoration_count")
	private Long locationAllCommemorationCount;

	@Column(name = "next_nation_id")
	private Long nextNationId;

	@Column(name = "next_location_id")
	private Long nextLocationId;

	@Column(name = "next_location_label_name")
	private String nextLocationLabelName;

	@Column(name = "prev_nation_id")
	private Long prevNationId;

	@Column(name = "prev_location_id")
	private Long prevLocationId;

	@Column(name = "prev_location_label_name")
	private String prevLocationLabelName;

	@Column(name = "location_last_update_time")
	private LocalDate locationLastUpdateTime;

	@Column(name = "alt_location_id")
	private Long altLocationId;

	@Column(name = "alt_nation_id")
	private Long altNationId;

	@Column(name = "alt_location_label_name")
	private String altLocationLabelName;

	@Column(name = "alt_location_type_id")
	private Long altLocationTypeId;

	@Column(name = "alt_location_type_name")
	private String altLocationTypeName;

	@Column(name = "alt_location_type_code")
	private String altLocationTypeCode;

	@Column(name = "alt_location_point_x")
	private Double altLocationPointX;

	@Column(name = "alt_location_point_y")
	private Double altLocationPointY;

	@Column(name = "alt_location_related_patronage_subtype_id")
	private Long altLocationRelatedPatronageSubtypeId;

	@Column(name = "alt_location_related_patronage_subtype_name")
	private String altLocationRelatedPatronageSubtypeName;

	@Column(name = "alt_location_related_patronage_id")
	private Long altLocationRelatedPatronageId;

	@Column(name = "alt_location_related_patronage_name")
	private String altLocationRelatedPatronageName;

	@Column(name = "alt_location_related_patronage_point_x")
	private Double altLocationRelatedPatronagePointX;

	@Column(name = "alt_location_related_patronage_point_y")
	private Double altLocationRelatedPatronagePointY;

	@Column(name = "alt_location_inset_id")
	private Long altLocationInsetId;

	@Column(name = "alt_next_location_id")
	private Long altNextLocationId;

	@Column(name = "alt_next_location_label_name")
	private String altNextLocationLabelName;

	@Column(name = "alt_prev_location_id")
	private Long altPrevLocationId;

	@Column(name = "alt_prev_location_label_name")
	private String altPrevLocationLabelName;

	@Column(name = "alt_location_last_update_time")
	private LocalDate altLocationLastUpdateTime;

	@Column(name = "alt_location_all_commemoration_count")
	private Long altLocationAllCommemorationCount;

	@Column(name = "route_a_id")
	private Long routeAId;

	@Column(name = "route_a_name")
	private String routeAName;

	@Column(name = "route_b_id")
	private Long routeBId;

	@Column(name = "route_b_name")
	private String routeBName;

	@Column(name = "route_c_id")
	private Long routeCId;

	@Column(name = "route_c_name")
	private String routeCName;

	@Column(name = "route_d_id")
	private Long routeDId;

	@Column(name = "route_d_name")
	private String routeDName;

	@Column(name = "route_e_id")
	private Long routeEId;

	@Column(name = "route_e_name")
	private String routeEName;

	/**
	 * Map by Constellation key fields
	 */
	@Column(name = "constellation_id")
	private Long constellationId;

	@Column(name = "constellation_name")
	private String constellationName;

	/**
	 * Map by Star key fields
	 */
	@Column(name = "star_id")
	private Long starId;

	@Column(name = "star_name")
	private String starName;

	@Column(name = "star_name_alt")
	private String starNameAlt;

	/**
	 * Mapped field values for all keys
	 */

	/**
	 * Commemoration
	 */
	@Column(name = "commemoration_id")
	private Long commemorationId;

	@Column(name = "commemoration_name")
	private String commemorationName;

	@Column(name = "commemoration_alt_name")
	private String commemorationAltName;

	@Column(name = "commemoration_detail")
	private String commemorationDetail;

	@Column(name = "commemoration_year_ad")
	private String commemorationYearAd;

	@Column(name = "commemoration_year_ah")
	private String commemorationYearAh;

	@Column(name = "commemoration_year_am")
	private String commemorationYearAm;

	@Column(name = "contributors")
	private String contributors;

	@Column(name = "commemoration_last_update_time")
	private LocalDate commemorationLastUpdateTime;

	@Column(name = "has_last_contributor_been_notified")
	private Boolean hasLastContributorBeenNotified;

	/**
	 * CommemorationType
	 */
	@Column(name = "commemoration_type_id")
	private Long commemorationTypeId;

	@Column(name = "commemoration_type_name")
	private String commemorationTypeName;

	@Column(name = "commemoration_type_name_alt")
	private String commemorationTypeNameAlt;

	/**
	 * Creed
	 */
	@Column(name = "creed_id")
	private Long creedId;

	@Column(name = "creed_name")
	private String creedName;

	/**
	 * CalendarAltType
	 */
	@Column(name = "calendar_alt_type_id")
	private Long calendarAltTypeId;

	@Column(name = "calendar_alt_type_name")
	private String calendarAltTypeName;

	/**
	 * CalendarAltReason
	 */
	@Column(name = "calendar_alt_reason_id")
	private Long calendarAltReasonId;

	@Column(name = "calendar_alt_reason_name")
	private String calendarAltReasonName;

	/**
	 * Calendar
	 */
	@Column(name = "calendar_id")
	private Long calendarId;

	@Column(name = "calendar_code")
	private String calendarCode;

	/**
	 * Month
	 */
	@Column(name = "month_id")
	private Long monthId;

	@Column(name = "month_name")
	private String monthName;

	@Column(name = "alt_month_ad_id")
	private Long altMonthAdId;

	@Column(name = "alt_month_name_ad")
	private String altMonthNameAd;

	@Column(name = "alt_month_ah_id")
	private Long altMonthAhId;

	@Column(name = "alt_month_name_ah")
	private String altMonthNameAh;

	@Column(name = "alt_month_am_id")
	private Long altMonthAmId;

	@Column(name = "alt_month_name_am")
	private String altMonthNameAm;

	/**
	 * Day
	 */
	@Column(name = "day_id")
	private Long dayId;

	@Column(name = "day_of_month")
	private String dayOfMonth;

	@Column(name = "alt_day_ad_id")
	private Long altDayAdId;

	@Column(name = "alt_day_of_month_ad")
	private String altDayOfMonthAd;

	@Column(name = "alt_day_ah_id")
	private Long altDayAhId;

	@Column(name = "alt_day_of_month_ah")
	private String altDayOfMonthAh;

	@Column(name = "alt_day_am_id")
	private Long altDayAmId;

	@Column(name = "alt_day_of_month_am")
	private String altDayOfMonthAm;

	/**
	 * PatronageType
	 */
	@Column(name = "patronage_type_id")
	private Long patronageTypeId;

	@Column(name = "patronage_type_name")
	private String patronageTypeName;

	/**
	 * PatronageSubtype
	 */
	@Column(name = "patronage_subtype_id")
	private Long patronageSubtypeId;

	@Column(name = "patronage_subtype_name")
	private String patronageSubtypeName;

	@Column(name = "patronage_subtype_code")
	private String patronageSubtypeCode;

	/**
	 * Reference
	 */
	@Column(name = "reference_id")
	private Long referenceId;

	@Column(name = "reference_name")
	private String referenceName;

	@Column(name = "reference_volume")
	private Long referenceVolume;

	@Column(name = "reference_start")
	private Long referenceStart;

	@Column(name = "reference_end")
	private Long referenceEnd;

	/**
	 * Reading
	 */
	@Column(name = "reading_id")
	private Long readingId;

	@Column(name = "reading_content")
	private String readingContent;

	@Column(name = "reading_name")
	private String readingName;

	@Column(name = "reading_name_alt")
	private String readingNameAlt;

	@Column(name = "reading_last_update_time")
	private LocalDate readingLastUpdateTime;

	/**
	 * Tag
	 */
	@Column(name = "tag_a_id")
	private Long tagAId;

	@Column(name = "tag_a_name")
	private String tagAName;

	@Column(name = "tag_b_id")
	private Long tagBId;

	@Column(name = "tag_b_name")
	private String tagBName;

	@Column(name = "tag_c_id")
	private Long tagCId;

	@Column(name = "tag_c_name")
	private String tagCName;

	@Column(name = "tag_d_id")
	private Long tagDId;

	@Column(name = "tag_d_name")
	private String tagDName;

	@Column(name = "tag_e_id")
	private Long tagEId;

	@Column(name = "tag_e_name")
	private String tagEName;

	/**
	 * Getter Methods
	 */

	public Long getDayCommemorationDtoId() {
		return dayCommemorationDtoId;
	}

	public String getContributors() {
		return contributors;
	}

	public Commemoration getCommemoration() {
		return new Commemoration.Builder()
				.withCommemorationId(commemorationId)
				.withDayId(dayId)
				.withAltDayAdId(altDayAdId)
				.withAltDayAhId(altDayAhId)
				.withAltDayAmId(altDayAmId)
				.withCreedId(creedId)
				.withCalendarAltTypeId(calendarAltTypeId)
				.withCalendarAltReasonId(calendarAltReasonId)
				.withCommemorationTypeId(commemorationTypeId)
				.withCommemorationName(commemorationName)
				.withCommemorationAltName(commemorationAltName)
				.withCommemorationYearAd(commemorationYearAd)
				.withCommemorationYearAh(commemorationYearAh)
				.withCommemorationYearAm(commemorationYearAm)
				.withCommemorationDetail(commemorationDetail)
				.withLocationId(locationId)
				.withAltLocationId(altLocationId)
				.withPatronageId(patronageId)
				.withCenturyId(centuryId)
				.withReadingId(readingId)
				.withTagAId(tagAId)
				.withTagBId(tagBId)
				.withTagCId(tagCId)
				.withTagDId(tagDId)
				.withTagEId(tagEId)
				.withReferenceId(referenceId)
				.withReferenceStart(referenceStart)
				.withReferenceEnd(referenceEnd)
				.withStarId(starId)
				.withContributors(contributors)
				.withLastUpdateTime(commemorationLastUpdateTime)
				.withHasLastContributorBeenNotified(hasLastContributorBeenNotified)
				.build();
	}

	public CommemorationType getCommemorationType() {
		return new CommemorationType.Builder()
				.withCommemorationTypeId(commemorationTypeId)
				.withCommemorationTypeName(commemorationTypeName)
				.withCommemorationTypeNameAlt(commemorationTypeNameAlt)
				.build();
	}

	public Creed getCreed() {
		return new Creed.Builder()
				.withCreedId(creedId)
				.withCreedName(creedName)
				.build();
	}

	public CalendarAltType getCalendarAltType() {
		return new CalendarAltType.Builder()
				.withCalendarAltTypeId(calendarAltTypeId)
				.withCalendarAltTypeName(calendarAltTypeName)
				.build();
	}

	public CalendarAltReason getCalendarAltReason() {
		return new CalendarAltReason.Builder()
				.withCalendarAltReasonId(calendarAltReasonId)
				.withCalendarAltReasonName(calendarAltReasonName)
				.build();
	}

	public String getCalendarAltTypeAndReasonFormatted(Long calendarId) {
		if (calendarId == CALENDAR_ALL_ID || calendarId == CALENDAR_GREGORIAN_ID) {
			StringBuilder sb = new StringBuilder();
			if (calendarAltTypeName != null && !calendarAltTypeName.isEmpty()) {
				sb.append("In the Catholic Church, this was ").append(calendarAltTypeName);
				if (altMonthNameAd != null 
						&& !altMonthNameAd.isEmpty() 
						&& this.altMonthAdId != MONTH_VARIABLE_ID
						&& altDayOfMonthAd != null) {
					sb.append(" from its original observance on " + altDayOfMonthAd + " " + altMonthNameAd + " ");
				}
				if (calendarAltReasonName != null && !calendarAltReasonName.isEmpty()) {
					sb.append(" ").append(calendarAltReasonName);
				}
				sb.append(".");
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	public Day getDay() {
		return new Day.Builder()
				.withDayId(dayId)
				.withDayOfMonth(dayOfMonth)
				.withMonthId(monthId)
				.withMonthName(monthName)
				.withCalendarId(calendarId)
				.withCalendarCode(calendarCode)
				.build();
	}

	public Day getAltDayAd() {
		return new Day.Builder()
				.withDayId(altDayAdId)
				.withDayOfMonth(altDayOfMonthAd)
				.withMonthId(altMonthAdId)
				.withMonthName(altMonthNameAd)
				.withCalendarId(CALENDAR_GREGORIAN_ID)
				.build();
	}

	public Day getAltDayAh() {
		return new Day.Builder()
				.withDayId(altDayAhId)
				.withDayOfMonth(altDayOfMonthAh)
				.withMonthId(altMonthAhId)
				.withMonthName(altMonthNameAh)
				.withCalendarId(CALENDAR_HIJRI_ID)
				.build();
	}

	public Day getAltDayAm() {
		return new Day.Builder()
				.withDayId(altDayAmId)
				.withDayOfMonth(altDayOfMonthAm)
				.withMonthId(altMonthAmId)
				.withMonthName(altMonthNameAm)
				.withCalendarId(CALENDAR_HEBREW_ID)
				.build();
	}

	public Nation getNation() {
		return new Nation.Builder()
				.withNationId(nationId)
				.withNationName(nationName)
				.withNationCode(nationCode)
				.withAltNationCode(altNationCode)
				.withIsOldWorld(nationIsOldWorld)
				.build();
	}

	public Location getLocation() {
		return new Location.Builder()
				.withLocationId(locationId)
				.withNationId(nationId)
				.withLabelName(locationLabelName)
				.withLocationTypeCode(locationTypeCode)
				.withLocationTypeName(locationTypeName)
				.withLocationTypeId(locationTypeId)
				.withPointX(locationPointX)
				.withPointY(locationPointY)
				.withRouteAId(routeAId)
				.withRouteAName(routeAName)
				.withRouteBId(routeBId)
				.withRouteBName(routeBName)
				.withRouteCId(routeCId)
				.withRouteCName(routeCName)
				.withRouteDId(routeDId)
				.withRouteDName(routeDName)
				.withRouteEId(routeEId)
				.withRouteEName(routeEName)
				.withLastUpdateTime(locationLastUpdateTime)
				.withAllCommemorationCount(locationAllCommemorationCount)
				.withRelatedPatronageSubtypeId(locationRelatedPatronageSubtypeId)
				.withRelatedPatronageSubtypeName(locationRelatedPatronageSubtypeName)
				.withRelatedPatronageId(locationRelatedPatronageId)
				.withRelatedPatronageName(locationRelatedPatronageName)
				.withNextNationId(nextNationId)
				.withNextLocationId(nextLocationId)
				.withNextLocationLabelName(nextLocationLabelName)
				.withPrevNationId(prevNationId)
				.withPrevLocationId(prevLocationId)
				.withPrevLocationLabelName(prevLocationLabelName)
				.withAltInsetId(locationAltInsetId)
				.withAltLabelName(locationAltLabelName)
				.build();
	}

	public LocationType getLocationType() {
		return new LocationType.Builder()
				.withLocationTypeId(locationTypeId)
				.withLocationTypeName(locationTypeName)
				.withLocationTypeCode(locationTypeCode)
				.build();
	}

	public Route getRouteA() {
		return new Route.Builder()
				.withRouteId(routeAId)
				.withRouteName(routeAName)
				// All other fields excluded
				.build();
	}

	public Route getRouteB() {
		return new Route.Builder()
				.withRouteId(routeBId)
				.withRouteName(routeBName)
				// All other fields excluded
				.build();
	}

	public Route getRouteC() {
		return new Route.Builder()
				.withRouteId(routeCId)
				.withRouteName(routeCName)
				// All other fields excluded
				.build();
	}

	public Route getRouteD() {
		return new Route.Builder()
				.withRouteId(routeDId)
				.withRouteName(routeDName)
				// All other fields excluded
				.build();
	}

	public Route getRouteE() {
		return new Route.Builder()
				.withRouteId(routeEId)
				.withRouteName(routeEName)
				// All other fields excluded
				.build();
	}

	public Location getAltLocation() {
		return new Location.Builder()
				.withLocationId(altLocationId)
				.withNationId(altNationId)
				.withLabelName(altLocationLabelName)
				.withLocationTypeCode(altLocationTypeCode)
				.withLocationTypeId(altLocationTypeId)
				.withLocationTypeName(altLocationTypeName)
				.withPointX(altLocationPointX)
				.withPointY(altLocationPointY)
				.withNextLocationId(altNextLocationId)
				.withNextLocationLabelName(altNextLocationLabelName)
				.withPrevLocationId(altPrevLocationId)
				.withPrevLocationLabelName(altPrevLocationLabelName)
				.withLastUpdateTime(altLocationLastUpdateTime)
				.withAllCommemorationCount(altLocationAllCommemorationCount)
				.withRelatedPatronageSubtypeId(altLocationRelatedPatronageSubtypeId)
				.withRelatedPatronageSubtypeName(altLocationRelatedPatronageSubtypeName)
				.withRelatedPatronageId(altLocationRelatedPatronageId)
				.withRelatedPatronageName(altLocationRelatedPatronageName)
				.withInsetId(altLocationInsetId)
				.build();
	}

	public LocationType getAltLocationType() {
		return new LocationType.Builder()
				.withLocationTypeId(altLocationTypeId)
				.withLocationTypeName(altLocationTypeName)
				.withLocationTypeCode(altLocationTypeCode)
				.build();
	}

	public Constellation getConstellation() {
		return new Constellation.Builder()
				.withConstellationId(constellationId)
				.withConstellationNameLatin(constellationName)
				.build();
	}

	public Star getStar() {
		return new Star.Builder()
				.withConstellationId(constellationId)
				.withStarId(starId)
				.withStarName(starName)
				.withStarNameAlt(starNameAlt)
				.build();
	}

	public Patronage getPatronage() {
		return new Patronage.Builder()
				.withPatronageId(patronageId)
				.withPatronageName(patronageName)
				.withPatronageSubtypeId(patronageSubtypeId)
				.withPatronageSubtypeName(patronageSubtypeName)
				.withPatronageSubtypeCode(patronageSubtypeCode)
				.withPatronageTypeId(patronageTypeId)
				.withPatronageTypeName(patronageTypeName)
				.withRelatedSearchLink(patronageRelatedSearchLink)
				.withPointX(patronagePointX)
				.withPointY(patronagePointY)
				.withLastUpdateTime(patronageLastUpdateTime)
				.build();
	}

	public Patronage getLocationRelatedPatronage() {
		return new Patronage.Builder()
				.withPatronageId(locationRelatedPatronageId)
				.withPatronageName(locationRelatedPatronageName)
				.withPointX(locationRelatedPatronagePointX)
				.withPointX(locationRelatedPatronagePointY)
				.build();
	}

	public Patronage getAltLocationRelatedPatronage() {
		return new Patronage.Builder()
				.withPatronageId(altLocationRelatedPatronageId)
				.withPatronageName(altLocationRelatedPatronageName)
				.withPointX(altLocationRelatedPatronagePointX)
				.withPointX(altLocationRelatedPatronagePointY)
				.build();
	}

	public PatronageSubtype getPatronageSubtype() {
		return new PatronageSubtype.Builder()
				.withPatronageSubtypeId(patronageSubtypeId)
				.withPatronageSubtypeName(patronageSubtypeName)
				.withPatronageSubtypeCode(patronageSubtypeCode)
				.withPatronageTypeId(patronageTypeId)
				.withPatronageTypeName(patronageTypeName)
				.build();
	}

	public Reference getReference() {
		return new Reference.Builder()
				.withReferenceId(referenceId)
				.withReferenceName(referenceName)
				.withReferenceVolume(referenceVolume)
				.withReferenceStart(referenceStart)
				.withReferenceEnd(referenceEnd)
				.build();
	}

	public Century getCentury() {
		return new Century.Builder()
				.withCenturyId(centuryId)
				.withCenturyNameAd(centuryNameAd)
				.withCenturyNameAh(centuryNameAh)
				.withCenturyNameAm(centuryNameAm)
				.build();
	}

	public String getCenturyNameFormatted() {
		final Century century = this.getCentury();
		return (century != null && century.getCenturyId() != null) 
				? century.getCenturyNameFormatted(Creed.getPrimaryCalendarForCreed(this.creedId)) 
				: null;
	}

	public Reading getReading() {
		return new Reading.Builder()
				.withReadingId(readingId)
				.withReadingName(readingName)
				.withReadingNameAlt(readingNameAlt)
				.withReadingContent(readingContent)
				.withLastUpdateTime(readingLastUpdateTime)
				.build();
	}

	public Boolean hasTags() {
		return (tagAId != null || tagBId != null || tagCId != null || tagDId != null || tagEId != null);
	}

	public Tag getTagA() {
		return new Tag.Builder()
				.withTagId(tagAId)
				.withTagName(tagAName)
				.build();
	}

	public Tag getTagB() {
		return new Tag.Builder()
				.withTagId(tagBId)
				.withTagName(tagBName)
				.build();
	}

	public Tag getTagC() {
		return new Tag.Builder()
				.withTagId(tagCId)
				.withTagName(tagCName)
				.build();
	}

	public Tag getTagD() {
		return new Tag.Builder()
				.withTagId(tagDId)
				.withTagName(tagDName)
				.build();
	}

	public Tag getTagE() {
		return new Tag.Builder()
				.withTagId(tagEId)
				.withTagName(tagEName)
				.build();
	}

	/**
	 * Getters follow
	 */

	public Long getNationId() {
		return nationId;
	}

	public String getNationName() {
		return nationName;
	}

	public String getNationCode() {
		return nationCode;
	}

	public String getAltNationCode() {
		return altNationCode;
	}

	public Long getNationRelatedPatronageId() {
		return nationRelatedPatronageId;
	}

	public Boolean getNationIsOldWorld() {
		return nationIsOldWorld;
	}

	public Long getCenturyId() {
		return centuryId;
	}

	public String getCenturyNameAd() {
		return centuryNameAd;
	}

	public String getCenturyNameAh() {
		return centuryNameAh;
	}

	public String getCenturyNameAm() {
		return centuryNameAm;
	}

	public Long getPatronageId() {
		return patronageId;
	}

	public String getPatronageName() {
		return patronageName;
	}

	public LocalDate getPatronageLastUpdateTime() {
		return patronageLastUpdateTime;
	}

	public Long getLocationId() {
		return locationId;
	}

	public String getLocationLabelName() {
		return locationLabelName;
	}

	public Long getLocationTypeId() {
		return locationTypeId;
	}

	public String getLocationTypeName() {
		return locationTypeName;
	}

	public String getLocationTypeCode() {
		return locationTypeCode;
	}

	public Double getLocationPointX() {
		return locationPointX;
	}

	public Double getLocationPointY() {
		return locationPointY;
	}

	public Long getLocationRelatedPatronageSubtypeId() {
		return locationRelatedPatronageSubtypeId;
	}

	public String getLocationRelatedPatronageSubtypeName() {
		return locationRelatedPatronageSubtypeName;
	}

	public Long getLocationRelatedPatronageId() {
		return locationRelatedPatronageId;
	}

	public String getLocationRelatedPatronageName() {
		return locationRelatedPatronageName;
	}

	public Long getNextNationId() {
		return nextNationId;
	}

	public Long getNextLocationId() {
		return nextLocationId;
	}

	public String getNextLocationLabelName() {
		return nextLocationLabelName;
	}

	public Long getPrevNationId() {
		return prevNationId;
	}

	public Long getPrevLocationId() {
		return prevLocationId;
	}

	public String getPrevLocationLabelName() {
		return prevLocationLabelName;
	}

	public LocalDate getLocationLastUpdateTime() {
		return locationLastUpdateTime;
	}

	public Long getAltLocationId() {
		return altLocationId;
	}

	public Long getAltNationId() {
		return altNationId;
	}

	public String getAltLocationLabelName() {
		return altLocationLabelName;
	}

	public Long getAltLocationTypeId() {
		return altLocationTypeId;
	}

	public String getAltLocationTypeName() {
		return altLocationTypeName;
	}

	public String getAltLocationTypeCode() {
		return altLocationTypeCode;
	}

	public Double getAltLocationPointX() {
		return altLocationPointX;
	}

	public Double getAltLocationPointY() {
		return altLocationPointY;
	}

	public Long getAltLocationRelatedPatronageSubtypeId() {
		return altLocationRelatedPatronageSubtypeId;
	}

	public String getAltLocationRelatedPatronageSubtypeName() {
		return altLocationRelatedPatronageSubtypeName;
	}

	public Long getAltLocationRelatedPatronageId() {
		return altLocationRelatedPatronageId;
	}

	public String getAltLocationRelatedPatronageName() {
		return altLocationRelatedPatronageName;
	}

	public Long getAltLocationInsetId() {
		return altLocationInsetId;
	}

	public Long getAltNextLocationId() {
		return altNextLocationId;
	}

	public String getAltNextLocationLabelName() {
		return altNextLocationLabelName;
	}

	public Long getAltPrevLocationId() {
		return altPrevLocationId;
	}

	public String getAltPrevLocationLabelName() {
		return altPrevLocationLabelName;
	}

	public LocalDate getAltLocationLastUpdateTime() {
		return altLocationLastUpdateTime;
	}

	public Long getRouteAId() {
		return routeAId;
	}

	public String getRouteAName() {
		return routeAName;
	}

	public Long getRouteBId() {
		return routeBId;
	}

	public String getRouteBName() {
		return routeBName;
	}

	public Long getRouteCId() {
		return routeCId;
	}

	public String getRouteCName() {
		return routeCName;
	}

	public Long getRouteDId() {
		return routeDId;
	}

	public String getRouteDName() {
		return routeDName;
	}

	public Long getRouteEId() {
		return routeEId;
	}

	public String getRouteEName() {
		return routeEName;
	}

	public Long getConstellationId() {
		return constellationId;
	}

	public String getConstellationName() {
		return constellationName;
	}

	public Long getStarId() {
		return starId;
	}

	public String getStarName() {
		return starName;
	}

	public String getStarNameAlt() {
		return starNameAlt;
	}

	public Long getCommemorationId() {
		return commemorationId;
	}

	public String getCommemorationName() {
		return commemorationName;
	}

	public String getCommemorationAltName() {
		return commemorationAltName;
	}

	public String getCommemorationDetail() {
		return commemorationDetail;
	}

	public String getCommemorationYearAd() {
		return commemorationYearAd;
	}

	public String getCommemorationYearAh() {
		return commemorationYearAh;
	}

	public String getCommemorationYearAm() {
		return commemorationYearAm;
	}

	public LocalDate getCommemorationLastUpdateTime() {
		return commemorationLastUpdateTime;
	}

	public Long getCommemorationTypeId() {
		return commemorationTypeId;
	}

	public String getCommemorationTypeName() {
		return commemorationTypeName;
	}

	public String getCommemorationTypeNameAlt() {
		return commemorationTypeNameAlt;
	}

	public Long getCreedId() {
		return creedId;
	}

	public String getCreedName() {
		return creedName;
	}

	public Long getCalendarAltTypeId() {
		return calendarAltTypeId;
	}

	public String getCalendarAltTypeName() {
		return calendarAltTypeName;
	}

	public Long getCalendarAltReasonId() {
		return calendarAltReasonId;
	}

	public String getCalendarAltReasonName() {
		return calendarAltReasonName;
	}

	public Long getCalendarId() {
		return calendarId;
	}

	public String getCalendarCode() {
		return calendarCode;
	}

	public Long getMonthId() {
		return monthId;
	}

	public String getMonthName() {
		return monthName;
	}

	public Long getAltMonthAdId() {
		return altMonthAdId;
	}

	public String getAltMonthNameAd() {
		return altMonthNameAd;
	}

	public Long getAltMonthAhId() {
		return altMonthAhId;
	}

	public String getAltMonthNameAh() {
		return altMonthNameAh;
	}

	public Long getAltMonthAmId() {
		return altMonthAmId;
	}

	public String getAltMonthNameAm() {
		return altMonthNameAm;
	}

	public Long getDayId() {
		return dayId;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public Long getAltDayAdId() {
		return altDayAdId;
	}

	public String getAltDayOfMonthAd() {
		return altDayOfMonthAd;
	}

	public Long getAltDayAhId() {
		return altDayAhId;
	}

	public String getAltDayOfMonthAh() {
		return altDayOfMonthAh;
	}

	public Long getAltDayAmId() {
		return altDayAmId;
	}

	public String getAltDayOfMonthAm() {
		return altDayOfMonthAm;
	}

	public Long getPatronageTypeId() {
		return patronageTypeId;
	}

	public String getPatronageTypeName() {
		return patronageTypeName;
	}

	public Long getPatronageSubtypeId() {
		return patronageSubtypeId;
	}

	public String getPatronageSubtypeName() {
		return patronageSubtypeName;
	}

	public String getPatronageSubtypeCode() {
		return patronageSubtypeCode;
	}

	public String getPatronageRelatedSearchLink() {
		return patronageRelatedSearchLink;
	}

	public Double getPatronagePointX() {
		return patronagePointX;
	}

	public Double getPatronagePointY() {
		return patronagePointY;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public Long getReferenceVolume() {
		return referenceVolume;
	}

	public Long getReferenceStart() {
		return referenceStart;
	}

	public Long getReferenceEnd() {
		return referenceEnd;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public Long getReadingId() {
		return readingId;
	}

	public String getReadingContent() {
		return readingContent;
	}

	public String getReadingName() {
		return readingName;
	}

	public String getReadingNameAlt() {
		return readingNameAlt;
	}

	public LocalDate getReadingLastUpdateTime() {
		return readingLastUpdateTime;
	}

	public Long getTagAId() {
		return tagAId;
	}

	public String getTagAName() {
		return tagAName;
	}

	public Long getTagBId() {
		return tagBId;
	}

	public String getTagBName() {
		return tagBName;
	}

	public Long getTagCId() {
		return tagCId;
	}

	public String getTagCName() {
		return tagCName;
	}

	public Long getTagDId() {
		return tagDId;
	}

	public String getTagDName() {
		return tagDName;
	}

	public Long getTagEId() {
		return tagEId;
	}

	public String getTagEName() {
		return tagEName;
	}

	public Long getLocationAllCommemorationCount() {
		return (locationAllCommemorationCount != null) ? locationAllCommemorationCount : 0L;
	}

	public Long getAltLocationAllCommemorationCount() {
		return (altLocationAllCommemorationCount != null) ? altLocationAllCommemorationCount : 0L;
	}

	public Boolean getHasLastContributorBeenNotified() {
		return (hasLastContributorBeenNotified != null) 
				? hasLastContributorBeenNotified 
				: true;
	}

	public Long getLocationAltInsetId() {
		return locationAltInsetId;
	}

	/**
	 * Setters follow
	 */

	public void setDayCommemorationDtoId(Long dayCommemorationDtoId) {
		this.dayCommemorationDtoId = dayCommemorationDtoId;
	}

	public void setNationId(Long nationId) {
		this.nationId = nationId;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public void setAltNationCode(String altNationCode) {
		this.altNationCode = altNationCode;
	}

	public void setNationIsOldWorld(Boolean nationIsOldWorld) {
		this.nationIsOldWorld = nationIsOldWorld;
	}

	public void setCenturyId(Long centuryId) {
		this.centuryId = centuryId;
	}

	public void setCenturyName(String centuryName) {
		if (centuryName == null) {
			return;
		}
		final Boolean isAnnoDomini = centuryName.contains(EpochEnum.AD.getValue()) 
				|| centuryName.contains(EpochEnum.BC.getValue());
		final Boolean isAnnoHegirae = centuryName.contains(EpochEnum.AH.getValue()) 
				|| centuryName.contains(EpochEnum.BH.getValue());
		final Boolean isAnnoMundi = centuryName.contains(EpochEnum.AM.getValue());
		if (isAnnoDomini && isAnnoHegirae && isAnnoMundi) {
			final String[] centuryNameParts = centuryName.split(" / ");
			this.centuryNameAd = centuryNameParts[0];
			this.centuryNameAh = centuryNameParts[1];
			this.centuryNameAm = centuryNameParts[2];
			return;
		}
		if (isAnnoDomini || centuryName.equals(TIME_IMMEMORIAL)) {
			this.centuryNameAd = centuryName;
			return;
		}
		if (isAnnoHegirae) {
			this.centuryNameAh = centuryName;
			return;
		}
		if (isAnnoMundi) {
			this.centuryNameAm = centuryName;
			return;
		}
	}

	public void setPatronageId(Long patronageId) {
		this.patronageId = patronageId;
	}

	public void setPatronageName(String patronageName) {
		this.patronageName = patronageName;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setLocationLabelName(String locationLabelName) {
		this.locationLabelName = locationLabelName;
	}

	public void setLocationTypeId(Long locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public void setLocationTypeName(String locationTypeName) {
		this.locationTypeName = locationTypeName;
	}

	public void setLocationTypeCode(String locationTypeCode) {
		this.locationTypeCode = locationTypeCode;
	}

	public void setLocationPointX(Double locationPointX) {
		this.locationPointX = locationPointX;
	}

	public void setLocationPointY(Double locationPointY) {
		this.locationPointY = locationPointY;
	}

	public void setLocationRelatedPatronageSubtypeId(Long locationRelatedPatronageSubtypeId) {
		this.locationRelatedPatronageSubtypeId = locationRelatedPatronageSubtypeId;
	}

	public void setLocationRelatedPatronageSubtypeName(String locationRelatedPatronageSubtypeName) {
		this.locationRelatedPatronageSubtypeName = locationRelatedPatronageSubtypeName;
	}

	public void setLocationRelatedPatronageId(Long locationRelatedPatronageId) {
		this.locationRelatedPatronageId = locationRelatedPatronageId;
	}

	public void setLocationRelatedPatronageName(String locationRelatedPatronageName) {
		this.locationRelatedPatronageName = locationRelatedPatronageName;
	}

	public void setNextNationId(Long nextNationId) {
		this.nextNationId = nextNationId;
	}

	public void setNextLocationId(Long nextLocationId) {
		this.nextLocationId = nextLocationId;
	}

	public void setPrevNationId(Long prevNationId) {
		this.prevNationId = prevNationId;
	}

	public void setPrevLocationId(Long prevLocationId) {
		this.prevLocationId = prevLocationId;
	}

	public void setAltLocationId(Long altLocationId) {
		this.altLocationId = altLocationId;
	}

	public void setAltNationId(Long altNationId) {
		this.altNationId = altNationId;
	}

	public void setAltLocationLabelName(String altLocationLabelName) {
		this.altLocationLabelName = altLocationLabelName;
	}

	public void setAltLocationTypeId(Long altLocationTypeId) {
		this.altLocationTypeId = altLocationTypeId;
	}

	public void setAltLocationTypeName(String altLocationTypeName) {
		this.altLocationTypeName = altLocationTypeName;
	}

	public void setAltLocationTypeCode(String altLocationTypeCode) {
		this.altLocationTypeCode = altLocationTypeCode;
	}

	public void setAltLocationPointX(Double altLocationPointX) {
		this.altLocationPointX = altLocationPointX;
	}

	public void setAltLocationPointY(Double altLocationPointY) {
		this.altLocationPointY = altLocationPointY;
	}

	public void setAltLocationRelatedPatronageSubtypeId(Long altLocationRelatedPatronageSubtypeId) {
		this.altLocationRelatedPatronageSubtypeId = altLocationRelatedPatronageSubtypeId;
	}

	public void setAltLocationRelatedPatronageSubtypeName(String altLocationRelatedPatronageSubtypeName) {
		this.altLocationRelatedPatronageSubtypeName = altLocationRelatedPatronageSubtypeName;
	}

	public void setAltLocationRelatedPatronageId(Long altLocationRelatedPatronageId) {
		this.altLocationRelatedPatronageId = altLocationRelatedPatronageId;
	}

	public void setAltLocationRelatedPatronageName(String altLocationRelatedPatronageName) {
		this.altLocationRelatedPatronageName = altLocationRelatedPatronageName;
	}

	public void setAltNextLocationId(Long altNextLocationId) {
		this.altNextLocationId = altNextLocationId;
	}

	public void setAltPrevLocationId(Long altPrevLocationId) {
		this.altPrevLocationId = altPrevLocationId;
	}

	public void setCommemorationId(Long commemorationId) {
		this.commemorationId = commemorationId;
	}

	public void setConstellationId(Long constellationId) {
		this.constellationId = constellationId;
	}

	public void setConstellationName(String constellationName) {
		this.constellationName = constellationName;
	}

	public void setStarId(Long starId) {
		this.starId = starId;
	}

	public void setStarName(String starName) {
		this.starName = starName;
	}

	public void setStarNameAlt(String starNameAlt) {
		this.starNameAlt = starNameAlt;
	}

	public void setCommemorationName(String commemorationName) {
		this.commemorationName = commemorationName;
	}

	public void setCommemorationAltName(String commemorationAltName) {
		this.commemorationAltName = commemorationAltName;
	}

	public void setCommemorationDetail(String commemorationDetail) {
		this.commemorationDetail = commemorationDetail;
	}

	public void setCommemorationYearAd(String commemorationYearAd) {
		this.commemorationYearAd = commemorationYearAd;
	}

	public void setCommemorationYearAh(String commemorationYearAh) {
		this.commemorationYearAh = commemorationYearAh;
	}

	public void setCommemorationYearAm(String commemorationYearAm) {
		this.commemorationYearAm = commemorationYearAm;
	}

	public void setCommemorationTypeId(Long commemorationTypeId) {
		this.commemorationTypeId = commemorationTypeId;
	}

	public void setCommemorationTypeName(String commemorationTypeName) {
		this.commemorationTypeName = commemorationTypeName;
	}

	public void setCommemorationTypeNameAlt(String commemorationTypeNameAlt) {
		this.commemorationTypeNameAlt = commemorationTypeNameAlt;
	}

	public void setCreedId(Long creedId) {
		this.creedId = creedId;
	}

	public void setCreedName(String creedName) {
		this.creedName = creedName;
	}

	public void setCalendarAltTypeId(Long calendarAltTypeId) {
		this.calendarAltTypeId = calendarAltTypeId;
	}

	public void setCalendarAltTypeName(String calendarAltTypeName) {
		this.calendarAltTypeName = calendarAltTypeName;
	}

	public void setCalendarAltReasonId(Long calendarAltReasonId) {
		this.calendarAltReasonId = calendarAltReasonId;
	}

	public void setCalendarAltReasonName(String calendarAltReasonName) {
		this.calendarAltReasonName = calendarAltReasonName;
	}

	public void setCalendarId(Long calendarId) {
		this.calendarId = calendarId;
	}

	public void setMonthId(Long monthId) {
		this.monthId = monthId;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public void setAltMonthAdId(Long altMonthAdId) {
		this.altMonthAdId = altMonthAdId;
	}

	public void setAltMonthNameAd(String altMonthNameAd) {
		this.altMonthNameAd = altMonthNameAd;
	}

	public void setAltMonthAhId(Long altMonthAhId) {
		this.altMonthAhId = altMonthAhId;
	}

	public void setAltMonthNameAh(String altMonthNameAh) {
		this.altMonthNameAh = altMonthNameAh;
	}

	public void setAltMonthAmId(Long altMonthAmId) {
		this.altMonthAmId = altMonthAmId;
	}

	public void setAltMonthNameAm(String altMonthNameAm) {
		this.altMonthNameAm = altMonthNameAm;
	}

	public void setDayId(Long dayId) {
		this.dayId = dayId;
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public void setAltDayAdId(Long altDayAdId) {
		this.altDayAdId = altDayAdId;
	}

	public void setAltDayOfMonthAd(String altDayOfMonthAd) {
		this.altDayOfMonthAd = altDayOfMonthAd;
	}

	public void setAltDayAhId(Long altDayAhId) {
		this.altDayAhId = altDayAhId;
	}

	public void setAltDayOfMonthAh(String altDayOfMonthAh) {
		this.altDayOfMonthAh = altDayOfMonthAh;
	}

	public void setAltDayAmId(Long altDayAmId) {
		this.altDayAmId = altDayAmId;
	}

	public void setAltDayOfMonthAm(String altDayOfMonthAm) {
		this.altDayOfMonthAm = altDayOfMonthAm;
	}

	public void setPatronageTypeId(Long patronageTypeId) {
		this.patronageTypeId = patronageTypeId;
	}

	public void setPatronageTypeName(String patronageTypeName) {
		this.patronageTypeName = patronageTypeName;
	}

	public void setPatronageSubtypeId(Long patronageSubtypeId) {
		this.patronageSubtypeId = patronageSubtypeId;
	}

	public void setPatronageSubtypeName(String patronageSubtypeName) {
		this.patronageSubtypeName = patronageSubtypeName;
	}

	public void setPatronageRelatedSearchLink(String patronageRelatedSearchLink) {
		this.patronageRelatedSearchLink = patronageRelatedSearchLink;
	}

	public void setPatronagePointX(Double patronagePointX) {
		this.patronagePointX = patronagePointX;
	}

	public void setPatronagePointY(Double patronagePointY) {
		this.patronagePointY = patronagePointY;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public void setReferenceVolume(Long referenceVolume) {
		this.referenceVolume = referenceVolume;
	}

	public void setReferenceStart(Long referenceStart) {
		this.referenceStart = referenceStart;
	}

	public void setReferenceEnd(Long referenceEnd) {
		this.referenceEnd = referenceEnd;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public void setReadingId(Long readingId) {
		this.readingId = readingId;
	}

	public void setReadingContent(String readingContent) {
		this.readingContent = readingContent;
	}

	public void setReadingName(String readingName) {
		this.readingName = readingName;
	}

	public void setReadingNameAlt(String readingNameAlt) {
		this.readingNameAlt = readingNameAlt;
	}

	public void setTagAId(Long tagAId) {
		this.tagAId = tagAId;
	}

	public void setTagAName(String tagAName) {
		this.tagAName = tagAName;
	}

	public void setTagBId(Long tagBId) {
		this.tagBId = tagBId;
	}

	public void setTagBName(String tagBName) {
		this.tagBName = tagBName;
	}

	public void setTagCId(Long tagCId) {
		this.tagCId = tagCId;
	}

	public void setTagCName(String tagCName) {
		this.tagCName = tagCName;
	}

	public void setTagDId(Long tagDId) {
		this.tagDId = tagDId;
	}

	public void setTagDName(String tagDName) {
		this.tagDName = tagDName;
	}

	public void setTagEId(Long tagEId) {
		this.tagEId = tagEId;
	}

	public void setTagEName(String tagEName) {
		this.tagEName = tagEName;
	}

	public void setCenturyNameAd(String centuryNameAd) {
		this.centuryNameAd = centuryNameAd;
	}

	public void setCenturyNameAh(String centuryNameAh) {
		this.centuryNameAh = centuryNameAh;
	}

	public void setCenturyNameAm(String centuryNameAm) {
		this.centuryNameAm = centuryNameAm;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public void setRouteAId(Long routeAId) {
		this.routeAId = routeAId;
	}

	public void setRouteAName(String routeAName) {
		this.routeAName = routeAName;
	}

	public void setRouteBId(Long routeBId) {
		this.routeBId = routeBId;
	}

	public void setRouteBName(String routeBName) {
		this.routeBName = routeBName;
	}

	public void setRouteCId(Long routeCId) {
		this.routeCId = routeCId;
	}

	public void setRouteCName(String routeCName) {
		this.routeCName = routeCName;
	}

	public void setRouteDId(Long routeDId) {
		this.routeDId = routeDId;
	}

	public void setRouteDName(String routeDName) {
		this.routeDName = routeDName;
	}

	public void setRouteEId(Long routeEId) {
		this.routeEId = routeEId;
	}

	public void setRouteEName(String routeEName) {
		this.routeEName = routeEName;
	}

	public void setLocationAllCommemorationCount(Long locationAllCommemorationCount) {
		this.locationAllCommemorationCount = locationAllCommemorationCount;
	}

	public void setAltLocationAllCommemorationCount(Long altLocationAllCommemorationCount) {
		this.altLocationAllCommemorationCount = altLocationAllCommemorationCount;
	}

	public DayCommemorationDto setHasLastContributorBeenNotified(Boolean hasLastContributorBeenNotified) {
		this.hasLastContributorBeenNotified = hasLastContributorBeenNotified;
		this.commemorationLastUpdateTime = LocalDate.now(BaseEntity.ZONE_ID);
		return this;
	}

	public void setLocationAltInsetId(Long locationAltInsetId) {
		this.locationAltInsetId = locationAltInsetId;
	}

	public void setAltLocationInsetId(Long altLocationInsetId) {
		this.altLocationInsetId = altLocationInsetId;
	}

	public void setNextLocationLabelName(String nextLocationLabelName) {
		this.nextLocationLabelName = nextLocationLabelName;
	}

	public void setPrevLocationLabelName(String prevLocationLabelName) {
		this.prevLocationLabelName = prevLocationLabelName;
	}

	public void setAltNextLocationLabelName(String altNextLocationLabelName) {
		this.altNextLocationLabelName = altNextLocationLabelName;
	}

	public void setAltPrevLocationLabelName(String altPrevLocationLabelName) {
		this.altPrevLocationLabelName = altPrevLocationLabelName;
	}

	public String getLocationAltLabelName() {
		return locationAltLabelName;
	}

	public void setLocationAltLabelName(String locationAltLabelName) {
		this.locationAltLabelName = locationAltLabelName;
	}

	@Override
	public int compareTo(DayCommemorationDto other) {
		return Comparator
				.comparing(DayCommemorationDto::getCommemorationTypeId, 
						Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(DayCommemorationDto::getCenturyId, 
						Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(DayCommemorationDto::getCommemorationYearAd, 
						Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(DayCommemorationDto::getCommemorationYearAh, 
						Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(DayCommemorationDto::getCommemorationYearAm, 
						Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(DayCommemorationDto::getDayId, 
						Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(DayCommemorationDto::getCommemorationName, 
						Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}

	@Override
	public int hashCode() {
		return Objects.hash(altDayAdId, altDayAhId, altDayAmId, altDayOfMonthAd, altDayOfMonthAh, altDayOfMonthAm,
				altLocationAllCommemorationCount, altLocationId, altLocationInsetId,
				altLocationLabelName, altLocationLastUpdateTime, altLocationPointX, altLocationPointY,
				altLocationRelatedPatronageId, altLocationRelatedPatronageName, altLocationRelatedPatronagePointX,
				altLocationRelatedPatronagePointY, altLocationRelatedPatronageSubtypeId,
				altLocationRelatedPatronageSubtypeName, altLocationTypeCode, altLocationTypeId, altLocationTypeName,
				altMonthAdId, altMonthAhId, altMonthAmId, altMonthNameAd, altMonthNameAh, altMonthNameAm, altNationCode,
				altNationId, altNextLocationId, altNextLocationLabelName, altPrevLocationId, altPrevLocationLabelName,
				calendarAltReasonId, calendarAltReasonName, calendarAltTypeId, calendarAltTypeName, calendarCode,
				calendarId, centuryId, centuryNameAd, centuryNameAh, centuryNameAm, commemorationAltName,
				commemorationDetail, commemorationId, commemorationLastUpdateTime, commemorationName,
				commemorationTypeId, commemorationTypeName, commemorationTypeNameAlt, commemorationYearAd,
				commemorationYearAh, commemorationYearAm, constellationId, constellationName, contributors, creedId,
				creedName, dayCommemorationDtoId, dayId, dayOfMonth, hasLastContributorBeenNotified,
				locationAllCommemorationCount, locationAltInsetId, locationAltLabelName, locationId,
				locationLabelName, locationLastUpdateTime, locationPointX, locationPointY, locationRelatedPatronageId,
				locationRelatedPatronageName, locationRelatedPatronagePointX, locationRelatedPatronagePointY,
				locationRelatedPatronageSubtypeId, locationRelatedPatronageSubtypeName, locationTypeCode,
				locationTypeId, locationTypeName, monthId, monthName, nationCode, nationId, nationIsOldWorld,
				nationName, nationRelatedPatronageId, nextLocationId, nextLocationLabelName, nextNationId, patronageId,
				patronageLastUpdateTime, patronageName, patronagePointX, patronagePointY, patronageRelatedSearchLink,
				patronageSubtypeCode, patronageSubtypeId, patronageSubtypeName, patronageTypeId, patronageTypeName,
				prevLocationId, prevLocationLabelName, prevNationId, readingContent, readingId, readingLastUpdateTime,
				readingName, readingNameAlt, referenceEnd, referenceId, referenceName, referenceStart,
				referenceVolume, routeAId, routeAName, routeBId, routeBName, routeCId, routeCName, routeDId, routeDName,
				routeEId, routeEName, starId, starName, starNameAlt, tagAId, tagAName, tagBId, tagBName, tagCId, tagCName, tagDId,
				tagDName, tagEId, tagEName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DayCommemorationDto other = (DayCommemorationDto) obj;
		return Objects.equals(altDayAdId, other.altDayAdId) && Objects.equals(altDayAhId, other.altDayAhId)
				&& Objects.equals(altDayAmId, other.altDayAmId)
				&& Objects.equals(altDayOfMonthAd, other.altDayOfMonthAd)
				&& Objects.equals(altDayOfMonthAh, other.altDayOfMonthAh)
				&& Objects.equals(altDayOfMonthAm, other.altDayOfMonthAm)
				&& Objects.equals(altLocationAllCommemorationCount, other.altLocationAllCommemorationCount)
				&& Objects.equals(altLocationId, other.altLocationId)
				&& Objects.equals(altLocationInsetId, other.altLocationInsetId)
				&& Objects.equals(altLocationLabelName, other.altLocationLabelName)
				&& Objects.equals(altLocationLastUpdateTime, other.altLocationLastUpdateTime)
				&& Objects.equals(altLocationPointX, other.altLocationPointX)
				&& Objects.equals(altLocationPointY, other.altLocationPointY)
				&& Objects.equals(altLocationRelatedPatronageId, other.altLocationRelatedPatronageId)
				&& Objects.equals(altLocationRelatedPatronageName, other.altLocationRelatedPatronageName)
				&& Objects.equals(altLocationRelatedPatronagePointX, other.altLocationRelatedPatronagePointX)
				&& Objects.equals(altLocationRelatedPatronagePointY, other.altLocationRelatedPatronagePointY)
				&& Objects.equals(altLocationRelatedPatronageSubtypeId, other.altLocationRelatedPatronageSubtypeId)
				&& Objects.equals(altLocationRelatedPatronageSubtypeName, other.altLocationRelatedPatronageSubtypeName)
				&& Objects.equals(altLocationTypeCode, other.altLocationTypeCode)
				&& Objects.equals(altLocationTypeId, other.altLocationTypeId)
				&& Objects.equals(altLocationTypeName, other.altLocationTypeName)
				&& Objects.equals(altMonthAdId, other.altMonthAdId) && Objects.equals(altMonthAhId, other.altMonthAhId)
				&& Objects.equals(altMonthAmId, other.altMonthAmId)
				&& Objects.equals(altMonthNameAd, other.altMonthNameAd)
				&& Objects.equals(altMonthNameAh, other.altMonthNameAh)
				&& Objects.equals(altMonthNameAm, other.altMonthNameAm)
				&& Objects.equals(altNationCode, other.altNationCode) && Objects.equals(altNationId, other.altNationId)
				&& Objects.equals(altNextLocationId, other.altNextLocationId)
				&& Objects.equals(altNextLocationLabelName, other.altNextLocationLabelName)
				&& Objects.equals(altPrevLocationId, other.altPrevLocationId)
				&& Objects.equals(altPrevLocationLabelName, other.altPrevLocationLabelName)
				&& Objects.equals(calendarAltReasonId, other.calendarAltReasonId)
				&& Objects.equals(calendarAltReasonName, other.calendarAltReasonName)
				&& Objects.equals(calendarAltTypeId, other.calendarAltTypeId)
				&& Objects.equals(calendarAltTypeName, other.calendarAltTypeName)
				&& Objects.equals(calendarCode, other.calendarCode) && Objects.equals(calendarId, other.calendarId)
				&& Objects.equals(centuryId, other.centuryId) && Objects.equals(centuryNameAd, other.centuryNameAd)
				&& Objects.equals(centuryNameAh, other.centuryNameAh)
				&& Objects.equals(centuryNameAm, other.centuryNameAm)
				&& Objects.equals(commemorationAltName, other.commemorationAltName)
				&& Objects.equals(commemorationDetail, other.commemorationDetail)
				&& Objects.equals(commemorationId, other.commemorationId)
				&& Objects.equals(commemorationLastUpdateTime, other.commemorationLastUpdateTime)
				&& Objects.equals(commemorationName, other.commemorationName)
				&& Objects.equals(commemorationTypeId, other.commemorationTypeId)
				&& Objects.equals(commemorationTypeName, other.commemorationTypeName)
				&& Objects.equals(commemorationTypeNameAlt, other.commemorationTypeNameAlt)
				&& Objects.equals(commemorationYearAd, other.commemorationYearAd)
				&& Objects.equals(commemorationYearAh, other.commemorationYearAh)
				&& Objects.equals(commemorationYearAm, other.commemorationYearAm)
				&& Objects.equals(constellationId, other.constellationId)
				&& Objects.equals(constellationName, other.constellationName)
				&& Objects.equals(contributors, other.contributors) && Objects.equals(creedId, other.creedId)
				&& Objects.equals(creedName, other.creedName)
				&& Objects.equals(dayCommemorationDtoId, other.dayCommemorationDtoId)
				&& Objects.equals(dayId, other.dayId) && Objects.equals(dayOfMonth, other.dayOfMonth)
				&& Objects.equals(hasLastContributorBeenNotified, other.hasLastContributorBeenNotified)
				&& Objects.equals(locationAllCommemorationCount, other.locationAllCommemorationCount)
				&& Objects.equals(locationAltInsetId, other.locationAltInsetId)
				&& Objects.equals(locationAltLabelName, other.locationAltLabelName)
				&& Objects.equals(locationId, other.locationId) 
				&& Objects.equals(locationLabelName, other.locationLabelName)
				&& Objects.equals(locationLastUpdateTime, other.locationLastUpdateTime)
				&& Objects.equals(locationPointX, other.locationPointX)
				&& Objects.equals(locationPointY, other.locationPointY)
				&& Objects.equals(locationRelatedPatronageId, other.locationRelatedPatronageId)
				&& Objects.equals(locationRelatedPatronageName, other.locationRelatedPatronageName)
				&& Objects.equals(locationRelatedPatronagePointX, other.locationRelatedPatronagePointX)
				&& Objects.equals(locationRelatedPatronagePointY, other.locationRelatedPatronagePointY)
				&& Objects.equals(locationRelatedPatronageSubtypeId, other.locationRelatedPatronageSubtypeId)
				&& Objects.equals(locationRelatedPatronageSubtypeName, other.locationRelatedPatronageSubtypeName)
				&& Objects.equals(locationTypeCode, other.locationTypeCode)
				&& Objects.equals(locationTypeId, other.locationTypeId)
				&& Objects.equals(locationTypeName, other.locationTypeName) && Objects.equals(monthId, other.monthId)
				&& Objects.equals(monthName, other.monthName) && Objects.equals(nationCode, other.nationCode)
				&& Objects.equals(nationId, other.nationId) && Objects.equals(nationIsOldWorld, other.nationIsOldWorld)
				&& Objects.equals(nationName, other.nationName)
				&& Objects.equals(nationRelatedPatronageId, other.nationRelatedPatronageId)
				&& Objects.equals(nextLocationId, other.nextLocationId)
				&& Objects.equals(nextLocationLabelName, other.nextLocationLabelName)
				&& Objects.equals(nextNationId, other.nextNationId) && Objects.equals(patronageId, other.patronageId)
				&& Objects.equals(patronageLastUpdateTime, other.patronageLastUpdateTime)
				&& Objects.equals(patronageName, other.patronageName)
				&& Objects.equals(patronagePointX, other.patronagePointX)
				&& Objects.equals(patronagePointY, other.patronagePointY)
				&& Objects.equals(patronageRelatedSearchLink, other.patronageRelatedSearchLink)
				&& Objects.equals(patronageSubtypeCode, other.patronageSubtypeCode)
				&& Objects.equals(patronageSubtypeId, other.patronageSubtypeId)
				&& Objects.equals(patronageSubtypeName, other.patronageSubtypeName)
				&& Objects.equals(patronageTypeId, other.patronageTypeId)
				&& Objects.equals(patronageTypeName, other.patronageTypeName)
				&& Objects.equals(prevLocationId, other.prevLocationId)
				&& Objects.equals(prevLocationLabelName, other.prevLocationLabelName)
				&& Objects.equals(prevNationId, other.prevNationId)
				&& Objects.equals(readingContent, other.readingContent) && Objects.equals(readingId, other.readingId)
				&& Objects.equals(readingLastUpdateTime, other.readingLastUpdateTime)
				&& Objects.equals(readingName, other.readingName)
				&& Objects.equals(readingNameAlt, other.readingNameAlt)
				&& Objects.equals(referenceEnd, other.referenceEnd) && Objects.equals(referenceId, other.referenceId)
				&& Objects.equals(referenceName, other.referenceName)
				&& Objects.equals(referenceStart, other.referenceStart)
				&& Objects.equals(referenceVolume, other.referenceVolume) && Objects.equals(routeAId, other.routeAId)
				&& Objects.equals(routeAName, other.routeAName) && Objects.equals(routeBId, other.routeBId)
				&& Objects.equals(routeBName, other.routeBName) && Objects.equals(routeCId, other.routeCId)
				&& Objects.equals(routeCName, other.routeCName) && Objects.equals(routeDId, other.routeDId)
				&& Objects.equals(routeDName, other.routeDName) && Objects.equals(routeEId, other.routeEId)
				&& Objects.equals(routeEName, other.routeEName) && Objects.equals(starId, other.starId)
				&& Objects.equals(starName, other.starName) && Objects.equals(starNameAlt, other.starNameAlt) 
				&& Objects.equals(tagAId, other.tagAId) && Objects.equals(tagAName, other.tagAName) 
				&& Objects.equals(tagBId, other.tagBId) && Objects.equals(tagBName, other.tagBName) 
				&& Objects.equals(tagCId, other.tagCId) && Objects.equals(tagCName, other.tagCName) 
				&& Objects.equals(tagDId, other.tagDId) && Objects.equals(tagDName, other.tagDName) 
				&& Objects.equals(tagEId, other.tagEId) && Objects.equals(tagEName, other.tagEName);
	}
}