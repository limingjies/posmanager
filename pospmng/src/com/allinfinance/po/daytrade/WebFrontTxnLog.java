package com.allinfinance.po.daytrade;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 *  WebFrontTxnLog.java
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
public class WebFrontTxnLog implements Serializable {

	private static final long serialVersionUID = 8794954926061138788L;
	private WebFrontTxnLogPK id;
	private String txnCode;
	private String txnLog;
	
	/**
	 * @return the id
	 */
	public WebFrontTxnLogPK getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(WebFrontTxnLogPK id) {
		this.id = id;
	}
	/**
	 * @return the txnCode
	 */
	public String getTxnCode() {
		return txnCode;
	}
	/**
	 * @param txnCode the txnCode to set
	 */
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}
	/**
	 * @return the txnLog
	 */
	public String getTxnLog() {
		return txnLog;
	}
	/**
	 * @param txnLog the txnLog to set
	 */
	public void setTxnLog(String txnLog) {
		this.txnLog = txnLog;
	}
	/**
	 * @param txnLog the txnLog to set
	 */
	public void setTxnLog(Object object) {
		this.txnLog = JSON.toJSONString(object);
	}
	

}
