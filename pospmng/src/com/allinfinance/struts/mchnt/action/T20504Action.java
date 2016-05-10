package com.allinfinance.struts.mchnt.action;

import java.lang.reflect.InvocationTargetException;

import com.allinfinance.bo.mchnt.T20504BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.mchnt.TblInsMchtInf;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T20504Action extends BaseAction {
	
	T20504BO t20504BO = (T20504BO) ContextUtil.getBean("T20504BO");
	
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} 
//			else if("update".equals(method)) {
//				rspCode = update();
//			} 
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，一二级商户映射信息维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String add() throws Exception {
		TblInsMchtInf tblInsMchtInf = new TblInsMchtInf();
		if(t20504BO.get(mchtNo) != null){
			return ErrorCode.T20504_01;
		}
		tblInsMchtInf.setMchtNo(getMchtNo());
		tblInsMchtInf.setFirstMchtCd(getFirstMchtCd());
		tblInsMchtInf.setFirstTermId(getFirstTermId());
//		System.out.println(getMchtNo());
//		System.out.println(getFirstMchtCd());
//		System.out.println(getFirstTermId());
		tblInsMchtInf.setInsIdCd(getInsIdCd());
		tblInsMchtInf.setAcqInstId("");
		tblInsMchtInf.setInsertTime("");
		tblInsMchtInf.setUpdateTime("");
		tblInsMchtInf.setMisc("");
		t20504BO.add(tblInsMchtInf);
		
		log("添加一二级商户映射信息成功。操作员编号：" + operator.getOprId());		
		return Constants.SUCCESS_CODE;
	}
	
	private String delete() {
		if(t20504BO.get(mchtNo) == null) {
			return "没有找到要删除的一级商户信息";
		}
		t20504BO.delete(mchtNo);
		return Constants.SUCCESS_CODE;
	}
	
	// 二级商户编号
	private String mchtNo;
	
	
	// 一级商户编号
	private String firstMchtCd;
	// 一级商户所属机构号
	private String insIdCd;
	// 一级机构终端号
	private String firstTermId;
	// 一级商户银联入网号
	private String acqInstId;
	// 
	private String misc;
	// 入网时间
	private String insertTime;
	// 最新入网时间
	private String updateTime;

	
	public T20504BO getT20504BO() {
		return t20504BO;
	}

	public void setT20504BO(T20504BO t20504bo) {
		t20504BO = t20504bo;
	}
	
	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getFirstMchtCd() {
		return firstMchtCd;
	}

	public void setFirstMchtCd(String firstMchtCd) {
		this.firstMchtCd = firstMchtCd;
	}

	public String getInsIdCd() {
		return insIdCd;
	}

	public void setInsIdCd(String insIdCd) {
		this.insIdCd = insIdCd;
	}

	public String getFirstTermId() {
		return firstTermId;
	}

	public void setFirstTermId(String firstTermId) {
		this.firstTermId = firstTermId;
	}

	public String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(String acqInstId) {
		this.acqInstId = acqInstId;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}