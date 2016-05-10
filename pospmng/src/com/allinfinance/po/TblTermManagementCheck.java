package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblTermManagementCheck implements Serializable{

	
	private TblTermManagementCheckPK id;
	private String manufaturer;
	private String terminalType;
	private String termType;
	private String amount;
	private String accAmount;
	private String stat;
	private String mic;
	private String misc2;
	
	public TblTermManagementCheck(TblTermManagementCheckPK id,String manufaturer,
			String terminalType, String termType, String amount,
			String accAmount, String stat,String mic,
			String misc2) {
		this.id = id;
		this.manufaturer = manufaturer;
		this.terminalType = terminalType;
		this.termType = termType;
		this.amount = amount;
		this.accAmount = accAmount;
		this.stat = stat;
		this.mic = mic;
		this.misc2 = misc2;
	}

	public TblTermManagementCheck() {

	}


	public TblTermManagementCheckPK getId() {
		return id;
	}

	public void setId(TblTermManagementCheckPK id) {
		this.id = id;
	}

	public String getManufaturer() {
		return manufaturer;
	}

	public void setManufaturer(String manufaturer) {
		this.manufaturer = manufaturer;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccAmount() {
		return accAmount;
	}

	public void setAccAmount(String accAmount) {
		this.accAmount = accAmount;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}


	public String getMic() {
		return mic;
	}

	public void setMic(String mic) {
		this.mic = mic;
	}

	public String getMisc2() {
		return misc2;
	}

	public void setMisc2(String misc2) {
		this.misc2 = misc2;
	}

	
	
}
