/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-2       first release
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
package com.allinfinance.dwr.mchnt;

import java.math.BigDecimal;

import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;

/**
 * Title:商户维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-2
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T20101 {
	
	/**
	 * 获得商户编号
	 * @param str
	 * @return
	 */
	public String getMchntId(String str) {
		return GenerateNextId.getMchntId(str);
	}
	
	
	/**
	 * 查看是否存在该商户号的商户
	 * 
	 * @param id
	 * @return
	 */
	public String checkHasMchnt(String id){
		
		if (StringUtil.isNull(id)) {
			return "F";
		}
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");

		String sql = "select count(*) from TBL_MCHT_BASE_INF_TMP where MCHT_NO = '" + id.trim() + "'";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		if (0 == count.intValue()) {
			return "F";
		} else {
			return "T";
		}
	}
}
