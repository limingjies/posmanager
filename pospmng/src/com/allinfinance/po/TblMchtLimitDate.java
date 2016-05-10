package com.allinfinance.po;

import java.io.Serializable;

public class TblMchtLimitDate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mchtno;
	private String limitdate;
	private String status;
	private String reserved;
	private String crtopr;
	private String crttime;
	private String updopr;
	private String updtime;
	private String misc;
	private String misc1;

	public TblMchtLimitDate() {
	}

	public String getMchtno() {
		return mchtno;
	}

	public void setMchtno(String mchtno) {
		this.mchtno = mchtno;
	}

	public String getLimitdate() {
		return limitdate;
	}

	public void setLimitdate(String limitdate) {
		this.limitdate = limitdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getCrtopr() {
		return crtopr;
	}

	public void setCrtopr(String crtopr) {
		this.crtopr = crtopr;
	}

	public String getCrttime() {
		return crttime;
	}

	public void setCrttime(String crttime) {
		this.crttime = crttime;
	}

	public String getUpdopr() {
		return updopr;
	}

	public void setUpdopr(String updopr) {
		this.updopr = updopr;
	}

	public String getUpdtime() {
		return updtime;
	}

	public void setUpdtime(String updtime) {
		this.updtime = updtime;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public String getMisc1() {
		return misc1;
	}

	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}

}
