package com.allinfinance.struts.base.action;

import org.apache.log4j.Logger;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T1060401Action  extends ReportBaseAction  {
	
	
	private static Logger log = Logger.getLogger(T1060401Action.class);
	
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
		String fileName=path+SysParamConstants.ROUTE_TEMPLET_NAME;
		String downName=SysParamConstants.ROUTE_TEMPLET;
		log.info(fileName+"下载");
		callDownload(fileName,downName);
	}

	
}
