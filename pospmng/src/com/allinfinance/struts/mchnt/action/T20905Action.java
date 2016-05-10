package com.allinfinance.struts.mchnt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.mchnt.TblMchntPosfServiceImpl;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.dao.iface.base.TblBrhInfoDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntInfoDAO;
import com.allinfinance.log.Log;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
/*import com.allinfinance.bo.base.T10101BO;
 import com.allinfinance.bo.base.T10214BO;*/
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TMSService;

@SuppressWarnings("serial")
public class T20905Action extends BaseAction {

	public static TblMchntPosfServiceImpl service = (TblMchntPosfServiceImpl) ContextUtil
			.getBean("TblMchntPosfService");
	ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO = (ITblMchtBaseInfTmpTmpDAO) ContextUtil
			.getBean("MchntTmpInfoTmpDAO");
	ITblMchtSettleInfTmpTmpDAO mchtSettleInfTmpTmpDAO = (ITblMchtSettleInfTmpTmpDAO) ContextUtil
			.getBean("MchtSettleInfTmpTmpDAO");
	TblMchntInfoDAO tblMchntInfoDAO = (TblMchntInfoDAO) ContextUtil
			.getBean("MchntInfoDAO");
	// TblOprInfoDAO tblOprInfoDAO=(TblOprInfoDAO)
	// ContextUtil.getBean("TblOprInfoDAO");
	// TblRoleInfDAO tblRoleInfoDAO=(TblRoleInfDAO)
	// ContextUtil.getBean("tblRoleInfoDAO");
	TblBrhInfoDAO tblBrhInfoDAO = (TblBrhInfoDAO) ContextUtil
			.getBean("BrhInfoDAO");
	public String dir = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK)
			.replace("\\", "/");
	// 文件集合
	private List<File> files;
	// 文件名称集合
	private List<String> filesFileName;
	private String result = "";
	private List<String> mchntIds_fail = new ArrayList<String>();
	private List<String> mchntIds_success = new ArrayList<String>();

	// private T10214BO t10214BO = (T10214BO) ContextUtil.getBean("T10214BO");

	@Override
	protected String subExecute() throws Exception {
		String upload = "";
		if ("upload".equals(method)) {
			upload = upload();
			writeSuccessMsg(upload);
		} else if ("singleUpd".equals(method)) {
			upload = singleUpd();
			writeSuccessMsg(upload);
		} else if ("exportData".equals(method)) {
			upload = exportData();
		} else if ("freezMcht".equals(method)) {
			upload = freezMcht();
		} else if ("restoreMcht".equals(method)) {
			upload = restoreMcht();
		}
		return upload;
	}

	private String mchtNo;
	private String orgBrhId;
	private String newBrhId;
	private String serialNo;
	/**
	 * 根据终端SN号查找终端厂商和终端型号
	 * @return 
	 * 查找成功    返回json例如： {"success"：true,"msg":"新大陆|SP60"}
	 * 查找失败    返回json例如： {"success"：false,"msg":"加载所属厂商和终端型号信息失败！"}
	 * 2016年2月29日 下午3:42:58
	 * @author 
	 * @throws IOException 
	 */
	public String findFacBySN() throws IOException{
		TMSService tmsService=new TMSService();
		String msg;
		if(StringUtils.isBlank(serialNo)){
			return "";
		}
		ArrayList<String> list = tmsService.checkSN(serialNo);
		if(list!=null&&list.size()>0){
			msg=list.get(1)+"|"+list.get(2);
			writeSuccessMsg(msg);
			return "";
		}else {
			writeErrorMsg("加载所属厂商和终端型号信息失败,请检查SN号！");
			return "";
		}
	}
	private String exportData() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
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
			whereSql += " AND a.MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
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
			whereSql += " AND a.CONN_TYPE = '"
					+ request.getParameter("connType") + "' ";
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
			whereSql += " AND m.integral = '"
					+ request.getParameter("integral") + "' ";
		}
		if (isNotEmpty(request.getParameter("bankStatement"))) {
			whereSql += " AND m.bank_statement = '"
					+ request.getParameter("bankStatement") + "' ";
		}
		if (isNotEmpty(request.getParameter("compliance"))) {
			whereSql += " AND m.compliance = '"
					+ request.getParameter("compliance") + "' ";
		}
		if (isNotEmpty(request.getParameter("country"))) {
			whereSql += " AND m.country = '" + request.getParameter("country")
					+ "' ";
		}
		Object[] ret = new Object[2];

		String sql = " SELECT a.MCHT_NO,a.MCHT_NM,(SELECT (d.CREATE_NEW_NO||'-'||d.BRH_NAME) from TBL_BRH_INFO d WHERE d.BRH_ID=a.BANK_NO)as bankNo,a.MCHT_GROUP_FLAG,a.LICENCE_NO,"
				+ "  case a.RISL_LVL when '0' then '' else a.RISL_LVL ||(select distinct '-'||b.RESVED from TBL_RISK_LVL b where b.RISK_LVL=a.RISL_LVL) end as risk_lvl_name,"
				+ " a.MCHT_STATUS,M.INTEGRAL,M.BANK_STATEMENT,M.COUNTRY,M.COMPLIANCE, "
				+ " nvl((select count(*)  from TBL_TERM_INF_TMP b where A.MCHT_NO = B.MCHT_CD ),0) as v2,"
				+ " nvl((select count(*) from TBL_TERM_INF_TMP c WHERE a.MCHT_NO = c.MCHT_CD ),0) AS term_tmp_count "
				+ " FROM TBL_MCHT_BASE_INF_TMP_TMP A left join (select * from TBL_MCHNT_REFUSE where TXN_TIME in (select max(TXN_TIME) from TBL_MCHNT_REFUSE group by MCHNT_ID)) g ON a.MCHT_NO = g.MCHNT_ID "
				+ "  left join TBL_MCHT_SETTLE_INF_TMP_TMP M on M.MCHT_NO = A.MCHT_NO"
				+ whereSql + " ORDER BY a.rec_upd_ts desc";

		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF_TMP_TMP a left join TBL_MCHT_SETTLE_INF_TMP_TMP M on M.MCHT_NO = A.MCHT_NO "
				+ whereSql;

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, 0, Integer.parseInt(count));
		try {

			WritableWorkbook wwb = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String time = formatter.format(curDate);
			// 创建可写入的Excel工作簿
			String fileName = dir + File.separator + "商户信息报表" + time + ".xls";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
			}
			// 以fileName为文件名来创建一个Workbook
			wwb = jxl.Workbook.createWorkbook(file);
			// 创建工作表
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			ws.setColumnView(0, 20);
			ws.setColumnView(1, 20);
			ws.setColumnView(2, 20);
			ws.setColumnView(3, 20);
			ws.setColumnView(4, 20);
			ws.setColumnView(5, 20);
			ws.setColumnView(6, 20);
			ws.setColumnView(7, 20);
			ws.setColumnView(8, 20);
			ws.setColumnView(9, 20);
			ws.setColumnView(10, 20);
			ws.setColumnView(11, 20);
			ws.setColumnView(12, 20);
			ws.setName("商户信息");
			// 要插入到的Excel表格的行号，默认从0开始
			Label mchtNo_head = new Label(0, 0, "商户编号");// 表示第
			Label cardNo_head = new Label(1, 0, "商户名称");
			Label authNo_head = new Label(2, 0, "合作伙伴");
			Label tradeConsultNo_head = new Label(3, 0, "商户种类");
			Label tradeType_head = new Label(4, 0, "营业执照编号");
			Label money_head = new Label(5, 0, "风险级别");
			Label approveStatus_head = new Label(6, 0, "商户状态");
			Label approveOpr_head = new Label(7, 0, "是否积分");
			Label approveTime_head = new Label(8, 0, "是否对账单");
			Label approveAdvice_head = new Label(9, 0, "是否完全合规");
			Label postStatus_head = new Label(10, 0, "是否县乡");
			Label postTime_head = new Label(11, 0, "终端数量");
			Label reserver_head = new Label(12, 0, "终端添加状态");

			ws.addCell(mchtNo_head);
			ws.addCell(cardNo_head);
			ws.addCell(authNo_head);
			ws.addCell(tradeConsultNo_head);
			ws.addCell(tradeType_head);
			ws.addCell(money_head);
			ws.addCell(approveStatus_head);
			ws.addCell(approveOpr_head);
			ws.addCell(approveTime_head);
			ws.addCell(approveAdvice_head);
			ws.addCell(postStatus_head);
			ws.addCell(postTime_head);
			ws.addCell(reserver_head);

			for (int i = 0; i < dataList.size(); i++) {
				String mchtGroupFlag = dataList.get(i)[3] == null ? ""
						: dataList.get(i)[3] + "";
				mchtGroupFlag = mchtGroupFlagExchange(mchtGroupFlag);
				String mchtStatus = dataList.get(i)[6] == null ? " " : dataList
						.get(i)[6] + "";
				mchtStatus = mchtStaExchange(mchtStatus.substring(0, 1));
				String integral = dataList.get(i)[7] == null ? "" : dataList
						.get(i)[7] + "";
				integral = yesOrNoExchange(integral);
				String bankStatement = dataList.get(i)[8] == null ? ""
						: dataList.get(i)[8] + "";
				bankStatement = yesOrNoExchange(bankStatement);
				String compliance = dataList.get(i)[9] == null ? "" : dataList
						.get(i)[9] + "";
				compliance = yesOrNoExchange(compliance);
				String country = dataList.get(i)[10] == null ? "" : dataList
						.get(i)[10] + "";
				country = yesOrNoExchange(country);
				String termTmpCount = dataList.get(i)[12] == null ? ""
						: dataList.get(i)[12] + "";
				termTmpCount = termAddSta(termTmpCount);

				Label mchtNo_i = new Label(0, i + 1, dataList.get(i)[0] + "");
				Label mchtNm_i = new Label(1, i + 1,
						dataList.get(i)[1] == null ? "" : dataList.get(i)[1]
								+ "");
				Label bankNo1_i = new Label(2, i + 1,
						dataList.get(i)[2] == null ? "" : dataList.get(i)[2]
								+ "");
				Label mchtGroupFlag_i = new Label(3, i + 1, mchtGroupFlag);
				Label licenceNo_i = new Label(4, i + 1,
						dataList.get(i)[4] == null ? "" : dataList.get(i)[4]
								+ "");
				Label rislLvl_i = new Label(5, i + 1,
						dataList.get(i)[5] == null ? "" : dataList.get(i)[5]
								+ "" + "");
				Label mchtStatus_i = new Label(6, i + 1, mchtStatus);
				Label integral_i = new Label(7, i + 1, integral);
				Label bankStatement_i = new Label(8, i + 1, bankStatement);
				Label compliance_i = new Label(9, i + 1, compliance);
				Label country_i = new Label(10, i + 1, country);
				Label termCount_i = new Label(11, i + 1,
						dataList.get(i)[11] == null ? "" : dataList.get(i)[11]
								+ "");
				Label termTmpCount_i = new Label(12, i + 1, termTmpCount);

				ws.addCell(mchtNo_i);
				ws.addCell(mchtNm_i);
				ws.addCell(bankNo1_i);
				ws.addCell(mchtGroupFlag_i);
				ws.addCell(licenceNo_i);
				ws.addCell(rislLvl_i);
				ws.addCell(mchtStatus_i);
				ws.addCell(integral_i);
				ws.addCell(bankStatement_i);
				ws.addCell(compliance_i);
				ws.addCell(country_i);
				ws.addCell(termCount_i);
				ws.addCell(termTmpCount_i);

			}
			// 写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();
			writeUsefullMsg(fileName);
			writeSuccessMsg("数据下载成功.");
			// return "数据下载成功.";
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("数据下载失败,请稍后重试！");
			// return "数据下载失败,请稍后重试！";
		}
		return "00";
	}

	/**
	 * 单个变更
	 * 
	 * @return
	 * @throws Exception
	 */
	private String singleUpd() throws Exception {
		if (StringUtils.isEmpty(mchtNo) || StringUtils.isEmpty(orgBrhId)
				|| StringUtils.isEmpty(newBrhId)) {
			return "商户或签约机构不存在！";
		}
		List<String> mchtList = new ArrayList<String>();
		mchtList.add(mchtNo);
		boolean adjust = adjust(orgBrhId, newBrhId);
		if (!adjust) {
			return "<font color=red>新签约机构缺少分润档位！</font>";
		}
		editToUpd(mchtList, orgBrhId, newBrhId);
		String success = "";
		String fail = "";
		for (String string : mchntIds_success) {
			string = "<font color=green>" + string + "</font>";
			success = success + string + "<div/>";
		}
		for (String string : mchntIds_fail) {
			string = "<font color=red>" + string + "</font>";
			fail = fail + string + "<div/>";
		}
		if ("".equals(fail)) {
			result = "<font  size=2>商户变更成功！</font>" + "<div/>";
		} else
			result = "<font  size=2>商户变更失败！</font>" + "<div/>" 
					+ "<div/>" + fail;
		// ServletActionContext.getResponse().getWriter().write("修改成功："+success+";"+"修改失败："+fail);
		return result;
	}

	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNo
	 *            the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	/**
	 * @return the orgBrhId
	 */
	public String getOrgBrhId() {
		return orgBrhId;
	}

	/**
	 * @param orgBrhId
	 *            the orgBrhId to set
	 */
	public void setOrgBrhId(String orgBrhId) {
		this.orgBrhId = orgBrhId;
	}

	/**
	 * @return the newBrhId
	 */
	public String getNewBrhId() {
		return newBrhId;
	}

	/**
	 * @param newBrhId
	 *            the newBrhId to set
	 */
	public void setNewBrhId(String newBrhId) {
		this.newBrhId = newBrhId;
	}

	/**
	 * 批量变更
	 * 
	 * @return
	 * @throws Exception
	 */
	private String upload() throws Exception {
		try {
			// 使用Apache POI解析Excel文件
			Workbook workbook = null;
			Sheet sheet = null;

			String filename = filesFileName.get(0);
			String filetype = StringUtils.substringAfterLast(filename, ".");

			if (filetype.equals("xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(files.get(0)));
				sheet = workbook.getSheetAt(0);// 读取第一个sheet
			} else {
				workbook = null;
				sheet = null;
				workbook = new XSSFWorkbook(new FileInputStream(files.get(0)));
				sheet = workbook.getSheetAt(0);// 读取第一个sheet
			}
			String orgBrh = new String();
			String newBrh = new String();
			List<String> mchtList = new ArrayList<String>();
			int i = 0;
			for (Row row : sheet) {// 判断所有记录新、旧合作伙伴是否一致
				int rowNum = row.getRowNum();
				if (rowNum == 0) {
					continue;
				}
				i++;
				if (row.getCell(0) == null || row.getCell(1) == null
						|| row.getCell(2) == null) {
					continue;
				}
				// 获得原签约机构
				String oldvalue = new String();
				try {
					oldvalue = row.getCell(1).getStringCellValue();
					if (StringUtils.isEmpty(oldvalue)) {
						continue;
					}
				} catch (Exception e2) {
					oldvalue = String.valueOf(row.getCell(1)
							.getNumericCellValue());
				}
				String[] strings = StringUtils.split(oldvalue, "-");
				String brh_id = strings[0];// 原签约机构id
				// 获得新签约机构
				String newvalue;
				try {
					newvalue = row.getCell(2).getStringCellValue();
					if (StringUtils.isEmpty(newvalue)) {
						continue;
					}
				} catch (Exception e1) {
					newvalue = String.valueOf(row.getCell(2)
							.getNumericCellValue());
				}

				String[] new_strings = StringUtils.split(newvalue, "-");
				String new_brh_id = new_strings[0];// 签约机构id
				if (StringUtils.isEmpty(brh_id)
						|| StringUtils.isEmpty(new_brh_id)) {// 合作伙伴号出现空值
					return "第" + i + "条记录的合作伙伴号为空或不正确！";
				}
				if (brh_id.length() != 8
						|| new_brh_id.length() != 8) {// 合作伙伴号不是8位
					return "第" + i + "条记录的合作伙伴号不是8位！";
				}
				if (rowNum == 1) {
					orgBrh = brh_id;
					newBrh = new_brh_id;
				} else {
					if (!(orgBrh.equals(brh_id) && newBrh.equals(new_brh_id))) {
						return "第" + i + "条记录的签约机构号与前边记录不一致！";
					}
				}
				// 获得商户信息编号
				String mchtNo = new String();
				try {
					mchtNo = row.getCell(0).getStringCellValue();
					if (mchtNo.equals("0.0")) {
						continue;
					}
				} catch (Exception e3) {
					mchtNo = String.valueOf(row.getCell(0)
							.getNumericCellValue());
				}
				mchtList.add(mchtNo);
			}
			// 两个合作伙伴的分润信息一致性判断
			TblBrhInfo orgBrhInfo = tblBrhInfoDAO.getByCreateNewNo(orgBrh.trim());
			TblBrhInfo newBrhInfo = tblBrhInfoDAO.getByCreateNewNo(newBrh.trim());
			if (newBrhInfo == null) {
				return "新签约机构不存在！";
			} else if (orgBrhInfo == null) {
				return "原签约机构不存在！";
			}
			orgBrh = orgBrhInfo.getBrhId();
			newBrh = newBrhInfo.getBrhId();
			
			boolean adjust = adjust(orgBrh, newBrh);
			if (!adjust) {
				return "新签约机构缺少分润档位！";
			}
			editToUpd(mchtList, orgBrh, newBrh);

			String success = "";
			String fail = "";
			for (String string : mchntIds_success) {
				string = "<font color=green>" + string + "</font>";
				success = success + string + "<div/>";
			}
			for (String string : mchntIds_fail) {
				string = "<font color=red>" + string + "</font>";
				fail = fail + string + "<div/>";
			}
			if ("".equals(fail)) {
				result = "<font  size=2>商户变更成功！</font>" + "<div/>";
			} else if(mchntIds_success.size()>0){
				result = "<font  size=2>商户变更成功！</font>" + "<div/>" + "变更失败的商户："
						+ "<div/>" + fail;
			}else result = "<font  size=2>商户变更失败！</font>" + "<div/>" + "变更失败的商户："
					+ "<div/>" + fail;
			// ServletActionContext.getResponse().getWriter().write("修改成功："+success+";"+"修改失败："+fail);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return "服务器异常，请稍后重试！";
		}

	}

	/**
	 * 变更商户信息
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void editToUpd(List<String> mchtList, String orgBrh, String newBrh)
			throws Exception {

		for (String mchtNo : mchtList) {
			String mchntId = mchtNo;
			// 根据id获得商户信息
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp = tblMchtBaseInfTmpTmpDAO
					.get(mchntId);
			if (tblMchtBaseInfTmpTmp == null
					|| !tblMchtBaseInfTmpTmp.getMchtStatus().equals("0")) {
				mchntIds_fail.add(mchntId + "</div>原因：商户不存在或未过终审！");
				continue;
			}
			TblMchtSettleInfTmpTmp tmpSettle = mchtSettleInfTmpTmpDAO
					.get(mchtNo);
			if (tmpSettle == null || tmpSettle.getOpenStlno() == null) {
				mchntIds_fail.add("</div>原因：获取商户清算信息失败。");
				continue;
			}
			String sql = "select b.subbranch_id,b.bank_name,b.subbranch_name,b.province,b.city from tbl_subbranch_info b where b.subbranch_id = '"
					+ tmpSettle.getOpenStlno() + "'";
			List retList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			Object[] objBank = null;
			if (null == retList || retList != null && retList.size() < 1) {
				mchntIds_fail.add("</div>原因：获取清算账户信息失败。");
				continue;
			} else {
				objBank = (Object[]) retList.get(0);
			}

			
			// 获得操作员信息
			Operator operator = (Operator) ServletActionContext.getRequest()
					.getSession().getAttribute(Constants.OPERATOR_INFO);
			String oprId = operator.getOprId();
			// TblBrhInfo tblBrhInfo = tblBrhInfoDAO.get(oprBrhId);

			String settleType = "";
			String tnN = "";
			String weekDay = "";
			String monthDay = "";
			// 取得结算类型---开始
			if (!"".equals(tblMchtBaseInfTmpTmp.getMchtFunction())) {
				String mixStr = tblMchtBaseInfTmpTmp.getMchtFunction();
				String type = mixStr.substring(0, 1);
				if ("0".equals(type)) {
					// T+N
					settleType = "10";
					tnN = String.valueOf(Integer.parseInt(mixStr
							.substring(2, 5)));
					if("0".equals(tnN)){ //T+0
						settleType = "03";
						tnN = "";
					}
				} else if ("1".equals(type)) {
					String periodType = mixStr.substring(1, 2);
					// 周结
					if ("0".equals(periodType)) {
						settleType = "11";
						weekDay = "1";
						// 月结
					} else if ("1".equals(periodType)) {
						settleType = "12";
						monthDay = "01";
						// 季结
					} else if ("2".equals(periodType)) {
						settleType = "13";
					}
				}
			}
			// 取得结算类型---结束
			String bankNo = tblMchtBaseInfTmpTmp.getBankNo();
			TblMchtBaseInfTmp baseInfTmp = new TblMchtBaseInfTmp(
					String.valueOf(mchtNo));
			TblMchtBaseInf baseInf = new TblMchtBaseInf(String.valueOf(mchtNo));

			// 商户bankno是否和文件中所写是否一致
			if (!orgBrh.equals(bankNo)) {
				mchntIds_fail.add(mchntId + "</div>原因：原签约机构错误！");
				continue;
			}

			try {
				tblMchtBaseInfTmpTmp.setBankNo(newBrh);
				tblMchtBaseInfTmpTmp.setSignInstId(newBrh);
				tblMchtBaseInfTmpTmp.setAgrBr(newBrh);
				tblMchtBaseInfTmpTmp.setUpdOprId(oprId);
				tblMchtBaseInfTmpTmp.setRecUpdTs(CommonFunction
						.getCurrentDateTime());
				BeanUtils.copyProperties(baseInfTmp, tblMchtBaseInfTmpTmp);
				BeanUtils.copyProperties(baseInf, tblMchtBaseInfTmpTmp);

				// 发送报文更新商户信息
				// 发送报文
				Log.log("开始发送商户信息更新报文");
				Msg reqBody = MsgEntity.genMchtUpdRequestBodyMsg();
				Msg reqHead = MsgEntity.genCommonRequestHeadMsg("F0100", "0");
				// reqHead.getField(1).setValue("00");// 版本号
				// reqHead.getField(2).setValue("F0100");// 交易码
				reqHead.getField(3).setValue("0");// 子交易码
				reqHead.getField(8).setValue("0000000000");// 接入方交易码
				reqHead.getField(9).setValue("");// 交易类型+接入方检索参考号
				reqHead.getField(11).setValue("");// 请求方交易日期
				reqHead.getField(12).setValue("");
				reqHead.getField(14).setValue("");
				reqHead.getField(15).setValue("");
				reqHead.getField(16).setValue("");
				reqHead.getField(17).setValue(tblMchtBaseInfTmpTmp.getMchtNo());// 外部账号
				reqHead.getField(18).setValue(" ");// 外部账号类型
				reqHead.getField(20).setValue("");
				reqHead.getField(21).setValue("");
				reqHead.getField(22).setValue("");
				reqHead.getField(23).setValue("");
				reqHead.getField(24).setValue("");
				reqHead.getField(25).setValue("+");
				reqHead.getField(26).setValue("0");// 交易额
				reqHead.getField(27).setValue("");
				reqHead.getField(28).setValue("");
				reqHead.getField(28).setValue("");

				reqBody.getField(1).setValue(tblMchtBaseInfTmpTmp.getMchtNo());
				reqBody.getField(2)
						.setValue(tblMchtBaseInfTmpTmp.getEtpsAttr());
				reqBody.getField(3).setValue(tblMchtBaseInfTmpTmp.getMchtNm());
				reqBody.getField(4).setValue(tblMchtBaseInfTmpTmp.getBankNo()); // 所属合作伙伴编号
				reqBody.getField(5).setValue(tblMchtBaseInfTmpTmp.getRislLvl()); // 商户风险级别
				reqBody.getField(6).setValue("0"); // 商户状态
				reqBody.getField(7).setValue(tblMchtBaseInfTmpTmp.getAddr()); // 地址
				reqBody.getField(8)
						.setValue(tblMchtBaseInfTmpTmp.getPostCode()); // 邮编
				reqBody.getField(9).setValue(tblMchtBaseInfTmpTmp.getContact()); // 联系人姓名
				reqBody.getField(10).setValue(
						tblMchtBaseInfTmpTmp.getCommMobil()); // 联系人移动电话
				reqBody.getField(11)
						.setValue(tblMchtBaseInfTmpTmp.getCommTel()); // 联系人固定电话
				reqBody.getField(12).setValue(
						tblMchtBaseInfTmpTmp.getCommEmail()); // 联系人电子信箱
				reqBody.getField(13).setValue(""); // 联系人传真
				reqBody.getField(14).setValue("1"); // 申请类别1 –POSP系统发起
				reqBody.getField(15).setValue((String) objBank[1]); // 结算银行名称
				reqBody.getField(16).setValue((String) objBank[0]); // 结算账号开户行行号
				reqBody.getField(17).setValue((String) objBank[2]); // 结算账号开户行名称
				reqBody.getField(18).setValue(
						tmpSettle.getSettleAcct().substring(0, 1)); // 结算账号类型
				reqBody.getField(19).setValue(
						tmpSettle.getSettleAcct().substring(1)); // 结算账号
				reqBody.getField(20).setValue(tmpSettle.getSettleAcctNm()); // 结算账号户名
				reqBody.getField(21).setValue(settleType); // 结算方式
				reqBody.getField(22).setValue(tnN); // 只填写N的值。如T+1填写1结算方式为10时必填
				reqBody.getField(23).setValue(""); // 周一填写1。。。周日填写7结算方式为11时必填
				reqBody.getField(24).setValue(""); // 介于01-28之间。不可填写29.30.31结算方式为12时必填
				reqBody.getField(25).setValue((String) objBank[3]); // 户省份（和结算账号开户行对应）
				reqBody.getField(26).setValue((String) objBank[4]); // 开户城市（和结算账号开户行对应）
				reqBody.getField(27).setValue("0000");		// 垫资日息 填写整数，万分之10，填写0010。(N4)
				reqBody.getField(28).setValue("00000000");	// 代付手续费 分为单位，无小数点 (N8)
				reqBody.getField(29).setValue("   "); 		// 暂时不用，填写全空格即可(AN3)
				
				String secretKey = "1111111111111111";

				String reqStr = MsgEntity.genCompleteRequestMsg(reqHead,
						reqBody, secretKey);
				Msg respHead = MsgEntity.genCommonResponseHeadMsg();
				Msg respBody = MsgEntity.genCommonResponseBodyMsg();

				Log.log(reqStr);
				byte[] bufMsg = MsgEntity.sendMessage(reqStr);
				String strRet = new String(bufMsg, "gb2312");
				Log.log(strRet);
				MsgEntity.parseResponseMsg(bufMsg, respHead, respBody);
				String respCode = respHead.getField(28).getRealValue();
				String responseResult = "";
				if ("0000".equals(respCode)) { // 交易成功
					service.saveAndDelete(baseInfTmp, mchntId,
							tblMchtBaseInfTmpTmp, baseInf);
					mchntIds_success.add(mchntId);
					continue;
				} else {
					responseResult = respBody.getField(1).getRealValue();
					mchntIds_fail.add(mchntId + "</div>原因：系统错误！");
					log("商户" + mchntId + "报文异常信息：" + responseResult);
					continue;
				}

			} catch (Exception e) {
				log(e.getMessage());
				mchntIds_fail.add(mchntId + "</div>原因：系统错误！");
			}
		}
	}

	private String restoreMcht() {
		try{
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp = tblMchtBaseInfTmpTmpDAO
					.get(mchtNo);
			TblMchtBaseInfTmp baseInfTmp = new TblMchtBaseInfTmp(
					String.valueOf(mchtNo));
			TblMchtBaseInf baseInf = new TblMchtBaseInf(String.valueOf(mchtNo));
	
			tblMchtBaseInfTmpTmp.setMchtStatus("0");
			
			BeanUtils.copyProperties(baseInfTmp, tblMchtBaseInfTmpTmp);
			BeanUtils.copyProperties(baseInf, tblMchtBaseInfTmpTmp);
	
	
			service.saveAndDelete(baseInfTmp, mchtNo,
						tblMchtBaseInfTmpTmp, baseInf);
		} catch (Exception e) {
			log(e.getMessage());
			return mchtNo + "</div>原因：系统错误！";
		}
		return Constants.SUCCESS_CODE_CUSTOMIZE + "<font  size=2>商户恢复成功！</font>" + "<div/>";
	}

	private String freezMcht() {
		try{
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp = tblMchtBaseInfTmpTmpDAO
					.get(mchtNo);
			TblMchtBaseInfTmp baseInfTmp = new TblMchtBaseInfTmp(
					String.valueOf(mchtNo));
			TblMchtBaseInf baseInf = new TblMchtBaseInf(String.valueOf(mchtNo));
	
			tblMchtBaseInfTmpTmp.setMchtStatus("6");
			
			BeanUtils.copyProperties(baseInfTmp, tblMchtBaseInfTmpTmp);
			BeanUtils.copyProperties(baseInf, tblMchtBaseInfTmpTmp);
	
	
			service.saveAndDelete(baseInfTmp, mchtNo,
						tblMchtBaseInfTmpTmp, baseInf);
		} catch (Exception e) {
			log(e.getMessage());
			return mchtNo + "</div>原因：系统错误！";
		}
		return Constants.SUCCESS_CODE_CUSTOMIZE + "<font  size=2>商户冻结成功！</font>" + "<div/>";
	}
	
	private boolean adjust(String orgAgentNo, String newAgentNo) {
		String newSql = getSql(newAgentNo.trim());
		String orgSql = getSql(orgAgentNo.trim());
		@SuppressWarnings("unchecked")
		List<String> newRateList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(newSql);
		@SuppressWarnings("unchecked")
		List<String> orgRateList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(orgSql);
		if (newRateList.size() != orgRateList.size()) {
			return false;
		}
		;
		for (int i = 0; i < orgRateList.size(); i++) {
			if (!newRateList.get(i).equals(orgRateList.get(i))) {
				return false;
			}
			;
		}
		return true;
	}

	private String getSql(String agentNo) {
		String sql = "	SELECT " + "     tari.rate_id " + " FROM "
				+ "     tbl_agent_rate_info tari " + " WHERE "
				+ "     tari.disc_id = ( " + "         SELECT "
				+ "             disc_id  " + "         FROM "
				+ "             tbl_agent_fee_cfg tafc   " + "         where "
				+ "              tafc.agent_no ='" + agentNo + "'  "
				+ "     )  " + " order by " + "     tari.rate_id  ";
		return sql;
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

	/**
	 * 商户状态转换
	 * 
	 * @param val
	 * @return
	 */
	private String mchtStaExchange(String val) {
		if (val.equals("0")) {
			return "正常";
		} else if (val.equals("1")) {
			return "添加待终审";
		} else if (val.equals("B")) {
			return "添加待初审";
		} else if (val.equals("3")) {
			return "正常";// 变更待初审
		} else if (val.equals("5")) {
			return "冻结待审核";
		} else if (val.equals("6")) {
			return "冻结";
		} else if (val.equals("7")) {
			return "恢复待审核";
		} else if (val.equals("8")) {
			return "注销";
		} else if (val.equals("9")) {
			return "注销待审核";
		} else if (val.equals("A")) {
			return "添加初审退回";
		} else if (val.equals("2")) {
			return "添加终审退回";
		} else if (val.equals("4")) {
			return "正常";// 变更待终审
		} else if (val.equals("C")) {
			return "拒绝";
		} else if (val.equals("I")) {
			return "批量录入待审核";
		} else if (val.equals("D")) {
			return "暂存未提交";
		} else if (val.equals("E")) {
			return "正常";// 变更初审退回
		} else if (val.equals("F")) {
			return "正常";// 变更终审退回
		}
		return val;
	}

	/**
	 * 0、1转换是或否
	 * 
	 * @param val
	 * @return
	 */
	private String yesOrNoExchange(String val) {
		if ("0".equals(val)) {
			return "是";
		} else
			return "否";
	}

	/**
	 * 商户种类转换
	 * 
	 * @param val
	 * @return
	 */
	private String mchtGroupFlagExchange(String val) {
		if ("1".equals(val)) {
			return "普通商户";
		}
		if ("2".equals(val)) {
			return "集团商户";
		}
		if ("3".equals(val)) {
			return "集团子商户";
		}
		return val;
	}

	/**
	 * 终端添加状态
	 * 
	 * @param val
	 * @return
	 */
	private String termAddSta(String val) {
		if ("0".equals(val)) {
			return "未添加";
		} else {
			return "已添加";
		}
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files
	 *            the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}

	/**
	 * @return the filesFileName
	 */
	public List<String> getFilesFileName() {
		return filesFileName;
	}

	/**
	 * @param filesFileName
	 *            the filesFileName to set
	 */
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
