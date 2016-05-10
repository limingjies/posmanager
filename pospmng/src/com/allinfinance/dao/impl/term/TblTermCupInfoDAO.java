package com.allinfinance.dao.impl.term;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermCupInfo;


public class TblTermCupInfoDAO extends _RootDAO<com.allinfinance.po.TblTermCupInfo> implements com.allinfinance.dao.iface.term.TblTermCupInfoDAO{

public TblTermCupInfoDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblTermCupInfoDAO#findAll()
 */
public List<TblTermCupInfo> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblTermCupInfo> getReferenceClass () {
	return com.allinfinance.po.TblTermCupInfo.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblTermCupInfo
 */
public com.allinfinance.po.TblTermCupInfo cast (Object object) {
	return (com.allinfinance.po.TblTermCupInfo) object;
}


public com.allinfinance.po.TblTermCupInfo load(java.lang.String key)
{
	return (com.allinfinance.po.TblTermCupInfo) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblTermCupInfo get(java.lang.String key)
{
	return (com.allinfinance.po.TblTermCupInfo) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblTermCupInfo> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTermCupInfo a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.allinfinance.po.TblTermCupInfo tblTermCupInfo)
{
	return (java.lang.String) super.save(tblTermCupInfo);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTermCupInfo a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblTermCupInfo tblTermCupInfo)
{
	super.saveOrUpdate(tblTermCupInfo);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTermCupInfo a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblTermCupInfo tblTermCupInfo)
{
	super.update(tblTermCupInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTermCupInfo the instance to be removed
 */
public void delete(com.allinfinance.po.TblTermCupInfo tblTermCupInfo)
{
	super.delete((Object) tblTermCupInfo);
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