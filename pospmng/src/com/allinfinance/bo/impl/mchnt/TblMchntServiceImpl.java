/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-17       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 allinfinance, Inc. All rights reserved.
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
package com.allinfinance.bo.impl.mchnt;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.allinfinance.bo.impl.daytrade.FrontMcht;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.common.TblOprInfoConstants;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.base.TblBrhInfoDAO;
import com.allinfinance.dao.iface.daytrade.WebFrontTxnLogDAO;
import com.allinfinance.dao.iface.mchnt.ITblGroupMchtInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblGroupMchtSettleInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpLogDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpHistDAO;
import com.allinfinance.dao.iface.mchnt.ShTblOprInfoDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntRefuseDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamMngDAO;
import com.allinfinance.dao.iface.term.TblTermInfDAO;
import com.allinfinance.dao.iface.term.TblTermInfTmpDAO;
import com.allinfinance.dao.iface.term.TblTermKeyDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpHistDAO;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblMchntRefuse;
import com.allinfinance.po.TblMchntRefusePK;
import com.allinfinance.po.TblMchtBaseInfTmpLog;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.mchnt.TblGroupMchtInf;
import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpHist;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpHistPK;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtCashInf;
import com.allinfinance.po.mchnt.TblMchtCashInfTmp;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpHist;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpHistPK;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.po.risk.TblRiskParamMngPK;
import com.allinfinance.struts.pos.EposMisc;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.Encryption;
import com.allinfinance.system.util.FileFilter;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.StatusUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:终审
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class TblMchntServiceImpl implements TblMchntService {
	private ITblMchtSettleInfTmpHistDAO tblMchtSettleInfTmpHistDAO;
	private ITblMchtBaseInfTmpHistDAO tblMchtBaseInfTmpHistDAO;
	private TblRiskParamMngDAO tblRiskParamMngDAO;
	private static Logger log = Logger.getLogger(TblMchntServiceImpl.class);
	private ITblMchtCashInfTmpTmpDAO cashInfTmpTmpDAO;
	private ITblMchtCashInfTmpDAO cashInfTmpDAO;
	private ITblMchtCashInfDAO cashInfDAO;
	/**
	 * @return the cashInfTmpTmpDAO
	 */
	public ITblMchtCashInfTmpTmpDAO getCashInfTmpTmpDAO() {
		return cashInfTmpTmpDAO;
	}

	/**
	 * @param cashInfTmpTmpDAO the cashInfTmpTmpDAO to set
	 */
	public void setCashInfTmpTmpDAO(ITblMchtCashInfTmpTmpDAO cashInfTmpTmpDAO) {
		this.cashInfTmpTmpDAO = cashInfTmpTmpDAO;
	}

	/**
	 * @return the cashInfTmpDAO
	 */
	public ITblMchtCashInfTmpDAO getCashInfTmpDAO() {
		return cashInfTmpDAO;
	}

	/**
	 * @param cashInfTmpDAO the cashInfTmpDAO to set
	 */
	public void setCashInfTmpDAO(ITblMchtCashInfTmpDAO cashInfTmpDAO) {
		this.cashInfTmpDAO = cashInfTmpDAO;
	}

	/**
	 * @return the cashInfDAO
	 */
	public ITblMchtCashInfDAO getCashInfDAO() {
		return cashInfDAO;
	}

	/**
	 * @param cashInfDAO the cashInfDAO to set
	 */
	public void setCashInfDAO(ITblMchtCashInfDAO cashInfDAO) {
		this.cashInfDAO = cashInfDAO;
	}

	public TblTermKeyDAO tblTermKeyDAO;
	/**
	 * @return the tblTermKeyDAO
	 */
	public TblTermKeyDAO getTblTermKeyDAO() {
		return tblTermKeyDAO;
	}

	/**
	 * @param tblTermKeyDAO the tblTermKeyDAO to set
	 */
	public void setTblTermKeyDAO(TblTermKeyDAO tblTermKeyDAO) {
		this.tblTermKeyDAO = tblTermKeyDAO;
	}

	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(
			ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	public ITblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	public void setTblMchtBaseInfDAO(ITblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	public ITblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}

	public void setTblMchtSettleInfTmpDAO(
			ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}

	public ITblMchtSettleInfDAO getTblMchtSettleInfDAO() {
		return tblMchtSettleInfDAO;
	}

	public void setTblMchtSettleInfDAO(ITblMchtSettleInfDAO tblMchtSettleInfDAO) {
		this.tblMchtSettleInfDAO = tblMchtSettleInfDAO;
	}

	public TblMchntRefuseDAO getTblMchntRefuseDAO() {
		return tblMchntRefuseDAO;
	}

	public void setTblMchntRefuseDAO(TblMchntRefuseDAO tblMchntRefuseDAO) {
		this.tblMchntRefuseDAO = tblMchntRefuseDAO;
	}

	public ITblGroupMchtInfDAO getTblGroupMchtInfDAO() {
		return tblGroupMchtInfDAO;
	}

	public void setTblGroupMchtInfDAO(ITblGroupMchtInfDAO tblGroupMchtInfDAO) {
		this.tblGroupMchtInfDAO = tblGroupMchtInfDAO;
	}

	public ITblGroupMchtSettleInfDAO getTblGroupMchtSettleInfDAO() {
		return tblGroupMchtSettleInfDAO;
	}

	public void setTblGroupMchtSettleInfDAO(ITblGroupMchtSettleInfDAO tblGroupMchtSettleInfDAO) {
		this.tblGroupMchtSettleInfDAO = tblGroupMchtSettleInfDAO;
	}

	/**
	 * @return the shTblOprInfoDAO
	 */
	public ShTblOprInfoDAO getShTblOprInfoDAO() {
		return shTblOprInfoDAO;
	}

	/**
	 * @param shTblOprInfoDAO the shTblOprInfoDAO to set
	 */
	public void setShTblOprInfoDAO(ShTblOprInfoDAO shTblOprInfoDAO) {
		this.shTblOprInfoDAO = shTblOprInfoDAO;
	}

	
	public ITblMchtBaseInfTmpLogDAO getiTblMchtBaseInfTmpLogDAO() {
		return iTblMchtBaseInfTmpLogDAO;
	}

	
	public void setiTblMchtBaseInfTmpLogDAO(ITblMchtBaseInfTmpLogDAO iTblMchtBaseInfTmpLogDAO) {
		this.iTblMchtBaseInfTmpLogDAO = iTblMchtBaseInfTmpLogDAO;
	}

	public ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;

	public ITblMchtBaseInfDAO tblMchtBaseInfDAO;

	public ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;
	
	public ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO;

	public ITblMchtSettleInfDAO tblMchtSettleInfDAO;

	public TblMchntRefuseDAO tblMchntRefuseDAO;

	public ITblGroupMchtInfDAO tblGroupMchtInfDAO;

	public ITblGroupMchtSettleInfDAO tblGroupMchtSettleInfDAO;
	
	public ShTblOprInfoDAO shTblOprInfoDAO;
	public ITblMchtBaseInfTmpLogDAO iTblMchtBaseInfTmpLogDAO;
	
	public WebFrontTxnLogDAO webFrontTxnLogDAO;
	public ICommQueryDAO commQueryDAO;
	
	public TblTermInfDAO tblTermInfDAO;
	public TblTermInfTmpDAO tblTermInfTmpDAO;
	

	/**
	 * @return the webFrontTxnLogDAO
	 */
	public WebFrontTxnLogDAO getWebFrontTxnLogDAO() {
		return webFrontTxnLogDAO;
	}

	/**
	 * @param webFrontTxnLogDAO the webFrontTxnLogDAO to set
	 */
	public void setWebFrontTxnLogDAO(WebFrontTxnLogDAO webFrontTxnLogDAO) {
		this.webFrontTxnLogDAO = webFrontTxnLogDAO;
	}

	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	/*
	 * 保存商户信息至临时表
	 * 
	 * @see
	 * com.allinfinance.bo.impl.mchnt.TblMchntService#saveTmp(com.allinfinance.po.mchnt
	 * .TblMchtBaseInfTmp, as.allinfinance.po.management.mer.TblMchtSettleInfTmp)
	 */
	public String saveTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
			TblMchtSettleInfTmp tblMchtSettleInfTmp) {

		if(tblMchtBaseInfTmpDAO.get(tblMchtBaseInfTmp.getMchtNo()) != null) {
			return "您自定义的商户号已经存在";
		}
		tblMchtBaseInfTmpDAO.save(tblMchtBaseInfTmp);
		tblMchtSettleInfTmpDAO.save(tblMchtSettleInfTmp);

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 更新商户信息至临时表
	 * 
	 * @see
	 * com.allinfinance.bo.impl.mchnt.TblMchntService#updateTmp(com.allinfinance.po.mchnt
	 * .TblMchtBaseInfTmp, com.allinfinance.po.mchnt.TblMchtSettleInfTmp)
	 */
	public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
			TblMchtSettleInfTmp tblMchtSettleInfTmp) {

		tblMchtBaseInfTmpDAO.update(tblMchtBaseInfTmp);
		tblMchtSettleInfTmpDAO.update(tblMchtSettleInfTmp);

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 商户审核通过
	 * 
	 * @see com.allinfinance.bo.impl.mchnt.TblMchntService#accept(java.lang.String)
	 */
	public String accept(String mchntId,TblMchtBaseInfTmp tmp,TblMchtSettleInfTmp tmpSettle,TblMchtBaseInf inf,TblMchtSettleInf infSettle, TblRiskParamMng tblRiskParamMng) throws Exception {

//		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
//		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
//		if (null == tmp || null == tmpSettle) {
//			return "没有找到商户的临时信息，请重试";
//		}
		//生成新的商户号
		String newMchtNo ;
		if(mchntId.contains("AAA")){
			String idStr = "848" + tmp.getAreaNo().trim() + tmp.getMcc().trim();
			newMchtNo = GenerateNextId.getMchntId(idStr);
			newMchtNo=StringUtils.trim(newMchtNo);
			
		}else{
			newMchtNo=mchntId;
		}
		System.out.println("新的商户号为：" + newMchtNo);
		
		//更新前的状态
		String status = tmp.getMchtStatus();
		//临时表中的cre时间
//		String crtDateTmp= tmp.getRecCrtTs();
		//临时表最后修改时间
		String upDateTmp=tmp.getRecUpdTs();
		//临时表的最后修改人
		String oprid=tmp.getUpdOprId();
		if(null==oprid){
			oprid=tmp.getCrtOprId();			
		}

		// 取得原始信息
//		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(newMchtNo);
//		TblMchtSettleInf infSettle = tblMchtSettleInfDAO.get(newMchtNo);
		//正式表中cre日期
		String crtDate= "0";
		
		if (null != inf) {
			inf.setMchtStatus("0");
			crtDate= inf.getRecCrtTs();
		}
		if (null == inf) {
			inf = new TblMchtBaseInf();
		}
		if (null == infSettle) {
			infSettle = new TblMchtSettleInf();
		}
		
		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		tmp.setUpdOprId(opr.getOprId());
		tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		
		// 获得下一状态
		tmp.setMchtStatus(StatusUtil.getNextStatus("A."
				+ status));	
		
		// 记录退回信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(newMchtNo,
				CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo("终审通过");
		refuse.setBrhId(tmp.getBankNo());
		refuse.setOprId(opr.getOprId());

		// 获得退回信息
		refuse.setRefuseType("终审通过");
		
		//插入日志
		Object[] ret = insertTblMchtBaseInfTmpLog( tmp, inf, tmpSettle,infSettle,  status, upDateTmp, oprid);
		String retMsg = Constants.SUCCESS_CODE;
		/*
		 * 判断商户是否在账户系统开户
		
		 */
		String txnCode = "";
		boolean isOpen=(Boolean) ret[1];
		if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(status) || TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(status)){
			txnCode = FrontMcht.TXN_CODE_ADDACCT;
			isOpen = true;
		}
		if(!TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(status) && !TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(status)){
			if(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(status)){
				txnCode = FrontMcht.TXN_CODE_UPDACCT;
			}else if(TblMchntInfoConstants.MCHNT_ST_DEL_UNCK.equals(status)){
				txnCode = FrontMcht.TXN_CODE_DELACCT;
				isOpen = true;
			}
		}
		if(isOpen){
			retMsg = optMchtAcct(inf, infSettle, txnCode);
		}
		
		//==========虚拟账户调用============

		log.info("虚拟账户：商户开户及信息变更接口调用开始。");
		retMsg = this.sendMessage(newMchtNo,tmp,inf, tmpSettle,infSettle);	
		log.info("虚拟账户返回值:"+retMsg);
		if(!Constants.SUCCESS_CODE.equals(retMsg)){
			return retMsg;
		}
		log.info("虚拟账户：商户开户及信息变更接口调用结束。");
		//商户风控参数
				TblRiskParamMngPK key = new TblRiskParamMngPK();
				key.setMchtId(newMchtNo);
				key.setTermId(CommonFunction.fillString("00000000", ' ', 12, true));
				key.setRiskType("0");
				tblRiskParamMng.setId(key);
				tblRiskParamMngDAO.saveOrUpdate(tblRiskParamMng);
		//=============================
		
		// 复制新的信息，复制的是数据集，与终端审核时clone函数不同，clone复制的是数据库数据
//		if(mchntId.contains("AAA")){
		BeanUtils.copyProperties(tmp, inf);
		BeanUtils.copyProperties(tmpSettle, infSettle);
//		}
	log.info("提现标志位："+inf.getCashFlag());
		//提现
	if("1".equals(inf.getCashFlag())){
		if(mchntId.contains("AAA")){
			TblMchtCashInfTmpTmp tblMchtCashInfTmpTmp = cashInfTmpTmpDAO.get(mchntId);
			TblMchtCashInfTmp tblMchtCashInfTmp = cashInfTmpDAO.get(mchntId);
			if(tblMchtCashInfTmp != null && tblMchtCashInfTmpTmp != null ){
				tblMchtCashInfTmp.setUpdOpr(inf.getUpdOprId());
				tblMchtCashInfTmp.setUpdTime(CommonFunction.getCurrentDateTime());
				TblMchtCashInf cashInf = new TblMchtCashInf(newMchtNo);
				TblMchtCashInfTmp cashInfTmp = new TblMchtCashInfTmp(newMchtNo);
				TblMchtCashInfTmpTmp cashInfTmpTmp = new TblMchtCashInfTmpTmp(newMchtNo);
				BeanUtils.copyProperties(tblMchtCashInfTmp, cashInf,new String[]{"mchtId"});
				BeanUtils.copyProperties(tblMchtCashInfTmp, cashInfTmp,new String[]{"mchtId"});
				BeanUtils.copyProperties(tblMchtCashInfTmp, cashInfTmpTmp,new String[]{"mchtId"});
				cashInfTmpDAO.delete(tblMchtCashInfTmp);
				cashInfTmpTmpDAO.delete(tblMchtCashInfTmpTmp);
				cashInfDAO.saveOrUpdate(cashInf);
				cashInfTmpDAO.saveOrUpdate(cashInfTmp);
				cashInfTmpTmpDAO.saveOrUpdate(cashInfTmpTmp);
			}
		}else{
			TblMchtCashInfTmp tblMchtCashInfTmp = cashInfTmpDAO.get(mchntId);
			if(tblMchtCashInfTmp != null){
				tblMchtCashInfTmp.setUpdOpr(inf.getUpdOprId());
				tblMchtCashInfTmp.setUpdTime(CommonFunction.getCurrentDateTime());
				TblMchtCashInf cashInf = new TblMchtCashInf(newMchtNo);
				BeanUtils.copyProperties(tblMchtCashInfTmp, cashInf);
				cashInfDAO.saveOrUpdate(cashInf);
			}
		}
	}
		//如果是新增终审通过，更新创建日期记录入网日期
		if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(status) || TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(status)){
			inf.setRecCrtTs(CommonFunction.getCurrentDateTime());//入网日期
		}
		//如果不是新增，crt日期不变
		if(!TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(status) && !TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(status)){
			inf.setRecCrtTs(crtDate);
		}
		
		if(Constants.SUCCESS_CODE.equals(retMsg)){
			
			if (status.equals(TblMchntInfoConstants.MCHNT_ST_DEL_UNCK)) {//注销待审核同步更新到终端表
				String update0 = "update tbl_term_inf set TERM_STA = '7' where MCHT_CD = '" + tmp.getMchtNo() + "'";
				String update1 = "update tbl_term_inf_tmp set TERM_STA = '7' where MCHT_CD = '" + tmp.getMchtNo() + "'";
				commQueryDAO.excute(update0);
				commQueryDAO.excute(update1);
			} else if (status.equals(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK)) {//停用待审核同步更新到终端表
				String update0 = "update tbl_term_inf set TERM_STA = '4' where MCHT_CD = '" + tmp.getMchtNo() + "'";
				String update1 = "update tbl_term_inf_tmp set TERM_STA = '4' where MCHT_CD = '" + tmp.getMchtNo() + "'";
				String update2 = "update TBL_ALARM_MCHT set BLOCK_MCHT_FLAG='1' where CARD_ACCP_ID='"+tmp.getMchtNo()+"' ";
				commQueryDAO.excute(update0);
				commQueryDAO.excute(update1);
				commQueryDAO.excute(update2);
			}else if (status.equals(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK)) {//修改待审核（所属机构）
				if(!tmp.getBankNo().equals(inf.getBankNo())){
					String update0 = "update BTH_CHK_TXN_SUSPS set stlm_inst='"+tmp.getBankNo()+"' where card_accp_id='" + tmp.getMchtNo() + "'";
					String update1 = "update BTH_CHK_TXN set stlm_inst='"+tmp.getBankNo()+"' where card_accp_id = '" + tmp.getMchtNo() + "'";
					String update2 = "update tbl_mchnt_infile_dtl set brh_code='"+tmp.getBankNo()+"' where mcht_no = '" + tmp.getMchtNo() + "'";
					String update3 = "update tbl_algo_dtl set brh_ins_id_cd='"+tmp.getBankNo()+"' where mcht_cd = '" + tmp.getMchtNo() + "'";
					String update4 = "update tbl_term_algo_sum set brh_ins_id_cd='"+tmp.getBankNo()+"' where mcht_cd = '" + tmp.getMchtNo() + "'";
					String update5 = "update TBL_TERM_INF set TERM_BRANCH='"+tmp.getBankNo()+"' where mcht_cd = '" + tmp.getMchtNo() + "'";
					String update6 = "update TBL_TERM_INF_TMP set TERM_BRANCH='"+tmp.getBankNo()+"' where mcht_cd = '" + tmp.getMchtNo() + "'";
					commQueryDAO.excute(update0);
					commQueryDAO.excute(update1);
					commQueryDAO.excute(update2);
					commQueryDAO.excute(update3);
					commQueryDAO.excute(update4);
					commQueryDAO.excute(update5);
					commQueryDAO.excute(update6);
				}
			}else if (status.equals(TblMchntInfoConstants.MCHNT_ST_RCV_UNCK)) {//恢复待审核同步更新到 风控商户
				String update0 = "update TBL_ALARM_MCHT set BLOCK_MCHT_FLAG='0' where CARD_ACCP_ID='"+tmp.getMchtNo()+"' ";
				commQueryDAO.excute(update0);
			}
			

			// 将审批记录里的旧商户ID都改成新的商户ID，方便审批痕迹画面查看
			if(mchntId.contains("AAA")){
				String updateRefuse = "update tbl_Mchnt_Refuse set mchnt_id='" + newMchtNo + "' where mchnt_id='" + mchntId + "'";
				commQueryDAO.excute(updateRefuse);
			}
			//将结算类型放入TBL_INF_DISC_CD.DISC_NM用作商户结算类型
			String updateDiscCd="UPDATE TBL_INF_DISC_CD SET DISC_NM='"+tmp.getMchtFunction().trim()+"',REC_UPD_TS='"+CommonFunction.getCurrentDateTime()+"' WHERE DISC_CD='"+tmpSettle.getFeeRate().trim()+"'";
			commQueryDAO.excute(updateDiscCd);
			String updateHisDisc="UPDATE TBL_HIS_DISC_ALGO A SET A.REC_UPD_TS='"+CommonFunction.getCurrentDateTime()+"' WHERE A.DISC_ID='"+tmpSettle.getFeeRate().trim()+"'";
			commQueryDAO.excute(updateHisDisc);
			if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(status)){
				//添加终审的时候，如果为添加待终审向TBL_INF_DISC_CD_MIRROR,TBL_HIS_DISC_ALGO_MIRROR表插入数据
				String updateDiscMirror="INSERT INTO TBL_INF_DISC_CD_MIRROR A SELECT * FROM TBL_INF_DISC_CD B WHERE B.DISC_CD='"+tmpSettle.getFeeRate().trim()+"'";
				commQueryDAO.excute(updateDiscMirror);
				String updateHisDiscMirror="INSERT INTO TBL_HIS_DISC_ALGO_MIRROR A SELECT * FROM TBL_HIS_DISC_ALGO B WHERE B.DISC_ID='"+tmpSettle.getFeeRate().trim()+"'";
				commQueryDAO.excute(updateHisDiscMirror);
			}
			
			//插入日志
			iTblMchtBaseInfTmpLogDAO.saveOrUpdate((TblMchtBaseInfTmpLog) ret[0]);
			if(FrontMcht.TXN_CODE_ADDACCT.equals(txnCode)){// 系统自动生成此商户操作员
				addOprInfo(newMchtNo);
			}
			
			//List<TblTermKey> termKeyList=tblTermKeyDAO.getByMchnt(mchntId);
