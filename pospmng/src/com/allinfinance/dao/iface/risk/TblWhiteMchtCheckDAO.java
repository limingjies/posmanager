package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblWhiteMchtCheck;
import com.allinfinance.po.risk.TblWhiteMchtCheckPK;

public interface TblWhiteMchtCheckDAO {
	
	public TblWhiteMchtCheck get(TblWhiteMchtCheckPK key) throws org.hibernate.HibernateException;

	public void save(TblWhiteMchtCheck tblWhiteMchtCheck) throws org.hibernate.HibernateException;

	public void update(TblWhiteMchtCheck tblWhiteMchtCheck) throws org.hibernate.HibernateException;

	public void delete(TblWhiteMchtCheck tblWhiteMchtCheck) throws org.hibernate.HibernateException;

}