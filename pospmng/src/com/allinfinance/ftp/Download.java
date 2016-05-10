
package com.allinfinance.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.allinfinance.common.Constants;
import com.allinfinance.system.util.SysParamUtil;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;


public class Download {
	
	private static String ftpIP = SysParamUtil.getParam("ftpIP");
	private static String userName = SysParamUtil.getParam("ftpName");
	private static String passWord = SysParamUtil.getParam("ftpPwd");
	
	
	public static void fileDown( String downUrl, String fileName,String fileUrl) throws Exception {
		TelnetInputStream fget = null;
		FtpClient fc = null;

		try {
			if (!new File(downUrl).isDirectory()) {// 判断本地存放文件的文件夹是否存在
				new File(downUrl).mkdirs();
			}
			
			fc = new FtpClient();// ftp客户端对象
//			 connectServer(ftpIP, userName, passWord,savePath);
			fc.openServer(ftpIP);// 连接ftp服务器
			fc.login(userName, passWord);// 登录ftp服务器
			fc.binary();// 使用二进制的方式下载
			
//			System.out.println(fc.pwd());

			fget = fc.get(fileUrl+fileName);// 读取ftp远程文件
			
			
			 byte[] buf = new byte[204800];
	         int bufsize = 0;
	         FileOutputStream  ftpOut = new FileOutputStream(downUrl+fileName);              //存放在本地硬盘的物理位置
	            while ((bufsize = fget.read(buf, 0, buf.length)) != -1) {
	                        ftpOut.write(buf, 0, bufsize);
	              }
			
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new Exception(e.getMessage());
		} finally {
				fget.close();
				fc.closeServer();
		}
	}
	
	public static boolean check(String fileUrl,String fileName) {
		FTPClient ftp=new FTPClient();
		try {
			ftp.connect(ftpIP);
			ftp.login(userName, passWord);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			if(!ftp.changeWorkingDirectory(fileUrl)){
				ftp.logout();
				ftp.disconnect();
				return false;
			}
			FTPFile[] fs=ftp.listFiles();
			for (FTPFile file : fs) {
				if(fileName.equals(file.getName())){
					ftp.logout();
					ftp.disconnect();
					return true;
				}
			}
			ftp.logout();
			ftp.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return false;
	}
	
	public static String FTPDownLoad(String fileUrl,String fileName,String downUrl) {
		FTPClient ftp=new FTPClient();
		try {
			ftp.connect(ftpIP);
			ftp.login(userName, passWord);
			
			if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
				ftp.disconnect();
			}
			
			if(!ftp.changeWorkingDirectory(fileUrl)){
				ftp.logout();
				ftp.disconnect();
				return "该日期的文件不存在！";
			}
			
			
			boolean boo=false;
			FTPFile[] fs=ftp.listFiles();
			for (FTPFile file : fs) {
				if(fileName.equals(file.getName())){
					boo=true;
					break;
				}
			}
			if(!boo){
				return "该日期的文件不存在！";
			}
			
			if (!new File(downUrl).isDirectory()) {// 判断本地存放文件的文件夹是否存在
				new File(downUrl).mkdirs();
			}
			
			ftp.setBufferSize(1024);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			File localFile=new File(downUrl+fileName);
			OutputStream  is=new FileOutputStream(localFile);
			
			ftp.retrieveFile(fileName, is);
			is.close();
			
			
			ftp.logout();
			if(ftp.isConnected()){
				ftp.disconnect();
			}
			
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "对不起，FTP获取文件失败！";
	}
	
}
