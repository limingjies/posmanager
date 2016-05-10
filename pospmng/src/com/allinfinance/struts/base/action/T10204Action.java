package com.allinfinance.struts.base.action;


import org.apache.log4j.Logger;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T10204Action  extends ReportBaseAction  {
	
	
	private static Logger log = Logger.getLogger(T10204Action.class);
	
	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void reportAction() throws Exception {
		// TODO Auto-generated method stub
		String basePath=getRequest().getSession().getServletContext().getRealPath("/") ;
		String path=basePath+ SysParamUtil.getParam(SysParamConstants.TEMPLET_PATH);
		String fileName=path+SysParamConstants.CARD_BIN_TEMPLET_NAME;
		String downName=SysParamConstants.CARD_BIN_TEMPLET;
		log.info(fileName+"下载");
		callDownload(fileName,downName);
	}

}
