package com.allinfinance.bo.mchnt;

import java.util.List;

import com.allinfinance.po.mchnt.TblInfMchntTp;
import com.allinfinance.po.mchnt.TblInfMchntTpPK;




/**
 * Title:商户MCC维护BO
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
public interface T20303BO {
	
	/**
	 * 添加商户MCC息
	 * @param TblMchntTp    商户MCC
	 * @return
	 */
	public String add(TblInfMchntTp tblInfMchntTp);
	
	/**
	 * 查询商户MCC信息
	 * @param mchntTp    商户MCC编号
	 * @return
	 */
	public TblInfMchntTp get(TblInfMchntTpPK mchntTp);
	
	/**
	 * 删除商户MCC信息
	 * @param mchntTp    商户MCC编号
	 * @return
	 */
	public String delete(TblInfMchntTpPK mchntTp);
	
	/**
	 * 删除商户MCC信息
	 * @param TblInfMchntTp    商户MCC信息
	 * @return
	 */
	public String delete(TblInfMchntTp tblInfMchntTp);
	
	/**
	 * 更新商户MCC信息
	 * @param TblInfMchntTp    商户MCC信息
	 * @return
	 */
	public String update(TblInfMchntTp tblInfMchntTp);
	
	/**
	 * 批量更新商户MCC
	 * @param tblMchntTpList    商户MCC信息集合
	 * @return
	 */
	public String update(List<TblInfMchntTp> tblMchntTpList);
}
