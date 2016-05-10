package com.allinfinance.dao.iface.mchnt;


public interface TblMchntRefuseDAO {
	public com.allinfinance.po.TblMchntRefuse get(com.allinfinance.po.TblMchntRefusePK key);

	public com.allinfinance.po.TblMchntRefuse load(com.allinfinance.po.TblMchntRefusePK key);

	public java.util.List<com.allinfinance.po.TblMchntRefuse> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblMchntRefuse a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.allinfinance.po.TblMchntRefusePK save(com.allinfinance.po.TblMchntRefuse tblMchntRefuse);
	
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblMchntRefuse a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.TblMchntRefuse tblMchntRefuse);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchntRefuse a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblMchntRefuse tblMchntRefuse);
	

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.TblMchntRefusePK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblMchntRefuse the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblMchntRefuse tblMchntRefuse);


}