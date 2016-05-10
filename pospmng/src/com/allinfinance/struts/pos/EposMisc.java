package com.allinfinance.struts.pos;

public class EposMisc {

	String version;
	String loginDate;
	String preLoginDate;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getPreLoginDate() {
		return preLoginDate;
	}
	public void setPreLoginDate(String preLoginDate) {
		this.preLoginDate = preLoginDate;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if(version != null)
			sb.append(version);
		if(loginDate != null)
			sb.append(loginDate);
		if(preLoginDate != null)
			sb.append(preLoginDate);
		return sb.toString();
	}
	
	public EposMisc(String value){
		if(value!=null && value.trim().length()>=20){
			this.version = value.substring(0,3);
			this.loginDate = value.substring(4,11);
			this.preLoginDate = value.substring(12,19);	
		}
	}
	
}
