package com.allinfinance.po.daytrade;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * 描述：提现差错表
 * @author tangly
 * 2015年7月16日下午10:38:37
 */
public class TblWithdrawErr implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String front_dt;
	private String front_sn;
	private String mcht_withdraw_dt;
	private String mcht_withdraw_sn;
	private String txn_date;
	private String err_type  ;
	private String front_stat;
	private BigDecimal front_withdraw_amt;
	private BigDecimal front_withdraw_fee;
	private BigDecimal front_withdraw_stlm_amt;
	private String acct_withdraw_stat;
	private BigDecimal acct_withdraw_amt;
	private BigDecimal acct_withdraw_fee;
	private BigDecimal acct_withdraw_stlm_amt;
	private String acct_change;
	private String acct_change_resp;
	private String acct_change_dt;
	private String inst_dt;
	private String update_dt;
	private String reserved_1;
	private String reserved_2;
	
	public String getFront_dt() {
		return front_dt;
	}
	public void setFront_dt(String front_dt) {
		this.front_dt = front_dt;
	}
	public String getFront_sn() {
		return front_sn;
	}
	public void setFront_sn(String front_sn) {
		this.front_sn = front_sn;
	}
	public String getMcht_withdraw_dt() {
		return mcht_withdraw_dt;
	}
	public void setMcht_withdraw_dt(String mcht_withdraw_dt) {
		this.mcht_withdraw_dt = mcht_withdraw_dt;
	}
	public String getMcht_withdraw_sn() {
		return mcht_withdraw_sn;
	}
	public void setMcht_withdraw_sn(String mcht_withdraw_sn) {
		this.mcht_withdraw_sn = mcht_withdraw_sn;
	}
	public String getTxn_date() {
		return txn_date;
	}
	public void setTxn_date(String txn_date) {
		this.txn_date = txn_date;
	}
	public String getErr_type() {
		return err_type;
	}
	public void setErr_type(String err_type) {
		this.err_type = err_type;
	}
	public String getFront_stat() {
		return front_stat;
	}
	public void setFront_stat(String front_stat) {
		this.front_stat = front_stat;
	}
	public BigDecimal getFront_withdraw_amt() {
		return front_withdraw_amt;
	}
	public void setFront_withdraw_amt(BigDecimal front_withdraw_amt) {
		this.front_withdraw_amt = front_withdraw_amt;
	}
	public BigDecimal getFront_withdraw_fee() {
		return front_withdraw_fee;
	}
	public void setFront_withdraw_fee(BigDecimal front_withdraw_fee) {
		this.front_withdraw_fee = front_withdraw_fee;
	}
	public BigDecimal getFront_withdraw_stlm_amt() {
		return front_withdraw_stlm_amt;
	}
	public void setFront_withdraw_stlm_amt(BigDecimal front_withdraw_stlm_amt) {
		this.front_withdraw_stlm_amt = front_withdraw_stlm_amt;
	}
	public String getAcct_withdraw_stat() {
		return acct_withdraw_stat;
	}
	public void setAcct_withdraw_stat(String acct_withdraw_stat) {
		this.acct_withdraw_stat = acct_withdraw_stat;
	}
	public BigDecimal getAcct_withdraw_amt() {
		return acct_withdraw_amt;
	}
	public void setAcct_withdraw_amt(BigDecimal acct_withdraw_amt) {
		this.acct_withdraw_amt = acct_withdraw_amt;
	}
	public BigDecimal getAcct_withdraw_fee() {
		return acct_withdraw_fee;
	}
	public void setAcct_withdraw_fee(BigDecimal acct_withdraw_fee) {
		this.acct_withdraw_fee = acct_withdraw_fee;
	}
	public BigDecimal getAcct_withdraw_stlm_amt() {
		return acct_withdraw_stlm_amt;
	}
	public void setAcct_withdraw_stlm_amt(BigDecimal acct_withdraw_stlm_amt) {
		this.acct_withdraw_stlm_amt = acct_withdraw_stlm_amt;
	}
	public String getAcct_change() {
		return acct_change;
	}
	public void setAcct_change(String acct_change) {
		this.acct_change = acct_change;
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
	public String getReserved_1() {
		return reserved_1;
	}
	public void setReserved_1(String reserved_1) {
		this.reserved_1 = reserved_1;
	}
	public String getReserved_2() {
		return reserved_2;
	}
	public void setReserved_2(String reserved_2) {
		this.reserved_2 = reserved_2;
	}
	public String getInst_dt() {
		return inst_dt;
	}
	public void setInst_dt(String inst_dt) {
		this.inst_dt = inst_dt;
	}
	public String getUpdate_dt() {
		return update_dt;
	}
	public void setUpdate_dt(String update_dt) {
		this.update_dt = update_dt;
	}

	

}
