package com.allinfinance.dao.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.allinfinance.dao.iface.TblBatMainCtlDAO;

import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblBatMainCtlDAO extends com.allinfinance.dao._RootDAO {

	public BaseTblBatMainCtlDAO () {}
	
	// query name references


	public static TblBatMainCtlDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static TblBatMainCtlDAO getInstance () {
		if (null == instance) instance = new com.allinfinance.dao.impl.settle.TblBatMainCtlDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.allinfinance.po.settle.TblBatMainCtl.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.allinfinance.po.settle.TblBatMainCtl
	 */
	public com.allinfinance.po.settle.TblBatMainCtl cast (Object object) {
		return (com.allinfinance.po.settle.TblBatMainCtl) object;
	}

	public com.allinfinance.po.settle.TblBatMainCtl get(java.lang.String key)
	{
		return (com.allinfinance.po.settle.TblBatMainCtl) get(getReferenceClass(), key);
	}

	public com.allinfinance.po.settle.TblBatMainCtl load(java.lang.String key)
	{
		return (com.allinfinance.po.settle.TblBatMainCtl) load(getReferenceClass(), key);
	}

/* Generic methods */

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblBatMainCtl a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.allinfinance.po.settle.TblBatMainCtl tblBatMainCtl)
	{
		return (java.lang.String) super.save(tblBatMainCtl);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblBatMainCtl a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.settle.TblBatMainCtl tblBatMainCtl)
	{
		saveOrUpdate((Object) tblBatMainCtl);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblBatMainCtl a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.settle.TblBatMainCtl tblBatMainCtl) 
	{
		update((Object) tblBatMainCtl);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblBatMainCtl the instance to be removed
	 */
	public void delete(com.allinfinance.po.settle.TblBatMainCtl tblBatMainCtl)
	{
		delete((Object) tblBatMainCtl);
	}

	

}