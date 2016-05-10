/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-6-23       first release
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
package com.allinfinance.dwr.mchnt;

import java.util.List;

import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title: 
 * 
 * File: RemoteTransMethod.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-23
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class RemoteTransMethod {
	
	public static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	/**
	 * mcc码翻译
	 * 
	 * @param val
	 * @return
	 */
	public String mcc(String val){
		
		String sql = "select descr from tbl_inf_mchnt_tp where trim(mchnt_tp) = '" +  val.trim() + "'";
		
		return findData(sql,val);
	}
	
	
	/**
	 * 机构名称翻译
	 * 
	 * @param val
	 * @return
	 */
	public String brh(String val){
		
		String sql = "select brh_name from tbl_brh_info where trim(brh_id) = '" +  val.trim() + "'";
		
		return findData(sql,val);
	}
	
	/**
	 * 翻译商户名称
	 * @param val
	 * @return
	 * 2011-8-4下午06:00:20
	 */
	public String mchntName(String val){
		String sql = "select MCHT_NO||' - '||MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO = '" +  val.trim() + "'";
		return findData(sql,val);
	}
	
	/**
	 * 简单sql语句执行
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findData(String sql,String val){
		
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
			return list.get(0).toString();
		} else {
			return val;
		}
	}

}

