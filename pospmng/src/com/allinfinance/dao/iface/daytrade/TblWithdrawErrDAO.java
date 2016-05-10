package com.allinfinance.dao.iface.daytrade;

import com.allinfinance.po.daytrade.TblWithdrawErr;


/**
 *  MbWithdrawFeeDAO.java
 *     
 *  Project: T+0  
 *
 *  Description: T+0提现费率DAO接口类
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
public interface TblWithdrawErrDAO {
	
	public TblWithdrawErr get(Long key) throws org.hibernate.HibernateException;

	public TblWithdrawErr load(Long key) throws org.hibernate.HibernateException;

	public void save(TblWithdrawErr tblWithdrawErr) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblWithdrawErr tblWithdrawErr) throws org.hibernate.HibernateException;

	public void update(TblWithdrawErr tblWithdrawErr) throws org.hibernate.HibernateException;

	public void delete(Long key) throws org.hibernate.HibernateException;

	public void delete(TblWithdrawErr tblWithdrawErr) throws org.hibernate.HibernateException;

}