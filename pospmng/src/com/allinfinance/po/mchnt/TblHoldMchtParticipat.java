package com.allinfinance.po.mchnt;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class TblHoldMchtParticipat implements Serializable{

	private TblHoldMchtParticipatPK id;
	
	private String start_date;
	private String end_date;
	private String ACT_CONTENT;
	private BigDecimal ACT_FEE;
	private String DEV_NUM;
	private String DEV_PROD;
	
	
	
	
	

	public TblHoldMchtParticipat(TblHoldMchtParticipatPK id, String start_date,
			String end_date, String act_content, BigDecimal act_fee,
			String dev_num, String dev_prod) {
		this.id = id;
		this.start_date = start_date;
		this.end_date = end_date;
		ACT_CONTENT = act_content;
		ACT_FEE = act_fee;
		DEV_NUM = dev_num;
		DEV_PROD = dev_prod;
	}

	public TblHoldMchtParticipat() {

	}

	public TblHoldMchtParticipatPK getId() {
		return id;
	}

	public void setId(TblHoldMchtParticipatPK id) {
		this.id = id;
	}

	public String getACT_CONTENT() {
		return ACT_CONTENT;
	}

	public void setACT_CONTENT(String act_content) {
		ACT_CONTENT = act_content;
	}

	public BigDecimal getACT_FEE() {
		return ACT_FEE;
	}

	public void setACT_FEE(BigDecimal act_fee) {
		ACT_FEE = act_fee;
	}

	public String getDEV_NUM() {
		return DEV_NUM;
	}

	public void setDEV_NUM(String dev_num) {
		DEV_NUM = dev_num;
	}

	public String getDEV_PROD() {
		return DEV_PROD;
	}

	public void setDEV_PROD(String dev_prod) {
		DEV_PROD = dev_prod;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	
	

	
}
