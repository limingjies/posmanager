package com.allinfinance.struts.base.action;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.socket.AmtbackApply;
import com.allinfinance.struts.system.action.BaseAction;

@SuppressWarnings("serial")
public class T10213Action extends BaseAction{

	
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("start".equals(getMethod())) {			
			rspCode = start();			
		}
		return rspCode;
	}

	

	private String start() {
		// TODO Auto-generated method stub
		String resStr=AmtbackApply.getResString("6121");
		
		
		log(resStr);
		
		if(StringUtil.isEmpty(resStr)){
			return "刷新共享内存失败！";
		}
		
		if(StringUtil.isNotEmpty(resStr)&&("00".equals(resStr.substring(8,10)))) {
			
			return Constants.SUCCESS_CODE;
		}else{
			return "刷新共享内存失败！";
		}
	}

	

	
}
