package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblRiskGreyMcht;
import com.allinfinance.po.risk.TblRiskGreyMchtPK;

public interface TblRiskGreyMchtDAO {
	
	public TblRiskGreyMcht get(TblRiskGreyMchtPK key) throws org.hibernate.HibernateException;

	public void save(TblRiskGreyMcht tblRiskGreyMcht) throws org.hibernate.HibernateException;

	public void update(TblRiskGreyMcht tblRiskGreyMcht) throws org.hibernate.HibernateException;

	public void delete(TblRiskGreyMcht tblRiskGreyMcht) throws org.hibernate.HibernateException;

	public void delete(TblRiskGreyMchtPK key) throws org.hibernate.HibernateException;

}