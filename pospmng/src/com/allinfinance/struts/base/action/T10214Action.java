package com.allinfinance.struts.base.action;

import java.io.File;
import java.util.List;

import com.allinfinance.bo.base.T10214BO;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T10214Action extends BaseAction {
	
	
	// 文件集合
	private List<File> files;
	// 文件名称集合
	private List<String> filesFileName;
	
	private String fileType;
	private String amtUp;
	private String amtRes;
	
	private T10214BO t10214BO = (T10214BO) ContextUtil.getBean("T10214BO");

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception{
		if("upload".equals(method)) {
			rspCode = upload();
		}
		return rspCode;
	}
	
	
	
	
	private String upload() throws Exception{
//		amtUp="50000";
//		amtRes="49000";
//		String amtups="50000";
//		String amtRes="49999";
		if(Double.parseDouble(amtUp)<=Double.parseDouble(amtRes)){
			return "大额上限值必须大于拆分金额值";
		}
		return t10214BO.formatFile(files, filesFileName,fileType,amtUp,amtRes);
		
	}

	
	
	


	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getAmtUp() {
		return amtUp;
	}

	public void setAmtUp(String amtUp) {
		this.amtUp = amtUp;
	}

	public String getAmtRes() {
		return amtRes;
	}

	public void setAmtRes(String amtRes) {
		this.amtRes = amtRes;
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
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
	 * @param filesFileName the filesFileName to set
	 */
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	
	
	
}