//			if(termKeyList!=null&&termKeyList.size()!=0){
				
//				for (TblTermKey tblTermKey : termKeyList) {
					//更新商户号 和更新时间
					List<TblTermInfTmp> list = null;
					String currentDateTime = CommonFunction.getCurrentDateTime();
					if(!"4".equals(status)){
						list = tblTermInfTmpDAO.getByMchntAll(mchntId);
						tblTermKeyDAO.updateByTremId(mchntId,newMchtNo,currentDateTime);
					}
//					String termId = tblTermKey.getId().getTermId();
//					tblTermKey.setRecUpdTs(CommonFunction.getCurrentDateTime());
//				}
//			}
			//将所有termkey的更新时间改下
			// 商户终审通过终端审核
			if(list!=null&&list.size()!=0){
				for (TblTermInfTmp tblTermInfTmp : list) {
					
					TblTermInf termInf;
					
					//如果不是新添加的，将正式表里的部分值赋进去
					TblTermInf tblTermInfOld = tblTermInfDAO.get(tblTermInfTmp.getId().getTermId());
					if(tblTermInfOld!=null){
						tblTermInfOld= (TblTermInf) tblTermInfTmp.cloneHasExists(tblTermInfOld);
						termInf=tblTermInfOld;
						tblTermInfTmp.setMisc1(tblTermInfOld.getMisc1());
						tblTermInfTmp.setProductCd(tblTermInfOld.getProductCd());
					}else {
						termInf=new TblTermInf();
						termInf = (TblTermInf) tblTermInfTmp.clone();
					}
					termInf.setTermSerialNum(tblTermInfTmp.getTermSerialNum());
					termInf.setTermPara(tblTermInfTmp.getTermPara().replaceAll("\\|", ""));
					if(tblTermInfTmp.getMisc2() != null && !tblTermInfTmp.getMisc2().equals(""))
					{
						EposMisc epos = new EposMisc(termInf.getMisc2());
						epos.setVersion(tblTermInfTmp.getMisc2());
						termInf.setMisc2(epos.toString());
					}
					//由8字节改为14字节数据的格式有日期格式YYYYMMDD改为 YYYYMMDDHHMMSS
					//tblTermInf.setRecUpdTs(CommonFunction.getCurrentDate());
					termInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
					Operator opra = (Operator) ServletActionContext.getRequest()
							.getSession().getAttribute(Constants.OPERATOR_INFO);
					termInf.setRecUpdOpr(opra.getOprId());
					//mis中将删除的终端状态改为7---注销
					if(!"7".equals(tblTermInfTmp.getTermSta())){
						termInf.setTermSta("1");
						tblTermInfTmp.setTermSta("1");
					}
					
					tblTermInfTmp.setRecUpdOpr(opra.getOprId());
					tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
					tblTermInfTmp.setMchtCd(newMchtNo);
					tblTermInfTmpDAO.update(tblTermInfTmp);
					
					termInf.setMchtCd(newMchtNo);
					tblTermInfDAO.saveOrUpdate(termInf);
					if(mchntId.contains("AAA")){
						//给终端添加默认的风控参数
						TblRiskParamMng termRiskParamMngNew = new TblRiskParamMng();
						TblRiskParamMngPK riskParamMngPK = new TblRiskParamMngPK(newMchtNo,tblTermInfTmp.getId().getTermId(),"1");
						TblRiskParamMng termRiskParamMng = tblRiskParamMngDAO.get(mchntId, CommonFunction.fillString(tblTermInfTmp.getId().getTermId(),' ',12, true), "1");
						if(termRiskParamMng == null){
							termRiskParamMngNew.setId(riskParamMngPK);
							termRiskParamMngNew.setCreditDayAmt(0.0);
							termRiskParamMngNew.setCreditMonAmt(0.0);
							termRiskParamMngNew.setCreditSingleAmt(tblRiskParamMng.getCreditSingleAmt());
							termRiskParamMngNew.setDebitDayAmt(0.0);
							termRiskParamMngNew.setDebitMonAmt(0.0);
							termRiskParamMngNew.setDebitSingleAmt(tblRiskParamMng.getDebitSingleAmt());
							tblRiskParamMngDAO.saveOrUpdate(termRiskParamMngNew);
						}else {
							termRiskParamMngNew = (TblRiskParamMng) org.apache.commons.beanutils.BeanUtils.cloneBean(termRiskParamMng);
							termRiskParamMngNew.setId(riskParamMngPK);
							tblRiskParamMngDAO.delete(termRiskParamMng);
							tblRiskParamMngDAO.saveOrUpdate(termRiskParamMngNew);
						}
					}
					
				}
			}////
			
			//更新历史表的商户号
			if(!mchntId.equals(newMchtNo)){
				//List<TblMchtBaseInfTmpHist> histList=tblMchtBaseInfTmpHistDAO.getByMchtNo(mchntId);
				tblMchtBaseInfTmpHistDAO.updateByMchtNo(newMchtNo,mchntId);
				tblMchtSettleInfTmpHistDAO.updateByMchtNo(newMchtNo,mchntId);
			}
			// 更新到数据库
			if(mchntId.contains("AAA")){
			tblMchtBaseInfTmpDAO.delete(mchntId);
			tblMchtSettleInfTmpDAO.delete(mchntId);
			}
