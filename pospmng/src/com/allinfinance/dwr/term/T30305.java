package com.allinfinance.dwr.term;

import java.util.List;

import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;


public class T30305 {

	/**
	 * 判断该终端是否已经绑定机具
	 * @param actNo
	 * @param mchntNo
	 * @return
	 */

	
	public String isExistMana(String termMana,String term)
	{
		String sql = "select term_id from tbl_term_inf  where term_id <> '"+ term +"' and term_id_id = '"+ termMana +"'";
			
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List list = commQueryDAO.findBySQLQuery(sql);
		
		if(list != null&&!list.isEmpty())
			return "03";
		return Constants.SUCCESS_CODE;
	}
	
	public String isExistPinPad(String pinId,String term)
	{
		String sql = "select equip_inv_id from tbl_term_inf  where term_id <> '"+ term +"' and equip_inv_nm == '"+ pinId +"'";
			
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List list = commQueryDAO.findBySQLQuery(sql);
		
		if(list != null&&!list.isEmpty())
			return "02";
		return Constants.SUCCESS_CODE;
	}
}