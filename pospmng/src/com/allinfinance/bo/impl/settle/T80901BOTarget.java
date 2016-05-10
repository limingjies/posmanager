package com.allinfinance.bo.impl.settle;

import com.allinfinance.bo.settle.T80901BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.settle.TblPayChannelInfoDAO;
import com.allinfinance.po.settle.TblPayChannelInfo;

public class T80901BOTarget implements T80901BO {
	private TblPayChannelInfoDAO tblPayChannelInfoDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80901BO#get(java.lang.String)
	 */
	@Override
	public TblPayChannelInfo get(String id) {
		// TODO Auto-generated method stub
		return tblPayChannelInfoDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80901BO#add(com.allinfinance.po.settle.TblPayChannelInfo)
	 */
	@Override
	public String add(TblPayChannelInfo tblPayChannelInfo) {
		// TODO Auto-generated method stub
		tblPayChannelInfoDAO.save(tblPayChannelInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80901BO#update(com.allinfinance.po.settle.TblPayChannelInfo)
	 */
	@Override
	public String update(TblPayChannelInfo tblPayChannelInfo) {
		// TODO Auto-generated method stub
		tblPayChannelInfoDAO.update(tblPayChannelInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80901BO#delete(java.lang.String)
	 */
	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		tblPayChannelInfoDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblPayChannelInfoDAO
	 */
	public TblPayChannelInfoDAO getTblPayChannelInfoDAO() {
		return tblPayChannelInfoDAO;
	}

	/**
	 * @param tblPayChannelInfoDAO the tblPayChannelInfoDAO to set
	 */
	public void setTblPayChannelInfoDAO(TblPayChannelInfoDAO tblPayChannelInfoDAO) {
		this.tblPayChannelInfoDAO = tblPayChannelInfoDAO;
	}

}