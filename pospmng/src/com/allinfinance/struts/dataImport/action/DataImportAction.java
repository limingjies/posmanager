package com.allinfinance.struts.dataImport.action;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSONArray;
import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.base.T10105BO;
import com.allinfinance.bo.dataImport.DataImportBO;
import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblOprInfoConstants;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;
import com.allinfinance.po.TblAgentFeeCfg;
import com.allinfinance.po.TblAgentRateInfo;
import com.allinfinance.po.TblAgentRateInfoId;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblInfDiscCd;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.Encryption;
import com.allinfinance.system.util.HibernateConfigurationUtil;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TMSService;
import com.allinfinance.system.util.TarZipUtils;
import com.csvreader.CsvReader;

@SuppressWarnings("serial")
public class DataImportAction extends BaseAction {
	private DataImportBO dataImportBO=(DataImportBO) ContextUtil.getBean("DataImportBO");
	private T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
	private T10105BO t10105BO = (T10105BO) ContextUtil.getBean("T10105BO");
	private T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
	// 文件集合
	private List<File> files;
	// 文件名称集合
	private List<String> filesFileName;
	// 图片路径
	private String path;
	
	// 本次导入总记录数
	private double cntTotal = 0.00;
	// 本次导入成功记录数
	private int cntSuccess = 0;
	// 本次导入失败记录数
	private int cntFail = 0;
	
	private String newLine = System.getProperty("line.separator");
	
	private String csvHeader;
	// 导入失败的CSV记录
	private List<String[]> failRecList = new ArrayList<String[]>();
	// 导入成功的CSV记录
	private List<String[]> succRecList = new ArrayList<String[]>();
	// 导入失败的错误原因记录
	private List<Map<String, String>> errInfoList = new ArrayList<Map<String, String>>();
	private HttpSession session;
	private String importTime;
	private String dataType;
	// 结果文件写入目录
	public String resultDir = SysParamUtil.getParam(
			SysParamConstants.TEMP_FILE_DISK).replace("\\", "/");
	@Override
	protected String subExecute() throws Exception {
		
		// 文件路径
		String csvFilePath = null;
		CsvReader reader = null;
		try {

			session = ServletActionContext.getRequest().getSession();
			
			if("3".equals(method)){
				// 图片导入
				picImport(path);
			} else if("SynData".equals(method)){
				// 数据同步
				SynData();
			} else {
				// 数据导入
				if(files == null || files.size() == 0){
					return null;
				}
				csvFilePath = files.get(0).getPath();  
		
				reader = new CsvReader(csvFilePath,',',Charset.forName("UTF-8"));
				// 读取头部
				reader.readHeaders();
				csvHeader = reader.getRawRecord();
				while (reader.readRecord()) {
					// 总记录数+1
					cntTotal++;
				}
				reader.close();
	
				session = ServletActionContext.getRequest().getSession();
				session.setAttribute("progressNumber", 0/cntTotal*100.0);
				reader = new CsvReader(csvFilePath,',',Charset.forName("UTF-8"));
				if("0".equals(method)){
					brhInfoImport(reader);
				}else if("1".equals(method)){
					mchtImport(reader);
				}else if("2".equals(method)){
					termInfoImport(reader);
				}
			}

		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg(e.getMessage());
		} finally{
			if(reader!=null){
				reader.close();
			}
			String resultFile = makeResultFile();
			if (errInfoList.size() > 100){
				errInfoList = errInfoList.subList(0, 99);
			}
			ImportResult result = new ImportResult((int)cntTotal, cntSuccess, cntFail, errInfoList, resultFile);
			writeSuccessMsg(result.toJSONString());
		}  
		return null;
	}
	/**
	 * 数据同步
	 * @return
	 */
	public String SynData() {
		if("1".equals(dataType)){//商户
			importData(TblMchtBaseInf.class);
			importData(TblMchtSettleInf.class);
			importData(TblInfDiscCd.class);
			importData(TblHisDiscAlgo.class);
		}else if("2".equals(dataType)){//终端
			importData(TblTermInf.class);
			// 数据推送的脚本会做这件事情，所以同步不再做
//			importData(TblTermKey.class);
		}
		return null;
	}
	
	public String importData(Class clazz){
		List<Object> list = null;; 
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.eq("RecCrtTs", importTime));
		int totalNum = CommonFunction.getCommQueryDAO().getTotalNum(dc);
		int pageSize = 100;
		int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		int curRecNo = 0;
		cntTotal += totalNum;
		
