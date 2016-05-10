package com.allinfinance.bo.settleNew;




public interface T100304BO {

	/** 生成全部划付文件 */
	public String allFile(String instDate) throws Exception;
	
	/** 转划付文件 */
	public String makeFile(String instDate,String channelId,String aimChnlId) throws Exception;
	


}