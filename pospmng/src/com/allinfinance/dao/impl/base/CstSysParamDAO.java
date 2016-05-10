package com.allinfinance.dao.impl.base;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.CstSysParam;


public class CstSysParamDAO extends _RootDAO<com.allinfinance.po.CstSysParam> implements com.allinfinance.dao.iface.base.CstSysParamDAO{

public CstSysParamDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.CstSysParamDAO#findAll()
 */
public List<CstSysParam> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.CstSysParam> getReferenceClass () {
	return com.allinfinance.po.CstSysParam.class;
}


/**
 * Cast the object as a com.allinfinance.po.CstSysParam
 */
public com.allinfinance.po.CstSysParam cast (Object object) {
	return (com.allinfinance.po.CstSysParam) object;
}


public com.allinfinance.po.CstSysParam load(com.allinfinance.po.CstSysParamPK key)
{
	return (com.allinfinance.po.CstSysParam) load(getReferenceClass(), key);
}

public com.allinfinance.po.CstSysParam get(com.allinfinance.po.CstSysParamPK key)
{
	return (com.allinfinance.po.CstSysParam) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.CstSysParam> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param cstSysParam a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.CstSysParamPK save(com.allinfinance.po.CstSysParam cstSysParam)
{
	return (com.allinfinance.po.CstSysParamPK) super.save(cstSysParam);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param cstSysParam a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.CstSysParam cstSysParam)
{
	super.saveOrUpdate(cstSysParam);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param cstSysParam a transient instance containing updated state
 */
public void update(com.allinfinance.po.CstSysParam cstSysParam)
{
	super.update(cstSysParam);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param cstSysParam the instance to be removed
 */
public void delete(com.allinfinance.po.CstSysParam cstSysParam)
{
	super.delete((Object) cstSysParam);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.CstSysParamPK id)
{
	super.delete((Object) load(id));
}

}