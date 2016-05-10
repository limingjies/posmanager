package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblBillReceipt;

public class TblBillReceiptDAO extends _RootDAO<TblBillReceipt> implements com.allinfinance.dao.iface.risk.TblBillReceiptDAO{

	protected Class<TblBillReceipt> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblBillReceipt.class;
	}
	public TblBillReceipt cast (Object object) {
		return (TblBillReceipt) object;
	}
	
	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public TblBillReceipt get(String key) {
		// TODO Auto-generated method stub
		return (TblBillReceipt) get(getReferenceClass(), key);
	}

	public TblBillReceipt load(String key) {
		// TODO Auto-generated method stub
		return (TblBillReceipt) load(getReferenceClass(), key);
	}

	public void update(TblBillReceipt tblBillReceipt) {
		super.update(tblBillReceipt);
	}
	
	public void save(TblBillReceipt tblBillReceipt) {
		super.save(tblBillReceipt);
	}
	

}
