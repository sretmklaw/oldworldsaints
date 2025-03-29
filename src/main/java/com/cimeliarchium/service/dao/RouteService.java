package com.cimeliarchium.service.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Route;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.service.dto.CalendarMonthDayService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class RouteService extends SessionAttributeHelperService {

	private static final String SESSION_ATTRIBUTE_NAME_BY_LOCATION_ID = SessionAttributeEnum.ROUTE_MAP_BY_LOCATION_ID.getValue();
	
	private static final String SESSION_ATTRIBUTE_NAME_BY_ID = SessionAttributeEnum.ROUTE_MAP_BY_ID.getValue();

	CalendarMonthDayService calendarMonthDayService;

	@Autowired
	public RouteService(CalendarMonthDayService calendarMonthDayService) {
		this.calendarMonthDayService = calendarMonthDayService;
	}

	/**
	 * Utilizes previously-cached values from initializer inside NationLocationService.
	 * 
	 * @param session the HttpSession
	 * @return routeMapById
	 * @throws AppException
	 */
	public Map<Long, List<Route>> getCachedValuesByLocationId(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME_BY_LOCATION_ID, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	/**
	 * Utilizes previously-cached values from initializer inside NationLocationService.
	 * 
	 * @param session the HttpSession
	 * @return routeMapById
	 * @throws AppException
	 */
	public Map<Long, Route> getCachedValuesById(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME_BY_ID, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	/**
	 * Used when calling the Controller method to obtain a list of all available Routes.
	 * 
	 * @param session
	 * @param model
	 * @param user
	 * @throws AppException
	 */
	public void initModel(
			@NotNull HttpSession session, 
			Model model, 
			User user) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		model.addAttribute(
				ModelAttributeEnum.ROUTE_LIST.getValue(),
				new TreeSet<Route>(this.getCachedValuesByLocationId(session).values().stream()
						.flatMap(Collection::stream)
						.collect(Collectors.toList())));
	}

	/**
	 * Used when populating the search result header for an individual Location.
	 * 
	 * @param model the Model
	 * @param id the Location ID
	 * @param entityMap the Map of Routes by Location ID
	 * @throws AppException
	 */
	public void populateSearchHeader(
			Model model, 
			@NotNull Long id,
			@NotNull Map<Long,List<Route>> entityMap) throws AppException {

		if (id != null && entityMap != null) {
			List<Route> routeList = entityMap.get(id);
			if (routeList != null) {
				model.addAttribute(ModelAttributeEnum.LOCATION_ROUTE_LIST.getValue(), routeList);
			}
		}
	}

	@Value("${routeservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${routeservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${routeservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${routeservice.populatesearchheader.user}")
	private String populateSearchHeaderMissingUser;

	@Value("${calendar.all.id}")
	private Long calendarAllId;
}
