package com.cimeliarchium.service.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.ReadingTitleEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Reading;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.repository.dao.ReadingRepository;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;
import com.google.common.collect.ImmutableList;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties",
})
public class ReadingService extends SessionAttributeHelperService
		implements BaseCacheableDAOServiceIF<Long,Reading> {

	private static final String SESSION_ATTRIBUTE_BY_TITLE_NAME = SessionAttributeEnum.COMMEMORATION_READING_MAP_BY_TITLE.getValue();
	private static final String SESSION_ATTRIBUTE_BY_ID_NAME = SessionAttributeEnum.COMMEMORATION_READING_MAP_BY_ID.getValue();

	private ReadingRepository repo;

	@Autowired
	public ReadingService(ReadingRepository repo) {
		this.repo = repo;
	}

	public Boolean hasMatchForId(Long id) {

		return this.repo.findByReadingId(id) != null;
	}

	/**
	 * Method used to obtain previously-initialized listing of unique Readings by ID.
	 * 
	 * @param session the HttpSession
	 * @return readingMapById
	 * @throws AppException
	 */
	public Map<Long, Reading> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_BY_ID_NAME,
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	/**
	 * Method used to obtain previously-initialized listing of unique Readings for each Title.
	 * 
	 * @param session the HttpSession
	 * @return readingMapByTitle
	 * @throws AppException
	 */
	public Map<String, Set<Reading>> getCachedValuesByTitle(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_BY_TITLE_NAME,
				getCachedValuesByTitleMissingSession,
				getCachedValuesByTitleNotFound);
	}

	/**
	 * Method used to return a listing of Reading Titles applicable to a given Calendar
	 * 
	 * @param calendarId the Calendar ID
	 * @param isAdminUser toggle to display titles with prepended ID field
	 * @return readingTitleMap
	 */
	public Map<String, Map<Long, String>> lookupTitlesByCreedId(Long calendarId) {
		if (calendarId == calendarHebrewId) {
			return ReadingTitleEnum.getJewishReadingTitles();
		} else if (calendarId == calendarHijriId) {
			return ReadingTitleEnum.getIslamicReadingTitles();
		} else if (calendarId == calendarGregorianId || calendarId == calendarJulianId) {
			return ReadingTitleEnum.getChristianReadingTitles();
		} else {
			return ReadingTitleEnum.getAllReadingTitles();
		}
	}

	/**
	 * Method used to return a listing of Readings applicable to a given Title
	 * 
	 * @param session the HttpSession
	 * @param name the Reading title
	 * @return readingList
	 * @throws AppException
	 */
	public List<Reading> lookupByTitle(
			@NotNull HttpSession session, 
			String name) throws AppException {

		final Map<String, Set<Reading>> entityMap = this.getCachedValuesByTitle(session);
		super.throwIfMissing(entityMap, lookupByTitleMissingEntityMap);
		if (name != null && entityMap.containsKey(name)) {
			return new LinkedList<Reading>(entityMap.get(name));
		} else {
			return ImmutableList.of();
		}
	}

	@Override
	@Deprecated
	public List<Reading> list() throws AppException {
		return null;
	}

	@Override
	@Deprecated
	public Boolean initCache(HttpSession session) throws AppException { 
		return null; // Intentionally, do nothing.
	}

	@Override
	@Deprecated
	public void initModel(HttpSession session, Model model, User user) throws AppException { }

	@Override
	@Deprecated
	public Long getSearchOrRandomId(Long id, Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap, Long riteId) {
		return null; // Intentionally, do nothing.
	}

	@Value("${readingservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${readingservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${readingservice.getcachedvaluesbytitle.session}")
	private String getCachedValuesByTitleMissingSession;

	@Value("${readingservice.getcachedvaluesbytitle.notfound}")
	private String getCachedValuesByTitleNotFound;

	@Value("${readingservice.lookupbytitle.entitymap}")
	private String lookupByTitleMissingEntityMap;

	@Value("${calendar.gregorian.id}")
	private Long calendarGregorianId;

	@Value("${calendar.julian.id}")
	private Long calendarJulianId;

	@Value("${calendar.hijri.id}")
	private Long calendarHijriId;

	@Value("${calendar.hebrew.id}")
	private Long calendarHebrewId;

	@Value("${calendar.all.id}")
	private Long calendarAllId;
}
