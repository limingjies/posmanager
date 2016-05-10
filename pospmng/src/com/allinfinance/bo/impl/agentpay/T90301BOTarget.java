package com.allinfinance.bo.impl.agentpay;

import java.util.List;

import com.allinfinance.bo.agentpay.T90301BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.agentpay.TblMchtInfoDAO;
import com.allinfinance.po.agentpay.TblMchtInfo;

public class T90301BOTarget implements T90301BO {
	
	private TblMchtInfoDAO tblMchtInfoDAO;
	
	@Override
	public TblMchtInfo getMchtInfo(String mcht) {
		// TODO Auto-generated method stub
		return this.tblMchtInfoDAO.get(mcht);
	}

	@Override
	public String add(TblMchtInfo tblMchtInfo){
		// TODO Auto-generated method stub
		tblMchtInfoDAO.save(tblMchtInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(List<TblMchtInfo> tblMchtInfoList) {
		for(TblMchtInfo tblMchtInfo: tblMchtInfoList){
			tblMchtInfoDAO.update(tblMchtInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblMchtInfo tblMchtInfo) {
		tblMchtInfoDAO.update(tblMchtInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblMchtInfo tblMchtInfo) {
		tblMchtInfoDAO.delete(tblMchtInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String mcht) {
		tblMchtInfoDAO.delete(mcht);
		return Constants.SUCCESS_CODE;
	}

	public TblMchtInfoDAO getTblMchtInfoDAO() {
		return tblMchtInfoDAO;
	}

	public void setTblMchtInfoDAO(TblMchtInfoDAO tblMchtInfoDAO) {
		this.tblMchtInfoDAO = tblMchtInfoDAO;
	}
	
}
