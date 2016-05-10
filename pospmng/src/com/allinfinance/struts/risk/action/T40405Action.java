package com.allinfinance.struts.risk.action;

import com.allinfinance.bo.risk.TblRiskParamMngBo;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

/**
 * 商户风控参数维护
 * @author Jee Khan
 *
 */
@SuppressWarnings("serial")
public class T40405Action extends BaseAction{

	private TblRiskParamMngBo tblRiskParamMngBo;
	private TblRiskParamMng tblRiskParamMng;
		
	@Override
	protected String subExecute() throws Exception {
		tblRiskParamMngBo = (TblRiskParamMngBo) ContextUtil.getBean("tblRiskParamMngBo");
		if("add".equals(getMethod())) {
		} else if("edit".equals(getMethod())) {
			rspCode = edit();
		}
		return rspCode;
	}

	
	private String edit() {
		String ret = tblRiskParamMngBo.save(tblRiskParamMng, operator);
		return ret;
	}


	public TblRiskParamMngBo getTblRiskParamMngBo() {
		return tblRiskParamMngBo;
	}


	public void setTblRiskParamMngBo(TblRiskParamMngBo tblRiskParamMngBo) {
		this.tblRiskParamMngBo = tblRiskParamMngBo;
	}


	public TblRiskParamMng getTblRiskParamMng() {
		return tblRiskParamMng;
	}


	public void setTblRiskParamMng(TblRiskParamMng tblRiskParamMng) {
		this.tblRiskParamMng = tblRiskParamMng;
	}
	

}
