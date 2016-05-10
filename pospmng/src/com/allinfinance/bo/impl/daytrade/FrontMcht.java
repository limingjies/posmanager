package com.allinfinance.bo.impl.daytrade;

import com.allinfinance.common.Constants;
import com.allinfinance.common.FrontConstants;
import com.allinfinance.frontend.dto.acctmanager.AcctDtlJsonDto;
import com.allinfinance.frontend.dto.acctmanager.AcctDtlRtnJsonDto;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.po.daytrade.WebFrontTxnLogPK;

/**
 *  FrontMcht.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年5月28日	  徐鹏飞          created this class.
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
public class FrontMcht extends HessianFront {
	
	/**
	 * 账户类型——T+0账户
	 */
	public static final String ACCT_TYPE_0 = "0";
	
	/**
	 * 账户类型——T+1账户
	 */
	public static final String ACCT_TYPE_1 = "1";
	
	/**
	 * 账户状态——已开通
	 */
	public static final String ACCT_CREATE_Y = "0";
	
	/**
	 * 账户状态——未开通
	 */
	public static final String ACCT_CREATE_N = "1";
	
	/**
	 * 账户类型——内部账户
	 */
	public static final String ACCT_TYPE_INTER = "0";
	
	/**
	 * 账户类型——个人账户
	 */
	public static final String ACCT_TYPE_PERSON = "1";
	
	/**
	 * 账户类型——企业账户
	 */
	public static final String ACCT_TYPE_ETPIS = "2";
	
	/**
	 * 货币种类——人民币
	 */
	public static final String CURRENCY_TYPE_CNY = "156";


	/**
	 * 业务交易码——商户开户
	 */
	public static final String TXN_CODE_ADDACCT = "022001";
	
	/**
	 * 业务交易码——商户注销账户
	 */
	public static final String TXN_CODE_DELACCT = "022002";

	/**
	 * 业务交易码——商户修改账户
	 */
	public static final String TXN_CODE_UPDACCT = "022003";
 
	/**
	 * 商户账户
	 * 
	 * @param txnCode 交易码
	 * @param acctDtl 账户信息对象 
	 * @return
	 */
    public Object[] doTxn(String txnCode, AcctDtlJsonDto acctDtl) {	
    	
    	// 定义返回信息
    	Object[] ret;
    	
    	// 组装发送信息
    	Object[] send = new Object[2];
    	send[0] = acctDtl;
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
    			id.setWebTime(acctDtl.getWebTime());
    			id.setWebSeqNum(acctDtl.getWebSeqNum());
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
