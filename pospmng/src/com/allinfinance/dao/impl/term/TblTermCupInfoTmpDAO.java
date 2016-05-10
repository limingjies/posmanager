package com.allinfinance.dao.impl.term;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermCupInfoTmp;


public class TblTermCupInfoTmpDAO extends _RootDAO<com.allinfinance.po.TblTermCupInfoTmp> implements com.allinfinance.dao.iface.term.TblTermCupInfoTmpDAO{

public TblTermCupInfoTmpDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblTermCupInfoTmpDAO#findAll()
 */
public List<TblTermCupInfoTmp> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblTermCupInfoTmp> getReferenceClass () {
	return com.allinfinance.po.TblTermCupInfoTmp.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblTermCupInfoTmp
 */
public com.allinfinance.po.TblTermCupInfoTmp cast (Object object) {
	return (com.allinfinance.po.TblTermCupInfoTmp) object;
}


public com.allinfinance.po.TblTermCupInfoTmp load(java.lang.String key)
{
	return (com.allinfinance.po.TblTermCupInfoTmp) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblTermCupInfoTmp get(java.lang.String key)
{
	return (com.allinfinance.po.TblTermCupInfoTmp) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblTermCupInfoTmp> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTermCupInfoTmp a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.allinfinance.po.TblTermCupInfoTmp tblTermCupInfoTmp)
{
	return (java.lang.String) super.save(tblTermCupInfoTmp);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTermCupInfoTmp a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblTermCupInfoTmp tblTermCupInfoTmp)
{
	super.saveOrUpdate(tblTermCupInfoTmp);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblTermCupInfoTmp a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblTermCupInfoTmp tblTermCupInfoTmp)
{
	super.update(tblTermCupInfoTmp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTermCupInfoTmp the instance to be removed
 */
public void delete(com.allinfinance.po.TblTermCupInfoTmp tblTermCupInfoTmp)
{
	super.delete((Object) tblTermCupInfoTmp);
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