package com.allinfinance.dao.base;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblCtlMchtSettleInf;


public class TblCtlMchtSettleInfDAO extends _RootDAO<com.allinfinance.po.TblCtlMchtSettleInf> implements com.allinfinance.dao.iface.risk.TblCtlMchtSettleInfDAO{

public TblCtlMchtSettleInfDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblCtlMchtSettleInfDAO#findAll()
 */
public List<TblCtlMchtSettleInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblCtlMchtSettleInf> getReferenceClass () {
	return com.allinfinance.po.TblCtlMchtSettleInf.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblCtlMchtSettleInf
 */
public com.allinfinance.po.TblCtlMchtSettleInf cast (Object object) {
	return (com.allinfinance.po.TblCtlMchtSettleInf) object;
}


public com.allinfinance.po.TblCtlMchtSettleInf load(com.allinfinance.po.TblCtlMchtSettleInfPK key)
{
	return (com.allinfinance.po.TblCtlMchtSettleInf) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblCtlMchtSettleInf get(com.allinfinance.po.TblCtlMchtSettleInfPK key)
{
	return (com.allinfinance.po.TblCtlMchtSettleInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblCtlMchtSettleInf> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param TblCtlMchtSettleInf a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblCtlMchtSettleInfPK save(com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf)
{
	return (com.allinfinance.po.TblCtlMchtSettleInfPK) super.save(tblCtlMchtSettleInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblCtlMchtSettleInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf)
{
	super.saveOrUpdate(tblCtlMchtSettleInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblCtlMchtSettleInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf)
{
	super.update(tblCtlMchtSettleInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblCtlMchtSettleInf the instance to be removed
 */
public void delete(com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf)
{
	super.delete((Object) tblCtlMchtSettleInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblCtlMchtSettleInfPK id)
{
	super.delete((Object) load(id));
}


}