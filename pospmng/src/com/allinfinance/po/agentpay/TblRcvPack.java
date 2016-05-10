package com.allinfinance.po.agentpay;

import java.io.Serializable;
import java.math.BigDecimal;

public class TblRcvPack implements Serializable {

	/**
	 * Tbl_Rcv_Pack表映射对象
	 */
	private static final long serialVersionUID = 1L;
	// 主键
	private String batchId;
	private String entDate;
	private String busType;
	private String mchtNo;
	private String rcvDate;
	private String rcvBatchId;
	private String procStat;
	private Integer totCnt;
	private BigDecimal totAmt;
	private String feeCode;
	private String curCd;
	private String rspDate;
	private Integer succCnt;
	private BigDecimal succAmt;
	private Integer failCnt;
	private BigDecimal failAmt;
	private String ver;
	private String rcvFileName;
	private String regDate;
	private String regTime;
	private String batSsn;
	private String lockFlag;
	private String auditFlag;
	private String preAuditTlr;
	private String checkTlr;
	private String finalAuditTlr;
	private String flag1;
	private String flag2;
	private String flag3;
	private String misc1;
	private String misc2;
	private String misc3;
	private String lstUpdTlr;
	private String lstUpdTime;
	private String createTime;

	public TblRcvPack() {

	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getEntDate() {
		return entDate;
	}

	public void setEntDate(String entDate) {
		this.entDate = entDate;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getRcvDate() {
		return rcvDate;
	}

	public void setRcvDate(String rcvDate) {
		this.rcvDate = rcvDate;
	}

	public String getRcvBatchId() {
		return rcvBatchId;
	}

	public void setRcvBatchId(String rcvBatchId) {
		this.rcvBatchId = rcvBatchId;
	}

	public String getProcStat() {
		return procStat;
	}

	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}

	public Integer getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(Integer totCnt) {
		this.totCnt = totCnt;
	}

	public BigDecimal getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(BigDecimal totAmt) {
		this.totAmt = totAmt;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getCurCd() {
		return curCd;
	}

	public void setCurCd(String curCd) {
		this.curCd = curCd;
	}

	public String getRspDate() {
		return rspDate;
	}

	public void setRspDate(String rspDate) {
		this.rspDate = rspDate;
	}

	public Integer getSuccCnt() {
		return succCnt;
	}

	public void setSuccCnt(Integer succCnt) {
		this.succCnt = succCnt;
	}

	public BigDecimal getSuccAmt() {
		return succAmt;
	}

	public void setSuccAmt(BigDecimal succAmt) {
		this.succAmt = succAmt;
	}

	public Integer getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	public BigDecimal getFailAmt() {
		return failAmt;
	}

	public void setFailAmt(BigDecimal failAmt) {
		this.failAmt = failAmt;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getRcvFileName() {
		return rcvFileName;
	}

	public void setRcvFileName(String rcvFileName) {
		this.rcvFileName = rcvFileName;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getBatSsn() {
		return batSsn;
	}

	public void setBatSsn(String batSsn) {
		this.batSsn = batSsn;
	}

	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getPreAuditTlr() {
		return preAuditTlr;
	}

	public void setPreAuditTlr(String preAuditTlr) {
		this.preAuditTlr = preAuditTlr;
	}

	public String getCheckTlr() {
		return checkTlr;
	}

	public void setCheckTlr(String checkTlr) {
		this.checkTlr = checkTlr;
	}

	public String getFinalAuditTlr() {
		return finalAuditTlr;
	}

	public void setFinalAuditTlr(String finalAuditTlr) {
		this.finalAuditTlr = finalAuditTlr;
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
