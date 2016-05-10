package com.allinfinance.bo.agentpay;

import java.util.List;

import com.allinfinance.po.agentpay.TblMchtFund;

/**
 * 商户资金信息信息BO
 * @author huangjl
 *
 * 2014年3月20日 下午2:53:38
 */
public interface T90401BO {
	
	
	/**
	 * 根据 商户资金信息 获取 商户资金信息信息
	 *@author huangjl
	 * @param bankNO 商户资金信息
	 * @return
	 * 2014年3月20日 下午2:54:12
	 */
	public TblMchtFund getMchtFund(String mcht);
	
	/**
	 * 新增商户资金信息信息
	 *@author huangjl
	 * @param tblMchtFund
	 * @return
	 * 2014年3月20日 下午2:55:13
	 */
	public String add(TblMchtFund tblMchtFund) ;
	/**
	 * 批量更新商户资金信息信息
	 *@author huangjl
	 * @param tblMchtFundList
	 * @return
	 * 2014年3月20日 下午2:55:34
	 */
	public String update(List<TblMchtFund> tblMchtFundList);
	/**
	 * 更新商户资金信息信息
	 *@author huangjl
	 * @param tblMchtFund
	 * @return
	 * 2014年3月20日 下午2:57:01
	 */
	public String update(TblMchtFund tblMchtFund);
	/**
	 * 删除商户资金信息信息
	 *@author huangjl
	 * @param tblMchtFund
	 * @return
	 * 2014年3月20日 下午2:57:09
	 */
	public String delete(TblMchtFund tblMchtFund);
	/**
	 * 根据商户资金信息删除商户资金信息信息
	 *@author huangjl
	 * @param bankNo
	 * @return
	 * 2014年3月20日 下午2:57:25
	 */
	public String delete(String mcht);

}
