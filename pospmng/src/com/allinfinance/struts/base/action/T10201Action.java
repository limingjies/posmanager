/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-17       first release
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
package com.allinfinance.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.base.T10201BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.TblCityCode;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:地区码维护
 * 
 * Description:
 * 
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author caotz
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10201Action extends BaseAction {
	
	
	private T10201BO t10201BO = (T10201BO) ContextUtil.getBean("T10201BO");
	
	private String intCityCode;
	private String cupCityCode;
//	private String mchtAddr;
	private String cityName;
//	private String cityFlag;
	private String cityCodeList;	
//	private String mchtCityCode;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增地区码信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除地区码信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if("update".equals(method)) {
			log("同步地区码信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}
		
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 */
	private String add() {
		
		if(t10201BO.get(cupCityCode) != null){
			return "该地区码已经存在！";
		}
		
		TblCityCode TblCityCode = getCstCityCode();
		
		return t10201BO.add(TblCityCode);
		
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
		
		if(t10201BO.get(cupCityCode) == null) {
			return "没有找到要删除的地区码信息";
		}
		
		return t10201BO.delete(cupCityCode);
	}
	
	/**
	 * update city code
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getCityCodeList());
		
		int len = jsonBean.getArray().size();
		
		TblCityCode tblCityCode = null;
		
		List<TblCityCode> cityCodeInfoList = new ArrayList<TblCityCode>(len);
		
		for(int i = 0; i < len; i++) {			
			tblCityCode = new TblCityCode();			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));			
			BeanUtils.setObjectWithPropertiesValue(tblCityCode, jsonBean, false);
			TblCityCode tblCupCityCode = t10201BO.get(tblCityCode.getCupCityCode());			
			String sysTime = CommonFunction.getCurrentDate()+CommonFunction.getCurrentTime();
			String INIT_OPR_ID = "".equals(operator.getOprId()) ? "-" : operator.getOprId().trim();		
			tblCityCode.setCityFlag(tblCupCityCode.getCityFlag());
			tblCityCode.setMchtAddr("-");
			tblCityCode.setInitOprId(tblCupCityCode.getInitOprId());
			tblCityCode.setModiOprId(INIT_OPR_ID);
			tblCityCode.setInitTime(tblCupCityCode.getInitTime());
			tblCityCode.setModiTime(sysTime);
			tblCityCode.setMchtCityCode(tblCupCityCode.getMchtCityCode());
			cityCodeInfoList.add(tblCityCode);
		}
		
		t10201BO.update(cityCodeInfoList);
		
		return Constants.SUCCESS_CODE;
	}
	

	
	public String getIntCityCode() {
		return intCityCode;
	}

	public void setIntCityCode(String intCityCode) {
		this.intCityCode = intCityCode;
	}

	public String getCupCityCode() {
		return cupCityCode;
	}

	public void setCupCityCode(String cupCityCode) {
		this.cupCityCode = cupCityCode;
	}
	

	public T10201BO getT10201BO() {
		return t10201BO;
	}

	public void setT10201BO(T10201BO t10201bo) {
		t10201BO = t10201bo;
	}


	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the cityCodeList
	 */
	public String getCityCodeList() {
		return cityCodeList;
	}

	/**
	 * @param cityCodeList the cityCodeList to set
	 */
	public void setCityCodeList(String cityCodeList) {
		this.cityCodeList = cityCodeList;
	}
	
	
	private TblCityCode getCstCityCode(){
		String sysTime = CommonFunction.getCurrentDate()+CommonFunction.getCurrentTime();
		TblCityCode cstCityCode = new TblCityCode();
		String INT_CITY_CODE = "".equals(intCityCode) ? "-" : intCityCode.trim();
		String CUP_CITY_CODE = "".equals(cupCityCode) ? "-" : cupCityCode.trim();
		String MCHT_CITY_CODE= "".equals(cupCityCode)? "-" : cupCityCode.trim();
		String CITY_NAME = "".equals(cityName) ? "-" : cityName.trim();
//		String MCHT_ADDR = "".equals(mchtAddr) ? "-" : mchtAddr.trim();
		String INIT_OPR_ID = "".equals(operator.getOprId()) ? "-" : operator.getOprId().trim();		
		cstCityCode.setIntCityCode(INT_CITY_CODE);
		cstCityCode.setCupCityCode(CUP_CITY_CODE);
		cstCityCode.setMchtCityCode(MCHT_CITY_CODE);
		cstCityCode.setCityFlag("0");
		cstCityCode.setMchtAddr("-");
		cstCityCode.setCityName(CITY_NAME);
		cstCityCode.setInitOprId(INIT_OPR_ID);
		cstCityCode.setModiOprId(INIT_OPR_ID);
		cstCityCode.setInitTime(sysTime);
		cstCityCode.setModiTime(sysTime);
		return cstCityCode;
	}
	
}
