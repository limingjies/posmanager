package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;



public class TblMchtBaseInfPosfDAO extends _RootDAO<TblMchtBaseInf> {

	public Class<TblMchtBaseInf> getReferenceClass () {
		return TblMchtBaseInf.class;
	}


	/**
	 * Cast the object as a com.allinfinance.po.TblMchtBaseInf
	 */
	public TblMchtBaseInf cast (Object object) {
		return (TblMchtBaseInf) object;
	}


	public TblMchtBaseInf load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtBaseInf) load(getReferenceClass(), key);
	}

	public TblMchtBaseInf get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtBaseInf) get(getReferenceClass(), key);
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBaseInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(TblMchtBaseInf tblMchtBaseInf)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblMchtBaseInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBaseInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtBaseInf tblMchtBaseInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblMchtBaseInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBaseInf a transient instance containing updated state
	 */
	public void update(TblMchtBaseInf tblMchtBaseInf)
		throws org.hibernate.HibernateException {
		super.update(tblMchtBaseInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBaseInf the instance to be removed
	 */
	public void delete(TblMchtBaseInf tblMchtBaseInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblMchtBaseInf);
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