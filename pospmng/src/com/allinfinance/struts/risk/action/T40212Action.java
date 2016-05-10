package com.allinfinance.struts.risk.action;


import com.allinfinance.bo.risk.T40212BO;
import com.allinfinance.po.risk.TblBankCardBlack;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40212Action extends BaseAction {
	
	T40212BO t40212BO = (T40212BO) ContextUtil.getBean("T40212BO");
	private TblBankCardBlack tblBankCardBlackAdd;
	private String cardNo;
	
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("操作员["+operator.getOprId()+"]，于["+CommonFunction.getCurrentDateTimeForShow()
					+"]添加银行卡黑名单信息！卡号["+tblBankCardBlackAdd.getCardNo()+"]");
			rspCode = add();
		} else if("delete".equals(method)) {
			log("操作员["+operator.getOprId()+"]，于["+CommonFunction.getCurrentDateTimeForShow()
					+"]删除银行卡黑名单信息！卡号["+cardNo+"]");
			rspCode = delete();
		} 
		return rspCode;
	}
	
	/**
	 * 添加银行卡黑名单信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String add() throws Exception {
		
		if(t40212BO.get(tblBankCardBlackAdd.getCardNo()) != null) {
			return "该银行卡已列入黑名单,请刷新！";
		}
		tblBankCardBlackAdd.setCrtOpr(operator.getOprId());
		tblBankCardBlackAdd.setCrtTime(CommonFunction.getCurrentDateTime());
		return t40212BO.add(tblBankCardBlackAdd);
	}
	
	/**
	 * 删除银行卡黑名单信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String delete() throws Exception {
		
		if(t40212BO.get(cardNo) == null) {
			return "该银行卡未列入黑名单,请刷新！";
		}
		return t40212BO.delete(cardNo,operator);
	}

	/**
	 * @return the tblBankCardBlackAdd
	 */
	public TblBankCardBlack getTblBankCardBlackAdd() {
		return tblBankCardBlackAdd;
	}

	/**
	 * @param tblBankCardBlackAdd the tblBankCardBlackAdd to set
	 */
	public void setTblBankCardBlackAdd(TblBankCardBlack tblBankCardBlackAdd) {
		this.tblBankCardBlackAdd = tblBankCardBlackAdd;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
}
