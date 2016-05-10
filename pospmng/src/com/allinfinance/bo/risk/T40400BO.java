package com.allinfinance.bo.risk;

import com.allinfinance.po.risk.TblGreyMchtSort;
import com.allinfinance.po.risk.TblRiskGreyMcht;
import com.allinfinance.po.risk.TblRiskGreyMchtPK;


/**
 * 
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-01
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * author: 徐鹏飞
 *  
 * time: 2014年12月4日下午4:56:54
 * 
 * version: 1.0
 */
public interface T40400BO {
	/**
	 * 获取商户灰名单类别关联灰名单商户
	 */
	public boolean query(String key) throws Exception;
	/**
	 * 获取商户灰名单类别
	 */
	public TblGreyMchtSort getSort(String key) throws Exception;
	/**
	 * 新增商户灰名单类别
	 */
	public String addSort(TblGreyMchtSort tblGreyMchtSort) throws Exception;
	/**
	 * 修改商户灰名单类别
	 */
	public String updateSort(TblGreyMchtSort tblGreyMchtSort) throws Exception;
	/**
	 * 删除商户灰名单类别
	 */
	public String deleteSort(String key) throws Exception;
	
	
	/**
	 * 获取商户灰名单
	 */
	public TblRiskGreyMcht get(TblRiskGreyMchtPK key) throws Exception;
	/**
	 * 新增商户灰名单
	 */
	public String add(TblRiskGreyMcht tblRiskGreyMcht) throws Exception;
	/**
	 * 修改商户灰名单
	 */
	public String update(TblRiskGreyMcht tblRiskGreyMcht) throws Exception;
	/**
	 * 删除商户灰名单
	 */
	public String delete(TblRiskGreyMchtPK key) throws Exception;
	
	
}
