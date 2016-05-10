package com.allinfinance.po.mchnt;

/**
 * TblMchtSettleInfTmpHist entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TblMchtSettleInfTmpHist implements java.io.Serializable {

	// Fields

	private TblMchtSettleInfTmpHistPK id;
	private String settleType;
	private String rateFlag;
	private String settleChn;
	private String batTime;
	private String autoStlFlg;
	private String partNum;
	private String feeType;
	private String feeFixed;
	private String feeMaxAmt;
	private String feeMinAmt;
	private String feeRate;
	private String feeDiv1;
	private String feeDiv2;
	private String feeDiv3;
	private String settleMode;
	private String feeCycle;
	private String settleRpt;
	private String settleBankNo;
	private String settleBankNm;
	private String settleAcctNm;
	private String settleAcct;
	private String settleAcctMid;
	private String settleAcctMidNm;
	private String settleAcctMidBank;
	private String feeAcctNm;
	private String feeAcct;
	private String groupFlag;
	private String openStlno;
	private String changeStlno;
	private String speSettleTp;
	private String speSettleLv;
	private String speSettleDs;
	private String feeBackFlg;
	private String reserved;
	private String recUpdTs;
	private String recCrtTs;

	private java.lang.String bankStatement;
	private java.lang.String bankStatementReason;
	private java.lang.String integral;
	private java.lang.String integralReason;
	private java.lang.String country;
	private java.lang.String compliance;
	private java.lang.String emergency;

	public java.lang.String getEmergency() {
		return emergency;
	}

	public void setEmergency(java.lang.String emergency) {
		this.emergency = emergency;
	}

	/**
	 * @return the bankStatement
	 */
	public java.lang.String getBankStatement() {
		return bankStatement;
	}

	/**
	 * @param bankStatement
	 *            the bankStatement to set
	 */
	public void setBankStatement(java.lang.String bankStatement) {
		this.bankStatement = bankStatement;
	}

	/**
	 * @return the bankStatementReason
	 */
	public java.lang.String getBankStatementReason() {
		return bankStatementReason;
	}

	/**
	 * @param bankStatementReason
	 *            the bankStatementReason to set
	 */
	public void setBankStatementReason(java.lang.String bankStatementReason) {
		this.bankStatementReason = bankStatementReason;
	}

	/**
	 * @return the integral
	 */
	public java.lang.String getIntegral() {
		return integral;
	}

	/**
	 * @param integral
	 *            the integral to set
	 */
	public void setIntegral(java.lang.String integral) {
		this.integral = integral;
	}

	/**
	 * @return the integralReason
	 */
	public java.lang.String getIntegralReason() {
		return integralReason;
	}

	/**
	 * @param integralReason
	 *            the integralReason to set
	 */
	public void setIntegralReason(java.lang.String integralReason) {
		this.integralReason = integralReason;
	}

	/**
	 * @return the country
	 */
	public java.lang.String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(java.lang.String country) {
		this.country = country;
	}

	/**
	 * @return the compliance
	 */
	public java.lang.String getCompliance() {
		return compliance;
	}

	/**
	 * @param compliance
	 *            the compliance to set
	 */
	public void setCompliance(java.lang.String compliance) {
		this.compliance = compliance;
	}

	// Constructors

	/** default constructor */
	public TblMchtSettleInfTmpHist() {
	}

	/** minimal constructor */
	public TblMchtSettleInfTmpHist(TblMchtSettleInfTmpHistPK id,
			String settleType, String rateFlag, String feeType,
			String feeMaxAmt, String feeMinAmt, String feeDiv1, String feeDiv2,
			String feeDiv3, String recUpdTs, String recCrtTs) {
		this.id = id;
		this.settleType = settleType;
		this.rateFlag = rateFlag;
		this.feeType = feeType;
		this.feeMaxAmt = feeMaxAmt;
		this.feeMinAmt = feeMinAmt;
		this.feeDiv1 = feeDiv1;
		this.feeDiv2 = feeDiv2;
		this.feeDiv3 = feeDiv3;
		this.recUpdTs = recUpdTs;
		this.recCrtTs = recCrtTs;
	}

	/** full constructor */
	public TblMchtSettleInfTmpHist(TblMchtSettleInfTmpHistPK id,
			String settleType, String rateFlag, String settleChn,
			String batTime, String autoStlFlg, String partNum, String feeType,
			String feeFixed, String feeMaxAmt, String feeMinAmt,
			String feeRate, String feeDiv1, String feeDiv2, String feeDiv3,
			String settleMode, String feeCycle, String settleRpt,
			String settleBankNo, String settleBankNm, String settleAcctNm,
			String settleAcct, String settleAcctMid, String settleAcctMidNm,
			String settleAcctMidBank, String feeAcctNm, String feeAcct,
			String groupFlag, String openStlno, String changeStlno,
			String speSettleTp, String speSettleLv, String speSettleDs,
			String feeBackFlg, String reserved, String recUpdTs, String recCrtTs) {
		this.id = id;
		this.settleType = settleType;
		this.rateFlag = rateFlag;
		this.settleChn = settleChn;
		this.batTime = batTime;
		this.autoStlFlg = autoStlFlg;
		this.partNum = partNum;
		this.feeType = feeType;
		this.feeFixed = feeFixed;
		this.feeMaxAmt = feeMaxAmt;
		this.feeMinAmt = feeMinAmt;
		this.feeRate = feeRate;
		this.feeDiv1 = feeDiv1;
		this.feeDiv2 = feeDiv2;
		this.feeDiv3 = feeDiv3;
		this.settleMode = settleMode;
		this.feeCycle = feeCycle;
		this.settleRpt = settleRpt;
		this.settleBankNo = settleBankNo;
		this.settleBankNm = settleBankNm;
		this.settleAcctNm = settleAcctNm;
		this.settleAcct = settleAcct;
		this.settleAcctMid = settleAcctMid;
		this.settleAcctMidNm = settleAcctMidNm;
		this.settleAcctMidBank = settleAcctMidBank;
		this.feeAcctNm = feeAcctNm;
		this.feeAcct = feeAcct;
		this.groupFlag = groupFlag;
		this.openStlno = openStlno;
		this.changeStlno = changeStlno;
		this.speSettleTp = speSettleTp;
		this.speSettleLv = speSettleLv;
		this.speSettleDs = speSettleDs;
		this.feeBackFlg = feeBackFlg;
		this.reserved = reserved;
		this.recUpdTs = recUpdTs;
		this.recCrtTs = recCrtTs;
	}

	// Property accessors

	public TblMchtSettleInfTmpHistPK getId() {
		return this.id;
	}

	public void setId(TblMchtSettleInfTmpHistPK id) {
		this.id = id;
	}

	public String getSettleType() {
		return this.settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public String getRateFlag() {
		return this.rateFlag;
	}

	public void setRateFlag(String rateFlag) {
		this.rateFlag = rateFlag;
	}

	public String getSettleChn() {
		return this.settleChn;
	}

	public void setSettleChn(String settleChn) {
		this.settleChn = settleChn;
	}

	public String getBatTime() {
		return this.batTime;
	}

	public void setBatTime(String batTime) {
		this.batTime = batTime;
	}

	public String getAutoStlFlg() {
		return this.autoStlFlg;
	}

	public void setAutoStlFlg(String autoStlFlg) {
		this.autoStlFlg = autoStlFlg;
	}

	public String getPartNum() {
		return this.partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public String getFeeType() {
		return this.feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeFixed() {
		return this.feeFixed;
	}

	public void setFeeFixed(String feeFixed) {
		this.feeFixed = feeFixed;
	}

	public String getFeeMaxAmt() {
		return this.feeMaxAmt;
	}

	public void setFeeMaxAmt(String feeMaxAmt) {
		this.feeMaxAmt = feeMaxAmt;
	}

	public String getFeeMinAmt() {
		return this.feeMinAmt;
	}

	public void setFeeMinAmt(String feeMinAmt) {
		this.feeMinAmt = feeMinAmt;
	}

	public String getFeeRate() {
		return this.feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}

	public String getFeeDiv1() {
		return this.feeDiv1;
	}

	public void setFeeDiv1(String feeDiv1) {
		this.feeDiv1 = feeDiv1;
	}

	public String getFeeDiv2() {
		return this.feeDiv2;
	}

	public void setFeeDiv2(String feeDiv2) {
		this.feeDiv2 = feeDiv2;
	}

	public String getFeeDiv3() {
		return this.feeDiv3;
	}

	public void setFeeDiv3(String feeDiv3) {
		this.feeDiv3 = feeDiv3;
	}

	public String getSettleMode() {
		return this.settleMode;
	}

	public void setSettleMode(String settleMode) {
		this.settleMode = settleMode;
	}

	public String getFeeCycle() {
		return this.feeCycle;
	}

	public void setFeeCycle(String feeCycle) {
		this.feeCycle = feeCycle;
	}

	public String getSettleRpt() {
		return this.settleRpt;
	}

	public void setSettleRpt(String settleRpt) {
		this.settleRpt = settleRpt;
	}

	public String getSettleBankNo() {
		return this.settleBankNo;
	}

	public void setSettleBankNo(String settleBankNo) {
		this.settleBankNo = settleBankNo;
	}

	public String getSettleBankNm() {
		return this.settleBankNm;
	}

	public void setSettleBankNm(String settleBankNm) {
		this.settleBankNm = settleBankNm;
	}

	public String getSettleAcctNm() {
		return this.settleAcctNm;
	}

	public void setSettleAcctNm(String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}

	public String getSettleAcct() {
		return this.settleAcct;
	}

	public void setSettleAcct(String settleAcct) {
		this.settleAcct = settleAcct;
	}

	public String getSettleAcctMid() {
		return this.settleAcctMid;
	}

	public void setSettleAcctMid(String settleAcctMid) {
		this.settleAcctMid = settleAcctMid;
	}

	public String getSettleAcctMidNm() {
		return this.settleAcctMidNm;
	}

	public void setSettleAcctMidNm(String settleAcctMidNm) {
		this.settleAcctMidNm = settleAcctMidNm;
	}

	public String getSettleAcctMidBank() {
		return this.settleAcctMidBank;
	}

	public void setSettleAcctMidBank(String settleAcctMidBank) {
		this.settleAcctMidBank = settleAcctMidBank;
	}

	public String getFeeAcctNm() {
		return this.feeAcctNm;
	}

	public void setFeeAcctNm(String feeAcctNm) {
		this.feeAcctNm = feeAcctNm;
	}

	public String getFeeAcct() {
		return this.feeAcct;
	}

	public void setFeeAcct(String feeAcct) {
		this.feeAcct = feeAcct;
	}

	public String getGroupFlag() {
		return this.groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getOpenStlno() {
		return this.openStlno;
	}

	public void setOpenStlno(String openStlno) {
		this.openStlno = openStlno;
	}

	public String getChangeStlno() {
		return this.changeStlno;
	}

	public void setChangeStlno(String changeStlno) {
		this.changeStlno = changeStlno;
	}

	public String getSpeSettleTp() {
		return this.speSettleTp;
	}

	public void setSpeSettleTp(String speSettleTp) {
		this.speSettleTp = speSettleTp;
	}

	public String getSpeSettleLv() {
		return this.speSettleLv;
	}

	public void setSpeSettleLv(String speSettleLv) {
		this.speSettleLv = speSettleLv;
	}

	public String getSpeSettleDs() {
		return this.speSettleDs;
	}

	public void setSpeSettleDs(String speSettleDs) {
		this.speSettleDs = speSettleDs;
	}

	public String getFeeBackFlg() {
		return this.feeBackFlg;
	}

	public void setFeeBackFlg(String feeBackFlg) {
		this.feeBackFlg = feeBackFlg;
	}

	public String getReserved() {
		return this.reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getRecUpdTs() {
		return this.recUpdTs;
	}

	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public String getRecCrtTs() {
		return this.recCrtTs;
	}

	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

}