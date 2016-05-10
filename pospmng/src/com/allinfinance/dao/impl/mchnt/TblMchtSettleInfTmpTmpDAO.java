package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpTmpDAO;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;


public class TblMchtSettleInfTmpTmpDAO extends _RootDAO<TblMchtSettleInfTmpTmp> implements ITblMchtSettleInfTmpTmpDAO{



	// query name references




	public Class<TblMchtSettleInfTmpTmp> getReferenceClass () {
		return TblMchtSettleInfTmpTmp.class;
	}


	/**
	 * Cast the object as a TblMchtSettleInfTmp
	 */
	public TblMchtSettleInfTmpTmp cast (Object object) {
		return (TblMchtSettleInfTmpTmp) object;
	}


	public TblMchtSettleInfTmpTmp load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtSettleInfTmpTmp) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtSettleInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtSettleInfTmpTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtSettleInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtSettleInfTmpTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtSettleInfTmp a transient instance containing updated state
	 */
	public void update(TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp)
		throws org.hibernate.HibernateException {
		super.update(tblMchtSettleInfTmpTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtSettleInfTmp the instance to be removed
	 */
	public void delete(TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtSettleInfTmpTmp);
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