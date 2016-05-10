package com.allinfinance.bo.query;

import com.allinfinance.po.query.TblAccInConfirmHis;

/**
 * T50109页面业务操作对象接口
 * 
 * @author luhq
 *
 */
public interface T50109BO {
	/**
	 * 根据清算日期获取确认入账操作历史记录
	 * 
	 * @param dateSettlmt 清算日期（格式YYYYMMDD）
	 * @return 入账操作历史记录
	 */
	public TblAccInConfirmHis get(String dateSettlmt);
	
	/**
	 * 保存入账操作历史记录
	 * 
	 * @param tblAccInConfirmHis 入账操作历史记录
	 */
	public void save(TblAccInConfirmHis tblAccInConfirmHis);
	
	/**
	 * 判断该清算日期后台是否有成功跑批记录
	 * 
	 * @param dateSettlmt 清算日期（格式YYYYMMDD）
	 * @return 是否存在
	 */
	public boolean existRunBatchRecords(String dateSettlmt);
}
