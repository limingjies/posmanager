package com.allinfinance.dao.iface.base;


public interface TblCardRouteDAO {
	public com.allinfinance.po.TblCardRoute get(com.allinfinance.po.TblCardRoutePK key);

	public com.allinfinance.po.TblCardRoute load(com.allinfinance.po.TblCardRoutePK key);

	public java.util.List<com.allinfinance.po.TblCardRoute> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblCardRoute a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.allinfinance.po.TblCardRoutePK save(com.allinfinance.po.TblCardRoute tblCardRoute);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblCardRoute a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.TblCardRoute tblCardRoute);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblCardRoute a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblCardRoute tblCardRoute);
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.TblCardRoutePK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblCardRoute the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblCardRoute tblCardRoute);


}