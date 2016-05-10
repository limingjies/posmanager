/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-6-24       first release
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
package com.allinfinance.interceptor;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblTxnInfoConstants;
import com.allinfinance.dao.iface.TblTxnInfoDAO;
import com.allinfinance.po.TblTxnInfo;
import com.allinfinance.po.TblTxnInfoPK;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.TxnInfoUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Title: 
 * 
 * File: LogInter.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-24
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class LogInter extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LogInter.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		//本次交易的随机数
		String random = String.valueOf(new Random().nextInt(999999));
		
		logger.info("***[" + random + "]*****Transaction Begin****************");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String txnId = request.getParameter("txnId");
		String subTxnId = request.getParameter("subTxnId");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String host = request.getRemoteHost();
		
		String result = invocation.invoke();
		
		if(!StringUtil.isNull(txnId) && !StringUtil.isNull(subTxnId)) {
			TblTxnInfoDAO txnInfoDAO = (TblTxnInfoDAO) ContextUtil.getBean("TxnInfoDAO");
			TblTxnInfo tblTxnInfo = new TblTxnInfo();
			TblTxnInfoPK tblTxnInfoPK = new TblTxnInfoPK();				
			String acctTxnDate = CommonFunction.getCurrentDate();				
			tblTxnInfoPK.setAcctDate(acctTxnDate);
			tblTxnInfoPK.setTxnSeqNo(GenerateNextId.getTxnSeq());				
			tblTxnInfo.setId(tblTxnInfoPK);
			
			String currentTime = CommonFunction.getCurrentDateTime();				
			tblTxnInfo.setTxnDate(currentTime.substring(0, 8));
			tblTxnInfo.setTxnTime(currentTime.substring(8, 14));				
			tblTxnInfo.setTxnCode(txnId);
			tblTxnInfo.setSubTxnCode(subTxnId);
			tblTxnInfo.setOprId(operator.getOprId());
			tblTxnInfo.setOrgCode(operator.getOprBrhId());
			tblTxnInfo.setIpAddr(host);
			tblTxnInfo.setTxnName(TxnInfoUtil.getTxnInfo(txnId + "." + subTxnId));
			if ("success".equals(result)) {
				tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_SUCCESS);
			} else {
				tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_FAILURE);
				tblTxnInfo.setErrMsg("-");
				if (null != request.getAttribute("exception")) {
					try {
						Exception ex = (Exception)request.getAttribute("exception");
						tblTxnInfo.setErrMsg(ex.getMessage().length() > 512 ? ex.getMessage().substring(0, 512) : ex.getMessage());
					} catch (Exception e) {e.printStackTrace();}
				}
			}
			
			txnInfoDAO.save(tblTxnInfo);
			logger.info("***[" + random + "]*****Transaction End******************");
		}
		
		return "success";
	}


}

