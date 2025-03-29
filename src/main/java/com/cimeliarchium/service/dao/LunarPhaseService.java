package com.cimeliarchium.service.dao;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
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
import com.cimeliarchium.model.dao.CalendarDates;
import com.cimeliarchium.model.dao.LunarPhase;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.LunarPhaseRepository;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class LunarPhaseService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF<Long,LunarPhase> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LunarPhaseService.class);

	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.CURRENT_LUNAR_PHASE.getValue();

	private static final String SESSION_ATTRIBUTE_BY_ID_NAME = SessionAttributeEnum.LUNAR_PHASE_MAP_BY_ID.getValue();

	private Map<Long,LunarPhase> lunarPhaseMap;

	private LunarPhaseRepository repo;

	@Autowired
	public LunarPhaseService(LunarPhaseRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<LunarPhase> query() {

		final List<LunarPhase> queryResults = repo
				.findAll(Sort.by(Sort.Direction.ASC, "lunarPhaseId"));
		LOGGER.info("LunarPhaseRepository returned {} results", queryResults.size());
		return queryResults;
	}

	@SuppressWarnings("unchecked")
	public void map() throws AppException {

		this.lunarPhaseMap = ((List<LunarPhase>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								LunarPhase::getLunarPhaseId, // Key
								lunarPhase -> lunarPhase)); // Value
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_BY_ID_NAME)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					lunarPhaseMap, 
					session, 
					SESSION_ATTRIBUTE_BY_ID_NAME);
		}
		return hasUpdate;
	}

	/**
	 * Custom method to initialize both the current Lunar Phase, 
	 * as well as a mapping of Lunar Phases by ID.
	 * 
	 * @param session the HttpSession
	 * @param calendarDates the CalendarDates
	 * @throws AppException
	 */
	public void initCacheForCurrentDate(
		@NotNull HttpSession session,
		@NotNull CalendarDates calendarDates) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		super.throwIfMissing(calendarDates, initCacheMissingCalendarDates);
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					lunarPhaseMap, 
					session, 
					SESSION_ATTRIBUTE_BY_ID_NAME);
			session.setAttribute(SESSION_ATTRIBUTE_NAME, 
					this.getLunarPhaseForHijriDayOfMonth(session, calendarDates.getHijriDayOfMonth()));
			LOGGER.debug(
					SESSION_ATTRIBUTE_ADDED,
					SESSION_ATTRIBUTE_NAME);
		}
	}

	/**
	 * Method used to obtain the current Lunar Phase
	 * 
	 * @param session the HttpSession
	 * @return lunarPhase the current Lunar Phase
	 * @throws AppException
	 */
	public LunarPhase getCachedValueForCurrentDate(
			@NotNull HttpSession session) throws AppException {

		super.validateSessionAttributeParams(session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValueForCurrentDateMissingSession, 
				getCachedValueForCurrentDateNotFound);
		return (LunarPhase) session.getAttribute(SESSION_ATTRIBUTE_NAME);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, LunarPhase> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		super.validateSessionAttributeParams(session, 
				SESSION_ATTRIBUTE_BY_ID_NAME, 
				getCachedValuesMissingSession, 
				getCachedValuesNotFound);
		return (Map<Long, LunarPhase>) session.getAttribute(SESSION_ATTRIBUTE_BY_ID_NAME);
	}

	@Override
	@Deprecated
	public void initModel(HttpSession session, Model model, User user) { }

	/**
	 * Method used to display the Lunar Phase for the current date, or the passed-in Calendar Date.
	 * 
	 * @param session the HttpSession
	 * @param model the Model
	 * @param user the User
	 * @param calendarDates the CalendarDates
	 * @throws AppException
	 */
	public void initModel(
			@NotNull HttpSession session, 
			Model model,
			@NotNull User user,
			CalendarDates calendarDates) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		super.throwIfMissing(user, initModelMissingUser);
		final LunarPhase lunarPhase = (calendarDates != null)
				? this.getLunarPhaseForHijriDayOfMonth(
						session, 
						calendarDates.getHijriDayOfMonth())
				: this.getCachedValueForCurrentDate(session);
		lunarPhase.setLunarPhaseDisplayName(
				this.getLunarPhaseNameFor(lunarPhase, user.getCalendarId()));
		model.addAttribute(ModelAttributeEnum.LUNAR_PHASE.getValue(), lunarPhase);
	}

	/**
	 * Method used to retrieve Lunar Phase for a given Hijri date
	 * 
	 * @param session the HttpSession
	 * @param hijriDayOfMonth the Hijri calendar Day of Month
	 * @return lunarPhase
	 * @throws AppException
	 */
	private LunarPhase getLunarPhaseForHijriDayOfMonth(
			@NotNull HttpSession session,
			Integer hijriDayOfMonth) throws AppException {
	
		super.throwIfMissing(session, initCacheMissingSession);
		return this.getCachedValues(session).values().stream()
				.filter(lunarPhase -> lunarPhase.isLunarPhaseApplicableToHijriDayOfMonth(hijriDayOfMonth))
				.findFirst().orElse(null);
	}

	/**
	 * Conditionally return different values based on User Calendar
	 * 
	 * @param lunarPhase the LunarPhase
	 * @param calendarId the User Calendar ID
	 * @return lunarPhaseName
	 */
	public String getLunarPhaseNameFor(LunarPhase lunarPhase, Long calendarId) {

		if (calendarId == calendarHebrewId) {
			return lunarPhase.getLunarPhaseNameHebrew();
		} else if (calendarId == calendarHijriId) {
			return lunarPhase.getLunarPhaseNameArabic();
		} else if (calendarId == calendarJulianId) {
			return lunarPhase.getLunarPhaseNameGreek();
		} else if (calendarId == calendarGregorianId) {
			return lunarPhase.getLunarPhaseNameLatin();
		} else {
			StringJoiner sj = new StringJoiner("%");
			sj.add(lunarPhase.getLunarPhaseNameLatin())
				.add(lunarPhase.getLunarPhaseNameGreek())
				.add(lunarPhase.getLunarPhaseNameArabic())
				.add(lunarPhase.getLunarPhaseNameHebrew());
			return sj.toString().trim();
		}
	}

	@Value("${lunarphaseservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${lunarphaseservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${lunarphaseservice.initcache.calendardates}")
	private String initCacheMissingCalendarDates;

	@Value("${lunarphaseservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${lunarphaseservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${lunarphaseservice.getcachedvalueforcurrentdate.session}")
	private String getCachedValueForCurrentDateMissingSession;

	@Value("${lunarphaseservice.getcachedvalueforcurrentdate.notfound}")
	private String getCachedValueForCurrentDateNotFound;

	@Value("${lunarphaseservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${lunarphaseservice.initmodel.user}")
	private String initModelMissingUser;

	@Value("${lunarphaseservice.initmodel.calendardates}")
	private String initModelMissingCalendarDates;

	@Value("${calendar.gregorian.id}")
	private Long calendarGregorianId;

	@Value("${calendar.julian.id}")
	private Long calendarJulianId;

	@Value("${calendar.hijri.id}")
	private Long calendarHijriId;

	@Value("${calendar.hebrew.id}")
	private Long calendarHebrewId;
}
