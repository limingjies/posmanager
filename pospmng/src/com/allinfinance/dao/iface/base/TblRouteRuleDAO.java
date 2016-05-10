package com.allinfinance.dao.iface.base;

public interface TblRouteRuleDAO {

	public com.allinfinance.po.TblRouteRule get(com.allinfinance.po.TblRouteRulePK key);


	
 
	public void save(com.allinfinance.po.TblRouteRule tblRouteRule);

	
	public void update(com.allinfinance.po.TblRouteRule tblRouteRule);
	
	
	public void delete(com.allinfinance.po.TblRouteRulePK id);

	
	public void delete(com.allinfinance.po.TblRouteRule tblRouteRule);

}
