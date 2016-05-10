package com.allinfinance.dao.impl.mchnt;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblHolderDrawAct;

public class TblHolderDrawActDAO extends _RootDAO<com.allinfinance.po.TblHolderDrawAct>
		implements com.allinfinance.dao.iface.mchnt.TblHolderDrawActDAO{
	
	public TblHolderDrawActDAO(){
		
	}
	public TblHolderDrawAct load(String actNo)
		throws org.hibernate.HibernateException {
			return (com.allinfinance.po.TblHolderDrawAct) load(getReferenceClass(),
					actNo);
	}
	@Override
	public Class<com.allinfinance.po.TblHolderDrawAct> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.TblHolderDrawAct.class;
	}

	public void delete(String actNo) throws HibernateException {
		// TODO Auto-generated method stub
		super.delete(load(actNo));
	}

	public TblHolderDrawAct get(String actNo) throws HibernateException {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.TblHolderDrawAct) get(getReferenceClass(),actNo);
	}

	public void save(TblHolderDrawAct tblHolderDrawAct)
			throws HibernateException {
		// TODO Auto-generated method stub
		super.save(tblHolderDrawAct);
	}

	public void update(TblHolderDrawAct tblHolderDrawAct) throws HibernateException {
		// TODO Auto-generated method stub
		super.update(tblHolderDrawAct);
	}

}
