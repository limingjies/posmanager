package com.allinfinance.po.query;

import java.io.Serializable;
import java.util.Date;

/**
 * 确认入账历史
 * 
 * @author luhq
 *
 */
public class TblAccInConfirmHis implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6932922269927967001L;
	/**
	 * 清算时间(格式:YYYYMMDD)
	 */
	private String dateSettlmt;
	/**
	 * 操作员ID
	 */
	private String recOprId;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	public String getRecOprId() {
		return recOprId;
	}

	public void setRecOprId(String recOprId) {
		this.recOprId = recOprId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
