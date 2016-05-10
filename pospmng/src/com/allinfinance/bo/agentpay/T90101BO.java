package com.allinfinance.bo.agentpay;

import java.util.List;

import com.allinfinance.po.agentpay.TblMchtFileTransInfo;

/**
 * 地区码信息BO
 * @author huangjl
 *
 * 2014年3月21日 下午1:54:22
 */
public interface T90101BO {
	
	/**
	 * 获取 商户文件传输配置表
	 *@author huangjl
	 * @param MchtFileTransNo
	 * @return
	 * 2014年4月8日 下午4:46:30
	 */
	public TblMchtFileTransInfo getMchtFileTransInfo(String MchtFileTransNo);
	
	/**
	 * 增加商户文件传输配置表
	 *@author huangjl
	 * @param tblMchtFileTransInfo
	 * @return
	 * 2014年4月8日 下午4:47:10
	 */
	public String add(TblMchtFileTransInfo tblMchtFileTransInfo);
	/**
	 * 批量更新商户文件传输配置表
	 *@author huangjl
	 * @param tblMchtFileTransInfoList
	 * @return
	 * 2014年4月8日 下午4:47:29
	 */
	public String update(List<TblMchtFileTransInfo> tblMchtFileTransInfoList);
	/**
	 * 更新商户文件传输配置表
	 *@author huangjl
	 * @param tblMchtFileTransInfo
	 * @return
	 * 2014年4月8日 下午4:47:49
	 */
	public String update(TblMchtFileTransInfo tblMchtFileTransInfo);
	/**
	 * 删除 商户文件传输配置表
	 *@author huangjl
	 * @param tblMchtFileTransInfo
	 * @return
	 * 2014年4月8日 下午4:47:58
	 */
	public String delete(TblMchtFileTransInfo tblMchtFileTransInfo);
	/**
	 * 根据id删除 商户文件传输配置表
	 *@author huangjl
	 * @param MchtFileTransNo
	 * @return
	 * 2014年4月8日 下午4:48:13
	 */
	public String delete(String MchtFileTransNo);

}
