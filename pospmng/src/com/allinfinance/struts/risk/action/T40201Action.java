/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-20       first release
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
package com.allinfinance.struts.risk.action;



import com.allinfinance.bo.risk.T40101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.TblRiskInf;
import com.allinfinance.po.TblRiskParamInf;
import com.allinfinance.po.TblRiskParamInfPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:设置风险模型
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-20
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T40201Action extends BaseAction {
	
	T40101BO t40101BO = (T40101BO) ContextUtil.getBean("T40101BO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("start".equals(method)) {
			log("启动模型");
			rspCode = start();
		} else if("stop".equals(method)) {
			log("停止模型");
			rspCode = stop();
		} else if("update".equals(method)) {
			log("修改模型参数");
			rspCode = update();
		} else if("updateLvl".equals(method)) {
			log("风控级别修改");
			rspCode = updateLvl();
		}else {
			return "未知的交易类型";
		}
		return rspCode;
		
		
		
		
	}
	
	private String update() throws Exception {
		// TODO Auto-generated method stub
		jsonBean.parseJSONArrayData(getModelDataList());
		int len = jsonBean.getArray().size();
		TblRiskParamInf tblRiskParamInf=null;
		TblRiskParamInfPK tblRiskParamInfPK =null;
		TblRiskParamInf oldTblRiskParamInf=null;
//		Map<TblRiskLvlPK, String> riskLvlMap=new HashMap<TblRiskLvlPK, String>();
		
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			tblRiskParamInf = new TblRiskParamInf();
			tblRiskParamInfPK = new TblRiskParamInfPK();
			BeanUtils.setObjectWithPropertiesValue(tblRiskParamInfPK,jsonBean,false);
			BeanUtils.setObjectWithPropertiesValue(tblRiskParamInf,jsonBean,false);
			tblRiskParamInfPK.setRiskLvl(StringUtil.isNotEmpty(tblRiskParamInfPK.getRiskLvl())?tblRiskParamInfPK.getRiskLvl().substring(0,1):"*");
			tblRiskParamInf.setId(tblRiskParamInfPK);
			oldTblRiskParamInf = new TblRiskParamInf();
			oldTblRiskParamInf = t40101BO.get(tblRiskParamInfPK);
			if(oldTblRiskParamInf==null)
				return "参数信息不存在，请重新刷新选择！";
			//tblRiskInf.setSaLimitAmount(CommonFunction.transYuanToFen(tblRiskInf.getSaLimitAmount()));
			if(tblRiskParamInf.getParamValue().length()>Integer.parseInt(tblRiskParamInf.getParamLen().toString())){
				if(len>1){
					return "第"+(i+1)+"条参数值的长度大于此参数的上限长度！";
				}else{
					return "此参数值的长度大于此参数的上限长度！";
				}
			}
			String modelKind=tblRiskParamInf.getId().getModelKind();
			String modelSeq=tblRiskParamInf.getId().getModelSeq();
			if(("A00".equals(modelKind)&&"1".equals(modelSeq))||
					("A01".equals(modelKind)&&"1".equals(modelSeq))||
					"A02".equals(modelKind)||
					("A05".equals(modelKind)&&"2".equals(modelSeq))||
					("A06".equals(modelKind)&&"2".equals(modelSeq))||
					("A07".equals(modelKind)&&"2".equals(modelSeq))||
					("A08".equals(modelKind)&&"1".equals(modelSeq))||
					("A09".equals(modelKind)&&"2".equals(modelSeq))||
					("A10".equals(modelKind)&&"1".equals(modelSeq))||
					("A15".equals(modelKind)&&"1".equals(modelSeq))||
					("A16".equals(modelKind)&&"1".equals(modelSeq))||
					("A18".equals(modelKind)&&"1".equals(modelSeq))
					){
				if(tblRiskParamInf.getParamValue().split("\\.").length>1){
					if(len>1){
						return "第"+(i+1)+"条参数值请输入整数！";
					}else{
						return "此参数值请输入整数！";
					}
				}
			}
			
			if(
					("A08".equals(modelKind)&&"2".equals(modelSeq))||
					("A13".equals(modelKind)&&"1".equals(modelSeq))
//					||
//					("A14".equals(modelKind)&&"1".equals(modelSeq))
					
					){
				if(Double.parseDouble(tblRiskParamInf.getParamValue().toString())>1){
					if(len>1){
						return "第"+(i+1)+"条参数值比率不能大于1！";
					}else{
						return "此参数值比率不能大于1！";
					}
				}
			}
			
			
			if(
					("A10".equals(modelKind)&&"2".equals(modelSeq))||
//					("A10".equals(modelKind)&&"3".equals(modelSeq))||
					("A10".equals(modelKind)&&"3".equals(modelSeq))
					
					){
				if(tblRiskParamInf.getParamValue().split("\\.").length>1||Integer.parseInt(tblRiskParamInf.getParamValue().toString())>24){
					if(len>1){
						return "第"+(i+1)+"条参数值请输入00-24之间！";
					}else{
						return "此参数值请输入00-24之间！";
					}
				}
			}
			
			
			t40101BO.update(tblRiskParamInf, oldTblRiskParamInf, operator);
		}
		
		return Constants.SUCCESS_CODE;
	}

	
	
	
	private String updateLvl() throws Exception {
		// TODO Auto-generated method stub
		TblRiskInf tblRiskInf = t40101BO.get(saModelKind);
		if(tblRiskInf==null){
			return "该模型不存在，请重新刷新选择！";
		}
//		tblRiskInf.setSaBeUse(com.huateng.common.RiskConstants.STOP_RISK);
//		tblRiskInf.setMisc(misc);
		return t40101BO.updateLvl(tblRiskInf,operator,misc);
	}

	
	private String stop() throws Exception {
		// TODO Auto-generated method stub
		TblRiskInf tblRiskInf = t40101BO.get(saModelKind);
		if(tblRiskInf==null){
			return "该模型不存在，请重新刷新选择！";
		}
		tblRiskInf.setSaBeUse(com.allinfinance.common.RiskConstants.STOP_RISK);
		return t40101BO.update(tblRiskInf,operator,com.allinfinance.common.RiskConstants.STOP_RISK);
	}

	private String start() throws Exception {
		// TODO Auto-generated method stub
		TblRiskInf tblRiskInf = t40101BO.get(saModelKind);
		if(tblRiskInf==null){
			return "该模型不存在，请重新刷新选择！";
		}
		tblRiskInf.setSaBeUse(com.allinfinance.common.RiskConstants.START_RISK);
		return t40101BO.update(tblRiskInf,operator,com.allinfinance.common.RiskConstants.START_RISK);
	}

	private String saModelKind;
	
	private String saBeUse;
	
	private String misc;
	
	private String modelDataList;

	/**
	 * @return the saModelKind
	 */
	public String getSaModelKind() {
		return saModelKind;
	}

	/**
	 * @param saModelKind the saModelKind to set
	 */
	public void setSaModelKind(String saModelKind) {
		this.saModelKind = saModelKind;
	}

	/**
	 * @return the saBeUse
	 */
	public String getSaBeUse() {
		return saBeUse;
	}

	/**
	 * @param saBeUse the saBeUse to set
	 */
	public void setSaBeUse(String saBeUse) {
		this.saBeUse = saBeUse;
	}

	
	/**
	 * @return the modelDataList
	 */
	public String getModelDataList() {
		return modelDataList;
	}

	/**
	 * @param modelDataList the modelDataList to set
	 */
	public void setModelDataList(String modelDataList) {
		this.modelDataList = modelDataList;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}
	
	
	
}
