package com.allinfinance.dao.iface.route;

import com.allinfinance.po.route.TblUpbrhFeeDetail;
import com.allinfinance.po.route.TblUpbrhFeeDetailPk;

public interface TblUpbrhFeeDetailDAO {
	public TblUpbrhFeeDetail get(String key);

	public TblUpbrhFeeDetail load(TblUpbrhFeeDetailPk key);

	public java.util.List<TblUpbrhFeeDetail> findAll();


	public void save(TblUpbrhFeeDetail TblUpbrhFeeDetail);

	public void saveOrUpdate(TblUpbrhFeeDetail TblUpbrhFeeDetail);


	public void update(TblUpbrhFeeDetail TblUpbrhFeeDetail);

	public void delete(TblUpbrhFeeDetailPk id);


	public void delete(TblUpbrhFeeDetail tblCityCode);

}