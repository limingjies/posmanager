package com.allinfinance.dwr.term;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.allinfinance.bo.mchnt.T20401BO;
import com.allinfinance.po.TblMchtCupInfoTmp;
import com.allinfinance.system.util.CommonFunction;
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
public class T30401 {
	
	private static Logger log = Logger.getLogger(T30401.class);
	public String getMchnt(String mchntId,HttpServletRequest request,
			HttpServletResponse response) {
		
		String jsonData = null;
		try {
			T20401BO t20401BO = (T20401BO) ContextUtil.getBean("T20401BO");
			TblMchtCupInfoTmp tmp = t20401BO.get(mchntId);
			// 把清算信息存在临时域
			if(null==tmp){
				return "0";
			}else {
				jsonData = JSONArray.fromObject(tmp).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}

	
}