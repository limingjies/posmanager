package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblBillReceipt implements Serializable{

	
	private String billId;
	private String brhId;
	private String mchtNo;
	private String instDate;
	private String sysSeqNum;
	private String pan;
	private String amtTrans;
	private String receiptStatus;
	private String billOpr;
	private String billTime;
	private String receiptOpr;
	private String receiptTime;
	private String receiptRemark;
	private String posImagPath;
	private String misc;
	private String misc1;
	
	public TblBillReceipt() {

	}
	
	
	public String getMisc() {
		return misc;
	}
	public void setMisc(String misc) {
		this.misc = misc;
	}
	public String getMisc1() {
		return misc1;
	}
	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}
	public String getMchtNo() {
		return mchtNo;
	}


	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getInstDate() {
		return instDate;
	}


	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}


	public String getSysSeqNum() {
		return sysSeqNum;
	}


	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}


	public String getPan() {
		return pan;
	}


	public void setPan(String pan) {
		this.pan = pan;
	}


	public String getBillId() {
		return billId;
	}


	public void setBillId(String billId) {
		this.billId = billId;
	}


	public String getBrhId() {
		return brhId;
	}


	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}


	public String getAmtTrans() {
		return amtTrans;
	}


	public void setAmtTrans(String amtTrans) {
		this.amtTrans = amtTrans;
	}


	public String getReceiptStatus() {
		return receiptStatus;
	}


	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}


	public String getBillOpr() {
		return billOpr;
	}


	public void setBillOpr(String billOpr) {
		this.billOpr = billOpr;
	}


	public String getBillTime() {
		return billTime;
	}


	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}


	public String getReceiptOpr() {
		return receiptOpr;
	}


	public void setReceiptOpr(String receiptOpr) {
		this.receiptOpr = receiptOpr;
	}


	public String getReceiptTime() {
		return receiptTime;
	}


	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}


	public String getReceiptRemark() {
		return receiptRemark;
	}


	public void setReceiptRemark(String receiptRemark) {
		this.receiptRemark = receiptRemark;
	}


	public String getPosImagPath() {
		return posImagPath;
	}


	public void setPosImagPath(String posImagPath) {
		this.posImagPath = posImagPath;
	}

	

	
	

	
	
}
