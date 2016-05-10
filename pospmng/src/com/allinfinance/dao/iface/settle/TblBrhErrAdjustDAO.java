package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblBrhErrAdjust;


public interface TblBrhErrAdjustDAO {
	
	public TblBrhErrAdjust get(String id);

	public void save(TblBrhErrAdjust TblBrhErrAdjust);

	public void update(TblBrhErrAdjust TblBrhErrAdjust);

	public void delete(TblBrhErrAdjust TblBrhErrAdjust);



}