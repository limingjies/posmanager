package com.allinfinance.dao.impl.mchnt;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblDivMchnt;


public class TblDivMchntDAO extends _RootDAO<com.allinfinance.po.TblDivMchnt> implements com.allinfinance.dao.iface.mchnt.TblDivMchntDAO{

public TblDivMchntDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblDivMchntDAO#findAll()
 */
public List<TblDivMchnt> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblDivMchnt> getReferenceClass () {
	return com.allinfinance.po.TblDivMchnt.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblDivMchnt
 */
public com.allinfinance.po.TblDivMchnt cast (Object object) {
	return (com.allinfinance.po.TblDivMchnt) object;
}


public com.allinfinance.po.TblDivMchnt load(com.allinfinance.po.TblDivMchntPK key)
{
	return (com.allinfinance.po.TblDivMchnt) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblDivMchnt get(com.allinfinance.po.TblDivMchntPK key)
{
	return (com.allinfinance.po.TblDivMchnt) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblDivMchnt> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblDivMchnt a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblDivMchntPK save(com.allinfinance.po.TblDivMchnt tblDivMchnt)
{
	return (com.allinfinance.po.TblDivMchntPK) super.save(tblDivMchnt);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblDivMchnt a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblDivMchnt tblDivMchnt)
{
	super.saveOrUpdate(tblDivMchnt);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblDivMchnt a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblDivMchnt tblDivMchnt)
{
	super.update(tblDivMchnt);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblDivMchnt the instance to be removed
 */
public void delete(com.allinfinance.po.TblDivMchnt tblDivMchnt)
{
	super.delete((Object) tblDivMchnt);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblDivMchntPK id)
{
	super.delete((Object) load(id));
}
}