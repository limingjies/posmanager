package com.allinfinance.bo.agentpay;

import java.util.List;

import com.allinfinance.po.agentpay.TblBankInfo;

/**
 * 银行代码信息BO
 * @author huangjl
 *
 * 2014年3月20日 下午2:53:38
 */
public interface T90202BO {
	
	
	/**
	 * 根据 银行代码 获取 银行代码信息
	 *@author huangjl
	 * @param bankNO 银行代码
	 * @return
	 * 2014年3月20日 下午2:54:12
	 */
	public TblBankInfo getBankInfo(String bankNo);
	
	/**
	 * 新增银行代码信息
	 *@author huangjl
	 * @param tblBankInfo
	 * @return
	 * 2014年3月20日 下午2:55:13
	 */
	public String add(TblBankInfo tblBankInfo) ;
	/**
	 * 批量更新银行代码信息
	 *@author huangjl
	 * @param tblBankInfoList
	 * @return
	 * 2014年3月20日 下午2:55:34
	 */
	public String update(List<TblBankInfo> tblBankInfoList);
	/**
	 * 更新银行代码信息
	 *@author huangjl
	 * @param tblBankInfo
	 * @return
	 * 2014年3月20日 下午2:57:01
	 */
	public String update(TblBankInfo tblBankInfo);
	/**
	 * 删除银行代码信息
	 *@author huangjl
	 * @param tblBankInfo
	 * @return
	 * 2014年3月20日 下午2:57:09
	 */
	public String delete(TblBankInfo tblBankInfo);
	/**
	 * 根据银行代码删除银行代码信息
	 *@author huangjl
	 * @param bankNo
	 * @return
	 * 2014年3月20日 下午2:57:25
	 */
	public String delete(String bankNo);

}
