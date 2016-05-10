package com.allinfinance.bo.impl.base;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.base.T10216BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblPayChannelInfoDAO;
import com.allinfinance.po.settle.TblPayChannelInfo;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TarZipUtils;

public class T10216BOTarget implements T10216BO {


	private ICommQueryDAO commQueryDAO;
	private TblPayChannelInfoDAO tblPayChannelInfoDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public String download(String settleDate,String aimChnlId) throws Exception {
		
		
		String zthSql="select SETTLE_DATE,SYS_DATE  from TBL_ZTH_DATE_NEW ";
		List<Object[]> zthDate =commQueryDAO.findBySQLQuery(zthSql);
		if(zthDate==null||zthDate.size()==0){
			return "请先进行准退货批量跑批！";
		}
		if(Integer.parseInt(settleDate)>Integer.parseInt(zthDate.get(0)[0].toString())){
			return "该日期目前未进行准退货跑批，请先进行准退货批量跑批！";
		}
		
		
//		updatePayTmp(settleDate);
		List<File> fileList=new ArrayList<File>();
		File payfile;
		List<Object[]> dataList;
		if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZX).equals(aimChnlId)){
			
			dataList =commQueryDAO.findBySQLQuery(getPayFileSqlZX(settleDate));
			payfile=new File(createPayfileZX(settleDate,dataList));
			
		}else if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZXWY).equals(aimChnlId)){
			
			dataList =commQueryDAO.findBySQLQuery(getPayFileSqlZX(settleDate));
			payfile=new File(createPayfileZXWY(settleDate,dataList));
			
		}else if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_GH).equals(aimChnlId)){
			
			dataList =commQueryDAO.findBySQLQuery(getPayFileSqlGH(settleDate));
			payfile=new File(createPayfileGH(settleDate,dataList));
			
		}else if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_YC).equals(aimChnlId)){
//			payfile=new File(createPayfileYC(settleDate,dateList,count));*/
			return "邮储划付文件暂不支持！";
		}else {
			return "结算通道未配置！";
		}
		
		File xlsFile=new File(createXls(settleDate));
		fileList.add(xlsFile);
		fileList.add(payfile);
//		List<TblPaySettleDtl> dataListCJ =getDataList(settleDate,SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ));
		List<Object[]> dataListCJ =commQueryDAO.findBySQLQuery(getPayFileSqlCJ(settleDate));
		if(dataListCJ!=null&&dataListCJ.size()>0){
			fileList.add(new File(CJfile(settleDate,dataListCJ).substring(2)));
		}
		
		
//		if(dataListCJ!=null&&dataListCJ.size()>0){
		fileList.add(new File(CJbackfile(settleDate).substring(2)));
//		}
		
		File[] srcfile =new File[fileList.size()];
		for (int i = 0; i < srcfile.length; i++) {
			srcfile[i]=fileList.get(i);
		}
		
		//将文件打包压缩
		File zipfile=new File(SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK) 
				+CommonFunction.getCurrentDate().substring(0, 6)+"/"+ "payDtlFileNew_"
				+CommonFunction.getCurrentTime()+".zip");
		
		TarZipUtils.zipCompress(srcfile , zipfile);
		
		
		return zipfile.getPath();
	}
	
	
	
