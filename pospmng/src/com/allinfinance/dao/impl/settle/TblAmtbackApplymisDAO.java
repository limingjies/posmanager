package com.allinfinance.dao.impl.settle;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblAmtbackApplyPKmis;
import com.allinfinance.po.TblAmtbackApplymis;


public  class TblAmtbackApplymisDAO extends _RootDAO<com.allinfinance.po.TblRiskWhite> implements com.allinfinance.dao.iface.settle.TblAmtbackApplymisDAO{

public TblAmtbackApplymisDAO () {}



public Class<com.allinfinance.po.TblAmtbackApplymis> getReferenceClass () {
	return com.allinfinance.po.TblAmtbackApplymis.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblRiskInf
 */
public com.allinfinance.po.TblAmtbackApplymis cast (Object object) {
	return (com.allinfinance.po.TblAmtbackApplymis) object;
}


public com.allinfinance.po.TblAmtbackApplymis load(TblAmtbackApplyPKmis tblAmtbackApplyPKmis)
{
	return (com.allinfinance.po.TblAmtbackApplymis) load(getReferenceClass(), tblAmtbackApplyPKmis);
}

public com.allinfinance.po.TblAmtbackApplymis get(TblAmtbackApplyPKmis tblAmtbackApplyPKmis)
{
	return (com.allinfinance.po.TblAmtbackApplymis) get(getReferenceClass(), tblAmtbackApplyPKmis);
}







/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.allinfinance.po.TblAmtbackApplymis tblAmtbackApply_mis)
{
	  super.save(tblAmtbackApply_mis);
}



/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblAmtbackApplymis tblAmtbackApplymis)
{
	super.update(tblAmtbackApplymis);
}


/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(TblAmtbackApplyPKmis tblAmtbackApplyPKmis)
{
	super.delete((Object) load(tblAmtbackApplyPKmis));
}

}