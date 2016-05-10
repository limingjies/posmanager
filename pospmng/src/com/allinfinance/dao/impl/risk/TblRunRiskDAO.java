package com.allinfinance.dao.impl.risk;

import java.util.Iterator;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblRunRisk;
import com.allinfinance.po.TblRunRiskPK;

public class TblRunRiskDAO extends _RootDAO<com.allinfinance.po.TblRiskParamInf> implements com.allinfinance.dao.iface.risk.TblRunRiskDAO{

	@Override
	protected Class<TblRunRisk> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.TblRunRisk.class;
	}
	public com.allinfinance.po.TblRunRisk cast (Object object) {
		return (com.allinfinance.po.TblRunRisk) object;
	}
	@Override
	public void delete(TblRunRiskPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblRunRisk get(TblRunRiskPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.TblRunRisk) get(getReferenceClass(), key);
	}

	@Override
	public TblRunRisk load(TblRunRiskPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.TblRunRisk) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblRunRisk tblRunRisk) {
		super.update(tblRunRisk);
	}
	@Override
	public void save(TblRunRisk tblRunRisk) {
		super.save(tblRunRisk);
	}
	@Override
	public void saveList(List<TblRunRisk> dataList) {
		// TODO Auto-generated method stub
		Iterator<TblRunRisk> it = dataList.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().saveOrUpdate(it.next());
		}
	}

}
