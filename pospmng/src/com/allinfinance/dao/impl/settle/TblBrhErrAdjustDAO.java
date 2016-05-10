package com.allinfinance.dao.impl.settle;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblBrhErrAdjust;


public class TblBrhErrAdjustDAO extends _RootDAO<com.allinfinance.po.TblRiskWhite> implements com.allinfinance.dao.iface.settle.TblBrhErrAdjustDAO{

public TblBrhErrAdjustDAO () {}



public Class<com.allinfinance.po.settle.TblBrhErrAdjust> getReferenceClass () {
	return com.allinfinance.po.settle.TblBrhErrAdjust.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblRiskInf
 */
public com.allinfinance.po.settle.TblBrhErrAdjust cast (Object object) {
	return (com.allinfinance.po.settle.TblBrhErrAdjust) object;
}


public com.allinfinance.po.settle.TblBrhErrAdjust load(String id)
{
	return (com.allinfinance.po.settle.TblBrhErrAdjust) load(getReferenceClass(), id);
}

public com.allinfinance.po.settle.TblBrhErrAdjust get(String id)
{
	return (com.allinfinance.po.settle.TblBrhErrAdjust) get(getReferenceClass(), id);
}






/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.allinfinance.po.settle.TblBrhErrAdjust tblMchtErrAdjust)
{
	  super.save(tblMchtErrAdjust);
}



/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.settle.TblBrhErrAdjust tblMchtErrAdjust)
{
	super.update(tblMchtErrAdjust);
}


/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(String id)
{
	super.delete((Object) load(id));
}



@Override
public void delete(TblBrhErrAdjust tblMchtErrAdjust) {
super.delete(tblMchtErrAdjust);	
}

}