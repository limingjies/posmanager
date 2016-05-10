package com.allinfinance.bo.impl.agentpay;

import java.util.List;

import com.allinfinance.bo.agentpay.T90201BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.agentpay.TblAreaInfoDAO;
import com.allinfinance.po.agentpay.TblAreaInfo;
import com.allinfinance.po.agentpay.TblAreaInfoPK;

public class T90201BOTarget implements T90201BO {
	
	private TblAreaInfoDAO tblAreaInfoDAO;
	
	@Override
	public TblAreaInfo getAreaInfo(TblAreaInfoPK tblAreaInfoPK) {
		// TODO Auto-generated method stub
		return this.tblAreaInfoDAO.get(tblAreaInfoPK);
	}

	@Override
	public String add(TblAreaInfo tblAreaInfo) {
		// TODO Auto-generated method stub
		tblAreaInfoDAO.save(tblAreaInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(List<TblAreaInfo> tblAreaInfoList) {
		for(TblAreaInfo tblAreaInfo: tblAreaInfoList){
			tblAreaInfoDAO.update(tblAreaInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblAreaInfo tblAreaInfo) {
		tblAreaInfoDAO.update(tblAreaInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblAreaInfo tblAreaInfo) {
		tblAreaInfoDAO.delete(tblAreaInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblAreaInfoPK id) {
		tblAreaInfoDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblAreaInfoDAO getTblAreaInfoDAO() {
		return tblAreaInfoDAO;
	}

	public void setTblAreaInfoDAO(TblAreaInfoDAO tblAreaInfoDAO) {
		this.tblAreaInfoDAO = tblAreaInfoDAO;
	}
	
}
