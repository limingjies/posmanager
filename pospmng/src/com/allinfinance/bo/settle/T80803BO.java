package com.allinfinance.bo.settle;




public interface T80803BO {

	/** 生成全部划付文件 */
	public String allFile(String instDate) throws Exception;
	
	/** 转划付文件 */
	public String makeFile(String instDate,String channelId,String aimChnlId) throws Exception;
	
	/**生成中信划付文件*//*
	public String ZXfile(String instDate,String channelId) throws Exception;
	
	*//**生成工行划付文件*//*
	public String GHfile(String instDate,String channelId) throws Exception;
	
	*//**生成邮储划付文件*//*
	public String YCfile(String instDate,String channelId) throws Exception;*/
	


}