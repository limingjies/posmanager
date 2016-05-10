package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.TblMchtBaseInfTmpLog;


public interface ITblMchtBaseInfTmpLogDAO {
	public void saveOrUpdate(TblMchtBaseInfTmpLog tblMchtBaseInfTmpLog)
			throws org.hibernate.HibernateException ;
	
	public void delete(String id);
	public void delete(TblMchtBaseInfTmpLog tblMchtBaseInfTmpLog);

}
