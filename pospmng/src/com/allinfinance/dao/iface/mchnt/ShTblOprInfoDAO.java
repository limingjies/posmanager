package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.ShTblOprInfoPk;


public interface ShTblOprInfoDAO {
	public com.allinfinance.po.ShTblOprInfo get(ShTblOprInfoPk key) throws org.hibernate.HibernateException;

	public com.allinfinance.po.ShTblOprInfo load(ShTblOprInfoPk key) throws org.hibernate.HibernateException;


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblOprInfo a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public void save(com.allinfinance.po.ShTblOprInfo tblOprInfo) throws org.hibernate.HibernateException;

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblOprInfo a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.ShTblOprInfo tblOprInfo) throws org.hibernate.HibernateException;

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblOprInfo a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.ShTblOprInfo tblOprInfo) throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(ShTblOprInfoPk id) throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblOprInfo the instance to be removed
	 */
	public void delete(com.allinfinance.po.ShTblOprInfo tblOprInfo) throws org.hibernate.HibernateException;


}