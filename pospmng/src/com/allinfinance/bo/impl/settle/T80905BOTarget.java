package com.allinfinance.bo.impl.settle;

import com.allinfinance.bo.settle.T80905BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.settle.TblFirstBrhDestIdDAO;
import com.allinfinance.po.settle.TblFirstBrhDestId;

public class T80905BOTarget implements T80905BO {
	private TblFirstBrhDestIdDAO tblFirstBrhDestIdDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80905BO#get(java.lang.String)
	 */
	@Override
	public TblFirstBrhDestId get(String id) {
		// TODO Auto-generated method stub
		return tblFirstBrhDestIdDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80905BO#add(com.allinfinance.po.settle.TblFirstBrhDestId)
	 */
	@Override
	public String add(TblFirstBrhDestId tblFirstBrhDestId) {
		// TODO Auto-generated method stub
		tblFirstBrhDestIdDAO.save(tblFirstBrhDestId);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80905BO#update(com.allinfinance.po.settle.TblFirstBrhDestId)
	 */
	@Override
	public String update(TblFirstBrhDestId tblFirstBrhDestId) {
		// TODO Auto-generated method stub
		tblFirstBrhDestIdDAO.update(tblFirstBrhDestId);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80905BO#delete(java.lang.String)
	 */
	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		tblFirstBrhDestIdDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblFirstBrhDestIdDAO
	 */
	public TblFirstBrhDestIdDAO getTblFirstBrhDestIdDAO() {
		return tblFirstBrhDestIdDAO;
	}

	/**
	 * @param tblFirstBrhDestIdDAO the tblFirstBrhDestIdDAO to set
	 */
	public void setTblFirstBrhDestIdDAO(TblFirstBrhDestIdDAO tblFirstBrhDestIdDAO) {
		this.tblFirstBrhDestIdDAO = tblFirstBrhDestIdDAO;
	}

}