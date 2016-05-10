package com.allinfinance.dao.impl.term;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermRefuse;


public class TblTermRefuseDAO extends _RootDAO<com.allinfinance.po.TblTermRefuse> implements com.allinfinance.dao.iface.term.TblTermRefuseDAO{

public TblTermRefuseDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblTermRefuseDAO#findAll()
 */
public List<TblTermRefuse> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblTermRefuse> getReferenceClass () {
	return com.allinfinance.po.TblTermRefuse.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblTermRefuse
 */
public com.allinfinance.po.TblTermRefuse cast (Object object) {
	return (com.allinfinance.po.TblTermRefuse) object;
}


public com.allinfinance.po.TblTermRefuse load(com.allinfinance.po.TblTermRefusePK key)
{
	return (com.allinfinance.po.TblTermRefuse) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblTermRefuse get(com.allinfinance.po.TblTermRefusePK key)
{
	return (com.allinfinance.po.TblTermRefuse) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblTermRefuse> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTermRefuse a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblTermRefusePK save(com.allinfinance.po.TblTermRefuse tblTermRefuse)
{
	return (com.allinfinance.po.TblTermRefusePK) super.save(tblTermRefuse);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTermRefuse a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblTermRefuse tblTermRefuse)
{
	super.saveOrUpdate(tblTermRefuse);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTermRefuse a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblTermRefuse tblTermRefuse)
{
	super.update(tblTermRefuse);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTermRefuse the instance to be removed
 */
public void delete(com.allinfinance.po.TblTermRefuse tblTermRefuse)
{
	super.delete((Object) tblTermRefuse);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblTermRefusePK id)
{
	super.delete((Object) load(id));
}

}