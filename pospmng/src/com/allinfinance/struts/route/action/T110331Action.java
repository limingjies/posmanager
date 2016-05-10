package com.allinfinance.struts.route.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.route.T110331BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.dao.iface.route.TblRouteMchtgDetailDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapHistDAO;
import com.allinfinance.po.route.TblRouteMchtgDetail;
import com.allinfinance.po.route.TblRouteRuleMap;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.JSONBean;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T110331Action extends BaseAction{
	
	private TblRouteRuleMapHistDAO tblRouteRuleMapHistDAO=(TblRouteRuleMapHistDAO) ContextUtil.getBean("TblRouteRuleMapHistDAO");
	private T110331BO t110331BO=(T110331BO) ContextUtil.getBean("T110331BO");
	private TblRouteMchtgDetailDAO tblRouteMchtgDetailDAO=(TblRouteMchtgDetailDAO) ContextUtil.getBean("TblRouteMchtgDetailDAO");
	private TblRouteRuleMapDAO tblRouteRuleMapDAO=(TblRouteRuleMapDAO) ContextUtil.getBean("TblRouteRuleMapDAO");
	Operator operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
	// 文件集合
	private List<File> files;
	
	@Override
	protected String subExecute() throws Exception {
		return null;
	}
	/**
	 * 检查是否已经为商户配置了商户组
	 * @return
	 * @throws IOException 
	 */
	public String checkHasGroup() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		String mchtId = request.getParameter("mchtId");
		TblRouteMchtgDetail tblRouteMchtgDetail=tblRouteMchtgDetailDAO.getByMchtId(mchtId);
		if(tblRouteMchtgDetail!=null){
			writeSuccessMsg(tblRouteMchtgDetail.getPk().getMchtGid().toString());
		}else{
			writeSuccessMsg("no");
		}
		return null;
	}
	/**
	 * 修改商户映射关系
	 * @return
	 * @throws IOException
	 */
	public String editMchtUpbrh() throws IOException{
		try {
			HttpServletRequest request=ServletActionContext.getRequest();
			String mchtId = request.getParameter("mchtId");
			String mchtUpbrhId = request.getParameter("mchtUpbrhId").split(",")[0];
			String propertyId = request.getParameter("propertyId");
			String ruleId = request.getParameter("ruleId");
			String termId = request.getParameter("termId");
			String result = t110331BO.editMchtUpbrh(operator,ruleId,mchtId,mchtUpbrhId,propertyId,termId);
			if(Constants.SUCCESS_CODE.equals(result)){
				writeSuccessMsg("修改成功！");
			}else{
				writeErrorMsg("修改失败！");
			}
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("修改失败！");
		}
		return null;
	}
	/**
	 * 新增商户映射关系
	 * @return
	 * @throws IOException
	 */
	public String addMchtUpbrh() throws IOException{
		
			HttpServletRequest request = ServletActionContext.getRequest();
			String success="添加成功的性质：<div/>";
			String failure="添加失败的性质：<div/>";
			String termId="";
			String propertyId="";
			String mchtUpbrhId="";
			String mchtId = request.getParameter("mchtId");
			String mchtUpbrhIds = request.getParameter("mchtUpbrhIds");
			String propertyIds = request.getParameter("propertyIds");
			String[] mchtUpbrhIdStrings = mchtUpbrhIds.split(",");
			String[] propertyIdStrings = propertyIds.split(",");
			String[] termIds = request.getParameter("termIds").split(",");
			for (int i = 0; i < termIds.length; i++) {
				try {
				termId=termIds[i];
				propertyId=propertyIdStrings[i];
				mchtUpbrhId=mchtUpbrhIdStrings[i];
				String result = t110331BO.addMchtUpbrh(operator.getOprId(),mchtId,propertyId,mchtUpbrhId,termId,0);
				if(Constants.SUCCESS_CODE.equals(result)){
					
					success+="<font color=green>渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
				}else failure+="<font color=red>渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
			
			} catch (Exception e) {
				log(e.getMessage());
				failure+="<font color=red>渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
			}
		}
		writeSuccessMsg(success+failure);
		return null;
	}
	/**
	 * 批量修改商户映射关系
	 * @return
	 * @throws IOException
	 */
	public String bacthAddMchtUpbrh() throws IOException{
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			TblRouteRuleMap routeRuleMap;
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
			String crtTime=format.format(date);
			String mchtIds = request.getParameter("mchtIds");
			String mchtUpbrhIds = request.getParameter("mchtUpbrhIds");
			String groupId = request.getParameter("newGid");
			String propertyIds = request.getParameter("propertyIds");
			String[] mchtIdStrings = mchtIds.split(",");
			String[] mchtUpbrhIdStrings = mchtUpbrhIds.split(",");
			String[] propertyIdStrings = propertyIds.split(",");
			String[] termIds = request.getParameter("termIds").split(",");
			
			String mchtId="";
			String mchtUpbrhId="";
			String propertyId="";
			String termId="";
			String success="添加成功的商户：<div/>";
			String failure="添加失败的商户：<div/>";
			for (int i = 0; i < mchtUpbrhIdStrings.length; i++) {
				mchtId=mchtIdStrings[i];
				//删除以前的映射关系
				tblRouteRuleMapDAO.deleteByMchtId(mchtId);
			}
			for (int i = 0; i < mchtUpbrhIdStrings.length; i++) {
				mchtUpbrhId=mchtUpbrhIdStrings[i];
				propertyId=propertyIdStrings[i];
				termId=termIds[i];
				mchtId=mchtIdStrings[i];
					//查看是否已经存在
					try {
						String result = t110331BO.bacthAddMchtUpbrh(operator, mchtId, mchtUpbrhId, propertyId,termId,groupId);
						if(result.equals(Constants.FAILURE_CODE)){
							writeErrorMsg("映射关系已满！");
							return null;
						}
	                	success+="<font color=green>商户号"+mchtId+"-渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
	                	
					} catch (Exception e) {
						e.printStackTrace();
						log(e.getMessage());
						//失败
						failure+="<font color=red>商户号"+mchtId+"-渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
					}
			}
			writeSuccessMsg(success+failure);
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("添加性质失败！");
		}
		return null;
	}
	/**
	 * 删除商户映射关系
	 * @return
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String mchntId = request.getParameter("mchntId");
			String mchtUpbrhNo = request.getParameter("mchtUpbrhNo");
			String propertyId = request.getParameter("propertyId");
			String termId = request.getParameter("termId");
			if(!isNotEmpty(mchntId)||!isNotEmpty(propertyId)||!isNotEmpty(mchtUpbrhNo)){
				writeSuccessMsg("商户性质不存在！");
				return null;
			}
			tblRouteRuleMapDAO.deleteByConditions(mchntId,mchtUpbrhNo,propertyId,termId);
			tblRouteRuleMapHistDAO.updateByConditions(mchntId,mchtUpbrhNo,propertyId,operator.getOprId(),termId);
			writeSuccessMsg("删除商户性质成功！");
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("删除商户性质失败！");
		}
		return null;
	}
	
	/**
	 * 导入商户映射关系
	 * @return
	 * @throws IOException
	 */
	public String upload() throws IOException {
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		if (files == null || files.size() == 0 || files.get(0) == null) {
			writeErrorMsg("商户映射关系导入文件为空！");
			return null;
		}
		try{
			fis = new FileInputStream(files.get(0));
			wb = new HSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);
			Row titles = sheet.getRow(0);
			int rowCount = sheet.getLastRowNum() + 1;
			int cellCount = titles.getLastCellNum() + 1;

			String[] titleArr = new String[cellCount];
			Map<String,Integer> useColMap = new HashMap<String,Integer>();
			
		
			if(rowCount <=1){
				writeErrorMsg("商户映射关系数据不可为空！");
				return null;
			}else if(rowCount > Constants.REPORT_MAX_COUNT+1){
				writeErrorMsg("商户映射关系数据太多（>"+Constants.REPORT_MAX_COUNT+"）,请分多次导入！");
				return null;
			}
			//确定指定的列所在的位置
			for(int i=0;i<cellCount;i++){
				String title = null;
				try{
					Cell cell = titles.getCell(i);
					title = cell.getStringCellValue();
				}catch(Exception e){
					title = null;
				}
				titleArr[i] = title;
				if("商户号".equals(title)){
					useColMap.put("商户号", i);
				}else if("性质编码".equals(title)){
					useColMap.put("性质编码", i);
				}else if("渠道商户号".equals(title)){
					useColMap.put("渠道商户号", i);
				}else if("渠道终端号".equals(title)){
					useColMap.put("渠道终端号", i);
				}else if("映射关系ID".equals(title)){
					useColMap.put("映射关系ID", i);
				}
			}
			if(useColMap.isEmpty() || useColMap.size() < 5){
				writeErrorMsg("商户映射关系导入失败:请检查文件中是否含有以下字段（映射关系ID，商户号，性质编码，渠道商户号，渠道终端号）!");
				return null;
			}
			//数据处理
			List<Object[]> errorDataList = new ArrayList<Object[]>();
			for (Row row : sheet) {
				if(row.getRowNum() == 0){
					continue;
				}
				Map<String, String> useDataMap = new HashMap<String, String>();	
				Object[] rowData = new Object[cellCount];
				
				for (int i = 0; i < cellCount; i++) {
					String data = null;
					try{
						Cell cell = row.getCell(i);
						data = cell.getStringCellValue();
					}catch(Exception e){
						data = null;
					}
					//获取有用数据
					for(Map.Entry<String, Integer> entry:useColMap.entrySet()){
						if(i== entry.getValue()){
							useDataMap.put(entry.getKey(), data);
						}
					}
					useDataMap.put(useDataMap.get(i), data);
					rowData[i] = data;
				}
				useDataMap.put("执行结果", null);
				try{
					t110331BO.importMapData(useDataMap, operator);
				} catch(Exception e){
					useDataMap.put("执行结果", "导入失败！");
				}
				String ret = useDataMap.get("执行结果");
				if(!"00".equals(ret)){
					rowData[cellCount-1] = useDataMap.get("执行结果");
					errorDataList.add(rowData);	
				}
			}
			titleArr[cellCount-1] = "执行结果";
			if(errorDataList.isEmpty()){
				writeSuccessMsg("数据导入成功！");
			}else{
				Object[][] errorDataArr = new Object[errorDataList.size()][];
				for(int i=0;i<errorDataList.size();i++){
					errorDataArr[i]= errorDataList.get(i);
				}
				String fieleName = createResFile(errorDataArr,titleArr);
				int sucCount = rowCount-errorDataList.size()-1;
				String msg = "导入结果汇总:<br/>成功记录数：" + sucCount + ",失败记录数：" + errorDataList.size() + "<br/>详细失败信息请查看文件内容！";
				writeRespSucMsg(msg,fieleName);
			}
			
		}catch(Exception e){
			log("操作员："+operator.getOprId()+"，商户映射关系导入失败；原因：" + e.getMessage());
			writeErrorMsg("商户映射关系导入失败!");
			return null;
		}finally{
			if(fis != null){
				fis.close();
			}
		}
		return null;
	}
	/**
	 * 输出成功信息
	 * @param msg
	 * @throws IOException 
	 */
	private void  writeRespSucMsg(String msg,String filename) throws IOException {
		JSONBean jsonBean = new JSONBean();
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		jsonBean.addJSONElement("filename", filename);
		writeMessage(jsonBean.toString());
	}
	
	private String createResFile(Object[][] dataArr,String[] colTitle) throws Exception{
		ExcelReportCreator excelReportCreator = new ExcelReportCreator(); 
		List<String> sheetList = new ArrayList<String>();
		List<String[]> coulmHeaderList = new ArrayList<String[]>();
		List<Object[][]> dataList = new ArrayList<Object[][]>();
		dataList.add(dataArr);
		coulmHeaderList.add(colTitle);
		sheetList.add("商户映射自动导入出错数据");
		String fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "商户映射自动导入出错数据_" + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		FileOutputStream outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={4,7};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(dataList);
		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		return fileName;
	}
	
	
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	
}
