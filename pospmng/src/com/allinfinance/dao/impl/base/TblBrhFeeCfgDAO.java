package com.allinfinance.dao.impl.base;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.base.TblBrhFeeCfg;
import com.allinfinance.po.base.TblBrhFeeCfgPK;

public class TblBrhFeeCfgDAO extends _RootDAO<TblBrhFeeCfg> implements
		com.allinfinance.dao.iface.base.TblBrhFeeCfgDAO {

	public TblBrhFeeCfgDAO() {
	}

	public Class<TblBrhFeeCfg> getReferenceClass() {
		return TblBrhFeeCfg.class;
	}

	/**
	 * Cast the object as a com.allinfinance.po.brh.TblBrhInfo
	 */
	public TblBrhFeeCfg cast(Object object) {
		return (TblBrhFeeCfg) object;
	}

	public TblBrhFeeCfg load(TblBrhFeeCfgPK key) {
		return (TblBrhFeeCfg) load(getReferenceClass(), key);
	}

	public TblBrhFeeCfg get(TblBrhFeeCfgPK key) {
		return (TblBrhFeeCfg) get(getReferenceClass(), key);
	}

	public void save(TblBrhFeeCfg tblBrhFeeCfg) {
		 super.save(tblBrhFeeCfg);
	}

	public void update(TblBrhFeeCfg tblBrhFeeCfg) {
		super.update(tblBrhFeeCfg);
	}

	public void delete(TblBrhFeeCfgPK id) {
		super.delete((Object) load(id));
	}

	@Override
	public void deleteList(List<TblBrhFeeCfg> dataList) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		for (TblBrhFeeCfg tblBrhFeeCfg : dataList) {
			session.delete(tblBrhFeeCfg);
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
	}

	@Override
	public void delete(TblBrhFeeCfg tblBrhFeeCfg) {
		// TODO Auto-generated method stub
		super.delete(tblBrhFeeCfg);
	}
}