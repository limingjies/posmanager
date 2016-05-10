/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-8-3       first release
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
package com.allinfinance.struts.settle.action;


import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.SFTPUtil;
import com.allinfinance.system.util.SysParamUtil;

//新添架包
import java.io.FileOutputStream;


/**
 * Title:
 * 
 * File: T80104Acion.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-3
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80105Action extends BaseSupport {

	private static final long serialVersionUID = 1L;

	public String download()  {

		String host = SysParamUtil.getParam(SysParamConstants.SFTP_IP);
		int port = 22;
		String username = SysParamUtil.getParam(SysParamConstants.SFTP_Name);
		String password = SysParamUtil.getParam(SysParamConstants.SFTP_Pwd);
		
		String directory = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_REPORT);
		String downloadFile = "report" + date + ".tar.gz";
		String saveFile = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + downloadFile;

		
		
		try {
			FileOutputStream outputStream = new FileOutputStream(saveFile);
			SFTPUtil su = new SFTPUtil(username,password,host,port);
			su.connect();
			su.download(directory, downloadFile,outputStream);
			su.disConnect();

		} catch (Exception e) {
			e.printStackTrace();
			return returnService("远程获取文件失败!", e);
		}
		return returnService(Constants.SUCCESS_CODE_CUSTOMIZE
				+ saveFile);
		
	}



	private String date;


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
