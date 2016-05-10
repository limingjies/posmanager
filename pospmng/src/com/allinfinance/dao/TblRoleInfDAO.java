package com.allinfinance.dao;

import java.util.List;

import com.allinfinance.po.TblRoleInf;


public class TblRoleInfDAO extends _RootDAO<com.allinfinance.po.TblRoleInf> implements com.allinfinance.dao.iface.TblRoleInfDAO{

public TblRoleInfDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblRoleInfDAO#findAll()
 */
public List<TblRoleInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblRoleInf> getReferenceClass () {
	return com.allinfinance.po.TblRoleInf.class;
}


/**
 * Cast the object as a com.allinfinance.po.role.TblRoleInf
 */
public com.allinfinance.po.TblRoleInf cast (Object object) {
	return (com.allinfinance.po.TblRoleInf) object;
}


public com.allinfinance.po.TblRoleInf load(java.lang.Integer key)
{
	return (com.allinfinance.po.TblRoleInf) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblRoleInf get(java.lang.Integer key)
{
	return (com.allinfinance.po.TblRoleInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblRoleInf> loadAll()
{
	return loadAll(getReferenceClass());
}

/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRoleInf a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.Integer save(com.allinfinance.po.TblRoleInf tblRoleInf)
{
	return (java.lang.Integer) super.save(tblRoleInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblRoleInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblRoleInf tblRoleInf)
{
	super.saveOrUpdate(tblRoleInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRoleInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblRoleInf tblRoleInf)
{
	super.update(tblRoleInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblRoleInf the instance to be removed
 */
public void delete(com.allinfinance.po.TblRoleInf tblRoleInf)
{
	super.delete((Object) tblRoleInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(java.lang.Integer id)
{
	super.delete((Object) load(id));
}

}