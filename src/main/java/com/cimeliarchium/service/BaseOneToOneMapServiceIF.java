package com.cimeliarchium.service;

import java.util.Map;

import com.cimeliarchium.exception.AppException;

public interface BaseOneToOneMapServiceIF<Key,Val> 
		extends BaseServiceIF<Key,Val> {

	/**
	 * Build a mapping of entity-type keys,
	 * each having one and only one query-returned value.
	 * 
	 * @return entityMap
	 * @throws AppException 
	 */
	public Map<Key,Val> map() throws AppException;

}
