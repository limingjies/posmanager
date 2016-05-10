package com.allinfinance.po.daytrade;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * 描述：账户差错表
 * @author tangly
 * 2015年7月16日下午10:38:18
 */
public class TblAcctErr implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key_inst;
	private String date_stlm;
	private String acct_err_type;
	private String acct_chk_flag;
	private String txn_num;
	private String stlm_flag;
	private String txn_date;
	private String txn_time;
	private String sys_seq_num;
	private String retrivl_ref;
	private String inst_code;
	private String inst_mcht_id;
	private String inst_term_id;
	private String inst_retrivl_ref;
	private String brh_id;
	private String card_accp_id;
	private String card_accp_term_id;
	private String term_sn;
	private String pan;
	private String card_type;
	private String amt_trans;
	private String amt_trans_fee;
	private String amt_stlm;

	private String resp_code;
	private String inst_amt_fee;
	private String inst_amt_stlm;
	private String inst_pan;
	private String inst_amt;
	private String acct_change_stat;
	private String acct_change_resp;
	private String acct_change_dt;
	
	public String getKey_inst() {
		return key_inst;
	}
	public void setKey_inst(String key_inst) {
		this.key_inst = key_inst;
	}
	public String getDate_stlm() {
		return date_stlm;
	}
	public void setDate_stlm(String date_stlm) {
		this.date_stlm = date_stlm;
	}
	public String getAcct_err_type() {
		return acct_err_type;
	}
	public void setAcct_err_type(String acct_err_type) {
		this.acct_err_type = acct_err_type;
	}
	public String getAcct_chk_flag() {
		return acct_chk_flag;
	}
	public void setAcct_chk_flag(String acct_chk_flag) {
		this.acct_chk_flag = acct_chk_flag;
	}
	public String getTxn_num() {
		return txn_num;
	}
	public void setTxn_num(String txn_num) {
		this.txn_num = txn_num;
	}
	public String getStlm_flag() {
		return stlm_flag;
	}
	public void setStlm_flag(String stlm_flag) {
		this.stlm_flag = stlm_flag;
	}
	public String getTxn_date() {
		return txn_date;
	}
	public void setTxn_date(String txn_date) {
		this.txn_date = txn_date;
	}
	public String getTxn_time() {
		return txn_time;
	}
	public void setTxn_time(String txn_time) {
		this.txn_time = txn_time;
	}
	public String getSys_seq_num() {
		return sys_seq_num;
	}
	public void setSys_seq_num(String sys_seq_num) {
		this.sys_seq_num = sys_seq_num;
	}
	public String getRetrivl_ref() {
		return retrivl_ref;
	}
	public void setRetrivl_ref(String retrivl_ref) {
		this.retrivl_ref = retrivl_ref;
	}
	public String getInst_code() {
		return inst_code;
	}
	public void setInst_code(String inst_code) {
		this.inst_code = inst_code;
	}
	public String getInst_mcht_id() {
		return inst_mcht_id;
	}
	public void setInst_mcht_id(String inst_mcht_id) {
		this.inst_mcht_id = inst_mcht_id;
	}
	public String getInst_term_id() {
		return inst_term_id;
	}
	public void setInst_term_id(String inst_term_id) {
		this.inst_term_id = inst_term_id;
	}
	public String getInst_retrivl_ref() {
		return inst_retrivl_ref;
	}
	public void setInst_retrivl_ref(String inst_retrivl_ref) {
		this.inst_retrivl_ref = inst_retrivl_ref;
	}
	public String getCard_accp_id() {
		return card_accp_id;
	}
	public void setCard_accp_id(String card_accp_id) {
		this.card_accp_id = card_accp_id;
	}
	public String getCard_accp_term_id() {
		return card_accp_term_id;
	}
	public void setCard_accp_term_id(String card_accp_term_id) {
		this.card_accp_term_id = card_accp_term_id;
	}
	public String getTerm_sn() {
		return term_sn;
	}
	public void setTerm_sn(String term_sn) {
		this.term_sn = term_sn;
	}
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getInst_amt_fee() {
		return inst_amt_fee;
	}
	public void setInst_amt_fee(String inst_amt_fee) {
		this.inst_amt_fee = inst_amt_fee;
	}
	public String getInst_amt_stlm() {
		return inst_amt_stlm;
	}
	public void setInst_amt_stlm(String inst_amt_stlm) {
		this.inst_amt_stlm = inst_amt_stlm;
	}
	public String getInst_pan() {
		return inst_pan;
	}
	public void setInst_pan(String inst_pan) {
		this.inst_pan = inst_pan;
	}
	public String getInst_amt() {
		return inst_amt;
	}
	public void setInst_amt(String inst_amt) {
		this.inst_amt = inst_amt;
	}
	public String getAcct_change_stat() {
		return acct_change_stat;
	}
	public void setAcct_change_stat(String acct_change_stat) {
		this.acct_change_stat = acct_change_stat;
	}
	public String getAcct_change_resp() {
		return acct_change_resp;
	}
	public void setAcct_change_resp(String acct_change_resp) {
		this.acct_change_resp = acct_change_resp;
	}
	public String getAcct_change_dt() {
		return acct_change_dt;
	}
	public void setAcct_change_dt(String acct_change_dt) {
		this.acct_change_dt = acct_change_dt;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getAmt_trans() {
		return amt_trans;
	}
	public void setAmt_trans(String amt_trans) {
		this.amt_trans = amt_trans;
	}
	public String getAmt_trans_fee() {
		return amt_trans_fee;
	}
	public void setAmt_trans_fee(String amt_trans_fee) {
		this.amt_trans_fee = amt_trans_fee;
	}
	public String getAmt_stlm() {
		return amt_stlm;
	}
	public void setAmt_stlm(String amt_stlm) {
		this.amt_stlm = amt_stlm;
	}
	public String getBrh_id() {
		return brh_id;
	}
	public void setBrh_id(String brh_id) {
		this.brh_id = brh_id;
	}
	
	
}
