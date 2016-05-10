package com.allinfinance.po.settle;

import java.io.Serializable;
import java.math.BigDecimal;

public class TblInfileOpt implements Serializable {

	/**
	 * @return the id
	 */
	public TblInfileOptPK getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(TblInfileOptPK id) {
		this.id = id;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the settleAmt
	 */
	public BigDecimal getSettleAmt() {
		return settleAmt;
	}
	/**
	 * @param settleAmt the settleAmt to set
	 */
	public void setSettleAmt(BigDecimal settleAmt) {
		this.settleAmt = settleAmt;
	}
	/**
	 * @return the txnAmt
	 */
	public BigDecimal getTxnAmt() {
		return txnAmt;
	}
	/**
	 * @param txnAmt the txnAmt to set
	 */
	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}
	/**
	 * @return the reservedAmt1
	 */
	public BigDecimal getReservedAmt1() {
		return reservedAmt1;
	}
	/**
	 * @param reservedAmt1 the reservedAmt1 to set
	 */
	public void setReservedAmt1(BigDecimal reservedAmt1) {
		this.reservedAmt1 = reservedAmt1;
	}
	/**
	 * @return the reservedAmt2
	 */
	public BigDecimal getReservedAmt2() {
		return reservedAmt2;
	}
	/**
	 * @param reservedAmt2 the reservedAmt2 to set
	 */
	public void setReservedAmt2(BigDecimal reservedAmt2) {
		this.reservedAmt2 = reservedAmt2;
	}
	/**
	 * @return the settleMchntFee
	 */
	public BigDecimal getSettleMchntFee() {
		return settleMchntFee;
	}
	/**
	 * @param settleMchntFee the settleMchntFee to set
	 */
	public void setSettleMchntFee(BigDecimal settleMchntFee) {
		this.settleMchntFee = settleMchntFee;
	}
	/**
	 * @return the settleCupsFee
	 */
	public BigDecimal getSettleCupsFee() {
		return settleCupsFee;
	}
	/**
	 * @param settleCupsFee the settleCupsFee to set
	 */
	public void setSettleCupsFee(BigDecimal settleCupsFee) {
		this.settleCupsFee = settleCupsFee;
	}
	/**
	 * @return the reservedFee1
	 */
	public BigDecimal getReservedFee1() {
		return reservedFee1;
	}
	/**
	 * @param reservedFee1 the reservedFee1 to set
	 */
	public void setReservedFee1(BigDecimal reservedFee1) {
		this.reservedFee1 = reservedFee1;
	}
	/**
	 * @return the reservedFee2
	 */
	public BigDecimal getReservedFee2() {
		return reservedFee2;
	}
	/**
	 * @param reservedFee2 the reservedFee2 to set
	 */
	public void setReservedFee2(BigDecimal reservedFee2) {
		this.reservedFee2 = reservedFee2;
	}
	/**
	 * @return the outMchntSettleFile
	 */
	public String getOutMchntSettleFile() {
		return outMchntSettleFile;
	}
	/**
	 * @param outMchntSettleFile the outMchntSettleFile to set
	 */
	public void setOutMchntSettleFile(String outMchntSettleFile) {
		this.outMchntSettleFile = outMchntSettleFile;
	}
	/**
	 * @return the outMchntSettleFileSta
	 */
	public String getOutMchntSettleFileSta() {
		return outMchntSettleFileSta;
	}
	/**
	 * @param outMchntSettleFileSta the outMchntSettleFileSta to set
	 */
	public void setOutMchntSettleFileSta(String outMchntSettleFileSta) {
		this.outMchntSettleFileSta = outMchntSettleFileSta;
	}
	/**
	 * @return the outCupsFeeFile
	 */
	public String getOutCupsFeeFile() {
		return outCupsFeeFile;
	}
	/**
	 * @param outCupsFeeFile the outCupsFeeFile to set
	 */
	public void setOutCupsFeeFile(String outCupsFeeFile) {
		this.outCupsFeeFile = outCupsFeeFile;
	}
	/**
	 * @return the outCupsFeeFileSta
	 */
	public String getOutCupsFeeFileSta() {
		return outCupsFeeFileSta;
	}
	/**
	 * @param outCupsFeeFileSta the outCupsFeeFileSta to set
	 */
	public void setOutCupsFeeFileSta(String outCupsFeeFileSta) {
		this.outCupsFeeFileSta = outCupsFeeFileSta;
	}
	/**
	 * @return the reservedFile1
	 */
	public String getReservedFile1() {
		return reservedFile1;
	}
	/**
	 * @param reservedFile1 the reservedFile1 to set
	 */
	public void setReservedFile1(String reservedFile1) {
		this.reservedFile1 = reservedFile1;
	}
	/**
	 * @return the reservedFile1Sta
	 */
	public String getReservedFile1Sta() {
		return reservedFile1Sta;
	}
	/**
	 * @param reservedFile1Sta the reservedFile1Sta to set
	 */
	public void setReservedFile1Sta(String reservedFile1Sta) {
		this.reservedFile1Sta = reservedFile1Sta;
	}
	/**
	 * @return the reservedFile2
	 */
	public String getReservedFile2() {
		return reservedFile2;
	}
	/**
	 * @param reservedFile2 the reservedFile2 to set
	 */
	public void setReservedFile2(String reservedFile2) {
		this.reservedFile2 = reservedFile2;
	}
	/**
	 * @return the reservedFile2Sta
	 */
	public String getReservedFile2Sta() {
		return reservedFile2Sta;
	}
	/**
	 * @param reservedFile2Sta the reservedFile2Sta to set
	 */
	public void setReservedFile2Sta(String reservedFile2Sta) {
		this.reservedFile2Sta = reservedFile2Sta;
	}
	/**
	 * @return the succMchntFile
	 */
	public String getSuccMchntFile() {
		return succMchntFile;
	}
	/**
	 * @param succMchntFile the succMchntFile to set
	 */
	public void setSuccMchntFile(String succMchntFile) {
		this.succMchntFile = succMchntFile;
	}
	/**
	 * @return the failMchntFile
	 */
	public String getFailMchntFile() {
		return failMchntFile;
	}
	/**
	 * @param failMchntFile the failMchntFile to set
	 */
	public void setFailMchntFile(String failMchntFile) {
		this.failMchntFile = failMchntFile;
	}
	/**
	 * @return the succCupsFeeFile
	 */
	public String getSuccCupsFeeFile() {
		return succCupsFeeFile;
	}
	/**
	 * @param succCupsFeeFile the succCupsFeeFile to set
	 */
	public void setSuccCupsFeeFile(String succCupsFeeFile) {
		this.succCupsFeeFile = succCupsFeeFile;
	}
	/**
	 * @return the failCupsFeeFile
	 */
	public String getFailCupsFeeFile() {
		return failCupsFeeFile;
	}
	/**
	 * @param failCupsFeeFile the failCupsFeeFile to set
	 */
	public void setFailCupsFeeFile(String failCupsFeeFile) {
		this.failCupsFeeFile = failCupsFeeFile;
	}
	/**
	 * @return the succReservedFile1
	 */
	public String getSuccReservedFile1() {
		return succReservedFile1;
	}
	/**
	 * @param succReservedFile1 the succReservedFile1 to set
	 */
	public void setSuccReservedFile1(String succReservedFile1) {
		this.succReservedFile1 = succReservedFile1;
	}
	/**
	 * @return the failReservedFile1
	 */
	public String getFailReservedFile1() {
		return failReservedFile1;
	}
	/**
	 * @param failReservedFile1 the failReservedFile1 to set
	 */
	public void setFailReservedFile1(String failReservedFile1) {
		this.failReservedFile1 = failReservedFile1;
	}
	/**
	 * @return the succReservedFile2
	 */
	public String getSuccReservedFile2() {
		return succReservedFile2;
	}
	/**
	 * @param succReservedFile2 the succReservedFile2 to set
	 */
	public void setSuccReservedFile2(String succReservedFile2) {
		this.succReservedFile2 = succReservedFile2;
	}
	/**
	 * @return the failReservedFile2
	 */
	public String getFailReservedFile2() {
		return failReservedFile2;
	}
	/**
	 * @param failReservedFile2 the failReservedFile2 to set
	 */
	public void setFailReservedFile2(String failReservedFile2) {
		this.failReservedFile2 = failReservedFile2;
	}
	/**
	 * @return the stlmFlag
	 */
	public String getStlmFlag() {
		return stlmFlag;
	}
	/**
	 * @param stlmFlag the stlmFlag to set
	 */
	public void setStlmFlag(String stlmFlag) {
		this.stlmFlag = stlmFlag;
	}
	/**
	 * @return the stlmFlag2
	 */
	public String getStlmFlag2() {
		return stlmFlag2;
	}
	/**
	 * @param stlmFlag2 the stlmFlag2 to set
	 */
	public void setStlmFlag2(String stlmFlag2) {
		this.stlmFlag2 = stlmFlag2;
	}
	/**
	 * @return the mchntCount
	 */
	public String getMchntCount() {
		return mchntCount;
	}
	/**
	 * @param mchntCount the mchntCount to set
	 */
	public void setMchntCount(String mchntCount) {
		this.mchntCount = mchntCount;
	}
	/**
	 * @return the mchntCountDis
	 */
	public String getMchntCountDis() {
		return mchntCountDis;
	}
	/**
	 * @param mchntCountDis the mchntCountDis to set
	 */
	public void setMchntCountDis(String mchntCountDis) {
		this.mchntCountDis = mchntCountDis;
	}
	/**
	 * @return the settleAmtDis
	 */
	public BigDecimal getSettleAmtDis() {
		return settleAmtDis;
	}
	/**
	 * @param settleAmtDis the settleAmtDis to set
	 */
	public void setSettleAmtDis(BigDecimal settleAmtDis) {
		this.settleAmtDis = settleAmtDis;
	}
	/**
	 * @return the settleReservedAmtDis
	 */
	public BigDecimal getSettleReservedAmtDis() {
		return settleReservedAmtDis;
	}
	/**
	 * @param settleReservedAmtDis the settleReservedAmtDis to set
	 */
	public void setSettleReservedAmtDis(BigDecimal settleReservedAmtDis) {
		this.settleReservedAmtDis = settleReservedAmtDis;
	}
	/**
	 * @return the settleCupsFeeDis
	 */
	public BigDecimal getSettleCupsFeeDis() {
		return settleCupsFeeDis;
	}
	/**
	 * @param settleCupsFeeDis the settleCupsFeeDis to set
	 */
	public void setSettleCupsFeeDis(BigDecimal settleCupsFeeDis) {
		this.settleCupsFeeDis = settleCupsFeeDis;
	}
	/**
	 * @return the settleMchntFeeDis
	 */
	public BigDecimal getSettleMchntFeeDis() {
		return settleMchntFeeDis;
	}
	/**
	 * @param settleMchntFeeDis the settleMchntFeeDis to set
	 */
	public void setSettleMchntFeeDis(BigDecimal settleMchntFeeDis) {
		this.settleMchntFeeDis = settleMchntFeeDis;
	}
	/**
	 * @return the settleReservedFeeDis
	 */
	public BigDecimal getSettleReservedFeeDis() {
		return settleReservedFeeDis;
	}
	/**
	 * @param settleReservedFeeDis the settleReservedFeeDis to set
	 */
	public void setSettleReservedFeeDis(BigDecimal settleReservedFeeDis) {
		this.settleReservedFeeDis = settleReservedFeeDis;
	}
	/**
	 * @return the stlmDsp
	 */
	public String getStlmDsp() {
		return stlmDsp;
	}
	/**
	 * @param stlmDsp the stlmDsp to set
	 */
	public void setStlmDsp(String stlmDsp) {
		this.stlmDsp = stlmDsp;
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
	/**
	 * @return the recOprId
	 */
	public String getRecOprId() {
		return recOprId;
	}
	/**
	 * @param recOprId the recOprId to set
	 */
	public void setRecOprId(String recOprId) {
		this.recOprId = recOprId;
	}
	/**
	 * @return the recUpdUsr
	 */
	public String getRecUpdUsr() {
		return recUpdUsr;
	}
	/**
	 * @param recUpdUsr the recUpdUsr to set
	 */
	public void setRecUpdUsr(String recUpdUsr) {
		this.recUpdUsr = recUpdUsr;
	}
	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}
	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}
	/**
	 * @return the recUpdTs
	 */
	public String getRecUpdTs() {
		return recUpdTs;
	}
	/**
	 * @param recUpdTs the recUpdTs to set
	 */
	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}
	private static final long serialVersionUID = 1L;

	private TblInfileOptPK id;
	private String startDate;
	private String endDate;
	private BigDecimal settleAmt;
	private BigDecimal txnAmt;
	private BigDecimal reservedAmt1;
	private BigDecimal reservedAmt2;
	private BigDecimal settleMchntFee;
	private BigDecimal settleCupsFee;
	private BigDecimal reservedFee1;
	private BigDecimal reservedFee2;
	private String outMchntSettleFile;
	private String outMchntSettleFileSta;
	private String outCupsFeeFile;
	private String outCupsFeeFileSta;
	private String reservedFile1;
	private String reservedFile1Sta;
	private String reservedFile2;
	private String reservedFile2Sta;
	private String succMchntFile;
	private String failMchntFile;
	private String succCupsFeeFile;
	private String failCupsFeeFile;
	private String succReservedFile1;
	private String failReservedFile1;
	private String succReservedFile2;
	private String failReservedFile2;
	private String stlmFlag;
	private String stlmFlag2;
	private String mchntCount;
	private String mchntCountDis;
	private BigDecimal settleAmtDis;
	private BigDecimal settleReservedAmtDis;
	private BigDecimal settleCupsFeeDis;
	private BigDecimal settleMchntFeeDis;
	private BigDecimal settleReservedFeeDis;
	private String stlmDsp;
	private String misc1;
	private String recOprId;
	private String recUpdUsr;
	private String recCrtTs;
	private String recUpdTs;
	
}