package com.allinfinance.struts.daytrade.action;

import java.util.Date;

import com.allinfinance.bo.daytrade.T70101BO;
import com.allinfinance.po.daytrade.MbWithdrawFee;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;

/**
 * T70404Action.java
 * 
 * Project: T+0
 * 
 * Description:
 * =========================================================================
 * 
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容. 序号 时间 作者 修改内容 ========== =============
 * ============= ============================= 1. 2015年6月23日 尹志强 created this
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
public class T70404Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected String subExecute() throws Exception {

		if ("add".equals(getMethod())) {
			rspCode = add();
		}
		return rspCode;
	}

	private String add() {

		return SUCCESS;

	}

}
