package com.allinfinance.struts.base.action;


import com.allinfinance.bo.base.T10215BO;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T10215Action extends ReportBaseAction {
	
	
	private String settleDate;
	private String aimChnlId;
	
	private T10215BO t10215BO = (T10215BO) ContextUtil.getBean("T10215BO");

	
	@Override
	protected void reportAction() throws Exception {
		// TODO Auto-generated method stub
		fileName= t10215BO.download(settleDate.replace("-", ""),aimChnlId);
		
		if(fileName.indexOf(".zip")==-1){
			writeNoDataMsg(fileName);
		}else{
			callDownload(fileName,SysParamConstants.PAY_SETTLE_DTL_NAME);
		}
		
	}




	public String getSettleDate() {
		return settleDate;
	}




	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	



	public String getAimChnlId() {
		return aimChnlId;
	}




	public void setAimChnlId(String aimChnlId) {
		this.aimChnlId = aimChnlId;
	}




	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
	}




	
	
	


}
