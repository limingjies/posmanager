package com.allinfinance.dwr.agentpay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

public class GetDSFFiles {

	private static Logger log = Logger.getLogger(GetDSFFiles.class);

	/**
	 * 
	 * @author huangjl
	 * @param orgCode
	 *            机构号
	 * @param staffID
	 *            操作员
	 * @param txnCode
	 *            交易码
	 * @param mchtNo
	 *            商户号
	 * @param oprType
	 *            0-获取所有商户文件,1-获取指定商户文件
	 * @return 2014年3月12日 下午3:10:57
	 * @throws Exception
	 */
	public String sendGetFileMsg(HttpServletRequest request,HttpServletResponse response,
			String mchtNo, String oprType) {
		String txnCode = "1001";
		Operator opr = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String orgCode =opr.getOprBrhId() ;
		String staffID = opr.getOprId();
		// 组装报文
		StringBuffer msgPackage = new StringBuffer();
		msgPackage.append("<?xml version='1.0' encoding='GBK'?>");
		msgPackage.append("<Message><Public>");
		msgPackage.append("<OrgCode value='").append(orgCode).append("' />");
		msgPackage.append("<StaffID value='").append(staffID).append("' />");
		msgPackage.append("<TxnCode value='").append(txnCode).append("' />");
		msgPackage.append("<MchtNo value='").append(mchtNo).append("' />");
		msgPackage.append("<OprType value='").append(oprType).append("' />");
		msgPackage.append("</Public></Message>");
		//转换为16进制
		String tmp;
		try {
			tmp = String.valueOf(Integer.toHexString(msgPackage.toString().getBytes("gbk").length));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "转换发送报文长度失败";
		}
		//转换为内存中的形式
		String tmp2 =  CommonFunction.fillString(tmp, '0', 8, false); // 00 00 00 A8
		byte[] msgLen = CommonFunction.str2Bcd(tmp2);
		
		String sendMessage =msgPackage.toString();
		
		String head =SysParamUtil.getParam("RCV_MSG_HEAD");
		head = CommonFunction.fillString(head, ' ', 33, true);
		// 代付后台 C程序地址和接口
		String ip = SysParamUtil.getParam("DSF_IP");
		int port = Integer.parseInt(SysParamUtil.getParam("DSF_PORT"));

		String ret = "";
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		try {
			// 连接
			log.info("Connection " + ip + ":" + port);
			socket = new Socket(ip, port);
			socket.setSoTimeout(1000 * 60);

			// 发送
			log.info("Send Message ["+ head+ sendMessage + "]");
			outputStream = socket.getOutputStream();
			outputStream.write(head.getBytes());// head
			outputStream.write(msgLen);// 长度，4字节网络字节序
			outputStream.write(sendMessage.getBytes());// body
			outputStream.flush();
			log.info("***** 发送出 代付—获取文件报文 *****");

			// 接收
			inputstream = socket.getInputStream();
			byte []bufhead = new byte[8];  //读取报文头
			inputstream.read(bufhead);
			byte []buflen = new byte[4];    //读取报文体长度 为bcd码
			inputstream.read(buflen);
			int bdyLength = 0;
			try {
				//转化bcd码为十进制数字 1.先将bcd转换为16进制；2.将16进制转为10进制
				String rcvMsgLen16 = CommonFunction.bytesToHexString(buflen);
				bdyLength = Integer.valueOf(rcvMsgLen16, 16);
			} catch (Exception e) {
				log.warn("返回报文长度错误", e);
				return "返回报文长度错误";
			}
			byte [] msgXml = new byte[bdyLength];
			inputstream.read(msgXml);
			ret = new String(msgXml,"GBK");
			
			
			log.info("Rcv Message [" + ret + "]");
			Document doc = null;
			doc = DocumentHelper.parseText(ret);
			// 获取 报文信息
			String rcvMsg_OrgCode = doc.selectSingleNode("//OrgCode")==null?"":doc.selectSingleNode("//OrgCode").valueOf("@value");
			String rcvMsg_StaffID = doc.selectSingleNode("//StaffID")==null?"":doc.selectSingleNode("//StaffID").valueOf("@value");
			String rcvMsg_TxnCode = doc.selectSingleNode("//TxnCode")==null?"":doc.selectSingleNode("//TxnCode").valueOf("@value");
			String rcvMsg_AcctDate = doc.selectSingleNode("//AcctDate")==null?"":doc.selectSingleNode("//AcctDate").valueOf("@value");
			String rcvMsg_JrnlNum = doc.selectSingleNode("//JrnlNum")==null?"":doc.selectSingleNode("//JrnlNum").valueOf("@value");
			String rcvMsg_SysDate = doc.selectSingleNode("//SysDate")==null?"":doc.selectSingleNode("//SysDate").valueOf("@value");
			String rcvMsg_SysTime = doc.selectSingleNode("//SysTime")==null?"":doc.selectSingleNode("//SysTime").valueOf("@value");
			String rcvMsg_TxnStatus = doc.selectSingleNode("//TxnStatus")==null?"":doc.selectSingleNode("//TxnStatus").valueOf("@value");
			String rcvMsg_ReplyCode = doc.selectSingleNode("//ReplyCode")==null?"":doc.selectSingleNode("//ReplyCode").valueOf("@value");
			String rcvMsg_ReplyMsg = doc.selectSingleNode("//ReplyMsg")==null?"":doc.selectSingleNode("//ReplyMsg").valueOf("@value");
			String rcvMsg_MchtNo = doc.selectSingleNode("//MchtNo")==null?"":doc.selectSingleNode("//MchtNo").valueOf("@value");
			String rcvMsg_OprType = doc.selectSingleNode("//OprType")==null?"":doc.selectSingleNode("//OprType").valueOf("@value");
			ret =rcvMsg_ReplyCode;
			if(!StringUtil.isNull(ret)){
				if("CH10000".equals(ret)){	
					return "S";
				}else{
					StringBuffer tmpString = new StringBuffer();
					tmpString.append(":");
					tmpString.append(rcvMsg_ReplyMsg);
					if(":".equals(tmpString.toString().trim())){
						return "获取文件失败";
					}else{
						return "获取文件失败"+tmpString.toString().trim();
					}
				}
				
			}else{
				return "对不起 执行失败";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "连接失败，请重试";
		} catch (IOException e) {
			e.printStackTrace();
			return "连接异常，请重试";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "返回报文出错。";
		} catch(Exception e){
			e.printStackTrace();
			return "对不起，执行出错";
		}finally {
			try {
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != inputstream) {
					inputstream.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				outputStream = null;
				inputstream = null;
				socket = null;
			}
		}
	}
}