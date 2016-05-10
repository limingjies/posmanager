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
package com.allinfinance.struts.settleNew.action;


import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.SFTPUtil;
import com.allinfinance.system.util.SysParamUtil;



import java.io.File;
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
public class T100301Action extends BaseSupport {

	private static final long serialVersionUID = 1L;

	public String download()  {

		String host = SysParamUtil.getParam(SysParamConstants.SFTP_IP);
		int port = SysParamConstants.SFTP_PORT;
		String username = SysParamUtil.getParam(SysParamConstants.SFTP_NAME_NEW);
		String password = SysParamUtil.getParam(SysParamConstants.SFTP_PWD_NEW);
		
		String directory = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_REPORT_NEW);
		String downloadFile = "report" + date + ".tar.gz";
		String downloadFileNew = "report_" + date + "_new.tar.gz";
		
		String saveFilePath = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) ;
		String saveFile=saveFilePath+downloadFileNew;

		if(!new File(saveFilePath).exists()){
			new File(saveFilePath).getAbsoluteFile().mkdirs();
		}
		
		SFTPUtil su = new SFTPUtil(username,password,host,port);
		try {
			FileOutputStream outputStream = new FileOutputStream(saveFile);
			su.connect();
			su.download(directory, downloadFile,outputStream);

		} catch (Exception e) {
			e.printStackTrace();
//			return returnService("远程获取文件失败!", e);
			return returnService(e.getMessage());
		}finally{
			try {
				su.disConnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
