package com.allinfinance.dao.impl.risk;

import java.util.Iterator;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblRiskParamDef;
import com.allinfinance.po.risk.TblRiskParamDefPK;

public class TblRiskParamDefDAO extends _RootDAO<TblRiskParamDef> implements com.allinfinance.dao.iface.risk.TblRiskParamDefDAO{

	@Override
	protected Class<TblRiskParamDef> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblRiskParamDef.class;
	}
	public TblRiskParamDef cast (Object object) {
		return (TblRiskParamDef) object;
	}
	@Override
	public void delete(TblRiskParamDefPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblRiskParamDef get(TblRiskParamDefPK key) {
		// TODO Auto-generated method stub
		return (TblRiskParamDef) get(getReferenceClass(), key);
	}

	@Override
	public TblRiskParamDef load(TblRiskParamDefPK key) {
		// TODO Auto-generated method stub
		return (TblRiskParamDef) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblRiskParamDef tblRiskParamDef) {
		super.update(tblRiskParamDef);
	}
	@Override
	public void save(List<TblRiskParamDef> dataList) {
		// TODO Auto-generated method stub
		Iterator<TblRiskParamDef> it = dataList.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().saveOrUpdate(it.next());
		}
	}

}
