package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblParamInfo implements Serializable {
	
	private TblParamInfoPK id;
	private String paramType;
	private String paramPro;
	private String paramValue;
	private String descr;
	private String lstUpdTlr;
	private String lstUpdTime;
	private String createTime;
	
	public TblParamInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public TblParamInfo(TblParamInfoPK id, String paramType, String paramPro,
			String paramValue, String descr, String lstUpdTlr,
			String lstUpdTime, String createTime) {
		this.id = id;
		this.paramType = paramType;
		this.paramPro = paramPro;
		this.paramValue = paramValue;
		this.descr = descr;
		this.lstUpdTlr = lstUpdTlr;
		this.lstUpdTime = lstUpdTime;
		this.createTime = createTime;
	}

	public TblParamInfoPK getId() {
		return id;
	}
	public void setId(TblParamInfoPK id) {
		this.id = id;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getParamPro() {
		return paramPro;
	}
	public void setParamPro(String paramPro) {
		this.paramPro = paramPro;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
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
