package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.CalendarRiteEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class CalendarRiteService extends SessionAttributeHelperService {

	public List<Calendar> listKeys(
			@NotNull Map<Calendar, List<Rite>> entityMap) throws AppException {

		return entityMap.keySet().stream()
				.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public List<Rite> lookupById(Long entityId) throws AppException {

		final Map<Calendar, List<Rite>> entityMap = CalendarRiteEnum.getCalendarRiteMap();
		// Rite ID can be null on page load, before selection is made.
		if (entityId != null) {
			// Obtain mapped value by entity key, after first matching on ID
			final List<Rite> rs = entityMap.get(
					// Obtain non-null match for ID from key list
					((List<Calendar>) super.throwIfMissing(
							this.listKeys(entityMap), lookupByIdMissingEntityList)).stream()
					// Restrict results to those with matching ID
					.filter(e -> entityId.equals(e.getCalendarId()))
					// Return single result, or null
					.findFirst().orElse(null));
			final List<Rite> sortedList = new ArrayList<>(rs);
			Collections.sort(sortedList);
			return sortedList;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void initModel(
			@NotNull HttpSession session, 
			Model model, 
			User user) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		final Long calendarId = (user != null && user.getCalendarId() != null) 
				? Optional.ofNullable((Long) user.getCalendarId()).orElse(null)
				: null;
		final Rite foundRite = BaseSearchableDtoServiceHelper.populateMenuOptions(
				model,
				// Parent drop-down fields
				calendarId,
				(List<Calendar>) model.getAttribute(ModelAttributeEnum.CALENDAR_LIST.getValue()),
				listKeys(CalendarRiteEnum.getCalendarRiteMap()),
				ModelAttributeEnum.CALENDAR_LIST.getValue(),
				Calendar.class,
				// Child drop-down fields
				lookupById(calendarId),
				ModelAttributeEnum.RITE_LIST.getValue(),
				Rite.class,
				// Error messages
				initModelMissingSession);

		// Automatically set User Rite ID for single child matching parent.
		if (foundRite != null) {
			user.setRiteId(foundRite.getRiteId());
		}
	}

	@Value("${calendarriteservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${calendarriteservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${calendarriteservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${calendarriteservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${calendarriteservice.lookupbyid.session}")
	private String lookupByIdMissingSession;

	@Value("${calendarriteservice.lookupbyid.entitylist}")
	private String lookupByIdMissingEntityList;

	@Value("${calendarriteservice.initmodel.session}")
	private String initModelMissingSession;
}
