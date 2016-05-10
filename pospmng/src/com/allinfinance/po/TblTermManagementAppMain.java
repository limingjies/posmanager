package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblTermManagementAppMain implements Serializable{

	private String appId;
	private String stat;
	private String brhId;
	private String misc;
	private String appOprId;
	private String appDate;
	private String backOprId;
	private String backDate;
	
	
	public TblTermManagementAppMain(String appId, String stat, String brhId,
			String misc, String appOprId, String appDate, String backOprId,
			String backDate) {
		this.appId = appId;
		this.stat = stat;
		this.brhId = brhId;
		this.misc = misc;
		this.appOprId = appOprId;
		this.appDate = appDate;
		this.backOprId = backOprId;
		this.backDate = backDate;
	}


	public TblTermManagementAppMain() {
	}


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getStat() {
		return stat;
	}


	public void setStat(String stat) {
		this.stat = stat;
	}


	public String getBrhId() {
		return brhId;
	}


	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}


	public String getMisc() {
		return misc;
	}


	public void setMisc(String misc) {
		this.misc = misc;
	}


	public String getAppOprId() {
		return appOprId;
	}


	public void setAppOprId(String appOprId) {
		this.appOprId = appOprId;
	}


	public String getAppDate() {
		return appDate;
	}


	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}


	public String getBackOprId() {
		return backOprId;
	}


	public void setBackOprId(String backOprId) {
		this.backOprId = backOprId;
	}


	public String getBackDate() {
		return backDate;
	}


	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	
	
	
	
}
