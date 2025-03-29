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
import com.cimeliarchium.model.dao.LocationType;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.LocationTypeRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class LocationTypeService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,LocationType>,
				BaseOneToOneMapServiceIF<Long,LocationType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationTypeService.class);

	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.LOCATION_TYPE_MAP_BY_ID.getValue();

	private LocationTypeRepository repo;

	@Autowired
	public LocationTypeService(LocationTypeRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<LocationType> query() {

		return this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "locationTypeName"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long,LocationType> map() throws AppException {

		return ((List<LocationType>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								LocationType::getLocationTypeId, // Key
								locationType -> locationType)); // Value
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
	public Map<Long, LocationType> getCachedValues(
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
				ModelAttributeEnum.LOCATION_TYPE_LIST.getValue(),
				new TreeSet<LocationType>(this.getCachedValues(session).values()));
	}

	@Value("${locationtypeservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${locationtypeservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${locationtypeservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${locationtypeservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${locationtypeservice.initmodel.session}")
	private String initModelMissingSession;
}
