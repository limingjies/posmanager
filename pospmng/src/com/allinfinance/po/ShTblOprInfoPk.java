package com.allinfinance.po;

import java.io.Serializable;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2014-1-15
 * 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ShTblOprInfoPk implements Serializable{

	private String oprId;
	private String mchtNo;
	private String brhId;
	/**
	 * @return the oprId
	 */
	public String getOprId() {
		return oprId;
	}
	/**
	 * @param oprId the oprId to set
	 */
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}
	/**
	 * @param mchtNo the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	/**
	 * 
	 */
	public ShTblOprInfoPk() {
	}
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
		this.brhId = brhId;
	}
	
	
}
