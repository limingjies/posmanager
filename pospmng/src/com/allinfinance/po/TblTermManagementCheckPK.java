package com.allinfinance.po;
import java.io.Serializable;


@SuppressWarnings("serial")
public class TblTermManagementCheckPK implements Serializable{
	
	private String appId;
	private String sonAppId;
	
	public TblTermManagementCheckPK(String appId, String sonAppId) {
		this.appId = appId;
		this.sonAppId = sonAppId;
	}

	public TblTermManagementCheckPK() {
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSonAppId() {
		return sonAppId;
	}

	public void setSonAppId(String sonAppId) {
		this.sonAppId = sonAppId;
	}
	
	
}