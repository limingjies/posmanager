package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblBlukImpRetInf;


public class TblBlukImpRetInfDAO extends _RootDAO<TblBlukImpRetInf> implements com.allinfinance.dao.iface.mchnt.TblBlukImpRetInfDAO{
	

	public Class<TblBlukImpRetInf> getReferenceClass () {
		return TblBlukImpRetInf.class;
	}

	/**
	 * Cast the object as a TblBlukImpRetInf
	 */
	public TblBlukImpRetInf cast (Object object) {
		return (TblBlukImpRetInf) object;
	}


	public TblBlukImpRetInf load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblBlukImpRetInf) load(getReferenceClass(), key);
	}

	public TblBlukImpRetInf get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblBlukImpRetInf) get(getReferenceClass(), key);
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblBlukImpRetInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(TblBlukImpRetInf tblBlukImpRetInf)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblBlukImpRetInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblBlukImpRetInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblBlukImpRetInf tblBlukImpRetInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblBlukImpRetInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblBlukImpRetInf a transient instance containing updated state
	 */
	public void update(TblBlukImpRetInf tblBlukImpRetInf)
		throws org.hibernate.HibernateException {
		super.update(tblBlukImpRetInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblBlukImpRetInf the instance to be removed
	 */
	public void delete(TblBlukImpRetInf tblBlukImpRetInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblBlukImpRetInf);
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