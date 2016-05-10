package com.allinfinance.dao.impl.base;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.base.ITblImportMchtDao;
import com.allinfinance.po.base.TblImportMcht;

public class TblImportMchtDao extends _RootDAO<TblImportMcht> implements ITblImportMchtDao {

	@Override
	protected Class getReferenceClass() {
		return com.allinfinance.po.base.TblImportMcht.class;
	}

	@Override
	public void save(TblImportMcht tblImportMcht) {
		super.save(tblImportMcht);
	}

	@Override
	public void saveOrUpdate(TblImportMcht tblImportMcht) {
		super.saveOrUpdate(tblImportMcht);
	}
	
	@Override
	public TblImportMcht get(String key) {
		return (TblImportMcht)get(getReferenceClass(), key);
	}

}
