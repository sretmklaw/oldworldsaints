package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
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
import com.cimeliarchium.model.dao.Nation;
import com.cimeliarchium.model.dao.Region;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.NationRegionDto;
import com.cimeliarchium.service.BaseStaticMultiMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class NationRegionService extends SessionAttributeHelperService
		implements BaseSearchableDTOServiceIF<Nation,Region>,
				BaseStaticMultiMapServiceIF<Nation,Region> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NationRegionService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.NATION_REGION_MAP.getValue();
	private static final String STORED_PROCEDURE_NAME = "NationRegionMappingQuery";

	private List<NationRegionDto> nationRegionQueryResults;
	private Map<Nation, List<Region>> nationRegionMap;

	@Autowired
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public void query() throws AppException {

		nationRegionQueryResults = (List<NationRegionDto>) super.throwIfMissing(
				entityManager.createNamedStoredProcedureQuery(STORED_PROCEDURE_NAME).getResultList(), 
				mapMissingResultList);
		LOGGER.info("{} returned {} results", STORED_PROCEDURE_NAME, nationRegionQueryResults.size());
	}

	@Override
	public void map() throws AppException {

		this.nationRegionMap = new TreeMap<Nation, List<Region>>(
				// Obtain non-null results list by querying
				nationRegionQueryResults.stream()
				// Restrict results to those with non-null ID
				.filter(e -> e.getNation() != null)
				// Sort by ID
				.sorted((e1, e2) -> e1.getNation().compareTo(e2.getNation()))
				// Partition into sub-lists by Calendar
				.collect(Collectors.groupingBy(grp -> grp.getNation(), 
						Collectors.mapping(
								map -> map.getRegion(), // Key
								Collectors.toList())))); // Value
	}

	@Override
	public List<Nation> listKeys(
			@NotNull Map<Nation, List<Region>> entityMap) throws AppException {

		return entityMap.keySet().stream()
				// United Kingdom constituents England, N. Ireland, Scotland, Wales
				// are broken out as separate nations from a commemoration location standpoint,
				// but should not appear as separate nations on the user nationality drop-down.
				.filter(e -> !e.getNationCode().startsWith("GB-"))
				.collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Region> lookupById(
			@NotNull HttpSession session,
			Long entityId) throws AppException {

		super.throwIfMissing(session, lookupByIdMissingSession);
		final Map<Nation, List<Region>> entityMap = this.getCachedValues(session);
		// Nation ID can be null on page load, before selection is made.
		if (entityId != null) {
			// Obtain mapped value by entity key, after first matching on ID
			final List<Region> rs = entityMap.get(
					// Obtain non-null match for ID from key list
					((List<Nation>) super.throwIfMissing(
							this.listKeys(entityMap), lookupByIdMissingEntityList)).stream()
					// Restrict results to those with matching ID
					.filter(e -> entityId.equals(e.getNationId()))
					// Return single result, or null
					.findFirst().orElse(null));
			final List<Region> sortedList = new ArrayList<>(rs);
			Collections.sort(sortedList);
			return sortedList;
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initModel(
			@NotNull HttpSession session, 
			Model model, 
			User user) throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		final Long nationId = (user != null && user.getNationId() != null) 
				? Optional.ofNullable((Long) user.getNationId()).orElse(null)
				: null;
		final Region foundRegion = BaseSearchableDtoServiceHelper.populateMenuOptions(
				model,
				// Parent drop-down fields
				nationId,
				(List<Nation>) model.getAttribute(ModelAttributeEnum.NATION_LIST.getValue()),
				listKeys(this.getCachedValues(session)),
				ModelAttributeEnum.NATION_LIST.getValue(),
				Nation.class,
				// Child drop-down fields
				lookupById(session, nationId),
				ModelAttributeEnum.REGION_LIST.getValue(),
				Region.class,
				// Error messages
				initModelMissingSession);

		// Automatically set User Region ID for single child matching parent.
		if (foundRegion != null) {
			user.setRegionId(foundRegion.getRegionId());
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
					nationRegionMap, 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Nation, List<Region>> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseMultiMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	/**
	 * Method used to list only Old World-type Nations.
	 * 
	 * @param session the HttpSession
	 * @return resultsList
	 * @throws AppException 
	 */
	private List<Nation> listOldWorldNations(
			@NotNull HttpSession session) throws AppException {

		return this.getCachedValues(session).keySet().stream()
				.filter(n -> n.getIsOldWorld() == true)
				.collect(Collectors.toList());
	}

	/**
	 * Method used to populate Old World-type Nations as drop-down menu options 
	 * from in-memory entity mappings.
	 * 
	 * @param session the HttpSession
	 * @param model the Model
	 * @throws AppException 
	 */
	public void populateOldWorldNationMenuOptions(
			@NotNull HttpSession session, 
			Model model) throws AppException {

		if (model != null && model.containsAttribute(ModelAttributeEnum.NATION_LIST.getValue())) {
			return;
		}
		super.throwIfMissing(session, populateOldWorldNationMenuOptionsMissingSession);
		BaseSearchableDtoServiceHelper.addModelAttribute(
				model, 
				listOldWorldNations(session), 
				ModelAttributeEnum.NATION_LIST.getValue(), 
				Nation.class);
	}

	@Value("${nationregionservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${nationregionservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${nationregionservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${nationregionservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${nationregionservice.lookupbyid.session}")
	private String lookupByIdMissingSession;

	@Value("${nationregionservice.lookupbyid.entitylist}")
	private String lookupByIdMissingEntityList;

	@Value("${nationregionservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${nationregionservice.populateoldworldnationmenuoptions.session}")
	private String populateOldWorldNationMenuOptionsMissingSession;

	@Value("${nationregionservice.populateoldworldnationmenuoptions.model}")
	private String populateOldWorldNationMenuOptionsMissingModel;

}
