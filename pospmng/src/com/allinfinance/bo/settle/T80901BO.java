package com.allinfinance.bo.settle;

import com.allinfinance.po.settle.TblPayChannelInfo;

public interface T80901BO {
	
	public TblPayChannelInfo get(String id);

	public String add(TblPayChannelInfo tblPayChannelInfo);

	public String update(TblPayChannelInfo tblPayChannelInfo);

	public String delete(String id);
	
}