package com.allinfinance.bo.mchnt;

import com.allinfinance.po.mchnt.TblInsMchtInf;

public interface T20504BO {
	
	public TblInsMchtInf get(String id);
	
	public String add(TblInsMchtInf tblInsMchtInf);
	
	public String update(TblInsMchtInf tblInsMchtInf);
	
	public String delete(TblInsMchtInf tblInsMchtInf);
	
	public String delete(String id);

}
