package com.allinfinance.bo.daytrade;

import java.util.List;

import com.allinfinance.po.daytrade.MbWithdrawFee;

/**
 *  T70101BO.java
 *     
 *  Project: T+0  
 *
 *  Description: T+0提现费率业务处理接口类
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年5月21日	  徐鹏飞          created this class.
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
public interface T70101BO {
	/**
	 * 根据计费编号获取计费信息
	 * @param 计费编号
	 * @return 一条计费信息
	 */
	public MbWithdrawFee get(Long id);
	
	/**
	 * 根据商户编号获取计费信息
	 * @param 商户编号
	 * @return 一条计费信息
	 */
	public List<MbWithdrawFee> getDataList(String merchantId);

	/**
	 * 新增一条计费信息
	 * @param 一条计费信息
	 * @return 交易信息码
	 */
	public String add(MbWithdrawFee mbWithdrawFee);

	/**
	 * 更新一条计费信息
	 * @param 一条计费信息
	 * @return 交易信息码
	 */
	public String update(MbWithdrawFee mbWithdrawFee);

	/**
	 * 根据计费编号删除计费信息
	 * @param 计费编号
	 * @return 交易信息码
	 */
	public String delete(Long id);
	
}