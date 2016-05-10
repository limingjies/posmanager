package com.allinfinance.bo.impl.base;

import java.math.BigDecimal;

import com.allinfinance.bo.base.T10211BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.base.TblRuleMchtRelDAO;
import com.allinfinance.po.TblRuleMchtRel;
import com.allinfinance.po.TblRuleMchtRelPK;

public class T10211BOTarget implements T10211BO{

	private TblRuleMchtRelDAO tblRuleMchtRelDAO;
	private ICommQueryDAO commQueryDAO;
	
	@Override
	public String delete(TblRuleMchtRelPK key) {
		// TODO Auto-generated method stub
		tblRuleMchtRelDAO.delete(key);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblRuleMchtRel tblRuleMchtRel) {
		// TODO Auto-generated method stub
		tblRuleMchtRelDAO.delete(tblRuleMchtRel);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public TblRuleMchtRel get(TblRuleMchtRelPK key) {
		// TODO Auto-generated method stub
		return tblRuleMchtRelDAO.get(key);
	}

	@Override
	public String save(TblRuleMchtRel tblRuleMchtRel) {
		// TODO Auto-generated method stub
		tblRuleMchtRelDAO.save(tblRuleMchtRel);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblRuleMchtRel tblRuleMchtRel) {
		// TODO Auto-generated method stub
		tblRuleMchtRelDAO.update(tblRuleMchtRel);
		return Constants.SUCCESS_CODE;
	}
	
	
	
	
	@Override
	public String deleteMain(String ruleCode) {
		// TODO Auto-generated method stub
		String sql="select count(1) from tbl_rule_mcht_rel where trim(rule_code)='"+ruleCode+"' and msc2='1' ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		if (count.intValue() > 0 ) {
			return "该规则下还有启用状态的一级商户！";
		}
		
		String sql2="select count(1) from TBL_ROUTE_RULE where trim(RULE_CODE)='"+ruleCode+"'  ";
		BigDecimal count2 = (BigDecimal) commQueryDAO.findBySQLQuery(sql2).get(0);
		if (count2.intValue() > 0 ) {
			return "该规则在路由配置中有绑定，请先在路由配置中解除绑定！";
		}
		
		String sql3=" delete tbl_rule_mcht_rel where trim(rule_code)='"+ruleCode+"' ";
		commQueryDAO.excute(sql3);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String updateMain(TblRuleMchtRel tblRuleMchtRel) {
		// TODO Auto-generated method stub
		String sql="select count(1) from tbl_rule_mcht_rel where trim(rule_code)='"+
				tblRuleMchtRel.getTblRuleMchtRelPK().getRuleCode().trim()+"'  ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		if (count.intValue() == 0 ) {
			return "该规则不存在，请重新刷新选择！";
		}
		
		String sql2="update tbl_rule_mcht_rel set msc1='"+tblRuleMchtRel.getMsc1()+"' where trim(rule_code)='"+
				tblRuleMchtRel.getTblRuleMchtRelPK().getRuleCode().trim()+"'  ";
		commQueryDAO.excute(sql2);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String addMain(TblRuleMchtRel tblRuleMchtRel) {
		// TODO Auto-generated method stub
		String sql="select count(1) from tbl_rule_mcht_rel where trim(rule_code)='"+
				tblRuleMchtRel.getTblRuleMchtRelPK().getRuleCode().trim()+"'  ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		if (count.intValue() > 0 ) {
			return "该规则已经存在，请在该规则下添加新的一级商户！";
		}
		
		tblRuleMchtRelDAO.save(tblRuleMchtRel);
		return Constants.SUCCESS_CODE;
	}

	
	@Override
	public String deleteDtl(TblRuleMchtRelPK tblRuleMchtRelPK) {
		// TODO Auto-generated method stub
		tblRuleMchtRelDAO.delete(tblRuleMchtRelPK);
		String sql="select count(1) from tbl_rule_mcht_rel where trim(rule_code)='"+
				tblRuleMchtRelPK.getRuleCode().trim()+"'  ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		if (count.intValue() > 1 ) {
			return Constants.SUCCESS_CODE;
		}else{
			return  Constants.SUCCESS_CODE_CUSTOMIZE+Constants.SUCCESS_CODE;
		}
	}
	
	
	/**
	 * @return the tblRuleMchtRelDAO
	 */
	public TblRuleMchtRelDAO getTblRuleMchtRelDAO() {
		return tblRuleMchtRelDAO;
	}

	/**
	 * @param tblRuleMchtRelDAO the tblRuleMchtRelDAO to set
	 */
	public void setTblRuleMchtRelDAO(TblRuleMchtRelDAO tblRuleMchtRelDAO) {
		this.tblRuleMchtRelDAO = tblRuleMchtRelDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	

	

	
}
