package com.allinfinance.struts.risk.action;


import com.allinfinance.bo.risk.T402021BO;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.TblRiskBlackMcht;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40208Action extends BaseAction {
	
	T402021BO t402021BO = (T402021BO) ContextUtil.getBean("T402021BO");
	private TblRiskBlackMcht tblRiskBlackMcht;
	private String mchtNo;
	
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加商户黑名单信息");
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除商户黑名单信息");
			rspCode = delete();
		} 
		return rspCode;
	}
	
	/**
	 * 添加商户黑名单信息
	 * @return
	 * 2010-8-26下午11:19:52
	 * @throws Exception 
	 */
	private String add() throws Exception {
		
		if(t402021BO.get(tblRiskBlackMcht.getMchtNo()) != null) {
			return ErrorCode.T40202_01;
		}
		tblRiskBlackMcht.setCrtDateTime(CommonFunction.getCurrentDateTime());
		tblRiskBlackMcht.setCrtOprId(operator.getOprId());
		return t402021BO.add(tblRiskBlackMcht);
	}
	
	/**
	 * 删除商户黑名单信息
	 * @return
	 * 2010-8-26下午11:56:26
	 * @throws Exception 
	 */
	private String delete() throws Exception {
		
		return t402021BO.delete(mchtNo);
	}

	public TblRiskBlackMcht getTblRiskBlackMcht() {
		return tblRiskBlackMcht;
	}

	public void setTblRiskBlackMcht(TblRiskBlackMcht tblRiskBlackMcht) {
		this.tblRiskBlackMcht = tblRiskBlackMcht;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	
	
	
	
}
