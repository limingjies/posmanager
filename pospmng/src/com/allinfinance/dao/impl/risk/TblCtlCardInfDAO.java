package com.allinfinance.dao.impl.risk;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblCtlCardInf;


public class TblCtlCardInfDAO extends _RootDAO<com.allinfinance.po.TblCtlCardInf> implements com.allinfinance.dao.iface.risk.TblCtlCardInfDAO{

public TblCtlCardInfDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblCtlCardInfDAO#findAll()
 */
public List<TblCtlCardInf> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblCtlCardInf> getReferenceClass () {
	return com.allinfinance.po.TblCtlCardInf.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblCtlCardInf
 */
public com.allinfinance.po.TblCtlCardInf cast (Object object) {
	return (com.allinfinance.po.TblCtlCardInf) object;
}


public com.allinfinance.po.TblCtlCardInf load(java.lang.String key)
{
	return (com.allinfinance.po.TblCtlCardInf) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblCtlCardInf get(java.lang.String key)
{
	return (com.allinfinance.po.TblCtlCardInf) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblCtlCardInf> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblCtlCardInf a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.allinfinance.po.TblCtlCardInf tblCtlCardInf)
{
	return (java.lang.String) super.save(tblCtlCardInf);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblCtlCardInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblCtlCardInf tblCtlCardInf)
{
	super.saveOrUpdate(tblCtlCardInf);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblCtlCardInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblCtlCardInf tblCtlCardInf)
{
	super.update(tblCtlCardInf);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblCtlCardInf the instance to be removed
 */
public void delete(com.allinfinance.po.TblCtlCardInf tblCtlCardInf)
{
	super.delete((Object) tblCtlCardInf);
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