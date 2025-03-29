package com.cimeliarchium.service.dto;

import java.util.ArrayList;
import java.util.Collections;
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

import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.PatronageSubtype;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.PatronageSubtypeDto;
import com.cimeliarchium.service.BaseStaticMultiMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/application.properties",
	"classpath:/errorcode.properties"
})
public class PatronageSubtypeService extends SessionAttributeHelperService
		implements BaseSearchableDTOServiceIF<PatronageSubtype,Patronage>,
				BaseStaticMultiMapServiceIF<PatronageSubtype,Patronage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatronageSubtypeService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.PATRONAGE_SUBTYPE_MAP.getValue();
	private static final String STORED_PROCEDURE_NAME = "PatronageSubtypeMappingQuery";

	private List<PatronageSubtypeDto> patronageSubtypeQueryResults;
	private Map<PatronageSubtype, List<Patronage>> patronageSubtypeMap;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	public PatronageSubtypeService() { }

	@Override
	@SuppressWarnings("unchecked")
	public void query() throws AppException {

		patronageSubtypeQueryResults = (List<PatronageSubtypeDto>) super.throwIfMissing(
				entityManager.createNamedStoredProcedureQuery(STORED_PROCEDURE_NAME).getResultList(), 
				mapMissingResultList);
		LOGGER.info("{} returned {} results", STORED_PROCEDURE_NAME, patronageSubtypeQueryResults.size());
	}

	@Override
	public void map() throws AppException {

		this.patronageSubtypeMap = new TreeMap<PatronageSubtype, List<Patronage>>(
				// Obtain non-null results list by querying
				patronageSubtypeQueryResults.stream()
				// Restrict results to those with non-null ID
				.filter(e -> e.getPatronageSubtype() != null)
				// Sort by ID
				.sorted((e1, e2) -> e1.getPatronageSubtype().compareTo(e2.getPatronageSubtype()))
				// Partition into sub-lists by Patronage Subtype
				.collect(Collectors.groupingBy(grp -> grp.getPatronageSubtype(), 
						Collectors.mapping(
								map -> map.getPatronage(), // Key
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
					patronageSubtypeMap, 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<PatronageSubtype, List<Patronage>> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseMultiMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	public Map<Long, PatronageSubtype> getCachedValuesById(
			@NotNull HttpSession session) throws AppException {

		return this.getCachedValues(session).keySet().stream()
				.collect(Collectors.toMap(PatronageSubtype::getPatronageSubtypeId, Function.identity()));
	}

	@Override
	public List<PatronageSubtype> listKeys(
			@NotNull Map<PatronageSubtype, List<Patronage>> entityMap) throws AppException {

		return entityMap.keySet().stream()
				.collect(Collectors.toList());
	}

	@Override
	public List<Patronage> lookupById(
			@NotNull HttpSession session,
			@NotNull Long entityId) throws AppException {

		final Map<PatronageSubtype, List<Patronage>> entityMap = this.getCachedValues(session);
		final List<Patronage> rs = new ArrayList<>();
		// Restrict results to those with matching Subtype ID, where defined
		for (Map.Entry<PatronageSubtype, List<Patronage>> entry : entityMap.entrySet()) {
			final PatronageSubtype subtype = entry.getKey();
			final List<Patronage> patronageList = entry.getValue();
			if (entityId != null && entityId.equals(subtype.getPatronageSubtypeId())) {
				rs.addAll(patronageList);
			}
		}
		Collections.sort(rs);
		return rs;
	}

	@Deprecated
	@Override
	public void initModel(HttpSession session, Model model, User user) throws AppException {
		// Intentionally, do nothing.
	}

	@Value("${patronagesubtypeservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${patronagesubtypeservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${patronagesubtypeservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${patronagesubtypeservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${patronagesubtypeservice.lookupbyid.entityid}")
	private String lookupByIdMissingEntityId;

	@Value("${patronagesubtypeservice.lookupbyid.entitylist}")
	private String lookupByIdMissingEntityList;

	@Value("${patronagetype.place.id}")
	private Long patronageTypePlaceId;

}
