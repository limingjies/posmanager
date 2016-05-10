/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-9-29       first release
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
package com.allinfinance.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.allinfinance.chart.vo.ChartVO_20104;
import com.allinfinance.common.Operator;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.JSONBean;

/**
 * Title: 图表生成类
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-9-29
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class ChartGenerator {
	
	private static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	/**
	 * 商户状态统计图表
	 * @param param
	 * @return
	 * 2010-9-29下午05:21:48
	 */
	@SuppressWarnings("unchecked")
	public static String generateMchntStatus(Object[] param) {
		String brhId = ((Operator)param[0]).getBrhBelowId();
		String[] status = ((String)param[1]).split(",");
		List<Object> charDataList = new ArrayList<Object>();
		ChartVO_20104 chartVO_20104 = null;
		String sql = null;
//		List<Object[]> dataList = null;
		// 商户状态
		LinkedHashMap<String, String> statusMap = new LinkedHashMap<String, String>();
		statusMap.put(TblMchntInfoConstants.MCHNT_ST_OK,"正常");
		statusMap.put(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK,"添加待审核-终审");
		statusMap.put(TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK,"添加待审核-初审");
//		statusMap.put(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK_BACK,"添加审核退回");
		statusMap.put(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK,"修改待审核");
//		statusMap.put(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK_BACK,"修改审核退回");
		statusMap.put(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK,"停用待审核");
		statusMap.put(TblMchntInfoConstants.MCHNT_ST_STOP,"停用");
		statusMap.put(TblMchntInfoConstants.MCHNT_ST_RCV_UNCK,"恢复待审核");
		Iterator<String> iter = statusMap.keySet().iterator();
		String key;
		while(iter.hasNext()) {
			key = iter.next();
			for(String temp : status) {
				if(key.equals(temp)) {
					sql = "select count(*) from tbl_mcht_base_inf_tmp where BANK_NO in " + brhId + " and MCHT_STATUS = '" + key + "'";
					BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
					chartVO_20104 = new ChartVO_20104();
					chartVO_20104.setNumber(count.intValue());
					chartVO_20104.setStatus(statusMap.get(key));
					charDataList.add(chartVO_20104);
				}
			}
		}
		return JSONBean.genListToJSON(charDataList);
	}
}
