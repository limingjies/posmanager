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
package com.allinfinance.bo.mchnt;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;



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
public interface T20901BO {
	
	/**
	 * 保存商户临时信息
	 * @param tblMchtBaseInfTmp
	 * @param tblMchtSettleInfTmp
	 * @return
	 */
	public String saveTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp, TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp);
	
	/**
	 * 更新商户临时信息
	 * @param tblMchtBaseInfTmp
	 * @param tblMchtSettleInfTmp
	 * @return
	 */
	public String updateTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp, TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp);
	
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
	
	
	
	public String approveRecord(String mchntId, String approveInfo, String mchtStatus, String bankNo, String oprId)  throws IllegalAccessException, InvocationTargetException;
	
	
	
	
	/**
	 * 读取商户临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtBaseInfTmpTmp getBaseInfTmp(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	

	/**
	 * 更新商户临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String updateBaseInfTmpTmp(TblMchtBaseInfTmpTmp inf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 读取商户临时表清算信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtSettleInfTmpTmp getSettleInfTmp(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	
	/**
	 * 核对商户信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String checkMchtInfo(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp);
	public String checkMchtTmpInfo(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp);
	
	
	/**
	 * upload上传印象证书
	 * @param category 
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String upload(List<File> imgFile,List<String> imgFileFileName,String imagesId,String mcht, String category);

	public void save(TblMchtBaseInfTmp baseInfTmp);

	public String saveTmpPosf(TblMchtBaseInfTmp baseInfTmp);

	public void delete(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmpUpd,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmpUpd);

	public void update(String mchntId, String string);

	public void update(TblMchtBaseInfTmp baseInfTmp,
			TblMchtBaseInfTmpTmp baseInfTmpTmp);
	
	
}