		if (0 == totalNum) {
			makeErrInfo("0", "没有对应日期的数据可以同步，请确认。");
			return null;
		}
		Connection misConn = null;
		Connection pospConn = null;
		Connection gtwyConn = null;
		try {
		misConn = CommonFunction.getCommMisQueryDAO().getConnection();
		pospConn = CommonFunction.getcommPOSPQueryDAO().getConnection();
		gtwyConn = CommonFunction.getcommGWQueryDAO().getConnection();
		
		for (int i = 0; i < totalPage; i++) {
			list = CommonFunction.getCommQueryDAO().getListByPage(i , dc);
			if(list!=null&&list.size()>0){
				for (Object object : list) {
					int perSucc = 0;
					int perFail = 0;
					// 更新进度
					curRecNo++;
					session.setAttribute("progressNumber", curRecNo/cntTotal*100.0);
					//数据推送
					if(!(object instanceof TblTermKey)){
						//数据推送到MISADM
						try {
							if (0 == HibernateConfigurationUtil.save(misConn, object)){
								throw (new Exception("数据插入失败"));
							}
							perSucc++;
						} catch (Exception e) {
							e.printStackTrace();
							makeErrInfo(curRecNo+"", "数据推送到MISADM失败。" + getErrorInfo(object) + e.getMessage());
							perFail++;
						}
					}
					if(object instanceof TblMchtBaseInf || object instanceof TblMchtSettleInf || object instanceof TblTermInf || object instanceof TblTermKey){
						// 将数据推送到POSP
						try {
							if (0 == HibernateConfigurationUtil.save(pospConn, object)){
								throw (new Exception("数据插入失败"));
							}
							perSucc++;
						} catch (Exception e) {
							e.printStackTrace();
							makeErrInfo(curRecNo+"", "数据推送到POSP失败。" + getErrorInfo(object) + e.getMessage());
							perFail++;
						}
						if(object instanceof TblMchtBaseInf || object instanceof TblMchtSettleInf){
							// 将数据推送到GATEWAY
							try {
								if (0 == HibernateConfigurationUtil.save(gtwyConn, object)){
									throw (new Exception("数据插入失败"));
								}
								perSucc++;
							} catch (Exception e) {
								e.printStackTrace();
								makeErrInfo(curRecNo+"", "数据推送到POSP失败。" + getErrorInfo(object) + e.getMessage());
								perFail++;
							}
						}
							
					}
					if (perFail == 0 && perSucc != 0) {
						cntSuccess++;
					} else {
						cntFail++;
					}
				}
			}else {//没查出来
				makeErrInfo("0", "没有对应日期的数据可以同步，请确认。");
			}
		}
		}catch(Exception e){
			
		}finally{
			try{
				if (null != misConn){
					misConn.close();
				}
				if (null != pospConn){
					pospConn.close();
				}
				if (null != pospConn){
					gtwyConn.close();
				}
			}catch(Exception e){
			}
		}
		return null;
	}

	String getErrorInfo(Object object){
		String retStr = "";
		if (object instanceof TblMchtBaseInf) {
			TblMchtBaseInf t = (TblMchtBaseInf)object;
			return "商户基本信息同步失败，商户号：" + t.getMchtNo();
		}
		if (object instanceof TblMchtSettleInf) {
			TblMchtSettleInf t = (TblMchtSettleInf)object;
			return "商户清算信息同步失败，商户号：" + t.getMchtNo();
		}
		if (object instanceof TblTermInf) {
			TblTermInf t = (TblTermInf)object;
			return "终端信息同步失败,终端号：" + t.getId();
		}
		if (object instanceof TblTermKey) {
			TblTermKey t = (TblTermKey)object;
			return "终端秘钥信息同步失败，商户号/终端号：" + t.getId().getMchtCd() + "/" + t.getId().getTermId();
		}
		if (object instanceof TblInfDiscCd) {
			TblInfDiscCd t = (TblInfDiscCd)object;
			return "商户手续费类型定义表同步失败，手续费类型ID：" + t.getDiscCd();
		}
		if (object instanceof TblHisDiscAlgo) {
			TblHisDiscAlgo t = (TblHisDiscAlgo)object;
			return "商户手续费控制表同步失败，算法ID：" + t.getId();
		}
		return retStr;
	}
	
	public String makeResultFile(){
		List<File> resfileList = new ArrayList<File>();
		// 将文件打包压缩
		File zipfile = null;
		String fileNameTimePart = CommonFunction.getCurrentDateTime();
		String resultFileName = fileNameTimePart + "result.txt";
		String failFileName = fileNameTimePart + "failList.csv";
		String successFileName = fileNameTimePart + "successList.txt";
		try{
			// 处理结果
			if (errInfoList.size() > 0) {
				File resFile = new File(resultDir + "/" + resultFileName);
				FileOutputStream out=new FileOutputStream(resFile);
				OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
				for(Map<String, String> map:errInfoList){
					String str = map.get("index") + ":\t" + map.get("reason") + newLine;
					writer.append(str);
				}
				writer.flush();
				writer.close();
				out.close();
				resfileList.add(resFile);
			}
			// 处理结果
			if (succRecList.size() > 0) {
				File resFile = new File(resultDir + "/" + successFileName);
				FileOutputStream out=new FileOutputStream(resFile);
				OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
				if("1".equals(method)){
					String str = "商户编号（旧）\t新商户编号";
					writer.append(str);
				}else if("2".equals(method)){
					// 成功的终端导入记录：旧商户号，旧终端号，新商户号，新终端号
					String str = "旧商户编号\t旧终端号\t新商户号\t新终端号";
					writer.append(str);
				}else if("3".equals(method)){
					// // 成功的图片导入记录：申请单号，新商户号
					String str = "申请单号\t新商户号";
					writer.append(str);
				}
				writer.append(newLine);
				for(String[] strList:succRecList){
					for(String str:strList){
						str += "\t";
						writer.append(str);
					}
					writer.append(newLine);
				}
				writer.flush();
				writer.close();
				out.close();
				resfileList.add(resFile);
			}
			// 处理结果
			if (failRecList.size() > 0) {
				File resFile = new File(resultDir + "/" + failFileName);
				FileOutputStream out=new FileOutputStream(resFile);
				OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
				writer.append((csvHeader + newLine));
				for(String[] strList:failRecList){
					String line = "";
					for(String str:strList){
						if (StringUtil.isEmpty(line)) {
							line += str;
						} else {
							line += "," + str;
						}
					}
					line += newLine;
					writer.append(line);
				}
				writer.flush();
				writer.close();
				out.close();
				resfileList.add(resFile);
			}
			if (resfileList.size() > 0) {
				File[] files = new File[resfileList.size()];
				int i = 0;
				for(File f:resfileList){
					files[i++] = f;
				}
				zipfile = new File(resultDir + "/" + "/"+ "DataImportResult_" + fileNameTimePart+".zip");
				TarZipUtils.zipCompress(files , zipfile);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		if (null != zipfile) {
			return zipfile.getPath();
		}
		return null;
	}
	
	protected void writeUsefulMsg(String msg) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		PrintWriter printWriter = ServletActionContext.getResponse()
				.getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	public String termInfoImport(CsvReader reader){
		String retStr = null;
		String[] record = null;
		int curRecNo = 0;
		try {
			// 读取头部
			reader.readHeaders(); 
			while (reader.readRecord()) {
				String newMchtId = null;
				String termType = null;
				String orgTermId = null;
				String newTermId = null;
				String sn = null;
				// 读取一行数据
				record = reader.getValues();
				curRecNo++;
				session.setAttribute("progressNumber", curRecNo/cntTotal*100.0);
				// 检查数据
				retStr = termDataCheck(record);
				if (!Constants.SUCCESS_CODE.equals(retStr)) {
					failRecList.add(record);
					makeErrInfo(curRecNo+"", "商户号/终端号：" + record[0] + "/" + record[1] + ":" + retStr);
					cntFail++;
					continue;
				}
				// 根据旧商户号获取新商户号
				newMchtId = dataImportBO.getNewId(record[0], "tbl_import_mcht");
				if (null == newMchtId) {
					failRecList.add(record);
					makeErrInfo(curRecNo+"", "找不到相关商户信息,商户号/终端号：" + record[0] + "/" + record[1]);
					cntFail++;
					continue;
				}

				// 旧终端号
				orgTermId = record[1];
				// 终端类型
				termType = getTermType(record[6]);
				
				TblMchtBaseInfTmpTmp tblMchtBaseInf = t20901BO.getBaseInfTmp(newMchtId);
				TblTermInfTmp tblTermInfTmp =  new TblTermInfTmp();
				TblTermInfTmpPK pk = new TblTermInfTmpPK();
				// 根据地区吗和终端类型生成新终端号
				newTermId = dataImportBO.getTermId(tblMchtBaseInf.getAreaNo(), termType);
				
				// 商户编号
				tblTermInfTmp.setMchtCd(newMchtId);
				pk.setTermId(newTermId);
				pk.setRecCrtTs(importTime);
				tblTermInfTmp.setId(pk);
				// 终端状态
				tblTermInfTmp.setTermSta(getTermStatus(record[2]));
				// 联系电话
				tblTermInfTmp.setContTel(record[4]);
				// 产权属性
				tblTermInfTmp.setPropTp(getRightsProp(record[5]));
				// 终端类型
				tblTermInfTmp.setTermTp(termType);
				// 终端安装地址
				tblTermInfTmp.setTermAddr(record[7]);
				// 终端SN号
				sn = record[10];
				// 终端所属机构
				tblTermInfTmp.setTermBranch(tblMchtBaseInf.getBankNo());
				// 设置交易信息
				tblTermInfTmp.setTermPara1(getTradType(record));
				tblTermInfTmp.setTermSignSta(TblTermInfConstants.TERM_SIGN_DEFAULT);
				tblTermInfTmp.setKeyDownSign(TblTermInfConstants.CHECKED);
				tblTermInfTmp.setParamDownSign(TblTermInfConstants.CHECKED);
				tblTermInfTmp.setSupportIc(TblTermInfConstants.CHECKED);
				tblTermInfTmp.setIcDownSign(TblTermInfConstants.CHECKED);
				tblTermInfTmp.setOprNm(null);
				tblTermInfTmp.setEquipInvId("NN");// 机具及密码键盘绑定标识，第一位机具，第二位密码键盘，Y表示绑定，N表示未绑定
				tblTermInfTmp.setEquipInvNm(null);
				tblTermInfTmp.setTermBatchNm("000001");
				tblTermInfTmp.setTermMcc("AAA");
				tblTermInfTmp.setRecUpdOpr(operator.getOprId());
				tblTermInfTmp.setRecCrtOpr(operator.getOprId());
				tblTermInfTmp.setTermStlmDt(CommonFunction.getCurrentDate());
				tblTermInfTmp.setMisc1("1"); //签购单模板-1 - 银联标准签购单
				tblTermInfTmp.setMisc3("12");//是否强制下载主密钥（1-未下载主密钥，需要下载主密钥），是否强制联机报道（2-必须更新）
				tblTermInfTmp.setRecCrtTs(importTime);
				tblTermInfTmp.setRecUpdTs(importTime);
				
				try {
					retStr = dataImportBO.importTermInfo(orgTermId, tblTermInfTmp);
					if (!StringUtil.isEmpty(sn)) {
						TMSService tms = new TMSService();
						retStr = tms.BMSImport(tblTermInfTmp.getMchtCd(), tblTermInfTmp.getId().getTermId(), sn);
						
						if (!Constants.SUCCESS_CODE.equals(retStr)){
							makeErrInfo(curRecNo+"", "(新)商户号/终端号：" + newMchtId + "/" + newTermId + ",TMSService" + retStr + "SN:" + sn + "绑定失败，请手动处理。");
						} else {
							if (Constants.SUCCESS_CODE != dataImportBO.updateSN(newTermId, importTime, sn)){
								makeErrInfo(curRecNo+"", "(新)商户号/终端号：" + newMchtId + "/" + newTermId + ",TMSService绑定成功，本地终端表更新失败，请手动处理。");
							}
						}
					}
					
				} catch (Exception e) {
					failRecList.add(record);
					makeErrInfo(curRecNo+"", e.getMessage() + ",商户号/终端号：" + record[0] + "/" + record[1]);
					cntFail++;
					continue;
				}
				cntSuccess++;
				// 成功的终端导入记录：旧商户号，旧终端号，新商户号，新终端号
				succRecList.add(new String[]{record[0], record[1], newMchtId, newTermId});
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (null != record) {
				failRecList.add(record);
				makeErrInfo(curRecNo+"", e.getMessage() + ",商户号/终端号：" + record[0] + "/" + record[1]);
			} else {
				makeErrInfo(curRecNo+"", "发生了异常，未能正确导入数据：" + e.getMessage());
			}
		} finally{
			session.removeAttribute("progressNumber");
		}  
		return null;
	}
	
	/**
	 * 商户信息导入
	 * @return
	 * @throws IOException 
	 */
	public String mchtImport(CsvReader reader) throws IOException{
		int count = 0;
		try {
			reader.readHeaders(); 
			while (reader.readRecord()) {
				count++;
				session.setAttribute("progressNumber", count/cntTotal*100.0);
				String[] record = reader.getValues();
				try {
					
					String result = "";
					List<Object> mchtImportDataList = dataImportBO.mchtImportData(record,operator,importTime);
					if(mchtImportDataList!=null&&mchtImportDataList.size()==1){
						result = (String) mchtImportDataList.get(0);
						failRecList.add(record);
						log("商户数据导入出错：第"+count+"条，"+result);
						cntFail++;
						makeErrInfo(count+"", result+"商户号:"+record[0]);
						continue;
					}else if(mchtImportDataList.size()>1){
						String sendMessage = dataImportBO.sendMessage((String)mchtImportDataList.get(0), (TblMchtBaseInfTmp)mchtImportDataList.get(1), 
								(TblMchtBaseInf)mchtImportDataList.get(2), (TblMchtSettleInfTmp)mchtImportDataList.get(3), 
								(TblMchtSettleInf)mchtImportDataList.get(4), (String)mchtImportDataList.get(5));
						if (!"00".equals(sendMessage)) {
							failRecList.add(record);
							log("商户数据导入出错：第"+count+"条，"+sendMessage);
							cntFail++;
							makeErrInfo(count+"", sendMessage+"商户号:"+record[0]);
							continue;
						}else {
							try {
								String updateDiscMirror="INSERT INTO TBL_INF_DISC_CD_MIRROR A SELECT * FROM TBL_INF_DISC_CD B WHERE B.DISC_CD='"+mchtImportDataList.get(6).toString().trim()+"'";
								commQueryDAO.excute(updateDiscMirror);
								String updateHisDiscMirror="INSERT INTO TBL_HIS_DISC_ALGO_MIRROR A SELECT * FROM TBL_HIS_DISC_ALGO B WHERE B.DISC_ID='"+mchtImportDataList.get(6).toString().trim()+"'";
								commQueryDAO.excute(updateHisDiscMirror);
								cntSuccess++;
								// 成功的终端导入记录：旧商户号	新商户号
								result = (String) mchtImportDataList.get(0);
								succRecList.add(new String[]{record[0], result});
								continue;
							} catch (Exception e) {
								failRecList.add(record);
								e.printStackTrace();
								log("商户数据导入出错：第"+count+"条，mirror表插入数据失败，"+e.getMessage());
								cntFail++;
								makeErrInfo(count+"", e.getMessage()+"商户号:"+record[0]);
								continue;
							}
						
						}
					}
				} catch (Exception e) {
					failRecList.add(record);
					e.printStackTrace();
					log("商户数据导入出错：第"+count+"条，"+e.getMessage());
					cntFail++;
					makeErrInfo(count+"", e.getMessage()+"商户号:"+record[0]);
					continue;
				}
			}
//			String json=JSONArray.toJSON(errInfoList).toString();
//			writeSuccessMsg(json);
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			
		} finally{
			if(reader!=null){
				reader.close();
			}
			session.removeAttribute("progressNumber");
		}  

		return null;
	}
	
	public String picImport(String path) {
		File file = new File(path);
		int count = 0;
		if (!file.exists()) {
			makeErrInfo("0", "指定路径不存在：" + path);
			return null;
		}
		File[] files = file.listFiles();
		if (files.length == 0){
			makeErrInfo("0", "指定路径下没有可以处理的文件：" + path);
			return null;
		}
		cntTotal = files.length;
		session.setAttribute("progressNumber", 0/cntTotal);
		for (File order : files) {
			count++;
			session.setAttribute("progressNumber", count/cntTotal*100.0);
			if (order.isDirectory()) {
				String mchntCd = dataImportBO.getMchntCd(order.getName());
				if (mchntCd == null) {
					// 没找到相应的商户编号
					makeErrInfo(order.getName(), "找不到该申请单号对应的商户号。");
					cntFail++;
					continue;
				} else if (StringUtil.isEmpty(mchntCd)) {
					// 这个商户的图片数据已经导入过了
					makeErrInfo(order.getName(), "此商户号的图片数据已经被导入过了。" + mchntCd);
					cntFail++;
					continue;
				}
				// 导入此商户的图片数据
				String res = make1MchPics(order, mchntCd);
				if (Constants.SUCCESS_CODE.equals(res)) {
					// 将当前商户图片导入成功的标志记入数据库
					dataImportBO.updatePicFlag(mchntCd);
					cntSuccess++;
					// 成功的图片导入记录：申请单号，新商户号
					succRecList.add(new String[]{order.getName(), mchntCd});
				} else {
					// 将失败商户记录
					cntFail++;
					makeErrInfo(order.getName(), "商户图片导入失败：" + res + "商户号：" + mchntCd);
				}
			}			
		}
		session.removeAttribute("progressNumber");
		return null;
	}	
	
	private void makeErrInfo(String index, String error) {
		Map<String, String> record = new HashMap<String, String>();
		record.put("index", index);
		record.put("reason", error);
		errInfoList.add(record);
	}
	
	private String getTradType(String[] record) {
		String strTmp = "";
		// 获取交易类型
		String tradType = "";
		// 获取校验仪类型（卡类型）,默认开通借记卡
		String cartType = "1";
		// 是否开通贷记卡
		cartType += formatTermPara(record[20]);
		// 补两位0：原系统没有‘准贷记卡’‘预付费卡’
		cartType += "00";
		// 前10个字节（下标0~9）表示卡类型，从11字节开始表示交易信息
		tradType = CommonFunction.fillString(cartType, ' ', 10, true);
		// 11(下标10）位：是否开通查询余额
		tradType += formatTermPara(record[14]);
		// 12(下标11）位：是否开通消费
		strTmp = formatTermPara(record[11]);
		tradType += strTmp;
		// 13(下标12）位：消费类
		tradType += strTmp;
		// 14(下标13）位：是否开通预授权
		strTmp = formatTermPara(record[15]);
		tradType += strTmp;
		// 15(下标14）位：预授权类保留
		tradType += strTmp;
		// 16(下标15）位：预授权类保留
		tradType += strTmp;
		// 17(下标16）位：预授权类保留
		tradType += strTmp;
		// 18(下标17）位：是否开通退货
		strTmp = formatTermPara(record[13]);
		tradType += strTmp;
		// 19(下标18）位：退货类保留
		tradType += strTmp;
		
		return tradType;
	}
	
	private String formatTermPara(String content){
		if(StringUtil.isNotEmpty(content) && "是".equals(content)){
			return "1";
		}else{
			return "0";
		}
	}

	private String getTermStatus(String content){
		if("开通".equals(content)) {
			return "1";
		} else if("停用".equals(content)) {
			return "4";
		} else if("注销".equals(content)) {
			return "7";
		}
		return null;
	}
	
	private String getTermType(String content){
		if("移动".equals(content)) {
			return "5";
		} else if("固定".equals(content)) {
			return "0";
		} else if("MPOS".equals(content)) {
			return "7";
		}
//		} else if("固定（Modem+GPRS）".equals(content)) {
//			return "3";
//		} else if("固定分体（Modem+网络）".equals(content)) {
//			return "4";
//		} else if("移动（GPRS）".equals(content)) {
//			return "5";
//		} else if("手机POS".equals(content)) {
//			return "6";
//		} else if("固定（Modem）".equals(content)) {
//			return "7";
//		}
		return null;
	}
	
	private String make1MchPics(File path, String mchntCd){

		// 营业执照:01 <-> upload1
		File[] files01 = path.listFiles(new MyFileFilter("01_"));
		// 税务登记证:02 <-> upload2
		File[] files02 = path.listFiles(new MyFileFilter("02_"));
		// 法人身份证:03 <-> upload3
		File[] files03 = path.listFiles(new MyFileFilter("03_"));
		// 钱宝特约商户登记审核表:04 <-> upload5
		File[] files04 = path.listFiles(new MyFileFilter("04_"));
		// 照片（门脸门牌地址、内部所售商品照）:05 <-> upload4
		File[] files05 = path.listFiles(new MyFileFilter("05_"));
		// 组织机构代码证（企业须有，个体户可无）:06 <-> upload6
		File[] files06 = path.listFiles(new MyFileFilter("06_"));
		// 房屋租赁合同（无执照必须）:07 <-> upload8
		File[] files07 = path.listFiles(new MyFileFilter("07_"));
		// 银行卡、开户许可证: 08 <-> upload10
		File[] files08 = path.listFiles(new MyFileFilter("08_"));
		// 非法人授权书:09 <-> upload7
		File[] files09 = path.listFiles(new MyFileFilter("09_"));
		// 钱宝签约协议:11 <-> upload5
		File[] files11 = path.listFiles(new MyFileFilter("11_"));
		// 其他（各类情况说明等）:14 <-> upload
		File[] files14 = path.listFiles(new MyFileFilter("14_"));
		
		//
		// 创建商户目录
		String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
		String mchntPath = basePath + "/" + mchntCd;
		File mchntDir = new File(mchntPath);
		if (mchntDir != null && mchntDir.exists()){
			delPath(mchntDir);
		}
		mchntDir.mkdir();
		
		try {
			copyPictures(files01, mchntCd, mchntPath, "upload1", "");
			copyPictures(files02, mchntCd, mchntPath, "upload2", "");
			copyPictures(files03, mchntCd, mchntPath, "upload3", "");
			copyPictures(files04, mchntCd, mchntPath, "upload5", "djshb");
			copyPictures(files05, mchntCd, mchntPath, "upload4", "");
			copyPictures(files06, mchntCd, mchntPath, "upload6", "");
			copyPictures(files07, mchntCd, mchntPath, "upload8", "");
			copyPictures(files08, mchntCd, mchntPath, "upload10", "");
			copyPictures(files09, mchntCd, mchntPath, "upload7", "");
			copyPictures(files11, mchntCd, mchntPath, "upload5", "qyxy");
			copyPictures(files14, mchntCd, mchntPath, "upload", "");
		} catch (Exception e) {
			e.printStackTrace();
			delPath(mchntDir);
			return e.getMessage();
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	private void delPath(File path){
		if (!path.exists()) {
			return;
		}
		File[] files = path.listFiles();
		if (files.length == 0){
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				delPath(file);
			} else {
				file.delete();
			}
		}
		path.delete();
	}
	
	private String copyPictures(File[] pics, String mchntCd, String mchntPath, String upload, String spcialName) throws Exception{
		// 目标文件名特殊规则
		String spcialStr = "";
		// 创建upload目录
		File uploadDir = new File(mchntPath + "/" + upload);
		if (uploadDir != null && uploadDir.exists()){
			// 如果目录以及存在，则删除此目录
			//delPath(uploadDir);
		}
		uploadDir.mkdir();
		
		if (StringUtil.isNotEmpty(spcialName)){
			spcialStr += "_" + spcialName + "_";
		}
		
		for (File pic : pics) {
			if (pic.isDirectory()) continue;
			String fileName = mchntCd + spcialStr + CommonFunction.getRandom(6) + ".jpg";
			String filePath =  uploadDir.getPath() + "/" + fileName;
			fileCopy(pic.getPath(), filePath);
		}
		
		return Constants.SUCCESS_CODE;
	}
	
    public String fileCopy(String srcPath, String dstPath) throws Exception{
        long time=new Date().getTime();
        int length=2097152;
        File srcFile = new File(srcPath);
        File dstFile = new File(dstPath);
        FileInputStream in=new FileInputStream(srcFile);
        FileOutputStream out=new FileOutputStream(dstFile);
        FileChannel inC=in.getChannel();
        FileChannel outC=out.getChannel();
        int i=0;
        while(true){
            if(inC.position()==inC.size()){
                inC.close();
                outC.close();
                return Constants.SUCCESS_CODE;
            }
            if((inC.size()-inC.position())<20971520)
                length=(int)(inC.size()-inC.position());
            else
                length=20971520;
            inC.transferTo(inC.position(),length,outC);
            inC.position(inC.position()+length);
            i++;
        }
    }
    
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public List<String> getFilesFileName() {
		return filesFileName;
	}
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	// 数据一致性，完整性检查
	private String brhDataCheck(String[] records) {
		// TO DO:数据检查
		return Constants.SUCCESS_CODE;
	}
	
	private String compZeroAtF(String oldString,int num)
	{
		int len=oldString.length();
		for(int i=0;i<(num-len);i++)
		{
			oldString="0"+oldString;
		}
		return oldString;
	}
	
	//合作伙伴基本信息
	private TblBrhInfo tblBrhInfo(String[] record){
		String brhId = "";
		String orgNo = "";
		TblBrhInfo tblBrhInfo = new TblBrhInfo();
		String sqlStr = "select max(BRH_ID) from TBL_BRH_INFO";
		List idDataList = commQueryDAO.findBySQLQuery(sqlStr);
		
		if(idDataList!=null && (idDataList.isEmpty()||(idDataList.get(0)==null)))
		{
			brhId = "00001";
		}else{
			String maxNo=idDataList.get(0).toString();
			Integer max=Integer.parseInt(maxNo);
			max=max+1;
			maxNo=max+"";
			maxNo=compZeroAtF(maxNo,5);
			brhId = maxNo;
		}
		while(t10101BO.get(brhId) != null){
			String maxNo=brhId;
			Integer max=Integer.parseInt(maxNo);
			max++;
			maxNo=max+"";
			maxNo=compZeroAtF(maxNo,5);
			brhId = maxNo;
		}
		
		if(record[0]!=null && record[0].length()< 3)
		{
			orgNo = compZeroAtF(record[0],3);
		}
		String tempBrhId=null;
		tempBrhId = record[3] + orgNo + record[5] + record[4];
		String sql = "select max(CREATE_NEW_NO) from TBL_BRH_INFO where CREATE_NEW_NO like '"+tempBrhId+"%'";
		List dataList = commQueryDAO.findBySQLQuery(sql);
		if(dataList!=null && (dataList.isEmpty()||(dataList.get(0)==null)))
		{
			tblBrhInfo.setCreateNewNo(tempBrhId+"001");
		}else{
			String maxNo=dataList.get(0).toString();
			maxNo=maxNo.replace(tempBrhId,"");
			Integer max=Integer.parseInt(maxNo);
			max=max+1;
			maxNo=max+"";
			if(maxNo.length()<3){
				maxNo=compZeroAtF(maxNo,3);
			}
			tblBrhInfo.setCreateNewNo(tempBrhId + maxNo);
		}
//		BeanUtils.copyProperties(tblBrhInfo, this);			
		//状态:启用
		tblBrhInfo.setStatus("1");
		//机构编号
		tblBrhInfo.setBrhId(brhId);
		//银联机构编号
		//tblBrhInfo.setCupBrhId(getCupBrhId());
		//机构级别
//		tblBrhInfo.setBrhLevel(getBrhLvl());
		//机构状态:启用
		tblBrhInfo.setBrhSta("1");
		//机构地区码
//		tblBrhInfo.setResv1(resv1);

		tblBrhInfo.setSettleOrgNo(orgNo);//机构代码
		//上级机构编号
//		tblBrhInfo.setUpBrhId(getUpBrhId());
		
		//机构注册时间
		tblBrhInfo.setRegDt(CommonFunction.getCurrentDate());
		//机构所在地邮政编号
		tblBrhInfo.setPostCd("-");
		//人员属性
		tblBrhInfo.setSettleMemProperty(record[3]);
		//职务类型
		tblBrhInfo.setSettleJobType(record[4]);
		//机构所在地址
		tblBrhInfo.setBrhAddr(record[6]);
		//机构名称
		tblBrhInfo.setBrhName(record[1]);
		//机构联系电话
		tblBrhInfo.setBrhTelNo(record[7]);
		//机构联系人
		tblBrhInfo.setBrhContName(record[8]);
		//结算信息(T+1)
		tblBrhInfo.setResv4("0 001");
		//最后更新操作员
		tblBrhInfo.setLastUpdOprId(operator.getOprId());
		//最后更新时间
		tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
		//最后更新交易码
		tblBrhInfo.setLastUpdTxnId(getTxnId());
//        机构秘钥索引
		tblBrhInfo.setResv2(SysParamUtil.getParam(SysParamConstants.BRHINFO_KEY));
//		if(brhTmkIndex.length()<=3) {
//			for(int i=1;i<=(3-brhTmkIndex.length());i++){
//				brhTmkIndex=brhTmkIndex+" ";
//			}
//		}
//		resv2=brhTmkIndex+resv2;
		//POS柜员号
		//机构秘钥索引
		tblBrhInfo.setResv2(SysParamUtil.getParam(SysParamConstants.BRHINFO_KEY));
		
		//机构地区码+第五位：是否系统级签到+第六位：是否生成对账流水+第七位：是否清算到机构账号
		// 在分润信息取得完毕后追加：第八位：是否配置分润手续费 +5位分润费率代码（不配置则不加）
		tblBrhInfo.setResv1(record[5] + "0" + "0" + "0");
//				+resv1_8+(StringUtil.isEmpty(brhFee)?"":brhFee));

		return tblBrhInfo;
	}
	
	//合作伙伴账户信息
	private TblBrhSettleInf tblBrhSettleInf(String[] record, String brhId){
		TblBrhSettleInf tblBrhSettleInf = null;
		
		tblBrhSettleInf=new TblBrhSettleInf();
		// 合作伙伴编号
		tblBrhSettleInf.setBrhId(brhId);
		// 结算账户类型
		tblBrhSettleInf.setSettleFlag(record[9]);
		// 账户户名
		tblBrhSettleInf.setSettleAcctNm(record[10]);
		// 账户号
		tblBrhSettleInf.setSettleAcct(record[11]);
		// 开户支行号
		tblBrhSettleInf.setSettleBankNo(record[13]);
		// 开户支行名
		tblBrhSettleInf.setSettleBankNm(record[14]);
		tblBrhSettleInf.setCrtOpr(operator.getOprId());
		tblBrhSettleInf.setCrtTs(CommonFunction.getCurrentDateTime());
		
		return tblBrhSettleInf;
	}
	
	//合作伙伴费率信息
	TblAgentFeeCfg tblAgentFeeCfg(String[] record, String brhId) {
		TblAgentFeeCfg tblAgentFeeCfg = new TblAgentFeeCfg();
		
		String agentFeeSqlStr = "select max(DISC_ID) from TBL_agent_FEE_CFG";
		List agentFeeIdDataList = commQueryDAO.findBySQLQuery(agentFeeSqlStr);
		String agentFeeId = "";
		if(agentFeeIdDataList!=null && (agentFeeIdDataList.isEmpty()||(agentFeeIdDataList.get(0)==null)))
		{
			agentFeeId = "00001";
		}else{
			String maxNo=agentFeeIdDataList.get(0).toString();
			Integer max=Integer.parseInt(maxNo);
			max=max+1;
			maxNo=max+"";
			maxNo=compZeroAtF(maxNo,5);
			agentFeeId = maxNo;
		}
		tblAgentFeeCfg.setDiscId(agentFeeId);
		tblAgentFeeCfg.setSeq(new BigDecimal(1));
		tblAgentFeeCfg.setAgentNo(brhId);
		// 激励开始日期
		tblAgentFeeCfg.setPromotionBegDate(record[15]);
		// 激励结束日期
		tblAgentFeeCfg.setPromotionEndDate(record[16]);
		// 月交易额(万元)
		tblAgentFeeCfg.setBaseAmtMonth(record[17]!=null?Double.parseDouble(record[17]):0);
		// 提档交易额(万元)
		tblAgentFeeCfg.setGradeAmtMonth(record[18]!=null?Double.parseDouble(record[18]):0);
		//将费率等六个字段改为百分比
		// 激励系数%
		if(record[19] != null){
			tblAgentFeeCfg.setPromotionRate(Double.parseDouble(record[19]));
		}
		// 分润比率%
		if(record[20] != null){
			tblAgentFeeCfg.setAllotRate(Double.parseDouble(record[20]));
		}
		// VISA成本费率%
		if(record[21] != null){
			tblAgentFeeCfg.setExtVisaRate(Double.parseDouble(record[21]));
		}
		// MASTER成本费率%
		if(record[22] != null){
			tblAgentFeeCfg.setExtMasterRate(Double.parseDouble(record[22]));
		}
		// JCB成本费率%
		if(record[23] != null){
			tblAgentFeeCfg.setExtJcbRate(Double.parseDouble(record[23]));
		}
		// 特殊费率%
		tblAgentFeeCfg.setSpecFeeRate(0.0);

		
		return tblAgentFeeCfg;
	}
	
	// 分润费率信息的取得
	List<TblAgentRateInfo> rateList(String[] record, String agentFeeId) {
		//代理分润信息
		List<TblAgentRateInfo> rateList = new ArrayList<TblAgentRateInfo>();
		// 24列~33列代表分润费率信息
		int iColumn = 24;
		// 分润费率-封顶26
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("1");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("01");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-封顶80
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("1");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("02");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-比例0.38
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("0");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("03");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-比例0.78
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("0");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("04");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-比例1.25
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("0");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("05");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-县乡房车
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("1");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("07");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-县乡批发
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("1");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("06");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-分润费率-县乡餐娱
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("0");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("08");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-县乡一般
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("0");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("09");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		// 分润费率-县乡民生
		if (!StringUtil.isEmpty(record[iColumn])) {
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(Double.parseDouble(record[iColumn++]));
			// 1:封顶，0：比例
			tblAgentRateInfo.setFeeType("0");
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId("10");
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);			
		}
		return rateList;
	}
	
	//新增商户管理员信息
	ShTblOprInfo shTblOprInfo(String brhId) throws Exception {
 
	    ShTblOprInfo shTblOprInfo=new ShTblOprInfo();
		shTblOprInfo.setMchtBrhFlag("1");
		shTblOprInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		shTblOprInfo.setOprPwd(Encryption.encryptadd(SysParamUtil.getParam(SysParamConstants.OPR_DEFAULT_PWD)));
		shTblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction
				.getCurrentDate(), SysParamUtil
				.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		shTblOprInfo.setPwdWrTm(TblOprInfoConstants.PWD_WR_TM);
		shTblOprInfo.setRoleId(TblOprInfoConstants.SUP_BRH_ROLE);	
		shTblOprInfo.setPwdWrTmTotal(TblOprInfoConstants.PWD_WR_TM_TOTAL);
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_INIT);
		shTblOprInfo.setCurrentLoginTime(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginIp(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginStatus(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setPwdWrTmContinue(TblOprInfoConstants.PWD_WR_TM_CONTINUE);
		ShTblOprInfoPk id=new ShTblOprInfoPk();
		id.setBrhId(brhId);
		id.setOprId("admin");
		id.setMchtNo("-");
		shTblOprInfo.setId(id);
		shTblOprInfo.setOprName("管理员");
		
		return shTblOprInfo;
	}
	/**
	 * 合作伙伴信息导入
	 * @return
	 */
	public String brhInfoImport(CsvReader reader){
		try {
			
			reader.readHeaders(); 
			while (reader.readRecord()) {
				// 读取一行数据
				String[] record = reader.getValues();
				// 深圳合作伙伴编号
				String org_brhId = "";
				// 新生成合作伙伴编号
				String new_brhId = "";
				// 费率，分润用id
				String agentFeeId = "";
				// 检查数据
				if (!Constants.SUCCESS_CODE.equals(brhDataCheck(record))) {
					return null;
				}
				org_brhId = record[0];
				// 1	合作伙伴信息（TBL_BRH_INFO）
				TblBrhInfo tblBrhInfo = tblBrhInfo(record);
				new_brhId = tblBrhInfo.getBrhId();
				
				//
				// 将合作伙伴编号的映射情况记入临时表org_brhId,new_brhId
				//
//				dataImportBO.idRelationShipRecord(org_brhId, new_brhId, "tbl_import_brh");
				
				// 2	账户信息（TBL_BRH_SETTLE_INF）
				TblBrhSettleInf tblBrhSettleInf = tblBrhSettleInf(record, new_brhId);
				// 3	费率信息（TBL_AGENT_FEE_CFG）
				TblAgentFeeCfg tblAgentFeeCfg = tblAgentFeeCfg(record, new_brhId);
				agentFeeId = tblAgentFeeCfg.getDiscId();
				// 4	分润信息（TBL_AGENT_RATE_INFO）
				List<TblAgentRateInfo> rateList = rateList(record, agentFeeId);
				
				// 根据分润信息情况，设置机构表的分润关联信息
				if (rateList.size() > 0) {
					String resv1 = tblBrhInfo.getResv1();
					resv1 += "0" + agentFeeId;
					tblBrhInfo.setResv1(resv1);
				}
				
				// 5	商户管理员信息（sh_tbl_opr_info）
				ShTblOprInfo shTblOprInfo = shTblOprInfo(new_brhId);
				// 添加机构信息
//				rspCode=t10105BO.add(tblBrhInfo, tblBrhSettleInf, tblAgentFeeCfg, rateList, null);
				if(Constants.SUCCESS_CODE.equals(rspCode)){
					log("机构信息导入成功。操作员编号：" + operator.getOprId() + "，机构编号：" + new_brhId);
					//审核通过机构信息,并同步虚拟账户
					// 6	虚拟账户信息（VC虚拟账户接口）
					rspCode=t10101BO.accept(new_brhId, operator.getOprId() , getTxnId(), shTblOprInfo);
					
					if(Constants.SUCCESS_CODE.equals(rspCode)||"01".equals(rspCode)){
						log("审核通过信息成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + new_brhId);
					}
				}
				
			}
		} catch (Exception e) {
			// TO DO Auto-generated catch block
			e.printStackTrace();
			
		} finally{
			
		}  
		return null;
	}
	
	private String getRightsProp(String content){
		if("我司所有".equals(content)) {
			return "0";
		} else if("合作伙伴所有".equals(content)) {
			return "2";
		} else if("商户自有".equals(content)) {
			return "3";
		}
		return null;
	}
	
	// 数据一致性，完整性检查
	private String termDataCheck(String[] record) {
		
		// 终端所属商户编号
		if (StringUtil.isEmpty(record[0])) {
			return "终端所属商户编号不能为空。";
		}
		// 终端号
		if (StringUtil.isEmpty(record[1])) {
			return "终端号不能为空。";
		}
		// 终端状态
		if (StringUtil.isEmpty(record[2]) || null == getTermStatus(record[2])) {
			
			return "终端状态不能为空，或者终端状态不是下列关键字：（开通、停用、注销）。";
		}
		
		// 产权属性
		if (StringUtil.isEmpty(record[5]) || null == getRightsProp(record[5])) {
			
			return "产权属性不能为空，或者产权属性不是下列关键字：（我司所有、合作伙伴所有、商户自有）。";
		}
		
		// 终端类型
		if (StringUtil.isEmpty(record[6]) || null == getTermType(record[6])) {
			
			return "终端类型不能为空，或者终端类型关键字不正确。";
		}	
		return Constants.SUCCESS_CODE;
	}
	public String getImportTime() {
		return importTime;
	}
	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}


final class MyFileFilter implements FileFilter{
	
	private String filterStr;

	public MyFileFilter(String filterString){
		this.filterStr = filterString;
	}
	@Override
	public boolean accept(File pathname) {
		if (pathname.getName().startsWith(filterStr)){
			return true;
		}
		return false;
	}
	
}

class ImportResult {

	int totalRows;
	int successRows;
	int failRows;
	String resultFile;
	List<Map<String, String>> errInfoList;
	
	public ImportResult(int total, int success, int fail, List<Map<String, String>> errList, String file){
		totalRows = total;
		successRows = success;
		failRows = fail;
		errInfoList = errList;
		resultFile = file;
	}
	
	public String toJSONString(){
		return JSONArray.toJSON(this).toString();
	}
	

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getSuccessRows() {
		return successRows;
	}

	public void setSuccessRows(int successRows) {
		this.successRows = successRows;
	}

	public int getFailRows() {
		return failRows;
	}

	public void setFailRows(int failRows) {
		this.failRows = failRows;
	}

	public List<Map<String, String>> getErrInfoList() {
		return errInfoList;
	}

	public void setErrInfoList(List<Map<String, String>> errInfoList) {
		this.errInfoList = errInfoList;
	}

	public String getResultFile() {
		return resultFile;
	}

	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}
}
