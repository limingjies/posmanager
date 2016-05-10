package com.allinfinance.dao.impl.mchnt;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblMchntRefuse;


public class TblMchntRefuseDAO extends _RootDAO<com.allinfinance.po.TblMchntRefuse> implements com.allinfinance.dao.iface.mchnt.TblMchntRefuseDAO{

public TblMchntRefuseDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblMchntRefuseDAO#findAll()
 */
public List<TblMchntRefuse> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblMchntRefuse> getReferenceClass () {
	return com.allinfinance.po.TblMchntRefuse.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblMchntRefuse
 */
public com.allinfinance.po.TblMchntRefuse cast (Object object) {
	return (com.allinfinance.po.TblMchntRefuse) object;
}


public com.allinfinance.po.TblMchntRefuse load(com.allinfinance.po.TblMchntRefusePK key)
{
	return (com.allinfinance.po.TblMchntRefuse) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblMchntRefuse get(com.allinfinance.po.TblMchntRefusePK key)
{
	return (com.allinfinance.po.TblMchntRefuse) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblMchntRefuse> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblMchntRefuse a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblMchntRefusePK save(com.allinfinance.po.TblMchntRefuse tblMchntRefuse)
{
	return (com.allinfinance.po.TblMchntRefusePK) super.save(tblMchntRefuse);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblMchntRefuse a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblMchntRefuse tblMchntRefuse)
{
	super.saveOrUpdate(tblMchntRefuse);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblMchntRefuse a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblMchntRefuse tblMchntRefuse)
{
	super.update(tblMchntRefuse);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblMchntRefuse the instance to be removed
 */
public void delete(com.allinfinance.po.TblMchntRefuse tblMchntRefuse)
{
	super.delete((Object) tblMchntRefuse);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblMchntRefusePK id)
{
	super.delete((Object) load(id));
}

}