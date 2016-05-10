package com.allinfinance.bo.impl.daytrade;

import java.util.List;

import com.allinfinance.bo.daytrade.T70100BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.daytrade.SysParamsConfigDAO;
import com.allinfinance.po.daytrade.SysParamsConfig;
import com.allinfinance.po.daytrade.SysParamsConfigPK;

/**
 *  T70100BOTarget.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年6月3日	  徐鹏飞          created this class.
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
public class T70100BOTarget {
/*
 * 

	private ICommQueryDAO commQuery_frontDAO;
	private SysParamsConfigDAO sysParamsConfigDAO;

	@Override
	public SysParamsConfig get(SysParamsConfigPK id) {
		return sysParamsConfigDAO.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysParamsConfig> getDataList(String brhId, String merchantId,
			String paramCode) {
		StringBuffer whereHql = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(brhId)) {
			whereHql.append(" AND t.id.brhId = '" + brhId + "' ");
		}
		if (isNotEmpty(merchantId)) {
			whereHql.append(" AND t.id.merchantId = '" + merchantId + "' ");
		}
		if (isNotEmpty(paramCode)) {
			whereHql.append(" AND t.id.paramCode = '" + paramCode + "' ");
		}
		
		String hql=" from com.allinfinance.po.daytrade.SysParamsConfig t "
				+ whereHql.toString();
		List<SysParamsConfig> dataList = commQuery_frontDAO.findByHQLQuery(hql);
		return dataList;
	}

	@Override
	public String addList(List<SysParamsConfig> sysParamsConfigList) {
		for(SysParamsConfig sysParamsConfig : sysParamsConfigList){
			sysParamsConfigDAO.save(sysParamsConfig);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String updateList(List<SysParamsConfig> sysParamsConfigList) {
		for(SysParamsConfig sysParamsConfig : sysParamsConfigList){
			sysParamsConfigDAO.saveOrUpdate(sysParamsConfig);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String deleteList(List<SysParamsConfig> sysParamsConfigList) {
		for(SysParamsConfig sysParamsConfig : sysParamsConfigList){
			sysParamsConfigDAO.delete(sysParamsConfig);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String add(SysParamsConfig sysParamsConfig) {
		sysParamsConfigDAO.save(sysParamsConfig);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(SysParamsConfig sysParamsConfig) {
		sysParamsConfigDAO.update(sysParamsConfig);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(SysParamsConfigPK id) {
		sysParamsConfigDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.daytrade.T70100BO#process(java.util.List, java.util.List)
	 
	@Override
	public String process(List<SysParamsConfig> updateList,
			List<SysParamsConfig> deleteList) {
		updateList(updateList);
		deleteList(deleteList);
		return Constants.SUCCESS_CODE;
	}

	*//**
	 * @return the commQuery_frontDAO
	 *//*
	public ICommQueryDAO getCommQuery_frontDAO() {
		return commQuery_frontDAO;
	}

	*//**
	 * @param commQuery_frontDAO the commQuery_frontDAO to set
	 *//*
	public void setCommQuery_frontDAO(ICommQueryDAO commQuery_frontDAO) {
		this.commQuery_frontDAO = commQuery_frontDAO;
	}

	*//**
	 * @return the sysParamsConfigDAO
	 *//*
	public SysParamsConfigDAO getSysParamsConfigDAO() {
		return sysParamsConfigDAO;
	}

	*//**
	 * @param sysParamsConfigDAO the sysParamsConfigDAO to set
	 *//*
	public void setSysParamsConfigDAO(SysParamsConfigDAO sysParamsConfigDAO) {
		this.sysParamsConfigDAO = sysParamsConfigDAO;
	}
*/
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str.trim()))
			return true;
		else
			return false;
	}

}
