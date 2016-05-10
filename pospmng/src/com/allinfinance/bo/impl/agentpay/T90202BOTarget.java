package com.allinfinance.bo.impl.agentpay;

import java.util.List;

import com.allinfinance.bo.agentpay.T90202BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.agentpay.TblBankInfoDAO;
import com.allinfinance.po.agentpay.TblBankInfo;

public class T90202BOTarget implements T90202BO {
	
	private TblBankInfoDAO tblBankInfoDAO;
	
	@Override
	public TblBankInfo getBankInfo(String bankNo) {
		// TODO Auto-generated method stub
		return this.tblBankInfoDAO.get(bankNo);
	}

	@Override
	public String add(TblBankInfo tblBankInfo){
		// TODO Auto-generated method stub
		tblBankInfoDAO.save(tblBankInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(List<TblBankInfo> tblBankInfoList) {
		for(TblBankInfo tblBankInfo: tblBankInfoList){
			tblBankInfoDAO.update(tblBankInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblBankInfo tblBankInfo) {
		tblBankInfoDAO.update(tblBankInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblBankInfo tblBankInfo) {
		tblBankInfoDAO.delete(tblBankInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String bankNo) {
		tblBankInfoDAO.delete(bankNo);
		return Constants.SUCCESS_CODE;
	}

	public TblBankInfoDAO getTblBankInfoDAO() {
		return tblBankInfoDAO;
	}

	public void setTblBankInfoDAO(TblBankInfoDAO tblBankInfoDAO) {
		this.tblBankInfoDAO = tblBankInfoDAO;
	}
	
}