//	=======================================工行划付文件====================================================
	private String createPayfileGH(String settleDate,List<Object[]> dataList) throws Exception{
		
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, 
//				SysParamConstants.DF_FILE_NAME_GSFK 
				settleDate+"_GH_"
				+"payFile_"
				+CommonFunction.getCurrentTime()
				+"_NEW.txt");
		
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        if(payFile.createNewFile()){
        	DecimalFormat df=new DecimalFormat("0.00");
        	FileWriter fw=null;
        	try {
	        	fw=new FileWriter(payFile) ;
	        	StringBuffer row=null;
	        	fw.write("付款账号,付款账号名称,收款账号开户行,收款账号地市,收款账号,收款账号名称,金额,汇款用途,汇款方式\n");
	        	
	        	for (Object[] objects : dataList) {
	        		row=new StringBuffer();
	        		row.append(objects[0].toString()).append(",")	//1.付款账号
	        		.append(objects[1].toString()).append(",")		//2.付款账号名称
	        		.append(objects[2].toString()).append(",")		//3.收款账号开户行
	        		.append(StringUtil.isNotEmpty(objects[3])?objects[3].toString():"").append(",")		//4.收款账号地市
	        		.append(objects[4].toString()).append(",")		//5.收款账号
	        		.append(objects[5].toString()).append(",")		//6.收款账号名称
	        		.append(df.format(Double.parseDouble(
	        				StringUtil.isNotEmpty(objects[6])?objects[6].toString():"0"
	        				))).append(",")		//7.清算净额
	        		.append(objects[7].toString()).append(",")		//8.汇款用途
	        		.append(objects[8].toString())					//9.汇款方式
	        		.append("\n");									//换行
	        		fw.write(row.toString());
				}
	        	fw.flush();
        	} catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
    			return "工行代付文件生成失败!";
			}finally{
	            if(fw!=null){
	            	fw.close();
	            }
	        }
        	payFile.setWritable (false);
        }
		return payFile.getPath();
	}
	
	private String getPayFileSqlGH(String settleDate){
		 TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO
					.get(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_GH));
		
		
		String sql="SELECT "
				+ "'"+tblPayChannelInfo.getAcctNo()+"' ,"
				+ "'"+tblPayChannelInfo.getAcctNm()+"',"
				+ "a.CNAPS_NAME,"
				+ "a.area_nm,"
				+ "a.MCHT_ACCT_NO,"
				+ "a.MCHT_ACCT_NM,"
//				+ " as error,"
				+ "a.amt_settle-nvl((select nvl(yd_bal,0) from tbl_settle_mcht_error where mcht_no=trim(a.mcht_id) ),0) as error,"
//				+ 
				+ "'Pos结算',"
				+ "decode(a.pay_type,'1','加急','2','普通',a.pay_type) as type_pay "
				+" from TBL_SETTLE_DTL a "
				+" where a.date_stlm='"+settleDate+"' "
				+" and a.pay_type!='0' "
				+" and a.CHANNEL_ID in(select CHANNEL_ID from TBL_PAY_CHANNEL_INFO where CHANNEL_FLAG ='1')  ";
//				+ " order by error desc ";
		String retSql="select * from ("+sql+") where error>0 order by error desc ";
		return retSql;
	}
	
	
