package com.allinfinance.po.settle;

import java.io.Serializable;

/**
 * 差错入账调整记录表
 * 
 * @author luhq
 *
 */
public class TblInstBalanceAjust implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1558189174403188265L;

	private TblInstBalanceAjustPK tblInstBalanceAjustPK;
	private String dateSettlmt;     
	private String keyInst;
	private String mchtId;           
	private String mchtType;         
	private String acctCurr;         
	private String settlDate;        
	private String brhId;            
	private String transDate;
	private String transTime;
	private String sysSeqNum;       
	private String transType;    
	private String transRef;    
	private String transCode;        
	private String subTransCode;    
	private String recUpdUsrId;    
	private String recCrtTs;
	private String instCode;
	private String vcTranNo;
	private String solveMode;
	private String solveMsg;
	
	
	public String getDateSettlmt() {
		return dateSettlmt;
	}
	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}
	public String getKeyInst() {
		return keyInst;
	}
	public void setKeyInst(String keyInst) {
		this.keyInst = keyInst;
	}
	public String getMchtId() {
		return mchtId;
	}
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}
	public String getMchtType() {
		return mchtType;
	}
	public void setMchtType(String mchtType) {
		this.mchtType = mchtType;
	}
	public String getAcctCurr() {
		return acctCurr;
	}
	public void setAcctCurr(String acctCurr) {
		this.acctCurr = acctCurr;
	}
	public String getSettlDate() {
		return settlDate;
	}
	public void setSettlDate(String settlDate) {
		this.settlDate = settlDate;
	}
	public String getBrhId() {
		return brhId;
	}
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getSysSeqNum() {
		return sysSeqNum;
	}
	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getSubTransCode() {
		return subTransCode;
	}
	public void setSubTransCode(String subTransCode) {
		this.subTransCode = subTransCode;
	}
	public String getRecUpdUsrId() {
		return recUpdUsrId;
	}
	public void setRecUpdUsrId(String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}
	public String getRecCrtTs() {
		return recCrtTs;
	}
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}
	public TblInstBalanceAjustPK getTblInstBalanceAjustPK() {
		return tblInstBalanceAjustPK;
	}
	public void setTblInstBalanceAjustPK(TblInstBalanceAjustPK tblInstBalanceAjustPK) {
		this.tblInstBalanceAjustPK = tblInstBalanceAjustPK;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransRef() {
		return transRef;
	}
	public void setTransRef(String transRef) {
		this.transRef = transRef;
	}
	public String getInstCode() {
		return instCode;
	}
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	public String getVcTranNo() {
		return vcTranNo;
	}
	public void setVcTranNo(String vcTranNo) {
		this.vcTranNo = vcTranNo;
	}
	public String getSolveMode() {
		return solveMode;
	}
	public void setSolveMode(String solveMode) {
		this.solveMode = solveMode;
	}
	public String getSolveMsg() {
		return solveMsg;
	}
	public void setSolveMsg(String solveMsg) {
		this.solveMsg = solveMsg;
	}
	
}
