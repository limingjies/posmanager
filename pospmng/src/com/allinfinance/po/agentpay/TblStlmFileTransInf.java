package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblStlmFileTransInf implements Serializable {

	/**
	 * Tbl_Rcv_Pack表映射对象
	 */
	private static final long serialVersionUID = 1L;
	private TblStlmFileTransInfPK id;
	private String fileName;
	private String fileStat;
	private String failDesc;
	private String lstUpdTlr;
	private String lstUpdTime;
	private String createTime;

	public TblStlmFileTransInf() {

	}

	public TblStlmFileTransInfPK getId() {
		return id;
	}

	public void setId(TblStlmFileTransInfPK id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileStat() {
		return fileStat;
	}

	public void setFileStat(String fileStat) {
		this.fileStat = fileStat;
	}

	public String getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
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
