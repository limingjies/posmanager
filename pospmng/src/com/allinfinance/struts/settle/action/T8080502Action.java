package com.allinfinance.struts.settle.action;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;
/**
 * 下载所有明细
 * @author Jee Khan
 *
 */
public class T8080502Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String startDate;	
	private String endDate;
	

	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		String sql = getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		List<Object[]> sumList = CommonFunction.getCommQueryDAO().findBySQLQuery(getSumSQL());
		dataList.addAll(sumList);

		DecimalFormat df = new DecimalFormat("0.00");
		//1101-POS消费, ,1091-预授权完成 正交易
		String STRING_1101 = "1101";
		String STRING_1091 = "1091";
		//3101消费撤销  3091预授权完成撤销 5151-POS退货 负交易
		String STRING_3101 = "3101";
		String STRING_3091 = "3091";
		String STRING_5151 = "5151";
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6].toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6].toString());
				}
				if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7].toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7].toString());
				}
				if (objects[8] != null && !"".equals(objects[8])) {
					objects[8] = df.format(Double.parseDouble(objects[8].toString()));
					objects[8] = CommonFunction.moneyFormat(objects[8].toString());
				}
				
				if (objects[9] != null && !"".equals(objects[9].toString().trim())) {
					if (STRING_1101.equals(objects[9])) {
						objects[9] = "POS消费";
					}else if (STRING_1091.equals(objects[9])) {
						objects[9] = "预授权完成";
					}else if (STRING_3101.equals(objects[9])) {
						objects[9] = "消费撤销";
					}else if (STRING_3091.equals(objects[9])) {
						objects[9] = "预授权完成撤销";
					}else if (STRING_5151.equals(objects[9])) {
						objects[9] = "POS退货";
					}else{
						objects[9] = " ";
					}
				}
				
				if (objects[10] != null && !"".equals(objects[10].toString().trim())) {
					if (objects[10].toString().startsWith("0")) {
						String numb=objects[10].toString().substring(1,4);
						for (int i= 0; i < numb.length(); i++) {
							if (numb.startsWith("0")) {
								numb=numb.replace("0","");
							}
						}
						if("".equals(numb.trim()) || null==numb){
							objects[10] = "T+0";
						}else{
							objects[10] = "T+"+numb;
						}
					}else if(objects[10].toString().startsWith("1")){
						String endNumber= objects[10].toString().substring(4,5);
								if("0"==endNumber){
									objects[10] = "周结";
								}else if("1"==endNumber){
									objects[10] = "月结";
								}else if("2"==endNumber){
									objects[10] = "季结";
								}else{
									objects[10] = " ";
								}
							}else {
								objects[10] = " ";
							}
				}
			}
		}

		//String[] title={"商户号","商户名称","交易日期","交易时间","流水号","参考号","交易金额","手续费","清算金额","交易类型","结算类型","合作伙伴","合作伙伴分润佣金"};
		String[] title={"商户号","商户名称","交易日期","交易时间","流水号","参考号","交易金额","手续费","清算金额","交易类型","结算类型","合作伙伴"};//20160329修改，任务285-去掉合作伙伴分润
        String head="商户结算单详细报表";
        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + head+ ".xls";
       
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("fileName", fileName);
        map.put("sheet", "商户结算单详细报表");
        map.put("head", "商户结算单详细报表");
        map.put("title", title);
        map.put("dataList", dataList);
        map.put("isCount", false);
        //map.put("mapParm", mapParm);
        
        excelReport.reportDownloadTxnNew(map);
//		excelReport.reportDownloadTxn(dataList, title, os, head, false,null);
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}


	private String getSql() {
		//20160429 郭宇 修改 提现类交易不显示
		StringBuffer whereSql = new StringBuffer(" where A.TXN_NUM <> '1211' AND A.TXN_NUM <> '1221' ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.DATE_SETTLMT ").append(" <= ").append("'").append(endDate).append("'");
		}

		String sql = "select mcht_cd,mcht_nm,trans_date,trans_date_time,txn_ssn,retrivl_ref,"
				+ "       tran_amt trans_amt,sum_fee,(nvl(tran_amt,0)-nvl(sum_fee,0)) settl_amt,"
				+ "       txn_Num,misc2Flag,brhName "
				+ " from ("
				+ "    SELECT a.mcht_cd, m.mcht_nm,a.trans_date,a.trans_date_time, a.txn_ssn,b.retrivl_ref,"
				+ "          ( case"
				+ "             when a.txn_Num in ('3101', '3091', '5151') then"
				+ "                  nvl(a.TRANS_AMT,0)/-100"
				+ "             else"
				+ "                  nvl(a.TRANS_AMT,0)/100"
				+ "           END) TRAN_AMT,"
				+ "           (a.MCHT_FEE_D - a.MCHT_FEE_C) as sum_fee,"
				+ "           a.txn_Num,"
				+ "           substr(b.MISC_2, 117, 1) || substr(b.MISC_2, 46, 3) ||substr(b.MISC_2, 118, 1) as misc2Flag,"
				+ "           trim(c.CREATE_NEW_NO) || '-' || c.BRH_NAME as brhName "
				//+ "           (a.ACQ_INS_ALLOT_C - a.ACQ_INS_ALLOT_D) as acqInsAllot"//20160329修改，任务285-去掉合作伙伴分润
				+ "      from TBL_ALGO_DTL a"
				+ "		      join TBL_ALGO_CONFIRM_HIS h "
				+ "		   on a.DATE_SETTLMT in h.DATE_SETTLMT  and h.STATUS='0' "
				+ "      left join TBL_BRH_INFO c"
				+ "        on trim(a.BRH_INS_ID_CD) = c.BRH_ID"
				+ "      left join TBL_N_TXN b"
				+ "        on substr(b.MISC_2, 23, 14) = a.TRANS_DATE || a.TRANS_DATE_TIME"
				+ "       and a.txn_SSN = b.SYS_SEQ_NUM"
				+ "       and a.txn_SSN = b.SYS_SEQ_NUM"
				+ "      left join tbl_mcht_base_inf m"
				+ "        on a.mcht_cd = m.mcht_no"
				+ whereSql.toString()
				+ "    ) r";
		
		sql = sql  + " order by trans_date desc,trans_date_time desc ";
		
		return sql;
	}
	
	private String getSumSQL(){
		StringBuffer sb = new StringBuffer();
		//sb.append("select '','','','','','合计',sum(TRANS_AMT),sum(sum_fee),sum(SETTL_AMT),'','','',sum(acqInsAllot) from (");//20160329修改，任务285-去掉合作伙伴分润
		sb.append("select '','','','','','合计',sum(TRANS_AMT),sum(sum_fee),sum(SETTL_AMT),'','','','' from (");
		sb.append(getSql());
		sb.append(" )");
		return sb.toString();
	}

	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
