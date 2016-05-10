package com.allinfinance.struts.daytrade.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allinfinance.bo.daytrade.T70100BO;
import com.allinfinance.po.daytrade.SysParamsConfig;
import com.allinfinance.po.daytrade.SysParamsConfigPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.ContextUtil;

/**
 *  T70102Action.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年6月4日	  徐鹏飞          created this class.
 *    
 *  
 *  Copyright Notice:
 *   Copyright (c) 2009-2015   Allinpay Financial Services Co., Ltd. 
 *    All rights reserved.
 *
 *   This software is the confidential and proprietary information of
 *   allinfinance.com  Inc. ('Confidential Information').  You shall not
 *   disclose such Confidential Information and shall use it only in
 *   accordance with the terms of the license agreement you entered
 *   into with Allinpay Financial.
 *
 *  Warning:
 *  =========================================================================
 *  
 */
public class T70102Action extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private T70100BO t70100BO = (T70100BO) ContextUtil.getBean("T70100BO");
	
	@Override
	protected String subExecute() throws Exception {
		if("add".equals(getMethod())) {			
			rspCode = add();			
		}else if("update".equals(getMethod())) {
			rspCode = update();
		}else if("delete".equals(getMethod())) {
			rspCode = delete();
		}else {
			return "未知的交易类型";
		}
		return rspCode;
	}

	private String add() throws Exception {
		jsonBean.parseJSONArrayData(getDataList());
		int len = jsonBean.getArray().size();
		List <SysParamsConfig> addList = new ArrayList<SysParamsConfig>();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			SysParamsConfigPK sysParamsConfigPK = new SysParamsConfigPK();
			SysParamsConfig sysParamsConfig = new SysParamsConfig();
			BeanUtils.setObjectWithPropertiesValue(sysParamsConfigPK,jsonBean,false);
			BeanUtils.setObjectWithPropertiesValue(sysParamsConfig,jsonBean,false);
			sysParamsConfig.setId(sysParamsConfigPK);
			SysParamsConfig sysParamsConfigOld = t70100BO.get(sysParamsConfigPK);
			if(sysParamsConfigOld!=null){
				return "该商户或机构已配置参数！";
			}
			sysParamsConfig.setCreateDate(new Date());
			sysParamsConfig.setCreateBy(operator.getOprId());
			sysParamsConfig.setUpdateDate(new Date());
			sysParamsConfig.setUpdateBy(operator.getOprId());
			sysParamsConfig.setStatus("0");
			addList.add(sysParamsConfig);
		}
		return t70100BO.addList(addList);
	}

	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getDataList());
		int len = jsonBean.getArray().size();
		List <SysParamsConfig> updateList = new ArrayList<SysParamsConfig>();
		List <SysParamsConfig> deleteList = new ArrayList<SysParamsConfig>();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			SysParamsConfigPK sysParamsConfigPK = new SysParamsConfigPK();
			SysParamsConfig sysParamsConfig = new SysParamsConfig();
			BeanUtils.setObjectWithPropertiesValue(sysParamsConfigPK,jsonBean,false);
			BeanUtils.setObjectWithPropertiesValue(sysParamsConfig,jsonBean,false);
			sysParamsConfig.setId(sysParamsConfigPK);
			SysParamsConfig sysParamsConfigOld = t70100BO.get(sysParamsConfigPK);
			if("".equals(sysParamsConfig.getParamValue())
					||sysParamsConfig.getParamValue()==null){
				if(sysParamsConfigOld!=null){
					deleteList.add(sysParamsConfig);
				}
			}else {
				if(sysParamsConfigOld==null){
					sysParamsConfig.setCreateBy(operator.getOprId());
					sysParamsConfig.setCreateDate(new Date());
					sysParamsConfig.setUpdateBy(operator.getOprId());
					sysParamsConfig.setUpdateDate(new Date());
					sysParamsConfig.setStatus("0");
					updateList.add(sysParamsConfig);
				}else if(!sysParamsConfigOld.getParamValue().equals(sysParamsConfig.getParamValue())){
					sysParamsConfigOld.setParamValue(sysParamsConfig.getParamValue());
					sysParamsConfigOld.setUpdateBy(operator.getOprId());
					sysParamsConfigOld.setUpdateDate(new Date());
					updateList.add(sysParamsConfigOld);
				}
			}
		}
		return t70100BO.process(updateList,deleteList);
	}

	private String delete() {
		List <SysParamsConfig> deleteList = t70100BO.getDataList(brhId, merchantId, null);
		if(deleteList.size()!=0){
			return t70100BO.deleteList(deleteList);
		}
		return "该商户不存在参数配置信息，请刷新！";
	}
	

	private String dataList;
	private String brhId;
	private String merchantId;

	/**
	 * @return the dataList
	 */
	public String getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(String dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}
