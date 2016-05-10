package com.allinfinance.struts.risk.action;


import com.allinfinance.bo.risk.T40213BO;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;



@SuppressWarnings("serial")
public class T40213Action extends BaseAction {
	
	T40213BO t40213BO = (T40213BO) ContextUtil.getBean("T40213BO");
	
	private String mchtNo;
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		 if("remindMcht".equals(method)) {
			log("风险商户提醒");
			rspCode = remindMcht();
		} else if("blockMcht".equals(method)) {
			log("商户冻结");
			rspCode = blockMcht();
		} else if("recoverMcht".equals(method)) {
			log("商户解冻");
			rspCode = recoverMcht();
		} else if("blockSettle".equals(method)) {
			log("商户结算冻结");
			rspCode = blockSettle();
		} else if("recoverSettle".equals(method)) {
			log("商户结算解冻");
			rspCode = recoverSettle();
		} 
		
		return rspCode;
	}
	



	
	/**
	 * 商户结算解冻
	 * @return
	 */
	private String recoverSettle() {
		// TODO Auto-generated method stub
		
		return t40213BO.recoverSettle(mchtNo);
	}

	/**
	 * 商户结算冻结
	 * @return
	 */
	private String blockSettle() {
		// TODO Auto-generated method stub
		
		return t40213BO.blockSettle(mchtNo);
	}

	/**
	 * 商户解冻
	 * @return
	 */
	private String recoverMcht() {
		// TODO Auto-generated method stub
		
		return t40213BO.recoverMcht(mchtNo, operator);
	}

	/**
	 * 商户冻结
	 * @return
	 */
	private String blockMcht() {
		// TODO Auto-generated method stub
		
		return t40213BO.blockMcht(mchtNo, operator);
	}

	/**
	 * 风险商户提醒
	 * @return
	 */
	private String remindMcht() {
		// TODO Auto-generated method stub
//		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		return t40213BO.remindMcht(mchtNo,operator);
	}





	public String getMchtNo() {
		return mchtNo;
	}





	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	
	

	
	
	
	
}
