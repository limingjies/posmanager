package com.allinfinance.bo.impl.mchnt;

import com.allinfinance.bo.mchnt.T20804BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblHolderCardIdDAO;
import com.allinfinance.po.mchnt.TblHolderCardId;
import com.allinfinance.po.mchnt.TblHolderCardIdPK;

public class T20804BOTarget implements T20804BO{
	
	private TblHolderCardIdDAO tblHolderCardIdDAO;

	public String delete(TblHolderCardIdPK id) {
		// TODO Auto-generated method stub
		tblHolderCardIdDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblHolderCardId get(TblHolderCardIdPK id) {
		// TODO Auto-generated method stub
		return tblHolderCardIdDAO.get(id);
	}

	public String save(TblHolderCardId tblHolderCardId) {
		// TODO Auto-generated method stub
		tblHolderCardIdDAO.save(tblHolderCardId);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblHolderCardId tblHolderCardId) {
		// TODO Auto-generated method stub
		tblHolderCardIdDAO.update(tblHolderCardId);
		return Constants.SUCCESS_CODE;
	}

	public TblHolderCardIdDAO getTblHolderCardIdDAO() {
		return tblHolderCardIdDAO;
	}

	public void setTblHolderCardIdDAO(TblHolderCardIdDAO tblHolderCardIdDAO) {
		this.tblHolderCardIdDAO = tblHolderCardIdDAO;
	}

	
}
