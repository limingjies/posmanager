/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-18       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.system.util;

import java.math.BigDecimal;
import java.util.List;

import com.allinfinance.commquery.dao.ICommQueryDAO;

/**
 * Title:生成系统编号
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-18
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class GenerateNextId {

	private static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil
			.getBean("CommQueryDAO");

	/**
	 * 获得idcd
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static synchronized String getDiscId() {
		String idcd = "00000";
		String sql = "select max(DISC_CD) from TBL_INF_DISC_CD";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null
				&& idList.get(0).toString().trim().length() != 0) {
			idcd = CommonFunction.fillString(idList.get(0).toString(), '0', 5,
					false);
		}
		idcd = CommonFunction.fillString(
				String.valueOf(Integer.parseInt(idcd) + 1), '0', 5, false);
		return idcd;

	}

	/**
	 * 获得银联卡编号
	 * 
	 * @return
	 */
	public static synchronized String getBankBinId() {
		String sql1 = "select count(1) from TBL_BANK_BIN_INF ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql1)
				.get(0);
		if (count.intValue() == 0) {
			return "1";
		}
		String sql = "select max(IND)+ 1 from TBL_BANK_BIN_INF ";
		BigDecimal countNew = (BigDecimal) commQueryDAO.findBySQLQuery(sql)
				.get(0);
		return String.valueOf(countNew.intValue());

	}

	/**
	 * 获得角色信息的编号
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getNextRoleId() {
		String sql = "select min(role_id + 1) from tbl_role_inf where "
				+ "(role_id + 1) not in (select role_id from tbl_role_inf)";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(sql);
		return resultSet.get(0).toString();
	}

	/**
	 * 获得商户编号
	 * 
	 * @param str
	 * @return 如果返回""，则表示该类商户已经达到数量上线
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getMchntId(String str) {
		// 判断是否存在序号为6000的ID
		String bgJ = "select count(*) from TBL_MCHT_BASE_INF_TMP where trim(mcht_no) = '"
				+ str + "6000" + "'";
		BigDecimal cJ = (BigDecimal) commQueryDAO.findBySQLQuery(bgJ).get(0);
		String bgZ = "select count(*) from tbl_mcht_cup_info_tmp where trim(mcht_no) = '"
				+ str + "6000" + "'";
		BigDecimal cZ = (BigDecimal) commQueryDAO.findBySQLQuery(bgZ).get(0);
		if (cJ.intValue() == 0 && cZ.intValue() == 0) {
			return str + "6000";
		}
		// 间联表最小编号
		String sqlJ = "select min(substr(mcht_no,12,4) + 1) from TBL_MCHT_BASE_INF_TMP where (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(mcht_no,12,4) + 0 from TBL_MCHT_BASE_INF_TMP where substr(mcht_no,1,11) = '"
				+ str
				+ "') "
				+ "and substr(mcht_no,1,11) = '"
				+ str
				+ "' and (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(b.mcht_no,12,4) + 0 as cupNo from tbl_mcht_cup_info_tmp b where substr(b.mcht_no,1,11) = '"
				+ str + "')";
		List<BigDecimal> resultSetJ = commQueryDAO.findBySQLQuery(sqlJ);

		// 直联表最小编号
		String sqlZ = "select min(substr(mcht_no,12,4) + 1) as min from tbl_mcht_cup_info_tmp where (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(mcht_no,12,4) + 0 as cupNo from tbl_mcht_cup_info_tmp where substr(mcht_no,1,11) = '"
				+ str
				+ "') "
				+ "and substr(mcht_no,1,11) = '"
				+ str
				+ "' and (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(b.mcht_no,12,4) + 0 as conNo from tbl_mcht_base_inf_tmp b where substr(b.mcht_no,1,11) = '"
				+ str + "')";
		List<BigDecimal> resultSetZ = commQueryDAO.findBySQLQuery(sqlZ);

		int id = 6000;
		if (resultSetJ.get(0) == null && resultSetZ.get(0) == null) {
			return str + "6000";
		} else if (resultSetJ.get(0) == null && resultSetZ.get(0) != null) {
			id = resultSetZ.get(0).intValue();
		} else if (resultSetJ.get(0) != null && resultSetZ.get(0) == null) {
			id = resultSetJ.get(0).intValue();
		} else if (resultSetJ.get(0) != null && resultSetZ.get(0) != null) {
			if (resultSetJ.get(0).intValue() >= resultSetZ.get(0).intValue()) {
				id = resultSetZ.get(0).intValue();
			} else {
				id = resultSetJ.get(0).intValue();
			}
		}

		if (id == 10000) {
			return "";
		}
		return str
				+ CommonFunction.fillString(String.valueOf(id), '0', 4, false);
	}

	/**
	 * 获得商户临时编号
	 * 
	 * @param str
	 * @return 如果返回""，则表示该类商户已经达到数量上线
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getMchntTmpId(String str) {
		// 判断是否存在序号为6000的ID
		String bgJ = "select count(*) from TBL_MCHT_BASE_INF_TMP_TMP where trim(mcht_no) = '"
				+ str + "6000" + "'";
		BigDecimal cJ = (BigDecimal) commQueryDAO.findBySQLQuery(bgJ).get(0);
		String bgZ = "select count(*) from tbl_mcht_cup_info_tmp where trim(mcht_no) = '"
				+ str + "6000" + "'";
		BigDecimal cZ = (BigDecimal) commQueryDAO.findBySQLQuery(bgZ).get(0);
		if (cJ.intValue() == 0 && cZ.intValue() == 0) {
			return str + "6000";
		}
		// 间联表最小编号
		String sqlJ = "select min(substr(mcht_no,12,4) + 1) from TBL_MCHT_BASE_INF_TMP_TMP where (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(mcht_no,12,4) + 0 from TBL_MCHT_BASE_INF_TMP_TMP where substr(mcht_no,1,11) = '"
				+ str
				+ "') "
				+ "and substr(mcht_no,1,11) = '"
				+ str
				+ "' and (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(b.mcht_no,12,4) + 0 as cupNo from tbl_mcht_cup_info_tmp b where substr(b.mcht_no,1,11) = '"
				+ str + "')";
		List<BigDecimal> resultSetJ = commQueryDAO.findBySQLQuery(sqlJ);

		// 直联表最小编号
		String sqlZ = "select min(substr(mcht_no,12,4) + 1) as min from tbl_mcht_cup_info_tmp where (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(mcht_no,12,4) + 0 as cupNo from tbl_mcht_cup_info_tmp where substr(mcht_no,1,11) = '"
				+ str
				+ "') "
				+ "and substr(mcht_no,1,11) = '"
				+ str
				+ "' and (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(b.mcht_no,12,4) + 0 as conNo from tbl_mcht_base_inf_tmp_tmp b where substr(b.mcht_no,1,11) = '"
				+ str + "')";
		List<BigDecimal> resultSetZ = commQueryDAO.findBySQLQuery(sqlZ);

		int id = 6000;
		if (resultSetJ.get(0) == null && resultSetZ.get(0) == null) {
			return str + "6000";
		} else if (resultSetJ.get(0) == null && resultSetZ.get(0) != null) {
			id = resultSetZ.get(0).intValue();
		} else if (resultSetJ.get(0) != null && resultSetZ.get(0) == null) {
			id = resultSetJ.get(0).intValue();
		} else if (resultSetJ.get(0) != null && resultSetZ.get(0) != null) {
			if (resultSetJ.get(0).intValue() >= resultSetZ.get(0).intValue()) {
				id = resultSetZ.get(0).intValue();
			} else {
				id = resultSetJ.get(0).intValue();
			}
		}

		if (id == 10000) {
			return "";
		}
		return str
				+ CommonFunction.fillString(String.valueOf(id), '0', 4, false);
	}

	/**
	 * 获得终端类型编号
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getTermTpId() {
		String sql = "select min(term_tp + 1) from tbl_term_tp where "
				+ "(term_tp + 1) not in (select term_tp from tbl_term_tp)";
		List<Double> resultSet = commQueryDAO.findBySQLQuery(sql);
		if (resultSet.get(0) == null) {
			return "01";
		}
		int id = resultSet.get(0).intValue();
		if (id == 100) {
			return "";
		}
		return CommonFunction.fillString(String.valueOf(id), '0', 2, false);
	}

	/**
	 * 查询操作日志流水号
	 * 
	 * @return 2010-12-9 上午10:21:38 Shuang.Pan
	 */
	public synchronized static String getTxnSeq() {
		String sql = "SELECT SEQ_TERM_NO.NEXTVAL FROM DUAL";
		sql = commQueryDAO.findBySQLQuery(sql).get(0).toString();
		sql = "1" + CommonFunction.fillString(sql, '0', 14, false);
		return sql;
	}

	/**
	 * 查询CA银联公钥编号
	 * 
	 * @return 2010-12-9 上午10:21:38 Shuang.Pan
	 */
	public synchronized static String getParaId() {
		String sql = "SELECT SEQ_EMV_PARA_NO.NEXTVAL FROM DUAL";
		sql = commQueryDAO.findBySQLQuery(sql).get(0).toString();
		sql = "1" + CommonFunction.fillString(sql, '0', 8, false);
		return sql;
	}

	/**
	 * 获得内部参数索引编号
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static synchronized String getParaIdx(String usageKey) {
		String sql = "select min(para_idx + 1) from tbl_emv_para where "
				+ "(para_idx + 1) not in (select para_idx from tbl_emv_para where trim(usage_key) = '"
				+ usageKey + "') and trim(usage_key) = '" + usageKey + "'";
		List resultSet = commQueryDAO.findBySQLQuery(sql);
		if (resultSet.get(0) == null) {
			return "01";
		}
		String id = resultSet.get(0).toString();

		if (id.equals("100")) {
			return "";
		}
		return CommonFunction.fillString(id, '0', 2, false);
	}

	/**
	 * 获得批量导入时商户编号
	 * 
	 * @param str
	 * @return 如果返回""，则表示该类商户已经达到数量上线
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getBulkIptMchntId(String str) {

		// 判断是否存在序号为6000的ID
		String bgJ = "select count(*) from TBL_MCHT_BASE_INF_TMP where trim(mcht_no) = '"
				+ str + "6000" + "'";
		BigDecimal cJ = (BigDecimal) commQueryDAO.findBySQLQuery(bgJ).get(0);
		String bgZ = "select count(*) from tbl_mcht_cup_info_tmp where trim(mcht_no) = '"
				+ str + "6000" + "'";
		BigDecimal cZ = (BigDecimal) commQueryDAO.findBySQLQuery(bgZ).get(0);
		if (cJ.intValue() == 0 && cZ.intValue() == 0) {
			return str + "6000";
		}
		// 间联表最小编号
		String sqlJ = "select max(substr(mcht_no,12,4) + 1) from TBL_MCHT_BASE_INF_TMP where (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(mcht_no,12,4) + 0 from TBL_MCHT_BASE_INF_TMP where substr(mcht_no,1,11) = '"
				+ str
				+ "') "
				+ "and substr(mcht_no,1,11) = '"
				+ str
				+ "' and (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(b.mcht_no,12,4) + 0 as cupNo from tbl_mcht_cup_info_tmp b where substr(b.mcht_no,1,11) = '"
				+ str + "')";
		List<BigDecimal> resultSetJ = commQueryDAO.findBySQLQuery(sqlJ);

		// 直联表最小编号
		String sqlZ = "select max(substr(mcht_no,12,4) + 1) as min from tbl_mcht_cup_info_tmp where (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(mcht_no,12,4) + 0 as cupNo from tbl_mcht_cup_info_tmp where substr(mcht_no,1,11) = '"
				+ str
				+ "') "
				+ "and substr(mcht_no,1,11) = '"
				+ str
				+ "' and (substr(mcht_no,12,4) + 1) not in "
				+ "(select substr(b.mcht_no,12,4) + 0 as conNo from tbl_mcht_base_inf_tmp b where substr(b.mcht_no,1,11) = '"
				+ str + "')";
		List<BigDecimal> resultSetZ = commQueryDAO.findBySQLQuery(sqlZ);

		int id = 6000;
		if (resultSetJ.get(0) == null && resultSetZ.get(0) == null) {
			return str + "6000";
		} else if (resultSetJ.get(0) == null && resultSetZ.get(0) != null) {
			id = resultSetZ.get(0).intValue();
		} else if (resultSetJ.get(0) != null && resultSetZ.get(0) == null) {
			id = resultSetJ.get(0).intValue();
		} else if (resultSetJ.get(0) != null && resultSetZ.get(0) != null) {
			if (resultSetJ.get(0).intValue() >= resultSetZ.get(0).intValue()) {
				id = resultSetJ.get(0).intValue();
			} else {
				id = resultSetZ.get(0).intValue();
			}
		}

		if (id == 10000) {
			return "";
		}
		return str
				+ CommonFunction.fillString(String.valueOf(id), '0', 4, false);
	}

	/**
	 * 获取批量录入批次号
	 * 
	 */
	@SuppressWarnings("unchecked")
	public synchronized static String getBatchNo(String batchHead) {
		String sql = "select max(SUBSTR(BATCH_NO,13,4)) +1 from TBL_BLUK_IMP_RET_INF where SUBSTR(BATCH_NO,1,12) = '"
				+ batchHead + "'";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(sql);
		String batchNo = null;
		if (resultSet.size() > 0 && resultSet.get(0) != null) {
			batchNo = resultSet.get(0).toString();
		} else {
			batchNo = "1";
		}
		batchNo = batchHead + CommonFunction.fillString(batchNo, '0', 4, false);
		return batchNo;
	}

	/**
	 * 事后风控 获取风险商户提醒表的remind_id编号 2014-12-12
	 * 
	 * @return
	 */
	public synchronized static Integer getRemindMchtId() {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from TBL_REMIND_RISK_MCHT "
				+ " where risk_date='" + CommonFunction.getCurrentDate() + "' ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql1)
				.get(0);
		if (count.intValue() == 0) {
			return 1;
		}
		String sql = "select min(REMIND_ID + 1) from TBL_REMIND_RISK_MCHT "
				+ " where risk_date='" + CommonFunction.getCurrentDate() + "' "
				+ " and (REMIND_ID + 1) not in "
				+ "(select REMIND_ID from TBL_REMIND_RISK_MCHT "
				+ " where risk_date='" + CommonFunction.getCurrentDate()
				+ "' ) ";
		BigDecimal countNew = (BigDecimal) commQueryDAO.findBySQLQuery(sql)
				.get(0);
		return countNew.intValue();
	}

	/**
	 * 事后风控 获取风险交易提醒表的remind_id编号 2014-12-12
	 * 
	 * @return
	 */
	public synchronized static int getRemindTxnId() {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from TBL_REMIND_RISK_TXN "
				+ " where risk_date='" + CommonFunction.getCurrentDate() + "' ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql1)
				.get(0);
		if (count.intValue() == 0) {
			return 1;
		}
		String sql = "select min(REMIND_ID + 1) from TBL_REMIND_RISK_TXN "
				+ " where  risk_date='" + CommonFunction.getCurrentDate()
				+ "' " + " and (REMIND_ID + 1) not in "
				+ " (select REMIND_ID from TBL_REMIND_RISK_TXN "
				+ " where  risk_date='" + CommonFunction.getCurrentDate()
				+ "' ) ";
		BigDecimal countNew = (BigDecimal) commQueryDAO.findBySQLQuery(sql)
				.get(0);
		return countNew.intValue();
	}

	/**
	 * 事后风控 获取风险交易,调单回执表的id编号 2014-12-12
	 * 
	 * @return
	 */
	public synchronized static String getBillId() {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from TBL_BILL_RECEIPT where substr(BILL_ID,1,8)='"
				+ CommonFunction.getCurrentDate() + "' ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql1)
				.get(0);
		if (count.intValue() == 0) {
			return CommonFunction.getCurrentDate()
					+ CommonFunction.fillString("1", '0', 7, false);
		}
		String sql = "select  max(to_number(substr(BILL_ID,9)))+1 from TBL_BILL_RECEIPT where substr(BILL_ID,1,8)='"
				+ CommonFunction.getCurrentDate() + "' ";
		BigDecimal countNew = (BigDecimal) commQueryDAO.findBySQLQuery(sql)
				.get(0);
		return CommonFunction.getCurrentDate()
				+ CommonFunction.fillString(
						String.valueOf(countNew.intValue()), '0', 7, false);
	}

	/**
	 * 获取路由Id 2014-12-12
	 * 
	 * @return by caotz
	 */
	public synchronized static String getRuleId() {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from TBL_ROUTE_RULE ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql1)
				.get(0);
		if (count.intValue() == 0) {
			return "1";
		}
		String sql = "select max(rule_id)+ 1 from TBL_ROUTE_RULE ";
		BigDecimal countNew = (BigDecimal) commQueryDAO.findBySQLQuery(sql)
				.get(0);
		return String.valueOf(countNew.intValue());
	}

	/**
	 * 获取机构分润费率序号seq 2014-12-12
	 * 
	 * @return by caotz
	 */
	public synchronized static int getBrhFeeCfgSeq(String discId) {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from TBL_BRH_FEE_CFG where disc_id='"
				+ discId + "' ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql1)
				.get(0);
		if (count.intValue() == 0) {
			return 1;
		}
		String sql = "select max(seq)+ 1 from TBL_BRH_FEE_CFG where disc_id='"
				+ discId + "' ";
		BigDecimal countNew = (BigDecimal) commQueryDAO.findBySQLQuery(sql)
				.get(0);
		return countNew.intValue();
	}

	/**
	 * 获取机构分润费率序号seq 2014-12-12
	 * 
	 * @return by caotz
	 */
	public synchronized static int getBrhFeeCfgZlfSeq(String discId) {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from TBL_BRH_FEE_CFG_ZLF where disc_id='"
				+ discId + "' ";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql1)
				.get(0);
		if (count.intValue() == 0) {
			return 1;
		}
		String sql = "select max(seq)+ 1 from TBL_BRH_FEE_CFG_ZLF where disc_id='"
				+ discId + "' ";
		BigDecimal countNew = (BigDecimal) commQueryDAO.findBySQLQuery(sql)
				.get(0);
		return countNew.intValue();
	}
	/**
	 * 获取计费编码
	 * 
	 * @return 2010-12-9 上午10:21:38 Shuang.Pan
	 */
	public synchronized static String getFeeRowId() {
		String sql = "SELECT SEQ_MB_WITHDRAW_FEE.NEXTVAL FROM DUAL";
//		sql = CommonFunction.getCommQuery_frontDAO().findBySQLQuery(sql).get(0)
//				.toString();
		return "方法作废";
	}

	/**
	 * 获取系统流水号
	 * 
	 * @return 2010-12-9 上午10:21:38 Shuang.Pan
	 */
	public synchronized static String getSeqSysNum() {
		String sql = "SELECT SEQ_SYS_NUM.NEXTVAL FROM DUAL";
		sql = commQueryDAO.findBySQLQuery(sql).get(0).toString();
		return sql;
	}

	/**
	 * 获取商户资金冻结批次号
	 * 
	 * @return 2015-8-27 上午10:21:38 zhiqiang.Yin
	 */
	public synchronized static String getSeqFreezeNo() {
		String sql = "SELECT SEQ_FREEZE_NO.NEXTVAL FROM DUAL";
		sql = commQueryDAO.findBySQLQuery(sql).get(0).toString();
		return CommonFunction.getCurrentDate()+CommonFunction.fillString(sql, '0', 6, false);
	}
}
