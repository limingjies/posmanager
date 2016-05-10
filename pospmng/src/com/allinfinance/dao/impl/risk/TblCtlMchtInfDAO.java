package com.allinfinance.dao.impl.risk;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblCtlMchtInf;


public class TblCtlMchtInfDAO extends _RootDAO<com.allinfinance.po.TblCtlMchtInf> implements com.allinfinance.dao.iface.risk.TblCtlMchtInfDAO{

public TblCtlMchtInfDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblCtlMchtInfDAO#findAll()
 */
public List<TblCtlMchtInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblCtlMchtInf> getReferenceClass () {
	return com.allinfinance.po.TblCtlMchtInf.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblCtlMchtInf
 */
public com.allinfinance.po.TblCtlMchtInf cast (Object object) {
	return (com.allinfinance.po.TblCtlMchtInf) object;
}


public com.allinfinance.po.TblCtlMchtInf load(java.lang.String key)
{
	return (com.allinfinance.po.TblCtlMchtInf) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblCtlMchtInf get(java.lang.String key)
{
	return (com.allinfinance.po.TblCtlMchtInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblCtlMchtInf> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblCtlMchtInf a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.allinfinance.po.TblCtlMchtInf tblCtlMchtInf)
{
	return (java.lang.String) super.save(tblCtlMchtInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblCtlMchtInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblCtlMchtInf tblCtlMchtInf)
{
	super.saveOrUpdate(tblCtlMchtInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblCtlMchtInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblCtlMchtInf tblCtlMchtInf)
{
	super.update(tblCtlMchtInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblCtlMchtInf the instance to be removed
 */
public void delete(com.allinfinance.po.TblCtlMchtInf tblCtlMchtInf)
{
	super.delete((Object) tblCtlMchtInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(java.lang.String id)
{
	super.delete((Object) load(id));
}

}