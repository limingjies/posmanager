package com.allinfinance.bo.impl.agentpay;

import java.util.List;

import com.allinfinance.bo.agentpay.T90401BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.agentpay.TblMchtFundDAO;
import com.allinfinance.po.agentpay.TblMchtFund;

public class T90401BOTarget implements T90401BO {
	
	private TblMchtFundDAO tblMchtFundDAO;
	
	@Override
	public TblMchtFund getMchtFund(String mcht) {
		// TODO Auto-generated method stub
		return this.tblMchtFundDAO.get(mcht);
	}

	@Override
	public String add(TblMchtFund tblMchtFund){
		// TODO Auto-generated method stub
		tblMchtFundDAO.save(tblMchtFund);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(List<TblMchtFund> tblMchtFundList) {
		for(TblMchtFund tblMchtFund: tblMchtFundList){
			tblMchtFundDAO.update(tblMchtFund);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblMchtFund tblMchtFund) {
		tblMchtFundDAO.update(tblMchtFund);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblMchtFund tblMchtFund) {
		tblMchtFundDAO.delete(tblMchtFund);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String mcht) {
		tblMchtFundDAO.delete(mcht);
		return Constants.SUCCESS_CODE;
	}

	public TblMchtFundDAO getTblMchtFundDAO() {
		return tblMchtFundDAO;
	}

	public void setTblMchtFundDAO(TblMchtFundDAO tblMchtFundDAO) {
		this.tblMchtFundDAO = tblMchtFundDAO;
	}
	
}
