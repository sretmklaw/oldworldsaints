package com.cimeliarchium.service.dao;

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
import com.cimeliarchium.model.dao.Inset;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.InsetRepository;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class InsetService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,Inset> {

	private static final Logger LOGGER = LoggerFactory.getLogger(InsetService.class);

	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.INSET_MAP_BY_ID.getValue();

	private Map<Long,Inset> insetMap;

	private InsetRepository repo;

	@Autowired
	public InsetService(InsetRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Inset> query() {

		final List<Inset> queryResults = this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "insetId"));
		LOGGER.info("InsetRepository returned {} results", queryResults.size());
		return queryResults;
	}

	@SuppressWarnings("unchecked")
	public void map() throws AppException {

		this.insetMap = ((List<Inset>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								Inset::getInsetId, // Key
								inset -> inset)); // Value
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
					insetMap, 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Long, Inset> getCachedValues(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session, 
				SESSION_ATTRIBUTE_NAME, 
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	@Deprecated
	@Override
	public void initModel(HttpSession session, Model model, User user) throws AppException {
		// Intentionally, do nothing.
	}

	public List<Inset> lookupById(
			@NotNull HttpSession session,
			@NotNull Long entityId) throws AppException {

		throwIfMissing(entityId, lookupByIdMissingEntityId);
		final Map<Long, Inset> entityMap = this.getCachedValues(session);
		// Obtain mapped value by entity key, after first matching on ID
		final List<Inset> rs = entityMap.values().stream()
				.filter(inset -> inset.getNationId().equals(entityId))
				.collect(Collectors.toList());
		return rs;
	}

	@Value("${insetservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${insetservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${insetservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${insetservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${insetservice.lookupbyid.entityid}")
	private String lookupByIdMissingEntityId;
}
