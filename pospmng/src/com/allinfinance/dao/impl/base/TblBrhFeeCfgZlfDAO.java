package com.allinfinance.dao.impl.base;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.base.TblBrhFeeCfgPK;
import com.allinfinance.po.base.TblBrhFeeCfgZlf;

public class TblBrhFeeCfgZlfDAO extends _RootDAO<TblBrhFeeCfgZlf> implements
		com.allinfinance.dao.iface.base.TblBrhFeeCfgZlfDAO {

	public TblBrhFeeCfgZlfDAO() {
	}

	public Class<TblBrhFeeCfgZlf> getReferenceClass() {
		return TblBrhFeeCfgZlf.class;
	}

	/**
	 * Cast the object as a com.allinfinance.po.brh.TblBrhInfo
	 */
	public TblBrhFeeCfgZlf cast(Object object) {
		return (TblBrhFeeCfgZlf) object;
	}

	public TblBrhFeeCfgZlf load(TblBrhFeeCfgPK key) {
		return (TblBrhFeeCfgZlf) load(getReferenceClass(), key);
	}

	public TblBrhFeeCfgZlf get(TblBrhFeeCfgPK key) {
		return (TblBrhFeeCfgZlf) get(getReferenceClass(), key);
	}

	public void save(TblBrhFeeCfgZlf TblBrhFeeCfgZlf) {
		 super.save(TblBrhFeeCfgZlf);
	}

	public void update(TblBrhFeeCfgZlf TblBrhFeeCfgZlf) {
		super.update(TblBrhFeeCfgZlf);
	}

	public void delete(TblBrhFeeCfgPK id) {
		super.delete((Object) load(id));
	}

	@Override
	public void deleteList(List<TblBrhFeeCfgZlf> dataList) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		for (TblBrhFeeCfgZlf TblBrhFeeCfgZlf : dataList) {
			session.delete(TblBrhFeeCfgZlf);
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
	}

	@Override
	public void delete(TblBrhFeeCfgZlf TblBrhFeeCfgZlf) {
		// TODO Auto-generated method stub
		super.delete(TblBrhFeeCfgZlf);
	}
}