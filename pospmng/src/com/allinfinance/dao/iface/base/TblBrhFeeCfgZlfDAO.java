package com.allinfinance.dao.iface.base;

import java.util.List;

import com.allinfinance.po.base.TblBrhFeeCfgZlf;
import com.allinfinance.po.base.TblBrhFeeCfgPK;

public interface TblBrhFeeCfgZlfDAO {
	
	public TblBrhFeeCfgZlf get(TblBrhFeeCfgPK tblBrhFeeCfgPK);

	public void save(TblBrhFeeCfgZlf tblBrhFeeCfg);

	public void update(TblBrhFeeCfgZlf tblBrhFeeCfg);

	public void delete(TblBrhFeeCfgPK tblBrhFeeCfgPK);
	public void delete(TblBrhFeeCfgZlf tblBrhFeeCfg);
	

	public void deleteList(List<TblBrhFeeCfgZlf> dataList);
}