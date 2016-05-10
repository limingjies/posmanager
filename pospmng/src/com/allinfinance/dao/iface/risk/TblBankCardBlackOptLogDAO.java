package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblBankCardBlackOptLog;
import com.allinfinance.po.risk.TblBankCardBlackOptLogPK;

public interface TblBankCardBlackOptLogDAO {
	
	public TblBankCardBlackOptLog get(TblBankCardBlackOptLogPK key) throws org.hibernate.HibernateException;

	public void save(TblBankCardBlackOptLog tblBankCardBlackOptLog) throws org.hibernate.HibernateException;

	public void update(TblBankCardBlackOptLog tblBankCardBlackOptLog) throws org.hibernate.HibernateException;

	public void delete(TblBankCardBlackOptLog tblBankCardBlackOptLog) throws org.hibernate.HibernateException;

}