package com.allinfinance.dwr.mchnt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.allinfinance.bo.impl.mchnt.TblMchntService;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.po.mchnt.TblGroupMchtInf;
import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T20100 {
	
	private static Logger log = Logger.getLogger(T20100.class);
	
	private static Hashtable<String,String> typeMap = new Hashtable();
	
	static {
		
		typeMap.put("01","身份证");
		typeMap.put("02","军官证");
		typeMap.put("03","文职干部证");
		typeMap.put("04","警官证");
		typeMap.put("05","士兵证");
		typeMap.put("06","护照");
		typeMap.put("07","港澳台居民通行证");
		typeMap.put("08","户口簿");
		typeMap.put("09","边民出入境通行证");
		typeMap.put("10","外国人永久居留证");
		typeMap.put("11","临时身份证");
		typeMap.put("24","军官退休证");
		typeMap.put("25","文职干部退休证");
		typeMap.put("26","军事院校学员证");
		typeMap.put("31","武警士兵证");
		typeMap.put("33","武警文职干部证");
		typeMap.put("34","武警军官退休证");
		typeMap.put("35","武警文职干部退休证");
		typeMap.put("49","其他(对私)");
		typeMap.put("52","营业执照");
		typeMap.put("53","登记证书");
		typeMap.put("54","批文");
		typeMap.put("55","开户证明");
		typeMap.put("99","其他(对公)");
	}
	
	public String deleteImgFile(String fileName) {
		
		try {
			
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			File file = new File(basePath + fileName);
			if (file.exists()) {
				file.delete();
			}
			return "S";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "E";
	}
	public String deleteImgFileTmp(String fileName,String mcht) {
		
		try {
			
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			if(StringUtil.isNotEmpty(mcht)){
				basePath+=mcht+"/";
			}else{
				basePath+="addTmp/";
			}
			File file = new File(basePath + fileName);
			if (file.exists()) {
				file.delete();
			}
			return "S";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "E";
	}
	
	/**
	 * 验证商户账户
	 */
	public HashMap<String, String> verifyAccount(String account) throws IOException {

		HashMap<String, String> map = new LinkedHashMap<String, String>();
		
		// 组装要发送的报文
		String fillStr = "000000000000000000009999999";
		String accLen = CommonFunction.fillString(String.valueOf(account.length()), '0', 3, false);
		String sendEnd = SysParamUtil.getParam("MCHTNUM") + fillStr + accLen + account;
		String fillLen = CommonFunction.fillString(String.valueOf(sendEnd.length()), '0', 4, false);
		String sendMsg = fillLen + sendEnd;
		byte[] reqByte = sendMsg.getBytes();

		// 获取IP、PORT、TIMEOUT
		String ip = SysParamUtil.getParam("IP");
		int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
		int timeout = Integer.parseInt(SysParamUtil.getParam("TIMEOUT"));
		
		// 定义socket、输入输出流
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		
		try {
			
			// 获取socket、输入输出流
			socket = new Socket(ip,port);
			socket.setSoTimeout(timeout);
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
			
			// 发送报文
			outputStream.write(sendMsg.getBytes());
			outputStream.flush();
			log.info("发送内容: " + ip + ":" + port + " <-- {" + sendMsg + "}");

			// 接收报文的长度
			byte[] bRevLen = new byte[4];
			inputstream.read(bRevLen);
			int iRevLen = Integer.parseInt(new String(bRevLen));
			log.info("接收长度: " + ip + ":" + port + " --> {" + iRevLen + "}");
			
			// 接收报文的内容
			byte[] bRevMsq = new byte[iRevLen];
			inputstream.read(bRevMsq);
			String sRevMsq = new String(bRevMsq);
			log.info("接收内容: " + ip + ":" + port + " --> {" + sRevMsq + "}");

			// 解析接收到的报文内容
			String txnCode = sRevMsq.substring(0,4); // 交易码			
			String reSta = sRevMsq.substring(4,6); // 应答码
			
			if("00".equals(reSta)) {
				
				String reMsqVal = sRevMsq.substring(6,27); // 应答信息				
				String _61_LEN = sRevMsq.substring(27,30); // 61域长度
				String _61_VAL = sRevMsq.substring(32);	// 61域内容

				// 解析61域
				String certifType = typeMap.get(_61_VAL.substring(0,2)); // 证件类型
				String certifNo = _61_VAL.substring(2,22).trim(); // 证件号码
				String cliName = _61_VAL.substring(22,82).trim(); // 客户姓名
				String cliBrhNo = _61_VAL.substring(82,91).trim(); // 开户行号
				String cliType = _61_VAL.substring(91,92); // 账户类型 1-对私 2-对公
				if("1".equals(cliType)) {
					cliType = "P-本行对私账户";
				} else {
					cliType = "A-本行对公账户";
				}
				
				map.put("certifType", certifType);
				map.put("certifNo", certifNo);
				map.put("cliName", cliName);
				map.put("cliBrhNo", cliBrhNo);
				map.put("cliType", cliType);
				map.put("result", "S");
				map.put("msg", "验证完成");
				
			} else {
				
				map.put("result", "F");
				map.put("msg", "无该账户信息");
			}
			
			return map;
			
		} catch (Exception e) {
			
			log.error(e);
		} finally {
			try {
				if (null != inputstream) {
					inputstream.close();
				}
			} catch (Exception e) {}
			try {
				if (null != outputStream) {
					outputStream.close();
				}
			} catch (Exception e) {}
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {}
		}
		
		map.put("result", "E");
		map.put("msg", "请求账号验证服务异常");
		
		return map;
	}
	
	/**
	 * 验证商户内部账户
	 * 2012-7-20上午09:30:00
	 */
	public HashMap<String, String> verifyInAccount(String account) throws IOException {
		
		HashMap<String, String> map = new LinkedHashMap<String, String>();		
		
		String fillStr = "000000000000000000009999999";
		String accLen = CommonFunction.fillString(String.valueOf(account.length()), '0', 3, false);//左填充账号长度到3位
		String sendEnd = SysParamUtil.getParam("MCHTINNUM") + fillStr + accLen + account;
		String fillLen = CommonFunction.fillString(String.valueOf(sendEnd.length()), '0', 4, false);//左填充报文长度到4位
		String sendMsg = fillLen + sendEnd;
		byte[] reqByte = sendMsg.getBytes();
		log.info("账号:[" + account + "]");
		log.info("报文:[" + sendMsg + "]");

		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		
		try {

			String ip = SysParamUtil.getParam("IP");
			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
			int timeout = Integer.parseInt(SysParamUtil.getParam("TIMEOUT"));
			log.info("目的(IP):[" + ip + "]");
			log.info("目的(PORT):[" + port + "]");
			socket = new Socket(ip,port);
			socket.setSoTimeout(timeout);
			outputStream = socket.getOutputStream();

			outputStream.write(sendMsg.getBytes());
			outputStream.flush();
			log.info("报文(SEND):[" + sendMsg + "]");

			byte[] readBytes = new byte[200];
			inputstream = socket.getInputStream();
			inputstream.read(readBytes);
			String revMsq = new String(readBytes);
			log.info("报文(REV):[" + revMsq + "]");
			log.info("返回状态:[" + revMsq.substring(8,10) + "]");

			if("00".equals(revMsq.substring(8,10))){
				map.put("result", "S");
				map.put("msg", "验证完成");
			} else {
				map.put("result", "F");
				map.put("msg", "无该账户信息");
			}
			
			return map;
			
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (null != inputstream) {
					inputstream.close();
				}
			} catch (Exception e) {}
			try {
				if (null != outputStream) {
					outputStream.close();
				}
			} catch (Exception e) {}
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {}
		}
		
		map.put("result", "E");
		map.put("msg", "请求账号验证服务异常");
		
		return map;
	}
	
	public String getGroupMchnt(String groupMchtCd,HttpServletRequest request,
			HttpServletResponse response) {
		String jsonData = null;
		try {
			TblMchntService service = (TblMchntService) ContextUtil.getBean("tblMchntService");
			TblGroupMchtInf info = service.getGroupInf(groupMchtCd);
			TblGroupMchtSettleInf settleInfo = service.getGroupSettleInf(info.getGroupMchtCd());
//            把清算信息存在临时域
			if(null==settleInfo){
				return "0";
			}
			info.setMchtPerson(settleInfo.getSettleAcct().trim());
			info.setContactAddr(settleInfo.getSettleAcctMid().trim());
			if(info != null)
				jsonData = JSONArray.fromObject(info).toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 核实开户行号是否存在 by xupengfei
	 * @param checkStr 待验证的开户行号
	 * @return
	 */

	public String checkCnapsId(String checkStr) {
		String checkFlag = "N"; //待验证的开户行号为空
		if(!"".equals(checkStr)){
			String checkSql = "SELECT COUNT(*) FROM TBL_CNAPS_INFO WHERE CNAPS_ID = '"+checkStr+"'";
			String check = CommonFunction.getCommQueryDAO().findCountBySQLQuery(checkSql);
			if(!"0".equals(check)){
				checkFlag = "T"; //待验证的开户行号存在
			}else{
				checkFlag = "F"; //待验证的开户行号不存在
			}
		}
		//return checkFlag;
		return "T";
	}
	
}