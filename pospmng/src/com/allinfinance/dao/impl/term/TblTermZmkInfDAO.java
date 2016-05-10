package com.allinfinance.dao.impl.term;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermZmkInf;


public class TblTermZmkInfDAO extends _RootDAO<com.allinfinance.po.TblTermZmkInf> implements com.allinfinance.dao.iface.term.TblTermZmkInfDAO{

public TblTermZmkInfDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblTermZmkInfDAO#findAll()
 */
public List<TblTermZmkInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblTermZmkInf> getReferenceClass () {
	return com.allinfinance.po.TblTermZmkInf.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblTermZmkInf
 */
public com.allinfinance.po.TblTermZmkInf cast (Object object) {
	return (com.allinfinance.po.TblTermZmkInf) object;
}


public com.allinfinance.po.TblTermZmkInf load(com.allinfinance.po.TblTermZmkInfPK key)
{
	return (com.allinfinance.po.TblTermZmkInf) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblTermZmkInf get(com.allinfinance.po.TblTermZmkInfPK key)
{
	return (com.allinfinance.po.TblTermZmkInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblTermZmkInf> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTermZmkInf a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblTermZmkInfPK save(com.allinfinance.po.TblTermZmkInf tblTermZmkInf)
{
	return (com.allinfinance.po.TblTermZmkInfPK) super.save(tblTermZmkInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTermZmkInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblTermZmkInf tblTermZmkInf)
{
	super.saveOrUpdate(tblTermZmkInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTermZmkInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblTermZmkInf tblTermZmkInf)
{
	super.update(tblTermZmkInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTermZmkInf the instance to be removed
 */
public void delete(com.allinfinance.po.TblTermZmkInf tblTermZmkInf)
{
	super.delete((Object) tblTermZmkInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblTermZmkInfPK id)
{
	super.delete((Object) load(id));
}

}