package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;


public class TblMchtBaseInfTmpDAO extends _RootDAO<com.allinfinance.po.mchnt.TblMchtBaseInfTmp> implements ITblMchtBaseInfTmpDAO{



	// query name references




	public Class<com.allinfinance.po.mchnt.TblMchtBaseInfTmp> getReferenceClass () {
		return com.allinfinance.po.mchnt.TblMchtBaseInfTmp.class;
	}


	/**
	 * Cast the object as a com.allinfinance.po.mchnt.TblMchtBaseInfTmp
	 */
	public com.allinfinance.po.mchnt.TblMchtBaseInfTmp cast (Object object) {
		return (com.allinfinance.po.mchnt.TblMchtBaseInfTmp) object;
	}


	public com.allinfinance.po.mchnt.TblMchtBaseInfTmp load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.allinfinance.po.mchnt.TblMchtBaseInfTmp) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.mchnt.TblMchtBaseInfTmp get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.allinfinance.po.mchnt.TblMchtBaseInfTmp) get(getReferenceClass(), key);
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBaseInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.allinfinance.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtBaseInfTmp);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBaseInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtBaseInfTmp);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBaseInfTmp a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException {
		super.update(tblMchtBaseInfTmp);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBaseInfTmp the instance to be removed
	 */
	public void delete(com.allinfinance.po.mchnt.TblMchtBaseInfTmp tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtBaseInfTmp);
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