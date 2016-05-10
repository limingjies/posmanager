package com.allinfinance.bo.impl.mchnt;

import java.math.BigDecimal;
import java.util.List;

import com.allinfinance.bo.mchnt.T20701BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.mchnt.TblHisDiscAlgoDAO;
import com.allinfinance.dao.iface.mchnt.TblInfDiscAlgoDAO;
import com.allinfinance.dao.iface.mchnt.TblInfDiscCdDAO;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblInfDiscAlgo;
import com.allinfinance.po.mchnt.TblInfDiscCd;
import com.allinfinance.system.util.CommonFunction;

/**
 * Title:商户组别维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author
 * 
 * @version 1.0
 */
public class T20701BOTarget implements T20701BO {

	private TblInfDiscAlgoDAO tblInfDiscAlgoDAO;
	private TblInfDiscCdDAO tblInfDiscCdDAO;
	private TblHisDiscAlgoDAO tblHisDiscAlgoDAO;
	private ICommQueryDAO commQueryDAO;

	public TblInfDiscAlgoDAO getTblInfDiscAlgoDAO() {
		return tblInfDiscAlgoDAO;
	}

	public void setTblInfDiscAlgoDAO(TblInfDiscAlgoDAO tblInfDiscAlgoDAO) {
		this.tblInfDiscAlgoDAO = tblInfDiscAlgoDAO;
	}

	public TblInfDiscCdDAO getTblInfDiscCdDAO() {
		return tblInfDiscCdDAO;
	}

	public void setTblInfDiscCdDAO(TblInfDiscCdDAO tblInfDiscCdDAO) {
		this.tblInfDiscCdDAO = tblInfDiscCdDAO;
	}

	public TblHisDiscAlgoDAO getTblHisDiscAlgoDAO() {
		return tblHisDiscAlgoDAO;
	}

	public void setTblHisDiscAlgoDAO(TblHisDiscAlgoDAO tblHisDiscAlgoDAO) {
		this.tblHisDiscAlgoDAO = tblHisDiscAlgoDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public String createArith(List<TblInfDiscAlgo> list,
			TblInfDiscCd tblInfDiscCd, List<TblHisDiscAlgo> descList) {
		
		if(tblInfDiscCdDAO.get(tblInfDiscCd.getDiscCd()) != null) {
			return "该算法信息已经存在";
		}
				
		tblInfDiscCdDAO.save(tblInfDiscCd);// 商户手续费名称定义表

		//如存在不正确数据就先删除
		String sql = "delete from TBL_HIS_DISC_ALGO where trim(DISC_ID) = '"
			+ tblInfDiscCd.getDiscCd().trim() + "'";
		commQueryDAO.excute(sql);
		
		for (TblHisDiscAlgo his : descList) {
			tblHisDiscAlgoDAO.save(his);// 商户手续费配置表
		}
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 更新计费信息
	 * 
	 * @see com.allinfinance.bo.mchnt.T20701BO#updateArith(java.util.List, com.allinfinance.po.mchnt.TblInfDiscCd, java.util.List)
	 */
	public String updateArith(List<TblInfDiscAlgo> list,
			TblInfDiscCd tblInfDiscCd, List<TblHisDiscAlgo> descList) {

		String sql = "delete from TBL_HIS_DISC_ALGO where trim(DISC_ID) = '"
				+ tblInfDiscCd.getDiscCd().trim() + "'";
		commQueryDAO.excute(sql);

		tblInfDiscCdDAO.update(tblInfDiscCd);// 商户手续费名称定义表

		for (TblHisDiscAlgo his : descList) {
			tblHisDiscAlgoDAO.save(his);// 商户手续费配置表
		}

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 删除计费信息
	 * 
	 * @see com.allinfinance.bo.mchnt.T20701BO#deleteArith(java.lang.String)
	 */
	public String deleteArith(String discCd) {
	
		//判断当前商户是否有使用该计费算法
		String s = "select count(*) from TBL_MCHT_SETTLE_INF where TRIM(FEE_RATE) = '" + discCd + "'";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(s).get(0);
		if (count.intValue() > 0) {
			return "该计费算法已被商户使用，不能被删除";
		}
		s = "select count(*) from TBL_MCHT_SETTLE_INF_TMP where TRIM(FEE_RATE) = '" + discCd + "'";
		count = (BigDecimal) commQueryDAO.findBySQLQuery(s).get(0);
		if (count.intValue() > 0) {
			return "该计费算法已被商户使用，不能被删除";
		}
		
		String sql = "delete from TBL_HIS_DISC_ALGO where trim(DISC_ID) = '"
				+ discCd.trim() + "'";
		commQueryDAO.excute(sql);

		tblInfDiscCdDAO.delete(CommonFunction.fillString(discCd, ' ', 5, true));

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 取得计费信息
	 * 
	 * @see com.allinfinance.bo.mchnt.T20701BO#getTblInfDiscCd(java.lang.String)
	 */
	public TblInfDiscCd getTblInfDiscCd(String discCd) throws Exception {
		return tblInfDiscCdDAO.get(CommonFunction.fillString(discCd, ' ', 5,
				true));
	}

}
