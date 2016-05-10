package com.allinfinance.dwr.settle;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.JSONBean;

/**
 * 
 * Title:中信小额支付
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-01
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * author: 徐鹏飞
 *  
 * time: 2015年4月13日下午4:20:18
 * 
 * version: 1.0
 */
public class T80904 {
	
	public String getSettleInf(String acctNo,HttpServletRequest request,HttpServletResponse response) {
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");

		JSONBean jsonBean = new JSONBean();
		String sql = "SELECT DISTINCT SETTLE_ACCT_NM,OPEN_STLNO,SETTLE_BANK_NM FROM TBL_MCHT_SETTLE_INF WHERE SUBSTR(SETTLE_ACCT,2) = '" + acctNo + "'";
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		if(dataList.size() > 0){
			Object[] obj = dataList.get(0);
			jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
			jsonBean.addJSONElement("settleAcctNm", obj[0].toString().trim());
			if(obj[1] == null){
				jsonBean.addJSONElement("openStlno", "");
			}else{
				jsonBean.addJSONElement("openStlno", obj[1].toString().trim());
			}
			jsonBean.addJSONElement("settleBankNm", obj[2].toString().trim());
		}else{
			jsonBean.addJSONElement(Constants.SUCCESS_HEADER, false);
		}
		return jsonBean.toString();
	}
	
}