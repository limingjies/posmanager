package com.allinfinance.dwr.daytrade;

import com.allinfinance.bo.impl.daytrade.HessianFront;
import com.allinfinance.common.Constants;
import com.allinfinance.common.FrontConstants;
import com.allinfinance.frontend.dto.withdraw.WithDrawJsonDto;
import com.allinfinance.frontend.dto.withdraw.WithDrawRtnJsonDto;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.po.daytrade.WebFrontTxnLogPK;

/**
 *  Front044001.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年6月10日	  徐鹏飞          created this class.
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
public class Front044001 extends HessianFront {

	/**
	 * 业务交易码——发起提现
	 */
	public static final String FRONT_CODE_044001 = "044001";
	
	public Object[] doTxn(WithDrawJsonDto withDrawJson){
    	
    	// 定义返回信息
    	Object[] ret;

    	// 发送信息
    	Object[] send = new Object[2];
    	send[0] = withDrawJson;
    	send[1] = new WithDrawRtnJsonDto();
    	
    	// 获取信息
    	Object[] get = super.doTxn(FRONT_CODE_044001, send);
    	
    	// 分析信息
    	if(Constants.SUCCESS_CODE.equals(get[0])){
    		ret = new Object[2];
    		// 前置系统返回信息
    		WithDrawRtnJsonDto withDrawRtn = (WithDrawRtnJsonDto) get[1];
    		if(FrontConstants.SYS_CODE_SUCCESS.equals(withDrawRtn.getRtnCode())){
    			// 组装流水信息
    			WebFrontTxnLogPK id = new WebFrontTxnLogPK();
    			id.setWebTime(withDrawJson.getWebTime());
    			id.setWebSeqNum(withDrawJson.getWebSeqNum());
    			WebFrontTxnLog webFrontTxnLog = new WebFrontTxnLog();
    			webFrontTxnLog.setId(id);
    			webFrontTxnLog.setTxnCode(FRONT_CODE_044001);
    			webFrontTxnLog.setTxnLog(withDrawRtn);
    			// 组装返回前置系统成功信息
    			ret[0] = Constants.SUCCESS_CODE;
    			ret[1] = webFrontTxnLog;
    		}else{
	    		// 组装返回前置系统失败信息
	    		ret[0] = Constants.FAILURE_CODE;
	    		ret[1] = withDrawRtn.getRtnCode();
    		}
		}else{
			// 返回异常
			ret = get;
		}
		return ret;
	}
	
}
