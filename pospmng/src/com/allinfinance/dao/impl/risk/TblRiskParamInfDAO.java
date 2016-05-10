package com.allinfinance.dao.impl.risk;

import java.util.Iterator;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblRiskParamInf;
import com.allinfinance.po.TblRiskParamInfPK;

public class TblRiskParamInfDAO extends _RootDAO<com.allinfinance.po.TblRiskParamInf> implements com.allinfinance.dao.iface.risk.TblRiskParamInfDAO{

	@Override
	protected Class<TblRiskParamInf> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.TblRiskParamInf.class;
	}
	public com.allinfinance.po.TblRiskParamInf cast (Object object) {
		return (com.allinfinance.po.TblRiskParamInf) object;
	}
	@Override
	public void delete(TblRiskParamInfPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblRiskParamInf get(TblRiskParamInfPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.TblRiskParamInf) get(getReferenceClass(), key);
	}

	@Override
	public TblRiskParamInf load(TblRiskParamInfPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.TblRiskParamInf) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblRiskParamInf tblRiskParamInf) {
		super.update(tblRiskParamInf);
	}
	@Override
	public void save(List<TblRiskParamInf> dataList) {
		// TODO Auto-generated method stub
		Iterator<TblRiskParamInf> it = dataList.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().saveOrUpdate(it.next());
		}
	}
}
