package com.allinfinance.struts.mchnt.action;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.core.io.ClassPathResource;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T2010101Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void reportAction() throws Exception {

		reportType = "EXCEL";

		String rpName = "";
		title = InformationUtil.createTitles("V_", 46);
		rpName = "RN2010101RN";
		data = reportCreator.process(genSql(), title);

		reportModel.setData(data);
		reportModel.setTitle(title);
		reportCreator
				.initReportData(getJasperInputSteam("T2010101.jasper"),
						parameters, reportModel.wrapReportDataSource(),
						getReportType());
		// System.out.println(operator.getOprId());
		if (Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK)
					+ rpName + "_" + "0000" + "_"
					+ CommonFunction.getCurrentDateTime() + ".xls";
		else if (Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK)
					+ rpName + "_" + operator.getOprId() + "_"
					+ CommonFunction.getCurrentDateTime() + ".pdf";

		outputStream = new FileOutputStream(fileName);
		System.out.println(SysParamUtil.getParam(rpName));
		reportCreator.exportReport(outputStream, SysParamUtil.getParam(rpName));

		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryCardAccpId)) {
			whereSql.append(" AND L_MCHT_NO ='").append(queryCardAccpId)
					.append("' ");
	 	}
	    if (StringUtil.isNotEmpty(queryStartTm)) {
			
			whereSql.append(" AND l.l_createdate >='").append(queryStartTm)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryStartTm)) {
			whereSql.append(" AND l.l_createdate <='").append(queryEndTm)
					.append("' ");
		}
		StringBuffer sb = new StringBuffer();

		sb.append("select  (select trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF t where t.mcht_no=l.l_mcht_no )l_mcht_no ,l.l_createdate,l.l_createpeople,l.l_upts,l.l_upoprid ,(CASE l.be_mcht_status  WHEN '1' THEN '添加日志 'when '2' then '删除日志' when '3' then '修改日志' when '5' then '冻结日志' when '7' then '恢复日志' when '9' then '注销日志' when 'w' then '入网冻结日志' when 'B' then '添加日志' when 'I' then '批量导入日志'  end)be_mcht_status "
				+ ",l.be_mcht_nm ,l.be_licence_no ,l.be_risl_lvl,l.be_bank_no,l.be_mcht_cn_abbr ,l.be_mcc,l.be_fax_no,l.be_manager,(SELECT VALUE FROM CST_SYS_PARAM  WHERE KEY= l.be_artif_certif_tp  and OWNER = 'CERTIFICATE' )be_artif_certif_tp,l.be_entity_no,l.be_contact,l.be_commtel,l.be_electro_fax"
				+ ",l.be_signinst_id,l.be_settleacct,l.be_settlebanknm,l.be_settleacctnm,l.be_disccode,"
				+ "l.af_mcht_nm ,l.af_licence_no ,l.af_risl_lvl,l.af_bank_no,l.af_mcht_cn_abbr ,l.af_mcc,l.af_fax_no,l.af_manager,(SELECT VALUE FROM CST_SYS_PARAM  WHERE KEY= l.af_artif_certif_tp  and OWNER = 'CERTIFICATE' )af_artif_certif_tp,l.af_entity_no,l.af_contact,l.af_commtel,l.af_electro_fax"   
				+ ",l.af_signinst_id,l.af_settleacct,l.af_settlebanknm,l.af_settleacctnm,l.af_disccode"
				+",l.BE_OPENSTLNO,l.AF_OPENSTLNO,(case  l.BE_CLEARTYPENM when '0' then '对公账户' when '1' then '对私账户' end)BE_CLEARTYPENM,(case  l.AF_CLEARTYPENM when '0' then '对公账户' when '1' then '对私账户' end)AF_CLEARTYPENM"
				+ " from  tbl_mcht_base_inf_tmp_log l");
		sb.append(whereSql);
		 sb.append(" ORDER BY l.l_createdate  DESC");
		return sb.toString();
	}
     //贵妃舔朕
	// 错误类型
	private String errType;

	private String brhId;
	
	private String queryCardAccpId;
	
	private String queryStartTm;
	
	private String queryEndTm;

	public String getQueryCardAccpId() {
		return queryCardAccpId;
	}

	public void setQueryCardAccpId(String queryCardAccpId) {
		this.queryCardAccpId = queryCardAccpId;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the errType
	 */
	public String getErrType() {
		return errType;
	}

	/**
	 * @param errType
	 *            the errType to set
	 */
	public void setErrType(String errType) {
		this.errType = errType;
	}

	public String getQueryStartTm() {
		return queryStartTm;
	}

	public void setQueryStartTm(String queryStartTm) {
		this.queryStartTm = queryStartTm;
	}

	public String getQueryEndTm() {
		return queryEndTm;
	}

	public void setQueryEndTm(String queryEndTm) {
		this.queryEndTm = queryEndTm;
	}


}
