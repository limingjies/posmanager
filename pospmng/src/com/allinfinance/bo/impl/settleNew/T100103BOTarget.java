package com.allinfinance.bo.impl.settleNew;


import org.apache.log4j.Logger;

import com.allinfinance.bo.settleNew.T100103BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;

public class T100103BOTarget implements T100103BO {
	


	private ICommQueryDAO commQueryDAO;
	private static Logger log = Logger.getLogger(T100103BOTarget.class);
	
	@Override
	public String update(String falg, String batId,String restGrpId) throws Exception {
		String sql = "UPDATE TBL_BAT_TASK_CTL_T0 SET BAT_STATUS = '0' ,beg_time='' ,end_time ='' WHERE 1=1";
		if("1".equals(falg)) {
			sql += " AND BAT_ID ='" +  batId + "'";
		} else if("0".equals(falg)) {
			sql += " AND USE_FLAG = 'Y' AND SUBSTR(BAT_FLAG,0,1) != '3' and grpid='"+restGrpId+"' ";
		}
//		if(!"".equals(batId) && batId != null) {
//			
//		}
		commQueryDAO.excute(sql);
		log.info(sql);
		return Constants.SUCCESS_CODE;
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

	@Override
	public String resetBat(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE TBL_BAT_TASK_CTL_T0 SET BAT_STATUS = '0' ,beg_time='' ,end_time =''  WHERE BAT_ID = '" + id + "'";
		commQueryDAO.excute(sql);
		log.info(sql);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String resetAsn(String id2) throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE TBL_BAT_TASK_CTL_T0 SET ASN_STATUS = '0' ,beg_time='' ,end_time =''  WHERE BAT_ID = '" + id2 + "'";
		commQueryDAO.excute(sql);
		log.info(sql);
		return Constants.SUCCESS_CODE;
	}

	
	
	
	
}