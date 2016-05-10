package com.allinfinance.bo.impl.agentpay;

import java.util.List;

import com.allinfinance.bo.agentpay.T90101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.agentpay.TblMchtFileTransInfoDAO;
import com.allinfinance.po.agentpay.TblMchtFileTransInfo;

public class T90101BOTarget implements T90101BO {
	
	private TblMchtFileTransInfoDAO tblMchtFileTransInfoDAO;
	
	@Override
	public TblMchtFileTransInfo getMchtFileTransInfo(String mchtFileTransNo) {
		// TODO Auto-generated method stub
		return this.tblMchtFileTransInfoDAO.get(mchtFileTransNo);
	}

	@Override
	public String add(TblMchtFileTransInfo tblMchtFileTransInfo) {
		// TODO Auto-generated method stub
		tblMchtFileTransInfoDAO.save(tblMchtFileTransInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(List<TblMchtFileTransInfo> tblMchtFileTransInfoList) {
		for(TblMchtFileTransInfo tblMchtFileTransInfo: tblMchtFileTransInfoList){
			tblMchtFileTransInfoDAO.update(tblMchtFileTransInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblMchtFileTransInfo tblMchtFileTransInfo) {
		tblMchtFileTransInfoDAO.update(tblMchtFileTransInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblMchtFileTransInfo tblMchtFileTransInfo) {
		tblMchtFileTransInfoDAO.delete(tblMchtFileTransInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String areaNo) {
		tblMchtFileTransInfoDAO.delete(areaNo);
		return Constants.SUCCESS_CODE;
	}

	public TblMchtFileTransInfoDAO getTblMchtFileTransInfoDAO() {
		return tblMchtFileTransInfoDAO;
	}

	public void setTblMchtFileTransInfoDAO(TblMchtFileTransInfoDAO tblMchtFileTransInfoDAO) {
		this.tblMchtFileTransInfoDAO = tblMchtFileTransInfoDAO;
	}
	
}
