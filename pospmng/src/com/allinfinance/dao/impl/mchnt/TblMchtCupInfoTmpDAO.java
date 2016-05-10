package com.allinfinance.dao.impl.mchnt;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblMchtCupInfoTmp;


public class TblMchtCupInfoTmpDAO extends _RootDAO<com.allinfinance.po.TblMchtCupInfoTmp> implements com.allinfinance.dao.iface.mchnt.TblMchtCupInfoTmpDAO{

public TblMchtCupInfoTmpDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblMchtCupInfoTmpDAO#findAll()
 */
public List<TblMchtCupInfoTmp> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblMchtCupInfoTmp> getReferenceClass () {
	return com.allinfinance.po.TblMchtCupInfoTmp.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblMchtCupInfoTmp
 */
public com.allinfinance.po.TblMchtCupInfoTmp cast (Object object) {
	return (com.allinfinance.po.TblMchtCupInfoTmp) object;
}


public com.allinfinance.po.TblMchtCupInfoTmp load(java.lang.String key)
{
	return (com.allinfinance.po.TblMchtCupInfoTmp) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblMchtCupInfoTmp get(java.lang.String key)
{
	return (com.allinfinance.po.TblMchtCupInfoTmp) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblMchtCupInfoTmp> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblMchtCupInfoTmp a transient instance of a persistent class
 * @return the class identifier
 */
public java.lang.String save(com.allinfinance.po.TblMchtCupInfoTmp tblMchtCupInfoTmp)
{
	return (java.lang.String) super.save(tblMchtCupInfoTmp);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblMchtCupInfoTmp a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblMchtCupInfoTmp tblMchtCupInfoTmp)
{
	super.saveOrUpdate(tblMchtCupInfoTmp);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblMchtCupInfoTmp a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblMchtCupInfoTmp tblMchtCupInfoTmp)
{
	super.update(tblMchtCupInfoTmp);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblMchtCupInfoTmp the instance to be removed
 */
public void delete(com.allinfinance.po.TblMchtCupInfoTmp tblMchtCupInfoTmp)
{
	super.delete((Object) tblMchtCupInfoTmp);
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