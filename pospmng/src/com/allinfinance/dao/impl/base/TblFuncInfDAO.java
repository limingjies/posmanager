package com.allinfinance.dao.impl.base;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblFuncInf;




public class TblFuncInfDAO extends _RootDAO<com.allinfinance.po.TblFuncInf>  implements com.allinfinance.dao.iface.base.TblFuncInfDAO{

public TblFuncInfDAO () {}

public Class<TblFuncInf> getReferenceClass () {
	return TblFuncInf.class;
}


/**
 * Cast the object as a func.TblFuncInf
 */
public TblFuncInf cast (Object object) {
	return (TblFuncInf) object;
}


public TblFuncInf load(java.lang.Integer key)
	throws org.hibernate.HibernateException {
	return (TblFuncInf) load(getReferenceClass(), key);
}

public TblFuncInf get(java.lang.Integer key)
	throws org.hibernate.HibernateException {
	return (TblFuncInf) get(getReferenceClass(), key);
}


/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblFuncInf a transient instance of a persistent class
 * @return the class identifier
 */
public Integer save(TblFuncInf tblFuncInf)
	throws org.hibernate.HibernateException {
	return (java.lang.Integer) super.save(tblFuncInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblFuncInf a transient instance containing new or updated state
 */
public void saveOrUpdate(TblFuncInf tblFuncInf)
	throws org.hibernate.HibernateException {
	super.saveOrUpdate(tblFuncInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblFuncInf a transient instance containing updated state
 */
public void update(TblFuncInf tblFuncInf)
	throws org.hibernate.HibernateException {
	super.update(tblFuncInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblFuncInf the instance to be removed
 */
public void delete(TblFuncInf tblFuncInf)
	throws org.hibernate.HibernateException {
	super.delete((Object) tblFuncInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(java.lang.Integer id)
	throws org.hibernate.HibernateException {
	super.delete((Object) load(id));
}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblFuncInfDAO#findAll()
 */
public List<TblFuncInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

}