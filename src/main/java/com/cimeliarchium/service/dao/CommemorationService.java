package com.cimeliarchium.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.repository.dao.CommemorationRepository;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

/**
 * Note: Only be used to perform pre-insert checks for non-duplicate ID and CRUD operations on Request.
 * All other interactions with this entity should be performed via DayCommemorationDto.
 */
@Service
@PropertySource({
	"classpath:/application.properties",
	"classpath:/errorcode.properties"
})
public class CommemorationService extends SessionAttributeHelperService {

	private CommemorationRepository repo;

	@Autowired
	public CommemorationService(CommemorationRepository commemorationRepo) {
		this.repo = commemorationRepo;
	}

	public Boolean hasMatchForId(Long id) {

		return this.repo.findByCommemorationId(id) != null;
	}

	public Long getSearchOrRandomId(
			Long id,
			Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap,
			Long riteId) {

		// When random ID is specified, filter the mapping of all Commemoration DTOs by Commemoration ID down
		// to a list of available Commemoration IDs for the specified Rite ID and then pick a random entry.
		if (id == ID_SEARCH_RANDOM_INDICATOR) {
			final List<Long> commemorationIdsForRiteId = new ArrayList<>();
			for (Entry<Long, Map<DayCommemorationDto, List<Rite>>> entry : dtoMap.entrySet()) {
				final Long commemorationId = entry.getKey();
				final List<Long> commemorationRiteIds = entry.getValue().entrySet()
						.iterator().next().getValue().stream()
						.map(Rite::getRiteId)
						.collect(Collectors.toList());
				if (riteId == riteAllId || commemorationRiteIds.contains(riteId)) {
					commemorationIdsForRiteId.add(commemorationId);
				}
			}
			final Long[] eligibleCommemorationIds = commemorationIdsForRiteId.toArray(Long[]::new);
			return eligibleCommemorationIds[new Random().nextInt(eligibleCommemorationIds.length)];
		} 
		// Otherwise, simply return the passed-in ID with no change.
		else {
			return id;
		}
	}

	@Value("${rite.all.id}")
	private Long riteAllId;

	@Value("${commemorationservice.updatelastcontributornotification.session}")
	private String updateLastContributorNotificationMissingSession;

	@Value("${commemorationservice.buildcommemorationdtomapping.session}")
	private String buildCommemorationDtoMappingMissingSession;

	@Value("${commemorationservice.buildcommemorationdtomapping.notfound}")
	private String buildCommemorationDtoMappingNotFound;

}
