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
import org.dom4j.Node;

import com.allinfinance.bo.agentpay.T90701BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.agentpay.TblStlmFileTransInf;
import com.allinfinance.po.agentpay.TblStlmFileTransInfPK;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

public class SendStlmFiles {

	private static Logger log = Logger.getLogger(SendStlmFiles.class);
	
	/**
	 * 对账文件
	 *@author huangjl
	 * @param request
	 * @param response
	 * @param mchtNo
	 * @param oprType
	 * @return
	 * 2014年4月4日 上午10:05:10
	 */
	public String sendStlmFileMsg(HttpServletRequest request,HttpServletResponse response,
			String mchtNo, String oprType,String stlmDate){
		String txnCode = "1005";
		Operator opr = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String orgCode =opr.getOprBrhId() ;
		String staffID = opr.getOprId();
		T90701BO t90701 = (T90701BO)ContextUtil.getBean("T90701BO");
		TblStlmFileTransInfPK tpk = new TblStlmFileTransInfPK();
		tpk.setMchtNo(mchtNo);
		tpk.setStlmDate(CommonFunction.fillString(stlmDate, ' ', 8, true));
		//修改最近更新人
		if("0".equals(oprType)){
			//全部
			t90701.updateAllTlr(staffID);
		}else if("1".equals(oprType)){
			//指定
			TblStlmFileTransInf tmp=t90701.get(tpk);
			if(tmp==null){
				return "选择的商户和清算日期不存在";
			}
			tmp.setLstUpdTlr(staffID);
			t90701.update(tmp);
		}
		
		// 组装报文
		StringBuffer msgPackage = new StringBuffer();
		msgPackage.append("<?xml version='1.0' encoding='GBK'?>");
		msgPackage.append("<Message><Public>");
		msgPackage.append("<OrgCode value='").append(orgCode).append("' />");
		msgPackage.append("<StaffID value='").append(staffID).append("' />");
		msgPackage.append("<TxnCode value='").append(txnCode).append("' />");
		msgPackage.append("<StlmDate value='").append(stlmDate).append("' />");
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
			log.info("***** 发送出 代付—发送对账文件报文 *****");

			// 接收
			inputstream = socket.getInputStream();
			byte []bufhead = new byte[8];  //读取报文头
			inputstream.read(bufhead);
			byte []buflen = new byte[4];    //读取报文体长度 为bcd码
			inputstream.read(buflen);
			int bdyLength = 0;
			try {
				//转化bcd码为十进制数字 
				//1.先将bcd转换为16进制；
				//2.将16进制转为10进制
				String rcvMsgLen16 = CommonFunction.bytesToHexString(buflen);
				bdyLength = Integer.valueOf(rcvMsgLen16, 16);
			} catch (Exception e) {
				log.warn("返回报文长度错误", e);
				return "返回报文长度错误";
			}
			byte [] msgXml = new byte[bdyLength];
			inputstream.read(msgXml);
			ret = new String(msgXml,"GBK");
			
			log.info("***** 接收 代付—发送对账文件返回报文 *****");
			log.info("Rcv Message [" + ret + "]");
			Document doc = null;
			doc = DocumentHelper.parseText(ret);
			// 获取 报文信息
			Node rcvMsg_OrgCode_Node = doc.selectSingleNode("//OrgCode");
			Node rcvMsg_StaffID_Node = doc.selectSingleNode("//StaffID");
			Node rcvMsg_TxnCode_Node = doc.selectSingleNode("//TxnCode");
			Node rcvMsg_StlmDate_Node = doc.selectSingleNode("//StlmDate");
			Node rcvMsg_AcctDate_Node = doc.selectSingleNode("//AcctDate");
			Node rcvMsg_JrnlNum_Node = doc.selectSingleNode("//JrnlNum");
			Node rcvMsg_SysDate_Node = doc.selectSingleNode("//SysDate");
			Node rcvMsg_SysTime_Node = doc.selectSingleNode("//SysTime");
			Node rcvMsg_TxnStatus_Node = doc.selectSingleNode("//TxnStatus");
			Node rcvMsg_ReplyCode_Node = doc.selectSingleNode("//ReplyCode");
			Node rcvMsg_ReplyMsg_Node = doc.selectSingleNode("//ReplyMsg");
			Node rcvMsg_MchtNo_Node = doc.selectSingleNode("//MchtNo");
			Node rcvMsg_OprType_Node = doc.selectSingleNode("//OprType");
			
			String rcvMsg_OrgCode = rcvMsg_OrgCode_Node==null?"":rcvMsg_OrgCode_Node.valueOf("@value");
			String rcvMsg_StaffID = rcvMsg_StaffID_Node==null?"":rcvMsg_StaffID_Node.valueOf("@value");
			String rcvMsg_TxnCode = rcvMsg_TxnCode_Node==null?"":rcvMsg_TxnCode_Node.valueOf("@value");
			String rcvMsg_StlmDate = rcvMsg_StlmDate_Node==null?"":rcvMsg_StlmDate_Node.valueOf("@value");
			String rcvMsg_AcctDate = rcvMsg_AcctDate_Node==null?"":rcvMsg_AcctDate_Node.valueOf("@value");
			String rcvMsg_JrnlNum = rcvMsg_JrnlNum_Node==null?"":rcvMsg_JrnlNum_Node.valueOf("@value");
			String rcvMsg_SysDate = rcvMsg_SysDate_Node==null?"":rcvMsg_SysDate_Node.valueOf("@value");
			String rcvMsg_SysTime = rcvMsg_SysTime_Node==null?"":rcvMsg_SysTime_Node.valueOf("@value");
			String rcvMsg_TxnStatus = rcvMsg_TxnStatus_Node==null?"":rcvMsg_TxnStatus_Node.valueOf("@value");
			String rcvMsg_ReplyCode = rcvMsg_ReplyCode_Node==null?"":rcvMsg_ReplyCode_Node.valueOf("@value");
			String rcvMsg_ReplyMsg = rcvMsg_ReplyMsg_Node==null?"":rcvMsg_ReplyMsg_Node.valueOf("@value");
			String rcvMsg_MchtNo = rcvMsg_MchtNo_Node==null?"":rcvMsg_MchtNo_Node.valueOf("@value");
			String rcvMsg_OprType = rcvMsg_OprType_Node==null?"":rcvMsg_OprType_Node.valueOf("@value");
			ret =rcvMsg_ReplyCode;
			if(!StringUtil.isNull(ret)){
				if("CH10000".equals(ret)){	
					return "S";
				}else{
					StringBuffer tmpString = new StringBuffer();
					tmpString.append(":");
					tmpString.append(rcvMsg_ReplyMsg);
					if(":".equals(tmpString.toString().trim())){
						return "处理对账文件失败";
					}else{
						return "处理对账失败"+tmpString.toString().trim();
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
		}catch(Exception e){
			e.printStackTrace();
			return "对不起，执行出错";
		} finally {
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
}