package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblMchtFund implements Serializable {

	/**
	 * Tbl_MCHT_FUND表映射对象
	 */
	private static final long serialVersionUID = 1L;

	private String mchtNo;
	private String bal;
	private String avlBal;
	private String frzAmt;
	private String flag1;
	private String flag2;
	private String flag3;
	private String misc1;
	private String misc2;
	private String misc3;
	private String lstUpdTlr;
	private String lstUpdTime;
	private String createTime;

	public TblMchtFund() {
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

	public String getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(String avlBal) {
		this.avlBal = avlBal;
	}

	public String getFrzAmt() {
		return frzAmt;
	}

	public void setFrzAmt(String frzAmt) {
		this.frzAmt = frzAmt;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public String getFlag3() {
		return flag3;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	public String getMisc1() {
		return misc1;
	}

	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}

	public String getMisc2() {
		return misc2;
	}

	public void setMisc2(String misc2) {
		this.misc2 = misc2;
	}

	public String getMisc3() {
		return misc3;
	}

	public void setMisc3(String misc3) {
		this.misc3 = misc3;
	}

	public String getLstUpdTlr() {
		return lstUpdTlr;
	}

	public void setLstUpdTlr(String lstUpdTlr) {
		this.lstUpdTlr = lstUpdTlr;
	}

	public String getLstUpdTime() {
		return lstUpdTime;
	}

	public void setLstUpdTime(String lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
