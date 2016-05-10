package com.allinfinance.bo.base;


import java.util.List;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblRouteRule;
import com.allinfinance.po.TblRouteRulePK;

public interface T10600BO {

	public TblRouteRule get(TblRouteRulePK tblRouteRulePK);

	public String add(TblRouteRule tblRouteRule);

	public String update(TblRouteRule tblRouteRule);
	
	public String delete(TblRouteRule tblRouteRule);
	public String delete(TblRouteRulePK tblRouteRulePK);
	

	// 商户路由批量配置 modify by 徐鹏飞 in 2015.01.14
	public String addList(List<TblRouteRule> tblRouteRuleList,String routeRuleInfo,Operator operator) throws Exception;
	
}
