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

import com.allinfinance.po.mchnt.TblGroupMchtInf;
import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;



/**
 * Title:
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
public interface TblMchntService1 {
	
	/**
	 * 保存商户临时信息
	 * @param tblMchtBaseInfTmp
	 * @param tblMchtSettleInfTmp
	 * @return
	 */
	public String saveTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp, TblMchtSettleInfTmp tblMchtSettleInfTmp);
	
	/**
	 * 更新商户临时信息
	 * @param tblMchtBaseInfTmp
	 * @param tblMchtSettleInfTmp
	 * @return
	 */
	public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp, TblMchtSettleInfTmp tblMchtSettleInfTmp);
	
	/**
	 * 商户信息审核（通过）
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String accept(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 商户信息审核（退回）
	 * @param mchntId
	 * @param refuseInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String back(String mchntId, String refuseInfo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 商户信息审核（拒绝）
	 * @param mchntId
	 * @param refuseInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String refuse(String mchntId, String refuseInfo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 保存集团商户信息
	 * @param inf
	 * @param acctinf
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String saveGroup(TblGroupMchtInf inf, TblGroupMchtSettleInf acctinf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 更新集团商户信息
	 * @param inf
	 * @param acctinf
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String updateGroup(TblGroupMchtInf inf, TblGroupMchtSettleInf acctinf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 获取商户信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * 2011-6-24下午02:34:05
	 */
	public TblMchtBaseInf getMccByMchntId(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblGroupMchtInf getGroupInf(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblGroupMchtSettleInf getGroupSettleInf(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 读取商户临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtBaseInfTmp getBaseInfTmp(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	public TblMchtBaseInf getBaseInf(String mchntId) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 更新商户临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String updateBaseInfTmp(TblMchtBaseInfTmp inf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 读取商户临时表清算信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtSettleInfTmp getSettleInfTmp(String mchntId) throws IllegalAccessException, InvocationTargetException;
	public TblMchtSettleInf getSettleInf(String mchntId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 提供终端获取商户状态
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public boolean getMchntStatus(String mchntId) throws  IllegalAccessException, InvocationTargetException;
}
