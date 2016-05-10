
package com.allinfinance.bo.impl.risk;



import com.allinfinance.bo.risk.T40200BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.RiskConstants;
import com.allinfinance.dao.iface.risk.TblRiskInfUpdLogDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamDefDAO;
import com.allinfinance.po.TblRiskInfUpdLog;
import com.allinfinance.po.risk.TblRiskParamDef;
import com.allinfinance.po.risk.TblRiskParamDefPK;
import com.allinfinance.system.util.CommonFunction;


public class T40200BOTarget implements T40200BO {
	
	private TblRiskParamDefDAO tblRiskParamDefDAO;
	private TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO;
	
	public String update(TblRiskParamDef tblRiskParamDefNew, TblRiskParamDef tblRiskParamDefOld, Operator operator) throws Exception {
		
		if(!tblRiskParamDefOld.getDefaultValue().trim().equals(tblRiskParamDefNew.getDefaultValue().trim())){
			TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
			tblRiskInfUpdLog.setSaModelKind(tblRiskParamDefNew.getId().getRiskId());
			tblRiskInfUpdLog.setSaFieldName(RiskConstants.RISK_CONN_PARAM_UPD+tblRiskParamDefNew.getParamName());
			tblRiskInfUpdLog.setSaFieldValueBF(tblRiskParamDefOld.getDefaultValue());
			tblRiskInfUpdLog.setSaFieldValue(tblRiskParamDefNew.getDefaultValue());
			tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
			tblRiskInfUpdLog.setModiOprId(operator.getOprId());
			tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		}
		
		tblRiskParamDefDAO.update(tblRiskParamDefNew);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public TblRiskParamDef get(TblRiskParamDefPK key) throws Exception {
		// TODO Auto-generated method stub
		return tblRiskParamDefDAO.get(key);
	}

	/**
	 * @return the tblRiskParamDefDAO
	 */
	public TblRiskParamDefDAO getTblRiskParamDefDAO() {
		return tblRiskParamDefDAO;
	}

	/**
	 * @param tblRiskParamDefDAO the tblRiskParamDefDAO to set
	 */
	public void setTblRiskParamDefDAO(TblRiskParamDefDAO tblRiskParamDefDAO) {
		this.tblRiskParamDefDAO = tblRiskParamDefDAO;
	}

	/**
	 * @return the tblRiskInfUpdLogDAO
	 */
	public TblRiskInfUpdLogDAO getTblRiskInfUpdLogDAO() {
		return tblRiskInfUpdLogDAO;
	}

	/**
	 * @param tblRiskInfUpdLogDAO the tblRiskInfUpdLogDAO to set
	 */
	public void setTblRiskInfUpdLogDAO(TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO) {
		this.tblRiskInfUpdLogDAO = tblRiskInfUpdLogDAO;
	}
	
}
