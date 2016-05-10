package com.allinfinance.dao.impl;

import java.util.List;
import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblModelParameter;

public class TblModelParameterDao extends _RootDAO<TblModelParameter> {

	protected Class<TblModelParameter> getReferenceClass() {
		return TblModelParameter.class;
	}

	public TblModelParameter load(String key) {
		return (TblModelParameter) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public List<TblModelParameter> loadAll() {
		return loadAll(getReferenceClass());
	}

	@SuppressWarnings("unchecked")
	public List<TblModelParameter> LoadAllBykey(String key) {
		return getHibernateTemplate().findByNamedQuery(key);
	}

	public void add(TblModelParameter modelParameter) {
		this.save(modelParameter);
	}

	public void delete(String sql) {
		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	// 修改的方法
	public void update(TblModelParameter modelParameter) {
		getHibernateTemplate().update(modelParameter);
	}

}
