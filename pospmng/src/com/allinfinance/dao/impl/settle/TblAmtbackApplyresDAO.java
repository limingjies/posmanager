package com.allinfinance.dao.impl.settle;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblAmtbackApplyPKres;


public class TblAmtbackApplyresDAO extends _RootDAO<com.allinfinance.po.TblRiskWhite> implements com.allinfinance.dao.iface.settle.TblAmtbackApplyresDAO{

public TblAmtbackApplyresDAO () {}



public Class<com.allinfinance.po.TblAmtbackApplyres> getReferenceClass () {
	return com.allinfinance.po.TblAmtbackApplyres.class;
}





public com.allinfinance.po.TblAmtbackApplyres load(TblAmtbackApplyPKres tblAmtbackApplyPKres)
{
	return (com.allinfinance.po.TblAmtbackApplyres) load(getReferenceClass(), tblAmtbackApplyPKres);
}

public com.allinfinance.po.TblAmtbackApplyres get(TblAmtbackApplyPKres tblAmtbackApplyPKres)
{
	return (com.allinfinance.po.TblAmtbackApplyres) get(getReferenceClass(), tblAmtbackApplyPKres);
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.allinfinance.po.TblAmtbackApplyres tblAmtbackApply)
{
	  super.save(tblAmtbackApply);
}



/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblAmtbackApplyres tblAmtbackApply)
{
	super.update(tblAmtbackApply);
}

}