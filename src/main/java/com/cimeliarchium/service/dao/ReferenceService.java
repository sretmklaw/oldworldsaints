package com.cimeliarchium.service.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Reference;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.ReferenceRepository;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class ReferenceService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,Reference> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceService.class);

	private static final String SESSION_ATTRIBUTE_BY_TITLE_NAME = SessionAttributeEnum.REFERENCE_MAP_BY_TITLE.getValue();
	private static final String SESSION_ATTRIBUTE_BY_ID_NAME = SessionAttributeEnum.REFERENCE_MAP_BY_ID.getValue();

	private Map<Long,Reference> referenceMap;
	private Map<String,List<Reference>> referenceMapByTitle;

	private ReferenceRepository repo;

	@Autowired
	public ReferenceService(ReferenceRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Reference> query() {

		final List<Reference> queryResults = this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "referenceId"));
		LOGGER.info("ReferenceRepository returned {} results", queryResults.size());
		return queryResults;
	}

	public void map() throws AppException {

		final Map<Long,Reference> referenceByIdMap = new HashMap<Long,Reference>();
		final Map<String,List<Reference>> referenceVolumeByTitleMap = new HashMap<String,List<Reference>>();
		for (Reference ref : this.query()) {
			if (ref != null && ref.getReferenceId() != null) {
				referenceByIdMap.put(ref.getReferenceId(), ref);
				referenceVolumeByTitleMap.computeIfAbsent(ref.getReferenceName(), n -> { 
					return new LinkedList<Reference>();
				}).add(ref);
			}
		}
		this.referenceMap = referenceByIdMap;
		this.referenceMapByTitle = referenceVolumeByTitleMap;
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
					referenceMap, 
					session, 
					SESSION_ATTRIBUTE_BY_ID_NAME);
		}
		if (!super.isInitialized(session, SESSION_ATTRIBUTE_BY_TITLE_NAME)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					referenceMapByTitle, 
					session, 
					SESSION_ATTRIBUTE_BY_TITLE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Long, Reference> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_BY_ID_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	/**
	 * Method used to obtain previously-initialized listing of unique Reference Volumes for each Title.
	 * 
	 * @param session the HttpSession
	 * @return referenceMapByTitle
	 * @throws AppException
	 */
	public Map<String, List<Reference>> getCachedValuesByTitle(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				SESSION_ATTRIBUTE_BY_TITLE_NAME,
				getCachedValuesByTitleMissingSession,
				getCachedValuesByTitleNotFound);
	}

	@Override
	@Deprecated
	public void initModel(
			HttpSession session, 
			Model model, 
			User user) throws AppException {
		// Intentionally, do nothing.
	}

	/**
	 * Method used to return a listing of References applicable to a given Title
	 * 
	 * @param session the HttpSession
	 * @param referenceTitle the Reference Name
	 * @return referenceList
	 * @throws AppException
	 */
	public List<Reference> lookupByTitle(
			@NotNull HttpSession session, 
			String referenceTitle) throws AppException {

		final Map<String, List<Reference>> entityMap = this.getCachedValuesByTitle(session);
		super.throwIfMissing(entityMap, lookupByTitleMissingEntityMap);
		return entityMap.get(referenceTitle);
	}

	/**
	 * Method used to obtain a list of all current Entity IDs.
	 * 
	 * @param session the HttpSession
	 * @return idList
	 * @throws AppException 
	 */
	public List<Long> getIdList(
			@NotNull HttpSession session) throws AppException {

		return this.getCachedValues(session).values().stream()
				.filter(e -> e != null)
				.map(e -> e.getReferenceId())
				.collect(Collectors.toList());
	}

	@Value("${referenceservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${referenceservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${referenceservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${referenceservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${referenceservice.getcachedvaluesbytitle.session}")
	private String getCachedValuesByTitleMissingSession;

	@Value("${referenceservice.getcachedvaluesbytitle.notfound}")
	private String getCachedValuesByTitleNotFound;

	@Value("${referenceservice.lookupbytitle.entitymap}")
	private String lookupByTitleMissingEntityMap;

	@Value("${referenceservice.initmodel.session}")
	private String initModelMissingSession;

}
