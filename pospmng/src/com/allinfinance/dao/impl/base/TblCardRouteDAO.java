package com.allinfinance.dao.impl.base;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblCardRoute;


public class TblCardRouteDAO extends _RootDAO<com.allinfinance.po.TblCardRoute> implements com.allinfinance.dao.iface.base.TblCardRouteDAO{

public TblCardRouteDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblCardRouteDAO#findAll()
 */
public List<TblCardRoute> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblCardRoute> getReferenceClass () {
	return com.allinfinance.po.TblCardRoute.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblCardRoute
 */
public com.allinfinance.po.TblCardRoute cast (Object object) {
	return (com.allinfinance.po.TblCardRoute) object;
}


public com.allinfinance.po.TblCardRoute load(com.allinfinance.po.TblCardRoutePK key)
{
	return (com.allinfinance.po.TblCardRoute) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblCardRoute get(com.allinfinance.po.TblCardRoutePK key)
{
	return (com.allinfinance.po.TblCardRoute) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblCardRoute> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblCardRoute a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblCardRoutePK save(com.allinfinance.po.TblCardRoute tblCardRoute)
{
	return (com.allinfinance.po.TblCardRoutePK) super.save(tblCardRoute);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblCardRoute a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblCardRoute tblCardRoute)
{
	super.saveOrUpdate(tblCardRoute);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblCardRoute a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblCardRoute tblCardRoute)
{
	super.update(tblCardRoute);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblCardRoute the instance to be removed
 */
public void delete(com.allinfinance.po.TblCardRoute tblCardRoute)
{
	super.delete((Object) tblCardRoute);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblCardRoutePK id)
{
	super.delete((Object) load(id));
}

}