package com.allinfinance.bo.mchnt;

import java.util.List;

import com.allinfinance.po.TblMchtCupInfoTmp;

public interface T20401BO {

	public TblMchtCupInfoTmp get(String id);

	public String add(TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public String update(List<TblMchtCupInfoTmp> tblMchtCupInfoTmpList);
	
	public String saveOrUpdate(TblMchtCupInfoTmp tblMchtCupInfoTmp);
	
	public String cancel(TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public String delete(TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public String delete(String id);
}
