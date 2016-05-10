/**
 * Title: 批处理管理Socket
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2012-3-13
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author caotz
 * 
 * @version 1.0
 */
package com.allinfinance.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.allinfinance.system.util.CommonFunction;

public class BatchSocketClient {
	private static Logger logger = Logger.getLogger(BatchSocketClient.class);

	public static final String nLine = "----------------------------------------------------------------------------";

	public static void main(String args[]) {
		BatchSocketClient socketClient = new BatchSocketClient();
//		socketClient.sendMsg("172.16.4.142", 29999, socketClient.start("0002", "20120304"));
//		socketClient.sendMsg("172.16.4.142", 29999, socketClient.onl());
		socketClient.sendMsg("172.16.4.142", 29999, socketClient.run("20120410", "0001","201204102025"));
	}
	
	
	// 启动全部批量任务
	public byte[] run(String startBatId,String grpIdRun,String startBatDateTime) {
		String body =
				 CommonFunction.fillString(grpIdRun, ' ', 5, true)+
				 CommonFunction.fillString(startBatId, ' ', 9, true)
		+ CommonFunction.fillString(startBatDateTime, ' ', 15, true);
		byte[] reqCmd = new byte[452];
		int len = 0;
		reqCmd[len++] = (byte)0x05;
		reqCmd[len++] = (byte)0x00;
		reqCmd[len++] = (byte)0x00;
		reqCmd[len++] = (byte)0x00;
		for(int i=0;i<260;i++) {
			reqCmd[len++] = (byte)0x00;
		}
		char idAndDate[] = body.toCharArray();
		for(int i=0;i<idAndDate.length;i++) {
			reqCmd[len++] = (byte)(0x0 + idAndDate[i]);
		}
		for(int i=0;i<452-idAndDate.length-260-4;i++) {
			reqCmd[len++] = (byte)0x00;
		}
		return reqCmd;
	}
	
	// 启动联机批量任务
	public byte[] onl() {
		byte[] reqCmd = new byte[452];
		int len = 0;
		reqCmd[len++] = (byte)0x0A;
		reqCmd[len++] = (byte)0x00;
		reqCmd[len++] = (byte)0x00;
		reqCmd[len++] = (byte)0x00;
		for(int i=0;i<260;i++) {
			reqCmd[len++] = (byte)0x00;
		}
		for(int i=0;i<188;i++) {
			reqCmd[len++] = (byte)0x00;
		}
		return reqCmd;
	}
	
	// 启动单个批量任务
	public byte[] start(String startBatId,String startBatDate) {
		String body = CommonFunction.fillString(startBatId, ' ', 7, true)
		+ CommonFunction.fillString(startBatDate, ' ', 9, true);
		byte[] reqCmd = new byte[452];
		int len = 0;
		reqCmd[len++] = (byte)0x02;
		reqCmd[len++] = (byte)0x00;
		reqCmd[len++] = (byte)0x00;
		reqCmd[len++] = (byte)0x00;
		for(int i=0;i<260;i++) {
			reqCmd[len++] = (byte)0x00;
		}
		char idAndDate[] = body.toCharArray();
		for(int i=0;i<idAndDate.length;i++) {
			reqCmd[len++] = (byte)(0x0 + idAndDate[i]);
		}
		for(int i=0;i<172;i++) {
			reqCmd[len++] = (byte)0x00;
		}
		return reqCmd;
	}
	
