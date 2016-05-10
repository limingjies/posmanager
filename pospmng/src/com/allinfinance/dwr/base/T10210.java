package com.allinfinance.dwr.base;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.allinfinance.system.util.SysParamUtil;

public class T10210 {

	public String download() {
	
		String txnNo = "6100";
		String bitStr = "3030373360000380094C5249001C000000000269080600000000026909055800094900060000003200016001000000000800800000000080000004000000000000003039393939393939313031";
		Socket socket = null;
		
		try {
			
			String ip = SysParamUtil.getParam("IP");
			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
			socket = new Socket(ip, port);
			OutputStream outputStream = socket.getOutputStream();
			byte[] bytes = new byte[bitStr.length() / 2];
			byte keyByteHigh;
			byte keyByteLow;
			int len = 0;
			// 压缩位图信息
			for(int j = 0; j < bitStr.length(); j++) {
				
				keyByteHigh = (byte) (bitStr.charAt(j) > '9' ? bitStr.charAt(j) - 'A' + 0x0a : bitStr.charAt(j) - '0');
				j++;
				keyByteLow = (byte) (bitStr.charAt(j) > '9' ? bitStr.charAt(j) - 'A' + 0x0a : bitStr.charAt(j) - '0');
				bytes[len++] = (byte) ((keyByteHigh << 4 & 0xf0) | (keyByteLow & 0x0f));
			}
			outputStream.write(bytes);
			outputStream.flush();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "-1";
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "-1";
		} catch (IOException e) {
			e.printStackTrace();
			return "-1";
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}finally{
			try {
				if(socket != null){
					socket.getOutputStream().close();
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "0";
	}
}