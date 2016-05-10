package com.allinfinance.bo.agentpay;

import java.util.List;

import com.allinfinance.po.agentpay.TblParamInfo;
import com.allinfinance.po.agentpay.TblParamInfoPK;

/**
 * 参数信息BO
 * @author huangjl
 *
 * 2014年3月20日 下午2:53:38
 */
public interface T90204BO {
	
	
	/**
	 * 根据 参数 获取 参数信息
	 *@author huangjl
	 * @param bankNO 参数
	 * @return
	 * 2014年3月20日 下午2:54:12
	 */
	public TblParamInfo get(TblParamInfoPK id);
	
	/**
	 * 新增参数信息
	 *@author huangjl
	 * @param tblParamInfo
	 * @return
	 * 2014年3月20日 下午2:55:13
	 */
	public String add(TblParamInfo tblParamInfo) ;
	/**
	 * 批量更新参数信息
	 *@author huangjl
	 * @param tblParamInfoList
	 * @return
	 * 2014年3月20日 下午2:55:34
	 */
	public String update(List<TblParamInfo> tblParamInfoList);
	/**
	 * 更新参数信息
	 *@author huangjl
	 * @param tblParamInfo
	 * @return
	 * 2014年3月20日 下午2:57:01
	 */
	public String update(TblParamInfo tblParamInfo);
	/**
	 * 删除参数信息
	 *@author huangjl
	 * @param tblParamInfo
	 * @return
	 * 2014年3月20日 下午2:57:09
	 */
	public String delete(TblParamInfo tblParamInfo);
	/**
	 * 根据参数删除参数信息
	 *@author huangjl
	 * @param bankNo
	 * @return
	 * 2014年3月20日 下午2:57:25
	 */
	public String delete(TblParamInfoPK id);

}
