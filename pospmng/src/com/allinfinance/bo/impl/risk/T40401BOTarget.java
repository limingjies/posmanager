package com.allinfinance.bo.impl.risk;

import com.allinfinance.bo.risk.T40401BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.RiskConstants;
import com.allinfinance.dao.iface.risk.TblWhiteMchtApplyDAO;
import com.allinfinance.dao.iface.risk.TblWhiteMchtCheckDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.risk.TblWhiteMchtApply;
import com.allinfinance.po.risk.TblWhiteMchtCheck;

public class T40401BOTarget implements T40401BO {
	private TblWhiteMchtApplyDAO tblWhiteMchtApplyDAO;
	private TblWhiteMchtCheckDAO tblWhiteMchtCheckDAO;
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	private TblMchtBaseInfDAO tblMchtBaseInfDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40401BO#get(java.lang.String)
	 */
	@Override
	public TblWhiteMchtApply get(String id) {
		return tblWhiteMchtApplyDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40401BO#load(java.lang.String)
	 */
	@Override
	public TblMchtBaseInf load(String id) {
		return tblMchtBaseInfDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.risk.T40401BO#check(com.allinfinance.po.risk.TblWhiteMchtCheck, com.allinfinance.po.risk.TblWhiteMchtApply)
	 */
	@Override
	public String check(TblWhiteMchtCheck tblWhiteMchtCheck,TblWhiteMchtApply tblWhiteMchtApply) {
		tblWhiteMchtApplyDAO.update(tblWhiteMchtApply);
		tblWhiteMchtCheckDAO.save(tblWhiteMchtCheck);
		if((RiskConstants.LAST_CHECK_T).equals(tblWhiteMchtApply.getCheckStatus())){
			TblMchtBaseInfTmp tblMchtBaseInfTmp =tblMchtBaseInfTmpDAO.get(tblWhiteMchtApply.getMchtNo());
			TblMchtBaseInf tblMchtBaseInf =tblMchtBaseInfDAO.get(tblWhiteMchtApply.getMchtNo());
			// 更新此商户风险等级为白名单
			tblMchtBaseInfTmp.setRislLvl(RiskConstants.RISK_LVL_WHITE_MCHT);
			tblMchtBaseInf.setRislLvl(RiskConstants.RISK_LVL_WHITE_MCHT);
			tblMchtBaseInfTmpDAO.update(tblMchtBaseInfTmp);
			tblMchtBaseInfDAO.update(tblMchtBaseInf);
			// 将此商户加入商户白名单信息表
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblWhiteMchtApplyDAO
	 */
	public TblWhiteMchtApplyDAO getTblWhiteMchtApplyDAO() {
		return tblWhiteMchtApplyDAO;
	}

	/**
	 * @param tblWhiteMchtApplyDAO the tblWhiteMchtApplyDAO to set
	 */
	public void setTblWhiteMchtApplyDAO(TblWhiteMchtApplyDAO tblWhiteMchtApplyDAO) {
		this.tblWhiteMchtApplyDAO = tblWhiteMchtApplyDAO;
	}

	/**
	 * @return the tblWhiteMchtCheckDAO
	 */
	public TblWhiteMchtCheckDAO getTblWhiteMchtCheckDAO() {
		return tblWhiteMchtCheckDAO;
	}

	/**
	 * @param tblWhiteMchtCheckDAO the tblWhiteMchtCheckDAO to set
	 */
	public void setTblWhiteMchtCheckDAO(TblWhiteMchtCheckDAO tblWhiteMchtCheckDAO) {
		this.tblWhiteMchtCheckDAO = tblWhiteMchtCheckDAO;
	}

	/**
	 * @return the tblMchtBaseInfDAO
	 */
	public TblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	/**
	 * @return the tblMchtBaseInfTmpDAO
	 */
	public TblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpDAO the tblMchtBaseInfTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpDAO(TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfDAO the tblMchtBaseInfDAO to set
	 */
	public void setTblMchtBaseInfDAO(TblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}
}