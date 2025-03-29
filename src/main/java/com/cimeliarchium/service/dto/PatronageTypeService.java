package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import com.cimeliarchium.model.dao.PatronageSubtype;
import com.cimeliarchium.model.dao.PatronageType;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.PatronageTypeDto;
import com.cimeliarchium.service.BaseStaticMultiMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;
import com.google.common.collect.ImmutableList;

@Service
@PropertySource("classpath:/errorcode.properties")
public class PatronageTypeService extends SessionAttributeHelperService
		implements BaseSearchableDTOServiceIF<PatronageType,PatronageSubtype>,
				BaseStaticMultiMapServiceIF<PatronageType,PatronageSubtype> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatronageTypeService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.PATRONAGE_TYPE_MAP.getValue();
	private static final String STORED_PROCEDURE_NAME = "PatronageTypeMappingQuery";

	private List<PatronageTypeDto> patronageTypeQueryResults;
	private Map<PatronageType, List<PatronageSubtype>> patronageTypeSubtypeMap;

	@Autowired
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public void query() throws AppException {

		patronageTypeQueryResults = (List<PatronageTypeDto>) super.throwIfMissing(
				entityManager.createNamedStoredProcedureQuery(STORED_PROCEDURE_NAME).getResultList(), 
				mapMissingResultList);
		LOGGER.info("{} returned {} results", STORED_PROCEDURE_NAME, patronageTypeQueryResults.size());
	}

	@Override
	public void map() throws AppException {

		this.patronageTypeSubtypeMap = new TreeMap<PatronageType, List<PatronageSubtype>>(
				// Obtain non-null results list by querying
				patronageTypeQueryResults.stream()
				// Restrict results to those with non-null ID
				.filter(e -> e.getPatronageType() != null)
				// Sort by ID
				.sorted((e1, e2) -> e1.getPatronageType().compareTo(e2.getPatronageType()))
				// Partition into sub-lists by Patronage Type
				.collect(Collectors.groupingBy(grp -> grp.getPatronageType(), 
						Collectors.mapping(
								map -> map.getPatronageSubtype(), // Key
								Collectors.toList())))); // Value
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
					patronageTypeSubtypeMap,
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<PatronageType, List<PatronageSubtype>> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseMultiMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	public Map<Long, PatronageType> getCachedValuesById(
			@NotNull HttpSession session) throws AppException {

		return this.getCachedValues(session).keySet().stream()
				.collect(Collectors.toMap(PatronageType::getPatronageTypeId, Function.identity()));
	}

	@Override
	public List<PatronageType> listKeys(
			@NotNull Map<PatronageType, List<PatronageSubtype>> entityMap) throws AppException {

		return entityMap.keySet().stream()
				.collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PatronageSubtype> lookupById(
			@NotNull HttpSession session,
			Long entityId) throws AppException {

		super.throwIfMissing(session, lookupByIdMissingSession);
		final Map<PatronageType, List<PatronageSubtype>> entityMap = this.getCachedValues(session);
		// Nation ID can be null on page load, before selection is made.
		if (entityId != null) {
			// Obtain mapped value by entity key, after first matching on ID
			final List<PatronageSubtype> rs = entityMap.get(
					// Obtain non-null match for ID from key list
					((List<PatronageType>) super.throwIfMissing(
							this.listKeys(entityMap), lookupByIdMissingEntityList)).stream()
					// Restrict results to those with matching ID
					.filter(e -> entityId.equals(e.getPatronageTypeId()))
					// Return single result, or null
					.findFirst().orElse(null));
			final List<PatronageSubtype> sortedList = new ArrayList<>(rs);
			Collections.sort(sortedList);
			return sortedList;
		} else {
			return ImmutableList.of();
		}
	}

	@Override
	public void initModel(
			@NotNull HttpSession session,
			Model model, 
			User user)
			throws AppException {

		super.throwIfMissing(session, initModelMissingSession);
		model.addAttribute(ModelAttributeEnum.PATRONAGE_TYPE_LIST.getValue(), 
				new LinkedList<PatronageType>(this.getCachedValues(session).keySet()));
	}

	@Value("${patronagetypeservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${patronagetypeservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${patronagetypeservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${patronagetypeservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${patronagetypeservice.lookupbyid.session}")
	private String lookupByIdMissingSession;

	@Value("${patronagetypeservice.lookupbyid.entitylist}")
	private String lookupByIdMissingEntityList;

	@Value("${patronagetypeservice.initmodel.session}")
	private String initModelMissingSession;
}
