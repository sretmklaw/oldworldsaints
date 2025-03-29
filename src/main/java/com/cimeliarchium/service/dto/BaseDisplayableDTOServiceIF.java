package com.cimeliarchium.service.dto;

import com.cimeliarchium.exception.AppException;

public interface BaseDisplayableDTOServiceIF<Key,Val,Dto> 
		extends BaseDTOServiceIF<Key,Val> {

	/**
	 * Method used to retrieve the flattened data structure 
	 * from a database stored procedure.
	 * 
	 * @return resultList
	 * @throws AppException 
	 */
	public Dto query() throws AppException;
}
