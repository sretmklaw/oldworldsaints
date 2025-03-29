package com.cimeliarchium.service.dao;

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
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.CalendarAltReason;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.CalendarAltReasonRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class CalendarAltReasonService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,CalendarAltReason>,
				BaseOneToOneMapServiceIF<Long,CalendarAltReason> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CalendarAltReasonService.class);

	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.CALENDAR_ALT_REASON_MAP_BY_ID.getValue();

	private CalendarAltReasonRepository repo;

	@Autowired
	public CalendarAltReasonService(CalendarAltReasonRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<CalendarAltReason> query() {

		return this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "calendarAltReasonId"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long,CalendarAltReason> map() throws AppException {

		return ((List<CalendarAltReason>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								CalendarAltReason::getCalendarAltReasonId, // Key
								calendarAltReason -> calendarAltReason)); // Value
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
	public Map<Long, CalendarAltReason> getCachedValues(
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
				ModelAttributeEnum.CALENDAR_ALT_REASON_LIST.getValue(),
				new TreeSet<CalendarAltReason>(this.getCachedValues(session).values()));
	}

	@Value("${calendaraltreasonservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${calendaraltreasonservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${calendaraltreasonservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${calendaraltreasonservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${calendaraltreasonservice.initmodel.session}")
	private String initModelMissingSession;

}
