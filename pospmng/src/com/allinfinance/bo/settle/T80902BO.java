package com.allinfinance.bo.settle;

import com.allinfinance.po.settle.TblChannelCnapsMap;

public interface T80902BO {
	
	public TblChannelCnapsMap get(String id);

	public String add(TblChannelCnapsMap tblChannelCnapsMap);

	public String update(TblChannelCnapsMap tblChannelCnapsMap);

	public String delete(String id);

	public String batchDel(String id);
	
}