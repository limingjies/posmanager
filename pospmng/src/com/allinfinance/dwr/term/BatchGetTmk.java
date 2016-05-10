package com.allinfinance.dwr.term;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.allinfinance.po.TblTermKey;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.TCPClient;

/**
 * Title:批量生成密钥
 * 
 * Author: 徐鹏飞
 * 
 * Copyright: Copyright (c) 2015-2-5
 * 
 * Company: Shanghai Tonglian Software Systems.
 * 
 * @version 1.0
 */
public class BatchGetTmk {
	
	private TCPClient client = null;
	private String ip = com.allinfinance.system.util.SysParamUtil.getParam("REMOTE_IP");
	private int port = Integer.parseInt(com.allinfinance.system.util.SysParamUtil.getParam("REMOTE_PORT"));
	
/*	private static Map<String, String> specialBrhMap = new HashMap<String, String>();
	private static Map<String, List<String>> specialBrhBelowMap = new HashMap<String, List<String>>();
	@SuppressWarnings("unchecked")
	public BatchGetTmk() {
		// specialBrhMap 中按情况添加键值对，key = "0"除外
		specialBrhMap.put("1", "'00002'");      // 测试使用
//		specialBrhMap.put("1", "'00057'");
		specialBrhMap.put("2", "'00005','00006','00015'");
		
		for(Iterator<Entry<String, String>> iter = specialBrhMap.entrySet().iterator();iter.hasNext();){
			@SuppressWarnings("rawtypes")
			Map.Entry element = (Map.Entry)iter.next();
			specialBrhBelowMap.put((String) element.getKey(), CommonFunction.getCommQueryDAO().findBySQLQuery(getSpecialBrh((String) element.getValue())));
		}
	}*/

//	private String specialBrhNo_1 = "'00002'";      // 测试使用
	
	private String specialBrhNo_1 = "'00057'";
	private String specialBrhNo_2 = "'00005','00006','00015'";
	@SuppressWarnings("unchecked")
	private List<String> specialBrhList_1 = CommonFunction.getCommQueryDAO().findBySQLQuery(getSpecialBrh(specialBrhNo_1));
	@SuppressWarnings("unchecked")
	private List<String> specialBrhList_2 = CommonFunction.getCommQueryDAO().findBySQLQuery(getSpecialBrh(specialBrhNo_2));

	/*
	 * 根据上级机构获取特殊机构
	 */
	public String getSpecialBrh(String specialBrhNo){
		String sql = "SELECT brh_id FROM TBL_BRH_INFO  start with brh_id in (" + specialBrhNo + ") connect by prior  BRH_ID = UP_BRH_ID";
		return sql;
	}

	/*
	 * 判断此终端所属商户所在机构是否属于特殊机构
	 * @param brhId
	 * @return [0：不属于*：其他]
	 */
	public String checkSpecial(String brhId){
		String flag = "0";
		if(specialBrhList_1.contains(brhId))
			flag = "1";
		if(specialBrhList_2.contains(brhId))
			flag = "2";
		return flag;
	}
	
	/*
	 * 特殊机构商户终端TMK特殊处理
	 */
	public TblTermKey getTermKeyUpd(TblTermKey tblTermKeyOld, String flag){
		if("1".equals(flag)){
			updTermKey_1(tblTermKeyOld);
		}
		if("2".equals(flag)){
			updTermKey_2(tblTermKeyOld);
		}
		return tblTermKeyOld;
	}
	
	/*
	 * flag == "1"
	 * TblTermKey特殊处理_1
	 */
	public void updTermKey_1(TblTermKey tblTermKeyOld){
		tblTermKeyOld.setKeyIndex("0023");
		tblTermKeyOld.setMacKeyLen("08");
		tblTermKeyOld.setMacKey("76EE9987FDC3F55E");
		tblTermKeyOld.setMacKeyChk("208AAD33E7BA3813");
		tblTermKeyOld.setPinKeyLen("16");
		tblTermKeyOld.setPinKey("F4528FCC2FDC8DDFAC53F25665ED45FB");
		tblTermKeyOld.setPinKeyChk("99742F8F1F8AE05D");
		tblTermKeyOld.setTrkKeyLen("16");
		tblTermKeyOld.setTrkKey("4296068701B48511D50F29A7D46982EB");
		tblTermKeyOld.setTrkKeyChk("1322B743B47A011A");
		tblTermKeyOld.setPosBmk("E4878DE075E7594A66C062646D5FDB7F");
		tblTermKeyOld.setPosTmk("70F24A531D9FCCFBB37C47E6A51365A6");
		tblTermKeyOld.setTmkSt("1");
		tblTermKeyOld.setRecOprId("U");
		tblTermKeyOld.setRecUpdOpr("-");
		tblTermKeyOld.setRecCrtTs("-");
		tblTermKeyOld.setRecUpdTs("-");
	}
	
	/*
	 * flag == "2"
	 * TblTermKey特殊处理_2
	 */
	public void updTermKey_2(TblTermKey tblTermKeyOld){
		tblTermKeyOld.setMacKeyLen("08");
		tblTermKeyOld.setMacKey("3C29ACCF176D0A8A");
		tblTermKeyOld.setMacKeyChk("56A8749898923FE6");
		tblTermKeyOld.setPinKeyLen("16");
		tblTermKeyOld.setPinKey("13045ABE18C56398FE82A997709EE952");
		tblTermKeyOld.setPinKeyChk("D2A97C01C18C29DB");
		tblTermKeyOld.setTrkKeyLen("16");
		tblTermKeyOld.setTrkKey("A673819C0371257161380012DF194839");
		tblTermKeyOld.setTrkKeyChk("832099BC44F89E80");
		tblTermKeyOld.setPosBmk("6A4140FA94DE39F85B83EDD69B484BF3");
		tblTermKeyOld.setPosTmk("A0E4AD1F4C3C3C628A166940E863F029");
		tblTermKeyOld.setTmkSt("1");
	}
	
	/*
	 * 批量生成终端密钥_by socket
	 */
	public void crtTmkBySocket(List<TblTermKey> tblTermKeyOkList){

		String mchtId = null;
		String termId = null;
		String result = null;
		
		Iterator<TblTermKey> iter = tblTermKeyOkList.iterator();
        while(iter.hasNext()){
        	TblTermKey tblTermKey = iter.next();
        	
        	mchtId = tblTermKey.getId().getMchtCd();
			termId = tblTermKey.getId().getTermId();
			result = sendRecMsg(mchtId, termId);
			if(result.length() > 2) {
				if(!"00".equals(result.substring(8,10))) {//应答码00为成功
	                iter.remove();
				}
			} else {
                iter.remove();
			}
        }
	}
	
	/*
	 * 收发报文
	 */
	
	private String sendRecMsg(String mchtId,String termId) {
		try {
			Object[] command = genCommand(mchtId, termId);
			byte[] cmd = (byte[]) command[0];
			int len = Integer.parseInt(command[1].toString());
			
			client = new TCPClient(ip, port);
			//发送指令
			client.sendMessage(cmd,len);
			//接收TMK
			byte[] rspMessage = client.receiveMessage();
			//返回经解析的TMK
			return parseTmk(rspMessage);
			
		} catch (IOException e) {
			e.printStackTrace();
			return "-1";
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		} finally {
			if(client != null) {
				client.destroy();
			}
		}
	}

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
	
}