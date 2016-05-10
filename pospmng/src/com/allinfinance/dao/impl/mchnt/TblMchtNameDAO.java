package com.allinfinance.dao.impl.mchnt;


import com.allinfinance.dao._RootDAO;


public class TblMchtNameDAO extends _RootDAO<com.allinfinance.po.mchnt.TblMchtName> implements com.allinfinance.dao.iface.mchnt.TblMchtNameDAO{

public TblMchtNameDAO () {}



public Class<com.allinfinance.po.mchnt.TblMchtName> getReferenceClass () {
	return com.allinfinance.po.mchnt.TblMchtName.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblRiskInf
 */
public com.allinfinance.po.mchnt.TblMchtName cast (Object object) {
	return (com.allinfinance.po.mchnt.TblMchtName) object;
}


public com.allinfinance.po.mchnt.TblMchtName load(com.allinfinance.po.mchnt.TblMchtNamePK tblMchtNamePK)
{
	return (com.allinfinance.po.mchnt.TblMchtName) load(getReferenceClass(), tblMchtNamePK);
}

public com.allinfinance.po.mchnt.TblMchtName get(com.allinfinance.po.mchnt.TblMchtNamePK tblMchtNamePK)
{
	return (com.allinfinance.po.mchnt.TblMchtName) get(getReferenceClass(), tblMchtNamePK);
}






/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.allinfinance.po.mchnt.TblMchtName tblMchtName)
{
	  super.save(tblMchtName);
}



/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.mchnt.TblMchtName tblMchtName)
{
	super.update(tblMchtName);
}


/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.mchnt.TblMchtNamePK tblMchtNamePK)
{
	super.delete((Object) load(tblMchtNamePK));
}

}