package com.allinfinance.struts.pos.action;

import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;

public class T3010103Action extends BaseAction {

	private static final long serialVersionUID = 8817290734735660318L;
	private T3010BO t3010BO;
	private String termId;
	private String recCrtTs;
	

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

	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	@Override
	protected String subExecute() throws Exception {
		TblTermInfTmp tblTermInf = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
		TblTermInfTmpPK pk = new TblTermInfTmpPK();
		String id = t3010BO.getTermId(tblTermInf.getTermBranch());
		pk.setRecCrtTs(CommonFunction.getCurrentDate());
		pk.setTermId(id);
		tblTermInf.setId(pk);
		tblTermInf.setKeyDownSign(TblTermInfConstants.DEFUALT_CHECKBOX);
		tblTermInf.setParam1DownSign(TblTermInfConstants.DEFUALT_CHECKBOX);
		tblTermInf.setParamDownSign(TblTermInfConstants.DEFUALT_CHECKBOX);
		tblTermInf.setSupportIc(TblTermInfConstants.DEFUALT_CHECKBOX);
		tblTermInf.setTermSta(TblTermInfConstants.TERM_STA_COPY);
		tblTermInf.setTermFactory(null);
		tblTermInf.setTermIdId(Constants.DEFAULT);
		tblTermInf.setTermMachTp(null);
		String termId = t3010BO.add(tblTermInf);
		if(termId != null)
		{
			return Constants.SUCCESS_CODE_CUSTOMIZE+"新增终端号："+termId;
		}
		return Constants.FAILURE_CODE;
	}

}
