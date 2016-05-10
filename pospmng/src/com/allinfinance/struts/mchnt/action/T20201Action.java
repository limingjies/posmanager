/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-5       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.struts.mchnt.action;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.bo.impl.mchnt.TblMchntService;
import com.allinfinance.bo.mchnt.T20504BO;
import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;
/**
 * Title:商户信息审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-5
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20201Action extends BaseAction {
	private String mchtGrp;     ;
	private String idmcc;
	private String rislLvlId;
	private String countryId;
	private String complianceId;
	private TblRiskParamMng tblRiskParamMng;
	
	/**
	 * @return the mchtGrp
	 */
	public String getMchtGrp() {
		return mchtGrp;
	}

	/**
	 * @param mchtGrp the mchtGrp to set
	 */
	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	/**
	 * @return the idmcc
	 */
	public String getIdmcc() {
		return idmcc;
	}

	/**
	 * @param idmcc the idmcc to set
	 */
	public void setIdmcc(String idmcc) {
		this.idmcc = idmcc;
	}

	/**
	 * @return the rislLvlId
	 */
	public String getRislLvlId() {
		return rislLvlId;
	}

	/**
	 * @param rislLvlId the rislLvlId to set
	 */
	public void setRislLvlId(String rislLvlId) {
		this.rislLvlId = rislLvlId;
	}

	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the complianceId
	 */
	public String getComplianceId() {
		return complianceId;
	}

	/**
	 * @param complianceId the complianceId to set
	 */
	public void setComplianceId(String complianceId) {
		this.complianceId = complianceId;
	}

	private TblMchntService service = (TblMchntService) ContextUtil.getBean("tblMchntService");
	public ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	public ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;

	private T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
	private T20504BO t20504BO = (T20504BO) ContextUtil.getBean("T20504BO");
	
	@Override
	protected String subExecute() throws Exception {
		
		//在新增、修改、冻结、恢复和注销时，CRT_OPR_ID均保存该交易的申请人（发起柜员），UPD_OPR_ID保存该交易的审核人
		String sql = "SELECT CRT_OPR_ID FROM Tbl_Mcht_Base_Inf_Tmp WHERE MCHT_NO = '" + mchntId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}

		log("审核商户编号：" + mchntId);
		
		if("accept".equals(method)) {
			rspCode = accept();
		} else if("refuse".equals(method)) {
			rspCode = refuse();
		} else if("back".equals(method)) {
			rspCode = back();
		}
		
		return rspCode;
	}
		
	/**
	 * 审核通过
	 * 1、客户在POSP入网时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因
	 * 2、客户在POSP结算信息并更时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因
	 *
	 * @return
	 * @throws Exception 
	 */
	private String accept() throws Exception {
		/*if(t20504BO.get(mchntId) == null){
			return "请先配置该商户的一二级映射！";
		}*/
		TblMchtBaseInfTmp tmp = service.getBaseInfTmp(mchntId);
		TblMchtSettleInfTmp tmpSettle = service.getSettleInfTmp(mchntId);
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}else if(!"1".equals(tmp.getMchtStatus())&&!"4".equals(tmp.getMchtStatus())){
			return "商户已被审核，请刷新页面重试！";
		}
		if(StringUtils.isNotEmpty(mchtGrp)){
			tmp.setMchtGrp(mchtGrp);
		}
		if(StringUtils.isNotEmpty(idmcc)){
			tmp.setMcc(idmcc);
		}
		if(StringUtils.isNotEmpty(complianceId)){
			tmpSettle.setCompliance(complianceId);
		}
		if(StringUtils.isNotEmpty(countryId)){
			tmpSettle.setCountry(countryId);
		}
		if(StringUtils.isNotEmpty(rislLvlId)){
			tmp.setRislLvl(rislLvlId);
		}
		// 取得原始信息
		TblMchtBaseInf inf = service.getBaseInf(mchntId);
		TblMchtSettleInf infSettle = service.getSettleInf(mchntId);
		if (null == inf) {
			inf = new TblMchtBaseInf();
		}
		if (null == infSettle) {
			infSettle = new TblMchtSettleInf();
		}
		if(!("3".equals(tmp.getMchtStatus())||"4".equals(tmp.getMchtStatus()))){
			tblRiskParamMng.setRegOpr(operator.getOprId());
			tblRiskParamMng.setRegTime(CommonFunction.getCurrentDateTime());
		}
		tblRiskParamMng.setUpdOpr(operator.getOprId());
		tblRiskParamMng.setUpdTime(CommonFunction.getCurrentDateTime());
		rspCode = service.accept(mchntId,tmp,tmpSettle,inf,infSettle,tblRiskParamMng);
		
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			//将修改的数据同步到渠道入网
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmpUpd = t20901BO.getBaseInfTmp(mchntId);
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmpUpd = t20901BO.getSettleInfTmp(mchntId);
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmpUpdNew = new TblMchtBaseInfTmpTmp();
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmpUpdNew = new TblMchtSettleInfTmpTmp();
			if (null == tblMchtBaseInfTmpTmpUpd || null == tblMchtSettleInfTmpTmpUpd) {
				return Constants.SUCCESS_CODE_CUSTOMIZE + "商户审核通过，此商户非渠道录入，同步数据到渠道失败！";
			}