//	=======================================中信划付文件====================================================
	private String createPayfileZX(String settleDate,List<Object[]> dataList) throws Exception{
		
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, 
//				SysParamConstants.DF_FILE_NAME_GSFK 
				settleDate+"_ZX_"
				+"payFile_"
				+CommonFunction.getCurrentTime()
				+"_NEW.txt");
		
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        if(payFile.createNewFile()){
        	DecimalFormat df=new DecimalFormat("0.00");
        	FileWriter fw=null;
        	try {
	        	fw=new FileWriter(payFile) ;
	        	StringBuffer row=null;
	        	fw.write("文件类型:FTExternalTransferBatch\n");
	        	fw.write("标题:对外支付\n\n");
	        	fw.write("导入模板的字段内容为：\n\n");
	        	fw.write("说明:1.前后加'*'号的字段为必填项\n");
	        	fw.write("2.各字段前后不允许有空格\n");
	        	fw.write("-----------------------------------\n");
	        	
	        	
	        	for (Object[] objects : dataList) {
	        		row=new StringBuffer();
	        		
	        		row.append(objects[0].toString()).append("|")	//1.付款账号
	        		.append(objects[1].toString()).append("|")		//2.人民币
	        		.append(objects[2].toString()).append("|")		//3.其他银行账户
	        		.append(objects[3].toString()).append("|")		//4.划款账户
	        		.append(objects[4].toString()).append("|")		//5.划款账户名称
	        		.append("|")									//6.空
	        		.append(objects[5].toString()).append("|")		//7.大小额标识
	        		
	        		.append(df.format(Double.parseDouble(
	        				StringUtil.isNotEmpty(objects[6])?objects[6].toString():"0"
	        				))).append("|")							//8.清算净额
	        		.append(objects[7].toString()).append("|")		//9.开户行号
	        		.append(objects[8].toString()).append("|")		//10.开户行名称
	        		.append("|")									//11.空
	        		.append("|")									//12.空
	        		.append("立即支付").append("|")					//13.立即支付
	        		.append("|")									//14.空
	        		.append("|")									//15.空
	        		.append(objects[9].toString()).append("|")		//16.pos交易结算款
	        		.append("|")									//17.空
	        		.append("|")									//18.空
	        		.append("|")									//19.空
	        		.append("\n");									//换行
	        		fw.write(row.toString());
				}
	        	fw.flush();
        	} catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
        		return "中信代付文件生成失败!";
			}finally{
	            if(fw!=null){
	            	fw.close();
	            }
	        }
        	payFile.setWritable (false);
        }
		return payFile.getPath();
	}
	
	
	private String createPayfileZXWY(String settleDate,List<Object[]> dataList) throws Exception{
		
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, 
//				SysParamConstants.DF_FILE_NAME_GSFK 
				settleDate+"_ZXWY_"
				+"payFile_"
				+CommonFunction.getCurrentTime()
				+"_NEW.txt");
		
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        if(payFile.createNewFile()){
        	DecimalFormat df=new DecimalFormat("0.00");
        	FileWriter fw=null;
        	try {
	        	fw=new FileWriter(payFile) ;
	        	StringBuffer row=null;
	        	fw.write("文件类型:ExtPayBatch\n");
	        	fw.write("标题:支付转账经办\n\n");
	        	fw.write("导入模板的字段内容为：\n\n");
	        	fw.write("说明:1.前后加'*'号的字段为必填项\n");
	        	fw.write("2.各字段前后不允许有空格\n");
	        	fw.write("-----------------------------------\n");
	        			
	        	String[] payFormat;
	        	
	        	String smallFlagAcct="select acct_no,acct_nm from TBL_PAY_TYPE_SMALL where 1=1 ";
	        	@SuppressWarnings("unchecked")
				List<Object[]> payAcctList=commQueryDAO.findBySQLQuery(smallFlagAcct);
				
	        	
	        	for (Object[] objects : dataList) {
	        		row=new StringBuffer();
	        		payFormat=payFormat(objects,payAcctList);
	        		
	        		row.append(objects[0].toString()).append("|")	//1.付款账号
	        		.append(objects[1].toString()).append("|")		//2.人民币
//	        		.append(objects[2].toString()).append("|").append("|")		//3.其他银行账户
	        		.append("其他银行账户").append("|").append("|")		//3.其他银行账户
	        		.append(objects[3].toString()).append("|")		//4.划款账户
	        		.append(objects[4].toString()).append("|")		//5.划款账户名称
	        		.append(objects[7].toString()).append("|")		//9.开户行号
	        		.append(objects[8].toString()).append("|")		//10.开户行名称
	        		.append("|")									//6.空
	        		.append("|")
	        		.append(payFormat[0]).append("|")		//7.大小额标识
	        		
	        		.append(df.format(Double.parseDouble(
	        				StringUtil.isNotEmpty(objects[6])?objects[6].toString():"0"
	        				))).append("|")							//8.清算净额
	        		
	        		.append("立即支付").append("|")					//13.立即支付
	        		.append("|")									//11.空
	        		.append("|")									//12.空
	        		
	        		.append("结算款").append("|")					//16.pos交易结算款
	        		.append(payFormat[1]).append("|")									//17.空
	        		.append(payFormat[2]).append("|")									//18.空
	        		
	        		.append("|")									//19.空
	        		.append("|")									//19.空
	        		.append("|")									//19.空
	        		.append("\n");									//换行
	        		fw.write(row.toString());
				}
	        	fw.flush();
        	} catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
        		return "中信代付文件生成失败!";
			}finally{
	            if(fw!=null){
	            	fw.close();
	            }
	        }
        	payFile.setWritable (false);
        }
		return payFile.getPath();
	}
	
	private String getPayFileSqlZX(String settleDate){
		
		TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO
				.get(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZX));
		
		
		
		
		String sql="SELECT "
				+ "'"+tblPayChannelInfo.getAcctNo()+"',"
				+ "'人民币',"
				+ "decode(substr(a.cnaps_id,1,3),'302','中信账户','其他银行账户') as bank_flag,"
				+ "a.MCHT_ACCT_NO,"
				+ "a.MCHT_ACCT_NM,"
				+ "decode(a.pay_type,'1','大额支付','2','小额支付',a.pay_type) as type_pay, "
				+ "a.amt_settle-nvl((select nvl(yd_bal,0) from tbl_settle_mcht_error where mcht_no=trim(a.mcht_id) ),0) as error,"
				
				+ "a.CNAPS_ID,"
				+ "a.CNAPS_NAME,"
