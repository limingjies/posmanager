package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpTmpDAO;


public class TblMchtBaseInfTmpTmpDAO extends _RootDAO<com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp> implements ITblMchtBaseInfTmpTmpDAO{



	// query name references




	public Class<com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp> getReferenceClass () {
		return com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp.class;
	}


	/**
	 * Cast the object as a com.allinfinance.po.mchnt.TblMchtBaseInfTmp
	 */
	public com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp cast (Object object) {
		return (com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp) object;
	}


	public com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp) get(getReferenceClass(), key);
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBaseInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtBaseInfTmpTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBaseInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtBaseInfTmpTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBaseInfTmp a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp)
		throws org.hibernate.HibernateException {
		super.update(tblMchtBaseInfTmpTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBaseInfTmp the instance to be removed
	 */
	public void delete(com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtBaseInfTmpTmp);
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