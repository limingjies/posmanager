package com.allinfinance.bo.settle;

import com.allinfinance.po.settle.TblBrhErrAdjust;




public interface T80721BO {
	public TblBrhErrAdjust get(String id);
	public String save(TblBrhErrAdjust tblBrhErrAdjust);
	public String update(TblBrhErrAdjust tblBrhErrAdjust);
	
	public String accept(TblBrhErrAdjust tblBrhErrAdjust);
	public String refuse(TblBrhErrAdjust tblBrhErrAdjust);
}