//				+ 
				
				+ "'Pos交易结算款',"
				+ "(select t.cnaps_id from tbl_cnaps_for_zx_pay t where substr(t.cnaps_id,1,3)=substr(a.CNAPS_ID,1,3) and  substr(cnaps_id,1,3)!='302' and cnaps_flag='1' ) as zx_cnaps_id, "
				+ "(select t.cnaps_name from tbl_cnaps_for_zx_pay t where substr(t.cnaps_id,1,3)=substr(a.CNAPS_ID,1,3) and  substr(cnaps_id,1,3)!='302' and cnaps_flag='1' ) as zx_cnaps_name  "
				
				+" from TBL_SETTLE_DTL a "
				+" where a.date_stlm='"+settleDate+"' "
				+" and a.pay_type!='0' "
				+" and a.CHANNEL_ID in(select CHANNEL_ID from TBL_PAY_CHANNEL_INFO where CHANNEL_FLAG ='1') ";
			//	+ " order by error desc ";
		
		String retSql="select * from ("+sql+") where error>0 order by error desc ";
		return retSql;
	}
	
	
	
	
	
	private String createXls(String settleDate) throws Exception{
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
//		List<String[]> paramList=new ArrayList<String[]>();
		List<String> dataSqlList=new ArrayList<String>();
//		List<String> sumDataSqlList=new ArrayList<String>();
		sheetList.add("重复入账商户明细");
		sheetList.add(settleDate+"_结算划付明细");
		sheetList.add(settleDate+"_畅捷划付明细");
		titleList.add("重复入账商户明细");
		titleList.add(settleDate+"_结算划付明细");
		titleList.add(settleDate+"_畅捷划付明细");
		
		String[] coulmHerder1=new String[4+2];
		coulmHerder1[0]="商户号";
		coulmHerder1[1]="商户名";
		coulmHerder1[2]="所属机构";
		coulmHerder1[3]="截止上次划付后亏损金额";
		coulmHerder1[4]=settleDate+"入账金额";
		/*for (int i = 0; i < count+1; i++) {
			coulmHerder1[i+4]=dateList.get(i)+"入账金额";
		}*/
		coulmHerder1[coulmHerder1.length-1]="目前亏损金额";
		
		String[] coulmHerder2={"商户号","商户名称","所属机构","账户号",
				"账户名","行号","行名",settleDate+"原划付金额",settleDate+"为止所欠金额",
				"应划付金额"};
		String[] coulmHerder3={"商户号","商户名称","所属机构","账户号",
				"账户名","行号","行名",settleDate+"原划付金额",
				settleDate+"为止所欠金额",settleDate+"为止准退货金额","应划付金额"};
		coulmHeaderList.add(coulmHerder1);
		coulmHeaderList.add(coulmHerder2);
		coulmHeaderList.add(coulmHerder3);
		
		
		String sqlDate1=repeatMcht( settleDate);
		String sqlDate2=payDtl(settleDate);
		String sqlDate3=payDtlCJ(settleDate);
		dataSqlList.add(sqlDate1);
		dataSqlList.add(sqlDate2);
		dataSqlList.add(sqlDate3);
		
		String rnName=settleDate+"_payReprot_";
		String fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) 
				+ rnName  
			 + CommonFunction.getCurrentTime() + ".xls";
		FileOutputStream outputStream = new FileOutputStream(fileName);

		
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
//		excelReportCreator.setParamList(paramList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));
//		excelReportCreator.setSumList(excelReportCreator.processData(sumDataSqlList, coulmHeaderList));
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		
		return fileName;
	}
	
	private String payDtl(String settleDate){
		
		String sql="SELECT a.mcht_id ,a.mcht_nm,"
				+ "(select brh_id||'-'||brh_name from tbl_brh_info where brh_id in (select c.bank_no from tbl_mcht_base_inf c where c.mcht_no=a.mcht_id)) as brh_id_name,"
				+ "a.MCHT_ACCT_NO ,a.MCHT_ACCT_NM ,a.CNAPS_ID,a.CNAPS_NAME,"
				+ "nvl(a.AMT_SETTLE,0) as amt,"
				+ "nvl((select yd_bal from tbl_settle_mcht_error where mcht_no=trim(a.mcht_id) ),0) as error,"
				+ "nvl(a.AMT_SETTLE,0)-nvl((select yd_bal from tbl_settle_mcht_error where mcht_no=trim(a.mcht_id) ),0) as error1 "
				+" from TBL_SETTLE_DTL a where a.date_stlm='"+settleDate+"' "
				+ " and a.pay_type!='0' "
				+ " and a.CHANNEL_ID in(select CHANNEL_ID from TBL_PAY_CHANNEL_INFO where CHANNEL_FLAG ='1') "
				+ " order by error1";
		return sql;
	}
	
	
	private String payDtlCJ(String settleDate){
		
		StringBuffer backAmt1 = new StringBuffer(); 
		backAmt1.append("nvl(("
				+ "select sum(nvl(m.samt,0)-nvl(m.amt1,0)) from TBL_ZTH_NEW m "
				+ " where m.rspcode='00' "
				+ " and m.eflag!='1' "
				+ " and m.inst_date<='"+settleDate+"' "
//				+ " and d.CHANNEL_ID in(select CHANNEL_ID from TBL_PAY_CHANNEL_INFO where CHANNEL_FLAG ='1') "
//				+ " and m.mcht_no=a.mcht_no "
				+ " and m.f42 in ("
					+ " select mcht_no "
					+ " from tbl_mcht_base_inf "
					+ " where bank_no =("
						+ "case when trim(a.mcht_id)  in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001') "
						+ "then trim(a.mcht_id) "
						+ "else '' "
						+ "end )"
					+ "or mcht_no=("
						+ "case when trim(a.mcht_id)  in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001') "
						+ "then '' "
						+ "else a.mcht_id "
						+ "end )"
				+ ") "
			+ "),0)");
		
		StringBuffer backAmt2 = new StringBuffer(); 
		backAmt2.append("nvl(("
				+ "select sum(nvl(k.amt,0)) from TBL_ZTH_DTL_NEW k "
				+ " where 1=1 "
				+ " and k.settlmt_date='"+settleDate+"' "
//				+ " and d.CHANNEL_ID in(select CHANNEL_ID from TBL_PAY_CHANNEL_INFO where CHANNEL_FLAG ='1') "
//				+ " and m.mcht_no=a.mcht_no "
				+ " and k.mcht_no in ("
					+ " select mcht_no "
					+ " from tbl_mcht_base_inf "
					+ " where bank_no =("
						+ "case when trim(a.mcht_id)  in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001') "
						+ "then trim(a.mcht_id) "
						+ "else '' "
						+ "end )"
					+ "or mcht_no=("
						+ "case when trim(a.mcht_id)  in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001') "
						+ "then '' "
						+ "else a.mcht_id "
						+ "end )"
				+ ") "
			+ "),0)");
		
		StringBuffer backAmt= new StringBuffer();
		backAmt.append("("+backAmt1+"+"+backAmt2+")");
		
		
		StringBuffer factErrorAmt = new StringBuffer();
		factErrorAmt.append(" nvl(a.amt_settle,0)-nvl((select yd_bal from tbl_settle_mcht_error where mcht_no=trim(a.mcht_id) ),0)-"+backAmt);
		
		String sql="SELECT a.mcht_id ,a.mcht_nm,"
				+ "(select brh_id||'-'||brh_name from tbl_brh_info where brh_id in (select c.bank_no from tbl_mcht_base_inf c where c.mcht_no=a.mcht_id)) as brh_id_name,"
				+ "a.MCHT_ACCT_NO ,a.MCHT_ACCT_NM ,a.CNAPS_ID,a.CNAPS_NAME,"
				+ "nvl(a.amt_settle,0) as amt, "
				+ "nvl((select yd_bal from tbl_settle_mcht_error where mcht_no=trim(a.mcht_id) ),0) as error,"
//				+ error+" as error,"
				+ backAmt+" as back,"
				+ factErrorAmt+" as facterror "
				+" from TBL_SETTLE_DTL a where a.date_stlm='"+settleDate+"' "
				+ " and a.pay_type!='0' " 
				+ " and a.CHANNEL_ID ='07' "
				+ " order by facterror ";
		return sql;
	}
	
	
	private String repeatMcht(String settleDate){
		
		
		String sql="SELECT a.MCHT_NO,"
				+ "  a.mcht_nm,"
				+ "(select brh_id||'-'||brh_name from tbl_brh_info where brh_id in (select c.bank_no from tbl_mcht_base_inf c where c.mcht_no=a.mcht_no)) as brh_id_name,"
				+ "a.yd_bal*(-1),"
				+ "nvl((select sum(amt_settle) from tbl_settle_dtl where pay_type!='0' and trim(mcht_id)=a.mcht_no and date_stlm='"+settleDate+"' ),0),"
				+ " (a.yd_bal-nvl((select sum(amt_settle) from tbl_settle_dtl where pay_type!='0' and mcht_id=a.mcht_no and date_stlm='"+settleDate+"' ),0))*(-1) as error "
				+" from TBL_SETTLE_MCHT_ERROR a "
				+ "order by error ";
		
		String resSql="select * from ("+sql+") where error<0 ";
		return resSql;
	}
	
	
	
	
	private String[] payFormat(Object[] objects,List<Object[]> payAcctList){
		
		String zxCnapsId=StringUtil.isNotEmpty(objects[10])?objects[10].toString():"";
		String zxCnapsName=StringUtil.isNotEmpty(objects[11])?objects[11].toString():"";
		String payFlag=StringUtil.isNotEmpty(objects[5])?objects[5].toString():"";
		
		String [] foramtRes=new String[3];
		
		foramtRes[0]="小额支付".equals(payFlag)?"网银跨行支付":payFlag;
		
		if(!StringUtil.isEmpty(zxCnapsId)&&!StringUtil.isEmpty(zxCnapsName)){
			foramtRes[1]=zxCnapsId.toString();
			foramtRes[2]=zxCnapsName.toString();
		}else{
			foramtRes[0]=payFlag;
			foramtRes[1]="";
			foramtRes[2]="";
		}
		
		if(payAcctList!=null&&payAcctList.size()>0){
			for (Object[] payAcct : payAcctList) {
				if(objects[3].toString().equals(payAcct[0].toString())
						&&objects[4].toString().equals(payAcct[1].toString())){
					foramtRes[0]=payFlag;
					foramtRes[1]="";
					foramtRes[2]="";
					return foramtRes;
				}
			}
		}
		return foramtRes;
	}
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80803BO#GHfile(java.lang.String, java.lang.String)
	 * 畅捷划付文件生成
	 */
	public String CJfile(String instDate, List<Object[]> dataList) throws Exception {
		// TODO Auto-generated method stub
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		String fileName=path+SysParamConstants.DF_FILE_NAME_CJFK+ instDate+"_"+getBatchNo(instDate)+".xls";
		FileOutputStream outputStream = new FileOutputStream(fileName);
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO.get(SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ));
        
        ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
