package com.allinfinance.struts.route.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;


@SuppressWarnings("serial")
public class T1103311Action extends ReportBaseAction{
	HttpServletRequest request = ServletActionContext.getRequest();
	String mchtNo = request.getParameter("mchtNo");			//商户
	String partnerNo = request.getParameter("partnerNo");	//合作伙伴
	String mcc = request.getParameter("mcc");				//MCC
	String mchtAddr = request.getParameter("mchtAddr");		//商户地区
	
	String payRoad = request.getParameter("payRoad");		//支付渠道
	String business = request.getParameter("business");		//业务
	String proper = request.getParameter("proper");			//性质
	String qmchtNo = request.getParameter("qmchtNo");		//渠道商户
	String queryCharConAndId = request.getParameter("queryCharConAndId");	//是否包含性质
	String mchtGroup = request.getParameter("mchtGroup");					//路由商户组
	boolean parseBoolean = Boolean.parseBoolean(queryCharConAndId);
	
	@Override
	protected void reportAction() throws Exception {
		ExcelReportCreator excelReportCreator = new ExcelReportCreator(); 
		List<String> sheetList = new ArrayList<String>();
		List<String> titleList = new ArrayList<String>();
		List<String[]> coulmHeaderList = new ArrayList<String[]>();
		
		List<String> dataSqlList = new ArrayList<String>();
		
		if(!parseBoolean){	//不包含性质
			if(StringUtils.isNotEmpty(proper)){
				if(getPropId() == null){
					writeNoDataMsg("系统中没有该性质信息！");
					return;
				}
			}else{
				writeNoDataMsg("性质条件不可为空！");
				return;
			}
		}
		int count = 0;
		if(!parseBoolean){	//不包含性质
			count = Integer.parseInt(CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(getCountSqlNO()));
		}else{
			count = Integer.parseInt(CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(getCountSql()));
		}
		
		
		if(count == 0){
			writeNoDataMsg("没有找符合条件的记录，无法生成报表！");
			return;
		}else if(count > Constants.REPORT_MAX_COUNT){
			writeNoDataMsg("符合条件的记录数太多（>"+Constants.REPORT_MAX_COUNT+"），无法生成报表！");
			return;
		}

		String rnName="RN1103311RN_";
		String resName= SysParamUtil.getParam("RN1103311RN");
		
        String[] coulmHeader={"映射关系ID","商户号","商户名称","商户地区","MCC","结算扣率","是否对账单","是否积分","合作伙伴号","合作伙伴名称",
        		"支付渠道编码","支付渠道","业务编码","业务","性质编码","性质","渠道商户号","渠道商户名称","渠道终端号","启用时间"};
        
        sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		if(!parseBoolean){	//不包含性质
			dataSqlList.add(getDataSqlNO());
		}else{
			dataSqlList.add(getDataSql());
		}
		
		
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={4,7};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
//		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		//20160424 郭宇 修改 从gtwy数据库下载数据
//		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));
		excelReportCreator.setDataList(excelReportCreator.processDataGW(dataSqlList, coulmHeaderList));
//		excelReportCreator.setSumList(excelReportCreator.processData(sumDataSqlList, coulmHeaderList));
		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		writeUsefullMsg(fileName);
		return;
	}
	
	
	private String getWhereSQL(){
		StringBuffer whereSql1 = new StringBuffer("  AND instr(m.MCHT_NO,'AAA')= 0 ");
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
		if (isNotEmpty(payRoad)) {
			whereSql1.append(" AND ru1.BRH_ID = '"+payRoad+"' ");
		}	
		if (isNotEmpty(business)) {
			whereSql1.append(" AND ru2.BRH_ID =  '"+business+"' ");
		}	
		if (isNotEmpty(proper)) {
			if(parseBoolean==true){
				whereSql1.append(" AND ru3.NAME LIKE '%"+proper+"%' ");
			} else {
				whereSql1.append(" AND (ru3.brh_id is null "
						+ " or m.MCHT_NO not in ( "
						+ "  select distinct t1.mcht_id"
						+ " from Tbl_Route_Rule_Map t1,Tbl_Route_Upbrh t2 "
						+ " where t1.brh_id3 = t2.brh_id"
						+ "    and t2.status='0' and t2.brh_level='3' "
						+ "  and t2.NAME LIKE '%"+ proper +"%' ) )");
			}
		}
		
		if (isNotEmpty(qmchtNo)&&!"*".equals(qmchtNo.trim())) {
			whereSql1.append(" AND tum.mcht_id_up ='"+qmchtNo+"' ");
		}
		if (isNotEmpty(mchtGroup)&&!"*".equals(mchtGroup.trim())) {
			whereSql1.append(" AND mg.mcht_gid ='"+mchtGroup+"' ");
		}
		return whereSql1.toString();
	}
	
