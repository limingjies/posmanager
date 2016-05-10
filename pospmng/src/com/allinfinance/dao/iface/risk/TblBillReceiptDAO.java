package com.allinfinance.dao.iface.risk;


import com.allinfinance.po.risk.TblBillReceipt;

public interface TblBillReceiptDAO {

	
	public TblBillReceipt get(String key);
	public void delete(String key);
	public void update(TblBillReceipt tblBillReceipt);
	public void save(TblBillReceipt tblBillReceipt);
}
