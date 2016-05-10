package com.allinfinance.dao.impl.agentpay;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.agentpay.TblStlmFileTransInf;

public class TblStlmFileTransInfDAO extends _RootDAO<com.allinfinance.po.agentpay.TblStlmFileTransInf> implements com.allinfinance.dao.iface.agentpay.TblStlmFileTransInfDAO {

	@Override
	protected Class<com.allinfinance.po.agentpay.TblStlmFileTransInf> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.agentpay.TblStlmFileTransInf.class;
	}
	public com.allinfinance.po.agentpay.TblStlmFileTransInf cast (Object object) {
		return (com.allinfinance.po.agentpay.TblStlmFileTransInf) object;
	}
	public com.allinfinance.po.agentpay.TblStlmFileTransInf load(com.allinfinance.po.agentpay.TblStlmFileTransInfPK key)
	{
		return (com.allinfinance.po.agentpay.TblStlmFileTransInf) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.agentpay.TblStlmFileTransInf get(com.allinfinance.po.agentpay.TblStlmFileTransInfPK key)
	{
		return (com.allinfinance.po.agentpay.TblStlmFileTransInf) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<com.allinfinance.po.agentpay.TblStlmFileTransInf> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param TblStlmFileTransInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.allinfinance.po.agentpay.TblStlmFileTransInfPK save(com.allinfinance.po.agentpay.TblStlmFileTransInf tblStlmFileTransInf)
	{
		return (com.allinfinance.po.agentpay.TblStlmFileTransInfPK) super.save(tblStlmFileTransInf);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param TblStlmFileTransInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.agentpay.TblStlmFileTransInf tblStlmFileTransInf)
	{
		super.saveOrUpdate(tblStlmFileTransInf);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param TblStlmFileTransInf a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.agentpay.TblStlmFileTransInf tblStlmFileTransInf)
	{
		super.update(tblStlmFileTransInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param TblStlmFileTransInf the instance to be removed
	 */
	public void delete(com.allinfinance.po.agentpay.TblStlmFileTransInf tblStlmFileTransInf)
	{
		super.delete((Object) tblStlmFileTransInf);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.agentpay.TblStlmFileTransInfPK id)
	{
		super.delete((Object) load(id));
	}
	@Override
	public List<TblStlmFileTransInf> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
