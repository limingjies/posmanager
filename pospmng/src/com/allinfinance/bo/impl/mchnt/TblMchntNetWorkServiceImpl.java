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

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.mchnt.ITblGroupMchtInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblGroupMchtSettleInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpHistDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpHistDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntRefuseDAO;
import com.allinfinance.po.TblMchntRefuse;
import com.allinfinance.po.TblMchntRefusePK;
import com.allinfinance.po.mchnt.TblGroupMchtInf;
import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpHist;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpHistPK;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.StatusUtil;

/**
 * Title:渠道商户入网审核
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
public class TblMchntNetWorkServiceImpl implements TblMchntNetWorkService {
	
	private ITblMchtBaseInfTmpHistDAO tblMchtBaseInfTmpHistDAO;
	private ITblMchtSettleInfTmpHistDAO tblMchtSettleInfTmpHistDAO;
	private ICommQueryDAO commQueryDAO;
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

	public ITblMchtBaseInfTmpTmpDAO getTblMchtBaseInfTmpTmpDAO() {
		return tblMchtBaseInfTmpTmpDAO;
	}

	public void setTblMchtBaseInfTmpTmpDAO(
			ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO) {
		this.tblMchtBaseInfTmpTmpDAO = tblMchtBaseInfTmpTmpDAO;
	}

	public ITblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	public void setTblMchtBaseInfDAO(ITblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	public ITblMchtSettleInfTmpTmpDAO getTblMchtSettleInfTmpTmpDAO() {
		return tblMchtSettleInfTmpTmpDAO;
	}

	public void setTblMchtSettleInfTmpTmpDAO(
			ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO) {
		this.tblMchtSettleInfTmpTmpDAO = tblMchtSettleInfTmpTmpDAO;
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

	public ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO;

	public ITblMchtBaseInfDAO tblMchtBaseInfDAO;

	public ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO;

	public ITblMchtSettleInfDAO tblMchtSettleInfDAO;

	public TblMchntRefuseDAO tblMchntRefuseDAO;

	public ITblGroupMchtInfDAO tblGroupMchtInfDAO;

	public ITblGroupMchtSettleInfDAO tblGroupMchtSettleInfDAO;

	/*
	 * 保存商户信息至临时表
	 * 
	 * @see
	 * com.allinfinance.bo.impl.mchnt.TblMchntService#saveTmp(com.allinfinance.po.mchnt
	 * .TblMchtBaseInfTmpTmp, as.allinfinance.po.management.mer.TblMchtSettleInfTmpTmp)
	 */
	public String saveTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp) {

		if(tblMchtBaseInfTmpTmpDAO.get(tblMchtBaseInfTmpTmp.getMchtNo()) != null) {
			return "您自定义的商户号已经存在";
		}
		tblMchtBaseInfTmpTmpDAO.save(tblMchtBaseInfTmpTmp);
		tblMchtSettleInfTmpTmpDAO.save(tblMchtSettleInfTmpTmp);

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 更新商户信息至临时表
	 * 
	 * @see
	 * com.allinfinance.bo.impl.mchnt.TblMchntService#updateTmp(com.allinfinance.po.mchnt
	 * .TblMchtBaseInfTmpTmp, com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp)
	 */
	public String updateTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp) {

		tblMchtBaseInfTmpTmpDAO.update(tblMchtBaseInfTmpTmp);
		tblMchtSettleInfTmpTmpDAO.update(tblMchtSettleInfTmpTmp);

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 商户审核通过
	 * 
	 * @see com.allinfinance.bo.impl.mchnt.TblMchntService#accept(java.lang.String)
	 */
	public String accept(String mchntId) throws IllegalAccessException,
			InvocationTargetException {

		TblMchtBaseInfTmpTmp tmp = tblMchtBaseInfTmpTmpDAO.get(mchntId);
		TblMchtSettleInfTmpTmp tmpSettle = tblMchtSettleInfTmpTmpDAO.get(mchntId);
		
		//更新前的状态
		String status = tmp.getMchtStatus();
		//临时表中的cre时间
		String crtDateTmp= tmp.getRecCrtTs();
		
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		

		// 取得原始信息
		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(tmp.getMchtNo());
		TblMchtSettleInf infSettle = tblMchtSettleInfDAO.get(tmpSettle
				.getMchtNo());
		
		//正式表中cre日期
		String crtDate= "0";
		if (null != inf) {
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

		
		//判断如果为修改待审核并且更新了中文简称就同步到终端信息表
//		if (TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tmp.getMchtStatus()) 
//				&& (inf.getMchtCnAbbr() == null ||!inf.getMchtCnAbbr().equals(tmp.getMchtCnAbbr())
//				||inf.getEngName() == null ||!inf.getEngName().equals(tmp.getEngName()))) {
////			TODO 需要优化，暂时修养一下，不改了
//	    StringBuffer sql0 = new StringBuffer("update tbl_term_inf set term_para = substr(term_para,1,50)||'")
//	    .append(CommonFunction.fillStringByDB(tmp.getMchtCnAbbr(), ' ', 40, true)).append("'||substr(term_para,91,20)||'")
//	    .append(CommonFunction.fillStringByDB(tmp.getEngName()==null?"":tmp.getEngName(), ' ', 40, true)).append("'||substr(term_para,151) where MCHT_CD = '")
//	    .append(mchntId).append("'");
//		
//	    StringBuffer sql1 = new StringBuffer("update tbl_term_inf_tmp set term_para = substr(term_para,1,53)||'")
//	    .append(CommonFunction.fillStringByDB(tmp.getMchtCnAbbr(), ' ', 40, true)).append("'||substr(term_para,94,22)||'")
//	    .append(CommonFunction.fillStringByDB(tmp.getEngName()==null?"":tmp.getEngName(), ' ', 40, true)).append("'||substr(term_para,156) where MCHT_CD = '")
//	    .append(mchntId).append("'");
//			
//		 //同时更新临时表和正式表
//		 CommonFunction.getCommQueryDAO().excute(sql0.toString());
//		 CommonFunction.getCommQueryDAO().excute(sql1.toString());
//		}
		
		
		if (tmp.getMchtStatus().equals(TblMchntInfoConstants.MCHNT_ST_DEL_UNCK)) {//注销待审核同步更新到终端表
			String update0 = "update tbl_term_inf set TERM_STA = '7' where MCHT_CD = '" + tmp.getMchtNo() + "'";
			String update1 = "update tbl_term_inf_tmp set TERM_STA = '7' where MCHT_CD = '" + tmp.getMchtNo() + "'";
			CommonFunction.getCommQueryDAO().excute(update0);
			CommonFunction.getCommQueryDAO().excute(update1);
		} else if (tmp.getMchtStatus().equals(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK)) {//停用待审核同步更新到终端表
			String update0 = "update tbl_term_inf set TERM_STA = '4' where MCHT_CD = '" + tmp.getMchtNo() + "'";
			String update1 = "update tbl_term_inf_tmp set TERM_STA = '4' where MCHT_CD = '" + tmp.getMchtNo() + "'";
			CommonFunction.getCommQueryDAO().excute(update0);
			CommonFunction.getCommQueryDAO().excute(update1);
		}
		
		
		String tmpStatus = tmp.getMchtStatus();
		
		// 获得下一状态
		tmp.setMchtStatus(StatusUtil.getNextStatus("G."
				+ tmp.getMchtStatus()));
		
		// 复制新的信息
		BeanUtils.copyProperties(tmp, inf);
		BeanUtils.copyProperties(tmpSettle, infSettle);
		
		
		//如果不是新增，crt日期不变
		if(!TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(status) && !TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(status)){
			inf.setRecCrtTs(crtDate);
		}
		
		
		// 更新到数据库
		tblMchtBaseInfTmpTmpDAO.update(tmp);
		tblMchtSettleInfTmpTmpDAO.update(tmpSettle);
		tblMchtBaseInfDAO.saveOrUpdate(inf);
		tblMchtSettleInfDAO.saveOrUpdate(infSettle);
		
		return Constants.SUCCESS_CODE;
	}

	/*
	 * 商户审核退回
	 * 
	 * @see com.allinfinance.bo.impl.mchnt.TblMchntService#back(java.lang.String,
	 * java.lang.String)
	 */
	public String back(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {

		TblMchtBaseInfTmpTmp tmp = tblMchtBaseInfTmpTmpDAO.get(mchntId);
		tmp.setReserved("回退原因："+refuseInfo);
		TblMchtSettleInfTmpTmp tmpSettle = tblMchtSettleInfTmpTmpDAO.get(mchntId);
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);

		// 记录退回信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,
				CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo(refuseInfo);
		refuse.setBrhId(tmp.getBankNo());
		refuse.setOprId(opr.getOprId());

		// 获得退回信息
		refuse.setRefuseType(StatusUtil.getNextStatus("BM."
				+ tmp.getMchtStatus()));
		// 获得下一状态
		tmp.setMchtStatus(StatusUtil.getNextStatus("B."
				+ tmp.getMchtStatus()));

		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tmp.setUpdOprId(opr.getOprId());

		// 更新到数据库
		tblMchtBaseInfTmpTmpDAO.update(tmp);
		tblMchntRefuseDAO.save(refuse);
		//更新历史
		String sql = "select MAX(to_number(MCHT_NO_NEW)) from TBL_MCHT_BASE_INF_TMP_HIST where MCHT_NO = '" + tmp.getMchtNo() + "' ";
		List<BigDecimal> mchtHistSerail = commQueryDAO.findBySQLQuery(sql);
		TblMchtBaseInfTmpHistPK pk=new TblMchtBaseInfTmpHistPK();
		if(null!=mchtHistSerail &&mchtHistSerail.get(0)!=null&& mchtHistSerail.size()>0){
			String serail =  mchtHistSerail.get(0).toString();
			String mchtNew = String.valueOf(serail);
			pk.setMchtNoNew(mchtNew);
			pk.setMchtNo(tmp.getMchtNo());
			TblMchtBaseInfTmpHist tblMchtBaseInfTmpHist = tblMchtBaseInfTmpHistDAO.get(pk);
			if(tblMchtBaseInfTmpHist!=null){
				tblMchtBaseInfTmpHist.setMchtStatus(tmp.getMchtStatus());
				tblMchtBaseInfTmpHist.setUpdOprId(opr.getOprId());
				tblMchtBaseInfTmpHist.setRecUpdTs(CommonFunction.getCurrentDateTime());
			}
	}
		return Constants.SUCCESS_CODE;
	}

	/*
	 * 商户审核拒绝
	 * 
	 * @see com.allinfinance.bo.impl.mchnt.TblMchntService#refuse(java.lang.String,
	 * java.lang.String)
	 */
	public String refuse(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {

		TblMchtBaseInfTmpTmp tmp = tblMchtBaseInfTmpTmpDAO.get(mchntId);
		TblMchtSettleInfTmpTmp tmpSettle = tblMchtSettleInfTmpTmpDAO.get(mchntId);
		
		String crtDateTmp= tmp.getRecCrtTs();
		
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);

		// 记录拒绝信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,
				CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo(refuseInfo);
		refuse.setBrhId(tmp.getBankNo());
		refuse.setOprId(opr.getOprId());

		// 获得拒绝信息
		refuse.setRefuseType(StatusUtil.getNextStatus("RM."
				+ tmp.getMchtStatus()));
				
		// 分别处理新增拒绝和修改拒绝
		// 对于未审核通过的商户，拒绝时不删除，状态修改为拒绝状态-C 20120913
		if (TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(tmp.getMchtStatus())||TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tmp.getMchtStatus())) {
			/*tblMchtBaseInfTmpTmpDAO.delete(tmp);
			tblMchtSettleInfTmpTmpDAO.delete(tmpSettle);
			tblMchntRefuseDAO.save(refuse);
			ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
			commQueryDAO.excute("delete from tbl_term_inf_tmp where mcht_cd='" + mchntId + "'");*/
			tmp.setMchtStatus("C");
			tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tmp.setUpdOprId(opr.getOprId());
			tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tblMchtBaseInfTmpTmpDAO.update(tmp);
			tblMchtSettleInfTmpTmpDAO.update(tmpSettle);
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
				tblMchtBaseInfTmpTmpDAO.update(tmp);
				tblMchtBaseInfDAO.update(inf);
				tblMchtSettleInfTmpTmpDAO.update(tmpSettle);
				tblMchtSettleInfDAO.update(infSettle);
				tblMchntRefuseDAO.save(refuse);
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
	public TblMchtBaseInfTmpTmp getBaseInfTmp(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtBaseInfTmpTmpDAO.get(mchntId);
	}

	public TblMchtSettleInfTmpTmp getSettleInfTmp(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtSettleInfTmpTmpDAO.get(mchntId);
	}

	
	public TblMchtBaseInf getBaseInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtBaseInfDAO.get(mchntId);
	}

	public TblMchtSettleInf getSettleInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtSettleInfDAO.get(mchntId);
	}

	public String updateBaseInfTmp(TblMchtBaseInfTmpTmp inf)
			throws IllegalAccessException, InvocationTargetException {
		
		tblMchtBaseInfTmpTmpDAO.update(inf);
		
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

	public TblGroupMchtSettleInf getGroupSettleInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		return tblGroupMchtSettleInfDAO.get(mchntId);
	}

	public ITblMchtBaseInfTmpHistDAO getTblMchtBaseInfTmpHistDAO() {
		return tblMchtBaseInfTmpHistDAO;
	}

	public void setTblMchtBaseInfTmpHistDAO(ITblMchtBaseInfTmpHistDAO tblMchtBaseInfTmpHistDAO) {
		this.tblMchtBaseInfTmpHistDAO = tblMchtBaseInfTmpHistDAO;
	}

	public ITblMchtSettleInfTmpHistDAO getTblMchtSettleInfTmpHistDAO() {
		return tblMchtSettleInfTmpHistDAO;
	}

	public void setTblMchtSettleInfTmpHistDAO(ITblMchtSettleInfTmpHistDAO tblMchtSettleInfTmpHistDAO) {
		this.tblMchtSettleInfTmpHistDAO = tblMchtSettleInfTmpHistDAO;
	}



}
