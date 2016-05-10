package com.allinfinance.dao.impl.daytrade;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.daytrade.SysParamsConfig;
import com.allinfinance.po.daytrade.SysParamsConfigPK;

/**
 *  SysParamsConfigDAO.java
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
//
//public class SysParamsConfigDAO extends _RootDAO<SysParamsConfig> implements com.allinfinance.dao.iface.daytrade.SysParamsConfigDAO {
//
//	@Override
//	public SysParamsConfig get(SysParamsConfigPK key) throws HibernateException {
//		return (SysParamsConfig) get(getReferenceClass(), key);
//	}
//
//	@Override
//	public SysParamsConfig load(SysParamsConfigPK key)
//			throws HibernateException {
//		return (SysParamsConfig) load(getReferenceClass(), key);
//	}
//
//	@Override
//	public void save(SysParamsConfig sysParamsConfig) throws HibernateException {
//		 super.save(sysParamsConfig);
//	}
//
//	@Override
//	protected Class<SysParamsConfig> getReferenceClass() {
//		return SysParamsConfig.class;
//	}
//
//	@Override
//	public void saveOrUpdate(SysParamsConfig sysParamsConfig)
//			throws HibernateException {
//		super.saveOrUpdate(sysParamsConfig);
//		
//	}
//
//	@Override
//	public void update(SysParamsConfig sysParamsConfig)
//			throws HibernateException {
//		super.saveOrUpdate(sysParamsConfig);
//	}
//
//	@Override
//	public void delete(SysParamsConfigPK key) throws HibernateException {
//		super.delete(load(key));
//	}
//
//	@Override
//	public void delete(SysParamsConfig sysParamsConfig)
//			throws HibernateException {
//		super.delete(sysParamsConfig);
//	}
//
//
//
//}
