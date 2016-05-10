package com.allinfinance.bo.settle;




public interface T80603BO {

	/** 重置批处理状态 */
	public String update(String falg, String batId,String restGrpId) throws Exception;
	
	/**批处理状态重置*/
	public String resetBat(String id) throws Exception;
	
	/**批处理状态重置*/
	public String resetAsn(String id2) throws Exception;


}