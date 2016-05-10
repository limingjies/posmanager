package com.allinfinance.dao;

import java.util.List;

import com.allinfinance.po.TblRoleFuncMap;


public class TblRoleFuncMapDAO extends _RootDAO<com.allinfinance.po.TblRiskInf> implements com.allinfinance.dao.iface.TblRoleFuncMapDAO{

public TblRoleFuncMapDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblRoleFuncMapDAO#findAll()
 */
public List<TblRoleFuncMap> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblRoleFuncMap> getReferenceClass () {
	return com.allinfinance.po.TblRoleFuncMap.class;
}


/**
 * Cast the object as a com.allinfinance.po.role.TblRoleFuncMap
 */
public com.allinfinance.po.TblRoleFuncMap cast (Object object) {
	return (com.allinfinance.po.TblRoleFuncMap) object;
}


public com.allinfinance.po.TblRoleFuncMap load(com.allinfinance.po.TblRoleFuncMapPK key)
{
	return (com.allinfinance.po.TblRoleFuncMap) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblRoleFuncMap get(com.allinfinance.po.TblRoleFuncMapPK key)
{
	return (com.allinfinance.po.TblRoleFuncMap) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblRoleFuncMap> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRoleFuncMap a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblRoleFuncMapPK save(com.allinfinance.po.TblRoleFuncMap tblRoleFuncMap)
{
	return (com.allinfinance.po.TblRoleFuncMapPK) super.save(tblRoleFuncMap);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblRoleFuncMap a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblRoleFuncMap tblRoleFuncMap)
{
	super.saveOrUpdate(tblRoleFuncMap);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRoleFuncMap a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblRoleFuncMap tblRoleFuncMap)
{
	super.update(tblRoleFuncMap);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblRoleFuncMap the instance to be removed
 */
public void delete(com.allinfinance.po.TblRoleFuncMap tblRoleFuncMap)
{
	super.delete((Object) tblRoleFuncMap);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblRoleFuncMapPK id)
{
	super.delete((Object) load(id));
}

}