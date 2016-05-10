package com.allinfinance.bo.impl.risk;

import com.allinfinance.bo.risk.T40215BO;
import com.allinfinance.dao.iface.risk.TblMchtFreezeDAO;
import com.allinfinance.po.risk.TblMchtFreeze;

public class T40215BOTarget implements T40215BO {
	private TblMchtFreezeDAO tblMchtFreezeDAO;

	@Override
	public TblMchtFreeze get(String key) {
		// TODO Auto-generated method stub
		return tblMchtFreezeDAO.get(key);
	}

	@Override
	public void save(com.allinfinance.po.risk.TblMchtFreeze tblMchtFreeze) {
		// TODO Auto-generated method stub
		tblMchtFreezeDAO.save(tblMchtFreeze);
	}

	public TblMchtFreezeDAO getTblMchtFreezeDAO() {
		return tblMchtFreezeDAO;
	}

	public void setTblMchtFreezeDAO(TblMchtFreezeDAO tblMchtFreezeDAO) {
		this.tblMchtFreezeDAO = tblMchtFreezeDAO;
	}

	@Override
	public void update(TblMchtFreeze tblMchtFreeze) {
		// TODO Auto-generated method stub
		tblMchtFreezeDAO.update(tblMchtFreeze);
	}

}
