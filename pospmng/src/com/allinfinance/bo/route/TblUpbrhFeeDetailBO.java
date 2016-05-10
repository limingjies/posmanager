package com.allinfinance.bo.route;

import java.util.List;
import com.allinfinance.po.route.TblUpbrhFeeDetail;

public interface TblUpbrhFeeDetailBO {

	void add(TblUpbrhFeeDetail tblUpbrhFeeDetail);

	void update(TblUpbrhFeeDetail tblUpbrhFeeDetail);

	public String delete(TblUpbrhFeeDetail tblUpbrhFeeDetail);
	
	public TblUpbrhFeeDetail get(String key);
	public List<TblUpbrhFeeDetail> findAll();

}
