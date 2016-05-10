package com.allinfinance.struts.mchnt.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.allinfinance.bo.mchnt.T20105BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.mchnt.TblMchtCupInf;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T20105Action extends BaseAction{

	T20105BO t20105BO = (T20105BO) ContextUtil.getBean("T20105BO");
	
	private List<File> files;

	public T20105BO getT20105BO() {
		return t20105BO;
	}

	public void setT20105BO(T20105BO t20105bo) {
		t20105BO = t20105bo;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("upload".equals(method)) {
				rspCode = upload();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "直联商户导入" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String upload(){
		List<TblMchtCupInf> addList=new ArrayList<TblMchtCupInf>();
//		List<TblMchtCupInf> updateList=new ArrayList<TblMchtCupInf>();
//		List<TblMchtCupInf> delList=new ArrayList<TblMchtCupInf>();
		try {
			TblMchtCupInf tblMchtCupInf = null;
			for(File file : files) {
				BufferedReader reader = 
					new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				while(reader.ready()){
					String tmp = reader.readLine();
					if (!StringUtil.isNull(tmp)) {
						
						String[] data = tmp.split(",");
						if(data.length!=24){
							return "文件格式不正确！";
						}
						tblMchtCupInf = new TblMchtCupInf();
						
						if(data[1].length()!=15){
							data[1]=CommonFunction.fillString(data[1], ' ', 15, true);
						}
						
//						if("D".equals(data[0])){
//							delList.add(tblMchtCupInf);
//							continue;
//						}
						tblMchtCupInf.setMCHT_NO(data[0]);
						tblMchtCupInf.setACQ_INST_ID(data[1]);
						tblMchtCupInf.setACQ_INST_CD(data[2]);
						tblMchtCupInf.setMCHT_NM(data[3]);
						tblMchtCupInf.setMCHT_SHORT_CN(data[4]);
						tblMchtCupInf.setSTLM_INS_ID(data[5]);
						tblMchtCupInf.setMCHT_TYPE(data[6]);
						tblMchtCupInf.setSTAT(data[7]);
						tblMchtCupInf.setACQ_AREA_CD(data[8]);
						tblMchtCupInf.setMCHNT_TP_GRP(data[9]);
						tblMchtCupInf.setMCC(data[10]);
						tblMchtCupInf.setSETTLE_AREA_CD(data[11]);
						tblMchtCupInf.setLICENCE_NO(data[12]);
						tblMchtCupInf.setCONTACT_ADDR(data[13]);
						tblMchtCupInf.setMCHT_PERSON(data[14]);
						tblMchtCupInf.setCOMM_TEL(data[15]);
						tblMchtCupInf.setMANAGER(data[16]);
						tblMchtCupInf.setIDENTITY_NO(data[17]);
						tblMchtCupInf.setADDR(data[18]);
						tblMchtCupInf.setLICENCE_STAT(data[19]);
						tblMchtCupInf.setMCHT_STLM_ACCT(data[20]);
						tblMchtCupInf.setMCHT_ACCT_NM(data[21]);
						tblMchtCupInf.setSETTLE_BANK_NM(data[22]);
						tblMchtCupInf.setSETTLE_BANK_NO(data[23]);
						tblMchtCupInf.setAPPLY_DATE(data[24]);
						
						
//						tblMchtCupInf.setRESERVED(data[69]);
						
						
							
//						if("I".equals(data[0])){
							addList.add(tblMchtCupInf);
//						}	else if("U".equals(data[0])){
//							updateList.add(tblMchtCupInf);
//						}
//						map.put(data[15] + data[16], tmp);
					}
				}
				reader.close();
			}
			
//			int success = 0;
//			int fail = 0;
			
			for(TblMchtCupInf inf:addList){
				
//				if(t20105BO.get(CommonFunction.fillString(inf.getMCHT_NO(), ' ', 15, true))!=null){
//					fail++;
//					continue;
//				}
//				inf.setCRT_OPR_ID(operator.getOprId());
//				inf.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
				
				t20105BO.add(inf);
//				success++;
			}
			
//			for(TblMchtCupInf inf:updateList){
//				
//				TblMchtCupInf tblMchtCupInfs=t20105BO.get(CommonFunction.fillString(inf.getMCHT_NO(), ' ', 15, true));
//				if(tblMchtCupInfs==null){
//					fail++;
//					continue;
//				}
//				inf.setCRT_OPR_ID(tblMchtCupInfs.getCRT_OPR_ID());
//				inf.setREC_CRT_TS(tblMchtCupInfs.getREC_CRT_TS());
//				inf.setUPD_OPR_ID(operator.getOprId());
//				inf.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
//				t20105BO.update(inf);
//				success++;
//			}
//			
//			for(TblMchtCupInf inf:delList){
//				
//				if(t20105BO.get(CommonFunction.fillString(inf.getMCHT_NO(), ' ', 15, true))==null){
//					fail++;
//					continue;
//				}
//				t20105BO.delete(CommonFunction.fillString(inf.getMCHT_NO(), ' ', 15, true));
//				success++;
//			}
			
			return Constants.SUCCESS_CODE;
//			return Constants.SUCCESS_CODE_CUSTOMIZE + 
//				"成功录入条目：" + String.valueOf(success) + ",失败的条目：" + String.valueOf(fail);
		} catch (Exception e) {
			e.printStackTrace();
			return "解析文件失败";
		}
		
	}
}
