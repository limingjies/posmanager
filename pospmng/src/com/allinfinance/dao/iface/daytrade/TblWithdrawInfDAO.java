

package com.allinfinance.dao.iface.daytrade;

import com.allinfinance.po.daytrade.TblWithdrawInf;

public interface TblWithdrawInfDAO {

	public TblWithdrawInf get(String key) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblWithdrawInf tblWithdrawInf)
			throws org.hibernate.HibernateException;
	
	
	public void delete(TblWithdrawInf tblWithdrawInf);
	
}
