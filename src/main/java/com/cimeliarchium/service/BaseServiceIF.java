package com.cimeliarchium.service;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.ui.Model;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.User;

/**
 * This is the top-level interface from which all Service-type interfaces inherit.
 * It and its children specify the template method signatures which must exist for
 * each corresponding category of Service-type classes.
 * 
 * Overview of Service Interface hierarchy:
 * 
 * BaseServiceIF (this) - initCache(HttpSession)
 * |                      initModel(HttpSession, Model, User) returning Long
 * |
 * +-- BaseMultiMapServiceIF - map() void method
 * |
 * +-- BaseOneToOneMapServiceIF - map() returning Map<Key,Val>
 * |
 * +-- BaseDAOServiceIF - getCachedValues(HttpSession) returning Map<Long,Val>
 * |   |
 * |   +-- BaseCacheableDAOServiceIF - list() returning List<Val>
 * |   |
 * |   +-- BaseQueryableDAOServiceIF - query() returning List<Val>
 * |   |   |
 * |   |   +-- BaseCreatableUpdateableDAOServiceIF - create(HttpSession, Val) returning Val
 * |   |                                             update(Val) returning Val
 * |   |
 * |   +-- BaseSearchableDAOServiceIF - populateSearchHeader(Model,Long,Map<Long,Val>)
 * |
 * +-- BaseDTOServiceIF - getCachedValues(HttpSession) returning Map<Key,List<Val>>
 *     |
 *     +-- BaseDisplayableDTOServiceIF - query() returning Dto
 *     |
 *     +-- BaseSearchableDTOServiceIF - query() returning List<Dto>,
 *                                      listKeys(Map<Key,List<Val>>) returning List<Key>, 
 *                                      lookupById(HttpSession, Long), returning List<Val>
 *
 * @param <Key> the Type of Key
 * @param <Val> the Type of Value
 */
public interface BaseServiceIF<Key,Val> {

	/**
	 * Store mapped query results in memory as a session attribute.
	 * 
	 * @param session the HttpSession
	 * @return 
	 * @throws AppException 
	 */
	public Boolean initCache(
			@NotNull HttpSession session) throws AppException;


	/**
	 * Method used to initialize model attributes.
	 * 
	 * @param user the User
	 * @param model the Model
	 * @param session the HttpSession
	 * @throws AppException
	 */
	public void initModel(
			@NotNull HttpSession session,
			Model model,
			@NotNull User user) throws AppException;
}
