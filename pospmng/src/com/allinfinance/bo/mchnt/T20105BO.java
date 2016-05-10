package com.allinfinance.bo.mchnt;

import com.allinfinance.po.mchnt.TblMchtCupInf;

public interface T20105BO {

	
	public TblMchtCupInf get(String mchntNo) ;
	
	
	public void add(TblMchtCupInf tblMchtCupInf);
	
	public void update(TblMchtCupInf tblMchtCupInf);
	
	public void delete(String mchntNo);
	
}
