package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.BthGcTxnSuccPK;


public interface BthGcTxnSuccDAO {
	public com.allinfinance.po.BthGcTxnSucc get(BthGcTxnSuccPK bthGcTxnSuccPK);

	public void save(com.allinfinance.po.BthGcTxnSucc bthGcTxnSucc);

	public void update(com.allinfinance.po.BthGcTxnSucc bthGcTxnSucc);

	public void delete(BthGcTxnSuccPK bthGcTxnSuccPK);



}