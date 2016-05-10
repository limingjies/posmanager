package com.allinfinance.bo.daytrade;

import java.util.List;

import com.allinfinance.po.daytrade.SysParamsConfig;
import com.allinfinance.po.daytrade.SysParamsConfigPK;

/**
 *  T70100BO.java
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
public interface T70100BO {
	/**
	 * 根据参数配置主键获取参数配置信息
	 * @param 参数配置主键
	 * @return 一条参数配置信息
	 */
	public SysParamsConfig get(SysParamsConfigPK id);
	
	/**
	 * 获取参数配置列表信息
	 * @param 商户编号
	 * @return 一条参数配置信息
	 */
	public List<SysParamsConfig> getDataList(String brhId, String merchantId, String paramCode);

	/**
	 * 新增一组参数配置信息
	 * @param 一组参数配置信息
	 * @return 交易信息码
	 */
	public String addList(List<SysParamsConfig> sysParamsConfigList);

	/**
	 * 更新一组参数配置信息
	 * @param 一组参数配置信息
	 * @return 交易信息码
	 */
	public String updateList(List<SysParamsConfig> sysParamsConfigList);

	/**
	 * 删除一组参数配置信息
	 * @param 一组参数配置信息
	 * @return 交易信息码
	 */
	public String deleteList(List<SysParamsConfig> sysParamsConfigList); 

	/**
	 * 新增一条参数配置信息
	 * @param 一条参数配置信息
	 * @return 交易信息码
	 */
	public String add(SysParamsConfig sysParamsConfig);

	/**
	 * 更新一条参数配置信息
	 * @param 一条参数配置信息
	 * @return 交易信息码
	 */
	public String update(SysParamsConfig sysParamsConfig);

	/**
	 * 根据参数配置主键删除参数配置信息
	 * @param 参数配置主键
	 * @return 交易信息码
	 */
	public String delete(SysParamsConfigPK id); 

	/**
	 * * 处理参数配置信息
	 * @param 参数配置主键
	 * @param updateList 更新一组参数配置信息
	 * @param deleteList 删除一组参数配置信息
	 * @return 交易信息码
	 */
	public String process(List<SysParamsConfig> updateList, List<SysParamsConfig> deleteList); 

}
