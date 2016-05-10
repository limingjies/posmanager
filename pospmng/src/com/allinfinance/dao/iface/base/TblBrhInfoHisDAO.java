package com.allinfinance.dao.iface.base;

import com.allinfinance.po.TblBrhInfoHisPK;


public interface TblBrhInfoHisDAO {
	public com.allinfinance.po.TblBrhInfoHis get(TblBrhInfoHisPK key);

	public com.allinfinance.po.TblBrhInfoHis load(TblBrhInfoHisPK key);

	public java.util.List<com.allinfinance.po.TblBrhInfoHis> findAll ();

	public java.lang.String getNextSeqId(String brhId);

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblBrhInfoHis a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.allinfinance.po.TblBrhInfoHisPK save(com.allinfinance.po.TblBrhInfoHis tblBrhInfoHis);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblBrhInfoHis a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.TblBrhInfoHis tblBrhInfoHis);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblBrhInfoHis a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblBrhInfoHis tblBrhInfoHis);
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String brhId);

	public void delete(TblBrhInfoHisPK id);
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblBrhInfoHis the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblBrhInfoHis tblBrhInfoHis);

}