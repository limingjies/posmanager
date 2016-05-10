package com.allinfinance.dao.impl.risk;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblWhiteMchtApply;



public class TblWhiteMchtApplyDAO extends _RootDAO<TblWhiteMchtApply> implements com.allinfinance.dao.iface.risk.TblWhiteMchtApplyDAO {

	public TblWhiteMchtApplyDAO() {
	}

	
	public Class<TblWhiteMchtApply> getReferenceClass() {
		return TblWhiteMchtApply.class;
	}

	public TblWhiteMchtApply cast(Object object) {
		return (TblWhiteMchtApply) object;
	}

	
	public TblWhiteMchtApply load(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblWhiteMchtApply) load(getReferenceClass(), key);
	}

	
	public TblWhiteMchtApply get(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblWhiteMchtApply) get(getReferenceClass(), key);
	}

	
	public void save(TblWhiteMchtApply tblWhiteMchtApply)
			throws org.hibernate.HibernateException {
		 super.save(tblWhiteMchtApply);
	}

	
	public void saveOrUpdate(TblWhiteMchtApply tblWhiteMchtApply)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblWhiteMchtApply);
	}

	public void update(TblWhiteMchtApply tblWhiteMchtApply)
			throws org.hibernate.HibernateException {
		super.update(tblWhiteMchtApply);
	}

	
	public void delete(TblWhiteMchtApply tblWhiteMchtApply)
			throws org.hibernate.HibernateException {
		super.delete(tblWhiteMchtApply);
	}

	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException {
		super.delete(load(id));
	}
}