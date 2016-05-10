package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblGroupMchtSettleInfDAO;
import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;



public class TblGroupMchtSettleInfDAO extends _RootDAO<TblGroupMchtSettleInf> implements ITblGroupMchtSettleInfDAO {

	public Class<TblGroupMchtSettleInf> getReferenceClass () {
		return TblGroupMchtSettleInf.class;
	}


	/**
	 * Cast the object as a TblGroupMchtSettleInf
	 */
	public TblGroupMchtSettleInf cast (Object object) {
		return (TblGroupMchtSettleInf) object;
	}


	public TblGroupMchtSettleInf load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblGroupMchtSettleInf) load(getReferenceClass(), key);
	}

	public TblGroupMchtSettleInf get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblGroupMchtSettleInf) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblGroupMchtSettleInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(TblGroupMchtSettleInf tblGroupMchtSettleInf)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblGroupMchtSettleInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblGroupMchtSettleInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblGroupMchtSettleInf tblGroupMchtSettleInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblGroupMchtSettleInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblGroupMchtSettleInf a transient instance containing updated state
	 */
	public void update(TblGroupMchtSettleInf tblGroupMchtSettleInf)
		throws org.hibernate.HibernateException {
		super.update(tblGroupMchtSettleInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblGroupMchtSettleInf the instance to be removed
	 */
	public void delete(TblGroupMchtSettleInf tblGroupMchtSettleInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblGroupMchtSettleInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}



}