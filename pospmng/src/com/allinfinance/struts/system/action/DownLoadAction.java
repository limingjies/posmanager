
package com.allinfinance.struts.system.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

public class DownLoadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String filename;
	private String fileName;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	
	public String execute() throws Exception {
		return SUCCESS;

	}

	public InputStream getDown() throws Exception {
		
		
		File fileDes = new File(filename);
		InputStream is = new FileInputStream(fileDes);
		return is;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	


	
	
}
