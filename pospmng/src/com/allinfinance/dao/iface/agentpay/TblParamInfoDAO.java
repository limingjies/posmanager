package com.allinfinance.dao.iface.agentpay;


public interface TblParamInfoDAO {
	public com.allinfinance.po.agentpay.TblParamInfo get(com.allinfinance.po.agentpay.TblParamInfoPK key);

	public com.allinfinance.po.agentpay.TblParamInfo load(com.allinfinance.po.agentpay.TblParamInfoPK key);

	public java.util.List<com.allinfinance.po.agentpay.TblParamInfo> findAll ();

	public com.allinfinance.po.agentpay.TblParamInfoPK save(com.allinfinance.po.agentpay.TblParamInfo tblParamInfo);

	public void saveOrUpdate(com.allinfinance.po.agentpay.TblParamInfo tblParamInfo);

	public void update(com.allinfinance.po.agentpay.TblParamInfo tblParamInfo);

	public void delete(com.allinfinance.po.agentpay.TblParamInfoPK id);

	public void delete(com.allinfinance.po.agentpay.TblParamInfo tblParamInfo);

}