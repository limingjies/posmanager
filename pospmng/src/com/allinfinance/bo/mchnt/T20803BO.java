package com.allinfinance.bo.mchnt;


import com.allinfinance.po.TblHolderDrawAct;

public interface T20803BO {

	public TblHolderDrawAct get(String actNo);
	
	public String add(TblHolderDrawAct tblHolderDrawAct);

	public String update(TblHolderDrawAct tblHolderDrawAct);
	
	public String delete(String actNo);
}
