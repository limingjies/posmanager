package com.allinfinance.dao.iface.route;

import com.allinfinance.po.route.TblRouteRuleMap;

public interface TblRouteRuleMapDAO {
	public TblRouteRuleMap get(Integer key);

	public TblRouteRuleMap load(Integer key);

	public java.util.List<TblRouteRuleMap> findAll();

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param tblCityCode
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public Integer save(TblRouteRuleMap TblRouteRuleMap);

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param tblCityCode
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRouteRuleMap TblRouteRuleMap);

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param tblCityCode
	 *            a transient instance containing updated state
	 */
	public void update(TblRouteRuleMap TblRouteRuleMap);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(Integer id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param tblCityCode
	 *            the instance to be removed
	 */
	public void delete(TblRouteRuleMap tblCityCode);

	public Integer getRuleId();

	public void deleteByConditions(String mchntId, String mchtUpbrhNo,
			String propertyId, String termId);

	public TblRouteRuleMap getByConditions(String mchtId, String mchtUpbrhId,
			String propertyId, String termId);

	public int getMappedBrhid3(String brhId3);
	
	public void deleteByMchtId(String mchtId);
	
	public TblRouteRuleMap getByConditions(String mchtId,String propertyId);

}