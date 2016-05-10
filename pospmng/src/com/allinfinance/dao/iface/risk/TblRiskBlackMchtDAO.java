package com.allinfinance.dao.iface.risk;


public interface TblRiskBlackMchtDAO {
	
	public com.allinfinance.po.TblRiskBlackMcht get(java.lang.String key);
	public java.lang.String save(com.allinfinance.po.TblRiskBlackMcht tblRiskBlackMcht);
	public void update(com.allinfinance.po.TblRiskBlackMcht tblRiskBlackMcht);
	public void delete(java.lang.String mchtNo);



}