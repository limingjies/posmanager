package com.allinfinance.bo.impl.agentpay;

import java.util.List;

import com.allinfinance.bo.agentpay.T90203BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.agentpay.TblBusCodeInfoDAO;
import com.allinfinance.po.agentpay.TblBusCodeInfo;

public class T90203BOTarget implements T90203BO {
	
	private TblBusCodeInfoDAO tblBusCodeInfoDAO;
	
	@Override
	public TblBusCodeInfo getBusCodeInfo(String busCode) {
		// TODO Auto-generated method stub
		return this.tblBusCodeInfoDAO.get(busCode);
	}

	@Override
	public String add(TblBusCodeInfo tblBusCodeInfo){
		// TODO Auto-generated method stub
		tblBusCodeInfoDAO.save(tblBusCodeInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(List<TblBusCodeInfo> tblBusCodeInfoList) {
		for(TblBusCodeInfo tblBusCodeInfo: tblBusCodeInfoList){
			tblBusCodeInfoDAO.update(tblBusCodeInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblBusCodeInfo tblBusCodeInfo) {
		tblBusCodeInfoDAO.update(tblBusCodeInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblBusCodeInfo tblBusCodeInfo) {
		tblBusCodeInfoDAO.delete(tblBusCodeInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String busCode) {
		tblBusCodeInfoDAO.delete(busCode);
		return Constants.SUCCESS_CODE;
	}

	public TblBusCodeInfoDAO getTblBusCodeInfoDAO() {
		return tblBusCodeInfoDAO;
	}

	public void setTblBusCodeInfoDAO(TblBusCodeInfoDAO tblBusCodeInfoDAO) {
		this.tblBusCodeInfoDAO = tblBusCodeInfoDAO;
	}
	
}
