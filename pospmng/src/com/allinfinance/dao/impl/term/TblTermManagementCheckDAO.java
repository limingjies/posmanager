package com.allinfinance.dao.impl.term;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermManagementCheck;
import com.allinfinance.po.TblTermManagementCheckPK;

public class TblTermManagementCheckDAO extends _RootDAO<com.allinfinance.po.TblTermManagementCheck> implements com.allinfinance.dao.iface.term.TblTermManagementCheckDAO {

	@Override
	protected Class<com.allinfinance.po.TblTermManagementCheck> getReferenceClass() {
		return com.allinfinance.po.TblTermManagementCheck.class;
	}

	public void delete(TblTermManagementCheckPK id) {
		super.delete(load(id));
	}

	public TblTermManagementCheck get(TblTermManagementCheckPK key) {
		return (com.allinfinance.po.TblTermManagementCheck) get(getReferenceClass(),
				key);
	}

	public TblTermManagementCheck load(TblTermManagementCheckPK key) {
		return (com.allinfinance.po.TblTermManagementCheck) load(getReferenceClass(),
				key);
	}

	public void save(TblTermManagementCheck tblTermManagementCheck) {
		super.save(tblTermManagementCheck);
	}

	public void update(TblTermManagementCheck tblTermManagementCheck) {
		super.update(tblTermManagementCheck);
	}

	public void delete(TblTermManagementCheck tblTermManagementCheck) {
		
	}

	
}
