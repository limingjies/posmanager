package com.allinfinance.vo;

import java.io.Serializable;

/**
 * 调账操作VO
 * 
 * @author luhq
 *
 */
public class T8073201VO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5684663326343364249L;

	/**
	 * 商户号
	 */
	private String merchId;
	/**
	 * 清算日期
	 */
	private String settlDate;
	/**
	 * 对账差错表主键
	 */
	private String keyInst;

	/**
	 * 操作者ID
	 */
	private String oprId;
	
	/**
	 * 手工调账时填写的处理内容
	 */
	private String solveMsg;

	public String getSolveMsg() {
		return solveMsg;
	}

	public void setSolveMsg(String solveMsg) {
		this.solveMsg = solveMsg;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getSettlDate() {
		return settlDate;
	}

	public void setSettlDate(String settlDate) {
		this.settlDate = settlDate;
	}

	public String getKeyInst() {
		return keyInst;
	}

	public void setKeyInst(String keyInst) {
		this.keyInst = keyInst;
	}

	public String getOprId() {
		return oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}


}
