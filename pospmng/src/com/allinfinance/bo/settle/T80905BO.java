package com.allinfinance.bo.settle;

import com.allinfinance.po.settle.TblFirstBrhDestId;

public interface T80905BO {
	
	public TblFirstBrhDestId get(String id);

	public String add(TblFirstBrhDestId tblFirstBrhDestId);

	public String update(TblFirstBrhDestId tblFirstBrhDestId);

	public String delete(String id);
	
}