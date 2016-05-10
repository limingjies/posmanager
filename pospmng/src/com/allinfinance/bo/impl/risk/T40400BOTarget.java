package com.allinfinance.bo.impl.risk;

import com.allinfinance.bo.risk.T40400BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblGreyMchtSortDAO;
import com.allinfinance.dao.iface.risk.TblRiskGreyMchtDAO;
import com.allinfinance.po.risk.TblGreyMchtSort;
import com.allinfinance.po.risk.TblRiskGreyMcht;
import com.allinfinance.po.risk.TblRiskGreyMchtPK;

/**
 * 
 * Title: 商户灰名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-01
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * author: 徐鹏飞
 *  
 * time: 2014年12月4日下午4:53:20
 * 
 * version: 1.0
 */
public class T40400BOTarget implements T40400BO {
	private TblGreyMchtSortDAO tblGreyMchtSortDAO;
	private TblRiskGreyMchtDAO tblRiskGreyMchtDAO;
	private ICommQueryDAO commQueryDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#query(java.lang.String)
	 */
	@Override
	public boolean query(String key) throws Exception {
		// TODO Auto-generated method stub
		boolean queryFlag = false;
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_GREY_MCHT WHERE SORT_NO = '" + key + "'";
		if(!"0".equals(commQueryDAO.findCountBySQLQuery(countSql)))
			queryFlag = true;
		return queryFlag;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#getSort(java.lang.String)
	 */
	@Override
	public TblGreyMchtSort getSort(String key) throws Exception {
		// TODO Auto-generated method stub
		return tblGreyMchtSortDAO.get(key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#addSort(com.allinfinance.po.risk.TblGreyMchtSort)
	 */
	@Override
	public String addSort(TblGreyMchtSort tblGreyMchtSort) throws Exception {
		// TODO Auto-generated method stub
		tblGreyMchtSortDAO.save(tblGreyMchtSort);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#updateSort(com.allinfinance.po.risk.TblGreyMchtSort)
	 */
	@Override
	public String updateSort(TblGreyMchtSort tblGreyMchtSort) throws Exception {
		// TODO Auto-generated method stub
		tblGreyMchtSortDAO.update(tblGreyMchtSort);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#deleteSort(java.lang.String)
	 */
	@Override
	public String deleteSort(String key) throws Exception {
		// TODO Auto-generated method stub
		tblGreyMchtSortDAO.delete(key);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#get(com.allinfinance.po.risk.TblRiskGreyMchtPK)
	 */
	@Override
	public TblRiskGreyMcht get(TblRiskGreyMchtPK key) throws Exception {
		// TODO Auto-generated method stub
		return tblRiskGreyMchtDAO.get(key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#add(com.allinfinance.po.risk.TblRiskGreyMcht)
	 */
	@Override
	public String add(TblRiskGreyMcht tblRiskGreyMcht) throws Exception {
		// TODO Auto-generated method stub
		tblRiskGreyMchtDAO.save(tblRiskGreyMcht);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#update(com.allinfinance.po.risk.TblRiskGreyMcht)
	 */
	@Override
	public String update(TblRiskGreyMcht tblRiskGreyMcht) throws Exception {
		// TODO Auto-generated method stub
		tblRiskGreyMchtDAO.update(tblRiskGreyMcht);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40400BO#delete(com.allinfinance.po.risk.TblRiskGreyMchtPK)
	 */
	@Override
	public String delete(TblRiskGreyMchtPK key) throws Exception {
		// TODO Auto-generated method stub
		tblRiskGreyMchtDAO.delete(key);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblGreyMchtSortDAO
	 */
	public TblGreyMchtSortDAO getTblGreyMchtSortDAO() {
		return tblGreyMchtSortDAO;
	}

	/**
	 * @param tblGreyMchtSortDAO the tblGreyMchtSortDAO to set
	 */
	public void setTblGreyMchtSortDAO(TblGreyMchtSortDAO tblGreyMchtSortDAO) {
		this.tblGreyMchtSortDAO = tblGreyMchtSortDAO;
	}

	/**
	 * @return the tblRiskGreyMchtDAO
	 */
	public TblRiskGreyMchtDAO getTblRiskGreyMchtDAO() {
		return tblRiskGreyMchtDAO;
	}

	/**
	 * @param tblRiskGreyMchtDAO the tblRiskGreyMchtDAO to set
	 */
	public void setTblRiskGreyMchtDAO(TblRiskGreyMchtDAO tblRiskGreyMchtDAO) {
		this.tblRiskGreyMchtDAO = tblRiskGreyMchtDAO;
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