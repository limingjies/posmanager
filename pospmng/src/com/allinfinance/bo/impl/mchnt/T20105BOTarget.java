package com.allinfinance.bo.impl.mchnt;

import com.allinfinance.bo.mchnt.T20105BO;
import com.allinfinance.dao.iface.mchnt.TblMchtCupInfDAO;
import com.allinfinance.po.mchnt.TblMchtCupInf;

public class T20105BOTarget implements T20105BO{

	
	private TblMchtCupInfDAO tblMchtCupInfDAO;
	public TblMchtCupInfDAO getTblMchtCupInfDAO() {
		return tblMchtCupInfDAO;
	}

	public void setTblMchtCupInfDAO(TblMchtCupInfDAO tblMchtCupInfDAO) {
		this.tblMchtCupInfDAO = tblMchtCupInfDAO;
	}

	public void add(TblMchtCupInf tblMchtCupInf) {
		// TODO Auto-generated method stub
		tblMchtCupInfDAO.save(tblMchtCupInf);
	}

	public void delete(String mchntNo) {
		// TODO Auto-generated method stub
		tblMchtCupInfDAO.delete(mchntNo);
	}

	public TblMchtCupInf get(String mchntNo) {
		// TODO Auto-generated method stub
		return tblMchtCupInfDAO.get(mchntNo);
	}

	public void update(TblMchtCupInf tblMchtCupInf) {
		// TODO Auto-generated method stub
		tblMchtCupInfDAO.update(tblMchtCupInf);
	}



}
