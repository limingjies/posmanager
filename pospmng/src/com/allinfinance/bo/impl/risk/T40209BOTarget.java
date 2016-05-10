package com.allinfinance.bo.impl.risk;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.risk.T40209BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamMccDAO;
import com.allinfinance.po.risk.TblRiskParamMcc;
import com.allinfinance.system.util.CommonFunction;

/**
 * MCC风控参数管理--T40209BOTarget-实现类
 * @author yww
 * @version 1.0
 * 2016年4月7日  下午1:56:44
 */
public class T40209BOTarget implements T40209BO {

	private TblRiskParamMccDAO tblRiskParamMccDAO;
	private ICommQueryDAO commQueryDAO;

	/*public List<String> query(String riskLvl) {
		String countSql = "SELECT RISK_ID FROM TBL_RISK_LVL WHERE RISK_LVL='" + riskLvl + "'";
		return commQueryDAO.findBySQLQuery(countSql);
	}*/
	
	public void save(TblRiskParamMcc tblRiskParamMcc){
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		tblRiskParamMcc.setRegOpr(operator.getOprId());
		tblRiskParamMcc.setRegTime(CommonFunction.getCurrentDateTime());
		tblRiskParamMcc.setUpdOpr(operator.getOprId());
		tblRiskParamMcc.setUpdTime(CommonFunction.getCurrentDateTime());
		tblRiskParamMccDAO.save(tblRiskParamMcc);
	}
	
	public void delete(TblRiskParamMcc tblRiskParamMcc){
		tblRiskParamMccDAO.delete(tblRiskParamMcc);
	}

	public void deleteByKey(String key){
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String[] mccData = key.split(",");
		for (String mcc : mccData) {
			TblRiskParamMcc riskParamMcc = tblRiskParamMccDAO.get(mcc);
			if (null != riskParamMcc) {
				tblRiskParamMccDAO.delete(riskParamMcc);
				System.out.println(("操作员编号：" + operator.getOprId()+ " MCC风控参数管理，删除 MCC="+mcc+" 的数据成功"));
			}
		}
		
	}
	
	public void update(TblRiskParamMcc tblRiskParamMcc){
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		tblRiskParamMcc.setUpdOpr(operator.getOprId());
		tblRiskParamMcc.setUpdTime(CommonFunction.getCurrentDateTime());
		tblRiskParamMccDAO.update(tblRiskParamMcc);
	}
	
	public TblRiskParamMcc get(String mcc){
		return tblRiskParamMccDAO.get(mcc);
	}
	
	public TblRiskParamMcc load(String key){
		return tblRiskParamMccDAO.load(key);
	}
	
	
	public TblRiskParamMccDAO getTblRiskParamMccDAO() {
		return tblRiskParamMccDAO;
	}

	public void setTblRiskParamMccDAO(TblRiskParamMccDAO tblRiskParamMccDAO) {
		this.tblRiskParamMccDAO = tblRiskParamMccDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}


}