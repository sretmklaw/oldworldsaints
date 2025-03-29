package com.cimeliarchium.service.dao;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.service.BaseServiceIF;

public interface BaseDAOServiceIF<Key,Val> 
		extends BaseServiceIF<Key,Val> {

	/**
	 * Find a mapping previously stored as a session attribute.
	 * 
	 * @param session the HttpSession
	 * @return keyMap
	 * @throws AppException 
	 */
	public Map<Key,Val> getCachedValues(
			@NotNull HttpSession session) throws AppException;
}
