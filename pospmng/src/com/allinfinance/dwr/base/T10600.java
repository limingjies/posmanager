package com.allinfinance.dwr.base;

import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;

public class T10600 {

	public String getMchtGp(String mcc){
		String sql="select MCHNT_TP_GRP from TBL_INF_MCHNT_TP where MCHNT_TP='"+mcc+"'";
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		if(commQueryDAO.findBySQLQuery(sql)==null){
			return null;
		}
		String mchtGp=(String) commQueryDAO.findBySQLQuery(sql).get(0);
		return mchtGp;
	}
}
