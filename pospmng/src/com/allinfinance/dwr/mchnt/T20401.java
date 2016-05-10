package com.allinfinance.dwr.mchnt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.allinfinance.dao.iface.base.TblMbfBankInfoDAO;
import com.allinfinance.po.TblMbfBankInfo;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class T20401 {
	
	private static Logger log = Logger.getLogger(T20401.class);
	public String getInfo(String cnapsbankno,HttpServletRequest request,
			HttpServletResponse response) {
		
		String jsonData = null;
		try {
			TblMbfBankInfoDAO MbfBrhInfoDAO = (TblMbfBankInfoDAO) ContextUtil.getBean("MbfBrhInfoDAO");
			TblMbfBankInfo info = MbfBrhInfoDAO.get(cnapsbankno);
			// 把清算信息存在临时域
			if(null==info){
				return "0";
			}else {
				jsonData = JSONArray.fromObject(info).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}

	
}