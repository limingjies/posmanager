package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRuleMchtRel implements Serializable{

	/*private String ruleCode;
	private String firstMchtCd;*/
	private TblRuleMchtRelPK tblRuleMchtRelPK;
	private String srvId;
	
	private String relDesc;
	private String msc1;
	private String msc2;
	private String msc3;
	
	
	/**
	 * 
	 */
	public TblRuleMchtRel() {
//		super();
	}


	

	public TblRuleMchtRelPK getTblRuleMchtRelPK() {
		return tblRuleMchtRelPK;
	}




	public void setTblRuleMchtRelPK(TblRuleMchtRelPK tblRuleMchtRelPK) {
		this.tblRuleMchtRelPK = tblRuleMchtRelPK;
	}




	/**
	 * @return the srvId
	 */
	public String getSrvId() {
		return srvId;
	}


	/**
	 * @param srvId the srvId to set
	 */
	public void setSrvId(String srvId) {
		this.srvId = srvId;
	}


	/**
	 * @return the relDesc
	 */
	public String getRelDesc() {
		return relDesc;
	}


	/**
	 * @param relDesc the relDesc to set
	 */
	public void setRelDesc(String relDesc) {
		this.relDesc = relDesc;
	}


	/**
	 * @return the msc1
	 */
	public String getMsc1() {
		return msc1;
	}


	/**
	 * @param msc1 the msc1 to set
	 */
	public void setMsc1(String msc1) {
		this.msc1 = msc1;
	}


	/**
	 * @return the msc2
	 */
	public String getMsc2() {
		return msc2;
	}


	/**
	 * @param msc2 the msc2 to set
	 */
	public void setMsc2(String msc2) {
		this.msc2 = msc2;
	}


	/**
	 * @return the msc3
	 */
	public String getMsc3() {
		return msc3;
	}


	/**
	 * @param msc3 the msc3 to set
	 */
	public void setMsc3(String msc3) {
		this.msc3 = msc3;
	}
	
	
}
