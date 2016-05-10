package com.allinfinance.dao.impl.base;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.base.ITblImportTermDao;
import com.allinfinance.po.base.TblImportTerm;

public class TblImportTermDao extends _RootDAO<TblImportTerm> implements ITblImportTermDao {

	@Override
	protected Class getReferenceClass() {
		return TblImportTerm.class;
	}

	@Override
	public void save(TblImportTerm tblImportTerm) {
		super.save(tblImportTerm);
	}

}
