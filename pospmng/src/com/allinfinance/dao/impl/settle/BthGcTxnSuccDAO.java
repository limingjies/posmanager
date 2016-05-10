package com.allinfinance.dao.impl.settle;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.BthGcTxnSuccPK;


public class BthGcTxnSuccDAO extends _RootDAO<com.allinfinance.po.TblRiskWhite> implements com.allinfinance.dao.iface.settle.BthGcTxnSuccDAO{

public BthGcTxnSuccDAO () {}



public Class<com.allinfinance.po.BthGcTxnSucc> getReferenceClass () {
	return com.allinfinance.po.BthGcTxnSucc.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblRiskInf
 */
public com.allinfinance.po.BthGcTxnSucc cast (Object object) {
	return (com.allinfinance.po.BthGcTxnSucc) object;
}


public com.allinfinance.po.BthGcTxnSucc load(BthGcTxnSuccPK bthGcTxnSuccPK)
{
	return (com.allinfinance.po.BthGcTxnSucc) load(getReferenceClass(), bthGcTxnSuccPK);
}

public com.allinfinance.po.BthGcTxnSucc get(BthGcTxnSuccPK bthGcTxnSuccPK)
{
	return (com.allinfinance.po.BthGcTxnSucc) get(getReferenceClass(), bthGcTxnSuccPK);
}






/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.allinfinance.po.BthGcTxnSucc bthGcTxnSucc)
{
	  super.save(bthGcTxnSucc);
}



/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.BthGcTxnSucc bthGcTxnSucc)
{
	super.update(bthGcTxnSucc);
}


/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(BthGcTxnSuccPK bthGcTxnSuccPK)
{
	super.delete((Object) load(bthGcTxnSuccPK));
}

}