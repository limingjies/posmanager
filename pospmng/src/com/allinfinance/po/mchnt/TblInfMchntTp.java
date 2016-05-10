package com.allinfinance.po.mchnt;

import java.io.Serializable;

public class TblInfMchntTp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// primary key
	private TblInfMchntTpPK id;

	// fields
	private java.lang.String mchntTpGrp;
	private java.lang.String descr;
	private java.lang.String recSt;
	private java.lang.String lastOperIn;
	private java.lang.String recUpdUsrId;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;
	private java.lang.String discCd;

	public TblInfMchntTpPK getId() {
		return id;
	}
	
	public void setId(TblInfMchntTpPK id) {
		this.id = id;
	}

	public java.lang.String getMchntTpGrp() {
		return mchntTpGrp;
	}

	public void setMchntTpGrp(java.lang.String mchntTpGrp) {
		this.mchntTpGrp = mchntTpGrp;
	}

	public java.lang.String getDescr() {
		return descr;
	}

	public void setDescr(java.lang.String descr) {
		this.descr = descr;
	}

	public java.lang.String getRecSt() {
		return recSt;
	}

	public void setRecSt(java.lang.String recSt) {
		this.recSt = recSt;
	}

	public java.lang.String getLastOperIn() {
		return lastOperIn;
	}

	public void setLastOperIn(java.lang.String lastOperIn) {
		this.lastOperIn = lastOperIn;
	}

	public java.lang.String getRecUpdUsrId() {
		return recUpdUsrId;
	}

	public void setRecUpdUsrId(java.lang.String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}

	public java.lang.String getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public java.lang.String getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public java.lang.String getDiscCd() {
		return discCd;
	}

	public void setDiscCd(java.lang.String discCd) {
		this.discCd = discCd;
	}	
}