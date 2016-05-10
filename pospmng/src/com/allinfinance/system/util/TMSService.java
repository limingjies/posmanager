package com.allinfinance.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.impl.mchnt.TblMchntService;
import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.mchnt.TblMchtBaseInf;

public class TMSService {
	// BMS信息导入
	public String BMSImport(String mchntId, String termId, String sn) throws Exception {
		// 根据id查找相应的数据
		// 取商户信息
		TblMchntService tblMchntService = (TblMchntService) ContextUtil.getBean("tblMchntService");
		TblMchtBaseInf tblMchtBaseInf = tblMchntService.getBaseInf(mchntId);
		// 取终端信息
		T3010BO t3010BO = (T3010BO)ContextUtil.getBean("t3010BO");
		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true));
		
		// http请求用参数
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		
		if(tblMchtBaseInf == null || tblTermInf == null) {
			return "找不到对应的商户或者终端信息！";
		}
		
		// 打开模板excel，将数据写入，另存为临时文件
		HttpServletRequest request = ServletActionContext.getRequest();  
		String basePath = request.getSession().getServletContext().getRealPath("/") ;
		String templateFilePath = basePath + SysParamUtil.getParam(SysParamConstants.TEMPLET_PATH) + "tms/type5.xls";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String time = formatter.format(curDate);
		String fileName = "BMS信息导入" + time + ".xls";
		// 创建可写入的Excel工作簿
		String filePath = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK).replace("\\", "/") 
				+ fileName;
	
		InputStream is = new FileInputStream(templateFilePath); // 打开模板文件		
		Workbook templateWorkBook = Workbook.getWorkbook(is); // 得到工作薄
		WritableWorkbook workBook = Workbook.createWorkbook(new File(filePath), templateWorkBook);
		// 关闭模板
		templateWorkBook.close();
		is.close();
		
		// 写入内容
		WritableSheet ws = workBook.getSheet(0);
		
		// 商户信息
		ws.addCell(new Label(1, 3, tblMchtBaseInf.getMchtNm()));	// 商户名称
		ws.addCell(new Label(2, 3, tblMchtBaseInf.getMchtNo()));	// 商户编号
		ws.addCell(new Label(3, 3, tblMchtBaseInf.getMchtCnAbbr()));	// 商户简称
		ws.addCell(new Label(4, 3, tblMchtBaseInf.getEngName()));	// 英文名称
		ws.addCell(new Label(5, 3, tblMchtBaseInf.getMchtEnAbbr()));	// 英文简称
		ws.addCell(new Label(6, 3, "无"));	// 所属省份
		ws.addCell(new Label(7, 3, "无"));	// 所属城市
		ws.addCell(new Label(8, 3, "无")); 	// 所属城区(县)
		ws.addCell(new Label(9, 3, tblMchtBaseInf.getAddr())); // 商户地址
		ws.addCell(new Label(10, 3, tblMchtBaseInf.getPostCode())); // 邮编
		ws.addCell(new Label(11, 3, tblMchtBaseInf.getContact())); // 发货联系人
		ws.addCell(new Label(12, 3, tblMchtBaseInf.getCommTel())); // 联系电话
		ws.addCell(new Label(13, 3, tblMchtBaseInf.getCommEmail())); // 邮箱
		ws.addCell(new Label(14, 3, tblMchtBaseInf.getMcc())); // 商户类型
		ws.addCell(new Label(15, 3, "应用")); // 所属应用
		ws.addCell(new Label(16, 3, tblMchtBaseInf.getBankNo())); // 所属机构
		
		// 终端信息
		String strPosInfo = getPosInfo(sn);
		String strFacType = "";
		String strAppTmplate = "";
		if (null == strPosInfo || "".equals(strPosInfo)) {
			return "无法获取终端厂商型号等相关信息。";
		}
		strFacType = strPosInfo.split(",")[0];
		strAppTmplate = strPosInfo.split(",")[1];
		ws.addCell(new Label(17, 3, strFacType)); // POS具体型号
		ws.addCell(new Label(18, 3, tblTermInf.getId())); // 终端编号
		ws.addCell(new Label(19, 3, "无")); // 终端地址所属省份
		ws.addCell(new Label(20, 3, "无")); // 终端所属城市
		ws.addCell(new Label(21, 3, "无")); // 终端所属城区(县)
		ws.addCell(new Label(22, 3, tblTermInf.getTermAddr())); // 终端地址
		ws.addCell(new Label(23, 3, tblTermInf.getTermAddr())); // 终端收货地址
		ws.addCell(new Label(24, 3, tblTermInf.getContTel())); // 安装终端联系人
		ws.addCell(new Label(25, 3, tblTermInf.getContTel())); // 终端联系电话
		ws.addCell(new Label(26, 3, getPosType(tblTermInf.getTermTp()))); // POS功能
		
		// 模板信息
		ws.addCell(new Label(27, 3, strAppTmplate)); // 下载模板
		ws.addCell(new Label(28, 3, SysParamUtil.getParam(SysParamConstants.TMS_ONLINE_POLICY))); // 联机策略
		ws.addCell(new Label(29, 3, sn)); // 硬件序列号
		ws.addCell(new Label(30, 3, tblTermInf.getId())); // 逻辑终端号
		ws.addCell(new Label(31, 3, "1")); // 是否主应用
		
		workBook.write();
		workBook.close();

		// 将Excel文件路径（文件名）,还有导入类型（5）作为参数，调用TMS的action
		// importData.action
		parameterMap.put("typeName", "5");
		parameterMap.put("fileName", fileName);
		
		return doRequest(filePath, parameterMap);
	}
	
	// 撤机信息导入
	public String deleteTerm(String termSN, String reason) throws Exception {
		// http请求用参数
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		// 打开模板excel，将数据写入，另存为临时文件
		HttpServletRequest request = ServletActionContext.getRequest();  
		String basePath = request.getSession().getServletContext().getRealPath("/") ;
		String templateFilePath = basePath + SysParamUtil.getParam(SysParamConstants.TEMPLET_PATH) + "tms/type6.xls";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String time = formatter.format(curDate);
		String fileName = "撤机信息导入" + time + ".xls";
		// 创建可写入的Excel工作簿
		String filePath = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK).replace("\\", "/") 
				+ fileName;
	
		InputStream is = new FileInputStream(templateFilePath); // 打开模板文件		
		Workbook templateWorkBook = Workbook.getWorkbook(is); // 得到工作薄
		WritableWorkbook workBook = Workbook.createWorkbook(new File(filePath), templateWorkBook);
		// 关闭模板
		templateWorkBook.close();
		is.close();
		
		// 写入内容
		WritableSheet ws = workBook.getSheet(0);
		
		// 商户信息
		ws.addCell(new Label(0, 3, termSN));	// SN
		ws.addCell(new Label(1, 3, reason));	// 撤机理由
		
		workBook.write();
		workBook.close();

		// 将Excel文件路径（文件名）,还有导入类型（6）作为参数，调用TMS的action
		// importData.action
		parameterMap.put("typeName", "6");
		parameterMap.put("fileName", fileName);
		
		return doRequest(filePath, parameterMap);
	}
		
	// 换机
	public String replaceTerm(String oldTermSN, String newTermSN) throws Exception {

		// http请求用参数
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		if (oldTermSN.equals(newTermSN)) {
			return "SN号相同！";
		}
		parameterMap.put("oldSN", oldTermSN);
		parameterMap.put("newSN", newTermSN);
		parameterMap.put("typeName", "10");
		
		return doRequest("", parameterMap);		
	}
		
		// null 无记录
		// retStrArr[0]-状态 0:入库;1:出库
		// retStrArr[1]-厂商名称
		// retStrArr[2]-型号
		public ArrayList<String> checkSN(String sn) {
			ArrayList<String> retStrArr = null;
			
			String sql = "select status,facshortname, model from tbtermin t1 left join tbfacturer t2 on t1.facid = t2.facid where t1.serialno = '" + sn + "'";
			List list = CommonFunction.getCommTMSQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				retStrArr = new ArrayList<String> ();
				Object[] obj = (Object[]) list.get(0);
				retStrArr.add((String)obj[0]);
				retStrArr.add((String)obj[1]);
				retStrArr.add((String)obj[2]);
			}
			
			return retStrArr;
		}
		
		private String getPosInfo(String sn) {
			
			String sql = "select t1.facid||' '||t1.model||','||t3.TEMPLATEID  from tbtermin t1 left join tbfacturer t2 on t1.facid = t2.facid left join TBAPPTEMPLATE t3 on t1.model = t3.TEMPLATENAME where t1.serialno = '" + sn + "'";
			List list = CommonFunction.getCommTMSQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				
				return list.get(0).toString();
			}
			
			return "";
		}
		
		//0-普通POS，1-财务POS，2-签约POS，3-电话POS，4-MISPOS，5-移动POS，6-网络POS,7-MPOS(即手刷)
		private String getPosType(String typeCode) {
			if ("0".equals(typeCode)) {
				return "普通POS";
			} else if ("1".equals(typeCode)) {
				return "财务POS";
			} else if ("2".equals(typeCode)) {
				return "签约POS";
			} else if ("3".equals(typeCode)) {
				return "电话POS";
			} else if ("4".equals(typeCode)) {
				return "MISPOS";
			} else if ("5".equals(typeCode)) {
				return "移动POS";
			} else if ("6".equals(typeCode)) {
				return "网络POS";
			} else if ("7".equals(typeCode)) {
				return "MPOS(即手刷)";
			}
			return "";
		}
		
		private String doRequest(String filePath, Map parameterMap) throws Exception {
			String result = "";
			HttpRequestor httpRequestor = new HttpRequestor();
			
			String tmsServer = SysParamUtil.getParam(SysParamConstants.TMS_SERVER);
			String tmsPort = SysParamUtil.getParam(SysParamConstants.TMS_PORT);
			String tmsAction = SysParamUtil.getParam(SysParamConstants.TMS_IMPORT_ACTION);
			// 请求地址TMSserver Action
			String url = "http://" + tmsServer + ":" + tmsPort + tmsAction;
			
			parameterMap.put("pospImport", "yes");
			
			if (filePath != null && !"".equals(filePath)) {
				Map<Object, Object> fileMap = new HashMap<Object, Object>();
				
				fileMap.put("fileName", parameterMap.get("fileName"));
				fileMap.put("filePath", filePath);
				fileMap.put("fileFeild", "filePath");
				fileMap.put("fileType", "application/vnd.ms-excel");
				// file upload request
				result = httpRequestor.doPost(url, parameterMap, fileMap);
			} else {
				// normal parameter request
				result = httpRequestor.doPost(url, parameterMap);
			}

		JSONObject jsonObject = JSONObject.fromObject(result);
		result = jsonObject.getString("message");
		if (!"".equals(result)) {
			return result;
		}
		return Constants.SUCCESS_CODE;
	}
}
