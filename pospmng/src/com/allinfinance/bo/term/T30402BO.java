package com.allinfinance.bo.term;

import java.util.List;

import com.allinfinance.po.TblTermCupInfo;

public interface T30402BO {

	public TblTermCupInfo get(String id);

	public String add(TblTermCupInfo tblTermCupInfo);

	public String update(List<TblTermCupInfo> tblTermCupInfoList);
	
	public String saveOrUpdate(TblTermCupInfo tblTermCupInfo);
	
	public String cancel(TblTermCupInfo tblTermCupInfo);

	public String delete(TblTermCupInfo tblTermCupInfo);

	public String delete(String id);
}
