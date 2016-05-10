package com.allinfinance.bo.impl.agentpay;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.agentpay.T90502BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.dao.iface.agentpay.TblSndPackDAO;
import com.allinfinance.po.agentpay.TblSndPack;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

public class T90502BOTarget implements T90502BO {
	private TblSndPackDAO tblSndPackDAO;

	
	@Override
	public TblSndPack get(String batchId) {
		// TODO Auto-generated method stub
		return this.tblSndPackDAO.get(batchId);
	}
	@Override
	public String refuse(String batchId, String refuseInfo) {
		TblSndPack tmp = tblSndPackDAO.get(batchId);
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		if(tmp ==null){
			return "没有找到信息，请重试";
		}
		if(!StringUtil.isNull(tmp.getPreAuditTlr())){
			if(tmp.getPreAuditTlr().equals(opr.getOprId())){
				return "同一操作员不能审核！";
			}
		}
		tmp.setAuditFlag(SysParamUtil.getParam(SysParamConstants.CHECKADUIT_REFUSE));
		tmp.setMisc1(refuseInfo); //保留字段1存放拒绝原因
		tmp.setCheckTlr(opr.getOprId());
		tmp.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tmp.setLstUpdTlr(opr.getOprId());
		tblSndPackDAO.update(tmp);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String back(String batchId, String refuseInfo) {
		TblSndPack tmp = tblSndPackDAO.get(batchId);
		
		if(tmp ==null){
			return "没有找到信息，请重试";
		}
		return Constants.SUCCESS_CODE;
	}
	@Override
	public String accept(String batchId) {
		TblSndPack tmp = tblSndPackDAO.get(batchId);
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		if(tmp ==null){
			return "没有找到信息，请重试";
		}
		if(!StringUtil.isNull(tmp.getPreAuditTlr())){
			if(tmp.getPreAuditTlr().equals(opr.getOprId())){
				return "同一操作员不能审核！";
			}
		}
		tmp.setAuditFlag(SysParamUtil.getParam(SysParamConstants.CHECKADUIT_PASS));
		tmp.setCheckTlr(opr.getOprId());
		tmp.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tmp.setLstUpdTlr(opr.getOprId());
		tblSndPackDAO.update(tmp);
		return Constants.SUCCESS_CODE;
	}
	public TblSndPackDAO getTblSndPackDAO() {
		return tblSndPackDAO;
	}

	public void setTblSndPackDAO(TblSndPackDAO tblSndPackDAO) {
		this.tblSndPackDAO = tblSndPackDAO;
	}

}
