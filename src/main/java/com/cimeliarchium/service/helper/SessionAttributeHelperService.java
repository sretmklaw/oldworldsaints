package com.cimeliarchium.service.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Request;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dto.DayCommemorationDto;

@Service
public class SessionAttributeHelperService {

	@Autowired private EntityManager entityManager;

	protected static final Long ID_SEARCH_RANDOM_INDICATOR = 000L;
	protected static final String RELATED_PATRONAGE_SEARCH_PATH = "/search/patronage?byId=";
	protected static final String SESSION_ATTRIBUTE_ADDED = "Added attribute '{}' with {} values";
	protected static final String SESSION_ATTRIBUTE_UPDATED = "Updated attribute '{}: now has {} values, was {}";

	/**
	 * Method used to filter null input
	 * 
	 * @param input the input string
	 * @return filteredInput
	 */
	public static String filterInput(
			String input) {

		return (input == null || input.equals("")) 
				? DisplayKeyEnum.UNKNOWN.getValue() : input;
	}

	/**
	 * Determine the existence of a given Session Attribute
	 * 
	 * @param session the HttpSession
	 * @param attributeName the Session Attribute name
	 * @return isInitialized
	 * @throws AppException
	 */
	protected static boolean isInitialized(
			@NotNull HttpSession session, 
			@NotNull String attributeName) {

		return session.getAttribute(attributeName) != null;
	}

	/**
	 * This method will be used in a manner similar to Java's Object.requireNotNull(), 
	 * but it adds a predefined error code and other details to assist in troubleshooting
	 * 
	 * @param obj the Object to null-check
	 * @param errorCode the AppException message
	 * @param detail the parameter name
	 * @param signature the class and method signature
	 * @throws AppException
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Object throwIfMissing(
			Object obj, 
			String errorCode) throws AppException {

		if (obj == null 
				|| (obj instanceof String && ((String) obj).isEmpty())
				|| (obj instanceof Collection && ((Collection) obj).isEmpty())
				|| (obj instanceof List && ((List) obj).isEmpty())
				|| (obj instanceof Set && ((Set) obj).isEmpty())
				|| (obj instanceof Map && ((Map) obj).isEmpty())) {
			throw new AppException(errorCode);
		}
		return obj;
	}

	/**
	 * Method used to define Comparator used to sort lists by a specified key
	 * 
	 * @param key the sort column
	 * @return sortedList
	 */
	protected static <Key> Collector<Key,?,List<Key>> toSortedList(
			Comparator<? super Key> key) {

		return Collectors.collectingAndThen(
				Collectors.toCollection(ArrayList::new), lst->{ lst.sort(key); return lst; });
	}

	/**
	 * Verify the parameters required to retrieve a given Session Attribute
	 * 
	 * @param session the HttpSession
	 * @param attributeName the Session Attribute name
	 * @param missingSession the AppException message for missing Session
	 * @param notFound the AppException message for non-initialized Session Attribute
	 * @throws AppException
	 */
	protected static void validateSessionAttributeParams(
			@NotNull HttpSession session, 
			@NotNull String attributeName, 
			String missingSession,
			String notFound) throws AppException {

		if (!isInitialized(
				(HttpSession) throwIfMissing(session, missingSession), 
				attributeName)) {
			throw new AppException(notFound);
		} 
	}

	/**
	 * Inner class used to organize query parameters 
	 * for use with database stored procedures.
	 */
	public static class QueryParams {

		private String paramName;

		private Object paramVal;

		public static class Builder<T> {
			private String paramName;
			private T paramVal;
			public Builder<T> withParamName(String paramName) {
				this.paramName = paramName;
				return this;
			}
			public Builder<T> withParamVal(T paramVal) {
				this.paramVal = paramVal;
				return this;
			}
			public QueryParams build() {
				QueryParams params = new QueryParams();
				params.paramName = this.paramName;
				params.paramVal = this.paramVal;
				return params;
			}
		}

