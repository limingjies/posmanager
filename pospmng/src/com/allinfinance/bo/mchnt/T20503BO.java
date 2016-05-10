package com.allinfinance.bo.mchnt;

import com.allinfinance.po.mchnt.TblFirstMchtInf;

public interface T20503BO {

	public TblFirstMchtInf get(String id);

	public String add(TblFirstMchtInf tblFirstMchtInf);

	public String update(TblFirstMchtInf tblFirstMchtInf);
	
	public String delete(TblFirstMchtInf tblFirstMchtInf);
	
	public String delete(String id);

}
