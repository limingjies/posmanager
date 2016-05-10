package com.allinfinance.struts.pos.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;

import com.allinfinance.bo.term.TermManagementBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.po.TblTermManagement;
import com.allinfinance.struts.pos.TblTermManagementConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-9
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class T30301Action extends  BaseAction{

	private static final long serialVersionUID = -8992241418046593410L;
	private TermManagementBO t3030BO;
	private String manufacturerNew;
	private String terminalTypeNew;
	private String number;
	private String startProductCd;
	private String endProductCd;
	private String productCdNew;
	private String miscNew;
	private String termId;
	private String misc;
	private String termTypeNew;
	
	/**
	 * @param t30301bo the t30301BO to set
	 */
	public void setT3030BO(TermManagementBO t3030bo) {
		t3030BO = t3030bo;
	}


	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("start".equals(getMethod())) {			
//			rspCode = start();			
		}
		return rspCode;
	}
	
	public String storeTerminal(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		Operator operator = (Operator)map.get("operator");
//		String batchNo = getNextBatchNo();
		TblTermManagement tblTermManagement = new TblTermManagement();
//		tblTermManagement.setId(getNextId(operator.getOprBrhId()));
//		tblTermManagement.setBatchNo(batchNo);
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_SIGNED);
//		tblTermManagement.setManufacturer(manufacturer);
//		tblTermManagement.setProductCd(productCd);
//		tblTermManagement.setTermType(termType);
//		tblTermManagement.setTerminalType(terminalType);
		if(misc != null && !misc.trim().equals(""))
			tblTermManagement.setMisc(misc.toString());
		tblTermManagement.setStorOprId(operator.getOprId());
		tblTermManagement.setStorDate(CommonFunction.getCurrentDate());
		tblTermManagement.setLastUpdOprId(operator.getOprId());
//		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentTs());
//		tblTermManagementDAO.save(tblTermManagement);
//		return Constants.SUCCESS_CODE_CUSTOMIZE+"批次号："+batchNo;
		return null;
	}


	/**
	 * @return the manufacturerNew
	 */
	public String getManufacturerNew() {
		return manufacturerNew;
	}

	/**
	 * @param manufacturerNew the manufacturerNew to set
	 */
	public void setManufacturerNew(String manufacturerNew) {
		this.manufacturerNew = manufacturerNew;
	}

	/**
	 * @return the terminalTypeNew
	 */
	public String getTerminalTypeNew() {
		return terminalTypeNew;
	}

	/**
	 * @param terminalTypeNew the terminalTypeNew to set
	 */
	public void setTerminalTypeNew(String terminalTypeNew) {
		this.terminalTypeNew = terminalTypeNew;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the startProductCd
	 */
	public String getStartProductCd() {
		return startProductCd;
	}

	/**
	 * @param startProductCd the startProductCd to set
	 */
	public void setStartProductCd(String startProductCd) {
		this.startProductCd = startProductCd;
	}

	/**
	 * @return the endProductCd
	 */
	public String getEndProductCd() {
		return endProductCd;
	}

	/**
	 * @param endProductCd the endProductCd to set
	 */
	public void setEndProductCd(String endProductCd) {
		this.endProductCd = endProductCd;
	}

	/**
	 * @return the misc
	 */
	public String getMisc() {
		return misc;
	}

	/**
	 * @param misc the misc to set
	 */
	public void setMisc(String misc) {
		this.misc = misc;
	}

	/**
	 * @return the termTypeNew
	 */
	public String getTermTypeNew() {
		return termTypeNew;
	}

	/**
	 * @param termTypeNew the termTypeNew to set
	 */
	public void setTermTypeNew(String termTypeNew) {
		this.termTypeNew = termTypeNew;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the productCdNew
	 */
	public String getProductCdNew() {
		return productCdNew;
	}

	/**
	 * @param productCdNew the productCdNew to set
	 */
	public void setProductCdNew(String productCdNew) {
		this.productCdNew = productCdNew;
	}

	/**
	 * @return the miscNew
	 */
	public String getMiscNew() {
		return miscNew;
	}

	/**
	 * @param miscNew the miscNew to set
	 */
	public void setMiscNew(String miscNew) {
		this.miscNew = miscNew;
	}

}
