package com.allinfinance.bo.impl.settleNew;


import com.allinfinance.bo.impl.daytrade.FrontBatStart;
import com.allinfinance.bo.settleNew.T100201BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.daytrade.WebFrontTxnLogDAO;
import com.allinfinance.frontend.dto.acctmanager.AcctDtlRtnJsonDto;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.GenerateNextId;

public class T100201BOTarget implements T100201BO {

	
	
	private  WebFrontTxnLogDAO webFrontTxnLogDAO;
	
	
	@Override
	public String start(String transCode, String transDate) throws Exception {
		
		AcctDtlRtnJsonDto acctDtlRtnJsonDto=new AcctDtlRtnJsonDto();
		acctDtlRtnJsonDto.setStr(transDate);
		acctDtlRtnJsonDto.setSysSeqNum(GenerateNextId.getSeqSysNum());
		acctDtlRtnJsonDto.setInstDate(CommonFunction.getCurrentDateTime());
		
		FrontBatStart frontBatStart=new  FrontBatStart();
		Object[] ret=frontBatStart.doTxn(FrontBatStart.COM_CODE+transCode, acctDtlRtnJsonDto);
		
		
		if (Constants.SUCCESS_CODE.equals(ret[0])) {
			WebFrontTxnLog webFrontTxnLog = (WebFrontTxnLog) ret[1];
			webFrontTxnLogDAO.save(webFrontTxnLog);
			// 返回成功信息
			return Constants.SUCCESS_CODE;
		} else {
			return (String) ret[1];
		}
	}

	
	
	public WebFrontTxnLogDAO getWebFrontTxnLogDAO() {
		return webFrontTxnLogDAO;
	}


	public void setWebFrontTxnLogDAO(WebFrontTxnLogDAO webFrontTxnLogDAO) {
		this.webFrontTxnLogDAO = webFrontTxnLogDAO;
	}
	
	
	
	
	
}