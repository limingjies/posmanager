package com.allinfinance.struts.pos.action;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.allinfinance.bo.term.T30302BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermManagementDAO;
import com.allinfinance.po.TblTermManagement;
import com.allinfinance.po.TblTermManagementAppMain;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;

@SuppressWarnings("serial")
public class T30311Action extends BaseAction{

	private String params;
	private List<File> xlsFile;
	private List<String> xlsFileFileName;
	private TblTermManagementDAO tblTermManagementDAO;
	private T30302BO t30302BO;
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("upload".equals(method)) {
			rspCode = upload();
		}
		return rspCode;
	}

	@SuppressWarnings("unchecked")
	private String upload() {
		// TODO Auto-generated method stub
		int fileNameIndex = 0;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		FileInputStream fileInputStream = null;
		String fileName = null;
		try {
			
			TblTermManagementAppMain tblTermManagementAppMain=t30302BO.get(params);
			if(tblTermManagementAppMain==null){
				return "该申请单不存在，请重新刷新！";
			}
			
			
			
			String sql="select manufaturer,terminal_type,term_type,acc_amount from tbl_term_management_check where app_id='"+params+"' and stat in('1','2')";
			List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			int count=0;
			for (Object[] objects : dataList) {
				count=count+Integer.valueOf(String.valueOf(objects[3]));
			}
			String ssss="select count(*) from tbl_term_management where batch_no='"+CommonFunction.fillString(params,' ', 12, true)+"' ";
			String sss=String.valueOf(((BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(ssss).get(0)).doubleValue());
			sss=sss.substring(0, sss.indexOf("."));
			int counts=0;
			for(File file : xlsFile) {
				
				fileInputStream = new FileInputStream(file);
				workbook = new HSSFWorkbook(fileInputStream);
				sheet = workbook.getSheetAt(0);
				
				counts=counts+(sheet.getLastRowNum()+1);
				
				for(int rowIndex = sheet.getFirstRowNum(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					row = sheet.getRow(rowIndex);
					fileName = xlsFileFileName.get(fileNameIndex);
					if(row.getLastCellNum()!=5){
						return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行数据不正确!"; 
					}
					for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
						if(row.getCell(i).getCellType() != HSSFCell.CELL_TYPE_STRING){
							return  "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，" + 
										"第" + (i + 1) + "列单元格格式不正确";
						}
					}
				}
				fileInputStream.close();
				fileNameIndex++;
				
			}
			if("0".equals(sss)){
				if(counts>count){
					return "导入文件条数大于审核通过的数量！";
				}
			}else{
				if((counts+Integer.valueOf(sss))>count){
					return "此申请单已入库存在"+sss+"条,现在文件条数"+counts+"条，之和超过了审核通过的数量！";
				}
			}
			
			
			fileNameIndex = 0;
			row = null;
			fileName = null;
			fileInputStream=null;
			workbook = null;
			sheet = null;
			
			String brhId=operator.getOprBrhId();
			String date =CommonFunction.getCurrentDate();
			String dateTime=CommonFunction.getCurrentDateTime();
			for(File file : xlsFile) {
				
				fileInputStream = new FileInputStream(file);
				workbook = new HSSFWorkbook(fileInputStream);
				sheet = workbook.getSheetAt(0);
				
				TblTermManagement tblTermManagement=null;
				for(int rowIndex = sheet.getFirstRowNum(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					row = sheet.getRow(rowIndex);
					fileName = xlsFileFileName.get(fileNameIndex);
//					for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
//						if(row.getCell(i).getCellType() != HSSFCell.CELL_TYPE_STRING){
//							return  "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，" + 
//										"第" + (i + 1) + "列单元格格式不正确<br>";
//						}
//					}
					tblTermManagement =new TblTermManagement();
//					tblTermManagement.setBrhId(brhId);
					String termType=row.getCell(0).getStringCellValue().trim();
					
					if("POS".equals(termType)){
						termType= "P";
					}else if("EPOS".equals(termType)){
						termType= "E";
					}else if("ATM".equals(termType)){
						termType= "A";
					}else if("PINPAD".equals(termType)){
						termType= "K";
					}
					
					
					tblTermManagement.setId(getNextId(brhId,termType));
					tblTermManagement.setTermType(termType);
					tblTermManagement.setState("0");
					tblTermManagement.setManufacturer(row.getCell(1).getStringCellValue().trim());
					tblTermManagement.setProductCd(row.getCell(2).getStringCellValue().trim());
					tblTermManagement.setTerminalType(row.getCell(3).getStringCellValue().trim());
					tblTermManagement.setBatchNo(params);
					tblTermManagement.setStorOprId(operator.getOprId());
					tblTermManagement.setStorDate(date);
					tblTermManagement.setLastUpdOprId(operator.getOprId());
					tblTermManagement.setLastUpdTs(dateTime);
					
					String sqls="select count(*) from tbl_term_management_check where app_id='"+params+"' " +
							"and manufaturer ='"+tblTermManagement.getManufacturer().trim()+"' and terminal_type='"+tblTermManagement.getTerminalType().trim()+"' " +
									"and term_type='"+tblTermManagement.getTermType().trim()+"' and stat in('1','2') ";
					String foo=String.valueOf(((BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sqls).get(0)).doubleValue());
					if("0.0".equals(foo)){
						return   "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行与申请机具不符！" ;
					}
					
					sqls="select count(*) from tbl_term_management where product_cd ='"+tblTermManagement.getProductCd()+"' " +
							" and terminal_type='"+tblTermManagement.getTerminalType().trim()+"' and term_type='"+tblTermManagement.getTermType().trim()+"'";
					foo=String.valueOf(((BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sqls).get(0)).doubleValue());
					if(!"0.0".equals(foo)){
						return  "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行的机具序列号已经存在！" ;
					}
					
					
					sqls="select count(*) from tbl_term_management where batch_no='"+CommonFunction.fillString(params,' ', 12, true)+"' " +
							"and manufaturer ='"+tblTermManagement.getManufacturer().trim()+"' and terminal_type='"+tblTermManagement.getTerminalType().trim()+"' " +
									"and term_type='"+tblTermManagement.getTermType().trim()+"'";	
					foo=String.valueOf(((BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sqls).get(0)).doubleValue());
//					foo=(String) CommonFunction.getCommQueryDAO().findBySQLQuery(sqls).get(0);
					String sqlss="select acc_amount from tbl_term_management_check where app_id='"+params+"' " +
					"and manufaturer ='"+tblTermManagement.getManufacturer().trim()+"' and terminal_type='"+tblTermManagement.getTerminalType().trim()+"' " +
					"and term_type='"+tblTermManagement.getTermType().trim()+"' and stat in('1','2') ";	
					
					List<String> dataLists = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlss);
					int foos=0;
					for (String string : dataLists) {
						foos=foos+Integer.valueOf(String.valueOf(string));
					}
					System.out.println(foos+"--"+foo);
					if(Double.valueOf(foo)>=Double.valueOf(foos)){
						return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行的机具种类已经超过审核通过的数量！" ;
					}
					
					
					
					String pinPad=row.getCell(4).getStringCellValue().trim();
					if("外接".equals(pinPad)){
						pinPad="W";
					}else if("内置".equals(pinPad)){
						pinPad="N";
					}else{
						pinPad="P";
					}
					
					tblTermManagement.setPinPad(pinPad);
					
					
					
					tblTermManagementDAO.save(tblTermManagement);
				}
				fileInputStream.close();
				fileNameIndex++;
			}
			
//			if(counts<count){
//				return "导入成功，但是文件条数还差"+String.valueOf(count-counts)+"条！";
//			}
			if("0".equals(sss)){
				if(counts<count){
					return "导入成功，但是文件条数还差"+String.valueOf(count-counts)+"条！";
				}
			}else{
				if((counts+Integer.valueOf(sss))<count){
					return "此申请单已入库存在"+sss+"条,成功导入"+counts+"条，还差"+String.valueOf(count-counts-Integer.valueOf(sss))+"条！";
				}
			}
			
			
			tblTermManagementAppMain.setStat("3");
			return t30302BO.update(tblTermManagementAppMain);
		} catch (Exception e) {
			e.printStackTrace();
			return "解析文件失败";
		}
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public List<File> getXlsFile() {
		return xlsFile;
	}

	public void setXlsFile(List<File> xlsFile) {
		this.xlsFile = xlsFile;
	}

	public List<String> getXlsFileFileName() {
		return xlsFileFileName;
	}

	public void setXlsFileFileName(List<String> xlsFileFileName) {
		this.xlsFileFileName = xlsFileFileName;
	}

	@SuppressWarnings("unchecked")
	public String getNextId(String brhId,String termType) {
		if(brhId == null || brhId.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(brhId.length()>2)
			brhId = brhId.substring(0, 2);
		String sql = "select SEQ_TERM_MANAGEMENT.nextval from dual";
		List list = commQueryDAO.findBySQLQuery(sql);
		String nextId = "0";
		if(list != null && !list.isEmpty())
		{
			String num = list.get(0).toString();
			nextId = CommonFunction.fillString(num, '0', 5, false);
		}
		if("K".equals(termType)){
			return "P"+ brhId + nextId;
		}else {
			return "T"+ brhId + nextId;
		}
		
	}

	public TblTermManagementDAO getTblTermManagementDAO() {
		return tblTermManagementDAO;
	}

	public void setTblTermManagementDAO(TblTermManagementDAO tblTermManagementDAO) {
		this.tblTermManagementDAO = tblTermManagementDAO;
	}

	public T30302BO getT30302BO() {
		return t30302BO;
	}

	public void setT30302BO(T30302BO t30302bo) {
		t30302BO = t30302bo;
	}

	
	
}
