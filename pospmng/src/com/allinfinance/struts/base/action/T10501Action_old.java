/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-30       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.struts.base.action;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

import com.allinfinance.common.Constants;
import com.allinfinance.exception.AppException;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TCPClient;

import java.net.UnknownHostException;



/**
 * Title:机构密钥同步
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-30
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10501Action_old extends BaseAction {
	private TCPClient client = null;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		try {
			if("syKey".equals(method)) {
				rspCode = syKey();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，机构密钥同步操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 机构密钥同步
	 * @return
	 * @throws Exception 
	 */
//	private String syKey() throws Exception {
//		
//		//String bitStr = "3030373360000380094C5249001C000000000269080600000000026909055800094900060000003200016001000000000800800000000080000004000000000000003039393939393939313031";
//		
//		String sendMsgInfo = "81010800" + "09999999" + "101" ;
//	
//		// "报文内容长度" 4位 不足左补0
//		String sendMsgInfoLen = CommonFunction.fillString(String.valueOf(sendMsgInfo.length()), '0', 4, false);
//		
//		// "最终发送的报文" = "报文内容长度" + "报文内容"
//		String sendMsg = sendMsgInfoLen + sendMsgInfo;
//		
//		Socket socket = null ;
//		try {
//			
//			String ip = SysParamUtil.getParam("IP");
//			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
//			int timeout = Integer.parseInt(SysParamUtil.getParam("TIMEOUT"));
//
//			socket = new Socket(ip,port);
//			socket.setSoTimeout(timeout);
//			OutputStream outputStream = socket.getOutputStream();
//			
//			/*byte[] bytes = new byte[bitStr.length() / 2];
//			
//			byte keyByteHigh;
//			
//			byte keyByteLow;
//			
//			int len = 0;
//			// 压缩位图信息
//			for(int j = 0; j < bitStr.length(); j++) {
//				
//				keyByteHigh = (byte) (bitStr.charAt(j) > '9' ? bitStr.charAt(j) - 'A' + 0x0a : bitStr.charAt(j) - '0');
//				
//				j++;
//				
//				keyByteLow = (byte) (bitStr.charAt(j) > '9' ? bitStr.charAt(j) - 'A' + 0x0a : bitStr.charAt(j) - '0');
//				
//				bytes[len++] = (byte) ((keyByteHigh << 4 & 0xf0) | (keyByteLow & 0x0f));
//			}*/
//
//			outputStream.write(sendMsg.getBytes());
//			outputStream.flush();
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			log("密钥同步");
//			return "-1";
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//			log("密钥同步");
//			return "-1";
//		} catch (IOException e) {
//			e.printStackTrace();
//			log("密钥同步");
//			return "-1";
//		} catch (Exception e) {
//			e.printStackTrace();
//			log("密钥同步");
//			return "-1";
//		}finally{
//			try {
//				if(socket != null){
//					socket.getOutputStream().close();
//					socket.close();
//				}
//				 
//			} catch (Exception e) {
//				e.printStackTrace();
//				log("密钥同步");
//			}
//		}
//		return Constants.SUCCESS_CODE;
//	}
	
	private String syKey() throws Exception {
		Object[] command = genCommand();
		
		byte[] cmd = (byte[]) command[0];
		int len = Integer.parseInt(command[1].toString());
		
		String ip = SysParamUtil.getParam("REMOTE_IP");
		int port = Integer.parseInt(SysParamUtil.getParam("REMOTE_PORT"));
		
		client = new TCPClient(ip, port);
		//发送指令
		client.sendMessage(cmd,len);
		log("message send success wait for rspMessage!");
		//接收密钥同步应答
		byte[] rspMessage = client.receiveMessage();
		log("message recevive success!");
		//解析密钥同步应答
		String result = parseSync(rspMessage);
		if(result.length() > 2) {
			if("00".equals(result.substring(8,10))) {//应答码00为成功
				return Constants.SUCCESS_CODE;
			} else {
				return "-1";
			}
		} else {
			return "-1";
		}
	}
	
	private Object[] genCommand() throws Exception {
		byte[] cmd = new byte[1024];		
		int len = 0;
		
		String txnCode = "6101";
		String cardNo = "1234567890123456789";
		String termNo = "12345678";
		String exFixed = "003F603903F44003F61";
		
		String cmdStr = txnCode + cardNo + termNo + exFixed;
		byte[] cmdStrBytes = cmdStr.getBytes();
		len = cmdStrBytes.length;
		String lenStr = CommonFunction.fillString(String.valueOf(len), '0', 4, false);
		byte[] lenBytes = lenStr.getBytes();
		System.arraycopy(lenBytes, 0, cmd, 0, lenBytes.length);
		System.arraycopy(cmdStrBytes, 0, cmd, lenBytes.length, cmdStrBytes.length);
		
		return new Object[]{cmd, len};
	}
	
private String parseSync(byte[] arrayRsp) {
		
		String rsp = "";		
		String high = "";		
		String low = "";
		
		for(int i = 0; i < arrayRsp.length; i++) {
			
			high = Integer.toHexString(arrayRsp[i] >> 4 & 0x0f);			
			low = Integer.toHexString(arrayRsp[i] & 0x0f);			
			rsp += high + low;			
		}
		
		// 16进制转为字符串返回
		return toStringHex(rsp);
	}

	public static String toStringHex(String s) {
	byte[] baKeyword = new byte[s.length() / 2];
	for (int i = 0; i < baKeyword.length; i++) {
		try {
			baKeyword[i] = (byte) (0xff & Integer.parseInt(
					s.substring(i * 2, i * 2 + 2), 16));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	try {
		s = new String(baKeyword, "utf-8");// UTF-16le:Not
	} catch (Exception e1) {
		e1.printStackTrace();
	}
	return s;
}
	
}
