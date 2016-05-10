package com.allinfinance.dao.impl.agentpay;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.agentpay.TblBankInfo;

public class TblBankInfoDAO extends _RootDAO<com.allinfinance.po.agentpay.TblBankInfo> implements com.allinfinance.dao.iface.agentpay.TblBankInfoDAO {

	@Override
	protected Class<com.allinfinance.po.agentpay.TblBankInfo> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.agentpay.TblBankInfo.class;
	}
	public com.allinfinance.po.agentpay.TblBankInfo cast (Object object) {
		return (com.allinfinance.po.agentpay.TblBankInfo) object;
	}
	public com.allinfinance.po.agentpay.TblBankInfo load(java.lang.String key)
	{
		return (com.allinfinance.po.agentpay.TblBankInfo) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.agentpay.TblBankInfo get(java.lang.String key)
	{
		return (com.allinfinance.po.agentpay.TblBankInfo) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<com.allinfinance.po.agentpay.TblBankInfo> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblBankInfo a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.allinfinance.po.agentpay.TblBankInfo tblBankInfo)
	{
		return (java.lang.String) super.save(tblBankInfo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblBankInfo a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.agentpay.TblBankInfo tblBankInfo)
	{
		super.saveOrUpdate(tblBankInfo);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblBankInfo a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.agentpay.TblBankInfo tblBankInfo)
	{
		super.update(tblBankInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblBankInfo the instance to be removed
	 */
	public void delete(com.allinfinance.po.agentpay.TblBankInfo tblBankInfo)
	{
		super.delete((Object) tblBankInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
	@Override
	public List<TblBankInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
