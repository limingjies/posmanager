package com.allinfinance.bo.base;


import com.allinfinance.common.Operator;
//import com.allinfinance.po.base.TblBrhFeeCfg;
import com.allinfinance.po.base.TblBrhFeeCfgPK;
import com.allinfinance.po.base.TblBrhFeeCfgZlf;
import com.allinfinance.po.base.TblBrhFeeCtl;

public interface T10103BO {

//	public TblBrhFeeCfg get(TblBrhFeeCfgPK tblBrhFeeCfgPK);
//	public String addCfg(TblBrhFeeCfg tblBrhFeeCfg,Operator operator);
//	public String updateCfg(TblBrhFeeCfg tblBrhFeeCfg,Operator operator);
	public String deleteCfg(TblBrhFeeCfgPK tblBrhFeeCfgPK);
//	public String stop(TblBrhFeeCfg tblBrhFeeCfg,Operator operator);
//	public String start(TblBrhFeeCfg tblBrhFeeCfg,Operator operator);
	public TblBrhFeeCfgZlf get(TblBrhFeeCfgPK tblBrhFeeCfgPK);
	public String addCfg(TblBrhFeeCfgZlf tblBrhFeeCfgZlf,Operator operator);
	public String updateCfg(TblBrhFeeCfgZlf tblBrhFeeCfgZlf,Operator operator);
	public String stop(TblBrhFeeCfgZlf tblBrhFeeCfgZlf,Operator operator);
	public String start(TblBrhFeeCfgZlf tblBrhFeeCfgZlf,Operator operator);
	
	public TblBrhFeeCtl get(String discId);
	public String addCtl(TblBrhFeeCtl tblBrhFeeCtl,Operator operator);
	public String updateCtl(TblBrhFeeCtl tblBrhFeeCtl,Operator operator);
	public String deleteCtl(String discId);
	
	
}
