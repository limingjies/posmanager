package com.allinfinance.dao.impl.mchnt;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblMchtCupInfo;


public class TblMchtCupInfoDAO extends _RootDAO<com.allinfinance.po.TblMchtCupInfo> implements com.allinfinance.dao.iface.mchnt.TblMchtCupInfoDAO{

public TblMchtCupInfoDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblMchtCupInfoDAO#findAll()
 */
public List<TblMchtCupInfo> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblMchtCupInfo> getReferenceClass () {
	return com.allinfinance.po.TblMchtCupInfo.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblMchtCupInfo
 */
public com.allinfinance.po.TblMchtCupInfo cast (Object object) {
	return (com.allinfinance.po.TblMchtCupInfo) object;
}


public com.allinfinance.po.TblMchtCupInfo load(java.lang.String key)
{
	return (com.allinfinance.po.TblMchtCupInfo) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblMchtCupInfo get(java.lang.String key)
{
	return (com.allinfinance.po.TblMchtCupInfo) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblMchtCupInfo> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblMchtCupInfo a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.allinfinance.po.TblMchtCupInfo tblMchtCupInfo)
{
	return (java.lang.String) super.save(tblMchtCupInfo);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblMchtCupInfo a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblMchtCupInfo tblMchtCupInfo)
{
	super.saveOrUpdate(tblMchtCupInfo);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblMchtCupInfo a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblMchtCupInfo tblMchtCupInfo)
{
	super.update(tblMchtCupInfo);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblMchtCupInfo the instance to be removed
 */
public void delete(com.allinfinance.po.TblMchtCupInfo tblMchtCupInfo)
{
	super.delete((Object) tblMchtCupInfo);
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