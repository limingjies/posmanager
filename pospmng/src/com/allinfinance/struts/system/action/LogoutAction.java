/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-31       first release
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
package com.allinfinance.struts.system.action;




import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.TblTxnInfoConstants;
import com.allinfinance.dao.iface.TblTxnInfoDAO;
import com.allinfinance.po.TblTxnInfo;
import com.allinfinance.po.TblTxnInfoPK;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.TxnInfoUtil;

/**
 * Title: 操作员退出
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-31
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class LogoutAction extends BaseAction {

	@Override
	protected String subExecute() throws Exception {
		if (operator != null){
			log("USER:[" + operator.getOprId() + "] LOGOUT.");
			saveLogInfo(operator);
		}
		
		// 删除操作员信息
		removeSessionAttribute(Constants.OPERATOR_INFO);
		// 删除菜单树信息
		removeSessionAttribute(Constants.TREE_MENU_MAP);
		// 删除工具栏信息
		removeSessionAttribute(Constants.TOOL_BAR_STR);
		/*Cookie[] cookies = getRequest().getCookies(); 
		try 
		{ 
		     for(int i=0;i<cookies.length;i++)   
		     { 
		      Cookie cookie = new Cookie("spdb",null); 
		      cookie.setMaxAge(0); 
		      //cookie.setPath("/");//根据你创建cookie的路径进行填写     
		      ServletActionContext.getResponse().addCookie(cookie);
		     } 
		}catch(Exception ex) 
		{ 
			log(ex.toString());
		}*/
//		return null;
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 记录登陆日志
	 * @param menuMap
	 * @return
	 */
	private void saveLogInfo(Operator operator){
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
		tblTxnInfo.setTxnCode("10000");
		tblTxnInfo.setSubTxnCode("02");
		tblTxnInfo.setOprId(operator.getOprId());
		tblTxnInfo.setOrgCode(operator.getOprBrhId());
		tblTxnInfo.setIpAddr(getRequest().getRemoteHost());
		tblTxnInfo.setTxnName(TxnInfoUtil.getTxnInfo(tblTxnInfo.getTxnCode() + "." + tblTxnInfo.getSubTxnCode()));
		tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_SUCCESS);
		tblTxnInfo.setErrMsg("-");
		txnInfoDAO.save(tblTxnInfo);
	}

}
