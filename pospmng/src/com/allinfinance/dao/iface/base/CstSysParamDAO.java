package com.allinfinance.dao.iface.base;


public interface CstSysParamDAO {
	public com.allinfinance.po.CstSysParam get(com.allinfinance.po.CstSysParamPK key);

	public com.allinfinance.po.CstSysParam load(com.allinfinance.po.CstSysParamPK key);

	public java.util.List<com.allinfinance.po.CstSysParam> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param cstSysParam a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.allinfinance.po.CstSysParamPK save(com.allinfinance.po.CstSysParam cstSysParam);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param cstSysParam a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.CstSysParam cstSysParam);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param cstSysParam a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.CstSysParam cstSysParam);
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.CstSysParamPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param cstSysParam the instance to be removed
	 */
	public void delete(com.allinfinance.po.CstSysParam cstSysParam);


}