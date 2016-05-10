package com.allinfinance.dao.impl.settle;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblAmtbackApplyPK;


public class TblAmtbackApplyDAO extends _RootDAO<com.allinfinance.po.TblRiskWhite> implements com.allinfinance.dao.iface.settle.TblAmtbackApplyDAO{

public TblAmtbackApplyDAO () {}



public Class<com.allinfinance.po.TblAmtbackApply> getReferenceClass () {
	return com.allinfinance.po.TblAmtbackApply.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblRiskInf
 */
public com.allinfinance.po.TblAmtbackApply cast (Object object) {
	return (com.allinfinance.po.TblAmtbackApply) object;
}


public com.allinfinance.po.TblAmtbackApply load(TblAmtbackApplyPK tblAmtbackApplyPK)
{
	return (com.allinfinance.po.TblAmtbackApply) load(getReferenceClass(), tblAmtbackApplyPK);
}

public com.allinfinance.po.TblAmtbackApply get(TblAmtbackApplyPK tblAmtbackApplyPK)
{
	return (com.allinfinance.po.TblAmtbackApply) get(getReferenceClass(), tblAmtbackApplyPK);
}






/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.allinfinance.po.TblAmtbackApply tblAmtbackApply)
{
	  super.save(tblAmtbackApply);
}



/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblAmtbackApply tblAmtbackApply)
{
	super.update(tblAmtbackApply);
}


/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(TblAmtbackApplyPK tblAmtbackApplyPK)
{
	super.delete((Object) load(tblAmtbackApplyPK));
}

}