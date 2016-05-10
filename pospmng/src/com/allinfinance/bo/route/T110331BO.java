package com.allinfinance.bo.route;

import java.util.Map;

import com.allinfinance.common.Operator;

public interface T110331BO {

	String editMchtUpbrh(Operator operator,String ruleId,String mchtId,String mchtUpbrhId,String propertyId,String termId);

	String addMchtUpbrh(String oprId, String mchtId, String propertyId, String mchtUpbrhId, String termId,int index);

	String bacthAddMchtUpbrh(Operator operator, String mchtId,
			String mchtUpbrhId, String propertyId, String termId, String groupId);
	
	void importMapData(Map<String,String> data,Operator operator);

}
