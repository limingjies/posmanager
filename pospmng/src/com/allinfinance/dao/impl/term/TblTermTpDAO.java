package com.allinfinance.dao.impl.term;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermTp;


public class TblTermTpDAO extends _RootDAO<com.allinfinance.po.TblTermTp> implements com.allinfinance.dao.iface.term.TblTermTpDAO{

public TblTermTpDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblTermTpDAO#findAll()
 */
public List<TblTermTp> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblTermTp> getReferenceClass () {
	return com.allinfinance.po.TblTermTp.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblTermTp
 */
public com.allinfinance.po.TblTermTp cast (Object object) {
	return (com.allinfinance.po.TblTermTp) object;
}


public com.allinfinance.po.TblTermTp load(java.lang.String key)
{
	return (com.allinfinance.po.TblTermTp) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblTermTp get(java.lang.String key)
{
	return (com.allinfinance.po.TblTermTp) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblTermTp> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTermTp a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.allinfinance.po.TblTermTp tblTermTp)
{
	return (java.lang.String) super.save(tblTermTp);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTermTp a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblTermTp tblTermTp)
{
	super.saveOrUpdate(tblTermTp);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTermTp a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblTermTp tblTermTp)
{
	super.update(tblTermTp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTermTp the instance to be removed
 */
public void delete(com.allinfinance.po.TblTermTp tblTermTp)
{
	super.delete((Object) tblTermTp);
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