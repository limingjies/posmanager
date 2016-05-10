package com.allinfinance.dao.impl.risk;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblRiskLvl;
import com.allinfinance.po.TblRiskLvlPK;


public class TblRiskLvlDAO extends _RootDAO<com.allinfinance.po.TblRiskLvl> implements com.allinfinance.dao.iface.risk.TblRiskLvlDAO{

public TblRiskLvlDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblRiskInfDAO#findAll()
 */
public List<TblRiskLvl> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblRiskLvl> getReferenceClass () {
	return com.allinfinance.po.TblRiskLvl.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblRiskInf
 */
public com.allinfinance.po.TblRiskLvl cast (Object object) {
	return (com.allinfinance.po.TblRiskLvl) object;
}


public com.allinfinance.po.TblRiskLvl load(TblRiskLvlPK tblRiskLvlPK)
{
	return (com.allinfinance.po.TblRiskLvl) load(getReferenceClass(), tblRiskLvlPK);
}

public com.allinfinance.po.TblRiskLvl get(TblRiskLvlPK tblRiskLvlPK)
{
	return (com.allinfinance.po.TblRiskLvl) get(getReferenceClass(), tblRiskLvlPK);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblRiskLvl> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblRiskInf a transient instance of a persistent class
 * @return the class identifier
 */
public void save(com.allinfinance.po.TblRiskLvl tblRiskLvl)
{
	  super.save(tblRiskLvl);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblRiskInf a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblRiskLvl tblRiskLvl)
{
	super.saveOrUpdate(tblRiskLvl);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblRiskInf a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblRiskLvl tblRiskLvl)
{
	super.update(tblRiskLvl);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblRiskInf the instance to be removed
 */
public void delete(com.allinfinance.po.TblRiskLvl tblRiskLvl)
{
	super.delete((Object) tblRiskLvl);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(TblRiskLvlPK tblRiskLvlPk)
{
	super.delete((Object) load(tblRiskLvlPk));
}

}