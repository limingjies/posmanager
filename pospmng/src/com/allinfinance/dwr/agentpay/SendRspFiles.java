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
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.dao.iface.agentpay.TblSndPackDAO;
import com.allinfinance.po.agentpay.TblSndPack;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

public class SendRspFiles {

	private static Logger log = Logger.getLogger(SendRspFiles.class);
	private TblSndPackDAO tblSndPackDAO = (TblSndPackDAO) ContextUtil.getBean("SndPackDAO");
	public String sendExcuteMsg(HttpServletRequest request,HttpServletResponse response,
			String batchId) {
		//判断选择的批次是否已经发送过了，如果已经发送过则不能再次发送
		TblSndPack tmpTblSndPack = tblSndPackDAO.get(batchId);
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		if(tmpTblSndPack ==null){
			return "没有找到信息，请重试";
		}
		String tmpProStat = tmpTblSndPack.getProcStat();
		if(!("2".endsWith(tmpProStat) || "5".endsWith(tmpProStat) || "E".endsWith(tmpProStat))){
			return "该批次不可发送";
		}
		String ret = "";
		String txnCode = "1002";
		String orgCode =opr.getOprBrhId() ;
		String staffID = opr.getOprId();
		// 组装报文
		StringBuffer msgPackage = new StringBuffer();
		msgPackage.append("<?xml version='1.0' encoding='GBK'?>");
		msgPackage.append("<Message><Public>");
		msgPackage.append("<OrgCode value='").append(orgCode).append("' />");
		msgPackage.append("<StaffID value='").append(staffID).append("' />");
		msgPackage.append("<TxnCode value='").append(txnCode).append("' />");
		msgPackage.append("<BatchId value='").append(batchId).append("' />");
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

		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		
		try {
			// 连接
			log.info("Connection " + ip + ":" + port);
			socket = new Socket(ip, port);
			socket.setSoTimeout(1000 * 60);

			// 发送
			log.info("Send Message [" + sendMessage + "]");
			outputStream = socket.getOutputStream();
			outputStream.write(head.getBytes());// head
			outputStream.write(msgLen);// 长度，4字节网络字节序
			outputStream.write(sendMessage.getBytes());// body
			outputStream.flush();
			log.info("***** 发送出 代付—文件发送 报文 *****");

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
			log.info("***** 接收 代付—文件发送返回 报文 *****");
			log.info("Rcv Message [" + ret + "]");
			Document doc = null;
			doc = DocumentHelper.parseText(ret);
			String rcvOrgCode = doc.selectSingleNode("//OrgCode")==null?"":doc.selectSingleNode("//OrgCode").valueOf("@value");
			String rcvStaffID = doc.selectSingleNode("//StaffID")==null?"":doc.selectSingleNode("//StaffID").valueOf("@value");
			String rcvTxnCode = doc.selectSingleNode("//TxnCode")==null?"":doc.selectSingleNode("//TxnCode").valueOf("@value");
			String rcvAcctDate = doc.selectSingleNode("//AcctDate")==null?"":doc.selectSingleNode("//AcctDate").valueOf("@value");
			String rcvJrnlNum = doc.selectSingleNode("//JrnlNum")==null?"":doc.selectSingleNode("//JrnlNum").valueOf("@value");
			String rcvSysDate = doc.selectSingleNode("//SysDate")==null?"":doc.selectSingleNode("//SysDate").valueOf("@value");
			String rcvSysTime = doc.selectSingleNode("//SysTime")==null?"":doc.selectSingleNode("//SysTime").valueOf("@value");
			String rcvTxnStatus = doc.selectSingleNode("//TxnStatus")==null?"":doc.selectSingleNode("//TxnStatus").valueOf("@value");
			String rcvReplyCode = doc.selectSingleNode("//ReplyCode")==null?"":doc.selectSingleNode("//ReplyCode").valueOf("@value");
			String rcvReplyMsg = doc.selectSingleNode("//ReplyMsg")==null?"":doc.selectSingleNode("//ReplyMsg").valueOf("@value");
			String rcvBatchId = doc.selectSingleNode("//BatchId")==null?"":doc.selectSingleNode("//BatchId").valueOf("@value");
			ret = rcvReplyCode;
			if(!StringUtil.isNull(ret)){
				if("CH10000".equals(ret)){	
					return "S";
				}else{
					StringBuffer tmpString = new StringBuffer();
					tmpString.append(":");
					tmpString.append(rcvReplyMsg);
					if(":".equals(tmpString.toString().trim())){
						return "发送文件失败";
					}else{
						return "发送文件失败"+tmpString.toString().trim();
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
				outputStream =null;
				inputstream =null;
				socket =null;
			}
		}
	}
//	public TblSndPackDAO getTblSndPackDAO() {
//		return tblSndPackDAO;
//	}
//	public void setTblSndPackDAO(TblSndPackDAO tblSndPackDAO) {
//		this.tblSndPackDAO = tblSndPackDAO;
//	}
}