//		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
//		List<String[]> paramList=new ArrayList<String[]>();
		List<Object[][]> dataObjList=new ArrayList<Object[][]>();
//		List<String> sumDataSqlList=new ArrayList<String>();
        
		String[] coulmHeader={"业务参考号","收款人编号","收款人账号","收款人名称","收方开户支行",
				"收款人所在省","收款人所在市","收方邮件地址","收方移动电话","币种",
				"付款分行","结算方式","业务种类","付方账号",
				"期望日","期望时间","用途","金额","收方行号","收方开户银行",
				"业务摘要"};
		sheetList.add("畅捷支付结算单"+instDate);
//		titleList.add("");
		coulmHeaderList.add(coulmHeader);
		
		Object[][] object=new Object[dataList.size()][coulmHeader.length];
//		TblPaySettleDtl tblPaySettleDtl=null;
		Object[] srcObj=null;

		
		
		
		for (int i = 0; i < dataList.size(); i++) {
			srcObj=dataList.get(i);
			object[i][0]=instDate+CommonFunction.fillString(srcObj[0].toString().trim(),'0', 16, false);
			object[i][1]=instDate+CommonFunction.fillString(String.valueOf(i+1), '0', 7, false);
			object[i][2]=StringUtil.isNotEmpty(srcObj[1])?srcObj[1].toString():"";
			object[i][3]=StringUtil.isNotEmpty(srcObj[2])?srcObj[2].toString():"";
			object[i][4]=StringUtil.isNotEmpty(srcObj[3])?srcObj[3].toString():"";
			object[i][5]=StringUtil.isNotEmpty(srcObj[4])?srcObj[4].toString():"";//所在省
			object[i][6]=StringUtil.isNotEmpty(srcObj[5])?srcObj[5].toString():"";//所在市
			object[i][7]="";//邮件地址
			object[i][8]="";//移动电话
			object[i][9]="人民币";//币种
			object[i][10]="";//付款分行
			object[i][11]="快速";//结算方式
			object[i][12]="普通汇兑";//业务种类
			object[i][13]=tblPayChannelInfo.getAcctNo();//付方账号
			object[i][14]="";//期望日
			object[i][15]="";//期望时间
			object[i][16]="结算款";//用途
			object[i][17]=formatMoneyStr(StringUtil.isNotEmpty(srcObj[6])?srcObj[6].toString():"");
			object[i][18]=StringUtil.isNotEmpty(srcObj[7])?srcObj[7].toString():"";
			object[i][19]=StringUtil.isNotEmpty(srcObj[3])?srcObj[3].toString():"";
			object[i][20]="钱宝金融";//业务摘要
		}
		dataObjList.add(object);
		
		int leftFormat[]={2,3,4,5,6,19,20};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
