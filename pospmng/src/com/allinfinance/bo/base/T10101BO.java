package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.common.Operator;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblBrhInfoHis;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.po.base.TblBrhSettleInfHis;

/**
 * Title: 机构信息BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public interface T10101BO {
	/**
	 * 查询机构信息
	 * @param brhId    机构编号
	 * @return
	 */
	public TblBrhInfo get(String brhId);
	
	/**
	 * 查询机构清算信息
	 * @param brhId    机构编号
	 * @return
	 */
	public TblBrhSettleInf getSettle(String brhId);
	
	/**
	 * 查询机构信息（历史）
	 * @param brhId    机构编号
	 * @return
	 */
	public TblBrhInfoHis getBrhHis(String brhId, String seqId);
	
	/**
	 * 查询机构清算信息（历史）
	 * @param brhId    机构编号
	 * @return
	 */
	public TblBrhSettleInfHis getSettleHis(String brhId, String seqId);
	
	/**
	 * 添加机构信息
	 * @param tblBrhInfo    机构信息
	 * @return
	 */
	public String add(TblBrhInfo tblBrhInfo,TblBrhSettleInf tblBrhSettleInf);
	/**
	 * 更新机构信息
	 * @param tblBrhInfoList    机构信息列表
	 * @return
	 */
	public String update(List<TblBrhInfo> tblBrhInfoList);
	
	/**
	 * 更新机构信息、机构清算信息
	 * @param tblBrhInfoList    机构信息列表
	 * @return
	 */
	public String updateSettle(TblBrhInfo tblBrhInfo,TblBrhSettleInf tblBrhSettleInf,
			Operator operator,TblBrhInfo tblBrhInfOld);
	
	/**
	 * 删除机构信息
	 * @param tblBrhInfo    机构信息
	 * @return
	 */
	public String delete(TblBrhInfo tblBrhInfo);
	/**
	 * 删除机构信息
	 * @param brhId    机构编号
	 * @return
	 */
	public String delete(String brhId);
	//审核通过机构信息
	public String accept(String brhId,String oprId,String txnId, ShTblOprInfo shTblOprInfo) throws Exception;
	//审核退回机构信息
	public String refuse(String brhId,String oprId,String txnId);
	// 记录审批过程
	public String approveRecord(TblBrhInfo tblBrhInfo, String oprType, String oprInfo);
	/**
	 * 1、客户在POSP入网时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  0）、交易码：A0100 子交易码：1
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因
	 * 2、客户在POSP结算信息并更时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  0）、交易码：F0100 子交易码：1
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因 
	 *
	 * @param brhInfo
	 * @param settleInfo
	 * @param oprInfo
	 * @return
	 * @throws Exception 
	 */
	public String sendMessage(TblBrhInfo brhInfo,TblBrhSettleInf settleInfo,ShTblOprInfo oprInfo) throws Exception;
	
}
