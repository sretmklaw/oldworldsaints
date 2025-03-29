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
import com.cimeliarchium.model.dao.Creed;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.CreedRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class CreedService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,Creed>,
				BaseOneToOneMapServiceIF<Long,Creed> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreedService.class);

	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.CREED_MAP_BY_ID.getValue();

	private CreedRepository repo;

	@Autowired
	public CreedService(CreedRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Creed> query() {

		return this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "creedId"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long,Creed> map() throws AppException {

		return ((List<Creed>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								Creed::getCreedId, // Key
								creed -> creed)); // Value
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
	public Map<Long, Creed> getCachedValues(
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
				ModelAttributeEnum.CREED_LIST.getValue(),
				new TreeSet<Creed>(this.getCachedValues(session).values()));
	}

	@Value("${creedservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${creedservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${creedservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${creedservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${creedservice.initmodel.session}")
	private String initModelMissingSession;

}
