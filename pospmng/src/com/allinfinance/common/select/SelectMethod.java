package com.allinfinance.common.select;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TxnInfo;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.struts.pos.TblTermManagementConstants;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * 
 * Title: SelectOption接口方法
 * 
 * Description:
 * 
 * Copyright: 
 * 
 * Company:  ALL IN FINANCE 
 * 
 * @author 
 * 
 * @version 1.0
 */
public class SelectMethod {
	
	static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	static ICommQueryDAO commDFQueryDAO =null;//(ICommQueryDAO) ContextUtil.getBean("CommDFQueryDAO");
	static ICommQueryDAO commGWQueryDAO = (ICommQueryDAO) ContextUtil.getBean("commGWQueryDAO");
	/**
	 * 根据当前操作员级别获得机构级别信息，
	 * 只有通联金融返回
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getBrhLvlByOprInfo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		Operator operator = (Operator) params[0];
		
		//通联金融
		if("0".endsWith(operator.getOprBrhLvl())) {
			dataMap.put("1", "一级商户代理");
			dataMap.put("2", "二级商户代理");
//			dataMap.put("3", "网点");
		} else {
//			dataMap.put("2", "支行");
//			dataMap.put("3", "网点");
		}
		return dataMap;
	}
	
	
	/**
	 * 详细信息，
	 * 
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getBrhLvlByDtl(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		Operator operator = (Operator) params[0];
		
			dataMap.put("1", "一级商户代理");
			dataMap.put("2", "二级商户代理");
			dataMap.put("0", "钱宝金融");
		return dataMap;
	}
	
	/**
	 * 根据操作员所在机构返回机构信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getUpBrhIdByOprInfo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];
		StringBuffer sb=new StringBuffer("where trim(BRH_ID) in "+operator.getBrhBelowId());
		if(params.length>1&&StringUtil.isNotEmpty((String)params[1])){
			String brhLevTemp = (String)params[1];
			String brhLev=String.valueOf(Integer.parseInt(brhLevTemp)-1);
			sb.append("  and BRH_LEVEL='"+brhLev+"' ");
		}
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME from TBL_BRH_INFO "+ sb.toString()+" order by BRH_ID ";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据上级菜单编号查询菜单信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getLevelMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select func_id,func_name from tbl_func_inf where func_parent_id = " + params[1].toString() + " and func_type <> '3'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据角色编号查找角色菜单信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRoleMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select value_id,func_name from tbl_role_func_map,tbl_func_inf where value_id = func_id and key_id = " + params[1].toString();
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据MCC查找相应计费代码
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMccFee(Object[] params) {
		
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select disc_cd from tbl_inf_mchnt_tp where mchnt_tp='" + params[1].toString() + "'";
		List dataList = commQueryDAO.findBySQLQuery(sql);
		String feeIds = (String)dataList.get(0);
		if(!StringUtil.isEmpty(feeIds)) {
		
			String feeId[] = feeIds.split(",");
			
			for(int i=0; i<feeId.length; i++) {
				
				String subSql = "select disc_cd, disc_cd || '-' || disc_nm as disc_name from tbl_inf_disc_cd where disc_cd='" + feeId[i] + "'";
				List subDataList = commQueryDAO.findBySQLQuery(subSql);
				Iterator<Object[]> iterator = subDataList.iterator();
				while(iterator.hasNext()) {
					Object[] obj = iterator.next();
					dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
				}
			}
		}
		
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBrhInfoBelow(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构操作员
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getOprBellow(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		String sql = "select opr_id,opr_id||'-'||opr_name as opr from tbl_opr_info where brh_id in " + operator.getBrhBelowId() + " order by opr_id";
		List dataList = commQueryDAO.findBySQLQuery(sql);
		
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}

		return dataMap;
	}
	

	/**
	 * 获得直联商户信息表 的所属平台机构代码 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getInstId(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		String sql = "select distinct trim(a.INST_ID) brh_id , trim(a.INST_ID) " +
				"from tbl_mcht_cup_inf a  order by brh_id";
		List dataList = commQueryDAO.findBySQLQuery(sql);
		
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}

		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构(全部)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBrhInfoBelowBranchShort(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

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
			sb.toString() +" order by BRH_LEVEL ,BRH_ID ";
//		String BRH_NAME = "通联金融";
//		String sql = "select CUP_BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE BRH_NAME = '"+ BRH_NAME +"' ";
//		sql += " ORDER BY BRH_LEVEL,BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构(全部)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBrhInfoBelowBranch(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

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
			sb.toString() +" order by BRH_LEVEL ,CREATE_NEW_NO ";
//		String BRH_NAME = "通联金融";
//		String sql = "select CUP_BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE BRH_NAME = '"+ BRH_NAME +"' ";
//		sql += " ORDER BY BRH_LEVEL,BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}

	/**
	 * 获得当前机构的下属机构(全部)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBrhInfoBelowBranchForNewNo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select CREATE_NEW_NO,CREATE_NEW_NO||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1','2') AND trim(BRH_ID) IN " + 
				sb.toString() +" order by BRH_LEVEL ,BRH_ID ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构(总分行-机构号)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBrhInfoHd(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1') AND trim(BRH_ID) IN " + 
			sb.toString();
		sql += " ORDER BY BRH_LEVEL,BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构(总分行-银联机构号)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupInfoBelowBranch(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select CUP_BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1') AND trim(BRH_ID) IN " + 
			sb.toString();
//		String BRH_NAME = "通联金融";
//		String sql = "select CUP_BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE BRH_NAME = '"+ BRH_NAME +"' ";
		sql += " ORDER BY BRH_LEVEL,BRH_ID DESC";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	/**
	 * 获得当前机构的下属机构(-)
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getBrhInfoBelowShowId(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		Operator operator = (Operator) params[0];

		String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE trim(BRH_ID) IN " + 
			operator.getBrhBelowId();
		sql += " ORDER BY BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得机具申请单号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermBacth(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		Operator operator = (Operator) params[0];

		String sql = "select app_id,app_id from tbl_term_management_app_main where stat <>'0' and trim(brh_id) IN " + 
			operator.getBrhBelowId();
		sql += " ORDER BY BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得已审核的机具申请单号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermBacthApp(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		Operator operator = (Operator) params[0];

		String sql = "select app_id,app_id from tbl_term_management_app_main where stat ='1' and trim(brh_id) IN " + 
			operator.getBrhBelowId();
		sql += " ORDER BY BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 当前机构的下属机构（不含支行）
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getBrhUp(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		Operator operator = (Operator) params[0];

		String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1') AND trim(BRH_ID) IN " + 
			operator.getBrhBelowId();
		sql += " ORDER BY BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	/**
	 * 根据机构级别获得角色信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRoleInfoByBrh(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select role_id,role_name from tbl_role_inf where role_type = " +
					 "(select brh_level from tbl_brh_info where BRH_ID = '" + params[1].toString().trim() + "')";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 直联商户费率1
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupFeeTp1(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select fee_id,fee_id||'-'||fee_nm from tbl_mcht_cup_fee where fee_type = '" +  params[1].toString().trim() + "'" + " order by fee_id";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 直联商户费率2
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupFeeTp2(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select fee_id,fee_id||'-'||fee_nm from tbl_mcht_cup_fee where fee_type = '" +  params[1].toString().trim() + "'" + " order by fee_id";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据商户组别获得商户编号(启用部分)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntTpByGrp(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select mchnt_tp,descr from tbl_inf_mchnt_tp where rec_st = '1' and mchnt_tp_grp = '" +  params[1].toString().trim() + "'" + " order by mchnt_tp";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据商户组别获得商户编号（全部）
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntTpByGrpAll(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select mchnt_tp,descr from tbl_inf_mchnt_tp where mchnt_tp_grp = '" +  params[1].toString().trim() + "'" + " order by mchnt_tp";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 直联商户用MCC
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntTpCup(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select mchnt_tp,descr from tbl_inf_mchnt_tp where rec_st = '1'" + " order by mchnt_tp";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	/**
	 * 查询商户分店列表
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchtBranInf(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select  a.MCHT_CD,a.BRANCH_CD,a.BRANCH_NM,a.rec_upd_ts from TBL_MCHT_BRAN_INF a order by a.rec_upd_ts";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 查询商户限额查询
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCstMchtFee(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select a.mcht_cd,a.txn_num, a.card_type, a.channel, a.day_num, a.day_amt, " +
				"a.day_single, a.mon_num, a.mon_amt from cst_mcht_fee_inf a";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	/**
	 * 查询真实表商户编号
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntNo(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF where BANK_NO in " +  operator.getBrhBelowId() 
			+ " and MCHT_STATUS = '0'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 查询model
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntModel(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select MODEL_ID, MODEL_ID ||' - '|| MODEL_NAME as MODEL_NAME from TBL_MODEL_INFO where  STATUS = '1'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 查询真实表直联商户编号
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupMchntNo(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from tbl_mcht_cup_info where MCHT_STATUS = '1' and " +
					 "crt_opr_id in (select opr_id from tbl_opr_info where brh_id in "+operator.getBrhBelowId()+") ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 查询临时表直联商户编号
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupMchntNoTmp(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from tbl_mcht_cup_info_tmp where MCHT_STATUS = '1' and " +
					 "crt_opr_id in (select opr_id from tbl_opr_info where brh_id in "+operator.getBrhBelowId()+") ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 得到商户组别信息
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntGrpInfo(Object[] params){
		Operator operator=(Operator)params[0];
		LinkedHashMap<String, String> dataMap=new LinkedHashMap<String, String>();
		String sql="select mchnt_tp_grp, descr from TBL_INF_MCHNT_TP_GRP WHERE REC_ST = '1' and FRN_IN = '0' ORDER BY MCHNT_TP_GRP";

		List<Object[]> dataList=commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator=dataList.iterator();
		while(iterator.hasNext()){
			Object[] obj=iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 风险模型类型
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getKind(Object[] params) {

		
		
		/*dataMapTmp.put("A00", "A00-频繁试卡");
		dataMapTmp.put("A01", "A01-频繁试卡");
		dataMapTmp.put("A02", "A02-频繁试卡");
		dataMapTmp.put("A03", "A03-大额交易");
		dataMapTmp.put("A04", "A04-大额交易");
		dataMapTmp.put("A05", "A05-频繁小额交易");
		dataMapTmp.put("A06", "A06-频繁小额交易");
		dataMapTmp.put("A07", "A07-频繁整数交易");
		dataMapTmp.put("A08", "A08-高失败率");
		dataMapTmp.put("A09", "A09-可疑交易");
		
		
		dataMapTmp.put("A10", "A10-信用卡高风险交易");
		dataMapTmp.put("A11", "A11-信用卡高风险交易");
		dataMapTmp.put("A12", "A12-信用卡高风险交易");
		dataMapTmp.put("A13", "A13-信用卡高风险交易");
		dataMapTmp.put("A14", "A14-信用卡高风险交易");
		dataMapTmp.put("A15", "A15-信用卡高风险交易");
		dataMapTmp.put("A16", "A16-信用卡高风险交易");
		dataMapTmp.put("A17", "A17-交易时间");
		dataMapTmp.put("A18", "A18-交易超限");
		dataMapTmp.put("A19", "A19-交易超限");
		dataMapTmp.put("A20", "A20-交易超限");
		dataMapTmp.put("A21", "A21");
		dataMapTmp.put("A22", "A22");
		dataMapTmp.put("A23", "A23");
		dataMapTmp.put("A24", "A24");
		dataMapTmp.put("A25", "A25");
		dataMapTmp.put("A26", "A26");
		dataMapTmp.put("B01", "B01");*/
		
		
		LinkedHashMap<String, String> dataMapTmp = new LinkedHashMap<String, String>();
		dataMapTmp.put("M01", "频繁试卡");
		dataMapTmp.put("M02", "大额交易");
		dataMapTmp.put("M03", "频繁小额交易");
		dataMapTmp.put("M04", "频繁整数交易");
		dataMapTmp.put("M05", "高失败率");
		dataMapTmp.put("M06", "可疑交易");
		dataMapTmp.put("M07", "信用卡高风险交易");
		dataMapTmp.put("M08", "交易时间");
		dataMapTmp.put("M09", "交易超限");
		dataMapTmp.put("M10", "移动终端疑似移机");
		dataMapTmp.put("M51", "事中频繁试卡");
		dataMapTmp.put("M52", "事中信用卡高风险");
		dataMapTmp.put("M53", "事中交易时间");
		dataMapTmp.put("M54", "事中大额交易");
		LinkedHashMap<String, String> dataMap=new LinkedHashMap<String, String>();
		String sql="select sa_model_kind , sa_model_group from tbl_risk_inf  ORDER BY sa_model_kind";

