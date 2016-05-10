/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-4       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
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
package com.allinfinance.struts.mchnt.action;

import java.util.HashMap;
import java.util.Map;

import com.allinfinance.bo.mchnt.TblMchtNetTmpBO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.mchnt.TblMchtNetTmp;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:查看商户详细信息
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-4
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MchntNetDetailAction extends BaseAction {
	
	private TblMchtNetTmpBO tblMchtNetTmpBO = (TblMchtNetTmpBO) ContextUtil.getBean("TblMchtNetTmpBO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		TblMchtNetTmp tblMchtNetTmp = tblMchtNetTmpBO.get(mchntId);
		
		Map<String, String> mchntInfoMap = new HashMap<String, String>();
		
		BeanUtils.iterateBeanProperties(mchntInfoMap, mchntInfoMap);
		
		setSessionAttribute("mchntInfo", mchntInfoMap);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String mchntId;

	/**
	 * @return the mchntId
	 */
	public String getMchntId() {
		return mchntId;
	}

	/**
	 * @param mchntId the mchntId to set
	 */
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
}
