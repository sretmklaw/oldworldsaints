package com.cimeliarchium.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Century;
import com.cimeliarchium.model.dao.Creed;
import com.cimeliarchium.model.dao.Day;
import com.cimeliarchium.model.dao.Location;
import com.cimeliarchium.model.dao.Month;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.PatronageSubtype;
import com.cimeliarchium.model.dao.Reading;
import com.cimeliarchium.model.dao.Reference;
import com.cimeliarchium.model.dao.Region;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.SearchCriteria;
import com.cimeliarchium.model.dao.Star;
import com.cimeliarchium.model.dao.Tag;
import com.cimeliarchium.service.dao.CenturyService;
import com.cimeliarchium.service.dao.CommemorationTitleService;
import com.cimeliarchium.service.dao.ReadingService;
import com.cimeliarchium.service.dao.ReferenceService;
import com.cimeliarchium.service.dao.TagService;
import com.cimeliarchium.service.dto.CalendarMonthDayService;
import com.cimeliarchium.service.dto.CalendarRiteService;
import com.cimeliarchium.service.dto.ConstellationStarService;
import com.cimeliarchium.service.dto.NationLocationService;
import com.cimeliarchium.service.dto.NationRegionService;
import com.cimeliarchium.service.dto.PatronageSubtypeService;
import com.cimeliarchium.service.dto.PatronageTypeService;

@Controller
public class DropdownController {

	private CalendarRiteService calendarRiteService;
	private CenturyService centuryService;
	private CalendarMonthDayService calendarMonthDayService;
	private PatronageTypeService patronageTypeService;
	private PatronageSubtypeService patronageSubtypeService;
	private NationLocationService nationLocationService;
	private NationRegionService nationRegionService;
	private TagService tagService;
	private ReadingService readingService;
	private ReferenceService referenceService;
	private ConstellationStarService constellationStarService;
	private CommemorationTitleService commemorationTitleService;

	@Autowired
	public DropdownController(CalendarRiteService calendarRiteService, CenturyService centuryService,
			CalendarMonthDayService calendarMonthDayService, PatronageTypeService patronageTypeService,
			PatronageSubtypeService patronageSubtypeService, NationLocationService nationLocationService,
			NationRegionService nationRegionService, TagService tagService, ReadingService readingService,
			ReferenceService referenceService, ConstellationStarService constellationStarService, CommemorationTitleService commemorationTitleService) {
		this.calendarRiteService = calendarRiteService;
		this.centuryService = centuryService;
		this.calendarMonthDayService = calendarMonthDayService;
		this.patronageTypeService = patronageTypeService;
		this.patronageSubtypeService = patronageSubtypeService;
		this.nationLocationService = nationLocationService;
		this.nationRegionService = nationRegionService;
		this.tagService = tagService;
		this.readingService = readingService;
		this.referenceService = referenceService;
		this.constellationStarService = constellationStarService;
		this.commemorationTitleService = commemorationTitleService;
	}

	@RequestMapping(value = "/century", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Century> findCenturies(
			@RequestParam("creedId") Long creedId,
			HttpSession session) throws AppException {

		final Long primaryCalendarId = Creed.getPrimaryCalendarForCreed(creedId);
		return centuryService.getCachedValues(session).entrySet().stream()
				.map(e -> {
					Century century = e.getValue();
					century.setCenturyDisplayName(century.getCenturyNameFormatted(primaryCalendarId));
					return century;
				}).collect(Collectors.toList());
	}

	@RequestMapping(value = "/calendar", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Calendar> findCalendarByCreed(
			@RequestParam("creedId") Long creedId,
			HttpSession session) throws AppException {

		return calendarMonthDayService.lookupCalendarsByCreed(session, creedId);
	}

	@RequestMapping(value = "/month", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Month> findMonthsByCalendar(
			@RequestParam("calendarId") Long calendarId,
			HttpSession session) throws AppException {

		return calendarMonthDayService.lookupMonthsByCalendar(session, calendarId);
	}

	@RequestMapping(value = "/day", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Day> findDaysByMonth(
			@RequestParam("monthId") Long monthId,
			HttpSession session) throws AppException {

		return calendarMonthDayService.lookupDaysByMonth(session, monthId);
	}

	@RequestMapping(value = "/rite", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Rite> findRitesByCalendar(
			@RequestParam("calendarId") Long calendarId) throws AppException {

		return calendarRiteService.lookupById(calendarId);
	}

	@RequestMapping(value = "/region", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Region> findRegionsByNation(
			@RequestParam("nationId") Long nationId,
			HttpSession session) throws AppException {

		return nationRegionService.lookupById(session, nationId);
	}

	@RequestMapping(value = "/location", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Location> findLocationsByNation(
			@RequestParam("nationId") Long nationId,
			HttpSession session) throws AppException {

		return nationLocationService.lookupById(session, nationId);
	}

	@RequestMapping(value = "/location-inset", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Location> findInsetLocationsByNation(
			@RequestParam("nationId") Long nationId,
			HttpSession session,
			Boolean showSingleResultOnSpecialAltLocationDropdown) throws AppException {

		return nationLocationService.lookupInsetLocationsById(session, nationId, showSingleResultOnSpecialAltLocationDropdown);
	}

	@RequestMapping(value = "/patronage-subtype", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<PatronageSubtype> findPatronageSubtypeByType(
			@RequestParam("patronageTypeId") Long patronageTypeId,
			HttpSession session) throws AppException {

		return patronageTypeService.lookupById(session, patronageTypeId);
	}

	@RequestMapping(value = "/patronage", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Patronage> findPatronageBySubtype(
			@RequestParam("patronageSubtypeId") Long patronageSubtypeId,
			HttpSession session) throws AppException {

		return patronageSubtypeService.lookupById(session, patronageSubtypeId);
	}

	@RequestMapping(value = "/star", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Star> findStarByConstellation(
			@RequestParam("constellationId") Long constellationId,
			HttpSession session) throws AppException {

		return constellationStarService.lookupById(session, constellationId);
	}

	@RequestMapping(value = "/tag", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Tag> findTagsByCreed(
			@RequestParam("creedId") Long creedId,
			HttpSession session) throws AppException {

		return tagService.lookupById(session, creedId);
	}

	@RequestMapping(value = "/reading-title", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody Map<String, Map<Long, String>> findReadingTitlesByCreed(
			@RequestParam("creedId") Long creedId) throws AppException {

		return readingService.lookupTitlesByCreedId(Creed.getPrimaryCalendarForCreed(creedId));
	}

	@RequestMapping(value = "/reading", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Reading> findReadingsByTitle(
			@RequestParam("readingTitle") String readingTitle,
			HttpSession session) throws AppException {

		return readingService.lookupByTitle(session, readingTitle);
	}

	@RequestMapping(value = "/reference", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<Reference> findReferencesByTitle(
			@RequestParam("referenceTitle") String referenceTitle,
			HttpSession session) throws AppException {

		return referenceService.lookupByTitle(session, referenceTitle);
	}

	@RequestMapping(value = "/commemoration-title", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public @ResponseBody List<SearchCriteria> findTitleByCreed(
			@RequestParam("creedId") Long creedId) throws AppException {

		return commemorationTitleService.lookupById(creedId);
	}
}