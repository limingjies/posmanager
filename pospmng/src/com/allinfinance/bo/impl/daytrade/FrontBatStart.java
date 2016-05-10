package com.allinfinance.bo.impl.daytrade;

import com.allinfinance.common.Constants;
import com.allinfinance.common.FrontConstants;
import com.allinfinance.frontend.dto.acctmanager.AcctDtlRtnJsonDto;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.po.daytrade.WebFrontTxnLogPK;

/**
 *  AdjustAcct.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
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
public class FrontBatStart extends HessianBatch {

	/**
	 * 交易码——账户对账
	 */
	public static final String TXN_CHECK_ACC = "5001";
	
	/**
	 * 交易码——商户入账
	 */
	public static final String TXN_MCHT_INFILE = "5002";

	/**
	 * 交易码——通道入账
	 */
	public static final String TXN_INST_INFILE = "5003";

	/**
	 * 操作码
	 */
	public static final String COM_CODE = "05";
	
	/**
	 * 前置批量任务启动
	 * 
	 * @param txnCode 交易码
	 * @param acctDtl 账户信息对象 
	 * @return
	 */
    public Object[] doTxn(String txnCode, AcctDtlRtnJsonDto acctDtlRtnJsonDto) {	
    	
    	// 定义返回信息
    	Object[] ret;
    	
    	// 组装发送信息
    	Object[] send = new Object[2];
    	send[0] = acctDtlRtnJsonDto;
    	send[1] = new AcctDtlRtnJsonDto();
    	
    	// 获取信息
    	Object[] get = super.doTxn(txnCode, send);
    	
    	// 分析信息
    	if(Constants.SUCCESS_CODE.equals(get[0])){
    		ret = new Object[2];
    		// 前置系统返回信息
    		AcctDtlRtnJsonDto acctDtlRtn = (AcctDtlRtnJsonDto) get[1];
    		if(FrontConstants.SYS_CODE_SUCCESS.equals(acctDtlRtn.getRtnCode())){
    			// 组装流水信息
    			WebFrontTxnLogPK id = new WebFrontTxnLogPK();
    			id.setWebTime(acctDtlRtnJsonDto.getInstDate());
    			id.setWebSeqNum(acctDtlRtnJsonDto.getSysSeqNum());
    			WebFrontTxnLog webFrontTxnLog = new WebFrontTxnLog();
    			webFrontTxnLog.setId(id);
    			webFrontTxnLog.setTxnCode(txnCode);
    			webFrontTxnLog.setTxnLog(acctDtlRtn);
    			// 组装返回前置系统成功信息
    			ret[0] = Constants.SUCCESS_CODE;
    			ret[1] = webFrontTxnLog;
    		}else{
	    		// 组装返回前置系统失败信息
	    		ret[0] = Constants.FAILURE_CODE;
	    		ret[1] = acctDtlRtn.getRtnMsg();
    		}
		}else{
			// 返回异常
			ret = get;
		}
    	// 返回信息
		return ret;
    }

}
