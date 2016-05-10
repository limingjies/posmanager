package com.allinfinance.bo.term;

import com.allinfinance.po.TblTermManagementCheckPK;

public interface T30302BO {

	public com.allinfinance.po.TblTermManagementCheck get(TblTermManagementCheckPK key);

	public String save(com.allinfinance.po.TblTermManagementCheck tblTermManagementCheck);

	
	public String update(com.allinfinance.po.TblTermManagementCheck tblTermManagementCheck);

	
	public String delete(TblTermManagementCheckPK id);
	
	
	
	
	
	public com.allinfinance.po.TblTermManagementAppMain get(String key);

	public String save(com.allinfinance.po.TblTermManagementAppMain tblTermManagementAppMain);

	
	public String update(com.allinfinance.po.TblTermManagementAppMain tblTermManagementAppMain);

	
	public String delete(String id);
	
	
}
