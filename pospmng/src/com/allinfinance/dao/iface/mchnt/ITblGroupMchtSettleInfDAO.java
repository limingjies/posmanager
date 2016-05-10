/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-21       first release
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
package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public interface ITblGroupMchtSettleInfDAO {
	
	public Class<TblGroupMchtSettleInf> getReferenceClass ();


	public TblGroupMchtSettleInf cast (Object object);


	public TblGroupMchtSettleInf load(java.lang.String key)
		throws org.hibernate.HibernateException;

	public TblGroupMchtSettleInf get(java.lang.String key)
		throws org.hibernate.HibernateException;

	public java.lang.String save(TblGroupMchtSettleInf tblGroupMchtSettleInf)
		throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblGroupMchtSettleInf tblGroupMchtSettleInf)
		throws org.hibernate.HibernateException;


	public void update(TblGroupMchtSettleInf tblGroupMchtSettleInf)
		throws org.hibernate.HibernateException;

	public void delete(TblGroupMchtSettleInf tblGroupMchtSettleInf)
		throws org.hibernate.HibernateException;

	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException;

}