		List<Object[]> dataList=commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator=dataList.iterator();
		while(iterator.hasNext()){
			Object[] obj=iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim()+"-"+dataMapTmp.get(obj[1].toString().trim()));
		}
		return dataMap;
	}
	
	/**
	 * 风险模型组别
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getKindGrp(Object[] params) {

		
		LinkedHashMap<String, String> dataMapTmp = new LinkedHashMap<String, String>();
		dataMapTmp.put("M01", "M01-频繁试卡");
		dataMapTmp.put("M02", "M02-大额交易");
		dataMapTmp.put("M03", "M03-频繁小额交易");
		dataMapTmp.put("M04", "M04-频繁整数交易");
		dataMapTmp.put("M05", "M05-高失败率");
		dataMapTmp.put("M06", "M06-可疑交易");
		dataMapTmp.put("M07", "M07-信用卡高风险交易");
		dataMapTmp.put("M08", "M08-交易时间");
		dataMapTmp.put("M09", "M09-交易超限");
		dataMapTmp.put("M10", "M10-移动终端疑似移机");
		dataMapTmp.put("M11", "M11");
		dataMapTmp.put("M51", "M51-事中频繁试卡");
		dataMapTmp.put("M52", "M52-事中信用卡高风险");
		dataMapTmp.put("M53", "M53-事中交易时间");
		dataMapTmp.put("M54", "M54-事中大额交易");
		return dataMapTmp;
	}
	/**
	 * 移动国家码
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMoveTermMcc(Object[] params) {

		LinkedHashMap dataMapTmp = new LinkedHashMap();
		
		dataMapTmp.put("460", "中国");
		return dataMapTmp;
	}
	/**
	 * 移动网络码
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMoveTermMnc(Object[] params) {

		LinkedHashMap dataMapTmp = new LinkedHashMap();

		dataMapTmp.put("00", "移动TD");
		dataMapTmp.put("01", "联通GSM");
		dataMapTmp.put("02", "移动GSM");
		dataMapTmp.put("03", "电信CDMA");
		return dataMapTmp;
	}
	/**
	 * 获取终端厂商
	 * @param params
	 * @return
	 * 2011-6-8下午02:46:17
	 */
	public static LinkedHashMap<String, String> getManufacturer(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuffer sql = new StringBuffer("select key||'-'||value,value from CST_SYS_PARAM where owner = '")
			.append(TblTermManagementConstants.NAME).append("'");
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}

	/**
	 * 获取终端类型（出厂的终端类型）
	 * @param params
	 * @return
	 * 2011-6-8下午02:46:17
	 */
	public static LinkedHashMap<String, String> getTermType(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuffer sql = new StringBuffer("select key||'-'||value,value from CST_SYS_PARAM where 1<>1 ");
		if(params != null&& params.length>=2 && params[1] != null)
		{
			String manufacturer = params[1].toString();
			sql.append("or owner = '").append(manufacturer.substring(0,manufacturer.indexOf("-")).trim()).append("'");
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 获取终端库存编号
	 * @param params
	 * @return
	 * 2011-6-21下午03:14:37
	 */
	public static LinkedHashMap<String, String> getTermIdId(Object[] params){
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuffer sql = new StringBuffer("select term_no,product_cd from tbl_term_management where STATE ='4' ");
//		if(params.length<2 || params[1] == null)
//			return dataMap;
//		String[] tmp = params[1].toString().split(",");
//		String mechNo = tmp.length>=1?tmp[0]:"";
//		String terminalType = tmp.length>=2?tmp[1]:"";
//		String manufaturer = tmp.length>=3?tmp[2]:"";
//		if(mechNo!=null && !mechNo.equals(""))
//			sql.append("or MECH_NO = '").append(mechNo).append("' ");
//		if(terminalType!=null && !terminalType.equals(""))
//			sql.append("and TERMINAL_TYPE = '").append(terminalType).append("' ");
//		if(manufaturer!=null && !manufaturer.equals(""))
//			sql.append("and MANUFATURER = '").append(manufaturer).append("'");
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 查询POS交易类型
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	public static LinkedHashMap<String, String> getPosTxnNum(Object[] params) {
		return (LinkedHashMap<String, String>) TxnInfo.txnNameMap;
	}
	
	/**
	 * 查询商户地区代码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	public static LinkedHashMap<String, String> getCityCode(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();		
//			ICommQueryDAO commonQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAOCopy");
			List dates = null;
			String sql = "select MCHT_CITY_CODE,MCHT_CITY_CODE ||' - '|| CITY_NAME AS CITY_NAME From CST_CITY_CODE";
			dates = commQueryDAO.findBySQLQuery(sql);
			Iterator itor = dates.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		return hashMap;

	}

	/**
	 * 查询机构地区代码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	public static LinkedHashMap<String, String> getCupCityCode(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();	
			List dates = null;
			String sql = "select DISTINCT(CUP_CITY_CODE),CUP_CITY_CODE From CST_CITY_CODE";
			dates = commQueryDAO.findBySQLQuery(sql);
			Iterator itor = dates.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		return hashMap;

	}
	
	/**
	 * 根据商户编号查询该商户已经选择的交易权限码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	public static LinkedHashMap<String, String> getMchtRoleFuncByMerId(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		if(args.length>=2){
			//查找所有交易码
			String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";;
			List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);
			
			Long merId = Long.valueOf(args[1].toString().toString());			
			//查找选择商户所在 Mcc
			List<Object[]> roleIds = commQueryDAO.findBySQLQuery("select MCC,SIGN_INST_ID from TBL_MCHT_BASE_INF where MCHT_NO = "+ merId);
			String roleId = roleIds.get(0)[0].toString();	
			//相关二级商户具有的交易权限
			sql = "select VALUE_ID from TBL_MER_ROLE_FUNC where KEY_ID = " + roleId + " order by VALUE_ID";
			List<Object> funcRole = commQueryDAO.findBySQLQuery(sql);

			//该商户所在机构所拥有的权限集
			
			List<Object[]> rightFuncLIst = new ArrayList<Object[]>();
			//筛选权限信息
			for(int i = 0; i < funcAll.size(); i++) {
				Long funcId = Long.valueOf(funcAll.get(i)[0].toString().trim());
				for(int j = 0; j <funcRole.size(); j++ ) {
					Long valueId = Long.valueOf(String.valueOf(funcRole.get(j)));
					if(valueId.longValue() == funcId.longValue()) {
						Object[] funcInfo = new Object[2];
						funcInfo[0] = funcId;
						funcInfo[1] = funcAll.get(i)[1].toString();
						rightFuncLIst.add(funcInfo);
						funcAll.remove(i--);
						break;
					}
				}
				
			}
			
			Iterator itor = rightFuncLIst.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		}		
		return hashMap;

	}
	
	/**
	 * 根据商户类型编号查询商户组别已经选择的交易权限码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	public static LinkedHashMap<String, String> getMerRoleFunc(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		if(args.length>=2){
			Long roleId = Long.valueOf(args[1].toString());
			String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";;
			List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);
			sql = "select VALUE_ID from TBL_MER_ROLE_FUNC where KEY_ID = " + roleId + " order by VALUE_ID";
//			System.out.println(sql);
			List<Object> funcRole = commQueryDAO.findBySQLQuery(sql);
			//右侧权限集
			List<Object[]> rightFuncLIst = new ArrayList<Object[]>();
			//筛选权限信息
			for(int i = 0; i < funcAll.size(); i++) {				
//				Long funcId = Long.valueOf(funcAll.get(i)[0].toString());
				int funcId = Integer.parseInt(funcAll.get(i)[0].toString().trim());
				for(int j = 0; j <funcRole.size(); j++ ) {
//					Long valueId = Long.valueOf(String.valueOf(funcRole.get(j)));
					int valueId = Integer.parseInt(String.valueOf(funcRole.get(j)).trim());
//					if(valueId.longValue() == funcId.longValue()) {
					if(valueId == funcId) {
						Object[] funcInfo = new Object[2];
						funcInfo[0] = funcId;
						funcInfo[1] = funcAll.get(i)[1].toString();
						rightFuncLIst.add(funcInfo);
						funcAll.remove(i--);
						break;
					}
				}
				
			}
			
			Iterator itor = rightFuncLIst.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		}		
		return hashMap;

	}
	/**
	 * 根据商户类型编号查询商户组别已经选择的权限码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	public static LinkedHashMap<String, String> getMerMerFuncAll(Object[] args) {
//		String sql = "select FUNC_ID,FUNC_NAME from TBL_MER_FUNC_INF where FUNC_TYPE = '0' order by FUNC_ID";
		String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";
		List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);		
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		Iterator itor = funcAll.iterator();
		Object[] obj;
		String docType, descTx;
		while (itor.hasNext()) {
			obj = (Object[]) itor.next();
			docType = obj[0].toString().trim();
			descTx = obj[1].toString().trim();
			hashMap.put(docType, descTx);
		}
		return hashMap;

	}
	
	/**
	 * 获得商户资质等级
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getMchtLvl(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("1", "小商户");
		hashMap.put("2", "普通商户");
		hashMap.put("3", "大商户");
		hashMap.put("9", "未定级");
		hashMap.put("", "未定级");
		return hashMap;
	}
	
	/**
	 * 获得人员属性
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getMemproperty(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("1", "1-钱宝");
		hashMap.put("5", "5-代理");
		hashMap.put("6", "6-兼职");
		return hashMap;
	}
	
	
	/**
	 * 获得职务类别
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getJobtype(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "0-管理");
		hashMap.put("1", "1-市场");
		hashMap.put("2", "2-维护");
		hashMap.put("5", "5-其他");
		return hashMap;
	}
	
	
	/**
	 * getYesOrNotSelect
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getYesOrNotSelect(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "是");
		hashMap.put("1", "否");
		return hashMap;
	}
	
	/**
	 * getAOrPSelect
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getAOrPSelect(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "A");
		hashMap.put("1", "P");
		return hashMap;
	}
	
	/**
	 * getTxnNum
	 * 回佣类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getDiscAlgoFlag(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "按比率");
		hashMap.put("1", "按金额");
		return hashMap;
	}
	
	/**
	 * getTxnNum
	 * 交易类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getTxnNum(Object[] args) {
//		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
//		hashMap.put("1101", "信用卡消费");
//		hashMap.put("1091", "信用卡预授权完成");
//		hashMap.put("1701", "T+0收款");
//		return hashMap;
		LinkedHashMap<String, String> hashMap = new 

		LinkedHashMap<String, String>();
				List dates = null;
				String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";;
				dates = commQueryDAO.findBySQLQuery(sql);
				Iterator itor = dates.iterator();
				Object[] obj;
				String docType, descTx;
				while (itor.hasNext()) {
					obj = (Object[]) itor.next();
					docType = obj[0].toString().trim();
					descTx = obj[1].toString().trim();
					hashMap.put(docType, descTx);
				}
				return hashMap;

	}
	/**
	 * getCardType
	 * 卡类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getCardType(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
//		hashMap.put("01", "借记卡");
//		hashMap.put("00", "贷记卡");
//		hashMap.put("11", "本行IC借记卡");
//		hashMap.put("10", "本行IC贷记卡");
//		hashMap.put("12", "移动卡");
		hashMap.put("00", "借记卡");
		hashMap.put("01", "贷记卡");
		hashMap.put("02", "准贷记卡");
		hashMap.put("03", "其他");
		hashMap.put("04", "境外卡");
		return hashMap;
	}
	/**
	 * getCardType
	 * 卡类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getCardTypeTl(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
//		hashMap.put("01", "借记卡");
//		hashMap.put("00", "贷记卡");
//		hashMap.put("11", "本行IC借记卡");
//		hashMap.put("10", "本行IC贷记卡");
//		hashMap.put("12", "移动卡");
		hashMap.put("00", "借记卡");
		hashMap.put("01", "贷记卡");
		hashMap.put("02", "准贷记卡");
		hashMap.put("03", "预付费卡");
		//20160113 del by guoyu 暂时不再使用公务卡卡种了
//		hashMap.put("04", "公务卡");
		return hashMap;
	}
	
	/**
	 * getMchntGrpCup
	 * 直联商户组别
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getMchntGrpCup(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("00", "00-综合零售");
		hashMap.put("01", "01-专门零售");
		hashMap.put("02", "02-批发类");
		hashMap.put("03", "03-住宅餐饮");
		hashMap.put("04", "04-房地产业");
		hashMap.put("05", "05-房地产业*");
		hashMap.put("06", "06-金融业");
		hashMap.put("07", "07-政府服务");
		hashMap.put("08", "08-教育");
		hashMap.put("09", "09-卫生");
		hashMap.put("10", "10-公共事业");
		hashMap.put("11", "11-居民服务");
		hashMap.put("12", "12-商业服务");
		hashMap.put("13", "13-交通物流");
		hashMap.put("14", "14-直销类商户");
		hashMap.put("15", "15-租赁服务");
		hashMap.put("16", "16-维修服务");
		hashMap.put("17", "17-其他");
		return hashMap;
	}
	
	/**
	 * getMchntFeeAct
	 * 直联商户费率
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getMchntFeeAct(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("NK001", "NK001-全国南卡北用决定索引");
		hashMap.put("TP001", "TP001-全国通配决定索引");
		hashMap.put("AH001", "AH001-商户计费确定det");
		hashMap.put("AH002", "AH002-借记卡手续费");
		hashMap.put("AH003", "AH003-贷记卡手续费");
		hashMap.put("AH004", "AH004-准贷记卡手续费");
		hashMap.put("AH007", "AH007-通用借记卡");
		hashMap.put("AH011", "AH011-通用借记卡(以此为准)");
		hashMap.put("AH012", "AH012-通用贷记卡(以此为准)");
		hashMap.put("AH016", "AH016-南北准贷记(以此为准)");
		hashMap.put("AH444", "AH444-手续费默认通配条件");
		hashMap.put("AH447", "AH447-手续费默认同辈条件");
		hashMap.put("AH901", "AH901-南卡北用");
		return hashMap;
	}
	
	/**
	 * getMchntTypeCup
	 * 直联商户状态
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getMchntTypeCup(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("9", "新增待审核");
		hashMap.put("8", "新增拒绝");
		hashMap.put("7", "修改待审核");
		hashMap.put("6", "修改拒绝");
		hashMap.put("5", "冻结待审核");
		hashMap.put("3", "注销待审核");
		hashMap.put("H", "恢复待审核");
		hashMap.put("0", "注销");
		hashMap.put("1", "正常");
		hashMap.put("2", "冻结");
		return hashMap;
	}
	
	/**
	 * getCallType
	 * 直联终端拨入类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getCallType(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("D00", "有线拨号");
		hashMap.put("M01", "GPRS方式");
		hashMap.put("M02", "noter方式");
		hashMap.put("M03", "cdpd方式");
		hashMap.put("M04", "CDMA方式");
		hashMap.put("M05", "其他方式");
		return hashMap;
	}
	
	public static LinkedHashMap<String, String> getTermCupKeyInde(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("1", "密钥一");
		hashMap.put("2", "密钥二");
		hashMap.put("3", "密钥三");
		hashMap.put("4", "一机一密DES");
		hashMap.put("5", "一机一密3DES");
		return hashMap;
	}
	
	public static LinkedHashMap<String, String> getTermKeyMode(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "多机一密");
		hashMap.put("1", "一机一密");
		hashMap.put("2", "一商一密");
		hashMap.put("3", "一区一密");
		hashMap.put("4", "自定义");
		return hashMap;
	}
	
	/**
	 * getTermStateCup
	 * 直联终端状态
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getTermStateCup(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("9", "新增待审核");
		hashMap.put("8", "新增拒绝");
		hashMap.put("7", "修改待审核");
		hashMap.put("6", "修改拒绝");
		hashMap.put("5", "冻结待审核");
		hashMap.put("3", "注销待审核");
		hashMap.put("H", "恢复待审核");
		hashMap.put("0", "注销");
		hashMap.put("1", "正常");
		hashMap.put("2", "冻结");
		return hashMap;
	}
	
	/**
	 * getTermTypeCup
	 * 直联终端类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getTermTypeCup(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("P0", "无线POS");
		hashMap.put("P1", "有线POS");
		hashMap.put("PA", "自助终端");
		hashMap.put("PI", "IC卡终端");
		hashMap.put("PM", "MIS");
		hashMap.put("PT", "电话支付终端");
		return hashMap;
	}
	
	/**
	 * getAppReason
	 * 套用商户类型原因 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getAppReason(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("00", "真实公益类");
		hashMap.put("01", "入网优惠期套用");
		hashMap.put("02", "公共事业类商户");
		hashMap.put("03", "批发市场类商户");
		hashMap.put("04", "资金归集类商户");
		hashMap.put("05", "历史遗留类套用");
		return hashMap;
	}
	
	/**
	 * getStoreFlag
	 * 连锁店标识
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getStoreFlag(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "非连锁店");
		hashMap.put("1", "根总店");
		hashMap.put("2", "分店");
		return hashMap;
	}
	
	/**
	 * getMccMd
	 * MCC套用依据
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getMccMd(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("00", "私自套用");
		hashMap.put("01", "经价格小组通过");
		hashMap.put("02", "当地协商");
		hashMap.put("03", "人民银行/银监会发文");
		hashMap.put("04", "同业公会发文");
		hashMap.put("05", "其他组织发文");
		hashMap.put("06", "其他");
		return hashMap;
	}
	
	/**
	 * getCertCup
	 * 直联商户证件类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getCertCup(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("99", "其他证件");
		hashMap.put("01", "身份证");
		hashMap.put("02", "军官证");
		hashMap.put("03", "护照");
		hashMap.put("04", "港澳居民来往内地通行证（回乡证）");
		hashMap.put("05", "台湾同胞来往内地通行证（台胞证）");
		hashMap.put("06", "警官证");
		hashMap.put("07", "士兵证");
		hashMap.put("08", "户口薄");
		hashMap.put("09", "临时身份证");
		hashMap.put("10", "外国人居留证");
		return hashMap;
	}
	
	/**
	 * getMchntCupFate
	 * 直联商户分润算法
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getMchntCupFate(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0000", "0000 -扣率相关，发卡获得扣率的0，银联获得扣率的0");
		hashMap.put("AN001", "AN001-餐娱类；发卡0.9%，银联0.13%");
		hashMap.put("AN002", "AN002-房车类；发卡0.9%单笔最高60元，银联0.13%单笔最高10元");
		hashMap.put("AN003", "AN003-批发类；发卡0.55%单笔最高20元，银联0.08%单笔最高2.5元");
		hashMap.put("AN004", "AN004-一般类；发卡0.55%，银联0.08%");
		hashMap.put("AN005", "AN005-民生类；发卡0.26%，银联0.04%");
		hashMap.put("AX100", "AX100-消费类境内宾馆娱乐类，发卡1.4%，银联0.2%");
		hashMap.put("AX210", "AX210-房地产汽车典当拍卖类；发卡0.7%单笔最高40元，银联0.1%单笔最高5元");
		hashMap.put("AX230", "AX230-普通批发类；发卡0.7%单笔最高16元，银联0.1%单笔最高2元");
		hashMap.put("AX240", "AX240-大型企业批发，县乡优惠批发类；发卡0.35%单笔最高8元，银联0.05%单笔最高1元");
		hashMap.put("AX250", "AX250-烟草配送类；发卡0.35%单笔最高4元，银联0.05%单笔最高0.5元");
		hashMap.put("AX270", "AX270-县乡优惠房产汽车类；发卡0.35%单笔最高20元，银联0.05%单笔最高2.5元");
		hashMap.put("AX280", "AX280-县乡优惠三农商户类；发卡0.21%单笔最高2.1元，银联0.03%单笔最高0.3元");
		hashMap.put("AX300", "AX300-航空售票加油超市类；发卡0.35%，银联0.05%");
		hashMap.put("AX310", "AX310-县乡优惠超市加油类；发卡0.175%，银联0.025%");
		hashMap.put("AX500", "AX500-保险刷卡类；发卡0.21%，银联0.03%");
		hashMap.put("AX510", "AX510-保险批量代扣类；发卡1.4元/笔，银联0.2元/笔");
		hashMap.put("AX520", "AX520-公共事业类；发卡0.21元/笔，银联0.03元/笔");
		hashMap.put("AX600", "AX600-信用卡还款；发卡1.5元，银联0.3元");
		hashMap.put("AX900", "AX900-一般类，县乡优惠宾馆娱乐消费类；发卡0.7%，银联0.1%");
		hashMap.put("CI101", "CI101-发卡方收取商户回佣70%，银联10%");
		hashMap.put("CI122", "CI122-发卡40%，银联20%");
		hashMap.put("CI206", "CI206-发卡40%，银联20%");
		hashMap.put("CI500", "CI500-批量代收(除公缴)，单笔金额小于1000元发卡0.5元银联0.2元，1000-5000元发卡1元银联0.2元,超过5000元发卡1.5元银联0.2元");
		return hashMap;
	}
	
	/**
	 * getReasonCode
	 * 登记原因码
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getReasonCode(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("9790", "特殊结算方式");
		hashMap.put("9791", "大型批发商店资金归集业务");
		hashMap.put("9792", "缴费类业务");
		hashMap.put("9793", "历史遗留低扣率商户套用");
		hashMap.put("9794", "分公司自定义套用原因码1");
		hashMap.put("9795", "分公司自定义套用原因码2");
		hashMap.put("9796", "分公司自定义套用原因码3");
		hashMap.put("9797", "分公司自定义套用原因码4");
		hashMap.put("9798", "分公司自定义套用原因码5");
		hashMap.put("9799", "真实商户登记");
		return hashMap;
	}
	
	/**
	 * getCardTypeIn
	 * 本行卡类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getCardTypeIn(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
//		hashMap.put("00", "国内他行卡");
//		hashMap.put("01", "本行借记卡");
//		hashMap.put("02", "本行准贷记卡");
//		hashMap.put("03", "本行人民币贷记卡");
//		hashMap.put("04", "本行双币种贷记卡");
//		hashMap.put("05", "本行深发信用卡");
//		hashMap.put("51", "AMEX");
//		hashMap.put("52", "DC");
//		hashMap.put("53", "JCB");
//		hashMap.put("54", "VISA");
//		hashMap.put("55", "MASTER");
		hashMap.put("01", "本行借记卡");
		hashMap.put("02", "本行贷记卡");
//		hashMap.put("03", "他行借记卡");
//		hashMap.put("04", "他行贷记卡");
//		hashMap.put("05", "他行准贷记卡");
//		hashMap.put("06", "他行其他");
		return hashMap;
	}
	/**
	 * getChannel
	 * 交易渠道CHANNEL
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getChannel(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "间联POS");
		hashMap.put("1", "否");		
		return hashMap;
	}
	
	/**
	 * 获得币种信息
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getCurType(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "人民币");
		hashMap.put("1", "港币");
		hashMap.put("2", "美元");
		hashMap.put("3", "日元");
		hashMap.put("4", "马克");
		hashMap.put("5", "英镑");
		hashMap.put("6", "法郎");
		hashMap.put("7", "台币");
		return hashMap;
	}
	
	/**
	 * 授权管理的一级菜单获取
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAuthorMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		List<Object[]> list = null;
		
		String sql = "select FUNC_ID,FUNC_NAME from tbl_func_inf where func_id in " +
				"(select distinct func_parent_id from TBL_FUNC_INF where func_id in " +
				"(select distinct func_parent_id from TBL_FUNC_INF where trim(misc1) in ('1','2')))";
		
		list = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	/**
	 * 根据上级菜单编号查询菜单信息(授权管理)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAuthorNextMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		if (params.length == 1) {
			return dataMap;
		}
		
		String sql = "select FUNC_TYPE from tbl_func_inf where func_id = '" + params[1].toString().trim() + "'";
		List type = commQueryDAO.findBySQLQuery(sql);
		if (null != type && !type.isEmpty()) {
			if (!StringUtil.isNull(type.get(0))) {
				if ("1".equals(type.get(0).toString())) {
					sql = "select func_id,func_name from tbl_func_inf where func_parent_id = '" 
						+ params[1].toString().trim() 
						+ "' and func_id in (select distinct func_parent_id from TBL_FUNC_INF where trim(misc1) in ('1','2')) ";
				} else {
					sql = "select func_id,func_name from tbl_func_inf where func_parent_id = '" 
						+ params[1].toString().trim() 
						+ "' and trim(misc1) in ('1','2')";
				}
			}
			List<Object[]> list = commQueryDAO.findBySQLQuery(sql);
			Iterator<Object[]> iterator = list.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
			}
		}
		return dataMap;
	}
	
	/**
	 * 授权管理的已列为需授权的交易获取
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAuthorisedMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		List<Object[]> list = null;
		
		String sql = "select FUNC_ID,FUNC_NAME from tbl_func_inf where trim(misc1) = '1' and FUNC_TYPE = '0'";
		
		list = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	/**
	 * 当前机构和下属机构的32域
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupBrhInfoBelow(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select CUP_BRH_ID, CUP_BRH_ID ||' - '|| BRH_NAME from TBL_BRH_INFO where BRH_LEVEL IN ('0','1','2') AND BRH_ID in " +  operator.getBrhBelowId();
		sql += " ORDER BY BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			if(obj[0]!=null){
				dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
				
			}
		}
//		dataMap.put("00001", "通联金融");
		return dataMap;
	}
	
	/**
	 * 直联商户用银联机构码
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupBrhS(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select CUP_BRH_ID,CUP_BRH_ID ||' - '|| BRH_NAME from TBL_BRH_INFO where brh_level in('0','1') and BRH_ID in " +  operator.getBrhBelowId();
		sql += " ORDER BY BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put("08" + obj[0].toString().trim(), "08" + obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 *  终端活动编号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getActNo(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select ACT_NO, ACT_NO ||' - '|| ACT_NAME from TBL_MARKET_ACT where STATE = '0' and BANK_NO in " +  operator.getBrhBelowId();
		sql += " ORDER BY ACT_NO";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 获取专业服务机构
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getOragn(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select ORG_ID,ORG_ID||'-'||ORG_NAME from TBL_PROFESSIONAL_ORGAN where BRANCH in " +  operator.getBrhBelowId();
		sql += " ORDER BY ORG_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * EPOS版本号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String>getEposVersion(Object[] params) {
		Operator operator = (Operator) params[0];
		String brhId =  params[1].toString();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select distinct t1.VER_ID,t1.VER_ID||'-'||t1.MISC from TBL_VER_MNG t1 left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id  where t2.BRH_ID in " +  operator.getBrhBelowId();
		if(brhId != null && !brhId.equals(""))
			sql += "and t2.brh_id ='"+brhId+"'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * EPOS版本号通过银联编号查询
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String>getEposVer(Object[] params) {
		Operator operator = (Operator) params[0];
		String brhId =  params[1].toString();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select distinct t1.VER_ID,t1.VER_ID||'-'||t1.MISC from TBL_VER_MNG t1 left join tbl_brh_info t2 on t1.bank_id = t2.cup_brh_id  where t2.BRH_ID in " +  operator.getBrhBelowId();
		if(brhId != null && !brhId.equals(""))
			sql += "and t2.cup_brh_id ='"+brhId+"'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	/**
	 * EPOS交易提示信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String>getPptMsg(Object[] params) {
		Operator operator = (Operator) params[0];
		String verId =  params[1].toString();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select distinct t1.ppt_id,t1.ppt_id||'-'||t1.ppt_msg1 from TBL_PPT_MSG t1 where usage_key = '0' and msg_type = '1' ";
		if(verId != null && !verId.equals(""))
			sql += "and t1.ver_id ='"+verId+"'";
		
		sql += " order by t1.ppt_id ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("0", "0-默认");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 由地区码表凑出银联机构号和对应的地址信息
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupBrh(Object[] params) {
		String brhId = "";
		if(params.length > 0 && params[0] instanceof String)
			brhId = params[0].toString();
		
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
//		String sql = "select '0440' || cup_city_code as v1, '0440' || cup_city_code ||' - '|| city_name as v2 from cst_city_code ";
//		sql += " ORDER BY cup_city_code";
		String sql = "select '"+ brhId +"' || cup_city_code as v1, '"+ brhId +"' || cup_city_code ||' - '|| city_name as v2 from cst_city_code ";
		sql += " ORDER BY cup_city_code";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * mchtNo下拉菜单返回的信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchtData(Object[] params) {
		String mchtNo = "";
		if(params.length > 0 && params[0] instanceof String)
			mchtNo = params[0].toString();;
		
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
//		String sql = "select mcht_nm from tbl_mcht_base_inf where mcht_no = '"+mchtNo+"'";
		String sql = "select a.mcht_nm,a.mcc,c.disc_id||' - '||c.fee_value from tbl_mcht_base_inf a,tbl_mcht_settle_inf b,tbl_his_disc_algo c" +
				" where a.mcht_no=b.mcht_no and b.fee_rate=c.disc_id and c.txn_num='0000' and a.mcht_no='"+mchtNo+"'";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
//		System.out.println(dataList.toString());
		
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put("mchtNm",obj[0].toString().trim());
			dataMap.put("mchtMCC",obj[1].toString().trim());
			dataMap.put("mchtFeeRate",obj[2].toString().trim());
		}
//		System.out.println(dataMap);
		return dataMap;
	}
	
	
	/**
	 * mchtNo下拉菜单返回的信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getFirMchtData(Object[] params) {
		String firMchtNo = "";
		if(params.length > 0 && params[0] instanceof String)
			firMchtNo = params[0].toString();;
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

		String sql = "select first_mcht_nm,first_term_id,mcht_tp,fee_value||' - '||reserved from tbl_first_mcht_inf where first_mcht_cd = '"+firMchtNo+"'";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put("firMchtNm",obj[0].toString().trim());
			dataMap.put("firstTermNomber",obj[1].toString().trim());
			dataMap.put("firstMccNo",obj[2].toString().trim());
			dataMap.put("firstMchtFeeRate",obj[3].toString().trim());
		}
//		System.out.println(dataMap);
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTxnTypes(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
//		Operator operator = (Operator) params[0];
		String sql = "SELECT txn_num,txn_name FROM TBL_TXN_NAME" ;
//				" where txn_num in" +
//				"('1011','1091','1101','1111','1121','1141','2011','2091','2101','2111','2121','3011','3091','3101','3111','3121','4011','4091','4101','4111','4121','5151','5161')";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
//		dataMap.put("", "全部");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	public static LinkedHashMap<String, String> getTxnStatus(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0", "请求中");
		dataMap.put("1", "成功");
		dataMap.put("R", "已退货");
		dataMap.put("A", "失败");
		return dataMap;
	}
	
	/**
	 * 查找表TBL_MCHT_BASE_INF_TMP_TMP中商户名称
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchtNm(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT MCHT_NM,MCHT_NM as DESCR FROM TBL_MCHT_BASE_INF_TMP_TMP" ;
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//下属商户
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntTmpBelow(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];
		String sql = "SELECT mcht_no,MCHT_NM FROM TBL_MCHT_BASE_INF_TMP_TMP " +
				" where  BANK_NO in " +  operator.getBrhBelowId() ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//路由交易类型
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteTxnTp(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT txn_num,txn_name FROM TBL_TXN_NAME" ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//路由渠道编号
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteBrhId(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT BRH_ID,BRH_ID||'-'||BRH_NAME as BRH_NAME FROM TBL_BRH_INFO order by BRH_ID" ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//路由商户编号
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteMchtNo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select mcht_no,mcht_no||' - '||mcht_nm from tbl_mcht_base_inf" ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//路由商户组别
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteMchtGrp(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		//String sql = "SELECT MCHNT_TP_GRP,MCHNT_TP_GRP ||' - '||DESCR as DESCR FROM TBL_INF_MCHNT_TP_GRP" ;
		String sql = "select t.mcht_gid,t.mcht_gid||'-'||t.mcht_gname group_name from tbl_route_mchtg t" ;
		
		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 路由商户mcc
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteMchntTpByGrp(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select mchnt_tp,mchnt_tp||' - '||substr(descr,6) from tbl_inf_mchnt_tp where rec_st = '1' and mchnt_tp_grp = '" +  params[1].toString().trim() + "'" + " order by mchnt_tp";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}

	//路由发卡银行
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteBankCd(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
//		String sql = "SELECT distinct ACQ_INST_ID,trim(ACQ_INST_ID) ||' - '||RESERVED2 as DESCR FROM TBL_FIRST_MCHT_INF" ;
		String sql = "SELECT distinct BANK_ID,trim(BANK_ID) ||' - '||trim(BANK_NAME) as DESCR FROM TBL_INST_BANK_INF " ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//路由地区
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteMchtArea(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select MCHT_CITY_CODE,MCHT_CITY_CODE ||' - '|| CITY_NAME AS CITY_NAME From CST_CITY_CODE" ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//路由详细mcc
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteRuleMccDtl(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select mchnt_tp,mchnt_tp||' - '||substr(descr,6) from tbl_inf_mchnt_tp " ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	public static LinkedHashMap<String, String> getDsfMchtNo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select distinct(t.mcht_no),t.mcht_no  from TBL_MCHT_INFO t order by t.mcht_no" ;
		
		List<Object[]> dataList = commDFQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("", "全部");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	public static LinkedHashMap<String, String> getDsfMchtNoForAdd(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select distinct(t.mcht_no),t.mcht_no  from TBL_MCHT_INFO t order by t.mcht_no" ;
		
		List<Object[]> dataList = commDFQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	public static LinkedHashMap<String, String> getDsfParamType(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "技术参数");
		hashMap.put("1", "业务参数");
		return hashMap;
	}
	public static LinkedHashMap<String, String> getDsfParamPro(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "可查看不可修改");
		hashMap.put("1", "可查看可修改");
		hashMap.put("2", "不可查看");
		return hashMap;
	}
	public static LinkedHashMap<String,String> getDsfMchtInfoStat(Object[] args){
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0","正常");
		hashMap.put("1","商户新增待审核");
		hashMap.put("2","商户新增退回");
		hashMap.put("3","商户新增退回修改待审核");
		hashMap.put("4","商户变更待审核");
		hashMap.put("5","商户变更退回");
		hashMap.put("6","商户停用待审核");
		hashMap.put("7","商户恢复待审核");
		hashMap.put("8","商户停用状态");
		hashMap.put("9","注销");
		return hashMap;
	}
	public static LinkedHashMap<String,String> getDsfMchtinfoFlag(Object[] args){
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("00","不开通代付业务");
		hashMap.put("01","不开通代收业务");
		hashMap.put("02","不需要替换户名");
		hashMap.put("03","不开通单笔代收付业务");
		hashMap.put("04","不开通批量代收付业务");
		hashMap.put("10","开通代付业务");
		hashMap.put("11","开通代收业务");
		hashMap.put("12","需要替换户名");
		hashMap.put("13","开通单笔代收付业务");
		hashMap.put("14","开通批量代收付业务");
		return hashMap;
	}

	public static LinkedHashMap<String, String> getDsfMchtinfoRisllvl(
			Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "低风险");
		hashMap.put("1", "中风险");
		hashMap.put("2", "高风险");
		return hashMap;
	}

	public static LinkedHashMap<String, String> getDsfMchtinfoMchtlvl(
			Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "基本商户");

		return hashMap;
	}

	// 文件获取方式
	public static LinkedHashMap<String, String> getDsfGetFileWay(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "DSF系统主动获取文件");
		hashMap.put("1", "DSF系统被动获取文件");
		return hashMap;
	}

	// 通讯方式
	public static LinkedHashMap<String, String> getDsfCommWay(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "FTP");
		hashMap.put("1", "SFTP");
		return hashMap;
	}

	// 文件验证方式
	public static LinkedHashMap<String, String> getDsfCheckType(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "无需验证");
		hashMap.put("1", "MD5+RSA");
		return hashMap;
	}

	// 回执发送方式
	public static LinkedHashMap<String, String> getDsfRspSendFlag(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "手工发送");
		hashMap.put("1", "自动发送");
		hashMap.put("2", "手工+自动");
		return hashMap;
	}

	
	
	
	//风控级别
		@SuppressWarnings("unchecked")
		public static LinkedHashMap<String, String> getRiskLvl(Object[] params) {
			LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
			StringBuffer andSql = new StringBuffer(" where 1=1 ");
			if(params.length>1&&params[1]!=null&&!"".equals(params[1].toString())){
				andSql.append(" and RISK_ID ='"+params[1].toString().trim()+"'");
			}
			String sql = "select RISK_LVL,RISK_LVL||'-'||RESVED from TBL_RISK_LVL " +andSql+"order by RISK_LVL";
			
			List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
			Iterator<Object[]> iterator = dataList.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
			}
			return dataMap;
		}

	
	/**
	 * 当前机构的下属机构（不含支行）by xupengfei
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getMchtGroupNew(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		Operator operator = (Operator) params[0];
		
//		select mcht_no,mcht_no||' - '||mcht_nm from tbl_mcht_base_inf a left outer join TBL_BRH_INFO b on(a.bank_no = b.brh_id) 
//        where (b.UP_BRH_ID = '00001' or b.brh_id = '00001') and MCHT_GROUP_FLAG = '2' and MCHT_STATUS = '0' ORDER BY mcht_no

		String sql = "select mcht_no,mcht_no||' - '||mcht_nm from tbl_mcht_base_inf WHERE MCHT_GROUP_FLAG = '2' and MCHT_STATUS = '0' AND trim(bank_no) IN " + 
			operator.getBrhBelowId();
		sql += " ORDER BY mcht_no";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获取下属商户 by xupengfei
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntBelow(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];
		StringBuffer andSql = new StringBuffer(" ");
		if(params.length>1&&params[1]!=null&&!"".equals(params[1].toString())){
			String brh = params[1].toString().trim();
			String selectBrh = CommonFunction.getBelowBrhInfo(CommonFunction.getBelowBrhMap2(brh));
			andSql.append(" and bank_no in "+selectBrh);
		}
		
		String sql = "SELECT mcht_no,MCHT_NM FROM TBL_MCHT_BASE_INF " +
				" where bank_no in  "+operator.getBrhBelowId()+andSql;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//获取商户正式表全部商户号商户名 by xupengfei
		public static LinkedHashMap<String, String> getMchntNoAll() {
			LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
			String sql = "select MCHT_NO, MCHT_NM from TBL_MCHT_BASE_INF  ";
			List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
			Iterator<Object[]> iterator = dataList.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
			}
			return dataMap;
		}
		
		//根据商户号获取机构号及机构名称 by xupengfei
		public static LinkedHashMap<String, String> getBrhIdNm() {
			LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
			String sql = "SELECT A.MCHT_NO, B.BRH_ID||'-'||B.BRH_NAME AS BRH_NAME FROM TBL_MCHT_BASE_INF A LEFT OUTER JOIN TBL_BRH_INFO B ON(A.BANK_NO = B.BRH_ID)";
			List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
			Iterator<Object[]> iterator = dataList.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
			}
			return dataMap;
		}
		
	
	
	

	//风控级别
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRunRiskLvl(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT risk_lvl,risk_lvl||'-'||RESVED as risk_name FROM TBL_RISK_LVL order by risk_lvl" ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		dataMap.put("*", "*-无限制");
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 对账标志
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String,String> getStlmFlag(Object[] args){
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("1","POSP有，CUPS没有");               
		hashMap.put("2","POSP无，CUPS有");                 
		hashMap.put("3","POSP与CUPS金额不一致");           
		hashMap.put("4","POSP有，HOST没有");               
		hashMap.put("5","POSP无，HOST有");                 
		hashMap.put("6","POSP与HOST金额不一致");           
		hashMap.put("7","CUPS可疑交易");                   
		hashMap.put("8","导入未核对交易");                 
		hashMap.put("9","银联冲正及原交易交易标志");       
		hashMap.put("D","清理数据标志");                   
		hashMap.put("B","老系统交易");                     
		hashMap.put("C","T+0入账成功");                    
		hashMap.put("E","T+0入账失败");                    
		hashMap.put("F","T+0未入帐");                      
		hashMap.put("G","T+0入账成功(可疑交易)");          
		hashMap.put("H","T+0入账失败(可疑交易)");          
		hashMap.put("I","T+0未入帐(可疑交易)");            
		hashMap.put("J","T+0入账成功(可疑交易对平)");      
		hashMap.put("K","T+0入账失败(可疑交易对平)");      
		hashMap.put("L","T+0未入账(可疑交易对平)");        
		hashMap.put("Z","脱机交易中标志两者金额不同");     
		hashMap.put("W","脱机交易中标志是本地端有的");     
		hashMap.put("X","脱机交易中标志是银联端有的");     
		hashMap.put("0","对账结果平");                     
		hashMap.put("A","CUPS可疑交易对平");               
		hashMap.put("B","老的银联数据");                   

		return hashMap;
	}
	
	
	/**
	 * getCardType
	 * 手续费改---卡类型
	 * by ctz 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getFeeCardType(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("00", "借记卡");
		hashMap.put("01", "贷记卡");
		hashMap.put("02", "准贷记卡");
		hashMap.put("03", "预付费卡");
		hashMap.put("04", "公务卡");
		hashMap.put("99", "所有卡种");
		return hashMap;
	}
	
	/**
	 * getCardType
	 * 手续费改---交易类型
	 * by ctz 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getFeeTxnNum(Object[] args) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0000", "所有交易");
		String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where TXN_NUM in('1101','1091') order by TXN_NUM" ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
		
	}
	
	public static LinkedHashMap<String, String> getSettleFlag(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0", "未结算");
		dataMap.put("2", "已结算");
		dataMap.put("4", "暂缓结算");
		return dataMap;
	}
	

	/**
	 * getGrpIdName
	 * 批量处理管理-组Id
	 * by ctz 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getGrpIdName(Object[] args) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select GRPID,GRPID||'-'||GRPNAME from TBL_BAT_GRP_INFO  order by GRPID" ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
		
	}
	
	/**
	 * getGrpIdName
	 * 批量处理管理-组Id
	 * by ctz 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getGrpIdNameNew(Object[] args) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select GRPID,GRPID||'-'||GRPNAME from TBL_BAT_GRP_INFO_T0  order by GRPID" ;
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),  obj[1].toString().trim());
		}
		return dataMap;
		
	}
	
	
	
	

	/**
	 * getPayType
	 * 代付文件-支付类型(中信)
	 * by ctz 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getPayTypeZX(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0", "大额支付");
		dataMap.put("1", "小额支付");
		return dataMap;
	}
	/**
	 * getPayType
	 * 代付文件-支付类型(工行)
	 * by ctz 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getPayTypeGH(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0", "加急");
		dataMap.put("1", "普通");
		return dataMap;
	}
	
	
	/**
	 * getPayType
	 * 新清结算 代付文件-支付类型(中信)
	 * by ctz 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getPayTypeZXNew(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
//		dataMap.put("0", "大额支付");
//		dataMap.put("1", "小额支付");
		dataMap.put("1", "大额支付");
		dataMap.put("2", "小额支付");
		return dataMap;
	}
	/**
	 * getPayType
	 * 新清结算代付文件-支付类型(工行)
	 * by ctz 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getPayTypeGHNew(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
//		dataMap.put("0", "加急");
//		dataMap.put("1", "普通");
		dataMap.put("1", "加急");
		dataMap.put("2", "普通");
		return dataMap;
	}
	
	/**
	 * 获取开户行信息 by xupengfei
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCnapsInfo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("*", "* - 默认");
		String sql = "SELECT CNAPS_ID,CNAPS_ID||' - '||CNAPS_NAME FROM TBL_CNAPS_INFO WHERE CNAPS_FLAG = '1'";
		
		StringBuffer andSql = new StringBuffer(" ");
		if(params.length>1&&params[1]!=null&&"L".equals(params[1].toString())){ // 未与结算通道关联的开户行
			String countSql = "SELECT COUNT(*) FROM TBL_CHANNEL_CNAPS_MAP WHERE CNAPS_ID = '*'";
			String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
			if(!count.equals("0")){
				dataMap.remove("*");
			}
			andSql.append("AND CNAPS_ID NOT IN (SELECT CNAPS_ID FROM TBL_CHANNEL_CNAPS_MAP)");
			sql += andSql;
		}
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据风险等级查找关联风控模型信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRiskParamInf(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select distinct MODEL_KIND,MODEL_KIND from TBL_RISK_PARAM_INF WHERE RISK_LVL = '" + params[1].toString() + "'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 查找风控模型关联项信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getSaModelConn(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select distinct RISK_LVL from TBL_RISK_LVL order by RISK_LVL";
		List<String> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<String> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			String str = iterator.next();
			dataMap.put(str.trim(), "- 风控等级 [ " + str.trim() + " ] -");
		}
		dataMap.put("W", "- 风控警报级别 -");
		dataMap.put("S", "- 风控模型状态 -");
		dataMap.put("P", "- 参数值重定义 -");
		dataMap.put("A", "- 无限制 -");
		return dataMap;
	}
	
	/**
	 * 根据银行卡号查找发卡行信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBankBin(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String param = params[1].toString().trim().substring(0, 6);
		String sql = "SELECT CARD_DIS,CARD_TP FROM TBL_BANK_BIN_INF WHERE BIN_STA_NO = '" + param + "'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), getCardTypeTl(params).get(obj[1].toString().trim()));
		}
		return dataMap;
	}


	/**
	 * 结算方式类型 
	 */
	public static LinkedHashMap<String, String> getFlag(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("1", "固定金额");
		hashMap.put("2", "百分比");
		hashMap.put("3", "百分比封顶");
		return hashMap;
	}


	/**
	 * 取所有支付渠道作为下拉列表项
	 * @param params
	 * @return
	 * 2015-10-23下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getChannelAll(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		//String param = params[1].toString().trim().substring(0, 6);
		String sql = " select distinct t.brh_id chlId, substr(t.brh_id, 1, 4) || '-' || t.name chlName from TBL_ROUTE_UPBRH  t where t.brh_level = '1' and t.status='0' ";
		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}	
	/**
	 * 取业务作为下拉列表项
	 * @param params
	 * @return
	 * 2015-10-23下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBusinessSel(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String chlId = params[1].toString().trim();
		String sql;
		if(!"".equals(chlId) && !"*".equals(chlId)){
			sql = " select distinct t.brh_id busiId, substr(t.brh_id, 1, 8) || '-' || t.name busilName from TBL_ROUTE_UPBRH t where t.brh_level = '2' and substr(t.brh_id, 1, 4) = substr('"+chlId +"', 1, 4) and t.status='0'";
		}else{
			sql = " select distinct t.brh_id busiId, substr(t.brh_id, 1, 8) || '-' || t.name busilName from TBL_ROUTE_UPBRH t where t.brh_level = '2' and t.status='0' ";
		}
		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
		
	/**
	 * 取性质作为下拉列表项
	 * @param params
	 * @return
	 * 2015-10-23下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getPropertySel(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String brhId2 = params[1].toString().trim();
		String sql;
		if(!"".equals(brhId2)){
			sql = "select distinct t.brh_id busiId, substr(t.brh_id, 1, 12) || '-' || t.name busilName from TBL_ROUTE_UPBRH t where t.brh_level = '3' and t.status='0'" +
					" and substr(t.brh_id, 1, 8) = substr('"+brhId2 +"', 1, 8)";
		}else{
			sql = "select distinct t.brh_id busiId, substr(t.brh_id, 1, 12) || '-' || t.name busilName from TBL_ROUTE_UPBRH t where t.brh_level = '3' and t.status='0' ";
		}
		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 取商户组作为下拉列表项
	 * @param params
	 * @return
	 * 2015-10-23下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRouteMchtgSel(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

		String sql = "select distinct t.mcht_gid mchtGid, substr(t.mcht_gid, 1, 6) || '-' || t.mcht_gname mchtGname from Tbl_Route_Mchtg t";

		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/* 开户行信息 */
	/**
	 * 银行名
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBankName(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT SERIAL_NO,BANK_NAME FROM TBL_SUBBRANCH_INFO";
		StringBuilder sqlWhere = new StringBuilder();
		if(params != null && params.length > 1 && StringUtil.isNotEmpty(params[1].toString())){
			//参数分解
			String param = params[1].toString();
			String[] paramsStr = param.split(",");
			//选择了省
			if(StringUtil.isNotEmpty(paramsStr[0].toString())){
				if (StringUtil.isNotEmpty(sqlWhere)){
					sqlWhere.append(" AND");
				}else{
					sqlWhere.append(" WHERE");
				}
				sqlWhere.append(" PROVINCE='"+paramsStr[0].toString()+"'");
			}
			//选择了市
			if(StringUtil.isNotEmpty(paramsStr[1].toString())){
				if (StringUtil.isNotEmpty(sqlWhere)){
					sqlWhere.append(" AND");
				}else{
					sqlWhere.append(" WHERE");
				}
				sqlWhere.append(" CITY='"+paramsStr[1].toString()+"'");
			}
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql + sqlWhere.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[1].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 省
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getProvince(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT SERIAL_NO,PROVINCE FROM TBL_SUBBRANCH_INFO";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[1].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 市
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCity(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT SERIAL_NO,CITY FROM TBL_SUBBRANCH_INFO";
		//选择了省
		if(params != null && StringUtil.isNotEmpty(params[1].toString())){
			sql+=" WHERE PROVINCE='"+params[1].toString()+"'";
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[1].toString(), obj[1].toString());
		}
		return dataMap;
	}
	/**
	 * 开户行名和编号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getSubbranch(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT SUBBRANCH_ID,SUBBRANCH_NAME FROM TBL_SUBBRANCH_INFO";
		StringBuilder sqlWhere = new StringBuilder();
		if(params != null && params.length > 1 && StringUtil.isNotEmpty(params[1].toString())){
			//参数分解
			String param = params[1].toString();
			String[] paramsStr = param.split(",");
			//选择了银行名
			if(StringUtil.isNotEmpty(paramsStr[0].toString())){
				sqlWhere.append(" WHERE BANK_NAME='"+paramsStr[0].toString()+"'");
			}
			//选择了省
			if(StringUtil.isNotEmpty(paramsStr[1].toString())){
				if (StringUtil.isNotEmpty(sqlWhere)){
					sqlWhere.append(" AND");
				}else{
					sqlWhere.append(" WHERE");
				}
				sqlWhere.append(" PROVINCE='"+paramsStr[1].toString()+"'");
			}
			//选择了市
			if(StringUtil.isNotEmpty(paramsStr[2].toString())){
				if (StringUtil.isNotEmpty(sqlWhere)){
					sqlWhere.append(" AND");
				}else{
					sqlWhere.append(" WHERE");
				}
				sqlWhere.append(" CITY='"+paramsStr[2].toString()+"'");
			}
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql+sqlWhere.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString(), obj[1].toString());
		}
		return dataMap;
	}	
	/**
	 * 开户行名和编号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getSubbranchForUpdate(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		//选择了银行名
		if(StringUtil.isNotEmpty(params[1])){
			String sql = "SELECT SUBBRANCH_ID,SUBBRANCH_NAME FROM TBL_SUBBRANCH_INFO WHERE SUBBRANCH_ID='"+params[1].toString()+"'";
			List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
			Iterator<Object[]> iterator = dataList.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				dataMap.put(obj[0].toString(), obj[1].toString());
			}
		}
		return dataMap;
	}
	
	
	/**
	 * 查找档位名称
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getDiscNm(Object[] params) {
		String brhId2 = params[1].toString().trim();
		String sql ;

		//upd by yww 160408--优化sql，让成本扣率(档位名称)下拉框显示DISC_ID加DISC_NM的值
		if(!StringUtils.isBlank(brhId2)){
			sql = "SELECT DISC_ID, DISC_ID||'-'||DISC_NM FROM TBL_UPBRH_FEE where BRH_ID2 = '"+brhId2+"'";
		}else {
			sql = "SELECT DISC_ID, DISC_ID||'-'||DISC_NM FROM TBL_UPBRH_FEE where BRH_ID2 = '0'";
		}

		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	

	/**
	 * 查找商户名称
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchtName(Object[] params) {
		//Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();

		String sql = "select mcht_id,mcht_id || '-' || mcht_name_up from Tbl_Upbrh_Mcht where status=0 ";
		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBusinessNmStore(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String chlId = params[1].toString().trim();
		String sql;
		if(!"".equals(chlId)){
			sql = "select distinct t.brh_id busiId, t.brh_id || '-' || t.name busilName from TBL_ROUTE_UPBRH t where t.brh_level = '3' and substr(t.brh_id, 1, 8) = substr('"+chlId +"', 1, 8) and t.status='0' ";
		}else{
			sql = "select distinct t.brh_id busiId, t.brh_id || '-' || t.name busilName from TBL_ROUTE_UPBRH t where t.brh_level = '3' and t.status='0' ";
		}
		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}

	
	/**
	 * 查询渠道商户中 合规商户信息数据源(从gtwy数据库中查找) 
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntNoGtwy(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select a.MCHT_NO, a.MCHT_NO ||' - '|| a.MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF a,TBL_MCHT_SETTLE_INF b where a.MCHT_NO=b.MCHT_NO and b.Compliance ='0' and a.BANK_NO in " 
					+  operator.getBrhBelowId() + " and a.MCHT_STATUS = '0'";
		List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	public static LinkedHashMap<String, String> getRateIdStore1(Object[] params) {
		Operator operator = (Operator) params[0];
		String mchntId=(String) params[1];
		mchntId=mchntId.trim();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT tari.rate_id,  tpri.fee_name FROM tbl_agent_rate_info tari, tbl_profit_rate_info tpri WHERE tari.rate_id = tpri.rate_id And tari.fee_type = '0' AND tari.disc_id = (SELECT disc_id  FROM tbl_agent_fee_cfg tafc where tafc.agent_no =( select bank_no from TBL_MCHT_BASE_INF_TMP_TMP where Mcht_No='"+mchntId+"') ) order by tari.disc_id";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}

	public static LinkedHashMap<String, String> getRateIdStore2(Object[] params) {
		Operator operator = (Operator) params[0];
		String mchntId=(String) params[1];
		mchntId=mchntId.trim();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT tari.rate_id,  tpri.fee_name FROM tbl_agent_rate_info tari, tbl_profit_rate_info tpri WHERE tari.rate_id = tpri.rate_id And tari.fee_type = '1' AND tari.disc_id = (SELECT disc_id  FROM tbl_agent_fee_cfg tafc where tafc.agent_no =( select bank_no from TBL_MCHT_BASE_INF_TMP_TMP where Mcht_No='"+mchntId+"') ) order by tari.disc_id";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 省
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAddrProvince(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select REGION_CODE_BUSI,REGION_NAME from TBL_REGION_INFO WHERE SUPER_REGION_CODE_BUSI='0000'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 市
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAddrCity(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select REGION_CODE_BUSI,REGION_NAME from TBL_REGION_INFO";
		//选择了省
		if(params != null && params.length > 1 && StringUtil.isNotEmpty(params[1].toString())){
			sql+=" WHERE SUPER_REGION_CODE_BUSI='"+params[1].toString()+"'";
		}
		sql+=" ORDER BY REGION_CODE_BUSI";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString(), obj[1].toString());
		}
		return dataMap;
	}
	/**
	 * 终端厂商
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermFacture(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select fac_id,fac_name from TBL_TERM_FACTURER order by fac_id ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString(), obj[1].toString());
		}
		return dataMap;
	}
		
	/**
	 * 终端型号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermModel(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select model,model_name from TBL_TERM_MODEL ";
		//选择了厂商
		if(params != null && params.length > 1 && StringUtil.isNotEmpty(params[1].toString())){
			sql+=" WHERE fac_id='" + params[1].toString()+"'";
		}
		sql+=" ORDER BY fac_id,model";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString(), obj[1].toString());
		}
		return dataMap;
	}
	public static LinkedHashMap<String, String> getRateIdStore3(Object[] params) {
		Operator operator = (Operator) params[0];
		String mchntId=(String) params[1];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT TCRI.RATE_ID ,TCRI.NAME from TBL_CASH_RATE_INF TCRI,TBL_BRH_CASH_INF TBCI WHERE TCRI.RATE_ID = TBCI.RATE_ID AND TBCI.BRH_ID = (SELECT BANK_NO FROM TBL_MCHT_BASE_INF_TMP_TMP WHERE MCHT_NO ='"+mchntId+"')";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
}
