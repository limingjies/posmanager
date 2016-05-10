package com.allinfinance.bo.mchnt;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblMchtLimitDate;

public interface  T20202BO {
	
	public TblMchtLimitDate get(String mchntNo);
	
	public String complete(TblMchtLimitDate mchtLimitDate);
	
	public String limit(TblMchtLimitDate mchtLimitDate);
	
	public String freeze(String mchntNo, Operator operator);
	
}