		public String getParamName() {
			return paramName;
		}

		public Object getParamVal() {
			return paramVal;
		}
	}

	/**
	 * Execute queries against predefined database stored procedures.
	 * 
	 * @param storedProcedure the stored procedure name
	 * @param dtoType the Type of DTO object
	 * @return dtoList
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public <Dto> List<Dto> queryForDto(
			String storedProcedure, 
			Class<Dto> dtoType) {

		StoredProcedureQuery query = this.entityManager
				.createNamedStoredProcedureQuery(storedProcedure);
		query.execute();
		return (List<Dto>) query.getResultList();
	}

	/**
	 * Execute queries with one or more parameters 
	 * against predefined database stored procedures.
	 * 
	 * @param storedProcedure the stored procedure name
	 * @param queryParams the QueryParams object to iterate
	 * @param dtoType the Type of DTO object
	 * @return dtoList
	 */
	@SuppressWarnings("unchecked")
	public <Dto> List<Dto> queryForDtosWithParams(
			String storedProcedure, 
			List<QueryParams> queryParams,
			Class<Dto> dtoType) {

		StoredProcedureQuery query = this.entityManager
				.createNamedStoredProcedureQuery(storedProcedure);
		for (QueryParams params : queryParams) {
			query.setParameter(
					params.getParamName(), 
					params.getParamVal());
		}
		query.execute();
		return (List<Dto>) query.getResultList();
	}

	/**
	 * Execute queries with one or more parameters 
	 * against predefined database stored procedures.
	 * 
	 * @param storedProcedure the stored procedure name
	 * @param queryParams the QueryParams object to iterate
	 * @param dtoType the Type of DTO object
	 * @return dtoList
	 */
	public <Dto> Dto queryForDtoWithParams(
			String storedProcedure, 
			List<QueryParams> queryParams,
			Class<Dto> dtoType) {

		StoredProcedureQuery query = this.entityManager
				.createNamedStoredProcedureQuery(storedProcedure);
		for (QueryParams params : queryParams) {
			query.setParameter(
					params.getParamName(), 
					params.getParamVal());
		}
		query.execute();
		return dtoType.cast(query.getSingleResult());
	}

	/**
	 * Method used to obtain previously-initialized session variable 
	 * containing a single matching value.
	 * The passed-in error code will be thrown if not found.
	 * 
	 * @param session the HttpSession
	 * @param attributeName the Session Attribute name
	 * @param missingSession the AppException message for missing Session
	 * @param notFound the AppException message for non-initialized Session Attribute
	 * @return sessionAttribute
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static <Key> Key getSessionAttribute(
			@NotNull HttpSession session, 
			@NotNull String attributeName, 
			String missingSession,
			String notFound) throws AppException {

		validateSessionAttributeParams(session, attributeName, missingSession, notFound);
		return (Key) session.getAttribute(attributeName);
	}

	/**
	 * Inner class helper to organize method implementations 
	 * used by classes implementing BaseOneToOneMapSeviceIF.
	 */
	protected static class BaseOneToOneMapServiceHelper {

		/**
		 * Method used to store query-returned values as a session variable.
		 * This is used to minimize expensive database calls in the application.
		 * 
		 * @param logger the Logger
		 * @param entityMap the mapping to store as a Session Attribute
		 * @param session the HttpSession
		 * @param attributeName the Session Attribute name
		 * @return mapSize
		 * @throws AppException 
		 */
		public static <Key,Val> void addSessionAttribute(
				Logger logger,
				Map<Key,Val> entityMap,
				@NotNull HttpSession session, 
				@NotNull String attributeName) throws AppException {

			session.setAttribute(attributeName, entityMap);
			logger.debug(
					SESSION_ATTRIBUTE_ADDED,
					attributeName,
					entityMap.size(),
					0);
		}

