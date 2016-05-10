package com.allinfinance.dao.iface.settleNew;


import java.util.List;

import com.allinfinance.po.settleNew.TblZthDtlNew;
import com.allinfinance.po.settleNew.TblZthDtlNewPK;


public interface TblZthDtlNewDAO {
	
	public TblZthDtlNew get(TblZthDtlNewPK key);

	public TblZthDtlNew load(TblZthDtlNewPK key);
	public void delete(TblZthDtlNewPK key);
	public void update(TblZthDtlNew tblZthDtlNew);
	public void saveList(List<TblZthDtlNew> dataList);
	public void save(TblZthDtlNew tblZthDtlNew);
	
	




}