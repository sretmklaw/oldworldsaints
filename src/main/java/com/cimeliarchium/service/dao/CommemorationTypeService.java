package com.cimeliarchium.service.dao;

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

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.CommemorationType;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.CommemorationTypeRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class CommemorationTypeService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,CommemorationType>,
				BaseOneToOneMapServiceIF<Long,CommemorationType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommemorationTypeService.class);

	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.COMMEMORATION_TYPE_MAP_BY_ID.getValue();

	private CommemorationTypeRepository repo;

	@Autowired
	public CommemorationTypeService(CommemorationTypeRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<CommemorationType> query() {

		return this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "commemorationTypeId"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long,CommemorationType> map() throws AppException {

		return ((List<CommemorationType>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								CommemorationType::getCommemorationTypeId, // Key
								commemorationType -> commemorationType)); // Value
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
	public Map<Long, CommemorationType> getCachedValues(
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
				ModelAttributeEnum.COMMEMORATION_TYPE_LIST.getValue(),
				new LinkedList<CommemorationType>(this.getCachedValues(session).values()));
	}

	@Value("${commemorationtypeservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${commemorationtypeservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${commemorationtypeservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${commemorationtypeservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${commemorationtypeservice.initmodel.session}")
	private String initModelMissingSession;
}
