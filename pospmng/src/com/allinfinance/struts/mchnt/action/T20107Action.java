package com.allinfinance.struts.mchnt.action;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.allinfinance.bo.mchnt.T20107BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.RiskConstants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.Regex;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T20107Action extends BaseAction {
	
	private T20107BO t20107BO = (T20107BO) ContextUtil.getBean("T20107BO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("uploadFile".equals(method)) {
			log("批量录入商户信息");
			rspCode = uploadFile();
		} else if("downloadRetFile".equals(method)) {
			log("下载批导反馈文件");
			rspCode = downloadRetFile();
		}
		return rspCode;
	}
	
	/**
	 * 批量录入商户信息
	 * @return
	 * 2010-8-26下午11:59:38
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private String uploadFile() throws Exception {
		
			HSSFWorkbook workbook = null;
			HSSFSheet sheet = null;
			HSSFRow row = null;
			// 文件名称索引
			int fileNameIndex = 0;
			// 文件名称
			String fileName = null;
			// 待执行SQL查询语句
			String sql = null;
			// 存放查询结果集
			List<Object[]> dataList = null;
			// 预设dataList迭代器
			Iterator<Object[]> iterator = null;

		// 法人代表证件类型合集
			Map<String, String> artifCertifTpMap = new HashMap<String, String>();
			// 准备查询语句
			sql = "SELECT VALUE,KEY FROM CST_SYS_PARAM WHERE OWNER = 'CERTIFICATE'";
			// 执行查询，获取结果集
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			// 遍历结果集，存放数据
			iterator = dataList.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				artifCertifTpMap.put(obj[0].toString().trim(), obj[1].toString().trim());
			}
		// 商户风险等级合集
			Map<String, String> rislLvlMap = new HashMap<String, String>();
			// 准备查询语句
			sql = "SELECT DISTINCT trim(RESVED),trim(RISK_LVL) FROM TBL_RISK_LVL";
			// 执行查询，获取结果集
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			// 遍历结果集，存放数据
			iterator = dataList.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				if(!RiskConstants.RISK_LVL_WHITE_MCHT.equals(obj[1].toString().trim()))
					rislLvlMap.put(obj[0].toString().trim(), obj[1].toString().trim());
			}
		// 商户所属机构合集
			Map<String, String> cupBrhIdMap = new HashMap<String, String>();
			// 准备查询语句
			sql = "select BRH_ID,CUP_BRH_ID FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1','2')";
			// 执行查询，获取结果集
			dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			// 遍历结果集，存放数据
			iterator = dataList.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				cupBrhIdMap.put(obj[0].toString().trim(), obj[1].toString().trim());
			}
			
			// 存放临时商户基本信息数据集
			List<TblMchtBaseInfTmp> mchtBaseInfList = new ArrayList<TblMchtBaseInfTmp>();
			List<TblMchtSettleInfTmp> mchtSettleInfList = new ArrayList<TblMchtSettleInfTmp>();
			TblMchtBaseInfTmp tblMchtBaseInfTmp = null;
			TblMchtSettleInfTmp tblMchtSettleInfTmp = null;
			FileInputStream fileInputStream = null;
			//MCC-diskCd映射
			Map<String, String> mccdiskCdMap = new HashMap<String, String>();
			//未加费率商户-计费代码映射
			Map<String, TblHisDiscAlgo> mchtdiskCdMap = new HashMap<String, TblHisDiscAlgo>();
			// 终端电话索引
			int termTelIndex = 1;
			//终端电话索引-终端电话合集映射
			Map<String, String[]> termTelMap = new HashMap<String, String[]>();
			//验证信息不通过返回值
			String retMsg = null;

			for(File file : xlsFile) {
				
				fileInputStream = new FileInputStream(file);
				
				workbook = new HSSFWorkbook(fileInputStream);
				
				sheet = workbook.getSheetAt(0);
				
				fileName = xlsFileFileName.get(fileNameIndex);
				
				if(sheet.getLastRowNum() == 0)
					retMsg = "文件[ " + fileName + " ]中没有待导入的商户信息！";
				
				for(int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					
					row = sheet.getRow(rowIndex);
					if(row == null)
						break;

					// 验证是否为空行
					if(row.getCell(0) == null){
						retMsg = "文件[ " + fileName + "]，第" + (row.getRowNum() + 1) + "行为空行，请删除后再执行导入！";
						break;
					}
					
					// 验证公司注册名称
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(0).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，公司注册名称单元格式为文本格式<br>";
						break;
					}
					String mchtNm = row.getCell(0).getStringCellValue();
					if(!Regex.M1(mchtNm)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，公司注册名称不符合规则<br>";
						break;
					}
					
					// 验证公司注册地址
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(1).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，公司注册地址单元格式为文本格式<br>";
						break;
					}
					String compaddr = row.getCell(1).getStringCellValue();
					if(!Regex.M1(compaddr)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，公司注册地址不符合规则<br>";
						break;
					}
					
					// 验证营业执照编号
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(2).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，营业执照编号单元格式为文本格式<br>";
						break;
					}
					String licenceNo = row.getCell(2).getStringCellValue();
					if(!Regex.M2(licenceNo)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，营业执照编号不符合规则<br>";
						break;
					}
					
					// 验证税务登记证号码
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(3).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，税务登记证号码单元格式为文本格式<br>";
						break;
					}
					String faxNo = row.getCell(3).getStringCellValue();
					if("无".equals(faxNo)){
						faxNo = "0";
					}
					else if(!Regex.M2(faxNo)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，税务登记证号码不符合规则<br>";
						break;
					}
					
					// 验证法人代表
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(4).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，法人代表单元格式为文本格式<br>";
						break;
					}
					String manager = row.getCell(4).getStringCellValue();
					if(!Regex.M4(manager)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，法人代表不符合规则<br>";
						break;
					}
					
					// 验证法人代表证件类型
					String artifCertifTp = row.getCell(5).getStringCellValue();
					if(!artifCertifTpMap.containsKey(artifCertifTp)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，法人代表证件类型不存在<br>";
						break;
					}
					// 设法人代表证件类型值
					artifCertifTp = artifCertifTpMap.get(artifCertifTp);
					
					// 验证法人代表证件号码
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(6).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，法人代表证件号码单元格式为文本格式<br>";
						break;
					}
					String identityNo = row.getCell(6).getStringCellValue();
					if(!Regex.M5(identityNo)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，法人代表证件号码不符合规则<br>";
						break;
					}

					// 验证门店经营名称
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(7).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，门店经营名称单元格式为文本格式<br>";
						break;
					}
					String compNm = row.getCell(7).getStringCellValue();
					if(!Regex.M1(compNm)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，门店经营名称不符合规则<br>";
						break;
					}

					// 验证门店地址
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(8).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，门店地址单元格式为文本格式<br>";
						break;
					}
					String addr = row.getCell(8).getStringCellValue();
					if(!Regex.M1(addr)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，门店地址不符合规则<br>";
						break;
					}

					// 验证业务联系人
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(9).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，业务联系人单元格式为文本格式<br>";
						break;
					}
					String contact = row.getCell(9).getStringCellValue();
					if(!Regex.M6(contact)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，业务联系人不符合规则<br>";
						break;
					}

					// 验证业务联系人电话
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(10).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，业务联系人电话单元格式为文本格式<br>";
						break;
					}
					String commTel = row.getCell(10).getStringCellValue();
					if(!Regex.M7(commTel)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，业务联系人电话不符合规则<br>";
						break;
					}

					// 验证财务联系人
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(11).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，财务联系人单元格式为文本格式<br>";
						break;
					}
					String finacontact = row.getCell(11).getStringCellValue();
					if(!Regex.M6(finacontact)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，财务联系人不符合规则<br>";
						break;
					}

					// 验证财务联系人电话
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(12).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，财务联系人电话单元格式为文本格式<br>";
						break;
					}
					String finacommTel = row.getCell(12).getStringCellValue();
					if(!Regex.M7(finacommTel)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，财务联系人电话不符合规则<br>";
						break;
					}

					// 验证经营内容
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(13).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，经营内容单元格式为文本格式<br>";
						break;
					}
					String busInfo = row.getCell(13).getStringCellValue();
					if(!Regex.M6(busInfo)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，经营内容不符合规则<br>";
						break;
					}

					// 验证经营面积
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(14).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，经营面积单元格式为文本格式<br>";
						break;
					}
					String busArea = row.getCell(14).getStringCellValue();
					if(!Regex.M8(busArea)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，经营面积不符合规则<br>";
						break;
					}
					
					// 验证月平均营业额
//					String monaveTrans = row.getCell(15).getStringCellValue();
					
					// 验证单笔平均交易额
//					String sigaveTrans = row.getCell(16).getStringCellValue();
					
					// 验证帐户名
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(17).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，帐户名单元格式为文本格式<br>";
						break;
					}
					String settleAcctNm = row.getCell(17).getStringCellValue();
					if(!Regex.M9(settleAcctNm)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，帐户名不符合规则<br>";
						break;
					}
					
					// 验证是否为对公账户（clearType）
					String clearType = row.getCell(18).getStringCellValue();
					if("是".equals(clearType)){
						clearType = "0";
					}
					else if("否".equals(clearType)){
						clearType = "1";
					}
					else{
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，是否为对公账户不符合规则<br>";
						break;
					}
					
					// 验证账户账号
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(19).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，账户账号单元格式为文本格式<br>";
						break;
					}
					String settleAcct = row.getCell(19).getStringCellValue();
					if(!Regex.M10(settleAcct)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，账号不符合规则<br>";
						break;
					}
					
					// 验证开户行名称
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(20).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，开户行名称单元格式为文本格式<br>";
						break;
					}
					String settleBankNm = row.getCell(20).getStringCellValue();
					if(!Regex.M9(settleBankNm)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，开户行名称不符合规则<br>";
						break;
					}
					
					// 验证开户行行号
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(21).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，开户行行号单元格式为文本格式<br>";
						break;
					}
					String openStlno = row.getCell(21).getStringCellValue();
					if(!Regex.M11(openStlno)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，开户行行号不符合规则<br>";
						break;
					}
					sql = "SELECT CNAPS_ID,CNAPS_NAME FROM TBL_CNAPS_INFO WHERE CNAPS_ID = '"+openStlno+"'";
					dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
					if(dataList.size() == 0){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，开户行号不存在<br>";
						break;
					}
					
					// 验证MCC
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(27).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，MCC单元格式为文本格式<br>";
						break;
					}
					String mcc = row.getCell(27).getStringCellValue();
					sql = "select mchnt_tp_grp,disc_cd from tbl_inf_mchnt_tp where rec_st = '1' and mchnt_tp = '" + mcc + "'";
					dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
					if(dataList.size() == 0){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，MCC不存在<br>";
						break;
					}
					// 设商户组别
					String mchtGrp = dataList.get(0)[0].toString();
					String discCd = dataList.get(0)[1].toString();
					//存放计费代码数据集
					List<String> diskCdList = new ArrayList<String>();
					for (String dc : discCd.split(",")) {
						diskCdList.add(dc);  
					}
					// 验证交易手续费率
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(22).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，交易手续费率单元格式为文本格式<br>";
						break;
					}
					String feeRate = row.getCell(22).getStringCellValue();
					if(!Regex.M14(feeRate)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，交易手续费率不符合规则<br>";
						break;
					}
					// 对计费算法的处理
					feeRate = addFeeRate(feeRate, diskCdList, mcc, mccdiskCdMap, mchtdiskCdMap);
					// 验证合同有效期
//					String contDate = row.getCell(23).getStringCellValue();
					
					// 验证终端电话号码(商户必须配置终端，不然无效)
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(24).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，终端电话号码单元格式为文本格式<br>";
						break;
					}
					String termTel = row.getCell(24).getStringCellValue();
					if(!Regex.M7(termTel)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，终端电话号码不符合规则<br>";
						break;
					}
					termTelMap.put(String.valueOf(termTelIndex), termTel.split("，"));
					
					// 验证所在城市
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(25).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，所在城市单元格式为文本格式<br>";
						break;
					}
					String areaNo = row.getCell(25).getStringCellValue();
					sql = "select MCHT_CITY_CODE,CITY_NAME from CST_CITY_CODE WHERE CITY_NAME = '" + areaNo + "'";
					dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
					if(dataList.size() == 0){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，所在城市不存在<br>";
						break;
					}
					// 设所在城市值
					areaNo = dataList.get(0)[0].toString();
					
					// 验证终端数量
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(26).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，终端数量单元格式为文本格式<br>";
						break;
					}
					String termNum = row.getCell(26).getStringCellValue().trim();
					if(termTel.split("，").length != Integer.parseInt(termNum)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，终端电话号码数量与终端数量不符合<br>";
						break;
					}
					
					// 验证商户简称
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(28).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户简称单元格式为文本格式<br>";
						break;
					}
					String mchtCnAbbr = row.getCell(28).getStringCellValue();
					if(!Regex.M12(mchtCnAbbr)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户简称不符合规则<br>";
						break;
					}

					// 验证风险级别
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(29).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，风险级别单元格式为文本格式<br>";
						break;
					}
					String rislLvl = row.getCell(29).getStringCellValue();
					if(!rislLvlMap.containsKey(rislLvl)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，风险级别不存在<br>";
						break;
					}
					// 设风险级别
					rislLvl = rislLvlMap.get(rislLvl);
					
					// 验证商户路由规则
/*					if(HSSFCell.CELL_TYPE_STRING != row.getCell(30).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户路由规则单元格式为文本格式<br>";
						break;
					}
					String luyou = row.getCell(30).getStringCellValue();
					if(!Regex.M15(luyou)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户路由规则不符合要求<br>";
						break;
					}*/
					
					// 验证是否为标准商户（clearType）
					String tcc = row.getCell(30).getStringCellValue();
					if("是".equals(tcc)){
						tcc = "0";
					}
					else if("否".equals(tcc)){
						tcc = "1";
					}
					else{
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，是否为标准商户不符合规则<br>";
						break;
					}
					
					// 验证入网日期
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(31).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，入网日期单元格式为文本格式<br>";
						break;
					}
					String instDate = row.getCell(31).getStringCellValue();
					if(!Regex.M13(instDate)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，入网日期不符合规则<br>";
						break;
					}
					
					// 验证所属机构
					if(HSSFCell.CELL_TYPE_STRING != row.getCell(32).getCellType()){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，所属机构单元格式为文本格式<br>";
						break;
					}
					String bankNo = row.getCell(32).getStringCellValue();
					if(!cupBrhIdMap.containsKey(bankNo)){
						retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，所属机构不存在<br>";
						break;
					}
					// 设银联机构号
					String cupBrhId = cupBrhIdMap.get(bankNo);
					
					// 验证通过，对商户黑名单信息进行处理
					tblMchtBaseInfTmp = new TblMchtBaseInfTmp();
					tblMchtSettleInfTmp = new TblMchtSettleInfTmp();
					
					//添加基本信息
					tblMchtBaseInfTmp.setEngName("");
					tblMchtBaseInfTmp.setAreaNo(areaNo);
					tblMchtBaseInfTmp.setMchtCnAbbr(mchtCnAbbr);
					tblMchtBaseInfTmp.setMchtGrp(mchtGrp);
					tblMchtBaseInfTmp.setMcc(mcc);
					tblMchtBaseInfTmp.setTcc(tcc);
					tblMchtBaseInfTmp.setConnType("J");
					tblMchtBaseInfTmp.setMchtGroupFlag("1");
					tblMchtBaseInfTmp.setMchtGroupId("-");
					
					tblMchtBaseInfTmp.setBankNo(bankNo);
					tblMchtBaseInfTmp.setMchtNm(mchtNm);
					tblMchtBaseInfTmp.setAddr(addr);
					tblMchtBaseInfTmp.setLicenceNo(licenceNo);
					tblMchtBaseInfTmp.setFaxNo(faxNo);
					tblMchtBaseInfTmp.setEtpsAttr("");
					tblMchtBaseInfTmp.setCommEmail("");
					tblMchtBaseInfTmp.setHomePage("");
					tblMchtBaseInfTmp.setPostCode("");
					tblMchtBaseInfTmp.setManager(manager);
					tblMchtBaseInfTmp.setBusAmt("");
					tblMchtBaseInfTmp.setArtifCertifTp(artifCertifTp);
					tblMchtBaseInfTmp.setIdentityNo(identityNo);
					tblMchtBaseInfTmp.setContact(contact);
					tblMchtBaseInfTmp.setCommTel(commTel);
					tblMchtBaseInfTmp.setElectrofax(commTel);
					tblMchtBaseInfTmp.setFax("");
					tblMchtBaseInfTmp.setRislLvl(rislLvl);//商户风险等级
			        //是否支持无磁无密交易
					tblMchtBaseInfTmp.setPassFlag("0");
					//是否支持人工授权交易
					tblMchtBaseInfTmp.setManuAuthFlag("0");
					//是否支持折扣消费
					tblMchtBaseInfTmp.setDiscConsFlg("0");
					//是否仅营业时间内交易
					tblMchtBaseInfTmp.setMchtMngMode("0");
					//申请日期
					tblMchtBaseInfTmp.setApplyDate(instDate.replace("/", ""));
					// 记录修改时间
					tblMchtBaseInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
					// 记录创建时间
					tblMchtBaseInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
					// 记录修改人
					tblMchtBaseInfTmp.setUpdOprId("");
					// 记录创建人
					tblMchtBaseInfTmp.setCrtOprId(operator.getOprId());

					tblMchtBaseInfTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
					
