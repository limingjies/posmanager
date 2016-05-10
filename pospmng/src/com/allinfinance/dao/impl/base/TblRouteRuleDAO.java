package com.allinfinance.dao.impl.base;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblRouteRule;
import com.allinfinance.po.TblRouteRulePK;

public class TblRouteRuleDAO extends _RootDAO<com.allinfinance.po.TblRouteRule> implements com.allinfinance.dao.iface.base.TblRouteRuleDAO{

	@Override
	protected Class<com.allinfinance.po.TblRouteRule> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.TblRouteRule.class;
	}

	public com.allinfinance.po.TblRouteRule load(com.allinfinance.po.TblRouteRulePK key)
	{
		return (com.allinfinance.po.TblRouteRule) load(getReferenceClass(), key);
	}
	@Override
	public void delete(TblRouteRulePK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public void delete(TblRouteRule tblRouteRule) {
		// TODO Auto-generated method stub
		super.delete((Object) tblRouteRule);
	}

	@Override
	public com.allinfinance.po.TblRouteRule get(com.allinfinance.po.TblRouteRulePK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.TblRouteRule) get(getReferenceClass(), key);
	}

	@Override
	public void save(TblRouteRule tblRouteRule) {
		// TODO Auto-generated method stub
		super.save(tblRouteRule);
	}

	@Override
	public void update(TblRouteRule tblRouteRule) {
		// TODO Auto-generated method stub
		super.update(tblRouteRule);
	}

}
