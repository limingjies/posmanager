package com.allinfinance.struts.query.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.allinfinance.bo.query.T50109BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.query.TblAccInConfirmHis;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.FTPUtil;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TarZipUtils;

/**
 * 确认入账、下载回盘文件Action类
 * 
 * @author luhq
 *
 */
@SuppressWarnings("serial")
public class T5010902Action extends BaseAction {
	private static Logger log = Logger.getLogger(T5010902Action.class);

	/**
	 * 清算日期（格式:YYYYMMDD）
	 */
	private String dateSettlmt = "";
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		if (StringUtil.isEmpty(dateSettlmt)) {//清算日期不能为空
			return ErrorCode.T50109_01;
		}
		String today = DateUtil.formatDate(new Date(), "yyyyMMdd");
		if (dateSettlmt.compareTo(today) >= 0) {//清算日期必须小于今日
			return ErrorCode.T50109_05;
		}
		if ("01".equals(this.getSubTxnId())) {
			return confirmIn();
		} else {
			return downloadRediscFile();
		}
	}

	/**
	 * 确认入账，把请求文件从目录tmpin目录移到in目录
	 * 
	 * @throws Exception
	 */
	private String confirmIn() throws Exception {
		T50109BO t50109BO = (T50109BO) ContextUtil.getBean("T50109BO");
		if (t50109BO.get(dateSettlmt) != null) {//该清算日期已经确认入账
			return ErrorCode.T50109_02;
		}
		
		if (!t50109BO.existRunBatchRecords(dateSettlmt)) {
			return ErrorCode.T50109_06;
		}
		String ftpIP = SysParamUtil.getParam("ftpIP");
		String ftpName = SysParamUtil.getParam("ftpName");
		String ftpPwd = SysParamUtil.getParam("ftpPwd");
		int ftpPort = 21;
		if (SysParamUtil.contains("ftpPort")) {//端口号默认21，如果有配置，取配置值
			String ftpPortS = SysParamUtil.getParam("ftpPort");
			ftpPort = Integer.parseInt(ftpPortS);
		}
		String intempdir = "intmp";
		String indir = "in";
		if (SysParamUtil.contains("FILE_PATH_ACC_IN_TMP")) {//入账文件临时目录，如果有配置，取配置值
			intempdir = SysParamUtil.getParam("FILE_PATH_ACC_IN_TMP");
		}
		if (SysParamUtil.contains("FILE_PATH_ACC_IN")) {//入账文件目录，如果有配置，取配置值
			indir = SysParamUtil.getParam("FILE_PATH_ACC_IN");
		}
		FTPClient ftpClient = FTPUtil.getFTPClient(ftpIP, ftpPwd, ftpName, ftpPort);
		if (ftpClient == null || !ftpClient.isConnected()) {
			return ErrorCode.T50109_09;//FTP参数配置有误
		}
		FTPFile[] ftpFiles = ftpClient.listFiles(intempdir);
		if (ftpFiles.length == 0) {
			ftpClient.logout();
			ftpClient.disconnect();
			return ErrorCode.T50109_03;//临时目录中没有找到任何入账文件
		}
		ftpClient.changeWorkingDirectory(intempdir);
		//移动文件
		int fitFileCount = 0;
		for (FTPFile ftpFile:ftpFiles) {
			if (ftpFile.getName().matches("RQ_158158_" + dateSettlmt + "_.*")) {//文件格式符合RQ_158158_20151216_0001.txt
				ftpClient.rename(ftpFile.getName(), "/" + indir + "/" + ftpFile.getName());
				fitFileCount ++;
			}
		}

		//注销并断开FTP连接
		ftpClient.logout();
		ftpClient.disconnect();
		
		//生成移动历史记录
		TblAccInConfirmHis newConfirmHis = new TblAccInConfirmHis();
		newConfirmHis.setDateSettlmt(dateSettlmt);
		newConfirmHis.setRecOprId(super.operator.getOprId());
		newConfirmHis.setCreateTime(new Date());
		t50109BO.save(newConfirmHis);

		if (fitFileCount == 0) {
			return ErrorCode.T50109_04;//该日无清算数据
		}
		
		return Constants.SUCCESS_CODE;
	}


	/**
	 * 下载回盘文件，把out目录文件拷贝到webapp的相应目录下，再打包下载打包文件
	 * 
	 * @throws Exception
	 */
	private String downloadRediscFile() throws Exception {
		if (!SysParamUtil.contains("FILE_PATH_ACC_OUT_DOWNLOAD")) {
			return ErrorCode.T50109_10;//下载路径没有配置
		}
		String downloadDir = SysParamUtil.getParam("FILE_PATH_ACC_OUT_DOWNLOAD");
		if (StringUtil.isEmpty(downloadDir)) {
			return ErrorCode.T50109_10;//下载路径没有配置
		}
		File fDownloadDir = new File(downloadDir);
		if (!fDownloadDir.exists()) {//下载路径不存在则创建
			fDownloadDir.mkdir();
			log.warn("file download dir:" + downloadDir + " does not exist, created by system.");
		}
		
		//先判断服务器本地是否已经有该文件
		File targetZipFile = new File(downloadDir + "/clearReturnFile_" + dateSettlmt + ".zip");
		if (targetZipFile.exists()) {
			return Constants.SUCCESS_CODE_CUSTOMIZE+targetZipFile.getAbsolutePath();
		}
		//不存在则从FTP的/out目录拷贝文件
		String ftpIP = SysParamUtil.getParam("ftpIP");
		String ftpName = SysParamUtil.getParam("ftpName");
		String ftpPwd = SysParamUtil.getParam("ftpPwd");
		int ftpPort = 21;
		if (SysParamUtil.contains("ftpPort")) {//端口号默认21，如果有配置，取配置值
			String ftpPortS = SysParamUtil.getParam("ftpPort");
			ftpPort = Integer.parseInt(ftpPortS);
		}
		String outdir = "out";
		if (SysParamUtil.contains("FILE_PATH_ACC_OUT")) {//入账文件临时目录，如果有配置，取配置值
			outdir = SysParamUtil.getParam("FILE_PATH_ACC_OUT");
		}
		FTPClient ftpClient = FTPUtil.getFTPClient(ftpIP, ftpPwd, ftpName, ftpPort);
		if (ftpClient == null || !ftpClient.isConnected()) {
			return ErrorCode.T50109_09;//FTP参数配置有误
		}
		FTPFile[] ftpFiles = ftpClient.listFiles(outdir);
		if (ftpFiles.length == 0) {
			ftpClient.logout();
			ftpClient.disconnect();
			return ErrorCode.T50109_12;
		}
		//设置文件流传输
		ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		//设置缓冲
		ftpClient.setBufferSize(1024);
		//设置文件编码
		FileOutputStream fos = null;
		String downloadFilePath = downloadDir + "/" + dateSettlmt + "/";
		File dowloadFileDir = new File(downloadFilePath);
		if (!dowloadFileDir.exists()) {
			dowloadFileDir.mkdir();
		}
		int outFileCount = 0;
		for (FTPFile ftpFile : ftpFiles) {
			if (!ftpFile.getName().matches("RS_158158_" + dateSettlmt + "_.*")) {//文件格式符合RS_158158_20151216_0001.txt
				continue;
			}
			try {
				fos = new FileOutputStream(downloadFilePath + ftpFile.getName());
				ftpClient.retrieveFile(outdir + "/" + ftpFile.getName(), fos);
				outFileCount ++;
				//从拷贝成功后，文件从FTP中的out目录删除
				boolean deleted = ftpClient.deleteFile(outdir + "/" + ftpFile.getName());
				if (!deleted) {
					log.warn("file named:" + ftpFile.getName() + " was not deleted from ftp server after downloaded.");
				}
			} catch (Exception e) {
				log.error(e);
			} finally {
				if (fos != null) {
					fos.close();
					fos.flush();
				}
			}
		}
		
		//注销并断开FTP连接
		ftpClient.logout();
		ftpClient.disconnect();
		
		if (outFileCount == 0) {
			if (dowloadFileDir.exists()) {
				for (File dowloadFile:dowloadFileDir.listFiles()) {
					dowloadFile.delete();
				}
				dowloadFileDir.delete();
			}
			return ErrorCode.T50109_13;
		}
		
		//拷贝结束后进行压缩
		TarZipUtils.zipCompress(dowloadFileDir.listFiles(), targetZipFile);
		if (dowloadFileDir.exists()) {
			for (File dowloadFile:dowloadFileDir.listFiles()) {
				dowloadFile.delete();
			}
			dowloadFileDir.delete();
		}
		
		return Constants.SUCCESS_CODE_CUSTOMIZE+targetZipFile.getAbsolutePath();
	}

	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	
}