	//发送报文
	public String sendMsg(String ip,int port, byte[] reqBody) {
		logger.info("发送报文：["+new String(reqBody)+"]");
		String state = null;
		Socket socket = null;
		InetSocketAddress inetSocketAddress = null;
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			socket = new Socket(); 
			inetSocketAddress = new InetSocketAddress(ip, port);
			socket.connect(inetSocketAddress, 60 * 1000);
			int reqBodyLen = reqBody.length;
			String strReqBodyLen = CommonFunction.fillString(String.valueOf(reqBodyLen), '0', 4, false);
			char charReqBodyLen[] = strReqBodyLen.toCharArray();
			byte[] reqHead = new byte[charReqBodyLen.length];
			int len = 0;
			for(int i=0;i<charReqBodyLen.length;i++) {
				reqHead[len++] = (byte)(0x0 + charReqBodyLen[i]);
			}
			byte[] reqCmdResulet = new byte[reqHead.length + reqBody.length];
			System.arraycopy(reqHead, 0, reqCmdResulet, 0, reqHead.length);
			System.arraycopy(reqBody, 0, reqCmdResulet, reqHead.length, reqBody.length);
			out = new BufferedOutputStream(socket.getOutputStream());
			in = new BufferedInputStream(socket.getInputStream());
			out.write(reqCmdResulet,0,reqCmdResulet.length);
			out.flush();
//			logger.info("reqCmd head:\n" + trace(reqHead) + ".........");
//			logger.info("reqCmd body:\n" + trace(reqBody) + ".........");
			
			// 报文头长度
			byte[] headLen = new byte[4]; 
			in.read(headLen);
			// 从resBytes中读取4位到headLen中
			String lenStr = new String(headLen).trim();
			int length = ("".equals(lenStr) ? 0 : Integer.parseInt(lenStr));
			logger.info("返回报文长度：" + length);
			// 报文体的长度
			byte[] bodyBytes = new byte[length];
			// 读取数据源中的报文体数据
			in.read(bodyBytes);
			// 构建返回的报文
			state = new String(bodyBytes, "GBK");
			logger.info("Socket返回报文：" + state);
		} catch (IOException e) {
			logger.info("socket connect exception：" + e.fillInStackTrace());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
				if(out != null) {
					out.close();
				}
				if(socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				logger.info("close socket exception：" + e.fillInStackTrace());
			}
		}
		return state;
	}
	
	public static String stringToHexString(String strPart) {
        String hexString = "";
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch); 
            hexString = hexString + strHex;
        }
        return hexString;
    }

	
	public static String getHexString(byte[] b) throws Exception {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
		}

	
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	public BatchSocketClient() {

	}

	public static String trace(byte inBytes[]) {
		int j = 0;
		byte temp[] = new byte[76];
		bytesSet(temp, ' ');
		StringBuffer strc = new StringBuffer(" ");
		strc.append("----------------------------------------------------------------------------\n");
		for (int i = 0; i < inBytes.length; i++) {
			if (j == 0) {
				System.arraycopy(
						String.format("%03d: ",
								new Object[] { Integer.valueOf(i) }).getBytes(),
						0, temp, 0, 5);
				System.arraycopy(
						String.format(":%03d",
								new Object[] { Integer.valueOf(i + 15) })
								.getBytes(), 0, temp, 72, 4);
			}
			System.arraycopy(
					String.format("%02X ",
							new Object[] { Byte.valueOf(inBytes[i]) })
							.getBytes(), 0, temp, j * 3 + 5 + (j <= 7 ? 0 : 1),
					3);
			if (inBytes[i] == 0)
				temp[j + 55 + (j <= 7 ? 0 : 1)] = 46;
			else
				temp[j + 55 + (j <= 7 ? 0 : 1)] = inBytes[i];
			if (++j == 16) {
				strc.append(new String(temp)).append("\n");
				bytesSet(temp, ' ');
				j = 0;
			}
		}

		if (j != 0) {
			strc.append(new String(temp)).append("\n");
			bytesSet(temp, ' ');
		}
		strc.append("----------------------------------------------------------------------------\n");
		return strc.toString();
	}

	private static void bytesSet(byte inBytes[], char fill) {
		if (inBytes.length == 0)
			return;
		for (int i = 0; i < inBytes.length; i++)
			inBytes[i] = (byte) fill;

	}

	public static byte[] Int2ByteArray(int iSource, int iArrayLen) {
		byte bLocalArr[] = new byte[iArrayLen];
		for (int i = iArrayLen; i < 4 && i > 0; i--)
			bLocalArr[i - 1] = (byte) (iSource >> 8 * (iArrayLen - i) & 0xff);

		return bLocalArr;
	}

	public static byte[] Int2Byte(int send) throws IOException {
		byte bTemp[] = new byte[4];
		byte bRtn[] = new byte[4];
		for (int i = 0; i < 4; i++)
			bTemp[i] = (byte) (send >> i * 8 & 0xff);

		for (int i = 0; i < bTemp.length; i++)
			bRtn[i] = bTemp[3 - i];

		return bRtn;
	}
}
