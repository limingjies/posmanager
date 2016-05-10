package com.allinfinance.struts.mchnt.action;

import java.io.IOException;

import com.allinfinance.bo.mchnt.T20202BO;
import com.allinfinance.po.TblMchtLimitDate;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

public class T20202Action extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mchtNo;
	private String limitDate;

	private T20202BO t20202BO = (T20202BO) ContextUtil.getBean("T20202BO");

	@Override
	protected String subExecute() throws Exception {
		if (t20202BO.get(mchtNo) == null) {
			rspCode = "该商户补漏信息不存在！";
		} else {
			if (method.equals("limit")) {
				rspCode = limit();
			} 
			else if (method.equals("stop")) {
				rspCode = freeze();
			}
			else if (method.equals("complete")) {
				rspCode = complete();
			}
		}
		return rspCode;
	}

	public String complete() throws IOException {
		TblMchtLimitDate tblMchtLimitDate = t20202BO.get(mchtNo);
		tblMchtLimitDate.setUpdopr(operator.getOprId());
		tblMchtLimitDate.setUpdtime(CommonFunction.getCurrentDateTime());
		tblMchtLimitDate.setStatus("0");
		return t20202BO.complete(tblMchtLimitDate);
	}

	public String limit() throws IOException {
		TblMchtLimitDate tblMchtLimitDate = t20202BO.get(mchtNo);
		tblMchtLimitDate.setUpdopr(operator.getOprId());
		tblMchtLimitDate.setUpdtime(CommonFunction.getCurrentDateTime());
		tblMchtLimitDate.setLimitdate(limitDate);
		return t20202BO.limit(tblMchtLimitDate);
	}

	public String freeze() throws IOException {

		String retMsg = t20202BO.freeze(mchtNo,operator);
		return retMsg;
	}

	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNo the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	/**
	 * @return the limitDate
	 */
	public String getLimitDate() {
		return limitDate;
	}

	/**
	 * @param limitDate the limitDate to set
	 */
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}


}
