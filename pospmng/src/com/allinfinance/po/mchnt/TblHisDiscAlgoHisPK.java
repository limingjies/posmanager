package com.allinfinance.po.mchnt;

import java.io.Serializable;


public class TblHisDiscAlgoHisPK  implements Serializable{
	private java.lang.String discId;
	private java.lang.Integer indexNum;
	private java.lang.Integer serialNo;

	/**
	 * @return the discId
	 */
	public java.lang.String getDiscId() {
		return discId;
	}

	/**
	 * @param discId the discId to set
	 */
	public void setDiscId(java.lang.String discId) {
		this.discId = discId;
	}

	/**
	 * @return the indexNum
	 */
	public java.lang.Integer getIndexNum() {
		return indexNum;
	}

	/**
	 * @param indexNum the indexNum to set
	 */
	public void setIndexNum(java.lang.Integer indexNum) {
		this.indexNum = indexNum;
	}

	/**
	 * @return the serialNo
	 */
	public java.lang.Integer getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(java.lang.Integer serialNo) {
		this.serialNo = serialNo;
	}

	public TblHisDiscAlgoHisPK () {}
	
	public TblHisDiscAlgoHisPK (
		java.lang.String discId,
		java.lang.Integer indexNum) {

	}

}