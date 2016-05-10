/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-21       first release
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
package com.allinfinance.common.select;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.base.T10105BO;
import com.allinfinance.bo.base.T10206BO;
import com.allinfinance.bo.base.T10600BO;
import com.allinfinance.bo.impl.mchnt.TblMchntService;
import com.allinfinance.bo.mchnt.T20401BO;
import com.allinfinance.bo.mchnt.T20402BO;
import com.allinfinance.bo.mchnt.T20701BO;
import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.bo.term.T30401BO;
import com.allinfinance.bo.term.T30402BO;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.TblHisDiscAlgoDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntInfoTmpDAO;
import com.allinfinance.dao.iface.risk.TblRiskAlarmDAO;
import com.allinfinance.dao.iface.risk.TblRiskInfDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamInfDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamMngDAO;
import com.allinfinance.dao.iface.term.TblTermInfTmpDAO;
import com.allinfinance.po.PictureInfo;
import com.allinfinance.po.TblAgentFeeCfg;
import com.allinfinance.po.TblAgentFeeCfgHis;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblBrhInfoHis;
import com.allinfinance.po.TblMchtCupInfo;
import com.allinfinance.po.TblMchtCupInfoTmp;
import com.allinfinance.po.TblRiskInf;
import com.allinfinance.po.TblRiskParamInf;
import com.allinfinance.po.TblRiskParamInfPK;
import com.allinfinance.po.TblRouteRule;
import com.allinfinance.po.TblRouteRulePK;
import com.allinfinance.po.TblTermCupInfo;
import com.allinfinance.po.TblTermCupInfoTmp;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.po.base.TblBrhSettleInfHis;
import com.allinfinance.po.base.TblEmvPara;
import com.allinfinance.po.base.TblEmvParaPK;
import com.allinfinance.po.base.TblEmvParaValue;
import com.allinfinance.po.mchnt.TblGroupMchtInf;
import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblHisDiscAlgoPK;
import com.allinfinance.po.mchnt.TblInfDiscCd;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtCashInfTmp;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.po.risk.TblRiskAlarm;
import com.allinfinance.po.risk.TblRiskAlarmPK;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.po.risk.TblRiskParamMngPK;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TMSService;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class LoadRecordMethod {
	private static Logger log = Logger.getLogger(LoadRecordMethod.class);
	
	public static TblHisDiscAlgoDAO tblHisDiscAlgoDAO =  (TblHisDiscAlgoDAO) ContextUtil.getBean("TblHisDiscAlgoDAO");
	public static TblMchntService service = (TblMchntService) ContextUtil.getBean("tblMchntService");
	public static ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO =  (ITblMchtBaseInfTmpTmpDAO) ContextUtil
	.getBean("MchntTmpInfoTmpDAO");
	
	public static ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO =  (ITblMchtSettleInfTmpTmpDAO) ContextUtil
	.getBean("MchtSettleInfTmpTmpDAO");
	public static TblMchntInfoTmpDAO baseInfTmpDAO=(TblMchntInfoTmpDAO) ContextUtil.getBean("MchntTmpInfoDAO");
	public static TblRiskParamMngDAO tblRiskParamMngDAO=(TblRiskParamMngDAO) ContextUtil.getBean("tblRiskParamMngDAO");
	public static T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");
	private static T20701BO t20701BO = (T20701BO) ContextUtil.getBean("T20701BO");
	private static T20401BO t20401BO = (T20401BO) ContextUtil.getBean("T20401BO");
	private static T20402BO t20402BO = (T20402BO) ContextUtil.getBean("T20402BO");
	private static T30401BO t30401BO = (T30401BO) ContextUtil.getBean("T30401BO");
	private static T30402BO t30402BO = (T30402BO) ContextUtil.getBean("T30402BO");
	private static T10600BO t10600BO = (T10600BO) ContextUtil.getBean("T10600BO");
	private static T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
	private static TblTermInfTmpDAO tblTermInfTmpDAO = (TblTermInfTmpDAO) ContextUtil.getBean("TblTermInfTmpDAO");
	private ITblMchtCashInfTmpTmpDAO cashInfTmpTmpDAO = (ITblMchtCashInfTmpTmpDAO) ContextUtil.getBean("TblMchtCashInfTmpTmpDAO");
	private ITblMchtCashInfTmpDAO cashInfTmpDAO = (ITblMchtCashInfTmpDAO) ContextUtil.getBean("TblMchtCashInfTmpDAO");
	private ITblMchtCashInfDAO cashInfDAO = (ITblMchtCashInfDAO) ContextUtil.getBean("TblMchtCashInfDAO");
	/**
	 * 获得集团商户信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *             2011-6-22下午01:43:43
	 */
	public String getGroupMchnt(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblGroupMchtInf inf = service.getGroupInf(request
					.getParameter("groupMchtCd"));
			TblGroupMchtSettleInf settle = service.getGroupSettleInf(request
					.getParameter("groupMchtCd"));
			if (null == inf || null == settle) {
				return null;
			}
			inf = (TblGroupMchtInf) CommonFunction.trimObject(inf);
			settle = (TblGroupMchtSettleInf) CommonFunction.trimObject(settle);
			
			String[] srcs = { JSONArray.fromObject(inf).toString(),
					JSONArray.fromObject(settle).toString() };
			
			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得商户信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getMchntInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblMchtBaseInfTmp inf = service.getBaseInfTmp(request
					.getParameter("mchntId"));
			TblMchtSettleInfTmp settle = service.getSettleInfTmp(request
					.getParameter("mchntId"));
			TblRiskParamMngPK paramMngPK = new TblRiskParamMngPK(request.getParameter("mchntId"),
					CommonFunction.fillString("00000000", ' ', 12, true), "0");
			TblRiskParamMng tblRiskParamMng = tblRiskParamMngDAO.get(paramMngPK );
			if(tblRiskParamMng == null){
				tblRiskParamMng = new TblRiskParamMng(paramMngPK);
				tblRiskParamMng.setCreditDayAmt(0.0);
				tblRiskParamMng.setCreditDayCount(0);
				tblRiskParamMng.setCreditMonAmt(0.0);
				tblRiskParamMng.setCreditMonCount(0);
				tblRiskParamMng.setCreditSingleAmt(0.0);
				tblRiskParamMng.setDebitDayAmt(0.0);
				tblRiskParamMng.setDebitDayCount(0);
				tblRiskParamMng.setDebitMonAmt(0.0);
				tblRiskParamMng.setDebitMonCount(0);
				tblRiskParamMng.setDebitSingleAmt(0.0);
				tblRiskParamMng.setMchtAmt(0.0);
				tblRiskParamMng.setMchtDayAmt(0.0);
				tblRiskParamMng.setMchtPosAmt(0.0);
			}
			if (null == inf || null == settle) {
				return null;
			}
			if(inf.getCashFlag() == null){
				inf.setCashFlag("1");
			}
			inf = (TblMchtBaseInfTmp) CommonFunction.trimObject(inf);
			settle = (TblMchtSettleInfTmp) CommonFunction.trimObject(settle);
			TblMchtCashInfTmp tblMchtCashInfTmp = cashInfTmpDAO.get(request.getParameter("mchntId"));
			if(tblMchtCashInfTmp == null){
				tblMchtCashInfTmp = new TblMchtCashInfTmp();
			}
			//查询费率
			String discId=null;
			Map<String, String> map=new HashMap<String, String>();
			if(settle!=null){
				discId=settle.getFeeRate();
			}
			
			if(StringUtils.isNotEmpty(discId)){
				discId=discId.trim();
				TblHisDiscAlgoPK key=new TblHisDiscAlgoPK(discId, 0);
				TblHisDiscAlgo jTblHisDiscAlgo = tblHisDiscAlgoDAO.get(key);
				TblHisDiscAlgoPK key1=new TblHisDiscAlgoPK(discId, 1);
				TblHisDiscAlgo dTblHisDiscAlgo = tblHisDiscAlgoDAO.get(key1);
				if (jTblHisDiscAlgo!=null&&dTblHisDiscAlgo!=null) {
					map.put("jFee", jTblHisDiscAlgo.getFeeValue().toString().trim());
					map.put("jMaxFee", jTblHisDiscAlgo.getMaxFee().toString().trim());
					map.put("dFee", dTblHisDiscAlgo.getFeeValue().toString().trim());
					map.put("dMaxFee", dTblHisDiscAlgo.getMaxFee().toString().trim());
				}
			}
			
			String[] srcs = { JSONArray.fromObject(inf).toString(),
					JSONArray.fromObject(settle).toString() ,JSONArray.fromObject(map).toString(),JSONArray.fromObject(tblMchtCashInfTmp).toString(),
					JSONArray.fromObject(tblRiskParamMng).toString()};
			
			//由于《商户基本信息表》和《商户清算信息表》中都有reserved字段，页面上取值不方便
			//将《商户基本信息表》取出的reserved更名为idOtherNo
			String sub1 = srcs[0].substring(0,srcs[0].indexOf("reserved"));
			String sub2 = srcs[0].substring(srcs[0].indexOf("reserved")+"reserved".length());
			srcs[0] = sub1 + "idOtherNo" + sub2;

			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 从TmpTmp表获得商户信息
	 */
	public String getMchntInfoFromTmpTmp(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblMchtBaseInfTmpTmp inf = t20901BO.getBaseInfTmp(request.getParameter("mchntId"));
			TblMchtSettleInfTmpTmp settle = t20901BO.getSettleInfTmp(request.getParameter("mchntId"));

			if (null == inf || null == settle) {
				return null;
			}
			if(inf.getCashFlag() == null){
				inf.setCashFlag("1");
			}
			inf = (TblMchtBaseInfTmpTmp) CommonFunction.trimObject(inf);
			settle = (TblMchtSettleInfTmpTmp) CommonFunction.trimObject(settle);
			TblMchtCashInfTmpTmp tblMchtCashInfTmpTmp = cashInfTmpTmpDAO.get(request.getParameter("mchntId"));
			if(tblMchtCashInfTmpTmp == null){
				tblMchtCashInfTmpTmp = new TblMchtCashInfTmpTmp();
			}
			//查询费率
			String discId=null;
			Map<String, String> map=new HashMap<String, String>();
			if(settle!=null){
				discId=settle.getFeeRate();
			}
			
			if(StringUtils.isNotEmpty(discId)){
				discId=discId.trim();
				TblHisDiscAlgoPK key=new TblHisDiscAlgoPK(discId, 0);
				TblHisDiscAlgo jTblHisDiscAlgo = tblHisDiscAlgoDAO.get(key);
				TblHisDiscAlgoPK key1=new TblHisDiscAlgoPK(discId, 1);
				TblHisDiscAlgo dTblHisDiscAlgo = tblHisDiscAlgoDAO.get(key1);
				if (jTblHisDiscAlgo!=null&&dTblHisDiscAlgo!=null) {
					map.put("jFee", jTblHisDiscAlgo.getFeeValue().toString().trim());
					map.put("jMaxFee", jTblHisDiscAlgo.getMaxFee().toString().trim());
					map.put("dFee", dTblHisDiscAlgo.getFeeValue().toString().trim());
					map.put("dMaxFee", dTblHisDiscAlgo.getMaxFee().toString().trim());
				}
			}
			/////////////添加商户风险参数显示
			TblRiskParamMng tblRiskParamMng = tblRiskParamMngDAO.get(inf.getMchtNo(), "00000000", "0");
			/////////////
//			String[] srcs = { JSONArray.fromObject(inf).toString(),
//					JSONArray.fromObject(settle).toString() ,JSONArray.fromObject(map).toString()};
			List<String> srcList = new ArrayList<String>();
			srcList.add(JSONArray.fromObject(inf).toString());
			srcList.add(JSONArray.fromObject(settle).toString());
			srcList.add(JSONArray.fromObject(map).toString());
			srcList.add(JSONArray.fromObject(tblMchtCashInfTmpTmp).toString());
			if (tblRiskParamMng != null){
				srcList.add(JSONArray.fromObject(tblRiskParamMng).toString());
			}
			String[] srcs = new String[srcList.size()];
			srcList.toArray(srcs);
			//由于《商户基本信息表》和《商户清算信息表》中都有reserved字段，页面上取值不方便
			//将《商户基本信息表》取出的reserved更名为idOtherNo
			String sub1 = srcs[0].substring(0,srcs[0].indexOf("reserved"));
			String sub2 = srcs[0].substring(srcs[0].indexOf("reserved")+"reserved".length());
			srcs[0] = sub1 + "idOtherNo" + sub2;

			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得商户临时信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getMchntTmpInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblMchtBaseInfTmpTmp inf = tblMchtBaseInfTmpTmpDAO.get(request
					.getParameter("mchntId"));
			TblMchtSettleInfTmpTmp settle = tblMchtSettleInfTmpTmpDAO.get(request
					.getParameter("mchntId"));
			TblMchtBaseInfTmp tblMchtBaseInfTmp = baseInfTmpDAO.get(request.getParameter("mchntId"));
			TblMchtCashInfTmpTmp tblMchtCashInfTmpTmp = cashInfTmpTmpDAO.get(request.getParameter("mchntId"));
			if(tblMchtCashInfTmpTmp == null){
				tblMchtCashInfTmpTmp = new TblMchtCashInfTmpTmp();
			}
			//查询费率
			String discId=null;
			if(settle!=null){
				discId=settle.getFeeRate();
			}
			Map<String, String> map=new HashMap<String, String>();
			
			if(tblMchtBaseInfTmp!=null&&tblMchtBaseInfTmp.getMchtStatus()!=null){
				String sta=tblMchtBaseInfTmp.getMchtStatus().trim();
				String areaNo=tblMchtBaseInfTmp.getAreaNo().trim();
				String mchtGrp=tblMchtBaseInfTmp.getMchtGrp().trim();
				String mcc=tblMchtBaseInfTmp.getMcc().trim();
				String rislLvl=tblMchtBaseInfTmp.getRislLvl().trim();
//				String tcc=tblMchtBaseInfTmp.getTcc().trim();
				//String mchtFunction=tblMchtBaseInfTmp.getMchtFunction().trim();
				String mchtCnAbbr=inf.getMchtCnAbbr().trim();
				map.put("status", sta);
				map.put("areaNo1", areaNo);
				map.put("mchtGrp1", mchtGrp);
				map.put("mcc1", mcc);
				map.put("rislLvl1", rislLvl);
//				map.put("tcc1", tcc);
				//map.put("mchtFunction1", mchtFunction);
				map.put("mchtCnAbbr1", mchtCnAbbr);
			}
			if(discId!=null&&discId!=""){
				discId=discId.trim();
				TblHisDiscAlgoPK key=new TblHisDiscAlgoPK(discId, 0);
				TblHisDiscAlgo jTblHisDiscAlgo = tblHisDiscAlgoDAO.get(key);
				TblHisDiscAlgoPK key1=new TblHisDiscAlgoPK(discId, 1);
				TblHisDiscAlgo dTblHisDiscAlgo = tblHisDiscAlgoDAO.get(key1);
				if (jTblHisDiscAlgo!=null&&dTblHisDiscAlgo!=null) {
					map.put("jFee", jTblHisDiscAlgo.getFeeValue().toString().trim());
					map.put("jMaxFee", jTblHisDiscAlgo.getMaxFee().toString().trim());
					map.put("dFee", dTblHisDiscAlgo.getFeeValue().toString().trim());
					map.put("dMaxFee", dTblHisDiscAlgo.getMaxFee().toString().trim());
				}
			}
			map.put("validatyDate", inf.getReserved()==null?"":inf.getReserved().trim());
			///
			if (null == inf || null == settle) {
				return null;
			}
			inf = (TblMchtBaseInfTmpTmp) CommonFunction.trimObject(inf);
			settle = (TblMchtSettleInfTmpTmp) CommonFunction.trimObject(settle);
//			System.out.println(inf.getMchtNm()+"-"+settle.getSettleBankNm());
			String[] srcs = { JSONArray.fromObject(inf).toString(),
					JSONArray.fromObject(settle).toString(),JSONArray.fromObject(map).toString(),JSONArray.fromObject(tblMchtCashInfTmpTmp).toString() };
			
			//由于《商户基本信息表》和《商户清算信息表》中都有reserved字段，页面上取值不方便
			//将《商户基本信息表》取出的reserved更名为idOtherNo
			String sub1 = srcs[0].substring(0,srcs[0].indexOf("reserved"));
			String sub2 = srcs[0].substring(srcs[0].indexOf("reserved")+"reserved".length());
			srcs[0] = sub1 + "idOtherNo" + sub2;

			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 直联商户信息（TMP）
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getMchntCupInfoTmp(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblMchtCupInfoTmp tmp = t20401BO.get(request.getParameter("mchntId"));
			
			if (null == tmp) {
				return null;
			}
			tmp = (TblMchtCupInfoTmp) CommonFunction.trimObject(tmp);
			return getMessage(JSONArray.fromObject(tmp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 直联商户信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getMchntCupInfo(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblMchtCupInfo info = t20402BO.get(request.getParameter("mchntId"));
			
			if (null == info) {
				return null;
			}
			info = (TblMchtCupInfo) CommonFunction.trimObject(info);
			return getMessage(JSONArray.fromObject(info).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 直联终端信息tmp
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getTermCupTmp(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblTermCupInfoTmp tmp = t30401BO.get(request.getParameter("termId"));
			
			if (null == tmp) {
				return null;
			}
			tmp = (TblTermCupInfoTmp) CommonFunction.trimObject(tmp);
			return getMessage(JSONArray.fromObject(tmp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 直联终端信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getTermCupInfo(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblTermCupInfo info = t30402BO.get(request.getParameter("termId"));
			
			if (null == info) {
				return null;
			}
			info = (TblTermCupInfo) CommonFunction.trimObject(info);
			return getMessage(JSONArray.fromObject(info).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 计费算法信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getFeeInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblInfDiscCd inf = t20701BO.getTblInfDiscCd(request.getParameter("discCd"));
			
			if (null == inf) {
				return null;
			}
			inf = (TblInfDiscCd) CommonFunction.trimObject(inf);
			return getMessage(JSONArray.fromObject(inf).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取终端信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *             2011-6-27下午04:17:15
	 */
	public String getTermInfo(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		String termId = request.getParameter("termId");
		String recCrtTs = request.getParameter("recCrtTs");
		TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(
				termId, ' ', 12, true), recCrtTs);
		TMSService tms = new TMSService();
		tblTermInfTmp.setFinanceCard1(tblTermInfTmp.getFinanceCard1()!=null?tblTermInfTmp.getFinanceCard1().trim():tblTermInfTmp.getFinanceCard1());
		tblTermInfTmp.setFinanceCard2(tblTermInfTmp.getFinanceCard2()!=null?tblTermInfTmp.getFinanceCard2().trim():tblTermInfTmp.getFinanceCard2());
		tblTermInfTmp.setFinanceCard3(tblTermInfTmp.getFinanceCard3()!=null?tblTermInfTmp.getFinanceCard3().trim():tblTermInfTmp.getFinanceCard3());
		tblTermInfTmp.setBindTel2(tblTermInfTmp.getBindTel2()!=null?tblTermInfTmp.getBindTel2().trim():tblTermInfTmp.getBindTel2());
		tblTermInfTmp.setBindTel3(tblTermInfTmp.getBindTel3()!=null?tblTermInfTmp.getBindTel3().trim():tblTermInfTmp.getBindTel3());
		tblTermInfTmp.setTermAddr(tblTermInfTmp.getTermAddr()!=null?tblTermInfTmp.getTermAddr().trim():tblTermInfTmp.getTermAddr());
		tblTermInfTmp.setMisc2(tblTermInfTmp.getMisc2()==null?"":tblTermInfTmp.getMisc2().trim());
		tblTermInfTmp.setProductCd(tblTermInfTmp.getProductCd()==null?"":tblTermInfTmp.getProductCd().trim());
		if (tblTermInfTmp.getProductCd() != null) {
			// 调用tms接口获取’厂商‘和’型号‘
			ArrayList<String> list = tms.checkSN(tblTermInfTmp.getProductCd());
			if (null != list) {
				// 设置‘厂商’和‘型号’
				tblTermInfTmp.setTermFactory(list.get(1));
				tblTermInfTmp.setTermMachTp(list.get(2));
			} else {
				tblTermInfTmp.setTermFactory("");
				tblTermInfTmp.setTermMachTp("");
			}
		}
		
		
		if(tblTermInfTmp!=null&&tblTermInfTmp.getMisc1()!=null&&!tblTermInfTmp.getMisc1().trim().equals("")){
			String misc1 = tblTermInfTmp.getMisc1().trim();
			int parseInt = Integer.parseInt(misc1);
			String string = String.valueOf(parseInt);
			tblTermInfTmp.setMisc1(string);
		}
		if (tblTermInfTmp != null) {
			String mchtCd = tblTermInfTmp.getMchtCd();
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp = tblMchtBaseInfTmpTmpDAO.get(mchtCd);
			if(tblMchtBaseInfTmpTmp.getEngName()==null){
				tblMchtBaseInfTmpTmp.setEngName(" ");
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("txn22Dtl", tblMchtBaseInfTmpTmp.getMchtNm());
			map.put("txn27Dtl", tblMchtBaseInfTmpTmp.getEngName());
			/////////// 添加风控限额信息
			TblRiskParamMng tblRiskParamMng = tblRiskParamMngDAO.get(tblTermInfTmp.getMchtCd(), tblTermInfTmp.getId().getTermId(), "1");
			if (tblRiskParamMng != null){
				map.put("creditSingleAmt", tblRiskParamMng.getCreditSingleAmt());
				map.put("creditDayAmt", tblRiskParamMng.getCreditDayAmt());
				map.put("creditMonAmt", tblRiskParamMng.getCreditMonAmt());
				map.put("debitSingleAmt", tblRiskParamMng.getDebitSingleAmt());
				map.put("debitDayAmt", tblRiskParamMng.getDebitDayAmt());
				map.put("debitMonAmt", tblRiskParamMng.getDebitMonAmt());
				map.put("remark", tblRiskParamMng.getRemark());
			}
			///////////
			String message = JSONArray.fromObject(tblTermInfTmp).toString().replace("}]", "") + "," +JSONArray.fromObject(map).toString().replace("[{", "");
			return getMessage(message);
		}
		return null;
	}
	
	
	public String getIcParamInf(HttpServletRequest request){
		
		T10206BO t10206BO = (T10206BO) ContextUtil.getBean("T10206BO");

		TblEmvPara para = t10206BO.get(new TblEmvParaPK(CommonFunction.fillString("0", ' ', 8, true),CommonFunction.fillString(request.getParameter("index"), ' ', 4, true)));
		
		TblEmvParaValue value = new TblEmvParaValue(para.getParaVal(), para.getParaOrg());
		
		return getMessage(JSONArray.fromObject(value).toString());
	}

	
	
	/**
	 * 获得路由信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getRouteRuleInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblRouteRulePK tblRouteRulePK=new TblRouteRulePK();
			tblRouteRulePK.setAccpId(request.getParameter("accpId"));
			tblRouteRulePK.setBrhId(request.getParameter("brhId"));
			tblRouteRulePK.setRuleId(request.getParameter("ruleId"));
			TblRouteRule tblRouteRule = t10600BO.get(tblRouteRulePK);
			

			if (null == tblRouteRule ) {
				return null;
			}
			tblRouteRule = (TblRouteRule) CommonFunction.trimObject(tblRouteRule);
			String[] srcs = { JSONArray.fromObject(tblRouteRule).toString(), };
			
			

			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得风控警报
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getRiskAlarm(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		TblRiskAlarmDAO tblRiskAlarmDAO = (TblRiskAlarmDAO) ContextUtil.getBean("TblRiskAlarmDAO");
		TblRiskInfDAO tblRiskInfDAO= (TblRiskInfDAO) ContextUtil.getBean("TblRiskInfDAO");
		TblRiskParamInfDAO tblRiskParamInfDAO=(TblRiskParamInfDAO) ContextUtil.getBean("TblRiskParamInfDAO");
		String alarmId=request.getParameter("alarmId");
		Integer alarmSeq=Integer.parseInt(request.getParameter("alarmSeq"));
		TblRiskAlarmPK tblRiskAlarmPK=new TblRiskAlarmPK();
		tblRiskAlarmPK.setAlarmId(alarmId);
		tblRiskAlarmPK.setAlarmSeq(alarmSeq);
		TblRiskAlarm tblRiskAlarm = tblRiskAlarmDAO.get(tblRiskAlarmPK);
		if (null == tblRiskAlarm ) {
			return null;
		}
		tblRiskAlarm = (TblRiskAlarm) CommonFunction.trimObject(tblRiskAlarm);
		TblRiskInf tblRiskInf= tblRiskInfDAO.get(tblRiskAlarm.getRiskId());
		Map<String ,String> map=new HashMap<String, String>();
		map.put("saModelDesc", tblRiskInf.getSaModelDesc());
		map.put("alarmId", alarmId);
		map.put("alarmSeq", String.valueOf(alarmSeq).toString());
		tblRiskAlarm.setAlarmLvl(tblRiskInf.getMisc());;
		for (int i = 0; i < 2; i++) {
			TblRiskParamInfPK tblRiskParamInfPK=new TblRiskParamInfPK();
			tblRiskParamInfPK.setModelKind(tblRiskAlarm.getRiskId());
			tblRiskParamInfPK.setRiskLvl(tblRiskAlarm.getRiskLvl());
			tblRiskParamInfPK.setModelSeq(String.valueOf(i+1));
			TblRiskParamInf tblRiskParamInf =tblRiskParamInfDAO.get(tblRiskParamInfPK);
			String riskParaName="";
			String riskParaValue="";
			if(tblRiskParamInf!=null){
				riskParaName=tblRiskParamInf.getParamName();
				riskParaValue=tblRiskParamInf.getParamValue();
			}
			map.put("riskParaName"+String.valueOf(i+1), riskParaName);
			map.put("riskParaValue"+String.valueOf(i+1), riskParaValue);
			
		}
		
		String[] srcs = { JSONArray.fromObject(tblRiskAlarm).toString(),JSONArray.fromObject(JSONObject.fromObject(map)).toString() };
		return getMessage(srcs);
	}
	
	
	/**
	 * 获得机构信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getBrhInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
		String brhId=request.getParameter("brhId");
		TblBrhInfo tblBrhInfo=t10101BO.get(brhId);
		TblBrhSettleInf tblBrhSettleInf=null;
//		String resv1_7=tblBrhInfo.getResv1().length()>6?tblBrhInfo.getResv1().substring(6,7):"";
//		if((CommonsConstants.CHECKED.equals(resv1_7)&&t10101BO.getSettle(brhId)!=null)){
			tblBrhSettleInf=t10101BO.getSettle(brhId);
//		}else{
//			tblBrhSettleInf=new TblBrhSettleInf();
//			tblBrhSettleInf.setBrhId(brhId);
//		}
				
		
		Map<String ,String> map=new HashMap<String, String>();
		map.put("resv1_5", tblBrhInfo.getResv1().length()>4?tblBrhInfo.getResv1().substring(4,5):"");
		map.put("resv1_6", tblBrhInfo.getResv1().length()>5?tblBrhInfo.getResv1().substring(5,6):"");
		map.put("resv1_7", tblBrhInfo.getResv1().length()>6?tblBrhInfo.getResv1().substring(6,7):"");
		map.put("resv1_8", tblBrhInfo.getResv1().length()>7?tblBrhInfo.getResv1().substring(7,8):"");
		map.put("brhFee", tblBrhInfo.getResv1().length()>8?tblBrhInfo.getResv1().substring(8):"");
		tblBrhInfo.setResv1(tblBrhInfo.getResv1().substring(0,4));;
		
		String[] srcs = { JSONArray.fromObject(tblBrhInfo).toString(),JSONArray.fromObject(tblBrhSettleInf).toString(),JSONArray.fromObject(JSONObject.fromObject(map)).toString() };
		return getMessage(srcs);
	}
	
	/**
	 * 获得机构信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getAgentInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
		T10105BO t10105BO = (T10105BO) ContextUtil.getBean("T10105BO");
		String brhId=request.getParameter("brhId");
		TblBrhInfo tblBrhInfo=t10101BO.get(brhId);
//		TblBrhSettleInf tblBrhSettleInf=null;
		Map<String ,String> map=new HashMap<String, String>();
//		String resv1_7=tblBrhInfo.getResv1().length()>6?tblBrhInfo.getResv1().substring(6,7):"";
//		if((CommonsConstants.CHECKED.equals(resv1_7)&&t10101BO.getSettle(brhId)!=null)){
		TblBrhSettleInf	tblBrhSettleInf=t10101BO.getSettle(brhId);
//		}else{
//			tblBrhSettleInf=new TblBrhSettleInf();
//			tblBrhSettleInf.setBrhId(brhId);
//		}
			
		TblAgentFeeCfg tblAgentFeeCfg = t10105BO.getAgentInfo(brhId);
		
		//将费率等六个字段改为百分比
		DecimalFormat df=new DecimalFormat("#.000");

				if(tblAgentFeeCfg.getPromotionRate()!=null){
					tblAgentFeeCfg.setPromotionRate(Double.valueOf(df.format(tblAgentFeeCfg.getPromotionRate()*100.0)));
				}

				if(tblAgentFeeCfg.getAllotRate()!=null){
					tblAgentFeeCfg.setAllotRate(Double.valueOf(df.format(tblAgentFeeCfg.getAllotRate()*100.0)));
				}

				if(tblAgentFeeCfg.getExtJcbRate()!=null){
					tblAgentFeeCfg.setExtJcbRate(Double.valueOf(df.format(tblAgentFeeCfg.getExtJcbRate()*100.0)));
				}

				if(tblAgentFeeCfg.getExtMasterRate()!=null){
					tblAgentFeeCfg.setExtMasterRate(Double.valueOf(df.format(tblAgentFeeCfg.getExtMasterRate()*100.0)));;
				}

				if(tblAgentFeeCfg.getExtVisaRate()!=null){
					tblAgentFeeCfg.setExtVisaRate(Double.valueOf(df.format(tblAgentFeeCfg.getExtVisaRate()*100.0)));
				}

				if(tblAgentFeeCfg.getSpecFeeRate()!=null){
					tblAgentFeeCfg.setSpecFeeRate(Double.valueOf(df.format(tblAgentFeeCfg.getSpecFeeRate()*100.0)));
				}
				
		
		if(tblBrhSettleInf != null){
			Map bankInfoMap =t10105BO.getAreaInfoByBankNo(tblBrhSettleInf.getSettleBankNo());
			map.putAll(bankInfoMap);
		}else{
			tblBrhSettleInf = new TblBrhSettleInf();
		}
		
		map.put("resv1_5", tblBrhInfo.getResv1().length()>4?tblBrhInfo.getResv1().substring(4,5):"");
		map.put("resv1_6", tblBrhInfo.getResv1().length()>5?tblBrhInfo.getResv1().substring(5,6):"");
		map.put("resv1_7", tblBrhInfo.getResv1().length()>6?tblBrhInfo.getResv1().substring(6,7):"");
		map.put("resv1_8", tblBrhInfo.getResv1().length()>7?tblBrhInfo.getResv1().substring(7,8):"");
		map.put("brhFee", tblBrhInfo.getResv1().length()>8?tblBrhInfo.getResv1().substring(8):"");
		tblBrhInfo.setResv1(tblBrhInfo.getResv1().substring(0,4));;
		
		String[] srcs = { JSONArray.fromObject(tblBrhInfo).toString(),JSONArray.fromObject(tblBrhSettleInf).toString(),JSONArray.fromObject(JSONObject.fromObject(map)).toString(),
				JSONArray.fromObject(tblAgentFeeCfg).toString()};
		return getMessage(srcs);
	}
	
	/**
	 * 获得机构信息（历史）
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getAgentInfHis(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
		T10105BO t10105BO = (T10105BO) ContextUtil.getBean("T10105BO");
		String brhId=request.getParameter("brhId");
		String seqId=request.getParameter("seqId");
		TblBrhInfoHis tblBrhInfoHis=t10101BO.getBrhHis(brhId, seqId);
//		TblBrhSettleInf tblBrhSettleInf=null;
		Map<String ,String> map=new HashMap<String, String>();
//		String resv1_7=tblBrhInfo.getResv1().length()>6?tblBrhInfo.getResv1().substring(6,7):"";
//		if((CommonsConstants.CHECKED.equals(resv1_7)&&t10101BO.getSettle(brhId)!=null)){
		TblBrhSettleInfHis	tblBrhSettleInfHis=t10101BO.getSettleHis(brhId, seqId);
//		}else{
//			tblBrhSettleInf=new TblBrhSettleInf();
//			tblBrhSettleInf.setBrhId(brhId);
//		}
			
		TblAgentFeeCfgHis tblAgentFeeCfgHis = t10105BO.getAgentInfoHis(brhId, seqId);
		
		//将费率等六个字段改为百分比
		DecimalFormat df=new DecimalFormat("#.000");

				if(tblAgentFeeCfgHis.getPromotionRate()!=null){
					tblAgentFeeCfgHis.setPromotionRate(Double.valueOf(df.format(tblAgentFeeCfgHis.getPromotionRate()*100.0)));
				}

				if(tblAgentFeeCfgHis.getAllotRate()!=null){
					tblAgentFeeCfgHis.setAllotRate(Double.valueOf(df.format(tblAgentFeeCfgHis.getAllotRate()*100.0)));
				}

				if(tblAgentFeeCfgHis.getExtJcbRate()!=null){
					tblAgentFeeCfgHis.setExtJcbRate(Double.valueOf(df.format(tblAgentFeeCfgHis.getExtJcbRate()*100.0)));
				}

				if(tblAgentFeeCfgHis.getExtMasterRate()!=null){
					tblAgentFeeCfgHis.setExtMasterRate(Double.valueOf(df.format(tblAgentFeeCfgHis.getExtMasterRate()*100.0)));;
				}

				if(tblAgentFeeCfgHis.getExtVisaRate()!=null){
					tblAgentFeeCfgHis.setExtVisaRate(Double.valueOf(df.format(tblAgentFeeCfgHis.getExtVisaRate()*100.0)));
				}

				if(tblAgentFeeCfgHis.getSpecFeeRate()!=null){
					tblAgentFeeCfgHis.setSpecFeeRate(Double.valueOf(df.format(tblAgentFeeCfgHis.getSpecFeeRate()*100.0)));
				}
				
		
		if(tblBrhSettleInfHis != null){
			Map bankInfoMap =t10105BO.getAreaInfoByBankNo(tblBrhSettleInfHis.getSettleBankNo());
			map.putAll(bankInfoMap);
		}else{
			tblBrhSettleInfHis = new TblBrhSettleInfHis();
		}
		
		map.put("resv1_5", tblBrhInfoHis.getResv1().length()>4?tblBrhInfoHis.getResv1().substring(4,5):"");
		map.put("resv1_6", tblBrhInfoHis.getResv1().length()>5?tblBrhInfoHis.getResv1().substring(5,6):"");
		map.put("resv1_7", tblBrhInfoHis.getResv1().length()>6?tblBrhInfoHis.getResv1().substring(6,7):"");
		map.put("resv1_8", tblBrhInfoHis.getResv1().length()>7?tblBrhInfoHis.getResv1().substring(7,8):"");
		map.put("brhFee", tblBrhInfoHis.getResv1().length()>8?tblBrhInfoHis.getResv1().substring(8):"");
		tblBrhInfoHis.setResv1(tblBrhInfoHis.getResv1().substring(0,4));;
		
		String[] srcs = { JSONArray.fromObject(tblBrhInfoHis).toString(),JSONArray.fromObject(tblBrhSettleInfHis).toString(),JSONArray.fromObject(JSONObject.fromObject(map)).toString(),
				JSONArray.fromObject(tblAgentFeeCfgHis).toString()};
		return getMessage(srcs);
	}
	/**
	 * 获得照片路径数组的json格式数据
	 * 
	 * @param request 
	 * @return json格式数据
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getPicturePaths(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		String folderPath = request.getParameter("folderPath");
		if (StringUtil.isEmpty(folderPath)) {
			log.error("folderPath parameter required.");
			return "";
		}
		String func = request.getParameter("func");
		String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK) ;
		if(func != null && !"".equals(func.trim())){
			if("adjustErrTrad".equals(func.trim())){
				basePath = SysParamUtil.getParam(SysParamConstants.ADJUST_OFFLINE_FILE_DISK);
			}
		}
		String filePath = basePath + folderPath;
		File fileFolder = new File(filePath);
		if (!fileFolder.isDirectory()) {
			log.error("this is not a valid directory :" + filePath);
			return "";
		}
		List<PictureInfo> picPaths= new ArrayList<PictureInfo>();
		//获取所有的图片路径
		if(func != null && !"".equals(func.trim())){
			if("adjustErrTrad".equals(func.trim())){
				getAdjustErrTrad(fileFolder, picPaths);
			}
		}else{
			getPictureInfos(fileFolder, picPaths);
		}
		
		String srcs=JSONArray.fromObject(picPaths).toString(); 
		return getMessage(srcs);
	}	
	
	private void getPictureInfos(File folder, List<PictureInfo> picInfos) {
		String uploadPath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
		String filePathRegs[] = SysParamUtil.getParam(SysParamConstants.UPLOAD_CHILD_FOLDER).split(",");
		String fileNames[] = SysParamUtil.getParam(SysParamConstants.UPLOAD_CHILD_FOLDER_NAME).split(",");
		Map<String,String> pathMap = new HashMap<String,String>();
		for (int i=0; i<filePathRegs.length; i++) {
			pathMap.put(filePathRegs[i], fileNames[i]);
		}
		PictureInfo picInfo = null;
		for (File file:folder.listFiles()) {
			if (file.isDirectory()) {
				getPictureInfos(file, picInfos);
			} else {
				picInfo = new PictureInfo();
				String absoluteFileName = file.getAbsolutePath().replaceAll("\\\\", "/");
				int pos = absoluteFileName.indexOf(uploadPath) + uploadPath.length();
				String relativePath = absoluteFileName.substring(pos);
				String[] pathPieces = relativePath.split("/"); 
				picInfo.setId(relativePath);
				picInfo.setPicUrl(relativePath);
				if (pathMap.containsKey(pathPieces[1])) {
					picInfo.setPicBizName(pathMap.get(pathPieces[1]));
				} else {
					if (relativePath.contains("_qyxy_")) {//特约商户审核,签约协议是放在同一个文件夹下的，所以必须通过文件名来区分
						picInfo.setPicBizName(pathMap.get(pathPieces[1]+"/" + "_qyxy_"));
					} else if (relativePath.contains("_shdjb_")) {
						picInfo.setPicBizName(pathMap.get(pathPieces[1]+"/" + "_shdjb_"));
					} else {
						picInfo.setPicBizName("");
						log.error("there is no file name mapping of path :" + relativePath);
					}
				}
				picInfos.add(picInfo);
			}
		}
	}
	/**
	 * 差错交易手工调账影像
	 */
	public static void getAdjustErrTrad(File folder, List<PictureInfo> picInfos) {
		File[] files = folder.listFiles();
		PictureInfo picInfo = null;
		if(files != null){
			for (File file : files) {
				picInfo = new PictureInfo();
				picInfo.setId(file.getName());
				picInfo.setPicUrl(folder.getName() + "/" + file.getName());
				picInfo.setPicBizName(folder.getName());
				picInfos.add(picInfo);
			}
		}
	}
	
	/**
	 * 格式化输出
	 * 
	 * @param src
	 * @return 2011-6-22下午01:43:30
	 */
	public String getMessage(String src) {
		return "{\"data\":" + src + "}";
	}

	

	/**
	 * 多对象格式化输出
	 * 
	 * @param src
	 * @return 2011-6-22下午01:43:30
	 */
	public String getMessage(String[] srcs) {

		StringBuffer sb = new StringBuffer("{\"data\":[{");
		for (String src : srcs) {
			src = src.substring(2).trim();
			sb.append(src);
			sb.delete(sb.length() - 2, sb.length());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}]}");
		return sb.toString();
	}
	/**
	 * 查询商户风控以及终端交易信息
	 * @return
	 */
	public String getTermRiskInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		String termId = request.getParameter("termId");
		String mchntNo = request.getParameter("mchntNo");
		String recCrtTs = request.getParameter("recCrtTs");
		TblTermInfTmpPK key = new TblTermInfTmpPK(CommonFunction.fillString(termId, ' ', 12, true), CommonFunction.fillString(recCrtTs, ' ', 14, true));
		TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(key );
		TblRiskParamMng tblRiskParamMng = tblRiskParamMngDAO.get(mchntNo, CommonFunction.fillString(termId, ' ', 12, true), "1");
		if(tblRiskParamMng == null){
			tblRiskParamMng = tblRiskParamMngDAO.get(mchntNo, "00000000", "0");
			if(tblRiskParamMng == null){
				map.put("creditSingleAmt", "0");
				map.put("debitSingleAmt", "0");
				map.put("creditMonAmt", "0");
				map.put("debitMonAmt", "0");
				map.put("creditDayAmt", "0");
				map.put("debitDayAmt", "0");
				map.put("remark", "");
			}else {
				map.put("creditSingleAmt", tblRiskParamMng.getCreditSingleAmt());
				map.put("debitSingleAmt", tblRiskParamMng.getDebitSingleAmt());
				map.put("creditMonAmt", "0");
				map.put("debitMonAmt", "0");
				map.put("creditDayAmt", "0");
				map.put("debitDayAmt", "0");
				map.put("remark", "");
			}
		}else {
			map.put("creditSingleAmt", tblRiskParamMng.getCreditSingleAmt());
			map.put("debitSingleAmt", tblRiskParamMng.getDebitSingleAmt());
			map.put("creditMonAmt", tblRiskParamMng.getCreditMonAmt());
			map.put("debitMonAmt", tblRiskParamMng.getDebitMonAmt());
			map.put("creditDayAmt", tblRiskParamMng.getCreditDayAmt());
			map.put("debitDayAmt", tblRiskParamMng.getDebitDayAmt());
			map.put("remark", tblRiskParamMng.getRemark());
		}
		if(tblTermInfTmp!=null){
			map.put("termPara1", tblTermInfTmp.getTermPara1());
		}
		String result = JSONArray.fromObject(map).toString();
		return getMessage(result);
		
	}
}
