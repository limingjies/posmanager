package com.allinfinance.bo.agentpay;

import java.util.List;

import com.allinfinance.po.agentpay.TblMchtInfo;

/**
 * 商户信息信息BO
 * @author huangjl
 *
 * 2014年3月20日 下午2:53:38
 */
public interface T90301BO {
	
	
	/**
	 * 根据 商户信息 获取 商户信息信息
	 *@author huangjl
	 * @param bankNO 商户信息
	 * @return
	 * 2014年3月20日 下午2:54:12
	 */
	public TblMchtInfo getMchtInfo(String mcht);
	
	/**
	 * 新增商户信息信息
	 *@author huangjl
	 * @param tblMchtInfo
	 * @return
	 * 2014年3月20日 下午2:55:13
	 */
	public String add(TblMchtInfo tblMchtInfo) ;
	/**
	 * 批量更新商户信息信息
	 *@author huangjl
	 * @param tblMchtInfoList
	 * @return
	 * 2014年3月20日 下午2:55:34
	 */
	public String update(List<TblMchtInfo> tblMchtInfoList);
	/**
	 * 更新商户信息信息
	 *@author huangjl
	 * @param tblMchtInfo
	 * @return
	 * 2014年3月20日 下午2:57:01
	 */
	public String update(TblMchtInfo tblMchtInfo);
	/**
	 * 删除商户信息信息
	 *@author huangjl
	 * @param tblMchtInfo
	 * @return
	 * 2014年3月20日 下午2:57:09
	 */
	public String delete(TblMchtInfo tblMchtInfo);
	/**
	 * 根据商户信息删除商户信息信息
	 *@author huangjl
	 * @param bankNo
	 * @return
	 * 2014年3月20日 下午2:57:25
	 */
	public String delete(String mcht);

}
