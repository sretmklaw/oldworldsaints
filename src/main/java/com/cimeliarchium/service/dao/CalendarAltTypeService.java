package com.cimeliarchium.service.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.CalendarAltType;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.CalendarAltTypeRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
public class CalendarAltTypeService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,CalendarAltType>,
				BaseOneToOneMapServiceIF<Long,CalendarAltType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CalendarAltTypeService.class);

	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.CALENDAR_ALT_TYPE_MAP_BY_ID.getValue();

	private CalendarAltTypeRepository repo;

	@Autowired
	public CalendarAltTypeService(CalendarAltTypeRepository repo) throws IOException {
		this.repo = repo;
	}

	@Override
	public List<CalendarAltType> query() {

		return this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "calendarAltTypeId"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long,CalendarAltType> map() throws AppException {

		return ((List<CalendarAltType>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								CalendarAltType::getCalendarAltTypeId, // Key
								calendarAltType -> calendarAltType)); // Value
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					this.map(), 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Long, CalendarAltType> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	@Override
	public void initModel(
			@NotNull HttpSession session, 
			Model model, 
			User user) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		model.addAttribute(
				ModelAttributeEnum.CALENDAR_ALT_TYPE_LIST.getValue(),
				new TreeSet<CalendarAltType>(this.getCachedValues(session).values()));
	}

	@Value("${calendaralttypeservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${calendaralttypeservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${calendaralttypeservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${calendaralttypeservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${calendaralttypeservice.initmodel.session}")
	private String initModelMissingSession;

}
