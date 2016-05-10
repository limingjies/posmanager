package com.allinfinance.dao.iface.settle;


import java.util.List;

import com.allinfinance.po.settle.TblZthDtl;
import com.allinfinance.po.settle.TblZthDtlPK;

public interface TblZthDtlDAO {
	
	public com.allinfinance.po.settle.TblZthDtl get(TblZthDtlPK key);

	public com.allinfinance.po.settle.TblZthDtl load(TblZthDtlPK key);
	public void delete(TblZthDtlPK key);
	public void update(com.allinfinance.po.settle.TblZthDtl tblZthDtl);
	public void saveList(List<TblZthDtl> dataList);
	public void save(com.allinfinance.po.settle.TblZthDtl tblZthDtl);
	
	




}