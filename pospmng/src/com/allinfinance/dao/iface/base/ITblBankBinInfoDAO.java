/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-17       first release
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
package com.allinfinance.dao.iface.base;

import com.allinfinance.po.TblBankBinInf;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public interface ITblBankBinInfoDAO {
	
	public abstract Class getReferenceClass();

	public abstract TblBankBinInf get(Integer key);

	public abstract TblBankBinInf load(Integer key);

    public abstract Integer save(TblBankBinInf TblBankBinInf);

    public abstract void saveOrUpdate(TblBankBinInf TblBankBinInf);

    public abstract void update(TblBankBinInf TblBankBinInf);

    public abstract void delete(Integer id);

    public abstract void delete(TblBankBinInf TblBankBinInf);

    public abstract void refresh(TblBankBinInf TblBankBinInf);

}
