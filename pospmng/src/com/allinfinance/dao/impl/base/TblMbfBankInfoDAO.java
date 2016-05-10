package com.allinfinance.dao.impl.base;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblMbfBankInfo;


public class TblMbfBankInfoDAO extends _RootDAO<com.allinfinance.po.TblMbfBankInfo> implements com.allinfinance.dao.iface.base.TblMbfBankInfoDAO {

	public TblMbfBankInfoDAO () {}
	
	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMbfBankInfoDAO#findAll()
	 */
	public List<TblMbfBankInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<com.allinfinance.po.TblMbfBankInfo> getReferenceClass () {
		return com.allinfinance.po.TblMbfBankInfo.class;
	}


	/**
	 * Cast the object as a com.allinfinance.po.brh.TblMbfBankInfo
	 */
	public com.allinfinance.po.TblMbfBankInfo cast (Object object) {
		return (com.allinfinance.po.TblMbfBankInfo) object;
	}


	public com.allinfinance.po.TblMbfBankInfo load(java.lang.String key)
	{
		return (com.allinfinance.po.TblMbfBankInfo) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.TblMbfBankInfo get(java.lang.String key)
	{
		return (com.allinfinance.po.TblMbfBankInfo) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param TblMbfBankInfo a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.allinfinance.po.TblMbfBankInfo tblMbfBankInfo)
	{
		return (java.lang.String) super.save(tblMbfBankInfo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMbfBankInfo a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.TblMbfBankInfo tblMbfBankInfo)
	{
		super.saveOrUpdate(tblMbfBankInfo);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMbfBankInfo a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblMbfBankInfo tblMbfBankInfo)
	{
		super.update(tblMbfBankInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMbfBankInfo the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblMbfBankInfo tblMbfBankInfo)
	{
		super.delete((Object) tblMbfBankInfo);
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
}