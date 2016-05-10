package com.allinfinance.struts.route.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

public class T110351Action extends BaseAction {
	public String dir = SysParamUtil.getParam(
			SysParamConstants.TEMP_FILE_DISK).replace("\\", "/");
	@Override
	protected String subExecute() throws Exception {
		return null;
	}

	public String exportData() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		String mchtId = request.getParameter("mchtId");
		String countryId = request.getParameter("countryId");
		String mchtStatus = request.getParameter("mchtStatus");
		String queryIdmcc = request.getParameter("queryIdmcc");
		String queryMchtAreaId = request.getParameter("queryMchtAreaId");
		String minNum = request.getParameter("minNum");
		String maxNum = request.getParameter("maxNum");
		int min = 0;
		int max = 0;
		if (isNotEmpty(minNum)) {
			min = Integer.parseInt(minNum);
			whereSql.append(" AND " + "M.QNUM >= '" + min + "'");
		}
		if (isNotEmpty(maxNum)) {
			max = Integer.parseInt(maxNum);
			whereSql.append(" AND " + "M.QNUM <= '" + max + "'");
		}
		if (isNotEmpty(mchtId)) {
			whereSql.append(" AND " + "M.MCHT_NO = '" + mchtId + "'");
		}
		if (isNotEmpty(countryId)) {
			whereSql.append(" AND " + "M.COUNTRY = '" + countryId + "'");
		}
		if (isNotEmpty(mchtStatus)) {
			whereSql.append(" AND " + "M.MCHT_STATUS = '" + mchtStatus + "'");
		}
		if (isNotEmpty(queryIdmcc)) {
			whereSql.append(" AND " + "M.MCC = '" + queryIdmcc + "'");
		}
		if (isNotEmpty(queryMchtAreaId)) {
			whereSql.append(" AND "
					+ "M.ADDR = (SELECT CITY_NAME FROM CST_CITY_CODE  WHERE TRIM(MCHT_CITY_CODE)=TRIM( '"
					+ queryMchtAreaId + "' )) ");
		}

		String sql = "  SELECT M.MCHT_NO,M.MCHT_NM,M.ADDR,M.MCC,M.COUNTRY,M.MCHT_STATUS,M.QNUM FROM (     "
				+ "    SELECT A.MCHT_NO,  "
				+ "     A.MCHT_NM,  "
				+ "     A.MCC,  "
				+ "     (SELECT D.CITY_NAME FROM CST_CITY_CODE D WHERE TRIM(D.MCHT_CITY_CODE)=TRIM(A.AREA_NO) )ADDR,  "
				+ "     A.MCHT_STATUS,  "
				+ "     B.COUNTRY ,  "
				+ "     B.COMPLIANCE,  "
				+ "     (SELECT COUNT(1) FROM TBL_UPBRH_MCHT C WHERE C.STATUS='0' AND C.MCHT_ID=B.MCHT_NO) QNUM  "
				+ "   FROM TBL_MCHT_BASE_INF A  "
				+ "   RIGHT JOIN TBL_MCHT_SETTLE_INF B  "
				+ "   ON A.MCHT_NO =B.MCHT_NO  "
				+ "   WHERE B.COMPLIANCE='0'   ) M" + whereSql.toString();
		String countSql = "  SELECT COUNT(1) FROM (     "
				+ "    SELECT A.MCHT_NO,  "
				+ "     A.MCHT_NM,  "
				+ "     A.MCC,  "
				+ "     (SELECT D.CITY_NAME FROM CST_CITY_CODE D WHERE TRIM(D.MCHT_CITY_CODE)=TRIM(A.AREA_NO) )ADDR,  "
				+ "     A.MCHT_STATUS,  "
				+ "     B.COUNTRY ,  "
				+ "     B.COMPLIANCE,  "
				+ "     (SELECT COUNT(1) FROM TBL_UPBRH_MCHT C WHERE C.STATUS='0' AND C.MCHT_ID=B.MCHT_NO)QNUM  "
				+ "   FROM TBL_MCHT_BASE_INF A  "
				+ "   RIGHT JOIN TBL_MCHT_SETTLE_INF B  "
				+ "   ON A.MCHT_NO =B.MCHT_NO  "
				+ "   WHERE B.COMPLIANCE='0'   ) M" + whereSql.toString();

		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(
				countSql);
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO()
				.findBySQLQuery(sql, 0, Integer.parseInt(count));
		try {

			WritableWorkbook wwb = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String time = formatter.format(curDate);
			// 创建可写入的Excel工作簿
			String fileName = dir + File.separator + "合规商户信息报表" + time + ".xls";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
			}
			// 以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);
			// 创建工作表
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			ws.setColumnView(0, 20);
			ws.setColumnView(1, 20);
			ws.setColumnView(2, 20);
			ws.setColumnView(3, 20);
			ws.setColumnView(4, 20);
			ws.setColumnView(5, 20);
			ws.setColumnView(6, 20);
			ws.setName("合规商户");
			// 要插入到的Excel表格的行号，默认从0开始
			Label mchtNo = new Label(0, 0, "商户号");// 表示第
			Label mchtName = new Label(1, 0, "商户名称");
			Label addr = new Label(2, 0, "商户地区");
			Label mcc = new Label(3, 0, "MCC");
			Label country = new Label(4, 0, "是否县乡");
			Label satus = new Label(5, 0, "商户状态");
			Label num = new Label(6, 0, "关联渠道商户数");
			

