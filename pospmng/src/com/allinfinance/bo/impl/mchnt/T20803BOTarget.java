package com.allinfinance.bo.impl.mchnt;

import com.allinfinance.bo.mchnt.T20803BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblHolderDrawActDAO;
import com.allinfinance.po.TblHolderDrawAct;

public class T20803BOTarget implements T20803BO{

	private TblHolderDrawActDAO tblHolderDrawActDAO;
	
	public String add(TblHolderDrawAct tblHolderDrawAct) {
		// TODO Auto-generated method stub
		tblHolderDrawActDAO.save(tblHolderDrawAct);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String actNo) {
		// TODO Auto-generated method stub
		tblHolderDrawActDAO.delete(actNo);
		return Constants.SUCCESS_CODE;
	}

	public TblHolderDrawAct get(String actNo) {
		// TODO Auto-generated method stub
		return tblHolderDrawActDAO.get(actNo);
	}

	public String update(TblHolderDrawAct tblHolderDrawAct) {
		// TODO Auto-generated method stub
		tblHolderDrawActDAO.update(tblHolderDrawAct);
		return Constants.SUCCESS_CODE;
	}

	public TblHolderDrawActDAO getTblHolderDrawActDAO() {
		return tblHolderDrawActDAO;
	}

	public void setTblHolderDrawActDAO(TblHolderDrawActDAO tblHolderDrawActDAO) {
		this.tblHolderDrawActDAO = tblHolderDrawActDAO;
	}

	
}
