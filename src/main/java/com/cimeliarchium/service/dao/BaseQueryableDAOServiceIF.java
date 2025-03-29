package com.cimeliarchium.service.dao;

import java.util.List;

public interface BaseQueryableDAOServiceIF<Key,Val> 
		extends BaseDAOServiceIF<Key,Val> {

	/**
	 * List all values of specified type from the database.
	 * 
	 * @return list
	 */
	public List<Val> query();
}
