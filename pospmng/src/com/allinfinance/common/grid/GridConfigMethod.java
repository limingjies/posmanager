package com.allinfinance.common.grid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.RiskConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.common.select.SelectMethod;
import com.allinfinance.struts.pos.TblTermManagementConstants;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.FileFilter;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:信息列表动态获取方法集合
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-6
 * 
 * Company: Shanghai allinfinance Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class GridConfigMethod {

	/**
	 * 查询机构信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhInfoBelow(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "a.BRH_ID IN " + operator.getBrhBelowId());

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND a.BRH_ID = '" + request.getParameter("brhId")
			+ "' ");
		}
		
		if (isNotEmpty(request.getParameter("brhNewNoId"))) {
			whereSql.append(" AND a.CREATE_NEW_NO = '" + request.getParameter("brhNewNoId")
			+ "' ");	
		}
		if (isNotEmpty(request.getParameter("brhName"))) {
			whereSql.append(" AND a.BRH_NAME  like '%"
					+ request.getParameter("brhName") + "%' ");
		}
		if (isNotEmpty(request.getParameter("brhLevel"))) {
			whereSql.append(" AND a.BRH_LEVEL = '"
					+ request.getParameter("brhLevel") + "' ");
		}

		String sql = "SELECT a.BRH_ID,nvl(a.CREATE_NEW_NO,'旧数据未生成') createNewNo,a.BRH_LEVEL,a.UP_BRH_ID,nvl(d.CREATE_NEW_NO,'旧数据未生成') upCreateNewNo,a.BRH_NAME,a.BRH_ADDR,a.BRH_TEL_NO,a.POST_CD,"
				+ " a.BRH_CONT_NAME,a.CUP_BRH_ID,b.CITY_NAME,a.RESV2,c.DISC_NAME,CASE a.status WHEN '0' THEN '待审核' WHEN '1' THEN '通过' when '2' then '退回' ELSE '待审核' END status,a.status statusCode,a.reg_dt"
				+ " FROM TBL_BRH_INFO a left join CST_CITY_CODE b on substr(a.RESV1,1,4)=b.MCHT_CITY_CODE"
				+ " left join TBL_BRH_FEE_CTL c on substr(a.RESV1, 8,1) = '1' and substr(a.RESV1, 9) = c.DISC_ID"
				+ " left join TBL_BRH_INFO d on a.UP_BRH_ID = d.BRH_ID"
				+ whereSql.toString() + " order by a.status ,a.last_upd_ts desc,a.BRH_LEVEL  ,a.BRH_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_BRH_INFO a"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhHistoryRecord(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND a.BRH_ID = '" + request.getParameter("brhId")
			+ "' ");
		}

		String sql = "SELECT a.BRH_ID,a.SEQ_ID,nvl(a.CREATE_NEW_NO,'旧数据未生成') createNewNo,a.BRH_LEVEL,a.UP_BRH_ID,nvl(d.CREATE_NEW_NO,'旧数据未生成') upCreateNewNo,a.BRH_NAME,a.BRH_ADDR,a.BRH_TEL_NO,a.POST_CD,"
				+ " a.BRH_CONT_NAME,a.CUP_BRH_ID,b.CITY_NAME,a.RESV2,c.DISC_NAME,CASE a.status WHEN '0' THEN '待审核' WHEN '1' THEN '通过' when '2' then '退回' ELSE '待审核' END status,a.status statusCode,a.reg_dt,a.LAST_UPD_TS"
				+ " FROM TBL_BRH_INFO_HIS a left join CST_CITY_CODE b on substr(a.RESV1,1,4)=b.MCHT_CITY_CODE"
				+ " left join TBL_BRH_FEE_CTL c on substr(a.RESV1, 8,1) = '1' and substr(a.RESV1, 9) = c.DISC_ID"
				+ " left join TBL_BRH_INFO d on a.UP_BRH_ID = d.BRH_ID"
				+ whereSql.toString() + " order by to_number(a.seq_id) desc,a.status ,a.last_upd_ts desc,a.BRH_LEVEL  ,a.BRH_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_BRH_INFO_HIS a"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 查询操作员信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getOprInfoWithBrh(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "SELECT OPR_ID,BRH_ID,OPR_NAME,OPR_GENDER,REGISTER_DT,OPR_TEL,OPR_MOBILE FROM TBL_OPR_INFO WHERE BRH_ID = '"
				+ request.getParameter("brhId") + "'";
		String countSql = "SELECT COUNT(*) FROM TBL_OPR_INFO WHERE BRH_ID = '"
				+ request.getParameter("brhId") + "'";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询当前操作员下属操作员信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getOprInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "A.BRH_ID IN " + operator.getBrhBelowId()
				+ "AND A.OPR_ID NOT IN ('0000', '1111')");
		if (isNotEmpty(request.getParameter("oprId"))) {
			whereSql.append(" AND A.OPR_ID = '" + request.getParameter("oprId")
					+ "' ");
		}

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND A.BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND A.REGISTER_DT >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND A.REGISTER_DT <= '"
					+ request.getParameter("endDate") + "' ");
		}

		String sql = "SELECT A.OPR_ID,A.BRH_ID,B.CREATE_NEW_NO AS DISP_BRH_ID,A.OPR_DEGREE,A.OPR_NAME,A.OPR_GENDER,A.REGISTER_DT,A.OPR_TEL,"
				+ "A.OPR_MOBILE,A.PWD_OUT_DATE,A.OPR_STA FROM TBL_OPR_INFO A LEFT JOIN TBL_BRH_INFO B ON A.BRH_ID=B.BRH_ID"
				+ whereSql;

		String countSql = "SELECT COUNT(*) FROM TBL_OPR_INFO A" + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询交易日志信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTxnInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("oprNo"))) {
			whereSql.append(" and a.OPR_ID = '" + request.getParameter("oprNo")
					+ "' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and TXN_DATE >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and TXN_DATE <= '"
					+ request.getParameter("endDate") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("brhId"))) {
			whereSql.append(" and b.BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (!StringUtil.isNull(request.getParameter("conTxn"))) {
			whereSql.append(" and TXN_CODE = '"
					+ request.getParameter("conTxn") + "' ");
		}

		String sql = "SELECT a.OPR_ID,TXN_DATE,TXN_TIME,TXN_NAME,TXN_STA,ERR_MSG,IP_ADDR "
				+ "FROM TBL_TXN_INFO a , TBL_OPR_INFO b WHERE a.OPR_ID = b.OPR_ID AND "
				+ "b.BRH_ID IN "
				+ operator.getBrhBelowId()
				+ whereSql.toString() + " ORDER BY TXN_DATE DESC,TXN_TIME DESC";

		String countSql = "SELECT COUNT(*) "
				+ "FROM TBL_TXN_INFO a , TBL_OPR_INFO b WHERE a.OPR_ID = b.OPR_ID AND "
				+ "b.BRH_ID IN " + operator.getBrhBelowId()
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询集团商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getGroupMchtInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();

		whereSql.append(" WHERE 1=1 ");
		if (isNotEmpty(request.getParameter("groupMchtCd"))) {
			whereSql.append(" AND group_mcht_cd = '"
					+ request.getParameter("groupMchtCd") + "' ");
		}
		if (isNotEmpty(request.getParameter("grouName"))) {
			whereSql.append(" AND group_name = '"
					+ request.getParameter("grouName") + "' ");
		}

		String order = "ORDER BY a.GROUP_MCHT_CD";
		String sql = "SELECT a.GROUP_MCHT_CD,a.GROUP_NAME,a.GROUP_TYPE,a.REG_FUND,"
				+ "a.BUS_RANGE,a.MCHT_PERSON,a.CONTACT_ADDR,bank_no FROM TBL_GROUP_MCHT_INF a"
				+ whereSql.toString() + order;
		String countSql = "SELECT COUNT(*) FROM TBL_GROUP_MCHT_INF a"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户入网信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntNet(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE a.brh_id = b.brh_id and a.last_opr_id IN "
				+ operator.getBrhBelowId());
		String operate = request.getParameter("operate");
		if ("check".equals(operate)) {
			whereSql.append(" AND a.status IN ('1','3','5','7')");
		} else {
			whereSql.append(" AND a.status IN ('0','2','4','6')");
		}
		if (isNotEmpty(request.getParameter("id"))) {
			whereSql.append(" AND id = '" + request.getParameter("id") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNm"))) {
			whereSql.append(" AND legal_nm = '"
					+ request.getParameter("mchtNm") + "' ");
		}
		String order = "ORDER BY create_time";
		String sql = "SELECT a.ID,b.BRH_NAME,a.STATUS,a.MCHT_NM,a.LEGAL_NM,create_time FROM TBL_MCHT_NET_TMP a,TBL_BRH_INFO b "
				+ whereSql.toString() + order;
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_NET_TMP a,TBL_BRH_INFO b "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户限额维护信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtFeeInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		StringBuffer whereSql1 = new StringBuffer();
		whereSql.append("AND a.mcht_cd=b.MCHT_NO  ");
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql.append(" AND MCHT_CD = '"
					+ request.getParameter("mchntId") + "'");
		}
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "select a.mcht_cd, b.MCHT_NM,a.txn_num, a.card_type, a.day_num,a.day_amt, "
				+ "a.day_single,a.mon_num,a.mon_amt from cst_mcht_fee_inf a,TBL_MCHT_BASE_INF_TMP b WHERE b.MCHT_STATUS='0' and b.BANK_NO IN "
				+ operator.getBrhBelowId();
		String countSql = "select count(*) from cst_mcht_fee_inf a,TBL_MCHT_BASE_INF_TMP b WHERE b.MCHT_STATUS='0' and b.BANK_NO IN "
				+ operator.getBrhBelowId();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户限额维护信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtFeeInf2(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		StringBuffer whereSql1 = new StringBuffer();
		whereSql.append("AND a.mcht_cd=b.MCHT_NO  ");
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql.append(" AND MCHT_CD = '"
					+ request.getParameter("mchntId") + "'");
		}
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "select a.mcht_cd, b.MCHT_NM,a.txn_num, a.card_type, a.day_num,a.day_amt, "
				+ "a.day_single,a.mon_num,a.mon_amt from cst_mcht_fee_inf a,TBL_MCHT_BASE_INF b WHERE b.MCHT_STATUS='0' and b.BANK_NO IN "
				+ operator.getBrhBelowId();
		String countSql = "select count(*) from cst_mcht_fee_inf a,TBL_MCHT_BASE_INF b WHERE b.MCHT_STATUS='0' and b.BANK_NO IN "
				+ operator.getBrhBelowId();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql.toString());
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户MCC列表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntMccInfo(int begin, HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND A.MCHNT_TP_GRP = '"
					+ request.getParameter("mchtGrp") + "' ";
		}
		if (isNotEmpty(request.getParameter("mcc"))) {
			whereSql += " AND A.MCHNT_TP = '" + request.getParameter("mcc")
					+ "' ";
		}

		Object[] ret = new Object[2];
		String sql = "SELECT MCHNT_TP,"
				+ "A.mchnt_tp_grp||(SELECT ' - '||B.descr FROM tbl_inf_mchnt_tp_grp B WHERE A.mchnt_tp_grp= B.mchnt_tp_grp) AS MCHTGROUP,"
				+ "A.DESCR,A.REC_ST,A.disc_cd FROM TBL_INF_MCHNT_TP A "
				+ whereSql + " order by A.mchnt_tp_grp,A.MCHNT_TP ";
		String countSql = "SELECT COUNT(*) FROM TBL_INF_MCHNT_TP A " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 到真实表中查询正常商户的信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoTrue(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "SELECT MCHT_NO,MCHT_CN_ABBR,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,MCHT_STATUS FROM TBL_MCHT_BASE_INF WHERE BANK_NO IN "
				+ operator.getBrhBelowId()
				+ " AND MCHT_STATUS ='0' ORDER BY  MCHT_NO";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF WHERE BANK_NO IN "
				+ operator.getBrhBelowId() + " AND MCHT_STATUS ='0'";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfo(int begin, HttpServletRequest request) {

		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND BANK_NO = '" + request.getParameter("brhId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("idmchtGroupFlag"))) {
			whereSql += " AND MCHT_GROUP_FLAG= '"
					+ request.getParameter("idmchtGroupFlag") + "' ";
		} else {
			whereSql += " AND BANK_NO IN " + operator.getBrhBelowId() + " ";
		}
		whereSql += "AND MCHT_STATUS IN ('0','2','4','6') ";
		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(rec_crt_ts,1,8),MCHT_STATUS,nvl(B.TERM_COUNT,0) FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(*) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY  MCHT_NO";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户维护查询商户信息TMP
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoTmp(int begin, HttpServletRequest request) {

		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("rislLvl"))) {
			whereSql += " AND RISL_LVL = '" + request.getParameter("rislLvl")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND BANK_NO in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id = '"
					+ request.getParameter("brhId")
					+ "' connect by prior  BRH_ID = UP_BRH_ID)";
		} else {
			whereSql += " AND BANK_NO IN " + operator.getBrhBelowId() + " ";
		}
		if (isNotEmpty(request.getParameter("mchtGroupFlag"))) {
			whereSql += " AND MCHT_GROUP_FLAG = '"
					+ request.getParameter("mchtGroupFlag") + "' ";
		}
		if (isNotEmpty(request.getParameter("connType"))) {
			whereSql += " AND CONN_TYPE = '" + request.getParameter("connType")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mcc"))) {
			whereSql += " AND mcc = '" + request.getParameter("mcc") + "' ";
		}
		if (isNotEmpty(request.getParameter("licenceNo"))) {
			whereSql += " AND licence_no like '%"
					+ request.getParameter("licenceNo") + "%' ";
		}
		if (isNotEmpty(request.getParameter("termTmpCount"))) {
			if ("0".equals(request.getParameter("termTmpCount"))) {
				whereSql += " AND MCHT_NO not in ( select f.MCHT_CD from TBL_TERM_INF_TMP f ) ";
			} else {
				whereSql += " AND MCHT_NO  in ( select f.MCHT_CD from TBL_TERM_INF_TMP f ) ";
			}
		}
		// whereSql += "AND MCHT_STATUS IN ('0','B','1','3','5','6','8','9') ";
		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,mcht_lvl,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(rec_crt_ts,1,8) AS V1,MCHT_STATUS,"
				+ "nvl((select count(*) from TBL_TERM_INF_TMP B WHERE A.MCHT_NO = B.MCHT_CD ),0) AS V2,CRT_OPR_ID,UPD_OPR_ID,"
				+ "SUBSTR(REC_UPD_TS,1,8)||''||SUBSTR(REC_UPD_TS,9,2)||''||SUBSTR(REC_UPD_TS,11,2)||''||SUBSTR(REC_UPD_TS,13,2) AS V3,MCHT_GROUP_FLAG,"
				+ "  a.RISL_LVL||(select distinct '-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISL_LVL) as risk_lvl_name,"
				+ " nvl((select count(*) from TBL_TERM_INF_TMP c WHERE a.MCHT_NO = c.MCHT_CD ),0) AS term_tmp_count, g.REFUSE_INFO "
				+ " FROM TBL_MCHT_BASE_INF_TMP A left join (select * from TBL_MCHNT_REFUSE where TXN_TIME in (select max(TXN_TIME) from TBL_MCHNT_REFUSE group by MCHNT_ID)) g ON a.MCHT_NO = g.MCHNT_ID "
				+ whereSql + " ORDER BY rec_crt_ts desc";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP A "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 直联商户信息info all ctz
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCupInfo(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String whereSql = " WHERE crt_opr_id in (select opr_id from tbl_opr_info where brh_id in "
				+ operator.getBrhBelowId() + ") ";

		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchtNo")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND mcht_status = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtType"))) {
			whereSql += " AND mchnt_srv_tp = '"
					+ request.getParameter("mchtType") + "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND substr(crt_ts,0,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND substr(crt_ts,0,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("startDateU"))) {
			whereSql += " AND substr(upd_ts,0,8) >= '"
					+ request.getParameter("startDateU") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDateU"))) {
			whereSql += " AND substr(upd_ts,0,8) <= '"
					+ request.getParameter("endDateU") + "' ";
		}
		Object[] ret = new Object[2];

		String sql = "SELECT mcht_no,mcht_nm,mcht_status,mchnt_srv_tp,crt_ts,upd_ts,"
				+ "(case when mcht_status != 0 AND crt_ts = upd_ts then 'I' when mcht_status != 0 AND crt_ts < upd_ts then 'U' WHEN  mcht_status = 0 THEN 'D' END) AS insetFlag "
				+ " FROM tbl_mcht_cup_info "
				+ whereSql.toString()
				+ " ORDER BY MCHT_NO,mchnt_srv_tp,mcht_status,crt_ts ";
		String countSql = "SELECT COUNT(*) FROM tbl_mcht_cup_info "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 直联商户信息tmp all ctz
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCupInfoTmp(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String whereSql = " WHERE crt_opr_id in (select opr_id from tbl_opr_info where brh_id in "
				+ operator.getBrhBelowId() + ") ";

		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchtNo")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND mcht_status = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtType"))) {
			whereSql += " AND mchnt_srv_tp = '"
					+ request.getParameter("mchtType") + "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND substr(crt_ts,0,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND substr(crt_ts,0,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		Object[] ret = new Object[2];

		String sql = "SELECT mcht_no,mcht_nm,mcht_status,mchnt_srv_tp,crt_ts  "
				+ " FROM tbl_mcht_cup_info_tmp  " + whereSql.toString()
				+ " ORDER BY MCHT_NO,mchnt_srv_tp,mcht_status,crt_ts ";
		String countSql = "SELECT COUNT(*) FROM tbl_mcht_cup_info_tmp "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 直联商户审核 ctz
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCupInfoExa(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String whereSql = " WHERE mcht_status in('9','7','5','3','H') and crt_opr_id in (select opr_id from tbl_opr_info where brh_id in "
				+ operator.getBrhBelowId() + ") ";

		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchtNo")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND mcht_status = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtType"))) {
			whereSql += " AND mchnt_srv_tp = '"
					+ request.getParameter("mchtType") + "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND substr(crt_ts,0,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND substr(crt_ts,0,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		Object[] ret = new Object[2];

		String sql = "SELECT mcht_no,mcht_nm,mcht_status,mchnt_srv_tp,crt_ts  "
				+ " FROM tbl_mcht_cup_info_tmp  " + whereSql.toString()
				+ " ORDER BY MCHT_NO,mchnt_srv_tp,mcht_status,crt_ts ";
		String countSql = "SELECT COUNT(*) FROM tbl_mcht_cup_info_tmp "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 直联商户信息导入 ctz
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtCupInf(int begin, HttpServletRequest request) {

		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND STAT = '" + request.getParameter("mchtStatus")
					+ "' ";
		}
		/*
		 * if (isNotEmpty(request.getParameter("mchtGrp"))) { whereSql +=
		 * " AND MCHT_GRP = '" + request.getParameter("mchtGrp") + "' "; } if
		 * (isNotEmpty(request.getParameter("startDate"))) { whereSql +=
		 * " AND substr(rec_crt_ts,1,8) >= '" +
		 * request.getParameter("startDate") + "' "; } if
		 * (isNotEmpty(request.getParameter("endDate"))) { whereSql +=
		 * " AND substr(rec_crt_ts,1,8) <= '" + request.getParameter("endDate")
		 * + "' "; }
		 */
		// if (isNotEmpty(request.getParameter("brhId"))) {
		// whereSql += " AND INST_ID = '" + request.getParameter("brhId")
		// + "' ";
		// }
		/*
		 * if(isNotEmpty(request.getParameter("mchtGroupFlag"))){ whereSql +=
		 * " AND MCHT_GROUP_FLAG = '" + request.getParameter("mchtGroupFlag") +
		 * "' "; } if(isNotEmpty(request.getParameter("connType"))){ whereSql +=
		 * " AND CONN_TYPE = '" + request.getParameter("connType") + "' "; }
		 */
		// whereSql += "AND MCHT_STATUS IN ('0','B','1','3','5','6','8','9') ";
		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,ACQ_INST_ID,ACQ_INST_CD,MCHT_NM,MCHT_SHORT_CN,"
				+ "STLM_INS_ID,MCHT_TYPE,STAT,ACQ_AREA_CD,MCHT_TP_GRP,MCC,SETTLE_AREA_CD,"
				+ "LICENCE_NO,CONTACT_ADDR,MCHT_PERSON,COMM_TEL,MANAGER,IDENTITY_NO,ADDR,"
				+ "LICENCE_STAT,MCHT_STLM_ACCT,MCHT_ACCT_NM,SETTLE_BANK_NM,SETTLE_BANK_NO,"
				+ "APPLY_DATE "
				+ " FROM tbl_mcht_cup_inf  "
				+ whereSql.toString() + " ORDER BY MCHT_NO ";
		String countSql = "SELECT COUNT(*) FROM tbl_mcht_cup_inf "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询全部商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoAll(int begin, HttpServletRequest request) {
		String whereSql = " WHERE a.MCHT_STATUS != 'D' ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND a.MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("rislLvl"))) {
			whereSql += " AND a.RISL_LVL = '" + request.getParameter("rislLvl")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			String mchtStatus = request.getParameter("mchtStatus");
			if (mchtStatus.length() > 1){
				whereSql += " AND a.MCHT_STATUS in ("
						+ request.getParameter("mchtStatus") + ") ";
			} else {
				whereSql += " AND a.MCHT_STATUS = '"
						+ request.getParameter("mchtStatus") + "' ";
			}
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND a.MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND substr(a.rec_crt_ts,1,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND substr(a.rec_crt_ts,1,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND a.BANK_NO in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id = '"
					+ request.getParameter("brhId")
					+ "' connect by prior  BRH_ID = UP_BRH_ID)";
		} else {
			whereSql += " AND a.BANK_NO IN " + operator.getBrhBelowId() + " ";
		}
		if (isNotEmpty(request.getParameter("mchtGroupFlag"))) {
			whereSql += " AND a.MCHT_GROUP_FLAG= '"
					+ request.getParameter("mchtGroupFlag") + "' ";
		}
		if (isNotEmpty(request.getParameter("connType"))) {
			whereSql += " AND a.CONN_TYPE = '" + request.getParameter("connType")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mcc"))) {
			whereSql += " AND a.mcc = '" + request.getParameter("mcc") + "' ";
		}
		if (isNotEmpty(request.getParameter("licenceNo"))) {
			whereSql += " AND a.licence_no like '%"
					+ request.getParameter("licenceNo") + "%' ";
		}
		if (isNotEmpty(request.getParameter("termTmpCount"))) {
			if ("0".equals(request.getParameter("termTmpCount"))) {
				whereSql += " AND a.MCHT_NO not in ( select f.MCHT_CD from TBL_TERM_INF_TMP f ) ";
			} else {
				whereSql += " AND a.MCHT_NO  in ( select f.MCHT_CD from TBL_TERM_INF_TMP f ) ";
			}
		}
		if (isNotEmpty(request.getParameter("integral"))) {
			whereSql += " AND m.integral = '" + request.getParameter("integral") + "' ";
		}
		if (isNotEmpty(request.getParameter("bankStatement"))) {
			whereSql += " AND m.bank_statement = '" + request.getParameter("bankStatement") + "' ";
		}
		if (isNotEmpty(request.getParameter("compliance"))) {
			whereSql += " AND m.compliance = '" + request.getParameter("compliance") + "' ";
		}
		if (isNotEmpty(request.getParameter("country"))) {
			whereSql += " AND m.country = '" + request.getParameter("country") + "' ";
		}
		Object[] ret = new Object[2];

		String sql = "SELECT (SELECT (d.CREATE_NEW_NO||'-'||d.BRH_NAME) from TBL_BRH_INFO d WHERE d.BRH_ID=a.BANK_NO)as bankNo,a.BANK_NO as brhId,a.MCHT_NO,a.MCHT_NM,a.mcht_lvl,a.ENG_NAME,a.MCC,a.LICENCE_NO,a.ADDR,a.POST_CODE,"
				+ "a.COMM_EMAIL,a.MANAGER,a.CONTACT,a.COMM_TEL,substr(a.rec_crt_ts,1,8) as v1,"
				+ "a.MCHT_STATUS,nvl((select count(*)  from TBL_TERM_INF_TMP b where A.MCHT_NO = B.MCHT_CD ),0) as v2,"
				+ "a.CRT_OPR_ID,a.UPD_OPR_ID,SUBSTR(a.REC_UPD_TS,1,8)||''||SUBSTR(a.REC_UPD_TS,9,2)||''||SUBSTR(a.REC_UPD_TS,11,2)||''||SUBSTR(a.REC_UPD_TS,13,2) as v3,a.MCHT_GROUP_FLAG,"
				+ "  case a.RISL_LVL when '0' then '' else a.RISL_LVL ||(select distinct '-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISL_LVL) end as risk_lvl_name,"
				+ " nvl((select count(*) from TBL_TERM_INF_TMP c WHERE a.MCHT_NO = c.MCHT_CD ),0) AS term_tmp_count, g.REFUSE_INFO "
				+"  ,M.INTEGRAL,M.BANK_STATEMENT,M.COUNTRY,M.COMPLIANCE "
				+ " FROM TBL_MCHT_BASE_INF_TMP_TMP A left join (select * from TBL_MCHNT_REFUSE where TXN_TIME in (select max(TXN_TIME) from TBL_MCHNT_REFUSE group by MCHNT_ID)) g ON a.MCHT_NO = g.MCHNT_ID "
				+"  left join TBL_MCHT_SETTLE_INF_TMP_TMP M on M.MCHT_NO = A.MCHT_NO"
				+ whereSql + " ORDER BY a.rec_upd_ts desc";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP_TMP a left join TBL_MCHT_SETTLE_INF_TMP_TMP M on M.MCHT_NO = A.MCHT_NO "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询存量商户开户
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntOpenInfoAll(int begin,
			HttpServletRequest request) {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		whereSql.append(" and mcht_function is null ");
		whereSql.append(" and mcht_status !='8' ");

		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,mcht_lvl,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(rec_crt_ts,1,8) as v1,"
				+ "MCHT_STATUS,nvl((select count(*)  from TBL_TERM_INF b where A.MCHT_NO = B.MCHT_CD ),0) as v2,"
				+ "CRT_OPR_ID,UPD_OPR_ID,SUBSTR(REC_UPD_TS,1,8)||''||SUBSTR(REC_UPD_TS,9,2)||''||SUBSTR(REC_UPD_TS,11,2)||''||SUBSTR(REC_UPD_TS,13,2) as v3,MCHT_GROUP_FLAG,"
				+ "  a.RISL_LVL||(select distinct '-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISL_LVL) as risk_lvl_name,"
				+ " nvl((select count(*) from TBL_TERM_INF_TMP c WHERE a.MCHT_NO = c.MCHT_CD ),0) AS term_tmp_count, g.REFUSE_INFO "
				+ " FROM TBL_MCHT_BASE_INF A left join (select * from TBL_MCHNT_REFUSE where TXN_TIME in (select max(TXN_TIME) from TBL_MCHNT_REFUSE group by MCHNT_ID)) g ON a.MCHT_NO = g.MCHNT_ID "
				+ whereSql + " ORDER BY rec_crt_ts desc";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF a "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询全部商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntTmpInfoAll(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		whereSql += " AND BANK_NO IN " + operator.getBrhBelowId() + " ";
		if (isNotEmpty(request.getParameter("queryMchtNm"))) {
			whereSql += " AND MCHT_NM like '%"
					+ request.getParameter("queryMchtNm") + "%' ";
		}

		if (isNotEmpty(request.getParameter("queryLicenceNo"))) {
			whereSql += " AND LICENCE_NO like '%"
					+ request.getParameter("queryLicenceNo") + "%' ";
		}
		if (isNotEmpty(request.getParameter("queryFaxNo"))) {
			whereSql += " AND FAX_NO like '%"
					+ request.getParameter("queryFaxNo") + "%' ";
		}
		if (isNotEmpty(request.getParameter("queryManager"))) {
			whereSql += " AND MANAGER like '%"
					+ request.getParameter("queryManager") + "%' ";
		}

		if (isNotEmpty(request.getParameter("queryStartDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) >= '"
					+ request.getParameter("queryStartDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("queryEndDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) <= '"
					+ request.getParameter("queryEndDate") + "' ";
		}

		if (isNotEmpty(request.getParameter("queryCrtOprId"))) {
			whereSql += " AND CRT_OPR_ID like '%"
					+ request.getParameter("queryCrtOprId") + "%' ";
		}
		if (isNotEmpty(request.getParameter("queryMchtStatus"))) {
			whereSql += " AND MCHT_STATUS= '"
					+ request.getParameter("queryMchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("queryBankNo"))) {
			whereSql += " AND BANK_NO = '"
					+ request.getParameter("queryBankNo") + "' ";
		}

		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,mcht_lvl,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(rec_crt_ts,1,8) as v1,"
				+ "MCHT_STATUS,"
				+ "(select b.BRH_ID||'-'||b.BRH_NAME from TBL_BRH_INFO b where b.BRH_ID=a.BANK_NO) as bankNoName ,"
				+ "CRT_OPR_ID,UPD_OPR_ID,SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) as v3 ,fax_no,MCHT_GROUP_FLAG,RESERVED  FROM "
				+ "TBL_MCHT_BASE_INF_TMP_TMP a "
				+ whereSql
				+ " ORDER BY rec_crt_ts desc";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP_TMP a "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询全部渠道商户入网审核信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntNetworkCheckInfo(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		whereSql += " AND BANK_NO IN " + operator.getBrhBelowId() + " ";
		if (isNotEmpty(request.getParameter("mchtNm"))) {
			whereSql += " AND MCHT_NM like '%" + request.getParameter("mchtNm")
					+ "%' ";
		}
		if (isNotEmpty(request.getParameter("licenceNo"))) {
			whereSql += " AND LICENCE_NO like '%"
					+ request.getParameter("licenceNo") + "%' ";
		}
		if (isNotEmpty(request.getParameter("faxNo"))) {
			whereSql += " AND FAX_NO like '%" + request.getParameter("faxNo")
					+ "%' ";
		}
		if (isNotEmpty(request.getParameter("manager"))) {
			whereSql += " AND MANAGER like '%"
					+ request.getParameter("manager") + "%' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("crtOprId"))) {
			whereSql += " AND CRT_OPR_ID like '%"
					+ request.getParameter("crtOprId") + "%' ";
		}
		// if (isNotEmpty(request.getParameter("mchtStatus"))) {
		whereSql += " AND MCHT_STATUS in ( '"
				+ TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK + "' ,  '"
				+ TblMchntInfoConstants.MCHNT_ST_MODI_UNCK + "' ) ";
		// }
		if (isNotEmpty(request.getParameter("bankNo"))) {
			whereSql += " AND BANK_NO = '" + request.getParameter("bankNo")
					+ "' ";
		}

		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,mcht_lvl,ENG_NAME,"
				+ "(select b.CREATE_NEW_NO||'-'||b.BRH_NAME from TBL_BRH_INFO b where b.BRH_ID=a.BANK_NO) as bankNoName,"
				+ "MCC,LICENCE_NO,FAX_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(rec_crt_ts,1,8) as v1,"
				+ "MCHT_STATUS,"
				+ "CRT_OPR_ID,UPD_OPR_ID,SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) as v3 ,MCHT_GROUP_FLAG,RESERVED FROM "
				+ "TBL_MCHT_BASE_INF_TMP_TMP a " + whereSql
				+ " ORDER BY rec_crt_ts desc";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP_TMP a "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询全部渠道商户入网维护信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntUpdateInfo(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		whereSql += " AND BANK_NO IN " + operator.getBrhBelowId() + " ";
		if (isNotEmpty(request.getParameter("mchtNm"))) {
			whereSql += " AND MCHT_NM like '%" + request.getParameter("mchtNm")
					+ "%' ";
		}
		if (isNotEmpty(request.getParameter("licenceNo"))) {
			whereSql += " AND LICENCE_NO like '%"
					+ request.getParameter("licenceNo") + "%' ";
		}
		if (isNotEmpty(request.getParameter("faxNo"))) {
			whereSql += " AND FAX_NO like '%" + request.getParameter("faxNo")
					+ "%' ";
		}
		if (isNotEmpty(request.getParameter("manager"))) {
			whereSql += " AND MANAGER like '%"
					+ request.getParameter("manager") + "%' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND substr(rec_crt_ts,1,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("crtOprId"))) {
			whereSql += " AND CRT_OPR_ID like '%"
					+ request.getParameter("crtOprId") + "%' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS= '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("bankNo"))) {
			whereSql += " AND BANK_NO = '" + request.getParameter("bankNo")
					+ "' ";
		}

		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,mcht_lvl,ENG_NAME,"
				+ "(select b.BRH_ID||'-'||b.BRH_NAME from TBL_BRH_INFO b where b.BRH_ID=a.BANK_NO) as bankNoName ,"
				+ "MCC,LICENCE_NO,FAX_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(rec_crt_ts,1,8) as v1,"
				+ "MCHT_STATUS,"
				+ "CRT_OPR_ID,UPD_OPR_ID,SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) as v3 ,MCHT_GROUP_FLAG ,RESERVED FROM "
				+ "TBL_MCHT_BASE_INF_TMP_TMP a " + whereSql
				+ " ORDER BY rec_crt_ts desc";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP_TMP a "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询待审核商户信息（初审）
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCheckInfoFirst(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		whereSql += " AND BANK_NO IN " + operator.getBrhBelowId() + " ";

		whereSql += "AND MCHT_STATUS IN ('"
				+ TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_MODI_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_MODI_UNCK + "'," + "'"
				+ TblMchntInfoConstants.MCHNT_ST_STOP_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_RCV_UNCK + "','"
				/* +TblMchntInfoConstants.MCHNT_ST_BULK_IPT_UNCK */+ "','"
				+ TblMchntInfoConstants.MCHNT_ST_DEL_UNCK + "') ";
		Object[] ret = new Object[2];

		String sql = "SELECT (SELECT (c.CREATE_NEW_NO||'-'||c.BRH_NAME) from TBL_BRH_INFO c WHERE c.BRH_ID=a.BANK_NO)as bankNo,MCHT_NO,MCHT_NM,mcht_lvl,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(rec_crt_ts,1,8) as v1,MCHT_STATUS,"
				+ "nvl((select count(*) from TBL_TERM_INF_TMP b where A.MCHT_NO = B.MCHT_CD group by MCHT_CD),0) as v2,CRT_OPR_ID,PART_NUM,"
				+ "SUBSTR(REC_UPD_TS,1,8)||''||SUBSTR(REC_UPD_TS,9,2)||''||SUBSTR(REC_UPD_TS,11,2)||''||SUBSTR(REC_UPD_TS,13,2) as v3,MCHT_GROUP_FLAG,"
				+ "  a.RISL_LVL||(select distinct '-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISL_LVL) as risk_lvl_name FROM "
				+ "TBL_MCHT_BASE_INF_TMP a "
				+ whereSql
				+ "ORDER BY rec_upd_ts desc";

		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP"
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询待审核商户信息（终审）
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCheckInfo(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		whereSql += " AND BANK_NO IN " + operator.getBrhBelowId() + " ";

		whereSql += "AND MCHT_STATUS IN ('"
				+ TblMchntInfoConstants.MCHNT_ST_NEW_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_MODI_UNCK_SECOND + "','"
				+ TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_MODI_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_STOP_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_RCV_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_BULK_IPT_UNCK + "','"
				+ TblMchntInfoConstants.MCHNT_ST_DEL_UNCK + "') ";
		Object[] ret = new Object[2];

		String sql = "SELECT (SELECT (c.CREATE_NEW_NO||'-'||c.BRH_NAME) from TBL_BRH_INFO c WHERE c.BRH_ID=a.BANK_NO)as bankNo,MCHT_NO,MCHT_NM,mcht_lvl,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,substr(rec_crt_ts,1,8) as v1,MCHT_STATUS,"
				+ "nvl((select count(*) from TBL_TERM_INF_TMP b where A.MCHT_NO = B.MCHT_CD group by MCHT_CD),0) as v2,CRT_OPR_ID,PART_NUM,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) as v3,MCHT_GROUP_FLAG FROM "
				+ "TBL_MCHT_BASE_INF_TMP a "
				+ whereSql
				+ "ORDER BY rec_upd_ts desc";

		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP"
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端库存查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermManagementInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String termNo = request.getParameter("termNo");
		String mchnNo = request.getParameter("mchnNo");
		String batchNo = request.getParameter("batchNo");
		String state = request.getParameter("state");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		// Operator operator = (Operator) request.getSession().getAttribute(
		// Constants.OPERATOR_INFO);
		StringBuffer sql = new StringBuffer(
				"select a.TERM_NO,a.STATE,a.MANUFATURER,a.PRODUCT_CD,a.TERMINAL_TYPE,a.TERM_TYPE, "
						+ " a.MECH_NO || '-' || b.MCHT_NM ,a.BATCH_NO,a.STOR_OPR_ID,"
						+ " a.STOR_DATE,a.RECI_OPR_ID,a.RECI_DATE,a.BACK_OPR_ID,a.BANK_DATE,a.INVALID_OPR_ID,a.INVALID_DATE,a.SIGN_OPR_ID,"
						+ " a.SIGN_DATE,a.MISC,a.PIN_PAD "
						+ " from TBL_TERM_MANAGEMENT a left join    TBL_MCHT_BASE_INF b  on a.MECH_NO = b.MCHT_NO ");
		StringBuffer where = new StringBuffer(" where 1 = 1  ");
		if (state != null && !state.trim().equals("")) {
			if (state.length() == 1)
				where.append("and a.STATE ='").append(state).append("'");
			if (state.length() >= 2)
				where.append("and a.STATE in (").append(state).append(")");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			where.append("and a.TERM_NO='").append(termNo).append("'");
		}
		if (mchnNo != null && !mchnNo.trim().equals("")) {
			where.append("and a.MECH_NO='").append(mchnNo).append("'");
		}
		if (batchNo != null && !batchNo.trim().equals("")) {
			where.append("and a.BATCH_NO='").append(batchNo).append("'");
		}
		if (startTime != null && !startTime.trim().equals("")) {
			where.append("and a.LAST_UPD_TS >=").append("to_date('")
					.append(startTime.substring(0, 10)).append("','yyyy-mm-dd")
					.append("')");
		}
		if (endTime != null && !endTime.trim().equals("")) {
			where.append("and a.LAST_UPD_TS <=").append("to_date('")
					.append(endTime.substring(0, 10).concat(" 23:59:59"))
					.append("','yyyy-mm-dd hh24:mi:ss").append("')");
		}
		where.append(" order by a.BATCH_NO, a.TERM_NO , a.PRODUCT_CD ");
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_MANAGEMENT a "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.append(where).toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具维修报废申请
	 */
	public static Object[] getTermManagementInfoApp(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String termNo = request.getParameter("termNo");
		// String mchnNo = request.getParameter("mchnNo");
		String batchNo = request.getParameter("batchNo");
		String productCd = request.getParameter("productCd");
		String terminalType = request.getParameter("terminalType");
		String termType = request.getParameter("termType");
		// String brhId = request.getParameter("brhId");
		// String state = request.getParameter("state");
		// String termIdId = request.getParameter("termIdId");
		// String startTime = request.getParameter("startTime");
		// String endTime = request.getParameter("endTime");
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		// String brhId = operator.getOprBrhId().substring(0, 2);

		StringBuffer sql = new StringBuffer(
				"select "
						+ "(select trim(t.brh_id)||' - '||trim(t.brh_name) from tbl_brh_info t where trim(t.brh_id)=trim(a.brh_id)) as brh_id_name,"
						+ "TERM_NO,(select term_id from tbl_term_inf_tmp b where  a.TERM_NO=b.term_id_id) as termid,brh_id,STATE,MANUFATURER,PRODUCT_CD,TERMINAL_TYPE,TERM_TYPE,MECH_NO,BATCH_NO,STOR_OPR_ID,")
				.append("STOR_DATE,RECI_OPR_ID,RECI_DATE,BACK_OPR_ID,BANK_DATE,INVALID_OPR_ID,INVALID_DATE,SIGN_OPR_ID,SIGN_DATE,MISC,"
						+ "PIN_PAD,LAST_UPD_OPR_ID,LAST_UPD_TS from TBL_TERM_MANAGEMENT a ");

		StringBuffer where = new StringBuffer(" ");
		where = new StringBuffer(
				" where BRH_ID in "
						+ operator.getBrhBelowId()
						+ " and STATE in('0','1')  and SIGN_OPR_ID in('00','01','02','03','04') ");
		// if ("99".equals(brhId))
		// where = new StringBuffer(" where 1=1 ");
		// else
		// where = new StringBuffer(" where substr(TERM_NO,2,2)='" + brhId
		// + "' ");
		// if (state != null && !state.trim().equals("")) {
		// if (state.length() == 1)
		// where.append("and STATE ='").append(state).append("'");
		// if (state.length() >= 2)
		// where.append("and STATE in (").append(state).append(")");
		// }
		if (termNo != null && !termNo.trim().equals("")) {
			where.append("and TERM_NO like '%").append(termNo).append("%'");
		}
		if (productCd != null && !productCd.trim().equals("")) {
			where.append("and PRODUCT_CD like '%").append(productCd)
					.append("%'");
		}
		if (batchNo != null && !batchNo.trim().equals("")) {
			where.append("and BATCH_NO like '%").append(batchNo).append("%'");
		}
		if (terminalType != null && !terminalType.trim().equals("")) {
			where.append("and TERMINAL_TYPE like '%").append(terminalType)
					.append("%'");
		}
		if (termType != null && !termType.trim().equals("")) {
			where.append("and TERM_TYPE='").append(termType).append("'");
		}

		// if (startTime != null && !startTime.trim().equals("")) {
		// where.append(" and LAST_UPD_TS >='").append(startTime.split("T")[0].replaceAll("-",
		// "")).append("'");
		// // where.append("and LAST_UPD_TS >=").append("to_date('").append(
		// // startTime.substring(0, 10)).append("','yyyy-mm-dd").append(
		// // "')");
		// }
		// if (endTime != null && !endTime.trim().equals("")) {
		// where.append(" and LAST_UPD_TS <='").append(endTime.split("T")[0].replaceAll("-",
		// "")).append("'");
		// // where.append("and LAST_UPD_TS <=").append("to_date('").append(
		// // endTime.substring(0, 10).concat(" 23:59:59")).append(
		// // "','yyyy-mm-dd hh24:mi:ss").append("')");
		// }
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_MANAGEMENT "
				+ where.toString();
		List<Object[]> dataList = CommonFunction
				.getCommQueryDAO()
				.findBySQLQuery(
						sql.append(where).append(" order by BRH_ID").toString(),
						begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具维修报废申请
	 */
	public static Object[] getTermManagementInfoApp1(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String termNo = request.getParameter("termNo");
		String batchNo = request.getParameter("batchNo");
		String productCd = request.getParameter("productCd");
		String terminalType = request.getParameter("terminalType");
		String termType = request.getParameter("termType");
		String brhId = request.getParameter("brhId");
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer sql = new StringBuffer(
				"select "
						+ "(select trim(t.brh_id)||' - '||trim(t.brh_name) from tbl_brh_info t where trim(t.brh_id)=trim(a.SIGN_DATE)) as brh_id_name ,"
						+ "TERM_NO,"
						+ "(select term_id from tbl_term_inf_tmp b where  a.TERM_NO=b.term_id_id) as termid,"
						+ "brh_id,STATE,MANUFATURER,PRODUCT_CD,TERMINAL_TYPE,TERM_TYPE,MECH_NO,BATCH_NO,STOR_OPR_ID,")
				.append("STOR_DATE,RECI_OPR_ID,RECI_DATE,BACK_OPR_ID,BANK_DATE,INVALID_OPR_ID,INVALID_DATE,SIGN_OPR_ID,SIGN_DATE,MISC,"
						+ "PIN_PAD,LAST_UPD_OPR_ID,LAST_UPD_TS from TBL_TERM_MANAGEMENT a ");

		StringBuffer where = new StringBuffer(" ");
		where = new StringBuffer(" where SIGN_DATE in "
				+ operator.getBrhBelowId() + " and STATE in('0','1')  "
				+ " and SIGN_OPR_ID in('01','02') and SIGN_DATE !='"
				+ operator.getOprBrhId() + "' ");

		if (termNo != null && !termNo.trim().equals("")) {
			where.append("and TERM_NO like '%").append(termNo).append("%'");
		}
		if (productCd != null && !productCd.trim().equals("")) {
			where.append("and PRODUCT_CD like '%").append(productCd)
					.append("%'");
		}
		if (batchNo != null && !batchNo.trim().equals("")) {
			where.append("and BATCH_NO like '%").append(batchNo).append("%'");
		}
		if (terminalType != null && !terminalType.trim().equals("")) {
			where.append("and TERMINAL_TYPE like '%").append(terminalType)
					.append("%'");
		}
		if (termType != null && !termType.trim().equals("")) {
			where.append("and TERM_TYPE='").append(termType).append("'");
		}
		if (brhId != null && !brhId.trim().equals("")) {
			where.append("and SIGN_DATE='").append(brhId).append("'");
		}

		String countSql = "SELECT COUNT(*) FROM TBL_TERM_MANAGEMENT "
				+ where.toString();
		List<Object[]> dataList = CommonFunction
				.getCommQueryDAO()
				.findBySQLQuery(
						sql.append(where).append(" order by BRH_ID").toString(),
						begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端库存查询5
	 */
	public static Object[] getTermManagementInfo5(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String termNo = request.getParameter("termNo");
		String productCd = request.getParameter("productCd");
		String batchNo = request.getParameter("batchNo");

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select term_id,term_Id_Id,equip_inv_id,equip_inv_nm,b.sign_opr_id,b.MANUFATURER,b.TERMINAL_TYPE,b.TERM_TYPE,b.PRODUCT_CD,b.MECH_NO,b.BATCH_NO,b.MISC,"
				+ "b.PIN_PAD from tbl_term_inf a left outer join tbl_term_management b on(a.term_Id_Id = b.term_no) ";

		StringBuffer where = new StringBuffer(" ");
		where.append("where term_branch in " + operator.getBrhBelowId() + " ");

		if (isNotEmpty(termNo)) {
			where.append(" and TERM_NO='").append(termNo).append("'");
		}
		if (isNotEmpty(batchNo)) {
			where.append(" and BATCH_NO='").append(batchNo).append("'");
		}
		if (isNotEmpty(productCd)) {
			where.append(" and product_cd='").append(productCd).append("'");
		}

		where.append(" and equip_inv_id in('YY','NY','YN') ");
		sql = sql + where.toString();
		String countSql = "SELECT COUNT(*) FROM tbl_term_inf a left outer join tbl_term_management b on(a.term_Id_Id = b.term_no) "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端库存查询2
	 */
	public static Object[] getTermManagementInfo2(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String termNo = request.getParameter("termNo");
		String state = request.getParameter("state");
		String brhId = request.getParameter("brhId");
		String aimBrh = request.getParameter("aimBrh");
		String batchNo = request.getParameter("batchNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select a.TERM_NO,b.term_id,a.PIN_PAD,b.equip_inv_nm,a.BRH_ID,"
				+ "a.back_opr_id,a.STATE,a.MANUFATURER,a.PRODUCT_CD,a.TERMINAL_TYPE,a.TERM_TYPE,b.mcht_cd,(b.mcht_cd||' - '||c.mcht_nm)as mcht_no_name,a.BATCH_NO,STOR_OPR_ID,"
				+ "STOR_DATE,a.RECI_OPR_ID,a.RECI_DATE,a.BACK_OPR_ID,a.BANK_DATE,a.INVALID_OPR_ID,a.INVALID_DATE,a.SIGN_OPR_ID,a.SIGN_DATE,a.MISC,"
				+ "a.LAST_UPD_OPR_ID,a.LAST_UPD_TS,a.sign_opr_id from TBL_TERM_MANAGEMENT a left outer join tbl_term_inf b on(a.TERM_NO=b.term_id_id) "
				+ " left join tbl_mcht_base_inf c on c.mcht_no=b.mcht_cd  ";

		StringBuffer where = new StringBuffer(" ");
		where.append("where (a.BRH_ID in " + operator.getBrhBelowId()
				+ " or (STATE = " + TblTermManagementConstants.STAT_DOWN
				+ " and BACK_OPR_ID = '" + operator.getOprBrhId() + "')) ");
		if (isNotEmpty(state)) {
			where.append(" and STATE ='").append(state).append("'");
		}
		if (isNotEmpty(termNo)) {
			where.append(" and TERM_NO='").append(termNo).append("'");
		}
		if (isNotEmpty(batchNo)) {
			where.append(" and a.BATCH_NO='").append(batchNo).append("'");
		}
		if (isNotEmpty(brhId)) {
			where.append(" and a.brh_id='").append(brhId).append("'");
		}
		if (isNotEmpty(aimBrh)) {
			where.append(" and a.back_opr_id='").append(aimBrh).append("'");
		}
		if (isNotEmpty(startTime)) {
			where.append(" and substr(a.stor_date,0,8) >='" + startTime + "'");
		}
		if (isNotEmpty(endTime)) {
			where.append(" and substr(a.stor_date,0,8) <='" + endTime + "'");
		}
		sql = sql + where.toString() + " order by BATCH_NO,BRH_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_MANAGEMENT a left outer join tbl_term_inf b on(a.TERM_NO=b.term_id_id) "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具差错览
	 */
	public static Object[] getTermManagementInfoErrMain(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];

		String appId = request.getParameter("appId");
		String brhId = request.getParameter("brhId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select a.app_id,brh_id,app_date,back_opr_id,a.stat,"
				+ "(select brh_id||'-'||brh_name from  tbl_brh_info where brh_id=a.brh_id) as brh_id_name "
				+ "from tbl_term_management_app_main a,tbl_term_management_check b ";

		StringBuffer where = new StringBuffer(" ");
		where.append("where a.stat = '1' and brh_id in "
				+ operator.getBrhBelowId()
				+ " and a.app_id = b.app_id and substr(b.misc2,0,1) in('L','M') ");
		if (isNotEmpty(appId)) {
			where.append(" and a.app_id ='").append(appId).append("'");
		}
		if (isNotEmpty(brhId)) {
			where.append(" and brh_id='").append(brhId).append("'");
		}
		if (isNotEmpty(startDate)) {
			where.append(" and app_date >='").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			where.append(" and app_date <='").append(endDate).append("'");
		}

		sql = sql
				+ where.toString()
				+ " group by a.app_id,brh_id,app_date,back_opr_id,a.stat order by app_date,a.app_id";

		String countSql = "SELECT COUNT(distinct a.app_id) FROM tbl_term_management_app_main a,tbl_term_management_check b "
				+ where.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;

		return ret;
	}

	/**
	 * 机具差错览
	 */
	public static Object[] getTermManagementInfoErr(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];

		String appId = request.getParameter("appNo");

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select a.son_app_id,a.manufaturer,a.terminal_type,a.term_type,amount,acc_amount,"
				+ "(case substr(a.misc2,0,1) when 'L' then (acc_amount - substr(a.misc2,2))||'' when 'M' then (acc_amount + substr(a.misc2,2))||'' else acc_amount end) as inAmount,"
				+ "substr(a.misc2,0,1) as errType,substr(a.misc2,2) as errAmount,stat "
				+ "from tbl_term_management_check a ";

		StringBuffer where = new StringBuffer(" ");
		where.append("where a.app_id = " + appId + " ");

		sql = sql + where.toString() + " order by a.son_app_id";

		String countSql = "SELECT COUNT(*) FROM tbl_term_management_check a "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端库存申请查看
	 */
	public static Object[] getTermManagementAppInfo(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String appBrh = request.getParameter("appBrh");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select app_id,stat,brh_id,app_opr_id,"
				+ "app_date,back_opr_id,back_date,(select opr_tel from tbl_opr_info c where a.back_opr_id=c.opr_id) as tel "
				+ "from tbl_term_management_app_main a ";

		StringBuffer where = new StringBuffer(" ");
		where.append("where stat='1'");
		if (isNotEmpty(appBrh)) {
			where.append("  and app_opr_id in(select opr_id from tbl_opr_info e where e.brh_id = '"
					+ appBrh + "')");
		} else {
			where.append("  and app_opr_id in(select opr_id from tbl_opr_info e where e.brh_id  in"
					+ operator.getBrhBelowId() + ")");
		}
		if (isNotEmpty(startTime)) {
			where.append("  and app_date >= '" + startTime + "'");
		}
		if (isNotEmpty(endTime)) {
			where.append("  and app_date >= '" + endTime + "'");
		}
		sql = sql + where.toString() + " order by app_date,app_id,brh_id desc";
		String countSql = "SELECT COUNT(*) FROM tbl_term_management_app_main "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端库存查询3
	 */
	public static Object[] getTermManagementInfo3(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String termNo = request.getParameter("termNo");
		String batchNo = request.getParameter("batchNo");
		String productCd = request.getParameter("productCd");

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select TERM_NO,BRH_ID,STATE,MANUFATURER,PRODUCT_CD,TERMINAL_TYPE,TERM_TYPE,MECH_NO,BATCH_NO,STOR_OPR_ID,"
				+ "STOR_DATE,RECI_OPR_ID,RECI_DATE,BACK_OPR_ID,BANK_DATE,INVALID_OPR_ID,INVALID_DATE,SIGN_OPR_ID,SIGN_DATE,MISC,"
				+ "SUBSTR(PIN_PAD,0,1) AS PIN from TBL_TERM_MANAGEMENT ";

		StringBuffer where = new StringBuffer(" ");
		where.append("where substr(term_no,0,1) = 'T' and BRH_ID in "
				+ operator.getBrhBelowId());
		where.append(" and STATE ='")
				.append(TblTermManagementConstants.STAT_NEW).append("'");
		if (isNotEmpty(termNo)) {
			where.append(" and TERM_NO='").append(termNo).append("'");
		}
		if (isNotEmpty(batchNo)) {
			where.append(" and BATCH_NO='").append(batchNo).append("'");
		}
		if (isNotEmpty(productCd)) {
			where.append(" and product_cd='").append(productCd).append("'");
		}

		sql = sql + where.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_MANAGEMENT "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端库存查询3
	 */
	public static Object[] getTermManagementInfoPin(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String termNo = request.getParameter("termNo");
		String batchNo = request.getParameter("batchNo");
		String productCd = request.getParameter("productCd");
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select TERM_NO,BRH_ID,STATE,MANUFATURER,PRODUCT_CD,TERMINAL_TYPE,TERM_TYPE,MECH_NO,BATCH_NO,STOR_OPR_ID,"
				+ "STOR_DATE,RECI_OPR_ID,RECI_DATE,BACK_OPR_ID,BANK_DATE,INVALID_OPR_ID,INVALID_DATE,SIGN_OPR_ID,SIGN_DATE,MISC,"
				+ "SUBSTR(PIN_PAD,0,1) AS PIN from TBL_TERM_MANAGEMENT ";

		StringBuffer where = new StringBuffer(" ");
		where.append("where substr(term_no,0,1) = 'P' and BRH_ID in "
				+ operator.getBrhBelowId());
		where.append(" and STATE ='")
				.append(TblTermManagementConstants.STAT_NEW).append("'");
		if (isNotEmpty(termNo)) {
			where.append(" and TERM_NO='").append(termNo).append("'");
		}
		if (isNotEmpty(batchNo)) {
			where.append(" and BATCH_NO='").append(batchNo).append("'");
		}
		if (isNotEmpty(productCd)) {
			where.append(" and product_cd='").append(productCd).append("'");
		}

		sql = sql + where.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_MANAGEMENT "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具审核列表
	 */
	public static Object[] getTermManagementCheckInfo(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String appNo = request.getParameter("appNo");
		// String termType1 = request.getParameter("termType1");
		// String termType = request.getParameter("termType");
		// String state = request.getParameter("state");

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select t1.app_id,t1.son_app_id,t1.manufaturer,t1.terminal_type,t1.term_type,t1.amount,t1.acc_amount,t1.stat,t1.mic,t1.misc2 "
				+ " from tbl_term_management_check t1";

		StringBuffer where = new StringBuffer(" where  t1.app_id ='" + appNo
				+ "' ");
		// if (isNotEmpty(appNo)) {
		// where.append(" and t1.app_id like '%").append(appNo).append("%'");
		// }
		// if (isNotEmpty(termType1)) {
		// where.append(" and t1.terminal_type like '%").append(termType1).append("%'");
		// }
		// if (isNotEmpty(termType)) {
		// where.append(" and t1.term_type='").append(termType).append("'");
		// }
		// if (isNotEmpty(state)) {
		// where.append(" and t1.stat='").append(state).append("'");
		// }
		// if (isNotEmpty(startTime)) {
		// where.append(" and t1.app_date >='").append(startTime.split("T")[0].replaceAll("-",
		// "")).append("'");
		// }
		// if (isNotEmpty(endTime)) {
		// where.append(" and t1.app_date <='").append(endTime.split("T")[0].replaceAll("-",
		// "")).append("'");
		// }

		sql = sql + where.toString() + " order by t1.son_app_id  ";
		String countSql = "SELECT COUNT(*) FROM tbl_term_management_check t1 "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具审核列表
	 */
	public static Object[] getTermManagementCheckInfo3(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String appNo = operator.getOprBrhLvl();

		String sql = "select t1.app_id,t1.son_app_id,t1.manufaturer,t1.terminal_type,t1.term_type,t1.amount,t1.acc_amount,t1.stat,t1.mic,t1.misc2 "
				+ " from tbl_term_management_check t1";

		StringBuffer where = new StringBuffer(" where  t1.app_id ='" + appNo
				+ "' ");

		sql = sql + where.toString() + " order by t1.son_app_id  ";
		String countSql = "SELECT COUNT(*) FROM tbl_term_management_check t1 "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具审核列表
	 */
	public static Object[] getTermManagementCheckInfo3s(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String appNo = request.getParameter("appNo");
		;

		String sql = "select t1.app_id,t1.son_app_id,t1.manufaturer,t1.terminal_type,t1.term_type,t1.amount,t1.acc_amount,'',t1.mic,t1.misc2 "
				+ " from tbl_term_management_check t1";

		StringBuffer where = new StringBuffer(" where  t1.app_id ='" + appNo
				+ "' and t1.stat='0' ");

		sql = sql + where.toString() + " order by t1.son_app_id  ";
		String countSql = "SELECT COUNT(*) FROM tbl_term_management_check t1 "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具订单列表
	 */
	public static Object[] getTermManagementAppMainInfo(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String appNo = request.getParameter("appNo");
		String stat = request.getParameter("state");
		String brhId = request.getParameter("brhId");
		String appDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String now = CommonFunction.getCurrentDateTime();
		String threeMonth = null;
		int month = Integer.valueOf(now.substring(4, 6));
		if (month >= 3) {
			threeMonth = now.substring(0, 4)
					+ CommonFunction.fillString(
							String.valueOf(Integer.valueOf(month) - 3), '0', 2,
							false) + now.substring(6, 8);
		} else {
			if (month == 1) {
				threeMonth = String
						.valueOf(Integer.valueOf(now.substring(0, 4)) - 1)
						+ "10" + now.substring(6, 8);
			} else if (month == 2) {
				threeMonth = String
						.valueOf(Integer.valueOf(now.substring(0, 4)) - 1)
						+ "11" + now.substring(6, 8);
			}
		}

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select t1.app_id,t1.stat,t1.brh_id,(trim(t2.brh_id)||' - '||trim(t2.brh_name)) as brh_id_name ,"
				+ "t1.misc,t1.app_opr_id,t1.app_date,t1.back_opr_id,t1.back_date ,"
				+ "(select opr_mobile from tbl_opr_info where opr_id=t1.back_opr_id) as back_opr_mobile "
				+ " from tbl_term_management_app_main t1 left join tbl_brh_info t2 on trim(t1.brh_id)=trim(t2.brh_id) ";

		StringBuffer where = new StringBuffer(
				" where (stat = '0' OR app_date > '" + threeMonth + "') ");
		if (!"95599".equals(operator.getOprBrhId())) {
			where.append(" and t1.brh_id in " + operator.getBrhBelowId());
		}
		if (isNotEmpty(appNo)) {
			where.append(" and t1.app_id like '%").append(appNo).append("%'");
		}
		if (isNotEmpty(stat)) {
			where.append(" and t1.stat like '%").append(stat).append("%'");
		}
		if (isNotEmpty(brhId)) {
			where.append(" and t1.brh_id like '%").append(brhId).append("%'");
		}
		if (isNotEmpty(appDate)) {
			where.append(" and t1.app_date >='")
					.append(appDate.split("T")[0].replaceAll("-", ""))
					.append("'");
		}
		if (isNotEmpty(endDate)) {
			where.append(" and t1.app_date <='")
					.append(endDate.split("T")[0].replaceAll("-", ""))
					.append("'");
		}

		sql = sql + where.toString() + " order by t1.app_id desc ";
		String countSql = "SELECT COUNT(*) FROM tbl_term_management_app_main t1 "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具订单列表
	 */
	public static Object[] getTermManagementAppMainInfos(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String appNo = request.getParameter("appNo");
		String stat = request.getParameter("state");
		String brhId = request.getParameter("brhId");
		String appDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select t1.app_id,t1.stat,t1.brh_id,(trim(t2.brh_id)||' - '||trim(t2.brh_name)) as brh_id_name ,"
				+ "t1.misc,t1.app_opr_id,t1.app_date,t1.back_opr_id,t1.back_date ,"
				+ "(select opr_mobile from tbl_opr_info where opr_id=t1.back_opr_id) as back_opr_mobile "
				+ " from tbl_term_management_app_main t1 left join tbl_brh_info t2 on trim(t1.brh_id)=trim(t2.brh_id) ";

		StringBuffer where = new StringBuffer(" where 1=1 and t1.stat='0' ");
		if (!"95599".equals(operator.getOprBrhId())) {
			where.append(" and t1.brh_id in " + operator.getBrhBelowId()
					+ " and t1.brh_id !='" + operator.getOprBrhId() + "'");
		}
		if (isNotEmpty(appNo)) {
			where.append(" and t1.app_id like '%").append(appNo).append("%'");
		}
		if (isNotEmpty(stat)) {
			where.append(" and t1.stat like '%").append(stat).append("%'");
		}
		if (isNotEmpty(brhId)) {
			where.append(" and t1.brh_id like '%").append(brhId).append("%'");
		}
		if (isNotEmpty(appDate)) {
			where.append(" and t1.app_date >='")
					.append(appDate.split("T")[0].replaceAll("-", ""))
					.append("'");
		}
		if (isNotEmpty(endDate)) {
			where.append(" and t1.app_date <='")
					.append(endDate.split("T")[0].replaceAll("-", ""))
					.append("'");
		}

		sql = sql + where.toString() + " order by t1.app_id desc ";
		String countSql = "SELECT COUNT(*) FROM tbl_term_management_app_main t1 "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具订单列表
	 */
	public static Object[] getTermManagementAppMainInfos1(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String appNo = request.getParameter("appNo");
		String stat = request.getParameter("state");
		String brhId = request.getParameter("brhId");
		String appDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select t1.app_id,t1.stat,t1.brh_id,(trim(t2.brh_id)||' - '||trim(t2.brh_name)) as brh_id_name ,"
				+ "t1.misc,t1.app_opr_id,t1.app_date,t1.back_opr_id,t1.back_date ,"
				+ "(select opr_mobile from tbl_opr_info where opr_id=t1.back_opr_id) as back_opr_mobile "
				+ " from tbl_term_management_app_main t1 left join tbl_brh_info t2 on trim(t1.brh_id)=trim(t2.brh_id) ";

		StringBuffer where = new StringBuffer(" where 1=1 and t1.stat='1' ");
		if (!"95599".equals(operator.getOprBrhId())) {
			where.append(" and t1.brh_id in " + operator.getBrhBelowId());
		}
		if (isNotEmpty(appNo)) {
			where.append(" and t1.app_id like '%").append(appNo).append("%'");
		}
		if (isNotEmpty(stat)) {
			where.append(" and t1.stat like '%").append(stat).append("%'");
		}
		if (isNotEmpty(brhId)) {
			where.append(" and t1.brh_id like '%").append(brhId).append("%'");
		}
		if (isNotEmpty(appDate)) {
			where.append(" and t1.app_date >='")
					.append(appDate.split("T")[0].replaceAll("-", ""))
					.append("'");
		}
		if (isNotEmpty(endDate)) {
			where.append(" and t1.app_date <='")
					.append(endDate.split("T")[0].replaceAll("-", ""))
					.append("'");
		}

		sql = sql + where.toString() + " order by t1.app_id desc ";
		String countSql = "SELECT COUNT(*) FROM tbl_term_management_app_main t1 "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机具审核列表1
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermManagementCheckInfo1(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		String appNo = request.getParameter("appNo");
		String termType1 = request.getParameter("termType1");
		String termType = request.getParameter("termType");
		String state = request.getParameter("state");
		String brhId = request.getParameter("brhId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "select t1.app_id,t1.manufaturer,t1.terminal_type,t1.term_type,t1.amount,t1.acc_amount,t1.stat,t1.mic,t1.misc2,"
				+ "t1.app_opr_id,t1.app_date,t1.back_opr_id,t1.back_date,"
				+ "(select opr_mobile from tbl_opr_info where opr_id=t1.back_opr_id) as back_opr_mobile, "
				+ "(select t2.brh_id||' - '||t2.brh_name from  tbl_brh_info t2  where t2.brh_id=t1.misc2) as brh_id "
				+ " from tbl_term_management_check t1";

		// CommonFunction.getStringBelowBrhIds(operator.getOprBrhId());
		StringBuffer where = new StringBuffer(
				" where  t1.stat='0' and t1.misc2 in "
						+ operator.getBrhBelowId() + " and t1.misc2!='"
						+ operator.getOprBrhId() + "' ");
		if (isNotEmpty(appNo)) {
			where.append(" and t1.app_id like '%").append(appNo).append("%'");
		}
		if (isNotEmpty(termType1)) {
			where.append(" and t1.terminal_type like '%").append(termType1)
					.append("%'");
		}
		if (isNotEmpty(termType)) {
			where.append(" and t1.term_type='").append(termType).append("'");
		}
		if (isNotEmpty(state)) {
			where.append(" and t1.stat='").append(state).append("'");
		}
		if (isNotEmpty(brhId)) {
			where.append(" and t1.misc2='").append(brhId).append("'");
		}
		if (isNotEmpty(startTime)) {
			where.append(" and t1.app_date >='")
					.append(startTime.split("T")[0].replaceAll("-", ""))
					.append("'");
		}
		if (isNotEmpty(endTime)) {
			where.append(" and t1.app_date <='")
					.append(endTime.split("T")[0].replaceAll("-", ""))
					.append("'");
		}

		sql = sql + where.toString() + " order by t1.app_id desc ";
		String countSql = "SELECT COUNT(*) FROM tbl_term_management_check t1 "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得终端审核信息列表
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-18上午09:38:26
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermCheckInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "SELECT TERM_ID,MCHNT_NO,TERM_STA,IS_SUPP_IC,IS_SUPP_CREDIT,TERM_TYPE,CALL_TYPE,"
				+ "CALL_NO,BRH_ID,SEQUENCE_NO,KB_SEQUENCE_NO,V_TELLER,ENC_TYPE,BIND_NO FROM TBL_TERM_INF_TMP WHERE BRH_ID IN "
				+ operator.getBrhBelowId()
				+ " AND TERM_STA IN ('1','3','5','7') ORDER BY MCHNT_NO,TERM_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF_TMP WHERE BRH_ID IN "
				+ operator.getBrhBelowId()
				+ " AND TERM_STA IN ('1','3','5','7')";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户退回/拒绝原因查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:50:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntBackRefuseInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryMchtNo = request.getParameter("queryMchtNo");
		String queryOprId = request.getParameter("queryOprId");
		StringBuffer where = new StringBuffer(" ");
		where.append(" ");
		if (isNotEmpty(startDate)) {
			where.append(" and TXN_TIME >='").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			where.append(" and TXN_TIME <='").append(endDate).append("'");
		}
		if (isNotEmpty(queryMchtNo)) {
			where.append(" and MCHNT_ID = '").append(queryMchtNo).append("'");
		}
		if (isNotEmpty(queryOprId)) {
			where.append(" and OPR_ID = '").append(queryOprId).append("'");
		}

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String brhId = operator.getOprBrhId();
		String brhLev = operator.getOprBrhLvl();
		String sql = "";
		String countSql = "";
		// 如果是总行或者是分行可以看见其下属机构的商户回退或者拒绝的原因
		if (brhLev.equalsIgnoreCase("0") || brhLev.equalsIgnoreCase("1")) {
			sql = "SELECT TXN_TIME,MCHNT_ID,'MCHT_NM' as MCHT_NM,(select c.BRH_ID||'-'||c.BRH_NAME from TBL_BRH_INFO c where c.BRH_ID=a.BRH_ID) as bankNoName,OPR_ID,REFUSE_TYPE,REFUSE_INFO FROM TBL_MCHNT_REFUSE a WHERE BRH_ID IN "
					+ operator.getBrhBelowId()
					+ where
					+ " ORDER BY MCHNT_ID DESC,TXN_TIME DESC";
			countSql = "SELECT COUNT(*) FROM TBL_MCHNT_REFUSE WHERE BRH_ID IN "
					+ operator.getBrhBelowId() + where;

		}
		if (brhLev.equals("2")) {// 支行
			sql = "SELECT TXN_TIME,MCHNT_ID, 'MCHT_NM' as MCHT_NM,(select c.BRH_ID||'-'||c.BRH_NAME from TBL_BRH_INFO c where c.BRH_ID=a.BRH_ID) as bankNoName,OPR_ID,REFUSE_TYPE,REFUSE_INFO FROM TBL_MCHNT_REFUSE a WHERE BRH_ID = "
					+ "'" + brhId + "'" + where + " ORDER BY MCHNT_ID DESC,TXN_TIME DESC";
			countSql = "SELECT COUNT(*) FROM TBL_MCHNT_REFUSE WHERE BRH_ID = "
					+ "'" + brhId + "'" + where;

		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
	 
		String currentId = "";
		String mainTableName = "";
		for (int i = 0; i < dataList.size(); i++){
			String getMchtNmSql = "select MCHT_NM from ";
			if ("".equals(currentId) || !currentId.equals(dataList.get(i)[1])) {
				currentId = dataList.get(i)[1].toString();
				if ("进件提交".equals(dataList.get(i)[5].toString()) || "初审退回".equals(dataList.get(i)[5].toString()) || "修改提交".equals(dataList.get(i)[5].toString())){
					mainTableName = "tbl_mcht_base_inf_tmp_tmp";
				} else if ("终审退回".equals(dataList.get(i)[5].toString()) || "终审拒绝".equals(dataList.get(i)[5].toString()) || "初审通过".equals(dataList.get(i)[5].toString()) ){
					mainTableName = "tbl_mcht_base_inf_tmp";
				} else {
					mainTableName = "tbl_mcht_base_inf";
				}
			}
			getMchtNmSql += mainTableName + " where MCHT_NO = '" + currentId + "'";
			List<Object[]> mchtNmdataList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(getMchtNmSql, 0, Constants.QUERY_RECORD_COUNT);
			if (mchtNmdataList.size() != 0 && mchtNmdataList.get(0) != null) {
				dataList.get(i)[2] = mchtNmdataList.get(0);
			} else {
				dataList.get(i)[2] = new String("");
			}
		}
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 合作伙伴审批过程查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:50:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhApproveProcessInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String brhId = request.getParameter("queryBrhId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryOprId = request.getParameter("queryOprId");
		StringBuffer where = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(brhId)) {
			where.append("and a.BRH_ID ='").append(brhId).append("'");
		}
		if (isNotEmpty(startDate)) {
			where.append(" and a.TXN_TIME >='").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			where.append(" and a.TXN_TIME <='").append(endDate).append("'");
		}
		if (isNotEmpty(queryOprId)) {
			where.append(" and a.OPR_ID = '").append(queryOprId).append("'");
		}

		String sql = "";
		String countSql = "";
		// 
		sql = "SELECT TXN_TIME, a.BRH_ID, b.BRH_NAME, b.BRH_LEVEL, c.UP_BRH_ID||'-'|| c.BRH_NAME as UP_BRH_ID,"
				+ " OPR_ID, OPR_TYPE, OPR_INFO "
				+ "FROM TBL_BRH_APPROVE_PROCESS a "
				+ "LEFT JOIN TBL_BRH_INFO b ON a.BRH_ID = b.BRH_ID "
				+ "LEFT JOIN TBL_BRH_INFO c ON c.BRH_ID = b.UP_BRH_ID "
				+ where
				+ " ORDER BY TXN_TIME DESC";
		countSql = "SELECT COUNT(*) FROM TBL_BRH_APPROVE_PROCESS a" + where;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
	 
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 商户退回/拒绝原因查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:50:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getLastMchntBackRefuseInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryMchtNo = request.getParameter("queryMchtNo");
		StringBuffer where = new StringBuffer(" ");
		where.append(" and REFUSE_TYPE like '%退回%'");
		
		if (isNotEmpty(queryMchtNo)) {
			where.append(" and MCHNT_ID = '").append(queryMchtNo).append("'");
		}

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String brhId = operator.getOprBrhId();
		String brhLev = operator.getOprBrhLvl();
		String sql = "";
		String countSql = "";
		// 如果是总行或者是分行可以看见其下属机构的商户回退或者拒绝的原因
		if (brhLev.equalsIgnoreCase("0") || brhLev.equalsIgnoreCase("1")) {
			sql = "SELECT TXN_TIME,MCHNT_ID,'MCHT_NM' as MCHT_NM,(select c.BRH_ID||'-'||c.BRH_NAME from TBL_BRH_INFO c where c.BRH_ID=a.BRH_ID) as bankNoName,OPR_ID,REFUSE_TYPE,REFUSE_INFO FROM TBL_MCHNT_REFUSE a WHERE BRH_ID IN "
					+ operator.getBrhBelowId()
					+ where
					+ " ORDER BY MCHNT_ID DESC,TXN_TIME DESC";
			countSql = "SELECT COUNT(*) FROM TBL_MCHNT_REFUSE WHERE BRH_ID IN "
					+ operator.getBrhBelowId() + where;

		}
		if (brhLev.equals("2")) {// 支行
			sql = "SELECT TXN_TIME,MCHNT_ID, 'MCHT_NM' as MCHT_NM,(select c.BRH_ID||'-'||c.BRH_NAME from TBL_BRH_INFO c where c.BRH_ID=a.BRH_ID) as bankNoName,OPR_ID,REFUSE_TYPE,REFUSE_INFO FROM TBL_MCHNT_REFUSE a WHERE BRH_ID = "
					+ "'" + brhId + "'" + where + " ORDER BY MCHNT_ID DESC,TXN_TIME DESC";
			countSql = "SELECT COUNT(*) FROM TBL_MCHNT_REFUSE WHERE BRH_ID = "
					+ "'" + brhId + "'" + where;

		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 商户退回/拒绝原因查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:50:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getLastBrhRefuseInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryBrhId = request.getParameter("queryBrhId");
		StringBuffer where = new StringBuffer(" WHERE 1=1 ");
		where.append(" and OPR_TYPE like '%拒绝%'");
		
		if (isNotEmpty(queryBrhId)) {
			where.append(" and BRH_ID = '").append(queryBrhId).append("'");
		}

		String sql = "";
		String countSql = "";
		sql = "SELECT TXN_TIME,BRH_ID,OPR_ID,OPR_TYPE AS REFUSE_TYPE,OPR_INFO AS REFUSE_INFO FROM TBL_BRH_APPROVE_PROCESS "
				+ where
				+ " ORDER BY TXN_TIME DESC";
		countSql = "SELECT COUNT(*) FROM TBL_BRH_APPROVE_PROCESS "
					+ where;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 终端退回/拒绝原因查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:56:42
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermBackRefuseInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "SELECT TXN_TIME,TERM_ID,BRH_ID,OPR_ID,REFUSE_TYPE,REFUSE_INFO FROM TBL_TERM_REFUSE WHERE BRH_ID IN "
				+ operator.getBrhBelowId() + " ORDER BY TXN_TIME DESC";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_REFUSE WHERE BRH_ID IN "
				+ operator.getBrhBelowId();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取终端密钥申请信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-6上午10:52:10
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermTmkInfo(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		String state = request.getParameter("state");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		StringBuffer whereSql = new StringBuffer(
				" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')")
				.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}
		if (startDate != null && !startDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS>").append(startDate);
		}
		if (endDate != null && !endDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS<").append(endDate);
		}
		if (state != null && !state.trim().equals("")) {
			if (state.equals("1"))
				whereSql.append(" AND t2.STATE='1'");
			else
				whereSql.append(" AND t2.STATE is null");
		}

		String sql = "SELECT t1.term_id,t1.mcht_cd||' - '||t3.MCHT_NM,t1.term_branch,t1.term_sta,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
				+ "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
				+ " left join TBL_MCHT_BASE_INF t3 on t1.mcht_cd = t3.MCHT_NO"
				+ whereSql.toString() + " ORDER BY t1.term_id";
		String countSql = "SELECT COUNT(*) FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0' left join TBL_MCHT_BASE_INF t3 on t1.mcht_cd = t3.MCHT_NO"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	// @SuppressWarnings("unchecked")
	// public static Object[] getTermTmkInfo(int begin, HttpServletRequest
	// request) {
	// Operator operator = (Operator) request.getSession().getAttribute(
	// Constants.OPERATOR_INFO);
	// Object[] ret = new Object[2];
	// String termNo = request.getParameter("termId");
	// String mchtCd = request.getParameter("mchnNo");
	// String state = request.getParameter("state");
	// String termBranch = request.getParameter("termBranch");
	// StringBuffer whereSql = new StringBuffer("");
	// if(state != null && !state.trim().equals("") && state.equals("2"))
	// whereSql.append(" and t2.state != '1'");
	// whereSql.append(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')")
	// .append(" and t1.TERM_BRANCH in "+operator.getBrhBelowId());
	// if(mchtCd != null && !mchtCd.trim().equals(""))
	// whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
	// if(termBranch != null && !termBranch.trim().equals(""))
	// whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
	// if(termNo != null && !termNo.trim().equals(""))
	// whereSql.append(" AND t1.TERM_ID='").append(CommonFunction.fillString(termNo,
	// ' ', 12, true)).append("'");
	// if(state != null && !state.trim().equals("") && state.equals("1"))
	// whereSql.append(" AND t2.state = '1'");
	// String sql =
	// "SELECT t1.term_id,t1.mcht_cd,t1.term_branch,t1.term_sta,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
	// +
	// "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
	// + whereSql.toString() + " ORDER BY t1.term_id";
	// String countSql =
	// "SELECT COUNT(*) FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
	// + whereSql.toString();
	//
	// List<Object[]> dataList = CommonFunction.getCommQueryDAO()
	// .findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
	// String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
	// countSql);
	// ret[0] = dataList;
	// ret[1] = count;
	// return ret;
	// }
	/**
	 * 获取终端密钥待审核信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-6上午10:52:10
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermTmkInfoAll(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer(" WHERE t1.TERM_BRANCH in "
				+ operator.getBrhBelowId());
		Object[] ret = new Object[2];
		String batchNo = request.getParameter("batchNo");
		String termIdId = request.getParameter("termIdId");
		String reqOpr = request.getParameter("reqOpr");
		String mchntNo = request.getParameter("mchntNo");
		String state = request.getParameter("state");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if (!StringUtil.isEmpty(mchntNo))
			whereSql.append(" AND t1.MCHN_NO='").append(mchntNo).append("'");
		if (!StringUtil.isEmpty(reqOpr))
			whereSql.append(" AND t1.REQ_OPR='").append(reqOpr).append("'");
		if (!StringUtil.isEmpty(termIdId))
			whereSql.append(" AND t1.TERM_ID_ID='").append(termIdId)
					.append("'");
		if (!StringUtil.isEmpty(batchNo))
			whereSql.append(" AND t1.BATCH_NO='").append(batchNo).append("'");
		if (!StringUtil.isEmpty(startDate))
			whereSql.append(" AND t1.REQ_DATE>=").append(startDate);
		if (!StringUtil.isEmpty(endDate))
			whereSql.append(" AND t1.REQ_DATE<=").append(endDate);
		if (!StringUtil.isEmpty(state))
			whereSql.append(" AND t1.STATE='").append(state).append("'");
		String sql = "select t1.BATCH_NO,t1.TERM_ID_ID,t1.MCHN_NO||' - '||t2.MCHT_NM,t1.TERM_BRANCH,t1.STATE,t1.REQ_OPR,t1.REQ_DATE,"
				+ "t1.CHK_OPR,t1.CHK_DATE,t1.MISC,t1.REC_UPD_OPR,t1.REC_UPD_TS,t1.PRT_OPR,t1.PRT_DATE,t1.PRT_COUNT from TBL_TERM_TMK_LOG t1"
				+ " left join TBL_MCHT_BASE_INF t2 on t2.MCHT_NO=t1.MCHN_NO "
				+ whereSql.toString()
				+ " ORDER BY t1.REQ_DATE DESC,t1.BATCH_NO";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_TMK_LOG t1 "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构下所有终端信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午10:25:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermInfoAll(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String notEqsta= request.getParameter("notEqsta");
		String termSta = request.getParameter("termSta");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String termNo = request.getParameter("termId");
		// String termNo = request.getParameter("termNo");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		String termTp = request.getParameter("termTp");

		StringBuffer whereSql = new StringBuffer(" WHERE t1.TERM_BRANCH in "
				+ operator.getBrhBelowId());
		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS>").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS<").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}
		if (termSta != null && !termSta.trim().equals("")) {
			if (termSta.length() == 1)
				whereSql.append(" AND t1.TERM_STA='").append(termSta)
						.append("'");
			else {
//				termSta = termSta.replaceAll("1", "','");
				whereSql.append(" AND t1.TERM_STA in (").append(termSta)
						.append(")");
			}
		}
		if (notEqsta != null && !notEqsta.trim().equals("")) {
			if (notEqsta.length() == 1)
				whereSql.append(" AND t1.TERM_STA !='").append(notEqsta)
						.append("'");
			
		}

		if (!StringUtil.isNull(termTp)) {
			whereSql.append(" AND TRIM(t1.TERM_TP) = '").append(termTp.trim())
					.append("' ");
		}

		String sql = "SELECT t1.TERM_ID,t1.MCHT_CD,t1.bind_tel1,NVL(t2.MCHT_NM,t3.MCHT_NM),t1.TERM_STA,t1.TERM_SIGN_STA,t1.TERM_ID_ID,t1.FINANCE_CARD1,"
				+ "t1.TERM_FACTORY,t1.TERM_MACH_TP,t1.TERM_VER,TERM_TP,t4.CREATE_NEW_NO||'-'||t4.BRH_NAME,t1.TERM_INS,t1.REC_CRT_TS,t1.PROP_TP,T1.TERM_ADDR,t1.TERM_PARA_1,t1.PRODUCT_CD"
				+ " FROM TBL_TERM_INF_TMP t1 "
				+ "left join TBL_MCHT_BASE_INF_TMP t2 on t1.MCHT_CD = t2.MCHT_NO"
				+ " left join TBL_MCHT_BASE_INF_TMP_TMP t3 on t1.MCHT_CD = t3.MCHT_NO"
				+ " left join TBL_BRH_INFO t4 on t1.TERM_BRANCH = t4.BRH_ID"
				+ whereSql.toString()
				+ " ORDER BY t1.REC_CRT_TS desc ,t1.TERM_ID desc ";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF_TMP t1"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构下所有终端信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午10:25:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermInfoForMaint(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termSta = request.getParameter("termSta");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String termNo = request.getParameter("termId");
		// String termNo = request.getParameter("termNo");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		String termTp = request.getParameter("termTp");
		String productCd = request.getParameter("productCd");
		
		String modelId = request.getParameter("modelId");

		StringBuffer whereSql = new StringBuffer(" WHERE t1.TERM_BRANCH in "
				+ operator.getBrhBelowId());
		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND t1.REC_UPD_TS>").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND t1.REC_UPD_TS<").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}
		if (termSta != null && !termSta.trim().equals("")) {
			if (termSta.length() == 1)
				whereSql.append(" AND t1.TERM_STA='").append(termSta)
						.append("'");
			else {
				termSta = termSta.replaceAll("1", "','");
				whereSql.append(" AND t1.TERM_STA in ('").append(termSta)
						.append("')");
			}
		}

		if (!StringUtil.isNull(termTp)) {
			whereSql.append(" AND TRIM(t1.TERM_TP) = '").append(termTp.trim())
					.append("' ");
		}
		if (!StringUtil.isNull(productCd)) {
			whereSql.append(" and t1.PRODUCT_CD like '%").append(productCd.trim()).append("%' ");
		}
		
		if (!StringUtil.isNull(modelId)) {
			whereSql.append(" AND t3.model_id = '").append(modelId.trim()).append("' ");
		}
		

		whereSql.append(" AND t2.MCHT_STATUS IN ('0','3') ");
		
		String sql = " SELECT t1.PRODUCT_CD,t1.TERM_ID,t1.MCHT_CD,t1.bind_tel1,t2.MCHT_NM,t1.TERM_STA,t1.TERM_SIGN_STA,t1.TERM_ID_ID,t1.FINANCE_CARD1, "
		        + " t1.TERM_FACTORY,t1.TERM_MACH_TP,t1.TERM_VER,TERM_TP,t4.CREATE_NEW_NO||'-'||t4.BRH_NAME,t1.TERM_INS,t1.REC_CRT_TS,t1.REC_UPD_TS,t1.PROP_TP,T1.TERM_ADDR,t1.TERM_PARA_1,t3.model_id||'-'||t3.model_name modelName "
		        + " FROM TBL_TERM_INF_TMP t1 "
		        + "  left join TBL_MCHT_BASE_INF_TMP t2 on t1.MCHT_CD = t2.MCHT_NO "
		        + "  left join TBL_MODEL_INFO t3 on t3.model_id = to_number(t1.misc_1) "
		        + " left join TBL_BRH_INFO t4 on t1.TERM_BRANCH = t4.BRH_ID"
				+ whereSql.toString()
				+ " ORDER BY t1.REC_UPD_TS desc nulls last,t1.TERM_ID desc ";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF_TMP t1 left join TBL_MCHT_BASE_INF_TMP t2 on t1.MCHT_CD = t2.MCHT_NO left join TBL_MODEL_INFO t3 on t3.model_id = to_number(t1.misc_1)"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 查询机构下所有直联终端信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午10:25:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermCupInfoAll(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termSta = request.getParameter("termSta");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String startTimeU = request.getParameter("startTimeU");
		String endTimeU = request.getParameter("endTimeU");
		String termId = request.getParameter("termId");
		String mchtNo = request.getParameter("mchnNo");
		String termTp = request.getParameter("termTp");

		StringBuffer whereSql = new StringBuffer(
				" WHERE crt_opr in (select opr_id from tbl_opr_info where brh_id in "
						+ operator.getBrhBelowId() + ") ");
		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND substr(t1.crt_date,0,8) >= ").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND substr(t1.crt_date,0,8) <= ").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (startTimeU != null && !startTimeU.trim().equals("")) {
			whereSql.append(" AND substr(t1.upd_date,0,8) >= ").append(
					startTimeU.split("T")[0].replaceAll("-", ""));
		}
		if (endTimeU != null && !endTimeU.trim().equals("")) {
			whereSql.append(" AND substr(t1.upd_date,0,8) <= ").append(
					endTimeU.split("T")[0].replaceAll("-", ""));
		}
		if (mchtNo != null && !mchtNo.trim().equals("")) {
			whereSql.append(" AND t1.mcht_no='").append(mchtNo).append("'");
		}
		if (termId != null && !termId.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termId, ' ', 12, true))
					.append("'");
		}
		if (termSta != null && !termSta.trim().equals("")) {
			if (termSta.length() == 1)
				whereSql.append(" AND t1.term_state='").append(termSta)
						.append("'");
			else {
				termSta = termSta.replaceAll("1", "','");
				whereSql.append(" AND t1.term_state in ('").append(termSta)
						.append("')");
			}
		}

		if (!StringUtil.isNull(termTp)) {
			whereSql.append(" AND TRIM(t1.term_type) = '")
					.append(termTp.trim()).append("' ");
		}

		String sql = "SELECT t1.TERM_ID,t1.mcht_no||' - '||t2.MCHT_NM,t1.term_state,term_type,t1.crt_date,t1.upd_date,"
				+ "(case when term_state != 0 AND crt_date = upd_date then 'I' when term_state != 0 AND crt_date < upd_date then 'U' WHEN  term_state = 0 THEN 'D' END) AS INSET_FLAG "
				+ "FROM tbl_term_cup_info t1 "
				+ "left outer join tbl_mcht_cup_info t2 on t1.mcht_no = t2.MCHT_NO"
				+ whereSql.toString()
				+ " ORDER BY t1.TERM_ID,t1.mcht_no,term_type,t1.term_state,t1.crt_date";
		String countSql = "SELECT COUNT(*) FROM tbl_term_cup_info t1"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构下所有直联终端信息tmp
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午10:25:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermCupTmpAll(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termSta = request.getParameter("termSta");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String termId = request.getParameter("termId");
		String mchtNo = request.getParameter("mchnNo");
		String termTp = request.getParameter("termTp");

		StringBuffer whereSql = new StringBuffer(
				" WHERE crt_opr in (select opr_id from tbl_opr_info where brh_id in "
						+ operator.getBrhBelowId() + ") ");
		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND t1.crt_date >= ").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND t1.crt_date <= ").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (mchtNo != null && !mchtNo.trim().equals("")) {
			whereSql.append(" AND t1.mcht_no='").append(mchtNo).append("'");
		}
		if (termId != null && !termId.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termId, ' ', 12, true))
					.append("'");
		}
		if (termSta != null && !termSta.trim().equals("")) {
			if (termSta.length() == 1)
				whereSql.append(" AND t1.term_state='").append(termSta)
						.append("'");
			else {
				termSta = termSta.replaceAll("1", "','");
				whereSql.append(" AND t1.term_state in ('").append(termSta)
						.append("')");
			}
		}

		if (!StringUtil.isNull(termTp)) {
			whereSql.append(" AND TRIM(t1.term_type) = '")
					.append(termTp.trim()).append("' ");
		}

		String sql = "SELECT t1.TERM_ID,t1.mcht_no||' - '||t2.MCHT_NM,t1.term_state,term_type,t1.crt_date FROM tbl_term_cup_info_tmp t1 "
				+ "left outer join tbl_mcht_cup_info t2 on t1.mcht_no = t2.MCHT_NO"
				+ whereSql.toString()
				+ " ORDER BY t1.TERM_ID,t1.mcht_no,term_type,t1.term_state,t1.crt_date";
		String countSql = "SELECT COUNT(*) FROM tbl_term_cup_info_tmp t1"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 要审核的直联终端信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午10:25:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermCupInfoCheck(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termSta = request.getParameter("termSta");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String termId = request.getParameter("termId");
		String mchtNo = request.getParameter("mchnNo");
		String termTp = request.getParameter("termTp");

		StringBuffer whereSql = new StringBuffer(
				" WHERE term_state in('9','7','5','3','H') and crt_opr in (select opr_id from tbl_opr_info where brh_id in "
						+ operator.getBrhBelowId() + ") ");
		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND t1.crt_date >= ").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND t1.crt_date <= ").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (mchtNo != null && !mchtNo.trim().equals("")) {
			whereSql.append(" AND t1.mcht_no='").append(mchtNo).append("'");
		}
		if (termId != null && !termId.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termId, ' ', 12, true))
					.append("'");
		}
		if (termSta != null && !termSta.trim().equals("")) {
			if (termSta.length() == 1)
				whereSql.append(" AND t1.term_state='").append(termSta)
						.append("'");
			else {
				termSta = termSta.replaceAll("1", "','");
				whereSql.append(" AND t1.term_state in ('").append(termSta)
						.append("')");
			}
		}

		if (!StringUtil.isNull(termTp)) {
			whereSql.append(" AND TRIM(t1.term_type) = '")
					.append(termTp.trim()).append("' ");
		}

		String sql = "SELECT t1.TERM_ID,t1.mcht_no||' - '||t2.MCHT_NM,t1.term_state,term_type,t1.crt_date FROM tbl_term_cup_info_tmp t1 "
				+ "left outer join tbl_mcht_cup_info_tmp t2 on t1.mcht_no = t2.MCHT_NO"
				+ whereSql.toString()
				+ " ORDER BY t1.TERM_ID,t1.mcht_no,term_type,t1.term_state,t1.crt_date";
		String countSql = "SELECT COUNT(*) FROM tbl_term_cup_info_tmp t1"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构下带审核终端信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午10:25:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermForCheck(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termSta = request.getParameter("termSta");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String termNo = request.getParameter("termId");
		// String termNo = request.getParameter("termNo");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		String termTp = request.getParameter("termTp");

		StringBuffer whereSql = new StringBuffer(" WHERE t1.TERM_BRANCH in "
				+ operator.getBrhBelowId());
		whereSql.append(" and t1.TERM_STA in ('0', '2', '3', '5', '6')");

		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND t1.REC_UPD_TS>").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND t1.REC_UPD_TS<").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}
		if (termSta != null && !termSta.trim().equals("")) {
			if (termSta.length() == 1)
				whereSql.append(" AND t1.TERM_STA='").append(termSta)
						.append("'");
			else {
				termSta = termSta.replaceAll("1", "','");
				whereSql.append(" AND t1.TERM_STA in ('").append(termSta)
						.append("')");
			}
		}

		if (!StringUtil.isNull(termTp)) {
			whereSql.append(" AND TRIM(t1.TERM_TP) = '").append(termTp.trim())
					.append("' ");
		}

		String sql = "SELECT t1.TERM_ID,t1.MCHT_CD,t1.bind_tel1,NVL(t2.MCHT_NM,t3.MCHT_NM),t1.TERM_STA,t1.TERM_SIGN_STA,t1.TERM_ID_ID,t1.FINANCE_CARD1,"
				+ "t1.TERM_FACTORY,t1.TERM_MACH_TP,t1.TERM_VER,TERM_TP,t4.CREATE_NEW_NO||'-'||t4.BRH_NAME,t1.TERM_INS,t1.REC_CRT_TS,t1.PRODUCT_CD"
				+ " FROM TBL_TERM_INF_TMP t1 "
				+ "left join TBL_MCHT_BASE_INF_TMP t2 on t1.MCHT_CD = t2.MCHT_NO"
				+ " left join TBL_MCHT_BASE_INF_TMP_TMP t3 on t1.MCHT_CD = t3.MCHT_NO"
				+ " left join TBL_BRH_INFO t4 on t1.TERM_BRANCH = t4.BRH_ID"
				+ whereSql.toString()
				+ " ORDER BY t1.REC_UPD_TS desc ";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF_TMP t1"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端信息查询（正式表）
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-8-3下午04:15:43
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermInfo(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		StringBuffer whereSql = new StringBuffer(" WHERE TERM_BRANCH in "
				+ operator.getBrhBelowId());
		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND t1.REC_UPD_TS>").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND t1.REC_UPD_TS<").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}

		String sql = "SELECT TERM_ID,MCHT_CD,TERM_STA,TERM_SIGN_STA,TERM_ID_ID,TERM_FACTORY,TERM_MACH_TP,"
				+ "TERM_VER,TERM_TP,TERM_BRANCH,TERM_INS,equip_inv_id,equip_inv_nm,"
				+ "(select pin_pad from tbl_term_management b where a.term_id_id=b.term_no) as pinFlag FROM TBL_TERM_INF a "
				+ whereSql.toString() + " ORDER BY MCHT_CD,TERM_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端信息查询（正式表）未绑定机具的
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-8-3下午04:15:43
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermInfoBind(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		StringBuffer whereSql = new StringBuffer(
				" WHERE NVL(equip_inv_id,'NN') <> 'YY' AND TERM_BRANCH in "
						+ operator.getBrhBelowId());
		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND a.REC_UPD_TS>").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND a.REC_UPD_TS<").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND a.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND a.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND a.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}

		String sql = "SELECT TERM_ID,MCHT_CD,TERM_STA,TERM_SIGN_STA,TERM_ID_ID,TERM_FACTORY,TERM_MACH_TP,"
				+ "TERM_VER,TERM_TP,TERM_BRANCH,TERM_INS,equip_inv_id,equip_inv_nm,"
				+ "(select pin_pad from tbl_term_management b where a.term_id_id=b.term_no) as pinFlag FROM TBL_TERM_INF a "
				+ whereSql.toString() + " ORDER BY MCHT_CD,TERM_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF a "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询终端密钥信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午11:20:41
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPosKeyInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "SELECT a.MCHNT_ID,a.TERM_ID,b.BRH_ID,POS_TMK,TMK_ST FROM TBL_TERM_KEY a,TBL_TERM_INF b "
				+ "WHERE a.TERM_ID  = b.TERM_ID AND b.TERM_ST = '0' AND b.BRH_ID IN "
				+ operator.getBrhBelowId() + " ORDER BY a.MCHNT_ID,a.TERM_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_KEY a,TBL_TERM_INF b "
				+ "WHERE a.TERM_ID  = b.TERM_ID AND b.TERM_ST = '0' AND b.BRH_ID IN "
				+ operator.getBrhBelowId();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询卡黑名单信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-24下午04:47:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCardRiskInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String sql = "SELECT SA_CARD_NO,SA_LIMIT_AMT,SA_ACTION,SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,"
				+ "SA_MODI_ZONE_NO,SA_MODI_OPR_ID,SA_MODI_TIME FROM TBL_CTL_CARD_INF"
				+ " WHERE 1=1";
		String countSql = "SELECT COUNT(*) FROM TBL_CTL_CARD_INF WHERE 1=1";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("srCardNo"))) {
			date = request.getParameter("srCardNo");
			whereSql += " and SA_CARD_NO = '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("srBrhNo"))) {
			date = request.getParameter("srBrhNo");
			whereSql += " and SA_INIT_ZONE_NO = '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("srAction"))) {
			date = request.getParameter("srAction");
			whereSql += " and SA_ACTION = '" + date + "'";
		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql, begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[1] = CommonFunction.transFenToYuan(data[1].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户黑名单信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-26下午09:40:21
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntRiskInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "SELECT SA_MER_NO,SA_MER_CH_NAME,"
				+ "(select disc_cd||' - '||disc_nm from tbl_inf_disc_cd t,tbl_mcht_settle_inf m where a.sa_mer_no=m.mcht_no and m.fee_rate = t.disc_cd) as discNm,"
				+ "SA_MER_EN_NAME,SA_LIMIT_AMT,SA_ACTION,SA_ZONE_NO,SA_ADMI_BRAN_NO,SA_CONN_OR,SA_CONN_TEL,"
				+ "SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,SA_MODI_ZONE_NO,SA_MODI_OPR_ID,SA_MODI_TIME "
				+ "FROM TBL_CTL_MCHT_INF a WHERE SA_ZONE_NO IN "
				+ operator.getBrhBelowId();
		String countSql = "SELECT COUNT(*) FROM TBL_CTL_MCHT_INF WHERE SA_ZONE_NO IN "
				+ operator.getBrhBelowId();

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("srMerNo"))) {
			date = request.getParameter("srMerNo");
			whereSql += " and SA_MER_NO = '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("srBrhNo"))) {
			date = request.getParameter("srBrhNo");
			whereSql += " and SA_ZONE_NO = '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("srAction"))) {
			date = request.getParameter("srAction");
			whereSql += " and SA_ACTION = '" + date + "'";
		}
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql, begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[3] = CommonFunction.transFenToYuan(data[3].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户风控黑名单信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-26下午09:40:21
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskBlackMcht(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);

		String queryMchtNo = request.getParameter("queryMchtNo");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (isNotEmpty(queryMchtNo)) {
			whereSql.append(" AND a.mcht_no").append(" = ").append("'")
					.append(queryMchtNo).append("' ");
		}
		String sql = "SELECT a.mcht_no,"
				+ " a.mcht_no||'-'||b.mcht_nm ,b.licence_no,b.fax_no,b.manager,b.artif_certif_tp,b.identity_no,c.settle_acct,"
				+ " a.crt_opr_id,a.crt_date_time,resved "
				+ " FROM TBL_RISK_BLACK_MCHT a "
				+ " left join TBL_MCHT_BASE_INF b on a.mcht_no=b.MCHT_NO  "
				+ " left join TBL_MCHT_SETTLE_INF c on a.mcht_no=c.MCHT_NO "
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_BLACK_MCHT a "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户清算黑名单信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-26下午09:40:21
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntSettleRiskInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		boolean flag = true;
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String whereSql = "";
		String data = null;
		if (isNotEmpty(request.getParameter("saMchtNo"))) {
			data = request.getParameter("saMchtNo");
			whereSql += " and sa_mcht_no = '" + data + "'";
			flag = false;
		}

		if (isNotEmpty(request.getParameter("ctlSettleType"))) {
			data = request.getParameter("ctlSettleType");
			whereSql += " and ctl_settle_type = '" + data + "'";
			flag = false;
		}

		if (isNotEmpty(request.getParameter("oriSettleDate"))) {
			data = request.getParameter("oriSettleDate");
			whereSql += " and ori_settle_date = '" + data + "'";
			flag = false;
		}
		String sql = "";
		String countSql = "SELECT COUNT(*) FROM tbl_ctl_mcht_settle_inf where 1=1 ";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		if (flag) {
			sql = "SELECT first 100 ctl_settle_no,(select mcht_no ||' - '||mcht_nm from tbl_mcht_base_inf c where a.SA_MCHT_NO=c.mcht_no) as mcht,ctl_settle_type,sa_action_date,sa_action_amt,"
					+ "action_done_amt,ori_settle_date,action_flag,sa_init_time,sa_init_opr_id"
					+ " FROM tbl_ctl_mcht_settle_inf a WHERE 1=1 ";
			if (Integer.parseInt(count) > 100) {
				count = "100";
			}
		} else {
			sql = "SELECT ctl_settle_no,(select mcht_no ||' - '||mcht_nm from tbl_mcht_base_inf c where a.SA_MCHT_NO=c.mcht_no) as mcht,ctl_settle_type,sa_action_date,sa_action_amt,"
					+ "action_done_amt,ori_settle_date,action_flag,sa_init_time,sa_init_opr_id"
					+ " FROM tbl_ctl_mcht_settle_inf a WHERE 1=1 ";
		}
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql, begin,
						Constants.QUERY_RECORD_COUNT);

		ret[0] = dataList;
		ret[1] = count;
		flag = true;
		return ret;
	}

	/**
	 * 查询历史风险记录
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHistoryRiskRecords(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		boolean flag = true;
		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("startDate"))) {
			date = request.getParameter("startDate") + "000000";
			whereSql += " and SA_TXN_DATE||sa_txn_time >= '" + date + "'";
			flag = false;
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			date = request.getParameter("endDate") + "2359559";
			whereSql += " and SA_TXN_DATE||sa_txn_time <= '" + date + "'";
			flag = false;
		}
		if (isNotEmpty(request.getParameter("mchntNo"))) {
			date = request.getParameter("mchntNo");
			whereSql += " and SA_MCHT_NO = '" + date + "' ";
			flag = false;
		}
		String sql = "";

		String countSql = "SELECT COUNT(*) FROM TBL_CLC_MON WHERE (SA_TXN_CARD IN (SELECT SA_CARD_NO FROM TBL_CTL_CARD_INF) OR"
				+ " SA_MCHT_NO IN (SELECT SA_MER_NO FROM TBL_CTL_MCHT_INF)) ";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		if (flag) {
			sql = "SELECT first 100  SA_TXN_CARD,(select mcht_no ||' - '||mcht_nm from tbl_mcht_base_inf b where a.SA_MCHT_NO=b.mcht_no) as mcht,"
					+ "sa_clc_flag,sa_clc_rsn1,SA_TERM_NO,(SELECT txn_name FROM tbl_txn_name C WHERE A.SA_TXN_NUM=C.txn_num) AS TXNNAME,"
					+ "(case SA_TXN_AMT when null then 0 when ' ' then 0 else trim(SA_TXN_AMT)*1/100 end),SA_TXN_DATE,SA_TXN_TIME,sa_note_txt"
					+ " FROM TBL_CLC_MON a WHERE (SA_TXN_CARD IN (SELECT SA_CARD_NO FROM TBL_CTL_CARD_INF) OR"
					+ " SA_MCHT_NO IN (SELECT SA_MER_NO FROM TBL_CTL_MCHT_INF)) ";
			if (Integer.parseInt(count) > 100) {
				count = "100";
			}

		} else {
			sql = "SELECT SA_TXN_CARD,(select mcht_no ||' - '||mcht_nm from tbl_mcht_base_inf b where a.SA_MCHT_NO=b.mcht_no) as mcht,"
					+ "sa_clc_flag,sa_clc_rsn1,SA_TERM_NO,(SELECT txn_name FROM tbl_txn_name C WHERE A.SA_TXN_NUM=C.txn_num) AS TXNNAME,"
					+ "(case SA_TXN_AMT when null then 0 when ' ' then 0 else trim(SA_TXN_AMT)*1/100 end),SA_TXN_DATE,SA_TXN_TIME,sa_note_txt"
					+ " FROM TBL_CLC_MON a WHERE (SA_TXN_CARD IN (SELECT SA_CARD_NO FROM TBL_CTL_CARD_INF) OR"
					+ " SA_MCHT_NO IN (SELECT SA_MER_NO FROM TBL_CTL_MCHT_INF)) ";

		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql, begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[4] = CommonFunction.transFenToYuan(data[4].toString());
			dataList.set(i, data);
		}

		ret[0] = dataList;
		ret[1] = count;
		flag = true;
		return ret;
	}

	/**
	 * 查询卡黑名单历史交易
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-24下午04:47:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCardRiskHistory(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		Object[] ret = new Object[2];
		boolean flag = true;
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("srCardNo"))) {
			whereSql.append(" AND trim(sa_txn_card) = '"
					+ request.getParameter("srCardNo").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("srBrhNo"))) {
			whereSql.append(" AND trim(SA_OPEN_INST) = '"
					+ request.getParameter("srBrhNo").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND trim(sa_txn_date) >= '"
					+ request.getParameter("startDate").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND trim(sa_txn_date) <= '"
					+ request.getParameter("endDate").trim() + "' ");
			flag = false;
		}
		String sql = "";
		String countSql = "select count(*) "
				+ "from tbl_clc_mon,tbl_txn_name "
				+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '黑名单卡金额受限' "
				+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		if (flag) {
			sql = "select first 100 sa_txn_card,(select mcht_no ||' - '||mcht_nm from tbl_mcht_base_inf c where a.SA_MCHT_NO=c.mcht_no) as mcht,sa_term_no,txn_name,"
					+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
					+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG "
					+ "from tbl_clc_mon a,tbl_txn_name b "
					+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '黑名单卡金额受限' and a.SA_MCHT_NO in(select mcht_no from tbl_mcht_base_inf where bank_no in "
					+ operator.getBrhBelowId() + ") ";
			if (Integer.parseInt(count) > 100) {
				count = "100";
			}
		} else {
			sql = "select sa_txn_card,(select mcht_no ||' - '||mcht_nm from tbl_mcht_base_inf c where a.SA_MCHT_NO=c.mcht_no) as mcht,sa_term_no,txn_name,"
					+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
					+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG "
					+ "from tbl_clc_mon a,tbl_txn_name b "
					+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '黑名单卡金额受限' and a.SA_MCHT_NO in(select mcht_no from tbl_mcht_base_inf where bank_no in "
					+ operator.getBrhBelowId() + ") ";

		}
		sql += whereSql.toString();

		sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		ret[0] = dataList;
		ret[1] = count;
		flag = true;
		return ret;
	}

	/**
	 * 查询商户黑名单历史交易
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-24下午04:47:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtRiskHistory(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		Object[] ret = new Object[2];
		boolean flag = true;
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("srMerNo"))) {
			whereSql.append(" AND trim(sa_mcht_no) = '"
					+ request.getParameter("srMerNo").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("srBrhNo"))) {
			whereSql.append(" AND trim(SA_OPEN_INST) = '"
					+ request.getParameter("srBrhNo").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND trim(sa_txn_date) >= '"
					+ request.getParameter("startDate").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND trim(sa_txn_date) <= '"
					+ request.getParameter("endDate").trim() + "' ");
			flag = false;
		}
		String sql = "";

		String countSql = "select count(*) "
				+ "from tbl_clc_mon,tbl_txn_name "
				+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '黑名单商户金额受限' "
				+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		if (flag) {
			sql = "select first 100 sa_txn_card,(select mcht_no ||' - '||mcht_nm from tbl_mcht_base_inf c where a.SA_MCHT_NO=c.mcht_no) as mcht,"
					+ "(select disc_cd||' - '||disc_nm from tbl_inf_disc_cd t,tbl_mcht_settle_inf m where a.sa_mcht_no=m.mcht_no and m.fee_rate = t.disc_cd) as discNm,sa_term_no,txn_name,"
					+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
					+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG "
					+ "from tbl_clc_mon a,tbl_txn_name b "
					+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '黑名单商户金额受限' and a.SA_MCHT_NO in(select mcht_no from tbl_mcht_base_inf where bank_no in "
					+ operator.getBrhBelowId() + ") ";
			if (Integer.parseInt(count) > 100) {
				count = "100";
			}
		} else {
			sql = "select sa_txn_card,(select mcht_no ||' - '||mcht_nm from tbl_mcht_base_inf c where a.SA_MCHT_NO=c.mcht_no) as mcht,"
					+ "(select disc_cd||' - '||disc_nm from tbl_inf_disc_cd t,tbl_mcht_settle_inf m where a.sa_mcht_no=m.mcht_no and m.fee_rate = t.disc_cd) as discNm,sa_term_no,txn_name,"
					+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
					+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG "
					+ "from tbl_clc_mon a,tbl_txn_name b "
					+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '黑名单商户金额受限' and a.SA_MCHT_NO in(select mcht_no from tbl_mcht_base_inf where bank_no in "
					+ operator.getBrhBelowId() + ") ";

		}
		sql += whereSql.toString();

		sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		ret[0] = dataList;
		ret[1] = count;
		flag = true;
		return ret;
	}

	/**
	 * 获得监控模型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskModelInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "SELECT sa_model_kind,sa_model_group,sa_model_desc,sa_be_use,misc from TBL_RISK_INF order by substr(sa_model_kind,1,1),substr(sa_model_kind,2) ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_INF ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得监控模型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRisParamInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		String saModelKind = request.getParameter("saModelKind");
		if (isNotEmpty(saModelKind)) {
			whereSql.append(" and model_kind = '" + saModelKind + "' ");
		}
		String sql = "SELECT (select a.RISK_LVL||'-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISK_LVL and b.RISK_ID=a.model_kind ) as RISK_LVL_NAME,"
				+ "model_kind,model_seq,param_len,param_value,param_name from TBL_RISK_PARAM_INF a"
				+ whereSql + " order by a.RISK_LVL ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_PARAM_INF " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得风控级别
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskLvl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		String riskId = request.getParameter("queryRiskId");
		String riskLvl = request.getParameter("queryRisklvl");
		if (isNotEmpty(riskId)) {
			whereSql.append(" and risk_id = '" + riskId + "' ");
		}
		if (isNotEmpty(riskLvl)) {
			whereSql.append(" and risk_lvl = '" + riskLvl + "' ");
		}
		String sql = "SELECT RISK_LVL,RISK_ID,RESVED,RESVED1 from TBL_RISK_LVL a"
				+ whereSql + " order by RISK_ID,RISK_LVL ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_LVL " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户白名单
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskWhite(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		String riskId = request.getParameter("queryRiskId");
		String cardAccpId = request.getParameter("queryCardAccpId");
		if (isNotEmpty(riskId)) {
			whereSql.append(" and risk_id = '" + riskId + "' ");
		}
		if (isNotEmpty(cardAccpId)) {
			whereSql.append(" and card_accp_id = '" + cardAccpId + "' ");
		}
		String sql = "SELECT CARD_ACCP_ID,RISK_ID,RESVED,RESVED1,b.MCHT_NM from TBL_RISK_WHITE a left join TBL_MCHT_BASE_INF b on b.MCHT_NO=a.CARD_ACCP_ID "
				+ whereSql + " order by CARD_ACCP_ID,RISK_ID ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_WHITE " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得监控模型修改记录
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskModelInfoUpdLog(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		String date = null;
		if (isNotEmpty(request.getParameter("startDate"))) {
			date = request.getParameter("startDate") + "000000";
			// whereSql += " and b.MODI_TIME >= '"
			// + date.substring(4, date.length()) + "'";
			whereSql.append(" and b.MODI_TIME >= '" + date + "'");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			date = request.getParameter("endDate") + "2359559";
			// whereSql += " and b.MODI_TIME <= '"
			// + date.substring(4, date.length()) + "'";
			whereSql.append(" and b.MODI_TIME <= '" + date + "'");
		}

		if (isNotEmpty(request.getParameter("saModelKind"))) {

			whereSql.append(" and b.SA_MODEL_KIND = '"
					+ request.getParameter("saModelKind") + "'");
		}

		if (isNotEmpty(request.getParameter("saModelconn"))) {

			whereSql.append(" and substr(b.SA_FIELD_NAME,1,1) = '"
					+ request.getParameter("saModelconn") + "'");
		}
		String sql = "SELECT substr(b.SA_FIELD_NAME,1,1) as SA_MODEL_CONN,b.SA_MODEL_KIND,a.sa_model_desc,substr(b.SA_FIELD_NAME,2),b.SA_FIELD_VALUE_BF,b.SA_FIELD_VALUE,b.MODI_OPR_ID,"
				+ "substr(b.MODI_TIME,9)as modiTime,substr(b.MODI_TIME,1,8) as modiDate"
				+ " FROM TBL_RISK_INF_UPD_LOG b left join TBL_RISK_INF a on a.SA_MODEL_KIND = b.SA_MODEL_KIND "
				+ whereSql + " order by b.MODI_TIME desc ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		String countSql = "SELECT COUNT(*) FROM TBL_RISK_INF_UPD_LOG b left join TBL_RISK_INF a on a.SA_MODEL_KIND = b.SA_MODEL_KIND "
				+ whereSql;
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询银联卡信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBankBinInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("insIdCd"))) {
			whereSql.append(" and INS_ID_CD = '"
					+ request.getParameter("insIdCd").trim() + "'");
		}

		if (!StringUtil.isNull(request.getParameter("cardType"))) {
			whereSql.append(" and CARD_TP = '"
					+ request.getParameter("cardType").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("binStaNo"))) {
			whereSql.append(" and bin_sta_no like '%"
					+ request.getParameter("binStaNo").trim() + "%'");
		}
		if (!StringUtil.isNull(request.getParameter("cardDis"))) {
			whereSql.append(" and CARD_DIS like '%"
					+ request.getParameter("cardDis").trim() + "%'");
		}

		String sql = "SELECT IND,INS_ID_CD,CARD_DIS,CARD_TP,ACC1_OFFSET,ACC1_LEN,ACC1_TNUM,"
				+ "ACC2_OFFSET,ACC2_LEN,ACC2_TNUM,"
				+ "BIN_OFFSET,BIN_LEN,BIN_STA_NO,BIN_END_NO,BIN_TNUM FROM TBL_BANK_BIN_INF where 1 = 1 ";
		sql += whereSql.toString();
		sql += " ORDER BY INS_ID_CD,BIN_STA_NO ";

		String countSql = "SELECT COUNT(*) FROM TBL_BANK_BIN_INF where 1 = 1 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询本行卡信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCardRouteInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		// if (!StringUtil.isNull(request.getParameter("usageKey"))) {
		// whereSql.append(" and usage_key = '"
		// + request.getParameter("usageKey").trim() + "'");
		// }
		if (!StringUtil.isNull(request.getParameter("cardId"))) {
			whereSql.append(" and card_id = '"
					+ request.getParameter("cardId").trim() + "'");
		}

		String sql = "SELECT usage_key,card_id,card_id_offset,card_id_len,branch_no,branch_no_offset,branch_no_len,"
				+ "inst_code,card_name,dest_srv_id,txn_num,card_type FROM tbl_card_route where 1 = 1 ";
		sql += whereSql.toString();
		sql += " ORDER BY card_id";

		String countSql = "SELECT COUNT(*) FROM tbl_card_route where 1 = 1 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询银联CA公钥
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getEmvPara(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];

		String sql = "SELECT USAGE_KEY,PARA_IDX,PARA_ORG,PARA_VAL FROM TBL_EMV_PARA WHERE trim(USAGE_KEY) = '1' order by PARA_IDX";
		String countSql = "SELECT COUNT(*) FROM TBL_EMV_PARA WHERE trim(USAGE_KEY) = '1'";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> newDataList = new ArrayList();

		for (int i = 0; i < dataList.size(); i++) {

			Object[] obj = new Object[11];

			String USAGE_KEY = (String) dataList.get(i)[0];
			String PARA_IDX = (String) dataList.get(i)[1];
			String PARA_ORG = (String) dataList.get(i)[2];
			String PARA_VAL = (String) dataList.get(i)[3];

			String paraIdx = PARA_IDX; // 索引
			String usageKey = USAGE_KEY; // 参数种类
			String paraOrg = PARA_ORG; // 参数名称

			String a9F06 = PARA_VAL.substring(6, 16); // 注册应用提供商标识
			PARA_VAL = PARA_VAL.substring(16);

			String a9F22 = PARA_VAL.substring(6, 8); // 根CA公钥索引
			PARA_VAL = PARA_VAL.substring(8);

			String DF05 = PARA_VAL.substring(6, 22); // 证书失效日期
			char[] DF05Char = DF05.toCharArray();
			StringBuffer DF05StringBuffer = new StringBuffer();
			for (int j = 1; j < DF05Char.length; j = j + 2)
				DF05StringBuffer.append(DF05Char[j]);
			DF05 = DF05StringBuffer.toString();
			PARA_VAL = PARA_VAL.substring(22);

			String DF06 = PARA_VAL.substring(6, 8); // 哈希算法标识
			PARA_VAL = PARA_VAL.substring(8);

			String DF07 = PARA_VAL.substring(6, 8); // 数字签名算法长度
			PARA_VAL = PARA_VAL.substring(8);

			String DF02 = null; // 根CA公钥模
			PARA_VAL = PARA_VAL.substring(4);
			String lengthSi = PARA_VAL.substring(0, 2);
			if (lengthSi.equals("81")) {

				String lengthString = PARA_VAL.substring(2, 4);
				int length = Integer.parseInt(lengthString, 16);
				DF02 = PARA_VAL.substring(4, length * 2 + 4);
				PARA_VAL = PARA_VAL.substring(4 + length * 2);
			} else if (lengthSi.equals("82")) {

				String lengthString = PARA_VAL.substring(3, 6);
				int length = Integer.parseInt(lengthString, 16);
				DF02 = PARA_VAL.substring(6, length * 2 + 6);
				PARA_VAL = PARA_VAL.substring(6 + length * 2);
			} else {

				String lengthString = PARA_VAL.substring(0, 2);
				int length = Integer.parseInt(lengthString, 16);
				DF02 = PARA_VAL.substring(2, length * 2 + 2);
				PARA_VAL = PARA_VAL.substring(2 + length * 2);
			}

			String DF04 = PARA_VAL.substring(6, 8); // 公钥指数算法长度
			PARA_VAL = PARA_VAL.substring(8);

			String DF03 = PARA_VAL.substring(6); // 哈希结果

			obj[0] = paraIdx;
			obj[1] = usageKey;
			obj[2] = paraOrg;
			obj[3] = a9F06;
			obj[4] = a9F22;
			obj[5] = DF05;
			obj[6] = DF06;
			obj[7] = DF07;
			obj[8] = DF02;
			obj[9] = DF04;
			obj[10] = DF03;

			newDataList.add(obj);
		}

		ret[0] = newDataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询虚拟柜员
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhTlrInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "BRH_ID IN " + operator.getBrhBelowId());

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("tlrId"))) {
			whereSql.append(" AND TLR_ID  = '" + request.getParameter("tlrId")
					+ "' ");
		}

		String sql = "SELECT BRH_ID,TLR_ID,TLR_STA,RESV1,LAST_UPD_OPR_ID,REC_UPD_TS,REC_CRT_TS FROM TBL_BRH_TLR_INFO "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_BRH_TLR_INFO "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询计费算法
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblInfDiscCd(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		/*
		 * Operator operator = (Operator)
		 * request.getSession().getAttribute(Constants.OPERATOR_INFO);
		 * 
		 * String mccStr = null; String mccSql =
		 * "select disc_cd from tbl_inf_mchnt_tp where mchnt_tp='" +
		 * request.getParameter("mccCode") + "'"; List mccData =
		 * CommonFunction.getCommQueryDAO().findBySQLQuery(mccSql);
		 * if(mccData.size() != 0) { mccStr = (String)mccData.get(0);
		 * if(isNotEmpty(mccStr)) { String[] mccArr = mccStr.split(",");
		 * StringBuffer mccBuffer = new StringBuffer(); for(String mcc:mccArr) {
		 * mccBuffer.append("'"); mccBuffer.append(mcc); mccBuffer.append("',");
		 * } mccStr = mccBuffer.toString(); if(mccStr.endsWith(",")) { mccStr =
		 * mccStr.substring(0, mccStr.length()-1); } } }
		 * 
		 * if (isNotEmpty(mccStr)) { whereSql.append(" AND disc_cd in (" +
		 * mccStr + ") "); } else { ret[0] = new ArrayList(); ret[1] = "0";
		 * return ret; }
		 */
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		if (isNotEmpty(request.getParameter("discCd"))) {
			whereSql.append(" AND DISC_CD = '" + request.getParameter("discCd")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("discId"))) {
			whereSql.append(" AND DISC_CD = '" + request.getParameter("discId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("discNm"))) {
			whereSql.append(" AND DISC_NM like '%"
					+ request.getParameter("discNm") + "%' ");
		}
		String sql = "SELECT DISC_CD,DISC_NM,DISC_ORG,LAST_OPER_IN,REC_UPD_USER_ID,substr(REC_UPD_TS,1,8),substr(REC_CRT_TS,1,8) FROM TBL_INF_DISC_CD "
				+ whereSql.toString();
		sql += " order by DISC_CD";

		String countSql = "SELECT COUNT(*) FROM TBL_INF_DISC_CD "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询计费算法2
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblInfDiscCd2(int begin,
			HttpServletRequest request) {

		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		if (isNotEmpty(request.getParameter("discCd"))) {
			whereSql.append(" AND DISC_CD = '" + request.getParameter("discCd")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("discNm"))) {
			whereSql.append(" AND DISC_NM like '%"
					+ request.getParameter("discNm") + "%' ");
		}
		String sql = "SELECT DISC_CD,DISC_NM,DISC_ORG,LAST_OPER_IN,REC_UPD_USER_ID,substr(REC_UPD_TS,1,8),substr(REC_CRT_TS,1,8) FROM TBL_INF_DISC_CD "
				+ whereSql.toString();
		sql += " order by DISC_CD";

		String countSql = "SELECT COUNT(*) FROM TBL_INF_DISC_CD "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询交易类型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblTxnName(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "SELECT TXN_NUM,TXN_NAME,TXN_DSP,DC_FLAG FROM TBL_TXN_NAME ";
		String countSql = "SELECT COUNT(*) FROM TBL_TXN_NAME ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 清算凭证
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSettleDocInfo(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String sql = "select BRH_ID,EXCHANGE_NO,PAYER_NAME,PAYER_ACCOUNT_NO,PAY_BANK_NO,IN_BANK_SETTLE_NO,SETTLE_BUS_NO,INNER_ACCT,INNER_ACCT1,INNER_ACCT2 from SETTLE_DOC_INFO";
		sql = sql + " WHERE " + "BRH_ID IN " + operator.getBrhBelowId();
		String countSql = "select count(*) from SETTLE_DOC_INFO";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询计费算法
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getDiscInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String discId = request.getParameter("discCd");

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "trim(DISC_ID) = '" + discId + "'");

		String sql = "SELECT TXN_NUM,FLOOR_AMOUNT,"
				+ "(case when FLAG != 2 then null else MIN_FEE end) as minFee,"
				+ "(case when FLAG != 2 then null when FLAG = 2 and MAX_FEE = 999999999 then null else MAX_FEE end) as maxFee,"
				+ "FLAG,FEE_VALUE,CARD_TYPE FROM TBL_HIS_DISC_ALGO "
				+ whereSql.toString();
		sql += " order by TXN_NUM,FLOOR_AMOUNT";

		String countSql = "SELECT COUNT(*) FROM TBL_HIS_DISC_ALGO "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询差错列表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getErrorList(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "1=1 ");

		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql.append(" AND mchnt_no = '"
					+ request.getParameter("mchntId") + "' ");
		}
		if (isNotEmpty(request.getParameter("errType"))) {
			whereSql.append(" AND err_type = '"
					+ request.getParameter("errType") + "' ");
		}
		if (isNotEmpty(request.getParameter("stRegTime"))) {
			whereSql.append(" AND regist_time >='"
					+ request.getParameter("stRegTime") + "' ");
		}
		if (isNotEmpty(request.getParameter("enRegTime"))) {
			whereSql.append(" AND regist_time < '"
					+ request.getParameter("enRegTime") + "' ");
		}
		if (isNotEmpty(request.getParameter("stTime"))) {
			whereSql.append(" AND start_time >= '"
					+ request.getParameter("stTime") + "' ");
		}

		if (isNotEmpty(request.getParameter("enTime"))) {
			whereSql.append(" AND start_time < '"
					+ request.getParameter("enTime") + "' ");
		}

		String sql = "SELECT ERR_SEQ_NO,MCHNT_NO,TERM_ID,MCHNT_NM,ERR_TYPE,ERR_DESC,CD_FLAG,SYS_SEQ_NO,AMT1,FEE1,REGIST_OPR,reserved,rec_Crt_Ts,rec_Upd_Ts,RECORD_FLAG,REGIST_TIME,ERR_STATUS,"
				+ " START_TIME FROM BTH_ERR_REGIST_TXN " + whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM BTH_ERR_REGIST_TXN "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 证书影像
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getImgInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String imagesId = request.getParameter("imagesId");

		List<Object[]> dataList = new ArrayList<Object[]>();
		String basePath = SysParamUtil
				.getParam(SysParamConstants.FILE_UPLOAD_DISK);

		basePath = basePath.replace("\\", "/");
		File fl = new File(basePath);
		FileFilter filter = new FileFilter(imagesId);
		// 启用过滤
		File[] files = fl.listFiles(filter);

		// File[] files = fl.listFiles();

		if (null == files) {
			ret[0] = dataList;
			ret[1] = dataList.size();
			return ret;
		}
		// FileInputStream fs = null;
		BufferedImage bi = null;
		for (File file : files) {
			Object[] obj = new Object[6];
			obj[0] = "imageSelf" + dataList.size();
			obj[1] = "btBig" + dataList.size();
			obj[2] = "btDel" + dataList.size();
			try {
				// fs=new FileInputStream(file);
				bi = ImageIO.read(file);
				double width = bi.getWidth();
				double height = bi.getHeight();
				// bi.flush();
				bi = null;
				double rate = 0;
				// 等比例缩放
				if (width > 400 || height > 400) {
					if (width > height) {
						rate = 400 / width;
						width = 400;
						height = height * rate;
					} else {
						rate = 400 / height;
						height = 400;
						width = width * rate;
					}
				}
				obj[3] = (int) width;
				obj[4] = (int) height;

				// fs.close();
			} catch (Exception e) {
				obj[3] = 400;
				obj[4] = 400;
				/*
				 * try { if (null != fs) { fs.close(); } } catch (Exception el)
				 * { el.printStackTrace(); }
				 */
				e.printStackTrace();
			}
			obj[5] = file.getName();
			dataList.add(obj);

		}
		ret[0] = dataList;
		ret[1] = dataList.size();
		return ret;
	}

	/**
	 * 证书影像
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getImgInfTmp(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String imagesId = request.getParameter("imagesId");
		String mcht = request.getParameter("mcht");
		String upload = request.getParameter("upload");
		if(upload!=null){
			upload+="/";
		}else upload="upload/";
		List<Object[]> dataList = new ArrayList<Object[]>();
		String basePath = SysParamUtil
				.getParam(SysParamConstants.FILE_UPLOAD_DISK);

		basePath = basePath.replace("\\", "/");
		if (StringUtil.isNotEmpty(mcht)) {
			basePath += mcht + "/";
		} else {
			basePath += "addTmp/";
		}
		System.out.println(basePath);
			File fl = new File(basePath+upload);
			FileFilter filter = new FileFilter(imagesId);
			//lmj 增加分类目录 修改生成file数组的方法
			// 启用过滤
			File[] files = fl.listFiles(filter);
			
			// File[] files = fl.listFiles();
			
			// FileInputStream fs = null;
			if(files==null){
				files=new File[0];
			}
			for (File file : files) {
				Object[] obj = new Object[6];
				obj[0] = "imageSelf" + dataList.size()+upload.replace("/", "");
				obj[1] = "btBig" + dataList.size()+upload.replace("/", "");
				obj[2] = "btDel" + dataList.size()+upload.replace("/", "");
				try {
					// fs=new FileInputStream(file);
					BufferedImage bi = ImageIO.read(file);
					double width = bi.getWidth();
					double height = bi.getHeight();
					// bi.flush();
					// bi=null;
					double rate = 0;
					// 等比例缩放
					if (width > 400 || height > 400) {
						if (width > height) {
							rate = 400 / width;
							width = 400;
							height = height * rate;
						} else {
							rate = 400 / height;
							height = 400;
							width = width * rate;
						}
					}
					obj[3] = (int) width;
					obj[4] = (int) height;
					
					// fs.close();
				} catch (Exception e) {
					obj[3] = 400;
					obj[4] = 400;
					// try {
					// if (null != fs) {
					// fs.close();
					// }
					// } catch (Exception el) {
					// el.printStackTrace();
					// }
					e.printStackTrace();
				}
				obj[5] = upload+file.getName();
				dataList.add(obj);
				
			}
		
		ret[0] = dataList;
		ret[1] = dataList.size();
		return ret;
	}

	/**
	 * 获取商户下挂终端
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-29下午05:51:01
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntTermInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String mchntNo = request.getParameter("mchntNo");
		String sql = "select TERM_ID,TERM_STA,TERM_SIGN_STA,REC_CRT_TS FROM TBL_TERM_INF WHERE MCHT_CD = '"
				+ mchntNo.trim() + "' ";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF where  MCHT_CD = '"
				+ mchntNo.trim() + "'";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 获取商户下挂终端
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-29下午05:51:01
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntTermInfoTmp(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String mchntNo = request.getParameter("mchntNo");
		String sql = "select TERM_ID,TERM_STA,TERM_SIGN_STA,REC_CRT_TS FROM TBL_TERM_INF_TMP WHERE MCHT_CD = '"
				+ mchntNo.trim() + "' ";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF_TMP where  MCHT_CD = '"
				+ mchntNo.trim() + "' ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str.trim()))
			return true;
		else
			return false;
	}

	public static Object[] riskTxnInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();
		boolean flag = true;
		if (isNotEmpty(request.getParameter("saTxnCard"))) {
			whereSql.append(" AND trim(sa_txn_card) = '"
					+ request.getParameter("saTxnCard").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("saMchtNo"))) {
			whereSql.append(" AND trim(sa_mcht_no) = '"
					+ request.getParameter("saMchtNo").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND trim(sa_txn_date) >= '"
					+ request.getParameter("startDate").trim() + "' ");
			flag = false;
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND trim(sa_txn_date) <= '"
					+ request.getParameter("endDate").trim() + "' ");
			flag = false;
		}
		String sql = "";

		String countSql = "select count(*) "
				+ "from tbl_clc_mon,tbl_txn_name "
				+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) in (select SA_MODEL_KIND from TBL_RISK_INF) "
				+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		if (flag) {
			sql = "select first 100 sa_txn_card,sa_mcht_no,(SELECT mcht_nm FROM tbl_mcht_base_inf C WHERE A.sa_mcht_no=C.mcht_no ) AS MCHTNAME,sa_term_no,txn_name,"
					+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
					+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG,sa_clc_rsn1 "
					+ "from tbl_clc_mon A,tbl_txn_name "
					+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) in (select SA_MODEL_KIND from TBL_RISK_INF) ";
			if (Integer.parseInt(count) > 100) {
				count = "100";
			}

		} else {
			sql = "select sa_txn_card,sa_mcht_no,(SELECT mcht_nm FROM tbl_mcht_base_inf C WHERE A.sa_mcht_no=C.mcht_no ) AS MCHTNAME,sa_term_no,txn_name,"
					+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
					+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG,sa_clc_rsn1 "
					+ "from tbl_clc_mon A,tbl_txn_name "
					+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) in (select SA_MODEL_KIND from TBL_RISK_INF) ";

		}
		sql += whereSql.toString();

		sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		ret[0] = dataList;
		ret[1] = count;
		flag = true;
		return ret;
	}

	/**
	 * 查询专业服务机构信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getOrganInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String orgId = request.getParameter("orgId");
		String branch = request.getParameter("branch");
		StringBuffer whereSql = new StringBuffer("WHERE branch in")
				.append(operator.getBrhBelowId());
		if (orgId != null && !orgId.equals(""))
			whereSql.append(" and org_Id='").append(orgId).append("'");
		if (branch != null && !branch.equals(""))
			whereSql.append(" and branch='").append(branch).append("'");
		StringBuffer sql = new StringBuffer(
				"SELECT ORG_ID,BRANCH,ORG_NAME,RATE,RATE_TYPE,REMARKS,MISC,CRT_OPR,CRT_TS,LAST_UPD_OPR,LAST_UPD_TS FROM TBL_PROFESSIONAL_ORGAN ");

		sql.append(whereSql);

		String countSql = "SELECT COUNT(*) FROM TBL_PROFESSIONAL_ORGAN "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 问卷查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] PaperRecInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String curPaperId = InformationUtil.getCurPaperId();

		// 现使用RESERVE1来保存是否为当前试卷，为当前试卷RESERVE1=1，否则为0

		String sql = "select r.PAPER_ID,r.RESERVE1,r.RESERVE2,r.CRT_USER,substr(r.CRT_TIME,1,8),"
				+ "(case when p.QUESTION = r.PAPER_ID then '1' else '0' end) as STA "
				+ "from TBL_PAPER_REC_INF r,TBL_PAPER_DEF_INF p where p.PAPER_ID = 'PAPER_STATUS' order by r.CRT_TIME DESC";

		// String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		String countSql = "select count(*) from TBL_PAPER_REC_INF";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 问卷历史查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] PaperHisInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		// 问卷编号
		if (isNotEmpty(request.getParameter("PAPER_ID"))) {
			whereSql.append(" WHERE PAPER_ID = '"
					+ request.getParameter("PAPER_ID") + "' ");
		}

		String sql = "SELECT QUES_ID,QUES_INDEX,QUESTION,OPTIONS,REMARKS FROM TBL_PAPER_HIS_INF ";

		sql += whereSql.toString();

		sql += "ORDER BY QUES_INDEX";

		String countSql = "SELECT COUNT(*) FROM TBL_PAPER_HIS_INF ";
		countSql += whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 实时问卷查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] PaperDefInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT PAPER_ID,QUES_ID,QUES_INDEX,QUESTION,OPTIONS,REMARKS FROM TBL_PAPER_DEF_INF ";

		sql += "where PAPER_ID != 'PAPER_STATUS' ";

		sql += "ORDER BY QUES_INDEX";

		String countSql = "SELECT count(*) FROM TBL_PAPER_DEF_INF where PAPER_ID != 'PAPER_STATUS'";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户评级查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] PaperLelInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer sb = new StringBuffer(" b.BANK_NO IN  "
				+ operator.getBrhBelowId() + " ");
		// sb.append("and b.BANK_NO in " + operator.getBrhBelowId());

		if (!StringUtil.isNull(request.getParameter("BANK_NO"))) {
			sb.append(" and b.BANK_NO IN "
					+ InformationUtil.getBrhGroupString(request
							.getParameter("BANK_NO")) + "");
		}

		if (!StringUtil.isNull(request.getParameter("MCHT_NO"))) {
			sb.append(" and b.MCHT_NO = '" + request.getParameter("MCHT_NO")
					+ "' ");
		}

		if (!StringUtil.isNull(request.getParameter("MCHT_LEVEL"))) {
			sb.append(" and MCHT_LEVEL = '"
					+ request.getParameter("MCHT_LEVEL") + "' ");
		}

		String sql = "SELECT a.SEL_MCHT_ID, a.PAPER_ID, a.MCHT_ID,b.MCHT_NM, a.MCHT_POINT, a.MCHT_LEVEL, a.RESERVE1, a.RESERVE2, a.CRT_USER, substr(a.CRT_TIME,1,8) "
				+ "FROM TBL_PAPER_LEL_INF a left outer join TBL_MCHT_BASE_INF_TMP b on(trim(a.MCHT_ID) = trim(b.MCHT_NO))"
				+ " where " + sb + "";

		// sql += sb.toString();

		sql += " order by a.MCHT_ID,a.CRT_TIME desc";

		String countSql = "select count(*) FROM TBL_PAPER_LEL_INF a left outer join TBL_MCHT_BASE_INF_TMP b on(trim(a.MCHT_ID) = trim(b.MCHT_NO))"
				+ " where " + sb + "";
		// countSql += sb.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取选项信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getQuestionInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		String sql = "SELECT OPT_ID,OPT,POINT FROM TBL_PAPER_OPT_INF "
				+ "where trim(QUES_ID) = '"
				+ request.getParameter("quesId").trim() + "' ";
		sql += "order by POINT DESC";

		// String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		String countSql = "SELECT count(*) FROM TBL_PAPER_OPT_INF "
				+ "where trim(QUES_ID) = '"
				+ request.getParameter("quesId").trim() + "' ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取营销活动信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMarketAct(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String actNo = request.getParameter("actNo");
		String actName = request.getParameter("actName");
		String actCycle = request.getParameter("actCycle");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String state = request.getParameter("state");
		StringBuffer whereSql = new StringBuffer("WHERE BANK_NO in ")
				.append(operator.getBrhBelowId());

		if (isNotEmpty(state)) {
			whereSql.append(" AND STATE ='").append(state).append("'");
		}
		if (isNotEmpty(actNo)) {
			whereSql.append(" AND ACT_NO ='").append(actNo).append("'");
		}
		if (isNotEmpty(actName)) {
			whereSql.append(" AND ACT_NAME ='").append(actName).append("'");
		}
		if (isNotEmpty(actCycle)) {
			whereSql.append(" AND ACT_CYCLE ='").append(actCycle).append("'");
		}
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND START_DATE <").append(startDate);
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND END_DATE >").append(endDate);
		}

		String sql = "SELECT BANK_NO,ACT_NO,ACT_NAME,STATE,START_DATE,END_DATE,ACT_CYCLE,ORGAN_NO,ORGAN_TYPE,ACT_CONTENT,ACT_FEE,REMARKS,FLAG01,CRT_DT,CRT_OPR,UPD_DT,UPD_OPR,REC_OPR,MISC1 FROM TBL_MARKET_ACT_REVIEW "
				+ whereSql.toString() + " ORDER BY ACT_NO";

		String countSql = "SELECT COUNT(*) FROM TBL_MARKET_ACT_REVIEW "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取持卡人营销活动信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getActInfo(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String actNo = request.getParameter("actNo");
		String actContent = request.getParameter("actContent");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		StringBuffer whereSql = new StringBuffer("WHERE 1=1 ");
		// new StringBuffer("WHERE BANK_NO in ")
		// .append(operator.getBrhBelowId());

		if (isNotEmpty(actNo)) {
			whereSql.append(" AND ACT_NO ='").append(actNo).append("'");
		}
		if (isNotEmpty(actContent)) {
			whereSql.append(" AND ACT_CONTENT ='").append(actContent)
					.append("'");
		}
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND START_DATE >=").append(startDate);
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND END_DATE <=").append(endDate);
		}

		String sql = "SELECT ACT_NO,ACT_NAME,DEV_NUM,DEV_PROD,START_DATE,END_DATE,ACT_CONTENT,ACT_FEE FROM tbl_holder_act a "
				+ whereSql.toString() + " ORDER BY ACT_NO";

		String countSql = "SELECT COUNT(*) FROM tbl_holder_act "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取营销活动商户参与信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntParticipat(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String actNo = request.getParameter("actNo");
		String state = request.getParameter("state");
		String flag = request.getParameter("flag");
		String noFlag = request.getParameter("noFlag");
		StringBuffer whereSql = new StringBuffer(
				" WHERE t1.STATE!='2' AND t1.BANK_NO in ").append(operator
				.getBrhBelowId());
		if (!StringUtil.isNull(flag))
			whereSql.append(" AND t1.FLAG01='").append(flag).append("'");
		if (!StringUtil.isNull(noFlag))
			whereSql.append(" AND t1.FLAG01!='").append(noFlag).append("'");
		if (!StringUtil.isNull(actNo))
			whereSql.append(" AND t1.ACT_NO='").append(actNo).append("'");
		if (!StringUtil.isNull(state))
			whereSql.append(" AND t1.STATE='").append(state).append("'");
		StringBuffer sql = new StringBuffer(
				"SELECT t1.ACT_NO,t1.ACT_NAME,t1.BANK_NO,")
				.append("t1.STATE,t1.START_DATE,t1.END_DATE,t1.MCHNT_NO,t2.MCHT_NM,")
				.append("t1.ACT_CONTENT,t1.ACT_FEE,t1.FLAG01 FROM TBL_MCHNT_PARTICIPAT_REVIEW t1 ")
				.append("left join tbl_mcht_base_inf t2 on t1.MCHNT_NO=t2.MCHT_NO")
				.append(whereSql).append(" ORDER BY t1.ACT_NO");
		String countSql = "SELECT COUNT(*) FROM TBL_MCHNT_PARTICIPAT_REVIEW t1"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin, 200);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户信息(营销活动)
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoForActivity(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String bankNo = request.getParameter("bankNo");
		String mchtGrp = request.getParameter("mchtGrp");
		String mchnNo = request.getParameter("mchnNo");
		String connType = request.getParameter("connType");
		StringBuffer whereSql = new StringBuffer(
				" WHERE t1.MCHT_NO= t2.MCHT_NO and t2.FEE_RATE = t3.DISC_CD ")
				.append("and  t1.MCHT_STATUS = '0' ").append("and BANK_NO in ")
				.append(operator.getBrhBelowId());

		if (isNotEmpty(mchnNo)) {
			whereSql.append(" AND t1.MCHT_NO = '").append(mchnNo).append("' ");
		}
		if (isNotEmpty(mchtGrp)) {
			whereSql.append(" AND t1.mcht_grp = '").append(mchtGrp)
					.append("' ");
		}
		if (isNotEmpty(bankNo)) {
			whereSql.append(" AND t1.BANK_NO = '").append(bankNo).append("' ");
		}
		if (isNotEmpty(connType)) {
			whereSql.append(" AND t1.CONN_TYPE = '").append(connType)
					.append("' ");
		}
		Object[] ret = new Object[2];

		StringBuffer sql = new StringBuffer(
				"SELECT t1.MCHT_NO,t1.MCHT_NM,substr(t4.DESCR,1,4),t1.MCC,t1.BANK_NO,")
				.append("case CONN_TYPE when 'J' then '间联' when 'Z' then '直联' else CONN_TYPE end CONN_TYPE")
				.append(",t3.DISC_NM FROM TBL_MCHT_BASE_INF t1 left join TBL_INF_MCHNT_TP_GRP t4 on t1.MCHT_GRP=t4.MCHNT_TP_GRP,")
				.append("TBL_MCHT_SETTLE_INF t2,TBL_INF_DISC_CD t3 ")
				.append(whereSql).append(" ORDER BY t1.MCHT_NO");
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF t1,TBL_MCHT_SETTLE_INF t2,TBL_INF_DISC_CD t3 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin, 200);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * IC卡其他参数维护
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] icParam(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		String sql = "SELECT USAGE_KEY,PARA_IDX,PARA_ID,PARA_LEN,PARA_VAL,REC_UPD_OPR,REC_CRT_TS from TBL_EMV_PARA where trim(USAGE_KEY) = '0' order by PARA_IDX";

		String countSql = "SELECT COUNT(*) from TBL_EMV_PARA where trim(USAGE_KEY) = '0'";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 配置首页提示信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] firstMsg(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		Object[] ret = new Object[2];

		String sql = "SELECT TRIM(f.BRH_ID),BRH_NAME,PPT_MSG,CRT_OPR,CRT_DATE,UPD_OPR,UPD_DATE from TBL_FIRST_PAGE f,TBL_BRH_INFO b "
				+ "where TRIM(f.BRH_ID) = b.CUP_BRH_ID AND b.BRH_ID in "
				+ operator.getBrhBelowId()
				+ " and b.brh_level='1' order by f.BRH_ID desc";

		String countSql = "SELECT count(*) from TBL_FIRST_PAGE f,TBL_BRH_INFO b "
				+ "where TRIM(f.BRH_ID) = b.CUP_BRH_ID AND b.BRH_ID in "
				+ operator.getBrhBelowId() + " and b.brh_level='1' ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端菜单配置
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] menuMsg(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String menuLevel = request.getParameter("menuLevel");
		String brhId = request.getParameter("brhId");
		String verId = request.getParameter("verId");
		Object[] ret = new Object[2];
		StringBuffer where = new StringBuffer(
				" t1 left join tbl_brh_info t2 on t1.brh_id = t2.cup_brh_id ")
				.append("where t2.brh_id in ").append(operator.getBrhBelowId());
		if (brhId != null && !brhId.equals("")) {
			where.append(" and t1.BRH_ID='").append(brhId).append("'");
		}
		if (verId != null && !verId.equals("")) {
			where.append(" and t1.VER_ID='").append(verId).append("'");
		}
		if (menuLevel != null && !menuLevel.equals("")) {
			where.append(" and t1.MENU_LEVEL='").append(menuLevel).append("'");
		}
		where.append(" order by t1.BRH_ID,t1.VER_ID,t1.MENU_ID");
		String sql = "SELECT t1.USAGE_KEY,t1.BRH_ID,t1.MENU_ID,t1.MENU_LEVEL,t1.MENU_MSG,t1.MENU_PRE_ID1,t1.MENU_PRE_ID2,t1.TXN_CODE,t1.CON_FLAG,t1.PPT_ID,t1.OPR_ID,t1.VER_ID,t1.CRT_DATE,t1.UPD_DATE from tbl_menu_msg";
		String countSql = "SELECT count(*) FROM tbl_menu_msg "
				+ where.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + where.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 交易提示信息配置
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] pptMsg(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		// StringBuffer where = new
		// StringBuffer(" where brh_id in ").append(operator.getBrhBelowId());
		String sql = "SELECT USAGE_KEY,PPT_ID,TMP_ID,MSG_FMT1,PPT_MSG1,MSG_FMT2,PPT_MSG2,MSG_FMT3,PPT_MSG3,MSG_TYPE,VER_ID from tbl_ppt_msg";
		String countSql = "SELECT COUNT(*) FROM tbl_ppt_msg ";// +where.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql/* +where.toString() */, begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端打印信息控制
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] prtMsg(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];

		String sql = "SELECT USAGE_KEY,PRT_ID,PRT_MSG,CRT_DATE,UPD_DATE FROM TBL_PRT_MSG order by PRT_ID";

		String countSql = "SELECT COUNT(*) FROM TBL_PRT_MSG";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端返回码说明
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] rspMsg(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];

		String sql = "SELECT RSP_NO,CHG_NO,ERR_NO,ERR_MSG,CRT_DATE,UPD_DATE FROM TBL_RSP_MSG order by CHG_NO,ERR_NO";

		String countSql = "SELECT COUNT(*) FROM TBL_RSP_MSG";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 交易详细定义
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] txnCfgDsp(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		Object[] ret = new Object[2];

		String sql = "SELECT t.BRH_ID,b.BRH_NAME,TERM_TXN_CODE,USAGE_KEY,DSP FROM TBL_TXN_CFG_DSP t,TBL_BRH_INFO b "
				+ "WHERE t.BRH_ID = b.BRH_ID AND t.BRH_ID IN "
				+ operator.getBrhBelowId()
				+ " order by t.BRH_ID,t.TERM_TXN_CODE desc";

		String countSql = "SELECT COUNT(*) FROM TBL_TXN_CFG_DSP t,TBL_BRH_INFO b "
				+ "WHERE t.BRH_ID = b.BRH_ID AND t.BRH_ID IN "
				+ operator.getBrhBelowId();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端交易映射信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] termTxnCode(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String sql = "SELECT TERM_TXN_CODE,INT_TXN_CODE,DSP FROM TBL_TERM_TXN order by TERM_TXN_CODE";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_TXN";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端版本管理
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] verMng(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String sql = "SELECT t1.VER_ID,t1.BANK_ID,t2.BRH_NAME,t1.MISC,t1.CRT_DATE,t1.UPD_DATE FROM TBL_VER_MNG t1 left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id "
				+ "WHERE t2.BRH_ID IN "
				+ operator.getBrhBelowId()
				+ "and t2.brh_level ='1' ORDER BY t1.BANK_ID,t1.VER_ID ";
		String countSql = "SELECT COUNT(*) FROM TBL_VER_MNG t1 left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id "
				+ "WHERE t2.BRH_ID IN "
				+ operator.getBrhBelowId()
				+ "and t2.brh_level ='1'";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端菜单管理
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getEposMenu(int begin, HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String sql = "SELECT t1.BANK_ID,t2.BRH_NAME,t1.VER_ID,t1.MISC,"
				+ "nvl((select count(*) from TBL_MENU_MSG v1 where MENU_LEVEL = '1' and v1.BRH_ID = t1.BANK_ID and v1.VER_ID = t1.VER_ID group by BRH_ID,VER_ID),0) as MENU_NUM1,"
				+ "nvl((select count(*) from TBL_MENU_MSG v2 WHERE MENU_LEVEL = '2' and v2.BRH_ID = t1.BANK_ID and v2.VER_ID = t1.VER_ID group by BRH_ID,VER_ID),0) as MENU_NUM2,"
				+ "nvl((select count(*) from TBL_MENU_MSG v3 WHERE MENU_LEVEL = '3' and v3.BRH_ID = t1.BANK_ID and v3.VER_ID = t1.VER_ID group by BRH_ID,VER_ID),0) as MENU_NUM3 "
				+ "FROM TBL_VER_MNG t1 "
				+ "left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id and t2.brh_level='1' "
				+ "WHERE t2.BRH_ID IN " + operator.getBrhBelowId();

		if (!StringUtil.isNull(request.getParameter("brhId"))) {
			sql += " and t1.BANK_ID = '" + request.getParameter("brhId") + "' ";
		}
		if (!StringUtil.isNull(request.getParameter("verId"))) {
			sql += " and t1.VER_ID = '" + request.getParameter("verId") + "' ";
		}

		sql += " ORDER BY t1.BANK_ID,t1.VER_ID ";

		String countSql = "SELECT COUNT(*) FROM TBL_VER_MNG t1 "
				+ "left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id and t2.brh_level='1' "
				+ "WHERE t2.BRH_ID IN " + operator.getBrhBelowId();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端菜单配置
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] eposMenuMsg(int begin, HttpServletRequest request) {

		String menuLevel = request.getParameter("menuLevel");
		String brhId = request.getParameter("brhId");
		String verId = request.getParameter("verId");

		Object[] ret = new Object[2];
		StringBuffer where = new StringBuffer();
		if (brhId != null && !brhId.equals("")) {
			where.append(" and t1.BRH_ID='").append(brhId).append("'");
		}
		if (verId != null && !verId.equals("")) {
			where.append(" and t1.VER_ID='").append(verId).append("'");
		}
		if (menuLevel != null && !menuLevel.equals("")) {
			where.append(" and t1.MENU_LEVEL='").append(menuLevel).append("'");
		}

		if (menuLevel.equals("2")) {
			where.append(" and t1.MENU_PRE_ID1 = '"
					+ request.getParameter("upMenuId1") + "'");
		} else if (menuLevel.equals("3")) {
			where.append(" and t1.MENU_PRE_ID1 = '"
					+ request.getParameter("upMenuId1") + "'");
			where.append(" and t1.MENU_PRE_ID2 = '"
					+ request.getParameter("upMenuId2") + "'");
		}

		String countSql = "SELECT count(*) FROM tbl_menu_msg t1 "
				+ "left join TBL_TERM_TXN t2 on(t1.TXN_CODE = t2.INT_TXN_CODE and mod(t2.INT_TXN_CODE,2)=1) "
				+ "left join TBL_PPT_MSG t3 "
				+ "on (t3.usage_key = '0' and t3.msg_type = '1' and t1.ppt_id = t3.ppt_id) where 1 = 1 "
				+ where;

		where.append(" order by t1.MENU_ID");
		String sql = "SELECT t1.USAGE_KEY,t1.BRH_ID,t1.MENU_ID,t1.MENU_LEVEL,t1.MENU_MSG,"
				+ "t1.MENU_PRE_ID1,t1.MENU_PRE_ID2,t1.TXN_CODE,t2.INT_TXN_CODE||'-'||t2.DSP as TXN_DSP,"
				+ "t1.CON_FLAG,t1.PPT_ID,t3.ppt_id||'-'||t3.ppt_msg1 as PPT_MSG,"
				+ "t1.OPR_ID,t1.VER_ID,t1.CRT_DATE,t1.UPD_DATE from tbl_menu_msg t1 "
				+ "left join TBL_TERM_TXN t2 on(t1.TXN_CODE = t2.INT_TXN_CODE and mod(t2.INT_TXN_CODE,2)=1) "
				+ "left join TBL_PPT_MSG t3 "
				+ "on (t3.usage_key = '0' and t3.msg_type = '1' and t1.ppt_id = t3.ppt_id) where 1 = 1 ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + where.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询TMK信息
	 */
	public static Object[] getTmkInfo(int begin, HttpServletRequest request) {

		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer(" and bank_no in "
				+ operator.getBrhBelowId());
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND mcht_cd='").append(mchtCd).append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND term_id like '%").append(termNo)
					.append("%' ");
		}

		Object[] ret = new Object[2];
		String sql = "select mcht_cd,(SELECT mcht_nm from tbl_mcht_base_inf_tmp b where a.mcht_cd=b.mcht_no) as mch,"
				+ "term_id,pos_tmk,tmk_st from tbl_term_key a,tbl_mcht_base_inf c where a.mcht_cd=c.mcht_no "
				+ whereSql + " order by a.tmk_st ,a.term_id desc ";
		String countSql = "select count(*) from tbl_term_key a,  tbl_mcht_base_inf c where a.mcht_cd=c.mcht_no "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		// List<Object[]> dataList =
		// CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构密钥
	 */
	public static Object[] getKeyInfo(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		String sql = "select ins_id_cd, ins_key_idx, ins_mac_1 , ins_mac_2, ins_pin_1, ins_pin_2 from tbl_ins_key ";
		String countSql = "select count(*) from tbl_ins_key ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构密钥 by ctz
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getkeyInfoFirst(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		String querySettleBrh = request.getParameter("querySettleBrh");
		String queryFirstMchtCd = request.getParameter("queryFirstMchtCd");
		String queryFirstTermId = request.getParameter("queryFirstTermId");
		String queryInsKeyIdx = request.getParameter("queryInsKeyIdx");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(querySettleBrh)) {
			whereSql.append(" AND a.ins_id_cd ='").append(querySettleBrh)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryFirstMchtCd)) {
			whereSql.append(" AND b.FIRST_MCHT_CD ='").append(queryFirstMchtCd)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryFirstTermId)) {
			whereSql.append(" AND b.FIRST_TERM_ID ='").append(queryFirstTermId)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryInsKeyIdx)) {
			whereSql.append(" AND a.ins_key_idx ='").append(queryInsKeyIdx)
					.append("' ");
		}
		String sql = "select a.ins_id_cd, a.ins_key_idx, a.ins_mac_1 , a.ins_mac_2, a.ins_pin_1, a.ins_pin_2,"
				+ " trim(b.FIRST_MCHT_CD),trim(b.FIRST_MCHT_CD)||' - '||trim(b.FIRST_MCHT_NM) ,b.FIRST_TERM_ID,a.REC_OPR_ID, "
				+ " a.ins_id_cd||' - '||t.FIRST_BRH_NAME as channel_nm "
				+ " from tbl_ins_key a "
				+ " left join tbl_first_mcht_inf b on trim(a.ins_id_cd) =b.RESERVED1 and trim(a.REC_UPD_TS)=trim(b.UPDT_TIME) "
				+ " left join TBL_FIRST_BRH_DEST_ID t on trim(a.ins_id_cd)=t.DEST_ID "
				+ whereSql;
		String countSql = "select count(*) from tbl_ins_key a "
				+ " left join tbl_first_mcht_inf b on trim(a.ins_id_cd) =b.RESERVED1 and trim(a.REC_UPD_TS)=trim(b.UPDT_TIME) "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构密钥 by ctz
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getkeyInfoFirstRoute(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		String querySettleBrh = request.getParameter("querySettleBrh");
		String queryFirstMchtCd = request.getParameter("queryFirstMchtCd");
		String queryFirstTermId = request.getParameter("queryFirstTermId");
		//String queryInsKeyIdx = request.getParameter("queryInsKeyIdx");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(querySettleBrh)) {
			whereSql.append(" AND a.dest_id ='").append(querySettleBrh)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryFirstMchtCd)) {
			whereSql.append(" AND c.mcht_id_up ='").append(queryFirstMchtCd)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryFirstTermId)) {
			whereSql.append(" AND c.term_id_up ='").append(queryFirstTermId)
					.append("' ");
		}
//		if (StringUtil.isNotEmpty(queryInsKeyIdx)) {
//			whereSql.append(" AND CASE when a.enc_type ='0' then c.zmk else a.brh_key end ='").append(queryInsKeyIdx)
//					.append("' ");
//		}
		String sql = "select distinct(a.dest_id) as ins_id_cd, a.dest_id || '-' || b.name as channel_nm, "
				+ " CASE when a.enc_type ='0' then c.zmk else a.brh_key end as ins_key_idx,"
				+ " CASE when a.enc_type ='0' then c.mac_key else a.mac_key end as mac_key,"
				+ " CASE when a.enc_type ='0' then c.pin_key else a.pin_key end as pin_key,"
				+ " CASE when a.enc_type ='0' then c.mcht_id_up else '' end as first_mcht_no,"
				+ " CASE when a.enc_type ='0' then c.mcht_id_up || '-' || c.mcht_name_up else '' end as first_mcht_nm,"
				+ " CASE when a.enc_type ='0' then c.term_id_up else '' end as first_term_id"
				+ " from tbl_route_upbrh2 a "
				+ " inner join tbl_route_upbrh b"
				+ " on a.brh_id2 = b.brh_id"
				+ " inner join tbl_upbrh_mcht c"
				+ " on a.brh_id2 = SUBSTR(c.brh_id3, 0, 8)"
				+ whereSql;
		String countSql = "select count(*) "
				+ " from tbl_route_upbrh2 a "
				+ " inner join tbl_route_upbrh b"
				+ " on a.brh_id2 = b.brh_id"
				+ " inner join tbl_upbrh_mcht c"
				+ " on a.brh_id2 = SUBSTR(c.brh_id3, 0, 8)"
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 补发设备同步
	 */
	public static Object[] getSendTermInfo(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		String whereSql = " WHERE FLAG != '1' ";
		String sql = "select a.term_id,(CASE SUBSTR(a.DEST_ID,4,4) WHEN '1' THEN '1601' WHEN '2' THEN '1602' WHEN '3' THEN '1603' WHEN '4' THEN '1604' END),a.BRH_ID,a.FLAG,SUBSTR(a.DEST_ID,1,1) AS TYPE,c.mcht_no||'-'||c.mcht_nm as mcht_no from tbl_cup_term_inf a  "
				+ " left join tbl_term_inf b on a.term_id = b.term_id left join tbl_mcht_base_inf c on b.mcht_cd=c.mcht_no "
				+ whereSql;
		String countSql = "select count(*) from tbl_cup_term_inf " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 持卡人营销活动商户参与表数据
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHoldMchtParticipatStore(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String actNoQuerry = request.getParameter("actNoQuerry");
		String mchtNoQuerry = request.getParameter("mchtNoQuerry");
		String mchtTypeQuerry = request.getParameter("mchtTypeQuerry");

		Object[] ret = new Object[2];
		StringBuffer where = new StringBuffer();
		if (actNoQuerry != null && !actNoQuerry.equals("")) {
			where.append(" and t1.ACT_NO='")
					.append(CommonFunction.fillString(actNoQuerry, ' ', 10,
							true)).append("'");
		}
		if (mchtNoQuerry != null && !mchtNoQuerry.equals("")) {
			where.append(" and t1.MCHT_NO='")
					.append(CommonFunction.fillString(mchtNoQuerry, ' ', 15,
							true)).append("'");
		}
		if (mchtTypeQuerry != null && !mchtTypeQuerry.equals("")) {
			where.append(" and t1.ACT_CONTENT='").append(mchtTypeQuerry)
					.append("'");
		}

		String sql = "select t1.ACT_NO,t2.ACT_NAME,t1.MCHT_NO,(select trim(t3.MCHT_NM) from TBL_MCHT_BASE_INF t3 where t3.MCHT_NO=t1.MCHT_NO),t1.ACT_CONTENT,t1.ACT_FEE,t1.DEV_NUM,t1.DEV_PROD from TBL_HOLD_MCHT_PARTICIPAT t1 "
				+ " join TBL_HOLDER_ACT t2 on t1.ACT_NO=t2.ACT_NO where 1=1 "
				+ where;
		String countSql = "select count(*) from TBL_HOLD_MCHT_PARTICIPAT t1 join TBL_HOLDER_ACT t2 on t1.ACT_NO=t2.ACT_NO "
				+ "where 1=1 " + where;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 持卡人营销活动抽奖
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHolderDrawActStore(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String actNo = request.getParameter("actNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		Object[] ret = new Object[2];
		StringBuffer where = new StringBuffer();
		if (actNo != null && !actNo.equals("")) {
			where.append(" and t1.act_no='")
					.append(CommonFunction.fillString(actNo, ' ', 10, true))
					.append("'");
		}
		if (startDate != null && !startDate.equals("")) {
			where.append(" and t1.start_date>='")
					.append(startDate.split("T")[0].replaceAll("-", ""))
					.append("'");
		}
		if (endDate != null && !endDate.equals("")) {
			where.append(" and t1.end_date<='")
					.append(endDate.split("T")[0].replaceAll("-", ""))
					.append("'");
		}

		String sql = "select t1.act_no,t1.act_name,t1.start_date,t1.end_date,t1.draw_amt,t1.a_grade,t1.a_rate,t1.a_num, t1.a_num_left,t1.a_desc,"
				+ " t1.b_grade,t1.b_rate,t1.b_num,t1.b_num_left,t1.b_desc,t1.c_grade,t1.c_rate,t1.c_num,t1.c_num_left,t1.c_desc,"
				+ " t1.d_grade,t1.d_rate,t1.d_num,t1.d_num_left,t1.d_desc,t1.e_grade,t1.e_rate,t1.e_num,t1.e_num_left,t1.e_desc "
				+ " from tbl_holder_draw_act t1 " + " where 1=1 " + where;
		String countSql = "select count(*) from tbl_holder_draw_act t1  "
				+ "where 1=1 " + where;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 持卡人营销活动抽奖
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHolderCardBin(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String actNo = request.getParameter("actNo");
		String cardId = request.getParameter("cardId");

		Object[] ret = new Object[2];
		StringBuffer where = new StringBuffer();
		if (actNo != null && !actNo.equals("")) {
			where.append(" and t1.act_no='")
					.append(CommonFunction.fillString(actNo, ' ', 10, true))
					.append("'");
		}
		if (cardId != null && !cardId.equals("")) {
			where.append(" and t1.cardId ='").append(6).append("'");
		}

		String sql = "select t1.act_no,t1.card_id,t1.card_type,"
				+ "trim(t3.act_name)as actName,t3.act_content,trim(t2.card_name)as cardName "
				+ " from tbl_holder_card_id t1 left join tbl_card_route t2 on t1.card_id =trim(t2.card_id)  left join tbl_holder_act t3 on "
				+ " t1.act_no=t3.act_no " + " where 1=1 " + where;
		String countSql = "select count(*) from tbl_holder_card_id t1  "
				+ "where 1=1 " + where;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 一级商户信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblFirstMchtInfo(int begin,
			HttpServletRequest request) {
		String firstMchtId = request.getParameter("firstMchtCd");
		Object[] ret = new Object[2];
		StringBuffer where = new StringBuffer();
		if (firstMchtId != null && !firstMchtId.equals("")) {
			where.append(" and a.FIRST_MCHT_CD = '").append(firstMchtId)
					.append("'");
		}
		String sql = "select a.first_mcht_cd,a.first_mcht_Nm,a.first_term_id,a.acq_inst_id,a.mcht_tp,a.fee_value,a.reserved,"
				+ " (select  trim(SRV_ID)||' - '||trim(SRV_DSP)  from TBL_SRV_INF where trim(SRV_ID)=a.reserved1) as sev_name, "
				+ " a.reserved2,a.reserved1||' - '||t.FIRST_BRH_NAME asCHANNEL_NM,substr(a.CRT_TIME,1,1) "
				+ " from tbl_first_mcht_inf a left join TBL_FIRST_BRH_DEST_ID t on trim(a.reserved1)=t.DEST_ID "
				+ " where 1=1 " + where + " order by a.first_mcht_cd ";
		String countSql = "select count(*) from tbl_first_mcht_inf a where 1=1 "
				+ where;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 一二级商户映射信息维护
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getFirstToSecondMchtInfo(int begin,
			HttpServletRequest request) {
		String firstMchtId = request.getParameter("firstMchtCd");
		String mchtId = request.getParameter("mchtNo");
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (firstMchtId != null && !firstMchtId.trim().equals("")) {
			whereSql.append(" and z.first_mcht_cd='").append(firstMchtId)
					.append("'");
		}

		if (mchtId != null && !mchtId.trim().equals("")) {
			whereSql.append(" and z.mcht_no='").append(mchtId).append("'");
		}
		String sql = "select x.mcht_no, n.mcht_nm, n.mcc, x.fee_rate||'-'||y.disc_nm as feeRate, z.ins_id_cd, z.first_mcht_cd, m.first_mcht_nm, z.first_term_id,m.mcht_tp, m.fee_value||'%-'||m.reserved as feeValue "
				+ "from tbl_mcht_settle_inf x, tbl_inf_disc_cd y,tbl_ins_mcht_inf z, tbl_first_mcht_inf m ,tbl_mcht_base_inf n "
				+ "where x.fee_rate=y.disc_cd and x.mcht_no=z.mcht_no and z.first_mcht_cd=m.first_mcht_cd and x.mcht_no=n.mcht_no "
				+ whereSql + " order by z.first_mcht_cd,x.mcht_no";
		String countSql = "select count(*) from tbl_mcht_settle_inf x, tbl_inf_disc_cd y,tbl_ins_mcht_inf z, tbl_first_mcht_inf m ,tbl_mcht_base_inf n "
				+ "where x.fee_rate=y.disc_cd and x.mcht_no=z.mcht_no and z.first_mcht_cd=m.first_mcht_cd and x.mcht_no=n.mcht_no "
				+ whereSql + " order by z.first_mcht_cd,x.mcht_no ";
		// sql =
		// "select x.mcht_no, n.mcht_nm, n.mcc, x.fee_rate||' - '||y.disc_nm,z.ins_id_cd, z.first_mcht_cd, m.first_mcht_nm, z.first_term_id,m.mcht_tp, m.fee_value||' - '||m.reserved "+
		// "from tbl_mcht_settle_inf x, tbl_inf_disc_cd y,tbl_ins_mcht_inf z, tbl_first_mcht_inf m ,tbl_mcht_base_inf n "
		// +
		// "where x.fee_rate=y.disc_cd and x.mcht_no=z.mcht_no and z.first_mcht_cd=m.first_mcht_cd and x.mcht_no=n.mcht_no order by z.first_mcht_cd,x.mcht_no";
		// countSql =
		// "select count(*) from tbl_mcht_settle_inf x, tbl_inf_disc_cd y,tbl_ins_mcht_inf z, tbl_first_mcht_inf m ,tbl_mcht_base_inf n "
		// +
		// "where x.fee_rate=y.disc_cd and x.mcht_no=z.mcht_no and z.first_mcht_cd=m.first_mcht_cd and x.mcht_no=n.mcht_no order by z.first_mcht_cd,x.mcht_no";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风控交易明细查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskData(int begin, HttpServletRequest request) {
		String riskStartDate = request.getParameter("startDate");
		String riskEndDate = request.getParameter("endDate");
		String amtTransLow = request.getParameter("queryAmtTransLow");
		String amtTransUp = request.getParameter("queryAmtTransUp");

		String txnName = request.getParameter("queryTxnName");
		String sysSeqNum = request.getParameter("querySysSeqNum");
		String riskId = request.getParameter("queryRiskId");
		String transState = request.getParameter("queryTransState");
		String pan = request.getParameter("queryPan");
		String cardAccpTermId = request.getParameter("queryCardAccpTermId");
		String cardAccpId = request.getParameter("queryCardAccpId");
		String queryRiskLvl = request.getParameter("queryRiskLvl");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(
				" where 1=1 and a.risk_id !='B01' ");
		if (StringUtil.isNotEmpty(riskStartDate)) {
			whereSql.append(" and a.risk_date>='").append(riskStartDate)
					.append("'");
		}
		if (StringUtil.isNotEmpty(riskEndDate)) {
			whereSql.append(" and a.risk_date<='").append(riskEndDate)
					.append("'");
		}

		if (isNotEmpty(amtTransLow)) {
			whereSql.append(" AND TO_NUMBER(NVL(trim(a.amt_trans),0))/100 ")
					.append(" >= ").append("'").append(amtTransLow).append("'");
		}
		if (isNotEmpty(amtTransUp)) {
			whereSql.append(" AND TO_NUMBER(NVL(trim(a.amt_trans),0))/100 ")
					.append(" <= ").append("'").append(amtTransUp).append("'");
		}

		if (StringUtil.isNotEmpty(txnName)) {
			whereSql.append(" and a.txn_num = '").append(txnName).append("'");
		}
		if (StringUtil.isNotEmpty(sysSeqNum)) {
			whereSql.append(" and a.sys_seq_num like '%").append(sysSeqNum)
					.append("%'");
		}
		if (StringUtil.isNotEmpty(riskId)) {
			whereSql.append(" and a.risk_id = '").append(riskId).append("'");
		}
		if (StringUtil.isNotEmpty(transState)) {
			if (RiskConstants.TXN_FAIL.equals(transState))
				whereSql.append(" and a.trans_state !='0' and a.trans_state !='1' ");
			else
				whereSql.append(" and a.trans_state = '").append(transState)
						.append("'");
		}
		if (StringUtil.isNotEmpty(pan)) {
			whereSql.append(" and a.pan like '%").append(pan).append("%'");
		}
		if (StringUtil.isNotEmpty(cardAccpTermId)) {
			whereSql.append(" and a.card_accp_term_id like '%")
					.append(cardAccpTermId).append("%'");
		}
		if (StringUtil.isNotEmpty(cardAccpId)) {
			whereSql.append(" and a.card_accp_id = '").append(cardAccpId)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryRiskLvl)) {
			whereSql.append(" and a.risk_lvl = '").append(queryRiskLvl)
					.append("'");
		}

		String sql = "select substr(a.inst_date,1,8),substr(a.inst_date,9),a.sys_seq_num,a.risk_date,a.risk_id,"
				+ " (select g.TXN_NAME from TBL_TXN_NAME g where a.txn_num=g.txn_num )as txnName,"
				+ " a.trans_state,a.pan,TO_NUMBER(NVL(trim(a.amt_trans),0))/100,a.card_accp_term_id,a.card_accp_id||'-'||b.MCHT_NM,"
				+ "(select a.risk_lvl||'-'||c.RESVED from TBL_RISK_LVL c where c.RISK_ID =a.RISK_ID and c.RISK_LVL=a.risk_lvl) as riskLevlName,"
				+ "(select b.bank_no||'-'||e.brh_name from tbl_brh_info e where trim(e.brh_id) =trim(b.bank_no) ) , "
				+ "(select trim(RSP_CODE) || '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = trim(a.RSP_CODE)) "
				+ " from TBL_RISK_DATA a  left join TBL_MCHT_BASE_INF b on a.card_accp_id=b.MCHT_NO "
				+ whereSql.toString()
				+ " order by a.risk_date desc,a.risk_id,a.inst_date desc ";
		String countSql = " select count(*) from TBL_RISK_DATA a "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		for (Object[] objects : dataList) {
			objects[8] = CommonFunction.moneyFormat(objects[8].toString());
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 路由规则一览查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRouteRuleInfo(int begin, HttpServletRequest request) {
		String queryRuleId = request.getParameter("queryRuleId");
		String queryRuleName = request.getParameter("queryRuleName");
		String queryBrhId = request.getParameter("queryBrhId");
		String queryBrhId2 = request.getParameter("queryBrhId2");

		String queryBrhId3 = request.getParameter("queryBrhId3");
		String queryStatus = request.getParameter("queryStatus");

		String queryGroup = request.getParameter("queryGroup");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if(StringUtil.isNotEmpty(queryGroup)){
			whereSql.append(" and a.mcht_gid ='").append(queryGroup).append("'");
		}else{
			whereSql.append(" and 1=2");
		}
		if (StringUtil.isNotEmpty(queryRuleId)) {
			whereSql.append(" and a.rule_id ='").append(queryRuleId)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryRuleName)) {
			whereSql.append(" and a.rule_name like '%").append(queryRuleName)
					.append("%'");
		}
		
		if (StringUtil.isNotEmpty(queryBrhId)) {
			whereSql.append(" and SUBSTR(a.brh_id3, 1, 4) ='").append(queryBrhId).append("'");
		}
		
		if (StringUtil.isNotEmpty(queryBrhId2)) {
			whereSql.append(" and SUBSTR(a.brh_id3, 1, 8) ='").append(queryBrhId2).append("'");
		}
		
		if (StringUtil.isNotEmpty(queryBrhId3)) {
			whereSql.append(" and a.brh_id3 ='").append(queryBrhId3).append("'");
		}

		if (StringUtil.isNotEmpty(queryStatus)) {
			whereSql.append(" and a.status ='").append(queryStatus).append("'");
		}
		
		String sql = "  select a.rule_id, a.rule_name, orders,d.name as brh_id, c.name as brh_id2, b.name brh_id3,a.status, a.rule_dsp from TBL_ROUTE_RULE_INFO a  inner join tbl_route_upbrh b on a.brh_id3 = b.brh_id and b.brh_level='3' inner join  tbl_route_upbrh c on substr(a.brh_id3,1,8) = c.brh_id and c.brh_level='2' inner join  tbl_route_upbrh d on substr(a.brh_id3,1,4) = d.brh_id and d.brh_level='1' "
				+ whereSql.toString() + " order by orders asc ";
		String countSql = " select count(*) from TBL_ROUTE_RULE_INFO a " + whereSql.toString();

		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(
				countSql);
		// for (Object[] objects : dataList) {
		// objects[8]=CommonFunction.moneyFormat(objects[8].toString());
		// }
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 路由规则详细查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRouteRuleDtl(int begin, HttpServletRequest request) {
		String queryRuleId = request.getParameter("ruleId");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
//		if (StringUtil.isNotEmpty(queryRuleId)) {
			whereSql.append(" and rule_id ='").append(queryRuleId)
					.append("'");
//		} else {
//			ret[0] = "";
//			ret[1] = 0;
//			return ret;
//		}


		String sql = " select " +
				"			rule_id" +
				"			, rule_name" +
				"			, rule_dsp" +
				"			, to_char(to_timestamp(valid_time,'yyyyMMddHH24miss'),'yyyy-MM-dd HH24:mi:ss') as valid_time" +
				"			, to_char(to_timestamp(invalid_time,'yyyyMMddHH24miss'),'yyyy-MM-dd HH24:mi:ss') as invalid_time" +
				"			, orders" +
				"			, SUBSTR(brh_id3, 1, 4) as brh_id" +
				"			, SUBSTR(brh_id3, 1, 8) as brh_id2" +
				"			, brh_id3" +
				"			, mcht_gid" +
				"			, card_issuerg_flag" +
				"			, card_issuerg" +
				"			, card_bin_flag" +
				"			, card_bin" +
				"			, card_type_flag" +
				"			, card_type" +
				"			, amount_flag" +
				"			, amount_bigger" +
				"			, amount_smaller" +
				"			, txng_flag" +
				"			, txng" +
				"			, areag_flag" +
				"			, areag" +
				"			, mccg_flag" +
				"			, mccg" +
				"			, times_flag" +
				"			, times" +
				"			, date_type" +
				"			, crt_time" +
				"			, upt_time" +
				"			, upt_opr" +
				"			, crt_opr" +
				"			, status" +
				"			, misc1" +
				"			, misc2" +
				" from TBL_ROUTE_RULE_INFO "
				+ whereSql.toString()
				+ " order by orders asc ";
		String countSql = " select count(*) from TBL_ROUTE_RULE_INFO a " + whereSql.toString();

		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(
				countSql);
		// 新增
		if (StringUtil.isEmpty(queryRuleId)) {
			count = "1";
		}
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	private static String getWhereOnCommaString(String queryParam, String feildName) {
		String ret = "";
		
		if (queryParam != null && !queryParam.contains(",")){
			ret = feildName + " like '%" + queryParam + "%'";
			return ret;
		}
		
		if (queryParam != null) {
			String[] queryParamList = queryParam.split(",");
			String queryString = "'" + queryParamList[0] + "'";
			for (int i = 1; i < queryParamList.length; i++){
				queryString += ",'" + queryParamList[i] + "'";
			}
			ret = feildName + " in (" + queryString + ")";
			return ret;
		}	
		
		return ret;
	}
	
	/**
	 * 发卡行一览
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCardIssuerg(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		String queryParamId = request.getParameter("queryParamId");
		String queryParamName = request.getParameter("queryParamName");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		
		if (StringUtil.isNotEmpty(queryParamId)) {
			//whereSql.append(" and (ins_id_cd like '%").append(queryParamId).append("%'");
			whereSql.append(" and " + getWhereOnCommaString(queryParamId, "ins_id_cd"));
		}
		if (StringUtil.isNotEmpty(queryParamName)) {
			whereSql.append(" and card_dis like '%").append(queryParamName).append("%'");
		}

		String sql = "select (trim(ins_id_cd)) as id, trim(ins_id_cd) || '-' || card_dis as name from tbl_bank_bin_inf";
		sql += whereSql.toString();
		sql += " group by ins_id_cd,card_dis";
		sql += " order by trim(ins_id_cd) asc";
		
		String countSql = "select count(＊) from (" + sql + ")";
		//countSql += whereSql.toString();
		
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 卡BIN一览
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCardBin(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		String param1 = request.getParameter("param1");
		String queryParamId = request.getParameter("queryParamId");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		
		if (StringUtil.isNotEmpty(param1)) {
			whereSql.append(" and trim(ins_id_cd) in(").append(param1).append(")");
		}
		
		if (StringUtil.isNotEmpty(queryParamId)) {
			//whereSql.append(" and bin_sta_no like '%").append(queryParamId).append("%'");
			whereSql.append(" and " + getWhereOnCommaString(queryParamId, "bin_sta_no"));
		}

		String sql = "select distinct(trim(bin_sta_no)) as id, bin_sta_no as name from tbl_bank_bin_inf";
		sql += whereSql.toString();
		sql += " order by trim(bin_sta_no) asc";
		
		String countSql = "select count(distinct(trim(bin_sta_no))) from tbl_bank_bin_inf";
		countSql += whereSql.toString();

		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 地区一览
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAreag(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		String queryParamId = request.getParameter("queryParamId");
		String queryParamName = request.getParameter("queryParamName");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		
		if (StringUtil.isNotEmpty(queryParamId)) {
			//whereSql.append(" and (MCHT_CITY_CODE like '%").append(queryParamId).append("%'");
			whereSql.append(" and " + getWhereOnCommaString(queryParamId, "MCHT_CITY_CODE"));
		}
		
		if (StringUtil.isNotEmpty(queryParamName)) {
			whereSql.append(" and CITY_NAME like '%").append(queryParamName).append("%'");			
		}
		
		String sql = "select MCHT_CITY_CODE as id, MCHT_CITY_CODE||' - '||CITY_NAME as name from CST_CITY_CODE";
		sql += whereSql.toString();
		sql +=  "  order by MCHT_CITY_CODE";
		
		String countSql = "select count(*) from CST_CITY_CODE";
		countSql += whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * MCC一览
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMccg(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		String queryParamId = request.getParameter("queryParamId");
		String queryParamName = request.getParameter("queryParamName");
		StringBuffer whereSql = new StringBuffer(" ");
		
		if (StringUtil.isNotEmpty(queryParamId)) {
			//whereSql.append(" and (mchnt_tp like '%").append(queryParamId).append("%'");
			whereSql.append(" and " + getWhereOnCommaString(queryParamId, "mchnt_tp"));
		}
		if (StringUtil.isNotEmpty(queryParamName)) {
			whereSql.append(" and descr like '%").append(queryParamName).append("%'");		
		}
		String sql = "select mchnt_tp as id, descr as name from tbl_inf_mchnt_tp where rec_st = '1'";
		sql += whereSql.toString();
		sql += "  order by mchnt_tp";
		
		String countSql = "select count(*) from tbl_inf_mchnt_tp where rec_st = '1'";
		countSql += whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 路由管理
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRouteRule(int begin, HttpServletRequest request) {
		String queryRuleId = request.getParameter("queryRuleId");
		String queryPriority = request.getParameter("queryPriority");
		String queryOnFlag = request.getParameter("queryOnFlag");
		String queryCardTp = request.getParameter("queryCardTp");

		String queryBrhId = request.getParameter("queryBrhId");
		String queryAccpId = request.getParameter("queryAccpId");
		String queryRuleCode = request.getParameter("queryRuleCode");
		String queryTxnNum = request.getParameter("queryTxnNum");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryRuleId)) {
			whereSql.append(" and a.rule_id ='").append(queryRuleId)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryPriority)) {
			whereSql.append(" and a.priority ='").append(queryPriority)
					.append("'");
		}

		if (StringUtil.isNotEmpty(queryOnFlag)) {
			whereSql.append(" and a.on_flag ='").append(queryOnFlag)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryCardTp)) {
			whereSql.append(" and a.card_tp ='").append(queryCardTp)
					.append("'");
		}

		if (StringUtil.isNotEmpty(queryBrhId)) {
			whereSql.append(" and a.brh_id ='").append(queryBrhId).append("'");
		}
		if (StringUtil.isNotEmpty(queryAccpId)) {
			whereSql.append(" and a.accp_id ='").append(queryAccpId)
					.append("'");
		}

		if (StringUtil.isNotEmpty(queryRuleCode)) {
			whereSql.append(" and a.rule_code ='").append(queryRuleCode)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryTxnNum)) {
			whereSql.append(" and a.txn_num ='").append(queryTxnNum)
					.append("'");
		}

		String sql = " select distinct a.brh_id||'-'||c.brh_name,a.accp_id||'-'||b.MCHT_NM,a.rule_id,a.priority,"
				+ "a.txn_num||'-'||(select TXN_NAME from TBL_TXN_NAME where txn_num=a.txn_num) as txnName,"
				+ "a.card_tp,"
				+ "trim(a.rule_code)||'-'||trim(d.MSC1),"
				+ "a.card_bin,a.mcc,a.ins_id_cd,a.mcht_area,"
				+ "a.date_ctl,a.date_beg,a.date_end,a.time_ctl,a.time_beg,a.time_end,a.amt_ctl,a.amt_beg,a.amt_end,a.on_flag,a.mcht_group "
				+ " from TBL_ROUTE_RULE a  "
				+ " left join TBL_MCHT_BASE_INF b on trim(a.accp_id)=b.MCHT_NO "
				+ " left join tbl_brh_info c on trim(a.brh_id)=c.brh_id "
				+ " left join TBL_RULE_MCHT_REL d on trim(a.RULE_CODE)=trim(d.RULE_CODE) "
				+ whereSql.toString()
				+ " order by a.on_flag,a.priority desc, a.rule_id desc ";
		String countSql = " select count(*) from TBL_ROUTE_RULE a "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		// for (Object[] objects : dataList) {
		// objects[8]=CommonFunction.moneyFormat(objects[8].toString());
		// }
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 路由管理审核
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRouteRuleCheck(int begin,
			HttpServletRequest request) {
		String queryRuleId = request.getParameter("queryRuleId");
		String queryPriority = request.getParameter("queryPriority");
		String queryOnFlag = request.getParameter("queryOnFlag");
		String queryCardTp = request.getParameter("queryCardTp");

		String queryBrhId = request.getParameter("queryBrhId");
		String queryAccpId = request.getParameter("queryAccpId");
		String queryRuleCode = request.getParameter("queryRuleCode");
		String queryTxnNum = request.getParameter("queryTxnNum");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryRuleId)) {
			whereSql.append(" and a.rule_id ='").append(queryRuleId)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryPriority)) {
			whereSql.append(" and a.priority ='").append(queryPriority)
					.append("'");
		}

		if (StringUtil.isNotEmpty(queryOnFlag)) {
			whereSql.append(" and a.on_flag ='").append(queryOnFlag)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryCardTp)) {
			whereSql.append(" and a.card_tp ='").append(queryCardTp)
					.append("'");
		}

		if (StringUtil.isNotEmpty(queryBrhId)) {
			whereSql.append(" and a.brh_id ='").append(queryBrhId).append("'");
		}
		if (StringUtil.isNotEmpty(queryAccpId)) {
			whereSql.append(" and a.accp_id ='").append(queryAccpId)
					.append("'");
		}

		if (StringUtil.isNotEmpty(queryRuleCode)) {
			whereSql.append(" and a.rule_code ='").append(queryRuleCode)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryTxnNum)) {
			whereSql.append(" and a.txn_num ='").append(queryTxnNum)
					.append("'");
		}

		String sql = " select distinct a.brh_id||'-'||c.brh_name,a.accp_id||'-'||b.MCHT_NM,a.rule_id,a.priority,"
				+ "a.txn_num||'-'||(select TXN_NAME from TBL_TXN_NAME where txn_num=a.txn_num) as txnName,"
				+ "a.card_tp,"
				+ "trim(a.rule_code)||'-'||trim(d.MSC1),"
				+ "a.card_bin,a.mcc,a.ins_id_cd,a.mcht_area,"
				+ "a.date_ctl,a.date_beg,a.date_end,a.time_ctl,a.time_beg,a.time_end,a.amt_ctl,a.amt_beg,a.amt_end,a.on_flag,a.mcht_group "
				+ " from TBL_ROUTE_RULE a  "
				+ " left join TBL_MCHT_BASE_INF b on trim(a.accp_id)=b.MCHT_NO "
				+ " left join tbl_brh_info c on trim(a.brh_id)=c.brh_id "
				+ " left join TBL_RULE_MCHT_REL d on trim(a.RULE_CODE)=trim(d.RULE_CODE) "
				+ whereSql.toString()
				+ " order by a.on_flag, a.priority desc, a.rule_id desc";
		String countSql = " select count(*) from TBL_ROUTE_RULE a "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		// for (Object[] objects : dataList) {
		// objects[8]=CommonFunction.moneyFormat(objects[8].toString());
		// }
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 主规则商户映射查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRuleMchtMap(int begin, HttpServletRequest request) {
		String queryRuleCode = request.getParameter("queryRuleCode");
		String querySrvId = request.getParameter("querySrvId");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryRuleCode)) {
			whereSql.append(" and a.rule_code like '%").append(queryRuleCode)
					.append("%' ");
		}

		if (StringUtil.isNotEmpty(querySrvId)) {
			whereSql.append(" and a.srv_id ='").append(querySrvId).append("' ");
		}

		String sql = " select distinct a.rule_code,a.srv_id,a.msc1,a.srv_id||'-'||b.FIRST_BRH_NAME as srv_id_name "
				+ " from TBL_RULE_MCHT_REL a left join TBL_FIRST_BRH_DEST_ID b on b.DEST_ID=a.srv_id  "
				+ whereSql.toString() + " order by a.rule_code ";
		String countSql = " select count(*) from TBL_RULE_MCHT_REL a "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 路由商户规则映射
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRuleDtlMchtMap(int begin,
			HttpServletRequest request) {
		String queryRuleCode = request.getParameter("queryRuleCode");
		String queryFirstMchtCd = request.getParameter("queryFirstMchtCd");
		String querySrvId = request.getParameter("querySrvId");
		String queryMsc2 = request.getParameter("queryMsc2");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryRuleCode)) {
			whereSql.append(" and a.rule_code like '%").append(queryRuleCode)
					.append("%' ");
		}
		if (StringUtil.isNotEmpty(queryFirstMchtCd)) {
			whereSql.append(" and a.first_mcht_cd ='").append(queryFirstMchtCd)
					.append("' ");
		}

		if (StringUtil.isNotEmpty(querySrvId)) {
			whereSql.append(" and a.srv_id ='").append(querySrvId).append("' ");
		}

		if (StringUtil.isNotEmpty(queryMsc2)) {
			whereSql.append(" and a.msc2 ='").append(queryMsc2).append("' ");
		}

		String sql = " select a.rule_code,trim(a.first_mcht_cd)||'-'||trim(b.FIRST_MCHT_NM),trim(a.srv_id)||'-'||trim(c.srv_dsp),a.rel_desc,a.msc1,a.msc2,a.msc3 "
				+ " from TBL_RULE_MCHT_REL a  "
				+ " left join TBL_FIRST_MCHT_INF b on trim(a.first_mcht_cd)=trim(b.FIRST_MCHT_CD) "
				+ " left join TBL_SRV_INF c on trim(a.srv_id)=trim(c.srv_id) "
				+ whereSql.toString() + " order by a.rule_code ";
		String countSql = " select count(*) from TBL_RULE_MCHT_REL a "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		// for (Object[] objects : dataList) {
		// objects[8]=CommonFunction.moneyFormat(objects[8].toString());
		// }
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 路由商户规则映射控制
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRuleMchtMapContl(int begin,
			HttpServletRequest request) {
		String queryFirstMchtCd = request.getParameter("queryFirstMchtCd");
		String queryMsc2 = request.getParameter("queryMsc2");
		String queryRuleCode = request.getParameter("queryRuleCode");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryFirstMchtCd)) {
			whereSql.append(" and a.first_mcht_cd ='").append(queryFirstMchtCd)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryRuleCode)) {
			whereSql.append(" and a.rule_code = '").append(queryRuleCode)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryMsc2)) {
			whereSql.append(" and a.msc2 ='").append(queryMsc2).append("' ");
		}

		String sql = " select trim(a.first_mcht_cd)||'-'||trim(b.FIRST_MCHT_NM),"
				+ "a.msc2,a.first_mcht_cd "
				+ " from TBL_RULE_MCHT_REL a  "
				+ " left join TBL_FIRST_MCHT_INF b on trim(a.first_mcht_cd)=trim(b.FIRST_MCHT_CD) "
				+ whereSql.toString() + " order by msc2,a.first_mcht_cd ";
		String countSql = " select count(*) from TBL_RULE_MCHT_REL a "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 当日交易查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getTxnTodayInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		
		//获取用户信息
		Operator operator = (Operator)request.getSession().getAttribute(Constants.OPERATOR_INFO);
		//upd by yww 160414 实现禅道316任务--交易查询卡号隐藏--start
		String userSql = " SELECT OPR_ID, CRT_OPR_ID, CRT_TIME FROM TBL_ALL_CARD_NO_USER where opr_id= '"+operator.getOprId()+"' ";
		//查询用户ID在权限表是否存在，存在则可以查看全卡号，否则查看短卡号
		List<Object[]> userList = CommonFunction.getCommQueryDAO().findBySQLQuery(userSql);
		String panSql = "";
		if (userList.size()>0) {
			panSql = " f.pan, ";
		}else {
			panSql = " rpad(substr(f.pan,1,6),length(trim(f.pan))-4,'*')||substr(trim(f.pan),length(trim(f.pan))-3,4) pan, ";
		}
		//upd by yww 160414 实现禅道316任务--交易查询卡号隐藏--end
		
		String queryCardAccpt = request.getParameter("queryCardAccpt");
		String queryBrh = request.getParameter("queryBrh");
		String queryCardAccpTerm = request.getParameter("queryCardAccpTerm");
		String queryPan = request.getParameter("queryPan");
		//String queryTermSsn = request.getParameter("queryTermSsn");
		//String querySysSeqNum = request.getParameter("querySysSeqNum");
		String queryAmtTransLow = request.getParameter("queryAmtTransLow");//金额下限
		String queryAmtTransUp = request.getParameter("queryAmtTransUp");//金额上限
		String respName = request.getParameter("respName");//交易应答
		String acctType = request.getParameter("acctType");//账户类型
		String termType = request.getParameter("termType");//终端类型
		String queryTxnName = request.getParameter("queryTxnName");
		//String queryRespCode = request.getParameter("queryRespCode");
		//String querySettleBrh = request.getParameter("querySettleBrh");
		//String queryRevsalSsn = request.getParameter("queryRevsalSsn");
		String queryRetrivlRef = request.getParameter("queryRetrivlRef");
		//String queryCancelSsn = request.getParameter("queryCancelSsn");
		String queryBankName = request.getParameter("queryBankName");
		String queryCardOrg = request.getParameter("queryCardOrg");
		String queryMcntGroup = request.getParameter("queryMcntGroup");
		String queryCardAccpTermName = request.getParameter("queryCardAccpTermName");
		String queryAuthNo = request.getParameter("queryAuthNo");
		//String queryCardSettleNo = request.getParameter("queryCardSettleNo");
		String queryMchtUp = request.getParameter("queryMchtUp");
		
		/*
		 * by jee on 2016-1-19
		 * 增加交易状态、冲正状态、撤销状态这三个查询条件
		 */
		String queryTransState = request.getParameter("queryTransState");
		String queryRevsalFlag = request.getParameter("queryRevsalFlag");
		String queryCancelFlag = request.getParameter("queryCancelFlag");
		/**
		 * by jee on 2016-2-1
		 * 增加结算类型查询条件
		 */
		String querySettleType = request.getParameter("querySettleType");
		String querySysSeqNum = request.getParameter("querySysSeqNum");
		String queryTermSsn = request.getParameter("queryTermSsn");
		StringBuffer whereSql = new StringBuffer(" where 1=1 "
						// "f.card_accp_id in (select mcht_no from tbl_mcht_base_inf a where a.bank_no in "
						// + operator.getBrhBelowId() + ")"+
				//20160415 郭宇 修改 交易类型1181外全显示
//						+ " and a.txn_num in ('1011','1091','1101','1111','1121','1141','1161',"
//						+ "'2011','2091','2101','2111','2121','3011','3091','3101',"
//						+ "'3111','3121','4011','4091','4101','4111','4121','5151','5161') ");
				+ " and a.txn_num not in ('1181') ");

		whereSql.append(" and A.inst_date >=").append(CommonFunction.getCurrentDate()).append("000000 ");
		whereSql.append(" and A.inst_date <=").append(CommonFunction.getCurrentDate()).append("999999 ");
		if (isNotEmpty(queryBrh)) {
			// brhId = queryBrhId.trim();
			whereSql.append(" and a.card_accp_id in (select mcht_no from tbl_mcht_base_inf b where b.bank_no in(select brh_id  from TBL_BRH_INFO where brh_id='"
					+ queryBrh.trim()
					+ "' or UP_BRH_ID='"
					+ queryBrh.trim() + "' ))");
			// whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
			// + queryBrhId.trim() +
			// "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
		}
		if (isNotEmpty(queryCardAccpt)) {
			whereSql.append(" AND a.card_accp_id").append(" = '").append(queryCardAccpt).append("'");
		}
		if (isNotEmpty(queryCardAccpTerm)) {
			whereSql.append(" AND a.card_accp_term_id").append(" like ").append("'%").append(queryCardAccpTerm).append("%'");
		}
		/* ①可根据卡bin查询，即卡号前六位，如62262，其全卡号为6226230192972527；							
		②可根据短卡号查询，即卡号的前六位+后四位，如：6226232527，其全卡号为6226230192972527；								
		③可根据全卡号查询，即卡号6226230192972527；*/							
		if (isNotEmpty(queryPan)) {
			if(queryPan.trim().length() == 6){
				whereSql.append(" AND instr(trim(a.pan),").append(queryPan).append(",1)=1");
			}else if(queryPan.trim().length() == 10){
				whereSql.append(" AND instr(trim(a.pan),'").append(queryPan.substring(0, 6)).append("',1)=1").append(" AND instr(trim(a.pan),'").append(queryPan.substring(6, 10)).append("',-1)=(length(trim(a.pan))-4 +1)");
			}else if(queryPan.trim().length() > 10){
				whereSql.append(" AND trim(a.pan)").append(" ='").append(queryPan).append("'");
			}else{
				whereSql.append(" AND 1=3");
			}
		}
		//-----------
/*		if (isNotEmpty(queryTermSsn)) {
			whereSql.append(" AND f.term_ssn").append(" like ").append("'%")
					.append(queryTermSsn).append("%'");
		}
		if (isNotEmpty(querySysSeqNum)) {
			whereSql.append(" AND f.sys_seq_num").append(" like ").append("'%")
					.append(querySysSeqNum).append("%'");
		}*/
		if (isNotEmpty(queryAmtTransLow)) {
			whereSql.append(" AND a.amt ").append(" >= ").append(Float.parseFloat(queryAmtTransLow.trim()));
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSql.append(" AND a.amt ").append(" <= ").append(Float.parseFloat(queryAmtTransUp.trim()));
		}
		
		if (isNotEmpty(respName)) {
			whereSql.append(" AND a.RESP_CODE").append(" = '").append(respName).append("'");
		}
		if (isNotEmpty(acctType)) {
			whereSql.append(" AND a.ACCT_TYPE").append(" = '").append(acctType).append("'");
		}
		if (isNotEmpty(termType)) {
			whereSql.append(" AND a.TERM_TP").append(" = '").append(termType).append("'");
		}
		
		if (isNotEmpty(queryTxnName)) {
			whereSql.append(" AND a.txn_num").append(" = '").append(queryTxnName).append("'");
		}
		//20160504 guoyu upd 成功，请求中，交易确认，已退货以外都算失败；选择成功，可检索出交易确认
		if (isNotEmpty(queryTransState)) {
			//失败
			if (Constants.TransState.equals(queryTransState)) {
//				whereSql.append(" AND a.trans_state  !='1'  and a.trans_state  !='0'  and a.trans_state  !='R' ");
				whereSql.append(" AND a.trans_state !='1' and a.trans_state !='0' and a.trans_state !='R' and a.trans_state !='9'");
			//成功
			} else if (Constants.TransState_Success.equals(queryTransState)) {
				whereSql.append(" AND a.trans_state in ('1','9') ");
			} else {
				whereSql.append(" AND a.trans_state").append(" = ").append("'").append(queryTransState).append("'");
			}
		}
		/*
		if (isNotEmpty(queryRespCode)) {
			whereSql.append(" AND f.RESP_CODE").append(" = ").append("'")
					.append(queryRespCode).append("' ");
		}

		if (isNotEmpty(querySettleBrh)) {
			whereSql.append(" AND f.tdestid").append(" = ").append("'")
					.append(querySettleBrh).append("' ");
		}
		if (isNotEmpty(queryRevsalSsn)) {
			whereSql.append(" AND f.revsal_ssn").append(" like ").append("'%")
					.append(queryRevsalSsn).append("%'");
		}*/
		if (isNotEmpty(queryRetrivlRef)) {
			whereSql.append(" AND a.retrivl_ref").append(" like ").append("'%").append(queryRetrivlRef).append("%'");
		}
     
		if (isNotEmpty(queryRevsalFlag)) {
			whereSql.append(" AND a.revsal_flag").append(" = ").append("'").append(queryRevsalFlag).append("' ");
		}
		/*
		 * if(isNotEmpty(queryCancelSsn)) {
		 * whereSql.append(" AND f.cancel_ssn").
		 * append(" like ").append("'%").append(queryCancelSsn).append("%'"); }
		 */
		if (isNotEmpty(queryCancelFlag)) {
			whereSql.append(" AND a.cancel_flag").append(" = ").append("'").append(queryCancelFlag).append("' ");
		}
		if (isNotEmpty(queryBankName)) {
			//upd by yww 160414：当发卡行选择为总行时，查询出所有该发卡行的交易。
			if (queryBankName.endsWith("0000")) {
				whereSql.append(" AND a.bank_no").append(" like ").append("'").append(queryBankName.substring(0, 4)).append("%'");
			}else{
				whereSql.append(" AND a.bank_no").append(" = ").append("'").append(queryBankName).append("' ");
			}
		}

		if (isNotEmpty(queryCardOrg)) {  //卡组织
			whereSql.append(" AND a.card_org = '").append(queryCardOrg.trim()).append("'");
		}
		if (isNotEmpty(queryMcntGroup)) { //商户种类
			whereSql.append(" AND a.mcht_group = '").append(queryMcntGroup).append("'");
		}
		if (isNotEmpty(queryCardAccpTermName)) {
			whereSql.append(" AND a.term_name").append(" like ").append("'%").append(queryCardAccpTermName).append("%'");
		}
		if (isNotEmpty(queryAuthNo)) {
			whereSql.append(" AND a.authr_id_resp").append(" like ").append("'%").append(queryAuthNo).append("%' ");
		}
		/*if (isNotEmpty(queryCardSettleNo)) {
			whereSql.append(" AND f.revsal_flag").append(" = ").append("'").append(queryRevsalFlag).append("' ");
		}*/	
		if (isNotEmpty(queryMchtUp)) {
			whereSql.append(" AND a.mcht_brh_id").append(" = ").append("'").append(queryMchtUp).append("' ");
		}

		if (isNotEmpty(querySettleType)) {
			whereSql.append(" AND a.settle_type").append(" = ").append("'").append(querySettleType).append("' ");
		}
		if (isNotEmpty(queryTermSsn)) {
			whereSql.append(" AND a.term_ssn").append(" = ").append("'").append(queryTermSsn).append("' ");
		}
		if (isNotEmpty(querySysSeqNum)) {
			whereSql.append(" AND a.sys_seq_num").append(" = ").append("'").append(querySysSeqNum).append("' ");
		}
		String sql = "SELECT txn_date,txn_time,pan,card_accp_id,mcht_name,card_accp_term_id,term_ssn,sys_seq_num,brh_id_name,retrivl_ref,amt,txn_num_name,trans_state,resp_name,tdestid,"
				+ " settle_brh_name,revsal_ssn,revsal_flag,cancel_ssn,cancel_flag,trans_chl_name,pos_entry_mode,msg_src_retrivl_ref ,mcht_group,card_type,card_org,bank_name, mcht_brh_id,mcht_brh_name,term_brh_id,brh_id3,term_name,track_3_data,settle_type,RESP_CODE,TERM_TP,ACCT_TYPE "
				+ " from ( select inst_date,substr(f.inst_date,1,8) as txn_date,"
				+ "	substr(f.inst_date,9,6) as txn_time,"
				+ panSql
				+ "	f.card_accp_id,f.authr_id_resp,"
				+ "	(select MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id) as mcht_name,"
				+ "	f.card_accp_term_id,"
				+ "	f.term_ssn,"
				+ "	f.sys_seq_num,"
				+ "	(SELECT c.CREATE_NEW_NO||' - '||c.brh_name FROM TBL_BRH_INFO c WHERE c.brh_id in (select bank_no from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id))AS brh_id_name,"
				+ "	f.retrivl_ref,"
				+ "	(case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ) as amt, "
				+ "	(select TXN_NAME from TBL_TXN_NAME where TXN_NUM =f.txn_num ) as txn_num_name, f.txn_num,"
				+ "	f.trans_state,"
				+ "	f.RESP_CODE||(select '-'||trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = f.RESP_CODE and SRC_ID='2801' ) as resp_name,"
				+ " f.tdestid,"
				+ "	f.tdestid||(select '-'||FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where DEST_ID = f.tdestid) as settle_brh_name,"
				+ "	f.revsal_ssn,f.revsal_flag,f.cancel_ssn,f.cancel_flag,"
				+ " f.msg_src_id||'-'||(select FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where dest_id = f.msg_src_id) trans_chl_name,"
				+ " f.pos_entry_mode,f.up_f37 msg_src_retrivl_ref,"
				+ " decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group,"
			    + " substr(f.misc_2,104,2) card_type, "
			    + " (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '0' else '1' end) card_org, "
			    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no,"
			    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then "
			    + "         (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) ) "
			    + "         else "
			    + "           '*' "
			    + " end) bank_name, "
			    + " m.mcht_id_up mcht_brh_id,m.mcht_name_up mcht_brh_name,m.term_id_up term_brh_id,m.brh_id3, "
			    + " (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name, "
			    + " f.track_3_data, "//完成禅道任务207	--yww 20160113
			    + " (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type, "
				+ " f.RESP_CODE,(select term.TERM_TP from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP,"
				+ " (select trim(substr(b.SETTLE_ACCT,1,1)) from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
				+ " from tbl_n_txn f left join gtwyadm.tbl_upbrh_mcht m on f.up_mcht_id = m.mcht_id_up and f.up_term_id = m.term_id_up and f.up_brh_id3 = m.brh_id3"
				+ " ) a";


		sql = sql + whereSql.toString();
		sql = sql + " order by a.inst_date desc";
		String countSql = "SELECT count(*) "
				+ " from ( select inst_date,substr(f.inst_date,1,8) as txn_date,"
				+ "	substr(f.inst_date,9,6) as txn_time,"
				+ panSql
				+ "	f.card_accp_id,f.authr_id_resp,"
				+ "	(select MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id) as mcht_name,"
				+ "	f.card_accp_term_id,"
				+ "	f.term_ssn,"
				+ "	f.sys_seq_num,"
				+ "	(SELECT c.CREATE_NEW_NO||' - '||c.brh_name FROM TBL_BRH_INFO c WHERE c.brh_id in (select bank_no from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id))AS brh_id_name,"
				+ "	f.retrivl_ref,"
				+ "	(case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ) as amt,  "
				+ "	(select TXN_NAME from TBL_TXN_NAME where TXN_NUM =f.txn_num ) as txn_num_name, f.txn_num,"
				+ "	f.trans_state,"
				+ "	f.RESP_CODE||(select '-'||trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = f.RESP_CODE and SRC_ID='2801' ) as resp_name,"
				+ " f.tdestid,"
				+ "	f.tdestid||(select '-'||FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where DEST_ID = f.tdestid) as settle_brh_name,"
				+ "	f.revsal_ssn,f.revsal_flag,f.cancel_ssn,f.cancel_flag,"
				+ " f.msg_src_id||'-'||(select FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where dest_id = f.msg_src_id) trans_chl_name,"
				+ " f.pos_entry_mode,f.up_f37 msg_src_retrivl_ref,"
				+ " decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group,"
			    + " substr(f.misc_2,104,2) card_type, "
			    + " (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '0' else '1' end) card_org, "
			    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no,"
			    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then "
			    + " (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) ) "
			    + " else '*' end) bank_name, "
			    + " m.mcht_id_up mcht_brh_id,m.mcht_name_up mcht_brh_name,m.term_id_up term_brh_id,m.brh_id3, "
			    + " (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name, "
			    + " f.track_3_data, "
			    + " (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type, "
				+ " f.RESP_CODE,(select term.TERM_TP from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP,"
				+ " (select trim(substr(b.SETTLE_ACCT,1,1)) from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
				+ " from tbl_n_txn f left join gtwyadm.tbl_upbrh_mcht m on f.up_mcht_id = m.mcht_id_up and f.up_term_id = m.term_id_up and f.up_brh_id3 = m.brh_id3"
				+ " ) a";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);


		if (dataList.size() > 0) {
			String amtSql = "select "
					+ " to_char("
					+ "nvl(sum( a.amt ),0) "
					+ ",'99,999,999,990.99') as amt_total "
					+ " from (select inst_date,substr(f.inst_date,1,8) as txn_date,"
					+ "	substr(f.inst_date,9,6) as txn_time,"
					+ panSql
					+ "	f.card_accp_id,f.authr_id_resp,"
					+ "	(select MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id) as mcht_name,"
					+ "	f.card_accp_term_id,"
					+ "	f.term_ssn,"
					+ "	f.sys_seq_num,"
					+ "	(SELECT c.CREATE_NEW_NO||' - '||c.brh_name FROM TBL_BRH_INFO c WHERE c.brh_id in (select bank_no from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id))AS brh_id_name,"
					+ "	f.retrivl_ref,"
					+ "	(case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ) as amt, "
					+ "	(select TXN_NAME from TBL_TXN_NAME where TXN_NUM =f.txn_num ) as txn_num_name, f.txn_num,"
					+ "	f.trans_state,"
					+ "	f.RESP_CODE||(select '-'||trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = f.RESP_CODE and SRC_ID='2801' ) as resp_name,"
					+ " f.tdestid,"
					+ "	f.tdestid||(select '-'||FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where DEST_ID = f.tdestid) as settle_brh_name,"
					+ "	f.revsal_ssn,f.revsal_flag,f.cancel_ssn,f.cancel_flag,"
					+ " f.msg_src_id||'-'||(select FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where dest_id = f.msg_src_id) trans_chl_name,"
					+ " f.pos_entry_mode,f.up_f37 msg_src_retrivl_ref,"
					+ " decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group,"
				    + " substr(f.misc_2,104,2) card_type, "
				    + " (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '0' else '1' end) card_org, "
				    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no,"
				    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then "
				    + " (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) ) "
				    + " else '*' end) bank_name, "
				    + " (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name,"
				    + " m.mcht_id_up mcht_brh_id,m.mcht_name_up mcht_brh_name,m.term_id_up term_brh_id,m.brh_id3, "
				    + "f.track_3_data, "
				    + " (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type, "
				    + " f.RESP_CODE,(select term.TERM_TP from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP,"
					+ " (select trim(substr(b.SETTLE_ACCT,1,1)) from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
					+ " from tbl_n_txn f left join gtwyadm.tbl_upbrh_mcht m on f.up_mcht_id = m.mcht_id_up and f.up_term_id = m.term_id_up and f.up_brh_id3 = m.brh_id3"
					+ " ) a"
					+ whereSql.toString()
					+ " and a.txn_num in ('1101','1091','5151') and a.trans_state in ( '1','R') and a.REVSAL_FLAG!='1' and a.CANCEL_FLAG!='1' ";
			String amt = CommonFunction.getCommQueryDAO().findCountBySQLQuery(amtSql);

			Object[] obj = new Object[dataList.get(0).length];
			obj[0] = "<font color='red'>总计</font>";
			obj[1] = "<font color='red'>交易总笔数:</font>";
			obj[2] = "<font color='red'>" + count + "笔</font>";
			obj[3] = "";
			obj[4] = "";
			obj[5] = "<font color='red'>交易总金额:</font>";
			obj[6] = "<font color='red'>" + amt + "</font>";

			dataList.add(obj);
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 历史交易查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getTxnHisInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		//获取用户信息
		Operator operator = (Operator)request.getSession().getAttribute(Constants.OPERATOR_INFO);
		//upd by yww 160414 实现禅道316任务--交易查询卡号隐藏--start
		String userSql = " SELECT OPR_ID, CRT_OPR_ID, CRT_TIME FROM TBL_ALL_CARD_NO_USER where opr_id= '"+operator.getOprId()+"' ";
		//查询用户ID在权限表是否存在，存在则可以查看全卡号，否则查看短卡号
		List<Object[]> userList = CommonFunction.getCommQueryDAO().findBySQLQuery(userSql);
		String panSql = "";
		if (userList.size()>0) {
			panSql = " f.pan, ";
		}else {
			panSql = " rpad(substr(f.pan,1,6),length(trim(f.pan))-4,'*')||substr(trim(f.pan),length(trim(f.pan))-3,4) pan, ";
		}
		//upd by yww 160414 实现禅道316任务--交易查询卡号隐藏--end
		
		String queryCardAccpt = request.getParameter("queryCardAccpt");
		String queryBrh = request.getParameter("queryBrh");
		String queryCardAccpTermId = request.getParameter("queryCardAccpTerm");
		String queryPan = request.getParameter("queryPan");
		String queryTxnName = request.getParameter("queryTxnName");
		String queryRetrivlRef = request.getParameter("queryRetrivlRef");
		String queryBankName = request.getParameter("queryBankName");
		String queryCardOrg = request.getParameter("queryCardOrg");
		String queryMcntGroup = request.getParameter("queryMcntGroup");
		String queryCardAccpTermName = request.getParameter("queryCardAccpTermName");
		String queryAuthNo = request.getParameter("queryAuthNo");
		//String queryCardSettleNo = request.getParameter("queryCardSettleNo");
		String queryMchtUp = request.getParameter("queryMchtUp");
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryAmtTransLow = request.getParameter("queryAmtTransLow");//金额下限
		String queryAmtTransUp = request.getParameter("queryAmtTransUp");//金额上限
		String respName = request.getParameter("respName");//交易应答
		String acctType = request.getParameter("acctType");//账户类型
		String termType = request.getParameter("termType");//终端类型
		/*
		 * by jee on 2016-1-19
		 * 增加交易状态、冲正状态、撤销状态这三个查询条件
		 */
		String queryTransState = request.getParameter("queryTransState");
		String queryRevsalFlag = request.getParameter("queryRevsalFlag");
		String queryCancelFlag = request.getParameter("queryCancelFlag");
		/**
		 * by jee on 2016-2-1
		 * 增加结算类型查询条件
		 */
		String querySettleType = request.getParameter("querySettleType");
		String querySysSeqNum = request.getParameter("querySysSeqNum");
		String queryTermSsn = request.getParameter("queryTermSsn");

		StringBuffer whereSql = new StringBuffer(" where 1=1 " );
		//20160415 郭宇 修改 交易类型1181外全显示
//		whereSql.append(" and a.txn_num in ('1011','1091','1101','1111','1121','1141','1161', '2011','2091','2101','2111','2121','3011','3091','3101', '3111','3121','4011','4091','4101','4111','4121','5151','5161')" );
		whereSql.append(" and a.txn_num not in ('1181')" );

		if (isNotEmpty(queryBrh)) {
			whereSql.append(" and a.card_accp_id in (select mcht_no from tbl_mcht_base_inf n where n.bank_no in(select brh_id from TBL_BRH_INFO where brh_id='"
					+ queryBrh.trim()
					+ "' or UP_BRH_ID='"
					+ queryBrh.trim() + "' ))");

		}
		if (isNotEmpty(queryCardAccpt)) {
			whereSql.append(" AND a.card_accp_id").append(" = ").append("'").append(queryCardAccpt).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND a.card_accp_term_id").append(" like ")
					.append("'%").append(queryCardAccpTermId).append("%'");
		}
		/* ①可根据卡bin查询，即卡号前六位，如62262，其全卡号为6226230192972527；							
		②可根据短卡号查询，即卡号的前六位+后四位，如：6226232527，其全卡号为6226230192972527；								
		③可根据全卡号查询，即卡号6226230192972527；*/							
		if (isNotEmpty(queryPan)) {
			if(queryPan.trim().length() == 6){
				whereSql.append(" AND instr(trim(a.pan),").append(queryPan).append(",1)=1");
			}else if(queryPan.trim().length() == 10){
				whereSql.append(" AND instr(trim(a.pan),'").append(queryPan.substring(0, 6)).append("',1)=1").append(" AND instr(trim(a.pan),'").append(queryPan.substring(6, 10)).append("',-1)=(length(trim(a.pan))-4 +1)");
			}else if(queryPan.trim().length() > 10){
				whereSql.append(" AND trim(a.pan)").append(" ='").append(queryPan).append("'");
			}else{
				whereSql.append(" AND 1=3");
			}
		}

		
		if (isNotEmpty(queryAmtTransLow)) {
			whereSql.append(" AND a.amt ").append(" >= ").append(Float.parseFloat(queryAmtTransLow.trim()));
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSql.append(" AND a.amt ").append(" <= ").append(Float.parseFloat(queryAmtTransUp.trim()));
		}
		if (isNotEmpty(respName)) {
			whereSql.append(" AND a.RESP_CODE").append(" = '").append(respName).append("'");
		}
		if (isNotEmpty(acctType)) {
			whereSql.append(" AND a.ACCT_TYPE").append(" = '").append(acctType).append("'");
		}
		if (isNotEmpty(termType)) {
			whereSql.append(" AND a.TERM_TP").append(" = '").append(termType).append("'");
		}
		
		
		if (isNotEmpty(queryTxnName)) {
			whereSql.append(" AND a.txn_num").append(" = ").append("'")
					.append(queryTxnName).append("'");
		}
		//20160504 guoyu upd 成功，请求中，交易确认，已退货以外都算失败；选择成功，可检索出交易确认
		if (isNotEmpty(queryTransState)) {
			//失败
			if (Constants.TransState.equals(queryTransState)) {
//				whereSql.append(" AND a.trans_state  !='1'  and a.trans_state  !='0'  and a.trans_state  !='R' ");
				whereSql.append(" AND a.trans_state !='1' and a.trans_state !='0' and a.trans_state !='R' and a.trans_state !='9'");
			//成功
			} else if (Constants.TransState_Success.equals(queryTransState)) {
				whereSql.append(" AND a.trans_state in ('1','9') ");
			} else {
				whereSql.append(" AND a.trans_state").append(" = ").append("'").append(queryTransState).append("'");
			}
		}
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.inst_date").append(" >= ")
					.append(startDate).append("000000 ");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.inst_date").append(" <= ").append(endDate)
					.append("999999 ");
		}

		if (isNotEmpty(queryRetrivlRef)) {
			whereSql.append(" AND a.retrivl_ref").append(" like ").append("'%")
					.append(queryRetrivlRef).append("%'");
		}
		if (isNotEmpty(queryBankName)) {
			//upd by yww 160414：当发卡行选择为总行时，查询出所有该发卡行的交易。
			if (queryBankName.endsWith("0000")) {
				whereSql.append(" AND a.bank_no").append(" like ").append("'").append(queryBankName.substring(0, 4)).append("%'");
			}else{
				whereSql.append(" AND a.bank_no").append(" = ").append("'").append(queryBankName).append("' ");
			}
		}

		if (isNotEmpty(queryCardOrg)) {  //卡组织
			whereSql.append(" AND a.card_org = '").append(queryCardOrg.trim()).append("'");
		}
		if (isNotEmpty(queryMcntGroup)) { //商户种类
			whereSql.append(" AND a.mcht_group = '").append(queryMcntGroup).append("'");
		}
		if (isNotEmpty(queryCardAccpTermName)) {
			whereSql.append(" AND a.term_name").append(" like ").append("'%").append(queryCardAccpTermName).append("%'");
		}
		if (isNotEmpty(queryAuthNo)) {
			whereSql.append(" AND a.authr_id_resp").append(" like ").append("'%").append(queryAuthNo).append("%' ");
		}
/*		if (isNotEmpty(queryCardSettleNo)) {
			whereSql.append(" AND f.revsal_flag").append(" = ").append("'").append(queryRevsalFlag).append("' ");
		}*/	
		if (isNotEmpty(queryMchtUp)) {
			whereSql.append(" AND a.mcht_brh_id").append(" = ").append("'").append(queryMchtUp).append("' ");
		}
		
		if (isNotEmpty(queryRevsalFlag)) {
			whereSql.append(" AND a.revsal_flag").append(" = ").append("'").append(queryRevsalFlag).append("' ");
		}
		if (isNotEmpty(queryCancelFlag)) {
			whereSql.append(" AND a.cancel_flag").append(" = ").append("'").append(queryCancelFlag).append("' ");
		}

		if (isNotEmpty(querySettleType)) {
			whereSql.append(" AND a.settle_type").append(" = ").append("'").append(querySettleType).append("' ");
		}
		if (isNotEmpty(queryTermSsn)) {
			whereSql.append(" AND a.term_ssn").append(" = ").append("'").append(queryTermSsn).append("' ");
		}
		if (isNotEmpty(querySysSeqNum)) {
			whereSql.append(" AND a.sys_seq_num").append(" = ").append("'").append(querySysSeqNum).append("' ");
		}
		
		String sql = "SELECT txn_date,txn_time,pan,card_accp_id,mcht_name,card_accp_term_id,term_ssn,sys_seq_num,brh_id_name,retrivl_ref,amt,txn_num_name,trans_state,resp_name,tdestid,"
				+ " settle_brh_name,revsal_ssn,revsal_flag,cancel_ssn,cancel_flag,trans_chl_name,pos_entry_mode,msg_src_retrivl_ref ,mcht_group,card_type,card_org,bank_name, mcht_brh_id,mcht_brh_name,term_brh_id,brh_id3,term_name,track_3_data,settle_type,RESP_CODE,TERM_TP,ACCT_TYPE "
				+ " from ( select inst_date,substr(f.inst_date,1,8) as txn_date,"
				+ "	substr(f.inst_date,9,6) as txn_time,"
				+ panSql
				+ "	f.card_accp_id,f.authr_id_resp,"
				+ "	(select MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id) as mcht_name,"
				+ "	f.card_accp_term_id,"
				+ "	f.term_ssn,"
				+ "	f.sys_seq_num,"
				+ "	(SELECT c.CREATE_NEW_NO||' - '||c.brh_name FROM TBL_BRH_INFO c WHERE c.brh_id in (select bank_no from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id))AS brh_id_name,"
				+ "	f.retrivl_ref,"
				+ "	(case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ) as amt, "
				+ "	(select TXN_NAME from TBL_TXN_NAME where TXN_NUM =f.txn_num ) as txn_num_name, f.txn_num,"
				+ "	f.trans_state,"
				+ "	f.RESP_CODE||(select '-'||trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = f.RESP_CODE and SRC_ID='2801' ) as resp_name,"
				+ " f.tdestid,"
				+ "	f.tdestid||(select '-'||FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where DEST_ID = f.tdestid) as settle_brh_name,"
				+ "	f.revsal_ssn,f.revsal_flag,f.cancel_ssn,f.cancel_flag,"
				+ " f.msg_src_id||'-'||(select FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where dest_id = f.msg_src_id) trans_chl_name,"
				+ " f.pos_entry_mode,f.up_f37 msg_src_retrivl_ref,"
				+ " decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group,"
			    + " substr(f.misc_2,104,2) card_type, "
			    + " (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '0' else '1' end) card_org, "
			    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no,"
			    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then "
			    + "         (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) ) "
			    + "         else "
			    + "           '*' "
			    + " end) bank_name, "
			    + " (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name,"
			    + " m.mcht_id_up mcht_brh_id,m.mcht_name_up mcht_brh_name,m.term_id_up term_brh_id,m.brh_id3, "
			    + "f.track_3_data, "//完成禅道任务207	--yww 20160113
			    + " (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type, "
			    + " f.RESP_CODE,(select term.TERM_TP from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP,"
				+ " (select trim(substr(b.SETTLE_ACCT,1,1)) from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
				+ " from tbl_n_txn_his f left join gtwyadm.tbl_upbrh_mcht m on f.up_mcht_id = m.mcht_id_up and f.up_term_id = m.term_id_up and f.up_brh_id3 = m.brh_id3"
				+ " ) a";

		sql = sql + whereSql.toString();
		sql = sql + " order by a.inst_date desc";
		String countSql = "SELECT count(*) "
				+ " from ( select inst_date,substr(f.inst_date,1,8) as txn_date,"
				+ "	substr(f.inst_date,9,6) as txn_time,"
				+ panSql
				+ "	f.card_accp_id,f.authr_id_resp,"
				+ "	(select MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id) as mcht_name,"
				+ "	f.card_accp_term_id,"
				+ "	f.term_ssn,"
				+ "	f.sys_seq_num,"
				+ "	(SELECT c.CREATE_NEW_NO||' - '||c.brh_name FROM TBL_BRH_INFO c WHERE c.brh_id in (select bank_no from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id))AS brh_id_name,"
				+ "	f.retrivl_ref,"
				+ "	(case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ) as amt,"
				+ "	(select TXN_NAME from TBL_TXN_NAME where TXN_NUM =f.txn_num ) as txn_num_name, f.txn_num,"
				+ "	f.trans_state,"
				+ "	f.RESP_CODE||(select '-'||trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = f.RESP_CODE and SRC_ID='2801' ) as resp_name,"
				+ " f.tdestid,"
				+ "	f.tdestid||(select '-'||FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where DEST_ID = f.tdestid) as settle_brh_name,"
				+ "	f.revsal_ssn,f.revsal_flag,f.cancel_ssn,f.cancel_flag,"
				+ " f.msg_src_id||'-'||(select FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where dest_id = f.msg_src_id) trans_chl_name,"
				+ " f.pos_entry_mode,f.up_f37 msg_src_retrivl_ref,"
				+ " decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group,"
			    + " substr(f.misc_2,104,2) card_type, "
			    + " (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '0' else '1' end) card_org, "
			    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no,"
			    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then "
			    + " (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) ) "
			    + " else '*' end) bank_name, "
			    + " (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name,"
			    + " m.mcht_id_up mcht_brh_id,m.mcht_name_up mcht_brh_name,m.term_id_up term_brh_id,m.brh_id3, "
			    + "f.track_3_data, "
			    + " (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type, "
			    + " f.RESP_CODE,(select term.TERM_TP from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP,"
				+ " (select trim(substr(b.SETTLE_ACCT,1,1)) from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
				+ " from tbl_n_txn_his f left join gtwyadm.tbl_upbrh_mcht m on f.up_mcht_id = m.mcht_id_up and f.up_term_id = m.term_id_up and f.up_brh_id3 = m.brh_id3"
				+ " ) a";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

		System.out.println("历史交易查询sql= "+sql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		if (dataList.size() > 0) {
			String amtSql = "select "
					+ " to_char("
					+ "nvl(sum( a.amt ),0)"
					+ ",'99,999,999,990.99') as amt_total "
					+ " from ( select inst_date,substr(f.inst_date,1,8) as txn_date,"
					+ "	substr(f.inst_date,9,6) as txn_time,"
					+ panSql
					+ "	f.card_accp_id,f.authr_id_resp,"
					+ "	(select MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id) as mcht_name,"
					+ "	f.card_accp_term_id,"
					+ "	f.term_ssn,"
					+ "	f.sys_seq_num,"
					+ "	(SELECT c.CREATE_NEW_NO||' - '||c.brh_name FROM TBL_BRH_INFO c WHERE c.brh_id in (select bank_no from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id))AS brh_id_name,"
					+ "	f.retrivl_ref,"
					+ "	(case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ) as amt, "
					+ "	(select TXN_NAME from TBL_TXN_NAME where TXN_NUM =f.txn_num ) as txn_num_name, f.txn_num,"
					+ "	f.trans_state,"
					+ "	f.RESP_CODE||(select '-'||trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = f.RESP_CODE and SRC_ID='2801' ) as resp_name,"
					+ " f.tdestid,"
					+ "	f.tdestid||(select '-'||FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where DEST_ID = f.tdestid) as settle_brh_name,"
					+ "	f.revsal_ssn,f.revsal_flag,f.cancel_ssn,f.cancel_flag,"
					+ " f.msg_src_id||'-'||(select FIRST_BRH_NAME from TBL_FIRST_BRH_DEST_ID where dest_id = f.msg_src_id) trans_chl_name,"
					+ " f.pos_entry_mode,f.up_f37 msg_src_retrivl_ref,"
					+ " decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group,"
				    + " substr(f.misc_2,104,2) card_type, "
				    + " (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '0' else '1' end) card_org, "
				    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no,"
				    + " (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then "
				    + " (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) ) "
				    + " else '*' end) bank_name, "
				    + " (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name,"
				    + " m.mcht_id_up mcht_brh_id,m.mcht_name_up mcht_brh_name,m.term_id_up term_brh_id,m.brh_id3, "
				    + "f.track_3_data, "
				    + " (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type, "
				    + " f.RESP_CODE,(select term.TERM_TP from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP,"
					+ " (select trim(substr(b.SETTLE_ACCT,1,1)) from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
					+ " from tbl_n_txn_his f left join gtwyadm.tbl_upbrh_mcht m on f.up_mcht_id = m.mcht_id_up and f.up_term_id = m.term_id_up and f.up_brh_id3 = m.brh_id3"
					+ " ) a"+ whereSql.toString()
					+ " and a.txn_num in ('1101','1091','5151') and a.trans_state in ( '1','R') and a.REVSAL_FLAG!='1' and a.CANCEL_FLAG!='1' ";
			String amt = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
					amtSql);

			Object[] obj = new Object[dataList.get(0).length];
			obj[0] = "<font color='red'>总计</font>";
			obj[1] = "<font color='red'>交易总笔数:</font>";
			obj[2] = "<font color='red'>" + count + "笔</font>";
			obj[3] = "";
			obj[4] = "";
			obj[5] = "<font color='red'>交易总金额:</font>";
			obj[6] = "<font color='red'>" + amt + "</font>";

			dataList.add(obj);
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * T+0结算查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getTxnTaddZeroInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryBrhId = request.getParameter("queryBrhId");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");
		String queryTermSsn = request.getParameter("queryTermSsn");

		String querySysSeqNum = request.getParameter("querySysSeqNum");
		String queryRevsalSsn = request.getParameter("queryRevsalSsn");
		String queryAmtTransLow = request.getParameter("queryAmtTransLow");
		String queryAmtTransUp = request.getParameter("queryAmtTransUp");
		// String queryTxnName = request.getParameter("queryTxnName");
		// String queryTransState = request.getParameter("queryTransState");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		StringBuffer whereSql = new StringBuffer(
				" where f.card_accp_id in (select mcht_no from tbl_mcht_base_inf a where a.bank_no in "
						+ operator.getBrhBelowId()
						+ ") "
						+ "and f.txn_num = '1101' "
						+ "and f.trans_state = '1' "
						+ "and f.revsal_flag = '0' "
						+ "and f.cancel_flag in ('0' , '-') "
						+ "and f.retrivl_ref in (select k.retrivl_ref from (select retrivl_ref,count(*) from TBL_N_TXN group by retrivl_ref having count(*)=1) k) ");

		if (isNotEmpty(queryBrhId)) {
			// whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf a where a.bank_no = '"
			// + queryBrhId.trim() + "')");
			whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ queryBrhId.trim()
					+ "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
		}
		if (isNotEmpty(queryCardAccpId)) {
			// whereSql.append(" AND f.card_accp_id").append(" = ").append("'").append(queryCardAccpId).append("'");
			whereSql.append(" AND f.card_accp_id")
					.append(" in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =")
					.append("'")
					.append(queryCardAccpId)
					.append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND f.card_accp_term_id").append(" like ")
					.append("'%").append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND f.pan").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(queryTermSsn)) {
			whereSql.append(" AND f.term_ssn").append(" like ").append("'%")
					.append(queryTermSsn).append("%'");
		}
		if (isNotEmpty(querySysSeqNum)) {
			whereSql.append(" AND f.sys_seq_num").append(" like ").append("'%")
					.append(querySysSeqNum).append("%'");
		}
		if (isNotEmpty(queryRevsalSsn)) {
			whereSql.append(" AND f.revsal_ssn").append(" like ").append("'%")
					.append(queryRevsalSsn).append("%'");
		}
		if (isNotEmpty(queryAmtTransLow)) {
			whereSql.append(" AND TO_NUMBER(NVL(trim(f.amt_trans),0))/100 ")
					.append(" >= ").append("'").append(queryAmtTransLow)
					.append("'");
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSql.append(" AND TO_NUMBER(NVL(trim(f.amt_trans),0))/100 ")
					.append(" <= ").append("'").append(queryAmtTransUp)
					.append("'");
		}
		// if(isNotEmpty(queryTxnName)) {
		// whereSql.append(" AND f.txn_num").append(" = ").append("'").append(queryTxnName).append("'");
		// }
		// if(isNotEmpty(queryTransState)) {
		// if(Constants.TransState.equals(queryTransState)){
		// whereSql.append(" AND f.trans_state  !='1'  and f.trans_state  !='0' ");
		// }else{
		// whereSql.append(" AND f.trans_state").append(" = ").append("'").append(queryTransState).append("'");
		// }
		// }
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND f.inst_date").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND f.inst_date").append(" <= ").append("'")
					.append(endDate).append("'");
		}

		String sql = "SELECT substr(f.inst_date,1,8),substr(f.inst_date,9,6),"
				+ "f.pan,f.card_accp_id,"
				+ "(select b.MCHT_NO||' - '||b.MCHT_NM from  TBL_MCHT_BASE_INF b where  b.MCHT_NO=f.card_accp_id )as mcht_name,"
				+ "f.card_accp_term_id,f.term_ssn,f.sys_seq_num,"
				+ "(select b.brh_id||' - '||b.brh_name from TBL_BRH_INFO b where b.brh_id = (select bank_no from tbl_mcht_base_inf a where a.mcht_no = f.card_accp_id))as brh_id,"
				+ "f.revsal_ssn,f.retrivl_ref,"
				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,"
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_name ,"
				+ "f.trans_state,"
				+ "trim(f.RESP_CODE) ||(select  '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = trim(f.RESP_CODE) and SRC_ID='2801') as resp_name "
				+ "  from TBL_N_TXN f ";

		sql = sql + whereSql.toString();
		sql = sql + " order by f.inst_date desc";
		String countSql = "SELECT count(*) FROM TBL_N_TXN f ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		// TblMchntInfoDAO tblMchntInfoDAO = (TblMchntInfoDAO)
		// ContextUtil.getBean("MchntInfoDAO");
		// TblMchtBaseInf inf = tblMchntInfoDAO.get(operator.getOprMchtNo());

		DecimalFormat df = new DecimalFormat("0.00");
		/** 0：普通商户 1：一级商户 2：二级商户 3：网上支付商户 */
		// if(!"1".equals(inf.getMchtGroupFlag())){
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				// if(objects[2]!=null&&!"".equals(objects[2])){
				// String str=objects[2].toString();
				// objects[2]=str.substring(0, 6)+"****"+str.substring(10);
				// }
				if (objects[11] != null && !"".equals(objects[11])) {
					objects[11] = df.format(Double.parseDouble(objects[11]
							.toString()));
					objects[11] = CommonFunction.moneyFormat(objects[11]
							.toString());
				}
			}
		}
		// }

		if (dataList.size() > 0) {
			// List<Object[]> amtList =
			// CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			// Double amt=0.00;
			String sqls1 = "select "
					+ "nvl(sum( case when f.txn_num ='5151' then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 "
					+ "else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end),0) from TBL_N_TXN f  "
					+ whereSql.toString()
					+ "and f.txn_num in ('1101','1091','5151') and f.trans_state = '1' and f.REVSAL_FLAG!='1' and f.CANCEL_FLAG!='1' ";
			String s1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
					sqls1);
			Double amt = Double.parseDouble(s1);
			/*
			 * for (Object[] objects : amtList) {
			 * if(objects[11]!=null&&!"".equals(objects[11])){ String
			 * str11=objects[11].toString(); amt+=Double.parseDouble(str11);
			 * objects
			 * [11]=df.format(Double.parseDouble(objects[11].toString()));
			 * 
			 * } }
			 */

			Object[] obj = new Object[dataList.get(0).length];

			obj[0] = "<font color='red'>总计</font>";

			obj[1] = "<font color='red'>交易总笔数:</font>";
			obj[2] = "<font color='red'>" + count + "笔</font>";
			obj[3] = "";
			obj[4] = "";
			obj[5] = "<font color='red'>交易总金额:</font>";
			obj[6] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt)) + "元</font>";

			// for (int i = 5; i < obj.length; i++) {
			// obj[i]=" ";
			// }
			dataList.add(obj);

		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 代收付来帐包登记簿 查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2014年3月10日 下午3:49:36
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRcvPacksData(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String batchId = request.getParameter("batchId");
		String startEntDate = request.getParameter("startEntDate");
		String stopEntDate = request.getParameter("stopEntDate");
		String procStat = request.getParameter("procStat");
		String mchtNo = request.getParameter("mchtNo");
		// String bankCode = request.getParameter("bankCode");
		// String acctNo = request.getParameter("acctNo");
		// String name = request.getParameter("name");
		// String rspCode = request.getParameter("rspCode");
		StringBuffer whereSql = new StringBuffer(" where 1=1");
		if (isNotEmpty(batchId)) {
			whereSql.append(" and trim(t.BATCH_ID) like '%"
					+ StringUtil.escapeSpecialCharsSql(batchId) + "%' ");
		}
		if (isNotEmpty(startEntDate)) {
			whereSql.append(" and t.ENT_DATE >='"
					+ startEntDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(stopEntDate)) {
			whereSql.append(" and t.ENT_DATE <='"
					+ stopEntDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(procStat)) {
			whereSql.append(" and trim(t.PROC_STAT)='" + procStat + "' ");
		}
		if (isNotEmpty(mchtNo)) {
			whereSql.append(" and trim(t.MCHT_NO)='" + mchtNo + "' ");
		}
		// tbl_rcv_pack_dtl 的字段过滤 不再使用
		// if(isNotEmpty(rspCode) || isNotEmpty(bankCode) || isNotEmpty(acctNo)
		// || isNotEmpty(name)){
		// whereSql.append(" and t.batch_id in (select t2.batch_id from tbl_rcv_pack_dtl t2 where 1=1 ");
		// if(isNotEmpty(rspCode)){
		// whereSql.append(" and t2.RSP_CODE ='"+rspCode+"'");
		// }
		// if(isNotEmpty(bankCode)){
		// whereSql.append(" and t2.BANK_CODE ='"+bankCode+"'");
		// }
		// if(isNotEmpty(acctNo)){
		// whereSql.append(" and t2.ACCT_NO	 ='"+acctNo+"'");
		// }
		// if(isNotEmpty(name)){
		// whereSql.append(" and t2.NAME like '%"+name+"%'"); //注意数据库关键字如：_*/等
		// }
		// whereSql.append(")");
		// }
		whereSql.append(" and 1 like 1 escape '/'");
		String sql = "select ENT_DATE,BATCH_ID,BUS_TYPE,trim(MCHT_NO),RCV_DATE,RCV_BATCH_ID,PROC_STAT,TOT_CNT,TOT_AMT,FEE_CODE,CUR_CD,RSP_DATE,SUCC_CNT,SUCC_AMT,FAIL_CNT,FAIL_AMT,VER,RCV_FILE_NAME,REG_DATE,REG_TIME,BAT_SSN,LOCK_FLAG,substr(AUDIT_FLAG,1,2),PRE_AUDIT_TLR,CHECK_TLR,FINAL_AUDIT_TLR,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_RCV_PACK t"
				+ whereSql.toString() + " order by t.BATCH_ID desc";

		String countSql = "SELECT COUNT(*) FROM TBL_RCV_PACK t"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 根据代收付来帐包查询明细
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年3月11日 下午1:43:47
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRcvPackDtlData(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		String batchId = request.getParameter("batchId");
		String startEntDate = request.getParameter("startEntDate");
		String stopEntDate = request.getParameter("stopEntDate");
		String procStat = request.getParameter("procStat");
		String mchtNo = request.getParameter("mchtNo");
		String bankCode = request.getParameter("bankCode");
		String acctNo = request.getParameter("acctNo");
		String name = request.getParameter("name");
		String rspCode = request.getParameter("rspCode");
		if (isNotEmpty(batchId)) {
			whereSql.append(" and trim(t.BATCH_ID) like '%"
					+ StringUtil.escapeSpecialCharsSql(batchId) + "%' ");
		}
		if (isNotEmpty(startEntDate)) {
			whereSql.append(" and t.ENT_DATE >='"
					+ startEntDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(stopEntDate)) {
			whereSql.append(" and t.ENT_DATE <='"
					+ stopEntDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(procStat)) {
			whereSql.append(" and t.PROC_STAT='" + procStat + "' ");
		}
		if (isNotEmpty(mchtNo)) {
			whereSql.append(" and trim(t.MCHT_NO)='" + mchtNo + "' ");
		}
		if (isNotEmpty(rspCode)) {
			// 页面传来的值：0-处理成功，1-处理失败,2-未处理
			// 数据库中 0000-处理成功，非0000-处理失败，空-未处理
			if ("0".equals(rspCode)) {
				whereSql.append(" and t.RSP_CODE ='0000'");
			} else if ("1".equals(rspCode)) {
				whereSql.append(" and t.RSP_CODE !='0000'");
			} else if ("2".equals(rspCode)) {
				whereSql.append(" and t.RSP_CODE is null");
			}
		}
		if (isNotEmpty(bankCode)) {
			whereSql.append(" and trim(t.BANK_CODE) like '%"
					+ StringUtil.escapeSpecialCharsSql(bankCode) + "%'");
		}
		if (isNotEmpty(acctNo)) {
			whereSql.append(" and trim(t.ACCT_NO)	like '%"
					+ StringUtil.escapeSpecialCharsSql(acctNo) + "%'");
		}
		if (isNotEmpty(name)) {
			whereSql.append(" and trim(t.NAME) like '%"
					+ StringUtil.escapeSpecialCharsSql(name) + "%'");
		}
		whereSql.append(" and 1 like 1 escape '/'");
		String sql = "SELECT ENT_DATE,BATCH_ID,SEQ,MCHT_NO,RCV_DATE,PROC_STAT,RCV_BATCH_ID,CUST_ID,BANK_CODE,ACCT_NO,NAME,PROVINCE,CITY,OPN_ORG_NAME,trim(ACCT_TYPE),trim(CUST_TYPE),AMT,trim(CUR_CD),AGT_NO,AGT_CUST_NO,ID_TYPE,ID_NO,MOBILE,CUST_SELF_NO,REMARK,RSP_CODE,RSP_DESC,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_RCV_PACK_DTL t"
				+ whereSql + " order by BATCH_ID desc";
		String countSql = "SELECT COUNT(*) FROM TBL_RCV_PACK_DTL t" + whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 代收付往帐包登记簿 查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2014年3月14日 上午10:35:36
	 */

	public static Object[] getSndPacksData(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String batchId = request.getParameter("batchId");
		String startEntDate = request.getParameter("startEntDate");
		String stopEntDate = request.getParameter("stopEntDate");
		String procStat = request.getParameter("procStat");
		String mchtNo = request.getParameter("mchtNo");
		// String rspCode = request.getParameter("rspCode");
		// String bankCode = request.getParameter("bankCode");
		// String acctNo = request.getParameter("acctNo");
		// String name = request.getParameter("name");

		StringBuffer whereSql = new StringBuffer(" where 1=1");
		if (isNotEmpty(batchId)) {
			whereSql.append(" and trim(t.BATCH_ID) like '%"
					+ StringUtil.escapeSpecialCharsSql(batchId) + "%' ");
		}
		if (isNotEmpty(startEntDate)) {
			whereSql.append(" and t.ENT_DATE >='"
					+ startEntDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(stopEntDate)) {
			whereSql.append(" and t.ENT_DATE <='"
					+ stopEntDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(procStat)) {
			whereSql.append(" and t.PROC_STAT='" + procStat + "' ");
		}
		if (isNotEmpty(mchtNo)) {
			whereSql.append(" and t.MCHT_NO='" + mchtNo + "' ");
		}
		// tbl_rcv_pack_dtl 的字段过滤
		// if(isNotEmpty(rspCode) || isNotEmpty(bankCode) || isNotEmpty(acctNo)
		// || isNotEmpty(name)){
		// whereSql.append(" and t.batch_id in (select t2.batch_id from tbl_snd_pack_dtl t2 where 1=1 ");
		// if(isNotEmpty(rspCode)){
		// whereSql.append(" and t2.RSP_CODE ='"+rspCode+"'");
		// }
		// if(isNotEmpty(bankCode)){
		// whereSql.append(" and t2.BANK_NO ='"+bankCode+"'");
		// }
		// if(isNotEmpty(acctNo)){
		// whereSql.append(" and t2.ACCT_NO	 ='"+acctNo+"'");
		// }
		// if(isNotEmpty(name)){
		// whereSql.append(" and t2.NAME like '%"+name+"%'"); //注意数据库关键字如：_*/等
		// }
		// whereSql.append(")");
		// }
		whereSql.append(" and 1 like 1 escape '/'");
		String sql = "select BATCH_ID,ENT_DATE,MCHT_NO,trim(BUS_TYPE),COM_CODE,FILE_BATCH_ID,BANK_CODE,FEE_CODE,PROC_STAT,TOT_CNT,TOT_AMT,RSP_DATE,SUCC_CNT,SUCC_AMT,FAIL_CNT,FAIL_AMT,trim(BUS_CODE),TRAN_DATE,trim(CUR_CD),TXN_FILE_NAME,FILE_NAME,AGENT_ACCT_NO,AGENT_ACCT_NAME,R_NAME_FLAG,R_MCHT_NO,PROD_CODE,REG_DATE,REG_TIME,BAT_SSN,LOCK_FLAG,substr(AUDIT_FLAG,1,2),PRE_AUDIT_TLR,CHECK_TLR,FINAL_AUDIT_TLR,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_SND_PACK t"
				+ whereSql.toString() + " order by t.BATCH_ID desc";

		String countSql = "SELECT COUNT(1) FROM TBL_SND_PACK t"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 根据代收付往帐包查询明细
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年3月14日 下午2:15:09
	 */
	public static Object[] getSndPackDtlData(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		String batchId = request.getParameter("batchId");
		String startEntDate = request.getParameter("startEntDate");
		String stopEntDate = request.getParameter("stopEntDate");
		String procStat = request.getParameter("procStat");
		String comCode = request.getParameter("comCode");
		String rspCode = request.getParameter("rspCode");
		String bankCode = request.getParameter("bankCode");
		String acctNo = request.getParameter("acctNo");
		String name = request.getParameter("name");
		if (isNotEmpty(batchId)) {
			whereSql.append(" and BATCH_ID like '%"
					+ StringUtil.escapeSpecialCharsSql(batchId) + "%' ");
		}
		if (isNotEmpty(startEntDate)) {
			whereSql.append(" and t.ENT_DATE >='"
					+ startEntDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(stopEntDate)) {
			whereSql.append(" and t.ENT_DATE <='"
					+ stopEntDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(procStat)) {
			whereSql.append(" and t.PROC_STAT='" + procStat + "' ");
		}
		if (isNotEmpty(comCode)) {
			whereSql.append(" and t.COM_CODE='" + comCode + "' ");
		}
		if (isNotEmpty(rspCode)) {
			whereSql.append(" and t.RSP_CODE ='" + rspCode + "'");
		}
		if (isNotEmpty(bankCode)) {
			whereSql.append(" and t.BANK_NO ='" + bankCode + "'");
		}
		if (isNotEmpty(acctNo)) {
			whereSql.append(" and t.ACCT_NO	 ='" + acctNo + "'");
		}
		if (isNotEmpty(name)) {
			whereSql.append(" and t.NAME like '%"
					+ StringUtil.escapeSpecialCharsSql(name) + "%'");
		}
		whereSql.append(" and 1 like 1 escape '/'");
		String sql = "SELECT BATCH_ID,SEQ,ENT_DATE,PROC_STAT,COM_CODE,FILE_BATCH_ID,AGT_NO,AMT,BANK_TYPE,BANK_NO,ACCT_NO,NAME,REMARK,RSP_CODE,NOTE,RSP_DESC,RSP_DATE,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_SND_PACK_DTL t"
				+ whereSql + " order by BATCH_ID desc";
		String countSql = "SELECT COUNT(*) FROM TBL_SND_PACK_DTL t " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 代收付 退汇信息 查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2014年3月10日 下午3:49:36
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRcvReexchangeData(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 and t.RSP_CODE ！='0000' and t.RSP_CODE !='4000'");
		String batchId = request.getParameter("batchId");
		String startEntDate = request.getParameter("startEntDate");
		// String stopEntDate = request.getParameter("stopEntDate");
		String mchtNo = request.getParameter("mchtNo");
		if (isNotEmpty(batchId)) {
			whereSql.append(" and t.BATCH_ID like '%"
					+ StringUtil.escapeSpecialCharsSql(batchId) + "%' ");
		}
		if (isNotEmpty(startEntDate)) {
			whereSql.append(" and t.ENT_DATE ='"
					+ startEntDate.split("T")[0].replace("-", "") + "' ");
		}
		// if(isNotEmpty(stopEntDate)){
		// whereSql.append(" and t.ENT_DATE <='"+stopEntDate.split("T")[0].replace("-",
		// "")+"' ");
		// }
		if (isNotEmpty(mchtNo)) {
			whereSql.append(" and t.MCHT_NO='" + mchtNo + "' ");
		}
		whereSql.append(" and 1 like 1 escape '/'");
		String sql = "SELECT t.ENT_DATE,t.BATCH_ID,t.SEQ,t.MCHT_NO,t.RCV_DATE,m.acct_no,t.PROC_STAT,t.RCV_BATCH_ID,t.CUST_ID,t.BANK_CODE,t.ACCT_NO,t.NAME,t.PROVINCE,t.CITY,t.OPN_ORG_NAME,t.ACCT_TYPE,t.CUST_TYPE,t.AMT,t.CUR_CD,t.AGT_NO,t.AGT_CUST_NO,t.ID_TYPE,t.ID_NO,t.MOBILE,t.CUST_SELF_NO,t.REMARK,t.RSP_CODE,t.RSP_DESC,t.RSP_DATE,t.FLAG1,t.FLAG2,t.FLAG3,t.MISC1,t.MISC2,t.MISC3,t.LST_UPD_TLR,t.LST_UPD_TIME,t.CREATE_TIME from TBL_RCV_PACK_DTL t left join tbl_mcht_info m on t.mcht_no = m.mcht_no"
				+ whereSql + " order by t.BATCH_ID desc";
		String countSql = "SELECT COUNT(1) FROM TBL_RCV_PACK_DTL t left join tbl_mcht_info m on t.mcht_no = m.mcht_no"
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 代收付 参数信息 查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2014年3月10日 下午3:49:36
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getParamInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");

		String sql = "SELECT PARAM_MARK,PARAM_CODE,PARAM_TYPE,PARAM_PRO,PARAM_VALUE,DESCR,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_PARAM_INFO t"
				+ whereSql + " order by PARAM_MARK,PARAM_CODE ";
		String countSql = "SELECT COUNT(*) FROM TBL_PARAM_INFO t " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 地区码表
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年3月19日 下午4:27:19
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAreaInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");

		String sql = "SELECT AREA_NO,ZIP,PROVINCE,AREA_NAME from TBL_AREA_INFO t"
				+ whereSql + " order by AREA_NO ";
		String countSql = "SELECT COUNT(*) FROM TBL_AREA_INFO t" + whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 银行代码信息表
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年3月19日 下午4:39:36
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBankInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");

		String sql = "SELECT BANK_NO,BANK_NAME from TBL_BANK_INFO t" + whereSql
				+ " order by BANK_NO ";
		String countSql = "SELECT COUNT(*) FROM TBL_BANK_INFO t " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 业务代码信息表
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年3月19日 下午4:50:40
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBusCodeInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");

		String sql = "SELECT BUS_CODE，USAGE，TYPE_NO，NAME，SEQ from TBL_BUS_CODE_INFO t"
				+ whereSql + " order by BUS_CODE ";
		String countSql = "SELECT COUNT(*) FROM TBL_BUS_CODE_INFO t "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户文件传输配置表
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年3月19日 下午9:07:07
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtFileTransInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");

		String sql = "SELECT MCHT_NO,GET_FILE_WAY,COMM_WAY,CHECK_TYPE,USER_NAME,PASSWD,IP,PORT,FILE_PATH,KEY_PATH,KEY_IDX,KEY_VAL,CHECK_VAL,RSP_SEND_FLAG,RSP_SEND_TIME,DZ_SEND_TIME,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_MCHT_FILE_TRANS_INFO t"
				+ whereSql + " order by MCHT_NO ";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_FILE_TRANS_INFO t "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	public static Object[] getMchtInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");

		String sql = "SELECT MCHT_NO,MCHT_NAME,STAT,MCHT_COM_CODE,OPN_BANK_NO,ACCT_NO,NAME,substr(FLAG,1,1),substr(FLAG,2,1),substr(FLAG,3,1),substr(FLAG,4,1),substr(FLAG,5,1),RISL_LVL,MCHT_LVL,MCHT_GRP,AREA_NO,ADDR,ZIP_CODE,TEL,MAIL,ENTRY_TLR,ENTRY_DATE,PRE_AUD_TLR,CONFIRM_TLR,ENABLE_DATE,CONTRACT,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_MCHT_INFO t"
				+ whereSql + " order by MCHT_NO ";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_INFO t " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	public static Object[] getMchtFund(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String mchtNo = request.getParameter("mchtNo");

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		if (isNotEmpty(mchtNo)) {
			whereSql.append(" and t.MCHT_NO='" + mchtNo + "' ");
		}
		String sql = "SELECT MCHT_NO,BAL,AVL_BAL,FRZ_AMT,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_MCHT_FUND t"
				+ whereSql + " order by MCHT_NO ";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_FUND t " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 初审查询
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年3月25日 下午5:55:32
	 */
	public static Object[] getPreAuditData(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(" where 1=1");
		whereSql.append(" and substr(t.AUDIT_FLAG,1,1) in ('0','2')");
		String sql = "select BATCH_ID,ENT_DATE,MCHT_NO,BUS_TYPE,COM_CODE,FILE_BATCH_ID,BANK_CODE,FEE_CODE,PROC_STAT,TOT_CNT,TOT_AMT,RSP_DATE,SUCC_CNT,SUCC_AMT,FAIL_CNT,FAIL_AMT,BUS_CODE,TRAN_DATE,CUR_CD,TXN_FILE_NAME,FILE_NAME,AGENT_ACCT_NO,AGENT_ACCT_NAME,R_NAME_FLAG,R_MCHT_NO,PROD_CODE,REG_DATE,REG_TIME,BAT_SSN,LOCK_FLAG,subStr(t.AUDIT_FLAG,1,2),PRE_AUDIT_TLR,CHECK_TLR,FINAL_AUDIT_TLR,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME FROM TBL_SND_PACK t"
				+ whereSql.toString() + " order by t.BATCH_ID desc";

		String countSql = "SELECT COUNT(1) FROM TBL_SND_PACK t"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 复核查询
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年3月25日 下午5:58:06
	 */
	public static Object[] getCheckData(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(
				" where 1=1 and substr(t.AUDIT_FLAG,1,2) ='10'");

		String sql = "select BATCH_ID,ENT_DATE,MCHT_NO,BUS_TYPE,COM_CODE,FILE_BATCH_ID,BANK_CODE,FEE_CODE,PROC_STAT,TOT_CNT,TOT_AMT,RSP_DATE,SUCC_CNT,SUCC_AMT,FAIL_CNT,FAIL_AMT,BUS_CODE,TRAN_DATE,CUR_CD,TXN_FILE_NAME,FILE_NAME,AGENT_ACCT_NO,AGENT_ACCT_NAME,R_NAME_FLAG,R_MCHT_NO,PROD_CODE,REG_DATE,REG_TIME,BAT_SSN,LOCK_FLAG,subStr(t.AUDIT_FLAG,1,2),PRE_AUDIT_TLR,CHECK_TLR,FINAL_AUDIT_TLR,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME FROM TBL_SND_PACK t"
				+ whereSql.toString() + " order by t.BATCH_ID desc";

		String countSql = "SELECT COUNT(1) FROM TBL_SND_PACK t"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	public static Object[] getSendFilesData(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(
				" where 1=1 and subStr(t.AUDIT_FLAG,1,2) ='11'");
		whereSql.append(" and t.PROC_STAT in ('2','5','E')"); // 处理状态 :2-已入库
																// 5-发送失败 E-文件错误
		String sql = "select BATCH_ID,ENT_DATE,MCHT_NO,BUS_TYPE,COM_CODE,FILE_BATCH_ID,BANK_CODE,FEE_CODE,PROC_STAT,TOT_CNT,TOT_AMT,RSP_DATE,SUCC_CNT,SUCC_AMT,FAIL_CNT,FAIL_AMT,BUS_CODE,TRAN_DATE,CUR_CD,TXN_FILE_NAME,FILE_NAME,AGENT_ACCT_NO,AGENT_ACCT_NAME,R_NAME_FLAG,R_MCHT_NO,PROD_CODE,REG_DATE,REG_TIME,BAT_SSN,LOCK_FLAG,subStr(t.AUDIT_FLAG,1,2),PRE_AUDIT_TLR,CHECK_TLR,FINAL_AUDIT_TLR,FLAG1,FLAG2,FLAG3,MISC1,MISC2,MISC3,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME FROM TBL_SND_PACK t"
				+ whereSql.toString() + " order by t.BATCH_ID desc";

		String countSql = "SELECT COUNT(1) FROM TBL_SND_PACK t"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 对账文件查询
	 * 
	 * @author huangjl
	 * @param begin
	 * @param request
	 * @return 2014年4月4日 上午9:56:25
	 */
	public static Object[] getStlmFileTransInf(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		String mchoNo = request.getParameter("mchtNo");
		String fileStat = request.getParameter("fileStat");
		String startDate = request.getParameter("startDate");
		String stopDate = request.getParameter("stoptDate");
		if (isNotEmpty(mchoNo)) {
			whereSql.append(" and t.MCHT_NO ='" + mchoNo + "'");
		}
		if (isNotEmpty(fileStat)) {
			whereSql.append(" and t.FILE_STAT ='" + fileStat + "'");
		}
		if (isNotEmpty(startDate)) {
			whereSql.append(" and t.STLM_DATE ='"
					+ startDate.split("T")[0].replace("-", "") + "' ");
		}
		if (isNotEmpty(stopDate)) {
			whereSql.append(" and t.STLM_DATE <='"
					+ stopDate.split("T")[0].replace("-", "") + "' ");
		}
		String sql = "select STLM_DATE,MCHT_NO,FILE_NAME,FILE_STAT,FAIL_DESC,LST_UPD_TLR,LST_UPD_TIME,CREATE_TIME from TBL_STLM_FILE_TRANS_INF t"
				+ whereSql.toString() + " order by t.MCHT_NO";

		String countSql = "SELECT COUNT(1) FROM TBL_STLM_FILE_TRANS_INF t"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommDFQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommDFQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 通联用户信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-11下午04:20:05
	 */
	@SuppressWarnings("unchecked")
	public static Object[] oprInfoQueryTl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		@SuppressWarnings("unused")
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String queryOprId = request.getParameter("queryOprId");
		String queryOprName = request.getParameter("queryOprName");
		String queryMchtNo = request.getParameter("queryMchtNo");
		String queryOprStatus = request.getParameter("queryOprStatus");
		String queryMchtBrhFlag = request.getParameter("queryMchtBrhFlag");
		String queryBrhId = request.getParameter("queryBrhId");
		StringBuffer oprInfo = new StringBuffer("");

		/** modify by xiaojian.yu 不支持模糊查询bug修复 20101116 start **/
		if (isNotEmpty(queryOprId)) {
			oprInfo.append(" AND a.OPR_ID").append(" like ").append("'%")
					.append(queryOprId).append("%'");
		}
		if (isNotEmpty(queryOprName)) {
			oprInfo.append(" AND a.OPR_NAME").append(" like ").append("'%")
					.append(queryOprName).append("%'");
		}
		if (isNotEmpty(queryMchtNo)) {
			oprInfo.append(" AND a.MCHT_NO").append(" = ").append("'")
					.append(queryMchtNo).append("' ");
		}
		if (isNotEmpty(queryMchtBrhFlag)) {
			oprInfo.append(" AND a.MCHT_BRH_FLAG").append(" = ").append("'")
					.append(queryMchtBrhFlag).append("'");
		}
		if (isNotEmpty(queryOprStatus)) {
			oprInfo.append(" AND a.OPR_STATUS").append(" = ").append("'")
					.append(queryOprStatus).append("'");
		}
		if (isNotEmpty(queryBrhId)) {
			oprInfo.append(" AND a.BRH_ID").append(" = ").append("'")
					.append(queryBrhId).append("'");
		}
		oprInfo.append(" AND b.ROLE_LEVEL ='0'  ");

		//20160412 guoyu update
//		String sql = "SELECT a.OPR_ID,a.OPR_NAME,a.MCHT_NO||'-'||c.MCHT_NM,a.role_id,d.CREATE_NEW_NO||'-'||d.BRH_NAME,b.ROLE_NAME,a.opr_status,a.OPR_SEX,"
		String sql = "SELECT a.OPR_ID,a.OPR_NAME,"
				+ " case when a.MCHT_BRH_FLAG='0' then a.MCHT_NO||'-'||c.MCHT_NM else '-' end,a.role_id,"
				+ " case when a.MCHT_BRH_FLAG='0' then e.CREATE_NEW_NO||'-'||e.BRH_NAME else d.CREATE_NEW_NO||'-'||d.BRH_NAME end,b.ROLE_NAME,a.opr_status,a.OPR_SEX,"
				+ " a.cer_type,a.cer_no,a.contact_phone,a.EMAIL,a.PWD_OUT_DATE ,a.MCHT_NO ,a.BRH_ID,a.MCHT_BRH_FLAG"
				+ " FROM SH_TBL_OPR_INFO a left join SH_TBL_ROLE_INFO b on a.ROLE_ID=b.ROLE_ID "
				+ " left join TBL_BRH_INFO d on a.BRH_ID=d.BRH_ID"
//				+ " left join TBL_MCHT_BASE_INF c on a.MCHT_NO=c.MCHT_NO WHERE 1=1 ";
				+ " left join TBL_MCHT_BASE_INF c on a.MCHT_NO=c.MCHT_NO "
		        + " left join TBL_BRH_INFO e on c.BANK_NO = e.BRH_ID WHERE 1=1 ";
		sql = sql + oprInfo.toString();
		sql = sql + " order by OPR_ID";
		String countSql = "SELECT count(*) FROM SH_TBL_OPR_INFO a left join SH_TBL_ROLE_INFO b on a.ROLE_ID=b.ROLE_ID  WHERE 1=1 ";
		countSql = countSql + oprInfo.toString();

//		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
		List<Object[]> dataList = CommonFunction.getCommMisQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
//		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
		String count = CommonFunction.getCommMisQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 移机风控查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskTotal(int begin, HttpServletRequest request) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryBrhId = request.getParameter("queryBrhId");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		StringBuffer whereSql = new StringBuffer(
				"where a.RISK_ID='B01' and a.risk_seq!=1");
		if (isNotEmpty(startDate)) {
			whereSql.append("and a.RISK_DATE>='" + startDate + "'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" and a.RISK_DATE<='" + endDate + "'");
		}
		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" and substr(a.RISK_INFO,1,15)='" + queryCardAccpId
					+ "'");
		}
		if (isNotEmpty(queryBrhId)) {

			whereSql.append(" and (SELECT B.BANK_NO FROM TBL_MCHT_BASE_INF B WHERE B.MCHT_NO = substr(a.RISK_INFO,1,15))='"
					+ queryBrhId + "'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" and substr(a.RISK_INFO,17,8)='"
					+ queryCardAccpTermId + "'");
		}
		Object[] ret = new Object[2];

		String sql = "SELECT a.RISK_DATE,a.RISK_SEQ,a.RISK_INFO from TBL_RISK_TOTAL a "
				+ whereSql.toString()
				+ " order by a.RISK_ID,a.RISK_DATE desc,a.RISK_SEQ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_TOTAL a "
				+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataListOld = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		LinkedHashMap<String, String> mchtMap = SelectMethod.getMchntNoAll();
		LinkedHashMap<String, String> brhIdNmMap = SelectMethod.getBrhIdNm();
		List<Object[]> dataListNew = null;
		dataListNew = new ArrayList<Object[]>();

		for (int i = 0; i < dataListOld.size(); i++) {
			Object[] obj = new Object[6];
			if (dataListOld != null && dataListOld.size() > 0) {

				String[] rets = dataListOld.get(i)[2].toString().trim()
						.split("\\|");
				// 第一个字段
				obj[0] = dataListOld.get(i)[0].toString().trim();
				// 第二个字段
				obj[1] = rets[0].toString().trim();
				// 第三个字段
				if (mchtMap.get(rets[0].toString().trim()) != null) {
					obj[2] = mchtMap.get(rets[0].toString().trim());
				} else {
					obj[2] = rets[0].toString().trim();
				}
				// 第四个字段
				if (brhIdNmMap.get(rets[0].toString().trim()) != null) {
					obj[3] = brhIdNmMap.get(rets[0].toString().trim());
				} else {
					obj[3] = rets[0].toString().trim();
				}
				// 第五个字段
				obj[4] = rets[1].toString().trim();
				// 第六个字段
				obj[5] = dataListOld.get(i)[1].toString().trim();
			}
			dataListNew.add(obj);
		}

		ret[0] = dataListNew;
		ret[1] = count;
		return ret;
	}

	/**
	 * 移机详细信息
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermMoveInfo(int begin, HttpServletRequest request) {

		String riskDate = request.getParameter("riskDate");
		String riskSeq = request.getParameter("riskSeq");
		Object[] ret = new Object[2];

		String sql = "SELECT a.RISK_DATE,a.RISK_INFO from TBL_RISK_TOTAL a where a.RISK_ID='B01'"
				+ " and a.RISK_DATE = '"
				+ riskDate
				+ "' and a.RISK_SEQ = '"
				+ riskSeq
				+ "'"
				+ " order by a.RISK_ID,a.RISK_DATE desc,a.RISK_SEQ";

		List<Object[]> dataListOld = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);
		List<Object[]> dataListNew = null;
		dataListNew = new ArrayList<Object[]>();
		if (dataListOld != null) {
			String[] rets = dataListOld.get(0)[1].toString().trim()
					.split("\\|");
			for (int j = 3; j < rets.length; j++) {
				if (rets[j].length() < 28) {
					continue;
				}
				Object[] obj = new Object[5];
				// 第一个字段
				obj[0] = rets[j].substring(0, 3).toString().trim();
				// 第二个字段
				obj[1] = rets[j].substring(3, 6).toString().trim();
				// 第三个字段
				obj[2] = rets[j].substring(6, 16).toString().trim();
				// 第四个字段
				obj[3] = rets[j].substring(16, 26).toString().trim();
				// 第五个字段
				obj[4] = rets[j].substring(27, rets[j].length()).toString()
						.trim()
						+ "笔";
				dataListNew.add(obj);
			}
		}

		ret[0] = dataListNew;
		ret[1] = dataListNew.size();
		return ret;
	}

	/**
	 * 差错退货申请
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getBthGcTxnSucc(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");

		String queryTxnName = request.getParameter("queryTxnName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryRetrivlRef = request.getParameter("queryRetrivlRef");

		StringBuffer whereSql = new StringBuffer(
				" where f.stlm_flag in ('2') and "
						+ "f.card_accp_id in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId()
						+ " ) "
						+ " and f.txn_num in ('1011','1091','1101','1111','1121','1141','1161','2011','2091','2101','2111','2121','3011','3091','3101','3111','3121','4011','4091','4101','4111','4121','5151','5161')");

		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND f.card_accp_id").append(" = ").append("'")
					.append(queryCardAccpId).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND f.card_accp_term_id").append(" like ")
					.append("'%").append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND f.pan").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(queryTxnName)) {
			whereSql.append(" AND f.txn_num").append(" = ").append("'")
					.append(queryTxnName).append("'");
		}

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND f.insert_date").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND f.insert_date").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryRetrivlRef)) {
			whereSql.append(" AND f.retrivl_ref").append(" like ").append("'%")
					.append(queryRetrivlRef).append("%'");
		}

		String sql = "SELECT f.DATE_SETTLMT,f.key_rsp,f.insert_date,f.insert_time,"
				+ "f.pan,f.card_accp_id,"
				+ " b.MCHT_NO||' - '||b.MCHT_NM as mcht_name,"
				+ "f.card_accp_term_id,f.retrivl_ref,"
				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,"
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_name "
				// +"(select trim(b.bank_no)||' - '||trim(d.brh_name) from   tbl_brh_info d where (b.bank_no)=trim(d.brh_id)  )as brhIdName "
				+ "  from BTH_CHK_TXN f left join TBL_MCHT_BASE_INF b on b.MCHT_NO=f.card_accp_id ";
		sql = sql + whereSql.toString();
		sql = sql + " order by f.insert_date desc,f.insert_time desc";
		String countSql = "SELECT count(*) FROM BTH_CHK_TXN  f ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		for (Object[] objects : dataList) {
			if (objects[9] != null && !"".equals(objects[9])) {
				objects[9] = CommonFunction.moneyFormat(objects[9].toString());
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 业务退货申请
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getTblNTxnHis(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");

		String queryTxnName = request.getParameter("queryTxnName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryRetrivlRef = request.getParameter("queryRetrivlRef");

		StringBuffer whereSql = new StringBuffer(
				" where  "
						+ "f.card_accp_id in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) "
						+ " and f.txn_num in ('1101') ");

		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND f.card_accp_id").append(" = ").append("'")
					.append(queryCardAccpId).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND f.card_accp_term_id").append(" like ")
					.append("'%").append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND f.pan").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(queryTxnName)) {
			whereSql.append(" AND f.txn_num").append(" = ").append("'")
					.append(queryTxnName).append("'");
		}

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND substr(f.inst_date,1,8)").append(" >= ")
					.append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND substr(f.inst_date,1,8)").append(" <= ")
					.append("'").append(endDate).append("'");
		}
		if (isNotEmpty(queryRetrivlRef)) {
			whereSql.append(" AND f.retrivl_ref").append(" like ").append("'%")
					.append(queryRetrivlRef).append("%'");
		}

		String sql = "SELECT f.sys_seq_num,substr(f.inst_date,1,8),substr(f.inst_date,9,6),"
				+ "f.pan,f.card_accp_id,"
				+ " b.MCHT_NO||' - '||b.MCHT_NM as mcht_name,"
				+ "f.card_accp_term_id,f.retrivl_ref,"
				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,"
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_name,"
				+ "f.trans_state,"
				+ "trim(f.RESP_CODE) ||(select  '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = trim(f.RESP_CODE)) as resp_name "
				// +"(select trim(b.bank_no)||' - '||trim(d.brh_name) from   tbl_brh_info d where (b.bank_no)=trim(d.brh_id)  )as brhIdName "
				+ "  from TBL_N_TXN_HIS f left join TBL_MCHT_BASE_INF b on b.MCHT_NO=f.card_accp_id ";
		sql = sql + whereSql.toString();
		sql = sql + " order by f.inst_date desc ";
		String countSql = "SELECT count(*) FROM TBL_N_TXN_HIS  f ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		for (Object[] objects : dataList) {
			if (objects[8] != null && !"".equals(objects[8])) {
				objects[8] = CommonFunction.moneyFormat(objects[8].toString());
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 差错退货申请查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getAmtbackApply(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");

		String queryTxnName = request.getParameter("queryTxnName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryDateSettlmt8 = request.getParameter("queryDateSettlmt8");
		String queryKeyCup = request.getParameter("queryKeyCup");
		String dateSettlmt8 = request.getParameter("dateSettlmt8");
		String keyCup = request.getParameter("keyCup");
		String queryRetrivlRef = request.getParameter("queryRetrivlRef");
		String queryApplyState = request.getParameter("queryApplyState");
		StringBuffer whereSql = new StringBuffer(
				" where "
						+ "f.card_accp_id in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND f.card_accp_id").append(" = ").append("'")
					.append(queryCardAccpId).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND f.card_accp_term_id").append(" like ")
					.append("'%").append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND f.pan").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(queryTxnName)) {
			whereSql.append(" AND f.txn_num").append(" = ").append("'")
					.append(queryTxnName).append("'");
		}

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND f.inst_date").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND f.inst_date").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryDateSettlmt8)) {
			whereSql.append(" AND f.date_settlmt_8").append(" = ").append("'")
					.append(queryDateSettlmt8).append("'");
		}
		if (isNotEmpty(queryKeyCup)) {
			whereSql.append(" AND trim(f.key_cup)").append(" = ").append("'")
					.append(queryKeyCup).append("'");
		}
		if (isNotEmpty(dateSettlmt8)) {
			whereSql.append(" AND f.date_settlmt_8").append(" = ").append("'")
					.append(dateSettlmt8).append("'");
		}
		if (isNotEmpty(keyCup)) {
			whereSql.append(" AND trim(f.key_cup)").append(" = ").append("'")
					.append(keyCup).append("'");
		}
		if (isNotEmpty(queryRetrivlRef)) {
			whereSql.append(" AND f.retrivl_ref").append(" like ").append("'%")
					.append(queryRetrivlRef).append("%'");
		}
		if (isNotEmpty(queryApplyState)) {
			whereSql.append(" AND f.apply_state").append(" = ").append("'")
					.append(queryApplyState).append("'");
		}

		String sql = "SELECT  f.date_settlmt_8,f.key_cup,f.apply_opr,f.apply_date,f.apply_time,f.apply_state,"
				+ "f.inst_date,f.inst_time,f.pan,f.card_accp_term_id,f.card_accp_id,f.brh_id,f.txn_num,"
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_num_name,"
				// + "f.amt_trans,"
				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,f.res_state,"
				// + "f.term_ssn,"
				+ "f.check_opr,f.check_date,"
				// + "f.sys_seq_num,f.revsal_ssn,"
				+ "f.retrivl_ref,f.misc_1,f.misc_2,"
				// + "f.misc_2,f.misc_3,f.misc_4 "
				+ " b.MCHT_NO||' - '||b.MCHT_NM as mcht_name "
				// +"(select trim(b.bank_no)||' - '||trim(d.brh_name) from   tbl_brh_info d where (b.bank_no)=trim(d.brh_id)  )as brhIdName "
				+ "  from tbl_amt_back_apply f left join TBL_MCHT_BASE_INF b on b.MCHT_NO=f.card_accp_id ";
		sql = sql + whereSql.toString();
		sql = sql + " order by f.apply_date desc ,f.apply_time desc";
		String countSql = "SELECT count(*) FROM tbl_amt_back_apply  f ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		for (Object[] objects : dataList) {
			if (objects[14] != null && !"".equals(objects[14])) {
				objects[14] = CommonFunction
						.moneyFormat(objects[14].toString());
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 事中风控查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRunRisk(int begin, HttpServletRequest request) {

		String queryTermId = request.getParameter("queryTermId");
		String queryMchtIdId = request.getParameter("queryMchtIdId");
		String queryTransTypeId = request.getParameter("queryTransTypeId");
		String queryCardTpId = request.getParameter("queryCardTpId");
		String queryMccId = request.getParameter("queryMccId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String ASTERISK = "*";
		
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryTermId)) {
			whereSql.append(" and a.TERM_ID ='").append(queryTermId).append("'");
		}
		if (StringUtil.isNotEmpty(queryMchtIdId) && !ASTERISK.equals(queryMchtIdId)) {
			whereSql.append(" and a.MCHT_ID ='").append(queryMchtIdId).append("'");
		}
		if (StringUtil.isNotEmpty(queryTransTypeId) && !ASTERISK.equals(queryTransTypeId)) {
			whereSql.append(" and a.trans_type ='").append(queryTransTypeId).append("'");
		}
		if (StringUtil.isNotEmpty(queryCardTpId) && !ASTERISK.equals(queryCardTpId)) {
			whereSql.append(" and a.card_type ='").append(queryCardTpId).append("'");
		}
		if (StringUtil.isNotEmpty(queryMccId)) {
			whereSql.append(" and b.MCC ='").append(queryMccId).append("'");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			startDate = startDate.replaceAll("-", "").substring(0,8);
			whereSql.append(" and substr(a.inst_date,1,8) >='").append(startDate).append("'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate = endDate.replaceAll("-", "").substring(0,8);
			whereSql.append(" and substr(a.inst_date,1,8) <='").append(endDate).append("'");
		}

		String sql = "select a.TERM_ID , a.MCHT_ID, b.MCHT_NM ,a.trans_type,c.txn_name, a.card_type,d.DESCR as mcc, a.amt_trans, a.inst_date,a.RISK_INFO_TRIGGERED "
					+ " from TBL_RISK_INFO_TRIGGERED a left join TBL_MCHT_BASE_INF b on trim(a.MCHT_ID)=b.MCHT_NO "
					+ " left join TBL_TXN_NAME c on trim(a.trans_type)=c.txn_num "
					+ " left join TBL_INF_MCHNT_TP d on b.MCC = d.MCHNT_TP "
					+ whereSql.toString() + " order by a.inst_date desc ";
		String countSql = " select count(*) from TBL_RISK_INFO_TRIGGERED a left join TBL_MCHT_BASE_INF b on trim(a.MCHT_ID)=b.MCHT_NO "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				objects[7] = CommonFunction.moneyFormat(objects[7].toString());
			}
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户别名维护查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblMchtName(int begin, HttpServletRequest request) {

		String queryAccpId = request.getParameter("queryAccpId");

		String queryCardTp = request.getParameter("queryCardTp");
		String queryTxnNum = request.getParameter("queryTxnNum");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryCardTp)) {
			whereSql.append(" and a.card_tp ='").append(queryCardTp)
					.append("'");
		}

		if (StringUtil.isNotEmpty(queryAccpId)) {
			whereSql.append(" and a.accp_id ='").append(queryAccpId)
					.append("'");
		}

		if (StringUtil.isNotEmpty(queryTxnNum)) {
			whereSql.append(" and a.txn_num ='").append(queryTxnNum)
					.append("'");
		}

		String sql = " select  "
				+ "a.accp_id||'-'||b.MCHT_NM,"
				+ "a.card_tp,"
				+ "a.txn_num||'-'||(select TXN_NAME from TBL_TXN_NAME where txn_num=a.txn_num) as txnName,"
				+ "a.mcht_nm,a.MSC1  "
				+ " from TBL_MCHT_NAME a  "
				+ " left join TBL_MCHT_BASE_INF b on trim(a.accp_id)=b.MCHT_NO "
				+ whereSql.toString() + " order by a.MSC1 desc ";
		String countSql = " select count(*) from TBL_MCHT_NAME a "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商服审核查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getAmtbackApplyres(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");

		String queryTxnName = request.getParameter("queryTxnName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryDateSettlmt8 = request.getParameter("queryDateSettlmt8");
		String queryKeyCup = request.getParameter("queryKeyCup");
		String dateSettlmt8 = request.getParameter("dateSettlmt8");
		String keyCup = request.getParameter("keyCup");
		String queryRetrivlRef = request.getParameter("queryRetrivlRef");
		String queryResState = request.getParameter("queryResState");
		StringBuffer whereSql = new StringBuffer(
				" where "
						+ "f.card_accp_id in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) "
						+ "and f.res_State in ('0')"
						+ "and f.apply_State in('1')");
		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND f.card_accp_id").append(" = ").append("'")
					.append(queryCardAccpId).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND f.card_accp_term_id").append(" like ")
					.append("'%").append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND f.pan").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(queryTxnName)) {
			whereSql.append(" AND f.txn_num").append(" = ").append("'")
					.append(queryTxnName).append("'");
		}

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND f.apply_date").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND f.apply_date").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryDateSettlmt8)) {
			whereSql.append(" AND f.date_settlmt_8").append(" = ").append("'")
					.append(queryDateSettlmt8).append("'");
		}
		if (isNotEmpty(queryKeyCup)) {
			whereSql.append(" AND trim(f.key_cup)").append(" = ").append("'")
					.append(queryKeyCup).append("'");
		}
		if (isNotEmpty(dateSettlmt8)) {
			whereSql.append(" AND f.date_settlmt_8").append(" = ").append("'")
					.append(dateSettlmt8).append("'");
		}
		if (isNotEmpty(keyCup)) {
			whereSql.append(" AND trim(f.key_cup)").append(" = ").append("'")
					.append(keyCup).append("'");
		}
		if (isNotEmpty(queryRetrivlRef)) {
			whereSql.append(" AND f.retrivl_ref").append(" like ").append("'%")
					.append(queryRetrivlRef).append("%'");
		}
		if (isNotEmpty(queryResState)) {
			whereSql.append(" AND f.res_state").append(" = ").append("'")
					.append(queryResState).append("'");
		}

//		String sql = "SELECT  f.date_settlmt_8,f.key_cup,f.apply_opr,f.apply_date,f.apply_time,f.apply_state,"
//				+ "f.inst_date,f.inst_time,f.pan,f.card_accp_term_id,f.card_accp_id,f.brh_id,f.txn_num,"
//				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_num_name,"
//				+ "f.txn_name,f.misc_3,"
//				// + "f.amt_trans,"
//				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,f.res_state,"
//				// + "f.term_ssn,"
//				+ "f.check_opr,f.check_date,"
//				// + "f.sys_seq_num,f.revsal_ssn,"
//				+ "f.retrivl_ref,f.misc_1,f.misc_2,"
//				// + "f.misc_2,f.misc_3,f.misc_4 "
//				+ " b.MCHT_NO||' - '||b.MCHT_NM as mcht_name "
//				// +"(select trim(b.bank_no)||' - '||trim(d.brh_name) from   tbl_brh_info d where (b.bank_no)=trim(d.brh_id)  )as brhIdName "
//				+ "  from (select * from TBL_AMT_BACK_APPLY_MIS a where not  exists (select * from TBL_AMT_BACK_APPLY_RES  b where a.APPLY_DATE=b.APPLY_DATE and a.DATE_SETTLMT_8=b.DATE_SETTLMT_8 and a.APPLY_OPR=b.APPLY_OPR and a.APPLY_TIME=b.APPLY_TIME and a.KEY_CUP=b.KEY_CUP)) f left join TBL_MCHT_BASE_INF b on b.MCHT_NO=f.card_accp_id ";
//		sql = sql + whereSql.toString();
//		sql = sql + " order by f.apply_date desc ,f.apply_time desc";
//		String countSql = "SELECT count(*) FROM (select * from TBL_AMT_BACK_APPLY_MIS a where not  exists (select * from TBL_AMT_BACK_APPLY_RES  b where a.APPLY_DATE=b.APPLY_DATE and a.DATE_SETTLMT_8=b.DATE_SETTLMT_8 and a.APPLY_OPR=b.APPLY_OPR and a.APPLY_TIME=b.APPLY_TIME and a.KEY_CUP=b.KEY_CUP)) f ";
//		countSql = countSql + whereSql.toString();
//		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
//				countSql);
//
//		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
//				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		String sqlMis = "SELECT  f.date_settlmt_8,f.key_cup,f.apply_opr,f.apply_date,f.apply_time,f.apply_state,"
				+ "f.inst_date,f.inst_time,f.pan,f.card_accp_term_id,f.card_accp_id,f.brh_id,f.txn_num,"
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_num_name,"
				+ "f.txn_name,f.misc_3,"
				// + "f.amt_trans,"
				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,f.res_state,"
				// + "f.term_ssn,"
				+ "f.check_opr,f.check_date,"
				// + "f.sys_seq_num,f.revsal_ssn,"
				+ "f.retrivl_ref,f.misc_1,f.misc_2,"
				// + "f.misc_2,f.misc_3,f.misc_4 "
				+ " b.MCHT_NO||' - '||b.MCHT_NM as mcht_name "
				// +"(select trim(b.bank_no)||' - '||trim(d.brh_name) from   tbl_brh_info d where (b.bank_no)=trim(d.brh_id)  )as brhIdName "
				+ "  from (select * from TBL_AMT_BACK_APPLY_MIS) f left join TBL_MCHT_BASE_INF b on b.MCHT_NO=f.card_accp_id ";
		sqlMis = sqlMis + whereSql.toString();
		sqlMis = sqlMis + " order by f.apply_date desc ,f.apply_time desc";
		String countSqlMis = "SELECT count(*) FROM (select * from TBL_AMT_BACK_APPLY_MIS) f ";
		countSqlMis = countSqlMis + whereSql.toString();
		String countMis = CommonFunction.getCommMisQueryDAO().findCountBySQLQuery(
				countSqlMis);

		List<Object[]> dataListMis = CommonFunction.getCommMisQueryDAO()
				.findBySQLQuery(sqlMis, begin, Constants.QUERY_RECORD_COUNT);
		
		String sqlRes = "select date_settlmt_8,key_cup,apply_opr,apply_date,apply_time from TBL_AMT_BACK_APPLY_RES";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlRes);
		
		int countMisInt = Integer.parseInt(countMis);
		if (countMisInt == 0){
			ret[0] = dataListMis;
			ret[1] = countMis;
		}else{
			if (dataList == null || dataList.size() ==0){
				for (Object[] objects : dataListMis) {
					if (objects[16] != null && !"".equals(objects[16])) {
						objects[16] = CommonFunction
								.moneyFormat(objects[16].toString());
					}
				}
				ret[0] = dataListMis;
				ret[1] = countMis;
			}else{
				//同时有数据时
				List<Object[]> resultMis = new ArrayList<Object[]>();
				boolean sameFlag = false;
				//按着主键去掉mis中的重复数据
				for (Object[] objectsMis : dataListMis) {
					sameFlag = false;
					for (Object[] objects : dataList) {
						//对比主键,res内有的数据,mis数据不再显示
						if (objects[0].equals(objectsMis[0]) && objects[1].equals(objectsMis[1])
								&&objects[2].equals(objectsMis[2]) &&objects[3].equals(objectsMis[3])
								&&objects[4].equals(objectsMis[4])) {
							sameFlag = true;
							break;
						}
					}
					if (!sameFlag){
						resultMis.add(objectsMis);
					}
				}
				for (Object[] objects : resultMis) {
					if (objects[16] != null && !"".equals(objects[16])) {
						objects[16] = CommonFunction
								.moneyFormat(objects[16].toString());
					}
				}
				ret[0] = resultMis;
				ret[1] = resultMis.size();
			}
		}
		return ret;
	}

	/**
	 * 商服退货申请查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getAmtbackApplyquery(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");

		String queryTxnName = request.getParameter("queryTxnName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryDateSettlmt8 = request.getParameter("queryDateSettlmt8");
		String queryKeyCup = request.getParameter("queryKeyCup");
		String dateSettlmt8 = request.getParameter("dateSettlmt8");
		String keyCup = request.getParameter("keyCup");
		String queryRetrivlRef = request.getParameter("queryRetrivlRef");
		String queryResState = request.getParameter("queryResState");
		StringBuffer whereSql = new StringBuffer(
				" where "
						+ "f.card_accp_id in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND f.card_accp_id").append(" = ").append("'")
					.append(queryCardAccpId).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND f.card_accp_term_id").append(" like ")
					.append("'%").append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND f.pan").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(queryTxnName)) {
			whereSql.append(" AND f.txn_num").append(" = ").append("'")
					.append(queryTxnName).append("'");
		}

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND f.apply_date").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND f.apply_date").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryDateSettlmt8)) {
			whereSql.append(" AND f.date_settlmt_8").append(" = ").append("'")
					.append(queryDateSettlmt8).append("'");
		}
		if (isNotEmpty(queryKeyCup)) {
			whereSql.append(" AND trim(f.key_cup)").append(" = ").append("'")
					.append(queryKeyCup).append("'");
		}
		if (isNotEmpty(dateSettlmt8)) {
			whereSql.append(" AND f.date_settlmt_8").append(" = ").append("'")
					.append(dateSettlmt8).append("'");
		}
		if (isNotEmpty(keyCup)) {
			whereSql.append(" AND trim(f.key_cup)").append(" = ").append("'")
					.append(keyCup).append("'");
		}
		if (isNotEmpty(queryRetrivlRef)) {
			whereSql.append(" AND f.retrivl_ref").append(" like ").append("'%")
					.append(queryRetrivlRef).append("%'");
		}
		if (isNotEmpty(queryResState)) {
			whereSql.append(" AND f.res_state").append(" = ").append("'")
					.append(queryResState).append("'");
		}

//		String sql = "SELECT  f.date_settlmt_8,f.key_cup,f.apply_opr,f.apply_date,f.apply_time,f.apply_state,"
//				+ "f.inst_date,f.inst_time,f.pan,f.card_accp_term_id,f.card_accp_id,f.brh_id,f.txn_num,"
//				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_num_name,"
//				+ "f.txn_name,f.misc_3,"
//				// + "f.amt_trans,"
//				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,f.res_state,"
//				// + "f.term_ssn,"
//				+ "f.check_opr,f.check_date,"
//				// + "f.sys_seq_num,f.revsal_ssn,"
//				+ "f.retrivl_ref,f.misc_1,f.misc_2,"
//				// + "f.misc_2,f.misc_3,f.misc_4 "
//				+ " b.MCHT_NO||' - '||b.MCHT_NM as mcht_name "
//				// +"(select trim(b.bank_no)||' - '||trim(d.brh_name) from   tbl_brh_info d where (b.bank_no)=trim(d.brh_id)  )as brhIdName "
//				+ "  from (select * from TBL_AMT_BACK_APPLY_RES  union select * from TBL_AMT_BACK_APPLY_MIS a where not  exists (select * from TBL_AMT_BACK_APPLY_RES  b where a.APPLY_DATE=b.APPLY_DATE and a.DATE_SETTLMT_8=b.DATE_SETTLMT_8 and a.APPLY_OPR=b.APPLY_OPR and a.APPLY_TIME=b.APPLY_TIME and a.KEY_CUP=b.KEY_CUP)) f left join TBL_MCHT_BASE_INF b on b.MCHT_NO=f.card_accp_id ";
//		sql = sql + whereSql.toString();
//		sql = sql + " order by f.apply_date desc ,f.apply_time desc";
//		String countSql = "SELECT count(*) FROM (select * from TBL_AMT_BACK_APPLY_RES  union select * from TBL_AMT_BACK_APPLY_MIS a where not  exists (select * from TBL_AMT_BACK_APPLY_RES  b where a.APPLY_DATE=b.APPLY_DATE and a.DATE_SETTLMT_8=b.DATE_SETTLMT_8 and a.APPLY_OPR=b.APPLY_OPR and a.APPLY_TIME=b.APPLY_TIME and a.KEY_CUP=b.KEY_CUP))  f ";
//		countSql = countSql + whereSql.toString();
//		
//		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
//
//		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
//		.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		String sql = "SELECT  f.date_settlmt_8,f.key_cup,f.apply_opr,f.apply_date,f.apply_time,f.apply_state,"
				+ "f.inst_date,f.inst_time,f.pan,f.card_accp_term_id,f.card_accp_id,f.brh_id,f.txn_num,"
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_num_name,"
				+ "f.txn_name,f.misc_3,"
				// + "f.amt_trans,"
				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,f.res_state,"
				// + "f.term_ssn,"
				+ "f.check_opr,f.check_date,"
				// + "f.sys_seq_num,f.revsal_ssn,"
				+ "f.retrivl_ref,f.misc_1,f.misc_2,"
				// + "f.misc_2,f.misc_3,f.misc_4 "
				+ " b.MCHT_NO||' - '||b.MCHT_NM as mcht_name "
				// +"(select trim(b.bank_no)||' - '||trim(d.brh_name) from   tbl_brh_info d where (b.bank_no)=trim(d.brh_id)  )as brhIdName "
				+ "  from (select * from TBL_AMT_BACK_APPLY_RES) f left join TBL_MCHT_BASE_INF b on b.MCHT_NO=f.card_accp_id ";
		sql = sql + whereSql.toString();
		sql = sql + " order by f.apply_date desc ,f.apply_time desc";
		String countSql = "SELECT count(*) FROM (select * from TBL_AMT_BACK_APPLY_RES) f ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		
		//mis数据库
		String sqlMis = "SELECT  f.date_settlmt_8,f.key_cup,f.apply_opr,f.apply_date,f.apply_time,f.apply_state,"
				+ "f.inst_date,f.inst_time,f.pan,f.card_accp_term_id,f.card_accp_id,f.brh_id,f.txn_num,"
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_num_name,"
				+ "f.txn_name,f.misc_3,"
				// + "f.amt_trans,"
				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt,f.res_state,"
				// + "f.term_ssn,"
				+ "f.check_opr,f.check_date,"
				// + "f.sys_seq_num,f.revsal_ssn,"
				+ "f.retrivl_ref,f.misc_1,f.misc_2,"
				// + "f.misc_2,f.misc_3,f.misc_4 "
				+ " b.MCHT_NO||' - '||b.MCHT_NM as mcht_name "
				// +"(select trim(b.bank_no)||' - '||trim(d.brh_name) from   tbl_brh_info d where (b.bank_no)=trim(d.brh_id)  )as brhIdName "
				+ "  from (select * from TBL_AMT_BACK_APPLY_MIS) f left join TBL_MCHT_BASE_INF b on b.MCHT_NO=f.card_accp_id ";
		sqlMis = sqlMis + whereSql.toString();
		sqlMis = sqlMis + " order by f.apply_date desc ,f.apply_time desc";
		String countSqlMis = "SELECT count(*) FROM (select * from TBL_AMT_BACK_APPLY_MIS)  f ";
		countSqlMis = countSqlMis + whereSql.toString();
		String countMis = CommonFunction.getCommMisQueryDAO().findCountBySQLQuery(
				countSqlMis);

		List<Object[]> dataListMis = CommonFunction.getCommMisQueryDAO()
				.findBySQLQuery(sqlMis, begin, Constants.QUERY_RECORD_COUNT);
		
		int countInt = Integer.parseInt(count);
		int countMisInt = Integer.parseInt(countMis);
		//res没有数据
		if (countInt == 0){
			for (Object[] objects : dataListMis) {
				if (objects[16] != null && !"".equals(objects[16])) {
					objects[16] = CommonFunction
							.moneyFormat(objects[16].toString());
				}
			}
			ret[0] = dataListMis;
			ret[1] = countMis;
		}else{
			//mis没有数据
			if (countMisInt == 0){
				for (Object[] objects : dataList) {
					if (objects[16] != null && !"".equals(objects[16])) {
						objects[16] = CommonFunction
								.moneyFormat(objects[16].toString());
					}
				}
				ret[0] = dataList;
				ret[1] = count;
			}else{
				//同时有数据时
				List<Object[]> resultMis = new ArrayList<Object[]>();
				boolean sameFlag = false;
				//按着主键去掉mis中的重复数据
				for (Object[] objectsMis : dataListMis) {
					sameFlag = false;
					for (Object[] objects : dataList) {
						//对比主键,res内有的数据,mis数据不再显示
						if (objects[0].equals(objectsMis[0]) && objects[1].equals(objectsMis[1])
								&&objects[2].equals(objectsMis[2]) &&objects[3].equals(objectsMis[3])
								&&objects[4].equals(objectsMis[4])) {
							sameFlag = true;
							break;
						}
					}
					if (!sameFlag){
						resultMis.add(objectsMis);
					}
				}
				//不重复的mis数据
				dataList.addAll(resultMis);
				for (Object[] objects : dataList) {
					if (objects[16] != null && !"".equals(objects[16])) {
						objects[16] = CommonFunction
								.moneyFormat(objects[16].toString());
					}
				}
				ret[0] = dataList;
				ret[1] = dataList.size();
			}
		}
//		for (Object[] objects : dataList) {
//			if (objects[16] != null && !"".equals(objects[16])) {
//				objects[16] = CommonFunction
//						.moneyFormat(objects[16].toString());
//			}
//		}

//		ret[0] = dataList;
//		ret[1] = count;
		return ret;
	}

	/**
	 * 结算单信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getMchtInfileDtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String mchtNo = request.getParameter("mchtNo");
		String brhId = request.getParameter("brhId");

		StringBuffer whereSql = new StringBuffer(
				" where  "
						+ "MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(mchtNo)) {
			// whereSql.append(" AND MCHT_NO").append(" = ").append("'").append(mchtNo).append("'");
			// whereSql.append(" AND MCHT_NO").append(" in ").append(CommonFunction.getBelowMchtByMchtNo(mchtNo));
			whereSql.append(
					" AND MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =")
					.append("'")
					.append(mchtNo)
					.append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
		}
		if (isNotEmpty(brhId)) {
			// whereSql.append(" AND trim(BRH_CODE)").append(" = ").append("'").append(brhId).append("'");
			// whereSql.append(" AND trim(BRH_CODE)").append(" in ").append(CommonFunction.getBelowBrhInfo(CommonFunction.getBelowBrhMap2(brhId)));
			whereSql.append(" and trim(BRH_CODE)  in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ brhId.trim()
					+ "' connect by prior  BRH_ID = UP_BRH_ID   ) ");
		}

		String sql = "SELECT DATE_SETTLMT,MCHT_NO,MCHT_NO||'-'||MCHT_NM,"
				+ "(select b.BRH_ID||'-'||b.BRH_NAME from TBL_BRH_INFO b where trim(b.BRH_ID)=trim(a.BRH_CODE)) as bankNoName,"
				+ "START_DATE,END_DATE,TXN_AMT,SETTLE_AMT,SETTLE_FEE1,"
				+ "a.CHANNEL_CD||(select '-'||d.FIRST_BRH_NAME from  TBL_FIRST_BRH_DEST_ID d where a.CHANNEL_CD=substr(d.FIRST_BRH_ID,3,2)) as channel_name "
				+ "FROM TBL_MCHNT_INFILE_DTL a ";
		sql = sql + whereSql.toString();
		sql = sql + " ORDER BY DATE_SETTLMT DESC";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHNT_INFILE_DTL ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {

			for (Object[] objects : dataList) {
				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = CommonFunction.dateFormat(objects[4]
							.toString());
				}
				if (objects[5] != null && !"".equals(objects[5])) {
					objects[5] = CommonFunction.dateFormat(objects[5]
							.toString());
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6]
							.toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6]
							.toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7]
							.toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7]
							.toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8]
							.toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8]
							.toString());
				}
			}

		}

		if (dataList.size() > 0) {
			List<Object[]> amtList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql);
			Double amt6 = 0.00;
			Double amt7 = 0.00;
			Double amt8 = 0.00;
			Object[] obj = new Object[dataList.get(0).length];

			for (Object[] objects : amtList) {

				if (objects[6] != null && !"".equals(objects[6])) {
					amt6 += Double.parseDouble(objects[6].toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					amt7 += Double.parseDouble(objects[7].toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					amt8 += Double.parseDouble(objects[8].toString());
				}
			}

			obj[2] = "<font color='red'>总计</font>";
			;
			obj[6] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt6)) + "</font>";
			obj[7] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt7)) + "</font>";
			obj[8] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt8)) + "</font>";

			dataList.add(obj);
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 结算单详细信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getDetailMchtInfileDtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String dateSettlmt = request.getParameter("dateSettlmt");
		String mchtNo = request.getParameter("mchtNo");

		StringBuffer whereSql = new StringBuffer(
				" where  "
						+ "a.MCHT_CD in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(dateSettlmt)) {
			whereSql.append(" AND a.DATE_SETTLMT ").append(" = ").append("'")
					.append(dateSettlmt).append("'");
		}
		// if(isNotEmpty(mchtNo)) {
		whereSql.append(" AND a.mcht_cd ").append(" = ").append("'")
				.append(mchtNo).append("'");
		// }

		String sql = "SELECT a.trans_date,a.trans_date_time,a.pan,"
				+ " (select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =a.txn_num ) as txn_name,"
				+ "a.term_id,a.term_ssn,"
				+ "abs(a.MCHT_AT_C-a.MCHT_AT_D) as txn_amt,"
				+ "(a.MCHT_AT_C-a.MCHT_AT_D+MCHT_FEE_C-MCHT_FEE_D) as settle_amt,"
				+ "abs(a.MCHT_FEE_C-a.MCHT_FEE_D) as settle_fee "
				+ " from TBL_ALGO_DTL a ";
		sql = sql + whereSql.toString();
		sql = sql + " order by trans_date desc,trans_date_time desc ";
		String countSql = "SELECT count(*) FROM TBL_ALGO_DTL  a ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[2] != null && !"".equals(objects[2])) {
					String str = objects[2].toString();
					objects[2] = str.substring(0, 6) + str.substring(6, 10)
							+ str.substring(10);
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6]
							.toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6]
							.toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7]
							.toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7]
							.toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8]
							.toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8]
							.toString());
				}
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 新 结算单信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getNewMchtInfileDtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String mchtNo = request.getParameter("mchtNo");
		String brhId = request.getParameter("brhId");

		StringBuffer whereSql = new StringBuffer(
				" where  "
						+ "MCHT_ID in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_STLM ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_STLM").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(mchtNo)) {

			whereSql.append(
					" AND MCHT_ID  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =")
					.append("'")
					.append(mchtNo)
					.append("' connect by prior  trim(MCHT_ID) = MCHT_GROUP_ID ) ");
		}
		if (isNotEmpty(brhId)) {

			whereSql.append(" and trim(BRH_ID)  in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ brhId.trim()
					+ "' connect by prior  BRH_ID = UP_BRH_ID   ) ");
		}

		String sql = "SELECT DATE_STLM,MCHT_ID,MCHT_ID||'-'||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF where MCHT_NO=MCHT_ID)MCHT_NM,"
				+ "(select b.BRH_ID||'-'||b.BRH_NAME from TBL_BRH_INFO b where trim(b.BRH_ID)=trim(a.BRH_ID)) as bankNoName,"
				+ "STLM_AMT_TRANS,STLM_AMT,STLM_AMT_FEE,"
				+ "substr(a.INST_CODE,3,2)||(select '-'||d.FIRST_BRH_NAME from  TBL_FIRST_BRH_DEST_ID d where a.INST_CODE=d.FIRST_BRH_ID) as channel_name ,a.INST_CODE"
				+ " FROM TBL_MCHT_SETTLE_DTL a ";
		sql = sql + whereSql.toString();
		sql = sql + " ORDER BY DATE_STLM DESC";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_SETTLE_DTL ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {

			for (Object[] objects : dataList) {

				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = df.format(Double.parseDouble(objects[4]
							.toString()));
					objects[4] = CommonFunction.moneyFormat(objects[4]
							.toString());
				}
				if (objects[5] != null && !"".equals(objects[5])) {
					objects[5] = df.format(Double.parseDouble(objects[5]
							.toString()));
					objects[5] = CommonFunction.moneyFormat(objects[5]
							.toString());
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6]
							.toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6]
							.toString());
				}
			}

		}

		if (dataList.size() > 0) {
			List<Object[]> amtList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql);
			Double amt6 = 0.00;
			Double amt7 = 0.00;
			Double amt8 = 0.00;
			Object[] obj = new Object[dataList.get(0).length];

			for (Object[] objects : amtList) {

				if (objects[4] != null && !"".equals(objects[4])) {
					amt6 += Double.parseDouble(objects[4].toString());
				}
				if (objects[5] != null && !"".equals(objects[5])) {
					amt7 += Double.parseDouble(objects[5].toString());
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					amt8 += Double.parseDouble(objects[6].toString());
				}
			}

			obj[2] = "<font color='red'>总计</font>";
			;
			obj[4] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt6)) + "</font>";
			obj[5] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt7)) + "</font>";
			obj[6] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt8)) + "</font>";

			dataList.add(obj);
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 新结算单详细信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getNewDetailMchtInfileDtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String dateSettlmt = request.getParameter("dateSettlmt");
		String mchtNo = request.getParameter("mchtNo");
		String instCode = request.getParameter("instCode");

		StringBuffer whereSql = new StringBuffer(
				" where  "
						+ "a.MCHT_ID in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(dateSettlmt)) {
			whereSql.append(" AND a.DATE_STLM ").append(" = ").append("'")
					.append(dateSettlmt).append("'");
		}
		// if(isNotEmpty(mchtNo)) {
		whereSql.append(" AND a.MCHT_ID ").append(" = ").append("'")
				.append(mchtNo).append("'");
		if (isNotEmpty(instCode)) {
			whereSql.append(" AND a.INST_CODE ").append(" = ").append("'")
					.append(instCode).append("'");
		}
		// }

		String sql = "SELECT a.TXN_DATE,a.TXN_TIME,a.pan,"
				+ " (select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =a.txn_num ) as txn_name,"
				+ "a.TERM_ID,a.TERM_SN,"
				+ "abs(a.MCHT_AMT_CR-a.MCHT_AMT_DB) as txn_amt,"
				+ "(a.MCHT_AMT_CR-a.MCHT_AMT_DB+MCHT_FEE_CR-MCHT_FEE_DB) as settle_amt,"
				+ "abs(a.MCHT_FEE_CR-a.MCHT_FEE_DB) as settle_fee "
				+ " from TBL_CLEAR_DTL a ";
		sql = sql + whereSql.toString();
		sql = sql + " order by TXN_DATE desc,TXN_TIME desc ";
		String countSql = "SELECT count(*) FROM TBL_CLEAR_DTL  a ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[2] != null && !"".equals(objects[2])) {
					String str = objects[2].toString();
					objects[2] = str.substring(0, 6) + str.substring(6, 10)
							+ str.substring(10);
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6]
							.toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6]
							.toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7]
							.toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7]
							.toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8]
							.toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8]
							.toString());
				}
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户批量导入回执信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-26下午09:40:21
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtBlukImpRecInf(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		StringBuffer whereSql = new StringBuffer();
		whereSql.append("  where MISC1 = '")
				.append(TblMchntInfoConstants.BLUK_IMP_MCHNT).append("'");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND substr(BLUK_DATE,0,8) ").append(" >= ")
					.append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND substr(BLUK_DATE,0,8)").append(" <= ")
					.append("'").append(endDate).append("'");
		}

		String sql = "SELECT BATCH_NO, substr(BLUK_DATE,0,8), substr(BLUK_DATE,9,6), "
				+ "(select b.BRH_ID||'-'||b.BRH_NAME from TBL_BRH_INFO b where trim(b.BRH_ID)=trim(a.BRH_ID)) as bankNoName, "
				+ "OPR_ID, BLUK_FILE_NAME, BLUK_MCHN_NUM "
				+ " FROM TBL_BLUK_IMP_RET_INF a " + whereSql.toString();
		sql = sql + " order by BLUK_DATE desc ";
		String countSql = "SELECT COUNT(*) FROM TBL_BLUK_IMP_RET_INF "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}


	/**
	 * 合作伙伴差错明细
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getBrhErrInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String querybrhNo = request.getParameter("querybrhNo");
		String queryTradeType = request.getParameter("queryTradeType");
		String queryAproveStatus = request.getParameter("queryAproveStatus");
		String queryPostStatus = request.getParameter("queryPostStatus");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");

		if (isNotEmpty(querybrhNo)) {
			whereSql.append(
					" AND brh_no = ")
					.append("'")
					.append(querybrhNo)
					.append("'");
		}
		if (isNotEmpty(queryTradeType)) {
			whereSql.append(
					" AND trade_type=")
					.append("'")
					.append(queryTradeType)
					.append("'");
		}
		
		if (isNotEmpty(queryAproveStatus)) {
			whereSql.append(
					" AND aprove_status=")
					.append("'")
					.append(queryAproveStatus)
					.append("'");
		}
		
		if (isNotEmpty(queryPostStatus)) {
			whereSql.append(
					" AND post_status=")
					.append("'")
					.append(queryPostStatus)
					.append("'");
		}
		
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND substr(approve_time,0,8) ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND substr(approve_time,0,8)").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		
		String sql = "SELECT uuid,b.CREATE_NEW_NO||'-'||BRH_NAME as brh_no,trade_type,to_char(money,'9999999990.00'),aprove_status,aprove_opr,approve_time,approve_advice,post_status,post_time,vc_tran_no,reserver,crt_opr FROM TBL_BRH_ERR_ADJUST a";
		sql += " LEFT JOIN TBL_BRH_INFO b ON b.brh_id = a.brh_no";
		sql = sql + whereSql.toString();
		sql = sql + " order by aprove_status,post_time desc,approve_time desc";
		String countSql = "SELECT count(*) FROM TBL_BRH_ERR_ADJUST a ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

//		DecimalFormat df = new DecimalFormat("0.00");
//		if (dataList.size() > 0) {
//			for (Object[] objects : dataList) {
//				if (objects[10] != null && !"".equals(objects[10])) {
//					objects[10] = df.format(Double.parseDouble(objects[10]
//							.toString()));
//					objects[10] = CommonFunction.moneyFormat(objects[10]
//							.toString());
//				}
//				if (objects[11] != null && !"".equals(objects[11])) {
//					objects[11] = df.format(Double.parseDouble(objects[11]
//							.toString()));
//					objects[11] = CommonFunction.moneyFormat(objects[11]
//							.toString());
//				}
//				if (objects[12] != null && !"".equals(objects[12])) {
//					objects[12] = df.format(Double.parseDouble(objects[12]
//							.toString()));
//					objects[12] = CommonFunction.moneyFormat(objects[12]
//							.toString());
//				}
//			}
//		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 差错明细查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getErrDtlInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		String queryInstCode = request.getParameter("queryInstCode");

		StringBuffer whereSql = new StringBuffer(
				" where (CARD_ACCP_ID is null or CARD_ACCP_ID in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " )) ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			whereSql.append(
					" AND CARD_ACCP_ID  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =")
					.append("'")
					.append(queryMchtNoNm)
					.append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
		}
		if (isNotEmpty(queryInstCode)) {
			whereSql.append(" AND a.INST_CODE").append(" = ").append("'")
					.append(queryInstCode).append("'");
		}

		String sql = "SELECT DATE_SETTLMT,a.INST_CODE||' - '||e.FIRST_BRH_NAME,TRANS_DATE_TIME,"
				+ "(CASE WHEN b.MCHT_NO IS NULL THEN '' ELSE b.MCHT_NO||' - '||b.MCHT_NM END) AS mchtNoNm,"
				+ "(CASE WHEN c.BRH_ID IS NULL THEN '' ELSE c.BRH_ID||' - '||c.BRH_NAME END) AS bankNoName,"
				+ "CARD_ACCP_TERM_ID,d.TXN_NAME,SYS_SEQ_NUM,POSP_PAN,INST_PAN,"
				+ "TO_NUMBER(NVL(trim(POSP_AMT_TRANS),0))/100,TO_NUMBER(NVL(trim(INST_AMT_TRANS),0))/100,"
				+ "TO_NUMBER(NVL(trim(FEE_DEBIT),0))/100-TO_NUMBER(NVL(trim(FEE_CREDIT),0))/100,STLM_FLAG "
				+ "FROM BTH_ERR_DTL a "
				+ "LEFT JOIN TBL_MCHT_BASE_INF b ON b.MCHT_NO = a.CARD_ACCP_ID "
				+ "LEFT JOIN TBL_BRH_INFO c ON c.BRH_ID = b.BANK_NO "
				+ "LEFT JOIN TBL_TXN_NAME d ON d.TXN_NUM = a.TXN_NUM "
				+ "LEFT JOIN TBL_FIRST_BRH_DEST_ID e ON e.DEST_ID = a.INST_CODE ";
		sql = sql + whereSql.toString();
		sql = sql + " order by DATE_SETTLMT desc";
		String countSql = "SELECT count(*) FROM BTH_ERR_DTL a ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[10] != null && !"".equals(objects[10])) {
					objects[10] = df.format(Double.parseDouble(objects[10]
							.toString()));
					objects[10] = CommonFunction.moneyFormat(objects[10]
							.toString());
				}
				if (objects[11] != null && !"".equals(objects[11])) {
					objects[11] = df.format(Double.parseDouble(objects[11]
							.toString()));
					objects[11] = CommonFunction.moneyFormat(objects[11]
							.toString());
				}
				if (objects[12] != null && !"".equals(objects[12])) {
					objects[12] = df.format(Double.parseDouble(objects[12]
							.toString()));
					objects[12] = CommonFunction.moneyFormat(objects[12]
							.toString());
				}
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	public static Object[] getInstBalanceAjust(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		//清算查询日期
		String startDateSettlmt = request.getParameter("startDateSettlmt");
		String endDateSettlmt = request.getParameter("endDateSettlmt");
		//合作伙伴号
		String queryBrhId = request.getParameter("queryBrhId");
		//通道机构号
		String queryInstCode = request.getParameter("queryInstCode");
		//商户号
		String queryMchtId = request.getParameter("queryMchtId");
		//流水号
		String querySeqNum = request.getParameter("querySeqNum");
		//参考号
		String queryRefNum = request.getParameter("queryRefNum");
		//交易查询日期
		String startDateTrans = request.getParameter("startDateTrans");
		String endDateTrans = request.getParameter("endDateTrans");

		StringBuffer whereSql = new StringBuffer(" where 1=1");
		
		Map<String, String> params = new HashMap<String,String>();
		if (isNotEmpty(startDateSettlmt)) {
			whereSql.append(" AND A.DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDateSettlmt).append("'");
		}
		if (isNotEmpty(endDateSettlmt)) {
			whereSql.append(" AND A.DATE_SETTLMT").append(" <= ").append("'")
					.append(endDateSettlmt).append("'");
		}
		if (isNotEmpty(queryInstCode)) {
			whereSql.append(" AND A.INST_CODE = :INST_CODE ");
			params.put("INST_CODE", queryInstCode);
		}
		if (isNotEmpty(queryBrhId)) {
			whereSql.append(" AND A.BRH_ID = :BRH_ID ");
			params.put("BRH_ID", queryBrhId);
		}
		if (isNotEmpty(queryMchtId)) {
			whereSql.append(" AND A.MCHT_ID = :MCHT_ID ");
			params.put("MCHT_ID", queryMchtId);
		}
		if (isNotEmpty(querySeqNum)) {
			whereSql.append(" AND A.SYS_SEQ_NUM like '%' || :SEQ_NUM || '%'");
			params.put("SEQ_NUM", querySeqNum);
		}
		if (isNotEmpty(queryRefNum)) {
			whereSql.append(" AND A.RETRIVL_REF like '%' || :REF_NUM || '%'");
			params.put("REF_NUM", queryRefNum);
		}
		if (isNotEmpty(startDateTrans)) {
			whereSql.append(" AND A.TRANS_DATE ").append(" >= ").append("'")
					.append(startDateTrans).append("'");
		}
		if (isNotEmpty(endDateTrans)) {
			whereSql.append(" AND A.TRANS_DATE").append(" <= ").append("'")
					.append(endDateTrans).append("'");
		}
		StringBuffer selectBuf = new StringBuffer("select A.DATE_SETTLMT,");
		selectBuf.append(" A.MCHT_ID,    ");
		selectBuf.append(" C.VALUE,  ");
		selectBuf.append(" A.INST_CODE,   ");
		selectBuf.append(" A.ACCT_CURR,   ");
		selectBuf.append(" A.SETTL_DATE,   ");
		selectBuf.append(" A.BRH_ID,      ");
		selectBuf.append(" A.TRANS_DATE,   ");
		selectBuf.append(" A.SYS_SEQ_NUM,   ");
		selectBuf.append(" D.TXN_NAME,      ");
		selectBuf.append(" A.RETRIVL_REF,  ");
		selectBuf.append(" A.REC_UPD_USR_ID,  ");
		selectBuf.append(" A.REC_CRT_TS  ");
		
		StringBuffer fromBuf = new StringBuffer(" from TBL_INST_BALANCE_AJUST A ");
		fromBuf.append(" LEFT JOIN TBL_TXN_NAME d ON d.TXN_NUM = a.TRANS_TYPE ");
		fromBuf.append(" LEFT JOIN (select key,value from cst_sys_param c where c.owner = 'MCHNT_ATTR') c ON c.key = a.MCHT_TYPE ");

		String sql = selectBuf.toString() + fromBuf.toString() + whereSql.toString();
		sql += " order by A.REC_CRT_TS desc";
		
		String countSql = "SELECT count(1) " + fromBuf.toString() + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql, params);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT, params);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 差错交易调账查询
	 * 
	 * @param begin 页开始记录
	 * @param request 
	 * @return 查询结果集
	 */
	public static Object[] getBthChkInfo(int begin, HttpServletRequest request) {
		String checkFlag = request.getParameter("checkFlag");
		Object[] datas = null;
		if ("0".equals(checkFlag)) {
			datas = getBthChkOKInfo(begin, request);//对平
		} else if ("1".equals(checkFlag)) {
			datas = getBthChkErrInfo(begin, request);//不平
		} else {
			datas = getBthChkSuspsInfo(begin, request);//存疑
		}
		
		List<Object[]> dataList = (List<Object[]>)datas[0];
		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[0] != null && !"".equals(objects[0])) {
					objects[0] = CommonFunction.dateFormat(objects[0]
							.toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7]
							.toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7]
							.toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8]
							.toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8]
							.toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					objects[9] = df.format(Double.parseDouble(objects[9]
							.toString()));
					objects[9] = CommonFunction.moneyFormat(objects[9]
							.toString());
				}
				if (objects[10] != null && !"".equals(objects[10])) {
					objects[10] = df.format(Double.parseDouble(objects[10]
							.toString()));
					objects[10] = CommonFunction.moneyFormat(objects[10]
							.toString());
				}
				if (objects[11] != null && !"".equals(objects[11])) {
					objects[11] = CommonFunction.dateFormat(objects[11]
							.toString());
				}
				if (objects[12] != null && !"".equals(objects[12])) {
					objects[12] = CommonFunction.dateFormat(objects[12]
							.toString());
				}
				if (objects[13] != null && !"".equals(objects[13])) {
					objects[13] = CommonFunction.dateFormat(objects[13]
							.toString());
				}
				if (objects[14] != null && !"".equals(objects[14])) {
					objects[14] = CommonFunction.dateFormat(objects[14]
							.toString());
				}
				//20160324 guoyu delete 调账人名不用格式化
//				if (objects[23] != null && !"".equals(objects[23])) {
//					objects[23] = CommonFunction.dateFormat(objects[23]
//							.toString());
//				}
			}
		}
		
		return datas;
	}
	
	private static Object[] getBthChkOKInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		int queryCount = Constants.QUERY_RECORD_COUNT;
		Integer custQueryCount = (Integer)request.getAttribute("CUSTOMER_QUERY_RECORD_COUNT");
		if (custQueryCount != null) {
			queryCount = custQueryCount;
		}
		//清算查询日期
		String startDateSettlmt = request.getParameter("startDateSettlmt");
		String endDateSettlmt = request.getParameter("endDateSettlmt");
		//合作伙伴号
		String queryBrhId = request.getParameter("queryBrhId");
		//商户号
		String queryMchtId = request.getParameter("queryMchtId");
		//流水号
		String querySeqNum = request.getParameter("querySeqNum");
		//参考号
		String queryRefNum = request.getParameter("queryRefNum");
		//卡号
		String queryCardNum = request.getParameter("queryCardNum");
		
		//交易查询日期
		String startDateTrans = request.getParameter("startDateTrans");
		String endDateTrans = request.getParameter("endDateTrans");

		StringBuffer whereSql = new StringBuffer(" where 1=1");
		
		Map<String, String> params = new HashMap<String,String>();
		if (isNotEmpty(startDateSettlmt)) {
			whereSql.append(" AND A.DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDateSettlmt).append("'");
		}
		if (isNotEmpty(endDateSettlmt)) {
			whereSql.append(" AND A.DATE_SETTLMT").append(" <= ").append("'")
					.append(endDateSettlmt).append("'");
		}
		if (isNotEmpty(queryBrhId)) {
			whereSql.append(" AND C.BRH_ID = :BRH_ID ");
			params.put("BRH_ID", queryBrhId);
		}
		if (isNotEmpty(queryMchtId)) {
			whereSql.append(" AND B.MCHT_NO = :MCHT_ID ");
			params.put("MCHT_ID", queryMchtId);
		}
		if (isNotEmpty(querySeqNum)) {
			whereSql.append(" AND A.TXN_SSN like '%' || :SEQ_NUM || '%'");
			params.put("SEQ_NUM", querySeqNum);
		}
		if (isNotEmpty(queryRefNum)) {
//			whereSql.append(" AND D.RETRIVL_REF like '%' || :REF_NUM || '%'");
			whereSql.append(" AND (A.INST_RETRIVL_REF like '%' || :REF_NUM || '%'");
			whereSql.append(" OR A.RETRIVL_REF like '%' || :REF_NUM || '%')");
			params.put("REF_NUM", queryRefNum);
		}
		if (isNotEmpty(queryCardNum)) {
			whereSql.append(" AND (A.PAN like '%' || :PAN || '%')");
			params.put("PAN", queryCardNum);
		}
		
		if (isNotEmpty(startDateTrans)) {
			whereSql.append(" AND A.TRANS_DATE ").append(" >= ").append("'")
					.append(startDateTrans).append("'");
		}
		if (isNotEmpty(endDateTrans)) {
			whereSql.append(" AND A.TRANS_DATE").append(" <= ").append("'")
					.append(endDateTrans).append("'");
		}
		StringBuffer selectBuf = new StringBuffer("select A.DATE_SETTLMT,");
		selectBuf.append(" C.BRH_ID BRH_ID,      ");
		selectBuf.append(" C.CREATE_NEW_NO CREATE_NEW_NO,      ");
		selectBuf.append(" C.BRH_NAME BRH_NAME,  ");
		selectBuf.append(" A.MCHT_CD MCHT_ID,    ");
		selectBuf.append(" B.MCHT_NM MCHT_NAME,  ");
		selectBuf.append(" A.PAN POSP_PAN,   ");
		selectBuf.append(" A.PAN INST_PAN,   ");
		selectBuf.append(" TO_NUMBER(NVL(trim(A.TRANS_AMT),0))/100 POSP_AMT_TRANS,   ");
		selectBuf.append(" TO_NUMBER(NVL(trim(A.TRANS_AMT),0))/100 INST_AMT_TRANS,   ");
		selectBuf.append(" A.MCHT_FEE_D - A.MCHT_FEE_C MCHT_FEE,      ");
		selectBuf.append(" A.ACQ_INS_ALLOT_C - A.ACQ_INS_ALLOT_D INS_ALLOT,  ");
		selectBuf.append(" A.TRANS_DATE,  ");
		selectBuf.append(" A.TRANS_DATE_TIME,    ");
		selectBuf.append(" A.TRANS_DATE INST_TRANS_DATE,  ");
		selectBuf.append(" A.TRANS_DATE_TIME INST_TRANS_DATE_TIME,    ");
		selectBuf.append(" A.TXN_SSN SEQ_NUM,    ");
		selectBuf.append(" A.RETRIVL_REF REF_NUM,");
		selectBuf.append(" A.INST_RETRIVL_REF INST_REF_NUM,");
		selectBuf.append(" '对平' CHECK_RESULT,");
		selectBuf.append(" '' STLM_FLG,     ");
		selectBuf.append(" '' KEY_INST,   ");
		selectBuf.append(" '' AJUST_STATUS,   ");
		selectBuf.append(" '' ADJUST_MODE,   ");
		selectBuf.append(" '' AJUST_NAME,   ");
		selectBuf.append(" '' AJUST_TIME,   ");
		selectBuf.append(" '' VC_TRAN_NO,   ");
		selectBuf.append(" '' ADJUST_MSG  ");
		
		
		StringBuffer fromBuf = new StringBuffer(" from TBL_ALGO_DTL A ");
		fromBuf.append(" left join TBL_MCHT_BASE_INF B on TRIM(A.MCHT_CD) = B.MCHT_NO  ");
		fromBuf.append(" left join TBL_BRH_INFO C on B.SIGN_INST_ID = C.BRH_ID  ");

		String sql = selectBuf.toString() + fromBuf.toString() + whereSql.toString();
		sql += " order by A.DATE_SETTLMT desc";
		
		String countSql = "SELECT count(1) " + fromBuf.toString() + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql, params);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, queryCount, params);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	private static Object[] getBthChkErrInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		int queryCount = Constants.QUERY_RECORD_COUNT;
		Integer custQueryCount = (Integer)request.getAttribute("CUSTOMER_QUERY_RECORD_COUNT");
		if (custQueryCount != null) {
			queryCount = custQueryCount;
		}
		//清算查询日期
		String startDateSettlmt = request.getParameter("startDateSettlmt");
		String endDateSettlmt = request.getParameter("endDateSettlmt");
		//合作伙伴号
		String queryBrhId = request.getParameter("queryBrhId");
		//商户号
		String queryMchtId = request.getParameter("queryMchtId");
		//流水号
		String querySeqNum = request.getParameter("querySeqNum");
		//参考号
		String queryRefNum = request.getParameter("queryRefNum");
		//卡号
		String queryCardNum = request.getParameter("queryCardNum");
		
		//交易查询日期
		String startDateTrans = request.getParameter("startDateTrans");
		String endDateTrans = request.getParameter("endDateTrans");
		//不平类型
		String queryResultType = request.getParameter("queryResultType");
		//调账状态
		String ajustStatus = request.getParameter("ajustStatus");
		
		StringBuffer whereSql = new StringBuffer(" where 1=1");
		
		Map<String, String> params = new HashMap<String,String>();
		if (isNotEmpty(startDateSettlmt)) {
			whereSql.append(" AND A.DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDateSettlmt).append("'");
		}
		if (isNotEmpty(endDateSettlmt)) {
			whereSql.append(" AND A.DATE_SETTLMT").append(" <= ").append("'")
					.append(endDateSettlmt).append("'");
		}
		if (isNotEmpty(queryBrhId)) {
			whereSql.append(" AND C.BRH_ID = :BRH_ID ");
			params.put("BRH_ID", queryBrhId);
		}
		if (isNotEmpty(queryMchtId)) {
			whereSql.append(" AND B.MCHT_NO = :MCHT_ID ");
			params.put("MCHT_ID", queryMchtId);
		}
		if (isNotEmpty(querySeqNum)) {
			whereSql.append(" AND A.SYS_SEQ_NUM like '%' || :SEQ_NUM || '%'");
			params.put("SEQ_NUM", querySeqNum);
		}
		if (isNotEmpty(queryRefNum)) {
			whereSql.append(" AND (A.INST_RETRIVL_REF like '%' || :REF_NUM || '%'");
			whereSql.append(" OR A.RETRIVL_REF like '%' || :REF_NUM || '%')");
			params.put("REF_NUM", queryRefNum);
		}
		if (isNotEmpty(queryCardNum)) {
			whereSql.append(" AND (A.POSP_PAN like '%' || :PAN || '%'");
			whereSql.append(" OR A.INST_PAN like '%' || :PAN || '%')");
			params.put("PAN", queryCardNum);
		}
		if (isNotEmpty(startDateTrans) && StringUtil.isEmpty(endDateTrans)) {
			whereSql.append(" AND (A.INSERT_DATE >= '").append(startDateTrans).append("'");
			whereSql.append(" OR A.INST_INSERT_DATE >= '").append(startDateTrans).append("')");
		}
		if (isNotEmpty(startDateTrans) && isNotEmpty(endDateTrans)) {
			whereSql.append(" AND (A.INSERT_DATE BETWEEN '").append(startDateTrans).append("' AND '").append(endDateTrans).append("'");
			whereSql.append(" OR A.INST_INSERT_DATE BETWEEN '").append(startDateTrans).append("' AND '").append(endDateTrans).append("')");
		}
		if (isNotEmpty(endDateTrans) && StringUtil.isEmpty(startDateTrans)) {
			whereSql.append(" AND A.INSERT_DATE <= '").append(endDateTrans).append("'");
			whereSql.append(" OR A.INST_INSERT_DATE <= '").append(endDateTrans).append("')");
		}
		if (isNotEmpty(queryResultType)) {
			whereSql.append(" AND A.STLM_FLAG = '").append(queryResultType).append("'");
		}
		if (isNotEmpty(ajustStatus)) {
			if ("0".equals(ajustStatus)) {
				whereSql.append(" AND D.REC_CRT_TS is null ");
			} else {
				whereSql.append(" AND D.REC_CRT_TS is not null ");
			}
		}
		StringBuffer selectBuf = new StringBuffer(" select A.DATE_SETTLMT,  ");
		selectBuf.append(" C.BRH_ID,   ");
		selectBuf.append(" C.CREATE_NEW_NO, ");
		selectBuf.append(" C.BRH_NAME, ");
		selectBuf.append(" A.CARD_ACCP_ID, ");
		selectBuf.append(" B.MCHT_NM,    ");
		selectBuf.append(" A.POSP_PAN, ");
		selectBuf.append(" A.INST_PAN, ");
		selectBuf.append(" DECODE(trim(A.POSP_AMT_TRANS),'','',TO_NUMBER(trim(A.POSP_AMT_TRANS))/100) POSP_AMT_TRANS, ");
		selectBuf.append(" DECODE(trim(A.INST_AMT_TRANS),'','',TO_NUMBER(trim(A.INST_AMT_TRANS))/100) INST_AMT_TRANS, ");
		selectBuf.append(" TO_NUMBER(NVL(trim(A.FEE_DEBIT),0))/100-TO_NUMBER(NVL(trim(A.FEE_CREDIT),0))/100 MCHT_FEE,");
		selectBuf.append(" '' INS_ALLOT,     ");
		selectBuf.append(" A.INSERT_DATE, ");
		selectBuf.append(" A.INSERT_TIME,  ");
		selectBuf.append(" A.INST_INSERT_DATE, ");
		selectBuf.append(" A.INST_INSERT_TIME,  ");
		selectBuf.append(" A.SYS_SEQ_NUM,  ");
		selectBuf.append(" A.RETRIVL_REF,   ");
		selectBuf.append(" A.INST_RETRIVL_REF,   ");
		selectBuf.append(" '不平' CHECK_RESULT,    ");
		selectBuf.append(" decode(A.STLM_FLAG,1,'POSP成功，渠道失败',2,'POSP失败，渠道成功',3,'POSP与渠道金额不一致',''), ");
		selectBuf.append(" A.KEY_INST,   ");
		selectBuf.append(" decode(D.REC_CRT_TS,null,'未调账','已调账') AJUST_STATUS,  ");
		selectBuf.append(" decode(D.KEY_INST,null,'',decode(D.SOLVE_MODE,'1','人工处理','系统调账')) ADJUST_MODE,   ");
		selectBuf.append(" E.OPR_NAME AJUST_NAME,   ");
		selectBuf.append(" D.REC_CRT_TS AJUST_TIME,   ");
		selectBuf.append(" D.VC_TRAN_NO VC_TRAN_NO,   ");
		selectBuf.append(" D.SOLVE_MSG ADJUST_MSG  ");
		
		StringBuffer fromBuf = new StringBuffer(" from BTH_ERR_DTL A ");
		fromBuf.append("left join TBL_MCHT_BASE_INF B on TRIM(A.CARD_ACCP_ID) = B.MCHT_NO left join TBL_BRH_INFO C on B.SIGN_INST_ID = C.BRH_ID ");	
		fromBuf.append("left join TBL_INST_BALANCE_AJUST D on A.DATE_SETTLMT = D.DATE_SETTLMT and trim(A.KEY_INST) = D.KEY_INST ");
		fromBuf.append("left join TBL_OPR_INFO E on D.REC_UPD_USR_ID = E.OPR_ID  ");

		String sql = selectBuf.toString() + fromBuf.toString() + whereSql.toString();
		sql += " order by decode(D.REC_CRT_TS,null,0,1),A.STLM_FLAG, A.DATE_SETTLMT desc,D.REC_CRT_TS desc";
		
		String countSql = "SELECT count(1) " + fromBuf.toString() + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql, params);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, queryCount, params);

		ret[0] = dataList;
		ret[1] = count;		
		return ret;
	}
	
	private static Object[] getBthChkSuspsInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		int queryCount = Constants.QUERY_RECORD_COUNT;
		Integer custQueryCount = (Integer)request.getAttribute("CUSTOMER_QUERY_RECORD_COUNT");
		if (custQueryCount != null) {
			queryCount = custQueryCount;
		}
		//清算查询日期
		String startDateSettlmt = request.getParameter("startDateSettlmt");
		String endDateSettlmt = request.getParameter("endDateSettlmt");
		//合作伙伴号
		String queryBrhId = request.getParameter("queryBrhId");
		//商户号
		String queryMchtId = request.getParameter("queryMchtId");
		//流水号
		String querySeqNum = request.getParameter("querySeqNum");
		//参考号
		String queryRefNum = request.getParameter("queryRefNum");
		//卡号
		String queryCardNum = request.getParameter("queryCardNum");
		//交易查询日期
		String startDateTrans = request.getParameter("startDateTrans");
		String endDateTrans = request.getParameter("endDateTrans");
		//存疑类型
		String queryResultType = request.getParameter("queryResultType1");

		StringBuffer whereSql1 = new StringBuffer(" where A.STLM_FLAG = '7' ");
		StringBuffer whereSql2 = new StringBuffer(" where A.STLM_FLAG = '7' ");
		
		Map<String, String> params = new HashMap<String,String>();
		if (isNotEmpty(startDateSettlmt)) {
			whereSql1.append(" AND A.DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDateSettlmt).append("'");
			whereSql2.append(" AND A.DATE_SETTLMT ").append(" >= ").append("'")
			.append(startDateSettlmt).append("'");
		}
		if (isNotEmpty(endDateSettlmt)) {
			whereSql1.append(" AND A.DATE_SETTLMT").append(" <= ").append("'")
					.append(endDateSettlmt).append("'");
			whereSql2.append(" AND A.DATE_SETTLMT").append(" <= ").append("'")
			.append(endDateSettlmt).append("'");
		}
		if (isNotEmpty(queryBrhId)) {
			whereSql1.append(" AND C.BRH_ID = :BRH_ID ");
			whereSql2.append(" AND C.BRH_ID = :BRH_ID ");
			params.put("BRH_ID", queryBrhId);
		}
		if (isNotEmpty(queryMchtId)) {
			whereSql1.append(" AND B.MCHT_NO = :MCHT_ID ");
			whereSql2.append(" AND B.MCHT_NO = :MCHT_ID ");
			params.put("MCHT_ID", queryMchtId);
		}
		if (isNotEmpty(querySeqNum)) {
			whereSql1.append(" AND A.SYS_SEQ_NUM like '%' || :SEQ_NUM || '%'");
			whereSql2.append(" AND A.TXN_NUM like '%' || :SEQ_NUM || '%'");
			params.put("SEQ_NUM", querySeqNum);
		}
		if (isNotEmpty(queryRefNum)) {
			whereSql1.append(" AND A.RETRIVL_REF like '%' || :REF_NUM || '%'");
			whereSql2.append(" AND A.RETRIVL_REF like '%' || :REF_NUM || '%'");
			params.put("REF_NUM", queryRefNum);
		}
		if (isNotEmpty(queryCardNum)) {
			whereSql1.append(" AND A.PAN like '%' || :PAN || '%'");
			whereSql2.append(" AND A.PAN like '%' || :PAN || '%'");
			params.put("PAN", queryCardNum);
		}
		if (isNotEmpty(startDateTrans)) {
			whereSql1.append(" AND A.INSERT_DATE ").append(" >= ").append("'")
					.append(startDateTrans).append("'");
			whereSql2.append(" AND A.INST_INSERT_DATE ").append(" >= ").append("'")
			.append(startDateTrans).append("'");
		}
		if (isNotEmpty(endDateTrans)) {
			whereSql1.append(" AND A.INSERT_DATE").append(" <= ").append("'")
					.append(endDateTrans).append("'");
			whereSql2.append(" AND A.INST_INSERT_DATE").append(" <= ").append("'")
			.append(endDateTrans).append("'");
		}
		
		StringBuffer selectBuf1 = new StringBuffer("select A.DATE_SETTLMT,");		
		selectBuf1.append("C.BRH_ID,      ");
		selectBuf1.append(" C.CREATE_NEW_NO, ");
		selectBuf1.append("C.BRH_NAME,    ");
		selectBuf1.append("A.CARD_ACCP_ID MCHT_ID,      ");
		selectBuf1.append("B.MCHT_NM MCHT_NAME,  ");
		selectBuf1.append("A.PAN POSP_PAN,");
		selectBuf1.append("'' INST_PAN,   ");
		selectBuf1.append("DECODE(trim(A.AMT_TRANS),'','',TO_CHAR(TO_NUMBER(trim(A.AMT_TRANS))/100)) POSP_AMT_TRANS,  ");
		selectBuf1.append("'' INST_AMT_TRANS,    ");
		selectBuf1.append("TO_CHAR(TO_NUMBER(trim(A.MCHT_FEE))/100) MCHT_FEE,  ");
		selectBuf1.append("'' INS_ALLOT,  ");
		selectBuf1.append("A.INSERT_DATE TRANS_DATE,");
		selectBuf1.append("A.INSERT_TIME TRANS_DATE_TIME,  ");
		selectBuf1.append("'' INST_TRANS_DATE,");
		selectBuf1.append("'' INST_TRANS_DATE_TIME,  ");
		selectBuf1.append("A.SYS_SEQ_NUM SEQ_NUM,");
		selectBuf1.append("A.RETRIVL_REF RETRIVL_REF,  ");
		selectBuf1.append("'' INST_RETRIVL_REF,  ");
		selectBuf1.append("'存疑' CHECK_RESULT, ");
		selectBuf1.append("'POSP有，渠道无' STLM_FLAG,    ");
		selectBuf1.append(" '' KEY_INST,   ");
		selectBuf1.append(" '' AJUST_STATUS,   ");
		selectBuf1.append(" '' ADJUST_MODE,   ");
		selectBuf1.append(" '' AJUST_NAME,   ");
		selectBuf1.append(" '' AJUST_TIME,   ");
		selectBuf1.append(" '' VC_TRAN_NO,   ");
		selectBuf1.append(" '' ADJUST_MSG  ");
		
		String from1 = " from BTH_CHK_TXN A left join TBL_MCHT_BASE_INF B on TRIM(A.CARD_ACCP_ID) = B.MCHT_NO "
				+ "left join TBL_BRH_INFO C on B.SIGN_INST_ID = C.BRH_ID ";
		
		StringBuffer selectBuf2 = new StringBuffer("select A.DATE_SETTLMT,");		
		selectBuf2.append("C.BRH_ID,      ");
		selectBuf2.append(" C.CREATE_NEW_NO, ");
		selectBuf2.append("C.BRH_NAME,    ");
		selectBuf2.append("A.CARD_ACCP_ID MCHT_ID,      ");
		selectBuf2.append("B.MCHT_NM MCHT_NAME,  ");
		selectBuf2.append("'' POSP_PAN,   ");
		selectBuf2.append("A.PAN INST_PAN,");
		selectBuf2.append("'' POSP_AMT_TRANS ,   ");
		selectBuf2.append("DECODE(trim(A.AMT_TRANS),'','',TO_CHAR(TO_NUMBER(trim(A.AMT_TRANS))/100)) INST_AMT_TRANS , ");
		selectBuf2.append("'' MCHT_FEE,   ");
		selectBuf2.append("'' INS_ALLOT,  ");
		selectBuf2.append("'' TRANS_DATE,");
		selectBuf2.append("'' TRANS_DATE_TIME,  ");
		selectBuf2.append("A.INST_INSERT_DATE INST_TRANS_DATE,");
		selectBuf2.append("A.INST_INSERT_TIME INST_TRANS_DATE_TIME,  ");
		selectBuf2.append("A.TXN_NUM SEQ_NUM,    ");
		selectBuf2.append("'' RETRIVL_REF,");
		selectBuf2.append("A.RETRIVL_REF INST_RETRIVL_REF,");
		selectBuf2.append("'存疑' CHECK_RESULT, ");
		selectBuf2.append("'POSP无，渠道有' STLM_FLAG,    ");
		selectBuf2.append(" '' KEY_INST,   ");
		selectBuf2.append(" '' AJUST_STATUS,   ");
		selectBuf2.append(" '' ADJUST_MODE,   ");
		selectBuf2.append(" '' AJUST_NAME,   ");
		selectBuf2.append(" '' AJUST_TIME,   ");
		selectBuf2.append(" '' VC_TRAN_NO,   ");
		selectBuf2.append(" '' ADJUST_MSG  ");
		
		String from2 = " from BTH_INST_TXN A left join TBL_MCHT_BASE_INF B on TRIM(A.CARD_ACCP_ID) = B.MCHT_NO "
				+ "left join TBL_BRH_INFO C on B.SIGN_INST_ID = C.BRH_ID ";

		
		String sql = "";
		String countSql = "";
		if ("1".equals(queryResultType)) {
			sql = selectBuf1.toString() + from1 + whereSql1.toString() + " order by A.DATE_SETTLMT desc";
			countSql = "select count(1) " + from1 + whereSql1.toString();
		} else if ("2".equals(queryResultType)) {
			sql = selectBuf2.toString() + from2 + whereSql2.toString() + " order by A.DATE_SETTLMT desc";
			countSql = "select count(1) " + from2 + whereSql2.toString();
		} else {
			String innerSql = selectBuf1.toString() + from1 + whereSql1.toString() + " union all " + selectBuf2.toString() + from2 + whereSql2.toString();
			sql = "select * from (" + innerSql + ") " + " order by DATE_SETTLMT desc";
			countSql = "select count(1) from (" + innerSql + ") ";
		}

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql, params);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, queryCount, params);

		ret[0] = dataList;
		ret[1] = count;
		
		return ret;
	}
	
	/**
	 * 小金额结算查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getSmallAmtSettleInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String actStartDate = request.getParameter("actStartDate");
		String actEndDate = request.getParameter("actEndDate");
		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		String querySettleFlag = request.getParameter("querySettleFlag");

		StringBuffer whereSql = new StringBuffer(" where  SETTLE_TYPE = '1' ");
		// +
		// "AND MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(actStartDate)) {
			whereSql.append(" AND ACT_SETTLMT_DATE ").append(" >= ")
					.append("'").append(actStartDate).append("'");
		}
		if (isNotEmpty(actEndDate)) {
			whereSql.append(" AND ACT_SETTLMT_DATE").append(" <= ").append("'")
					.append(actEndDate).append("'");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			// whereSql.append(" AND MCHT_NO").append(" = ").append("'").append(mchtNo).append("'");
			// whereSql.append(" AND MCHT_NO").append(" in ").append(CommonFunction.getBelowMchtByMchtNo(mchtNo));
			whereSql.append(
					" AND MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =")
					.append("'")
					.append(queryMchtNoNm)
					.append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
		}
		if (isNotEmpty(querySettleFlag)) {
			whereSql.append(" AND a.SETTLE_FLAG").append(" = ").append("'")
					.append(querySettleFlag).append("'");
		}

		String sql = "SELECT DATE_SETTLMT,ACT_SETTLMT_DATE,(MCHT_NO||' - '||MCHT_NM) as mchtNoNm,"
				+ "(select b.BRH_ID||'-'||b.BRH_NAME from TBL_BRH_INFO b where trim(b.BRH_ID)=trim(a.BRH_CODE)) as bankNoName,"
				+ "TXN_AMT,SETTLE_FEE1,SETTLE_AMT,SETTLE_FLAG,"
				+ "a.CHANNEL_CD||(select '-'||d.FIRST_BRH_NAME from  TBL_FIRST_BRH_DEST_ID d where a.CHANNEL_CD=substr(d.FIRST_BRH_ID,3,2)) as channel_name "
				+ "from TBL_MCHNT_INFILE_DTL a ";
		sql = sql + whereSql.toString();
		sql = sql + " order by date_settlmt desc";
		String countSql = "SELECT count(*) FROM TBL_MCHNT_INFILE_DTL a ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = df.format(Double.parseDouble(objects[4]
							.toString()));
					objects[4] = CommonFunction.moneyFormat(objects[4]
							.toString());
				}
				if (objects[5] != null && !"".equals(objects[5])) {
					objects[5] = df.format(Double.parseDouble(objects[5]
							.toString()));
					objects[5] = CommonFunction.moneyFormat(objects[5]
							.toString());
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6]
							.toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6]
							.toString());
				}
			}
		}

		if (dataList.size() > 0) {
			String sumSql = "select nvl(sum(nvl(a.TXN_AMT,0)),0),nvl(sum(nvl(a.SETTLE_FEE1,0)),0),nvl(sum(nvl(a.SETTLE_AMT,0)),0) "
					+ " from TBL_MCHNT_INFILE_DTL a " + whereSql.toString();
			List<Object[]> amtList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sumSql);

			Object[] obj = new Object[dataList.get(0).length];

			obj[0] = "<font color='red'>总计</font>";
			obj[4] = "<font color='red'>"
					+ CommonFunction.moneyFormat(StringUtil.isNotEmpty(amtList
							.get(0)[0]) ? amtList.get(0)[0].toString() : "")
					+ "</font>";
			obj[5] = "<font color='red'>"
					+ CommonFunction.moneyFormat(StringUtil.isNotEmpty(amtList
							.get(0)[1]) ? amtList.get(0)[1].toString() : "")
					+ "</font>";
			obj[6] = "<font color='red'>"
					+ CommonFunction.moneyFormat(StringUtil.isNotEmpty(amtList
							.get(0)[2]) ? amtList.get(0)[2].toString() : "")
					+ "</font>";

			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 小金额结算查询 ：新清结算
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getSmallAmtSettleInfoNew(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String actStartDate = request.getParameter("actStartDate");
		String actEndDate = request.getParameter("actEndDate");
		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		String queryHangingFlag = request.getParameter("querySettleFlag");

		StringBuffer whereSql = new StringBuffer(
				" where  1=1  and a.hanging_flag= '0' ");
		// +
		// "AND MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.date_stlm ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.date_stlm").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(actStartDate)) {
			whereSql.append(" AND a.date_stlm_true ").append(" >= ")
					.append("'").append(actStartDate).append("'");
		}
		if (isNotEmpty(actEndDate)) {
			whereSql.append(" AND a.date_stlm_true").append(" <= ").append("'")
					.append(actEndDate).append("'");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			whereSql.append(" AND a.mcht_id").append(" = ").append("'")
					.append(queryMchtNoNm).append("'");
			// whereSql.append(" AND MCHT_NO").append(" in ").append(CommonFunction.getBelowMchtByMchtNo(mchtNo));
			/*
			 * whereSql.append(
			 * " AND MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO ="
			 * ) .append("'") .append(queryMchtNoNm)
			 * .append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
			 */
		}
		if (isNotEmpty(queryHangingFlag)) {
			whereSql.append(" AND a.hanging_flag").append(" = ").append("'")
					.append(queryHangingFlag).append("'");
		}

		String sql = "SELECT date_stlm,date_stlm_true,(a.mcht_id||' - '||b.mcht_nm) as mcht_no_nm,"
				+ "(select c.brh_id||'-'||c.brh_name from tbl_brh_info c where trim(c.brh_id)=trim(a.brh_id)) as bank_no_name,"
				+ "a.amt_settle,a.hanging_flag "
				+ "from TBL_SETTLE_HANGING a left join tbl_mcht_base_inf b on a.mcht_id=b.mcht_no ";
		sql = sql + whereSql.toString();
		sql = sql + " order by date_stlm desc";
		String countSql = "SELECT count(*) FROM TBL_SETTLE_HANGING a "
				+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = df.format(Double.parseDouble(objects[4]
							.toString()));
					objects[4] = CommonFunction.moneyFormat(objects[4]
							.toString());
				}
			}
		}

		if (dataList.size() > 0) {
			String sumSql = "select nvl(sum(nvl(a.amt_settle,0)),0) "
					+ " from TBL_SETTLE_HANGING a " + whereSql.toString();
			String amtSum = CommonFunction.getCommQueryDAO()
					.findCountBySQLQuery(sumSql);

			Object[] obj = new Object[dataList.get(0).length];

			obj[0] = "<font color='red'>总计</font>";
			obj[4] = "<font color='red'>"
					+ CommonFunction
							.moneyFormat(StringUtil.isNotEmpty(amtSum) ? amtSum
									: "") + "</font>";
			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 资金结算明细查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getAmtSettleDtlInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryAmtTransLow = request.getParameter("queryAmtTransLow");
		String queryAmtTransUp = request.getParameter("queryAmtTransUp");
		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		String queryBrhId = request.getParameter("queryBrhId");

		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		// StringBuffer whereSql = new
		// StringBuffer(" where a.MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryAmtTransLow)) {
			whereSql.append(" AND NVL(a.TXN_AMT,0) ").append(" >= ")
					.append("'").append(queryAmtTransLow).append("'");
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSql.append(" AND NVL(a.TXN_AMT,0) ").append(" <= ")
					.append("'").append(queryAmtTransUp).append("'");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			// whereSql.append(" AND MCHT_NO").append(" = ").append("'").append(mchtNo).append("'");
			// whereSql.append(" AND MCHT_NO").append(" in ").append(CommonFunction.getBelowMchtByMchtNo(mchtNo));
			whereSql.append(
					" AND a.MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =")
					.append("'")
					.append(queryMchtNoNm)
					.append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
		}
		if (isNotEmpty(queryBrhId)) {
			// whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf a where a.bank_no = '"
			// + queryBrhId.trim() + "')");
			whereSql.append(" and a.MCHT_NO in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ queryBrhId.trim()
					+ "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
		}

		String sql = "SELECT DATE_SETTLMT,(a.MCHT_NO||' - '||a.MCHT_NM) as MCHT_NO_NM,"
				+ "(select b.brh_id||' - '||b.brh_name from TBL_BRH_INFO b where b.brh_id = trim(BRH_CODE)) as brh_id,"
				+ "MCHT_ACCT,ACCT_NM,c.SETTLE_BANK_NM,substr(c.OPEN_STLNO,1,12),TXN_AMT,SETTLE_FEE1,SETTLE_AMT,"
				+ "a.CHANNEL_CD||(select '-'||d.FIRST_BRH_NAME from  TBL_FIRST_BRH_DEST_ID d where a.CHANNEL_CD=substr(d.FIRST_BRH_ID,3,2)) as channel_name "
				+ "FROM TBL_MCHNT_INFILE_DTL a left join TBL_MCHT_SETTLE_INF c on a.MCHT_NO = c.MCHT_NO ";
		sql = sql + whereSql.toString();
		sql = sql + " order by date_settlmt desc";
		String countSql = "SELECT count(*) FROM TBL_MCHNT_INFILE_DTL a  ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7]
							.toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7]
							.toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8]
							.toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8]
							.toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					objects[9] = df.format(Double.parseDouble(objects[9]
							.toString()));
					objects[9] = CommonFunction.moneyFormat(objects[9]
							.toString());
				}
			}
		}

		if (dataList.size() > 0) {
			String sumSql = "select nvl(sum(nvl(a.TXN_AMT,0)),0),nvl(sum(nvl(a.SETTLE_FEE1,0)),0),nvl(sum(nvl(a.SETTLE_AMT,0)),0) "
					+ " from TBL_MCHNT_INFILE_DTL a " + whereSql.toString();
			List<Object[]> amtList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sumSql);

			Object[] obj = new Object[dataList.get(0).length];

			obj[0] = "<font color='red'>总计</font>";
			obj[7] = "<font color='red'>"
					+ CommonFunction.moneyFormat(StringUtil.isNotEmpty(amtList
							.get(0)[0]) ? amtList.get(0)[0].toString() : "")
					+ "</font>";
			obj[8] = "<font color='red'>"
					+ CommonFunction.moneyFormat(StringUtil.isNotEmpty(amtList
							.get(0)[1]) ? amtList.get(0)[1].toString() : "")
					+ "</font>";
			obj[9] = "<font color='red'>"
					+ CommonFunction.moneyFormat(StringUtil.isNotEmpty(amtList
							.get(0)[2]) ? amtList.get(0)[2].toString() : "")
					+ "</font>";

			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风控警报 :事后
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskAlarmT1(int begin, HttpServletRequest request) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String queryAlarmSta = request.getParameter("queryAlarmSta");
		String queryAlarmLvl = request.getParameter("queryAlarmLvl");
		String queryRiskId = request.getParameter("queryRiskId");
		String queryAlarmSeq = request.getParameter("queryAlarmSeq");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(
				" where 1=1 and substr(a.risk_id,1,1)!='T' ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.RISK_DATE >='").append(startDate)
					.append("'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.RISK_DATE <='").append(endDate).append("'");
		}
		if (StringUtil.isNotEmpty(queryAlarmLvl)) {
			whereSql.append(" and b.misc ='").append(queryAlarmLvl).append("'");
		}
		if (StringUtil.isNotEmpty(queryAlarmSta)) {
			whereSql.append(" and a.ALARM_STA ='").append(queryAlarmSta)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryRiskId)) {
			whereSql.append(" and a.RISK_ID ='").append(queryRiskId)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryAlarmSeq)) {
			whereSql.append(" and a.ALARM_SEQ like '%").append(queryAlarmSeq)
					.append("%' ");
		}

		String sql = " select "
				+ "a.ALARM_ID,a.ALARM_SEQ,a.RISK_DATE,a.RISK_ID,"
				+ "a.RISK_LVL||(select distinct '-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISK_LVL) as risk_lvl_name,"
				+ "b.misc,a.ALARM_STA,b.SA_MODEL_DESC,a.misc "
				+ " from TBL_RISK_ALARM a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND "
				+ whereSql.toString()
				+ " order by a.ALARM_STA,a.ALARM_LVL desc,a.ALARM_ID desc,a.ALARM_SEQ ";
		String countSql = " select count(*) from TBL_RISK_ALARM a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风控警报 :事中
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskAlarmT0(int begin, HttpServletRequest request) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String queryAlarmSta = request.getParameter("queryAlarmSta");
		String queryAlarmLvl = request.getParameter("queryAlarmLvl");
		String queryRiskId = request.getParameter("queryRiskId");
		String queryAlarmSeq = request.getParameter("queryAlarmSeq");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(
				" where 1=1 and substr(a.risk_id,1,1)='T' ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.RISK_DATE >='").append(startDate)
					.append("'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.RISK_DATE <='").append(endDate).append("'");
		}
		if (StringUtil.isNotEmpty(queryAlarmLvl)) {
			whereSql.append(" and b.misc ='").append(queryAlarmLvl).append("'");
		}
		if (StringUtil.isNotEmpty(queryAlarmSta)) {
			whereSql.append(" and a.ALARM_STA ='").append(queryAlarmSta)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryRiskId)) {
			whereSql.append(" and a.RISK_ID ='").append(queryRiskId)
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryAlarmSeq)) {
			whereSql.append(" and a.ALARM_SEQ like '%").append(queryAlarmSeq)
					.append("%' ");
		}

		String sql = " select "
				+ "a.ALARM_ID,a.ALARM_SEQ,a.RISK_DATE,a.RISK_ID,"
				+ "a.RISK_LVL||(select distinct '-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISK_LVL) as risk_lvl_name,"
				+ "b.misc,a.ALARM_STA,b.SA_MODEL_DESC,a.misc "
				+ " from TBL_RISK_ALARM a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND "
				+ whereSql.toString()
				+ " order by a.ALARM_STA,a.ALARM_LVL desc,a.ALARM_ID desc,a.ALARM_SEQ ";
		String countSql = " select count(*) from TBL_RISK_ALARM a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风控警报--交易
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAlarmTxn(int begin, HttpServletRequest request) {

		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String querySysSeqNum = request.getParameter("querySysSeqNum");

		String queryCautionFlag = request.getParameter("queryCautionFlag");
		String queryReceiptFlag = request.getParameter("queryReceiptFlag");
		String queryBlockFlag = request.getParameter("queryBlockFlag");
		String queryAlarmId = request.getParameter("queryAlarmId");
		String queryAlarmSeq = request.getParameter("queryAlarmSeq");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryAlarmId)) {
			whereSql.append(" and a.alarm_id  = '").append(queryAlarmId)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryAlarmSeq)) {
			whereSql.append(" and a.alarm_seq  = '").append(queryAlarmSeq)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" and a.CARD_ACCP_TERM_ID  like '%")
					.append(queryCardAccpTermId).append("%' ");
		}
		if (StringUtil.isNotEmpty(querySysSeqNum)) {
			whereSql.append(" and a.sys_seq_num  ='").append(querySysSeqNum)
					.append("' ");
		}

		if (StringUtil.isNotEmpty(queryCautionFlag)) {
			if (queryCautionFlag.equals("0")) {
				whereSql.append(" and f.risk_date is null ");
			} else {
				whereSql.append(" and f.risk_date is not null ");
			}
		}
		if (StringUtil.isNotEmpty(queryReceiptFlag)) {
			if (queryReceiptFlag.equals("0")) {
				whereSql.append(" and b.RECEIPT_STATUS is null ");
			} else {
				whereSql.append(" and b.RECEIPT_STATUS ='")
						.append(queryReceiptFlag).append("' ");
			}
		}
		if (StringUtil.isNotEmpty(queryBlockFlag)) {
			// whereSql.append(" and a.BLOCK_FLAG ='").append(queryBlockFlag).append("'");
			if (queryBlockFlag.equals("1")) {
				whereSql.append(" and g.FREEZE_FLAG is null ");
			} else {
				whereSql.append(" and g.FREEZE_FLAG ='").append(queryBlockFlag)
						.append("' ");
			}
		}

		String sql = " select "
				+ "a.alarm_id,a.inst_date,a.sys_seq_num,"
				+ "a.card_accp_id,a.pan,a.card_accp_term_id,"
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =a.txn_num ) as txn_name,"
				+ "TO_NUMBER(NVL(trim(a.amt_trans),0))/100 as amt,"
				+ "a.trans_state,"
				+ "a.rsp_code||'-'||trim(d.rsp_code_dsp)  as rsp_code_name,"
				+ "a.cheat_tp,a.cheat_flag,"
				+ " case when f.risk_date is null then '0' else '1' end  as CAUTION_STA,"
				// +
				// " case when a.receipt_flag='1' then b.RECEIPT_STATUS else a.receipt_flag end  as receipt_sta ,"
				+ " nvl(b.RECEIPT_STATUS,0) as receipt_sta,"
				// + "a.block_flag,"
				+ " nvl(g.FREEZE_FLAG,0) as block_sta,"
				+ "b.POS_IMAG_PATH,'400','400', "
				+ "a.card_accp_id||'-'||c.mcht_nm as mcht_name,"
				+ "(case when e.extra_flag='3' then e.extra_flag else  e.stlm_flag end  ) as settle_flag,"
				// +" e.stlm_flag as settle_flag,"
				+ "a.txn_num "

				+ " from TBL_ALARM_TXN a "
				+ " left join TBL_BILL_RECEIPT b on b.inst_date=a.inst_date and b.sys_seq_num=a.sys_seq_num and trim(a.pan)=b.pan "
				+ " left join tbl_mcht_base_inf c on a.card_accp_id=c.mcht_no "
				+ " left join tbl_rsp_code_map d on trim(a.rsp_code)=trim(d.dest_rsp_code) "
				+ "	left join  tbl_clear_dtl e on e.txn_date=substr(a.inst_date,1,8) and e.txn_sn=a.sys_seq_num and substr(a.inst_date,9)=e.txn_time "
				+ " left join tbl_remind_risk_txn f on f.inst_date=a.inst_date and f.sys_seq_num=a.sys_seq_num and trim(a.pan)=f.pan "
				+ " left join tbl_chk_freeze g on g.txn_dt=a.inst_date and g.sys_seq_num=a.sys_seq_num and a.pan=g.pan "
				+ whereSql.toString()
				+ " order by a.alarm_id,a.inst_date desc,a.sys_seq_num ";
		String countSql = " select count(*) from TBL_ALARM_TXN a  "
				+ " left join TBL_BILL_RECEIPT b on b.inst_date=a.inst_date and b.sys_seq_num=a.sys_seq_num and trim(a.pan)=b.pan "
				+ " left join tbl_mcht_base_inf c on a.card_accp_id=c.mcht_no "
				+ " left join tbl_rsp_code_map d on trim(a.rsp_code)=trim(d.dest_rsp_code) "
				+ "	left join tbl_clear_dtl e on e.txn_date=substr(a.inst_date,1,8) and e.txn_sn=a.sys_seq_num and substr(a.inst_date,9)=e.txn_time "
				+ " left join tbl_remind_risk_txn f on f.inst_date=a.inst_date and f.sys_seq_num=a.sys_seq_num and trim(a.pan)=f.pan "
				+ " left join tbl_chk_freeze g on g.txn_dt=a.inst_date and g.sys_seq_num=a.sys_seq_num and a.pan=g.pan "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		Object[] obj = null;
		for (Object[] objects : dataList) {
			if (objects[7] != null && !"".equals(objects[7])) {
				objects[7] = CommonFunction.moneyFormat(objects[7].toString());
			}
			obj = null;
			obj = getRiskTxnImgInf(objects[15] == null ? "" : objects[15]
					.toString());
			objects[16] = obj[0];
			objects[17] = obj[1];
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风控调单回执图片
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	private static Object[] getRiskTxnImgInf(String imagPath) {

		String basePath = SysParamUtil
				.getParam(SysParamConstants.FILE_UPLOAD_DISK);
		// basePath = basePath.replace("\\", "/");

		Object[] obj = new Object[2];
		if (StringUtil.isEmpty(imagPath)) {
			return obj;
		}
		try {
			BufferedImage bi = ImageIO.read(new File(basePath + imagPath));
			double width = bi.getWidth();
			double height = bi.getHeight();
			bi = null;
			double rate = 0;

			if (width > 400 || height > 400) {
				if (width > height) {
					rate = 400 / width;
					width = 400;
					height = height * rate;
				} else {
					rate = 400 / height;
					height = 400;
					width = width * rate;
				}
			}
			obj[0] = (int) width;
			obj[1] = (int) height;

		} catch (Exception e) {
			obj[0] = 400;
			obj[1] = 400;
		}

		return obj;
	}

	/**
	 * 风控警报--商户
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAlarmMcht(int begin, HttpServletRequest request) {

		// String queryCardAccpId = request.getParameter("queryCardAccpId");

		String queryMchtCautionFlag = request
				.getParameter("queryMchtCautionFlag");
		String queryBlockSettleFlag = request
				.getParameter("queryBlockSettleFlag");
		String queryBlockMchtFlag = request.getParameter("queryBlockMchtFlag");
		String queryAlarmId = request.getParameter("queryAlarmId");
		String queryAlarmSeq = request.getParameter("queryAlarmSeq");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (StringUtil.isNotEmpty(queryAlarmId)) {
			whereSql.append(" and a.alarm_id  = '").append(queryAlarmId)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryAlarmSeq)) {
			whereSql.append(" and a.alarm_seq  = '").append(queryAlarmSeq)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryMchtCautionFlag)) {
			// whereSql.append(" and a.CAUTION_FLAG ='").append(queryMchtCautionFlag).append("'");
			if (queryMchtCautionFlag.equals("0")) {
				whereSql.append(" and d.mcht_no  is null ");
			} else {
				whereSql.append(" and d.mcht_no  is not null ");
			}
		}
		if (StringUtil.isNotEmpty(queryBlockSettleFlag)) {
			if (queryBlockSettleFlag.equals("1")) {
				whereSql.append(" and c.settle_type ='3' ");
			} else {
				whereSql.append(" and c.settle_type !='3' ");
			}
		}
		if (StringUtil.isNotEmpty(queryBlockMchtFlag)) {
			if (queryBlockMchtFlag.equals("1")) {
				whereSql.append(" and b.mcht_status ='6' ");
			} else {
				whereSql.append(" and b.mcht_status !='6' ");
			}
		}
		String sql = " select "
				+ "a.alarm_id,a.card_accp_id,"
				+ "a.card_accp_id||'-'||b.mcht_nm as mcht_name,"
				+ "b.risl_lvl||(select distinct '-'|| trim(resved) from tbl_risk_lvl  where risk_lvl=b.risl_lvl) as risk_name ,"
				// + "a.alarm_num,"
				+ "(select sum(f.alarm_num) from TBL_ALARM_MCHT f where f.card_accp_id=a.card_accp_id and substr(f.alarm_id,1,8)='"
				+ queryAlarmId.substring(0, 8)
				+ "') as alarm_totay,"
				+ "(select sum(j.alarm_num) from TBL_ALARM_MCHT j where j.card_accp_id=a.card_accp_id ) as alarm_total,"
				// + "a.caution_flag,"
				+ " case when d.mcht_no is null then '0' else '1' end  as CAUTION_STA,"
				// + "a.block_settle_flag,"
				+ "case when c.settle_type='3' then '1' else '0' end as block_settle_status ,"
				// + "a.block_mcht_flag,"
				+ "case when b.mcht_status='6' then '1' else '0' end  as block_mcht_status ,"
				// +
				// " (select count(1) from TBL_RISK_ALARM g where g.cheat_flag='1' and g.ALARM_ID in (select h.ALARM_ID from TBL_ALARM_MCHT h where h.card_accp_id=a.card_accp_id  ) ) as cheat_num "
				+ " (select count(1) from TBL_RISK_ALARM g where g.cheat_flag='1' "
				+ "and EXISTS (select * from TBL_ALARM_MCHT h "
				+ "where h.card_accp_id=a.card_accp_id  "
				+ "and g.alarm_id=h.alarm_id and g.alarm_seq=h.alarm_seq) ) as cheat_num "
				+ " from TBL_ALARM_MCHT a "
				+ " left join tbl_mcht_base_inf b on a.card_accp_id=b.mcht_no "
				+ " left join tbl_mcht_settle_inf c on a.card_accp_id=c.mcht_no "
				+ " left join tbl_remind_risk_mcht d on a.card_accp_id=d.mcht_no and d.risk_date='"
				+ CommonFunction.getCurrentDate() + "' " + whereSql.toString()
				+ " order by a.alarm_id,a.alarm_seq,a.card_accp_id  ";
		String countSql = " select count(*) from TBL_ALARM_MCHT a  "
				+ " left join tbl_mcht_base_inf b on a.card_accp_id=b.mcht_no "
				+ " left join tbl_mcht_settle_inf c on a.card_accp_id=c.mcht_no "
				+ " left join tbl_remind_risk_mcht d on a.card_accp_id=d.mcht_no and d.risk_date='"
				+ CommonFunction.getCurrentDate() + "' " + whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风控警报 违规记录（商户）
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtRule(int begin, HttpServletRequest request) {

		String queryCardAccpId = request.getParameter("queryCardAccpId");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where  ");
		whereSql.append("EXISTS (select * from TBL_ALARM_MCHT h "
				+ " where h.card_accp_id='" + queryCardAccpId + "' "
				+ " and  a.alarm_id=h.alarm_id and a.alarm_seq=h.alarm_seq )");
		/*
		 * if (StringUtil.isNotEmpty(queryCardAccpId)) {
		 * whereSql.append(" and a.card_accp_id >='"
		 * ).append(queryCardAccpId).append("'"); }
		 */
		String sql = " select "
				+ "a.ALARM_ID,a.ALARM_SEQ,a.RISK_DATE,a.RISK_ID,"
				+ "b.SA_MODEL_DESC, "
				+ "(select count(1) from TBL_ALARM_TXN g where g.ALARM_ID=a.ALARM_ID and g.ALARM_SEQ=a.ALARM_SEQ) as txn_count "
				+ " from TBL_RISK_ALARM a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND "
				+ whereSql.toString()
				+ " order by a.ALARM_ID desc,a.ALARM_SEQ ,a.RISK_ID ";
		String countSql = " select count(*) from TBL_RISK_ALARM a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风控警报--卡
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAlarmCard(int begin, HttpServletRequest request) {

		// String queryCardAccpId = request.getParameter("queryCardAccpId");

		String queryPan = request.getParameter("queryPan");
		String queryAlarmId = request.getParameter("queryAlarmId");
		String queryAlarmSeq = request.getParameter("queryAlarmSeq");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (StringUtil.isNotEmpty(queryAlarmId)) {
			whereSql.append(" and a.alarm_id  = '").append(queryAlarmId)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryAlarmSeq)) {
			whereSql.append(" and a.alarm_seq  = '").append(queryAlarmSeq)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryPan)) {
			whereSql.append(" and a.pan  like  '%").append(queryPan)
					.append("%' ");
		}

		String sql = " select "
				// + "a.alarm_id,a.alarm_seq,"
				+ "trim(a.pan),b.CARD_TP,b.card_dis,"
				+ "(select sum(f.alarm_num) from TBL_ALARM_CARD f where f.pan=a.pan and substr(f.alarm_id,1,8)='"
				+ queryAlarmId.substring(0, 8)
				+ "') as alarm_totay,"
				+ "(select sum(j.alarm_num) from TBL_ALARM_CARD j where j.pan=a.pan ) as alarm_total,"
				+ " (select count(1) from TBL_RISK_ALARM g where g.cheat_flag='1' "
				+ "and EXISTS (select * from TBL_ALARM_CARD h "
				+ "where h.pan=a.pan  "
				+ "and g.alarm_id=h.alarm_id and g.alarm_seq=h.alarm_seq) ) as cheat_num ,"
				+ " (select count(1) from TBL_BANK_CARD_BLACK t where trim(t.card_no)=trim(a.pan) ) as balck_num "
				+ " from TBL_ALARM_CARD a left join TBL_BANK_BIN_INF b on trim(b.BIN_STA_NO)=substr(a.pan,1,6) "
				+ whereSql.toString()
				+ " order by a.alarm_id,a.alarm_seq,a.pan  ";
		String countSql = " select count(*) from TBL_ALARM_CARD a   "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风控警报 违规记录（银行卡）
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCardRule(int begin, HttpServletRequest request) {

		String queryPan = request.getParameter("queryPan");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where  ");
		whereSql.append("EXISTS (select * from TBL_ALARM_CARD h "
				+ " where trim(h.pan)='" + queryPan + "' "
				+ " and  a.alarm_id=h.alarm_id and a.alarm_seq=h.alarm_seq )");
		/*
		 * if (StringUtil.isNotEmpty(queryCardAccpId)) {
		 * whereSql.append(" and a.card_accp_id >='"
		 * ).append(queryCardAccpId).append("'"); }
		 */
		String sql = " select "
				+ "a.ALARM_ID,a.ALARM_SEQ,a.RISK_DATE,a.RISK_ID,"
				+ "b.SA_MODEL_DESC, "
				+ "(select count(1) from TBL_ALARM_TXN g where g.ALARM_ID=a.ALARM_ID and g.ALARM_SEQ=a.ALARM_SEQ) as txn_count "
				+ " from TBL_RISK_ALARM a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND "
				+ whereSql.toString()
				+ " order by a.ALARM_ID desc,a.ALARM_SEQ ,a.RISK_ID ";
		String countSql = " select count(*) from TBL_RISK_ALARM a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 事后风控商户处理
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskMchtInfo(int begin, HttpServletRequest request) {
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		// Operator operator = (Operator) request.getSession().getAttribute(
		// Constants.OPERATOR_INFO);
		String queryMchntId = request.getParameter("queryMchntId");
		String queryRislLvl = request.getParameter("queryRislLvl");
		String queryBrhId = request.getParameter("queryBrhId");
		String queryMchtCautionFlag = request
				.getParameter("queryMchtCautionFlag");
		String queryBlockSettleFlag = request
				.getParameter("queryBlockSettleFlag");
		String queryBlockMchtFlag = request.getParameter("queryBlockMchtFlag");
		if (isNotEmpty(queryMchntId)) {
			whereSql.append(" AND a.MCHT_NO = '").append(queryMchntId)
					.append("' ");
		}
		if (isNotEmpty(queryRislLvl)) {
			whereSql.append(" AND a.RISL_LVL = '").append(queryRislLvl)
					.append("' ");
		}

		if (isNotEmpty(queryBrhId)) {
			whereSql.append(" AND a.BANK_NO = '").append(queryBrhId)
					.append("' ");
		}
		// else {
		// whereSql.append(" AND a.BANK_NO IN ").append(operator.getBrhBelowId());
		// }

		if (StringUtil.isNotEmpty(queryMchtCautionFlag)) {
			// whereSql.append(" and a.CAUTION_FLAG ='").append(queryMchtCautionFlag).append("'");
			if (queryMchtCautionFlag.equals("0")) {
				whereSql.append(" and d.mcht_no  is null ");
			} else {
				whereSql.append(" and d.mcht_no  is not null ");
			}
		}
		if (StringUtil.isNotEmpty(queryBlockSettleFlag)) {
			if (queryBlockSettleFlag.equals("1")) {
				whereSql.append(" and c.settle_type ='3' ");
			} else {
				whereSql.append(" and c.settle_type !='3' ");
			}
		}
		if (StringUtil.isNotEmpty(queryBlockMchtFlag)) {
			if (queryBlockMchtFlag.equals("1")) {
				whereSql.append(" and a.mcht_status ='6' ");
			} else {
				whereSql.append(" and a.mcht_status !='6' ");
			}
		}

		Object[] ret = new Object[2];

		String sql = "SELECT a.MCHT_NO,a.MCHT_NM,a.mcht_lvl,a.ENG_NAME,a.MCC,a.LICENCE_NO,a.ADDR,a.POST_CODE,"
				+ "a.COMM_EMAIL,a.MANAGER,a.CONTACT,a.COMM_TEL,substr(a.rec_crt_ts,1,8) as v1,"
				+ "a.MCHT_STATUS,"
				+ "a.CRT_OPR_ID,a.MCHT_GROUP_FLAG,"
				+ "a.RISL_LVL||(select distinct '-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISL_LVL) as risk_lvl_name,"
				+ "a.bank_no||'-'||b.brh_name as bank_name, "
				+ "case when d.mcht_no is null then '0' else '1' end  as CAUTION_STA,"
				+ "case when c.settle_type='3' then '1' else '0' end as block_settle ,"
				+ "case when a.mcht_status='6' then '1' else '0' end  as block_mcht_status "
				+ " FROM TBL_MCHT_BASE_INF a "
				+ " left join tbl_brh_info b on a.bank_no=b.brh_id "
				+ " left join tbl_mcht_settle_inf c on a.mcht_no=c.mcht_no "
				+ " left join tbl_remind_risk_mcht d on a.mcht_no=d.mcht_no and d.risk_date='"
				+ CommonFunction.getCurrentDate()
				+ "' "
				+ whereSql
				+ " ORDER BY a.rec_crt_ts desc";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF a "
				+ " left join tbl_brh_info b on a.bank_no=b.brh_id "
				+ " left join tbl_mcht_settle_inf c on a.mcht_no=c.mcht_no "
				+ " left join tbl_remind_risk_mcht d on a.mcht_no=d.mcht_no and d.risk_date='"
				+ CommonFunction.getCurrentDate() + "' " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 批处理管理
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-01 上午13:53:44
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatTaskCtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryId = request.getParameter("id");
		String queryGrpId = request.getParameter("queryGrpId");
		StringBuffer sql = new StringBuffer(
				"SELECT BAT_ID, a.GRPID||'-'||b.GRPNAME,BAT_LEVEL,BAT_STATUS||'-'||BAT_ID,ASN_STATUS||'-'||BAT_ID,BAT_FLAG,BEG_TIME,END_TIME,"
						+ "CAST(to_number(to_date(END_TIME,'yyyymmddhh24miss')-to_date(BEG_TIME,'yyyymmddhh24miss'))*24*60*60 as DECIMAl) as shichang,"
						+ "NUM_COMMIT,CHILD_COUNT,PROCESS_FUNC,BAT_DESC,BAT_ID,"
						+ "case when BAT_STATUS = 'F' then '1' when BAT_STATUS = '0' then '2' "
						+ "when BAT_STATUS = 'S' then '3' else BAT_STATUS end as orderby,a.RUN_STLM_DATE "
						+ " FROM TBL_BAT_TASK_CTL  a "
						+ " left join TBL_BAT_GRP_INFO b on a.GRPID=b.GRPID WHERE USE_FLAG = 'Y' ");

		if (isNotEmpty(queryId)) {
			sql.append(" AND BAT_ID ").append(" LIKE '%").append(queryId)
					.append("%'");
		}
		if (isNotEmpty(queryGrpId)) {
			sql.append(" AND a.GRPID ").append(" = '").append(queryGrpId)
					.append("' ");
		}
		sql.append(" order by a.GRPID,a.BAT_ID");

		String countSql = "select count(*) from (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin, 10);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 批处理子任务查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-01 上午13:53:44
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatTaskChd(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryId = request.getParameter("id");
		StringBuffer sql = new StringBuffer(
				"SELECT A.STLM_DT,A.BAT_ID,A.CHD_ID,A.STATUS,A.PARAM,A.COMMIT_POINT,A.FAIL_POINT"
						+ " FROM TBL_BAT_TASK_CHD A ");
		StringBuffer sb = new StringBuffer();
		sb.append(" where  A.BAT_ID").append(" = '").append(queryId)
				.append("'");
		sql = sql.append(sb);

		String countSql = "select count(*) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 批处理顺序查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-01
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatTaskRel(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryId = request.getParameter("id");
		String batId1 = request.getParameter("batId1");
		StringBuffer sql = new StringBuffer(
				"select a.bat_id,b.BAT_DESC,a.REL_BAT_ID,a.MISC from TBL_BAT_TASK_REL a,TBL_BAT_TASK_CTL b"
						+ " where a.bat_id = b.bat_id");
		if (queryId != null && !"".equals(queryId)) {
			StringBuffer sb = new StringBuffer();
			sb.append(" AND a.BAT_ID").append(" = '").append(queryId)
					.append("'");
			sql = sql.append(sb);
		}

		if (isNotEmpty(batId1)) {
			sql.append(" and a.REL_BAT_ID = '" + batId1 + "'");
		}

		String countSql = "select count(*) from (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 批处理管理_T0
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-01 上午13:53:44
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatTaskCtlNew(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryId = request.getParameter("id");
		String queryGrpId = request.getParameter("queryGrpId");
		StringBuffer sql = new StringBuffer(
				"SELECT BAT_ID, a.GRPID||'-'||b.GRPNAME,BAT_LEVEL,BAT_STATUS||'-'||BAT_ID,ASN_STATUS||'-'||BAT_ID,BAT_FLAG,BEG_TIME,END_TIME,"
						+ "CAST(to_number(to_date(END_TIME,'yyyymmddhh24miss')-to_date(BEG_TIME,'yyyymmddhh24miss'))*24*60*60 as DECIMAl) as shichang,"
						+ "NUM_COMMIT,CHILD_COUNT,PROCESS_FUNC,BAT_DESC,BAT_ID,"
						+ "case when BAT_STATUS = 'F' then '1' when BAT_STATUS = '0' then '2' "
						+ "when BAT_STATUS = 'S' then '3' else BAT_STATUS end as orderby,a.RUN_STLM_DATE "
						+ " FROM TBL_BAT_TASK_CTL_T0  a "
						+ " left join TBL_BAT_GRP_INFO_T0 b on a.GRPID=b.GRPID WHERE USE_FLAG = 'Y' ");

		if (isNotEmpty(queryId)) {
			sql.append(" AND BAT_ID ").append(" LIKE '%").append(queryId)
					.append("%'");
		}
		if (isNotEmpty(queryGrpId)) {
			sql.append(" AND a.GRPID ").append(" = '").append(queryGrpId)
					.append("' ");
		}
		sql.append(" order by a.GRPID,a.BAT_ID");

		String countSql = "select count(*) from (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin, 10);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 批处理子任务查询_T0
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-01 上午13:53:44
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatTaskChdNew(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryId = request.getParameter("id");
		StringBuffer sql = new StringBuffer(
				"SELECT A.STLM_DT,A.BAT_ID,A.CHD_ID,A.STATUS,A.PARAM,A.COMMIT_POINT,A.FAIL_POINT"
						+ " FROM TBL_BAT_TASK_CHD_T0 A ");
		StringBuffer sb = new StringBuffer();
		sb.append(" where  A.BAT_ID").append(" = '").append(queryId)
				.append("'");
		sql = sql.append(sb);

		String countSql = "select count(*) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 批处理顺序查询_T0
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-01
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatTaskRelNew(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryId = request.getParameter("id");
		String batId1 = request.getParameter("batId1");
		StringBuffer sql = new StringBuffer(
				"select a.bat_id,b.BAT_DESC,a.REL_BAT_ID,a.MISC from TBL_BAT_TASK_REL_T0 a,TBL_BAT_TASK_CTL_T0 b"
						+ " where a.bat_id = b.bat_id");
		if (queryId != null && !"".equals(queryId)) {
			StringBuffer sb = new StringBuffer();
			sb.append(" AND a.BAT_ID").append(" = '").append(queryId)
					.append("'");
			sql = sql.append(sb);
		}

		if (isNotEmpty(batId1)) {
			sql.append(" and a.REL_BAT_ID = '" + batId1 + "'");
		}

		String countSql = "select count(*) from (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 划付文件下载查询（总）
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-13
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPaySettle(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String settleDate = request.getParameter("settleDate");

		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		whereSql.append(" AND a.INST_DATE").append(" = '").append(settleDate)
				.append("' ");

		String sql = " select t.inst_date,t.channel_id,"
				+ "(select t.channel_id||'-'||b.channel_name from TBL_PAY_CHANNEL_INFO b where b.channel_id=t.channel_id) as channel_id_nm,"
				+ "t.amt_total "
				+ " from ("
				+ "	 select a.inst_date as inst_date,a.channel_id as channel_id,"
				+ "  sum(a.amt_settlmt) as amt_total "
				+ "  from TBL_PAY_SETTLE_DTL a  " + whereSql
				+ "	 group by a.inst_date,a.channel_id order by a.channel_id"
				+ "	) t ";

		String countSql = "select count(1) from ("
				+ " select a.inst_date, a.channel_id from TBL_PAY_SETTLE_DTL a "
				+ whereSql + " group by a.inst_date,a.channel_id) t2 ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			objects[3] = StringUtil.isNotEmpty(objects[3]) ? CommonFunction
					.moneyFormat(objects[3].toString()) : "0.00";
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 划付文件下载明细查询（明细）
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-13
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPaySettleDtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String instDate = request.getParameter("instDate");
		String channelId = request.getParameter("channelId");

		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		whereSql.append(" AND a.INST_DATE").append(" = '").append(instDate)
				.append("' ");
		whereSql.append(" AND a.CHANNEL_ID").append(" = '").append(channelId)
				.append("' ");

		String sql = "select a.mcht_no,a.mcht_no||'-'||a.misc as mcht_no_nm,a.set_dt_beg,a.set_dt_end,"
				+ "	a.amt_settlmt as amt,"
				+ "	a.mcht_acct_no,a.mcht_acct_nm,a.cnaps_id,a.pay_type "
				+ " from TBL_PAY_SETTLE_DTL a  "
				+ whereSql
				+ " order by a.mcht_no ";

		String countSql = "select count(*) from TBL_PAY_SETTLE_DTL a "
				+ whereSql + " order by a.mcht_no ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			objects[4] = CommonFunction.moneyFormat(objects[4].toString());
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户白名单申请信息(初审)
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-26下午09:40:21
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWhiteMchtF(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String queryMchtNo = request.getParameter("queryMchtNo");
		String queryBankNo = request.getParameter("queryBankNo");
		StringBuffer whereSql = new StringBuffer(
				" where a.CHECK_STATUS = '"
						+ RiskConstants.UNCHECK
						+ "' "
						+ "AND a.MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(queryMchtNo)) {
			whereSql.append(" AND a.MCHT_NO").append(" = ").append("'")
					.append(queryMchtNo).append("' ");
		}
		if (isNotEmpty(queryBankNo)) {
			whereSql.append(" AND b.BANK_NO").append(" = ").append("'")
					.append(queryBankNo).append("' ");
		}
		String sql = "SELECT a.MCHT_NO,b.MCHT_NM,a.MCHT_NO||' - '||b.MCHT_NM,c.BRH_ID||' - '||c.BRH_NAME,DAY_AVE_AMT,MON_AVE_AMT,SIG_MIN_AMT,SIG_MAX_AMT,"
				+ "SERV_DISP,APPLY_REASON,a.APPLY_OPR,a.APPLY_TIME"
				+ " FROM TBL_WHITE_MCHT_APPLY a "
				+ "LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO=b.MCHT_NO "
				+ "LEFT JOIN TBL_BRH_INFO c on b.BANK_NO=c.BRH_ID"
				+ whereSql
				+ " ORDER BY APPLY_TIME desc";
		String countSql = "SELECT COUNT(*) FROM TBL_WHITE_MCHT_APPLY a "
				+ "LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO=b.MCHT_NO "
				+ "LEFT JOIN TBL_BRH_INFO c on b.BANK_NO=c.BRH_ID" + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户白名单申请信息(终审)
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-26下午09:40:21
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWhiteMchtL(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String queryMchtNo = request.getParameter("queryMchtNo");
		String queryBankNo = request.getParameter("queryBankNo");
		StringBuffer whereSql = new StringBuffer(
				" where a.CHECK_STATUS = '"
						+ RiskConstants.FST_CHECK_T
						+ "' "
						+ "AND a.MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(queryMchtNo)) {
			whereSql.append(" AND a.MCHT_NO").append(" = ").append("'")
					.append(queryMchtNo).append("' ");
		}
		if (isNotEmpty(queryBankNo)) {
			whereSql.append(" AND b.BANK_NO").append(" = ").append("'")
					.append(queryBankNo).append("' ");
		}
		String sql = "SELECT a.MCHT_NO,b.MCHT_NM,a.MCHT_NO||' - '||b.MCHT_NM,c.BRH_ID||' - '||c.BRH_NAME,a.DAY_AVE_AMT,a.MON_AVE_AMT,a.SIG_MIN_AMT,a.SIG_MAX_AMT,"
				+ "a.SERV_DISP,a.APPLY_REASON,a.APPLY_OPR,a.APPLY_TIME,d.MCHT_CASE_DESP "
				+ "FROM TBL_WHITE_MCHT_APPLY a "
				+ "LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO=b.MCHT_NO "
				+ "LEFT JOIN TBL_BRH_INFO c on b.BANK_NO=c.BRH_ID "
				+ "LEFT JOIN (select * from TBL_WHITE_MCHT_CHECK where CHECK_TIME in (select max(CHECK_TIME) from TBL_WHITE_MCHT_CHECK where "
				+ "CHECK_STATUS ='"
				+ RiskConstants.FST_CHECK_T
				+ "' group by MCHT_NO)) d ON b.MCHT_NO=d.MCHT_NO"
				+ whereSql
				+ " ORDER BY a.APPLY_TIME desc";
		String countSql = "SELECT COUNT(*) FROM TBL_WHITE_MCHT_APPLY a "
				+ "LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO=b.MCHT_NO "
				+ "LEFT JOIN TBL_BRH_INFO c on b.BANK_NO=c.BRH_ID" + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户白名单审核记录信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-26下午09:40:21
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWhiteMchtCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String queryMchtNo = request.getParameter("queryMchtNo");
		String queryBankNo = request.getParameter("queryBankNo");
		String chackSta = request.getParameter("chackSta");
		StringBuffer whereSql = new StringBuffer(
				" where a.MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(queryMchtNo)) {
			whereSql.append(" AND a.MCHT_NO").append(" = ").append("'")
					.append(queryMchtNo).append("' ");
		}
		if (isNotEmpty(queryBankNo)) {
			whereSql.append(" AND b.BANK_NO").append(" = ").append("'")
					.append(queryBankNo).append("' ");
		}
		if (isNotEmpty(chackSta)) {
			whereSql.append(" AND CHECK_STATUS").append(" = ").append("'")
					.append(chackSta).append("' ");
		}
		String sql = "SELECT a.MCHT_NO,b.MCHT_NM,a.MCHT_NO||' - '||b.MCHT_NM,c.BRH_ID||' - '||c.BRH_NAME,"
				+ "CHECK_TIME,CHECK_OPR,CHECK_STATUS,MCHT_CASE_DESP,REFUSE_REASON,DAY_AVE_AMT,MON_AVE_AMT,SIG_MIN_AMT,SIG_MAX_AMT,"
				+ "SERV_DISP,APPLY_REASON,APPLY_OPR,APPLY_TIME FROM TBL_WHITE_MCHT_CHECK a "
				+ "LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO=b.MCHT_NO "
				+ "LEFT JOIN TBL_BRH_INFO c on b.BANK_NO=c.BRH_ID"
				+ whereSql
				+ " ORDER BY CHECK_TIME desc";
		String countSql = "SELECT COUNT(*) FROM TBL_WHITE_MCHT_CHECK a "
				+ "LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO=b.MCHT_NO "
				+ "LEFT JOIN TBL_BRH_INFO c on b.BANK_NO=c.BRH_ID" + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 结算通道信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2012-04-01
	 * @author xupengfei
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPayChannelInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryChannelId = request.getParameter("queryChannelId");
		String queryChannelFlag = request.getParameter("queryChannelFlag");
		String queryChannelSta = request.getParameter("queryChannelSta");
		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");

		if (isNotEmpty(queryChannelId)) {
			whereSql.append(" AND a.CHANNEL_ID").append(" LIKE '%")
					.append(queryChannelId).append("%'");
		}
		if (isNotEmpty(queryChannelFlag)) {
			whereSql.append(" AND CHANNEL_FLAG").append(" = ").append("'")
					.append(queryChannelFlag).append("' ");
		}
		if (isNotEmpty(queryChannelSta)) {
			whereSql.append(" AND CHANNEL_STA").append(" = ").append("'")
					.append(queryChannelSta).append("' ");
		}
		String sql = "SELECT CHANNEL_ID,CHANNEL_FLAG,CHANNEL_NAME,CHANNEL_STA,(SELECT COUNT(*) FROM TBL_CHANNEL_CNAPS_MAP b WHERE a.CHANNEL_ID = b.CHANNEL_ID) AS BELOW_CNAPS_NUM,"
				+ "ACCT_NO,ACCT_NM,MCHT_NO,MCHT_NM "
				+ "FROM TBL_PAY_CHANNEL_INFO a"
				+ whereSql
				+ " ORDER BY CHANNEL_ID ";
		String countSql = "SELECT COUNT(*) FROM TBL_PAY_CHANNEL_INFO a"
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 开户行结算通道关联信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2012-04-01
	 * @author xupengfei
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getChannelCnapsMap(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryCnapsId = request.getParameter("queryCnapsId");
		String queryChannelId = request.getParameter("queryChannelId");
		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");

		if (isNotEmpty(queryCnapsId)) {
			whereSql.append(" AND a.CNAPS_ID").append(" = '")
					.append(queryCnapsId).append("' ");
		}
		if (isNotEmpty(queryChannelId)) {
			whereSql.append(" AND a.CHANNEL_ID").append(" = '")
					.append(queryChannelId).append("'");
		}
		String sql = "SELECT NVL(b.CNAPS_ID,'*'),NVL(b.CNAPS_NAME,'*'),c.CHANNEL_ID,c.CHANNEL_NAME FROM TBL_CHANNEL_CNAPS_MAP a "
				+ "LEFT JOIN TBL_CNAPS_INFO b ON a.CNAPS_ID = b.CNAPS_ID "
				+ "LEFT JOIN TBL_PAY_CHANNEL_INFO c ON a.CHANNEL_ID = c.CHANNEL_ID "
				+ whereSql + " ORDER BY a.CHANNEL_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_CHANNEL_CNAPS_MAP a"
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 资金结算统计查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getAmtSettleCountInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryAmtTransLow = request.getParameter("queryAmtTransLow");
		String queryAmtTransUp = request.getParameter("queryAmtTransUp");
		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		String queryBrhId = request.getParameter("queryBrhId");

		StringBuffer whereSql = new StringBuffer(
				" where MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");
		StringBuffer whereSqlAmt = new StringBuffer(" where 1=1 ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryAmtTransLow)) {
			whereSqlAmt.append(" AND a.SUM_TXN_AMT ").append(" >= ")
					.append("'").append(queryAmtTransLow).append("'");
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSqlAmt.append(" AND a.SUM_TXN_AMT ").append(" <= ")
					.append("'").append(queryAmtTransUp).append("'");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			// whereSql.append(" AND MCHT_NO").append(" = ").append("'").append(mchtNo).append("'");
			// whereSql.append(" AND MCHT_NO").append(" in ").append(CommonFunction.getBelowMchtByMchtNo(mchtNo));
			whereSql.append(
					" AND MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =")
					.append("'")
					.append(queryMchtNoNm)
					.append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
		}
		if (isNotEmpty(queryBrhId)) {
			// whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf a where a.bank_no = '"
			// + queryBrhId.trim() + "')");
			whereSql.append(" and MCHT_NO in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ queryBrhId.trim()
					+ "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
		}

		String sql = "SELECT '"
				+ startDate
				+ "','"
				+ endDate
				+ "',(b.MCHT_NO||' - '||b.MCHT_NM) as MCHT_NO_NM,"
				+ "(select d.brh_id||' - '||d.brh_name from TBL_BRH_INFO d where d.brh_id = trim(b.BANK_NO)) as brh_id,"
				+ "substr(c.SETTLE_ACCT,2),c.SETTLE_ACCT_NM,c.SETTLE_BANK_NM,substr(c.OPEN_STLNO,1,12),"
				+ "a.SUM_TXN_AMT,a.SUM_SETTLE_FEE1,a.SUM_SETTLE_AMT "
				+ "FROM (SELECT MCHT_NO,SUM(TXN_AMT) AS SUM_TXN_AMT,SUM(SETTLE_FEE1) AS SUM_SETTLE_FEE1,SUM(SETTLE_AMT) AS SUM_SETTLE_AMT FROM TBL_MCHNT_INFILE_DTL "
				+ whereSql.toString()
				+ " GROUP BY MCHT_NO) a "
				+ "left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO left join TBL_MCHT_SETTLE_INF c on a.MCHT_NO = c.MCHT_NO "
				+ whereSqlAmt.toString();
		sql = sql + " order by a.MCHT_NO desc";
		String countSql = "SELECT count(*) "
				+ "FROM (SELECT MCHT_NO,SUM(TXN_AMT) AS SUM_TXN_AMT,SUM(SETTLE_FEE1) AS SUM_SETTLE_FEE1,SUM(SETTLE_AMT) AS SUM_SETTLE_AMT FROM TBL_MCHNT_INFILE_DTL "
				+ whereSql.toString() + " GROUP BY MCHT_NO) a "
				+ whereSqlAmt.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8]
							.toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8]
							.toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					objects[9] = df.format(Double.parseDouble(objects[9]
							.toString()));
					objects[9] = CommonFunction.moneyFormat(objects[9]
							.toString());
				}
				if (objects[10] != null && !"".equals(objects[10])) {
					objects[10] = df.format(Double.parseDouble(objects[10]
							.toString()));
					objects[10] = CommonFunction.moneyFormat(objects[10]
							.toString());
				}
			}
		}

		if (dataList.size() > 0) {
			List<Object[]> amtList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql);
			Double amt8 = 0.00;
			Double amt9 = 0.00;
			Double amt10 = 0.00;
			Object[] obj = new Object[dataList.get(0).length];

			for (Object[] objects : amtList) {

				if (objects[8] != null && !"".equals(objects[8])) {
					amt8 += Double.parseDouble(objects[8].toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					amt9 += Double.parseDouble(objects[9].toString());
				}
				if (objects[10] != null && !"".equals(objects[10])) {
					amt10 += Double.parseDouble(objects[10].toString());
				}
			}
			// obj[0]="";
			obj[0] = "<font color='red'>总计</font>";
			obj[8] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt8)) + "</font>";
			obj[9] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt9)) + "</font>";
			obj[10] = "<font color='red'>"
					+ CommonFunction.moneyFormat(df.format(amt10)) + "</font>";

			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得商户风险级别
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskLvlDist(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select distinct RISK_LVL,RESVED from TBL_RISK_LVL order by RISK_LVL ";
		String countSql = "SELECT COUNT(distinct RISK_LVL) FROM TBL_RISK_LVL ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得监控模型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskParamInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		String riskLvl = request.getParameter("riskLvl");
		if (isNotEmpty(riskLvl)) {
			whereSql.append(" and RISK_LVL = '" + riskLvl + "' ");
		}
		String sql = "SELECT model_kind,model_seq,param_len,param_value,param_name from TBL_RISK_PARAM_INF "
				+ whereSql + " order by model_kind ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_PARAM_INF " + whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得风控模型信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "SELECT SA_MODEL_KIND,SA_MODEL_DESC from TBL_RISK_INF order by SA_MODEL_KIND ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_INF ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得监控模型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskParamDef(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		String riskGrpIdQuery = request.getParameter("riskGrpIdQuery");
		if (isNotEmpty(riskGrpIdQuery)) {
			whereSql.append(" AND b.sa_model_group = '" + riskGrpIdQuery + "' ");
		}
		String sql = "SELECT a.RISK_ID,a.PARAM_SEQ,a.PARAM_LEN,a.DEFAULT_VALUE,a.PARAM_NAME,b.sa_model_group "
				+ "FROM TBL_RISK_PARAM_DEF a left join tbl_risk_inf b on a.risk_id=b.sa_model_kind "
				+ whereSql + " ORDER BY a.RISK_ID,a.PARAM_SEQ ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_PARAM_DEF a left join tbl_risk_inf b on a.risk_id=b.sa_model_kind "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得银行卡黑名单
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBankCardBlack(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		String cardNoQuery = request.getParameter("cardNoQuery");
		if (isNotEmpty(cardNoQuery)) {
			whereSql.append(" AND CARD_NO LIKE '%" + cardNoQuery + "%' ");
		}
		String sql = "SELECT CARD_NO,INS_ID_CD,CARD_DIS,CARD_TP,BIN_NO,REMARK_INFO,CRT_OPR,CRT_TIME FROM TBL_BANK_CARD_BLACK "
				+ whereSql + " ORDER BY CRT_TIME ";
		String countSql = "SELECT COUNT(*) FROM TBL_BANK_CARD_BLACK "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得银行卡黑名单历史操作日志
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBankCardBlackOptLog(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		String startDate = request.getParameter("startDate");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND substr(OPT_TIME,1,8) >= '" + startDate + "' ");
		}
		String endDate = request.getParameter("endDate");
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND substr(OPT_TIME,1,8) <= '" + endDate + "' ");
		}
		String optFlagId = request.getParameter("optFlagId");
		if (isNotEmpty(optFlagId)) {
			whereSql.append(" AND OPT_FLAG = '" + optFlagId + "' ");
		}
		String cardNoId = request.getParameter("cardNoId");
		if (isNotEmpty(cardNoId)) {
			whereSql.append(" AND CARD_NO LIKE '%" + cardNoId + "%' ");
		}
		String sql = "SELECT OPT_TIME,OPR_ID,OPT_FLAG,CARD_NO,REMARK_INFO FROM TBL_BANK_CARD_BLACK_OPT_LOG "
				+ whereSql.toString() + " ORDER BY OPT_TIME DESC";
		String countSql = "SELECT COUNT(*) FROM TBL_BANK_CARD_BLACK_OPT_LOG "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得商户灰名单类别
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getGreyMchtSort(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "SELECT SORT_NO,SORT_NAME,CRT_OPR,CRT_TIME,UPD_OPR,UPD_TIME FROM TBL_GREY_MCHT_SORT ORDER BY SORT_NO ";
		String countSql = "SELECT COUNT(*) FROM TBL_GREY_MCHT_SORT ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得灰名单商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskGreyMcht(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE 1=1 ");
		String sortNoQuery = request.getParameter("sortNoQuery");
		if (isNotEmpty(sortNoQuery)) {
			whereSql.append(" AND SORT_NO = '" + sortNoQuery + "' ");
		}
		String sql = "SELECT a.MCHT_NO,b.MCHT_NM,REMARK_INFO,CRT_OPR,CRT_TIME FROM TBL_RISK_GREY_MCHT a LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO = b.MCHT_NO "
				+ whereSql.toString() + " ORDER BY CRT_TIME ";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_GREY_MCHT "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getMchtRiskAnalyticsSummary(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		StringBuffer whereBuffer = new StringBuffer(
				" WHERE MBI.MCHT_NO in (SELECT DISTINCT t.CARD_ACCP_ID FROM TBL_N_TXN_HIS t WHERE t.TXN_NUM IN ('1101', '1091') $STARTDATE$ $ENDDATE$) ");
		if (isNotEmpty(queryMchtNoNm)) {
			whereBuffer.append(" AND MBI.MCHT_NO='").append(queryMchtNoNm)
					.append("'");
		}

		String queryRiskLvl = request.getParameter("queryRiskLvl");
		if (isNotEmpty(queryRiskLvl)) {
			whereBuffer.append(" AND MBI.RISL_LVL='").append(queryRiskLvl)
					.append("'");
		}

		StringBuffer sqlBuffer = new StringBuffer()
				.append("SELECT MBI.MCHT_NO, MBI.MCHT_NM, (SELECT DISTINCT RL.RESVED FROM TBL_RISK_LVL RL WHERE RL.RISK_LVL=MBI.RISL_LVL) RISK_LVL,")
				.append(" (SELECT SUM(NVL(TO_NUMBER(TRIM(t.AMT_TRANS))/100, 0)) FROM TBL_N_TXN_HIS t WHERE t.CARD_ACCP_ID = MBI.MCHT_NO AND t.TXN_NUM IN ('1101', '1091') $STARTDATE$ $ENDDATE$ GROUP BY t.CARD_ACCP_ID) TXN_AMT,")
				.append(" (SELECT NVL(COUNT(*), 0) FROM TBL_N_TXN_HIS t WHERE t.CARD_ACCP_ID = MBI.MCHT_NO AND t.TXN_NUM IN ('1101', '1091') $STARTDATE$ $ENDDATE$ GROUP BY t.CARD_ACCP_ID) TXN_CNT,")
				.append(" (SELECT NVL(COUNT(AM.CARD_ACCP_ID),0) FROM TBL_ALARM_MCHT AM, TBL_RISK_ALARM t WHERE AM.CARD_ACCP_ID = MBI.MCHT_NO AND t.ALARM_ID = AM.ALARM_ID AND t.ALARM_SEQ = AM.ALARM_SEQ AND SUBSTR(AM.ALARM_ID,9,3) IN (SELECT DISTINCT RI.SA_MODEL_KIND FROM TBL_RISK_INF RI WHERE misc='3') $STARTRISKDATE$ $ENDRISKDATE$ GROUP BY AM.CARD_ACCP_ID) FRT_ALM_CNT,")
				.append(" (SELECT NVL(COUNT(AM.CARD_ACCP_ID),0) FROM TBL_ALARM_MCHT AM, TBL_RISK_ALARM t WHERE AM.CARD_ACCP_ID = MBI.MCHT_NO AND t.ALARM_ID = AM.ALARM_ID AND t.ALARM_SEQ = AM.ALARM_SEQ AND SUBSTR(AM.ALARM_ID,9,3) IN (SELECT DISTINCT RI.SA_MODEL_KIND FROM TBL_RISK_INF RI WHERE misc='2') $STARTRISKDATE$ $ENDRISKDATE$ GROUP BY AM.CARD_ACCP_ID) SED_ALM_CNT,")
				.append(" (SELECT NVL(COUNT(AM.CARD_ACCP_ID),0) FROM TBL_ALARM_MCHT AM, TBL_RISK_ALARM t WHERE AM.CARD_ACCP_ID = MBI.MCHT_NO AND t.ALARM_ID = AM.ALARM_ID AND t.ALARM_SEQ = AM.ALARM_SEQ AND SUBSTR(AM.ALARM_ID,9,3) IN (SELECT DISTINCT RI.SA_MODEL_KIND FROM TBL_RISK_INF RI WHERE misc='1') $STARTRISKDATE$ $ENDRISKDATE$ GROUP BY AM.CARD_ACCP_ID) THD_ALM_CNT,")
				.append(" (SELECT NVL(COUNT(AM.CARD_ACCP_ID),0) FROM TBL_ALARM_MCHT AM, TBL_RISK_ALARM t WHERE AM.CARD_ACCP_ID = MBI.MCHT_NO AND t.ALARM_ID = AM.ALARM_ID AND t.ALARM_SEQ = AM.ALARM_SEQ $STARTRISKDATE$ $ENDRISKDATE$ GROUP BY AM.CARD_ACCP_ID) ALL_ALM_CNT,")
				.append(" (SELECT SUM(NVL(TO_NUMBER(TRIM(t.AMT_TRANS))/100, 0)) FROM TBL_ALARM_TXN t, TBL_RISK_ALARM RA WHERE t.CARD_ACCP_ID = MBI.MCHT_NO AND t.TXN_NUM IN ('1101', '1091') AND t.ALARM_ID = RA.ALARM_ID AND t.ALARM_SEQ = RA.ALARM_SEQ AND RA.CHEAT_FLAG = 1 $STARTDATE$ $ENDDATE$ GROUP BY t.CARD_ACCP_ID) CHEAT_AMT,")
				.append(" (SELECT NVL(COUNT(t.CARD_ACCP_ID),0) FROM TBL_ALARM_TXN t, TBL_RISK_ALARM RA WHERE t.CARD_ACCP_ID = MBI.MCHT_NO AND t.TXN_NUM IN ('1101', '1091') AND t.ALARM_ID=RA.ALARM_ID AND t.ALARM_SEQ=RA.ALARM_SEQ AND RA.CHEAT_FLAG=1 $STARTDATE$ $ENDDATE$ GROUP BY t.CARD_ACCP_ID) CHEAT_CNT,")
				.append(" 0, 0");

		String from = " FROM TBL_MCHT_BASE_INF MBI";
		String orderBy = " ORDER BY MBI.MCHT_NO";
		String sql = sqlBuffer.append(from).append(whereBuffer).append(orderBy)
				.toString();

		StringBuffer countSqlBuffer = new StringBuffer("SELECT count(*) ");
		String countSql = countSqlBuffer.append(from).append(whereBuffer)
				.toString();

		String startDate = request.getParameter("startDate");
		if (isNotEmpty(startDate)) {
			String andStartDate = " AND t.INST_DATE>='" + startDate + "000000'";
			String andStartRiskDate = " AND t.RISK_DATE >='" + startDate + "'";

			sql = sql.replace("$STARTDATE$", andStartDate);
			sql = sql.replace("$STARTRISKDATE$", andStartRiskDate);

			countSql = countSql.replace("$STARTDATE$", andStartDate);
			countSql = countSql.replace("$STARTRISKDATE$", andStartRiskDate);
		} else {
			sql = sql.replace("$STARTDATE$", "");
			sql = sql.replace("$STARTRISKDATE$", "");
			countSql = countSql.replace("$STARTDATE$", "");
			countSql = countSql.replace("$STARTRISKDATE$", "");
		}

		String endDate = request.getParameter("endDate");
		if (isNotEmpty(endDate)) {
			String andEndDate = " AND t.INST_DATE<='" + endDate + "2359559'";
			String andEndRiskDate = " AND t.RISK_DATE <= '" + endDate + "'";

			sql = sql.replace("$ENDDATE$", andEndDate);
			sql = sql.replace("$ENDRISKDATE$", andEndRiskDate);

			countSql = countSql.replace("$ENDDATE$", andEndDate);
			countSql = countSql.replace("$ENDRISKDATE$", andEndRiskDate);
		} else {
			sql = sql.replace("$ENDDATE$", "");
			sql = sql.replace("$ENDRISKDATE$", "");
			countSql = countSql.replace("$ENDDATE$", "");
			countSql = countSql.replace("$ENDRISKDATE$", "");
		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		for (Object[] objects : dataList) {
			if (objects[3] != null && objects[9] != null) {
				objects[11] = ((BigDecimal) objects[9]).divide(
						(BigDecimal) objects[3], 4, RoundingMode.HALF_UP);
			}
			if (objects[3] != null) {
				objects[3] = CommonFunction.moneyFormat(objects[3].toString());
			}
			if (objects[9] != null) {
				objects[9] = CommonFunction.moneyFormat(objects[9].toString());
			}

			if (objects[4] != null && objects[10] != null) {
				objects[12] = ((BigDecimal) objects[10]).divide(
						(BigDecimal) objects[4], 4, RoundingMode.HALF_UP);
			}

			if (objects[5] == null) {
				objects[5] = 0;
			}
			if (objects[6] == null) {
				objects[6] = 0;
			}
			if (objects[7] == null) {
				objects[7] = 0;
			}
			if (objects[8] == null) {
				objects[8] = 0;
			}
			if (objects[9] == null) {
				objects[9] = 0;
			}
			if (objects[10] == null) {
				objects[10] = 0;
			}
		}

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getMchtRiskAnalyticsDetail(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String joinDate = request.getParameter("joinDate");
		String queryRiskLvl = request.getParameter("queryRiskLvl");
		String queryRule = request.getParameter("queryRule");
		String queryIsCheat = request.getParameter("queryIsCheat");
		String queryPan = request.getParameter("queryPan");

		StringBuffer whereBuffer = new StringBuffer(
				" WHERE ATXN.CARD_ACCP_ID = MBI.MCHT_NO AND ATXN.TXN_NUM IN ('1101', '1091')");
		if (isNotEmpty(queryMchtNoNm)) {
			whereBuffer.append(" AND ATXN.CARD_ACCP_ID='")
					.append(queryMchtNoNm).append("'");
		}
		if (isNotEmpty(queryRiskLvl)) {
			whereBuffer.append(" AND MBI.RISL_LVL='").append(queryRiskLvl)
					.append("'");
		}
		if (isNotEmpty(startDate)) {
			startDate = startDate + "000000";
			whereBuffer.append(" AND ATXN.INST_DATE>='").append(startDate)
					.append("'");
		}
		if (isNotEmpty(endDate)) {
			endDate = endDate + "2359559";
			whereBuffer.append(" AND ATXN.INST_DATE<='").append(endDate)
					.append("'");
		}
		if (isNotEmpty(joinDate)) {
			whereBuffer.append(" AND SUBSTR(MBI.REC_CRT_TS, 0, 8) ='")
					.append(joinDate).append("'");
		}
		if (isNotEmpty(queryPan)) {
			whereBuffer.append(" AND ATXN.PAN ='").append(queryPan).append("'");
		}
		if (isNotEmpty(queryIsCheat)) {
			whereBuffer.append(" AND ATXN.CHEAT_FLAG ='").append(queryIsCheat)
					.append("'");
		}
		if (isNotEmpty(queryRule)) {
			whereBuffer.append(" AND SUBSTR(ATXN.ALARM_ID,9,3) ='")
					.append(queryRule).append("'");
		}

		StringBuffer sqlBuffer = new StringBuffer()
				.append("SELECT MBI.MCHT_NO, MBI.MCHT_NM, (SELECT DISTINCT RL.RESVED FROM TBL_RISK_LVL RL WHERE RL.RISK_LVL=MBI.RISL_LVL) RISK_LVL, SUBSTR(ATXN.ALARM_ID,9,3) ALARM_ID, ATXN.CHEAT_FLAG, ")
				.append(" ATXN.PAN, TO_NUMBER(NVL(trim(ATXN.AMT_TRANS),0))/100, SUBSTR(ATXN.INST_DATE, 0, 8)  TXN_DATE, SUBSTR(ATXN.INST_DATE, 9, 14) TXN_TIME, ")
				.append(" (select TN.TXN_NAME from TBL_TXN_NAME TN where TN.TXN_NUM=ATXN.txn_num) TXN_NAME, ATXN.TRANS_STATE, ATXN.CARD_ACCP_TERM_ID ");
		String from = " FROM TBL_ALARM_TXN ATXN, TBL_MCHT_BASE_INF MBI";
		String orderBy = " ORDER BY ATXN.INST_DATE";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(
						sqlBuffer.append(from).append(whereBuffer)
								.append(orderBy).toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		for (Object[] objects : dataList) {
			objects[6] = CommonFunction.moneyFormat(objects[6].toString());
		}

		StringBuffer countSqlBuffer = new StringBuffer(
				"SELECT count(MBI.MCHT_NO)");
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSqlBuffer.append(from).append(whereBuffer).toString());

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 一级商户限额信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getFirstMchtTxn(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryFirstMchtCd = request.getParameter("queryFirstMchtCd");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryFirstMchtCd)) {
			whereSql.append(" AND a.FIRST_MCHT_CD ='").append(queryFirstMchtCd)
					.append("' ");
		}

		String sql = "SELECT a.FIRST_MCHT_CD ,b.FIRST_MCHT_NM ,TXN_DATE ,TXN_NUM ,TXN_AMT ,AMT_LIMIT "
				+ "FROM TBL_FIRST_MCHT_TXN a LEFT JOIN TBL_FIRST_MCHT_INF b ON a.FIRST_MCHT_CD = b.FIRST_MCHT_CD "
				+ whereSql.toString();
		String countSql = " select count(*) from TBL_FIRST_MCHT_TXN a "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		for (Object[] objects : dataList) {
			objects[3] = objects[3].toString() + "笔";
			objects[4] = CommonFunction.moneyFormat(objects[4].toString());
			objects[5] = CommonFunction.moneyFormat(objects[5].toString());
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询路由批量导入回执信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-26下午09:40:21
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRouteBlukImpRecInf(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		StringBuffer whereSql = new StringBuffer();
		whereSql.append("  where MISC1 = '")
				.append(TblMchntInfoConstants.BLUK_IMP_ROUTE).append("'");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND substr(BLUK_DATE,0,8) ").append(" >= ")
					.append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND substr(BLUK_DATE,0,8)").append(" <= ")
					.append("'").append(endDate).append("'");
		}

		String sql = "SELECT BATCH_NO, substr(BLUK_DATE,0,8), substr(BLUK_DATE,9,6), "
				+ "(select b.BRH_ID||'-'||b.BRH_NAME from TBL_BRH_INFO b where trim(b.BRH_ID)=trim(a.BRH_ID)) as bankNoName, "
				+ "OPR_ID, BLUK_FILE_NAME, BLUK_MCHN_NUM, MISC3 "
				+ " FROM TBL_BLUK_IMP_RET_INF a " + whereSql.toString();
		sql = sql + " order by BLUK_DATE desc ";
		String countSql = "SELECT COUNT(*) FROM TBL_BLUK_IMP_RET_INF "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		List<Object[]> dataListNew = new ArrayList<Object[]>();
		Object[] objectNew;
		for (Object[] objects : dataList) {
			objectNew = new Object[objects.length + 18];
			for (int i = 0; i < objects.length; i++) {
				objectNew[i] = objects[i];
			}
			String[] routeRuleInfo = objects[7].toString().split("\\|", -1);
			for (int i = 7; i < objectNew.length; i++) {
				objectNew[i] = routeRuleInfo[i - 7];
			}
			dataListNew.add(objectNew);
		}

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataListNew;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机构分润费率配置 (名称-总)
	 * 
	 * @param begin
	 * @param request
	 * @return 2015-02-09
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhFeeCtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String discId = request.getParameter("queryDiscId");
		String discName = request.getParameter("queryDiscName");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (isNotEmpty(discId)) {
			whereSql.append(" and a.disc_id").append(" like '%").append(discId)
					.append("%' ");
		}
		if (isNotEmpty(discName)) {
			whereSql.append(" and a.disc_name").append(" like '%")
					.append(discName).append("%' ");
		}

		String sql = "select a.disc_id,a.disc_name,a.lst_upd_time,a.lst_upd_tlr,a.create_time "
				+ " from tbl_brh_fee_ctl a  "
				+ whereSql
				+ " order by a.disc_id ";

		String countSql = "select count(*) from tbl_brh_fee_ctl a " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 机构分润费率配置 (名称-总)
	 * 
	 * @param begin
	 * @param request
	 * @return 2015-02-09
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAgentFeeCtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		String sql = "select a.rate_id,a.fee_name,a.fee_type,a.fee_rate,a.amt1,a.amt2,a.misc,a.crt_time,a.crt_opr ,a.upt_time,a.upt_opr "
				+ " from tbl_profit_rate_info a  "
				+ whereSql
				+ " order by a.rate_id ";

		String countSql = "select count(*) from tbl_profit_rate_info a " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 提现费率配置 (名称-总)
	 * 
	 * @param begin
	 * @param request
	 * @return 
	 * @author 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhRateCashInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		String sql = "select a.rate_id,a.name,a.type,a.rate,a.crt_time,a.crt_opr ,a.upt_time,a.upt_opr "
				+ " from TBL_CASH_RATE_INF a  "
				+ whereSql
				+ " order by a.rate_id ";

		String countSql = "select count(*) from TBL_CASH_RATE_INF a " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 提现费率配置 (名称-总)
	 * 
	 * @param begin
	 * @param request
	 * @return 
	 * @author 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhCashRateInfSel(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1 = 1 ");
		 
		String tableName = " TBL_BRH_CASH_INF a ";
		String brhId = request.getParameter("brhId");
		String seqId = request.getParameter("seqId");
		
		if (isNotEmpty(brhId)) {
			whereSql.append(" and a.brh_Id ='").append(brhId).append("' ");
		}
		
		if (isNotEmpty(seqId)) {
			whereSql.append(" and a.seq_Id ='").append(seqId).append("' ");
			tableName = " TBL_BRH_CASH_INF_HIS a ";
		}
		String sql = "select a.rate_id,a.name,a.type,a.rate"
				+ " from " + tableName
				+ whereSql
				+ " order by a.rate_id ";

		String countSql = "select count(*) from " + tableName + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 机构分润费率配置 (名称-总)
	 * 
	 * @param begin
	 * @param request
	 * @return 2015-02-09
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAgentSelFeeCtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1 = 1 ");
		
		String brhId = request.getParameter("brhId");
		if (isNotEmpty(brhId)) {
			whereSql.append(" and tafc.agent_no ='").append(brhId)
					.append("' ");
		}
		
		String sql = "SELECT tari.disc_id,tari.fee_type,tari.rate_id,tari.fee_rate,tpri.fee_name FROM tbl_agent_rate_info tari,tbl_profit_rate_info tpri  WHERE tari.rate_id = tpri.rate_id AND tari.disc_id = (SELECT disc_id FROM tbl_agent_fee_cfg tafc "
				+ whereSql
				+ ") order by tari.disc_id ";

		String countSql = "SELECT count(*) FROM tbl_agent_rate_info tari  WHERE  tari.disc_id = (SELECT disc_id FROM tbl_agent_fee_cfg tafc " + whereSql+" ) ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 机构分润费率配置历史 (名称-总)
	 * 
	 * @param begin
	 * @param request
	 * @return 机构分润费率信息
	 * @author chenw
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAgentSelFeeCtlHis(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1 = 1 ");
		
		String brhId = request.getParameter("brhId");
		String seqId = request.getParameter("seqId");
		if (isNotEmpty(brhId)) {
			whereSql.append(" and tafc.agent_no ='").append(brhId)
					.append("' ");
		}
		if (isNotEmpty(seqId)) {
			whereSql.append(" and tafc.seq_id ='").append(seqId)
					.append("' ");
		}
		String sql = "SELECT tari.disc_id,tari.fee_type,tari.rate_id,tari.fee_rate,tpri.fee_name FROM tbl_agent_rate_info_his tari,tbl_profit_rate_info tpri  "
				+ "WHERE tari.rate_id = tpri.rate_id "
				+ " AND tari.seq_id='" + seqId + "'"
				+ " AND tari.disc_id = (SELECT disc_id FROM tbl_agent_fee_cfg_his tafc "
				+ whereSql
				+ ") order by tari.disc_id ";

		String countSql = "SELECT count(*) FROM tbl_agent_rate_info_his tari  WHERE  "
				+ " tari.seq_id='" + seqId + "'"
				+ "AND tari.disc_id = (SELECT disc_id FROM tbl_agent_fee_cfg_his tafc " + whereSql+" ) ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 机构分润费率配置 (名称-总)
	 * 
	 * @param begin
	 * @param request
	 * @return 2015-02-09
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAgentSelForUpdFeeCtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1 = 1 ");
		
		String brhId = request.getParameter("brhId");
		if (isNotEmpty(brhId)) {
			whereSql.append(" and tafc.agent_no ='").append(brhId)
					.append("' ");
		}
		
		String sql = "SELECT tpri.rate_id,tpri.fee_name,tpri.fee_type, CASE WHEN b.fee_rate IS NOT NULL THEN b.fee_rate ELSE tpri.fee_rate END AS fee_rate, CASE WHEN b.fee_rate IS NOT NULL THEN '1' ELSE '0' END AS selected FROM tbl_profit_rate_info tpri left JOIN (SELECT * FROM tbl_agent_rate_info tari where tari.disc_id = ( SELECT disc_id FROM tbl_agent_fee_cfg tafc "
				+ whereSql
				+ ")) b ON tpri.rate_id = b.rate_id";

		String countSql = "SELECT count(*) FROM tbl_profit_rate_info tpri";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 提现费率配置 (名称-总)
	 * 
	 * @param begin
	 * @param request
	 * @return 2015-02-09
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhCashRateForUpd(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1 = 1 ");
		
		String brhId = request.getParameter("brhId");
		if (isNotEmpty(brhId)) {
			whereSql.append(" and brh_id ='").append(brhId)
					.append("' ");
		}
		
		String sql = "SELECT t.rate_id,t.name,t.type, CASE WHEN a.rate IS NOT NULL THEN a.rate ELSE t.rate END AS rate, CASE WHEN a.rate IS NOT NULL THEN '1' ELSE '0' END AS selected "
				+ "FROM TBL_CASH_RATE_INF t  "
				+ "left JOIN (select * from TBL_BRH_CASH_INF "
				+ whereSql
				+ ") a on t.rate_id = a.rate_id";

		String countSql = "SELECT count(*) FROM TBL_CASH_RATE_INF t";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 机构分润费率配置 (明细)
	 * 
	 * @param begin
	 * @param request
	 * @return 2015-02-09
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhFeeCfg(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String discId = request.getParameter("discId");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		whereSql.append(" and a.disc_id").append(" = '").append(discId)
				.append("' ");
		String sql = "select a.disc_id,a.seq,a.name,a.enable_flag,a.mcht_no,"
				+ "decode(a.mcht_no,null,'*',a.mcht_no||(select '-'||b.mcht_nm from tbl_mcht_base_inf b where b.mcht_no=a.mcht_no)) as mcht_no_nm,"
				+ "a.mcht_grp,"
				+ "decode(a.mcht_grp,null,'*',a.mcht_grp||(select '-'||c.descr from tbl_inf_mchnt_tp_grp c where c.mchnt_tp_grp=a.mcht_grp)) as mcht_grp_nm,"
//				+ "a.join_type,"
//				+ "decode(a.join_beg_date,null,'*',to_char(to_date(join_beg_date,'yyyy-mm-dd'),'yyyy-mm-dd')) as start_date,"
//				+ "decode(a.join_end_date,null,'*',to_char(to_date(join_end_date,'yyyy-mm-dd'),'yyyy-mm-dd')) as end_date,"
//				+ "decode(a.mcht_fee_low,0,'*',to_char(a.mcht_fee_low,'90.99')) as mcht_low,"
//				+ "decode(a.mcht_fee_up,0,'*',to_char(a.mcht_fee_up,'90.99')) as mcht_up,"
//				+ "decode(a.mcht_cap_fee_low,0,'*',to_char(a.mcht_cap_fee_low,'99,999,999,990.99'))as mcht_cap_low,"
//				+ "decode(a.mcht_cap_fee_up,0,'*',to_char(a.mcht_cap_fee_up,'99,999,999,990.99')) as mcht_cap_up,"
//				promotionBegDate,promotionEndDate,baseAmtMonth,gradeAmtMonth,promotionRate,
				+ "decode(a.promotion_beg_date,null,'*',to_char(to_date(promotion_beg_date,'yyyy-mm-dd'),'yyyy-mm-dd')) as start_date,"
				+ "decode(a.promotion_end_date,null,'*',to_char(to_date(promotion_end_date,'yyyy-mm-dd'),'yyyy-mm-dd')) as end_date,"
				+ "decode(a.base_amt_month,0,'*',to_char(a.base_amt_month,'9,999,999,999,990.99')) as base_amt_month,"
				+ "decode(a.grade_amt_month,0,'*',to_char(a.grade_amt_month,'9,999,999,999,990.99')) as grade_amt_month,"
				+ "decode(a.promotion_rate,0,'*',to_char(a.promotion_rate,'999,999,999,990.999')) as promotion_rate,"
				+ "to_char(a.fee_rate,'90.999') as brh_rate,"
				+ "to_char(a.cap_fee_value,'99,999,999,990.99') as brh_rate_value,"
				+ "to_char(a.allot_rate,'90.999') as brh_allot_rate,"
				+ "a.lst_upd_time,a.lst_upd_tlr,a.create_time "
				+ " from tbl_brh_fee_cfg_zlf a  " + whereSql
				+ " order by disc_id,a.seq ";

		String countSql = "select count(*) from tbl_brh_fee_cfg_zlf a " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户维护信息日志查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblMchtBaseInfTmpLog(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryCardAccpId = request.getParameter("queryCardAccpId");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		String queryStartTm = request.getParameter("queryStartTm");
		String queryEndTm = request.getParameter("queryEndTm");
		if (StringUtil.isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND L_MCHT_NO ='").append(queryCardAccpId)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryStartTm)) {

			whereSql.append(" AND l_createdate >='").append(queryStartTm)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryEndTm)) {
			whereSql.append(" AND l_createdate <='").append(queryEndTm)
					.append("' ");
		}
		String sql = "select (select trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF t where  t.mcht_no=L_MCHT_NO )L_MCHT_NO,L_MANU_AUTH_FLAG,BE_MCHT_NM,BE_LICENCE_NO,BE_RISL_LVL,BE_MCHT_STATUS,BE_BANK_NO,BE_MCHT_CN_ABBR,BE_MCHT_GROUP_ID,"
				+ "BE_MCC,BE_FAX_NO,BE_ADDR,BE_MANAGER,(SELECT VALUE FROM CST_SYS_PARAM  WHERE KEY= BE_ARTIF_CERTIF_TP  and OWNER = 'CERTIFICATE' )BE_ARTIF_CERTIF_TP,BE_ENTITY_NO,BE_CONTACT,BE_COMMTEL,BE_ELECTRO_FAX,BE_AGR_BR,BE_SIGNINST_ID,"
				+ "BE_SETTLEACCT,BE_SETTLEBANKNM,BE_SETTLEACCTNM,BE_DISCCODE,"
				+ "AF_MCHT_NM,AF_LICENCE_NO,AF_RISL_LVL,AF_MCHT_STATUS,AF_BANK_NO,AF_MCHT_CN_ABBR,AF_MCHT_GROUP_ID,AF_MCC,AF_FAX_NO,AF_ADDR,AF_MANAGER,"
				+ "(SELECT VALUE FROM CST_SYS_PARAM  WHERE KEY= AF_ARTIF_CERTIF_TP  and OWNER = 'CERTIFICATE')AF_ARTIF_CERTIF_TP,AF_ENTITY_NO,AF_CONTACT,AF_COMMTEL,AF_ELECTRO_FAX,AF_AGR_BR,AF_SIGNINST_ID,AF_SETTLEACCT,AF_SETTLEBANKNM,AF_SETTLEACCTNM,AF_DISCCODE,L_CREATEDATE,"
				+ "L_CREATEPEOPLE,L_UPTS,L_UPOPRID,L_UPTYPE,BE_OPENSTLNO,AF_OPENSTLNO,(case  BE_CLEARTYPENM when '0' then '对公账户' when '1' then '对私账户' end)BE_CLEARTYPENM,(case  AF_CLEARTYPENM when '0' then '对公账户' when '1' then '对私账户' end)AF_CLEARTYPENM from tbl_mcht_base_inf_tmp_log "
				+ whereSql.toString() + " order by l_createdate desc ";
		String countSql = "select count(*) from tbl_mcht_base_inf_tmp_log "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户信息报表查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchnReport(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String LMchtNo = request.getParameter("LMchtNo");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(LMchtNo)) {
			whereSql.append(" AND m.mcht_no ='").append(LMchtNo).append("' ");
		}

		String sql = "select m.mcht_no,m.mcht_nm,(SELECT DISTINCT  trim(RISK_LVL)||' - '||trim(RESVED) FROM TBL_RISK_LVL where risk_lvl = m.risl_lvl)risl_lvl,m.sign_inst_id,m.mcc,"
				+ "(select BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE trim(BRH_ID)=m.bank_no)bank_no from tbl_mcht_base_inf m"
				+ whereSql.toString();
		String countSql = " select count(*) from tbl_mcht_base_inf a "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		/*
		 * for (Object[] objects : dataList) { objects[3] =
		 * objects[3].toString()+"笔"; objects[4] =
		 * CommonFunction.moneyFormat(objects[4].toString()); objects[5] =
		 * CommonFunction.moneyFormat(objects[5].toString()); }
		 */
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 统计相同MCC商户数量
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSameMccCount(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryMcc = request.getParameter("queryMcc");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryMcc)) {
			whereSql.append(" AND mcc ='").append(queryMcc).append("' ");
		}
		String sql = "select count(*),(select mchnt_tp ||' - '||descr from tbl_inf_mchnt_tp where mchnt_tp=mcc ) mcc from tbl_mcht_base_inf "
				+ whereSql.toString() + " group by mcc order by count(*) desc";
		String countSql = " select count(*) from (select count(*),mcc from tbl_mcht_base_inf  "
				+ whereSql.toString() + "  group by mcc )";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 统计相同MCC商户在某段时间内的交易总金额
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSameMccAmt(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryStartTm = request.getParameter("queryStartTm");
		String queryEndTm = request.getParameter("queryEndTm");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryStartTm)) {
			whereSql.append(" AND a.DATE_SETTLMT >='").append(queryStartTm)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryStartTm)) {
			whereSql.append(" AND a.DATE_SETTLMT <='").append(queryEndTm)
					.append("' ");
		}
		String sql = "select count(*),sum(abs(a.MCHT_AT_C-a.MCHT_AT_D)) as txn_amt,(select mchnt_tp ||' - '||descr from tbl_inf_mchnt_tp where mchnt_tp=mcc )mcc from TBL_ALGO_DTL a "
				+ "left join tbl_mcht_base_inf b on a.mcht_cd=b.mcht_no "
				+ whereSql + "group by mcc order by txn_amt desc";
		String countSql = " select count(*) from (select count(*),sum(abs(a.MCHT_AT_C-a.MCHT_AT_D)) as txn_amt,b.mcc from TBL_ALGO_DTL a "
				+ "left join tbl_mcht_base_inf b on a.mcht_cd=b.mcht_no "
				+ whereSql + "group by mcc )";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 统计相同MCC商户在某段时间内的交易总金额（新）
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSameMccAmt2(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryStartTm = request.getParameter("queryStartTm");
		String queryEndTm = request.getParameter("queryEndTm");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryStartTm)) {
			whereSql.append(" AND a.DATE_STLM >='").append(queryStartTm)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryStartTm)) {
			whereSql.append(" AND a.DATE_STLM <='").append(queryEndTm)
					.append("' ");
		}
		String sql = "select count(*),sum(abs(a.MCHT_AMT_CR-a.MCHT_AMT_DB)) as txn_amt,(select mchnt_tp ||' - '||descr from tbl_inf_mchnt_tp where mchnt_tp=mcc )mcc from tbl_clear_dtl a "
				+ "left join tbl_mcht_base_inf b on a.MCHT_ID=b.mcht_no "
				+ whereSql + "group by mcc order by txn_amt desc";
		String countSql = " select count(*) from (select count(*),sum(abs(a.MCHT_AMT_CR-a.MCHT_AMT_DB)) as txn_amt,b.mcc from tbl_clear_dtl a "
				+ "left join tbl_mcht_base_inf b on a.MCHT_ID=b.mcht_no "
				+ whereSql + "group by mcc )";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 一级商户交易统计
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getFirstMccCount(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryTxnKey = request.getParameter("queryTxnKey");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryTxnKey) && !"请选择".equals(whereSql.toString())) {
			whereSql.append("and substr(a.txn_key, 26, 12) in ('")
					.append(queryTxnKey).append("')");
		}
		String sql = "select (select substr(FIRST_MCHT_CD,4, 12)||' - '||trim(FIRST_MCHT_NM)  from TBL_FIRST_MCHT_INF where p.txnKey =  substr(FIRST_MCHT_CD,4, 12))txnKey　,"

				+ "settlmt ,BISHU,JIAOYIJINE from(select * from (select substr(a.txn_key, 26, 12) txnKey, substr(a.date_settlmt,1,8) settlmt, count(*) as bishu, (sum(a.mcht_at_c) - sum(a.mcht_at_d)) as jiaoyijine from tbl_algo_dtl a "
				+ whereSql
				+ " group by substr(a.txn_key, 26, 12), substr(a.date_settlmt,1,8) order by substr(a.date_settlmt,1,8)) o"
				+ " union "
				+ "select * from (select substr(a.txn_key, 26, 12)txnKey, substr(a.date_settlmt,1,8) settlmt, count(*) as bishu, (sum(a.mcht_at_c) - sum(a.mcht_at_d)) as jiaoyijine from tbl_algo_dtl_his a  "
				+ whereSql
				+ "group by substr(a.txn_key, 26, 12), substr(a.date_settlmt,1,8) order by substr(a.date_settlmt,1,8))) p ";
		String countSql = " select count(*) from (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 一级商户交易统计(新)
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getFirstMccCount2(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryTxnKey = request.getParameter("queryTxnKey");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryTxnKey) && !"请选择".equals(whereSql)) {
			whereSql.append("and substr(a.txn_key, 26, 12) in ('")
					.append(queryTxnKey).append("')");
		}
		String sql = "select (select substr(FIRST_MCHT_CD,4, 12)||' - '||trim(FIRST_MCHT_NM)  from TBL_FIRST_MCHT_INF where p.txnKey =  substr(FIRST_MCHT_CD,4, 12))txnKey　,"

				+ "settlmt ,BISHU,JIAOYIJINE from(select * from (select substr(a.txn_key, 26, 12) txnKey, substr(a.DATE_STLM,1,8) settlmt, count(*) as bishu, (sum(a.MCHT_AMT_CR) - sum(a.MCHT_AMT_DB)) as jiaoyijine from tbl_clear_dtl a "
				+ whereSql
				+ " group by substr(a.txn_key, 26, 12), substr(a.DATE_STLM,1,8) order by substr(a.DATE_STLM,1,8)) o) p ";
		String countSql = " select count(*) from (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 入网提醒查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtLimitDate(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String mchtNo = request.getParameter("mchtNo");
		String queryStartDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		StringBuffer whereSql = new StringBuffer("where t.status=1 ");
		if (StringUtil.isNotEmpty(mchtNo)) {

			whereSql.append(" AND t.mcht_no ='").append(mchtNo).append("' ");
		}
		if (StringUtil.isNotEmpty(queryStartDate)) {

			whereSql.append(" AND t.limit_date >='").append(queryStartDate)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" AND t.limit_date<='").append(endDate)
					.append("' ");
		}

		String sql =" select trim(b.MCHT_NO) || ' - ' || trim(b.MCHT_NM) mchtName, t.mcht_no, t.limit_date, t.status, t.reserved, t.crt_opr,t.crt_time, t.upd_opr, t.upd_time, t.misc, ceil((to_date(t.limit_date, 'yyyy-mm-dd hh24-mi-ss') - sysdate)) ceilDate "
				+ "  from tbl_mcht_limit_date t "
				+ "  inner join TBL_MCHT_BASE_INF b "
				+ "     on trim(t.mcht_no) = trim(b.mcht_no) "
				+ whereSql + "order by ceilDate";
		String countSql = " select count(*) from (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 中信小额支付
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSmallPay(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryAcctNo = request.getParameter("queryAcctNo");
		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(queryAcctNo)) {
			whereSql.append(" AND ACCT_NO").append(" = '").append(queryAcctNo)
					.append("' ");
		}

		String sql = "SELECT ACCT_NO,ACCT_NM,CNAPS_ID,CNAPS_NAME,CRT_OPR,CRT_TIME,RESERVED FROM TBL_PAY_TYPE_SMALL "
				+ whereSql;

		String countSql = "SELECT COUNT(*) FROM TBL_PAY_TYPE_SMALL " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 当日交易查询for 移动端
	 * 
	 * @param begin
	 * @param request
	 * @return 2015-04-28下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getTxnTodayInfo_wap(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String queryTransState = request.getParameter("queryTransState");
		String queryRespCode = request.getParameter("queryRespCode");
		String querySettleBrh = request.getParameter("querySettleBrh");

		StringBuffer whereSql = new StringBuffer(
				" where 1=1 and f.txn_num in ('1011','1091','1101','1111','1121','1141',"
						+ "'2011','2091','2101','2111','2121','3011','3091','3101',"
						+ "'3111','3121','4011','4091','4101','4111','4121','5151','5161') ");

		whereSql.append(" and f.inst_date >=")
				.append(CommonFunction.getCurrentDate()).append("000000 ");
		whereSql.append(" and f.inst_date <=")
				.append(CommonFunction.getCurrentDate()).append("999999 ");
		if (isNotEmpty(queryTransState)) {
			if (Constants.TransState.equals(queryTransState)) {
				whereSql.append(" AND f.trans_state  !='1'  and f.trans_state  !='0'  and f.trans_state  !='R' ");
			} else {
				whereSql.append(" AND f.trans_state").append(" = ").append("'")
						.append(queryTransState).append("'");
			}
		}
		if (isNotEmpty(queryRespCode)) {
			whereSql.append(" AND f.RESP_CODE").append(" = ").append("'")
					.append(queryRespCode).append("' ");
		}

		if (isNotEmpty(querySettleBrh)) {
			whereSql.append(" AND f.tdestid").append(" = ").append("'")
					.append(querySettleBrh).append("' ");
		}

		String sql = "SELECT "
				+ "	substr(f.inst_date,1,8) as txn_date,"
				+ "	substr(f.inst_date,9,6) as txn_time,"
				+ "	(select TXN_NAME from TBL_TXN_NAME where TXN_NUM =f.txn_num ),"
				+ "	f.trans_state,"
				+ "	f.RESP_CODE,"
				+ " f.tdestid,"
				+ "	to_char(case when f.txn_num in('5151','3101','2101','3091','2091') "
				+ " then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 "
				+ " else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 "
				+ " end ,'99,999,999,990.99') as amt,"
				+ "	f.pan,"
				+ "	(SELECT c.brh_id||' - '||c.brh_name FROM TBL_BRH_INFO c WHERE c.brh_id in (select bank_no from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id))AS brh_id_name,"
				+ "	f.card_accp_id,"
				+ "	(select MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO=f.card_accp_id) as mcht_name,"
				+ "	f.card_accp_term_id," + "	f.term_ssn," + "	f.sys_seq_num "
				+ " from tbl_n_txn f ";

		sql = sql + whereSql.toString();
		sql = sql + " order by f.inst_date desc";
		String countSql = "SELECT count(*) FROM tbl_n_txn  f ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_SELECT_COUNT);

		if (dataList.size() > 0) {
			String amtSql = "select "
					+ " to_char("
					+ "nvl(sum( case when f.txn_num ='5151' then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 "
					+ "else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end),0) "
					+ ",'99,999,999,990.99') as amt_total "
					+ "	from tbl_n_txn f  "
					+ whereSql.toString()
					+ " and f.txn_num in ('1101','1091','5151') and f.trans_state in ( '1','R') and f.REVSAL_FLAG!='1' and f.CANCEL_FLAG!='1' ";
			String amt = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
					amtSql);

			Object[] obj = new Object[dataList.get(0).length];
			obj[0] = "";
			obj[1] = "<font color='red'>交易总计</font>";
			obj[2] = "<font color='red'>总笔数:</font>";
			obj[3] = "<font color='red'>" + count + "</font>";
			obj[4] = "";
			obj[5] = "<font color='red'>总金额:</font>";
			obj[6] = "<font color='red'>" + amt + "</font>";

			dataList.add(obj);
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 交易通道维护
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getFirstBrhDestInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String queryDestId = request.getParameter("queryDestId");
		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(queryDestId)) {
			whereSql.append(" AND DEST_ID").append(" LIKE '%")
					.append(queryDestId).append("%' ");
		}

		String sql = "SELECT DEST_ID,FIRST_BRH_ID,FIRST_BRH_NAME,BAK1,BAK2 FROM TBL_FIRST_BRH_DEST_ID "
				+ whereSql.toString();

		String countSql = "SELECT COUNT(*) FROM TBL_FIRST_BRH_DEST_ID "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 准退货查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblZth(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");
		String querySysSeqNum = request.getParameter("querySysSeqNum");
		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryEflag = request.getParameter("queryEflag");

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND INST_DATE").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND F42").append(" = ").append("'")
					.append(queryCardAccpId).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND F41").append(" like ").append("'%")
					.append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND F2").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(querySysSeqNum)) {
			whereSql.append(" AND SYS_SEQ_NUM").append(" like ").append("'%")
					.append(querySysSeqNum).append("%'");
		}
		if (isNotEmpty(queryEflag)) {
			whereSql.append(" AND EFLAG").append(" = ").append("'")
					.append(queryEflag).append("'");
		}

		String sql = "SELECT INST_DATE,INST_TIME,SYS_SEQ_NUM,MSG_SRC_ID,MSG_DEST_ID,TXN_NUM,RSPCODE,RSPDSP,"
				+ "F60,F11_POS,F2,F4,NFEE,SAMT,AMT1,EFLAG,DDATE,F22,F37_POS,F49,F41,F42||' - '||MCHT_NAME,F41_SY,F42_SY,"
				+ "O_AMT,O_F60,O_F11,O_F13,O_F12,O_F22,O_F37_POS,O_F37_SY,O_F49,BAK1 FROM TBL_ZTH "
				+ whereSql.toString()
				+ "ORDER BY INST_DATE DESC,INST_TIME DESC";

		String countSql = "SELECT COUNT(*) FROM TBL_ZTH " + whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			if (objects[11] != null && !"".equals(objects[11])) {
				objects[11] = CommonFunction
						.moneyFormat(objects[11].toString());
			}
			if (objects[12] != null && !"".equals(objects[12])) {
				objects[12] = CommonFunction
						.moneyFormat(objects[12].toString());
			}
			if (objects[13] != null && !"".equals(objects[13])) {
				objects[13] = CommonFunction
						.moneyFormat(objects[13].toString());
			}
			if (objects[14] != null && !"".equals(objects[14])) {
				objects[14] = CommonFunction
						.moneyFormat(objects[14].toString());
			}
			if (objects[24] != null && !"".equals(objects[24])) {
				objects[24] = CommonFunction
						.moneyFormat(objects[24].toString());
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 准退货查询_新 2015-08-19
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblZth2(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");
		String querySysSeqNum = request.getParameter("querySysSeqNum");
		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryEflag = request.getParameter("queryEflag");

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND INST_DATE").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND F42").append(" = ").append("'")
					.append(queryCardAccpId).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND F41").append(" like ").append("'%")
					.append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND F2").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(querySysSeqNum)) {
			whereSql.append(" AND SYS_SEQ_NUM").append(" like ").append("'%")
					.append(querySysSeqNum).append("%'");
		}
		if (isNotEmpty(queryEflag)) {
			whereSql.append(" AND EFLAG").append(" = ").append("'")
					.append(queryEflag).append("'");
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select inst_date as instDate,");
		sql.append(" inst_time as instTime,");
		sql.append(" sys_seq_num as sysSeqNum,");
		sql.append(" rspcode,");
		sql.append(" rspdsp,");
		sql.append(" f11_pos as f11Pos,");
		sql.append(" f2,");
		sql.append(" f4,");
		sql.append(" nfee,");
		sql.append(" samt,");
		sql.append(" amt1,");
		sql.append(" eflag,");
		sql.append(" to_char(to_date(ddate,'yyyymmdd'),'yyyy-mm-dd')  as ddate,");
		sql.append(" f41,");
		sql.append(" f42||'-'||(select mcht_nm from tbl_mcht_base_inf where mcht_no=f42) as f42,");
		sql.append(" o_amt as oAmt,");
		sql.append(" o_f11 as oF11,");
		sql.append(" to_char(to_date(o_f13,'mmdd'),'mm-dd')  as oF13,");
		sql.append(" to_char(to_date(o_f12,'hh24miss'),'hh24:mi:ss')  as oF12");
		sql.append(" from TBL_ZTH");
		sql.append(whereSql.toString());
		sql.append(" ORDER BY INST_DATE ,INST_TIME ");

		String countSql = "SELECT COUNT(*) FROM TBL_ZTH " + whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			if (objects[7] != null && !"".equals(objects[7])) {
				objects[7] = CommonFunction.moneyFormat(objects[7].toString());
			}
			if (objects[8] != null && !"".equals(objects[8])) {
				objects[8] = CommonFunction.moneyFormat(objects[8].toString());
			}
			if (objects[9] != null && !"".equals(objects[9])) {
				objects[9] = CommonFunction.moneyFormat(objects[9].toString());
			}
			if (objects[10] != null && !"".equals(objects[10])) {
				objects[10] = CommonFunction
						.moneyFormat(objects[10].toString());
			}
			if (objects[15] != null && !"".equals(objects[15])) {
				objects[15] = CommonFunction
						.moneyFormat(objects[15].toString());
			}
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 准退货明细查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblZthDtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String instDate = request.getParameter("instDate");
		String sysSeqNum = request.getParameter("sysSeqNum");

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(instDate)) {
			whereSql.append(" AND INST_DATE").append(" = ").append("'")
					.append(instDate).append("'");
		}
		if (isNotEmpty(sysSeqNum)) {
			whereSql.append(" AND SYS_SEQ_NUM").append(" = ").append("'")
					.append(sysSeqNum).append("'");
		}
		String sql = "SELECT SETTLMT_DATE,MCHT_NO,AMT FROM TBL_ZTH_DTL "
				+ whereSql.toString() + "ORDER BY SETTLMT_DATE DESC";

		String countSql = "SELECT COUNT(*) FROM TBL_ZTH_DTL "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			if (objects[2] != null && !"".equals(objects[2])) {
				objects[2] = CommonFunction.moneyFormat(objects[2].toString());
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * T+0 商户提现计费查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWithDrawFee(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");

		String queryMchtNo = request.getParameter("queryMchtNo");

		if (isNotEmpty(queryMchtNo)) {
			whereSql.append(" AND a.MERCHANT_ID").append(" = ").append("'")
					.append(queryMchtNo).append("'");
		}

		String sql = "SELECT ROW_ID,a.MERCHANT_ID,"
				+ "a.MERCHANT_ID||(SELECT DISTINCT ' - '||c.MERCHANT_NAME FROM MB_ACCOUT_DETAIL c WHERE c.MERCHANT_ID = a.MERCHANT_ID) AS mcht_id_name,"
				+ "FEE_TYPE,"
				// +
				// "(CASE WHEN FEE_TYPE = '01' THEN '百分比' WHEN  FEE_TYPE = '02' THEN '单笔' ELSE FEE_TYPE END) AS FEE_TYPE,"
				+ "RATE,START_DATE,END_DATE,STATUS,"
				// +
				// "(CASE WHEN STATUS = '0' THEN '已生效' WHEN  STATUS = '1' THEN '已失效' ELSE STATUS END) AS STATUS,"
				+ "MAX_FEE,MIN_FEE,"
				// + "CREATE_DATE,"
				+ "to_char(CREATE_DATE,'yyyy-mm-dd hh24:mi:ss') AS CREATE_DATE,"
				+ "CREATE_BY,"
				// + "UPDATE_DATE,"
				+ "to_char(UPDATE_DATE,'yyyy-mm-dd hh24:mi:ss') AS UPDATE_DATE,"
				+ "UPDATE_BY FROM MB_WITHDRAW_FEE a" + whereSql.toString()
				+ "ORDER BY CREATE_DATE DESC";

		String countSql = "SELECT COUNT(*) FROM MB_WITHDRAW_FEE a"
				+ whereSql.toString();
//方法作废
//		List<Object[]> dataList = CommonFunction.getCommQuery_frontDAO()
//				.findBySQLQuery(sql.toString(), begin,
//						Constants.QUERY_RECORD_COUNT);
//		String count = CommonFunction.getCommQuery_frontDAO()
//				.findCountBySQLQuery(countSql);
		List<Object[]> dataList = new ArrayList<Object[]>();
		ret[0] = dataList;
		ret[1] = "0";
		return ret;
	}

	/**
	 * T+0 商户参数查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAcctMchtParams(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");

		String queryBrhId = request.getParameter("queryBrhId");
		String queryMchtNo = request.getParameter("queryMchtNo");
		if (isNotEmpty(queryBrhId)) {
			whereSql.append(" AND t.BRH_ID").append(" = ").append("'")
					.append(queryBrhId).append("'");
		}
		if (isNotEmpty(queryMchtNo)) {
			whereSql.append(" AND t.MERCHANT_ID").append(" = ").append("'")
					.append(queryMchtNo).append("'");
		}

		String sql = "SELECT "
				+ "t.BRH_ID||(SELECT DISTINCT ' - '||c.BRH_NAME FROM MB_ACCOUT_DETAIL c WHERE c.BRH_ID = t.BRH_ID) AS brh_id_name, "
				+ "t.MERCHANT_ID||(SELECT DISTINCT ' - '||c.MERCHANT_NAME FROM MB_ACCOUT_DETAIL c WHERE c.MERCHANT_ID = t.MERCHANT_ID) AS mcht_id_name, "
				+ "max(decode(t.PARAM_CODE, '0101', PARAM_VALUE,null)) as 提现时段, "
				+ "max(decode(t.PARAM_CODE, '0102', PARAM_VALUE,null)) as 提现限额A, "
				+ "max(decode(t.PARAM_CODE, '0103', PARAM_VALUE,null)) as 提现限额B, "
				+ "max(decode(t.PARAM_CODE, '0104', PARAM_VALUE,null)) as 提现限额C, "
				+ "max(decode(t.PARAM_CODE, '0201', PARAM_VALUE,null)) as 科目代码, "
				+ "t.BRH_ID, t.MERCHANT_ID "
				+ "FROM SYS_PARAMS_CONFIG t "
				+ whereSql.toString()
				+ "GROUP BY t.BRH_ID, t.MERCHANT_ID ORDER BY t.BRH_ID, t.MERCHANT_ID";

		String countSql = "SELECT COUNT(*) FROM (SELECT COUNT(*) FROM SYS_PARAMS_CONFIG t "
				+ whereSql.toString() + "GROUP BY t.BRH_ID, t.MERCHANT_ID)";
//方法作废
//		List<Object[]> dataList = CommonFunction.getCommQuery_frontDAO()
//				.findBySQLQuery(sql.toString(), begin,
//						Constants.QUERY_RECORD_COUNT);
//		String count = CommonFunction.getCommQuery_frontDAO()
//				.findCountBySQLQuery(countSql);
		List<Object[]> dataList = new ArrayList<Object[]>();
		String count = "0";
		// String count =
		// String.valueOf(CommonFunction.getCommQuery_frontDAO().findBySQLQuery(sql.toString()).size());
		for (Object[] objects : dataList) {
			if (objects[2] != null && !"".equals(objects[2])) {
				objects[2] = CommonFunction.timeRangeFormat(objects[2]
						.toString());
			}
			if (objects[3] != null && !"".equals(objects[3])) {
				objects[3] = objects[3].toString() + "%";
			}
			if (objects[4] != null && !"".equals(objects[4])) {
				objects[4] = CommonFunction.moneyFormat(objects[4].toString());
			}
			if (objects[5] != null && !"".equals(objects[5])) {
				objects[5] = CommonFunction.moneyFormat(objects[5].toString());
			}
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * T+0 商户参数详情查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAcctMchtParamsDtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1  ");

		String queryMchtNo = request.getParameter("queryMchtNo");
		String queryBrhId = request.getParameter("queryBrhId");

		if (isNotEmpty(queryMchtNo)) {
			whereSql.append(" AND MERCHANT_ID").append(" = ").append("'")
					.append(queryMchtNo).append("'");
		}
		if (isNotEmpty(queryBrhId)) {
			whereSql.append(" AND BRH_ID").append(" = ").append("'")
					.append(queryBrhId).append("'");
		}

		String sql = "SELECT a.PARAM_NAME,a.PARAM_CODE,c.PARAM_VALUE,"
				+ "to_char(c.CREATE_DATE,'yyyy-mm-dd hh24:mi:ss') AS CREATE_DATE,c.CREATE_BY,"
				+ "to_char(c.UPDATE_DATE,'yyyy-mm-dd hh24:mi:ss') AS UPDATE_DATE,c.UPDATE_BY "
				+ "FROM SYS_PARAMS_SORT a "
				+ "LEFT JOIN (SELECT * FROM SYS_PARAMS_CONFIG "
				+ whereSql.toString()
				+ ") c ON c.PARAM_CODE=a.PARAM_CODE "
				+ "WHERE a.PARAM_PARENT_CODE IN ('01','02') and a.PARAM_CODE <> '0101' order by a.PARAM_CODE";

		String countSql = "SELECT COUNT(*) FROM SYS_PARAMS_SORT a WHERE a.PARAM_PARENT_CODE IN ('01','02') ";
//方法作废
//		List<Object[]> dataList = CommonFunction.getCommQuery_frontDAO()
//				.findBySQLQuery(sql.toString(), begin,
//						Constants.QUERY_RECORD_COUNT);
//		String count = CommonFunction.getCommQuery_frontDAO()
//				.findCountBySQLQuery(countSql);
//		ret[0] = dataList;
//		ret[1] = count;
		List<Object[]> dataList = new ArrayList<Object[]>();
		ret[0] = dataList;
		ret[1] = "0";
		return ret;
	}

	/**
	 * T+0提现审核记录查询（前置）
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWithdrawTxn(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(" WHERE TRANS_CODE='4001' ");

		String queryMerchantIdId = request.getParameter("queryMerchantId");
		String querySysSeqNumber = request.getParameter("querySysSeqNumber");
		String queryWebSqlNumber = request.getParameter("queryWebSqlNumber");
		String queryStatus = request.getParameter("queryStatus");
		String queryStartDate = request.getParameter("queryStartDate");
		String queryEndDate = request.getParameter("queryEndDate");
		if (isNotEmpty(queryMerchantIdId)) {
			whereSql.append(" AND MERCHANT_ID").append(" = ").append("'")
					.append(queryMerchantIdId).append("'");
		}
		if (isNotEmpty(querySysSeqNumber)) {
			whereSql.append(" AND SYS_SEQ_NUM").append(" like ").append("'%")
					.append(querySysSeqNumber).append("%'");
		}
		if (isNotEmpty(queryWebSqlNumber)) {
			whereSql.append(" AND WEB_SEQ_NUM").append(" like ").append("'%")
					.append(queryWebSqlNumber).append("%'");
		}
		if (isNotEmpty(queryStatus)) {
			whereSql.append(" AND STATUS").append(" = ").append("'")
					.append(queryStatus).append("'");
		}

		if (isNotEmpty(queryStartDate)) {
			whereSql.append(" AND INST_DATE").append(" >= ").append("'")
					.append(queryStartDate).append("'");
		}
		if (isNotEmpty(queryEndDate)) {
			whereSql.append(" AND INST_DATE").append(" < ").append("'")
					.append(queryEndDate).append("'");
		}
		String sql = "select a.INST_DATE,a.CREATE_DATE,a.SYS_SEQ_NUM, a.WEB_TIME,a.WEB_SEQ_NUM,a.MERCHANT_ID||'-'||(select m.MERCHANT_NAME from MB_ACCOUT_DETAIL m where m.MERCHANT_ID=a.MERCHANT_ID),a.ACCT_NO,a.TOTALNUM,a.TOTALAMOUNT,a.TOTALFEE, a.STATUS,a.TOTALSETTLE from AF_WITHDRAW_TXN a"
				+ whereSql.toString() + "  order by SYS_SEQ_NUM desc";

		String countSql = "SELECT COUNT(*) FROM AF_WITHDRAW_TXN "
				+ whereSql.toString();
//方法作废
//		List<Object[]> dataList = CommonFunction.getCommQuery_frontDAO()
//				.findBySQLQuery(sql.toString(), begin,
//						Constants.QUERY_RECORD_COUNT);
//		String count = CommonFunction.getCommQuery_frontDAO()
//				.findCountBySQLQuery(countSql);
//		ret[0] = dataList;
//		ret[1] = count;
		List<Object[]> dataList = new ArrayList<Object[]>();
		ret[0] = dataList;
		ret[1] = "0";
		return ret;
	}

	/**
	 * T+0提现审核记录明细查询（前置）
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWithdrawTxnDetail(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer(
				" WHERE  d.POSP_INST_DATE=t.POSP_INST_DATE and d.POSP_SEQ_NUM=t.POSP_SYS_SEQ_NUM");

		String instDate = request.getParameter("instDate");
		String sysSeqNumber = request.getParameter("sysSeqNumber");

		if (isNotEmpty(instDate)) {
			whereSql.append(" AND d.INST_DATE").append(" = ").append("'")
					.append(instDate).append("'");
		}
		if (isNotEmpty(sysSeqNumber)) {
			whereSql.append(" AND d.SYS_SEQ_NUM").append(" = ").append("'")
					.append(sysSeqNumber).append("'");
		}

		String sql = "select d.INST_DATE,d.SYS_SEQ_NUM,t.WEB_TIME,t.WEB_SEQ_NUM,t.MERCHANT_ID||'-'||(select m.MERCHANT_NAME from MB_ACCOUT_DETAIL m where m.MERCHANT_ID=t.MERCHANT_ID ),t.TRANS_AMOUNT,d.STATUS,t.CREATE_DATE,t.TRANS_DATE,t.CARD_NO,t.TRANS_FEE,t.SETTLE_AMOUNT,t.TERMINAL_NUM,WITHDRAW_INST_DATE from AF_WITHDRAW_TXN_DETAIL d , AF_POSP_TXN  t "
				+ whereSql.toString() + "  order by INST_DATE desc";

		String countSql = "SELECT COUNT(*) FROM AF_WITHDRAW_TXN_DETAIL d , AF_POSP_TXN  t"
				+ whereSql.toString();
//方法作废
//		List<Object[]> dataList = CommonFunction.getCommQuery_frontDAO()
//				.findBySQLQuery(sql.toString(), begin,
//						Constants.QUERY_RECORD_COUNT);
//		String count = CommonFunction.getCommQuery_frontDAO()
//				.findCountBySQLQuery(countSql);
//		ret[0] = dataList;
//		ret[1] = count;
		List<Object[]> dataList = new ArrayList<Object[]>();
		ret[0] = dataList;
		ret[1] = "0";
		return ret;
	}

	/*	*//**
	 * T+0 提现信息查询 ，当前机构下T+0商户提现交易信息查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked") public static Object[] getWithdrawInf(int
	 * begin, HttpServletRequest request) { Object[] ret = new Object[2];
	 * 
	 * StringBuffer whereSql = new StringBuffer(" WHERE 1=1 "); Operator
	 * operator = (Operator) request.getSession().getAttribute(
	 * Constants.OPERATOR_INFO); String queryBatchNO =
	 * request.getParameter("queryBatchNO"); if (isNotEmpty(queryBatchNO)) {
	 * whereSql.append(" AND BATCH_NO").append(" = ").append("'")
	 * .append(queryBatchNO).append("'"); }
	 * 
	 * String sql =
	 * "select BATCH_NO,WD_TIME,WD_SEQ_NUM,MCHT_NO,WD_NUM,SUM_AMT_TRANS,WD_AMT_FEE,AMT_SETTLMT,WD_STATUS,APPLY_OPR,APPLY_TIME,CHECK_OPR,CHECK_TIME,REFUSE_REASON "
	 * + " from TBL_WITHDRAW_INF " + whereSql;
	 * 
	 * String countSql = "select count(*) from TBL_WITHDRAW_INF " + whereSql;
	 * 
	 * List<Object[]> dataList = CommonFunction.getCommQueryDAO()
	 * .findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
	 * String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
	 * countSql); ret[0] = dataList; ret[1] = count; return ret; }
	 */
	/**
	 * T+0 提现交易详情信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked") public static Object[]
	 * getWithdrawInfDtl(int begin, HttpServletRequest request) { Object[] ret =
	 * new Object[2];
	 * 
	 * StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
	 * 
	 * String queryBatchNo = request.getParameter("queryBatchNo"); if
	 * (isNotEmpty(queryBatchNo)) {
	 * whereSql.append(" AND BATCH_NO").append(" = ").append("'")
	 * .append(queryBatchNo).append("'"); }
	 * 
	 * String sql =
	 * "select BATCH_NO,INST_DATE,SYS_SEQ_NUM from TBL_WITHDRAW_INF_DTL " +
	 * whereSql + "order by SYS_SEQ_NUM";
	 * 
	 * String countSql = "select count(*) from TBL_WITHDRAW_INF_DTL" + whereSql;
	 * 
	 * List<Object[]> dataList = CommonFunction.getCommQueryDAO()
	 * .findBySQLQuery(sql.toString(), begin, Constants.QUERY_RECORD_COUNT);
	 * String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
	 * countSql); ret[0] = dataList; ret[1] = count; return ret; }
	 */

	/**
	 * 查询商户提现申请信息
	 * 
	 * @param begin
	 * @param request
	 *            2015年6月12日 上午09:30:16
	 * @author 徐鹏飞
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWithdrawInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		String queryMchtNo = request.getParameter("queryMchtNo");
		String queryBrhId = request.getParameter("queryBrhId");
		StringBuffer whereSql = new StringBuffer(" where 1=1");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND substr(a.APPLY_TIME,1,8)").append(" >= ")
					.append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND substr(a.APPLY_TIME,1,8)").append(" <= ")
					.append("'").append(endDate).append("'");
		}
		if (isNotEmpty(status)) {
			whereSql.append(" AND a.WD_STATUS").append(" = ").append("'")
					.append(status).append("' ");
		} else {

			whereSql.append(" AND a.WD_STATUS").append(" in ").append(" ('")
					.append("5,6,7").append("') ");// 5:人工审核待审核，6:人工审核通过，7:人工审核拒绝

		}
		if (isNotEmpty(queryMchtNo)) {
			whereSql.append(" AND a.MCHT_NO").append(" = ").append("'")
					.append(queryMchtNo).append("' ");
		}
		if (isNotEmpty(queryBrhId)) {
			whereSql.append(" and a.MCHT_NO in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ queryBrhId.trim()
					+ "' connect by prior BRH_ID = UP_BRH_ID)) ");
		}
		String sql = "SELECT BATCH_NO, "
				+ "b.MCHT_NO||' - '||b.MCHT_NM, "
				+ "(select trim(b.bank_no)||' - '||trim(d.brh_name) from tbl_brh_info d where (b.bank_no)=trim(d.brh_id))as brhIdName, "
				+ "WD_NUM, SUM_AMT_TRANS, WD_AMT_FEE, AMT_SETTLMT, WD_STATUS, APPLY_OPR, APPLY_TIME, CHECK_OPR, CHECK_TIME, REFUSE_REASON,WD_TIME ,WD_SEQ_NUM  "
				+ "FROM TBL_WITHDRAW_INF a LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO=b.MCHT_NO "
				+ whereSql + " ORDER BY APPLY_TIME desc";
		String countSql = "SELECT COUNT(*) FROM TBL_WITHDRAW_INF a " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户提现申请交易流水信息
	 * 
	 * @param begin
	 * @param request
	 *            2015年6月12日 上午09:30:16
	 * @author 徐鹏飞
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWithdrawInfDtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String batchNo = request.getParameter("batchNo");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (isNotEmpty(batchNo)) {
			whereSql.append(" AND b.BATCH_NO").append(" = ").append("'")
					.append(batchNo).append("' ");
		}
		String sql = "SELECT f.inst_date, f.pan, f.card_accp_term_id, f.term_ssn, f.sys_seq_num, f.retrivl_ref, "
				+ "TO_NUMBER(NVL(trim(f.amt_trans),0))/100 as amt, "
				+ "TO_NUMBER(NVL(trim(f.AMT_CDHLDR_BIL),0))/100 as fee, "
				+ "TO_NUMBER(NVL(trim(f.AMT_SETTLMT),0))/100 as settle, "
				+ "(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =f.txn_num ) as txn_name, f.trans_state "
				+ "from tbl_n_txn_for_mqs f where (f.inst_date, f.sys_seq_num) in (select b.inst_date, b.sys_seq_num from TBL_WITHDRAW_INF_DTL b "
				+ whereSql + ") order by f.inst_date desc";
		String countSql = "SELECT COUNT(*) FROM TBL_WITHDRAW_INF_DTL b "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 提现调账
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getWithdrawErr(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String txn_date = request.getParameter("txn_date");
		String txn_date2 = request.getParameter("txn_date2");
		String queryEflag = request.getParameter("queryEflag");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.front_dt,t.front_sn,t.mcht_withdraw_dt,t.mcht_withdraw_sn,t.txn_date,t.err_type,");
		sql.append(" t.acct_change,t.acct_change_resp,t.acct_change_dt, t.front_stat, ");
		sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.front_withdraw_amt),0.00))/100,0),'99,999,999,990.99') as front_withdraw_amt, ");
		sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.front_withdraw_fee),0.00))/100,0),'99,999,999,990.99') as front_withdraw_fee, ");
		sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.front_withdraw_stlm_amt),0.00))/100,0),'99,999,999,990.99') as front_withdraw_stlm_amt, ");
		sql.append("t.acct_withdraw_stat, ");
		sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.acct_withdraw_amt),0.00))/100,0),'99,999,999,990.99') as acct_withdraw_amt, ");
		sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.acct_withdraw_fee),0.00))/100,0),'99,999,999,990.99') as acct_withdraw_fee, ");
		sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.acct_withdraw_stlm_amt),0.00))/100,0),'99,999,999,990.99') as acct_withdraw_stlm_amt, ");
		sql.append(" inst_dt,update_dt ");
		sql.append("from TBL_WITHDRAW_ERR t  where 1=1");
		if (isNotEmpty(txn_date) && !isNotEmpty(txn_date2)) {
			sql.append(" and t.txn_date =").append("'").append(txn_date)
					.append("' ");
		}
		if (!isNotEmpty(txn_date) && isNotEmpty(txn_date2)) {
			sql.append(" and t.txn_date <=").append("'").append(txn_date2)
					.append("' ");
		}
		if (isNotEmpty(txn_date) && isNotEmpty(txn_date2)) {
			sql.append(" and t.txn_date >=").append("'").append(txn_date)
					.append("' ");
			sql.append(" and t.txn_date <=").append("'").append(txn_date2)
					.append("' ");
		}
		if (isNotEmpty(queryEflag)) {			
			sql.append(" and t.acct_change =").append("'").append(queryEflag).append("' ");
		}else{
			sql.append("  and  t.acct_change  is null  ");
			}
		sql.append(" order by t.txn_date,t.txn_date ");
		StringBuffer countSql = new StringBuffer(
				"SELECT COUNT(*) FROM TBL_WITHDRAW_ERR t where 1=1 ");
		if (isNotEmpty(txn_date) && !isNotEmpty(txn_date2)) {
			countSql.append(" and t.txn_date =").append("'").append(txn_date)
					.append("' ");
		}
		if (!isNotEmpty(txn_date) && isNotEmpty(txn_date2)) {
			countSql.append(" and t.txn_date <=").append("'").append(txn_date2)
					.append("' ");
		}
		if (isNotEmpty(txn_date) && isNotEmpty(txn_date2)) {
			countSql.append(" and t.txn_date >=").append("'").append(txn_date)
					.append("' ");
			countSql.append(" and t.txn_date <=").append("'").append(txn_date2)
					.append("' ");
		}
		if (isNotEmpty(queryEflag)) {			
		countSql.append(" and t.acct_change =").append("'").append(queryEflag).append("' ");
		}else{
		countSql.append(" and  t.acct_change  is null ");
		}
		
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql.toString());
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 消费调账
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAcctErr(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String txn_dateStart = request.getParameter("txn_dateStart");
		String txn_dateEnd = request.getParameter("txn_dateEnd");
		String machNo = request.getParameter("machNo");
		String brhNo = request.getParameter("brhNo");
		String queryEflag = request.getParameter("queryEflag");
		StringBuffer sql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		sql.append("select t.key_inst,t.date_stlm,");
		sql.append( " t.acct_err_type,(select  b.txn_name from tbl_txn_name b where t.txn_num=b.txn_num) as txn_num,");
		sql.append( " t.acct_change_stat, t.acct_change_resp,t.acct_change_dt,");
		sql.append(	" t.brh_id,");
		sql.append( " t.card_accp_id||'-'||(select mcht_nm from tbl_mcht_base_inf where mcht_no=t.card_accp_id) as card_accp_id , "  );
		sql.append( " t.stlm_flag,t.txn_date,t.txn_time,t.sys_seq_num,");
		sql.append( " t.retrivl_ref, (select first_brh_name from tbl_first_brh_dest_id where first_brh_id=t.inst_code) as inst_code,");
		sql.append( " t.inst_mcht_id,t.inst_term_id,t.inst_retrivl_ref,");	
		sql.append( " t.card_accp_term_id,t.term_sn,t.pan,t.card_type,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.amt_trans),0.00))/100,0),'99,999,999,990.99') as amt_trans,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.amt_trans_fee),0.00))/100,0),'99,999,999,990.99') as amt_trans_fee,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.amt_stlm),0.00))/100,0),'99,999,999,990.99') as amt_stlm,"
				+ " t.resp_code,"
				+ " t.inst_pan,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.inst_amt),0.00))/100,0),'99,999,999,990.99') as inst_amt,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.inst_amt_fee),0.00))/100,0),'99,999,999,990.99') as inst_amt_fee,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.inst_amt_stlm),0.00))/100,0),'99,999,999,990.99') as inst_amt_stlm ");
		sql.append(" from TBL_ACCT_ERR t ");
		if (StringUtil.isNotEmpty(txn_dateStart)&& !StringUtil.isNotEmpty(txn_dateEnd) ) {
			whereSql.append("and t.date_stlm ='").append(txn_dateStart)
					.append("'");
		}
		if (!StringUtil.isNotEmpty(txn_dateStart) && StringUtil.isNotEmpty(txn_dateEnd)) {
			whereSql.append("and t.date_stlm <='").append(txn_dateEnd)
					.append("'");
		}
		if (StringUtil.isNotEmpty(txn_dateStart) && StringUtil.isNotEmpty(txn_dateEnd) ) {
			whereSql.append("and t.date_stlm >='").append(txn_dateStart).append("'");
			whereSql.append("and t.date_stlm <='").append(txn_dateEnd).append("'");
		}
		
		if (StringUtil.isNotEmpty(brhNo)) {
			whereSql.append(" and t.brh_id ='").append(brhNo.trim())
					.append("'");
		}
		if (StringUtil.isNotEmpty(machNo)) {
			whereSql.append(" and t.card_accp_id ='").append(machNo.trim())
					.append("'");
		}
		if (isNotEmpty(queryEflag)) {
			
			whereSql.append(" and t.ACCT_CHANGE_STAT =").append("'").append(queryEflag).append("' ");
		}else{
				whereSql.append(" and  t.ACCT_CHANGE_STAT is null ");
		}
		
       
		sql.append(whereSql);
		StringBuffer countSql = new StringBuffer(
				"SELECT COUNT(*) FROM TBL_ACCT_ERR t ").append(whereSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql.toString());
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 前置批量任务查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getFrontBat(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String date = request.getParameter("date");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(date)) {
			whereSql.append("and trans_date ='").append(date).append("' ");
		}
		String sql = "select inst_date,sys_seq_num,check_name,trans_code,trans_date,status,send_code "
				+ "from check_acc "
				+ whereSql
				+ "order by inst_date desc ,sys_seq_num desc ";

		StringBuffer countSql = new StringBuffer(
				"select count(*) from check_acc  ").append(whereSql);
//方法作废
//		List<Object[]> dataList = CommonFunction.getCommQuery_frontDAO()
//				.findBySQLQuery(sql.toString(), begin,
//						Constants.QUERY_RECORD_COUNT);
//		String count = CommonFunction.getCommQuery_frontDAO()
//				.findCountBySQLQuery(countSql.toString());
//		ret[0] = dataList;
//		ret[1] = count;
		List<Object[]> dataList = new ArrayList<Object[]>();
		ret[0] = dataList;
		ret[1] = "0";
		return ret;
	}

	/**
	 * 资金结算明细查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-11-12下午04:23:34
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getMchtSettleDtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryAmtTransLow = request.getParameter("queryAmtTransLow");
		String queryAmtTransUp = request.getParameter("queryAmtTransUp");
		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		String queryBrhId = request.getParameter("queryBrhId");

		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" and a.date_stlm ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" and a.date_stlm").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryAmtTransLow)) {
			whereSql.append(" and nvl(a.stlm_amt_trans,0) ").append(" >= ")
					.append("'").append(queryAmtTransLow).append("' ");
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSql.append(" and nvl(a.stlm_amt_trans,0) ").append(" <= ")
					.append("'").append(queryAmtTransUp).append("' ");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			whereSql.append(" and a.mcht_id").append(" = ").append("'")
					.append(queryMchtNoNm).append("' ");
		}
		if (isNotEmpty(queryBrhId)) {
			// whereSql.append(" and a.brh_id").append(" = ").append("'").append(queryBrhId).append("' ");
			whereSql.append(
					" and trim(a.brh_id) in(select brh_id from tbl_brh_info where brh_id='")
					.append(queryBrhId).append("' " + " or up_brh_id='")
					.append(queryBrhId).append("') ");
			// whereSql.append(" and  a.brh_id in (select brh_id from tbl_brh_info  start with brh_id ='"
			// + queryBrhId.trim()
			// + "' connect by prior  brh_id = up_brh_id  )  ");
		}

		String sql = "select a.date_stlm,"
				+ "trim(a.brh_id)||(select ' - '||b.brh_name from tbl_brh_info b where b.brh_id = trim(a.brh_id)) as brh_id_name,"
				+ "a.mcht_id||(select ' - '||mcht_nm from tbl_mcht_base_inf where mcht_no=a.mcht_id) as mcht_no_nm,"
				+ "a.inst_code||(select ' - '||first_brh_name from tbl_first_brh_dest_id where first_brh_id=a.inst_code)as inst_code_name,"
				+ "a.stlm_flag,a.date_stlm_true,a.stlm_num,a.stlm_amt_trans,a.stlm_amt_fee,a.stlm_amt "
				+ "FROM tbl_mcht_settle_dtl a  ";
		sql = sql + whereSql.toString();
		sql = sql + " order by a.date_stlm desc,a.brh_id,a.mcht_id";
		String countSql = "select count(*) FROM tbl_mcht_settle_dtl a  ";
		countSql = countSql + whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = CommonFunction.moneyFormat(objects[7]
							.toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = CommonFunction.moneyFormat(objects[8]
							.toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					objects[9] = CommonFunction.moneyFormat(objects[9]
							.toString());
				}
			}
		}

		if (dataList.size() > 0) {
			String sumSql = "select nvl(sum(nvl(a.stlm_num,0)),0),nvl(sum(nvl(a.stlm_amt_trans,0)),0),nvl(sum(nvl(a.stlm_amt_fee,0)),0),nvl(sum(nvl(a.stlm_amt,0)),0) "
					+ " from tbl_mcht_settle_dtl a " + whereSql.toString();
			List<Object[]> amtList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sumSql);

			Object[] obj = new Object[dataList.get(0).length];

			obj[0] = "<font color='red'>总计</font>";
			obj[6] = "<font color='red'>" + amtList.get(0)[0].toString()
					+ "</font>";
			obj[7] = "<font color='red'>"
					+ CommonFunction.moneyFormat(amtList.get(0)[1].toString())
					+ "</font>";
			obj[8] = "<font color='red'>"
					+ CommonFunction.moneyFormat(amtList.get(0)[2].toString())
					+ "</font>";
			obj[9] = "<font color='red'>"
					+ CommonFunction.moneyFormat(amtList.get(0)[3].toString())
					+ "</font>";

			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 新清结算 准退货查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblZthNew(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryCardAccpTermId = request
				.getParameter("queryCardAccpTermId");
		String queryPan = request.getParameter("queryPan");
		String querySysSeqNum = request.getParameter("querySysSeqNum");
		String queryCardAccpId = request.getParameter("queryCardAccpId");
		String queryEflag = request.getParameter("queryEflag");

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND INST_DATE").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND F42").append(" = ").append("'")
					.append(queryCardAccpId).append("'");
		}
		if (isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" AND F41").append(" like ").append("'%")
					.append(queryCardAccpTermId).append("%'");
		}
		if (isNotEmpty(queryPan)) {
			whereSql.append(" AND F2").append(" like ").append("'%")
					.append(queryPan).append("%'");
		}
		if (isNotEmpty(querySysSeqNum)) {
			whereSql.append(" AND SYS_SEQ_NUM").append(" like ").append("'%")
					.append(querySysSeqNum).append("%'");
		}
		if (isNotEmpty(queryEflag)) {
			whereSql.append(" AND EFLAG").append(" = ").append("'")
					.append(queryEflag).append("'");
		}

		String sql = "SELECT INST_DATE,INST_TIME,SYS_SEQ_NUM,MSG_SRC_ID,MSG_DEST_ID,TXN_NUM,RSPCODE,RSPDSP,"
				+ "F60,F11_POS,F2,F4,NFEE,SAMT,AMT1,EFLAG,DDATE,F22,F37_POS,F49,F41,F42||' - '||MCHT_NAME,F41_SY,F42_SY,"
				+ "O_AMT,O_F60,O_F11,O_F13,O_F12,O_F22,O_F37_POS,O_F37_SY,O_F49,BAK1 FROM TBL_ZTH_NEW "
				+ whereSql.toString()
				+ "ORDER BY INST_DATE DESC,INST_TIME DESC";

		String countSql = "SELECT COUNT(*) FROM TBL_ZTH_NEW "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			if (objects[11] != null && !"".equals(objects[11])) {
				objects[11] = CommonFunction
						.moneyFormat(objects[11].toString());
			}
			if (objects[12] != null && !"".equals(objects[12])) {
				objects[12] = CommonFunction
						.moneyFormat(objects[12].toString());
			}
			if (objects[13] != null && !"".equals(objects[13])) {
				objects[13] = CommonFunction
						.moneyFormat(objects[13].toString());
			}
			if (objects[14] != null && !"".equals(objects[14])) {
				objects[14] = CommonFunction
						.moneyFormat(objects[14].toString());
			}
			if (objects[24] != null && !"".equals(objects[24])) {
				objects[24] = CommonFunction
						.moneyFormat(objects[24].toString());
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 准退货明细查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblZthDtlNew(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String instDate = request.getParameter("instDate");
		String sysSeqNum = request.getParameter("sysSeqNum");

		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		if (isNotEmpty(instDate)) {
			whereSql.append(" AND INST_DATE").append(" = ").append("'")
					.append(instDate).append("'");
		}
		if (isNotEmpty(sysSeqNum)) {
			whereSql.append(" AND SYS_SEQ_NUM").append(" = ").append("'")
					.append(sysSeqNum).append("'");
		}
		String sql = "SELECT SETTLMT_DATE,MCHT_NO,AMT FROM TBL_ZTH_DTL_NEW "
				+ whereSql.toString() + "ORDER BY SETTLMT_DATE DESC";

		String countSql = "SELECT COUNT(*) FROM TBL_ZTH_DTL_NEW "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			if (objects[2] != null && !"".equals(objects[2])) {
				objects[2] = CommonFunction.moneyFormat(objects[2].toString());
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 新清结算：划付文件下载查询（总）
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-13
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPaySettleNew(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String settleDate = request.getParameter("settleDate");

		StringBuffer whereSql = new StringBuffer(
				" where 1=1 and a.pay_type!='0' ");
		whereSql.append(" AND a.date_stlm").append(" = '").append(settleDate)
				.append("' ");

		String sql = " select t.inst_date,t.channel_id,"
				+ "(select t.channel_id||'-'||b.channel_name from TBL_PAY_CHANNEL_INFO b where b.channel_id=t.channel_id) as channel_id_nm,"
				+ "nvl(t.sum_amt_settle,0) "
				+ " from ("
				+ "	 select a.date_stlm as inst_date,a.channel_id as channel_id,"
				+ "  sum(a.amt_settle) as sum_amt_settle "
				+ "  from TBL_SETTLE_DTL a  " + whereSql
				+ "	 group by a.date_stlm,a.channel_id " + "	) t "
				+ " order by t.channel_id ";

		String countSql = "select count(1) from ("
				+ " select a.date_stlm, a.channel_id from TBL_SETTLE_DTL a "
				+ whereSql + " group by a.date_stlm,a.channel_id) t2 ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			objects[3] = CommonFunction.moneyFormat(objects[3].toString());
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 新清结算 划付文件下载明细查询（明细）
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-13
	 * @author caotz
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPaySettleDtlNew(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String instDate = request.getParameter("instDate");
		String channelId = request.getParameter("channelId");

		StringBuffer whereSql = new StringBuffer(
				" where 1=1 and a.pay_type!='0' ");
		whereSql.append(" AND a.date_stlm").append(" = '").append(instDate)
				.append("' ");
		whereSql.append(" AND a.CHANNEL_ID").append(" = '").append(channelId)
				.append("' ");

		String sql = "select a.mcht_id,"
				+ "a.mcht_id||'-'||a.mcht_nm  as mcht_no_nm,"
				+ "a.beg_date,a.end_date," + "	a.amt_settle as amt,"
				+ "	a.mcht_acct_no,a.mcht_acct_nm,a.cnaps_id,a.pay_type "
				+ " from TBL_SETTLE_DTL a  " + whereSql
				+ " order by a.mcht_id ";

		String countSql = "select count(*) from TBL_SETTLE_DTL a " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			objects[4] = CommonFunction.moneyFormat(objects[4].toString());
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 新清收单结算信息表
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-11-13
	 * @author yinZq
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSettleDtlNew(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String queryMchtNoNm = request.getParameter("queryMchtNoNm");
		StringBuffer whereSql = new StringBuffer(" where 1=1  ");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_STLM").append(" >= ").append("'")
					.append(startDate).append("'");
		}

		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_STLM").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			whereSql.append(" AND MCHT_ID").append(" = ").append("'")
					.append(queryMchtNoNm.trim()).append("'");
		}
		String sql = "select DATE_STLM,MCHT_ID,(select b.channel_id||'-'||b.channel_name from TBL_PAY_CHANNEL_INFO b where b.channel_id=a.channel_id) as channel_id_nm,BEG_DATE,END_DATE,AMT_NORMAL,AMT_FREEZE,AMT_ERR_MINUS,AMT_ERR_ADD,AMT_HANGING,AMT_SETTLE, PAY_TYPE,MCHT_ID||'-'||MCHT_NM,MCHT_ACCT_NO,MCHT_ACCT_NM,CNAPS_ID,CNAPS_NAME,AREA_NM,LST_UPD_TM  from TBL_SETTLE_DTL a "
				+ whereSql + " order by MCHT_ID ";

		String countSql = "select count(*) from TBL_SETTLE_DTL a " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			objects[5] = CommonFunction.moneyFormat(objects[5].toString());
			objects[6] = CommonFunction.moneyFormat(objects[6].toString());
			objects[7] = CommonFunction.moneyFormat(objects[7].toString());
			objects[8] = CommonFunction.moneyFormat(objects[8].toString());
			objects[9] = CommonFunction.moneyFormat(objects[9].toString());
			objects[10] = CommonFunction.moneyFormat(objects[10].toString());
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风空冻结查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRecoverTxn(int begin, HttpServletRequest request) {

		String queryMchtNo = request.getParameter("queryMchtNo");
		String querySysSeqNum = request.getParameter("querySysSeqNum");

		String queryBlockFlag = request.getParameter("queryBlockFlag");
		
		String queryPan=request.getParameter("queryPan");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");

		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (StringUtil.isNotEmpty(queryMchtNo)) {
			whereSql.append(" and CARD_ACCP_ID ='").append(queryMchtNo)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(querySysSeqNum)) {
			whereSql.append(" and SYS_SEQ_NUM  ='").append(querySysSeqNum)
					.append("' ");
		}

		if (StringUtil.isNotEmpty(queryBlockFlag)) {

			whereSql.append(" and FREEZE_FLAG ='").append(queryBlockFlag)
					.append("' ");

		}
		if (StringUtil.isNotEmpty(startDate)) {

			whereSql.append(" and TXN_DT >='").append(startDate)
					.append("' ");

		}
		if (StringUtil.isNotEmpty(endDate)) {

			whereSql.append(" and TXN_DT <='").append(endDate)
					.append("' ");

		}
		if (StringUtil.isNotEmpty(queryPan)) {

			whereSql.append(" and PAN like'").append("%"+queryPan+"%")
					.append("' ");

		}
		String sql = "select CARD_ACCP_ID,CARD_ACCP_ID||(select '-'||MCHT_NM from tbl_mcht_base_inf where MCHT_NO=CARD_ACCP_ID),TXN_DT,SYS_SEQ_NUM,(select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =a.txn_num )TXN_NUM,TRANS_STATE,FREEZE_FLAG,STLM_FLAG,INST_CODE,PAN,AMT_TRANS/100,AMT_FEE/100,AMT_STLM/100 from tbl_chk_freeze a "
				+ whereSql.toString() + " order by sys_seq_num ";
		String countSql = " select count(*) from tbl_chk_freeze a  "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			if (objects[10] != null && !"".equals(objects[10])) {
				objects[10] = CommonFunction
						.moneyFormat(objects[10].toString());
			}
			if (objects[11] != null && !"".equals(objects[11])) {
				objects[11] = CommonFunction
						.moneyFormat(objects[11].toString());
			}
			if (objects[12] != null && !"".equals(objects[12])) {
				objects[12] = CommonFunction
						.moneyFormat(objects[12].toString());
			}
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户冻结解冻
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtFreeze(int begin, HttpServletRequest request) {

		String queryMchtNo = request.getParameter("queryMchtNo");
		String queryFreezeFlag = request.getParameter("queryFreezeFlag");
		String queryUnFreezeFlag = request.getParameter("queryUnFreezeFlag");
		String queryBatch = request.getParameter("queryBatch");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (StringUtil.isNotEmpty(queryMchtNo)) {
			whereSql.append(" and MCHT_ID ='").append(queryMchtNo)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryFreezeFlag)) {
			whereSql.append(" and FREEZE_FLAG  ='").append(queryFreezeFlag)
					.append("' ");
		}

		if (StringUtil.isNotEmpty(queryUnFreezeFlag)) {

			whereSql.append(" and UNFREEZE_FLAG ='").append(queryUnFreezeFlag)
					.append("' ");

		}
		if (StringUtil.isNotEmpty(queryBatch)) {

			whereSql.append(" and BATCH_NO like'").append("%"+queryBatch+"%")
					.append("' ");

		}
		if (StringUtil.isNotEmpty(startDate)) {

			whereSql.append(" and FREEZE_DATE >='").append(startDate)
					.append("' ");

		}
		if (StringUtil.isNotEmpty(endDate)) {

			whereSql.append(" and FREEZE_DATE <='").append(endDate)
					.append("' ");

		}
		String sql = "select MCHT_ID||(select '-'||MCHT_NM from tbl_mcht_base_inf where MCHT_NO=MCHT_ID),FREEZE_AMT,DO_FREEZE_AMT,BATCH_NO,FREEZE_DATE,FREEZE_FLAG,UNFREEZE_FLAG,UNFREEZE_DATE,INST_DATE,UPDATE_DT from TBL_MCHT_FREEZE "
				+ whereSql.toString() + " order by BATCH_NO desc";
		String countSql = " select count(*) from TBL_MCHT_FREEZE  "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			if (objects[1] != null && !"".equals(objects[1])) {
				objects[1] = CommonFunction
						.moneyFormat(objects[1].toString());
			}
			if (objects[2] != null && !"".equals(objects[2])) {
				objects[2] = CommonFunction
						.moneyFormat(objects[2].toString());
			}		
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 商户冻结解冻明细
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtFreezeDtl(int begin, HttpServletRequest request) {

		String batchNo = request.getParameter("batchNo");
	
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(batchNo)) {

			whereSql.append(" and BATCH_NO ='").append(batchNo)
					.append("' ");

		}
		String sql = "select MCHT_ID||(select '-'||MCHT_NM from tbl_mcht_base_inf where MCHT_NO=MCHT_ID),BATCH_NO,DATE_STLM,FREEZE_FLAG,CHANNLE_ID,FREEZE_AMT,INST_DATE from TBL_MCHT_FREEZE_DTL "
				+ whereSql.toString() + " order by BATCH_NO desc";
		String countSql = " select count(*) from TBL_MCHT_FREEZE_DTL  "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		for (Object[] objects : dataList) {
			if (objects[5] != null && !"".equals(objects[5])) {
				objects[5] = CommonFunction
						.moneyFormat(objects[5].toString());
			}
					
		}

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户信息差分列表 
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtInfDiff(int begin, HttpServletRequest request) {

		String mchntId = request.getParameter("mchntId");
	
		Object[] ret = new Object[2];
		
		//修改前数据查询sql
		StringBuilder sqlCommon = new StringBuilder();
		sqlCommon.append("SELECT MCHT_GROUP_FLAG 商户类别,MCHT_GROUP_ID 集团商户,MCHT_NM 中文名称,MCHT_CN_ABBR 中文名称简写,ENG_NAME 英文名称,");
		sqlCommon.append("SIGN_INST_ID 签约机构,MCHT_GRP 商户组别,b.RISL_LVL||(select distinct '-'||c.RESVED from TBL_RISK_LVL c where c.RISK_LVL=b.RISL_LVL) as 风险级别,AREA_NO 所在地区,MCC,TCC 是否标准入网,MCHT_FUNCTION 业务类型,");
		sqlCommon.append("LICENCE_NO 营业执照号码,FAX_NO 税务登记证号码,ETPS_ATTR 企业性质,COMM_EMAIL 电子邮件,HOME_PAGE 公司网址,ADDR 商户地址,");
		sqlCommon.append("POST_CODE 邮政编码,MANAGER 法人代表,BUS_AMT 注册资金,ARTIF_CERTIF_TP 法人代表证件类型,IDENTITY_NO 法人代表证件号码,");
		sqlCommon.append("CONTACT 联系人姓名,COMM_TEL 联系人电话,FAX 企业传真,REG_ADDR 注册地址,PROL_TLR 经营单位,OPEN_TIME 营业开始时间,");
		sqlCommon.append("CLOSE_TIME 营业结束时间,SUBSTR (SETTLE_ACCT, 0, 1) 商户结算账户类型,OPEN_STLNO 商户账户开户行号,SETTLE_BANK_NM 商户账户开户行名称,");
		sqlCommon.append("SUBSTR (SETTLE_ACCT, 2) 商户结算帐户开户行号,SETTLE_ACCT_NM 商户账户户名,FEE_RATE 手续费类型");
		
//		StringBuilder sqlAfter = new StringBuilder(sqlCommon.toString());
//		sqlAfter.append(" FROM TBL_MCHT_BASE_INF_TMP b,TBL_MCHT_SETTLE_INF_TMP s ");
//		sqlAfter.append(" WHERE b.MCHT_NO='"+mchntId+"' AND b.MCHT_NO = s.MCHT_NO");
		
		//修改历史数据查询sql
		StringBuilder sqlBefore = new StringBuilder(sqlCommon.toString());
		sqlBefore.append(" FROM TBL_MCHT_BASE_INF_TMP_HIST b,TBL_MCHT_SETTLE_INF_TMP_HIST s ");
		sqlBefore.append(" WHERE b.MCHT_NO='"+mchntId+"' AND b.MCHT_NO = s.MCHT_NO AND b.MCHT_NO_NEW = s.MCHT_NO_NEW order by B.mcht_NO_NEW desc");
		//sqlBSb.append(" AND b.MCHT_NO_NEW = s.MCHT_NO_NEW");

		List<Object[]> dataListBefore = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlBefore.toString());

		List<Object[]> dataListResult = new ArrayList<Object[]>();
		//对比列的个数(等于sql查询出的列数+1(1为最左边的固定文字列))
		int colsNum = 37;
		
		String blank = "";
		
		//修改前内容
		Object[] resultsBefor =  new Object[colsNum];
		resultsBefor[0]="修改前";
		//修改前内容
		Object[] resultsAfter =  new Object[colsNum];
		resultsAfter[0]="修改后";
		
		if (dataListBefore.size() <= 1){
			ret[1] = 0;
		}else{
			//List<Object[]> dataListAfter = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlAfter.toString());
//			if (dataListAfter.size() == 0){
//				ret[1] = 0;
//			}else{
				ret[1] = 1;
				
				//获取历史记录
				Object[] objectsBefore = dataListBefore.get(1);
				Object[] objectsAfter = dataListBefore.get(0);
				
				for (int i = 0; i < objectsBefore.length; i++) {
					if(objectsBefore[i] == null && objectsAfter[i] == null){
						resultsBefor[i+1] = blank;
						resultsAfter[i+1] = blank;
					}else if (objectsBefore[i] == null && objectsAfter[i] != null){
						resultsBefor[i+1] = blank;
						resultsAfter[i+1] = objectsAfter[i].toString();
					}else if (objectsBefore[i] != null && objectsAfter[i] == null){
						resultsBefor[i+1] = objectsBefore[i].toString();
						resultsAfter[i+1] = blank;
					}else{
						//内容相同
						if (objectsBefore[i].equals(objectsAfter[i])){
							resultsBefor[i+1] = blank;
							resultsAfter[i+1] = blank;
							//内容不同
						}else{
							resultsBefor[i+1] = objectsBefore[i].toString();
							resultsAfter[i+1] = objectsAfter[i].toString();
						}
					}
//				}
			}
		}
		dataListResult.add(resultsBefor);
		dataListResult.add(resultsAfter);
		ret[0] = dataListResult;
		return ret;
	}

	public static Object[] getModelList(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		String modelName = request.getParameter("modelName");
		String status = request.getParameter("status");
		String createTime = request.getParameter("createTime");
		String updateTime = request.getParameter("updateTime");

		if (isNotEmpty(modelName)) {
			whereSql.append(" and MODEL_NAME like '%"+modelName+"%'");
		}
		if (isNotEmpty(status)) {
			whereSql.append(" and STATUS = "+status+" ");
		}
		if (isNotEmpty(createTime)) {
			createTime = createTime.replaceAll("-","").substring(0,8);
			whereSql.append(" and CREATE_TIME like '%"+createTime+"%'");
		}
		if (isNotEmpty(updateTime)) {
			updateTime = updateTime.replaceAll("-","").substring(0,8);
			whereSql.append(" and UPDATE_TIME like '%"+updateTime+"%'");
		}

		String sql = "select * from TBL_MODEL_INFO "+ whereSql+" order by STATUS desc , MODEL_ID asc ";
		String countSql = " select count(*) from TBL_MODEL_INFO  "+ whereSql;

		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	public static Object[] getModelInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select a.MODEL_NAME, a.STATUS ,b.FIELD_NAME, b.FONT_SIZE, b.FIELD_ORDER, b.CONT_FORMAT_TYPE, b.CONT_FORMAT,b.STATUS from TBL_MODEL_INFO a, TBL_MODEL_PARAMETER b where a.MODEL_ID = b.MODEL_ID and a.MODEL_ID = "+begin;
		String countSql = " select count(*) from TBL_MODEL_INFO  ";

		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	//商户组查询
		public static Object[] getRouteMchtgWithProperty(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			
			String mchtGid = request.getParameter("mchtGid");
			StringBuffer whereSql = new StringBuffer(" where 1=1 ");
			
			if (!isNotEmpty(mchtGid)) {
				mchtGid="1";
			}
			
			String sql = "SELECT c.MCHT_GID,c.MCHT_GNAME,c.MCHT_GDSP from TBL_ROUTE_MCHTG c where c.MCHT_GID in (select distinct(mcht_gid) from TBL_ROUTE_RULE_INFO where status='0') and c.MCHT_GID !='"+mchtGid+"'";
			String countSql = " select count(*) from TBL_ROUTE_MCHTG c where c.MCHT_GID in (select distinct(mcht_gid) from TBL_ROUTE_RULE_INFO where status='0') and c.MCHT_GID !='"+mchtGid+"'";

			@SuppressWarnings("unchecked")
			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
	
	//
	//商户组详细信息查询
		public static Object[] getRouteMchtgDetail(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			StringBuffer whereSql = new StringBuffer("");
			
			String mchtNo = request.getParameter("mchtNoDetail");
			String mchtNm = request.getParameter("mchtNmDetail");
			String mchtAddr = request.getParameter("mchtAddr");
			String mchtMcc = request.getParameter("mchtMcc");
			String mchtGid = request.getParameter("mchtGid");

			if (isNotEmpty(mchtNo)) {
				whereSql.append(" AND b.MCHT_NO ='"+mchtNo+"' ");
			}
			if (isNotEmpty(mchtMcc)) {
				whereSql.append(" AND b.MCC ='"+mchtMcc+"' ");
			}
			if (isNotEmpty(mchtNm)) {
				whereSql.append("AND b.MCHT_NM LIKE '%"+mchtNm+"%' ");
			}
			if (isNotEmpty(mchtAddr)) {
				whereSql.append(" and trim(b.AREA_NO) =(select d.MCHT_CITY_CODE from CST_CITY_CODE d where d.CITY_NAME like '"+mchtAddr+"%')");
			}
			
			String sql = "SELECT b.MCHT_NO,b.MCHT_NM,(select c.CITY_NAME FROM CST_CITY_CODE c WHERE C.MCHT_CITY_CODE =trim(b.AREA_NO)) AREA_NO,b.MCC from TBL_MCHT_BASE_INF b WHERE b.MCHT_NO IN (SELECT a.MCHT_ID from TBL_ROUTE_MCHTG_DETAIL a WHERE a.MCHT_GID='"+mchtGid+"')"+whereSql;
			String countSql = " select count(*) from TBL_MCHT_BASE_INF b WHERE b.MCHT_NO IN (SELECT a.MCHT_ID from TBL_ROUTE_MCHTG_DETAIL a WHERE a.MCHT_GID='"+mchtGid+"')"+whereSql;

			@SuppressWarnings("unchecked")
			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
		
	/**
	 * 路由商户详细信息查询
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getMchtDetailWithConditions(int begin, HttpServletRequest request) {Object[] ret = new Object[2];
		StringBuffer whereSql1 = new StringBuffer("  AND instr(m.MCHT_NO,'AAA')= 0 ");
		StringBuffer whereSql2 = new StringBuffer(" where 1=1 ");
		
		String mchtNo = request.getParameter("mchtNo");			//商户
		String partnerNo = request.getParameter("partnerNo");	//合作伙伴
		String mcc = request.getParameter("mcc");				//MCC
		String mchtAddr = request.getParameter("mchtAddr");		//商户地区
		
		String payRoad = request.getParameter("payRoad");		//支付渠道
		String business = request.getParameter("business");		//业务
		String proper = request.getParameter("proper");			//性质
		String qmchtNo = request.getParameter("qmchtNo");		//渠道商户
		String queryCharConAndId = request.getParameter("queryCharConAndId");	//是否包含性质
		String mchtGroup = request.getParameter("mchtGroup");	//路由商户组
		boolean parseBoolean = Boolean.parseBoolean(queryCharConAndId);
				
		if (isNotEmpty(mchtNo)&&!"*".equals(mchtNo)) {
			whereSql1.append(" AND m.MCHT_NO ='"+mchtNo+"' ");
		}
		if (isNotEmpty(mcc)) {
			whereSql1.append(" AND m.MCC ='"+mcc+"' ");
		}
		if (isNotEmpty(mchtAddr)) {
			whereSql1.append(" and m.AREA_NO ='"+mchtAddr+"' ");
		}
		if (isNotEmpty(partnerNo)) {
			whereSql1.append(" AND m.bank_no = '"+partnerNo+"' ");
		}
		String sql = "SELECT distinct a.mcht_no, a.mcht_nm,a.area, a.mcc,a.settleRate,a.bill, a.integral,a.brh_id,a.brh_no,a.brh_name "
				+ "	  FROM (SELECT m.mcht_no, m.mcht_nm, m.mcc, s.bank_statement bill,s.integral,b.brh_id, b.create_new_no brh_no, b.brh_name, "
				+ "	           (SELECT c.CITY_NAME "
				+ "	              FROM CST_CITY_CODE c "
				+ "	             WHERE C.MCHT_CITY_CODE = TRIM(m.AREA_NO)) area, "
				+ "	           (case substr(s.spe_settle_tp, 1, 1) "
				+ "	             when '0' then "
				+ "	              (select p.fee_name "
				+ "	                 from tbl_profit_rate_info p "
				+ "	                where p.rate_id = substr(s.spe_settle_tp, 2, 2)) "
				+ "	             when '1' then "
				+ "	              (select p.fee_name "
				+ "	                 from tbl_profit_rate_info p "
				+ "	                where p.rate_id = substr(s.spe_settle_tp, 2, 2)) || '-' || "
				+ "	              (select p.fee_name "
				+ "	                 from tbl_profit_rate_info p "
				+ "	                where p.rate_id = substr(s.spe_settle_tp, 4, 2)) "
				+ "	             else '' "
				+ "	           end) settleRate    "    
				+ "	      FROM TBL_MCHT_BASE_INF m "
				+ "	      join tbl_mcht_settle_inf s on m.mcht_no = s.mcht_no "
				+ "	      join tbl_brh_info b on m.bank_no = b.brh_id "
				+ "	     WHERE m.mcht_status = '0'     "   
				+ whereSql1.toString()
				+ "	   ) a" ;
		
		String countSql = "SELECT  count(distinct a.mcht_no)  "
				+ "	  FROM (SELECT m.mcht_no "
				+ "	      FROM TBL_MCHT_BASE_INF m "
				+ "	      join tbl_mcht_settle_inf s on m.mcht_no = s.mcht_no "
				+ "	      join tbl_brh_info b on m.bank_no = b.brh_id "
				+ "	     WHERE m.mcht_status = '0'     "
				+ whereSql1.toString()
				+ "	   ) a ";
				
		StringBuffer subQuerySql = new StringBuffer();
		if (isNotEmpty(payRoad)||isNotEmpty(business)||isNotEmpty(proper)||isNotEmpty(qmchtNo) || isNotEmpty(mchtGroup)) {
			subQuerySql.append(" left join Tbl_Route_Rule_Map rrm on rrm.mcht_id=a.MCHT_NO");
			if (isNotEmpty(payRoad)) {
				subQuerySql.append(" left join Tbl_Route_Upbrh ru1 on ru1.brh_id=substr(rrm.brh_id3,1,4) and ru1.brh_level='1' and ru1.status='0' ");
				whereSql2.append(" AND ru1.BRH_ID = '"+payRoad+"' ");
			}	
			if (isNotEmpty(business)) {
				subQuerySql.append(" left join Tbl_Route_Upbrh ru2 on ru2.brh_id=substr(rrm.brh_id3,1,8) and ru2.brh_level='2' and ru2.status='0' ");
				whereSql2.append(" AND ru2.BRH_ID =  '"+business+"' ");
			}	
			if (isNotEmpty(proper)) {
				subQuerySql.append(" left join Tbl_Route_Upbrh ru3 on ru3.brh_id = rrm.brh_id3 and ru3.brh_level='3' and ru3.status='0' ");
				if(parseBoolean==true){
					whereSql2.append(" AND ru3.NAME LIKE '%"+proper+"%' ");
				} else {
					whereSql2.append(" AND (ru3.brh_id is null "
							+ " or a.MCHT_NO not in ( "
							+ "  select distinct t1.mcht_id"
							+ " from Tbl_Route_Rule_Map t1,Tbl_Route_Upbrh t2 "
							+ " where t1.brh_id3 = t2.brh_id"
							+ "    and t2.status='0' and t2.brh_level='3' "
							+ "  and t2.NAME LIKE '%"+ proper +"%' ) )");
				}
			}
			
			if (isNotEmpty(qmchtNo)&&!"*".equals(qmchtNo.trim())) {
				subQuerySql.append("  left join TBL_UPBRH_MCHT tum on tum.mcht_id_up=rrm.mcht_id_up and tum.term_id_up = substr(rrm.MISC1,0,8) ");
				whereSql2.append(" AND tum.mcht_id_up ='"+qmchtNo+"' ");
			}
			if (isNotEmpty(mchtGroup)&&!"*".equals(mchtGroup.trim())) {
				subQuerySql.append("   left join tbl_route_mchtg_detail mg on rrm.mcht_id = mg.mcht_id ");
				whereSql2.append(" AND mg.mcht_gid ='"+mchtGroup+"' ");
			}
		}
		
		if(StringUtil.isNotEmpty(subQuerySql)){
			sql += subQuerySql.toString();
			countSql += subQuerySql.toString();
		}
		
		sql += whereSql2;
		countSql += whereSql2;
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}



		/**
		 * MCC风控参数管理
		 * @author yww
		 * 2016年4月5日  下午3:49:57
		 * @param begin
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static Object[] getRiskParamMcc(int begin, HttpServletRequest request) {

			String mccQuery = request.getParameter("mccQuery");
			Object[] ret = new Object[2];
			StringBuffer whereSql = new StringBuffer(" where 1=1 ");

			if (StringUtil.isNotEmpty(mccQuery)) {
				whereSql.append(" and a.MCC ='").append(mccQuery).append("'");
			}

			String sql = "SELECT a.MCC, b.DESCR as mccDesc, a.MCHT_CO_AMT, a.MCHT_SE_AMT, a.MCHT_NL_AMT, a.UPD_OPR,UPD_TIME,a.REMARK FROM TBL_RISK_PARAM_MCC a left join TBL_INF_MCHNT_TP b on a.MCC = b.MCHNT_TP "
					+ whereSql.toString() + " order by UPD_TIME desc ";
			
			String countSql = " select count(*) from TBL_RISK_PARAM_MCC a "+ whereSql.toString();

			List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql,begin,Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

			if (dataList.size() > 0) {
				for (Object[] objects : dataList) {
					objects[2] = CommonFunction.moneyFormat(objects[2].toString());
					objects[3] = CommonFunction.moneyFormat(objects[3].toString());
					objects[4] = CommonFunction.moneyFormat(objects[4].toString());
				}
			}
			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
			
/**
 * 渠道商户列表
 */
@SuppressWarnings("unchecked")
public static Object[] getTblUpbrhMcht(int begin,
		HttpServletRequest request) {
	Object[] ret = new Object[2];

	StringBuffer whereSql = new StringBuffer();
	whereSql.append(" where 1=1 ");
	if (isNotEmpty(request.getParameter("mchtIdUp"))) {
		//渠道商户号精确查询
		whereSql.append(" AND  a.MCHT_ID_UP = '" + request.getParameter("mchtIdUp")+ "' ");
	}
	if (isNotEmpty(request.getParameter("mchtNameUp"))) {
		//渠道商户名称模糊查询
		whereSql.append(" AND a.mcht_name_up like '%"+ request.getParameter("mchtNameUp") + "%' ");
	}
	if (isNotEmpty(request.getParameter("termIdUp"))) {
		//渠道商户名称模糊查询
		whereSql.append(" AND a.TERM_ID_UP like '%"+ request.getParameter("termIdUp") + "%' ");
	}
	if (isNotEmpty(request.getParameter("channelNm"))) {
		//支付渠道精确查询
		whereSql.append(" AND  ru1.BRH_ID = '" + request.getParameter("channelNm") + "' ");
	}
	if (isNotEmpty(request.getParameter("businessNm"))) {
		//业务精确查询
		whereSql.append(" AND ru2.BRH_ID = '"+ request.getParameter("businessNm") + "' ");
	}
	if (isNotEmpty(request.getParameter("characterNm"))) {
		//性质精确查询
		whereSql.append(" AND ru3.BRH_ID = '"+ request.getParameter("characterNm") + "' ");
	}
	if (isNotEmpty(request.getParameter("status"))) {
		//渠道商户状态精确查询
		whereSql.append(" AND a.STATUS = '"+ request.getParameter("status") + "' ");
	}
	if (isNotEmpty(request.getParameter("area"))) {
		//所属地区精确查询
		whereSql.append(" AND a.AREA = '"+ request.getParameter("area") + "' ");
	}
	if (isNotEmpty(request.getParameter("mchtId"))) {
		//渠道商户号精确查询
		whereSql.append(" AND a.MCHT_ID = '"+ request.getParameter("mchtId") + "' ");
	}
	if (isNotEmpty(request.getParameter("misc1"))) {
		//特殊计费查询 -- 特殊计费是misc_1字段的第一个字节
		whereSql.append(" AND trim(substr(a.misc_1,1,length(a.misc_1))) = '"+ request.getParameter("misc1") + "' ");
	}

	//关联本地商户数量最小值
	String min = request.getParameter("min");
	//关联本地商户数量最大值
	String max = request.getParameter("max");
	
	if (!StringUtils.isBlank(min) && !"0".equals(min) && StringUtils.isBlank(max) ) {
		whereSql.append(" AND d.amount >= '"+ min + "' ");
	}
	if (StringUtils.isBlank(min) && !StringUtils.isBlank(max)) {
		whereSql.append(" AND d.amount <= '"+ max + "' or d.amount is null ");
	}
	if (!StringUtils.isBlank(min) && !StringUtils.isBlank(max)) {
		if (!"0".equals(min)) {
			whereSql.append("  AND d.amount >= '"+ min + "' AND d.amount <= '"+ max + "' ");
		}else {
			whereSql.append(" AND d.amount <= '"+ max + "' or d.amount is null ");
		}
	}
	

	//查询显示地区名称
	String sql = "SELECT a.brh_Id3, a.MCHT_ID_UP, a.MCHT_NAME_UP, a.TERM_ID_UP,ru1.BRH_ID as channelId,ru1.name as channelNm,ru2.BRH_ID as businessId,ru2.name as businessNm,ru3.BRH_ID as characterId, ru3.name as characterNm, a.STATUS, c.MCHT_CITY_CODE||' - '||c.CITY_NAME as area, a.MCHT_ID, b.MCHT_NM, nvl(d.amount,0) as amount,a.RUN_TIME, a.STOP_TIME, a.STOP_TYPE, a.STOP_REASON, a.UPT_TIME, a.UPT_OPR,a.ZMK ,a.DISC_ID,a.mcht_dsp,a.misc_1 FROM TBL_UPBRH_MCHT a left join Tbl_Route_Upbrh ru1 on substr(a.brh_id3, 1, 4) = ru1.brh_id and ru1.BRH_LEVEL='1' and ru1.STATUS='0' left join Tbl_Route_Upbrh ru2 on substr(a.brh_id3, 1, 8) = ru2.brh_id and ru2.BRH_LEVEL='2' and ru2.STATUS='0' left join Tbl_Route_Upbrh ru3 on a.brh_id3 = ru3.brh_id and ru3.BRH_LEVEL='3' and ru3.STATUS='0' left join TBL_MCHT_BASE_INF b on a.MCHT_ID = b.MCHT_NO left join cst_city_code c on a.area = c.MCHT_CITY_CODE left join (select mcht_id_up, count(distinct mcht_id) as amount from Tbl_Route_Rule_Map group by mcht_id_up) d on a.MCHT_ID_UP=d.MCHT_ID_UP "
			+ whereSql.toString()+" order by a.UPT_TIME desc";

	String countSql = "SELECT COUNT(*) FROM TBL_UPBRH_MCHT a left join Tbl_Route_Upbrh ru1 on substr(a.brh_id3, 1, 4) = ru1.brh_id and ru1.BRH_LEVEL='1' and ru1.STATUS='0' left join Tbl_Route_Upbrh ru2 on substr(a.brh_id3, 1, 8) = ru2.brh_id and ru2.BRH_LEVEL='2' and ru2.STATUS='0' left join Tbl_Route_Upbrh ru3 on a.brh_id3 = ru3.brh_id and ru3.BRH_LEVEL='3' and ru3.STATUS='0' left join TBL_MCHT_BASE_INF b on a.MCHT_ID = b.MCHT_NO left join cst_city_code c on a.area = c.MCHT_CITY_CODE left join (select mcht_id_up, count(distinct mcht_id) as amount from Tbl_Route_Rule_Map group by mcht_id_up) d on a.MCHT_ID_UP=d.MCHT_ID_UP "+ whereSql.toString();

	List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
	String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
	ret[0] = dataList;
	ret[1] = count;
	return ret;
}

	/**
	 * 成本扣率维护列表
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblUpbrhFee(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		
		String sql = "SELECT DISC_ID, DISC_NM, BRH_ID2, STATUS, CRT_TIME, CRT_OPR, UPT_TIME, UPT_OPR FROM TBL_UPBRH_FEE order by  UPT_TIME desc";
		String countSql = "SELECT COUNT(*) FROM TBL_UPBRH_FEE ";

		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 成本扣率明细
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getUpbrhFeeDetail(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		if (isNotEmpty(request.getParameter("discId"))) {
			//渠道商户号精确查询
			whereSql.append(" AND  DISC_ID = '" + request.getParameter("discId")+ "' ");
		}
		
		String sql = "SELECT DISC_ID, MAX_FEE, FLAG, FEE_VALUE, TXN_NUM, CARD_TYPE FROM TBL_UPBRH_FEE_DETAIL" +whereSql;
		String countSql = "SELECT COUNT(*) FROM TBL_UPBRH_FEE_DETAIL " +whereSql;

		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 成本扣率配置左边页面数据源
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCostDiscount(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("channelNm"))) {
			//渠道商户号精确查询
			whereSql.append(" and substr(tr.brh_id2, 1, 4) = '" + request.getParameter("channelNm")+ "' ");
		}
		if (isNotEmpty(request.getParameter("businessNm"))) {
			//渠道商户号精确查询
			whereSql.append(" and substr(tr.brh_id2, 1, 8) = '" + request.getParameter("businessNm")+ "' ");
		}
		if (isNotEmpty(request.getParameter("isfee"))) {
			//渠道商户号精确查询
			whereSql.append(" and tr.isfee = '" + request.getParameter("isfee")+ "' ");
		}
		
		String sql = "select rt1.brh_id,rt2.name as channelNm,rt1.name as businessNm,tr.isfee from Tbl_Route_Upbrh rt1, Tbl_Route_Upbrh rt2, Tbl_Route_Upbrh2 tr where tr.brh_id2 = rt1.brh_id and substr(rt1.brh_id, 1, 4) = rt2.brh_id " +whereSql;
		String countSql = "SELECT COUNT(*) from Tbl_Route_Upbrh rt1, Tbl_Route_Upbrh rt2, Tbl_Route_Upbrh2 tr where tr.brh_id2 = rt1.brh_id and substr(rt1.brh_id, 1, 4) = rt2.brh_id " +whereSql;

		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}


	/**
	 * 成本扣率配置右边页面数据源
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCostDiscountDetail(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		String a = request.getParameter("brhId");
		String str ="";
		if (!StringUtils.isBlank(a)) {
			str = " and b.brh_id = "+a;
		}else {
			str = "and 1=1";
		}
		String sql = "select a.DISC_ID, a.DISC_NM, a.STATUS, a.UPT_TIME, a.UPT_OPR from Tbl_Upbrh_Fee a ,Tbl_Route_Upbrh b where a.brh_id2 = b.brh_id "+str;
		String countSql = "SELECT COUNT(*) from Tbl_Upbrh_Fee a ,Tbl_Route_Upbrh b where a.brh_id2 = b.brh_id "+str;

		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 支付渠道维护
	 * 2015-10-23
	 * zhaofachun
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getChannel(int begin, HttpServletRequest request) {

		String queryChannel = request.getParameter("queryChannel");
		String queryBusiness = request.getParameter("queryBusiness");
		String queryDealType = request.getParameter("queryDealType");
		String queryChlStatus = request.getParameter("queryChlStatus");

		Object[] ret = new Object[2];
		
		String chlId = "";
		if (StringUtil.isNotEmpty(queryChannel)) {
			chlId = queryChannel.trim();
		}else if(StringUtil.isNotEmpty(queryBusiness) && queryBusiness.length()>4){
			chlId = queryBusiness.trim().substring(0, 4);
		}
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(chlId)) {
			whereSql.append(" and tc.brh_id = '").append(chlId).append("'");
		}
		if (StringUtil.isNotEmpty(queryChlStatus)) {
			whereSql.append(" and tc.status = '").append(queryChlStatus).append("'");
		}
		if (StringUtil.isNotEmpty(queryBusiness)) {
			whereSql.append(" and tbd.brh_id2 = '").append(queryBusiness).append("'");
		}

		if (StringUtil.isNotEmpty(queryDealType)) {
			whereSql.append(" and  tbd.supt_txn like '%").append(queryDealType).append("%'");
		}

		String sql = "select busiId,busiName,area,keyType,dealType,chlName, chlStatus, busiStatus,enDate,onFlag"
				//20160426 郭宇 添加 渠道ID
				+ ",busiId1 "
				+ "  from (select tb.brh_id busiId, tb.brh_id||'-'||tb.name busiName, tbd.supt_area area, decode(tbd.enc_type,'0','一机一密','1','机构加密','') keyType, tbd.supt_txn dealType, tc.brh_id||'-'||tc.name chlName, tc.status chlStatus, tb.status busiStatus,tb.use_time enDate,"
				+ "               decode(nvl((select count(*) from Tbl_Route_Rule_Info t4 where substr(t4.brh_id3, 1, 8) = tb.brh_id and t4.status = '0'), 0), 0, 'T', 'F') onFlag"
				+ " ,tc.brh_id busiId1 "
				+ "          from TBL_ROUTE_UPBRH tb"
				+ "         inner join  TBL_ROUTE_UPBRH tc"
				+ "            on substr(tb.brh_id, 1, 4) = tc.brh_id and tb.brh_level = '2' and tc.brh_level = '1'"
				+ "          left join Tbl_Route_Upbrh2 tbd"
				+ "            on tb.brh_id = tbd.brh_id2"
				+ whereSql.toString()
				+ "           ) r "
				+ "order by r.enDate desc ,r.busiId desc";
		String countSql = "select count(*) from TBL_ROUTE_UPBRH tb  inner join  TBL_ROUTE_UPBRH tc   on substr(tb.brh_id, 1, 4) = tc.brh_id and tb.brh_level = '2' and tc.brh_level = '1'  left join Tbl_Route_Upbrh2 tbd  on tb.brh_id = tbd.brh_id2"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	//商户组业务性质查询
	public static Object[] getRouteUpbrh(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		
		String mchtGid = request.getParameter("mchtGid");
		
		String sql = "  SELECT distinct "+
				"  	ru1. NAME name1, "+
				"  	ru2. NAME name2, "+
				"  	ru3. NAME name3 ,"+
				"  	ru3.brh_id id3, "+
				"  	'请点击选择渠道商户 ' text"+
				"  FROM "+
				"  	Tbl_Route_Rule_Info rri "+
				"  LEFT JOIN Tbl_Route_Upbrh ru1 ON ru1.brh_id = SUBSTR (rri.brh_id3, 1, 4) "+
				"  AND ru1.brh_level = '1' "+
				"  AND ru1.status = '0' "+
				"  LEFT JOIN Tbl_Route_Upbrh ru2 ON ru2.brh_id = SUBSTR (rri.brh_id3, 1, 8) "+
				"  AND ru2.brh_level = '2' "+
				"  AND ru2.status = '0' "+
				"  LEFT JOIN Tbl_Route_Upbrh ru3 ON ru3.brh_id = rri.brh_id3 "+
				"  AND ru3.brh_level = '3' "+
				"  AND ru3.status = '0' "+
				"  WHERE "+
				"  	mcht_gid = '"+mchtGid+"' and rri.status='0' ";
		String countSql = "  SELECT "+
				"  	count(*) "+
				"  FROM "+
				"  	Tbl_Route_Rule_Info rri "+
				"  LEFT JOIN Tbl_Route_Upbrh ru1 ON ru1.brh_id = SUBSTR (rri.brh_id3, 1, 4) "+
				"  AND ru1.brh_level = '1' "+
				"  AND ru1.status = '0' "+
				"  LEFT JOIN Tbl_Route_Upbrh ru2 ON ru2.brh_id = SUBSTR (rri.brh_id3, 1, 8) "+
				"  AND ru2.brh_level = '2' "+
				"  AND ru2.status = '0' "+
				"  LEFT JOIN Tbl_Route_Upbrh ru3 ON ru3.brh_id = rri.brh_id3 "+
				"  AND ru3.brh_level = '3' "+
				"  AND ru3.status = '0' "+
				"  WHERE "+
				"  	mcht_gid = '"+mchtGid+"' and rri.status='0'";

		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	//商户组业务性质查询1
		public static Object[] getRouteUpbrh1(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			
			String mchtId = request.getParameter("mchtId");
			String sql = "   SELECT  distinct"+
					"   	ru1. NAME name1,  "+
					"   	ru2. NAME name2,  "+
					"   	ru3. NAME name3,  "+
					"   	ru3.brh_id id3,  "+
					"   	SUBSTR (rri.crt_time, 0, 8) aPPlyTIME,  "+
					"   	RRI.MCHT_ID_UP ID,  "+
					"   	TUM1.MCHT_NAME_UP NAME ,rri.misc1 "+
					"   FROM  "+
					"   	TBL_ROUTE_RULE_MAP rri  "+
					"   LEFT JOIN Tbl_Route_Upbrh ru1 ON ru1.brh_id = SUBSTR (rri.brh_id3, 1, 4)  "+
					"   AND ru1.brh_level = '1'  "+
					"   AND ru1.status = '0'  "+
					"   LEFT JOIN Tbl_Route_Upbrh ru2 ON ru2.brh_id = SUBSTR (rri.brh_id3, 1, 8)  "+
					"   AND ru2.brh_level = '2'  "+
					"   AND ru2.status = '0'  "+
					"   LEFT JOIN Tbl_Route_Upbrh ru3 ON ru3.brh_id = rri.brh_id3  "+
					"   AND ru3.brh_level = '3'  "+
					"   AND ru3.status = '0'  "+
					"   LEFT JOIN TBL_MCHT_BASE_INF T ON T .MCHT_NO = rri.mcht_id  "+
					"   LEFT JOIN TBL_UPBRH_MCHT TUM1 ON RRI.MCHT_ID_UP = TUM1.MCHT_ID_UP and rri.MISC1=TUM1.term_id_up and substr(rri.brh_id3, 1, 4)= substr(tum1.brh_id3, 1, 4) "+
					"   WHERE  "+
					"   	rri.mcht_id = '"+mchtId+"' "  ;
			String countSql = "  SELECT "+
					"  	count(*) "+
					"  FROM "+
					"   	TBL_ROUTE_RULE_MAP rri  "+
					"   LEFT JOIN Tbl_Route_Upbrh ru1 ON ru1.brh_id = SUBSTR (rri.brh_id3, 1, 4)  "+
					"   AND ru1.brh_level = '1'  "+
					"   AND ru1.status = '0'  "+
					"   LEFT JOIN Tbl_Route_Upbrh ru2 ON ru2.brh_id = SUBSTR (rri.brh_id3, 1, 8)  "+
					"   AND ru2.brh_level = '2'  "+
					"   AND ru2.status = '0'  "+
					"   LEFT JOIN Tbl_Route_Upbrh ru3 ON ru3.brh_id = rri.brh_id3  "+
					"   AND ru3.brh_level = '3'  "+
					"   AND ru3.status = '0'  "+
					"   LEFT JOIN TBL_MCHT_BASE_INF T ON T .MCHT_NO = rri.mcht_id  "+
					"   LEFT JOIN TBL_UPBRH_MCHT TUM1 ON RRI.MCHT_ID_UP = TUM1.MCHT_ID_UP and rri.MISC1=TUM1.term_id_up and substr(rri.brh_id3, 1, 4)= substr(tum1.brh_id3, 1, 4) "+
					"   WHERE  "+
					"   	rri.mcht_id = '"+mchtId+"' "  ;

			@SuppressWarnings("unchecked")
			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
	//根据性质查渠道商户
	public static Object[] getmchtUpbrh(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql=new StringBuffer("  where 1=1");   
		String mchtUpbrhNo = request.getParameter("mchtUpbrhNo");
		String mchtUpbrhNm = request.getParameter("mchtUpbrhNm");
		String area = request.getParameter("area");
		String mchtId = request.getParameter("mchtId");
		String mchtNm = request.getParameter("mchtNm");
		if (isNotEmpty(mchtUpbrhNo)&&!mchtUpbrhNo.equals("*")) {
			whereSql.append(" AND n.MCHT_ID_UP ='"+mchtUpbrhNo+"' ");
		}
		if (isNotEmpty(mchtUpbrhNm)) {
			whereSql.append(" AND n.MCHT_NAME_UP like '%"+mchtUpbrhNm+"%' ");
		}
		if (isNotEmpty(area)) {
			whereSql.append(" AND n.area LIKE '%"+area+"%' ");
		}
		if (isNotEmpty(mchtId)) {
			whereSql.append(" and n.mcht_id ='"+mchtId+"'");
		}
		if (isNotEmpty(mchtNm)) {
			whereSql.append(" AND n.mcht_nm like'%"+mchtNm+"%' ");
		}
		String wheresql=whereSql.toString();
		String properTyId = request.getParameter("properTyId");
		
		String sql = " select * from (SELECT t.MCHT_ID_UP,t.MCHT_NAME_UP,t.term_id_up,(select m.city_name from CST_CITY_CODE m where m.mcht_city_code=t.area ) area, "+
		"  t.mcht_id,(select n.mcht_nm from TBL_MCHT_BASE_INF n where n.mcht_no=t.mcht_id) mcht_nm FROM TBL_UPBRH_MCHT t WHERE t.BRH_ID3='"+properTyId+"' AND t.STATUS='0' ) n"+wheresql;
		String countSql = "SELECT count(1) FROM (SELECT t.MCHT_ID_UP,t.MCHT_NAME_UP,t.term_id_up,(select m.city_name from CST_CITY_CODE m where m.mcht_city_code=t.area ) area, "+
		"  t.mcht_id,(select n.mcht_nm from TBL_MCHT_BASE_INF n where n.mcht_no=t.mcht_id) mcht_nm FROM TBL_UPBRH_MCHT t WHERE t.BRH_ID3='"+properTyId+"' AND t.STATUS='0' ) n"+wheresql;

		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	//路由商户详细信息查询
			public static Object[] getMchtDetail(int begin, HttpServletRequest request) {Object[] ret = new Object[2];
			StringBuffer whereSql = new StringBuffer("where 1=1 AND instr(e.MCHT_NO,'AAA')= 0  ");
			
			String mchtNo = request.getParameter("mchtNo");
			String mchtNm = request.getParameter("mchtNm");
			String partnerNo = request.getParameter("partnerNo");
			String partnerNm = request.getParameter("partnerNm");
			String mcc = request.getParameter("mcc");
			String mchtAddr = request.getParameter("mchtAddr");
			String applyTime = request.getParameter("applyTime");
			//DateUtils.parseDate(applyTime, null);

			if (isNotEmpty(mchtNo)) {
				whereSql.append(" AND E.MCHT_NO ='"+mchtNo+"' ");
			}
			if (isNotEmpty(mcc)) {
				whereSql.append(" AND E.MCC ='"+mcc+"' ");
			}
			if (isNotEmpty(mchtNm)) {
				whereSql.append(" AND e.MCHT_NM LIKE '%"+mchtNm+"%' ");
			}
			if (isNotEmpty(mchtAddr)) {
				whereSql.append(" and E.AREA LIKE '"+mchtAddr+"%'");
			}
			if (isNotEmpty(partnerNo)) {
				whereSql.append(" AND E.BANK_NO ='"+partnerNo+"' ");
			}
			if (isNotEmpty(partnerNm)) {
				whereSql.append("AND E.BANK_NAME LIKE '%"+partnerNm+"%' ");
			}
			if (isNotEmpty(applyTime)) {
				whereSql.append(" and E.APPLYDATE='"+applyTime+"'");
			}
			String sql = " SELECT * FROM (SELECT"+
					"	b.MCHT_NO,"+
					"	b.MCHT_NM,"+
					"	("+
					"		SELECT"+
					"			c.CITY_NAME"+
					"		FROM"+
					"			CST_CITY_CODE c"+
					"		WHERE"+
					"			C.MCHT_CITY_CODE = TRIM (b.AREA_NO)"+
					"	) AREA,"+
					"	b.MCC,"+
					" 				( SELECT  "+
					" 					SUBSTR (TMSI1.SPE_SETTLE_TP, 1, 1) || '|' || (  "+
					" 						SELECT  "+
					" 							tpri1.fee_name name1  "+
					" 						FROM  "+
					" 							tbl_agent_rate_info tari1,  "+
					" 							tbl_profit_rate_info tpri1  "+
					" 						WHERE  "+
					" 							tari1.rate_id = tpri1.rate_id	  "+
					" 						AND tari1.fee_type = '0'  "+
					" 						AND tari1.rate_id = SUBSTR (TMSI1.SPE_SETTLE_TP, 2, 2)  "+
					" 						AND tari1.disc_id = (  "+
					" 							SELECT  "+
					" 								disc_id  "+
					" 							FROM  "+
					" 								tbl_agent_fee_cfg tafc1  "+
					" 							WHERE  "+
					" 								tafc1.agent_no = b.BANK_NO  "+
					" 						)  "+
					" 					) || '|' || (  "+
					" 						SELECT  "+
					" 							tpri1.fee_name name1  "+
					" 						FROM  "+
					" 							tbl_agent_rate_info tari1,  "+
					" 							tbl_profit_rate_info tpri1  "+
					" 						WHERE  "+
					" 							tari1.rate_id = tpri1.rate_id  "+
					" 						AND tari1.fee_type = '1'  "+
					" 						AND tari1.rate_id = SUBSTR (TMSI1.SPE_SETTLE_TP, 4, 2)  "+
					" 						AND tari1.disc_id = (  "+
					" 							SELECT  "+
					" 								disc_id  "+
					" 							FROM  "+
					" 								tbl_agent_fee_cfg tafc1  "+
					" 							WHERE  "+
					" 								tafc1.agent_no = b.BANK_NO  "+
					" 						)  "+
					" 					)  "+
					" 				FROM  "+
					" 					TBL_MCHT_SETTLE_INF TMSI1  "+
					" 				WHERE  "+
					" 					TMSI1.MCHT_NO = B.MCHT_NO  "+
					" 			)  FF1,"+
					"	(SELECT TMSI1.BANK_STATEMENT FROM TBL_MCHT_SETTLE_INF TMSI1 WHERE TMSI1.MCHT_NO=B.MCHT_NO ) FF2,"+
					"	(SELECT TMSI2.INTEGRAL FROM TBL_MCHT_SETTLE_INF TMSI2 WHERE TMSI2.MCHT_NO=B.MCHT_NO ) FF3,"+
					"	("+
					"		SELECT"+
					"			D .CREATE_NEW_NO"+
					"		FROM"+
					"			TBL_BRH_INFO D"+
					"		WHERE"+
					"			TRIM(D .BRH_ID) = TRIM(B.BANK_NO)"+
					"	) BANK_NO,"+
					"	("+
					"		SELECT"+
					"			D .BRH_NAME"+
					"		FROM"+
					"			TBL_BRH_INFO D"+
					"		WHERE"+
					"			TRIM(D .BRH_ID) = TRIM(B.BANK_NO)"+
					"	) BANK_NAME,"+
					"	SUBSTR(B.REC_UPD_TS, 0, 8) APPLYDATE"+
					" FROM"+
					"	TBL_MCHT_BASE_INF b "+
					"  WHERE  b.mcht_status='0'  and  "+
					"	b.MCHT_NO NOT IN ("+
					"		SELECT DISTINCT"+
					"			(A .MCHT_ID)"+
					"		FROM"+
					"			TBL_ROUTE_MCHTG_DETAIL A"+
					"	)) E "+whereSql;
			String countSql = " select count(*) from "+
					"(SELECT"+
							"	b.MCHT_NO,"+
							"	b.MCHT_NM,"+
							"	("+
							"		SELECT"+
							"			c.CITY_NAME"+
							"		FROM"+
							"			CST_CITY_CODE c"+
							"		WHERE"+
							"			C.MCHT_CITY_CODE = TRIM (b.AREA_NO)"+
							"	) AREA,"+
							"	b.MCC,"+
							"	'' || '' FF1,"+
							"	(SELECT TMSI1.BANK_STATEMENT FROM TBL_MCHT_SETTLE_INF TMSI1 WHERE TMSI1.MCHT_NO=B.MCHT_NO ) FF2,"+
							"	(SELECT TMSI2.INTEGRAL FROM TBL_MCHT_SETTLE_INF TMSI2 WHERE TMSI2.MCHT_NO=B.MCHT_NO ) FF3,"+
							"	b.BANK_NO,"+
							"	("+
							"		SELECT"+
							"			D .BRH_NAME"+
							"		FROM"+
							"			TBL_BRH_INFO D"+
							"		WHERE"+
							"			TRIM(D .BRH_ID) = TRIM(B.BANK_NO)"+
							"	) BANK_NAME,"+
							"	SUBSTR(B.REC_UPD_TS, 0, 8) APPLYDATE"+
							" FROM"+
							"	TBL_MCHT_BASE_INF b"+
							"  WHERE b.mcht_status='0'  and "+
							"	b.MCHT_NO NOT IN ("+
							"		SELECT DISTINCT"+
							"			(A .MCHT_ID)"+
							"		FROM"+
							"			TBL_ROUTE_MCHTG_DETAIL A"+
							"	)) E "+whereSql;

			@SuppressWarnings("unchecked")
			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

			ret[0] = dataList;
			ret[1] = count;
			return ret;}
	//根据商户id和渠道商户id查询
	public static Object[] getRouteUpbrhByMchtUpbrhId(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		
		String mchtUpbrhNo = request.getParameter("mchtUpbrhNo");
		String mchntId = request.getParameter("mchtId");
		String propertyId = request.getParameter("propertyId");
		
		String sql = " SELECT "
			+ " ( SELECT ru1. NAME FROM Tbl_Route_Upbrh ru1 WHERE ru1.brh_id = SUBSTR ('"+propertyId+"', 1, 4) AND ru1.brh_level = '1' AND ru1.status = '0' ) name1, "
			+ " ( SELECT ru2. NAME FROM Tbl_Route_Upbrh ru2 WHERE ru2.brh_id = SUBSTR ('"+propertyId+"', 1, 8) AND ru2.brh_level = '2' AND ru2.status = '0' ) name2, "
			+ " ( SELECT ru3. NAME FROM Tbl_Route_Upbrh ru3 WHERE ru3.brh_id = '"+propertyId +"' AND ru3.brh_level = '3' AND ru3.status = '0' ) name3, "
			+ " ( SELECT ru3.brh_id FROM Tbl_Route_Upbrh ru3 WHERE ru3.brh_id = '"+propertyId +"' AND ru3.brh_level = '3' AND ru3.status = '0') id3, "
			+ " rri.RULE_ID, "
			+ " 	'<font color=red>请点击更换渠道商户：  "+mchtUpbrhNo+" </font>'  text "
			+ " FROM "
			+ " 	TBL_ROUTE_RULE_MAP rri "
			+ " WHERE "
			+ " 	rri.MCHT_ID = '"+mchntId+"' "
			+ " AND rri.BRH_ID3 = '"+propertyId+"' "
			+ " AND rri.MCHT_ID_UP = '"+mchtUpbrhNo+"' ";
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = "1";

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}		
		


		//根据渠道、性质查性质信息
		public static Object[] busiAllInfo(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			
			String queryChannelId = request.getParameter("queryChannelId");
			String queryBusiName = request.getParameter("queryBusiName");
			StringBuffer whereSql = new StringBuffer("where 1=1 ");
			if (isNotEmpty(queryChannelId)) {
				whereSql.append(" AND tc.brh_id ='" + queryChannelId + "' ");
			}
			if (isNotEmpty(queryBusiName)) {
				whereSql.append(" AND tb.name like '%" + queryBusiName + "%' ");
			}
			//20160420 郭宇 添加 渠道id
//			String sql = "select tb.brh_id busiId,tc.name chlName,tb.name busiName from TBL_ROUTE_UPBRH tb inner join TBL_ROUTE_UPBRH tc on substr(tb.brh_id,1,4) = tc.brh_id and tb.brh_level='2' and tc.brh_level='1'"
			String sql = "select tb.brh_id busiId,tc.brh_id||'-'||tc.name chlName,tb.brh_id||'-'||tb.name busiName,tc.brh_id brh_id1 from TBL_ROUTE_UPBRH tb inner join TBL_ROUTE_UPBRH tc on substr(tb.brh_id,1,4) = tc.brh_id and tb.brh_level='2' and tc.brh_level='1'"
					   +  whereSql.toString()
					   +  "order by tb.upt_time desc ,tb.brh_id desc ";
			String countSql = "select count(*) from TBL_ROUTE_UPBRH tb inner join TBL_ROUTE_UPBRH tc on substr(tb.brh_id,1,4) = tc.brh_id and tb.brh_level='2' and tc.brh_level='1'" + whereSql.toString();

			@SuppressWarnings("unchecked")
			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
		//根据渠道、性质查性质信息
		public static Object[] characterInfo(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			
			String busiId = request.getParameter("busiId");
			String sql = "select t.brh_id charId, t.brh_id||'-'||t.name charName, t.brh_dsp charDesc, t.upt_time uptTime, t.upt_opr uptOper, t.status charStatus, decode(nvl((select count(*) from Tbl_Route_Rule_Info t1 where t.brh_id = t1.brh_id3 and t1.status = '0'), 0), 0, 'T', 'F') onFlag from TBL_ROUTE_UPBRH t where t.brh_level = '3' and substr(t.brh_id,1,8) = '" + busiId + "'"
					   +  "order by t.upt_time desc ,t.brh_id desc ";
			String countSql = "select count(*) from TBL_ROUTE_UPBRH t where t.brh_level = '3' and substr(t.brh_id,1,8) = '" + busiId + "'";

			@SuppressWarnings("unchecked")
			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}


		/**
		 * 将商户和所有性质匹配
		 * @param begin
		 * @param request
		 * @return
		 */
		public static Object[] getMchtAndAllProerty(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			String mchtNo = request.getParameter("mchtNo");
			String sql = " select mb.mcht_no, mb.mcht_nm, "
				+ "       ru3.brh_id propId, "
				+ "       ru3.name propName , "
				+ "       (select ru2.name from Tbl_Route_Upbrh ru2 where ru2.brh_id = SUBSTR(ru3.brh_id, 1, 8) and ru2.brh_level = '2' and ru2.status = '0' ) busiName, "
				+ "       (select ru1.name from Tbl_Route_Upbrh ru1 where ru1.brh_id = SUBSTR(ru3.brh_id, 1, 4) and ru1.brh_level = '1' and ru1.status = '0' ) chlName,  "
				+ "       '请点击选择渠道商户' text "
				+ "  from tbl_mcht_base_inf mb "
				+ "	  left join Tbl_Route_Upbrh ru3 on ru3.brh_level = '3' and ru3.status = '0' "
				+ " where (mb.mcht_no,ru3.brh_id) not in (select rrm.mcht_id,rrm.brh_id3 from tbl_route_rule_map rrm )"
				+ "	 and mb.mcht_no = '" + mchtNo + "' ";
			
			String countSql = " select count(*) "
				+ "  from tbl_mcht_base_inf mb "
				+ "	  left join Tbl_Route_Upbrh ru3 on ru3.brh_level = '3' and ru3.status = '0' "
				+ " where (mb.mcht_no,ru3.brh_id) not in (select rrm.mcht_id,rrm.brh_id3 from tbl_route_rule_map rrm )"
				+ "	 and mb.mcht_no = '" + mchtNo + "' ";		

			@SuppressWarnings("unchecked")
			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
			
			//商户组查询
			public static Object[] getRouteMchtg(int begin, HttpServletRequest request) {
				Object[] ret = new Object[2];
				StringBuffer whereSql = new StringBuffer(" where 1=1 ");
				
				String mchtNo = request.getParameter("mchtNo");
				String mchtNm = request.getParameter("mchtNm");
				String mchtGroupNm = request.getParameter("mchtGroupNm");

				if (isNotEmpty(mchtNo)&&isNotEmpty(mchtNm)) {
					whereSql.append("and c.MCHT_GID IN (SELECT b.MCHT_GID FROM TBL_ROUTE_MCHTG_DETAIL b WHERE b.MCHT_ID IN(SELECT a.MCHT_NO from TBL_MCHT_BASE_INF a WHERE a.MCHT_NO = '"+mchtNo+"' AND a.MCHT_NM LIKE '%"+mchtNm+"%')) ");
				}
				if (isNotEmpty(mchtNo)&&!isNotEmpty(mchtNm)) {
					whereSql.append("and c.MCHT_GID IN (SELECT b.MCHT_GID FROM TBL_ROUTE_MCHTG_DETAIL b WHERE b.MCHT_ID IN(SELECT a.MCHT_NO from TBL_MCHT_BASE_INF a WHERE a.MCHT_NO = '"+mchtNo+"' )) ");
				}
				if (!isNotEmpty(mchtNo)&&isNotEmpty(mchtNm)) {
					whereSql.append("and c.MCHT_GID IN (SELECT b.MCHT_GID FROM TBL_ROUTE_MCHTG_DETAIL b WHERE b.MCHT_ID IN(SELECT a.MCHT_NO from TBL_MCHT_BASE_INF a WHERE a.MCHT_NM LIKE '%"+mchtNm+"%' )) ");
				}
				if (isNotEmpty(mchtGroupNm)) {
					whereSql.append(" and c.MCHT_GNAME LIKE '%"+mchtGroupNm+"%'");
				}
				
				String sql = "SELECT c.MCHT_GID,c.MCHT_GNAME,c.MCHT_GDSP from TBL_ROUTE_MCHTG c "+whereSql;// and c.MCHT_GID IN (SELECT b.MCHT_GID FROM TBL_ROUTE_MCHTG_DETAIL b WHERE b.MCHT_ID IN(SELECT a.MCHT_NO from TBL_MCHT_BASE_INF a WHERE a.MCHT_NO = '' OR a.MCHT_NM LIKE '%%')) ";
				String countSql = " select count(*) from TBL_ROUTE_MCHTG c "+ whereSql;

				@SuppressWarnings("unchecked")
				List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
				String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

				ret[0] = dataList;
				ret[1] = count;
				return ret;
			}
			//查询所有性质
			public static Object[] getAllUpbrh(int begin, HttpServletRequest request) {
				Object[] ret = new Object[2];
				

				
				String sql = "   SELECT   distinct "+
						"   	ru1. NAME name1,   "+
						"   	ru2. NAME name2,   "+
						"   	ru3. NAME name3,   "+
						"   	ru3.brh_id id3,   "+
						"   	'请点击选择渠道商户 ' text   "+
						"   FROM   "+
						"   	Tbl_Route_Upbrh rri   "+
						"   LEFT JOIN Tbl_Route_Upbrh ru1 ON ru1.brh_id = SUBSTR (rri.brh_id, 1, 4)   "+
						"   AND ru1.brh_level = '1'   "+
						"   AND ru1.status = '0'   "+
						"   LEFT JOIN Tbl_Route_Upbrh ru2 ON ru2.brh_id = SUBSTR (rri.brh_id, 1, 8)   "+
						"   AND ru2.brh_level = '2'   "+
						"   AND ru2.status = '0'   "+
						"   LEFT JOIN Tbl_Route_Upbrh ru3 ON ru3.brh_id = rri.brh_id   "+
						"   AND ru3.brh_level = '3'   "+
						"   AND ru3.status = '0'   "+
						"   WHERE     LENGTH (rri.brh_id) = '12'  " ;
				String countSql = " select count(*) from  Tbl_Route_Upbrh rri   "+
						"   LEFT JOIN Tbl_Route_Upbrh ru1 ON ru1.brh_id = SUBSTR (rri.brh_id, 1, 4)   "+
						"   AND ru1.brh_level = '1'   "+
						"   AND ru1.status = '0'   "+
						"   LEFT JOIN Tbl_Route_Upbrh ru2 ON ru2.brh_id = SUBSTR (rri.brh_id, 1, 8)   "+
						"   AND ru2.brh_level = '2'   "+
						"   AND ru2.status = '0'   "+
						"   LEFT JOIN Tbl_Route_Upbrh ru3 ON ru3.brh_id = rri.brh_id   "+
						"   AND ru3.brh_level = '3'   "+
						"   AND ru3.status = '0'   "+
						"   WHERE     LENGTH (rri.brh_id) = '12'  " ;

				@SuppressWarnings("unchecked")
				List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
				String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

				ret[0] = dataList;
				ret[1] = count;
				return ret;
			}	
		//根据商户匹配业务性质
		public static Object[] getRouteUpbrhAndMcht(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			
			String mchtGid = request.getParameter("mchtGid");
			String mchtIds = request.getParameter("mchtIds");
			if(mchtIds==null){
				mchtIds="";
			}
			String[] split = mchtIds.split(",");
			String ids="";
			for (int i = 0; i < split.length; i++) {
				if(split.length==1||split.length-1==i){
					ids+="'"+split[i]+"'";
				}else{
					ids+="'"+split[i]+"' ,";
				}
			}
			String sql = "   SELECT  "+
					"   	DISTINCT *  "+
					"   FROM  "+
					"   	(  "+
					"   		SELECT  "+
					"   			T .MCHT_NO,  "+
					"   			T .MCHT_NM  "+
					"   		FROM  "+
					"   			TBL_MCHT_BASE_INF T  "+
					"   		WHERE  "+
					"   			T .MCHT_NO IN (  "+ids+
					"   			)  "+
					"   	) n,  "+
					"   	(  "+
					"   		SELECT  "+
					"   			ru1. NAME name1,  "+
					"   			ru2. NAME name2,  "+
					"   			ru3. NAME name3,  "+
					"   			ru3.brh_id id3,  "+
					"   			'请点击选择渠道商户 ' text  "+
					"   		FROM  "+
					"   			Tbl_Route_Rule_Info rri  "+
					"   		LEFT JOIN Tbl_Route_Upbrh ru1 ON ru1.brh_id = SUBSTR (rri.brh_id3, 1, 4)  "+
					"   		AND ru1.brh_level = '1'  "+
					"   		AND ru1.status = '0'  "+
					"   		LEFT JOIN Tbl_Route_Upbrh ru2 ON ru2.brh_id = SUBSTR (rri.brh_id3, 1, 8)  "+
					"   		AND ru2.brh_level = '2'  "+
					"   		AND ru2.status = '0'  "+
					"   		LEFT JOIN Tbl_Route_Upbrh ru3 ON ru3.brh_id = rri.brh_id3  "+
					"   		AND ru3.brh_level = '3'  "+
					"   		AND ru3.status = '0'  "+
					"   		WHERE  "+
					"   			mcht_gid = '"+mchtGid+"'  and rri.STATUS='0' "+
					"   	) M  ";
			String countSql = "  SELECT "+
					"  	count(*) "+
					"  FROM "+
					"   	(  "+
					"   		SELECT  "+
					"   			T .MCHT_NO,  "+
					"   			T .MCHT_NM  "+
					"   		FROM  "+
					"   			TBL_MCHT_BASE_INF T  "+
					"   		WHERE  "+
					"   			T .MCHT_NO IN (  "+ids+
					"   			)  "+
					"   	) n,  "+
					"   	(  "+
					"   		SELECT  "+
					"   			ru1. NAME name1,  "+
					"   			ru2. NAME name2,  "+
					"   			ru3. NAME name3,  "+
					"   			ru3.brh_id id3,  "+
					"   			'请点击选择渠道商户 ' text  "+
					"   		FROM  "+
					"   			Tbl_Route_Rule_Info rri  "+
					"   		LEFT JOIN Tbl_Route_Upbrh ru1 ON ru1.brh_id = SUBSTR (rri.brh_id3, 1, 4)  "+
					"   		AND ru1.brh_level = '1'  "+
					"   		AND ru1.status = '0'  "+
					"   		LEFT JOIN Tbl_Route_Upbrh ru2 ON ru2.brh_id = SUBSTR (rri.brh_id3, 1, 8)  "+
					"   		AND ru2.brh_level = '2'  "+
					"   		AND ru2.status = '0'  "+
					"   		LEFT JOIN Tbl_Route_Upbrh ru3 ON ru3.brh_id = rri.brh_id3  "+
					"   		AND ru3.brh_level = '3'  "+
					"   		AND ru3.status = '0'  "+
					"   		WHERE  "+
					"   			mcht_gid = '"+mchtGid+"'  and rri.STATUS='0' "+
					"   	) M  ";

			@SuppressWarnings("unchecked")
			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
	/**
	 * 获取指定商户组下没有为指定性质配置映射的商户
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getNoRouteMapMchtInGroup(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		
		String groupId = request.getParameter("groupId");
		String propertyId = request.getParameter("propertyId");

		String sql = "select mg.mcht_id,mb.mcht_nm,"
				+ "       (select ru1.name from Tbl_Route_Upbrh ru1 where ru1.brh_level='1' and ru1.status='0' and ru1.brh_id = substr('&propertyId',1,4)) chlName, "
				+ "       (select ru2.name from Tbl_Route_Upbrh ru2 where ru2.brh_level='2' and ru2.status='0' and ru2.brh_id = substr('&propertyId',1,8)) busiName, "
				+ "       (select ru3.name from Tbl_Route_Upbrh ru3 where ru3.brh_level='3' and ru3.status='0' and ru3.brh_id = '&propertyId') propName, "
				+ "       '&propertyId' propId, "
				+ "       '请点击选择渠道商户' text "
				+ "  from Tbl_Route_Mchtg_detail mg "
				+ " inner join tbl_mcht_base_inf mb "
				+ "    on mg.mcht_id = mb.mcht_no "
				+ " where mg.mcht_gid = '&groupId' "
				+ "   and mg.mcht_id not in "
				+ "      (select rm.mcht_id from Tbl_Route_Rule_Map_Hist rm where rm.mchtg_id = mg.mcht_gid and rm.brh_id3 = '&propertyId' and rm.status = '0') ";
		sql = sql.replaceAll("&propertyId", propertyId);	
		sql = sql.replaceAll("&groupId", groupId);	
		
		String countSql = "select count(*) "
				+ "  from Tbl_Route_Mchtg_detail mg "
				+ " inner join tbl_mcht_base_inf mb "
				+ "    on mg.mcht_id = mb.mcht_no "
				+ " where mg.mcht_gid = '&groupId' "
				+ "   and mg.mcht_id not in "
				+ "      (select rm.mcht_id from Tbl_Route_Rule_Map_Hist rm where rm.mchtg_id = mg.mcht_gid and rm.brh_id3 = '&propertyId' and rm.status = '0') ";
		countSql = countSql.replaceAll("&propertyId", propertyId);	
		countSql = countSql.replaceAll("&groupId", groupId);

		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin,Integer.MAX_VALUE); //取所有记录，不分页
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
		//路由商户详细信息查询
		public static Object[] getMappingHis(int begin, HttpServletRequest request) {
				Object[] ret = new Object[2];
				StringBuffer whereSql = new StringBuffer(" where 1=1 ");
				//mchtNo,mchtName,mchtArea,mchtMCC,chlName,busiName,charName,mchtIdUp,mchtNameUp,mapStatus,enDate,disDate
				String mchtId = request.getParameter("mchtId");
				String mchtAreaNo = request.getParameter("mchtAreaNo");
				String mchtMCC = request.getParameter("mchtMCC");
				String mapStatus = request.getParameter("mapStatus");
				String mchtUpId = request.getParameter("mchtUpId");
				String chlId = request.getParameter("chlId");
				String busiId = request.getParameter("busiId");
				String charName = request.getParameter("charName");
				String charCon = request.getParameter("charCon");

				if (isNotEmpty(mchtId)) {
					whereSql.append(" AND his.mcht_id ='" + mchtId + "' ");
				}
				if (isNotEmpty(mchtAreaNo)) {
					whereSql.append(" AND mcht.area_no ='" + mchtAreaNo + "' ");
				}
				if (isNotEmpty(mchtMCC)) {
					whereSql.append("AND mcht.mcc = '" + mchtMCC + "' ");
				}
				if (isNotEmpty(mapStatus)) {
					whereSql.append(" AND his.status ='" + mapStatus + "' ");
				}
				if (isNotEmpty(mchtUpId)) {
					whereSql.append(" and his.mcht_id_up = '"  + mchtUpId + "' ");
				}
				if (isNotEmpty(chlId)) {
					whereSql.append(" AND tchl.brh_id ='" + chlId + "' ");
				}
				if (isNotEmpty(busiId)) {
					whereSql.append("AND tbusi.brh_id = '" + busiId + "' ");
				}
				if (isNotEmpty(charName)) {
					String strNot = "";
					String strCon = " or ";
					if(isNotEmpty(charCon) && charCon.equals("1")){
						strNot = " not ";
						strCon = " and ";
					}
					whereSql.append("AND (tchar.brh_id " + strNot + "LIKE '%" + charName + "%' "+ strCon +" tchar.name " + strNot + " like '%" + charName + "%')");
				}
				
				String sql = "select mcht.mcht_no mchtNo,mcht.mcht_nm mchtName, city.city_name mchtArea, mcht.mcc mchtMCC,tchl.name chlName,tbusi.name busiName,tchar.name charName, upbrh.mcht_id_up mchtIdUp,upbrh.mcht_name_up mchtNameUp,his.status mapStatus,his.run_time enDate,decode(his.status,'1',his.stop_time,'') disDate,his.misc1 upBrhTermId"
					+	" from Tbl_Route_Rule_Map_Hist his"
					+	" left join TBL_MCHT_BASE_INF mcht on his.mcht_id = mcht.mcht_no"
					+	" left join Tbl_Upbrh_Mcht upbrh on his.mcht_id_up = upbrh.mcht_id_up and upbrh.term_id_up = trim(his.misc1) and upbrh.brh_id3 = his.brh_id3"
					+	" left join Tbl_Route_Upbrh tchar on his.brh_id3 = tchar.brh_id and tchar.brh_level = '3'"
					+	" left join Tbl_Route_Upbrh tbusi on substr(his.brh_id3,1,8) = tbusi.brh_id and tbusi.brh_level = '2'"
					+	" left join Tbl_Route_Upbrh tchl on substr(his.brh_id3,1,4) = tchl.brh_id and tchl.brh_level = '1'"
					+	" left join CST_CITY_CODE city on trim(mcht.area_no) = city.mcht_city_code"
					+	whereSql.toString()
					+	 " order by his.run_time desc ,his.rule_id desc ";
				
				String countSql = "select count(*) "
						+	" from Tbl_Route_Rule_Map_Hist his"
						+	" left join TBL_MCHT_BASE_INF mcht on his.mcht_id = mcht.mcht_no"
						+	" left join Tbl_Upbrh_Mcht upbrh on his.mcht_id_up = upbrh.mcht_id_up and upbrh.term_id_up = trim(his.misc1) and upbrh.brh_id3 = his.brh_id3 "
						+	" left join Tbl_Route_Upbrh tchar on his.brh_id3 = tchar.brh_id and tchar.brh_level = '3'"
						+	" left join Tbl_Route_Upbrh tbusi on substr(his.brh_id3,1,8) = tbusi.brh_id and tbusi.brh_level = '2'"
						+	" left join Tbl_Route_Upbrh tchl on substr(his.brh_id3,1,4) = tchl.brh_id and tbusi.brh_level = '1'"
						+	" left join CST_CITY_CODE city on trim(mcht.area_no) = city.mcht_city_code"
						+	whereSql.toString();
				
				@SuppressWarnings("unchecked")
				List<Object[]> dataList = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
				String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);

				ret[0] = dataList;
				ret[1] = count;
				return ret;
		}	
		
		/**
		 * 银行代码查询
		 * 
		 * @param begin
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static Object[] getSubbranchInfo(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			String subbranchId = request.getParameter("querySubbranchId");

			StringBuffer whereSql = new StringBuffer();
			whereSql.append(" WHERE " + "trim(subbranch_id) = '" + subbranchId.trim() + "'");

			String sql = "SELECT SERIAL_NO, BANK_NAME, PROVINCE, CITY FROM TBL_SUBBRANCH_INFO"
					+ whereSql.toString();

			String countSql = "SELECT COUNT(*) FROM TBL_SUBBRANCH_INFO "
					+ whereSql.toString();

			List<Object[]> dataList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
					countSql);
			
			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
		/**
		 * 银行代码查询
		 * 
		 * @param begin
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static Object[] getMchtCompliance(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			StringBuffer whereSql = new StringBuffer(" where 1=1 ");
			String mchtId = request.getParameter("mchtId");
			String countryId = request.getParameter("countryId");
			String mchtStatus = request.getParameter("mchtStatus");
			String queryIdmcc = request.getParameter("queryIdmcc");
			String queryMchtAreaId = request.getParameter("queryMchtAreaId");
			String minNum = request.getParameter("minNum");
			String maxNum = request.getParameter("maxNum");
			int min=0;
			int max=0;
			if(isNotEmpty(minNum)){
				min=Integer.parseInt(minNum);
				whereSql.append(" AND " + "M.QNUM >= '" +min + "'");
			}
			if(isNotEmpty(maxNum)){
				max=Integer.parseInt(maxNum);
				whereSql.append(" AND " + "M.QNUM <= '" +max + "'");
			}
			if(isNotEmpty(mchtId)){
				whereSql.append(" AND " + "M.MCHT_NO = '" +mchtId + "'");
			}
			if(isNotEmpty(countryId)){
				whereSql.append(" AND " + "M.COUNTRY = '" +countryId + "'");
			}
			if(isNotEmpty(mchtStatus)){
				whereSql.append(" AND " + "M.MCHT_STATUS = '" +mchtStatus + "'");
			}
			if(isNotEmpty(queryIdmcc)){
				whereSql.append(" AND " + "M.MCC = '" +queryIdmcc + "'");
			}
			if(isNotEmpty(queryMchtAreaId)){
				whereSql.append(" AND " + "M.ADDR = (SELECT CITY_NAME FROM CST_CITY_CODE  WHERE TRIM(MCHT_CITY_CODE)=TRIM( '" +queryMchtAreaId + "' )) ");
			}
			
			

			String sql =    "  SELECT M.MCHT_NO,M.MCHT_NM,M.ADDR,M.MCC,M.COUNTRY,M.MCHT_STATUS,M.QNUM FROM (     "+
							"    SELECT A.MCHT_NO,  "+
							"     A.MCHT_NM,  "+
							"     A.MCC,  "+
							"     (SELECT D.CITY_NAME FROM CST_CITY_CODE D WHERE TRIM(D.MCHT_CITY_CODE)=TRIM(A.AREA_NO) )ADDR,  "+
							"     A.MCHT_STATUS,  "+
							"     B.COUNTRY ,  "+
							"     B.COMPLIANCE,  "+
							"     (SELECT COUNT(1) FROM TBL_UPBRH_MCHT C WHERE C.STATUS='0' AND C.MCHT_ID=B.MCHT_NO) QNUM  "+
							"   FROM TBL_MCHT_BASE_INF A  "+
							"   RIGHT JOIN TBL_MCHT_SETTLE_INF B  "+
							"   ON A.MCHT_NO =B.MCHT_NO  "+
							"   WHERE B.COMPLIANCE='0'   ) M"
							+ whereSql.toString();

			String countSql = 	"  SELECT COUNT(1) FROM (     "+
								"    SELECT A.MCHT_NO,  "+
								"     A.MCHT_NM,  "+
								"     A.MCC,  "+
								"     (SELECT D.CITY_NAME FROM CST_CITY_CODE D WHERE TRIM(D.MCHT_CITY_CODE)=TRIM(A.AREA_NO) )ADDR,  "+
								"     A.MCHT_STATUS,  "+
								"     B.COUNTRY ,  "+
								"     B.COMPLIANCE,  "+
								"     (SELECT COUNT(1) FROM TBL_UPBRH_MCHT C WHERE C.STATUS='0' AND C.MCHT_ID=B.MCHT_NO)QNUM  "+
								"   FROM TBL_MCHT_BASE_INF A  "+
								"   RIGHT JOIN TBL_MCHT_SETTLE_INF B  "+
								"   ON A.MCHT_NO =B.MCHT_NO  "+
								"   WHERE B.COMPLIANCE='0'   ) M"
								+ whereSql.toString();

			List<Object[]> dataList = CommonFunction.getcommGWQueryDAO()
					.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(
					countSql);
			
			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
		
		/**
		 * 渠道商户-交易查询
		 * 
		 * @param begin
		 * @param request
		 * @return 2015-11-20
		 */

		@SuppressWarnings("unchecked")
		public static Object[] txnTodayInfo4Brh(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
		
			String queryCardBIN = request.getParameter("queryCardBIN");
			String queryAmtTransLow = request.getParameter("queryAmtTransLow");
			String queryAmtTransUp = request.getParameter("queryAmtTransUp");
			String queryRouteId = request.getParameter("queryRouteId");
			String queryTransDate = request.getParameter("queryTransDate");
			String queryTransTime = request.getParameter("queryTransTime");

			String queryRetrivlRef = request.getParameter("queryRetrivlRef");
			String queryTxnName = request.getParameter("queryTxnName");
			String queryTransState = request.getParameter("queryTransState");
			String queryRespCode = request.getParameter("queryRespCode");
			String queryBankName = request.getParameter("queryBankName");

			String queryMchtTermId = request.getParameter("queryMchtTermId");
			String queryUpbrhTermId = request.getParameter("queryUpbrhTermId");
			String queryCardAccptId = request.getParameter("queryCardAccptId");
			String queryMchtUpBrhId = request.getParameter("queryMchtUpBrhId");

			String queryBrh1Id = request.getParameter("queryBrh1Id");
			String queryBrh2Id = request.getParameter("queryBrh2Id");
			String queryBrh3Id = request.getParameter("queryBrh3Id");
			String queryAreaCode = request.getParameter("queryAreaCode");
			
			StringBuffer whereSql = new StringBuffer(" where 1=1 "
							+ " and a.txn_num in ('1011','1091','1101','1111','1121','1141',"
							+ "'2011','2091','2101','2111','2121','3011','3091','3101',"
							+ "'3111','3121','4011','4091','4101','4111','4121','5151','5161') ");
			if (isNotEmpty(queryCardBIN)) {
				whereSql.append(" AND instr(trim(a.pan),").append(queryCardBIN).append(",1)=1");
			}
			if (isNotEmpty(queryAmtTransLow)) {
				whereSql.append(" AND TO_NUMBER(NVL(trim(a.amt_trans),0))/100 ").append(" >= ").append(queryAmtTransLow);
			}
			if (isNotEmpty(queryAmtTransUp)) {
				whereSql.append(" AND TO_NUMBER(NVL(trim(a.amt_trans),0))/100 ").append(" <= ").append(queryAmtTransUp);
			}
			if (isNotEmpty(queryRouteId)) {
				whereSql.append(" AND a.rule_id").append(" like '%").append(queryRouteId).append("%'");
			}
			if (isNotEmpty(queryTransDate)) {
				whereSql.append(" AND a.txn_date").append(" = '").append(queryTransDate).append("'");
			}
			if (isNotEmpty(queryTransTime)) {
				whereSql.append(" AND a.txn_time").append(" = '").append(queryTransTime).append("'");
			}
			
			
			if (isNotEmpty(queryRetrivlRef)) {
				whereSql.append(" AND a.retrivl_ref").append(" like ").append("'%").append(queryRetrivlRef).append("%'");
			}
			if (isNotEmpty(queryTxnName)) {
				whereSql.append(" AND a.txn_num").append(" = '").append(queryTxnName).append("'");
			}
			if (isNotEmpty(queryTransState)) {
				if (Constants.TransState.equals(queryTransState)) {
					whereSql.append(" AND a.trans_state  !='1'  and a.trans_state  !='0'  and a.trans_state  !='R' ");
				} else {
					whereSql.append(" AND a.trans_state").append(" = '").append(queryTransState).append("'");
				}
			}
			if (isNotEmpty(queryRespCode)) {
				whereSql.append(" AND a.RESP_CODE").append(" = '").append(queryRespCode).append("' ");
			}
			if (isNotEmpty(queryBankName)) {
				whereSql.append(" AND a.bank_no").append(" = '").append(queryBankName).append("' ");
			}
			
			if (isNotEmpty(queryMchtTermId)) {
				whereSql.append(" AND a.mcht_term_id").append(" like ").append("'%").append(queryMchtTermId).append("%'");
			}
			if (isNotEmpty(queryUpbrhTermId)) {
				whereSql.append(" AND a.up_brh_term_Id").append(" like ").append("'%").append(queryUpbrhTermId).append("%'");
			}
			if (isNotEmpty(queryCardAccptId)) {
				whereSql.append(" AND a.card_accp_id").append(" = '").append(queryCardAccptId).append("'");
			}
			if (isNotEmpty(queryMchtUpBrhId)) {
				whereSql.append(" AND a.up_brh_Id").append(" = '").append(queryMchtUpBrhId).append("'");
			}
			
			
			if (isNotEmpty(queryBrh1Id)) {
				whereSql.append(" AND a.brh_id_1").append(" = '").append(queryBrh1Id).append("'");
			}
			if (isNotEmpty(queryBrh2Id)) {
				whereSql.append(" AND a.brh_id_2").append(" = '").append(queryBrh2Id).append("'");
			}
			if (isNotEmpty(queryBrh3Id)) {
				whereSql.append(" AND a.brh_id_3").append(" = '").append(queryBrh3Id).append("'");
			}
			if (isNotEmpty(queryAreaCode)) {
				whereSql.append(" AND a.up_brh_area_code").append(" = '").append(queryAreaCode).append("'");
			}
			if (isNotEmpty(request.getParameter("misc1"))) {
				//特殊计费查询 -- 特殊计费是misc_1字段的第一个字节
				whereSql.append(" AND trim(substr(a.misc_1,1,length(a.misc_1))) = '"+ request.getParameter("misc1").trim() + "' ");
			}


			
		    String subSql = "select  "
				+ "    	inst_date as inst_datetime, "
				+ "    	substr(f.inst_date,1,8) as txn_date, "
				+ "    	substr(f.inst_date,9,6) as txn_time, "
				+ "    	f.pan, "
				+ "    	f.card_accp_id, "
				+ "    	mb.mcht_no || '-'|| mb.mcht_nm as mcht_name, "
				+ "    	f.card_accp_term_id as mcht_term_id, "
				+ "    	f.retrivl_ref, "
				+ "    	to_char(case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ,'99,999,999,990.99') as amt,f.amt_trans,  "
				+ "    	f.txn_num, tn.txn_name, "
				+ "    	f.trans_state, "
				+ "    	f.resp_code,f.resp_code|| '-'||trim(rm.rsp_code_dsp) as resp_name, "
				+ "    	substr(f.misc_2,104,2) card_type,  "
				+ "    	(case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '0' else '1' end) card_org,  "
				+ "    	(case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no, "
				+ "    	(case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then  "
				+ "    	       (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) )  "
				+ "    	          else  '*'  "
				+ "    	          end) bank_name,  "
				+ "    	ub.mcht_id_up as up_brh_id, ub.mcht_id_up || '-'||ub.mcht_name_up as mcht_brh_name, "
				+ "    	ub.term_id_up as up_brh_term_id, "
				+ "    	ru3.brh_id as brh_id_3,ru3.name as brh_name_3, "
				+ "    	ru2.brh_id as brh_id_2, ru2.name as brh_name_2, "
				+ "    	ru1.brh_id as brh_id_1, ru1.name as brh_name_1, "
				+ "    	rrr.rule_id, "
				+ "    	ub.area as up_brh_area_code,ub.misc_1, "
				+ "	    f.tdestid||'-'||fb.FIRST_BRH_NAME as settle_brh_name,"
				+ "	    f.revsal_ssn,f.revsal_flag,f.cancel_ssn,f.cancel_flag"
				+ "    	   from tbl_n_txn f  "
				+ "			  left join TBL_FIRST_BRH_DEST_ID fb"
				+ "                on fb.DEST_ID = f.tdestid "
				+ "    	      left join TBL_MCHT_BASE_INF mb  "
				+ "    	           on f.card_accp_id = mb.mcht_no "
				+ "    	      left join TBL_TXN_NAME tn " 
				+ "    	           on f.txn_num = tn.txn_num "
				+ "    	      left join tbl_rsp_code_map rm "
				+ "    	           on f.resp_code = trim(rm.dest_rsp_code) and rm.SRC_ID='2801'"
				+ "    	      left join gtwyadm.tbl_upbrh_mcht ub  "
				+ "    	           on f.up_mcht_id = ub.mcht_id_up and f.up_term_id = ub.term_id_up and f.up_brh_id3 = ub.brh_id3 "
				+ "    	      left join gtwyadm.Tbl_Route_Upbrh ru3 "
				+ "    	           on ru3.brh_id = f.up_brh_id3 and ru3.brh_level = '3' "
				+ "    	      left join gtwyadm.Tbl_Route_Upbrh ru2 "
				+ "    	           on ru2.brh_id = substr(f.up_brh_id3,1,8) and ru2.brh_level = '2'  "
				+ "    	      left join gtwyadm.Tbl_Route_Upbrh ru1 "
				+ "    	           on ru1.brh_id = substr(f.up_brh_id3,1,4) and ru1.brh_level = '1'  "
				+ "    	      left join gtwyadm.tbl_route_mchtg_detail rmd "
				+ "    	           on f.card_accp_id = rmd.mcht_id      "
				+ "    	      left join gtwyadm.Tbl_Route_Rule_Info rrr "
				+ "    	           on rrr.mcht_gid = rmd.mcht_gid and rrr.brh_id3 = f.up_brh_id3   "
				+ "    	where rrr.orders is null or rrr.orders is not null and rrr.orders = (select min(rr.orders) minorder from gtwyadm.Tbl_Route_Rule_Info  rr "
				+ "    	           where f.up_brh_id3 = rr.brh_id3 and rr.mcht_gid = rmd.mcht_gid "
				+ "    	           group by rr.mcht_gid,rr.brh_id3) ";
				
		    
			String sql = " select pan,bank_name,card_type,trans_state,amt,inst_datetime,txn_name,mcht_name,mcht_term_id,mcht_brh_name,up_brh_term_id,brh_name_1,brh_name_2,brh_name_3,rule_id,retrivl_ref,resp_name,settle_brh_name,revsal_ssn,revsal_flag,cancel_ssn,cancel_flag,misc_1 "
					+ "   from (  "
					+ subSql
					+ "    	    ) a "
					+  whereSql.toString()
			        + " order by a.inst_datetime desc";
			
			String countSql = "SELECT count(*) "
					+ "   from (  "
					+ subSql
					+ "    	    ) a "
					+  whereSql.toString();
			
			String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);

			List<Object[]> dataList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);


			if (dataList.size() > 0) {
				String amtSql = "select "
						+ " to_char("
						+ "nvl(sum( case when a.txn_num ='5151' then -TO_NUMBER(NVL(trim(a.amt_trans),0))/100 "
						+ "else TO_NUMBER(NVL(trim(a.amt_trans),0))/100 end),0) "
						+ ",'99,999,999,990.99') as amt_total "
						+ "   from (  "
						+ subSql
						+ "    	    ) a "
						+  whereSql.toString()
						+ " and a.txn_num in ('1101','1091','5151') and a.trans_state in ( '1','R') and a.REVSAL_FLAG!='1' and a.CANCEL_FLAG!='1' ";
				String amt = CommonFunction.getCommQueryDAO().findCountBySQLQuery(amtSql);

				Object[] obj = new Object[dataList.get(0).length];
				obj[0] = "<font color='red'>总计</font>";
				obj[1] = "<font color='red'>交易总笔数:</font>";
				obj[2] = "<font color='red'>" + count + "笔</font>";
				obj[3] = "";
				obj[4] = "";
				obj[5] = "<font color='red'>交易总金额:</font>";
				obj[6] = "<font color='red'>" + amt + "</font>";

				dataList.add(obj);
			}

			ret[0] = dataList;
			ret[1] = count;
			return ret;
	}
		/**
		 * 商户手工差错调整查询
		 * 
		 * @param begin
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static Object[] getMchtErrAdjustAll(int begin, HttpServletRequest request) {
			Object[] ret = new Object[2];
			
			StringBuffer whereSql = new StringBuffer(" where 1=1 ");
			String mchtNo = request.getParameter("queryMchtNo");
			String cardNo = request.getParameter("cardNo");
			String authNo = request.getParameter("authNo");
			String tradeConsultNo = request.getParameter("tradeConsultNo");
			String tradeType = request.getParameter("tradeType");
			String approveStatus = request.getParameter("approveStatus");
			String postStatus = request.getParameter("postStatus");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(isNotEmpty(mchtNo)){
				whereSql.append(" AND " + "MCHT_NO= '" +mchtNo + "'");
			}
			if(isNotEmpty(cardNo)){
				whereSql.append(" AND " + "CARD_NO LIKE '%" +cardNo + "%'");
			}
			if(isNotEmpty(authNo)){
				whereSql.append(" AND " + "auth_No LIKE '%" +authNo + "%'");
			}
			if(isNotEmpty(tradeConsultNo)){
				whereSql.append(" AND " + "trade_Consult_No LIKE '%" +tradeConsultNo + "%'");
			}
			if(isNotEmpty(tradeType)){
				whereSql.append(" AND " + "trade_Type = '" +tradeType + "'");
			}
			if(isNotEmpty(approveStatus)){
				whereSql.append(" AND " + "aprove_Status = '" +approveStatus + "'");
			}
			if(isNotEmpty(postStatus)){
				whereSql.append(" AND " + "post_Status =  '" +postStatus + "'  ");
			}
			if(isNotEmpty(startDate)){
				whereSql.append(" AND " + "approve_Time >=  '" +startDate + "000000' ");
			}
			if(isNotEmpty(endDate)){
				whereSql.append(" AND " + "approve_Time <=  '" +endDate + "235959'  ");
			}

			String sql =   "SELECT UUID,MCHT_NO,CARD_NO,auth_No,trade_Consult_No,trade_Type,TO_CHAR(money, '9999999999990.00') money ,aprove_Status,aprove_Opr,approve_Time,approve_Advice,post_Time,post_Status,reserver, VC_TRAN_NO FROM TBL_MCHT_ERR_ADJUST" 
							+ whereSql.toString()+" order by aprove_Status asc,crt_time desc";

			String countSql = "SELECT count(1) FROM TBL_MCHT_ERR_ADJUST"	
								+ whereSql.toString();

			List<Object[]> dataList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
			String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
					countSql);
			
			ret[0] = dataList;
			ret[1] = count;
			return ret;
		}
		

	/**
	 * 商户结算单历史查询（汇总）
	 * crate by yww 20151225
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchSettelHis(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		//Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String mchtNo = request.getParameter("mchtNo");
		String brhId = request.getParameter("brhId");
		String pan = request.getParameter("pan");
		String misc2T = request.getParameter("misc2T");
		//2016.02.25 郭宇 修改 检索条件放在sql最里层，否则统计数据不准确
//		String sql ="";
		StringBuffer sql = new StringBuffer("select a.DATE_SETTLMT , a.MCHT_CD, ");
		sql.append("SUM (CASE WHEN A.TXN_NUM = '1101' THEN A.TRANS_AMT WHEN A.TXN_NUM = '1091' THEN A.TRANS_AMT WHEN A.TXN_NUM = '3101' THEN '-' || A.TRANS_AMT WHEN A.TXN_NUM = '3091' THEN '-' || A.TRANS_AMT WHEN A.TXN_NUM = '5151' THEN '-' || A.TRANS_AMT ELSE A.TRANS_AMT END) AS sum_amt,");
		sql.append("sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD,sum(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot from TBL_ALGO_CONFIRM_HIS h, TBL_ALGO_DTL a ");
		//20160429 郭宇 修改 提现类交易不显示
		StringBuffer whereSql = new StringBuffer(" where A.TXN_NUM <> '1211' AND A.TXN_NUM <> '1221' AND a.DATE_SETTLMT in h.DATE_SETTLMT and h.STATUS='0' ");
		if (isNotEmpty(startDate)) {
//			whereSql.append(" AND s.DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
			whereSql.append(" AND a.DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
//			whereSql.append(" AND s.DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
			whereSql.append(" AND a.DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
		}
		if (isNotEmpty(mchtNo)) {
//			whereSql.append(" AND s.MCHT_CD ='"+mchtNo+"' ");
			whereSql.append(" AND a.MCHT_CD ='"+mchtNo+"' ");
		}
		if (isNotEmpty(brhId)) {
//			whereSql.append(" and s.BRH_INS_ID_CD ='"+ brhId.trim()+ "' ");
			whereSql.append(" and a.BRH_INS_ID_CD ='"+ brhId.trim()+ "' ");
		}
		if (isNotEmpty(pan)) {
//			whereSql.append(" AND SETTLE_ACCT like '%"+ pan.trim()+ "%' ");
			sql.append("LEFT JOIN TBL_MCHT_SETTLE_INF si ON a.MCHT_CD = si.MCHT_NO ");
			whereSql.append(" AND si.SETTLE_ACCT like '%"+ pan.trim()+ "%' ");
		}
//		if (isNotEmpty(misc2T)) {
//			sql = "select s.DATE_SETTLMT,s.MCHT_CD,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,s.BRH_INS_ID_CD,s.BRH_INS_ID_CD||'-'||c.BRH_NAME,trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,s.acqInsAllot,nvl(s.SETTL_AMT,0) from (select a.DATE_SETTLMT , a.MCHT_CD, sum(a.TRANS_AMT) as sum_amt , sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD,sum(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,sum((a.TRANS_AMT/100)-(a.MCHT_FEE_D-a.MCHT_FEE_C)) as SETTL_AMT from TBL_ALGO_CONFIRM_HIS h, TBL_ALGO_DTL a "
//					+ "left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref "
//					+ "where a.DATE_SETTLMT in h.DATE_SETTLMT and h.STATUS='0' and substr(b.MISC_2,117,1)='0' and substr(b.MISC_2,46,3)='00"+misc2T+"' "
//					+ " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
//		}else{
//			sql = "select s.DATE_SETTLMT,s.MCHT_CD,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,s.BRH_INS_ID_CD,s.BRH_INS_ID_CD||'-'||c.BRH_NAME,trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,s.acqInsAllot,nvl(s.SETTL_AMT,0) from (select a.DATE_SETTLMT , a.MCHT_CD, sum(a.TRANS_AMT) as sum_amt , sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD,sum(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,sum((a.TRANS_AMT/100)-(a.MCHT_FEE_D-a.MCHT_FEE_C)) as SETTL_AMT from TBL_ALGO_DTL a,TBL_ALGO_CONFIRM_HIS h where a.DATE_SETTLMT in h.DATE_SETTLMT and h.STATUS='0' "
//					+ " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
//		}
		if (isNotEmpty(misc2T)) {
			sql.append("left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref ");
			whereSql.append(" and substr(b.MISC_2,117,1)='0' and substr(b.MISC_2,46,3)='00"+misc2T+"' ");
		}
		
//		sql = sql + whereSql.toString() + " order by s.DATE_SETTLMT desc,s.MCHT_CD ";
		String sqlSum = "select s.DATE_SETTLMT,s.MCHT_CD,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,s.BRH_INS_ID_CD,c.CREATE_NEW_NO||'-'||c.BRH_NAME,trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,s.acqInsAllot,NVL((s.sum_amt / 100) - (s.MCHT_FEE_D - s.MCHT_FEE_C), 0) from (";
		sqlSum = sqlSum + sql.toString() + whereSql.toString() + " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
		sqlSum = sqlSum + " order by s.DATE_SETTLMT desc,s.MCHT_CD ";
		
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlSum, begin, Constants.QUERY_RECORD_COUNT);
		//String count = dataList.size()+"";//CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
//		String count =String.valueOf(CommonFunction.getCommQueryDAO().findBySQLQuery(sqlSum).size());
		 
		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[3] != null && !"".equals(objects[3])) {
					objects[3] = df.format(Double.parseDouble(objects[3].toString())/100);//交易金额是以分为单位，所以要除100
					objects[3] = CommonFunction.moneyFormat(objects[3].toString());
				}
				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = df.format(Double.parseDouble(objects[4].toString()));
					objects[4] = CommonFunction.moneyFormat(objects[4].toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8].toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8].toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					objects[9] = df.format(Double.parseDouble(objects[9].toString()));
					objects[9] = CommonFunction.moneyFormat(objects[9].toString());
				}
			}
		}
		//20160429 郭宇 修改中总记录条数的处理
		List<Object[]> amtList = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlSum);
		String count =String.valueOf(amtList.size());
		if (amtList.size() > 0) {
			Double amt3 = 0.00;
			Double amt4 = 0.00;
			Double amt8 = 0.00;
			Double amt9 = 0.00;
			Object[] obj = new Object[dataList.get(0).length];
			for (Object[] objects : amtList) {
				if (objects[3] != null && !"".equals(objects[3])) {
					amt3 += Double.parseDouble(objects[3].toString());
				}
				if (objects[4] != null && !"".equals(objects[4])) {
					amt4 += Double.parseDouble(objects[4].toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					amt8 += Double.parseDouble(objects[8].toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					amt9 += Double.parseDouble(objects[9].toString());
				}
			}
			obj[2] = "<font color='red'>总计</font>";
			obj[3] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt3/100)) + "</font>";
			obj[4] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt4)) + "</font>";
			obj[8] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt8)) + "</font>";
			obj[9] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt9)) + "</font>";
			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}


	/**
	 * 商户结算单历史详细信息查询（详细）
	 * crate by yww 20151225
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getmchSettelHisDtl(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String dateSettlmt = request.getParameter("dateSettlmt");//清算日期
		String mchtNo = request.getParameter("mchtNo");//商户号
		String brhInsIdCd = request.getParameter("brhInsIdCd");//合作伙伴号
		//20160429 郭宇 修改 提现类交易不显示
		StringBuffer whereSql = new StringBuffer(" where A.TXN_NUM <> '1211' AND A.TXN_NUM <> '1221' AND a.MCHT_CD in (select g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");

		if (isNotEmpty(dateSettlmt)) {
			whereSql.append(" AND a.DATE_SETTLMT ").append(" = ").append("'").append(dateSettlmt).append("'");
		}
		if (isNotEmpty(mchtNo)) {
			whereSql.append(" AND a.mcht_cd ").append(" = ").append("'").append(mchtNo).append("'");
		}
		if (isNotEmpty(brhInsIdCd)) {
			whereSql.append(" AND a.BRH_INS_ID_CD ").append(" = ").append("'").append(brhInsIdCd).append("'");
		}
		
		String sql = "SELECT a.trans_date,a.trans_date_time,a.txn_ssn,a.BRH_INS_ID_CD, b.retrivl_ref, c.CREATE_NEW_NO||'-'||c.BRH_NAME as brhName , "
				//负交易时*-1
//				+ "(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot,((TRANS_AMT/100)-(MCHT_FEE_D-MCHT_FEE_C)) as SETTL_AMT, "
				+ "(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot,"
				+ "CASE WHEN A.TXN_NUM = '3101' THEN (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "WHEN A.TXN_NUM = '3091' THEN  (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "WHEN A.TXN_NUM = '5151' THEN  (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "ELSE (TRANS_AMT/100)-(MCHT_FEE_D-MCHT_FEE_C) END as SETTL_AMT,"
				+" a.TRANS_AMT,(a.MCHT_FEE_D-a.MCHT_FEE_C) as sum_fee, "
				+ " substr(b.MISC_2,117,1)||substr(b.MISC_2,46,3)||substr(b.MISC_2,118,1) ,a.txn_Num "
				+ "from TBL_ALGO_DTL a join TBL_ALGO_CONFIRM_HIS h on a.DATE_SETTLMT in h.DATE_SETTLMT  and h.STATUS='0' "
				+ "left join TBL_BRH_INFO c on trim(a.BRH_INS_ID_CD)=c.BRH_ID "
				+ "left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref ";
		sql = sql + whereSql.toString()  + " order by trans_date desc,trans_date_time desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String countSql = "SELECT count(*) "
				+ " from TBL_ALGO_DTL a join TBL_ALGO_CONFIRM_HIS h on a.DATE_SETTLMT in h.DATE_SETTLMT  and h.STATUS='0' "
				+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		//String count =String.valueOf(CommonFunction.getCommQueryDAO().findBySQLQuery(sql).size());

		DecimalFormat df = new DecimalFormat("0.00");
		//3101消费撤销  3091预授权完成撤销 5151-POS退货 负交易
		String STRING_3010 = "3101";
		String STRING_3091 = "3091";
		String STRING_5151 = "5151";
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				/*if (objects[2] != null && !"".equals(objects[2])) {
					String str = objects[2].toString();
					objects[2] = str.substring(0, 6) + str.substring(6, 10)+ str.substring(10);
				}*/
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6].toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6].toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7].toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7].toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8].toString())/100);//交易金额是以分为单位，所以要除100
//					objects[8] = CommonFunction.moneyFormat(objects[8].toString());
					//正交易
					if (!STRING_3010.equals(objects[11]) && !STRING_3091.equals(objects[11]) && !STRING_5151.equals(objects[11])){
						objects[8] = CommonFunction.moneyFormat(objects[8].toString());
						//3101消费撤销  3091预授权完成撤销 5151-POS退货 负交易
					}else{
						objects[8] = "-" + CommonFunction.moneyFormat(objects[8].toString());
					}
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					objects[9] = df.format(Double.parseDouble(objects[9].toString()));
					objects[9] = CommonFunction.moneyFormat(objects[9].toString());
				}
			}
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 对平数据汇总查询
	 * crate by yww 20160111
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchSumTrans(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		String dateSettlmt = request.getParameter("dateSettlmt");
		String mchtNo = request.getParameter("mchtNo");
		String brhId = request.getParameter("brhId");
		String pan = request.getParameter("pan");
		String misc2T = request.getParameter("misc2T");
		//2016.02.25 郭宇 修改 检索条件放在sql最里层，否则统计数据不准确
//		String sql ="";
		StringBuffer sql = new StringBuffer("select a.DATE_SETTLMT , a.MCHT_CD, ");
		//按交易类型计算交易金额合计
		sql.append("SUM (CASE WHEN A.TXN_NUM = '1101' THEN A.TRANS_AMT WHEN A.TXN_NUM = '1091' THEN A.TRANS_AMT WHEN A.TXN_NUM = '3101' THEN '-' || A.TRANS_AMT WHEN A.TXN_NUM = '3091' THEN '-' || A.TRANS_AMT WHEN A.TXN_NUM = '5151' THEN '-' || A.TRANS_AMT ELSE A.TRANS_AMT END) AS sum_amt,");
		sql.append("sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD,sum(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot from TBL_ALGO_DTL a ");
		//20160429 郭宇 修改 提现类交易不显示
		StringBuffer whereSql = new StringBuffer(" where A.TXN_NUM <> '1211' AND A.TXN_NUM <> '1221' ");	
		if (isNotEmpty(dateSettlmt)) {
			dateSettlmt = dateSettlmt.replace("-", "").substring(0,8);
//			whereSql.append(" AND s.DATE_SETTLMT ").append(" = ").append("'").append(dateSettlmt).append("'");
			whereSql.append(" AND a.DATE_SETTLMT ").append(" = ").append("'").append(dateSettlmt).append("' ");
		}
		if (isNotEmpty(mchtNo)) {
//			whereSql.append(" AND s.MCHT_CD ").append(" = ").append("'").append(mchtNo).append("'");
			whereSql.append(" AND a.MCHT_CD ").append(" = ").append("'").append(mchtNo).append("' ");
		}
		if (isNotEmpty(brhId)) {
//			whereSql.append(" AND s.BRH_INS_ID_CD ='"+ brhId.trim()+ "' ");
			whereSql.append(" AND a.BRH_INS_ID_CD ='"+ brhId.trim()+ "' ");
		}
		if (isNotEmpty(pan)) {
//			whereSql.append(" AND SETTLE_ACCT like '%"+ pan.trim()+ "%' ");
			sql.append("LEFT JOIN TBL_MCHT_SETTLE_INF si ON a.MCHT_CD = si.MCHT_NO ");
			whereSql.append(" AND si.SETTLE_ACCT like '%"+ pan.trim()+ "%' ");
		}

//		if (isNotEmpty(misc2T)) {
//			sql = "select s.DATE_SETTLMT,s.MCHT_CD,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,s.BRH_INS_ID_CD,s.BRH_INS_ID_CD||'-'||c.BRH_NAME,trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,s.acqInsAllot,nvl(s.SETTL_AMT,0) from (select a.DATE_SETTLMT , a.MCHT_CD, sum(a.TRANS_AMT) as sum_amt , sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD,sum(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,sum((a.TRANS_AMT/100)-(a.MCHT_FEE_D-a.MCHT_FEE_C)) as SETTL_AMT from TBL_ALGO_DTL a "
//					+ "left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref "
//					+ "where substr(b.MISC_2,117,1)='0' and substr(b.MISC_2,46,3)='00"+misc2T+"' "
//					+ " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
//		}else{
//			sql = "select s.DATE_SETTLMT,s.MCHT_CD,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,s.BRH_INS_ID_CD,s.BRH_INS_ID_CD||'-'||c.BRH_NAME,trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,s.acqInsAllot,nvl(s.SETTL_AMT,0) from (select a.DATE_SETTLMT , a.MCHT_CD, sum(a.TRANS_AMT) as sum_amt , sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD,sum(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,sum((a.TRANS_AMT/100)-(a.MCHT_FEE_D-a.MCHT_FEE_C)) as SETTL_AMT from TBL_ALGO_DTL a "
//					+ " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
//		}
		if (isNotEmpty(misc2T)) {
			sql.append("left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref ");
			whereSql.append(" and substr(b.MISC_2,117,1)='0' and substr(b.MISC_2,46,3)='00"+misc2T+"' ");
		}
		
		String sqlSum = "select s.DATE_SETTLMT,s.MCHT_CD,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,s.BRH_INS_ID_CD,c.CREATE_NEW_NO||'-'||c.BRH_NAME,trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,s.acqInsAllot,NVL((s.sum_amt / 100) - (s.MCHT_FEE_D - s.MCHT_FEE_C), 0) from (";
		sqlSum = sqlSum + sql.toString() + whereSql.toString() + " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
		sqlSum = sqlSum + " order by s.DATE_SETTLMT desc,s.MCHT_CD ";
		
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlSum, begin, Constants.QUERY_RECORD_COUNT);
//		String count = dataList.size()+"";

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[3] != null && !"".equals(objects[3])) {
					objects[3] = df.format(Double.parseDouble(objects[3].toString())/100);//交易金额是以分为单位，所以要除100
					objects[3] = CommonFunction.moneyFormat(objects[3].toString());
				}
				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = df.format(Double.parseDouble(objects[4].toString()));
					objects[4] = CommonFunction.moneyFormat(objects[4].toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8].toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8].toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					objects[9] = df.format(Double.parseDouble(objects[9].toString()));
					objects[9] = CommonFunction.moneyFormat(objects[9].toString());
				}
			}
		}
		//20160426 郭宇 修改 数据总条数设置为所有数据的总条数
		List<Object[]> amtList = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlSum);
		String count = amtList.size()+"";
//		if (dataList.size() > 0) {
//			List<Object[]> amtList = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlSum);
		if (amtList.size() > 0) {
			Double amt3 = 0.00;
			Double amt4 = 0.00;
			Double amt8 = 0.00;
			Double amt9 = 0.00;
			Object[] obj = new Object[dataList.get(0).length];
			for (Object[] objects : amtList) {
				if (objects[3] != null && !"".equals(objects[3])) {
					amt3 += Double.parseDouble(objects[3].toString());
				}
				if (objects[4] != null && !"".equals(objects[4])) {
					amt4 += Double.parseDouble(objects[4].toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					amt8 += Double.parseDouble(objects[8].toString());
				}
				if (objects[9] != null && !"".equals(objects[9])) {
					amt9 += Double.parseDouble(objects[9].toString());
				}
			}
			obj[2] = "<font color='red'>总计</font>";
			obj[3] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt3/100)) + "</font>";//交易金额是以分为单位，所以要除100
			obj[4] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt4)) + "</font>";
			obj[8] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt8)) + "</font>";
			obj[9] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt9)) + "</font>";
			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 对平数据详细信息查询（详细）
	 * crate by yww 20151229
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchFlatTrans(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		//Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String dateSettlmt = request.getParameter("dateSettlmt");
		String mchtNo = request.getParameter("mchtNo");
		String brhId = request.getParameter("brhId");
		//20160429 郭宇 修改 提现类交易不显示
		StringBuffer whereSql = new StringBuffer(" where A.TXN_NUM <> '1211' AND A.TXN_NUM <> '1221' ");		
		if (isNotEmpty(dateSettlmt)) {
			dateSettlmt = dateSettlmt.replace("-", "").substring(0,8);
			whereSql.append(" AND a.DATE_SETTLMT ").append(" = ").append("'").append(dateSettlmt).append("'");
		}
		if (isNotEmpty(mchtNo)) {
			whereSql.append(" AND a.MCHT_CD ").append(" = ").append("'").append(mchtNo).append("'");
		}
		if (isNotEmpty(brhId)) {
			whereSql.append(" AND a.BRH_INS_ID_CD ='"+ brhId.trim()+ "' ");
		}
//		String sql = "SELECT a.DATE_SETTLMT,a.BRH_INS_ID_CD, a.BRH_INS_ID_CD||'-'||c.BRH_NAME, a.MCHT_CD, a.MCHT_CD||'-'||d.MCHT_NM ,a.TRANS_AMT ,(a.MCHT_FEE_D - a.MCHT_FEE_C) as MCHT_FEE, (a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,a.trans_date,a.trans_date_time,a.txn_ssn, b.retrivl_ref, e.DATE_SETTLMT as settleFlag ,((TRANS_AMT/100)-(MCHT_FEE_D-MCHT_FEE_C)) as SETTL_AMT, substr(b.MISC_2,117,1)||substr(b.MISC_2,46,3)||substr(b.MISC_2,118,1),a.TXN_NUM from TBL_ALGO_DTL a left join TBL_BRH_INFO c on trim(a.BRH_INS_ID_CD)=c.BRH_ID left join TBL_MCHT_BASE_INF d on a.MCHT_CD = d.MCHT_NO left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref left join TBL_ALGO_CONFIRM_HIS e on a.DATE_SETTLMT = e.DATE_SETTLMT ";
		String sql = "SELECT a.DATE_SETTLMT,a.BRH_INS_ID_CD, c.CREATE_NEW_NO||'-'||c.BRH_NAME, a.MCHT_CD, a.MCHT_CD||'-'||d.MCHT_NM ,a.TRANS_AMT ,(a.MCHT_FEE_D - a.MCHT_FEE_C) as MCHT_FEE, (a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,a.trans_date,a.trans_date_time,a.txn_ssn, b.retrivl_ref, e.DATE_SETTLMT as settleFlag ,"
				+ "CASE WHEN A.TXN_NUM = '3101' THEN (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "WHEN A.TXN_NUM = '3091' THEN  (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "WHEN A.TXN_NUM = '5151' THEN  (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "ELSE (TRANS_AMT/100)-(MCHT_FEE_D-MCHT_FEE_C) END as SETTL_AMT,"
				+ "substr(b.MISC_2,117,1)||substr(b.MISC_2,46,3)||substr(b.MISC_2,118,1),a.TXN_NUM from TBL_ALGO_DTL a left join TBL_BRH_INFO c on trim(a.BRH_INS_ID_CD)=c.BRH_ID left join TBL_MCHT_BASE_INF d on a.MCHT_CD = d.MCHT_NO left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref left join TBL_ALGO_CONFIRM_HIS e on a.DATE_SETTLMT = e.DATE_SETTLMT ";
		sql = sql + whereSql.toString()  + " order by a.DATE_SETTLMT desc ";
		String countSql = "SELECT count(*) FROM TBL_ALGO_DTL a "+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		DecimalFormat df = new DecimalFormat("0.00");
		//3101消费撤销  3091预授权完成撤销 5151-POS退货 负交易
		String STRING_3010 = "3101";
		String STRING_3091 = "3091";
		String STRING_5151 = "5151";
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				/*if (objects[0] != null && !"".equals(objects[0])) {
					objects[0] = CommonFunction.dateFormat(objects[0].toString());
				}*/
				if (objects[5] != null && !"".equals(objects[5])) {
					objects[5] = df.format(Double.parseDouble(objects[5].toString())/100);//交易金额是以分为单位，所以要除100
//					objects[5] = CommonFunction.moneyFormat(objects[5].toString());
					//正交易
					if (!STRING_3010.equals(objects[15]) && !STRING_3091.equals(objects[15]) && !STRING_5151.equals(objects[15])){
						objects[5] = CommonFunction.moneyFormat(objects[5].toString());
						//3101消费撤销  3091预授权完成撤销 5151-POS退货 负交易
					}else{
						objects[5] = "-" + CommonFunction.moneyFormat(objects[5].toString());
					}
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6].toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6].toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7].toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7].toString());
				}
				if (objects[13] != null && !"".equals(objects[13])) {
					objects[13] = df.format(Double.parseDouble(objects[13].toString()));
					objects[13] = CommonFunction.moneyFormat(objects[13].toString());
				}
			
			}
		}
		
		/*if (dataList.size() > 0) {
			List<Object[]> amtList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			Double amt5 = 0.00;
			Double amt6 = 0.00;
			Double amt7 = 0.00;
			Object[] obj = new Object[dataList.get(0).length];
			for (Object[] objects : amtList) {
				if (objects[5] != null && !"".equals(objects[5])) {
					amt5 += Double.parseDouble(objects[5].toString());
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					amt6 += Double.parseDouble(objects[6].toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					amt7 += Double.parseDouble(objects[7].toString());
				}
			}
			obj[4] = "<font color='red'>总计</font>";
			obj[5] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt5)) + "</font>";
			obj[6] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt6)) + "</font>";
			obj[7] = "<font color='red'>"+ CommonFunction.moneyFormat(df.format(amt7)) + "</font>";
			dataList.add(obj);
		}*/
		
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 审核通过没有开户的商户信息查询
	 * @param begin
	 * @param request
	 * @return
	 * 2015年12月31日 下午2:41:39
	 * @author Jee Khan
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getUnOpenVirtualAcctMcht(int begin, HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);

		whereSql += " AND BANK_NO IN " + operator.getBrhBelowId() + " ";
		whereSql += " AND MCHT_STATUS = '0' ";
		whereSql += " AND nvl(MCHT_BAK3,'0') = '0'";
		Object[] ret = new Object[2];

		String sql = " SELECT MCHT_NO,MCHT_NM,MCHT_STATUS  FROM  TBL_MCHT_BASE_INF a "
				+ whereSql
				+ "ORDER BY rec_upd_ts desc";

		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 审核通过没有开户的合作伙伴信息查询
	 * @param begin
	 * @param request
	 * @return
	 * 2015年12月31日 下午6:06:30
	 * @author Jee Khan
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getUnOpenVirtualAcctBrh(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
	
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "a.BRH_ID IN " + operator.getBrhBelowId());
		whereSql.append(" AND a.STATUS = '1'");
		whereSql.append(" AND nvl(a.RESV3,'0') = '0'");
	
		String sql = "SELECT a.BRH_ID,nvl(a.CREATE_NEW_NO,'旧数据未生成') createNewNo,a.BRH_NAME,a.status"
				+ " FROM TBL_BRH_INFO a"
				+ whereSql.toString() 
				+ " order by a.last_upd_ts desc,a.BRH_LEVEL,a.BRH_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_BRH_INFO a"
				+ whereSql.toString();
	
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 通道应到账报表
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getChlTrans(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
	
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String queryInstCode = request.getParameter("queryInstCode");
		
		StringBuffer whereSql = new StringBuffer("where 1=1 ");
		if (isNotEmpty(queryInstCode)) {
			whereSql.append(" AND r.chl_id ").append(" = ").append("'").append(queryInstCode).append("'");
		}
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND r.settl_date ").append(" >= ").append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND r.settl_date ").append(" <= ").append("'").append(endDate).append("'");
		}
	
		String subsql = " select r.chl_id, "
		+ "	       r.settl_date, "
		+ "	       to_char(sum(amt_trans_bal), '99999999999999990D00') amt_trans_bal, "
		+ "	       to_char(sum(amt_trans_err), '99999999999999990D00') amt_trans_err, "
		+ "	       to_char(sum(poundage_bal), '99999999999999990D00') poundage_bal, "
		+ "	       to_char(sum(poundage_err), '99999999999999990D00') poundage_err, "
		+ "	       to_char(sum(amt_recv), '99999999999999990D00') amt_recv "
		+ "	  from (select a.CHNL_ID chl_id, "
		+ "	               a.settl_date, "
		+ "	               sum(nvl(to_number(trim(a.trans_amt)), 0)/100) amt_trans_bal, "
		+ "	               0 amt_trans_err, "
		+ "	               sum(nvl(to_number(trim(a.cups_fee_c)), 0) - nvl(to_number(trim(a.cups_fee_d)), 0)) poundage_bal, "
		+ "	               0 poundage_err, "
		+ "	               (sum(nvl(to_number(trim(a.trans_amt)), 0)/100) - sum(nvl(to_number(trim(a.cups_fee_c)), 0) - nvl(to_number(trim(a.cups_fee_d)), 0))) amt_recv "
		+ "	          from TBL_ALGO_DTL a "
		+ "	         group by a.CHNL_ID, a.settl_date "
		+ "	        union all "
		+ "	        select b.inst_code chl_id, "
		+ "	               b.date_settlmt settle_date, "
		+ "	               0 amt_trans_bal, "
		+ "	               sum(nvl(to_number(trim(b.inst_amt_trans)), 0)/100) amt_trans_err, "
		+ "	               0 poundage_bal, "
		+ "	               sum(nvl(to_number(trim(b.inst_trans_fee)), 0)/100) poundage_err, "
		+ "	               (sum(nvl(to_number(trim(b.inst_amt_trans)), 0)/100) - sum(nvl(to_number(trim(b.inst_trans_fee)), 0)/100)) amt_recv "
		+ "	          from BTH_ERR_DTL b "
		+ "	         where b.stlm_flag = '2' "
		+ "	         group by b.inst_code, b.date_settlmt) r "
		+ whereSql.toString()
		+ "	 group by r.chl_id, r.settl_date ";
			 
		String sql = "select d.chl_id,d.chl_id||'-'||f.first_brh_name,d.settl_date ,d.amt_trans_bal,d.amt_trans_err,d.poundage_bal,d.poundage_err,d.amt_recv from ( " + subsql	+ " ) d  "
			+ " left join tbl_first_brh_dest_id f "
			+ " on d.chl_id = f.dest_id "
			+ "	 order by d.settl_date desc ,d.chl_id ";
		
		String countSql = "SELECT COUNT(*) from ( " + subsql	+ ")";
	
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 商户历史信息查询
	 * @param begin
	 * @param request
	 * @return
	 * 2015年12月31日 下午6:06:30
	 * @author Jee Khan
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntHisInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String mchtNo = request.getParameter("mchtNo");
		if(mchtNo==null){
			mchtNo=" ";
		}
	
		String sql = "SELECT MCHT_STATUS,UPD_OPR_ID,TO_CHAR(TO_DATE(REC_UPD_TS,'YYYY-MM-DD HH24:MI:SS') ,'YYYY-MM-DD HH24:MI:SS') REC_UPD_TS,"+
					"CRT_OPR_ID,TO_CHAR(TO_DATE(REC_CRT_TS,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') REC_CRT_TS,MCHT_NO_NEW FROM TBL_MCHT_BASE_INF_TMP_HIST "+
					 "WHERE MCHT_NO='"+mchtNo+"'"+
					 " ORDER BY TO_NUMBER(MCHT_NO_NEW) DESC";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP_HIST "+
						 "WHERE MCHT_NO='"+mchtNo+"'"+
						 " ORDER BY TO_NUMBER(MCHT_NO_NEW) DESC";
	
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	/**
	 * 商户信息差分列表 
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtInfDiffByHis(int begin, HttpServletRequest request) {
		
		String mchntId = request.getParameter("mchtNo");
		String mchtNoNew = request.getParameter("mchtNoNew");
		if(StringUtils.isEmpty(mchtNoNew)){
			return getMchtInfDiff(begin, request);
		}else if("1".equals(mchtNoNew)){
			mchtNoNew="2";
		}
		int serail = Integer.parseInt(mchtNoNew);
		String orgSerail=serail+"";
		String newSerail=serail-1+"";
		Object[] ret = new Object[2];
		
		//修改前数据查询sql
		StringBuilder sqlCommon = new StringBuilder();
		sqlCommon
		.append("SELECT DECODE(TRIM(b.MCHT_BAK4),1,'开通','未开通') 开通提现,(SELECT CSP.VALUE FROM CST_SYS_PARAM CSP WHERE CSP.OWNER = 'MCHNT_ATTR' AND CSP.KEY = ETPS_ATTR) 商户类别,MCHT_GROUP_ID 集团商户,MCHT_NM 商户名称,MCHT_CN_ABBR 中文简称,");
sqlCommon
		.append("BANK_NO 签约机构,(SELECT TIMTG.DESCR FROM TBL_INF_MCHNT_TP_GRP TIMTG  WHERE TIMTG.MCHNT_TP_GRP =MCHT_GRP) 商户组别,b.RISL_LVL||(select distinct '-'||c.RESVED from TBL_RISK_LVL c where c.RISK_LVL=b.RISL_LVL) as 风险级别,MCC,");
sqlCommon
		.append("DECODE (SUBSTR (MCHT_FUNCTION,0,1),0,'T+N',1,'周期结算') 结算类型方式,SUBSTR (SUBSTR (MCHT_FUNCTION,3,3),3,1) N,");
sqlCommon
		.append("LICENCE_NO 营业执照号码,LICENCE_END_DATE 营业执照有效期,FAX_NO 税务登记证号码,(SELECT CSP.VALUE FROM CST_SYS_PARAM CSP WHERE CSP.OWNER = 'MCHNT_ATTR' AND CSP.KEY = B.ETPS_ATTR) 商户类型,COMM_EMAIL 电子邮件,");
sqlCommon
		.append("POST_CODE 邮政编码,MANAGER 法人代表,BUS_AMT 注册资金,(SELECT CSP.VALUE FROM CST_SYS_PARAM CSP WHERE CSP.OWNER = 'CERTIFICATE' AND CSP.KEY = B.ARTIF_CERTIF_TP) 法人代表证件类型,IDENTITY_NO 法人代表证件号码,PROL_DATE 证件有效期,");
sqlCommon
		.append("DECODE (SUBSTR (MCHT_BAK2,0,1),0,'比例',1,'比例+封顶') 手续费方式,");
sqlCommon
		.append("COMP_NM 经营名称,BUS_INFO 经营内容,BUS_AREA 经营面积,CONTACT 联系人姓名,COMM_TEL 联系人电话,COMP_ADDR 注册地址,PROL_TLR 经营单位,SUBSTR(OPEN_TIME,1,2)||':'||SUBSTR(OPEN_TIME,3,2) 营业开始时间,");
sqlCommon
		.append("SUBSTR(CLOSE_TIME,1,2)||':'||SUBSTR(CLOSE_TIME,3,2) 营业结束时间,ADDR 详细地址,DECODE (SUBSTR (SETTLE_ACCT, 0, 1),0,'对公账户',1,'对私账户') 商户结算账户类型,OPEN_STLNO 商户账户开户行号,SETTLE_BANK_NM 商户账户开户行名称,");
sqlCommon
		.append("(SELECT TRI.REGION_NAME FROM TBL_REGION_INFO TRI WHERE TRI.SUPER_REGION_CODE_BUSI = '0000' AND TRI.REGION_CODE_BUSI = B.MCHT_BAK1) 省,");
sqlCommon
		.append("(SELECT TRI.REGION_NAME FROM TBL_REGION_INFO TRI WHERE TRI.SUPER_REGION_CODE_BUSI != '0000' AND TRI.REGION_CODE_BUSI = TRIM(B.AREA_NO)) 市,");
sqlCommon
		.append("SUBSTR (SETTLE_ACCT, 2) 商户结算帐户开户行号,SETTLE_ACCT_NM 商户账户户名,");
sqlCommon
		.append("DECODE( SUBSTR (SPE_SETTLE_TP,1,1),0,'按比例',1,'比例+封顶') 分润方式,(SELECT TPRI.FEE_NAME FROM TBL_PROFIT_RATE_INFO TPRI WHERE TPRI.RATE_ID = SUBSTR (SPE_SETTLE_TP,2,2)) 分润比例档位,(SELECT TPRI.FEE_NAME FROM TBL_PROFIT_RATE_INFO TPRI WHERE TPRI.RATE_ID = SUBSTR (SPE_SETTLE_TP,4,2)) 分润封顶档位,");
sqlCommon
		.append("DECODE(BANK_STATEMENT,0,'是',1,'否') 对账单,BANK_STATEMENT_REASON 对账单理由,DECODE(INTEGRAL,0,'是',1,'否') 积分,INTEGRAL_REASON 积分理由,DECODE(EMERGENCY,0,'否',1,'是','否') 紧急,");
sqlCommon
		.append("DECODE(COMPLIANCE,0,'是',1,'否') 是否合规, DECODE(COUNTRY,1,'否',0,'是','否') 是否县乡");

		
//		StringBuilder sqlAfter = new StringBuilder(sqlCommon.toString());
//		sqlAfter.append(" FROM TBL_MCHT_BASE_INF_TMP b,TBL_MCHT_SETTLE_INF_TMP s ");
//		sqlAfter.append(" WHERE b.MCHT_NO='"+mchntId+"' AND b.MCHT_NO = s.MCHT_NO");
		
		//修改历史数据查询sql
		StringBuilder sqlBefore = new StringBuilder(sqlCommon.toString());
		sqlBefore.append(" FROM TBL_MCHT_BASE_INF_TMP_HIST b,TBL_MCHT_SETTLE_INF_TMP_HIST s ");
		sqlBefore.append(" WHERE b.MCHT_NO='"+mchntId+"' AND b.MCHT_NO = s.MCHT_NO AND b.MCHT_NO_NEW = s.MCHT_NO_NEW AND b.MCHT_NO_NEW IN ('"+orgSerail+"','"+newSerail+"') order by B.mcht_NO_NEW desc");
		//sqlBSb.append(" AND b.MCHT_NO_NEW = s.MCHT_NO_NEW");

		List<Object[]> dataListBefore = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlBefore.toString());

		List<Object[]> dataListResult = new ArrayList<Object[]>();
		//对比列的个数(等于sql查询出的列数+1(1为最左边的固定文字列))
		int colsNum = 50;
		
		String blank = "";
		
		//修改前内容
		Object[] resultsBefor =  new Object[colsNum];
		//resultsBefor[0]="修改前";
		//修改前内容
		Object[] resultsAfter =  new Object[colsNum];
		//resultsAfter[0]="修改后";
		
		if (dataListBefore.size() <= 1){
			ret[1] = 0;
		}else{
			//List<Object[]> dataListAfter = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlAfter.toString());
//			if (dataListAfter.size() == 0){
//				ret[1] = 0;
//			}else{
				ret[1] = 1;
				
				//获取历史记录
				Object[] objectsBefore = dataListBefore.get(1);
				Object[] objectsAfter = dataListBefore.get(0);
				
				for (int i = 0; i < objectsBefore.length; i++) {
					if(objectsBefore[i] == null && objectsAfter[i] == null){
						resultsBefor[i] = blank;
						resultsAfter[i] = blank;
					}else if (objectsBefore[i] == null && objectsAfter[i] != null){
						resultsBefor[i] = blank;
						resultsAfter[i] = objectsAfter[i].toString();
					}else if (objectsBefore[i] != null && objectsAfter[i] == null){
						resultsBefor[i] = objectsBefore[i].toString();
						resultsAfter[i] = blank;
					}else{
						//内容相同
						if (objectsBefore[i].equals(objectsAfter[i])){
							resultsBefor[i] = blank;
							resultsAfter[i] = blank;
							//内容不同
						}else{
							resultsBefor[i] = objectsBefore[i].toString();
							resultsAfter[i] = objectsAfter[i].toString();
						}
					}
//				}
			}
		}
		dataListResult.add(resultsBefor);
		dataListResult.add(resultsAfter);
		ret[0] = dataListResult;
		return ret;
	}
	/**
	 * 差错交易人工处理影像
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getAdjustErrTrad(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String settleDate = request.getParameter("settleDate");
		String keyInst = request.getParameter("keyInst");
		List<Object[]> dataList = new ArrayList<Object[]>();
		String basePath = SysParamUtil.getParam(SysParamConstants.ADJUST_OFFLINE_FILE_DISK);

		basePath = basePath.replace("\\", "/");
		basePath += settleDate.replaceAll("-", "") + "_" + keyInst;
		File fl = new File(basePath);
		File[] files = fl.listFiles();
		if (null == files) {
			ret[0] = dataList;
			ret[1] = dataList.size();
			return ret;
		}
		BufferedImage bi = null;
		for (File file : files) {
			Object[] obj = new Object[4];
			obj[0] = "btBig" + dataList.size();
			try {
				// fs=new FileInputStream(file);
				bi = ImageIO.read(file);
				double width = bi.getWidth();
				double height = bi.getHeight();
				// bi.flush();
				bi = null;
				double rate = 0;
				// 等比例缩放
				if (width > 400 || height > 400) {
					if (width > height) {
						rate = 400 / width;
						width = 400;
						height = height * rate;
					} else {
						rate = 400 / height;
						height = 400;
						width = width * rate;
					}
				}
				obj[1] = (int) width;
				obj[2] = (int) height;
			} catch (Exception e) {
				obj[1] = 400;
				obj[2] = 400;
				e.printStackTrace();
			}
			obj[3] = file.getName();
			dataList.add(obj);

		}
		ret[0] = dataList;
		ret[1] = dataList.size();
		return ret;
	}
	
	/**
	 * 商户风控参数信息查询 
	 * 
	 * @param begin
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtRiskParamList(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String mchtNo = request.getParameter("mchtNo");
		String mchtNm = request.getParameter("mchtNm");
		String brhNo = request.getParameter("brhNo");
		StringBuffer sb = new StringBuffer();
		if(!StringUtils.isEmpty(mchtNo)){
			sb.append(" and  p.mcht_id like '%").append(mchtNo).append("%'");
		}
		if(!StringUtils.isEmpty(mchtNm)){
			sb.append(" and  m.mcht_nm like '%").append(mchtNm).append("%'");
		}
		if(!StringUtils.isEmpty(brhNo)){
			sb.append(" and  b.brh_id like '%").append(brhNo).append("%'");
		}
		
		String sql = "select p.mcht_id, m.mcht_nm,b.create_new_no,b.brh_name,"
				+ "       p.credit_single_amt, p.credit_day_amt, p.credit_day_count, p.credit_mon_amt, p.credit_mon_count,"
				+ "       p.debit_single_amt, p.debit_day_amt, p.debit_day_count, p.debit_mon_amt, p.debit_mon_count,"
				+ "       p.remark, p.upd_time,m.MCHT_STATUS,p.mcht_amt,p.mcht_day_amt,p.mcht_pos_amt "
				+ "  from tbl_risk_param_mng p"
				+ "  join tbl_mcht_base_inf m"
				+ "    on p.mcht_id = m.mcht_no"
				+ "  join tbl_brh_info b"
				+ "    on m.bank_no = b.brh_id"
				+ " where p.risk_type = '0'"
				+ sb.toString()
				+ " order by p.upd_time desc,m.mcht_no";
		
		String countSql = " select count(*)"
				+ "  from tbl_risk_param_mng p"
				+ "  join tbl_mcht_base_inf m"
				+ "    on p.mcht_id = m.mcht_no"
				+ "  join tbl_brh_info b"
				+ "    on m.bank_no = b.brh_id"
				+ " where p.risk_type = '0'"
				+ sb.toString();
		
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 终端风控参数信息查询 
	 * 
	 * @param begin
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermRiskParamList(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String termNo = request.getParameter("termNo");
		String mchtNo = request.getParameter("mchtNo");
		String mchtNm = request.getParameter("mchtNm");
		String brhNo = request.getParameter("brhNo");
		StringBuffer sb = new StringBuffer();
		if(!StringUtils.isEmpty(termNo)){
			sb.append(" and  p.term_id like '%").append(termNo).append("%'");
		}
		if(!StringUtils.isEmpty(mchtNo)){
			sb.append(" and  p.mcht_id like '%").append(mchtNo).append("%'");
		}
		if(!StringUtils.isEmpty(mchtNm)){
			sb.append(" and  m.mcht_nm like '%").append(mchtNm).append("%'");
		}
		if(!StringUtils.isEmpty(brhNo)){
			sb.append(" and  b.brh_id like '%").append(brhNo).append("%'");
		}
		
		String sql = "select p.term_id,p.mcht_id, m.mcht_nm,b.create_new_no,b.brh_name,"
				+ "       p.credit_single_amt, p.credit_day_amt, p.credit_day_count, p.credit_mon_amt, p.credit_mon_count,"
				+ "       p.debit_single_amt, p.debit_day_amt, p.debit_day_count, p.debit_mon_amt, p.debit_mon_count,"
				+ "       p.remark, p.upd_time,t.term_para_1,t.term_sta,tt.rec_crt_ts"
				+ "  from tbl_risk_param_mng p"
				+ "  join tbl_term_inf t"
				+ "    on p.term_id = t.term_id"
				+ "   and p.mcht_id = t.mcht_cd"
				+ "  join tbl_term_inf_tmp tt"
				+ "    on p.term_id = tt.term_id"
				+ "   and p.mcht_id = tt.mcht_cd"
				+ "  join tbl_mcht_base_inf m"
				+ "    on p.mcht_id = m.mcht_no"
				+ "  join tbl_brh_info b"
				+ "    on m.bank_no = b.brh_id"
				+ " where p.risk_type = '1'"
				+ sb.toString()
				+ " order by p.upd_time desc,p.mcht_id,p.term_id";
		
		String countSql = " select count(*)"
				+ "  from tbl_risk_param_mng p"
				+ "  join tbl_term_inf t"
				+ "    on p.term_id = t.term_id"
				+ "   and p.mcht_id = t.mcht_cd"
				+ "  join tbl_mcht_base_inf m"
				+ "    on p.mcht_id = m.mcht_no"
				+ "  join tbl_brh_info b"
				+ "    on m.bank_no = b.brh_id"
				+ " where p.risk_type = '1'"
				+ sb.toString();
		
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 商户信息差分列表
	 * 
	 * @param begin
	 * @param request
	 * @return 2014-10-20下午04:23:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtDiff(int begin, HttpServletRequest request) {

		String mchntId = request.getParameter("mchntId");
		String mchtSta = "";
		String sql = "SELECT T.MCHT_STATUS FROM TBL_MCHT_BASE_INF_TMP_TMP T WHERE T.MCHT_NO = '"+mchntId+"'";
		List<String> mchtStaList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(mchtStaList!=null&&mchtStaList.size()>0){
			if(StringUtils.isNotBlank(mchtStaList.get(0))){
				mchtSta = mchtStaList.get(0);
			}
		}
		//查询商户状态1.变更时查询tmptmp和正式表做对比2.新添加初审tmptmp和his做对比3.新添加终审最新的两条his做对比
		Object[] ret = new Object[2];

		// 修改前数据查询sql
		StringBuilder sqlCommon = new StringBuilder();
		StringBuilder sqlFee = new StringBuilder();
		sqlCommon
				.append("SELECT DECODE(TRIM(b.MCHT_BAK4),1,'开通','未开通') 开通提现,(SELECT CSP.VALUE FROM CST_SYS_PARAM CSP WHERE CSP.OWNER = 'MCHNT_ATTR' AND CSP.KEY = ETPS_ATTR) 商户类别,MCHT_GROUP_ID 集团商户,MCHT_NM 商户名称,MCHT_CN_ABBR 中文简称,");
		sqlCommon
				.append("BANK_NO 签约机构,(SELECT TIMTG.DESCR FROM TBL_INF_MCHNT_TP_GRP TIMTG  WHERE TIMTG.MCHNT_TP_GRP =MCHT_GRP) 商户组别,b.RISL_LVL||(select distinct '-'||c.RESVED from TBL_RISK_LVL c where c.RISK_LVL=b.RISL_LVL) as 风险级别,MCC,");
		sqlCommon
				.append("DECODE (SUBSTR (MCHT_FUNCTION,0,1),0,'T+N',1,'周期结算') 结算类型方式,SUBSTR (SUBSTR (MCHT_FUNCTION,3,3),3,1) N,");
		sqlCommon
				.append("LICENCE_NO 营业执照号码,LICENCE_END_DATE 营业执照有效期,FAX_NO 税务登记证号码,(SELECT CSP.VALUE FROM CST_SYS_PARAM CSP WHERE CSP.OWNER = 'MCHNT_ATTR' AND CSP.KEY = B.ETPS_ATTR) 商户类型,COMM_EMAIL 电子邮件,");
		sqlCommon
				.append("POST_CODE 邮政编码,MANAGER 法人代表,BUS_AMT 注册资金,(SELECT CSP.VALUE FROM CST_SYS_PARAM CSP WHERE CSP.OWNER = 'CERTIFICATE' AND CSP.KEY = B.ARTIF_CERTIF_TP) 法人代表证件类型,IDENTITY_NO 法人代表证件号码,PROL_DATE 证件有效期,");
		sqlCommon
				.append("DECODE (SUBSTR (MCHT_BAK2,0,1),0,'比例',1,'比例+封顶') 手续费方式,");
		sqlCommon
				.append("COMP_NM 经营名称,BUS_INFO 经营内容,BUS_AREA 经营面积,CONTACT 联系人姓名,COMM_TEL 联系人电话,COMP_ADDR 注册地址,PROL_TLR 经营单位,SUBSTR(OPEN_TIME,1,2)||':'||SUBSTR(OPEN_TIME,3,2) 营业开始时间,");
		sqlCommon
				.append("SUBSTR(CLOSE_TIME,1,2)||':'||SUBSTR(CLOSE_TIME,3,2) 营业结束时间,ADDR 详细地址,DECODE (SUBSTR (SETTLE_ACCT, 0, 1),0,'对公账户',1,'对私账户') 商户结算账户类型,OPEN_STLNO 商户账户开户行号,SETTLE_BANK_NM 商户账户开户行名称,");
		sqlCommon
				.append("(SELECT TRI.REGION_NAME FROM TBL_REGION_INFO TRI WHERE TRI.SUPER_REGION_CODE_BUSI = '0000' AND TRI.REGION_CODE_BUSI = B.MCHT_BAK1) 省,");
		sqlCommon
				.append("(SELECT TRI.REGION_NAME FROM TBL_REGION_INFO TRI WHERE TRI.SUPER_REGION_CODE_BUSI != '0000' AND TRI.REGION_CODE_BUSI = TRIM(B.AREA_NO)) 市,");
		sqlCommon
				.append("SUBSTR (SETTLE_ACCT, 2) 商户结算帐户开户行号,SETTLE_ACCT_NM 商户账户户名,");
		sqlCommon
				.append("DECODE( SUBSTR (SPE_SETTLE_TP,1,1),0,'按比例',1,'比例+封顶') 分润方式,(SELECT TPRI.FEE_NAME FROM TBL_PROFIT_RATE_INFO TPRI WHERE TPRI.RATE_ID = SUBSTR (SPE_SETTLE_TP,2,2)) 分润比例档位,(SELECT TPRI.FEE_NAME FROM TBL_PROFIT_RATE_INFO TPRI WHERE TPRI.RATE_ID = SUBSTR (SPE_SETTLE_TP,4,2)) 分润封顶档位,");
		sqlCommon
				.append("DECODE(BANK_STATEMENT,0,'是',1,'否') 对账单,BANK_STATEMENT_REASON 对账单理由,DECODE(INTEGRAL,0,'是',1,'否') 积分,INTEGRAL_REASON 积分理由,DECODE(EMERGENCY,0,'否',1,'是','否') 紧急,");
		sqlCommon
		.append("DECODE(COMPLIANCE,0,'是',1,'否') 是否合规, DECODE(COUNTRY,1,'否',0,'是','否') 是否县乡");
		sqlFee
				.append("(SELECT THA.FEE_VALUE FROM TBL_HIS_DISC_ALGO THA WHERE THA.DISC_ID = S.FEE_RATE AND CARD_TYPE = '00') 借记卡手续费比例,");
		sqlFee
				.append("(SELECT THA.MAX_FEE FROM TBL_HIS_DISC_ALGO THA WHERE THA.DISC_ID = S.FEE_RATE AND CARD_TYPE = '00') 借记卡手续费封顶,");
		sqlFee
				.append("(SELECT THA.FEE_VALUE FROM TBL_HIS_DISC_ALGO THA WHERE THA.DISC_ID = S.FEE_RATE AND CARD_TYPE = '01') 贷记卡手续费比例,");
		sqlFee
				.append("(SELECT THA.MAX_FEE FROM TBL_HIS_DISC_ALGO THA WHERE THA.DISC_ID = S.FEE_RATE AND CARD_TYPE = '01') 贷记卡手续费封顶");
		StringBuilder sqlAfter = new StringBuilder(sqlCommon.toString());
		sqlAfter.append(" FROM TBL_MCHT_BASE_INF_TMP_TMP b,TBL_MCHT_SETTLE_INF_TMP_TMP s ");
		sqlAfter.append(" WHERE b.MCHT_NO='" + mchntId
				+ "' AND b.MCHT_NO = s.MCHT_NO");

		// 修改历史数据查询sql
		StringBuilder sqlBefore = new StringBuilder(sqlCommon.toString());
		if("B".equals(mchtSta)||"1".equals(mchtSta)){//添加待初审
			sqlBefore.append(" FROM TBL_MCHT_BASE_INF_TMP_HIST b,TBL_MCHT_SETTLE_INF_TMP_HIST s ");
			sqlBefore.append(" WHERE b.MCHT_NO='"+mchntId+"' AND B.MCHT_STATUS !='D' AND b.MCHT_NO = s.MCHT_NO AND b.MCHT_NO_NEW = s.MCHT_NO_NEW  order by TO_NUMBER(B.MCHT_NO_NEW) desc");
		}else {
			sqlBefore.append(" FROM TBL_MCHT_BASE_INF b,TBL_MCHT_SETTLE_INF s ");
			sqlBefore.append(" WHERE b.MCHT_NO='" + mchntId
					+ "' AND b.MCHT_NO = s.MCHT_NO ");
		}
		// sqlBSb.append(" AND b.MCHT_NO_NEW = s.MCHT_NO_NEW");

		List<Object[]> dataListBefore = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sqlBefore.toString());
		List<Object[]> dataListAfter = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sqlAfter.toString());

		List<Object[]> dataListResult = new ArrayList<Object[]>();
		// 对比列的个数(等于sql查询出的列数+1(1为最左边的固定文字列))
		int colsNum = 62;

		String blank = "";
		String cashFlag = "";//是否开通体现标志
		// 修改前内容
		Object[] resultsBefor = new Object[colsNum];
		// 修改前内容
		Object[] resultsAfter = new Object[colsNum];

		if (dataListBefore.size() < 1) {
			ret[1] = 0;
		} else if(("B".equals(mchtSta)||"1".equals(mchtSta))&&dataListBefore.size() <= 1){
			ret[1] = 0;
		}else {
			ret[1] = 1;
			// 获取历史记录
			Object[] objectsAfter;
			Object[] objectsBefore;
			if("B".equals(mchtSta)||"1".equals(mchtSta)){
				objectsBefore = dataListBefore.get(1);
				objectsAfter = dataListBefore.get(0);
			}else {
				objectsAfter = dataListAfter.get(0);
				objectsBefore = dataListBefore.get(0);
			}
			cashFlag = (String) objectsAfter[0];
			int length = objectsBefore.length;
			for (int i = 0; i < objectsBefore.length; i++) {
				if (objectsBefore[i] == null && objectsAfter[i] == null) {
					resultsBefor[i] = blank;
					resultsAfter[i] = blank;
				} else if (objectsBefore[i] == null && objectsAfter[i] != null) {
					resultsBefor[i] = blank;
					resultsAfter[i] = objectsAfter[i].toString();
				} else if (objectsBefore[i] != null && objectsAfter[i] == null) {
					resultsBefor[i] = objectsBefore[i].toString();
					resultsAfter[i] = blank;
				} else {
					// 内容相同
					if (objectsBefore[i].equals(objectsAfter[i])) {
						resultsBefor[i] = blank;
						resultsAfter[i] = blank;
						// 内容不同
					} else {
						resultsBefor[i] = objectsBefore[i].toString();
						resultsAfter[i] = objectsAfter[i].toString();
					}
				}
			}
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT (SELECT THA.FEE_VALUE FROM TBL_HIS_DISC_ALGO THA WHERE THA.DISC_ID = S.FEE_RATE AND THA.CARD_TYPE = '00') 借记卡手续费比例,");
			sqlQuery.append("(SELECT THA.MAX_FEE FROM TBL_HIS_DISC_ALGO THA WHERE THA.DISC_ID = S.FEE_RATE AND THA.CARD_TYPE = '00') 借记卡手续费封顶,");
			sqlQuery.append("(SELECT THA.FEE_VALUE FROM TBL_HIS_DISC_ALGO THA WHERE THA.DISC_ID = S.FEE_RATE AND THA.CARD_TYPE = '01') 贷记卡手续费比例,");
			sqlQuery.append("(SELECT THA.MAX_FEE FROM TBL_HIS_DISC_ALGO THA WHERE THA.DISC_ID = S.FEE_RATE AND THA.CARD_TYPE = '01') 贷记卡手续费封顶");
			sqlQuery.append(" FROM TBL_MCHT_SETTLE_INF_TMP_TMP s ");
			sqlQuery.append(" WHERE S.MCHT_NO='" + mchntId+"'");
			List<Object[]> feeListBefore;
			List<Object[]> feeListAfter;
			feeListAfter =  CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sqlQuery.toString());
			if("B".equals(mchtSta)||"1".equals(mchtSta)){//查询历史表最新两条对比
				StringBuilder sqlQuery1 = new StringBuilder();
				sqlQuery1.append("SELECT (SELECT THA.FEE_VALUE FROM TBL_HIS_DISC_ALGO_HIS THA WHERE THA.DISC_ID = S.FEE_RATE AND THA.CARD_TYPE = '00'AND THA.SERIAL_NO = (SELECT MAX(SERIAL_NO)-1 FROM TBL_HIS_DISC_ALGO_HIS WHERE DISC_ID = S.FEE_RATE )) 借记卡手续费比例,");
				sqlQuery1.append("(SELECT THA.MAX_FEE FROM TBL_HIS_DISC_ALGO_HIS THA WHERE THA.DISC_ID = S.FEE_RATE AND THA.CARD_TYPE = '00'AND THA.SERIAL_NO = (SELECT MAX(SERIAL_NO)-1 FROM TBL_HIS_DISC_ALGO_HIS WHERE DISC_ID = S.FEE_RATE )) 借记卡手续费封顶,");
				sqlQuery1.append("(SELECT THA.FEE_VALUE FROM TBL_HIS_DISC_ALGO_HIS THA WHERE THA.DISC_ID = S.FEE_RATE AND THA.CARD_TYPE = '01'AND THA.SERIAL_NO = (SELECT MAX(SERIAL_NO)-1 FROM TBL_HIS_DISC_ALGO_HIS WHERE DISC_ID = S.FEE_RATE )) 贷记卡手续费比例,");
				sqlQuery1.append("(SELECT THA.MAX_FEE FROM TBL_HIS_DISC_ALGO_HIS THA WHERE THA.DISC_ID = S.FEE_RATE AND THA.CARD_TYPE = '01'AND THA.SERIAL_NO = (SELECT MAX(SERIAL_NO)-1 FROM TBL_HIS_DISC_ALGO_HIS WHERE DISC_ID = S.FEE_RATE )) 贷记卡手续费封顶");
				sqlQuery1.append(" FROM TBL_MCHT_SETTLE_INF_TMP_TMP s ");
				sqlQuery1.append(" WHERE S.MCHT_NO='" + mchntId+"'");
				feeListBefore = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(sqlQuery1.toString());
			}else {//查询历史最新一条和mirror表对比
				StringBuilder sqlQuery1 = new StringBuilder();
				sqlQuery1.append("SELECT (SELECT THAM.FEE_VALUE FROM TBL_HIS_DISC_ALGO_MIRROR THAM WHERE THAM.DISC_ID = S.FEE_RATE AND THAM.CARD_TYPE = '00') 借记卡手续费比例,");
				sqlQuery1.append("(SELECT THAM.MAX_FEE FROM TBL_HIS_DISC_ALGO_MIRROR THAM WHERE THAM.DISC_ID = S.FEE_RATE AND THAM.CARD_TYPE = '00') 借记卡手续费封顶,");
				sqlQuery1.append("(SELECT THAM.FEE_VALUE FROM TBL_HIS_DISC_ALGO_MIRROR THAM WHERE THAM.DISC_ID = S.FEE_RATE AND THAM.CARD_TYPE = '01') 贷记卡手续费比例,");
				sqlQuery1.append("(SELECT THAM.MAX_FEE FROM TBL_HIS_DISC_ALGO_MIRROR THAM WHERE THAM.DISC_ID = S.FEE_RATE AND THAM.CARD_TYPE = '01') 贷记卡手续费封顶");
				sqlQuery1.append(" FROM TBL_MCHT_SETTLE_INF s ");
				sqlQuery1.append(" WHERE S.MCHT_NO='" + mchntId+"'");
				feeListBefore = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(sqlQuery1.toString());
			}
			if(feeListBefore==null||feeListBefore.size()<1||feeListBefore.get(0)==null){
				for (int i = length; i < colsNum ; i++) {
					resultsBefor[i] = blank;
					resultsAfter[i] = blank;
				}
			}else {
				objectsAfter = feeListAfter.get(0);
				objectsBefore = feeListBefore.get(0);
				for (int i = length; i < colsNum-8 ; i++) {
					if (objectsBefore[i-length] == null || objectsAfter[i-length] == null) {
						resultsBefor[i] = blank;
						resultsAfter[i] = blank;
					} else {
						// 内容相同
						if (objectsBefore[i-length].equals(objectsAfter[i-length])) {
							resultsBefor[i] = blank;
							resultsAfter[i] = blank;
							// 内容不同
						} else {
							resultsBefor[i] = objectsBefore[i-length].toString();
							resultsAfter[i] = objectsAfter[i-length].toString();
						}
					}
				}
			}
			//提现历史对比
			StringBuffer cashCommonBuffer = new StringBuffer();
			cashCommonBuffer.append("SELECT DECODE(CASH_TP,1,'主动按金额',2,'自动按单笔') 提现方式,DECODE(FEE_TP,1,'按比例',2,'单笔固定金额') 手续费方式,FEE_INVST_INTRST 手续费垫资日息,DECODE(FEE_INVST_TY,1,'按自然日（D）',2,'按工作日（T）') 手续费垫资天数计算方式, ");
			cashCommonBuffer.append(" FEE_AMT 单笔手续费金额,(SELECT TCRI.NAME FROM TBL_CASH_RATE_INF TCRI WHERE TCRI.RATE_ID = TC.SHR_PRFT_INVST_CD) 分润垫资档位, DECODE(SHR_PRFT_INVST_TY,1,'按自然日（D）',2,'按工作日（T）') 分润垫资天数计算方式,DECODE(BLNC_HNDL_TY,1,'自动结算','其他') 未提现余额处理方式 ");
			StringBuffer cashAfter = new StringBuffer(cashCommonBuffer.toString());
			cashAfter.append(" FROM TBL_MCHT_CASH_INF_TMP_TMP TC,TBL_MCHT_BASE_INF_TMP_TMP TM WHERE TC.MCHT_ID = TM.MCHT_NO AND TM.MCHT_NO='" + mchntId+"'");
			StringBuffer cashBefore = new StringBuffer(cashCommonBuffer);
			List<Object[]> cashListBefore;
			List<Object[]> cashListAfter;
			Object[] cashObjectBefore;
			Object[] cashObjectAfter;
			cashListAfter = CommonFunction.getCommQueryDAO().findBySQLQuery(cashAfter.toString());
			if("B".equals(mchtSta)||"1".equals(mchtSta)){//查询历史表最新两条对比
				cashBefore.append(" FROM TBL_MCHT_CASH_INF_TMP_HIS TC,TBL_MCHT_BASE_INF_TMP_TMP TM WHERE TC.MCHT_ID = TM.MCHT_NO AND TM.MCHT_NO='" + mchntId+"' ORDER BY TC.SEQ_ID DESC");
			}else {
				cashBefore.append(" FROM TBL_MCHT_CASH_INF TC,TBL_MCHT_BASE_INF_TMP_TMP TM WHERE TC.MCHT_ID = TM.MCHT_NO AND TM.MCHT_NO='" + mchntId+"'");
			}
			cashListBefore = CommonFunction.getCommQueryDAO().findBySQLQuery(cashBefore.toString());
			if(cashListBefore == null || cashListBefore.size() ==0 || cashListAfter == null ||cashListAfter.size() == 0){
				cashObjectBefore = new Object[9];
				cashObjectAfter = new Object[9];
			}else {
				if("B".equals(mchtSta)||"1".equals(mchtSta)){
					if(cashListBefore.size()<2){
						cashObjectBefore = cashListBefore.get(0);
					}else{
						cashObjectBefore = cashListBefore.get(1);
					}
					cashObjectAfter = cashListBefore.get(0);
				}else {
					cashObjectAfter = cashListAfter.get(0);
					cashObjectBefore = cashListBefore.get(0);
				}
			}
			for (int i = length+4; i < colsNum ; i++) {
				if("未开通".equals(cashFlag) ){
					resultsBefor[i] = blank;
					resultsAfter[i] = blank;
				}else {
				if (cashObjectBefore[i-length-4] == null || cashObjectAfter[i-length-4] == null) {
					if(cashObjectBefore[i-length-4] == null){
						resultsBefor[i] = blank;
					}else resultsBefor[i] = cashObjectBefore[i-length-4].toString();
					if(cashObjectAfter[i-length-4] == null){
						resultsAfter[i] = blank;
					}else resultsAfter[i] = cashObjectAfter[i-length-4].toString();
				} else {
					// 内容相同
					if (cashObjectBefore[i-length-4].equals(cashObjectAfter[i-length-4])) {
						resultsBefor[i] = blank;
						resultsAfter[i] = blank;
						// 内容不同
					} else {
						resultsBefor[i] = cashObjectBefore[i-length-4].toString();
						resultsAfter[i] = cashObjectAfter[i-length-4].toString();
					}
				}
				}
			}
		}
		
		dataListResult.add(resultsBefor);
		dataListResult.add(resultsAfter);
		ret[0] = dataListResult;
		return ret;
	}
}

