package com.allinfinance.bo.mchnt;

import java.util.List;

import com.allinfinance.po.mchnt.TblMchtBaseInf;

public interface T20108BO {
	
	public List<TblMchtBaseInf> getAll() ;
	public String openAcct(TblMchtBaseInf mchtInf);
	
}