//			TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
//			TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
			//###########################修改相关数据#############################
			tblMchtBaseInfTmpTmpUpd.setMchtGrp(tmp.getMchtGrp());
			tblMchtBaseInfTmpTmpUpd.setMcc(tmp.getMcc());
			tblMchtBaseInfTmpTmpUpd.setRislLvl(tmp.getRislLvl());
			tblMchtSettleInfTmpTmpUpd.setCompliance(tmpSettle.getCompliance());;
			tblMchtSettleInfTmpTmpUpd.setCountry(tmpSettle.getCountry());;
			
			tblMchtBaseInfTmpTmpUpd.setMchtCnAbbr(tmp.getMchtCnAbbr());
			tblMchtBaseInfTmpTmpUpd.setMchtGrp(tmp.getMchtGrp());
			tblMchtBaseInfTmpTmpUpd.setMcc(tmp.getMcc());
			tblMchtBaseInfTmpTmpUpd.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
			tblMchtBaseInfTmpTmpUpd.setMchtFunction(tmp.getMchtFunction());
			//修改页面营业时间
			tblMchtBaseInfTmpTmpUpd.setOpenTime(tmp.getOpenTime());
			tblMchtBaseInfTmpTmpUpd.setCloseTime(tmp.getCloseTime());
			tblMchtBaseInfTmpTmpUpd.setEngName(tmp.getEngName());	
//			tblMchtBaseInfTmpTmpUpd.setAreaNo(tmp.getAreaNo());	
//			tblMchtBaseInfTmpTmpUpd.setAcqBkName(tmp.getAcqBkName());
			tblMchtBaseInfTmpTmpUpd.setConnType(tmp.getConnType());
//			tblMchtBaseInfTmpTmpUpd.setMchtGroupFlag(tmp.getMchtGroupFlag());
//			tblMchtBaseInfTmpTmpUpd.setMchtGroupId(tmp.getMchtGroupId());
			tblMchtBaseInfTmpTmpUpd.setBankNo(tmp.getBankNo());
			tblMchtBaseInfTmpTmpUpd.setMchtNm(tmp.getMchtNm());
			tblMchtBaseInfTmpTmpUpd.setAddr(tmp.getAddr());
			tblMchtBaseInfTmpTmpUpd.setLicenceNo(tmp.getLicenceNo());
			tblMchtBaseInfTmpTmpUpd.setFaxNo(tmp.getFaxNo());
			tblMchtBaseInfTmpTmpUpd.setEtpsAttr(tmp.getEtpsAttr());
			tblMchtBaseInfTmpTmpUpd.setCommEmail(tmp.getCommEmail());
			tblMchtBaseInfTmpTmpUpd.setHomePage(tmp.getHomePage());
			tblMchtBaseInfTmpTmpUpd.setPostCode(tmp.getPostCode());
			tblMchtBaseInfTmpTmpUpd.setManager(tmp.getManager());
			tblMchtBaseInfTmpTmpUpd.setBusAmt(tmp.getBusAmt());
			tblMchtBaseInfTmpTmpUpd.setArtifCertifTp(tmp.getArtifCertifTp());
			tblMchtBaseInfTmpTmpUpd.setIdentityNo(tmp.getIdentityNo());
			tblMchtBaseInfTmpTmpUpd.setContact(tmp.getContact());
			tblMchtBaseInfTmpTmpUpd.setCommTel(tmp.getCommTel());
			tblMchtBaseInfTmpTmpUpd.setElectrofax(tmp.getElectrofax());
			tblMchtBaseInfTmpTmpUpd.setFax(tmp.getFax());	
			tblMchtBaseInfTmpTmpUpd.setRislLvl(tmp.getRislLvl());//商户风险等级
