package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.po.SettleDocInfo;

public interface T10102BO {

	public String add(SettleDocInfo settleDocInfo);
	public String update(List<SettleDocInfo> settleDocInfoList);
	public String delete(String key);
}
