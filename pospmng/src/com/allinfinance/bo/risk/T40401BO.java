package com.allinfinance.bo.risk;

import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.risk.TblWhiteMchtApply;
import com.allinfinance.po.risk.TblWhiteMchtCheck;

public interface T40401BO {
	
	public TblWhiteMchtApply get(String id);
	
	public TblMchtBaseInf load(String id);

	public String check(TblWhiteMchtCheck tblWhiteMchtCheck,TblWhiteMchtApply tblWhiteMchtApply);
	
}