package com.allinfinance.socket;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.allinfinance.common.StringUtil;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SocketConnect;
import com.allinfinance.system.util.SysParamUtil;


public class AmtbackApply {

	private static Logger log = Logger.getLogger(AmtbackApply.class);
	public static String  getSendString(TxnSocket txnSocket) {
		 StringBuffer sendString = new StringBuffer("");
		 sendString.append(txnSocket.getTxnNum());
		 sendString.append(txnSocket.getPan().replace(" ", "").length()).append(CommonFunction.fillString(txnSocket.getPan().trim(), ' ', 19, true));
		 sendString.append(txnSocket.getAmtTrans());
		 sendString.append(txnSocket.getRevsalSsn());
		 sendString.append(txnSocket.getCardAccpTermId());
		 sendString.append(txnSocket.getCardAccpId());
		 String str60=CommonFunction.fillString(txnSocket.getFalg(), ' ', 12, true)+txnSocket.getInstDate().substring(4);
		 sendString.append(CommonFunction.fillString(String.valueOf(str60.length()), '0', 3, false)).append(str60);
		return sendString.toString();
	}
	
	public static String  getResString(String sendStr) {
		String resp=null;
		try {
			
		
		SocketConnect socket = null;
		String len=CommonFunction.fillString(String.valueOf(sendStr.length()), '0', 4, false);
		log.info("报文:[" + len + sendStr.toString() + "]");
			try {
				socket = new SocketConnect(len + sendStr.toString());
//				socket.run("gbk");
				socket.run();
				resp = socket.getRsp();
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(e);
			}finally {
				socket.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e);
		}
		return resp;
		
	}
	
private static String parseTmk(byte[] arrayTmk) {
		
		String tmk = "";		
		String high = "";		
		String low = "";
		
		for(int i = 0; i < arrayTmk.length; i++) {
			
			high = Integer.toHexString(arrayTmk[i] >> 4 & 0x0f);			
			low = Integer.toHexString(arrayTmk[i] & 0x0f);			
			tmk += high + low;			
		}
		
		// 16进制转为字符串返回
		return toStringHex(tmk);
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
			s = new String(baKeyword, "gbk");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
	public static String  getResString2(String sendStr) {
		
		try {
			
		
		String ip = SysParamUtil.getParam("IP");
		int port = 19010;
		
		String sendMessage = CommonFunction.fillString(String.valueOf(sendStr.length()), '0', 4, false)+sendStr;
		String ret = "";
		
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
			// 连接
		try {
			log.info("Connection " + ip + ":" + port);
			socket = new Socket(ip,port);
			socket.setSoTimeout(1000 * 60);
			
			// 发送
			log.info("Send Message [" + sendMessage + "]");
			outputStream = socket.getOutputStream();
			outputStream.write(sendMessage.getBytes());
			outputStream.flush();
			log.info("aaaaa");
			// 接收
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			byte[] len = new byte[4];
			InputStream in = socket.getInputStream();
			in.read(len);
			int length = 0;
			try {
				length = Integer.parseInt(new String(len));
			} catch (Exception e) {
				log.warn("返回报文长度错误", e);
				throw e;
			}
			byte[] respContent = new byte[length];
			in.read(respContent);
			ret = new String(respContent);
			log.info("Rcv Message [" + ret + "]");
			//log.info("Rcv Message [" + ret + "]");
			// 解析
			if(StringUtil.isNotEmpty(ret)){
				return ret;
			}
			/*if (!StringUtil.isNull(ret) && ret.length() > 273) {
				if ("00".equals(ret.substring(271, 273))) {
					return "S";
				} else {
					StringBuffer tmpString = new StringBuffer();
					tmpString.append(":");
					tmpString.append(ret.substring(273));
					
					if(":".equals(tmpString.toString().trim())){
						return "启动任务失败";
					}else{
						return "启动任务失败"+tmpString.toString().trim();
					}
					
				}
			}*/
			return "失败";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != inputstream) {
					inputstream.close();
				}
				if (null != socket) {
					socket.close();
				}
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
