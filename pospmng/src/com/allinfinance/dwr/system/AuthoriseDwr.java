/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-6       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 allinfinance, Inc. All rights reserved.
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
package com.allinfinance.dwr.system;

import java.util.List;

import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title: 
 * 
 * File: AuthoriseDwr.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-6
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class AuthoriseDwr {
	
	public String checkNeedAuthorise(String txnCode){
		
		if (StringUtil.isNull(txnCode)) {
			return "F";
		}
		
		String sql = "select MISC1 from tbl_func_inf where func_id = '" + txnCode.trim() + "'";
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");

		List list = commQueryDAO.findBySQLQuery(sql);
		
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				String result = list.get(0).toString();
				if ("1".equals(result) || "3".equals(result)) {
					return "S";
				}
			}
		}
		return "F";
	}
}
