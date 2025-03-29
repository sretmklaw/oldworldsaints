package com.cimeliarchium.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.service.dao.CenturyService;
import com.cimeliarchium.service.dao.FeatureFlagService;
import com.cimeliarchium.service.dao.InsetService;
import com.cimeliarchium.service.dao.LunarPhaseService;
import com.cimeliarchium.service.dao.ReferenceService;
import com.cimeliarchium.service.dao.TagService;
import com.cimeliarchium.service.dao.UserService;
import com.cimeliarchium.service.dto.CalendarMonthDayService;
import com.cimeliarchium.service.dto.ConstellationStarService;
import com.cimeliarchium.service.dto.DayCommemorationService;
import com.cimeliarchium.service.dto.NationLocationService;
import com.cimeliarchium.service.dto.NationRegionService;
import com.cimeliarchium.service.dto.PatronageSubtypeService;
import com.cimeliarchium.service.dto.PatronageTypeService;

@Service
public class ApplicationReadyEventListenerService {

	@Autowired CalendarMonthDayService calendarMonthDayService;
	@Autowired DayCommemorationService dayCommemorationService;
	@Autowired NationLocationService nationLocationService;
	@Autowired NationRegionService nationRegionService;
	@Autowired PatronageSubtypeService patronageSubtypeService;
	@Autowired PatronageTypeService patronageTypeService;
	@Autowired CenturyService centuryService;
	@Autowired InsetService insetService;
	@Autowired LunarPhaseService lunarPhaseService;
	@Autowired ReferenceService referenceService;
	@Autowired TagService tagService;
	@Autowired ConstellationStarService constellationStarService;
	@Autowired FeatureFlagService featureFlagService;
	@Autowired UserService userService;

	/**
	 * Map each static initialized mapping once immediately following application readiness status.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void init() throws AppException {

		// FIXME: Activate server restart notification to remind users to clear cached data
		//featureFlagService.setLastServerRestartDate(new Date());
		//userService.resetCacheStatusForAllUsers();

		// First obtain DTO query results from database
		calendarMonthDayService.query();
		dayCommemorationService.query();
		nationLocationService.query();
		nationRegionService.query();
		patronageSubtypeService.query();
		patronageTypeService.query();
		constellationStarService.query();

		// Then translate DTO query results to static mappings
		calendarMonthDayService.map();
		dayCommemorationService.map();
		nationLocationService.map();
		nationRegionService.map();
		patronageSubtypeService.map();
		patronageTypeService.map();
		constellationStarService.map();

		// Finally, initialize one-to-one entity mappings
		centuryService.map();
		insetService.map();
		lunarPhaseService.map();
		referenceService.map();
		tagService.map();

	}

}