		/**
		 * Method used to store query-returned values as a session variable.
		 * This is used to minimize expensive database calls in the application.
		 * 
		 * @param logger the Logger
		 * @param entityMap the mapping to store as a Session Attribute
		 * @param session the HttpSession
		 * @param attributeName the Session Attribute name
		 * @param oldMapSize the original mapping size
		 * @return mapSize
		 * @throws AppException 
		 */
		public static <Key,Val> void updateSessionAttribute(
				Logger logger,
				Map<Key,Val> entityMap,
				@NotNull HttpSession session, 
				@NotNull String attributeName,
				int oldMapSize) throws AppException {

			session.setAttribute(attributeName, entityMap);
			logger.debug(
					SESSION_ATTRIBUTE_UPDATED,
					attributeName,
					entityMap.size(),
					oldMapSize);
		}

		/**
		 * Method used to obtain previously-initialized session variable 
		 * containing a mapping of keys each having a single matching value.
		 * The passed-in error code will be thrown if not found.
		 * 
		 * @param session the HttpSession
		 * @param attributeName the Session Attribute name
		 * @param missingSession the AppException message for missing Session
		 * @param notFound the AppException message for non-initialized Session Attribute
		 * @return sessionAttribute
		 * @throws AppException
		 */
		@SuppressWarnings("unchecked")
		public static <Key,Val> Map<Key,Val> getSessionAttribute(
				@NotNull HttpSession session, 
				@NotNull String attributeName, 
				String missingSession,
				String notFound) throws AppException {
	
			validateSessionAttributeParams(session, attributeName, missingSession, notFound);
			return (Map<Key,Val>) session.getAttribute(attributeName);
		}
	}

	/**
	 * Inner class helper to organize method implementations 
	 * used by classes implementing BaseOneToOneMapSeviceIF.
	 */
	protected static class BaseMultiMapServiceHelper {

		/**
		 * Method used to store query-returned values as a session variable.
		 * This is used to minimize expensive database calls in the application.
		 * 
		 * @param logger the Logger
		 * @param entityMap the mapping to store as a Session Attribute
		 * @param session the HttpSession
		 * @param attributeName the Session Attribute name
		 * @param missingSession the AppException message for missing Session
		 * @return mapSize
		 * @throws AppException 
		 */
		public static <Key,Val> void addSessionAttribute(
				Logger logger,
				Map<Key,List<Val>> entityMap,
				@NotNull HttpSession session, 
				@NotNull String attributeName) throws AppException {

			session.setAttribute(attributeName, entityMap);
			logger.debug(
					SESSION_ATTRIBUTE_ADDED,
					attributeName,
					entityMap.size());
		}

		/**
		 * Method used to obtain previously-initialized session variable 
		 * containing a mapping of keys each having one or more values.
		 * The passed-in error code will be thrown if not found.
		 * 
		 * @param session the HttpSession
		 * @param attributeName the Session Attribute name
		 * @param missingSession the AppException message for missing Session
		 * @param notFound the AppException message for non-initialized Session Attribute
		 * @return sessionAttribute
		 * @throws AppException
		 */
		@SuppressWarnings("unchecked")
		public static <Key,Val> Map<Key,List<Val>> getSessionAttribute(
				@NotNull HttpSession session, 
				@NotNull String attributeName, 
				String missingSession,
				String notFound) throws AppException {
	
			validateSessionAttributeParams(session, attributeName, missingSession, notFound);
			return (Map<Key,List<Val>>) session.getAttribute(attributeName);
		}
	}

	/**
	 * Inner class helper to organize method implementations 
	 * used by classes implementing BaseCacheableDAOSeviceIF.
	 */
	protected static class BaseCacheableDAOSeviceHelper {

		/**
		 * Obtain list of keys from cached mapping, to avoid duplicative querying.
		 * 
		 * @param entityMap the flattened mapping to translate into a list 
		 * @return resultList
		 */
		public static <T> List<T> listFromKeySet(
				@NotNull Set<T> entityMap) {
	
			return (List<T>) entityMap.stream()
							.collect(Collectors.toList());
		}
	
