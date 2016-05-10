package com.allinfinance.dwr.settle;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * 
 * Title:准退货批量
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-01
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * author: 曹铁铮
 *  
 * time: 2015年5月17日下午6:20:18
 * 
 * version: 1.0
 */
public class T80604 {
	
	private ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	@SuppressWarnings("unchecked")
	public String getSettleDate(HttpServletRequest request,HttpServletResponse response) {

		String sql = "select settle_date from tbl_zth_date ";
		List<String> dataList = commQueryDAO.findBySQLQuery(sql);
		if(dataList.size()>0){
			return CommonFunction.dateFormat(dataList.get(0).toString());
		}else{
			return "";
		}
	}
	
}