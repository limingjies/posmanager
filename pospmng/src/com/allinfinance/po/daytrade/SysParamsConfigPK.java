package com.allinfinance.po.daytrade;

import java.io.Serializable;

/**
 *  SysParamsConfigPK.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年6月3日	  徐鹏飞          created this class.
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
public class SysParamsConfigPK implements Serializable {
	
	private static final long serialVersionUID = -6710923456480584852L;
	private String brhId;
	private String merchantId;
	private String paramCode;
	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}
	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		if(brhId==null||"".equals(brhId)){
			brhId = "*";
		}
		this.brhId = brhId;
	}
	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}
	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId)){
			merchantId = "*";
		}
		this.merchantId = merchantId;
	}
	/**
	 * @return the paramCode
	 */
	public String getParamCode() {
		return paramCode;
	}
	/**
	 * @param paramCode the paramCode to set
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	
}
