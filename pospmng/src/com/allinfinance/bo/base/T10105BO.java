package com.allinfinance.bo.base;

import java.util.List;
import java.util.Map;

import com.allinfinance.common.Operator;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.TblAgentFeeCfg;
import com.allinfinance.po.TblAgentFeeCfgHis;
import com.allinfinance.po.TblAgentRateInfo;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.base.TblBrhCashInf;
import com.allinfinance.po.base.TblBrhSettleInf;

public interface T10105BO {

	/**
	 * 合作伙伴新增
	 * @param tblBrhInfo
	 * @param tblBrhSettleInf
	 * @param tblAgentFeeCfgZlfAdd
	 * @param rateList
	 * @param shTblOprInfo 
	 * @return
	 */
	public String add(TblBrhInfo tblBrhInfo, TblBrhSettleInf tblBrhSettleInf, TblAgentFeeCfg tblAgentFeeCfgZlfAdd,
			List<TblAgentRateInfo> rateList, ShTblOprInfo shTblOprInfo, List<TblBrhCashInf> brhCashRateList);
	
	/**
	 * 更新机构信息、机构清算信息 机构费率信息，
	 * @param tblBrhInfoList    机构信息列表
	 * @return
	 */
	public String updateSettle(TblBrhInfo tblBrhInfo,TblBrhSettleInf tblBrhSettleInf,
			Operator operator,TblBrhInfo tblBrhInfOld,TblAgentFeeCfg tblAgentFeeCfgUpd,List<TblAgentRateInfo> rateList,List<TblBrhCashInf> brhCashRateList);

	/**
	 * 根据合作伙伴id查找分润信息
	 * @param brhId
	 * @return
	 */
	public TblAgentFeeCfg getAgentInfo(String brhId);
	
	/**
	 * 根据合作伙伴id查找分润信息(历史）
	 * @param brhId
	 * @param seqId
	 * @return
	 */
	public TblAgentFeeCfgHis getAgentInfoHis(String brhId, String seqId);
	
	/**
	 * 根据开户行信息  反查地区信息
	 * @param brhId
	 * @return
	 */
	public Map getAreaInfoByBankNo(String bankNo);

	// 记录修改历史-机构信息
	public String historyRecord(TblBrhInfo tblBrhInfo);
	// 记录修改历史-机构结算信息
	public String historyRecord(TblBrhSettleInf tblBrhSettleInf, String seqId);
	
	public String historyRecord(TblAgentFeeCfg tblAgentFeeCfg, String seqId);
	
	public String historyRecord(TblAgentRateInfo tblAgentRateInfo, String seqId);
}
