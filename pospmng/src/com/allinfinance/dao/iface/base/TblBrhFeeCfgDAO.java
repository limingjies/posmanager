package com.allinfinance.dao.iface.base;

import java.util.List;

import com.allinfinance.po.base.TblBrhFeeCfg;
import com.allinfinance.po.base.TblBrhFeeCfgPK;

public interface TblBrhFeeCfgDAO {
	
	public TblBrhFeeCfg get(TblBrhFeeCfgPK tblBrhFeeCfgPK);

	public void save(TblBrhFeeCfg tblBrhFeeCfg);

	public void update(TblBrhFeeCfg tblBrhFeeCfg);

	public void delete(TblBrhFeeCfgPK tblBrhFeeCfgPK);
	public void delete(TblBrhFeeCfg tblBrhFeeCfg);
	

	public void deleteList(List<TblBrhFeeCfg> dataList);
}