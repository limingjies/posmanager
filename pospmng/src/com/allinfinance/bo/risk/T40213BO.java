package com.allinfinance.bo.risk;

import com.allinfinance.common.Operator;



public interface T40213BO {
	
	
	/**风险商户提醒*/
	public String remindMcht(String  mchtNo,Operator operator);
	
	/**商户冻结*/
	public String blockMcht(String mchtNo,Operator operator);
	
	/**商户解冻*/
	public String recoverMcht(String mchtNo,Operator operator);
	
	/**商户结算冻结*/
	public String blockSettle(String mchtNo);
	
	/**商户结算解冻*/
	public String recoverSettle(String mchtNo);
	
	
	
	
}
