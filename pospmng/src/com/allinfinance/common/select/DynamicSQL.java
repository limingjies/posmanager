package com.allinfinance.common.select;

import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.system.util.ContextUtil;

public class DynamicSQL extends DynamicSQLSupport{
	
	static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	static ICommQueryDAO commGWQueryDAO = (ICommQueryDAO) ContextUtil.getBean("commGWQueryDAO");
	
	public static DynamicSqlBean getMchntId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO,trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' ";
		
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
//		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' ";

		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 动态获取签购单模板信息
	 */
	public static DynamicSqlBean getModelInfo(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select MODEL_ID, MODEL_ID ||' - '|| MODEL_NAME as MODEL_NAME from TBL_MODEL_INFO where  STATUS = '1' ";
		sql += provideSql(sql, "trim(MODEL_ID) ||' - '|| trim(MODEL_NAME)", inputValue);
		sql += " order by MODEL_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 动态获取签购单模板信息
	 */
	public static DynamicSqlBean getModelInfo_as(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select MODEL_ID, MODEL_ID ||' - '|| MODEL_NAME as MODEL_NAME from TBL_MODEL_INFO where  STATUS = '1' ";
		sql += provideSql(sql, "trim(MODEL_ID) ||' - '|| trim(MODEL_NAME)", inputValue);
		sql += " order by MODEL_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntCupBank(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select cnapsbankno, trim(cnapsbankno) ||' - '|| trim(bankname) as MCHT_NMS from tbl_mbf_bank_info where 1=1 ";

		sql += provideSql(sql, "trim(cnapsbankno) ||' - '|| trim(bankname)", inputValue);
		
		sql += " order by cnapsbankno";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntCupBank_as(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select cnapsbankno, trim(cnapsbankno) ||' - '|| trim(bankname) as MCHT_NMS from tbl_mbf_bank_info where 1=1 ";

		sql += provideSql(sql, "trim(cnapsbankno) ||' - '|| trim(bankname)", inputValue);
		
		sql += " order by cnapsbankno";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	
	public static DynamicSqlBean getMchntIdTmp(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF_TMP where MCHT_STATUS = '0' ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdNew(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF_TMP where MCHT_STATUS in('1','B') ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getMchntIdAll(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF_TMP ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntNoAll(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntCupIdAll(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from tbl_mcht_cup_info_tmp where " +
				" crt_opr_id in (select opr_id from tbl_opr_info where brh_id in "+operator.getBrhBelowId()+")";
			
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntCupIdAllInfo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from tbl_mcht_cup_info where " +
				" crt_opr_id in (select opr_id from tbl_opr_info where brh_id in "+operator.getBrhBelowId()+")";
			
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	//直联商户
	public static DynamicSqlBean getMchntIdAlls(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select distinct trim(MCHT_NO) as MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from tbl_mcht_cup_inf ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
//		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//直联终端TMP
	public static DynamicSqlBean getTermCupNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select term_id as id,term_id as c  from tbl_term_cup_info_tmp where " +
					 "crt_opr in (select opr_id from tbl_opr_info where brh_id in "+operator.getBrhBelowId()+")";
			
		sql += " order by id";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//直联终端
	public static DynamicSqlBean getTermCupNoInfo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select term_id as id,term_id as c  from tbl_term_cup_info where " +
					 "crt_opr in (select opr_id from tbl_opr_info where brh_id in "+operator.getBrhBelowId()+")";
			
		sql += " order by id";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getGroupMchntNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT trim(group_mcht_cd),trim(group_mcht_cd)||' - '||trim(group_name) as name FROM tbl_group_mcht_inf  ";
		
		sql += provideSql(sql, "trim(group_mcht_cd)||' - '||trim(group_name)", inputValue);
		
		sql += " ORDER BY group_mcht_cd";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getAreaCode(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_CITY_CODE,MCHT_CITY_CODE||' - '||CITY_NAME from CST_CITY_CODE ";
		
		sql += provideSql(sql, "MCHT_CITY_CODE||' - '||CITY_NAME", inputValue);
		
		sql += " order by MCHT_CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getAreaCode_as(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select CUP_CITY_CODE,CUP_CITY_CODE||' - '||CITY_NAME from CST_CITY_CODE ";
		
		sql += provideSql(sql, "CUP_CITY_CODE||' - '||CITY_NAME", inputValue);
		
		sql += " order by CUP_CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getBranchId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT BRH_ID,BRH_ID||'-'||BRH_NAME as BRH_NAME FROM TBL_BRH_INFO where BRH_LEVEL in ('0','1') ";
		
		sql += provideSql(sql, "BRH_ID||' - '||BRH_NAME", inputValue);
		sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		
		sql += " order by BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getBrhInfoBelowBranch(String inputValue, Operator operator, HttpServletRequest request){
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select BRH_ID,CREATE_NEW_NO||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1','2') AND trim(BRH_ID) IN " + 
			sb.toString();
		
		sql += provideSql(sql, "CREATE_NEW_NO||' - '||BRH_NAME", inputValue);
		sql += " order by BRH_LEVEL ,CREATE_NEW_NO ";
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getBrhInfoBelowBranch_as(String inputValue, Operator operator, HttpServletRequest request){
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1','2') AND trim(BRH_ID) IN " + 
			sb.toString();
		
		sql += provideSql(sql, "BRH_ID||' - '||BRH_NAME", inputValue);
		sql += " order by BRH_LEVEL ,BRH_ID ";
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getBrhId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT BRH_ID,CREATE_NEW_NO||'-'||BRH_NAME as BRH_NAME FROM TBL_BRH_INFO where 1=1 ";
		
		sql += provideSql(sql, "CREATE_NEW_NO||' - '||BRH_NAME", inputValue);
		sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		
		sql += " order by CREATE_NEW_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	public static DynamicSqlBean getBlackMchtId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO,trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_NO in "
				+ "( select MCHT_NO from TBL_RISK_BLACK_MCHT )";
		
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
//		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	
	/**查询操作员所属机构下的所有商户   by caotz*/
	public static DynamicSqlBean getAllMchntId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO,trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS != '8' ";
		
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	
	/**查询所有商户  *-无限制  by caotz*/
	public static DynamicSqlBean getMchntIdRoute(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = " select * from ("
				+ " select '*' as MCHT_NO,'*-无限制' as mcht_nms from dual"
				+ " union all "
				+ " select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF "
				+ " where MCHT_STATUS != '8' order by MCHT_NO ) where 1=1 ";
		
		sql += provideSql(sql, "mcht_nms", inputValue);
//		sql += provideSqlIn(sql, "BANK_NO", operator.getBrhBelowId());
		
//		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**查询所有机构  *-无限制  by caotz*/
	public static DynamicSqlBean getBrhIdRoute(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = " select * from ("
				+ " select '*' as BRH_ID,'*-无限制' as BRH_NAMES from dual"
				+ " union all "
				+ " select BRH_ID, trim(BRH_ID) ||' - '|| trim(BRH_NAME) as BRH_NAMES from TBL_BRH_INFO "
				+ " order by BRH_ID ) where 1=1 ";
		
		
		sql += provideSql(sql, "BRH_NAMES", inputValue);
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**查询所有渠道商户 
	 *  *-无限制 
	 * by zfc 2015-10-29
	 * */
	public static DynamicSqlBean getUpbrhMcht(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = " select * from ("
				+ " select '*' as UPMCHt_ID,'*-无限制' as UPMCHT_NAMES from dual"
				+ " union all "
				+ " select mcht_id_up, trim(mcht_id_up) ||' - '|| trim(mcht_name_up) as UPMCHT_NAMES from Tbl_Upbrh_Mcht	 "
				+ " order by UPMCHt_ID ) where 1=1 ";
		
		
		sql += provideSql(sql, "UPMCHT_NAMES", inputValue);
		
		return new DynamicSqlBean(sql, commGWQueryDAO);
	}

	/**
	 * 查询所有源编号为2801的目的应答码/交易应答
	 * @author yww
	 * 2016年4月15日  下午12:03:15
	 * @param inputValue
	 * @param operator
	 * @param request
	 * @return
	 */
	public static DynamicSqlBean getRespName(String inputValue, Operator operator, HttpServletRequest request){

		String sql = "select respCode,respName from (select trim(DEST_RSP_CODE) as respCode,trim(DEST_RSP_CODE) ||'-'|| trim(rsp_code_dsp) as respName from tbl_rsp_code_map where SRC_ID='2801') where 1=1 ";
		sql += provideSql(sql, "respName", inputValue);
		sql += " order by respCode asc ";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	/**
	 * 获取商户MCC--(TBL_INF_MCHNT_TP 表 MCHNT_TP字段的值)
	 * @author yww
	 * 2016年4月8日  下午2:38:32
	 * @param inputValue
	 * @param operator
	 * @param request
	 * @return
	 */
	public static DynamicSqlBean getMchtMcc(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select mcc,mccDesc from (SELECT MCHNT_TP as mcc, MCHNT_TP||'-'||DESCR as mccDesc FROM TBL_INF_MCHNT_TP) where 1=1 ";
		sql += provideSql(sql, "mccDesc", inputValue);
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**查询所有一级机构  *-无限制  by caotz*/
	public static DynamicSqlBean getFirstBrhId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT BRH_ID,CREATE_NEW_NO||'-'||BRH_NAME as BRH_NAME FROM TBL_BRH_INFO where 1=1 and UP_BRH_ID='00001' ";
		
		sql += provideSql(sql, "CREATE_NEW_NO||' - '||BRH_NAME", inputValue);
		sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		
		sql += " order by BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	
	/**查询机构分润费率代码 by caotz*/
	public static DynamicSqlBean getBrhFeeCtl(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT DISC_ID,DISC_ID||'-'||DISC_NAME as DISC_ID_NAME FROM TBL_BRH_FEE_CTL where 1=1  ";
		
		sql += provideSql(sql, "DISC_ID||' - '||DISC_NAME", inputValue);
//		sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		
		sql += " order by DISC_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchtAcctNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT DISTINCT SUBSTR(SETTLE_ACCT,2),SUBSTR(SETTLE_ACCT,2)||' - '||SETTLE_ACCT_NM FROM TBL_MCHT_SETTLE_INF";
		
		sql += provideSql(sql, "SUBSTR(SETTLE_ACCT,2)||' - '||SETTLE_ACCT_NM", inputValue);
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getHaveAcctMchntId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO,trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_FUNCTION = '0' ";
		
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getPartner(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT trim(d.brh_id),trim(d.CREATE_NEW_NO)||' - '||trim(D .BRH_NAME) as brh  FROM TBL_BRH_INFO D";
		
		sql += provideSql(sql, "trim(d.CREATE_NEW_NO) ||' - '|| trim(D .BRH_NAME)", inputValue);
		
		sql += " order by d.CREATE_NEW_NO";
		return new DynamicSqlBean(sql, commGWQueryDAO);
	}
	
	/**
	 * 发卡行和编号
	 * @param params
	 * @return
	 */
	public static DynamicSqlBean getDepositBank(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select '*' ins_id_cd,'*-外卡及未知卡' card_dis from dual union select trim(ins_id_cd),ins_id_cd||'-'||card_dis from (select cb.ins_id_cd,cb.card_dis, row_number() over(partition by cb.ins_id_cd order by cb.card_dis desc) rk from  tbl_bank_bin_inf cb) a where a.rk=1";
		if(StringUtil.isNotEmpty(inputValue)){
			sql += " AND a.card_dis like'%" + inputValue + "%'";
		}
		return new DynamicSqlBean(sql, commGWQueryDAO);
	}
}
