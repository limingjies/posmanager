/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-6-8       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2008 allinfinance, Inc. All rights reserved.
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
package com.allinfinance.dao.impl;

import java.util.List;

import com.allinfinance.dao.iface.TblTxnInfoDAO;
import com.allinfinance.po.TblTxnInfo;
import com.allinfinance.po.TblTxnInfoPK;

/**
 * Title:交易流水
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-8
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TblTxnInfoDAOTarget implements TblTxnInfoDAO {
	private com.allinfinance.dao.TblTxnInfoDAO txnInfoDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblTxnInfoDAO#delete(com.allinfinance.po.TblTxnInfoPK)
	 */
	public void delete(TblTxnInfoPK id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblTxnInfoDAO#delete(com.allinfinance.po.TblTxnInfo)
	 */
	public void delete(TblTxnInfo tblTxnInfo) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblTxnInfoDAO#findAll()
	 */
	public List<TblTxnInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblTxnInfoDAO#get(com.allinfinance.po.TblTxnInfoPK)
	 */
	public TblTxnInfo get(TblTxnInfoPK key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblTxnInfoDAO#load(com.allinfinance.po.TblTxnInfoPK)
	 */
	public TblTxnInfo load(TblTxnInfoPK key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblTxnInfoDAO#save(com.allinfinance.po.TblTxnInfo)
	 */
	public TblTxnInfoPK save(TblTxnInfo tblTxnInfo) {
		return this.txnInfoDAO.save(tblTxnInfo);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblTxnInfoDAO#saveOrUpdate(com.allinfinance.po.TblTxnInfo)
	 */
	public void saveOrUpdate(TblTxnInfo tblTxnInfo) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblTxnInfoDAO#update(com.allinfinance.po.TblTxnInfo)
	 */
	public void update(TblTxnInfo tblTxnInfo) {
		// TODO Auto-generated method stub

	}

	public com.allinfinance.dao.TblTxnInfoDAO getTxnInfoDAO() {
		return txnInfoDAO;
	}

	public void setTxnInfoDAO(com.allinfinance.dao.TblTxnInfoDAO txnInfoDAO) {
		this.txnInfoDAO = txnInfoDAO;
	}
}
