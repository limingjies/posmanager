package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblMchtFileTransInfo implements Serializable {

	/**
	 * TBL_MCHT_FILE_TRANS_INFO表映射对象
	 */
	private static final long serialVersionUID = 1L;
	
	public TblMchtFileTransInfo() {
		
	}
	private String mchtNo;
	private String getFileWay;
	private String commWay;
	private String checkType;
	private String userName;
	private String passwd;
	private String ip;
	private String port;
	private String filePath;
	private String keyPath;
	private String keyIdx;
	private String keyVal;
	private String checkVal;
	private String rspSendFlag;
	private Integer rspSendTime;
	private String dzSendTime;
	private String misc1;
	private String misc2;
	private String misc3;
	private String lstUpdTlr;
	private String lstUpdTime;
	private String createTime;

	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	public String getGetFileWay() {
		return getFileWay;
	}
	public void setGetFileWay(String getFileWay) {
		this.getFileWay = getFileWay;
	}
	public String getCommWay() {
		return commWay;
	}
	public void setCommWay(String commWay) {
		this.commWay = commWay;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getKeyPath() {
		return keyPath;
	}
	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}
	public String getKeyIdx() {
		return keyIdx;
	}
	public void setKeyIdx(String keyIdx) {
		this.keyIdx = keyIdx;
	}
	public String getKeyVal() {
		return keyVal;
	}
	public void setKeyVal(String keyVal) {
		this.keyVal = keyVal;
	}
	public String getCheckVal() {
		return checkVal;
	}
	public void setCheckVal(String checkVal) {
		this.checkVal = checkVal;
	}
	public String getRspSendFlag() {
		return rspSendFlag;
	}
	public void setRspSendFlag(String rspSendFlag) {
		this.rspSendFlag = rspSendFlag;
	}
	public Integer getRspSendTime() {
		return rspSendTime;
	}
	public void setRspSendTime(Integer rspSendTime) {
		this.rspSendTime = rspSendTime;
	}
	public String getDzSendTime() {
		return dzSendTime;
	}
	public void setDzSendTime(String dzSendTime) {
		this.dzSendTime = dzSendTime;
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
