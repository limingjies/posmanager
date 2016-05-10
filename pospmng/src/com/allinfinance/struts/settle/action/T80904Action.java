package com.allinfinance.struts.settle.action;

import com.allinfinance.bo.settle.T80904BO;
import com.allinfinance.po.settle.TblPayTypeSmall;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

public class T80904Action extends BaseAction {
	
	private static final long serialVersionUID = 8502465673466304924L;
	
	T80904BO t80904BO = (T80904BO) ContextUtil.getBean("T80904BO");
	private TblPayTypeSmall tblPayTypeSmallAdd;
	private String acctNo;
	
	
	@Override
	protected String subExecute() throws Exception {
		if("add".equals(method)) {
			rspCode = add();
		} else if("delete".equals(method)) {
			rspCode = delete();
		}
		return rspCode;
	}
	
	/**
	 * 中信小额支付-新增账户信息
	 * @return
	 * @throws Exception
	 */
	private String add() throws Exception {
		if(t80904BO.get(tblPayTypeSmallAdd.getAcctNo()) != null) {
			return "该账户信息已存在,请刷新！";
		}
		tblPayTypeSmallAdd.setCrtOpr(operator.getOprId());
		tblPayTypeSmallAdd.setCrtTime(CommonFunction.getCurrentDateTime());
		return t80904BO.add(tblPayTypeSmallAdd);
	}
	
	/**
	 * 中信小额支付-删除账户信息
	 * @return
	 * @throws Exception
	 */
	private String delete() throws Exception {
		
		if(t80904BO.get(acctNo) == null) {
			return "该账户信息不存在,请刷新！";
		}
		return t80904BO.delete(acctNo);
	}

	/**
	 * @return the tblPayTypeSmallAdd
	 */
	public TblPayTypeSmall getTblPayTypeSmallAdd() {
		return tblPayTypeSmallAdd;
	}

	/**
	 * @param tblPayTypeSmallAdd the tblPayTypeSmallAdd to set
	 */
	public void setTblPayTypeSmallAdd(TblPayTypeSmall tblPayTypeSmallAdd) {
		this.tblPayTypeSmallAdd = tblPayTypeSmallAdd;
	}

	/**
	 * @return the acctNo
	 */
	public String getAcctNo() {
		return acctNo;
	}

	/**
	 * @param acctNo the acctNo to set
	 */
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
}
