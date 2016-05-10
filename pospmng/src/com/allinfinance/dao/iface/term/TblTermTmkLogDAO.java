package com.allinfinance.dao.iface.term;

import java.util.List;

import com.allinfinance.po.TblTermTmkLog;
import com.allinfinance.po.TblTermTmkLogPK;


public interface TblTermTmkLogDAO {
	public com.allinfinance.po.TblTermTmkLog get(TblTermTmkLogPK key);

	public com.allinfinance.po.TblTermTmkLog load(TblTermTmkLogPK key);

	public java.util.List<com.allinfinance.po.TblTermTmkLog> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblTermTmkLog a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public TblTermTmkLogPK save(com.allinfinance.po.TblTermTmkLog tblTermTmkLog);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblTermTmkLog a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.TblTermTmkLog tblTermTmkLog);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermTmkLog a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblTermTmkLog tblTermTmkLog);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblTermTmkLogPK key);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblTermTmkLog the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblTermTmkLog tblTermTmkLog);
	/**
	 * Query by hql
	 * @param hql
	 * @return
	 * 2011-7-5下午03:02:45
	 */
	public List findByHQLQuery(final String hql);
	/**
	 * save TMK information for batch
	 * @return
	 * 2011-7-7上午11:09:58
	 */
	public void batchSave(final List<TblTermTmkLog> list);
}