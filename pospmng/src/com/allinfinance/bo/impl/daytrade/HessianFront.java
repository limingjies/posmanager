package com.allinfinance.bo.impl.daytrade;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.allinfinance.bo.daytrade.HessianBase;
import com.allinfinance.common.Constants;
import com.allinfinance.common.FrontConstants;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.HessianUtil;

/**
 *  HessianFront.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年5月22日	  徐鹏飞          created this class.
 *    
 *  
 *  Copyright Notice:
 *   Copyright (c) 2009-2015   Allinpay Financial Services Co., Ltd. 
 *    All rights reserved.
 *
 *   This software is the confidential and proprietary information of
 *   allinfinance.com  Inc. ('Confidential Information').  You shall not
 *   disclose such Confidential Information and shall use it only in
 *   accordance with the terms of the license agreement you entered
 *   into with Allinpay Financial.
 *
 *  Warning:
 *  =========================================================================
 *  
 */
public class HessianFront implements HessianBase {
	
	private static Logger log = Logger.getLogger(HessianFront.class);
	/**
	 * 请求信息
	 */
	private String requestMsg = null;
	/**
	 * 回复信息
	 */
	private String responseMsg = null;
	
	/**
	 * 所有子类使用方法实现业务逻辑
	 * 
	 * @param txnCode 业务交易码
	 * @param obj = new Object();
	 * @param obj[0] = 发送数据类
	 * @param obj[1] = 接收数据类
	 * 
	 * @return 
	 */
	protected Object[] doTxn(String txnCode, Object[] obj){
		
		Object[] ret = new Object[2];
		
		requestMsg = HessianUtil.jsonCreator(txnCode, obj[0]);
		
		processe();
		
		if(responseMsg.startsWith(txnCode)){
			// 组装返回成功信息
			ret[0] = Constants.SUCCESS_CODE;
			// 回执信息处理为对象
			ret[1] = HessianUtil.objCreator(txnCode, responseMsg, obj[1]);
		}else{
			// 返回异常
			ret[0] = Constants.FAILURE_CODE;
			ret[1] = responseMsg;
		}
		return ret;
	};


	/* (non-Javadoc)
	 * @see com.allinfinance.bo.daytrade.HessianBase#processe(java.lang.String)
	 * 发送json数据给前置
	 */
	@Override
	public void processe(){
    	try{
    		log.info(requestMsg);
    		responseMsg = CommonFunction.getFrontService().process(requestMsg);
    		log.info(responseMsg);
    	} catch (Exception e) {
			log.error(e.getMessage(),e);
			responseMsg = FrontConstants.HESN_CONN_FAIL;
    	}
	}
	
	/**
	 * 获取回执交易数据
	 * @return
	 * @throws IOException 
	 */
	protected String getMessage() {
		return responseMsg;
	}
	   /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
	public boolean isNotEmpty(String str) {
        if(str != null && !"".equals(str.trim()))
            return true;
        else
            return false;
    }

}
