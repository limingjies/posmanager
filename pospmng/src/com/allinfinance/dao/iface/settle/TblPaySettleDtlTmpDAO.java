package com.allinfinance.dao.iface.settle;

import java.util.List;

import com.allinfinance.po.settle.TblPaySettleDtlTmp;
import com.allinfinance.po.settle.TblPaySettleDtlTmpPK;

public interface TblPaySettleDtlTmpDAO {
	
	public TblPaySettleDtlTmp get(TblPaySettleDtlTmpPK tblPaySettleDtlPK);

	public void update(TblPaySettleDtlTmp tblPaySettleDtl);
	
	public void save(TblPaySettleDtlTmp tblPaySettleDtl);
	
	
	public void saveList(List<TblPaySettleDtlTmp> dataList);




}