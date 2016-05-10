package com.allinfinance.bo.impl.risk;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.risk.T40206BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblRiskLvlDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamInfDAO;
import com.allinfinance.po.TblRiskLvl;
import com.allinfinance.po.TblRiskLvlPK;
import com.allinfinance.po.TblRiskParamInf;
import com.allinfinance.po.TblRiskParamInfPK;
import com.allinfinance.po.risk.TblRiskParamDef;

public class T40206BOTarget implements T40206BO {

	private TblRiskLvlDAO tblRiskLvlDAO;

	private TblRiskParamInfDAO tblRiskParamInfDAO;
	private ICommQueryDAO commQueryDAO;


	@SuppressWarnings("unchecked")
	public List<String> query(String riskLvl) {
		String countSql = "SELECT RISK_ID FROM TBL_RISK_LVL WHERE RISK_LVL='" + riskLvl + "'";
		return commQueryDAO.findBySQLQuery(countSql);
	}
	
	public String add(String riskLvl,String resved,List<String> addList) {
		for(String modelKind : addList) {
			TblRiskLvl tblRiskLvl = new TblRiskLvl();
			TblRiskLvlPK tblRiskLvlPK = new TblRiskLvlPK();
			tblRiskLvlPK.setRiskLvl(riskLvl);
			tblRiskLvlPK.setRiskId(modelKind);
			tblRiskLvl.setId(tblRiskLvlPK);
			tblRiskLvl.setResved(resved);
			String hql = " from com.allinfinance.po.risk.TblRiskParamDef t where t.id.riskId ='" + modelKind + "'";
			@SuppressWarnings("unchecked")
			// 需要将TblRiskParamDef对应的TblRiskParamDef.hbm.xml在applicationContext的查询缓冲池中进行配置才能查询到数据
			List<TblRiskParamDef> dataList = commQueryDAO.findByHQLQuery(hql);
			List<TblRiskParamInf> tblRiskParamDefList = new ArrayList<TblRiskParamInf>();
			for (TblRiskParamDef tblRiskParamDef : dataList) {
				TblRiskParamInf tblRiskParamInf = new TblRiskParamInf();
				TblRiskParamInfPK tblRiskParamInfPK = new TblRiskParamInfPK();
				tblRiskParamInfPK.setRiskLvl(riskLvl);
				tblRiskParamInfPK.setModelKind(modelKind);
				tblRiskParamInfPK.setModelSeq(tblRiskParamDef.getId().getParamSeq());
				tblRiskParamInf.setId(tblRiskParamInfPK);
				tblRiskParamInf.setParamLen(tblRiskParamDef.getParamLen());
				tblRiskParamInf.setParamName(tblRiskParamDef.getParamName());
				tblRiskParamInf.setParamValue(tblRiskParamDef.getDefaultValue());
				tblRiskParamDefList.add(tblRiskParamInf);
			}
			tblRiskParamInfDAO.save(tblRiskParamDefList);
			tblRiskLvlDAO.save(tblRiskLvl);
		}
		return Constants.SUCCESS_CODE;
	}

	public String update(String riskLvl,String resved,List<String> keepList,List<String> deleteList,List<String> addList) {
		for (String riskId : keepList) {
			TblRiskLvlPK tblRiskLvlPK = new TblRiskLvlPK();
			tblRiskLvlPK.setRiskLvl(riskLvl);
			tblRiskLvlPK.setRiskId(riskId);
			TblRiskLvl tblRiskLvl = tblRiskLvlDAO.get(tblRiskLvlPK);
			tblRiskLvl.setResved(resved);
			tblRiskLvlDAO.update(tblRiskLvl);
		}
		if(delete(riskLvl,deleteList)!=Constants.SUCCESS_CODE){
			return "该风控级别信息不存在，请刷新重新确认！";
		}
		return add(riskLvl,resved,addList);
	}

	public String delete(String riskLvl,List<String> deleteList) {
		for (String modelKind : deleteList) {
			String hql = " from com.allinfinance.po.TblRiskParamInf t "
					+ "where t.id.riskLvl ='" + riskLvl + "' and t.id.modelKind ='" + modelKind + "'";
			@SuppressWarnings("unchecked")
			List<TblRiskParamInf> tblRiskParamInfList = commQueryDAO.findByHQLQuery(hql);
			for (TblRiskParamInf tblRiskParamInf : tblRiskParamInfList) {
				tblRiskParamInfDAO.delete(tblRiskParamInf.getId());
			}
			TblRiskLvlPK tblRiskLvlPK = new TblRiskLvlPK();
			tblRiskLvlPK.setRiskLvl(riskLvl);
			tblRiskLvlPK.setRiskId(modelKind);
			tblRiskLvlDAO.delete(tblRiskLvlPK);
		}
		return Constants.SUCCESS_CODE;
	}

	public TblRiskLvlDAO getTblRiskLvlDAO() {
		return tblRiskLvlDAO;
	}

	public void setTblRiskLvlDAO(TblRiskLvlDAO tblRiskLvlDAO) {
		this.tblRiskLvlDAO = tblRiskLvlDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public TblRiskParamInfDAO getTblRiskParamInfDAO() {
		return tblRiskParamInfDAO;
	}

	public void setTblRiskParamInfDAO(TblRiskParamInfDAO tblRiskParamInfDAO) {
		this.tblRiskParamInfDAO = tblRiskParamInfDAO;
	}

}