		/**
		 * Obtain list of values from cached mapping, to avoid duplicative querying.
		 * 
		 * @param entityMap the flattened mapping to translate into a list 
		 * @return resultList
		 */
		public static <T> List<T> listFromValues(
				@NotNull Collection<List<T>> entityMap) {
	
			return (List<T>) entityMap.stream()
							.flatMap(Collection::stream)
							.collect(Collectors.toList());
		}
	}

	/**
	 * Inner class helper to organize method implementations 
	 * used by classes implementing BaseSearchableDTOSeviceIF.
	 */
	protected static class BaseDisplayableDtoServiceHelper {

		/**
		 * Populate a single mapped entity for the specified value.
		 * This method returns one and only one entity of specified type.
		 * 
		 * @Param model the Model
		 * @param entity the mapped entity
		 * @param attributeName the Session Attribute name
		 * @return entityId
		 */
		public static <T> T addModelAttribute(
				Model model,
				@NotNull T entity,
				@NotNull String attributeName) {
	
			model.addAttribute(attributeName, entity);
			return entity;
		}
	}

	/**
	 * Inner class helper to organize method implementations 
	 * used by classes implementing BaseSearchableDTOSeviceIF.
	 */
	protected static class BaseSearchableDtoServiceHelper {

		/**
		 * Populate drop-down menu options for the specified value.
		 * This method returns an entity of specified type, 
		 * where the passed-in list of entities is found 
		 * to contain one and only one value.
		 * 
		 * @Param model the Model
		 * @param entityList the list of entities
		 * @param attributeName the Session Attribute name
		 * @param type the Type of listed entities
		 * @return entityId
		 */
		public static <T> T addModelAttribute(
				Model model,
				@NotNull List<T> entityList,
				@NotNull String attributeName,
				Class<T> type) {
	
			model.addAttribute(attributeName, entityList);
			return (entityList != null && !entityList.isEmpty() && entityList.size() == 1) 
					? entityList.get(0) // Return sole element, where list size is 1
					: null; // Otherwise, return null
		}
	
		/**
		 * Populate parent and child drop-down menu options.
		 * The return value is used on form submission to determine
		 * whether validation should be run against the child entity,
		 * i.e. a Nation with more than one Region, 
		 * or Calendar with more than one Rite.
		 * @param model the Model
		 * @param parentId the parent entity ID
		 * @param foundParentList the list of parent entities from Model
		 * @param cacheParentList the list of parent entities from Session
		 * @param parentAttribute the parent Model Attribute name
		 * @param parentType the parent Type
		 * @param childList the list of child entities from Session
		 * @param childAttribute the child Model Attribute name
		 * @param childType the child Type
		 * @param missingSession the missing Session error message
		 * @param missingModel the missing Model error message
		 * 
		 * @return childElement
		 * @throws AppException
		 */
		public static <Key,Val> Val populateMenuOptions(
				Model model, 
				// Parent drop-down fields
				Long parentId, 
				List<Key> foundParentList,
				List<Key> cacheParentList,
				String parentAttribute,
				Class<Key> parentType,
				// Child drop-down fields
				List<Val> childList,
				String childAttribute,
				Class<Val> childType,
				// Error Messages
				String missingSession) throws AppException {

			// Add parent menu options based on selection
			if (foundParentList == null) {
				addModelAttribute(
						model,
						cacheParentList,
						parentAttribute,
						parentType);
			}
			// Add child drop-down menu options based on parent
			Val childElement = null;
			if (parentId != null) {
				childElement = addModelAttribute(
						model, 
						childList, 
						childAttribute,
						childType);
			}
			return childElement;
		}
	}

	public static class DayCommemorationDtoSessionAttributeMapper<T> {

		private HttpSession session;
		private String entityMappingSessionAttributeName;
		private String dtoMappingSessionAttributeName;
		private Map<DayCommemorationDto, List<Rite>> addedDtoRiteMapEntry;
		private Request request;
		private Long commemorationId;
		private T targetEntity;
		private Long targetEntityId;
		private String lastContributor;
		private String calendarCode;
		private Class<T> entityType;

