package com.allinfinance.dao.impl.daytrade;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.daytrade.MbWithdrawFee;
import com.allinfinance.po.daytrade.TblWithdrawErr;

/**
 * TblWithdrawErrDAO.java
 * 
 * Project: T+0
 * 
 * Description: T+0提现费率DAO接口类
 * =========================================================================
 * 
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容. 序号 时间 作者 修改内容 ========== =============
 * ============= ============================= 1. 2015年5月21日 徐鹏飞 created this
 * class.
 * 
 * 
 * Copyright Notice: Copyright (c) 2009-2015 Allinpay Financial Services Co.,
 * Ltd. All rights reserved.
 * 
 * This software is the confidential and proprietary information of
 * allinfinance.com Inc. ('Confidential Information'). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with Allinpay Financial.
 * 
 * Warning:
 * =========================================================================
 * 
 */
public class TblWithdrawErrDAO extends _RootDAO<TblWithdrawErr> implements com.allinfinance.dao.iface.daytrade.TblWithdrawErrDAO {
	public TblWithdrawErrDAO() {
	}

	@Override
	public TblWithdrawErr get(Long key) throws org.hibernate.HibernateException {
		return (TblWithdrawErr) get(getReferenceClass(), key);
	}

	@Override
	public TblWithdrawErr load(Long key) throws org.hibernate.HibernateException {
		return (TblWithdrawErr) load(getReferenceClass(), key);
	}

	@Override
	public void save(TblWithdrawErr tblWithdrawErr) throws HibernateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdate(TblWithdrawErr tblWithdrawErr) throws org.hibernate.HibernateException {
		 super.save(tblWithdrawErr);
	}

	@Override
	public void update(TblWithdrawErr tblWithdrawErr) throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblWithdrawErr);
		
	}

	@Override
	public void delete(Long key)throws org.hibernate.HibernateException {
		super.delete(load(key));
		
	}

	@Override
	public void delete(TblWithdrawErr tblWithdrawErr) 
		throws org.hibernate.HibernateException {
			super.delete(tblWithdrawErr);
		
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return TblWithdrawErr.class;
	}


}