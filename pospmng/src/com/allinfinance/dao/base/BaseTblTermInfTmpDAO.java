package com.allinfinance.dao.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.allinfinance.dao.iface.TblTermInfTmpDAO;

import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblTermInfTmpDAO extends com.allinfinance.dao._RootDAO {

	public BaseTblTermInfTmpDAO () {}
	
	// query name references


	public static TblTermInfTmpDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static TblTermInfTmpDAO getInstance () {
		if (null == instance) instance = new com.allinfinance.dao.TblTermInfTmpDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.allinfinance.po.TblTermInfTmp.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.allinfinance.po.TblTermInfTmp
	 */
	public com.allinfinance.po.TblTermInfTmp cast (Object object) {
		return (com.allinfinance.po.TblTermInfTmp) object;
	}

	public com.allinfinance.po.TblTermInfTmp get(com.allinfinance.po.TblTermInfTmpPK key)
	{
		return (com.allinfinance.po.TblTermInfTmp) get(getReferenceClass(), key);
	}


	public com.allinfinance.po.TblTermInfTmp load(com.allinfinance.po.TblTermInfTmpPK key)
	{
		return (com.allinfinance.po.TblTermInfTmp) load(getReferenceClass(), key);
	}



/* Generic methods */


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblTermInfTmp a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.allinfinance.po.TblTermInfTmpPK save(com.allinfinance.po.TblTermInfTmp tblTermInfTmp)
	{
		return (com.allinfinance.po.TblTermInfTmpPK) super.save(tblTermInfTmp);
	}


	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblTermInfTmp a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.TblTermInfTmp tblTermInfTmp)
	{
		saveOrUpdate((Object) tblTermInfTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermInfTmp a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblTermInfTmp tblTermInfTmp) 
	{
		update((Object) tblTermInfTmp);
	}


	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.TblTermInfTmpPK id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblTermInfTmp the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblTermInfTmp tblTermInfTmp)
	{
		delete((Object) tblTermInfTmp);
	}

	


}