package com.allinfinance.dao.impl.daytrade;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.po.daytrade.WebFrontTxnLogPK;

/**
 *  WebFrontTxnLogDAO.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年5月28日	  徐鹏飞          created this class.
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
public class WebFrontTxnLogDAO extends _RootDAO<WebFrontTxnLog> implements com.allinfinance.dao.iface.daytrade.WebFrontTxnLogDAO {

	@Override
	public WebFrontTxnLog get(WebFrontTxnLogPK key) throws HibernateException {
		// TODO Auto-generated method stub
		return (WebFrontTxnLog) get(getReferenceClass(), key);
	}

	@Override
	public WebFrontTxnLog load(WebFrontTxnLogPK key) throws HibernateException {
		// TODO Auto-generated method stub
		return (WebFrontTxnLog) get(getReferenceClass(), key);
	}

	@Override
	public void save(WebFrontTxnLog webFrontTxnLog) throws HibernateException {
		// TODO Auto-generated method stub
		super.save(webFrontTxnLog);
	}

	@Override
	protected Class<WebFrontTxnLog> getReferenceClass() {
		// TODO Auto-generated method stub
		return WebFrontTxnLog.class;
	}

}
