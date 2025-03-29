package com.cimeliarchium.service.dao;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.enums.StatusCodeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.RequestRepository;
import com.cimeliarchium.service.BaseServiceIF;
import com.cimeliarchium.service.dto.UserSessionInfoService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class RequestService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF<Long,Request>,
				BaseServiceIF<Long,Request> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestService.class);
	private static final String BY_TYPE_SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.REQUEST_MAP_BY_TYPE.getValue();
	private static final String BY_ID_SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.REQUEST_MAP_BY_ID.getValue();
	private static final String BUGFIX = "Bugfix";
	private static final String ENTRY = "Entry";

	private UserSessionInfoService userSessionInfoService;
	private RequestRepository repo;

	@Autowired
	public RequestService(UserSessionInfoService userSessionInfoService, RequestRepository repo) {
		this.userSessionInfoService = userSessionInfoService;
		this.repo = repo;
	}

	@Override
	public List<Request> query() {

		return this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "requestId"));
	}

	public Map<Long, List<Request>> map() throws AppException {

		// Result List may be null, if there are no open Requests.
		final List<Request> resultList = this.query();
		return (resultList != null) 
				? new TreeMap<Long, List<Request>>(
						resultList.stream()
						// Restrict results to those with non-null time-stamp
						.filter(e -> e.getRequestTimestamp() != null)
						// Sort by time-stamp
						.sorted((e1, e2) -> e1.getRequestTimestamp().compareTo(e2.getRequestTimestamp()))
						// Partition into sub-lists by Request Type
						.collect(Collectors.groupingBy(
								reqType -> reqType.getRequestTypeId(), 
								Collectors.mapping(
										req -> req, // Key
										Collectors.toList())))) // Value
				// Otherwise, return empty mapping
				: new TreeMap<Long, List<Request>>();
	}

	public Map<Long, Request> mapById() throws AppException {

		// Result List may be null, if there are no open Requests.
		final List<Request> resultList = this.query();
		return (resultList != null) 
				? new TreeMap<Long, Request>(
						resultList.stream()
						// Restrict results to those with non-null time-stamp
						.filter(e -> e.getRequestTimestamp() != null)
						// Sort by time-stamp
						.sorted((e1, e2) -> e1.getRequestTimestamp().compareTo(e2.getRequestTimestamp()))
						// Partition into sub-lists by Request Type
						.collect(Collectors.toMap(
								rq -> rq.getRequestId(), 
								req -> req)))
				// Otherwise, return empty mapping
				: new TreeMap<Long, Request>();
	}

	@Override
	@Deprecated
	public Boolean initCache(HttpSession session) throws AppException {
		return null; // Intentionally, do nothing.
	}

	public void initCacheByType(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		BaseOneToOneMapServiceHelper.addSessionAttribute(
				LOGGER,
				this.map(), 
				session, 
				BY_TYPE_SESSION_ATTRIBUTE_NAME);
	}

	public Boolean initCacheById(
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(session, initCacheMissingSession);
		Boolean hasUpdate = false;
		if (!super.isInitialized(session, BY_ID_SESSION_ATTRIBUTE_NAME)) {
			hasUpdate = true;
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					this.mapById(), 
					session, 
					BY_ID_SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	@Deprecated
	public Map<Long, Request> getCachedValues(HttpSession session) throws AppException {
		return null;
	}

	public Map<Long, Request> getCachedValuesByType(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				BY_TYPE_SESSION_ATTRIBUTE_NAME,
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	public Map<Long, Request> getCachedValuesById(
			@NotNull HttpSession session) throws AppException {

		return BaseOneToOneMapServiceHelper.getSessionAttribute(
				session,
				BY_ID_SESSION_ATTRIBUTE_NAME,
				getCachedValuesMissingSession,
				getCachedValuesNotFound);
	}

	/**
	 * Method used to submit a Request for add or update.
	 * 
	 * @param session the HttpSession
	 * @param request the Request
	 * @return errorMessages whether or not the Request was processed successfully
	 * @throws AppException
	 */
	public String create(@NotNull HttpSession session, Request request) throws AppException {

		final Long requestTypeId = request.getRequestTypeId();		
		final String requestTimestamp = OffsetDateTime.now(ZoneOffset.UTC).toString();
		String errorMessages = null;
		LOGGER.debug("Creating {} request with ID {} as of {}", 
				(requestTypeId != null && requestTypeId == requestBugfixId) ? BUGFIX : ENTRY,
				request.getRequestId(),
				requestTimestamp);
		try {
			// Persist the value in the database
			request.setRequestTimestamp(requestTimestamp);
			request.setCaptcha(null);
			// Update the request object to reference the database-assigned ID.
			final Request createdRequest = repo.save(request);
			request.setRequestId(createdRequest.getRequestId());
			// Also, update cached session variables to show the new Request for this user
			final Map<Long, Request> updatedRequestCacheById = this.getCachedValuesById(session);
			updatedRequestCacheById.put(request.getRequestId(), request);
			BaseOneToOneMapServiceHelper.addSessionAttribute(
					LOGGER,
					updatedRequestCacheById, 
					session, 
					BY_ID_SESSION_ATTRIBUTE_NAME);
		} catch (Exception e) {
			errorMessages = DisplayKeyEnum.REQUEST_FAILURE.getValue();
		}
		return errorMessages;
	}

	/**
	 * Method used to perform batch update Request statuses
	 * 
	 * @param ids the listing of entity IDs
	 * @param session the HttpSession
	 * @return message
	 * @throws AppException
	 */
	public List<Long> closeRequests(
			@NotNull List<Long> ids, 
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(ids, updateMissingIds);
		super.throwIfMissing(session, updateMissingSession);

		final List<Long> updateIds = new ArrayList<>();
		if (!ids.isEmpty()) {
			for (Long id : ids) {

				LOGGER.info("Closing request ID {}", id);
				final Map<Long, Request> cachedRequestList = this.getCachedValuesById(session);
				Request cachedRequest = cachedRequestList.get(id);
				try {
					// Only clear Request from cache where one is found to exist
					if (cachedRequest != null) {
						userSessionInfoService.decrementUserRequestCount(session, cachedRequest);
						repo.delete(cachedRequest);
					}
					// Also update the cached list of Requests to reflect its removal in the UI
					cachedRequestList.remove(id);
					BaseOneToOneMapServiceHelper.addSessionAttribute(
							LOGGER,
							cachedRequestList,
							session, 
							BY_ID_SESSION_ATTRIBUTE_NAME);
					LOGGER.info(DisplayKeyEnum.REQUEST_SUCCESS.getValue());
					updateIds.add(id);
				} catch (Exception e) {
					LOGGER.error(DisplayKeyEnum.REQUEST_FAILURE.getValue());
				}
			}
		}
		return updateIds;
	}

	@Override
	@Deprecated
	public void initModel(HttpSession session, Model model, User user) throws AppException { 
		// Intentionally, do nothing.
	}

	/**
	 * Because Spring Forms does not enforce strict typing of numeric inputs,
	 * this method is included to handle casting user-provided Strings to Long values,
	 * and wraps exceptions due to invalid numeric format in application-specific error code.
	 * 
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Long parseId(String id) throws AppException {
		try {
			return Long.valueOf(id);
		} catch (NumberFormatException ex) {
			throw new AppException(parseIdType);
		}
	}

	/**
	 * Request-specific Model initializer
	 * 
	 * @param session the HttpSession
	 * @param model the Model
	 * @param user the User
	 * @throws AppException
	 */
	public void populateActiveRequestsForUser(
			@NotNull HttpSession session,
			Model model, 
			@NotNull User user) throws AppException {

		super.throwIfMissing(user, getActiveRequestsForUserMissingUser);

		final List<Request> currentUserActiveRequestList = this.getCachedValuesById(session).values().stream()
				.filter(req -> 
						req.getUserId() == user.getUserId() 
						&& req.getRequestTypeId() == requestEntryId
						&& StatusCodeEnum.RED.getValue().equals(req.getRequestStatusCode()))
				.collect(Collectors.toList());
		if (currentUserActiveRequestList.size() > 0) {
			model.addAttribute(ModelAttributeEnum.CURRENT_USER_ACTIVE_ENTRY_REQUEST_LIST.getValue(), 
					currentUserActiveRequestList);
		}
	}

	@Value("${requestservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${requestservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${requestservice.initmodel.request}")
	private String initModelMissingRequest;

	@Value("${requestservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${requestservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${requestservice.update.ids}")
	private String updateMissingIds;

	@Value("${requestservice.update.session}")
	private String updateMissingSession;

	@Value("${requestservice.commit.session}")
	private String commitMissingSession;

	@Value("${requestservice.commit.request}")
	private String commitMissingRequest;

	@Value("${requestservice.commit.error}")
	private String commitError;

	@Value("${requestservice.commit.commemoration}")
	private String commitMissingCommemoration;

	@Value("${requestservice.getactiverequestsforuser.user}")
	private String getActiveRequestsForUserMissingUser;

	@Value("${requestservice.parseid.type}")
	private String parseIdType;

	@Value("${calendar.gregorian.id}")
	private Long calendarGregorianId;

	@Value("${calendar.julian.id}")
	private Long calendarJulianId;

	@Value("${calendar.hijri.id}")
	private Long calendarHijriId;

	@Value("${calendar.hebrew.id}")
	private Long calendarHebrewId;

	@Value("${request.bugfix.id}")
	private Long requestBugfixId;

	@Value("${request.entry.id}")
	private Long requestEntryId;

	@Value("${featureflag.request.id}")
	private Long featureFlagRequestId;
}
