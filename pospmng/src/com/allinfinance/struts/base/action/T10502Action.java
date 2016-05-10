/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-30       first release
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
package com.allinfinance.struts.base.action;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

import com.allinfinance.common.Constants;
import com.allinfinance.exception.AppException;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.struts.pos.action.T30201Action;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;



/**
 * Title:机构密钥同步
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-30
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10502Action extends BaseAction {
	
	private String termId;
	private String brhId;
	private String type;
	private String destId;
	private String mchtNo;

	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		try {
			if("sSend".equals(method)) {
				rspCode = sSend();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，设备同步操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 机构密钥同步
	 * @return
	 * @throws Exception 
	 */
	private String sSend() throws Exception {
		System.out.println(termId);
		List dataList = CommonFunction.getCommQueryDAO().findBySQLQuery("select resv2 from tbl_brh_info where brh_id=(select bank_no from tbl_mcht_base_inf where mcht_no=(select mcht_cd from tbl_term_inf_tmp where term_id='"+termId+"'))");
		String ltrId = (String)dataList.get(0);
		if(ltrId == null) {
			ltrId = " ";
		}
		
		String sql = "select term_Branch from tbl_term_inf_tmp where term_id='"+termId+"'";
		List termInfo = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		String areaSql = "select cup_city_code from cst_city_code where mcht_city_code=(select area_no from tbl_mcht_base_inf where mcht_no=(select mcht_cd from tbl_term_inf_tmp where term_id='"+termId+"'))";
		List areaList = CommonFunction.getCommQueryDAO().findBySQLQuery(areaSql);
		String area = (String)areaList.get(0);
		
		String sendMsgInfo = "6005" + CommonFunction.fillString("0", '0', 19, false) + "09999999" + "046" 
							+ type 
							+ CommonFunction.fillString(termId.trim(), ' ', 11, true)
							+ termInfo.get(0).toString().trim().substring(0, 2) 
							+ area.trim() 
							+ CommonFunction.fillString(brhId ,' ',9,true)
							+ CommonFunction.fillString(ltrId, ' ', 6, true)
							+ CommonFunction.fillString(brhId ,' ',8,true)
							+ "1" + destId;
		System.out.println(sendMsgInfo);
		
		// "报文内容长度" 4位 不足左补0
		String sendMsgInfoLen = CommonFunction.fillString(String.valueOf(sendMsgInfo.length()), '0', 4, false);
		
		// "最终发送的报文" = "报文内容长度" + "报文内容"
		String sendMsg = sendMsgInfoLen + sendMsgInfo;
		System.out.println(sendMsgInfo);

		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		try {
			String ip = SysParamUtil.getParam("IP");
			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
			int timeout = Integer.parseInt(SysParamUtil.getParam("TIMEOUT"));
			socket = new Socket(ip,port);
			socket.setSoTimeout(timeout);
			outputStream = socket.getOutputStream();

			outputStream.write(sendMsg.getBytes());
			outputStream.flush();

			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log("密钥同步");
			return "-1";
		} catch (UnknownHostException e) {
			e.printStackTrace();
			log("密钥同步");
			return "-1";
		} catch (IOException e) {
			e.printStackTrace();
			log("密钥同步");
			return "-1";
		} catch (Exception e) {
			e.printStackTrace();
			log("密钥同步");
			return "-1";
		}finally{
			try {
				if(socket != null){
					socket.getOutputStream().close();
					socket.close();
				}
				 
			} catch (Exception e) {
				e.printStackTrace();
				log("密钥同步");
			}
		}
		return Constants.SUCCESS_CODE;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	
}
