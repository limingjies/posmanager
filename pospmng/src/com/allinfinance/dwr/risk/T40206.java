/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-26       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.dwr.risk;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;


public class T40206 {
	
	ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	@SuppressWarnings("unchecked")
	public String getRiskLvlName(String riskLvl,HttpServletRequest request,HttpServletResponse response) {
		String sql="select distinct RESVED from TBL_RISK_LVL where RISK_LVL ='"+riskLvl.trim()+"' ";
		List<String> dataList=commQueryDAO.findBySQLQuery(sql);
		if(dataList.size()>0){
			
			return dataList.get(0).toString();
		}else{
			return null;
		}
	}
		
	
}