		private DayCommemorationDtoSessionAttributeMapper() {}

		public static class Builder<T> {

			private HttpSession session;
			private String entityMappingSessionAttributeName;
			private String dtoMappingSessionAttributeName;
			private Map<DayCommemorationDto, List<Rite>> addedDtoRiteMapEntry;
			private Request request;
			private Long commemorationId;
			private T targetEntity;
			private Long targetEntityId;
			private String lastContributor;
			private String calendarCode;
			private Class<T> entityType;

			public Builder<T> withSession(HttpSession session) {
				this.session = session;
				return this;
			}

			public Builder<T> withEntityMappingSessionAttributeName(String entityMappingSessionAttributeName) {
				this.entityMappingSessionAttributeName = entityMappingSessionAttributeName;
				return this;
			}

			public Builder<T> withDtoMappingSessionAttributeName(String dtoMappingSessionAttributeName) {
				this.dtoMappingSessionAttributeName = dtoMappingSessionAttributeName;
				return this;
			}

			public Builder<T> withAddedDtoRiteMapEntry(Map<DayCommemorationDto, List<Rite>> addedDtoRiteMapEntry) {
				this.addedDtoRiteMapEntry = addedDtoRiteMapEntry;
				return this;
			}

			public Builder<T> withRequest(Request request) {
				this.request = request;
				return this;
			}

			public Builder<T> withCommemorationId(Long commemorationId) {
				this.commemorationId = commemorationId;
				return this;
			}

			public Builder<T> withTargetEntity(T targetEntity) {
				this.targetEntity = targetEntity;
				return this;
			}

			public Builder<T> withTargetEntityId(Long targetEntityId) {
				this.targetEntityId = targetEntityId;
				return this;
			}

			public Builder<T> withLastContributor(String lastContributor) {
				this.lastContributor = lastContributor;
				return this;
			}

			public Builder<T> withCalendarCode(String calendarCode) {
				this.calendarCode = calendarCode;
				return this;
			}

			public Builder<T> withEntityType(Class<T> entityType) {
				this.entityType = entityType;
				return this;
			}

			public DayCommemorationDtoSessionAttributeMapper<T> build() {
				final DayCommemorationDtoSessionAttributeMapper<T> mapper = new DayCommemorationDtoSessionAttributeMapper<T>();
				mapper.session = this.session;
				mapper.entityMappingSessionAttributeName = this.entityMappingSessionAttributeName;
				mapper.dtoMappingSessionAttributeName = this.dtoMappingSessionAttributeName;
				mapper.addedDtoRiteMapEntry = this.addedDtoRiteMapEntry;
				mapper.request = this.request;
				mapper.commemorationId = this.commemorationId;
				mapper.targetEntity = this.targetEntity;
				mapper.targetEntityId = this.targetEntityId;
				mapper.lastContributor = this.lastContributor;
				mapper.calendarCode = this.calendarCode;
				mapper.entityType = this.entityType;
				return mapper;
			}
		}

		public HttpSession getSession() {
			return session;
		}

		public String getEntityMappingSessionAttributeName() {
			return entityMappingSessionAttributeName;
		}

		public String getDtoMappingSessionAttributeName() {
			return dtoMappingSessionAttributeName;
		}

		public Map<DayCommemorationDto, List<Rite>> getAddedDtoRiteMapEntry() {
			return addedDtoRiteMapEntry;
		}

		public Request getRequest() {
			return request;
		}

		public Long getCommemorationId() {
			return commemorationId;
		}

		public T getTargetEntity() {
			return targetEntity;
		}

		public Long getTargetEntityId() {
			return targetEntityId;
		}

		public String getLastContributor() {
			return lastContributor;
		}

		public String getCalendarCode() {
			return calendarCode;
		}

		public Class<T> getEntityType() {
			return entityType;
		}
	}
}
