package com.cimeliarchium.service.dto;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.exception.AppException;

public interface BaseSearchableDTOServiceIF<Key,Val> 
		extends BaseDTOServiceIF<Key,Val> {

	/**
	 * Method used to retrieve the flattened data structure 
	 * from a database stored procedure.
	 */
	public void query() throws AppException;

	/**
	 * Method used to list available keys from a mapping.
	 * 
	 * @param entityMap the mapping of keys
	 * @return keyList
	 * @throws AppException 
	 */
	public List<Key> listKeys(
			@NotNull Map<Key,List<Val>> entityMap) throws AppException;

	/**
	 * Method used to lookup available values for a given key.
	 * 
	 * @param session the HttpSession
	 * @param lookupId the ID to lookup
	 * @return resultsList
	 * @throws AppException 
	 */
	public List<Val> lookupById(
			@NotNull HttpSession session,
			@NotNull Long lookupId) throws AppException;
}
