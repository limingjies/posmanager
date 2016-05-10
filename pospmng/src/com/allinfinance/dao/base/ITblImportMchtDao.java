package com.allinfinance.dao.base;

import com.allinfinance.po.base.TblImportMcht;

public interface ITblImportMchtDao {
	void save(TblImportMcht tblImportMcht);
	TblImportMcht get(java.lang.String key);
	void saveOrUpdate(TblImportMcht tblImportMcht);
}
