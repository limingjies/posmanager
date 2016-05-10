package com.allinfinance.bo.route;

import java.util.List;
import com.allinfinance.po.route.TblUpbrhFee;

public interface TblUpbrhFeeBO {

	void add(TblUpbrhFee tblUpbrhFee);

	void update(TblUpbrhFee tblUpbrhFee);

	public String delete(TblUpbrhFee tblUpbrhFee);
	
	public TblUpbrhFee get(String key);
	
	public String findConnect(TblUpbrhFee tblUpbrhFee);

	public List findUpbrhFeeByBrhId(String brhId);

}
