package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblRiskWhitePK;


public class TblRiskWhiteDAO extends _RootDAO<com.allinfinance.po.TblRiskWhite> implements com.allinfinance.dao.iface.risk.TblRiskWhiteDAO{

public TblRiskWhiteDAO () {}



public Class<com.allinfinance.po.TblRiskWhite> getReferenceClass () {
	return com.allinfinance.po.TblRiskWhite.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblRiskInf
 */
public com.allinfinance.po.TblRiskWhite cast (Object object) {
	return (com.allinfinance.po.TblRiskWhite) object;
}


public com.allinfinance.po.TblRiskWhite load(TblRiskWhitePK tblRiskWhitePK)
{
	return (com.allinfinance.po.TblRiskWhite) load(getReferenceClass(), tblRiskWhitePK);
}

public com.allinfinance.po.TblRiskWhite get(TblRiskWhitePK tblRiskWhitePK)
{
	return (com.allinfinance.po.TblRiskWhite) get(getReferenceClass(), tblRiskWhitePK);
}






/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.allinfinance.po.TblRiskWhite tblRiskWhite)
{
	  super.save(tblRiskWhite);
}



/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblRiskWhite tblRiskWhite)
{
	super.update(tblRiskWhite);
}


/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(TblRiskWhitePK tblRiskWhitePk)
{
	super.delete((Object) load(tblRiskWhitePk));
}

}