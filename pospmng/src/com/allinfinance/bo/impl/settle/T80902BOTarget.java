package com.allinfinance.bo.impl.settle;

import com.allinfinance.bo.settle.T80902BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblChannelCnapsMapDAO;
import com.allinfinance.po.settle.TblChannelCnapsMap;

public class T80902BOTarget implements T80902BO {
	private TblChannelCnapsMapDAO tblChannelCnapsMapDAO;
	private ICommQueryDAO commQueryDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80902BO#get(java.lang.String)
	 */
	@Override
	public TblChannelCnapsMap get(String id) {
		// TODO Auto-generated method stub
		return tblChannelCnapsMapDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80902BO#add(com.allinfinance.po.settle.TblChannelCnapsMap)
	 */
	@Override
	public String add(TblChannelCnapsMap tblChannelCnapsMap) {
		// TODO Auto-generated method stub
		tblChannelCnapsMapDAO.save(tblChannelCnapsMap);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80902BO#update(com.allinfinance.po.settle.TblChannelCnapsMap)
	 */
	@Override
	public String update(TblChannelCnapsMap tblChannelCnapsMap) {
		// TODO Auto-generated method stub
		tblChannelCnapsMapDAO.update(tblChannelCnapsMap);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80902BO#delete(java.lang.String)
	 */
	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		tblChannelCnapsMapDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80902BO#batchDel(java.lang.String)
	 */
	@Override
	public String batchDel(String id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM TBL_CHANNEL_CNAPS_MAP WHERE CHANNEL_ID = '" + id + "'";
		commQueryDAO.excute(sql);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblChannelCnapsMapDAO
	 */
	public TblChannelCnapsMapDAO getTblChannelCnapsMapDAO() {
		return tblChannelCnapsMapDAO;
	}

	/**
	 * @param tblChannelCnapsMapDAO the tblChannelCnapsMapDAO to set
	 */
	public void setTblChannelCnapsMapDAO(TblChannelCnapsMapDAO tblChannelCnapsMapDAO) {
		this.tblChannelCnapsMapDAO = tblChannelCnapsMapDAO;
	}

	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

}