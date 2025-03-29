package com.cimeliarchium.service;

import com.cimeliarchium.exception.AppException;

public interface BaseStaticMultiMapServiceIF<Key,Val> 
		extends BaseServiceIF<Key,Val> {

	/**
	 * Build a static mapping of entity-type keys, each having one or more query-returned values.
	 * For performance reasons, this service is used to perform one-time static initialization of 
	 * entity mappings on application startup. Note that any interactions with the database done 
	 * after startup will need to be performed as discreet updates to these class-level mappings 
	 * in order for them to get reflected in an already-running instance of the application.
	 * 
	 * @throws AppException 
	 */
	public void map() throws AppException;
}
