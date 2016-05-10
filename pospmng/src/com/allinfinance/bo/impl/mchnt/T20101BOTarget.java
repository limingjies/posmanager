package com.allinfinance.bo.impl.mchnt;

import java.util.List;

import com.allinfinance.bo.mchnt.T20101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.mchnt.TblDivMchntTmpDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntInfoTmpDAO;
import com.allinfinance.po.TblDivMchntTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;

/**
 * Title:商户BO实现
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T20101BOTarget implements T20101BO {
	
	
	private TblMchntInfoTmpDAO tblMchntInfoTmpDAO;
	
	private ICommQueryDAO commQueryDAO;
	
	private TblDivMchntTmpDAO tblDivMchntTmpDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20101BO#add()
	 */
	public String add(TblMchtBaseInfTmp tblMchntInfoTmp, TblMchtSettleInfTmp tblMchtSettleInfTmp) {
//		if(tblMchntInfoTmp.getFeePercent() == null) {
//			tblMchntInfoTmp.setFeePercent("");
//		}
//		if(tblMchntInfoTmp.getFeeLimit() == null) {
//			tblMchntInfoTmp.setFeeLimit("");
//		} else {
//			tblMchntInfoTmp.setFeeLimit(CommonFunction.transYuanToFen(tblMchntInfoTmp.getFeeLimit()));
//			if(tblMchntInfoTmp.getFeeLimit().trim().length()> 10) {
//				return "商户手续费封顶金额输入内容超出最大限制";
//			}
//		}
//		if(tblMchntInfoTmp.getFeeAmt() == null) {
//			tblMchntInfoTmp.setFeeAmt("");
//		} else {
//			tblMchntInfoTmp.setFeeAmt(CommonFunction.transYuanToFen(tblMchntInfoTmp.getFeeAmt()));
//			if(tblMchntInfoTmp.getFeeAmt().trim().length() > 10) {
//				return "商户手续费固定金额输入内容超出最大限制";
//			}
//		}

		tblMchntInfoTmpDAO.save(tblMchntInfoTmp);
		
		
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20101BO#get(java.lang.String)
	 */
	public TblMchtBaseInfTmp get(String mchntId) {
		return tblMchntInfoTmpDAO.get(mchntId);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20101BO#loadDiv(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TblDivMchntTmp> loadDiv(String mchntId) {
		String hql = "from com.allinfinance.po.TblDivMchntTmp t where t.Id.MchtId = '" + mchntId + "'";
		return commQueryDAO.findByHQLQuery(hql);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20101BO#update(com.allinfinance.po.TblMchntInfoTmp)
	 */
	public String update(TblMchtBaseInfTmp tblMchntInfoTmp) {
		tblMchntInfoTmpDAO.update(tblMchntInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblMchntInfoTmpDAO
	 */
	public TblMchntInfoTmpDAO getTblMchntInfoTmpDAO() {
		return tblMchntInfoTmpDAO;
	}

	/**
	 * @param tblMchntInfoTmpDAO the tblMchntInfoTmpDAO to set
	 */
	public void setTblMchntInfoTmpDAO(TblMchntInfoTmpDAO tblMchntInfoTmpDAO) {
		this.tblMchntInfoTmpDAO = tblMchntInfoTmpDAO;
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

	/**
	 * @return the tblDivMchntTmpDAO
	 */
	public TblDivMchntTmpDAO getTblDivMchntTmpDAO() {
		return tblDivMchntTmpDAO;
	}

	/**
	 * @param tblDivMchntTmpDAO the tblDivMchntTmpDAO to set
	 */
	public void setTblDivMchntTmpDAO(TblDivMchntTmpDAO tblDivMchntTmpDAO) {
		this.tblDivMchntTmpDAO = tblDivMchntTmpDAO;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20101BO#update(com.allinfinance.po.TblMchntInfoTmp, java.util.List, java.util.List)
	 */
	public String update(TblMchtBaseInfTmp tblMchntInfoTmp,
			List<TblDivMchntTmp> tblDivMchntTmpList,
			List<TblDivMchntTmp> tblDivMchntTmpListDelete) {
//		if(tblMchntInfoTmp.getFeePercent() == null) {
//			tblMchntInfoTmp.setFeePercent("");
//		}
//		if(tblMchntInfoTmp.getFeeLimit() == null) {
//			tblMchntInfoTmp.setFeeLimit("");
//		} else {
//			tblMchntInfoTmp.setFeeLimit(CommonFunction.transYuanToFen(tblMchntInfoTmp.getFeeLimit()));
//			if(tblMchntInfoTmp.getFeeLimit().trim().length()> 10) {
//				return "商户手续费封顶金额输入内容超出最大限制";
//			}
//		}
//		if(tblMchntInfoTmp.getFeeAmt() == null) {
//			tblMchntInfoTmp.setFeeAmt("");
//		} else {
//			tblMchntInfoTmp.setFeeAmt(CommonFunction.transYuanToFen(tblMchntInfoTmp.getFeeAmt()));
//			if(tblMchntInfoTmp.getFeeAmt().trim().length() > 10) {
//				return "商户手续费固定金额输入内容超出最大限制";
//			}
//		}
		for(TblDivMchntTmp tblDivMchntTmp : tblDivMchntTmpListDelete) {
			tblDivMchntTmpDAO.delete(tblDivMchntTmp);
		}
		
		for(TblDivMchntTmp tblDivMchntTmp : tblDivMchntTmpList) {
			tblDivMchntTmpDAO.save(tblDivMchntTmp);
		}
		tblMchntInfoTmpDAO.update(tblMchntInfoTmp);
		return Constants.SUCCESS_CODE;
	}
	
	
}
