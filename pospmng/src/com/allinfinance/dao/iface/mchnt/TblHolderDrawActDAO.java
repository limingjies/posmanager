package com.allinfinance.dao.iface.mchnt;


import com.allinfinance.po.TblHolderDrawAct;

public interface TblHolderDrawActDAO {

	public TblHolderDrawAct get(String actNo) throws org.hibernate.HibernateException;
	
	public  void save(TblHolderDrawAct tblHolderDrawAct ) throws org.hibernate.HibernateException;
	
	public void update(com.allinfinance.po.TblHolderDrawAct tblHolderDrawAct) throws org.hibernate.HibernateException;

	public void delete(String actNo) throws org.hibernate.HibernateException;
	
}
