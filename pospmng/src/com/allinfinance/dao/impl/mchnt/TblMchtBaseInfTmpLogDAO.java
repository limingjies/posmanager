package com.allinfinance.dao.impl.mchnt;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpLogDAO;
import com.allinfinance.po.TblMchtBaseInfTmpLog;

public class TblMchtBaseInfTmpLogDAO extends _RootDAO<com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp> implements ITblMchtBaseInfTmpLogDAO {

	public void saveOrUpdate(
			com.allinfinance.po.TblMchtBaseInfTmpLog tblMchtBaseInfTmpLog)
			throws HibernateException {
		super.save(tblMchtBaseInfTmpLog);

	}

	
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void delete(String id){
		
		super.delete(id);
	};
	public void delete(TblMchtBaseInfTmpLog tblMchtBaseInfTmpLog){
		super.delete(tblMchtBaseInfTmpLog);
	};
}
