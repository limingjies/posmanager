package com.allinfinance.dao.iface.mchnt;


public interface TblDivMchntTmpDAO {
	public com.allinfinance.po.TblDivMchntTmp get(com.allinfinance.po.TblDivMchntTmpPK key);

	public com.allinfinance.po.TblDivMchntTmp load(com.allinfinance.po.TblDivMchntTmpPK key);
	
	public java.util.List<com.allinfinance.po.TblDivMchntTmp> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblDivMchntTmp a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.allinfinance.po.TblDivMchntTmpPK save(com.allinfinance.po.TblDivMchntTmp tblDivMchntTmp);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblDivMchntTmp a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.TblDivMchntTmp tblDivMchntTmp);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblDivMchntTmp a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblDivMchntTmp tblDivMchntTmp);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.TblDivMchntTmpPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblDivMchntTmp the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblDivMchntTmp tblDivMchntTmp);


}