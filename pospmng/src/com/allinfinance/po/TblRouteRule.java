package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRouteRule implements Serializable{

	
	private TblRouteRulePK tblRouteRulePK;
	
	private String priority;
	
	private String txnNum;
	private String cardTp;
	private String ruleCode;
	private String cardBin;
	private String mchtGroup;
	private String mcc;
	private String insIdCd;
	private String mchtArea;
	private String dateCtl;
	private String dateBeg;
	private String dateEnd;
	private String timeCtl;
	private String timeBeg;
	private String timeEnd;
	private String amtCtl;
	private String amtBeg;
	private String amtEnd;
	
	private String msc1;
	private String msc2;
	private String msc3;
	private String onFlag;
	/**
	 * 
	 */
	public TblRouteRule() {
		
	}
	
	//针对于商户路由批量配置(过时无效)
	public TblRouteRule(String accpId, String ruleId) {
		TblRouteRulePK tblRouteRulePK = new TblRouteRulePK();
		tblRouteRulePK.setBrhId("*");
		tblRouteRulePK.setAccpId(accpId);
		tblRouteRulePK.setRuleId(ruleId);
		this.tblRouteRulePK = tblRouteRulePK;
		this.priority = "1";
		this.txnNum = "*";
		this.cardTp = "*";
		this.cardBin = "*";
		this.mchtGroup = "*";
		this.mcc = "*";
		this.insIdCd = "*";
		this.mchtArea = "*";
		this.dateCtl = "*";
		this.dateBeg = " ";
		this.dateEnd = " ";
		this.timeCtl = "*";
		this.timeBeg = " ";
		this.timeEnd = " ";
		this.amtCtl = "*";
		this.amtBeg = " ";
		this.amtEnd = " ";
		
		this.msc1 = " ";
		this.msc2 = " ";
		this.msc3 = " ";
		this.onFlag = "1";
	}
	
	//针对于商户路由批量配置
	public TblRouteRule(
			String ruleCode,
			String priority,
			String brhId,
			String mchtGroup,
			String mcc,
			String mchtArea,
			String insIdCd,
			String cardTp,
			String cardBin,
			String txnNum,
			String amtCtl,
			String amtBeg,
			String amtEnd,
			String dateCtl,
			String dateBeg,
			String dateEnd,
			String timeCtl,
			String timeBeg,
			String timeEnd) {
		TblRouteRulePK tblRouteRulePK = new TblRouteRulePK();
		tblRouteRulePK.setBrhId(brhId);
		this.tblRouteRulePK = tblRouteRulePK;
		this.ruleCode = ruleCode;
		this.priority = priority;
		this.mchtGroup = mchtGroup;
		this.mcc = mcc;
		this.mchtArea = mchtArea;
		this.insIdCd = insIdCd;
		this.cardTp = cardTp;
		this.cardBin = cardBin;
		this.txnNum = txnNum;
		this.amtCtl = amtCtl;
		this.amtBeg = amtBeg;
		this.amtEnd = amtEnd;
		this.dateCtl = dateCtl;
		this.dateBeg = dateBeg;
		this.dateEnd = dateEnd;
		this.timeCtl = timeCtl;
		this.timeBeg = timeBeg;
		this.timeEnd = timeEnd;
		
		this.onFlag = "1";
	}
	/**
	 * @return the tblRouteRulePK
	 */
	public TblRouteRulePK getTblRouteRulePK() {
		return tblRouteRulePK;
	}
	/**
	 * @param tblRouteRulePK the tblRouteRulePK to set
	 */
	public void setTblRouteRulePK(TblRouteRulePK tblRouteRulePK) {
		this.tblRouteRulePK = tblRouteRulePK;
	}
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return the txnNum
	 */
	public String getTxnNum() {
		return txnNum;
	}
	/**
	 * @param txnNum the txnNum to set
	 */
	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}
	/**
	 * @return the cardTp
	 */
	public String getCardTp() {
		return cardTp;
	}
	/**
	 * @param cardTp the cardTp to set
	 */
	public void setCardTp(String cardTp) {
		this.cardTp = cardTp;
	}
	/**
	 * @return the cardBin
	 */
	public String getCardBin() {
		return cardBin;
	}
	/**
	 * @param cardBin the cardBin to set
	 */
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	/**
	 * @return the mcc
	 */
	public String getMcc() {
		return mcc;
	}
	/**
	 * @param mcc the mcc to set
	 */
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	/**
	 * @return the insIdCd
	 */
	public String getInsIdCd() {
		return insIdCd;
	}
	/**
	 * @param insIdCd the insIdCd to set
	 */
	public void setInsIdCd(String insIdCd) {
		this.insIdCd = insIdCd;
	}
	/**
	 * @return the mchtArea
	 */
	public String getMchtArea() {
		return mchtArea;
	}
	/**
	 * @param mchtArea the mchtArea to set
	 */
	public void setMchtArea(String mchtArea) {
		this.mchtArea = mchtArea;
	}
	/**
	 * @return the dateCtl
	 */
	public String getDateCtl() {
		return dateCtl;
	}
	/**
	 * @param dateCtl the dateCtl to set
	 */
	public void setDateCtl(String dateCtl) {
		this.dateCtl = dateCtl;
	}
	/**
	 * @return the dateBeg
	 */
	public String getDateBeg() {
		return dateBeg;
	}
	/**
	 * @param dateBeg the dateBeg to set
	 */
	public void setDateBeg(String dateBeg) {
		this.dateBeg = dateBeg;
	}
	/**
	 * @return the dateEnd
	 */
	public String getDateEnd() {
		return dateEnd;
	}
	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	/**
	 * @return the timeCtl
	 */
	public String getTimeCtl() {
		return timeCtl;
	}
	/**
	 * @param timeCtl the timeCtl to set
	 */
	public void setTimeCtl(String timeCtl) {
		this.timeCtl = timeCtl;
	}
	/**
	 * @return the timeBeg
	 */
	public String getTimeBeg() {
		return timeBeg;
	}
	/**
	 * @param timeBeg the timeBeg to set
	 */
	public void setTimeBeg(String timeBeg) {
		this.timeBeg = timeBeg;
	}
	/**
	 * @return the timeEnd
	 */
	public String getTimeEnd() {
		return timeEnd;
	}
	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	/**
	 * @return the amtCtl
	 */
	public String getAmtCtl() {
		return amtCtl;
	}
	/**
	 * @param amtCtl the amtCtl to set
	 */
	public void setAmtCtl(String amtCtl) {
		this.amtCtl = amtCtl;
	}
	/**
	 * @return the amtBeg
	 */
	public String getAmtBeg() {
		return amtBeg;
	}
	/**
	 * @param amtBeg the amtBeg to set
	 */
	public void setAmtBeg(String amtBeg) {
		this.amtBeg = amtBeg;
	}
	/**
	 * @return the amtEnd
	 */
	public String getAmtEnd() {
		return amtEnd;
	}
	/**
	 * @param amtEnd the amtEnd to set
	 */
	public void setAmtEnd(String amtEnd) {
		this.amtEnd = amtEnd;
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
	/**
	 * @return the onFlag
	 */
	public String getOnFlag() {
		return onFlag;
	}
	/**
	 * @param onFlag the onFlag to set
	 */
	public void setOnFlag(String onFlag) {
		this.onFlag = onFlag;
	}
	/**
	 * @return the ruleCode
	 */
	public String getRuleCode() {
		return ruleCode;
	}
	/**
	 * @param ruleCode the ruleCode to set
	 */
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	/**
	 * @return the mchtGroup
	 */
	public String getMchtGroup() {
		return mchtGroup;
	}
	/**
	 * @param mchtGroup the mchtGroup to set
	 */
	public void setMchtGroup(String mchtGroup) {
		this.mchtGroup = mchtGroup;
	}

	
	
	
}