	private String getDataSql() {
		String sql = " SELECT rrm.rule_id,m.mcht_no, m.mcht_nm, c.CITY_NAME, m.mcc,"
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
				+ "	           end) settleRate ,   "  
				+ "    decode(s.bank_statement,'0','是','1','否','未知') bill,decode(s.integral,'0','是','1','否','未知') score, "
				+ "    b.create_new_no brh_no, b.brh_name, "
				+ "	   ru1.brh_id chlId,ru1.name chlName,ru2.brh_id busiId,ru2.name busiName,ru3.brh_id propId,ru3.name propName,tum.mcht_id_up,tum.mcht_name_up,tum.term_id_up,substr(rrm.crt_time,0,8) applyTime "
				+ "	      FROM TBL_MCHT_BASE_INF m "
				+ "	      join tbl_mcht_settle_inf s on m.mcht_no = s.mcht_no "
				+ "	      join tbl_brh_info b on m.bank_no = b.brh_id "
				+ "		  left join CST_CITY_CODE c on c.MCHT_CITY_CODE = TRIM(m.AREA_NO) "		
				+ "       left join Tbl_Route_Rule_Map rrm on rrm.mcht_id = m.MCHT_NO "
				+ "       left join Tbl_Route_Upbrh ru1 on ru1.brh_id = substr(rrm.brh_id3,1,4) and ru1.brh_level='1' "
				+ "       left join Tbl_Route_Upbrh ru2 on ru2.brh_id=substr(rrm.brh_id3,1,8) and ru2.brh_level='2' "
				+ "       left join Tbl_Route_Upbrh ru3 on ru3.brh_id=rrm.brh_id3 and ru3.brh_level='3' "
				+ "       left join TBL_UPBRH_MCHT tum on tum.mcht_id_up = rrm.mcht_id_up and tum.term_id_up = substr(rrm.MISC1,0,8) and tum.brh_id3 = rrm.brh_id3 and tum.status = '0' "
				+ "       left join tbl_route_mchtg_detail mg on rrm.mcht_id = mg.mcht_id "	
				+ "	     WHERE m.mcht_status = '0' "   
				+ getWhereSQL().toString() ;
		return sql;
	}
	
	private String getDataSqlNO() {
		String sql = " SELECT distinct '',r.mcht_no, r.mcht_nm, r.CITY_NAME, r.mcc, r.settleRate , r.bill,r.score,r.brh_no, r.brh_name, "
				+ "	   ru1.brh_id chlId,ru1.name chlName,ru2.brh_id busiId,ru2.name busiName,ru3.brh_id propId,ru3.name propName,"
				+ "    '','','','' "
				+ "	      FROM  ("
				+ getDataSql() + ") r "
				+ "    join Tbl_Route_Upbrh ru3 on ru3.brh_level='3' and ru3.brh_id='"+getPropId()+"'"
				+ "    join Tbl_Route_Upbrh ru1 on ru1.brh_id = substr(ru3.brh_id,1,4) and ru1.brh_level='1' "
				+ "    join Tbl_Route_Upbrh ru2 on ru2.brh_id = substr(ru3.brh_id,1,8) and ru2.brh_level='2' ";
				
		return sql;
	}
	

	private String getCountSql() {
		String countSql = "SELECT  count(1)  "
				+ "	      FROM TBL_MCHT_BASE_INF m "
				+ "	      join tbl_mcht_settle_inf s on m.mcht_no = s.mcht_no "
				+ "	      join tbl_brh_info b on m.bank_no = b.brh_id "
				+ "       left join Tbl_Route_Rule_Map rrm on rrm.mcht_id = m.MCHT_NO "
				+ "       left join Tbl_Route_Upbrh ru1 on ru1.brh_id = substr(rrm.brh_id3,1,4) and ru1.brh_level='1' "
				+ "       left join Tbl_Route_Upbrh ru2 on ru2.brh_id=substr(rrm.brh_id3,1,8) and ru2.brh_level='2' "
				+ "       left join Tbl_Route_Upbrh ru3 on ru3.brh_id=rrm.brh_id3 and ru3.brh_level='3' "
				+ "       left join TBL_UPBRH_MCHT tum on tum.mcht_id_up = rrm.mcht_id_up and tum.term_id_up = substr(rrm.MISC1,0,8) and tum.status = '0' "
				+ "       left join tbl_route_mchtg_detail mg on rrm.mcht_id = mg.mcht_id "
				+ "	     WHERE m.mcht_status = '0'     "
				+ getWhereSQL().toString() ;
		return countSql;
	}
	
	private String getPropId(){
		String sql = "select ru3.brh_id from Tbl_Route_Upbrh ru3 where ru3.brh_level='3' and ru3.status='0' and ru3.name like '%" + proper + "%'";
		List list = CommonFunction.getcommGWQueryDAO().findBySQLQuery(sql);
		if(list == null || list.isEmpty()){
			return null;
		}else{
			return (String) list.get(0);
		}
	}
	
	private String getCountSqlNO(){
		String countSql = "SELECT  count(distinct m.mcht_no)  "
				+ "	      FROM TBL_MCHT_BASE_INF m "
				+ "	      join tbl_mcht_settle_inf s on m.mcht_no = s.mcht_no "
				+ "	      join tbl_brh_info b on m.bank_no = b.brh_id "
				+ "       left join Tbl_Route_Rule_Map rrm on rrm.mcht_id = m.MCHT_NO "
				+ "       left join Tbl_Route_Upbrh ru1 on ru1.brh_id = substr(rrm.brh_id3,1,4) and ru1.brh_level='1' "
				+ "       left join Tbl_Route_Upbrh ru2 on ru2.brh_id=substr(rrm.brh_id3,1,8) and ru2.brh_level='2' "
				+ "       left join Tbl_Route_Upbrh ru3 on ru3.brh_id=rrm.brh_id3 and ru3.brh_level='3' "
				+ "       left join TBL_UPBRH_MCHT tum on tum.mcht_id_up = rrm.mcht_id_up and tum.term_id_up = substr(rrm.MISC1,0,8) and tum.status = '0' "
				+ "       left join tbl_route_mchtg_detail mg on rrm.mcht_id = mg.mcht_id "
				+ "	     WHERE m.mcht_status = '0'     "
				+ getWhereSQL().toString() ;
		return countSql;
	}

	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
	}

}
