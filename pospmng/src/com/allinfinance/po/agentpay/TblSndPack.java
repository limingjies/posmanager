package com.allinfinance.po.agentpay;

import java.io.Serializable;
import java.math.BigDecimal;

public class TblSndPack implements Serializable {

	/**
	 * Tbl_Snd_Pack表映射对象
	 */
	private static final long serialVersionUID = 1L;
	// 主键
	private String batchId;
	private String entDate;
	private String mchtNo;
	private String busType;
	private String comCode;
	private String fileBatchId;
	private String bankCode;
	private String feeCode;
	private String procStat;
	private Integer totCnt;
	private BigDecimal totAmt;
	private String rspDate;
	private Integer succCnt;
	private BigDecimal succAmt;
	private Integer failCnt;
	private BigDecimal failAmt;
	private String busCode;
	private String tranDate;
	private String curCd;
	private String txnFileName;
	private String fileName;
	private String agentAcctNo;
	private String agentAcctName;
	private String rNameFlag;
	private String rMchtNo;
	private String prodCode;
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
	private String misc1;  //存放拒绝原因
	private String misc2;
	private String misc3;
	private String lstUpdTlr;
	private String lstUpdTime;
	private String createTime;

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

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getFileBatchId() {
		return fileBatchId;
	}

	public void setFileBatchId(String fileBatchId) {
		this.fileBatchId = fileBatchId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
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

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getCurCd() {
		return curCd;
	}

	public void setCurCd(String curCd) {
		this.curCd = curCd;
	}

	public String getTxnFileName() {
		return txnFileName;
	}

	public void setTxnFileName(String txnFileName) {
		this.txnFileName = txnFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAgentAcctNo() {
		return agentAcctNo;
	}

	public void setAgentAcctNo(String agentAcctNo) {
		this.agentAcctNo = agentAcctNo;
	}

	public String getAgentAcctName() {
		return agentAcctName;
	}

	public void setAgentAcctName(String agentAcctName) {
		this.agentAcctName = agentAcctName;
	}

	public String getrNameFlag() {
		return rNameFlag;
	}

	public void setrNameFlag(String rNameFlag) {
		this.rNameFlag = rNameFlag;
	}

	public String getrMchtNo() {
		return rMchtNo;
	}

	public void setrMchtNo(String rMchtNo) {
		this.rMchtNo = rMchtNo;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
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
