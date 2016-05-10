package com.allinfinance.dao.impl;

import java.util.List;
import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblModelInfo;

public class TblModelInfoDao extends _RootDAO<TblModelInfo> {
    
	protected Class<TblModelInfo> getReferenceClass() {
		return TblModelInfo.class;
	}

	public TblModelInfo get(Integer id) {
        return (TblModelInfo) get(getReferenceClass(),id);
    }

	public TblModelInfo load(String id) {
//		String hql = "From TblModelInfo where modelName = ? ";
//		hibernateTemplate.getSessionFactory().getCurrentSession();
//		TblModelInfo a = (TblModelInfo) getSession().createQuery(hql).list();
		return (TblModelInfo) load(getReferenceClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<TblModelInfo> loadAll() {
        return loadAll(getReferenceClass());
    }

	@SuppressWarnings("unchecked")
	public List<TblModelInfo> LoadAllBykey(String key) {
        return getHibernateTemplate().findByNamedQuery(key);
    }
	
	public void add(TblModelInfo modelInfo) {
		this.save(modelInfo);
	}
	
	public void delete(TblModelInfo modelInfo) {
		getHibernateTemplate().delete(modelInfo);
	}

	// 修改的方法
	public void update(TblModelInfo modelInfo) {
		getHibernateTemplate().update(modelInfo);
	}


}
