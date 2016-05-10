package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.mchnt.CstMchtFeeInf;
import com.allinfinance.po.mchnt.CstMchtFeeInfPK;

public interface CstMchtFeeInfDAO {
	public Class<CstMchtFeeInf> getReferenceClass () ;
	public CstMchtFeeInf cast (Object object);
	public CstMchtFeeInf load(CstMchtFeeInfPK key);
	public CstMchtFeeInf get(CstMchtFeeInfPK key);
	public CstMchtFeeInfPK save(CstMchtFeeInf cstMchtFeeInf);
	public void saveOrUpdate(CstMchtFeeInf cstMchtFeeInf);
	public void update(CstMchtFeeInf cstMchtFeeInf);
	public void delete(CstMchtFeeInf cstMchtFeeInf);
	public void delete(CstMchtFeeInfPK id);
}