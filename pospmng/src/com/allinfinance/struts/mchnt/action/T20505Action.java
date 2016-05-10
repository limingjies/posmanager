package com.allinfinance.struts.mchnt.action;

import java.math.BigDecimal;

import com.allinfinance.bo.mchnt.T20505BO;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.mchnt.TblFirstMchtTxn;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:一级商户信息维护
 */
@SuppressWarnings("serial")
public class T20505Action extends BaseAction {
	
	T20505BO t20505BO = (T20505BO) ContextUtil.getBean("T20505BO");
	
	@Override
	protected String subExecute() throws Exception {
		if("add".equals(method)) {
			log("新增一级商户限额信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("update".equals(method)) {
			log("修改一级商户限额信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除一级商户限额信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		}
		return rspCode;
	}
	
	private String add() throws Exception {
		TblFirstMchtTxn tblFirstMchtTxn = new TblFirstMchtTxn();
		if(t20505BO.get(firstMchtCd) != null){
			return ErrorCode.T20505_01;
		}
		tblFirstMchtTxn.setFirstMchtCd(firstMchtCd);
		tblFirstMchtTxn.setTxnDate(" ");
		tblFirstMchtTxn.setTxnNum(new BigDecimal("0"));
		tblFirstMchtTxn.setTxnAmt(new BigDecimal("0"));
		tblFirstMchtTxn.setAmtLimit(new BigDecimal(amtLimit));
		return t20505BO.add(tblFirstMchtTxn);
	}
	
	private String update() throws Exception {
		if(t20505BO.get(firstMchtCd) == null){
			return ErrorCode.T20505_02;
		}
		TblFirstMchtTxn tblFirstMchtTxn = t20505BO.get(firstMchtCd);
		tblFirstMchtTxn.setAmtLimit(new BigDecimal(amtLimit));
		return t20505BO.update(tblFirstMchtTxn);
	}

	private String delete() {
		if(t20505BO.get(firstMchtCd) == null) {
			return ErrorCode.T20505_02;
		}
		return t20505BO.delete(firstMchtCd);
	}

	
	private String firstMchtCd;
	private String amtLimit;

	/**
	 * @return the firstMchtCd
	 */
	public String getFirstMchtCd() {
		return firstMchtCd;
	}

	/**
	 * @param firstMchtCd the firstMchtCd to set
	 */
	public void setFirstMchtCd(String firstMchtCd) {
		this.firstMchtCd = firstMchtCd;
	}

	/**
	 * @return the amtLimit
	 */
	public String getAmtLimit() {
		return amtLimit;
	}

	/**
	 * @param amtLimit the amtLimit to set
	 */
	public void setAmtLimit(String amtLimit) {
		this.amtLimit = amtLimit;
	}
	
	
}