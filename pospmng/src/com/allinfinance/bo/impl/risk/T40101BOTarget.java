
package com.allinfinance.bo.impl.risk;



import com.allinfinance.bo.risk.T40101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.RiskConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblRiskInfDAO;
import com.allinfinance.dao.iface.risk.TblRiskInfUpdLogDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamInfDAO;
import com.allinfinance.po.TblRiskInf;
import com.allinfinance.po.TblRiskInfUpdLog;
import com.allinfinance.po.TblRiskParamInf;
import com.allinfinance.po.TblRiskParamInfPK;
import com.allinfinance.system.util.CommonFunction;


public class T40101BOTarget implements T40101BO {
	
	private TblRiskInfDAO tblRiskInfDAO;
	private TblRiskParamInfDAO tblRiskParamInfDAO;
	private TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO;
	private ICommQueryDAO commQueryDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T40101BO#update(com.allinfinance.po.TblRiskInf)
	 */
	public String update(TblRiskParamInf tblRiskParamInfNew, TblRiskParamInf tblRiskParamInfOld, Operator operator) throws Exception {
		
		
		if(!tblRiskParamInfOld.getParamValue().trim().equals(tblRiskParamInfNew.getParamValue().trim())){
			TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
			tblRiskInfUpdLog.setSaModelKind(tblRiskParamInfNew.getId().getModelKind());
			tblRiskInfUpdLog.setSaFieldName(tblRiskParamInfNew.getId().getRiskLvl()+tblRiskParamInfNew.getParamName());
			tblRiskInfUpdLog.setSaFieldValueBF(tblRiskParamInfOld.getParamValue());
			tblRiskInfUpdLog.setSaFieldValue(tblRiskParamInfNew.getParamValue());
			tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
			tblRiskInfUpdLog.setModiOprId(operator.getOprId());
			tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		}
		
		/*if(!tblRiskParamInfOld.getRiskLvl().equals(tblRiskParamInfNew.getRiskLvl())){
			TblRiskInfUpdLog tblRiskInfUpdLogLvl = new TblRiskInfUpdLog();
			tblRiskInfUpdLogLvl.setSaModelKind(tblRiskParamInfNew.getId().getModelKind());
			tblRiskInfUpdLogLvl.setSaFieldName(tblRiskParamInfNew.getParamName()+"_"+RiskConstants.RISK_LVL);
			tblRiskInfUpdLogLvl.setSaFieldValueBF(getRiskLvl(tblRiskParamInfOld.getRiskLvl(),tblRiskParamInfNew.getId().getModelKind()));
			tblRiskInfUpdLogLvl.setSaFieldValue(getRiskLvl(tblRiskParamInfNew.getRiskLvl(),tblRiskParamInfNew.getId().getModelKind()));
			tblRiskInfUpdLogLvl.setModiZoneNo(operator.getOprBrhId());
			tblRiskInfUpdLogLvl.setModiOprId(operator.getOprId());
			tblRiskInfUpdLogLvl.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInfUpdLogDAO.save(tblRiskInfUpdLogLvl);
		}*/
				
			
		
		tblRiskParamInfDAO.update(tblRiskParamInfNew);
		return Constants.SUCCESS_CODE;
	}

	
	private  String getRiskLvl(String riskLvl,String riskId){
		String sql="select RISK_LVL||'-'||RESVED from TBL_RISK_LVL where risk_lvl ='"+riskLvl+"' and risk_id='"+riskId+"' ";
		String result=null;
		result=commQueryDAO.findCountBySQLQuery(sql);
		if(StringUtil.isNotEmpty(result)){
			return result;
		}else{
			return riskLvl;
		}
		
		
	}
	
	
	public TblRiskInf get(String key) {
		return tblRiskInfDAO.get(key);
	}
	
	/**
	 * @param tblRiskInfDAO the tblRiskInfDAO to set
	 */
	public void setTblRiskInfDAO(TblRiskInfDAO tblRiskInfDAO) {
		this.tblRiskInfDAO = tblRiskInfDAO;
	}

	/**
	 * @param tblRiskInfUpdLogDAO the tblRiskInfUpdLogDAO to set
	 */
	public void setTblRiskInfUpdLogDAO(TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO) {
		this.tblRiskInfUpdLogDAO = tblRiskInfUpdLogDAO;
	}

	/**
	 * @return the tblRiskParamInfDAO
	 */
	public TblRiskParamInfDAO getTblRiskParamInfDAO() {
		return tblRiskParamInfDAO;
	}

	/**
	 * @param tblRiskParamInfDAO the tblRiskParamInfDAO to set
	 */
	public void setTblRiskParamInfDAO(TblRiskParamInfDAO tblRiskParamInfDAO) {
		this.tblRiskParamInfDAO = tblRiskParamInfDAO;
	}






	@Override
	public TblRiskParamInf get(TblRiskParamInfPK key) throws Exception {
		// TODO Auto-generated method stub
		return tblRiskParamInfDAO.get(key);
	}





	@Override
	public String update(TblRiskInf tblRiskInf, Operator operator,String riskStatus) throws Exception {
		// TODO Auto-generated method stub
		TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
		tblRiskInfUpdLog.setSaModelKind(tblRiskInf.getId());
		tblRiskInfUpdLog.setSaFieldName(RiskConstants.RISK_CONN_SWITCH+RiskConstants.RISK_STATUS);
		if(RiskConstants.START_RISK.equals(riskStatus)){
			tblRiskInfUpdLog.setSaFieldValueBF(RiskConstants.RISK_STATUS_STOP);
			tblRiskInfUpdLog.setSaFieldValue(RiskConstants.RISK_STATUS_START);
		}else if (RiskConstants.STOP_RISK.equals(riskStatus)){
			tblRiskInfUpdLog.setSaFieldValueBF(RiskConstants.RISK_STATUS_START);
			tblRiskInfUpdLog.setSaFieldValue(RiskConstants.RISK_STATUS_STOP);
		}
		tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
		tblRiskInfUpdLog.setModiOprId(operator.getOprId());
		tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
		tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		tblRiskInfDAO.update(tblRiskInf);
		return Constants.SUCCESS_CODE; 
	}
	
	public String updateLvl(TblRiskInf tblRiskInf, Operator operator,String misc) throws Exception {
		// TODO Auto-generated method stub
		TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
		tblRiskInfUpdLog.setSaModelKind(tblRiskInf.getId());
		tblRiskInfUpdLog.setSaFieldName(RiskConstants.RISK_CONN_WARN_LVL+RiskConstants.RISK_WORING_LVL);
		
		tblRiskInfUpdLog.setSaFieldValueBF(getWaringLvlDes(tblRiskInf.getMisc()));
		tblRiskInfUpdLog.setSaFieldValue(getWaringLvlDes(misc));
		tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
		tblRiskInfUpdLog.setModiOprId(operator.getOprId());
		tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
		
		tblRiskInf.setMisc(misc);
		tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		tblRiskInfDAO.update(tblRiskInf);
		return Constants.SUCCESS_CODE; 
	}


	private static String getWaringLvlDes(String value){
		switch(Integer.valueOf(value)) {
			case 1 : return "低";
			case 2 : return "中";
			case 3 : return "高";
			default: return value;
		}
	}
	
	
	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}


	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}





	

	
	
	
}