//			tblMchtBaseInfDAO.delete(mchntId);
//			tblMchtSettleInfDAO.delete(mchntId);
			
			if (tmp.getMchtNo().contains("AAA")) {
				String sql1 = "update TBL_MCHT_BASE_INF_TMP_HIST set MCHT_NO='"+inf.getMchtNo()+"' where MCHT_NO = '" + tmp.getMchtNo() + "'";
				String sql2 = "update TBL_MCHT_SETTLE_INF_TMP_HIST set MCHT_NO='"+inf.getMchtNo()+"' where MCHT_NO = '" + tmpSettle.getMchtNo() + "'";
				commQueryDAO.excute(sql1);
				commQueryDAO.excute(sql2);
			}
			
			if(mchntId.contains("AAA")){ //设置开户成功标志
				inf.setOpenVirtualAcctFlag("1");
				tmp.setOpenVirtualAcctFlag("1");
			}
			inf.setMchtNo(newMchtNo);
			infSettle.setMchtNo(newMchtNo);
			tmp.setMchtNo(newMchtNo);
			tmpSettle.setMchtNo(newMchtNo);
			
			tblMchtSettleInfDAO.saveOrUpdate(infSettle);
			tblMchtBaseInfTmpDAO.saveOrUpdate(tmp);
			tblMchtBaseInfDAO.saveOrUpdate(inf);
			tblMchtSettleInfTmpDAO.saveOrUpdate(tmpSettle);
			tblMchntRefuseDAO.save(refuse);
			
			// 旧的商户图片文件夹图片转入新的文件夹
			String basePath = SysParamUtil
					.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			String basePathOld = basePath + mchntId + "/";
			for (int i = 0; i < 11; i++) {
				String upload="upload";
				if(i==0){
					upload+="/";
				}else upload=upload+i+"/";
			File deleteFile = new File(basePathOld+upload);
			if (deleteFile.exists()) {
				String basePathNew = basePath
						+ newMchtNo + "/"+upload;
				File writeFile = new File(basePathNew);
				if (!writeFile.exists()) {
					writeFile.mkdirs();
				}
				
					
					FileFilter filter = new FileFilter(mchntId);
					File[] files = deleteFile.listFiles(filter);
					for (File file : files) {
						file.renameTo(new File(basePathNew
								+ file.getName().replaceAll(
										mchntId,
										newMchtNo)));// 文件移动
					}
					
					deleteFile.delete();
				}
					// 删除旧的空图片文件夹
			
			}
			return Constants.SUCCESS_CODE;
		}
		return retMsg;
		
	}

	/**
	 * @return the tblMchtBaseInfTmpHistDAO
	 */
	public ITblMchtBaseInfTmpHistDAO getTblMchtBaseInfTmpHistDAO() {
		return tblMchtBaseInfTmpHistDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpHistDAO the tblMchtBaseInfTmpHistDAO to set
	 */
	public void setTblMchtBaseInfTmpHistDAO(
			ITblMchtBaseInfTmpHistDAO tblMchtBaseInfTmpHistDAO) {
		this.tblMchtBaseInfTmpHistDAO = tblMchtBaseInfTmpHistDAO;
	}

	/*
	 * 商户审核退回
	 * 
	 * @see com.allinfinance.bo.impl.mchnt.TblMchntService#back(java.lang.String,
	 * java.lang.String)
	 */
	public String back(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {

		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
		//添加退回原因
		tmp.setReserved("回退原因："+refuseInfo);
		
		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);

		// 记录退回信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo(refuseInfo);
		refuse.setBrhId(tmp.getBankNo());
		refuse.setOprId(opr.getOprId());

		// 获得退回信息
		refuse.setRefuseType(StatusUtil.getNextStatus("BM."+ tmp.getMchtStatus()));
		//更改终端状态
		List<TblTermInfTmp> list =null;
		if(!"4".equals(tmp.getMchtStatus())){
			list = tblTermInfTmpDAO.getByMchnt(mchntId);
		}
		// 获得下一状态
		tmp.setMchtStatus(StatusUtil.getNextStatus("B."
				+ tmp.getMchtStatus()));
		if(list!=null&&list.size()!=0){
			for (TblTermInfTmp tblTermInfTmp : list) {
				//由8字节改为14字节数据的格式有日期格式YYYYMMDD改为 YYYYMMDDHHMMSS
				//tblTermInf.setRecUpdTs(CommonFunction.getCurrentDate());
				Operator opra = (Operator) ServletActionContext.getRequest()
						.getSession().getAttribute(Constants.OPERATOR_INFO);
				tblTermInfTmp.setRecUpdOpr(opra.getOprId());
				tblTermInfTmp.setTermSta("8");
				
				if(tblTermInfTmp != null){
					tblTermInfTmp.setTermSta("8");
				}
				tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblTermInfTmpDAO.update(tblTermInfTmp);
				
			}
		}////
		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tmp.setUpdOprId(opr.getOprId());

		//20160321修改：实现禅道277任务需求，终审退回后将退回原因报存。  --yww 
		TblMchtBaseInfTmpTmp tmptmp = tblMchtBaseInfTmpTmpDAO.get(mchntId);
		tmptmp.setReserved("回退原因："+refuseInfo);
		/*tmptmp.setMchtStatus(StatusUtil.getNextStatus("B."+ tmptmp.getMchtStatus()));// 获得下一状态
		tmptmp.setRecUpdTs(CommonFunction.getCurrentDateTime());// 更新时间和柜员
		tmptmp.setUpdOprId(opr.getOprId());*/
		// 更新到数据库
		tblMchtBaseInfTmpTmpDAO.update(tmptmp);
		
		// 更新到数据库
		tblMchtBaseInfTmpDAO.update(tmp);
		tblMchntRefuseDAO.save(refuse);
		updHisSta(tmp);
		return Constants.SUCCESS_CODE;
	}
	/**
	 * 修改商户历史状态
	 */
	public void updHisSta(TblMchtBaseInfTmp tmp){
		Operator opra = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		String sql = "select MAX(to_number(MCHT_NO_NEW)) from TBL_MCHT_BASE_INF_TMP_HIST where MCHT_NO = '" + tmp.getMchtNo() + "' ";
		List<BigDecimal> mchtHistSerail = commQueryDAO.findBySQLQuery(sql);
		String mchtNoNew;
		if(null!=mchtHistSerail &&mchtHistSerail.get(0)!=null&& mchtHistSerail.size()>0){
			String serail =  mchtHistSerail.get(0).toString();
			mchtNoNew = String.valueOf(serail);
			TblMchtBaseInfTmpHistPK key=new TblMchtBaseInfTmpHistPK(tmp.getMchtNo(), mchtNoNew);
			TblMchtBaseInfTmpHist tblMchtBaseInfTmpHist = tblMchtBaseInfTmpHistDAO.get(key);
			if(tblMchtBaseInfTmpHist!=null){
				tblMchtBaseInfTmpHist.setMchtStatus(tmp.getMchtStatus());
				tblMchtBaseInfTmpHist.setUpdOprId(opra.getOprId());
				tblMchtBaseInfTmpHistDAO.update(tblMchtBaseInfTmpHist);
			}
		}
	}
	/*
	 * 商户审核拒绝
	 * 
	 * @see com.allinfinance.bo.impl.mchnt.TblMchntService#refuse(java.lang.String,
	 * java.lang.String)
	 */
	public String refuse(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {

		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
		
		String crtDateTmp= tmp.getRecCrtTs();
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		// 记录拒绝信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo(refuseInfo);
		refuse.setBrhId(tmp.getBankNo());
		refuse.setOprId(opr.getOprId());
		
		//更改终端状态
		List<TblTermInfTmp> list = tblTermInfTmpDAO.getByMchnt(mchntId);
		if(list!=null&&list.size()!=0){
			for (TblTermInfTmp tblTermInfTmp : list) {
				if(tblTermInfTmp == null){
					continue;
				}
				//由8字节改为14字节数据的格式有日期格式YYYYMMDD改为 YYYYMMDDHHMMSS
				//tblTermInf.setRecUpdTs(CommonFunction.getCurrentDate());
				Operator opra = (Operator) ServletActionContext.getRequest()
						.getSession().getAttribute(Constants.OPERATOR_INFO);
				tblTermInfTmp.setRecUpdOpr(opra.getOprId());
				tblTermInfTmp.setTermSta("8");
				tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
				tblTermInfTmpDAO.update(tblTermInfTmp);
				
			}
		}
		// 获得拒绝信息
		refuse.setRefuseType(StatusUtil.getNextStatus("RM."+ tmp.getMchtStatus()));

		// 分别处理新增拒绝和修改拒绝
		if (TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(tmp.getMchtStatus()) || TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(tmp.getMchtStatus())|| TblMchntInfoConstants.MCHNT_ST_MODI_UNCK_SECOND.equals(tmp.getMchtStatus())) {
//			tblMchtBaseInfTmpDAO.delete(tmp);
//			tblMchtSettleInfTmpDAO.delete(tmpSettle);
//			tblMchntRefuseDAO.save(refuse);
//			ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
//			commQueryDAO.excute("delete from tbl_term_inf_tmp where mcht_cd='" + mchntId + "'");
			tmp.setMchtStatus("C");
			tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tmp.setUpdOprId(opr.getOprId());
			tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tblMchtBaseInfTmpDAO.update(tmp);
			tblMchtSettleInfTmpDAO.update(tmpSettle);
			tblMchntRefuseDAO.save(refuse);
		} else if(TblMchntInfoConstants.MCHNT_ST_BULK_IPT_UNCK.equals(tmp.getMchtStatus())){
			tblMchtBaseInfTmpDAO.delete(tmp);
			tblMchtSettleInfTmpDAO.delete(tmpSettle);
			tblMchntRefuseDAO.save(refuse);
		} else {
			// 取得原始信息
			TblMchtBaseInf inf = tblMchtBaseInfDAO.get(tmp.getMchtNo());
			TblMchtSettleInf infSettle = tblMchtSettleInfDAO.get(tmpSettle.getMchtNo());
			if (null == inf || null == infSettle) {
				return "没有找到商户的正式信息，请重试";
			} else {
				// 更新时间和柜员
				inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
				inf.setUpdOprId(opr.getOprId());
				// 复制新的信息
				BeanUtils.copyProperties(inf, tmp);
				BeanUtils.copyProperties(infSettle, tmpSettle);
				tmp.setRecCrtTs(crtDateTmp);
				// 更新到数据库
				tblMchtBaseInfTmpDAO.update(tmp);
				tblMchtBaseInfDAO.update(inf);
				tblMchtSettleInfTmpDAO.update(tmpSettle);
				tblMchtSettleInfDAO.update(infSettle);
				tblMchntRefuseDAO.save(refuse);
				updHisSta(tmp);
			}
		}
		return Constants.SUCCESS_CODE;
	}

	/*
	 * 保存集团商户信息
	 * 
	 * @see com.allinfinance.bo.impl.mchnt.TblMchntService#saveGroup()
	 */
	public String saveGroup(TblGroupMchtInf inf, TblGroupMchtSettleInf acctinf)
			throws IllegalAccessException, InvocationTargetException {

		tblGroupMchtInfDAO.save(inf);

		// 缺表暂时屏蔽
		 tblGroupMchtSettleInfDAO.save(acctinf);

		return Constants.SUCCESS_CODE;
	}

	public TblMchtBaseInf getMccByMchntId(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		if(StringUtil.isNull(mchntId)){
			return null;
		}
		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(mchntId);
		if (null == inf) {
			return null;
		} else {
			return inf;
		}
	}

	/*
	 * 获取集团商户基本信息
	 * 
	 * @see
	 * com.allinfinance.bo.impl.mchnt.TblMchntService#getGroupInf(java.lang.String)
	 */
	public TblGroupMchtInf getGroupInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		TblGroupMchtInf inf = tblGroupMchtInfDAO.get(StringUtil.fillValue(
				mchntId, 8, ' '));

		return inf;
	}

	/*
	 * 更新集团商户
	 * 
	 * @seecom.allinfinance.bo.impl.mchnt.TblMchntService#updateGroup(as.allinfinance.po.
	 * management.mer.TblGroupMchtInf,
	 * as.allinfinance.po.management.mer.TblGroupMchtSettleInf)
	 */
	public String updateGroup(TblGroupMchtInf inf, TblGroupMchtSettleInf acctinf)
			throws IllegalAccessException, InvocationTargetException {

		try {

			inf.setGroupMchtCd(CommonFunction.fillString(inf.getGroupMchtCd(),
					' ', 8, true));

			tblGroupMchtInfDAO.update(inf);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
	}

	/*
	 * GET商户临时基本信息
	 * 
	 * @see
	 * com.allinfinance.bo.impl.mchnt.TblMchntService#getBaseInfTmp(java.lang.String)
	 */
	public TblMchtBaseInfTmp getBaseInfTmp(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtBaseInfTmpDAO.get(mchntId);
	}

	public TblMchtSettleInfTmp getSettleInfTmp(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtSettleInfTmpDAO.get(mchntId);
	}
	
	public TblMchtBaseInf getBaseInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtBaseInfDAO.get(mchntId);
	}

	public TblMchtSettleInf getSettleInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtSettleInfDAO.get(mchntId);
	}

	public ITblMchtBaseInfTmpTmpDAO getTblMchtBaseInfTmpTmpDAO() {
		return tblMchtBaseInfTmpTmpDAO;
	}

	public void setTblMchtBaseInfTmpTmpDAO(
			ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO) {
		this.tblMchtBaseInfTmpTmpDAO = tblMchtBaseInfTmpTmpDAO;
	}


	public String updateBaseInfTmp(TblMchtBaseInfTmp inf)
			throws IllegalAccessException, InvocationTargetException {
		
		tblMchtBaseInfTmpDAO.update(inf);
		
		return Constants.SUCCESS_CODE;
	}

	public boolean getMchntStatus(String mchntId) throws IllegalAccessException,
			InvocationTargetException {
		TblMchtBaseInf tblMchtBaseInf = tblMchtBaseInfDAO.get(mchntId);
		if(tblMchtBaseInf == null)
			return false;
		if(!tblMchtBaseInf.getMchtStatus().equals(TblMchntInfoConstants.MCHNT_ST_OK))
			return false;
		return true;
	}

	public TblGroupMchtSettleInf getGroupSettleInf(String mchtCd)
			throws IllegalAccessException, InvocationTargetException {
		
		return tblGroupMchtSettleInfDAO.get(mchtCd);
	}

	// 给正式的商户添加初始商户管理员
	public void addOprInfo(String mchtNo) throws Exception {
		ShTblOprInfo shTblOprInfo = new ShTblOprInfo();
		ShTblOprInfoPk shTblOprInfoPk = new ShTblOprInfoPk();
		shTblOprInfoPk.setOprId(TblOprInfoConstants.DEFAULT_OPR_NO);
		shTblOprInfoPk.setBrhId("-");
		shTblOprInfoPk.setMchtNo(mchtNo);
		shTblOprInfo.setId(shTblOprInfoPk);
		shTblOprInfo.setOprName(TblOprInfoConstants.DEFAULT_OPR_NAME);
		shTblOprInfo.setMchtBrhFlag("0");
		shTblOprInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		shTblOprInfo.setOprPwd(Encryption.encryptadd(SysParamUtil
				.getParam(SysParamConstants.OPR_DEFAULT_PWD)));
		shTblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction
				.getCurrentDate(), SysParamUtil
				.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		shTblOprInfo.setPwdWrTm(TblOprInfoConstants.PWD_WR_TM);
		shTblOprInfo.setRoleId(TblOprInfoConstants.SUP_MCHT_ROLE);
		shTblOprInfo.setPwdWrTmTotal(TblOprInfoConstants.PWD_WR_TM_TOTAL);
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_INIT);
		shTblOprInfo.setCurrentLoginTime(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginIp(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginStatus(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setPwdWrTmContinue(TblOprInfoConstants.PWD_WR_TM_CONTINUE);
		shTblOprInfoDAO.saveOrUpdate(shTblOprInfo);
	}
	/**
	 * 添加商戶信日志记录
	 * @param tmp
	 * @param inf
	 * @param tmpSettle
	 * @param infSettle
	 */
	
	
	public Object[] insertTblMchtBaseInfTmpLog(TblMchtBaseInfTmp tmp,
			TblMchtBaseInf inf, TblMchtSettleInfTmp tmpSettle,
			TblMchtSettleInf infSettle, String status, String date, String oprid) {
		TblMchtBaseInfTmpLog tblMchtBaseInfTmpLog = new TblMchtBaseInfTmpLog();
		tblMchtBaseInfTmpLog.setLId(UUID.randomUUID().toString());
		tblMchtBaseInfTmpLog.setLMchtNo(tmp.getMchtNo());
		tblMchtBaseInfTmpLog
				.setLCreatedate(CommonFunction.getCurrentDateTime());
		tblMchtBaseInfTmpLog.setLManuAuthFlag(tmp.getManuAuthFlag());
		tblMchtBaseInfTmpLog.setLCreatepeople(tmp.getUpdOprId());
		tblMchtBaseInfTmpLog.setLUpoprid(oprid);
		tblMchtBaseInfTmpLog.setLUpts(date);

		tblMchtBaseInfTmpLog.setBeMchtStatus(status);

		// 是否修改了账户信息的标识
		boolean acctModiFlag = false;
		// 修改前
		if ("3".equals(status)) {
			if (!inf.getAddr().equals(tmp.getAddr())) {
				tblMchtBaseInfTmpLog.setBeAddr(inf.getAddr());
				tblMchtBaseInfTmpLog.setAfAddr(tmp.getAddr());
				acctModiFlag = true; // 商户地址发生变更
			}
			if (!inf.getAgrBr().equals(tmp.getAgrBr())) {
				tblMchtBaseInfTmpLog.setBeAgrBr(inf.getAgrBr());
				tblMchtBaseInfTmpLog.setAfAgrBr(tmp.getAgrBr());
			}
			if (!inf.getArtifCertifTp().equals(tmp.getArtifCertifTp())) {
				tblMchtBaseInfTmpLog.setBeArtifCertifTp(inf.getArtifCertifTp());
				tblMchtBaseInfTmpLog.setAfArtifCertifTp(tmp.getArtifCertifTp());
			}
			if (!inf.getBankNo().equals(tmp.getBankNo())) {
				tblMchtBaseInfTmpLog.setBeBankNo(inf.getBankNo());
				tblMchtBaseInfTmpLog.setAfBankNo(tmp.getBankNo());
				acctModiFlag = true; // 签约机构发生变更
			}
			if (!inf.getContact().equals(tmp.getContact())) {
				tblMchtBaseInfTmpLog.setBeContact(inf.getContact());
				tblMchtBaseInfTmpLog.setAfContact(tmp.getContact());
			}
			if (!inf.getFaxNo().equals(tmp.getFaxNo())) {
				tblMchtBaseInfTmpLog.setBeFaxNo(inf.getFaxNo());
				tblMchtBaseInfTmpLog.setAfFaxNo(tmp.getFaxNo());

			}
			if (!inf.getLicenceNo().equals(tmp.getLicenceNo())) {
				tblMchtBaseInfTmpLog.setBeLicenceNo(inf.getLicenceNo());
				tblMchtBaseInfTmpLog.setAfLicenceNo(tmp.getLicenceNo());
			}
			if (!inf.getManager().equals(tmp.getManager())) {
				tblMchtBaseInfTmpLog.setBeManager(inf.getManager());
				tblMchtBaseInfTmpLog.setAfManager(tmp.getManager());
			}
			if (!inf.getMcc().equals(tmp.getMcc())) {
				tblMchtBaseInfTmpLog.setBeMcc(inf.getMcc());
				tblMchtBaseInfTmpLog.setAfMcc(tmp.getMcc());
			}
			if (!inf.getMchtCnAbbr().equals(tmp.getMchtCnAbbr())) {
				tblMchtBaseInfTmpLog.setBeMchtCnAbbr(inf.getMchtCnAbbr());
				tblMchtBaseInfTmpLog.setAfMchtCnAbbr(tmp.getMchtCnAbbr());
			}

			if (!inf.getMchtNm().equals(tmp.getMchtNm())) {
				tblMchtBaseInfTmpLog.setBeMchtNm(inf.getMchtNm());
				tblMchtBaseInfTmpLog.setAfMchtNm(tmp.getMchtNm());
				acctModiFlag = true; // 商户名称发生变更
			}
			if (!inf.getRislLvl().equals(tmp.getRislLvl())) {
				tblMchtBaseInfTmpLog.setBeRislLvl(inf.getRislLvl());
				tblMchtBaseInfTmpLog.setAfRislLvl(tmp.getRislLvl());
			}
			if (!(infSettle.getSettleAcct().substring(1)).equals(tmpSettle
					.getSettleAcct().substring(1))) {
				tblMchtBaseInfTmpLog.setBeSettleacct(infSettle
						.getSettleAcct().substring(1));
				tblMchtBaseInfTmpLog.setAfSettleacct(tmpSettle
						.getSettleAcct().substring(1));
				acctModiFlag = true; // 商户账户帐号发生变更
			}

			if (!(infSettle.getSettleAcct().substring(0, 1))
					.equals(tmpSettle.getSettleAcct().substring(0, 1))) {
				tblMchtBaseInfTmpLog.setBeCleartypenm(infSettle
						.getSettleAcct().substring(0, 1));
				tblMchtBaseInfTmpLog.setAfCleartypenm(tmpSettle
						.getSettleAcct().substring(0, 1));
				acctModiFlag = true; // 结算账户类型发生变更
			}
			if (!infSettle.getSettleAcctNm().equals(
							tmpSettle.getSettleAcctNm())) {
				tblMchtBaseInfTmpLog.setBeSettleacctnm(infSettle
						.getSettleAcctNm());
				tblMchtBaseInfTmpLog.setAfSettleacctnm(tmpSettle
						.getSettleAcctNm());
				acctModiFlag = true; // 商户账户名称发生变更
			}
			if (!infSettle.getSettleBankNm().equals(
							tmpSettle.getSettleBankNm())) {
				tblMchtBaseInfTmpLog.setBeSettlebanknm(infSettle
						.getSettleBankNm());
				tblMchtBaseInfTmpLog.setAfSettlebanknm(tmpSettle
						.getSettleBankNm());
				acctModiFlag = true; // 开户行名称发生变更
			}
			if (!inf.getSignInstId().equals(tmp.getSignInstId())) {
				tblMchtBaseInfTmpLog.setBeSigninstId(inf.getSignInstId());
				tblMchtBaseInfTmpLog.setAfSigninstId(tmp.getSignInstId());
			}
			if (!inf.getCommTel().equals(tmp.getCommTel())) {
				tblMchtBaseInfTmpLog.setBeCommtel(inf.getCommTel());
				tblMchtBaseInfTmpLog.setAfCommtel(tmp.getCommTel());
				acctModiFlag = true; // 联系电话发生变更
			}
			if (!inf.getConnType().equals(tmp.getConnType())) {
				tblMchtBaseInfTmpLog.setBeConntype(inf.getConnType());
				tblMchtBaseInfTmpLog.setAfConntype(tmp.getConnType());
			}
			if (!infSettle.getFeeRate().equals(tmpSettle.getFeeRate())) {
				tblMchtBaseInfTmpLog.setBeDisccode(infSettle.getFeeRate());
				tblMchtBaseInfTmpLog.setAfDisccode(tmpSettle.getFeeRate());
			}
			if (!inf.getElectrofax().equals(tmp.getElectrofax())) {
				tblMchtBaseInfTmpLog.setBeElectroFax(inf.getElectrofax());
				tblMchtBaseInfTmpLog.setAfElectroFax(tmp.getElectrofax());
			}
			if (!inf.getIdentityNo().equals(tmp.getIdentityNo())) {
				tblMchtBaseInfTmpLog.setBeEntityNo(inf.getIdentityNo());
				tblMchtBaseInfTmpLog.setAfEntityNo(tmp.getIdentityNo());
			}
			if (!tmpSettle.getOpenStlno().equals(infSettle.getOpenStlno())) {
				tblMchtBaseInfTmpLog.setBeOpenstlno(infSettle
						.getOpenStlno());
				tblMchtBaseInfTmpLog.setAfOpenstlno(tmpSettle
						.getOpenStlno());
				acctModiFlag = true; // 开户行号发生变更
			}
			
			if (!inf.getMchtFunction().equals(tmp.getMchtFunction())) {
				// tblMchtBaseInfTmpLog中暂时没有记录此信息变更的字段，以后添加
				acctModiFlag = true; // 是否支持T+0业务发生变更
			}
			
		}
		Object[] ret = new Object[2];
		ret[0] = tblMchtBaseInfTmpLog;
		ret[1] = acctModiFlag;
		return ret;
	};
	
	
//	public static void main(String[] args) {
//		"1".substring(1,2);
//	}
	
	/**
	 * 保存商户修改日志记录
	 * 
	 */	
	@Override
	public String saveTblMchtBaseInfTmpLog(
			TblMchtBaseInfTmpLog tblMchtBaseInfTmpLog) {
		iTblMchtBaseInfTmpLogDAO.saveOrUpdate(tblMchtBaseInfTmpLog);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 商户账户维护
	 * 
	 */	
	public String optMchtAcct(TblMchtBaseInf inf, TblMchtSettleInf infSettle, String txnCode) {
		
//		AcctDtlJsonDto acctDtl = new AcctDtlJsonDto();
//		acctDtl.setWebTime(CommonFunction.getCurrentDateTime());
//		acctDtl.setWebSeqNum(GenerateNextId.getSeqSysNum());
//		acctDtl.setAccountBizType(inf.getMchtFunction());
//		acctDtl.setAcctType(FrontMcht.ACCT_TYPE_PERSON);
//		acctDtl.setAddress(inf.getAddr());
//		acctDtl.setBrhId(inf.getBankNo());
//		acctDtl.setSysBrhId(FrontConstants.ACCT_BRH_ID);
//		String brhName = "SELECT BRH_NAME FROM TBL_BRH_INFO WHERE BRH_ID = '" +inf.getBankNo()+ "'";
//		acctDtl.setBrhName(CommonFunction.getCommQueryDAO().findCountBySQLQuery(brhName));
//		acctDtl.setContactPhone(inf.getCommTel());
//		acctDtl.setCurrency(FrontMcht.CURRENCY_TYPE_CNY);
//		acctDtl.setMerchantId(inf.getMchtNo());
//		acctDtl.setMerchantName(inf.getMchtNm());
//		acctDtl.setSettleBankNo(infSettle.getOpenStlno());
//		acctDtl.setSettleBankName(infSettle.getSettleBankNm());
//		acctDtl.setSettleCardNo(infSettle.getSettleAcct().trim().substring(1));
//		acctDtl.setSettleName(infSettle.getSettleAcctNm());
//		acctDtl.setSettleType(infSettle.getSettleAcct().trim().substring(0,1));
//		
//		FrontMcht frontMcht = new FrontMcht();
//		Object[] ret = frontMcht.doTxn(txnCode,acctDtl);
//		
//		if(Constants.SUCCESS_CODE.equals(ret[0])){
//			WebFrontTxnLog webFrontTxnLog = (WebFrontTxnLog) ret[1];
//			webFrontTxnLogDAO.save(webFrontTxnLog);
			// 返回成功信息
			return Constants.SUCCESS_CODE;
//		}else{
//			return (String) ret[1];
//		}
	}
	
	/**
	 * 1、客户在POSP入网时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  0）、交易码：A0100 子交易码：0
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因
	 * 2、客户在POSP结算信息并更时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  0）、交易码：F0100 子交易码：0
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因 
	 * @param newMchtNo 
	 *
	 * @param tmp
	 * @param tmpSettle
	 * @return
	 * @throws Exception 
	 */
	public String sendMessage(String newMchtNo, TblMchtBaseInfTmp tmp,TblMchtBaseInf inf,TblMchtSettleInfTmp tmpSettle,TblMchtSettleInf settle) throws Exception{
		String retMsg = "00";	//返回结果信息，00-表示成功
		String transCode = "";
		String settleType = "10" ; //默认T+1
		String tnN = "1";
		String weekDay = "";
		String monthDay = "";
		TblBrhInfoDAO tblBrhInfoDAO =  (TblBrhInfoDAO) ContextUtil.getBean("BrhInfoDAO");
		TblBrhInfo tblBrhInfo = tblBrhInfoDAO.get(tmp.getBankNo());
		if(tblBrhInfo == null){
			retMsg = "没有商户相关合作伙伴信息！";
			log.info(retMsg);
			return retMsg;
		}
				
		if(tmp.getMchtNo().contains("AAA")){
			transCode = "A0100";
			log.info("商户开户。");
		} else {	
			if(null == inf || null == settle){
				log.info("获取商户变更历史信息失败。");
				return  "取商户变更历史信息失败。";
			}
			String newSettleType = tmp.getMchtFunction();
			String oldSettleType = inf.getMchtFunction();
			oldSettleType = (null == oldSettleType) ? "" :oldSettleType.trim();
			newSettleType = (null == newSettleType) ? null : newSettleType.trim();
			if(tmpSettle.getSettleBankNm().equals(settle.getSettleBankNm()) && tmpSettle.getSettleAcct().equals(settle.getSettleAcct()) 
					&& tmpSettle.getSettleAcctNm().equals(settle.getSettleAcctNm()) 
					&& oldSettleType.equals(newSettleType)){
				log.info("商户结算信息未变更，不需调用接口。");
				return retMsg;
			}
			transCode = "F0100";
			log.info("商户结算信息变更。");
		}
		// 取得结算类型---开始
		if (!"".equals(tmp.getMchtFunction())) {
			String mixStr = tmp.getMchtFunction();
			String type = mixStr.substring(0,1);
			if ("0".equals(type)) {
				// T+N
				settleType = "10";
				tnN = String.valueOf(Integer.parseInt(mixStr.substring(2, 5)));
				if("0".equals(tnN)){ //T+0
					settleType = "03";
					tnN = "";
				}
			} else if ("1".equals(type)) {
				String periodType = mixStr.substring(1, 2);
				// 周结
				if ("0".equals(periodType)) {
					settleType = "11";
					weekDay = "1";
				// 月结
				} else if ("1".equals(periodType)) {
					settleType = "12";
					monthDay = "01";
				// 季结
				} else if ("2".equals(periodType)) {
					settleType = "13";
				}
			}
		}
		// 取得结算类型---结束
		
		Msg reqBody = MsgEntity.genMchtRequestBodyMsg();
		Msg reqHead = MsgEntity.genCommonRequestHeadMsg(transCode,"0");
//		reqHead.getField(1).setValue("00");					//版本号
//		reqHead.getField(2).setValue(transCode);			//交易类型码
		reqHead.getField(3).setValue("0");					//子交易码
//		reqHead.getField(4).setValue(institution); 			//接入机构号;固定值，钱宝机构号
//		reqHead.getField(5).setValue(CommonFunction.getCurrentDate());			//接入方交易日期
//		reqHead.getField(6).setValue(CommonFunction.getRandomNum(reqHead.getField(6).getLength()));		//接入方交易流水号
//		reqHead.getField(7).setValue(CommonFunction.getCurrentTime());			//接入方交易时间
		reqHead.getField(8).setValue("0000000000");			//接入方交易码
		reqHead.getField(9).setValue(transCode.substring(0, 4) + "");	//交易类型+接入方检索参考号
//		reqHead.getField(10).setValue(institution);			//请求方机构号
//		reqHead.getField(11).setValue("");					//请求方交易日期
//		reqHead.getField(12).setValue("");					//请求方交易流水号
//		reqHead.getField(13).setValue(institution);			//开户机构号;固定值，钱宝机构号
//		reqHead.getField(14).setValue("");					//开户机构交易日期
//		reqHead.getField(15).setValue("");					//开户机构交易流水号
//		reqHead.getField(16).setValue("");					//开户机构交易时间
		reqHead.getField(17).setValue(newMchtNo);			//外部账号
//		reqHead.getField(18).setValue("");					//外部账号类型
//		reqHead.getField(19).setValue("1");					//内部账号验证标志
//		reqHead.getField(20).setValue("");					//内部账号
//		reqHead.getField(21).setValue("");					//内部账号类型
//		reqHead.getField(22).setValue("");					//密码域1
//		reqHead.getField(23).setValue("");					//密码域2
//		reqHead.getField(24).setValue("");					//客户号
//		reqHead.getField(25).setValue("+");					//金额符号
//		reqHead.getField(26).setValue("");					//交易额
//		reqHead.getField(27).setValue("");					//用户支付手续费
//		reqHead.getField(28).setValue("");					//应答码
		String sql = "select b.subbranch_id,b.bank_name,b.subbranch_name,b.province,b.city from tbl_subbranch_info b where b.subbranch_id = '"+ tmpSettle.getOpenStlno() +"'";
		List retList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Object[] objBank = null;
		if(null == retList || retList != null && retList.size() < 1){
			retMsg = "获取清算账户信息失败。";
			return retMsg;
		} else{
			objBank = (Object[]) retList.get(0);
		}
		reqBody.getField(1).setValue(newMchtNo);
		reqBody.getField(2).setValue(tmp.getEtpsAttr());
		reqBody.getField(3).setValue(tmp.getMchtNm());
		reqBody.getField(4).setValue(tblBrhInfo.getCreateNewNo());		//所属合作伙伴编号
		reqBody.getField(5).setValue(tmp.getRislLvl());		//商户风险级别
		reqBody.getField(6).setValue("0");					//商户状态
		reqBody.getField(7).setValue(tmp.getAddr());		//地址
		reqBody.getField(8).setValue(tmp.getPostCode());	//邮编
		reqBody.getField(9).setValue(tmp.getContact());		//联系人姓名
		reqBody.getField(10).setValue(tmp.getCommMobil());	//联系人移动电话
		reqBody.getField(11).setValue(tmp.getCommTel());	//联系人固定电话
		reqBody.getField(12).setValue(tmp.getCommEmail());	//联系人电子信箱
		reqBody.getField(13).setValue("");					//联系人传真
		reqBody.getField(14).setValue("1");					//申请类别	
		reqBody.getField(15).setValue((String)objBank[1]);				//结算银行名称
		reqBody.getField(16).setValue((String)objBank[0]);				//结算账号开户行行号	
		reqBody.getField(17).setValue((String)objBank[2]);				//结算账号开户行名称
		reqBody.getField(18).setValue(tmpSettle.getSettleAcct().substring(0, 1));	//结算账号类型
		reqBody.getField(19).setValue(tmpSettle.getSettleAcct().substring(1));		//结算账号
		reqBody.getField(20).setValue(tmpSettle.getSettleAcctNm());					//结算账号户名
		reqBody.getField(21).setValue(settleType);		//结算方式
		reqBody.getField(22).setValue(tnN);				//只填写N的值。如T+1填写1结算方式为10时必填
		reqBody.getField(23).setValue(weekDay);				//周一填写1。。。周日填写7结算方式为11时必填
		reqBody.getField(24).setValue(monthDay);				//介于01-28之间。不可填写29.30.31结算方式为12时必填
		reqBody.getField(25).setValue((String)objBank[3]);				//户省份（和结算账号开户行对应）
		reqBody.getField(26).setValue((String)objBank[4]);				//开户城市（和结算账号开户行对应）
		reqBody.getField(27).setValue("0000");		// 垫资日息 填写整数，万分之10，填写0010。(N4)
		reqBody.getField(28).setValue("00000000");	// 代付手续费 分为单位，无小数点 (N8)
		reqBody.getField(29).setValue("   "); 		// 暂时不用，填写全空格即可(AN3)
		
		String secretKey = "1111111111111111";
		
		String reqStr = MsgEntity.genCompleteRequestMsg(reqHead, reqBody, secretKey);
		
		Msg respHead = MsgEntity.genCommonResponseHeadMsg();
		Msg respBody = MsgEntity.genCommonResponseBodyMsg();
		
		log.info(reqStr);
		byte[] bufMsg = MsgEntity.sendMessage(reqStr + " ");
		String strRet = new String(bufMsg,"gb2312");
		log.info(strRet);
		MsgEntity.parseResponseMsg(bufMsg, respHead, respBody);
		
		String respCode = respHead.getField(28).getRealValue();
		log.info("虚拟账户开户返回结果："+respCode);
		if("0000".equals(respCode)) { //交易成功
			retMsg = "00";
		} else{
			retMsg = respBody.getField(1).getRealValue();
		}
			
		return retMsg;
		
	}

	/**
	 * @return the tblTermInfDAO
	 */
	public TblTermInfDAO getTblTermInfDAO() {
		return tblTermInfDAO;
	}

	/**
	 * @param tblTermInfDAO the tblTermInfDAO to set
	 */
	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}

	/**
	 * @return the tblTermInfTmpDAO
	 */
	public TblTermInfTmpDAO getTblTermInfTmpDAO() {
		return tblTermInfTmpDAO;
	}

	/**
	 * @param tblTermInfTmpDAO the tblTermInfTmpDAO to set
	 */
	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}

	public ITblMchtSettleInfTmpHistDAO getTblMchtSettleInfTmpHistDAO() {
		return tblMchtSettleInfTmpHistDAO;
	}

	public void setTblMchtSettleInfTmpHistDAO(ITblMchtSettleInfTmpHistDAO tblMchtSettleInfTmpHistDAO) {
		this.tblMchtSettleInfTmpHistDAO = tblMchtSettleInfTmpHistDAO;
	}

	@Override
	public void updateHisMcc(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmpUpd,TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmpUpd) {
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		String sql = "select MAX(to_number(MCHT_NO_NEW)) from TBL_MCHT_BASE_INF_TMP_HIST where MCHT_NO = '" + tblMchtBaseInfTmpTmpUpd.getMchtNo() + "' ";
		List<BigDecimal> mchtHistSerail = commQueryDAO.findBySQLQuery(sql);
		String mchtNoNew;
		if(null!=mchtHistSerail &&mchtHistSerail.get(0)!=null&& mchtHistSerail.size()>0){
			String serail =  mchtHistSerail.get(0).toString();
			mchtNoNew = String.valueOf(serail);
		}else mchtNoNew="1";
		TblMchtBaseInfTmpHistPK key=new TblMchtBaseInfTmpHistPK(tblMchtBaseInfTmpTmpUpd.getMchtNo(), mchtNoNew);
		TblMchtSettleInfTmpHistPK settleInfTmpHistPK=new TblMchtSettleInfTmpHistPK(tblMchtBaseInfTmpTmpUpd.getMchtNo(), mchtNoNew);
		TblMchtSettleInfTmpHist tblMchtSettleInfTmpHist = tblMchtSettleInfTmpHistDAO.get(settleInfTmpHistPK);
		TblMchtBaseInfTmpHist tblMchtBaseInfTmpHist = tblMchtBaseInfTmpHistDAO.get(key);
		
		if(tblMchtBaseInfTmpHist!=null&&tblMchtSettleInfTmpHist!=null){
			tblMchtBaseInfTmpHist.setMchtStatus(tblMchtBaseInfTmpTmpUpd.getMchtStatus());
			tblMchtBaseInfTmpHist.setMcc(tblMchtBaseInfTmpTmpUpd.getMcc());
			tblMchtBaseInfTmpHist.setRislLvl(tblMchtBaseInfTmpTmpUpd.getRislLvl());
			tblMchtBaseInfTmpHist.setMchtGrp(tblMchtBaseInfTmpTmpUpd.getMchtGrp());
			tblMchtSettleInfTmpHist.setCompliance(tblMchtSettleInfTmpTmpUpd.getCompliance());
			tblMchtSettleInfTmpHist.setCountry(tblMchtSettleInfTmpTmpUpd.getCountry());
			
			tblMchtBaseInfTmpHist.setUpdOprId(opr.getOprId());
			tblMchtBaseInfTmpHist.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tblMchtSettleInfTmpHist.setRecUpdTs(CommonFunction.getCurrentDateTime());
			
			tblMchtSettleInfTmpHistDAO.update(tblMchtSettleInfTmpHist);
			tblMchtBaseInfTmpHistDAO.update(tblMchtBaseInfTmpHist);
		}
	}

	public TblRiskParamMngDAO getTblRiskParamMngDAO() {
		return tblRiskParamMngDAO;
	}

	public void setTblRiskParamMngDAO(TblRiskParamMngDAO tblRiskParamMngDAO) {
		this.tblRiskParamMngDAO = tblRiskParamMngDAO;
	}
}
