package com.allinfinance.bo.daytrade;

import java.util.List;

import com.allinfinance.frontend.dto.withdraw.WithDrawJsonDto;
import com.allinfinance.po.daytrade.TblWithdrawInf;
import com.allinfinance.po.daytrade.TblWithdrawInfDtl;

public interface T70202BO {
	public TblWithdrawInf get(String key) throws org.hibernate.HibernateException;

	public String saveOrUpdate(TblWithdrawInf tblWithdrawInf)
			throws org.hibernate.HibernateException;
	/**
	 * 获取商户提现流水信息
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public List<TblWithdrawInfDtl> getDtlList(String batchNo) throws Exception;
	/**
	 * 确认提现
	 * @param tblWithdrawInf
	 * @param withDrawJson
	 * @return
	 * @throws Exception
	 */
	public String wdProcess(TblWithdrawInf tblWithdrawInf, WithDrawJsonDto withDrawJson) throws Exception;
	
	public   void delete(TblWithdrawInf tblWithdrawInf);

}
