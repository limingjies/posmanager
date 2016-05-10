package com.allinfinance.struts.base.action;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.allinfinance.bo.base.T10600BO;
import com.allinfinance.bo.impl.mchnt.TblMchntService;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.po.TblRouteRule;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T10604Action extends BaseAction{

	private T10600BO t10600BO = (T10600BO) ContextUtil.getBean("T10600BO");
	private TblMchntService service = (TblMchntService) ContextUtil.getBean("tblMchntService");

	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
			if("uploadFile".equals(getMethod())) {
				rspCode = uploadFile();			
			} else if("downloadRetFile".equals(method)) {
				rspCode = downloadRetFile();
			}
		return rspCode;
	}
	
	/**
	 * 商户路由批量配置
	 * @throws Exception 
	 */
	private String uploadFile() throws Exception {
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		// 文件名称索引
		int fileNameIndex = 0;
		// 文件名称
		String fileName = null;
		FileInputStream fileInputStream = null;
		// 准备数据
		jsonBean.parseJSONArrayData(getMenuArray());
		JSONObject object = jsonBean.getJSONDataAt(0);
		String ruleCode = object.get("ruleCode").toString();
		String priority = object.get("priority").toString();
		String brhId = object.get("brhId").toString();
		String mchtGroup = object.get("mchtGroup").toString();
		String mcc = object.get("mcc").toString();
		String mchtArea = object.get("mchtArea").toString();
		String insIdCd = object.get("insIdCd").toString();
		String cardTp = object.get("cardTp").toString();
		String cardBin = object.get("cardBin").toString();
		if(StringUtil.isEmpty(cardBin)||"*-无限制".equals(cardBin)){
			cardBin = "*";
		}
		String txnNum = object.get("txnNum").toString();
		String amtCtl = object.get("amtCtl").toString();
		String amtBeg = object.get("amtBeg").toString();
		String amtEnd = object.get("amtEnd").toString();
		if(CommonsConstants.ROUTE_RULE_AMT_CTL.equals(amtCtl)){
			DecimalFormat df=new DecimalFormat("0.00");
			if(Double.parseDouble(amtBeg.toString())>Double.parseDouble(amtEnd.toString())){
				return "最小金额不得大于最大金额！";
			}
			amtBeg = CommonFunction.fillString(df.format(Double.parseDouble(amtBeg.toString())).replace(".", ""), '0', 12, false);
			amtEnd = CommonFunction.fillString(df.format(Double.parseDouble(amtEnd.toString())).replace(".", ""), '0', 12, false);
		}
		String dateCtl = object.get("dateCtl").toString();
		String dateBeg = object.get("dateBeg").toString();
		String dateEnd = object.get("dateEnd").toString();
		String timeCtl = object.get("timeCtl").toString();
		String timeBeg = object.get("timeBeg").toString();
		String timeEnd = object.get("timeEnd").toString();
		if(CommonsConstants.ROUTE_RULE_TIME_CTL.equals(timeCtl)){
			timeBeg = timeBeg.replace(":", "")+"00";
			timeEnd = timeEnd.replace(":", "")+"00";
		}
		//待入商户路由信息
		TblRouteRule tblRouteRule;
		List<TblRouteRule> tblRouteRuleList = new ArrayList<TblRouteRule>();
		//验证信息不通过返回值
		String retMsg = null;
		
		for(File file : xlsFile) {
			
			fileInputStream = new FileInputStream(file);
			
			workbook = new HSSFWorkbook(fileInputStream);
			
			sheet = workbook.getSheetAt(0);
			
			fileName = xlsFileFileName.get(fileNameIndex);
			
			for(int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				
				row = sheet.getRow(rowIndex);
				if(row == null)
					break;
				
				if(HSSFCell.CELL_TYPE_STRING != row.getCell(0).getCellType()){
					retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户号单元格式为文本格式<br>";
					break;
				}
				// 验证商户号是否存在
				String accpId = row.getCell(0).getStringCellValue();
				if(service.getMccByMchntId(accpId) == null){
					retMsg = "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，无效商户号！<br>";
					break;
				}
				tblRouteRule = new TblRouteRule(ruleCode,priority,brhId,mchtGroup,mcc,mchtArea,insIdCd,cardTp,cardBin,txnNum,
						amtCtl,amtBeg,amtEnd,dateCtl,dateBeg,dateEnd,timeCtl,timeBeg,timeEnd);
				tblRouteRule.getTblRouteRulePK().setAccpId(accpId);
				tblRouteRuleList.add(tblRouteRule);
			}
			fileInputStream.close();
			fileNameIndex++;
			if(isNotEmpty(retMsg))
				return retMsg;
		}
		StringBuffer routeRuleInfo = new StringBuffer();
		routeRuleInfo.append(ruleCode).append("|");
		routeRuleInfo.append(priority).append("|");
		routeRuleInfo.append(brhId).append("|");
		routeRuleInfo.append(mchtGroup).append("|");
		routeRuleInfo.append(mcc).append("|");
		routeRuleInfo.append(mchtArea).append("|");
		routeRuleInfo.append(insIdCd).append("|");
		routeRuleInfo.append(cardTp).append("|");
		routeRuleInfo.append(cardBin).append("|");
		routeRuleInfo.append(txnNum).append("|");
		routeRuleInfo.append(amtCtl).append("|");
		routeRuleInfo.append(amtBeg).append("|");
		routeRuleInfo.append(amtEnd).append("|");
		routeRuleInfo.append(dateCtl).append("|");
		routeRuleInfo.append(dateBeg).append("|");
		routeRuleInfo.append(dateEnd).append("|");
		routeRuleInfo.append(timeCtl).append("|");
		routeRuleInfo.append(timeBeg).append("|");
		routeRuleInfo.append(timeEnd);
		
		return t10600BO.addList(tblRouteRuleList,routeRuleInfo.toString(),operator);
	}
	
	/**
	 * 下载批导反馈文件
	 * @return
	 * 2010-8-26下午11:59:38
	 * @throws Exception 
	 */
	private String downloadRetFile() throws Exception {
		String downloadRetFile = SysParamUtil.getParam(SysParamConstants.FILE_DOWNLOAD_DISK) + 
				SysParamUtil.getParam(SysParamConstants.ROUTE_BLUK_IMP_RET) + blukFileName +".xls";
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

	// 文件集合
	private List<File> xlsFile;
	// 文件名称集合
	private List<String> xlsFileFileName;
	private String menuArray;
	// 待下载反馈文件名称
	private String blukFileName;
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
	 * @return the menuArray
	 */
	public String getMenuArray() {
		return menuArray;
	}

	/**
	 * @param menuArray the menuArray to set
	 */
	public void setMenuArray(String menuArray) {
		this.menuArray = menuArray;
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
	
}
