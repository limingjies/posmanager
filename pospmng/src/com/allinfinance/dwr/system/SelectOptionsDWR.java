/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-12       first release
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
package com.allinfinance.dwr.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.select.SelectMethod;
import com.allinfinance.common.select.SelectOption;
import com.allinfinance.dao.iface.base.TblBrhInfoDAO;


import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.JSONBean;

/**
 * Title:异步读取下拉列表内容
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-12
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public class SelectOptionsDWR {
	
	private static Logger log = Logger.getLogger(SelectOptionsDWR.class);
	/**
	 * 根据下拉列表所显示数据类型显示内容
	 * @param txnId
	 * @return
	 */
	public String getComboData(String txnId,HttpServletRequest request,
							HttpServletResponse response) {
		String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
		try {
			//获得操作员信息
			Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
			LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[]{operator});
			Iterator<String> iter = dataMap.keySet().iterator();
			if(iter.hasNext()) {
				Map<String, Object> jsonDataMap = new HashMap<String, Object>();
				LinkedList<Object> jsonDataList = new LinkedList<Object>();
				Map<String, String> tmpMap = null;
				String key = null;
				while(iter.hasNext()) {
					tmpMap = new LinkedHashMap<String, String>();
					key = iter.next();
					tmpMap.put("valueField", key);
					tmpMap.put("displayField", dataMap.get(key));
					jsonDataList.add(tmpMap);
				}
				jsonDataMap.put("data", jsonDataList);
				jsonData = JSONBean.genMapToJSON(jsonDataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 根据下拉列表所显示数据类型显示内容，可带参数
	 * @param txnId
	 * @return
	 */
	public String getComboDataWithParameter(String txnId,String parameter,HttpServletRequest request,
							HttpServletResponse response) {
		
		String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
		try {
			//获得操作员信息
			Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
			LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[]{operator,parameter});
			Iterator<String> iter = dataMap.keySet().iterator();
			if(iter.hasNext()) {
				Map<String, Object> jsonDataMap = new HashMap<String, Object>();
				LinkedList<Object> jsonDataList = new LinkedList<Object>();
				Map<String, String> tmpMap = null;
				String key = null;
				while(iter.hasNext()) {
					tmpMap = new LinkedHashMap<String, String>();
					key = iter.next();
					tmpMap.put("valueField", key);
					tmpMap.put("displayField", dataMap.get(key));
					jsonDataList.add(tmpMap);
				}
				jsonDataMap.put("data", jsonDataList);
				jsonData = JSONBean.genMapToJSON(jsonDataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
//		System.out.println(jsonData);
		return jsonData;
	}
	
	/**
	 * 获得数据集
	 * @param txnId
	 * @param request
	 * @param response
	 * @return
	 * 2010-8-18上午11:36:58
	 */
	public LinkedHashMap<String, String> getDataMap(String txnId,HttpServletRequest request,
			HttpServletResponse response)  {
		try {
		//获得操作员信息
			Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
			LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[]{operator});
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获得权限集
	 * @param txnId
	 * @param request
	 * @param response
	 * @return
	 * 2010-8-18上午11:36:58
	 */	
	public String getFuncAllData(String txnId,HttpServletRequest request,HttpServletResponse response) {
		String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
		try {
			//获得操作员信息
			Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
			LinkedHashMap<String, String> dataMap = SelectOption.getSelectView(txnId, new Object[]{operator});
			Iterator<String> iter = dataMap.keySet().iterator();
			if(iter.hasNext()) {
				Map<String, Object> jsonDataMap = new HashMap<String, Object>();
				LinkedList<Object> jsonDataList = new LinkedList<Object>();
				Map<String, String> tmpMap = null;
				String key = null;
				while(iter.hasNext()) {
					tmpMap = new LinkedHashMap<String, String>();
					key = iter.next();
					tmpMap.put("valueField", key);
					tmpMap.put("displayField", dataMap.get(key));
					jsonDataList.add(tmpMap);
				}
				jsonDataMap.put("data", jsonDataList);
				jsonData = JSONBean.genMapToJSON(jsonDataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	public String getOprRoleType(String key){
		TblBrhInfoDAO brhDA0=(TblBrhInfoDAO) ContextUtil.getBean("BrhInfoDAO");
		TblBrhInfo inf=brhDA0.get(key);
		Map<String, String> roleMap;
		
		List<Object> jsonDataList=new LinkedList<Object>();
		//角色属于通联金融，终审权限
		if(inf.getBrhLevel().equalsIgnoreCase("0")){
			roleMap=new LinkedHashMap<String, String>();
			roleMap.put("valueField","2" );
			roleMap.put("displayField", "终审");	
			jsonDataList.add(roleMap);
			
			
		}
		//支行
		if(inf.getBrhLevel().equalsIgnoreCase("2")){
			roleMap=new LinkedHashMap<String, String>();
			roleMap.put("valueField","0");
			roleMap.put("displayField", "录入");
			jsonDataList.add(roleMap);
			Map<String,String> map2=new LinkedHashMap<String, String>();
			map2.put("valueField","3" );
			map2.put("displayField", "无权限");
			jsonDataList.add(map2);
			
		}
		//分行
		if(inf.getBrhLevel().equalsIgnoreCase("1")){
			roleMap=new LinkedHashMap<String, String>();
			roleMap.put("valueField","1");
			roleMap.put("displayField", "初审");
			jsonDataList.add(roleMap);
			Map<String,String> map2=new LinkedHashMap<String, String>();
			map2.put("valueField","2" );
			map2.put("displayField", "终审");
			jsonDataList.add(map2);
		}
		Map<String, Object> jsonDataMap=new LinkedHashMap<String, Object>();
		jsonDataMap.put("data", jsonDataList);
//		System.out.println(JSONBean.genMapToJSON(jsonDataMap));
		return JSONBean.genMapToJSON(jsonDataMap);
		
	}
	
	
	/**
	 *@ 机构选择的时候的信息 
	 **/
	public String loadCupBrhIdOptData(String brhId){
		String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
		try {
			log.info("brhId=" + brhId);
			Object[] params = new Object[1];
			params[0] = StringUtils.substring(brhId, brhId.length()-4, brhId.length());
			LinkedHashMap<String, String> dataMap = SelectMethod.getCupBrh(params);
			Iterator<String> iter = dataMap.keySet().iterator();
			if(iter.hasNext()) {
				Map<String, Object> jsonDataMap = new HashMap<String, Object>();
				LinkedList<Object> jsonDataList = new LinkedList<Object>();
				Map<String, String> tmpMap = null;
				String key = null;
				while(iter.hasNext()) {
					tmpMap = new LinkedHashMap<String, String>();
					key = iter.next();
					tmpMap.put("valueField", key);
					tmpMap.put("displayField", dataMap.get(key));
					jsonDataList.add(tmpMap);
				}
				jsonDataMap.put("data", jsonDataList);
				jsonData = JSONBean.genMapToJSON(jsonDataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	
	/**
	 *@ 选择二级商户编号的时候 
	 **/
	public String loadSecMchtdataData(String mchtNoId){
		String jsonData = "{data:[{'mchtNm':'','mchtMCC':'','mchtFeeRate':''}]}";
		try {
			log.info("mchtNoId=" + mchtNoId);
			Object[] params = new Object[1];
			params[0] = mchtNoId;
			LinkedHashMap<String, String> dataMap = SelectMethod.getMchtData(params);
			Iterator<String> iter = dataMap.keySet().iterator();
			if(iter.hasNext()) {
				Map<String, Object> jsonDataMap = new HashMap<String, Object>();
				LinkedList<Object> jsonDataList = new LinkedList<Object>();
				Map<String, String> tmpMap = null;
				String key = null;
				while(iter.hasNext()) {
					tmpMap = new LinkedHashMap<String, String>();
					key = iter.next();
					if(key.equals("mchtNm")){
						tmpMap.put("mchtNm", dataMap.get(key));
					}else if(key=="mchtMCC"){
						tmpMap.put("mchtMCC", dataMap.get(key));
					}else if(key=="mchtFeeRate"){
						tmpMap.put("mchtFeeRate", dataMap.get(key));
					}
					jsonDataList.add(tmpMap);
				}
				jsonDataMap.put("data", jsonDataList);
				jsonData = JSONBean.genMapToJSON(jsonDataMap);
//				System.out.println(jsonData);

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	
	/**
	 *@ 选择一级商户编号的时候 
	 **/
	public String loadFirMchtdataData(String firMchtNoId){
		String jsonData = "{data:[{'firMchtNm':'','firstTermNomber':'','firstMccNo':'','firstMchtFeeRate':''}]}";
		try {
			log.info("firMchtNoId=" + firMchtNoId);
			Object[] params = new Object[1];
			params[0] = firMchtNoId;
			LinkedHashMap<String, String> dataMap = SelectMethod.getFirMchtData(params);
			Iterator<String> iter = dataMap.keySet().iterator();
			if(iter.hasNext()) {
				Map<String, Object> jsonDataMap = new HashMap<String, Object>();
				LinkedList<Object> jsonDataList = new LinkedList<Object>();
				Map<String, String> tmpMap = null;
				String key = null;
				while(iter.hasNext()) {
					tmpMap = new LinkedHashMap<String, String>();
					key = iter.next();
					if(key.equals("firMchtNm")){
						tmpMap.put("firMchtNm", dataMap.get(key));
					}else if(key=="firstTermNomber"){
						tmpMap.put("firstTermNomber", dataMap.get(key));
					}else if(key=="firstMccNo"){
						tmpMap.put("firstMccNo", dataMap.get(key));
					}else if(key=="firstMchtFeeRate"){
						tmpMap.put("firstMchtFeeRate", dataMap.get(key));
					}
					jsonDataList.add(tmpMap);
				}
				jsonDataMap.put("data", jsonDataList);
				jsonData = JSONBean.genMapToJSON(jsonDataMap);
//				System.out.println(jsonData);

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	
}