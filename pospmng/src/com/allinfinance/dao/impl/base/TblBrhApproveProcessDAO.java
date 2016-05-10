package com.allinfinance.dao.impl.base;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblBrhApproveProcess;
import com.allinfinance.system.util.CommonFunction;


public class TblBrhApproveProcessDAO extends _RootDAO<com.allinfinance.po.TblBrhApproveProcess> implements com.allinfinance.dao.iface.base.TblBrhApproveProcessDAO{

public TblBrhApproveProcessDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblBrhApproveProcessDAO#findAll()
 */
public List<TblBrhApproveProcess> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblBrhApproveProcess> getReferenceClass () {
	return com.allinfinance.po.TblBrhApproveProcess.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblBrhApproveProcess
 */
public com.allinfinance.po.TblBrhApproveProcess cast (Object object) {
	return (com.allinfinance.po.TblBrhApproveProcess) object;
}


public com.allinfinance.po.TblBrhApproveProcess load(com.allinfinance.po.TblBrhApproveProcessPK key)
{
	return (com.allinfinance.po.TblBrhApproveProcess) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblBrhApproveProcess get(com.allinfinance.po.TblBrhApproveProcessPK key)
{
	return (com.allinfinance.po.TblBrhApproveProcess) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblBrhApproveProcess> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblBrhApproveProcess a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblBrhApproveProcessPK save(com.allinfinance.po.TblBrhApproveProcess tblBrhApproveProcess)
{
	return (com.allinfinance.po.TblBrhApproveProcessPK) super.save(tblBrhApproveProcess);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblBrhApproveProcess a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblBrhApproveProcess tblBrhApproveProcess)
{
	super.saveOrUpdate(tblBrhApproveProcess);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblBrhApproveProcess a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblBrhApproveProcess tblBrhApproveProcess)
{
	super.update(tblBrhApproveProcess);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblBrhApproveProcess the instance to be removed
 */
public void delete(com.allinfinance.po.TblBrhApproveProcess tblBrhApproveProcess)
{
	super.delete((Object) tblBrhApproveProcess);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblBrhApproveProcessPK id)
{
	super.delete((Object) load(id));
}

public void delete(java.lang.String brhId) {
	String sql="DELETE FROM TBL_BRH_APPROVE_PROCESS WHERE BRH_ID='"+brhId+"' ";
	CommonFunction.getCommQueryDAO().excute(sql);
}

}