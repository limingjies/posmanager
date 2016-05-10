/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-8-12       first release
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
package com.allinfinance.struts.pos.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.struts.pos.TblTermTmkLogConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-12
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T3020303Action extends ReportBaseAction {

	private static final long serialVersionUID = 1777500196702175186L;

	private String batchNo;
	private ICommQueryDAO commQueryDAO;
	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	@Override
	protected void reportAction() throws Exception {
		StringBuffer sql = new StringBuffer("select t1.MCHN_NO,t1.TERM_ID_ID ").append("from TBL_TERM_TMK_LOG t1 ").append("where t1.STATE = '1'");
		if(!StringUtil.isEmpty(batchNo))
			sql.append(" and t1.BATCH_NO = '").append(batchNo).append("'");
		List list = commQueryDAO.findBySQLQuery(sql.toString());
		if(list.isEmpty()||list==null)
			writeNoDataMsg(TblTermTmkLogConstants.T30203_03);
		String fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK)+
			"SPDBKMS"+operator.getOprBrhId().trim().substring(0,2)+CommonFunction.getCurrentDate()+".txt";
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(new FileOutputStream(file)); 
		
		StringBuffer str = new StringBuffer();
		Iterator it = list.iterator();
		while(it.hasNext())
		{
			Object[] obj = (Object[])it.next();
			str.append(obj[0]).append(",").append(obj[1]).append("\r\n");
		}
		pw.write(str.toString().toCharArray()); 
		pw.flush();
		pw.close();
		writeUsefullMsg(fileName);
		//zjx
		//存储打印信息
		String oprId=operator.getOprId();
		List list2 = commQueryDAO.findBySQLQuery("select prt_count from TBL_TERM_TMK_LOG t where t.batch_no='"+batchNo+"'");
		if(list2==null||list2.isEmpty())
			writeNoDataMsg(TblTermTmkLogConstants.T30203_03);
		int prtCount = Integer.parseInt(list2.get(0)==null?"0":list2.get(0).toString());
			
		StringBuffer sql2 = new StringBuffer("update tbl_term_tmk_log set prt_opr='").append(oprId)
		.append("',rec_upd_opr = '").append(oprId).append("',rec_upd_ts = '").append(CommonFunction.getCurrentDateTime())
		.append("',prt_date = '").append(CommonFunction.getCurrentDate())
		.append("',prt_count = ").append(++prtCount)
		.append(" where batch_no='").append(batchNo).append("'");
		commQueryDAO.excute(sql2.toString());
		return;
		
	}

	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
