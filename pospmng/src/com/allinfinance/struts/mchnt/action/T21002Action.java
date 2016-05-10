package com.allinfinance.struts.mchnt.action;

import com.allinfinance.bo.mchnt.T21002BO;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblOprInfoConstants;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.Encryption;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2014-1-17
 * 
 * @author 曹铁铮
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T21002Action extends BaseAction{

	private ShTblOprInfo shTblOprInfo;
	private ShTblOprInfo shTblOprInfoUpd;
	private String oprId;
	private String mchtNo;
	private String brhId;
	T21002BO t21002BO = (T21002BO) ContextUtil.getBean("T21002BO");
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if ("add".equals(method)) {// 用户添加
			log("操作员：" + operator.getOprId() + "->操作员新增");
			return add();
		} else if ("update".equals(method)) {// 机构修改
			log("操作员：" + operator.getOprId() + "->操作员修改");
			return update();
		} else if ("delete".equals(method)) {
			log("操作员：" + operator.getOprId() + "->注销");
			return delete();
		} else if ("reset".equals(method)) {
			log("操作员：" + operator.getOprId() + "->密码重置");
			return reset();
		}
		return rspCode;
	}
	private String update() throws Exception {
		// TODO Auto-generated method stub
//		shTblOprInfoUpd.getId().setMchtNo(operator.getOprMchtNo());
		
		if(t21002BO.get(shTblOprInfoUpd.getId())==null){
			return "该用户不存在，请重新刷新选择！";
		}
		ShTblOprInfo shTblOprInfo=t21002BO.get(shTblOprInfoUpd.getId());
		shTblOprInfo.setOprName(shTblOprInfoUpd.getOprName());
//		shTblOprInfo.setRoleId(shTblOprInfoUpd.getRoleId());
		shTblOprInfo.setOprSex(shTblOprInfoUpd.getOprSex());
		shTblOprInfo.setCerType(shTblOprInfoUpd.getCerType());
		shTblOprInfo.setCerNo(shTblOprInfoUpd.getCerNo());
		shTblOprInfo.setContactPhone(shTblOprInfoUpd.getContactPhone());
		shTblOprInfo.setEmail(shTblOprInfoUpd.getEmail());
		shTblOprInfo.setMchtBrhFlag(shTblOprInfoUpd.getMchtBrhFlag());
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_OK);
		return t21002BO.update(shTblOprInfo);
	}
	private String delete() throws Exception {
		// TODO Auto-generated method stub
		ShTblOprInfoPk shTblOprInfoPk=new ShTblOprInfoPk();
		shTblOprInfoPk.setMchtNo(mchtNo);
		shTblOprInfoPk.setOprId(oprId);
		shTblOprInfoPk.setBrhId(brhId);
		if(t21002BO.get(shTblOprInfoPk)==null){
			return "该用户不存在，请重新刷新选择！";
		}
		ShTblOprInfo shTblOprInfo=t21002BO.get(shTblOprInfoPk);
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_OUT);
		return t21002BO.update(shTblOprInfo);
	}
	private String reset() throws Exception {
		// TODO Auto-generated method stub
		ShTblOprInfoPk shTblOprInfoPk=new ShTblOprInfoPk();
		shTblOprInfoPk.setMchtNo(mchtNo);
		shTblOprInfoPk.setOprId(oprId);
		shTblOprInfoPk.setBrhId(brhId);
		if(t21002BO.get(shTblOprInfoPk)==null){
			return "该用户不存在，请重新刷新选择！";
		}
		ShTblOprInfo shTblOprInfo=t21002BO.get(shTblOprInfoPk);
		shTblOprInfo.setOprPwd(Encryption.encryptadd(SysParamUtil
				.getParam(SysParamConstants.OPR_DEFAULT_PWD)));
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_INIT);
		shTblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction
				.getCurrentDate(), SysParamUtil
				.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		shTblOprInfo.setResvInfo("");
		shTblOprInfo.setPwdWrTm(TblOprInfoConstants.PWD_WR_TM);
		shTblOprInfo.setPwdWrTmTotal(TblOprInfoConstants.PWD_WR_TM_TOTAL);
		shTblOprInfo.setPwdWrTmContinue(TblOprInfoConstants.PWD_WR_TM_CONTINUE);
		return t21002BO.update(shTblOprInfo);
	}
	public String getBrhId() {
		return brhId;
	}
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	private String add() throws Exception  {
		// TODO Auto-generated method stub
//		shTblOprInfo.getId().setMchtNo(operator.getOprMchtNo());
		if(t21002BO.get(shTblOprInfo.getId())!=null){
			return "该用户已存在，请重新输入用户编号！";
		}
		shTblOprInfo.setMchtBrhFlag(shTblOprInfo.getMchtBrhFlag());
		shTblOprInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		shTblOprInfo.setOprPwd(Encryption.encryptadd(SysParamUtil
				.getParam(SysParamConstants.OPR_DEFAULT_PWD)));
		shTblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction
				.getCurrentDate(), SysParamUtil
				.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		shTblOprInfo.setPwdWrTm(TblOprInfoConstants.PWD_WR_TM);
		if(shTblOprInfo.getMchtBrhFlag().equals("0")){
		shTblOprInfo.setRoleId(TblOprInfoConstants.SUP_MCHT_ROLE);
		}else{
		shTblOprInfo.setRoleId(TblOprInfoConstants.SUP_BRH_ROLE);	
		}
		shTblOprInfo.setPwdWrTmTotal(TblOprInfoConstants.PWD_WR_TM_TOTAL);
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_INIT);
		shTblOprInfo.setCurrentLoginTime(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginIp(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginStatus(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setPwdWrTmContinue(TblOprInfoConstants.PWD_WR_TM_CONTINUE);
		return t21002BO.add(shTblOprInfo);
	}
	/**
	 * @return the shTblOprInfo
	 */
	public ShTblOprInfo getShTblOprInfo() {
		return shTblOprInfo;
	}
	/**
	 * @param shTblOprInfo the shTblOprInfo to set
	 */
	public void setShTblOprInfo(ShTblOprInfo shTblOprInfo) {
		this.shTblOprInfo = shTblOprInfo;
	}
	/**
	 * @return the shTblOprInfoUpd
	 */
	public ShTblOprInfo getShTblOprInfoUpd() {
		return shTblOprInfoUpd;
	}
	/**
	 * @param shTblOprInfoUpd the shTblOprInfoUpd to set
	 */
	public void setShTblOprInfoUpd(ShTblOprInfo shTblOprInfoUpd) {
		this.shTblOprInfoUpd = shTblOprInfoUpd;
	}
	/**
	 * @return the oprId
	 */
	public String getOprId() {
		return oprId;
	}
	/**
	 * @param oprId the oprId to set
	 */
	public void setOprId(String oprId) {
		this.oprId = oprId;
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
	
	

}
