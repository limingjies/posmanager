package com.allinfinance.po.route;

/**
 * TblRouteRuleInfo entity. @author MyEclipse Persistence Tools
 */

public class TblRouteRuleInfo implements java.io.Serializable {

	// Fields

	private Integer ruleId;
	private String ruleName;
	private String ruleDsp;
	private String status;
	private String validTime;
	private String invalidTime;
	private Integer orders;
	private String brhId3;
	private Integer mchtGid;
	private String cardIssuergFlag;
	private String cardIssuerg;
	private String cardBinFlag;
	private String cardBin;
	private String cardTypeFlag;
	private String cardType;
	private String amountFlag;
	private Double amountBigger;
	private Double amountSmaller;
	private String txngFlag;
	private String txng;
	private String areagFlag;
	private String areag;
	private String mccgFlag;
	private String mccg;
	private String timesFlag;
	private String times;
	private String dateType;
	private String misc1;
	private String misc2;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblRouteRuleInfo() {
	}

	/** minimal constructor */
	public TblRouteRuleInfo(Integer ruleId, String ruleName, String status,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.status = status;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	/** full constructor */
	public TblRouteRuleInfo(Integer ruleId, String ruleName, String ruleDsp,
			String status, String validTime, String invalidTime,
			Integer orders, String brhId3, Integer mchtGid,
			String cardIssuergFlag, String cardIssuerg, String cardBinFlag,
			String cardBin, String cardTypeFlag, String cardType,
			String amountFlag, Double amountBigger, Double amountSmaller,
			String txngFlag, String txng, String areagFlag, String areag,
			String mccgFlag, String mccg, String timesFlag, String times,String dateType, String misc1, 
			String misc2, String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.ruleDsp = ruleDsp;
		this.status = status;
		this.validTime = validTime;
		this.invalidTime = invalidTime;
		this.orders = orders;
		this.brhId3 = brhId3;
		this.mchtGid = mchtGid;
		this.cardIssuergFlag = cardIssuergFlag;
		this.cardIssuerg = cardIssuerg;
		this.cardBinFlag = cardBinFlag;
		this.cardBin = cardBin;
		this.cardTypeFlag = cardTypeFlag;
		this.cardType = cardType;
		this.amountFlag = amountFlag;
		this.amountBigger = amountBigger;
		this.amountSmaller = amountSmaller;
		this.txngFlag = txngFlag;
		this.txng = txng;
		this.areagFlag = areagFlag;
		this.areag = areag;
		this.mccgFlag = mccgFlag;
		this.mccg = mccg;
		this.timesFlag = timesFlag;
		this.times = times;
		this.dateType = dateType;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDsp() {
		return this.ruleDsp;
	}

	public void setRuleDsp(String ruleDsp) {
		this.ruleDsp = ruleDsp;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValidTime() {
		return this.validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Integer getOrders() {
		return this.orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getBrhId3() {
		return this.brhId3;
	}

	public void setBrhId3(String brhId3) {
		this.brhId3 = brhId3;
	}

	public Integer getMchtGid() {
		return this.mchtGid;
	}

	public void setMchtGid(Integer mchtGid) {
		this.mchtGid = mchtGid;
	}

	public String getCardIssuergFlag() {
		return this.cardIssuergFlag;
	}

	public void setCardIssuergFlag(String cardIssuergFlag) {
		this.cardIssuergFlag = cardIssuergFlag;
	}

	public String getCardIssuerg() {
		return this.cardIssuerg;
	}

	public void setCardIssuerg(String cardIssuerg) {
		this.cardIssuerg = cardIssuerg;
	}

	public String getCardBinFlag() {
		return this.cardBinFlag;
	}

	public void setCardBinFlag(String cardBinFlag) {
		this.cardBinFlag = cardBinFlag;
	}

	public String getCardBin() {
		return this.cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCardTypeFlag() {
		return this.cardTypeFlag;
	}

	public void setCardTypeFlag(String cardTypeFlag) {
		this.cardTypeFlag = cardTypeFlag;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAmountFlag() {
		return this.amountFlag;
	}

	public void setAmountFlag(String amountFlag) {
		this.amountFlag = amountFlag;
	}

	public Double getAmountBigger() {
		return this.amountBigger;
	}

	public void setAmountBigger(Double amountBigger) {
		this.amountBigger = amountBigger;
	}

	public Double getAmountSmaller() {
		return this.amountSmaller;
	}

	public void setAmountSmaller(Double amountSmaller) {
		this.amountSmaller = amountSmaller;
	}

	public String getTxngFlag() {
		return this.txngFlag;
	}

	public void setTxngFlag(String txngFlag) {
		this.txngFlag = txngFlag;
	}

	public String getTxng() {
		return this.txng;
	}

	public void setTxng(String txng) {
		this.txng = txng;
	}

	public String getAreagFlag() {
		return this.areagFlag;
	}

	public void setAreagFlag(String areagFlag) {
		this.areagFlag = areagFlag;
	}

	public String getAreag() {
		return this.areag;
	}

	public void setAreag(String areag) {
		this.areag = areag;
	}

	public String getMccgFlag() {
		return this.mccgFlag;
	}

	public void setMccgFlag(String mccgFlag) {
		this.mccgFlag = mccgFlag;
	}

	public String getMccg() {
		return this.mccg;
	}

	public void setMccg(String mccg) {
		this.mccg = mccg;
	}

	public String getDateType() {
		return this.dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getMisc1() {
		return this.misc1;
	}

	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}

	public String getMisc2() {
		return this.misc2;
	}

	public void setMisc2(String misc2) {
		this.misc2 = misc2;
	}

	public String getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}

	public String getCrtOpr() {
		return this.crtOpr;
	}

	public void setCrtOpr(String crtOpr) {
		this.crtOpr = crtOpr;
	}

	public String getUptTime() {
		return this.uptTime;
	}

	public void setUptTime(String uptTime) {
		this.uptTime = uptTime;
	}

	public String getUptOpr() {
		return this.uptOpr;
	}

	public void setUptOpr(String uptOpr) {
		this.uptOpr = uptOpr;
	}

	public String getTimesFlag() {
		return timesFlag;
	}

	public void setTimesFlag(String timesFlag) {
		this.timesFlag = timesFlag;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

}