//			tblMchtBaseInfTmpTmpUpd.setReserved("");//审核通过拒绝原因	
			tblMchtSettleInfTmpTmpUpd.setSettleAcct(tmpSettle.getSettleAcct());
			tblMchtSettleInfTmpTmpUpd.setSettleBankNm(tmpSettle.getSettleBankNm());
			tblMchtSettleInfTmpTmpUpd.setSettleAcctNm(tmpSettle.getSettleAcctNm());
			// 记录修改时间
			tblMchtBaseInfTmpTmpUpd.setRecUpdTs(CommonFunction.getCurrentDateTime());
			// 记录修改时间
			tblMchtSettleInfTmpTmpUpd.setRecUpdTs(CommonFunction.getCurrentDateTime());
			// 记录修改人
			tblMchtBaseInfTmpTmpUpd.setUpdOprId(tmp.getUpdOprId());	
			// 设置费率
			tblMchtSettleInfTmpTmpUpd.setFeeRate(tmpSettle.getFeeRate());
			//###########################修改相关数据#############################
			tblMchtSettleInfTmpTmpUpd.setSpeSettleTp(tmpSettle.getSpeSettleTp());
			//###########################更新到数据库#############################
			t20901BO.delete(tblMchtBaseInfTmpTmpUpd, tblMchtSettleInfTmpTmpUpd);
			tblMchtBaseInfTmpTmpUpd.setMchtNo(inf.getMchtNo());//设置新的商户号
			tblMchtSettleInfTmpTmpUpd.setMchtNo(inf.getMchtNo());
			tblMchtBaseInfTmpTmpUpd.setMchtStatus(inf.getMchtStatus());
			if(mchntId.contains("AAA")){ //设置开户成功标志“1”
				tblMchtBaseInfTmpTmpUpd.setOpenVirtualAcctFlag("1");
			}
			rspCode = t20901BO.saveTmp(tblMchtBaseInfTmpTmpUpd, tblMchtSettleInfTmpTmpUpd);
			//###########################更新到数据库#############################
			service.updateHisMcc(tblMchtBaseInfTmpTmpUpd,tblMchtSettleInfTmpTmpUpd);
			
		}
		
		return rspCode;
	}
	

	/**
	 * 审核拒绝
	 * @return
	 * @throws Exception 
	 */
	private String refuse() throws Exception {
		rspCode = service.refuse(mchntId, refuseInfo);
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			rspCode = tmpStatusChange();
		}
		return rspCode;
	}
	
	/**
	 * 审核退回
	 * @return
	 * @throws Exception  
	 */
	private String back() throws Exception {
		rspCode = service.back(mchntId, refuseInfo);
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			rspCode = tmpStatusChange();
		}
		return rspCode;
	}
	
	/**
	 * @return the mchntId
	 * @throws Exception 
	 * @throws  
	 */
	private String tmpStatusChange() throws Exception {
		//将状态同步到渠道入网
		TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmpUpd = t20901BO.getBaseInfTmp(mchntId);
		if (null != tblMchtBaseInfTmpTmpUpd) {
			TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
			tblMchtBaseInfTmpTmpUpd.setMchtStatus(tmp.getMchtStatus());
			rspCode = t20901BO.updateBaseInfTmpTmp(tblMchtBaseInfTmpTmpUpd);
		}
		return rspCode;
	}
	
	// 商户编号
	private String mchntId;
	// 商户拒绝的原因
	private String refuseInfo;
	/**
	 * @return the mchntId
	 */
	public String getMchntId() {
		return mchntId;
	}

	/**
	 * @param mchntId the mchntId to set
	 */
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	/**
	 * @return the refuseInfo
	 */
	public String getRefuseInfo() {
		return refuseInfo;
	}

	/**
	 * @param refuseInfo the refuseInfo to set
	 */
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	/**
	 * @return the t20504BO
	 */
	public T20504BO getT20504BO() {
		return t20504BO;
	}

	/**
	 * @param t20504bo the t20504BO to set
	 */
	public void setT20504BO(T20504BO t20504bo) {
		t20504BO = t20504bo;
	}
	
	/**
	 * @return the tblMchtBaseInfTmpDAO
	 */
	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpDAO the tblMchtBaseInfTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpDAO(ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	/**
	 * @return the tblMchtSettleInfTmpDAO
	 */
	public ITblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}

	/**
	 * @param tblMchtSettleInfTmpDAO the tblMchtSettleInfTmpDAO to set
	 */
	public void setTblMchtSettleInfTmpDAO(
			ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}

	/**
	 * @return the t20901BO
	 */
	public T20901BO getT20901BO() {
		return t20901BO;
	}

	/**
	 * @param t20901bo the t20901BO to set
	 */
	public void setT20901BO(T20901BO t20901bo) {
		t20901BO = t20901bo;
	}

	public TblRiskParamMng getTblRiskParamMng() {
		return tblRiskParamMng;
	}

	public void setTblRiskParamMng(TblRiskParamMng tblRiskParamMng) {
		this.tblRiskParamMng = tblRiskParamMng;
	}
	
}
