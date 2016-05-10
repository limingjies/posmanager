package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRunRisk implements Serializable{

	// primary key
		private TblRunRiskPK id;

		// fields
		private java.lang.String riskType;
		private java.lang.String txnNum;
		private java.lang.String cardTp;
		private java.lang.String txnAmt;
		private java.lang.String txnCount;
		private java.lang.String resved;
		private java.lang.String resved1;
		private java.lang.String regTime;
		private java.lang.String updTime;
		private java.lang.String onFlag;
		public TblRunRisk() {
		}
		public TblRunRiskPK getId() {
			return id;
		}
		public void setId(TblRunRiskPK id) {
			this.id = id;
		}
		public java.lang.String getRiskType() {
			return riskType;
		}
		public void setRiskType(java.lang.String riskType) {
			this.riskType = riskType;
		}
		public java.lang.String getTxnNum() {
			return txnNum;
		}
		public void setTxnNum(java.lang.String txnNum) {
			this.txnNum = txnNum;
		}
		public java.lang.String getCardTp() {
			return cardTp;
		}
		public void setCardTp(java.lang.String cardTp) {
			this.cardTp = cardTp;
		}
		public java.lang.String getTxnAmt() {
			return txnAmt;
		}
		public void setTxnAmt(java.lang.String txnAmt) {
			this.txnAmt = txnAmt;
		}
		public java.lang.String getTxnCount() {
			return txnCount;
		}
		public void setTxnCount(java.lang.String txnCount) {
			this.txnCount = txnCount;
		}
		public java.lang.String getResved() {
			return resved;
		}
		public void setResved(java.lang.String resved) {
			this.resved = resved;
		}
		public java.lang.String getResved1() {
			return resved1;
		}
		public void setResved1(java.lang.String resved1) {
			this.resved1 = resved1;
		}
		public java.lang.String getRegTime() {
			return regTime;
		}
		public void setRegTime(java.lang.String regTime) {
			this.regTime = regTime;
		}
		public java.lang.String getUpdTime() {
			return updTime;
		}
		public void setUpdTime(java.lang.String updTime) {
			this.updTime = updTime;
		}
		public java.lang.String getOnFlag() {
			return onFlag;
		}
		public void setOnFlag(java.lang.String onFlag) {
			this.onFlag = onFlag;
		}
		
		
		
		
}
