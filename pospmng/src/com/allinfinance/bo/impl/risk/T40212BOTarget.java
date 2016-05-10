package com.allinfinance.bo.impl.risk;

import java.util.List;

import com.allinfinance.bo.risk.T40212BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.RiskConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblBankCardBlackDAO;
import com.allinfinance.dao.iface.risk.TblBankCardBlackOptLogDAO;
import com.allinfinance.po.risk.TblBankCardBlack;
import com.allinfinance.po.risk.TblBankCardBlackOptLog;
import com.allinfinance.po.risk.TblBankCardBlackOptLogPK;
import com.allinfinance.system.util.CommonFunction;

public class T40212BOTarget implements T40212BO {
	private	 TblBankCardBlackDAO tblBankCardBlackDAO;
	private	 TblBankCardBlackOptLogDAO tblBankCardBlackOptLogDAO;
	private ICommQueryDAO commQueryDAO;
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40212BO#get(java.lang.String)
	 */
	@Override
	public TblBankCardBlack get(String key) {
		// TODO Auto-generated method stub
		return tblBankCardBlackDAO.get(key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40212BO#add(com.allinfinance.po.risk.TblBankCardBlack)
	 */
	@Override
	public String add(TblBankCardBlack tblBankCardBlack) throws Exception {
		// TODO Auto-generated method stub
		List<Object[]> bankBinList = query(tblBankCardBlack.getCardNo());
		if(!bankBinList.isEmpty()){
			Object[] bankBin = bankBinList.get(0);
			tblBankCardBlack.setInsIdCd(bankBin[0].toString().trim());
			tblBankCardBlack.setCardDis(bankBin[1].toString().trim());
			tblBankCardBlack.setCardTp(bankBin[2].toString().trim());
			tblBankCardBlack.setBinNo(bankBin[3].toString().trim());
		}
		TblBankCardBlackOptLog tblBankCardBlackOptLog = new TblBankCardBlackOptLog();
		TblBankCardBlackOptLogPK tblBankCardBlackOptLogPK = new TblBankCardBlackOptLogPK();
		tblBankCardBlackOptLogPK.setCardNo(tblBankCardBlack.getCardNo());
		tblBankCardBlackOptLogPK.setOptTime(tblBankCardBlack.getCrtTime());
		tblBankCardBlackOptLog.setId(tblBankCardBlackOptLogPK);
		tblBankCardBlackOptLog.setOprId(tblBankCardBlack.getCrtOpr());
		tblBankCardBlackOptLog.setOptFlag(RiskConstants.ADD_BANK_CARD_BLACK);
		tblBankCardBlackOptLog.setRemarkInfo(tblBankCardBlack.getRemarkInfo());
		tblBankCardBlackDAO.save(tblBankCardBlack);
		tblBankCardBlackOptLogDAO.save(tblBankCardBlackOptLog);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40212BO#delete(java.lang.String)
	 */
	@Override
	public String delete(String key,Operator operator) throws Exception {
		// TODO Auto-generated method stub
		TblBankCardBlack tblBankCardBlack = this.get(key);
		TblBankCardBlackOptLog tblBankCardBlackOptLog = new TblBankCardBlackOptLog();
		TblBankCardBlackOptLogPK tblBankCardBlackOptLogPK = new TblBankCardBlackOptLogPK();
		tblBankCardBlackOptLogPK.setCardNo(tblBankCardBlack.getCardNo());
		tblBankCardBlackOptLogPK.setOptTime(CommonFunction.getCurrentDateTime());
		tblBankCardBlackOptLog.setId(tblBankCardBlackOptLogPK);
		tblBankCardBlackOptLog.setOprId(operator.getOprId());
		tblBankCardBlackOptLog.setOptFlag(RiskConstants.DEL_BANK_CARD_BLACK);
		tblBankCardBlackOptLog.setRemarkInfo(tblBankCardBlack.getRemarkInfo());
		tblBankCardBlackOptLogDAO.save(tblBankCardBlackOptLog);
		tblBankCardBlackDAO.delete(key);
		return Constants.SUCCESS_CODE;
	}
	
	// 根据卡号获取发卡行信息
	@SuppressWarnings("unchecked")
	public List<Object[]> query(String settleAcct) {
		String countSql = "SELECT INS_ID_CD,CARD_DIS,CARD_TP,BIN_STA_NO FROM TBL_BANK_BIN_INF "
				+ "WHERE BIN_STA_NO = '" + settleAcct.substring(0, 6) + "'";
		return commQueryDAO.findBySQLQuery(countSql);
	}

	/**
	 * @return the tblBankCardBlackDAO
	 */
	public TblBankCardBlackDAO getTblBankCardBlackDAO() {
		return tblBankCardBlackDAO;
	}

	/**
	 * @param tblBankCardBlackDAO the tblBankCardBlackDAO to set
	 */
	public void setTblBankCardBlackDAO(TblBankCardBlackDAO tblBankCardBlackDAO) {
		this.tblBankCardBlackDAO = tblBankCardBlackDAO;
	}

	/**
	 * @return the tblBankCardBlackOptLogDAO
	 */
	public TblBankCardBlackOptLogDAO getTblBankCardBlackOptLogDAO() {
		return tblBankCardBlackOptLogDAO;
	}

	/**
	 * @param tblBankCardBlackOptLogDAO the tblBankCardBlackOptLogDAO to set
	 */
	public void setTblBankCardBlackOptLogDAO(
			TblBankCardBlackOptLogDAO tblBankCardBlackOptLogDAO) {
		this.tblBankCardBlackOptLogDAO = tblBankCardBlackOptLogDAO;
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
