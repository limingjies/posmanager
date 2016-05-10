/* @(#)
 *
 * Project:spdb
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   赵兴财                          2011-3-1         first release
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

import com.allinfinance.po.mchnt.TblMchtNetTmp;


/**
 * Title:新增临时入网商户新增(添加到临时表)
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-3-1
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author
 * 
 * @version 1.0
 */
public interface TblMchtNetTmpBO {
	
	/**
	 * 新增临时入网商户信息
	 * @param tblMchtNet
	 * @throws Exception
	 * 2011-3-1下午08:07:47
	 */
	public void add(TblMchtNetTmp tblMchtTmpNet) throws Exception ;
	/**
	 * 修改临时入网商户信息
	 * @param tblMchtNet
	 * @throws Exception
	 * 2011-3-1下午08:09:03
	 */
	public void update(TblMchtNetTmp tblMchtTmpNet) throws Exception ;
	/**
	 * 删除临时入网商户信息
	 * @param tblMchtNet
	 * @throws Exception
	 * 2011-3-1下午08:09:38
	 */
	public void delete(TblMchtNetTmp tblMchtTmpNet) throws Exception;
	/**
	 * 根据入网商户编号获取入网商户信息
	 * @param mchtNo
	 * @return
	 * 2011-3-1下午08:10:38
	 */
	public TblMchtNetTmp get(String mchtNo);

}