			ws.addCell(mchtNo);
			ws.addCell(mchtName);
			ws.addCell(addr);
			ws.addCell(mcc);
			ws.addCell(country);
			ws.addCell(satus);
			ws.addCell(num);
			

			for (int i = 0; i < dataList.size(); i++) {
				String cStatus=dataList.get(i)[4]+ "";
				cStatus=exchageSta(cStatus);
				String mStatus=dataList.get(i)[5]+ "";
				mStatus=exchageMchtSta(mStatus);
				Label mchtNo_i = new Label(0, i + 1, dataList.get(i)[0] + "");
				Label mchtName_i = new Label(1, i + 1, dataList.get(i)[1]+ "");
				Label addr_i = new Label(2, i + 1, dataList.get(i)[2] + "");
				Label mcc_i = new Label(3, i + 1, dataList.get(i)[3] + "");
				Label country_i = new Label(4, i + 1,cStatus );
				Label satus_i = new Label(5, i + 1, mStatus);
				Label num_i = new Label(6, i + 1, dataList.get(i)[6]+"");
				
				ws.addCell(mchtNo_i);
				ws.addCell(mchtName_i);
				ws.addCell(addr_i);
				ws.addCell(mcc_i);
				ws.addCell(country_i);
				ws.addCell(satus_i);
				ws.addCell(num_i);
			
			}
			// 写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();
			writeUsefullMsg(fileName);
			writeSuccessMsg("数据下载成功.");
		
		} catch (Exception e) {
			writeErrorMsg("数据下载失败");
		}
		
		return null;
	}
	protected void writeUsefullMsg(String msg) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		PrintWriter printWriter = ServletActionContext.getResponse()
				.getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	private String exchageSta(String sta){
		if (sta.equals("0")) {
			return "是";
		} else if (sta.equals( "1")) {
			return "否";
		}else  {
			return "";
		}
	}
	private String exchageMchtSta(String val){
		if (val.equals("0") ) {
			return "正常";
		} else if (val.equals( "1")) {
			return "添加待终审";
		} else if (val .equals( "B")) {
			return "添加待初审";
		} else if (val .equals( "3")) {
			return "修改待审核";
		} else if (val .equals( "5")) {
			return "冻结待审核";
		} else if (val .equals( "6")) {
			return "冻结";
		} else if (val .equals( "7")) {
			return "恢复待审核";
		} else if (val .equals( "8")) {
			return "注销";
		} else if (val .equals( "9")) {
			return "注销待审核";
		} else if (val .equals( "A")) {
			return "添加初审退回";
		} else if (val .equals( "2")) {
			return "添加终审退回";
		} else if (val .equals( "4")) {
			return "修改审核退回";
		} else if (val .equals( "C")) {
			return "拒绝";
		} else if (val .equals( "D")) {
			return "暂存未提交";
		}
		return val;
	}
}
