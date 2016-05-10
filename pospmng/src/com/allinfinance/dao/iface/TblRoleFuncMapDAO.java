package com.allinfinance.dao.iface;


public interface TblRoleFuncMapDAO {
	public com.allinfinance.po.TblRoleFuncMap get(com.allinfinance.po.TblRoleFuncMapPK key);

	public com.allinfinance.po.TblRoleFuncMap load(com.allinfinance.po.TblRoleFuncMapPK key);

	public java.util.List<com.allinfinance.po.TblRoleFuncMap> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblRoleFuncMap a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.allinfinance.po.TblRoleFuncMapPK save(com.allinfinance.po.TblRoleFuncMap tblRoleFuncMap);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblRoleFuncMap a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.TblRoleFuncMap tblRoleFuncMap);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblRoleFuncMap a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblRoleFuncMap tblRoleFuncMap);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.TblRoleFuncMapPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblRoleFuncMap the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblRoleFuncMap tblRoleFuncMap);


}