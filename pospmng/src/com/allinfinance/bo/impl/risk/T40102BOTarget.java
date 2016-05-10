package com.allinfinance.bo.impl.risk;



import java.math.BigDecimal;

import com.allinfinance.bo.risk.T40102BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblRunRiskDAO;
import com.allinfinance.po.TblRunRisk;
import com.allinfinance.po.TblRunRiskPK;

public class T40102BOTarget implements T40102BO {
	
	private TblRunRiskDAO tblRunRiskDAO;
	public ICommQueryDAO commQueryDAO;

	
	public String add( TblRunRisk  tblRunRisk) {

		tblRunRiskDAO.save(tblRunRisk);
		return Constants.SUCCESS_CODE;
	}


	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public String delete(TblRunRiskPK tblRunRiskPK) {
		tblRunRiskDAO.delete(tblRunRiskPK);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public TblRunRisk get(TblRunRiskPK tblRunRiskPK) {
		return tblRunRiskDAO.get(tblRunRiskPK);
	}


	@Override
	public String update(TblRunRisk tblRunRisk) {
		// TODO Auto-generated method stub
		tblRunRiskDAO.update(tblRunRisk);
		return Constants.SUCCESS_CODE;
	}


	public TblRunRiskDAO getTblRunRiskDAO() {
		return tblRunRiskDAO;
	}


	public void setTblRunRiskDAO(TblRunRiskDAO tblRunRiskDAO) {
		this.tblRunRiskDAO = tblRunRiskDAO;
	}


	public String getRuleId() {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from TBL_RUN_RISK " ;
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql1).get(0);
		if (count.intValue() == 0 ) {
			return "1";
		}
		String sql = "select min(rule_id + 1) from TBL_RUN_RISK where (rule_id + 1) not in " +
		  "(select rule_id from TBL_RUN_RISK  ) " ;
		BigDecimal countNew = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		return String.valueOf(countNew.intValue());
	}


	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}


	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	

	

	
	
}