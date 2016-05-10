package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblInstBalanceAjust;
import com.allinfinance.po.settle.TblInstBalanceAjustPK;

public interface TblInstBalanceAjustDAO {
	
	public TblInstBalanceAjust get(TblInstBalanceAjustPK pk);

	public void save(TblInstBalanceAjust tblInstBalanceAjust);

	public void update(TblInstBalanceAjust tblInstBalanceAjust);

	public void delete(TblInstBalanceAjust tblInstBalanceAjust);

}
