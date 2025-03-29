package com.cimeliarchium.service.dao;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.FeatureFlag;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.repository.dao.FeatureFlagRepository;
import com.cimeliarchium.service.BaseOneToOneMapServiceIF;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class FeatureFlagService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF <Long,FeatureFlag>,
				BaseOneToOneMapServiceIF<Long,FeatureFlag> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeatureFlagService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.FEATURE_FLAG_MAP_BY_ID.getValue();

	//private Date lastServerRestartDate;

	private FeatureFlagRepository repo;

	@Autowired
	public FeatureFlagService(FeatureFlagRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<FeatureFlag> query() {

		return this.repo
				.findAll(Sort.by(Sort.Direction.ASC, "featureFlagId"));
	}

	/**
	 * Method used to display listing of feature flag values on the Admin console.
	 * Intentionally, rerun the query on each page reload to pull updated values.
	 * 
	 * @return queryResults
	 */
	public List<FeatureFlag> list() {

		return this.query();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long,FeatureFlag> map() throws AppException {

		return ((List<FeatureFlag>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								FeatureFlag::getFeatureFlagId, // Key
								featureFlag -> featureFlag)); // Value
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
	public Map<Long, FeatureFlag> getCachedValues(
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
			@NotNull User user) throws AppException {

		if (model != null 
				&& model.containsAttribute(ModelAttributeEnum.ADMIN_BUTTON_IS_VISIBLE.getValue())
				&& model.containsAttribute(ModelAttributeEnum.UPDATE_BUTTON_IS_VISIBLE.getValue())
				&& model.containsAttribute(ModelAttributeEnum.REQUEST_BUTTON_IS_VISIBLE.getValue())
				&& model.containsAttribute(ModelAttributeEnum.DONATE_BUTTON_IS_VISIBLE.getValue())
				&& model.containsAttribute(ModelAttributeEnum.MAINTENANCE_NOTIFICATION.getValue())
				&& model.containsAttribute(ModelAttributeEnum.DATE_SEARCH_BUTTON_IS_VISIBLE.getValue())
				&& model.containsAttribute(ModelAttributeEnum.SHOW_AUDIO.getValue())
				&& model.containsAttribute(ModelAttributeEnum.REFERENCE_LINK_IS_VISIBLE.getValue())) {
			return;
		}
		super.throwIfMissing(user, initModelMissingUser);
		super.throwIfMissing(session, initModelMissingSession);

		Map<Long, FeatureFlag> featureFlagMap = getCachedValues(session);
		if (user.getHasAdminRole()) {
			model.addAttribute(ModelAttributeEnum.ADMIN_BUTTON_IS_VISIBLE.getValue(), true);
			model.addAttribute(ModelAttributeEnum.UPDATE_BUTTON_IS_VISIBLE.getValue(), true);
			model.addAttribute(ModelAttributeEnum.REQUEST_BUTTON_IS_VISIBLE.getValue(), true);
			model.addAttribute(ModelAttributeEnum.DONATE_BUTTON_IS_VISIBLE.getValue(), true);
			model.addAttribute(ModelAttributeEnum.DATE_SEARCH_BUTTON_IS_VISIBLE.getValue(), true);
			model.addAttribute(ModelAttributeEnum.SHOW_AUDIO.getValue(), true);
			model.addAttribute(ModelAttributeEnum.REFERENCE_LINK_IS_VISIBLE.getValue(), true);
		} else {
			if (featureFlagMap != null) {
				final FeatureFlag updateFlag = featureFlagMap.get(featureFlagUpdateId);
				if (updateFlag != null && updateFlag.getIsFeatureFlagActive()) {
					model.addAttribute(ModelAttributeEnum.UPDATE_BUTTON_IS_VISIBLE.getValue(), true);
				}
				final FeatureFlag requestFlag = featureFlagMap.get(featureFlagRequestId);

				if (requestFlag != null && requestFlag.getIsFeatureFlagActive() && this.isRequestAvailableForUser(user)) {
					model.addAttribute(ModelAttributeEnum.REQUEST_BUTTON_IS_VISIBLE.getValue(), true);
				}
				final FeatureFlag donateFlag = featureFlagMap.get(featureFlagDonateId);
				if (donateFlag != null && donateFlag.getIsFeatureFlagActive()) {
					model.addAttribute(ModelAttributeEnum.DONATE_BUTTON_IS_VISIBLE.getValue(), true);
				}
				final FeatureFlag dateSearchFlag = featureFlagMap.get(featureFlagDateSearchId);
				if (dateSearchFlag != null && dateSearchFlag.getIsFeatureFlagActive()) {
					model.addAttribute(ModelAttributeEnum.DATE_SEARCH_BUTTON_IS_VISIBLE.getValue(), true);
				}
				final FeatureFlag showAudioFlag = featureFlagMap.get(featureFlagAudioId);
				if (showAudioFlag != null && showAudioFlag.getIsFeatureFlagActive()) {
					model.addAttribute(ModelAttributeEnum.SHOW_AUDIO.getValue(), true);
				}
				final FeatureFlag referenceLinkFlag = featureFlagMap.get(featureFlagReferenceLinkId);
				if (referenceLinkFlag != null && referenceLinkFlag.getIsFeatureFlagActive()) {
					model.addAttribute(ModelAttributeEnum.REFERENCE_LINK_IS_VISIBLE.getValue(), true);
				}
			}
		}
		// View maintenance notifications as both Admin and User
		final FeatureFlag notificationFlag = featureFlagMap.get(featureFlagNotifyId);
		if (notificationFlag != null && notificationFlag.getIsFeatureFlagActive()) {
			model.addAttribute(ModelAttributeEnum.MAINTENANCE_NOTIFICATION.getValue(),
					notificationFlag.getFeatureFlagValue());
		} 
		/**
		 * FIXME: Show users cache update notification following server restart
		 *
		final Instant lastUserLoginSuccessDate = user.getLastLoginSuccessDate();
		else if (user.getTotalLoginCount() > 1
				&& !user.getHasClearedCache() 
				&& lastUserLoginSuccessDate != null 
				&& lastServerRestartDate != null 
				&& lastServerRestartDate.toInstant().isBefore(lastUserLoginSuccessDate)) {
			model.addAttribute(ModelAttributeEnum.MAINTENANCE_NOTIFICATION.getValue(),
					DisplayKeyEnum.SERVER_RESTART.getValue());
		}
		*/
	}

	/**
	 * Request flag specific check against User creation date and Administrator-determined User input privileges.
	 * 
	 * @param user the User
	 * @return isAvailable
	 */
	private Boolean isRequestAvailableForUser(User user) {
		Boolean isAvailable = false;
		final Instant startDate = user.getCreationDate();
		if (user.getHasAdminLimit()) {
			return isAvailable;
		} else if (startDate != null) {
			isAvailable = ChronoUnit.DAYS.between(startDate, Instant.now()) >= 7L;
		}
		return isAvailable;
	}

	/**
	 * Method used to perform batch update Feature Flag statuses
	 * 
	 * @param ids the listing of entity IDs
	 * @param newBooleanValue the updated Boolean value
	 * @param newBooleanValue the updated String value
	 * @param session the HttpSession
	 * @return message
	 * @throws AppException
	 */
	public List<Long> updateFlagsStatus(
			@NotNull List<Long> ids, 
			@NotNull String newBooleanValue, 
			String newStringValue,
			@NotNull HttpSession session) throws AppException {

		super.throwIfMissing(ids, updateMissingIds);
		super.throwIfMissing(newBooleanValue, updateMissingValue);
		super.throwIfMissing(session, updateMissingSession);

		final List<Long> updateIds = new ArrayList<>();
		if (!ids.isEmpty()) {
			for (Long id : ids) {
				LOGGER.info("Setting feature flag ID {} to value: {}", id, newBooleanValue);
				FeatureFlag flag = this.getCachedValues(session).get(id);
				flag.setIsFeatureFlagActive(Boolean.parseBoolean(newBooleanValue));
				// Attempt to parse value for string-bearing Feature Flag values
				if (newStringValue != null && !newStringValue.isEmpty()) {
					if (id == featureFlagRequestId || id == featureFlagNotifyId) {
						flag.setFeatureFlagValue(newStringValue);
					}
				} 
				try {
					repo.save(flag);
					LOGGER.info(DisplayKeyEnum.REQUEST_SUCCESS.getValue());
					updateIds.add(id);
				} catch (Exception e) {
					LOGGER.error(DisplayKeyEnum.REQUEST_FAILURE.getValue());
				}
			}
		}
		return updateIds;
	}

	/**
	 * Method used to gracefully prevent access to a resource
	 * whose visibility is determined by feature flag status.
	 * @param featureFlagId the ID of the target FeatureFlag
	 * @param session the HttpSession
	 * @param user the User
	 * @param sesssion the HttpSession
	 * 
	 * @throws AppException 
	 */
	public Boolean isFeatureFlagActiveForUser(
			long featureFlagId, HttpSession session, User user) throws AppException {
	
		// Ensure feature is not locked down by Administrator
		final FeatureFlag featureFlag = getCachedValues(session).get(featureFlagId);
		// Always return 'false' (i.e., proceed without redirect) for User with Administrator Role
		final Boolean featureFlagIsActive = featureFlag != null && featureFlag.getIsFeatureFlagActive();
		final Boolean isAdminUser = user != null && user.getHasAdminRole() != null && user.getHasAdminRole();
		return (featureFlagIsActive || isAdminUser);
	}

	/**
	 * When user has exceeded the maximum number of open requests, prevent additional submissions.
	 * Note that bugfix requests should not count toward the request per user limit.
	 * 
	 * @param session the HttpSession
	 * @param user the User
	 * @return hasMaxOpenRequests
	 * @throws AppException
	 */
	public Boolean hasMaxOpenRequests(HttpSession session, User user) throws AppException {
		final FeatureFlag requestFlag = getCachedValues(session).get(featureFlagRequestId);
		final String maxRequests = requestFlag.getFeatureFlagValue();
		final Long maxRequestCount = (requestFlag.getIsFeatureFlagActive() && maxRequests != null && !maxRequests.isEmpty()) 
				? Long.parseLong(maxRequests) 
				: 0L;
		return (!user.getHasAdminRole() && user.getRequestCount() >= maxRequestCount);
	}

	//public void setLastServerRestartDate(Date lastServerRestartDate) {
	//	this.lastServerRestartDate = lastServerRestartDate;
	//}

	@Value("${featureflagservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${featureflagservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${featureflagservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${featureflagservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${featureflagservice.initmodel.user}")
	private String initModelMissingUser;

	@Value("${featureflagservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${featureflagservice.update.ids}")
	private String updateMissingIds;

	@Value("${featureflagservice.update.value}")
	private String updateMissingValue;

	@Value("${featureflagservice.update.session}")
	private String updateMissingSession;

	@Value("${featureflag.update.id}")
	private Long featureFlagUpdateId;

	@Value("${featureflag.request.id}")
	private Long featureFlagRequestId;

	@Value("${featureflag.donate.id}")
	private Long featureFlagDonateId;

	@Value("${featureflag.notify.id}")
	private Long featureFlagNotifyId;

	@Value("${featureflag.datesearch.id}")
	private Long featureFlagDateSearchId;

	@Value("${featureflag.audio.id}")
	private Long featureFlagAudioId;

	@Value("${featureflag.referencelink.id}")
	private Long featureFlagReferenceLinkId;
}
