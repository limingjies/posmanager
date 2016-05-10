package com.allinfinance.struts.pos.action;
import com.allinfinance.bo.impl.mchnt.TblMchntService;
import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;

/**
 * Title:终端恢复管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-16
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class T30103Action extends BaseAction {

	private static final long serialVersionUID = 3574943988465857190L;
	private T3010BO t3010BO;
	private TblMchntService tblMchntService;
	private String termId;
	private String recCrtTs;
	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	public void setTblMchntService(TblMchntService tblMchntService) {
		this.tblMchntService = tblMchntService;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	@Override
	protected String subExecute() throws Exception {
		TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
		tblTermInfTmp.setRecUpdOpr(operator.getOprId());
		//由8字节改为14字节数据的格式有日期格式YYYYMMDD改为 YYYYMMDDHHMMSS
		//tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDate());
		tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		if(!tblMchntService.getMchntStatus(tblTermInfTmp.getMchtCd()))
			return TblTermInfConstants.T30103_01;
		if(tblTermInfTmp != null && TblTermInfConstants.TERM_STA_STOP.equals(tblTermInfTmp.getTermSta()))
		{
			t3010BO.updateForRisk(tblTermInfTmp, TblTermInfConstants.TERM_STA_RUN);
			return Constants.SUCCESS_CODE;
		}
		return Constants.FAILURE_CODE;
	}
	

}
