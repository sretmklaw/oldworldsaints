package com.cimeliarchium.service.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
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

import com.cimeliarchium.enums.CalendarRiteEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.Tag;
import com.cimeliarchium.model.dao.User;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.repository.dao.TagRepository;
import com.cimeliarchium.service.helper.ModelAttributeHelperService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties",
	"classpath:/application.properties"
})
public class TagService extends SessionAttributeHelperService
		implements BaseQueryableDAOServiceIF<Long,Tag>,
				BaseSearchableDAOServiceIF<Long,Tag> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);
	private static final String SESSION_ATTRIBUTE_NAME = SessionAttributeEnum.TAG_MAP_BY_ID.getValue();

	private Map<Long,Tag> tagMap;

	private TagRepository repo;

	@Autowired
	public TagService(TagRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Tag> query() {

		final List<Tag> queryResults = repo
				.findAll(Sort.by(Sort.Direction.ASC, "tagName"));
		LOGGER.info("TagRepository returned {} results", queryResults.size());
		return queryResults;
	}

	@SuppressWarnings("unchecked")
	public void map() throws AppException {

		this.tagMap = ((List<Tag>) super.throwIfMissing(
				this.query(), mapMissingResultList))
						.stream().collect(Collectors.toMap(
								Tag::getTagId, // Key
								tag -> tag)); // Value
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
					tagMap, 
					session, 
					SESSION_ATTRIBUTE_NAME);
		}
		return hasUpdate;
	}

	@Override
	public Map<Long, Tag> getCachedValues(
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

		if (model != null && model.containsAttribute(ModelAttributeEnum.TAG_LIST.getValue())) {
			return;
		}
		super.throwIfMissing(session, initModelMissingSession);
		super.throwIfMissing(user, initModelMissingUser);

		// Obtain a listing of Creed IDs applicable to the current User Rite from cached mapping.
		final Rite userRite = CalendarRiteEnum.getCalendarRiteMap().values().stream()
				.flatMap(Collection::stream)
				.filter(r -> r.getRiteId() == user.getRiteId())
				.findFirst().orElse(null);
		// Only show Tags that are applicable to the current User's specified Rite.
		Set<Tag> orderedTags = new TreeSet<>();
		if (userRite != null) {
			final Collection<Tag> cachedTags = this.getCachedValues(session).values();
			for (Tag tag : cachedTags) {
				if (tag.getTagName() != null && userRite.hasCreed(tag.getTagCreedId())) {
					orderedTags.add(tag);
				}
			}
		} else {
			orderedTags = Collections.emptySet();
		}
		model.addAttribute(
				ModelAttributeEnum.TAG_LIST.getValue(), new LinkedList<>(orderedTags));
	}

	public Long getSearchOrRandomId(
			Long id,
			Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap,
			Long riteId) {

		// When random ID is specified, filter the mapping of all Commemoration DTOs by Tag ID down
		// to a list of available Century IDs for the specified Tag ID and then pick a random entry.
		if (id == ID_SEARCH_RANDOM_INDICATOR) {
			final List<Long> tagIdsForRiteId = new ArrayList<>();
			for (Entry<Long, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final Long tagId = entry.getKey();
				final List<Long> commemorationRiteIds = entry.getValue().entrySet()
						.iterator().next().getValue().stream()
						.map(Rite::getRiteId)
						.collect(Collectors.toList());
				if (riteId == riteAllId || commemorationRiteIds.contains(riteId)) {
					tagIdsForRiteId.add(tagId);
				}
			}
			final Long[] eligibleTagIds = tagIdsForRiteId.toArray(Long[]::new);
			return eligibleTagIds[new Random().nextInt(eligibleTagIds.length)];
		} 
		// Otherwise, simply return the passed-in ID with no change.
		else {
			return id;
		}
	}

	@Override
	public void populateSearchHeader(
			Model model, 
			Long id,
			Map<Long,Tag> entityMap,
			Long calendarId) throws AppException {

		String searchTerm = null;
		if (id != null && entityMap != null) {
			Tag tag = entityMap.get(id);
			if (tag != null) {
				searchTerm = tag.getTagName();
				// Obtain Tag detail based on an eligible match for the passed-in search term.
				// We do it this way because there are potentially multiple Tags mapped
				// to each DayCommemorationDto, so attempting to obtain it from there
				// does not guarantee that we will be displaying the correct result.
				model.addAttribute(ModelAttributeEnum.SEARCH_DETAIL.getValue(), tag.getTagDetail());

				// Add Related Search Link, where applicable
				final Long relatedPatronageId = tag.getRelatedPatronageId();
				if (relatedPatronageId != null) {
					model.addAttribute(ModelAttributeEnum.RELATED_SEARCH_LINK.getValue(), 
							RELATED_PATRONAGE_SEARCH_PATH + relatedPatronageId);
				}
			}
		}
		ModelAttributeHelperService.addSearchTypeAndTerm(model, null, searchTerm);
	}

	/**
	 * Custom method used to map a specified Creed ID to a listing of applicable Tags,
	 * for use in populating the associated drop-down menus in the Request view.
	 * 
	 * @param session the HttpSession
	 * @param creedId the Creed ID
	 * @return resultList
	 * @throws AppException
	 */
	public List<Tag> lookupById(
			@NotNull HttpSession session, 
			@NotNull Long creedId) throws AppException {

		super.throwIfMissing(session, lookupByIdMissingSession);
		List<Tag> filteredTags = new ArrayList<>();

		if (creedId != null) {
			final List<Tag> cachedTags = this.getCachedValues(session).entrySet().stream()
					.map(Map.Entry::getValue).collect(Collectors.toList());

			// Jewish creed shows only Abrahamic and Jewish tags
			if (creedId == creedJewishId) {
				filteredTags = cachedTags.stream()
						.filter(t -> t.getTagCreedId() == creedAbrahamicId 
								|| t.getTagCreedId() == creedJewishId)
						.collect(Collectors.toList());
			} 
			// Muslim denominations show only Abrahamic and Islamic tags
			else if (creedId == creedIslamicId 
					|| creedId == creedSunniId 
					|| creedId == creedShiiteId) {
				filteredTags = cachedTags.stream()
						.filter(t -> t.getTagCreedId() == creedAbrahamicId 
								|| t.getTagCreedId() == creedIslamicId)
						.collect(Collectors.toList());
			} 
			// Christian denominations show only Abrahamic and Chalcedonian tags
			else if (creedId == creedOrthodoxId 
					|| creedId == creedFilioquistId 
					|| creedId == creedCatholicId
					|| creedId == creedProtestantId
					|| creedId == creedChalcedonianId 
					|| creedId == creedApostolicId) {
				filteredTags = cachedTags.stream()
						.filter(t -> t.getTagCreedId() == creedAbrahamicId 
								|| t.getTagCreedId() == creedChalcedonianId)
						.collect(Collectors.toList());
			} 
			// All Commemorations shows list of every available tag
			else {
				filteredTags = cachedTags;
			}
			Collections.sort(filteredTags);
		}
		return filteredTags;
	}

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${tagservice.map.resultlist}")
	private String mapMissingResultList;

	@Value("${tagservice.initcache.session}")
	private String initCacheMissingSession;

	@Value("${tagservice.getcachedvalues.session}")
	private String getCachedValuesMissingSession;

	@Value("${tagservice.getcachedvalues.notfound}")
	private String getCachedValuesNotFound;

	@Value("${tagservice.initmodel.session}")
	private String initModelMissingSession;

	@Value("${tagservice.initmodel.user}")
	private String initModelMissingUser;

	@Value("${tagservice.lookupbyid.session}")
	private String lookupByIdMissingSession;

	@Value("${creed.abrahamic.id}")
	private Long creedAbrahamicId;

	@Value("${creed.apostolic.id}")
	private Long creedApostolicId;

	@Value("${creed.chalcedonian.id}")
	private Long creedChalcedonianId;

	@Value("${creed.filioquist.id}")
	private Long creedFilioquistId;

	@Value("${creed.orthodox.id}")
	private Long creedOrthodoxId;

	@Value("${creed.islamic.id}")
	private Long creedIslamicId;

	@Value("${creed.sunni.id}")
	private Long creedSunniId;

	@Value("${creed.shiite.id}")
	private Long creedShiiteId;

	@Value("${creed.jewish.id}")
	private Long creedJewishId;

	@Value("${creed.catholic.id}")
	private Long creedCatholicId;

	@Value("${creed.protestant.id}")
	private Long creedProtestantId;

}
