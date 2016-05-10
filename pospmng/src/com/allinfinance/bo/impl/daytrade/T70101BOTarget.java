package com.allinfinance.bo.impl.daytrade;

import java.util.List;

import com.allinfinance.bo.daytrade.T70101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.daytrade.MbWithdrawFeeDAO;
import com.allinfinance.po.daytrade.MbWithdrawFee;

public class T70101BOTarget{
	
//	private ICommQueryDAO commQuery_frontDAO;
//	private MbWithdrawFeeDAO mbWithdrawFeeDAO;
//	@Override
//	public MbWithdrawFee get(Long id) {
//		return mbWithdrawFeeDAO.get(id);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.allinfinance.bo.daytrade.T70101BO#getDataList(java.lang.String)
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<MbWithdrawFee> getDataList(String merchantId) {
//		String hql=" from com.allinfinance.po.daytrade.MbWithdrawFee t "
//				+ "where t.merchantId = '" + merchantId + "' "
//				+ "and t.status = '0' ";
//		List<MbWithdrawFee> dataList = commQuery_frontDAO.findByHQLQuery(hql);
//		return dataList;
//	}
//
//	@Override
//	public String add(MbWithdrawFee mbWithdrawFee) {
//		mbWithdrawFeeDAO.save(mbWithdrawFee);
//		return Constants.SUCCESS_CODE;
//	}
//
//	@Override
//	public String update(MbWithdrawFee mbWithdrawFee) {
//		mbWithdrawFeeDAO.update(mbWithdrawFee);
//		return Constants.SUCCESS_CODE;
//	}
//
//	@Override
//	public String delete(Long id) {
//		mbWithdrawFeeDAO.delete(id);
//		return Constants.SUCCESS_CODE;
//	}
//
//	/**
//	 * @return the mbWithdrawFeeDAO
//	 */
//	public MbWithdrawFeeDAO getMbWithdrawFeeDAO() {
//		return mbWithdrawFeeDAO;
//	}
//
//	/**
//	 * @param mbWithdrawFeeDAO the mbWithdrawFeeDAO to set
//	 */
//	public void setMbWithdrawFeeDAO(MbWithdrawFeeDAO mbWithdrawFeeDAO) {
//		this.mbWithdrawFeeDAO = mbWithdrawFeeDAO;
//	}
//
//	/**
//	 * @return the commQuery_frontDAO
//	 */
//	public ICommQueryDAO getCommQuery_frontDAO() {
//		return commQuery_frontDAO;
//	}
//
//	/**
//	 * @param commQuery_frontDAO the commQuery_frontDAO to set
//	 */
//	public void setCommQuery_frontDAO(ICommQueryDAO commQuery_frontDAO) {
//		this.commQuery_frontDAO = commQuery_frontDAO;
//	}
	

}