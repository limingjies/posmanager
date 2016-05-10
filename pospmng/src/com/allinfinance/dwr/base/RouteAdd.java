package com.allinfinance.dwr.base;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.po.TblRouteRule;
/**
 * Title:商户路由维护
 * 
 * Author: 徐鹏飞
 * 
 * Copyright: Copyright (c) 2014-9-16
 * 
 * Company: Shanghai Tonglian Software Systems.
 * 
 * @version 1.0
 */
public class RouteAdd {
	
	/**
	 * 根据路由索引获取待添加商户路由规则列表
	 * @return the tblRouteRule
	 */
	private List<TblRouteRule> routeRuleList = new ArrayList<TblRouteRule>();
	private TblRouteRule tblRouteRule ;
	
	public List<TblRouteRule> getRouteRuleList(String routeIndex, String accpId, String ruleIdIndex) {
//		routeRuleList.clear();
		if(routeIndex.equals("XCKJ02")){
			tblRouteRule = new TblRouteRule(accpId, ruleIdIndex);
			tblRouteRule.setPriority("0");
			tblRouteRule.setRuleCode(routeIndex);
			routeRuleList.add(tblRouteRule);
		} else if(routeIndex.equals("TL001")){
			tblRouteRule = new TblRouteRule(accpId, ruleIdIndex);
			tblRouteRule.setRuleCode(routeIndex);
			routeRuleList.add(tblRouteRule);
		} else if(routeIndex.equals("TL003")){
			tblRouteRule = new TblRouteRule(accpId, ruleIdIndex);
			tblRouteRule.setRuleCode(routeIndex);
			routeRuleList.add(tblRouteRule);
		} else if(routeIndex.contains("TL005")){
			tblRouteRule = new TblRouteRule(accpId, String.valueOf(Integer.parseInt(ruleIdIndex)-1));
			tblRouteRule.setRuleCode("TL005");
			routeRuleList.add(tblRouteRule);
			tblRouteRule = new TblRouteRule(accpId, ruleIdIndex);
			tblRouteRule.setPriority("4");
			tblRouteRule.setInsIdCd("03020000");
			tblRouteRule.setRuleCode("TL012");
			routeRuleList.add(tblRouteRule);
		} else if(routeIndex.contains("TL006")){
			tblRouteRule = new TblRouteRule(accpId, String.valueOf(Integer.parseInt(ruleIdIndex)-1));
			tblRouteRule.setRuleCode("TL006");
			routeRuleList.add(tblRouteRule);
			tblRouteRule = new TblRouteRule(accpId, ruleIdIndex);
			tblRouteRule.setPriority("4");
			tblRouteRule.setInsIdCd("03020000");
			tblRouteRule.setRuleCode("TL013");
			routeRuleList.add(tblRouteRule);
		}
		return routeRuleList;
	}
	
	
}