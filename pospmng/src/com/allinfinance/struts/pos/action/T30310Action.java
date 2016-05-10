package com.allinfinance.struts.pos.action;

import java.util.List;

import com.allinfinance.bo.term.T30302BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.po.TblTermManagementAppMain;
import com.allinfinance.po.TblTermManagementCheck;
import com.allinfinance.po.TblTermManagementCheckPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T30310Action extends BaseAction{
	
	T30302BO t30302BO = (T30302BO) ContextUtil.getBean("t30302BO");
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("compare".equals(method)) {
			log("差错同步");
			rspCode = compare();
		}
		return rspCode;
	}

	protected String compare(){
		String sqlC = "select a.app_id,(SELECT count(*) from tbl_term_management c where a.app_id = c.batch_no and a.manufaturer=c.manufaturer and a.terminal_type= c.terminal_type and a.term_type = c.term_type)||'' as count," +
				"nvl(acc_amount,0) as acc,son_app_id,a.stat from tbl_term_management_app_main b left outer join tbl_term_management_check a on(a.app_id = b.app_id)  where b.stat='1' " +
				"and b.brh_id in "+ operator.getBrhBelowId();
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List<Object[]> listC = commQueryDAO.findBySQLQuery(sqlC);
		
		TblTermManagementCheck child = null;
		TblTermManagementCheckPK childPk = null;
		
		if(listC != null && listC.size() != 0 && !listC.isEmpty()){
			for(int i=0;i < listC.size();i++){
				String appId =(String) listC.get(i)[0];
				String count =(String) listC.get(i)[1];
				String accAmount =(String) listC.get(i)[2];
				String childId =(String) listC.get(i)[3];
				String stat =(String) listC.get(i)[4];
				
				int errAmount = Integer.valueOf(accAmount) - Integer.valueOf(count);

				childPk = new TblTermManagementCheckPK();
				childPk.setAppId(appId);
				childPk.setSonAppId(childId);
				
				child = t30302BO.get(childPk);
				
				if("1".equals(stat) || "2".equals(stat)){
					if(errAmount != 0){
						if(errAmount > 0){
							child.setMisc2("L" + errAmount);
						}else if(errAmount < 0){
							child.setMisc2("M" + Math.abs(errAmount));
						}
					}else {
						child.setMisc2("");
					}
				}
				
				
				t30302BO.update(child);
			}
			
		}
		
		return Constants.SUCCESS_CODE;
	}
	
}
