package com.allinfinance.bo.impl.agentpay;

import java.util.List;

import com.allinfinance.bo.agentpay.T90204BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.agentpay.TblParamInfoDAO;
import com.allinfinance.po.agentpay.TblParamInfo;
import com.allinfinance.po.agentpay.TblParamInfoPK;

public class T90204BOTarget implements T90204BO {
	
	private TblParamInfoDAO tblParamInfoDAO;
	
	@Override
	public TblParamInfo get(TblParamInfoPK id) {
		// TODO Auto-generated method stub
		return this.tblParamInfoDAO.get(id);
	}

	@Override
	public String add(TblParamInfo tblParamInfo){
		// TODO Auto-generated method stub
		tblParamInfoDAO.save(tblParamInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(List<TblParamInfo> tblParamInfoList) {
		for(TblParamInfo tblParamInfo: tblParamInfoList){
			tblParamInfoDAO.update(tblParamInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblParamInfo tblParamInfo) {
		tblParamInfoDAO.update(tblParamInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblParamInfo tblParamInfo) {
		tblParamInfoDAO.delete(tblParamInfo);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblParamInfoPK id) {
		tblParamInfoDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblParamInfoDAO getTblParamInfoDAO() {
		return tblParamInfoDAO;
	}
	
	public void setTblParamInfoDAO(TblParamInfoDAO tblParamInfoDAO) {
		this.tblParamInfoDAO = tblParamInfoDAO;
	}

}
