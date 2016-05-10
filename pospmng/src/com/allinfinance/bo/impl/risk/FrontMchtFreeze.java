package com.allinfinance.bo.impl.risk;

import com.allinfinance.bo.impl.daytrade.HessianFront;
import com.allinfinance.common.Constants;
import com.allinfinance.common.FrontConstants;
import com.allinfinance.frontend.dto.acctmanager.AcctDtlRtnJsonDto;
import com.allinfinance.frontend.dto.acctmanager.MerchantDrozenDto;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.po.daytrade.WebFrontTxnLogPK;

/**
 * AdjustAcct.java
 * 
 * Project: T+0
 * 
 * Description:
 * =========================================================================
 * 
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容. 序号 时间 作者 修改内容 ========== =============
 * ============= ============================= 1. 2015年8月28日 尹志强 created this
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
public class FrontMchtFreeze extends HessianFront {



	/**
	 * 业务交易码——商户资金冻结
	 */
	public static final String MACHT_CODE_FREEZE = "033005";
	/**
	 * 业务交易码——商户资金冻结
	 */
	public static final String MACHT_CODE_UNFREEZE = "033006";

	/**
	 * 商户账户
	 * 
	 * @param txnCode
	 *            交易码
	 * @param acctDtl
	 *            账户信息对象
	 * @return
	 */
	public Object[] doTxn(String txnCode, MerchantDrozenDto transCon) {

		// 定义返回信息
		Object[] ret;

		// 组装发送信息
		Object[] send = new Object[2];
		send[0] = transCon;
		send[1] = new AcctDtlRtnJsonDto();

		// 获取信息
		Object[] get = super.doTxn(txnCode, send);

		// 分析信息
		if (Constants.SUCCESS_CODE.equals(get[0])) {
			ret = new Object[2];
			// 前置系统返回信息
			AcctDtlRtnJsonDto acctDtlRtn = (AcctDtlRtnJsonDto) get[1];
			if (FrontConstants.SYS_CODE_SUCCESS.equals(acctDtlRtn.getRtnCode())) {
				// 组装流水信息
				WebFrontTxnLogPK id = new WebFrontTxnLogPK();
				id.setWebTime(transCon.getWebTime());
				id.setWebSeqNum(transCon.getWebSeqNum());
				WebFrontTxnLog webFrontTxnLog = new WebFrontTxnLog();
				webFrontTxnLog.setId(id);
				webFrontTxnLog.setTxnCode(txnCode);
				webFrontTxnLog.setTxnLog(acctDtlRtn);
				// 组装返回前置系统成功信息
				ret[0] = Constants.SUCCESS_CODE;
				ret[1] = webFrontTxnLog;
			} else {
				// 组装返回前置系统失败信息
				ret[0] = Constants.FAILURE_CODE;
				ret[1] = acctDtlRtn.getRtnMsg();
			}
		} else {
			// 返回异常
			ret = get;
		}
		// 返回信息
		return ret;
	}

}
