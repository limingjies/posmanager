package com.allinfinance.bo.agentpay;

import java.util.List;

import com.allinfinance.po.agentpay.TblBusCodeInfo;

/**
 * 业务代码信息BO
 * @author huangjl
 *
 * 2014年3月20日 下午2:53:38
 */
public interface T90203BO {
	
	
	/**
	 * 根据 业务代码 获取 业务代码信息
	 *@author huangjl
	 * @param bankNO 业务代码
	 * @return
	 * 2014年3月20日 下午2:54:12
	 */
	public TblBusCodeInfo getBusCodeInfo(String busCode);
	
	/**
	 * 新增业务代码信息
	 *@author huangjl
	 * @param tblBusCodeInfo
	 * @return
	 * 2014年3月20日 下午2:55:13
	 */
	public String add(TblBusCodeInfo tblBusCodeInfo) ;
	/**
	 * 批量更新业务代码信息
	 *@author huangjl
	 * @param tblBusCodeInfoList
	 * @return
	 * 2014年3月20日 下午2:55:34
	 */
	public String update(List<TblBusCodeInfo> tblBusCodeInfoList);
	/**
	 * 更新业务代码信息
	 *@author huangjl
	 * @param tblBusCodeInfo
	 * @return
	 * 2014年3月20日 下午2:57:01
	 */
	public String update(TblBusCodeInfo tblBusCodeInfo);
	/**
	 * 删除业务代码信息
	 *@author huangjl
	 * @param tblBusCodeInfo
	 * @return
	 * 2014年3月20日 下午2:57:09
	 */
	public String delete(TblBusCodeInfo tblBusCodeInfo);
	/**
	 * 根据业务代码删除业务代码信息
	 *@author huangjl
	 * @param bankNo
	 * @return
	 * 2014年3月20日 下午2:57:25
	 */
	public String delete(String busCode);

}
