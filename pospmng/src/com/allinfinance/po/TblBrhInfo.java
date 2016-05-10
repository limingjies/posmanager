package com.allinfinance.po;

/**
 * TblBrhInfo entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class TblBrhInfo implements java.io.Serializable {

	// Fields

	private String brhId;
	private String cupBrhId;
	private String brhLevel;
	private String brhSta;
	private String upBrhId;
	private String regDt;
	private String postCd;
	private String brhAddr;
	private String brhName;
	private String brhTelNo;
	private String brhContName;
	private String openRateId;
	private String resv1;
	private String resv2;
	private String resv3;
	private String resv4;
	private String resv5;
	private String lastUpdOprId;
	private String lastUpdTxnId;
	private String lastUpdTs;
	private String settleOrgNo;
	private String settleJobType;
	private String settleMemProperty;
	private String status;
	private String createNewNo;

	// Constructors

	/** default constructor */
	public TblBrhInfo() {
	}

	/** minimal constructor */
	public TblBrhInfo(String brhId, String brhLevel, String brhSta,
			String upBrhId, String regDt, String brhAddr, String brhName,
			String brhTelNo) {
		this.brhId = brhId;
		this.brhLevel = brhLevel;
		this.brhSta = brhSta;
		this.upBrhId = upBrhId;
		this.regDt = regDt;
		this.brhAddr = brhAddr;
		this.brhName = brhName;
		this.brhTelNo = brhTelNo;
	}

	/** full constructor */
	public TblBrhInfo(String brhId, String cupBrhId, String brhLevel,
			String brhSta, String upBrhId, String regDt, String postCd,
			String brhAddr, String brhName, String brhTelNo,
			String brhContName, String openRateId, String resv1, String resv2,
			String resv3, String resv4, String resv5, String lastUpdOprId,
			String lastUpdTxnId, String lastUpdTs, String settleOrgNo,
			String settleJobType, String settleMemProperty, String status,
			String createNewNo) {
		this.brhId = brhId;
		this.cupBrhId = cupBrhId;
		this.brhLevel = brhLevel;
		this.brhSta = brhSta;
		this.upBrhId = upBrhId;
		this.regDt = regDt;
		this.postCd = postCd;
		this.brhAddr = brhAddr;
		this.brhName = brhName;
		this.brhTelNo = brhTelNo;
		this.brhContName = brhContName;
		this.openRateId = openRateId;
		this.resv1 = resv1;
		this.resv2 = resv2;
		this.resv3 = resv3;
		this.resv4 = resv4;
		this.resv5 = resv5;
		this.lastUpdOprId = lastUpdOprId;
		this.lastUpdTxnId = lastUpdTxnId;
		this.lastUpdTs = lastUpdTs;
		this.settleOrgNo = settleOrgNo;
		this.settleJobType = settleJobType;
		this.settleMemProperty = settleMemProperty;
		this.status = status;
		this.createNewNo = createNewNo;
	}

	// Property accessors

	public String getBrhId() {
		return this.brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getCupBrhId() {
		return this.cupBrhId;
	}

	public void setCupBrhId(String cupBrhId) {
		this.cupBrhId = cupBrhId;
	}

	public String getBrhLevel() {
		return this.brhLevel;
	}

	public void setBrhLevel(String brhLevel) {
		this.brhLevel = brhLevel;
	}

	public String getBrhSta() {
		return this.brhSta;
	}

	public void setBrhSta(String brhSta) {
		this.brhSta = brhSta;
	}

	public String getUpBrhId() {
		return this.upBrhId;
	}

	public void setUpBrhId(String upBrhId) {
		this.upBrhId = upBrhId;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getPostCd() {
		return this.postCd;
	}

	public void setPostCd(String postCd) {
		this.postCd = postCd;
	}

	public String getBrhAddr() {
		return this.brhAddr;
	}

	public void setBrhAddr(String brhAddr) {
		this.brhAddr = brhAddr;
	}

	public String getBrhName() {
		return this.brhName;
	}

	public void setBrhName(String brhName) {
		this.brhName = brhName;
	}

	public String getBrhTelNo() {
		return this.brhTelNo;
	}

	public void setBrhTelNo(String brhTelNo) {
		this.brhTelNo = brhTelNo;
	}

	public String getBrhContName() {
		return this.brhContName;
	}

	public void setBrhContName(String brhContName) {
		this.brhContName = brhContName;
	}

	public String getOpenRateId() {
		return this.openRateId;
	}

	public void setOpenRateId(String openRateId) {
		this.openRateId = openRateId;
	}

	public String getResv1() {
		return this.resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}

	public String getResv2() {
		return this.resv2;
	}

	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}

	public String getResv3() {
		return this.resv3;
	}

	public void setResv3(String resv3) {
		this.resv3 = resv3;
	}

	public String getResv4() {
		return this.resv4;
	}

	public void setResv4(String resv4) {
		this.resv4 = resv4;
	}

	public String getResv5() {
		return this.resv5;
	}

	public void setResv5(String resv5) {
		this.resv5 = resv5;
	}

	public String getLastUpdOprId() {
		return this.lastUpdOprId;
	}

	public void setLastUpdOprId(String lastUpdOprId) {
		this.lastUpdOprId = lastUpdOprId;
	}

	public String getLastUpdTxnId() {
		return this.lastUpdTxnId;
	}

	public void setLastUpdTxnId(String lastUpdTxnId) {
		this.lastUpdTxnId = lastUpdTxnId;
	}

	public String getLastUpdTs() {
		return this.lastUpdTs;
	}

	public void setLastUpdTs(String lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}

	public String getSettleOrgNo() {
		return this.settleOrgNo;
	}

	public void setSettleOrgNo(String settleOrgNo) {
		this.settleOrgNo = settleOrgNo;
	}

	public String getSettleJobType() {
		return this.settleJobType;
	}

	public void setSettleJobType(String settleJobType) {
		this.settleJobType = settleJobType;
	}

	public String getSettleMemProperty() {
		return this.settleMemProperty;
	}

	public void setSettleMemProperty(String settleMemProperty) {
		this.settleMemProperty = settleMemProperty;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateNewNo() {
		return this.createNewNo;
	}

	public void setCreateNewNo(String createNewNo) {
		this.createNewNo = createNewNo;
	}

}