//		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(dataObjList);
		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}

	
	
	/**
	 * @param instDate
	 * @param channelId
	 * @return sql
	 * @throws Exception
	 */
	private String getPayFileSqlCJ(String instDate) throws Exception {
		
		
		
		StringBuffer backAmt = new StringBuffer(); 
		backAmt.append("nvl(("
				+ "select sum(nvl(m.AMT,0)) from TBL_ZTH_DTL_NEW m "
				+ " where 1=1 "
				+ " and m.SETTLMT_DATE='"+instDate+"' "
//				+ " and d.CHANNEL_ID in(select CHANNEL_ID from TBL_PAY_CHANNEL_INFO where CHANNEL_FLAG ='1') "
//				+ " and m.mcht_no=a.mcht_no "
				+ " and m.mcht_no in ("
					+ " select mcht_no "
					+ " from tbl_mcht_base_inf "
					+ " where bank_no =("
						+ "case when trim(a.mcht_id)  in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001') "
						+ "then trim(a.mcht_id) "
						+ "else '' "
						+ "end )"
					+ "or mcht_no=("
						+ "case when trim(a.mcht_id)  in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001') "
						+ "then '' "
						+ "else a.mcht_id "
						+ "end )"
				+ ") "
			+ "),0)");
		
		
		StringBuffer factErrorAmt = new StringBuffer();
		factErrorAmt.append(" nvl(a.amt_settle,0)-nvl((select yd_bal from tbl_settle_mcht_error where mcht_no=trim(a.mcht_id)),0)-"+backAmt);
		
		
		
		String sql="SELECT a.mcht_id ,"
				+ "a.MCHT_ACCT_NO ,"
				+ "a.MCHT_ACCT_NM ,"
				+ "a.CNAPS_NAME,"
				
				+ "(select AREA_NAME from CST_PROV_CODE where BEG_CODE<=substr(a.CNAPS_ID,4,2) and END_CODE>=substr(a.CNAPS_ID,4,2) ) as pro_name,"
				+ "a.area_nm,"
//				+ "nvl(a.AMT_SETTLMT,0) as amt, "
//				+ error+" as error,"
//				+ backAmt+" as back,"
				+ factErrorAmt+" as facterror, "
				+ "a.CNAPS_ID "
				+" from TBL_SETTLE_DTL a where a.date_stlm='"+instDate+"' "
				+ " and pay_type!='0' "
				+ " and a.CHANNEL_ID ='07' ";
//				+ " order by facterror desc ";
		
		String retSql="select * from ("+sql+") where facterror>0 order by facterror desc ";
		return retSql;
	}
	
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80803BO#GHfile(java.lang.String, java.lang.String)
	 * 畅捷退款明细文件生成
	 */
	public String CJbackfile(String instDate) throws Exception {
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
				
				
		String fileName=path+SysParamConstants.RETURN_FILE_NAME_CJ+ instDate+"_"+CommonFunction.getCurrentTime()+".xls";
		FileOutputStream outputStream = new FileOutputStream(fileName);
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        
        ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
//		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
//		List<String[]> paramList=new ArrayList<String[]>();
//		List<Object[][]> dataObjList=new ArrayList<Object[][]>();
		List<String> dataSqlList=new ArrayList<String>();
//		List<String> sumDataSqlList=new ArrayList<String>();
        
		String[] coulmHeader={"序号","商户名称","交易日期时间","商户编号","卡号",
				"交易参考号","交易金额","退货金额","备注"};
		sheetList.add("畅捷支付退款明细"+instDate);
//		titleList.add("");
		coulmHeaderList.add(coulmHeader);
		
		
		dataSqlList.add(getBackSqlCJ(instDate));
		
		int leftFormat[]={1};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
//		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
//		excelReportCreator.setDataList(dataObjList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));
		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}
	
	/**
	 * @param instDate
	 * @return sql
	 * @throws Exception
	 */
	private String getBackSqlCJ(String instDate) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="SELECT rownum,"
				+ "a.mcht_name ,"
