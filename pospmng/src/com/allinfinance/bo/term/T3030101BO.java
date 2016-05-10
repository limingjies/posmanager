package com.allinfinance.bo.term;

import java.util.List;

import com.allinfinance.po.TblTermManagementCheck;
import com.allinfinance.po.TblTermManagementCheckPK;

public interface T3030101BO {

	public TblTermManagementCheck get(TblTermManagementCheckPK id);

	public String add(TblTermManagementCheck tblTermManagementCheck);

	public String update(List<TblTermManagementCheck> tblTermManagementCheckList);
	
	public String update(TblTermManagementCheck tblTermManagementCheck);
	
	public String cancel(TblTermManagementCheck tblTermManagementCheck);

	public String delete(TblTermManagementCheck tblTermManagementCheck);

	public String delete(TblTermManagementCheckPK id);
}
