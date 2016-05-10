package com.allinfinance.po.mchnt;

import java.io.Serializable;

public class TblMchtCupInf implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String MCHT_NO;
	private String ACQ_INST_ID;
	private String ACQ_INST_CD;
	private String MCHT_NM;
	private String MCHT_SHORT_CN;
	private String STLM_INS_ID;
	private String MCHT_TYPE;
	private String STAT;
	private String ACQ_AREA_CD;
	private String MCHNT_TP_GRP;
	private String MCC;
	private String SETTLE_AREA_CD;
	private String LICENCE_NO;
	private String CONTACT_ADDR;
	private String MCHT_PERSON;
	private String COMM_TEL;
	private String MANAGER;
	private String IDENTITY_NO;
	private String ADDR;
	private String LICENCE_STAT;
	private String MCHT_STLM_ACCT;
	private String MCHT_ACCT_NM;
	private String SETTLE_BANK_NM;
	private String SETTLE_BANK_NO;
	private String APPLY_DATE;
	
	private String MISC1;
	private String MISC2;
	private String MISC3;
	private String MISC4;
	private String MISC5;
	private String MISC6;
	private String MISC7;
	private String MISC8;
	private String MISC9;
	private String MISC10;
	
	
	public TblMchtCupInf() {
	}


	

	public TblMchtCupInf(String mcht_no, String acq_inst_id,
			String acq_inst_cd, String mcht_nm, String mcht_short_cn,
			String stlm_ins_id, String mcht_type, String stat,
			String acq_area_cd, String mchnt_tp_grp, String mcc,
			String settle_area_cd, String licence_no, String contact_addr,
			String mcht_person, String comm_tel, String manager,
			String identity_no, String addr, String licence_stat,
			String mcht_stlm_acct, String mcht_acct_nm, String settle_bank_nm,
			String settle_bank_no, String apply_date, String misc1,
			String misc2, String misc3, String misc4, String misc5,
			String misc6, String misc7, String misc8, String misc9,
			String misc10) {
		MCHT_NO = mcht_no;
		ACQ_INST_ID = acq_inst_id;
		ACQ_INST_CD = acq_inst_cd;
		MCHT_NM = mcht_nm;
		MCHT_SHORT_CN = mcht_short_cn;
		STLM_INS_ID = stlm_ins_id;
		MCHT_TYPE = mcht_type;
		STAT = stat;
		ACQ_AREA_CD = acq_area_cd;
		MCHNT_TP_GRP = mchnt_tp_grp;
		MCC = mcc;
		SETTLE_AREA_CD = settle_area_cd;
		LICENCE_NO = licence_no;
		CONTACT_ADDR = contact_addr;
		MCHT_PERSON = mcht_person;
		COMM_TEL = comm_tel;
		MANAGER = manager;
		IDENTITY_NO = identity_no;
		ADDR = addr;
		LICENCE_STAT = licence_stat;
		MCHT_STLM_ACCT = mcht_stlm_acct;
		MCHT_ACCT_NM = mcht_acct_nm;
		SETTLE_BANK_NM = settle_bank_nm;
		SETTLE_BANK_NO = settle_bank_no;
		APPLY_DATE = apply_date;
		MISC1 = misc1;
		MISC2 = misc2;
		MISC3 = misc3;
		MISC4 = misc4;
		MISC5 = misc5;
		MISC6 = misc6;
		MISC7 = misc7;
		MISC8 = misc8;
		MISC9 = misc9;
		MISC10 = misc10;
	}



	

	public String getMISC1() {
		return MISC1;
	}




	public void setMISC1(String misc1) {
		MISC1 = misc1;
	}




	public String getMISC2() {
		return MISC2;
	}




	public void setMISC2(String misc2) {
		MISC2 = misc2;
	}




	public String getMISC3() {
		return MISC3;
	}




	public void setMISC3(String misc3) {
		MISC3 = misc3;
	}




	public String getMISC4() {
		return MISC4;
	}




	public void setMISC4(String misc4) {
		MISC4 = misc4;
	}




	public String getMISC5() {
		return MISC5;
	}




	public void setMISC5(String misc5) {
		MISC5 = misc5;
	}




	public String getMISC6() {
		return MISC6;
	}




	public void setMISC6(String misc6) {
		MISC6 = misc6;
	}




	public String getMISC7() {
		return MISC7;
	}




	public void setMISC7(String misc7) {
		MISC7 = misc7;
	}




	public String getMISC8() {
		return MISC8;
	}




	public void setMISC8(String misc8) {
		MISC8 = misc8;
	}




	public String getMISC9() {
		return MISC9;
	}




	public void setMISC9(String misc9) {
		MISC9 = misc9;
	}




	public String getMISC10() {
		return MISC10;
	}




	public void setMISC10(String misc10) {
		MISC10 = misc10;
	}


	



	public String getMCHT_NO() {
		return MCHT_NO;
	}




	public void setMCHT_NO(String mcht_no) {
		MCHT_NO = mcht_no;
	}




	public String getACQ_INST_ID() {
		return ACQ_INST_ID;
	}


	public void setACQ_INST_ID(String acq_inst_id) {
		ACQ_INST_ID = acq_inst_id;
	}


	public String getACQ_INST_CD() {
		return ACQ_INST_CD;
	}


	public void setACQ_INST_CD(String acq_inst_cd) {
		ACQ_INST_CD = acq_inst_cd;
	}


	public String getMCHT_NM() {
		return MCHT_NM;
	}


	public void setMCHT_NM(String mcht_nm) {
		MCHT_NM = mcht_nm;
	}


	public String getMCHT_SHORT_CN() {
		return MCHT_SHORT_CN;
	}


	public void setMCHT_SHORT_CN(String mcht_short_cn) {
		MCHT_SHORT_CN = mcht_short_cn;
	}


	public String getSTLM_INS_ID() {
		return STLM_INS_ID;
	}


	public void setSTLM_INS_ID(String stlm_ins_id) {
		STLM_INS_ID = stlm_ins_id;
	}


	public String getMCHT_TYPE() {
		return MCHT_TYPE;
	}


	public void setMCHT_TYPE(String mcht_type) {
		MCHT_TYPE = mcht_type;
	}


	public String getSTAT() {
		return STAT;
	}


	public void setSTAT(String stat) {
		STAT = stat;
	}


	public String getACQ_AREA_CD() {
		return ACQ_AREA_CD;
	}


	public void setACQ_AREA_CD(String acq_area_cd) {
		ACQ_AREA_CD = acq_area_cd;
	}


	public String getMCHNT_TP_GRP() {
		return MCHNT_TP_GRP;
	}


	public void setMCHNT_TP_GRP(String mchnt_tp_grp) {
		MCHNT_TP_GRP = mchnt_tp_grp;
	}


	public String getMCC() {
		return MCC;
	}


	public void setMCC(String mcc) {
		MCC = mcc;
	}


	public String getSETTLE_AREA_CD() {
		return SETTLE_AREA_CD;
	}


	public void setSETTLE_AREA_CD(String settle_area_cd) {
		SETTLE_AREA_CD = settle_area_cd;
	}


	public String getLICENCE_NO() {
		return LICENCE_NO;
	}


	public void setLICENCE_NO(String licence_no) {
		LICENCE_NO = licence_no;
	}


	public String getCONTACT_ADDR() {
		return CONTACT_ADDR;
	}


	public void setCONTACT_ADDR(String contact_addr) {
		CONTACT_ADDR = contact_addr;
	}


	public String getMCHT_PERSON() {
		return MCHT_PERSON;
	}


	public void setMCHT_PERSON(String mcht_person) {
		MCHT_PERSON = mcht_person;
	}


	public String getCOMM_TEL() {
		return COMM_TEL;
	}


	public void setCOMM_TEL(String comm_tel) {
		COMM_TEL = comm_tel;
	}


	public String getMANAGER() {
		return MANAGER;
	}


	public void setMANAGER(String manager) {
		MANAGER = manager;
	}


	public String getIDENTITY_NO() {
		return IDENTITY_NO;
	}


	public void setIDENTITY_NO(String identity_no) {
		IDENTITY_NO = identity_no;
	}


	public String getADDR() {
		return ADDR;
	}


	public void setADDR(String addr) {
		ADDR = addr;
	}


	public String getLICENCE_STAT() {
		return LICENCE_STAT;
	}


	public void setLICENCE_STAT(String licence_stat) {
		LICENCE_STAT = licence_stat;
	}


	public String getMCHT_STLM_ACCT() {
		return MCHT_STLM_ACCT;
	}


	public void setMCHT_STLM_ACCT(String mcht_stlm_acct) {
		MCHT_STLM_ACCT = mcht_stlm_acct;
	}


	public String getMCHT_ACCT_NM() {
		return MCHT_ACCT_NM;
	}


	public void setMCHT_ACCT_NM(String mcht_acct_nm) {
		MCHT_ACCT_NM = mcht_acct_nm;
	}


	public String getSETTLE_BANK_NM() {
		return SETTLE_BANK_NM;
	}


	public void setSETTLE_BANK_NM(String settle_bank_nm) {
		SETTLE_BANK_NM = settle_bank_nm;
	}


	public String getSETTLE_BANK_NO() {
		return SETTLE_BANK_NO;
	}


	public void setSETTLE_BANK_NO(String settle_bank_no) {
		SETTLE_BANK_NO = settle_bank_no;
	}


	public String getAPPLY_DATE() {
		return APPLY_DATE;
	}


	public void setAPPLY_DATE(String apply_date) {
		APPLY_DATE = apply_date;
	}

	

	
	
}
