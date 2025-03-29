package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Constellation;
import com.cimeliarchium.model.dao.Star;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.ConstellationStarDto;
import com.cimeliarchium.service.BaseStaticMultiMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class ConstellationStarService extends SessionAttributeHelperService
		implements BaseSearchableDTOServiceIF<Constellation,Star>,
				BaseStaticMultiMapServiceIF<Constellation,Star> {

	private static final String DETAIL_DELIMITER = "Hence its association with ";
	private static final Logger LOGGER = LoggerFactory.getLogger(ConstellationStarService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.CONSTELLATION_STAR_MAP.getValue();
	private static final String SESSION_ATTRIBUTE_NAME_BY_ID = SessionAttributeEnum.ASTERISM_MAP_BY_STAR_ID.getValue();
	private static final String STORED_PROCEDURE_NAME = "ConstellationStarMappingQuery";

	private List<ConstellationStarDto> constellationStarQueryResults;
	private Map<Constellation, List<Star>> constellationStarMap;
	private Map<Long, List<Constellation>> asterismMapByStarId;

	@Autowired
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public void query() throws AppException {

		constellationStarQueryResults = (List<ConstellationStarDto>) super.throwIfMissing(
				entityManager.createNamedStoredProcedureQuery(STORED_PROCEDURE_NAME).getResultList(), 
				mapMissingResultList);
		LOGGER.info("{} returned {} results", STORED_PROCEDURE_NAME, constellationStarQueryResults.size());
	}

	@Override
	public void map() throws AppException {
		mapStarsByConstellation();
		mapAsterismsByStarId();
	}

	public void mapStarsByConstellation() {
		this.constellationStarMap = new TreeMap<Constellation, List<Star>>(
				// Obtain non-null results list by querying
				constellationStarQueryResults.stream()
				// Restrict results to those with non-null ID
				.filter(e -> e.getConstellation() != null)
				// Sort by ID
				.sorted((e1, e2) -> e1.getConstellation().compareTo(e2.getConstellation()))
				// Partition into sub-lists by Constellation
				.collect(Collectors.groupingBy(grp -> grp.getConstellation(), 
						Collectors.mapping(
								map -> map.getStar(), // Key
								Collectors.toList())))); // Value
	}

	public void mapAsterismsByStarId() {
		this.asterismMapByStarId = new TreeMap<>();
		for (ConstellationStarDto dto : constellationStarQueryResults) {
			final Long starId = dto.getStar().getStarId();
			if (starId != null) {
				final Constellation constellation = dto.getConstellation();
				if (constellation.getConstellationId() != null) {
					asterismMapByStarId.computeIfAbsent(starId, n -> {
						return new LinkedList<>();
					}).add(constellation);
				}
			}
		}
	}

	@Override
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			BaseMultiMapServiceHelper.addSessionAttribute(
					LOGGER,
					constellationStarMap, 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_NAME_BY_ID)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					asterismMapByStarId, 
					session, 
					SESSION_ATTRIBUTE_NAME_BY_ID);
		}
		return hasUpdate;
	}

	@Override
	public Map<Constellation, List<Star>> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseMultiMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	public Map<Long, Constellation> getCachedValuesById(
			@NotNull HttpSession session) throws AppException {

		return this.getCachedValues(session).keySet().stream()
				.collect(Collectors.toMap(Constellation::getConstellationId, Function.identity()));
	}

	@Override
	public List<Constellation> listKeys(
			@NotNull Map<Constellation, List<Star>> entityMap) throws AppException {

		return entityMap.keySet().stream()
				.collect(Collectors.toList());
	}

	@Override
	public List<Star> lookupById(
			@NotNull HttpSession session,
			@NotNull Long entityId) throws AppException {

		final Map<Constellation, List<Star>> entityMap = this.getCachedValues(session);
		final List<Star> rs = new ArrayList<>();
		// Restrict results to those with matching Constellation ID, where defined
		for (Map.Entry<Constellation, List<Star>> entry : entityMap.entrySet()) {
			final Constellation constellation = entry.getKey();
			final List<Star> starList = entry.getValue();
			if (entityId != null && entityId.equals(constellation.getConstellationId())) {
				rs.addAll(starList);
			}
		}
		Collections.sort(rs);
		return rs;
	}

	/**
	 * Used when populating the search result header for an individual Star.
	 * 
	 * @param session the HttpSession
	 * @param model the Model
	 * @param id the Location ID
	 * @param entityMap the Map of Constellations by Star ID
	 * @throws AppException
	 */
	public void populateSearchHeader(
			@NotNull HttpSession session,
			Model model, 
			@NotNull Long id,
			@NotNull Map<Long,List<Constellation>> entityMap) throws AppException {

		if (id != null && entityMap != null) {
			List<Constellation> asterismList = entityMap.get(id);
			if (asterismList != null) {
				model.addAttribute(ModelAttributeEnum.STAR_ASTERISM_LIST.getValue(), asterismList);
			}
		}
	}

	/**
	 * Utilizes previously-cached values from initializer inside ConstellationStarService.
	 * 
	 * @param session the HttpSession
	 * @return asterismMapByStarId
	 * @throws AppException
	 */
	public Map<Long, List<Constellation>> getCachedValuesByStarId(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME_BY_ID, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	@Override
	@Deprecated
	public void initModel(
			@NotNull HttpSession session, 
			Model model, 
			User user) throws AppException {
		// Intentionally, do nothing.
	}

	public void initModel(
			@NotNull HttpSession session, 
			Model model,
			User user,
			Constellation constellation) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		final Long calendarId = user.getCalendarId();
		constellation.setConstellationDisplayName(getConstellationNameFor(constellation, calendarId));
		constellation.setConstellationDisplayDetail(getConstellationDetailFor(constellation, calendarId));
		model.addAttribute(ModelAttributeEnum.ZODIAC_SIGN.getValue(), constellation);
	}

	/**
	 * Method used to populate Stars belonging to Constellations as drop-down menu options 
	 * from in-memory entity mappings.
	 * 
	 * @param session the HttpSession
	 * @param model the Model
	 * @throws AppException 
	 */
	public void populateConstellationMenuOptions(
			@NotNull HttpSession session, 
			Model model) throws AppException {

		if (model != null && model.containsAttribute(ModelAttributeEnum.CONSTELLATION_LIST.getValue())) {
			return;
		}
		super.throwIfMissing(session, populateConstellationMenuOptionsMissingSession);
		BaseSearchableDtoServiceHelper.addModelAttribute(
				model, 
				new ArrayList<>(this.getCachedValues(session).keySet()), 
				ModelAttributeEnum.CONSTELLATION_LIST.getValue(), 
				Constellation.class);
	}

	/**
	 * Conditionally return different values based on User Calendar
	 * 
	 * @param constellation the Constellation
	 * @param calendarId the User Calendar ID
	 * @return constellationName
	 */
	public String getConstellationNameFor(Constellation constellation, Long calendarId) {

		if (calendarId == calendarHebrewId) {
			return constellation.getConstellationNameHebrew();
		} else if (calendarId == calendarHijriId) {
			return constellation.getConstellationNameArabic();
		} else if (calendarId == calendarJulianId) {
			return constellation.getConstellationNameGreek();
		} else if (calendarId == calendarGregorianId) {
			return constellation.getConstellationNameLatin();
		} else {
			StringJoiner sj = new StringJoiner("%")
				.add(constellation.getConstellationNameLatin())
				.add(constellation.getConstellationNameGreek())
				.add(constellation.getConstellationNameArabic())
				.add(constellation.getConstellationNameHebrew());
			return sj.toString().trim();
		}
	}

	/**
	 * Conditionally return different values based on User Calendar
	 * 
	 * @param constellation the Constellation
	 * @param calendarId the User Calendar ID
	 * @return constellationDetail
	 */
	public String getConstellationDetailFor(Constellation constellation, Long calendarId) {

		final StringBuilder sb = new StringBuilder()
				.append(constellation.getConstellationDetail());
		if (calendarId == calendarHebrewId) {
			return sb.append("%")
					.append(DETAIL_DELIMITER)
					.append(constellation.getConstellationDetailJewish())
					.toString();
		} else if (calendarId == calendarHijriId) {
			return sb.append("%")
					.append(DETAIL_DELIMITER)
					.append(constellation.getConstellationDetailIslamic())
					.toString();
		} else if (calendarId == calendarGregorianId || calendarId == calendarJulianId) {
			return sb.append("%")
					.append(DETAIL_DELIMITER)
					.append(constellation.getConstellationDetailChristian())
					.toString();
		} else {
			final StringJoiner sj = new StringJoiner("%").add("")
				.add(constellation.getConstellationDetailJewish())
				.add(constellation.getConstellationDetailChristian())
				.add(constellation.getConstellationDetailIslamic());
			return sb.append(sj.toString().trim())
					.toString();
		}
	}

	@Value("${constellationservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${constellationservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${constellationservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${constellationservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${constellationservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${constellationservice.populateconstellationmenuoptions.session}")
	private String populateConstellationMenuOptionsMissingSession;

	@Value("${calendar.gregorian.id}")
	private Long calendarGregorianId;

	@Value("${calendar.julian.id}")
	private Long calendarJulianId;

	@Value("${calendar.hijri.id}")
	private Long calendarHijriId;

	@Value("${calendar.hebrew.id}")
	private Long calendarHebrewId;
}
