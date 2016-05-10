package com.allinfinance.dao.iface.base;

public interface SettleDocInfoDAO {
	
	public com.allinfinance.po.SettleDocInfo get(java.lang.String key);

	public com.allinfinance.po.SettleDocInfo load(java.lang.String key);

	public java.lang.String save(com.allinfinance.po.SettleDocInfo settleDocInfo);

	public void update(com.allinfinance.po.SettleDocInfo settleDocInfo);

	public void delete(java.lang.String id);
}