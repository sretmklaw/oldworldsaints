package com.cimeliarchium.service.dao;

import java.util.Map;

import org.springframework.ui.Model;

import com.cimeliarchium.exception.AppException;

public interface BaseSearchableDAOServiceIF<Key,Val> extends BaseDAOServiceIF<Key,Val> {

	/**
	 * Method used to populate Search header.
	 * 
	 * @param model the Model
	 * @param id the entity ID
	 * @param entityMap the entity map
	 * @param calendarId the current User Calendar ID
	 * @throws AppException
	 */
	public void populateSearchHeader(
			Model model, 
			Long id,
			Map<Long,Val> entityMap,
			Long calendarId) throws AppException;

}
