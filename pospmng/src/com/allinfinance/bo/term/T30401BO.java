package com.allinfinance.bo.term;

import java.util.List;

import com.allinfinance.po.TblTermCupInfoTmp;

public interface T30401BO {

	public TblTermCupInfoTmp get(String id);

	public String add(TblTermCupInfoTmp tblTermCupInfoTmp);

	public String update(List<TblTermCupInfoTmp> tblTermCupInfoTmpList);
	
	public String saveOrUpdate(TblTermCupInfoTmp tblTermCupInfoTmp);
	
	public String cancel(TblTermCupInfoTmp tblTermCupInfoTmp);

	public String delete(TblTermCupInfoTmp tblTermCupInfoTmp);

	public String delete(String id);
}
