package com.allinfinance.dwr.term;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.allinfinance.bo.term.TblTermKeyBO;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.dao.base.TblTermKeyDAO;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.TblTermKeyPK;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TCPClient;
import com.allinfinance.system.util.TarZipUtils;

public class DownloadTmk {

	private TblTermKeyBO tblTermKeyBO;
	private static Logger log = Logger.getLogger(DownloadTmk.class);
	private TCPClient client = null;
	private String ip = com.allinfinance.system.util.SysParamUtil.getParam("REMOTE_IP");
	private int port = Integer.parseInt(com.allinfinance.system.util.SysParamUtil.getParam("REMOTE_PORT"));
	
	// comment by kchen@20131108:old
//	public String downloadTmk(String value) {
//		
//		try {
//			
//			String mchtId = value.split("@")[0];
//			String termId = value.split("@")[1];
//			String sql = "select brh.cup_brh_id from tbl_brh_info brh, tbl_mcht_base_inf mcht " +
//					"where brh_id = mcht.BANK_NO and mcht_no = '" + mchtId + "'";
//			List dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
//			String cupBrhId = dataList.get(0).toString();
//			log.info("cupBrhId:" + cupBrhId);
//			log.info("mchtId:" + mchtId);
//			log.info("termId:" + termId);
//			Object[] command = genCommand(cupBrhId, mchtId, termId);
//			byte[] cmd = (byte[]) command[0];
//			int len = Integer.parseInt(command[1].toString());
//			
//			client = new TCPClient(ip, port);
//			//发送指令
//			client.sendMessage(cmd,len);
//			log.info("message send success wait for rspMessage!");
//			//接收TMK
//			byte[] rspMessage = client.receiveMessage();
//			log.info("message recevive success!");
//			//解析TMK
//			
//			String result = parseTmk(rspMessage);
//			
//			if(result.length() > 2) {
//				if("41".equals(result.substring(0,2))) {//A开头为成功
//					String tmk = result.substring(4, 52);//截48个字节为TMK，32密钥 + 16校验位
//					log.info("TMK:" + tmk);
//					String ret = "";
//					for(int i = 1; i <= 10; i++) {
//						ret += tmk;
//					}
//					log.info("ret:" + cupBrhId + "|" + ret);
//					return cupBrhId + "|" + ret + "|" + termId + "|" + mchtId;
//				} else {
//					return "-1";
//				}
//			} else {
//				return "-1";
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.info(e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info(e.getMessage());
//		} finally {
//			if(client != null) {
//				client.destroy();
//			}
//		}
//		return "-1";
//	}
	
	public String downloadTmk(String value) {
		try {
			String mchtId = value.split("@")[0];
			String termId = value.split("@")[1];
			log.info("mchtId:" + mchtId);
			log.info("termId:" + termId);
			
			Object[] command = genCommand(mchtId, termId);
			byte[] cmd = (byte[]) command[0];
			int len = Integer.parseInt(command[1].toString());
			
			client = new TCPClient(ip, port);
			//发送指令
			client.sendMessage(cmd,len);
			log.info("message send success wait for rspMessage!");
			//接收TMK
			byte[] rspMessage = client.receiveMessage();
			log.info("message recevive success!");
			//解析TMK
			String result = parseTmk(rspMessage);
			
			if(result.length() > 2) {
				if("00".equals(result.substring(8,10))) {//应答码00为成功
//					String tmk = result.substring(4, 52);//截48个字节为TMK，32密钥 + 16校验位
//					log.info("TMK:" + tmk);
					String ret = result.substring(8,10);
//					for(int i = 1; i <= 10; i++) {
//						ret += tmk;
//					}
//					log.info("ret:" + ret);
					return ret + "|" + termId + "|" + mchtId;
				} else {
					return "-1";
				}
			} else {
				return "-1";
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} finally {
			if(client != null) {
				client.destroy();
			}
		}
		return "-1";
	}
	
	// comment by kchen@20131108: old
//	public String updateStatus(String mchntNo,String termId,String tmk) {
//		
//		try {
//			
//			String sql = "update tbl_term_key set pos_tmk='" + tmk + "' , tmk_st='1' where mcht_cd='" + mchntNo +"' and term_id='" + termId + "'";
//			CommonFunction.getCommQueryDAO().excute(sql);
////			com.allinfinance.dao.impl.term.TblTermKeyDAO tblTermKeyDao = new com.allinfinance.dao.impl.term.TblTermKeyDAO();
////			com.allinfinance.po.TblTermKeyPK pk = new com.allinfinance.po.TblTermKeyPK();
////			pk.setMchtCd(mchntNo);
////			pk.setTermId(termId);
////			com.allinfinance.po.TblTermKey obj = tblTermKeyDao.get(pk);
////
////			obj.setPosTmk(tmk);
////			obj.setTmkSt("1");
////			tblTermKeyDao.update(obj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "-1";
//		}
//		return "00";
//	}
	
	public String updateStatus(String mchntNo, String termId) {
		try {
			String sql = "update tbl_term_key set tmk_st='1' where mcht_cd='" + mchntNo + "' and term_id='" + termId + "'";
			CommonFunction.getCommQueryDAO().excute(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		return "00";
	}

	/**
	 * generate command<br>
	 * 0xD181 + Index + 0x0023 + 0x01 + LEN1 + 分散因子1 + LEN2 + 分散因子2<br>
	 * Index：数据库取出key_index做压缩处理<br>
	 * LEN1：分散因子1的长度，1字节，可取0x08/0x09/0x0A/0x0B<br>
	 * 分散因子1：目前可传入8-11字节的密钥所属机构号（这里用8位的银联编号）<br>
	 * LEN2：分散因子2的长度，1字节，按目前分散因子2的数据，填写0x1D<br>
	 * 分散因子2：目前可传入15字节商户号+8字节终端号+6字节终端随机数<br>
	 * 
	 * @param brhId
	 * @param mchntNo
	 * @param termId
	 * @return
	 * 加密机成功返回：0x65 + 0x10 + 16字节终端主密钥密文(被机构的TEK加密)<br>
	 * 加密机失败返回：0x69 + 1字节错误代码, 判断第一个字节内容即可
	 * @throws Exception 
	 */
	// comment by kchen@20131108: old
//	private Object[] genCommand(String cupBrhId,String mchntNo,String termId) throws Exception {
//		
//		byte[] cmd = new byte[1024];		
//		int len = 0;
//		
//		cmd[len++] = (byte)0xD1;//0xD181 高位
//		cmd[len++] = (byte)0x81;//0xD181 低位
//		
//		//read key_index from Sysparam.properties
//		String keyIndex = com.allinfinance.system.util.SysParamUtil.getParam("KEY_INDEX");
//		
//		keyIndex = keyIndex.toUpperCase();//uppercase hex charactor
//		
//		String sql = "select random from tbl_term_zmk_inf where mcht_no='" + mchntNo + "' and term_id='" + termId + "'";
//		List dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
//		
//		if(dataList.size() == 0) {
//			log.info("终端密钥索引表中没有记录。商户编号：" + mchntNo + "，终端编号：" + termId);
//			throw new Exception("终端密钥索引表中没有记录。商户编号：" + mchntNo + "，终端编号：" + termId);
//		}
//		
//		String randomNum = dataList.get(0).toString().trim();
//		
//		byte keyByteHigh;
//		
//		byte keyByteLow;
//		
//		for(int i = 0; i < keyIndex.length(); i++) {
//			
//			keyByteHigh = (byte) (keyIndex.charAt(i) > '9' ? keyIndex.charAt(i) - 'A' + 0x0a : keyIndex.charAt(i) - '0');
//			
//			i++;
//			
//			keyByteLow = (byte) (keyIndex.charAt(i) > '9' ? keyIndex.charAt(i) - 'A' + 0x0a : keyIndex.charAt(i) - '0');
//			
//			cmd[len++] = (byte) ((keyByteHigh << 4 & 0xf0) | (keyByteLow & 0x0f));
//			
//		}
//		
//		cmd[len++] = (byte)0x00;//0x0023高位
//		cmd[len++] = (byte)0x23;//0x0023低位
//		cmd[len++] = (byte)0x01;
//		cmd[len++] = (byte)0x08;//LEN 1
//		
//		System.arraycopy(cupBrhId.getBytes(), 0, cmd, len, cupBrhId.getBytes().length);//分散因子1
//		
//		len += cupBrhId.length();
//		
//		cmd[len++] = (byte)0x1D;//LEN2
//				
//		String tmp = mchntNo + termId + randomNum;
//		
//		System.arraycopy(tmp.getBytes(), 0, cmd, len, tmp.getBytes().length);//分散因子2
//		
//		len += tmp.length();
//		
//		String heigh = "";
//		
//		String low = "";
//		
//		String logInfo = "";
//		for(int i = 0; i < len; i++) {
//			heigh = Integer.toHexString(cmd[i] >> 4 & 0x0f);
//			low = Integer.toHexString(cmd[i] & 0x0f);
//			logInfo += heigh + low;
//		}
//		log.info("COMMAND:" + logInfo);
//		
//		return new Object[]{cmd,len};
//	}
	
	private Object[] genCommand(String mchntNo,String termId) throws Exception {
		byte[] cmd = new byte[1024];		
		int len = 0;
		
		String txnCode = "6111";
		String exFixed = "003F603903F44003F61";
		
		String cmdStr = txnCode + CommonFunction.fillString(mchntNo, 'x', 19, true) + termId + exFixed;
		byte[] cmdStrBytes = cmdStr.getBytes();
		len = cmdStrBytes.length;
		String lenStr = CommonFunction.fillString(String.valueOf(len), '0', 4, false);
		byte[] lenBytes = lenStr.getBytes();
		System.arraycopy(lenBytes, 0, cmd, 0, lenBytes.length);
		System.arraycopy(cmdStrBytes, 0, cmd, lenBytes.length, cmdStrBytes.length);
		
		return new Object[]{cmd, len};
	}
	
	private String parseTmk(byte[] arrayTmk) {
		
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
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
	public String createTmkFile(String checkedTerm){
		try{
			String rep="";	//临时存储
			String ret="";	//文件“终端密钥文件”的内容
			String ret1="";	//文件“商户终端信息”的内容
			String posTmkFile = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_TMK) +"posTmkFile"+ CommonFunction.getCurrentDate() +".txt";
			String posMchtFile = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_TMK) + "posMchtFile"+ CommonFunction.getCurrentDate() +".txt";
			Object[] obj = checkedTerm.split("#");
			for(int i=0;i<obj.length;i++){
				String mchtId = obj[i].toString().split("@")[0];
				String termId = obj[i].toString().split("@")[1];
				
				//获取终端密钥文件的内容
				String sql = "select pos_tmk from tbl_term_key where mcht_cd = '" + mchtId + "' and term_id = '" + termId + "'";
				List dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if(dataList.size()>0){
					String tmk = dataList.get(0).toString();
					String tmkValue = CommonFunction.fillString(tmk.trim(), ' ', 48, true);
					rep = "\"" + mchtId + "\",\""+ termId +"\",\"              \",\"" + tmkValue + "\",\"" + tmkValue.toString() + "\"\r\n";
					if(i<obj.length){
						ret += rep;
					}
				}
				
				//获取商户信息的内容
				String sql1 = "  select mcht_nm from tbl_mcht_base_inf where mcht_no= '" + mchtId + "'";
				List dataList1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sql1);
				if(dataList1.size()>0){
					String mcht_nm = dataList1.get(0).toString();
					String mcht_nmValue = CommonFunction.fillString(mcht_nm.trim(), ' ', 60, true);
					rep = "\"" +mchtId +"\",\""+ termId +"\",\""+ mcht_nmValue + "\"\r\n";
					if(i<obj.length){
						ret1 += rep;
					}
				}
			}
			
			//生成“商户终端信息文件内容"
			FileOutputStream Tmk_fos = null;
			DataOutputStream Tmk_dos = null;
			FileOutputStream Mcht_fos = null;
			DataOutputStream Mcht_dos = null;
			File TmkFile = new File(posTmkFile);
			File MchtFile = new File(posMchtFile);
			try{
//				out = new FileOutputStream(new File(posTmkFile));
//				out.write(ret.getBytes("GBK"));
				//建立文件的读入流
				if (TmkFile.exists()){	TmkFile.delete();	}
				if (MchtFile.exists()){	MchtFile.delete();	}
				Tmk_fos = new FileOutputStream(TmkFile);
				Tmk_dos = new DataOutputStream(Tmk_fos);
				Mcht_fos = new FileOutputStream(MchtFile);
				Mcht_dos = new DataOutputStream(Mcht_fos);
				Tmk_dos.write(ret.getBytes("UTF-8"));
				Mcht_dos.write(ret1.getBytes("UTF-8"));
				//关闭流
				Tmk_dos.close();
				Mcht_dos.close();
				Tmk_fos.close();
				Mcht_fos.close();
				//建立文件的读入流
//				FileWriter fw1=new FileWriter(posTmkFile,false); 
//				BufferedWriter bw1=new BufferedWriter(fw1);   
//				bw1.write(ret.getBytes("GBK"));    
//				bw1.close();          
//				fw1.close();            
			}catch(Exception ex){              
				ex.printStackTrace();
			}
		File[] srcfile = { TmkFile ,MchtFile };
		//将文件打包压缩
		File zipfile=new File(SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_TMK) + "TmkAndMcht.zip");
		TarZipUtils.zipCompress(srcfile , zipfile);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_TMK) + "TmkAndMcht.zip";
	}
	
	
	public static void main(String[] args) {
		String hexString = "3536363131323030323574786e2073756363657373202020";
		System.out.println( "hexString = " + hexString );
		String str = toStringHex(hexString);
		System.out.println( "str = " + str ); 
		System.out.println(str.substring(6,8));
	}

	public TblTermKeyBO getTblTermKeyBO() {
		return tblTermKeyBO;
	}

	public void setTblTermKeyBO(TblTermKeyBO tblTermKeyBO) {
		this.tblTermKeyBO = tblTermKeyBO;
	}
}
