package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title: 商户简称报表
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-27
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T80213Action extends ReportBaseAction {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		String rpName = "";
		title = InformationUtil.createTitles("V_", 7);
		rpName = "RN80213RN";
		data = reportCreator.process(genSql(), title);

		reportModel.setData(data);
		reportModel.setTitle(title);
		reportCreator.initReportData(getJasperInputSteam("T80213.jasper"), parameters, 
					reportModel.wrapReportDataSource(), getReportType());
		
        if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rpName + "_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rpName + "_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam(rpName));
		
		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {
		
		StringBuffer sb = new StringBuffer();

		String whereSql = "where x.bank_no=y.brh_id and x.mcht_no=m.accp_id and "+
				"m.rule_code=n.rule_code and x.mcht_no=j.mcht_no and "+
				"j.fee_rate=k.disc_cd "+
				"and x.mcht_status='0' ";
			if (!StringUtil.isNull(mchntId)) {
				whereSql += " AND x.mcht_no = '" + mchntId + "' ";
			}
			if (!StringUtil.isNull(brhId)) {
				whereSql += " AND x.bank_no = '" + brhId + "' ";
			}
			sb.append("select x.mcht_no, x.mcht_nm, x.mcht_cn_abbr,x.mcc, y.brh_name, "+
					"k.disc_nm, n.first_mcht_cd "+
					"from tbl_mcht_base_inf x, tbl_brh_info y , tbl_mcht_settle_inf j," +
					"tbl_inf_disc_cd k, tbl_route_rule m, tbl_rule_mcht_rel n "
					);
			sb.append(whereSql);
			sb.append(	"order by x.bank_no, x.mcht_no");
		return sb.toString();
	}
	
	
	
	
	// 商户编号
	private String mchntId;
	
	
	private String brhId;
	
	private String reportName;
	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the mchntId
	 */
	public String getMchntId() {
		return mchntId;
	}

	/**
	 * @param mchntId the mchntId to set
	 */
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	

}