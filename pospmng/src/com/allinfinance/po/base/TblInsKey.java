package com.allinfinance.po.base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblInsKey implements Serializable{

	
	private TblInsKeyPK tblInsKeyPK;
	
	private String insKeyIdx;
	private String insMacKeyLen;
	private String insMac1;
	private String insMac2;
	private String macChkValue;
	private String insPinKeyLen;
	private String insPin1;
	private String insPin2;
	private String pinChkValue;
	private String recOprId;
	private String recUpdOpr;
	private String recCrtTs;
	/**
	 * 
	 */
	public TblInsKey() {
		
	}
	public TblInsKeyPK getTblInsKeyPK() {
		return tblInsKeyPK;
	}
	public void setTblInsKeyPK(TblInsKeyPK tblInsKeyPK) {
		this.tblInsKeyPK = tblInsKeyPK;
	}
	public String getInsKeyIdx() {
		return insKeyIdx;
	}
	public void setInsKeyIdx(String insKeyIdx) {
		this.insKeyIdx = insKeyIdx;
	}
	public String getInsMacKeyLen() {
		return insMacKeyLen;
	}
	public void setInsMacKeyLen(String insMacKeyLen) {
		this.insMacKeyLen = insMacKeyLen;
	}
	public String getInsMac1() {
		return insMac1;
	}
	public void setInsMac1(String insMac1) {
		this.insMac1 = insMac1;
	}
	public String getInsMac2() {
		return insMac2;
	}
	public void setInsMac2(String insMac2) {
		this.insMac2 = insMac2;
	}
	public String getMacChkValue() {
		return macChkValue;
	}
	public void setMacChkValue(String macChkValue) {
		this.macChkValue = macChkValue;
	}
	public String getInsPinKeyLen() {
		return insPinKeyLen;
	}
	public void setInsPinKeyLen(String insPinKeyLen) {
		this.insPinKeyLen = insPinKeyLen;
	}
	public String getInsPin1() {
		return insPin1;
	}
	public void setInsPin1(String insPin1) {
		this.insPin1 = insPin1;
	}
	public String getInsPin2() {
		return insPin2;
	}
	public void setInsPin2(String insPin2) {
		this.insPin2 = insPin2;
	}
	public String getPinChkValue() {
		return pinChkValue;
	}
	public void setPinChkValue(String pinChkValue) {
		this.pinChkValue = pinChkValue;
	}
	public String getRecOprId() {
		return recOprId;
	}
	public void setRecOprId(String recOprId) {
		this.recOprId = recOprId;
	}
	public String getRecUpdOpr() {
		return recUpdOpr;
	}
	public void setRecUpdOpr(String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}
	public String getRecCrtTs() {
		return recCrtTs;
	}
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	
	
}
