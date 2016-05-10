package com.allinfinance.dao.impl.base;

public class SettleDocInfoDAO extends com.allinfinance.dao._RootDAO<com.allinfinance.po.SettleDocInfo> implements com.allinfinance.dao.iface.base.SettleDocInfoDAO{

	public Class<com.allinfinance.po.SettleDocInfo> getReferenceClass () {
		return com.allinfinance.po.SettleDocInfo.class;
	}

	public com.allinfinance.po.SettleDocInfo cast (Object object) {
		return (com.allinfinance.po.SettleDocInfo) object;
	}

	public com.allinfinance.po.SettleDocInfo load(java.lang.String key)
	{
		return (com.allinfinance.po.SettleDocInfo) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.SettleDocInfo get(java.lang.String key)
	{
		return (com.allinfinance.po.SettleDocInfo) get(getReferenceClass(), key);
	}

	public java.lang.String save(com.allinfinance.po.SettleDocInfo settleDocInfo)
	{
		return (java.lang.String) super.save(settleDocInfo);
	}

	public void update(com.allinfinance.po.SettleDocInfo settleDocInfo)
	{
		super.update(settleDocInfo);
	}

	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}