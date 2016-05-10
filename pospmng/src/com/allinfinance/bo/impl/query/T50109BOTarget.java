package com.allinfinance.bo.impl.query;

import java.util.HashMap;
import java.util.Map;

import com.allinfinance.bo.query.T50109BO;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.query.TblAccInConfirmHisDAO;
import com.allinfinance.po.query.TblAccInConfirmHis;

/**
 * T50109页面业务操作对象实现类
 * 
 * @author luhq
 *
 */
public class T50109BOTarget implements T50109BO {
	/**
	 * TblAccInConfirmHis数据操作对象
	 */
	private TblAccInConfirmHisDAO tblAccInConfirmHisDAO = null;

	/**
	 * commQueryDAO 通用数据查询对象
	 */
	private ICommQueryDAO commQueryDAO = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.bo.query.T50109BO#get(java.lang.String)
	 */
	@Override
	public TblAccInConfirmHis get(String dateSettlmt) {
		return tblAccInConfirmHisDAO.get(dateSettlmt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.bo.query.T50109BO#save(com.allinfinance.po.query.
	 * TblAccInConfirmHis)
	 */
	@Override
	public void save(TblAccInConfirmHis tblAccInConfirmHis) {
		tblAccInConfirmHisDAO.save(tblAccInConfirmHis);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.query.T50109BO#existRunBatchRecords(java.lang.String)
	 */
	@Override
	public boolean existRunBatchRecords(String dateSettlmt) {
		String countSql = "select count(1) from tbl_bat_exec_ctl c where bat_status='1' and grpid = '0001' and c.stlm_dt = :STLM_DT ";
		Map<String, String> params = new HashMap<String, String>();
		params.put("STLM_DT", dateSettlmt);
		String rst1 = commQueryDAO.findCountBySQLQuery(countSql, params);
		Integer nRst1 = Integer.parseInt(rst1);
		
		countSql = "select count(1) from tbl_bat_exec_ctl c where bat_status='1' and grpid = '0002' and c.stlm_dt = :STLM_DT ";
		String rst2 = commQueryDAO.findCountBySQLQuery(countSql, params);
		Integer nRst2 = Integer.parseInt(rst2);
		
		boolean rst = (nRst1 > 0 && nRst2 >0);
		return rst;
	}

	public void setTblAccInConfirmHisDAO(
			TblAccInConfirmHisDAO tblAccInConfirmHisDAO) {
		this.tblAccInConfirmHisDAO = tblAccInConfirmHisDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

}
