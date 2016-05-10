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

public class T8080501Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String mchtNo;
	private String dateSettlmt;
	private String mchtNoName;
	private String brhInsIdCd;
	

	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		String sql = getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);

		DecimalFormat df = new DecimalFormat("0.00");
		//1101-POS消费, ,1091-预授权完成 正交易
		String STRING_1101 = "1101";
		String STRING_1091 = "1091";
		//3101消费撤销  3091预授权完成撤销 5151-POS退货 负交易
		String STRING_3010 = "3101";
		String STRING_3091 = "3091";
		String STRING_5151 = "5151";
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = df.format(Double.parseDouble(objects[4].toString())/100);//交易金额是以分为单位，所以要除100
//					objects[4] = CommonFunction.moneyFormat(objects[4].toString());
					//正交易
					if (!STRING_3010.equals(objects[7]) && !STRING_3091.equals(objects[7]) && !STRING_5151.equals(objects[7])){
						objects[4] = CommonFunction.moneyFormat(objects[4].toString());
						//3101消费撤销  3091预授权完成撤销 5151-POS退货 负交易
					}else{
						objects[4] = "-" + CommonFunction.moneyFormat(objects[4].toString());
					}
				}
				if (objects[5] != null && !"".equals(objects[5])) {
					objects[5] = df.format(Double.parseDouble(objects[5].toString()));
					objects[5] = CommonFunction.moneyFormat(objects[5].toString());
				}
				if (objects[6] != null && !"".equals(objects[6])) {
					objects[6] = df.format(Double.parseDouble(objects[6].toString()));
					objects[6] = CommonFunction.moneyFormat(objects[6].toString());
				}
				
				if (objects[7] != null && !"".equals(objects[7].toString().trim())) {
					if (STRING_1101.equals(objects[7])) {
						objects[7] = "POS消费";
					}else if (STRING_1091.equals(objects[7])) {
						objects[7] = "预授权完成";
					}else if (STRING_3010.equals(objects[7])) {
						objects[7] = "消费撤销";
					}else if (STRING_3091.equals(objects[7])) {
						objects[7] = "预授权完成撤销";
					}else if (STRING_5151.equals(objects[7])) {
						objects[7] = "POS退货";
					}else{
						objects[7] = " ";
					}
				}
				
				if (objects[8] != null && !"".equals(objects[8].toString().trim())) {
					if (objects[8].toString().startsWith("0")) {
						String numb=objects[8].toString().substring(1,4);
						for (int i= 0; i < numb.length(); i++) {
							if (numb.startsWith("0")) {
								numb=numb.replace("0","");
							}
						}
						if("".equals(numb.trim()) || null==numb){
							objects[8] = "T+0";
						}else{
							objects[8] = "T+"+numb;
						}
					}else if(objects[8].toString().startsWith("1")){
						String endNumber= objects[8].toString().substring(4,5);
								if("0"==endNumber){
									objects[8] = "周结";
								}else if("1"==endNumber){
									objects[8] = "月结";
								}else if("2"==endNumber){
									objects[8] = "季结";
								}else{
									objects[8] = " ";
								}
							}else {
								objects[8] = " ";
							}
				}
			}
		}
		
		String[] title={"交易日期","交易时间","流水号","参考号","交易金额","手续费","清算金额","交易类型","结算类型","合作伙伴"};
        String head="商户结算单详细报表";
        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + head+ ".xls";
       
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("dataList", dataList);
        map.put("title", title);
        map.put("fileName", fileName);
        map.put("head", "商户结算单详细报表-"+mchtNoName);
        map.put("sheet", "商户结算单详细报表");
        map.put("isCount", false);
        //map.put("mapParm", mapParm);
        
        excelReport.reportDownloadTxnNew(map);
//		excelReport.reportDownloadTxn(dataList, title, os, head, false,null);
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}


	private String getSql() {
		/*HttpServletRequest request = ServletActionContext.getRequest();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String mchtNo = request.getParameter("mchtNo");
		String brhId = request.getParameter("brhId");*/
		//20160429 郭宇 修改 提现类交易不显示
		StringBuffer whereSql = new StringBuffer(" where A.TXN_NUM <> '1211' AND A.TXN_NUM <> '1221' AND "
				+ "MCHT_CD in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");

		if (isNotEmpty(mchtNo)) {
			whereSql.append(" AND a.MCHT_CD ").append(" = ").append("'").append(mchtNo).append("'");
		}
		if (isNotEmpty(dateSettlmt)) {
			whereSql.append(" AND a.DATE_SETTLMT ").append(" = ").append("'").append(dateSettlmt).append("'");
		}
		if (isNotEmpty(brhInsIdCd)) {
			whereSql.append(" AND a.BRH_INS_ID_CD ").append(" = ").append("'").append(brhInsIdCd).append("'");
		}

		String sql = "SELECT a.trans_date,a.trans_date_time,a.txn_ssn,b.retrivl_ref, "
//				+" a.TRANS_AMT,(a.MCHT_FEE_D-a.MCHT_FEE_C) as sum_fee,((TRANS_AMT/100)-(MCHT_FEE_D-MCHT_FEE_C)) as SETTL_AMT, a.txn_Num, "
				+" a.TRANS_AMT,(a.MCHT_FEE_D-a.MCHT_FEE_C) as sum_fee,"
				+ "CASE WHEN A.TXN_NUM = '3101' THEN (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "WHEN A.TXN_NUM = '3091' THEN  (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "WHEN A.TXN_NUM = '5151' THEN  (TRANS_AMT/100)*-1-(MCHT_FEE_D-MCHT_FEE_C) "
				+ "ELSE (TRANS_AMT/100)-(MCHT_FEE_D-MCHT_FEE_C) END as SETTL_AMT,"
				+ "a.txn_Num, "
				+ " substr(b.MISC_2,117,1)||substr(b.MISC_2,46,3)||substr(b.MISC_2,118,1) as misc2Flag, "
				//+ "trim(a.BRH_INS_ID_CD)||'-'||c.BRH_NAME as brhName , (a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot "
				+ "trim(c.CREATE_NEW_NO)||'-'||c.BRH_NAME as brhName "
				+ "from TBL_ALGO_DTL a join TBL_ALGO_CONFIRM_HIS h on a.DATE_SETTLMT in h.DATE_SETTLMT  and h.STATUS='0' "
				+ " left join TBL_BRH_INFO c on trim(a.BRH_INS_ID_CD)=c.BRH_ID "
				+ " left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref ";
		sql = sql + whereSql.toString()  + " order by trans_date desc,trans_date_time desc ";
		
		return sql;
	}


	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	
	public String getMchtNoName() {
		return mchtNoName;
	}

	public void setMchtNoName(String mchtNoName) {
		this.mchtNoName = mchtNoName;
	}

	public String getBrhInsIdCd() {
		return brhInsIdCd;
	}

	public void setBrhInsIdCd(String brhInsIdCd) {
		this.brhInsIdCd = brhInsIdCd;
	}


}
