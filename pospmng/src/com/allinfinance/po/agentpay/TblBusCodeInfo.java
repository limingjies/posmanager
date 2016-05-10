package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblBusCodeInfo implements Serializable {

	/**
	 * Tbl_Rcv_Pack表映射对象
	 */
	private static final long serialVersionUID = 1L;
	private String busCode;
	private String usage;
	private String typeNo;
	private String name;
	private String seq;
	
	public TblBusCodeInfo() {
		// TODO Auto-generated constructor stub
	}
	public TblBusCodeInfo(String busCode, String usage, String typeNo,
			String name, String seq) {
		this.busCode = busCode;
		this.usage = usage;
		this.typeNo = typeNo;
		this.name = name;
		this.seq = seq;
	}
	public String getBusCode() {
		return busCode;
	}
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}
