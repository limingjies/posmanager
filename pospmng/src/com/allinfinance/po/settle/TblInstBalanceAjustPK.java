package com.allinfinance.po.settle;

import java.io.Serializable;

/**
 * 差错入账调整记录表主键类
 * 
 * @author luhq
 *
 */
public class TblInstBalanceAjustPK implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1558189174403188265L;

	private String dateSettlmt;
	private String keyInst;

	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	public String getKeyInst() {
		return keyInst;
	}

	public void setKeyInst(String keyInst) {
		this.keyInst = keyInst;
	}
}
