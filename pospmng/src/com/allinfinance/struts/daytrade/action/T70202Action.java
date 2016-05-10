package com.allinfinance.struts.daytrade.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;

import com.allinfinance.bo.daytrade.T70202BO;
import com.allinfinance.frontend.dto.withdraw.WithDrawItem;
import com.allinfinance.frontend.dto.withdraw.WithDrawJsonDto;
import com.allinfinance.po.daytrade.TblWithdrawInf;
import com.allinfinance.po.daytrade.TblWithdrawInfDtl;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;

public class T70202Action extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T70202BO t70202BO = (T70202BO) ContextUtil.getBean("T70202BO");

	private TblWithdrawInf tblWithdrawInf;

	private String method;

	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if ("pass".equals(getMethod())) {
			return pass();
		} else if ("refuse".equals(getMethod())) {
			return refuse();
		}
		return SUCCESS;
	}

	// T+O提现审核通过

	private String pass() throws Exception {
		
		TblWithdrawInf withdrawInf = t70202BO.get(tblWithdrawInf.getBatchNo()
				.trim());

		withdrawInf.setCheckOpr(operator.getOprId());

		List<TblWithdrawInfDtl> dtlList = t70202BO.getDtlList(tblWithdrawInf
				.getBatchNo().trim());
		List<WithDrawItem> txnList = new ArrayList<WithDrawItem>();
		for (TblWithdrawInfDtl dtl : dtlList) {
			WithDrawItem item = new WithDrawItem();
			item.setPospInstDate(dtl.getInstDate());
			item.setPospSysSeqNum(dtl.getSysSeqNum());
			txnList.add(item);
		}
		WithDrawJsonDto withDrawJson = new WithDrawJsonDto();
		withDrawJson.setWebSeqNum(GenerateNextId.getSeqSysNum());
		withDrawJson.setWebTime(CommonFunction.getCurrentDateTime());
		withDrawJson.setWithDrawNum(String.valueOf(dtlList.size()));
		withDrawJson.setMerchantId(withdrawInf.getMchtNo());
		withDrawJson.setVersion("v1.0");
		withDrawJson.setInputSystem("ywgl");
		withDrawJson.setItems(txnList);
		return t70202BO.wdProcess(withdrawInf, withDrawJson);
	}

	// T+O提现审核拒绝
	public String refuse() throws IOException {

		TblWithdrawInf tblWithdrawInfNew = t70202BO.get(tblWithdrawInf
				.getBatchNo().trim());
		t70202BO.delete(tblWithdrawInfNew);// 删掉提现记录
		/*
		 * tblWithdrawInfNew.setWdStatus('7');// 人工审核拒绝
		 * tblWithdrawInfNew.setCheckTime(DateUtil.formatDate(new Date(),
		 * "yyyyMMddHHmmss"));
		 * tblWithdrawInfNew.setCheckOpr(operator.getOprId());
		 * tblWithdrawInfNew.setRefuseReason(tblWithdrawInf.getRefuseReason());
		 * t70202BO.saveOrUpdate(tblWithdrawInfNew);
		 */
		writeSuccessMsg("操作成功");
		return SUCCESS;

	}

	public T70202BO getT70202BO() {
		return t70202BO;
	}

	public void setT70202BO(T70202BO t70202bo) {
		t70202BO = t70202bo;
	}

	public TblWithdrawInf getTblWithdrawInf() {
		return tblWithdrawInf;
	}

	public void setTblWithdrawInf(TblWithdrawInf tblWithdrawInf) {
		this.tblWithdrawInf = tblWithdrawInf;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
