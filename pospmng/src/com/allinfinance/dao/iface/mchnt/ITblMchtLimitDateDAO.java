package com.allinfinance.dao.iface.mchnt;

public interface ITblMchtLimitDateDAO {
	
	public void saveOrUpdate(com.allinfinance.po.TblMchtLimitDate tblMchtLimitDate);
	
	public void save(com.allinfinance.po.TblMchtLimitDate tblMchtLimitDate);

	public com.allinfinance.po.TblMchtLimitDate get(String id);

}
