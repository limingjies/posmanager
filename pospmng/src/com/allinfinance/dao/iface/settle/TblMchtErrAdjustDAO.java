package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblMchtErrAdjust;


public interface TblMchtErrAdjustDAO {
	
	public TblMchtErrAdjust get(String id);

	public void save(TblMchtErrAdjust TblMchtErrAdjust);

	public void update(TblMchtErrAdjust TblMchtErrAdjust);

	public void delete(TblMchtErrAdjust TblMchtErrAdjust);



}