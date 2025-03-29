package com.cimeliarchium.service.dto;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.service.BaseServiceIF;

public interface BaseDTOServiceIF<Key,Val> 
		extends BaseServiceIF<Key,Val> {

	/**
	 * Find a mapping previously stored as a session attribute.
	 * 
	 * @param session the HttpSession
	 * @return keyMap
	 * @throws AppException 
	 */
	public Map<Key,List<Val>> getCachedValues(
			@NotNull HttpSession session) throws AppException;
}
