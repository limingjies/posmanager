package com.allinfinance.po.daytrade;

import java.io.Serializable;

/**
 *  TblWithdrawInfDtl.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年6月9日	  徐鹏飞          created this class.
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
public class TblWithdrawInfDtl implements Serializable {
	
	private static final long serialVersionUID = 5246252602045814067L;
	private java.lang.String batchNo;
	private java.lang.String instDate;
	private java.lang.String sysSeqNum;
	/**
	 * @return the batchNo
	 */
	public java.lang.String getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(java.lang.String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the instDate
	 */
	public java.lang.String getInstDate() {
		return instDate;
	}
	/**
	 * @param instDate the instDate to set
	 */
	public void setInstDate(java.lang.String instDate) {
		this.instDate = instDate;
	}
	/**
	 * @return the sysSeqNum
	 */
	public java.lang.String getSysSeqNum() {
		return sysSeqNum;
	}
	/**
	 * @param sysSeqNum the sysSeqNum to set
	 */
	public void setSysSeqNum(java.lang.String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}
}
