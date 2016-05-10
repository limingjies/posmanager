package com.allinfinance.bo.settleNew;




public interface T100104BO {

	/** 重置批处理状态 */
	public String start(String settleDate) throws Exception;
	
	/**批处理状态重置*/
	public String reset(String settleDate) throws Exception;
	


}