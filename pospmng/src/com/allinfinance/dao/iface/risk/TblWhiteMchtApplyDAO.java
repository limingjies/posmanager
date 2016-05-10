package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblWhiteMchtApply;


public interface TblWhiteMchtApplyDAO {
	public TblWhiteMchtApply get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblWhiteMchtApply load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblWhiteMchtApply tblWhiteMchtApply) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblWhiteMchtApply tblWhiteMchtApply) throws org.hibernate.HibernateException;

	public void update(TblWhiteMchtApply tblWhiteMchtApply) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblWhiteMchtApply tblWhiteMchtApply) throws org.hibernate.HibernateException;


}