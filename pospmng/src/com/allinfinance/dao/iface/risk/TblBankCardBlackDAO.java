package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblBankCardBlack;


public interface TblBankCardBlackDAO {
	public TblBankCardBlack get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblBankCardBlack load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblBankCardBlack tblBankCardBlack) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblBankCardBlack tblBankCardBlack) throws org.hibernate.HibernateException;

	public void update(TblBankCardBlack tblBankCardBlack) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblBankCardBlack tblBankCardBlack) throws org.hibernate.HibernateException;


}