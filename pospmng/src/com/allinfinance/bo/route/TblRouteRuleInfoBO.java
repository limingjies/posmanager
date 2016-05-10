package com.allinfinance.bo.route;

import java.util.List;
import com.allinfinance.po.route.TblRouteRuleInfo;

public interface TblRouteRuleInfoBO {

	String add(TblRouteRuleInfo tblRouteRuleInfo,String oprId);

	void update(TblRouteRuleInfo tblRouteRuleInfo);

	void delete(TblRouteRuleInfo tblRouteRuleInfo);
	
	List<TblRouteRuleInfo> getAll();

	int getMax();
}
