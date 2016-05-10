package com.allinfinance.bo.impl.base;

import java.util.List;

import com.allinfinance.bo.base.T10102BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.base.SettleDocInfoDAO;
import com.allinfinance.po.SettleDocInfo;

public class T10102BOTarget implements T10102BO {
	
	private SettleDocInfoDAO settleDocInfoDAO;

	public SettleDocInfoDAO getSettleDocInfoDAO() {
		return settleDocInfoDAO;
	}

	public void setSettleDocInfoDAO(SettleDocInfoDAO settleDocInfoDAO) {
		this.settleDocInfoDAO = settleDocInfoDAO;
	}

	public String add(SettleDocInfo settleDocInfo) {
		
		if(settleDocInfoDAO.get(settleDocInfo.getId()) != null) {
			return "该机构的清算凭证信息已存在。";
		}
		settleDocInfoDAO.save(settleDocInfo);
		return com.allinfinance.common.Constants.SUCCESS_CODE;
	}

	public String delete(String key) {
		settleDocInfoDAO.delete(key);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<SettleDocInfo> settleDocInfoList) {
		for(SettleDocInfo settleDocInfo : settleDocInfoList) {
			settleDocInfoDAO.update(settleDocInfo);
		}
		return Constants.SUCCESS_CODE;
	}	
}
