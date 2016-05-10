package com.allinfinance.po.settle;

import java.io.Serializable;

public class TblPayChannelInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String channelId;
	private String channelName;	
	private String channelFlag;
	private String channelSta;	
	private String acctNo;	
	private String acctNm;	
	private String mchtNo;	
	private String mchtNm;	
	private String misc;	
	private String misc1;
	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * @return the channelFlag
	 */
	public String getChannelFlag() {
		return channelFlag;
	}
	/**
	 * @param channelFlag the channelFlag to set
	 */
	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}
	/**
	 * @return the channelSta
	 */
	public String getChannelSta() {
		return channelSta;
	}
	/**
	 * @param channelSta the channelSta to set
	 */
	public void setChannelSta(String channelSta) {
		this.channelSta = channelSta;
	}
	/**
	 * @return the acctNo
	 */
	public String getAcctNo() {
		return acctNo;
	}
	/**
	 * @param acctNo the acctNo to set
	 */
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	/**
	 * @return the acctNm
	 */
	public String getAcctNm() {
		return acctNm;
	}
	/**
	 * @param acctNm the acctNm to set
	 */
	public void setAcctNm(String acctNm) {
		this.acctNm = acctNm;
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
	 * @return the mchtNm
	 */
	public String getMchtNm() {
		return mchtNm;
	}
	/**
	 * @param mchtNm the mchtNm to set
	 */
	public void setMchtNm(String mchtNm) {
		this.mchtNm = mchtNm;
	}
	/**
	 * @return the misc
	 */
	public String getMisc() {
		return misc;
	}
	/**
	 * @param misc the misc to set
	 */
	public void setMisc(String misc) {
		this.misc = misc;
	}
	/**
	 * @return the misc1
	 */
	public String getMisc1() {
		return misc1;
	}
	/**
	 * @param misc1 the misc1 to set
	 */
	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}
}