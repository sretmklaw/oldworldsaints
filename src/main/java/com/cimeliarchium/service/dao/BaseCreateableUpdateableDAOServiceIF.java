package com.cimeliarchium.service.dao;

import javax.servlet.http.HttpSession;

import com.cimeliarchium.exception.AppException;

public interface BaseCreateableUpdateableDAOServiceIF<Key,Val> 
		extends BaseQueryableDAOServiceIF<Key,Val> {

	/**
	 * Create a new entity of the specified type.
	 * 
	 * @return Object
	 */
	public Val create(HttpSession session, Val entity) throws AppException;

	/**
	 * Update an existing entity of the specified type.
	 * 
	 * @return Object
	 */
	public Val update(Val entity) throws AppException;
}
