package com.allinfinance.dao.iface.base;

import com.allinfinance.po.base.TblBrhFeeCtl;

public interface TblBrhFeeCtlDAO {
	
	public TblBrhFeeCtl get(String keyId);

	public void save(TblBrhFeeCtl tblBrhFeeCtl);

	public void update(TblBrhFeeCtl tblBrhFeeCtl);

	public void delete(String discId);

}