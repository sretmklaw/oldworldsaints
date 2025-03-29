package com.cimeliarchium.service.dao;

import java.util.List;
import java.util.Map;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dto.DayCommemorationDto;

public interface BaseCacheableDAOServiceIF<Key,Val> 
		extends BaseDAOServiceIF<Key,Val> {

	/**
	 * Build a list from cached a mapping, to avoid duplicative querying.
	 * 
	 * @return resultList
	 */
	public List<Val> list() throws AppException;

	/**
	 * Method used to obtain Search ID when a random entry is requested.
	 * 
	 * @param id the entity ID
	 * @param dtoMap the mapping of entity by ID
	 * @param riteId the current User Rite ID
	 * @return
	 */
	public Long getSearchOrRandomId(
			Long id,
			Map<Long, Map<DayCommemorationDto, List<Rite>>> dtoMap,
			Long riteId);

}
