package com.allinfinance.dwr.term;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.allinfinance.bo.impl.mchnt.TblMchntService;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
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
public class T30101 {
	private static Logger log = Logger.getLogger(T30101.class);
	public String getMchnt(String mchntId,HttpServletRequest request,
			HttpServletResponse response) {
		String jsonData = null;
		try {
			TblMchntService service = (TblMchntService) ContextUtil.getBean("tblMchntService");
			TblMchtBaseInf info = service.getMccByMchntId(mchntId);
			TblMchtSettleInf settleInfo = service.getSettleInf(info.getMchtNo());
//            把清算信息存在临时域
			if(null==settleInfo){
				return "0";
			}
			info.setUpdOprId(settleInfo.getSettleAcct().substring(1));
			if(info != null)
				jsonData = JSONArray.fromObject(info).toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	public String getTermParam(String termParam,HttpServletRequest request,
			HttpServletResponse response)
	{
		StringBuffer result = new StringBuffer();
		for (char c : termParam.toCharArray())
		{
			result.append(CommonFunction.fillString(Integer.toBinaryString(Integer.parseInt(String.valueOf(c),16)), '0', 4, false));
		}
		return result.toString();
	}
	
}