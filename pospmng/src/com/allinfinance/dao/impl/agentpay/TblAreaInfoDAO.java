package com.allinfinance.dao.impl.agentpay;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.agentpay.TblAreaInfo;

public class TblAreaInfoDAO extends _RootDAO<com.allinfinance.po.agentpay.TblAreaInfo> implements com.allinfinance.dao.iface.agentpay.TblAreaInfoDAO {

	@Override
	protected Class<com.allinfinance.po.agentpay.TblAreaInfo> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.agentpay.TblAreaInfo.class;
	}
	public com.allinfinance.po.agentpay.TblAreaInfo cast (Object object) {
		return (com.allinfinance.po.agentpay.TblAreaInfo) object;
	}
	public com.allinfinance.po.agentpay.TblAreaInfo load(com.allinfinance.po.agentpay.TblAreaInfoPK key)
	{
		return (com.allinfinance.po.agentpay.TblAreaInfo) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.agentpay.TblAreaInfo get(com.allinfinance.po.agentpay.TblAreaInfoPK key)
	{
		return (com.allinfinance.po.agentpay.TblAreaInfo) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<com.allinfinance.po.agentpay.TblAreaInfo> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param TblAreaInfo a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.allinfinance.po.agentpay.TblAreaInfoPK save(com.allinfinance.po.agentpay.TblAreaInfo TblAreaInfo)
	{
		return (com.allinfinance.po.agentpay.TblAreaInfoPK) super.save(TblAreaInfo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param TblAreaInfo a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.agentpay.TblAreaInfo TblAreaInfo)
	{
		super.saveOrUpdate(TblAreaInfo);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param TblAreaInfo a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.agentpay.TblAreaInfo TblAreaInfo)
	{
		super.update(TblAreaInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param TblAreaInfo the instance to be removed
	 */
	public void delete(com.allinfinance.po.agentpay.TblAreaInfo tblAreaInfo)
	{
		super.delete((Object) tblAreaInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.agentpay.TblAreaInfoPK id)
	{
		super.delete((Object) load(id));
	}
	@Override
	public List<TblAreaInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