//					tblMchtBaseInfTmp.setLicenceEndDate(Constants.DEFAULT);
					tblMchtBaseInfTmp.setMchtLvl("2");// 2-普通商户
					tblMchtBaseInfTmp.setSaAction("0");
					tblMchtBaseInfTmp.setCupMchtFlg("0");
					tblMchtBaseInfTmp.setDebMchtFlg("0");
					tblMchtBaseInfTmp.setCreMchtFlg("0");
					tblMchtBaseInfTmp.setCdcMchtFlg("0");
					tblMchtBaseInfTmp.setManagerTel("1");
					
					//添加清算信息
					tblMchtSettleInfTmp.setSettleAcct(clearType.substring(0,1) + settleAcct);
					tblMchtSettleInfTmp.setSettleBankNm(settleBankNm);
					tblMchtSettleInfTmp.setSettleAcctNm(settleAcctNm);
					tblMchtSettleInfTmp.setOpenStlno(openStlno);

					tblMchtSettleInfTmp.setSettleType("1");
					tblMchtSettleInfTmp.setRateFlag("-");
					tblMchtSettleInfTmp.setFeeType("3"); //分段收费
					tblMchtSettleInfTmp.setFeeFixed("-");
					tblMchtSettleInfTmp.setFeeMaxAmt("0");
					tblMchtSettleInfTmp.setFeeMinAmt("0");

					//是否自动清算		
					tblMchtSettleInfTmp.setAutoStlFlg("0");
					//退货是否返还手续费	
					tblMchtSettleInfTmp.setFeeBackFlg("1");

					tblMchtSettleInfTmp.setFeeRate(feeRate);
					// 记录修改时间
					tblMchtSettleInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
					// 记录创建时间
					tblMchtSettleInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
					// 内部账户号
					tblMchtSettleInfTmp.setFeeDiv1("1");
					tblMchtSettleInfTmp.setFeeDiv2("2");
					tblMchtSettleInfTmp.setFeeDiv3("3");
					
					
					// 将ACQ_INST_ID存银联机构号，并把项目里所有以前用到ACQ_INST_ID的地方改成BANK_NO
					tblMchtBaseInfTmp.setAcqInstId(cupBrhId);
					tblMchtBaseInfTmp.setAgrBr(bankNo);
					tblMchtBaseInfTmp.setSignInstId(bankNo);
					//暂存商户终端索引和路由规则索引
					tblMchtBaseInfTmp.setReserved(String.valueOf(termTelIndex));
					termTelIndex ++;
					
					//将验证的数据放到数据集
					mchtBaseInfList.add(tblMchtBaseInfTmp);
					mchtSettleInfList.add(tblMchtSettleInfTmp);
				}
				fileInputStream.close();
				fileNameIndex++;
				if(isNotEmpty(retMsg))
					return retMsg;
			}
			
			List<TblTermKey> tblTermKeyList;
			/*
			 * 以下操作目的：批量添加商户及其终端
			 * 
			 */
			try {
				tblTermKeyList= t20107BO.importFile(mchtBaseInfList, mchtSettleInfList,mchtdiskCdMap,mccdiskCdMap,termTelMap,operator,check);
			} catch (Exception e) {
				e.printStackTrace();
				writeErrorMsg("商户批量导入并发操作，请稍后执行批量导入");
				return "商户批量导入并发操作，异常信息：[ " + e.getMessage() + " ]";
			}finally{
				// 便于回收内存
				mchtBaseInfList = null;
				mchtSettleInfList = null;
				mchtdiskCdMap = null;
				mccdiskCdMap = null;
				termTelMap = null;
				artifCertifTpMap = null;
				rislLvlMap = null;
				cupBrhIdMap = null;
			}
			
			/*
			 * 以下操作目的：相当于终端生成密钥的操作
			 * 需要和以上步骤分开操作的原因：下面UPDATE的操作需要获取终端号，这些终端号需要在上一次commit操作进行数据库添加
			 * 
			 */
			try {
				t20107BO.batchGetTmk(tblTermKeyList);
			} catch (Exception e) {
				e.printStackTrace();
				writeErrorMsg("批量生成密钥操作异常！！！请尽快联系业务员");
				return "批量生成密钥操作异常，异常信息：[ " + e.getMessage() + " ]";
			}
			
			/*
			 * 以下操作目的：生成此次商户批量导入的反馈文件
			 * 需要和以上步骤分开操作的原因：下面生成的反馈文件中的数据需要在上一次commit操作进行数据库添加
			 * 
			 */
			try {
				return t20107BO.createRetFile(tblTermKeyList,operator);
			} catch (Exception e) {
				e.printStackTrace();
				writeErrorMsg("生成商户批量导入反馈文件异常！！！请尽快联系业务员");
				return "生成商户批量导入反馈文件异常，异常信息：[ " + e.getMessage() + " ]";
			}finally{
				// 便于回收内存
				tblTermKeyList = null;
			}
	}
	
	/**
	 * 下载批导反馈文件
	 * @return
	 * 2010-8-26下午11:59:38
	 * @throws Exception 
	 */
	private String downloadRetFile() throws Exception {
		String downloadRetFile = SysParamUtil.getParam(SysParamConstants.FILE_DOWNLOAD_DISK) + 
				SysParamUtil.getParam(SysParamConstants.MCHNT_BLUK_IMP_RET) + blukFileName +".xls";
		try {
			if (FileExists(downloadRetFile) == true) {	
				writeSuccessMsg(downloadRetFile);
			} else {
				return rspCode = "您所请求的反馈文件不存在!";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return rspCode = "对不起，本次操作失败!";
		}
		return Constants.SUCCESS_CODE;
	}

	// 判断文件是否存在
	public boolean FileExists(String downloadFile) {
		try {
			downloadFile = downloadFile.replace("\\", "/");
			File writeFile = new File(downloadFile);
			if (!writeFile.exists()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据费率调整计费详情缓存
	 * 
	 * @param feeRate
	 *            读取的费率信息
	 * @param diskCdList
	 *            存放计费代码数据集
	 * @param mcc
	 *            MCC信息
	 * @param mccdiskCdMap
	 *            MCC-diskCd映射
	 * @param mchtdiskCdMap
	 *            未加费率商户-计费代码映射
	 */
	public String addFeeRate(String feeRate, List<String> diskCdList, String mcc, 
			Map<String, String> mccdiskCdMap, Map<String, TblHisDiscAlgo> mchtdiskCdMap) throws Exception {
		if (feeRate.endsWith("%")) {	//按百分比计费，无上限金额
			flag = "2";
			rate = feeRate.substring(0, feeRate.length()-1);
			maxTax = "999999999";
		} else if (feeRate.startsWith("单笔")) {	//按单笔交易计费
			flag = "1";
			rate = feeRate.substring(2, feeRate.length()-1);
			maxTax = "999999999";
		} else {	//按百分比计费，有上限金额
			String[] fr = feeRate.split("，");
			flag = "2";
			rate = fr[0].substring(0, fr[0].length()-1);
			maxTax = fr[1].substring(0, fr[1].length()-2);
		}
		String sql = "select DISC_ID, CARD_TYPE from TBL_HIS_DISC_ALGO where CARD_TYPE = '" + cardType 
				+ "' and TXN_NUM = '" + txnCode 
				+ "' and FLOOR_AMOUNT = '" + minCapital 
				+ "' and FLAG = '" + flag 
				+ "' and FEE_VALUE = '" + rate 
				+ "' and MIN_FEE = '" + minTax 
				+ "' and MAX_FEE = '" + maxTax 
				+ "' and DISC_ID in (select DISC_ID from (select DISC_ID, count(DISC_ID) from TBL_HIS_DISC_ALGO group by DISC_ID having count(DISC_ID) = 1))";
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(dataList.size() != 0){
			//根据计费详情匹配数据库相应表，若存在对应计费代码：
			String discId = dataList.get(0)[0].toString();
			if(!diskCdList.contains(discId)){
				//根据MCC匹配此计费代码，若不包含此计费代码：
				//将此diskId存放到mccdiskCdMap，与后续更新数据库操作保持一致，保证数据库的完整性
				mccdiskCdKey = mcc;
				mccdiskCdValue = ("," + discId).toString();
				if (!mccdiskCdMap.containsKey(mccdiskCdKey)){
					mccdiskCdMap.put(mccdiskCdKey, mccdiskCdValue);
				}
				else if(!(mccdiskCdMap.get(mccdiskCdKey)).contains(mccdiskCdValue)){
					mccdiskCdValue= (mccdiskCdMap.remove(mccdiskCdKey) + mccdiskCdValue).toString();
					mccdiskCdMap.put(mccdiskCdKey, mccdiskCdValue);
				}
				feeRate = discId;
			} else{
				feeRate = discId;
			}
		} else {
			//根据计费详情匹配数据库相应表，若不存在对应计费代码：
			//创建一条计费详情，并生成一条计费代码
			TblHisDiscAlgo temp = new TblHisDiscAlgo();
			temp.setMinFee(new BigDecimal(minTax));
			temp.setMaxFee(new BigDecimal(maxTax));
			temp.setTxnNum(txnCode);
			temp.setCardType(cardType);
			
			temp.setFeeValue(new BigDecimal(rate));
			temp.setFloorMount(BigDecimal.valueOf(CommonFunction.getDValue(minCapital, _defaultDoutbl)));
			temp.setFlag(CommonFunction.getInt(flag, -1));
			temp.setUpperMount(new BigDecimal("0"));
			temp.setRecUpdUsrId(operator.getOprId());
			temp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			temp.setRecCrtTs(CommonFunction.getCurrentDateTime());
			if(!mchtdiskCdMap.isEmpty()){
				Flag = "on";
				for(Iterator<Entry<String, TblHisDiscAlgo>> iter = mchtdiskCdMap.entrySet().iterator();iter.hasNext();){
					@SuppressWarnings("rawtypes")
					Map.Entry element = (Map.Entry)iter.next();
					TblHisDiscAlgo tblHisDiscAlgo = (TblHisDiscAlgo) element.getValue();
					if(compare(tblHisDiscAlgo, temp)){
						feeRate = (String) element.getKey();
						mccdiskCdKey = mcc;
						mccdiskCdValue = ("," + feeRate + "#").toString();
						if (!mccdiskCdMap.containsKey(mccdiskCdKey)){
							mccdiskCdMap.put(mccdiskCdKey, mccdiskCdValue);
						}
						else if(!(mccdiskCdMap.get(mccdiskCdKey)).contains(mccdiskCdValue)){
							mccdiskCdValue= (mccdiskCdMap.remove(mccdiskCdKey) + mccdiskCdValue).toString();
							mccdiskCdMap.put(mccdiskCdKey, mccdiskCdValue);
						}
						Flag = "off";
						break;
					}
				}
			}
			if(mchtdiskCdMap.isEmpty()||Flag == "on"){
				mchtdiskCdMap.put(String.valueOf(index), temp);
				mccdiskCdKey = mcc;
				mccdiskCdValue = ("," + String.valueOf(index) + "#").toString();
				if (!mccdiskCdMap.containsKey(mccdiskCdKey)){
					mccdiskCdMap.put(mccdiskCdKey, mccdiskCdValue);
				}
				else if(!(mccdiskCdMap.get(mccdiskCdKey)).contains(mccdiskCdValue)){
					mccdiskCdValue= (mccdiskCdMap.remove(mccdiskCdKey) + mccdiskCdValue).toString();
					mccdiskCdMap.put(mccdiskCdKey, mccdiskCdValue);
				}
				feeRate = String.valueOf(index);
				index ++;								
			}
		}
		return feeRate;
	}
	
	
	/**
	 * 对比计费详情缓存是否有重复进行判断
	 * 
	 * @param obj1
	 *            目标对象
	 * @param obj2
	 *            对比对象
	 */
	public boolean compare(TblHisDiscAlgo obj1, TblHisDiscAlgo obj2)
			throws Exception {
		if(!obj1.getFlag().equals(obj2.getFlag())){
			return false;
		}
		if(!obj1.getFeeValue().equals(obj2.getFeeValue())){
			return false;
		}
		if(!obj1.getMinFee().equals(obj2.getMinFee())){
			return false;
		}
		if(!obj1.getMaxFee().equals(obj2.getMaxFee())){
			return false;
		}
		return true;
	}

	
	// 文件集合
	private List<File> xlsFile;
	// 文件名称集合
	private List<String> xlsFileFileName;
	//回佣类型
	private String flag = null;
	//回佣值
	private String rate = null;
	//最低手续费
	private String minTax = "0";
	//最高手续费
	private String maxTax = null;
	//卡类型
	private String cardType = "99";
	//交易代码
	private String txnCode = "0000";
	//最低交易金额
	private String minCapital = "0";
	//MCC-diskCd键值
	private String mccdiskCdKey = null;
	private String mccdiskCdValue = null;
	
	//商户-diskCd键值
	private int index = 1;
	//商户—计费代码合集缓存，计费代码存在标识
	private String Flag = null;
	
	private final static Double _defaultDoutbl = new Double(0);
	
	// 待下载反馈文件名称
	private String blukFileName;
	
	// 系统是否自动添加此批次商户管理员
	private boolean check;
	/**
	 * @return the xlsFile
	 */
	public List<File> getXlsFile() {
		return xlsFile;
	}

	/**
	 * @param xlsFile the xlsFile to set
	 */
	public void setXlsFile(List<File> xlsFile) {
		this.xlsFile = xlsFile;
	}

	/**
	 * @return the xlsFileFileName
	 */
	public List<String> getXlsFileFileName() {
		return xlsFileFileName;
	}

	/**
	 * @param xlsFileFileName the xlsFileFileName to set
	 */
	public void setXlsFileFileName(List<String> xlsFileFileName) {
		this.xlsFileFileName = xlsFileFileName;
	}

	/**
	 * @return the blukFileName
	 */
	public String getBlukFileName() {
		return blukFileName;
	}

	/**
	 * @param blukFileName the blukFileName to set
	 */
	public void setBlukFileName(String blukFileName) {
		this.blukFileName = blukFileName;
	}

	/**
	 * @return the check
	 */
	public boolean isCheck() {
		return check;
	}

	/**
	 * @param check the check to set
	 */
	public void setCheck(boolean check) {
		this.check = check;
	}
	
}
