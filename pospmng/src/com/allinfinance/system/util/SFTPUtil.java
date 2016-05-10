package com.allinfinance.system.util;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 连接SFTP工具类
 * 
 * 
 */
public class SFTPUtil {
	private static Logger log = Logger.getLogger(SFTPUtil.class);

	/**
	 * 文件传输模式：OVERWRITE-完全覆盖模式
	 */
	private static int SFTP_PUT_PATTERN = ChannelSftp.OVERWRITE;

	private ChannelSftp sftp = null;
	private Session session = null;

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 端口
	 */
	private int port;

	public SFTPUtil() {

	}

	public SFTPUtil(String username, String password, String ip, int port) {
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
	}

	/**
	 * 连接
	 * 
	 */
	public void connect() throws Exception {
		JSch jsch = new JSch();
		// 根据username,ip,port获取session对象
		session = jsch.getSession(username, ip, port);
		session.setPassword(password);

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		// 为session配置properties
		session.setConfig(config);
		// 通过session建立连接
		session.connect();
		// 打开SFTP通道
		sftp = (ChannelSftp) session.openChannel("sftp");
		// 建立SFTP通道连接
		sftp.connect();
		// 日志
		log.info("连接SFTP成功");
		
		// 进入上传文件指定的路径

	}

	/**
	 * 关闭连接
	 * 
	 * @throws Exception
	 */
	public void disConnect() throws Exception {
		if(!sftp.isClosed()){
			sftp.disconnect();
			log.info("关闭SFTP通道成功");
		}
		if(session.isConnected()){
			session.disconnect();
			log.info("关闭session成功");
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param input
	 * @param fileName
	 * @throws Exception
	 */
	public void upload(BufferedInputStream input, String fileName)
			throws Exception {
		sftp.put(input, fileName, SFTP_PUT_PATTERN);
		log.info("上传文件成功");
	}
	
	
	/**
	 * 下载文件
	 * 
	 * @param input
	 * @param fileName
	 * @throws Exception
	 */
	public void download(String directory, String downloadFile,OutputStream os) throws Exception{
		try {
			sftp.cd(directory);
			sftp.get(downloadFile,os);
			log.info("文件写入输出流");
		} catch (SftpException e) {
			e.printStackTrace();
			throw e;
		} 
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void remove(String fileName) throws Exception {
		sftp.rm(fileName);
		log.info("删除文件成功");
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void create(String fileName) throws Exception {
		sftp.put(fileName);
		log.info("创建文件成功");
	}
	
}
