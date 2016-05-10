package com.allinfinance.bo.agentpay;

import java.util.List;

import com.allinfinance.po.agentpay.TblAreaInfo;
import com.allinfinance.po.agentpay.TblAreaInfoPK;

/**
 * 地区码信息BO
 * @author huangjl
 *
 * 2014年3月21日 下午1:54:22
 */
public interface T90201BO {
	
	
	/**
	 * 根据 地区码 获取 地区码信息
	 *@author huangjl
	 * @param AreaNO 地区码
	 * @return
	 * 2014年3月20日 下午2:54:12
	 */
	public TblAreaInfo getAreaInfo(TblAreaInfoPK tblAreaInfoPK);
	
	/**
	 * 新增地区码信息
	 *@author huangjl
	 * @param tblAreaInfo
	 * @return
	 * 2014年3月20日 下午2:55:13
	 */
	public String add(TblAreaInfo tblAreaInfo);
	/**
	 * 批量更新地区码信息
	 *@author huangjl
	 * @param tblAreaInfoList
	 * @return
	 * 2014年3月20日 下午2:55:34
	 */
	public String update(List<TblAreaInfo> tblAreaInfoList);
	/**
	 * 更新地区码信息
	 *@author huangjl
	 * @param tblAreaInfo
	 * @return
	 * 2014年3月20日 下午2:57:01
	 */
	public String update(TblAreaInfo tblAreaInfo);
	/**
	 * 删除地区码信息
	 *@author huangjl
	 * @param tblAreaInfo
	 * @return
	 * 2014年3月20日 下午2:57:09
	 */
	public String delete(TblAreaInfo tblAreaInfo);
	/**
	 * 根据地区码删除地区码信息
	 *@author huangjl
	 * @param AreaNo
	 * @return
	 * 2014年3月20日 下午2:57:25
	 */
	public String delete(TblAreaInfoPK id);

}
