package com.allinfinance.dao.iface.risk;

public interface TblCtlMchtSettleInfDAO {	
	
	public com.allinfinance.po.TblCtlMchtSettleInf get(com.allinfinance.po.TblCtlMchtSettleInfPK key);

	public com.allinfinance.po.TblCtlMchtSettleInf load(com.allinfinance.po.TblCtlMchtSettleInfPK key);

	public java.util.List<com.allinfinance.po.TblCtlMchtSettleInf> findAll ();



	public com.allinfinance.po.TblCtlMchtSettleInfPK save(com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf);


	public void saveOrUpdate(com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf);


	public void update(com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf);
	
	public void delete(com.allinfinance.po.TblCtlMchtSettleInfPK id);


	public void delete(com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf);
}