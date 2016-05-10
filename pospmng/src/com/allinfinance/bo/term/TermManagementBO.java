package com.allinfinance.bo.term;

import java.util.HashMap;
import java.util.List;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermManagement;

public interface TermManagementBO {
	
	/**
	 * 入库
	 */
	public String storeTerminal(HashMap map);
	
	/**
	 * 批量入库
	 */
	public String storeTerminals(HashMap map);
	
	/**
	 * 获取终端库存表的nextId
	 */
	public String getNextId(String brhId,String termType);
	
	/**
	 * 终端库存作废
	 */
	public String invalidTerminal(HashMap map);
	
	/**
	 * 终端库存上缴
	 */
	public String upTerminal(HashMap map);
	
	/**
	 * 终端库存下发
	 */
	public String downTerminal(HashMap map);
	
	/**
	 * 终端库存接收
	 */
	public String revTerminal(HashMap map);
	
	
	/**
	 * 终端库存下发
	 */
	public List<TblTermManagement> queryTerminal(String manufacturer,String terminalType,int number,String termType);
	
	/**
	 * 获取批次号
	 */
	public String getNextBatchNo();
	
	/**
	 * 更新终端库存状态
	 */
	public boolean updTerminal(TblTermManagement terminal, String state,String oprId,String mech);
	
	/**
	 * 批次拒绝
	 */
	public String refuseTerminals(String batchNo,String termId,String mechNo,String oprId);
	
	/**
	 * 审核拒绝
	 */
	public String refuseTerminal(String batchNo,String termId,String mechNo,String oprId);
	
	/**
	 * 通过审核
	 */
	public String reciTerminal(String batchNo,String termId,String mechNo,String oprId);
	
	/**
	 * 批次号通过
	 */
	public String reciTerminals(String batchNo,String termId,String mechNo,String oprId);
	
	/**
	 * 批次签收
	 */
	public String signTerminals(String batchNo,String termId,String oprId);
	
	/**
	 * 单笔签收
	 */
	public String signTerminal(String batchNo,String termId,String oprId);
	
	/**
	 * 回退操作
	 */
	public String bankTermianl(int action,String termId,Operator operator);
	
	/**
	 * 获取终端库存信息
	 */
	public TblTermManagement getTerminal(String termId);
	
	/**
	 * 新增终端审核拒绝解绑终端库存
	 */
	public boolean refuseTerm(String termId,String oprId);
	
	/**
	 * 终端绑定
	 */

	public boolean bindTermInfo(TblTermManagement tblTermManagement,String oprId,TblTermInf tblTermInf,TblTermInfTmp tblTermInfTmp);
	
	/**
	 * 终端解除绑定
	 * 
	 * 
	 */
	public boolean unbindTermInfo(TblTermManagement tblTermManagement,String oprId,TblTermInf tblTermInf,TblTermInfTmp tblTermInfTmp);
}
