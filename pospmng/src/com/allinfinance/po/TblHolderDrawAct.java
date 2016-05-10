package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblHolderDrawAct implements Serializable{
	
	private String actNo;
	private String actName;
	private String startDate;
	private String endDate;
	private String drawAmt ;
	
	private String agrade;
	private String arate;
	private String anum;
	private String anumLeft;
	private String adesc;

	private String bgrade;
	private String brate;
	private String bnum;
	private String bnumLeft;
	private String bdesc;
	
	private String cgrade;
	private String crate;
	private String cnum;
	private String cnumLeft;
	private String cdesc;
	
	private String dgrade;
	private String drate;
	private String dnum;
	private String dnumLeft;
	private String ddesc;
	
	private String egrade;
	private String erate;
	private String enumb;
	private String enumLeft;
	private String edesc;
	
	public TblHolderDrawAct(String actNo, String actName, String startDate,
			String endDate, String drawAmt, String agrade, String arate,
			String anum, String anumLeft, String adesc, String bgrade,
			String brate, String bnum, String bnumLeft, String bdesc,
			String cgrade, String crate, String cnum, String cnumLeft,
			String cdesc, String dgrade, String drate, String dnum,
			String dnumLeft, String ddesc, String egrade, String erate,
			String enumb, String enumLeft, String edesc) {
		this.actNo = actNo;
		this.actName = actName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.drawAmt = drawAmt;
		this.agrade = agrade;
		this.arate = arate;
		this.anum = anum;
		this.anumLeft = anumLeft;
		this.adesc = adesc;
		this.bgrade = bgrade;
		this.brate = brate;
		this.bnum = bnum;
		this.bnumLeft = bnumLeft;
		this.bdesc = bdesc;
		this.cgrade = cgrade;
		this.crate = crate;
		this.cnum = cnum;
		this.cnumLeft = cnumLeft;
		this.cdesc = cdesc;
		this.dgrade = dgrade;
		this.drate = drate;
		this.dnum = dnum;
		this.dnumLeft = dnumLeft;
		this.ddesc = ddesc;
		this.egrade = egrade;
		this.erate = erate;
		this.enumb = enumb;
		this.enumLeft = enumLeft;
		this.edesc = edesc;
	}

	public TblHolderDrawAct() {
		
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDrawAmt() {
		return drawAmt;
	}

	public void setDrawAmt(String drawAmt) {
		this.drawAmt = drawAmt;
	}

	public String getAgrade() {
		return agrade;
	}

	public void setAgrade(String agrade) {
		this.agrade = agrade;
	}

	public String getArate() {
		return arate;
	}

	public void setArate(String arate) {
		this.arate = arate;
	}

	public String getAnum() {
		return anum;
	}

	public void setAnum(String anum) {
		this.anum = anum;
	}

	public String getAnumLeft() {
		return anumLeft;
	}

	public void setAnumLeft(String anumLeft) {
		this.anumLeft = anumLeft;
	}

	public String getAdesc() {
		return adesc;
	}

	public void setAdesc(String adesc) {
		this.adesc = adesc;
	}

	public String getBgrade() {
		return bgrade;
	}

	public void setBgrade(String bgrade) {
		this.bgrade = bgrade;
	}

	public String getBrate() {
		return brate;
	}

	public void setBrate(String brate) {
		this.brate = brate;
	}

	public String getBnum() {
		return bnum;
	}

	public void setBnum(String bnum) {
		this.bnum = bnum;
	}

	public String getBnumLeft() {
		return bnumLeft;
	}

	public void setBnumLeft(String bnumLeft) {
		this.bnumLeft = bnumLeft;
	}

	public String getBdesc() {
		return bdesc;
	}

	public void setBdesc(String bdesc) {
		this.bdesc = bdesc;
	}

	public String getCgrade() {
		return cgrade;
	}

	public void setCgrade(String cgrade) {
		this.cgrade = cgrade;
	}

	public String getCrate() {
		return crate;
	}

	public void setCrate(String crate) {
		this.crate = crate;
	}

	public String getCnum() {
		return cnum;
	}

	public void setCnum(String cnum) {
		this.cnum = cnum;
	}

	public String getCnumLeft() {
		return cnumLeft;
	}

	public void setCnumLeft(String cnumLeft) {
		this.cnumLeft = cnumLeft;
	}

	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public String getDgrade() {
		return dgrade;
	}

	public void setDgrade(String dgrade) {
		this.dgrade = dgrade;
	}

	public String getDrate() {
		return drate;
	}

	public void setDrate(String drate) {
		this.drate = drate;
	}

	public String getDnum() {
		return dnum;
	}

	public void setDnum(String dnum) {
		this.dnum = dnum;
	}

	public String getDnumLeft() {
		return dnumLeft;
	}

	public void setDnumLeft(String dnumLeft) {
		this.dnumLeft = dnumLeft;
	}

	public String getDdesc() {
		return ddesc;
	}

	public void setDdesc(String ddesc) {
		this.ddesc = ddesc;
	}

	public String getEgrade() {
		return egrade;
	}

	public void setEgrade(String egrade) {
		this.egrade = egrade;
	}

	public String getErate() {
		return erate;
	}

	public void setErate(String erate) {
		this.erate = erate;
	}

	public String getEnumb() {
		return enumb;
	}

	public void setEnumb(String enumb) {
		this.enumb = enumb;
	}

	public String getEnumLeft() {
		return enumLeft;
	}

	public void setEnumLeft(String enumLeft) {
		this.enumLeft = enumLeft;
	}

	public String getEdesc() {
		return edesc;
	}

	public void setEdesc(String edesc) {
		this.edesc = edesc;
	}
	
	
	


	

	
	
	
}
