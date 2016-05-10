package com.allinfinance.dao.iface.base;

import com.allinfinance.po.base.TblBrhSettleInfHis;
import com.allinfinance.po.base.TblBrhSettleInfHisPK;

public interface TblBrhSettleInfHisDAO {
	
	public TblBrhSettleInfHis get(TblBrhSettleInfHisPK id);

	public void save(TblBrhSettleInfHis tblBrhSettleInfHis);

	public void update(TblBrhSettleInfHis tblBrhSettleInfHis);

	public void delete(java.lang.String brhId);
	
	public void delete(TblBrhSettleInfHisPK id);
	
	public java.lang.String getNextSeqId(String brhId);

}