//				+ "a.o_f13,"
				+"to_char(to_date(trim(a.o_f13)||a.o_f12,'mm-dd hh24:mi:ss'),'mm-dd hh24:mi:ss') as date_time,"
				+ "a.f42_sy,"
				+ "a.f2,"
				+ "a.o_f37_sy,"
				+ "a.o_amt,"
				+ "a.f4,"
				+ "'' "
				+" from TBL_ZTH_NEW a "
				+ "where a.rspcode='00' "
				+ "and a.eflag='1' "
				+ "and a.ddate='"+instDate+"' "
				+ " order by f42 ";
		
		return sql;
	}
	
	
	
	/**
	 * @param instDate
	 * @return 获取批次号，并且在原基础上update加1
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String getBatchNo(String instDate) throws Exception {
		String sql=" select distinct max(to_number(nvl(RESERVED_1,0)))+1 from TBL_SETTLE_DTL "
				+ "where date_stlm='"+instDate+"' and pay_type!='0' ";
		List<BigDecimal> resultSet= commQueryDAO.findBySQLQuery(sql);
		String	batchNo= CommonFunction.fillString(resultSet.get(0).toString(), '0', 4, false);
		String updateSql="update TBL_SETTLE_DTL set RESERVED_1='"+batchNo+"' "
				+ "where date_stlm='"+instDate+"' and pay_type!='0' ";
		commQueryDAO.excute(updateSql);
		return batchNo;
	}
	
	
	private String  formatMoneyStr(String money) {
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(Double.parseDouble(money.toString()));
		
	}
	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}



	public TblPayChannelInfoDAO getTblPayChannelInfoDAO() {
		return tblPayChannelInfoDAO;
	}



	public void setTblPayChannelInfoDAO(TblPayChannelInfoDAO tblPayChannelInfoDAO) {
		this.tblPayChannelInfoDAO = tblPayChannelInfoDAO;
	}

	

	
	
}
