/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   gaohao      2012-7-2       first release
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


import java.util.List;

import com.allinfinance.bo.impl.mchnt.TblMchntNetWorkService;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;
/**
 * Title:渠道商户入网审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2014-3-10
 * 
 * @author XPF
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20904Action extends BaseAction {
	
	
	private TblMchntNetWorkService service = (TblMchntNetWorkService) ContextUtil.getBean("tblMchntNetWorkService");
	
	@Override
	protected String subExecute() throws Exception {
		
		//在新增、修改、冻结、恢复和注销时，CRT_OPR_ID均保存该交易的申请人（发起柜员），UPD_OPR_ID保存该交易的审核人
		String sql = "SELECT CRT_OPR_ID FROM Tbl_Mcht_Base_Inf_Tmp_Tmp WHERE MCHT_NO = '" + mchntId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}

		log("审核渠道入网商户编号：" + mchntId);
		
		if("accept".equals(method)) {
			rspCode = accept();
		} else if("refuse".equals(method)) {
			rspCode = refuse();
		} else if("back".equals(method)) {
			rspCode = back();
		}
		
		return rspCode;
	}
		
	/**
	 * 审核通过
	 * @return
	 * @throws Exception 
	 */
	private String accept() throws Exception {
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核拒绝
	 * @return
	 * @throws Exception 
	 */
	private String refuse() throws Exception {
		return service.refuse(mchntId, refuseInfo);
	}

	/**
	 * 审核退回
	 * @return
	 * @throws Exception  
	 */
	private String back() throws Exception {
		return service.back(mchntId, refuseInfo);
	}
	
	// 商户编号
	private String mchntId;
	// 商户拒绝的原因
	private String refuseInfo;

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
}