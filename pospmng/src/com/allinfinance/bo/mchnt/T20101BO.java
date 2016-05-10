package com.allinfinance.bo.mchnt;

import java.util.List;

import com.allinfinance.po.TblDivMchntTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;

/**
 * Title:商户维护BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public interface T20101BO {
	
	/**
	 * 查询商户信息
	 * @param mchntId    商户编号
	 * @return
	 */
	public TblMchtBaseInfTmp get(String mchntId);
	/**
	 * 添加商户信息
	 * @param tblMchntInfoTmp    商户信息
	 * @param tblDivMchntTmpList    商户分期信息
	 * @return
	 */
	public String add(TblMchtBaseInfTmp tblMchntInfoTmp,TblMchtSettleInfTmp tblMchtSettleInfTmp);
	/**
	 * 更新商户信息
	 * @param tblMchntInfoTmp    商户信息
	 * @return
	 */
	public String update(TblMchtBaseInfTmp tblMchntInfoTmp);
	/**
	 * 更新商户信息
	 * @param tblMchntInfoTmp    商户信息
	 * @param tblDivMchntTmpList    待添加商户分期信息
	 * @param tblDivMchntTmpListDelete    待删除商户分期信息
	 * @return
	 */
	public String update(TblMchtBaseInfTmp tblMchntInfoTmp,List<TblDivMchntTmp> tblDivMchntTmpList,
						 List<TblDivMchntTmp> tblDivMchntTmpListDelete);
	/**
	 * 加载商户分期信息
	 * @param mchntId    商户编号
	 * @return
	 */
	public List<TblDivMchntTmp> loadDiv(String mchntId);
}
