package com.allinfinance.dao.iface.base;

import com.allinfinance.po.base.TblBrhSettleInf;

public interface TblBrhSettleInfDAO {
	
	public TblBrhSettleInf get(java.lang.String brhId);

	public void save(TblBrhSettleInf tblBrhSettleInf);

	public void update(TblBrhSettleInf tblBrhSettleInf);

	public void delete(java.lang.String brhId);

}