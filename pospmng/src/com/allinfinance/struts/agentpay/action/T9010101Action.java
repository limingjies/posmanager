package com.allinfinance.struts.agentpay.action;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.JSONBean;
import com.allinfinance.system.util.SFTPUtil;
import com.allinfinance.system.util.SysParamUtil;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 从存放文件的服务器上下载文件到tomcat服务器上
 * 即：从存放C程序的服务器上下载到存储java程序的服务器上
 * @author huangjl
 *
 * 2014年3月27日 上午11:43:50
 */
public class T9010101Action extends ActionSupport {

	private FileOutputStream outputStream;
	private Operator	operator;
	/**客户端信息对象*/
	private JSONBean jsonBean = new JSONBean();
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		String fileName = SysParamUtil.getParam(SysParamConstants.TMP_FILE_DSF)+"代收付源文件"+ 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".txt";
		outputStream = new FileOutputStream(fileName);
		String ip = SysParamUtil.getParam("DSF_IP");
		int port =Integer.parseInt(SysParamUtil.getParam("DSF_SFTP_PORT"));
		String userName = SysParamUtil.getParam("DSF_SFTP_NAME");
		String password = SysParamUtil.getParam("DSF_SFTP_PSD");
		String rcvFilesPath = SysParamUtil.getParam("RCV_FILES_PATH");
		String rcvfileName = "test.txt";
		SFTPUtil su = new SFTPUtil(userName,password,ip,port);
		su.connect();
		su.download(rcvFilesPath, rcvfileName,outputStream);
		su.disConnect();
		writeUsefullMsg(fileName);
		return SUCCESS;
	}
	/**
	 * 向客户端输出信息
	 * @param response
	 * @param msg
	 * @throws IOException
	 * 2010-8-30下午07:45:40
	 */
	protected void writeNoDataMsg(String msg) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, false);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 向客户端输出有效信息
	 * @param response
	 * @param msg
	 * @throws IOException
	 * 2010-8-30下午07:45:40
	 */
	protected void writeUsefullMsg(String msg) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	private String mchtNo;
	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	
}
