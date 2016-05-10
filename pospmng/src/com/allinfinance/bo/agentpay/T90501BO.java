package com.allinfinance.bo.agentpay;

import com.allinfinance.po.agentpay.TblSndPack;


/**
 * 初审BO
 * @author huangjl
 *
 * 2014年3月20日 下午2:53:38
 */
public interface T90501BO {
	
	public TblSndPack get(String batchId);
	
	public String accept(String batchId);
	
	public String refuse(String batchId,String refuseInfo);
	
	public String back(String batchId,String refuseInfo);

}
