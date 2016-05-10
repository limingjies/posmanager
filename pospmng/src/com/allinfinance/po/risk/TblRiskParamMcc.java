package com.allinfinance.po.risk;

import java.io.Serializable;

/**
 * MCC风控参数管理 -- TBL_RISK_PARAM_MCC表实体bean
 * @author yww
 * @version 1.0
 * 2016年4月7日  下午1:55:39
 */
public class TblRiskParamMcc implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * MCC码
	 */
	private String mcc;
	
	/**
	 * 公司商户限额
	 */
	private String mchtCoAmt;
	
	/**
	 * 个体商户限额
	 */
	private String mchtSeAmt;
	
	/**
	 * 无营业执照商户限额
	 */
	private String mchtNlAmt;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 新增时间
	 */
	private String regTime;
	
	/**
	 * 新增操作员
	 */
	private String regOpr;
	
	/**
	 * 更新时间
	 */
	private String updTime;
	
	/**
	 * 更新操作员
	 */
	private String updOpr;
	
	/**
	 * 保留
	 */
	private String resved;
	
	/**
	 * 保留1
	 */
	private String resved1;

	
	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getMchtCoAmt() {
		return mchtCoAmt;
	}

	public void setMchtCoAmt(String mchtCoAmt) {
		this.mchtCoAmt = mchtCoAmt;
	}

	public String getMchtSeAmt() {
		return mchtSeAmt;
	}

	public void setMchtSeAmt(String mchtSeAmt) {
		this.mchtSeAmt = mchtSeAmt;
	}

	public String getMchtNlAmt() {
		return mchtNlAmt;
	}

	public void setMchtNlAmt(String mchtNlAmt) {
		this.mchtNlAmt = mchtNlAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getRegOpr() {
		return regOpr;
	}

	public void setRegOpr(String regOpr) {
		this.regOpr = regOpr;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getUpdOpr() {
		return updOpr;
	}

	public void setUpdOpr(String updOpr) {
		this.updOpr = updOpr;
	}

	public String getResved() {
		return resved;
	}

	public void setResved(String resved) {
		this.resved = resved;
	}

	public String getResved1() {
		return resved1;
	}

	public void setResved1(String resved1) {
		this.resved1 = resved1;
	}
	
	
}