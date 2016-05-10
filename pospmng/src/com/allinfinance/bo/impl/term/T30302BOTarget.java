package com.allinfinance.bo.impl.term;

import com.allinfinance.bo.term.T30302BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermManagementAppMainDAO;
import com.allinfinance.dao.iface.term.TblTermManagementCheckDAO;
import com.allinfinance.po.TblTermManagementAppMain;
import com.allinfinance.po.TblTermManagementCheck;
import com.allinfinance.po.TblTermManagementCheckPK;

public class T30302BOTarget implements T30302BO{

	private TblTermManagementCheckDAO tblTermManagementCheckDAO;
	private TblTermManagementAppMainDAO tblTermManagementAppMainDAO;
	
	
	public String delete(TblTermManagementCheckPK id) {
		// TODO Auto-generated method stub
		tblTermManagementCheckDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblTermManagementCheck get(TblTermManagementCheckPK key) {
		// TODO Auto-generated method stub
		return tblTermManagementCheckDAO.get(key);
	}

	public String save(TblTermManagementCheck tblTermManagementCheck) {
		// TODO Auto-generated method stub
		tblTermManagementCheckDAO.save(tblTermManagementCheck);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblTermManagementCheck tblTermManagementCheck) {
		// TODO Auto-generated method stub
		tblTermManagementCheckDAO.update(tblTermManagementCheck);
		return Constants.SUCCESS_CODE;
	}

	public TblTermManagementCheckDAO getTblTermManagementCheckDAO() {
		return tblTermManagementCheckDAO;
	}

	public void setTblTermManagementCheckDAO(
			TblTermManagementCheckDAO tblTermManagementCheckDAO) {
		this.tblTermManagementCheckDAO = tblTermManagementCheckDAO;
	}

	
	public String delete(String id) {
		// TODO Auto-generated method stub
		tblTermManagementAppMainDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblTermManagementAppMain get(String key) {
		// TODO Auto-generated method stub
		return tblTermManagementAppMainDAO.get(key);
	}

	public String save(TblTermManagementAppMain tblTermManagementAppMain) {
		// TODO Auto-generated method stub
		tblTermManagementAppMainDAO.save(tblTermManagementAppMain);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblTermManagementAppMain tblTermManagementAppMain) {
		// TODO Auto-generated method stub
		tblTermManagementAppMainDAO.update(tblTermManagementAppMain);
		return Constants.SUCCESS_CODE;
	}

	public TblTermManagementAppMainDAO getTblTermManagementAppMainDAO() {
		return tblTermManagementAppMainDAO;
	}

	public void setTblTermManagementAppMainDAO(
			TblTermManagementAppMainDAO tblTermManagementAppMainDAO) {
		this.tblTermManagementAppMainDAO = tblTermManagementAppMainDAO;
	}

	
}
