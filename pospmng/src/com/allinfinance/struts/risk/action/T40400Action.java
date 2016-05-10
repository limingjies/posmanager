package com.allinfinance.struts.risk.action;

import com.allinfinance.bo.risk.T40400BO;
import com.allinfinance.po.risk.TblGreyMchtSort;
import com.allinfinance.po.risk.TblRiskGreyMcht;
import com.allinfinance.po.risk.TblRiskGreyMchtPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * 
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-01
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * author: 徐鹏飞
 *  
 * time: 2014年12月4日下午4:55:01
 * 
 * version: 1.0
 */
@SuppressWarnings("serial")
public class T40400Action extends BaseAction{

	private T40400BO t40400BO = (T40400BO) ContextUtil.getBean("T40400BO");
	
	TblGreyMchtSort tblGreyMchtSort;
	TblRiskGreyMcht tblRiskGreyMcht;
	TblRiskGreyMchtPK tblRiskGreyMchtPK;
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("addSort".equals(getMethod())) {
			rspCode = addSort();
		} else if("updateSort".equals(getMethod())) {
			rspCode = updateSort();
		} else if("deleteSort".equals(getMethod())) {
			rspCode = deleteSort();
		} else if("add".equals(getMethod())) {
			rspCode = add();
		} else if("update".equals(getMethod())) {
			rspCode = update();
		} else if("delete".equals(getMethod())) {
			rspCode = delete();
		}
		return rspCode;
	}
	
	private String addSort() throws Exception {
		tblGreyMchtSort = new TblGreyMchtSort();
		tblGreyMchtSort.setSortNo(CommonFunction.getCurrentDateTime());
		tblGreyMchtSort.setSortName(sortNameAdd);
		tblGreyMchtSort.setCrtOpr(operator.getOprId());
		tblGreyMchtSort.setCrtTime(CommonFunction.getCurrentDateTime());
		return t40400BO.addSort(tblGreyMchtSort);
	}
	
	private String updateSort() throws Exception {
		if(t40400BO.getSort(sortNo) == null) {
			return "该灰名单类别不存在,请刷新！";
		}
		tblGreyMchtSort = t40400BO.getSort(sortNo);
		tblGreyMchtSort.setSortName(sortNameUpd);
		tblGreyMchtSort.setUpdOpr(operator.getOprId());
		tblGreyMchtSort.setUpdTime(CommonFunction.getCurrentDateTime());
		return t40400BO.updateSort(tblGreyMchtSort);
	}
	
	private String deleteSort() throws Exception {
		if(t40400BO.getSort(sortNo) == null) {
			return "该灰名单类别不存在,请刷新！";
		}
		if(t40400BO.query(sortNo)) {
			return "该灰名单类别下存在灰名单商户,请刷新！";
		}
		return t40400BO.deleteSort(sortNo);
	}
	
	private String add() throws Exception {
		tblRiskGreyMcht = new TblRiskGreyMcht();
		tblRiskGreyMchtPK = new TblRiskGreyMchtPK();
		tblRiskGreyMchtPK.setMchtNo(mchtNo);
		tblRiskGreyMchtPK.setSortNo(sortNo);
		if(t40400BO.get(tblRiskGreyMchtPK) != null) {
			return "该类别中已存在此灰名单商户,请刷新！";
		}
		tblRiskGreyMcht.setId(tblRiskGreyMchtPK);
		tblRiskGreyMcht.setRemarkInfo(remarkInfo);
		tblRiskGreyMcht.setCrtOpr(operator.getOprId());
		tblRiskGreyMcht.setCrtTime(CommonFunction.getCurrentDateTime());
		return t40400BO.add(tblRiskGreyMcht);
	}
	
	private String update() throws Exception {
		return t40400BO.update(tblRiskGreyMcht);
	}
	
	private String delete() throws Exception {
		tblRiskGreyMchtPK = new TblRiskGreyMchtPK();
		tblRiskGreyMchtPK.setMchtNo(mchtNo);
		tblRiskGreyMchtPK.setSortNo(sortNo);
		if(t40400BO.get(tblRiskGreyMchtPK) == null) {
			return "该类别中此灰名单商户不存在,请刷新！";
		}
		return t40400BO.delete(tblRiskGreyMchtPK);
	}
	
	private String sortNameAdd;
	private String sortNameUpd;
	private String mchtNo;
	private String sortNo;
	private String remarkInfo;

	/**
	 * @return the sortNameAdd
	 */
	public String getSortNameAdd() {
		return sortNameAdd;
	}

	/**
	 * @param sortNameAdd the sortNameAdd to set
	 */
	public void setSortNameAdd(String sortNameAdd) {
		this.sortNameAdd = sortNameAdd;
	}

	/**
	 * @return the sortNameUpd
	 */
	public String getSortNameUpd() {
		return sortNameUpd;
	}

	/**
	 * @param sortNameUpd the sortNameUpd to set
	 */
	public void setSortNameUpd(String sortNameUpd) {
		this.sortNameUpd = sortNameUpd;
	}

	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNo the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	/**
	 * @return the sortNo
	 */
	public String getSortNo() {
		return sortNo;
	}

	/**
	 * @param sortNo the sortNo to set
	 */
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * @return the remarkInfo
	 */
	public String getRemarkInfo() {
		return remarkInfo;
	}

	/**
	 * @param remarkInfo the remarkInfo to set
	 */
	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}
}
