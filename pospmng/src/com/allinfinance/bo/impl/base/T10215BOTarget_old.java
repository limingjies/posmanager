package com.allinfinance.bo.impl.base;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;






import com.allinfinance.bo.base.T10215BO;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblPayChannelInfoDAO;
import com.allinfinance.po.settle.TblPayChannelInfo;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TarZipUtils;

public class T10215BOTarget_old implements T10215BO {


	private ICommQueryDAO commQueryDAO;
	private TblPayChannelInfoDAO tblPayChannelInfoDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public String download(String settleDate,String aimChnlId) throws Exception {
		// TODO Auto-generated method stub
		String sql="select distinct INST_DATE from TBL_PAY_SETTLE_DTL"
				+ " where INST_DATE>'20150209' and INST_DATE<='"+settleDate+"' order by INST_DATE ";
		List<String> dateList =commQueryDAO.findBySQLQuery(sql);
		if(dateList.size()==0){
			return "划付表未同步！";
		}
		boolean boo=false;
		int count=0;
		for (int i = 0; i < dateList.size(); i++) {
			if(settleDate.equals(dateList.get(i))){
				count=i;
				boo=true;
			}
		}
		if(!boo){
			return "该日期目前还未生成划付数据！";
		}
		
		
//		updatePayTmp(settleDate);
		File[] srcfile;
		List<Object[]> dataList;
		if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZX).equals(aimChnlId)){
			
			dataList =commQueryDAO.findBySQLQuery(getPayFileSqlZX(settleDate, dateList,count));
			
			File payfile=new File(createPayfileZX(settleDate,dataList));
			File payfile2=new File(createPayfileZXWY(settleDate,dataList));
			File xlsFile=new File(createXls(settleDate,dateList,count));
			File[]  srcfiles = { xlsFile ,payfile ,payfile2};
			srcfile=srcfiles;
			
		}else if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_GH).equals(aimChnlId)){
			
			dataList =commQueryDAO.findBySQLQuery(getPayFileSqlGH(settleDate, dateList,count));
			File payfile=new File(createPayfileGH(settleDate,dataList));
			File xlsFile=new File(createXls(settleDate,dateList,count));
			File[]  srcfiles = { xlsFile ,payfile};
			srcfile=srcfiles;
			
		}else if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_YC).equals(aimChnlId)){
//			payfile=new File(createPayfileYC(settleDate,dateList,count));*/
			return "邮储划付文件暂不支持！";
		}else {
			return "结算通道未配置！";
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
	
	private String getPayFileSqlGH(String settleDate,List<String> dateList,int count){
		 TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO
					.get(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_GH));
		
		StringBuffer actAmtsql = new StringBuffer();
		actAmtsql.append("nvl(("
				+ "select sum(nvl(d.AMT_SETTLMT,0)) from TBL_PAY_SETTLE_DTL d "
				+ " where d.mcht_no in (select mcht_no from TBL_PAY_SETTLE_MCHT_ERROR ) "
				+ " and d.inst_date<'"+settleDate+"' "
				+ " and d.inst_date>='"+dateList.get(0)+"' "
				+ " and d.mcht_no=a.mcht_no "
			+ "),0)");
		actAmtsql.append("-nvl((select e.AMT from TBL_PAY_SETTLE_MCHT_ERROR e where e.mcht_no=a.mcht_no ),0)  ");
		
		String str1="abs(case when "+actAmtsql+">0 then 0 else "+actAmtsql+" end ) ";
		
		StringBuffer str2 = new StringBuffer();
		str2.append(" nvl(a.AMT_SETTLMT,0)-"+str1);
		
		String sql="SELECT "
				+ "'"+tblPayChannelInfo.getAcctNo()+"' ,"
				+ "'"+tblPayChannelInfo.getAcctNm()+"',"
				+ "a.CNAPS_NAME,"
				+ "a.misc1,"
				+ "a.MCHT_ACCT_NO,"
				+ "a.MCHT_ACCT_NM,"
				+ str2+" as error,"
//				+ 
				+ "'Pos结算',"
				+ "decode(a.pay_type,'0','加急','1','普通',a.pay_type) as type_pay "
				+" from TBL_PAY_SETTLE_DTL a "
				+" where a.inst_date='"+settleDate+"' "
				+" and "+str2+">0 "
				+ " order by error desc ";
		return sql;
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
	
	private String getPayFileSqlZX(String settleDate,List<String> dateList,int count){
		
		TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO
				.get(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZX));
		
		StringBuffer actAmtsql = new StringBuffer();
		actAmtsql.append("nvl(("
				+ "select sum(nvl(d.AMT_SETTLMT,0)) from TBL_PAY_SETTLE_DTL d "
				+ " where d.mcht_no in (select mcht_no from TBL_PAY_SETTLE_MCHT_ERROR ) "
				+ " and d.inst_date<'"+settleDate+"' "
				+ " and d.inst_date>='"+dateList.get(0)+"' "
				+ " and d.mcht_no=a.mcht_no "
			+ "),0)");
		actAmtsql.append("-nvl((select e.AMT from TBL_PAY_SETTLE_MCHT_ERROR e where e.mcht_no=a.mcht_no ),0)  ");
		
		String str1="abs(case when "+actAmtsql+">0 then 0 else "+actAmtsql+" end ) ";
		
		StringBuffer str2 = new StringBuffer();
		str2.append(" nvl(a.AMT_SETTLMT,0)-"+str1);
		
		String sql="SELECT "
				+ "'"+tblPayChannelInfo.getAcctNo()+"',"
				+ "'人民币',"
				+ "decode(substr(a.cnaps_id,1,3),'302','中信账户','其他银行账户') as bank_flag,"
				+ "a.MCHT_ACCT_NO,"
				+ "a.MCHT_ACCT_NM,"
				+ "decode(a.pay_type,'0','大额支付','1','小额支付',a.pay_type) as type_pay, "
				+ str2+" as error,"
				
				+ "a.CNAPS_ID,"
				+ "a.CNAPS_NAME,"
//				+ 
				
				+ "'Pos交易结算款',"
				+ "(select t.cnaps_id from tbl_cnaps_for_zx_pay t where substr(t.cnaps_id,1,3)=substr(a.CNAPS_ID,1,3) and  substr(cnaps_id,1,3)!='302' and cnaps_flag='1' ) as zx_cnaps_id, "
				+ "(select t.cnaps_name from tbl_cnaps_for_zx_pay t where substr(t.cnaps_id,1,3)=substr(a.CNAPS_ID,1,3) and  substr(cnaps_id,1,3)!='302' and cnaps_flag='1' ) as zx_cnaps_name  "
				
				+" from TBL_PAY_SETTLE_DTL a "
				+" where a.inst_date='"+settleDate+"' "
				+" and "+str2+">0 "
				+ " order by error desc ";
		return sql;
	}
	
	
	
	
	
	private String createXls(String settleDate,List<String> dateList,int count) throws Exception{
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
//		List<String[]> paramList=new ArrayList<String[]>();
		List<String> dataSqlList=new ArrayList<String>();
//		List<String> sumDataSqlList=new ArrayList<String>();
		sheetList.add("重复入账商户明细");
		sheetList.add(settleDate+"_商户划付明细");
		titleList.add("重复入账商户明细");
		titleList.add(settleDate+"_商户划付明细");
		
		String[] coulmHerder1=new String[4+count+2];
		coulmHerder1[0]="商户号";
		coulmHerder1[1]="商户名";
		coulmHerder1[2]="所属机构";
		coulmHerder1[3]="周一重复入账金额";
		for (int i = 0; i < count+1; i++) {
			coulmHerder1[i+4]=dateList.get(i)+"入账金额";
		}
		coulmHerder1[coulmHerder1.length-1]="截止上次划付后亏损金额";
		
		String[] coulmHerder2={"商户号","商户名称","所属机构","账户号",
				"账户名","行号","行名",settleDate+"商户原划付金额",settleDate+"为止所欠金额",
				"应划付金额"};
		coulmHeaderList.add(coulmHerder1);
		coulmHeaderList.add(coulmHerder2);
		
		
		String sqlDate1=repeatMcht( dateList);
		String sqlDate2=payDtl(settleDate,dateList,count);
		dataSqlList.add(sqlDate1);
		dataSqlList.add(sqlDate2);
		
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
	
	private String payDtl(String settleDate,List<String> dateList,int count){
		
		StringBuffer errorSql = new StringBuffer(); 
		errorSql.append("nvl(("
				+ "select sum(nvl(d.AMT_SETTLMT,0)) from TBL_PAY_SETTLE_DTL d "
				+ " where d.mcht_no in (select mcht_no from TBL_PAY_SETTLE_MCHT_ERROR ) "
				+ " and d.inst_date<'"+settleDate+"' "
				+ " and d.inst_date>='"+dateList.get(0)+"'"
				+ " and d.mcht_no=a.mcht_no "
			+ "),0)");
		errorSql.append("-nvl((select e.AMT from TBL_PAY_SETTLE_MCHT_ERROR e where e.mcht_no=a.mcht_no ),0)  ");
		
		String str="abs(case when "+errorSql+">0 then 0 else "+errorSql+" end  ) ";
		
				
		StringBuffer actAmtsql = new StringBuffer();
		actAmtsql.append(" nvl(a.AMT_SETTLMT,0)-"+str);
		
		
		String sql="SELECT a.MCHT_NO ,a.MISC,"
				+ "(select brh_id||'-'||brh_name from tbl_brh_info where brh_id in (select c.bank_no from tbl_mcht_base_inf c where c.mcht_no=a.mcht_no)) as brh_id_name,"
				+ "a.MCHT_ACCT_NO ,a.MCHT_ACCT_NM ,a.CNAPS_ID,a.CNAPS_NAME,"
				+ "nvl(a.AMT_SETTLMT,0) as amt,"
				+ str+" as error,"
				+ actAmtsql+" as error1 "
				+" from TBL_PAY_SETTLE_DTL a where a.inst_date='"+settleDate+"'"
				+ " order by error1";
		return sql;
	}
	
	
	private String repeatMcht(List<String> dateList){
		
		StringBuffer amtSql = new StringBuffer();
		for (int i = 0; i < dateList.size(); i++) {
			amtSql.append("nvl(("
					+ "select sum(nvl(AMT_SETTLMT,0)) from TBL_PAY_SETTLE_DTL "
					+ " where mcht_no in (select mcht_no from TBL_PAY_SETTLE_MCHT_ERROR ) "
					+ " and inst_date='"+dateList.get(i)+"' "
					+ " and mcht_no=a.mcht_no "
				+ "),0) as V_"+(i+1)+",");
		}
		
		StringBuffer errorSql = new StringBuffer();
		errorSql.append("nvl(("
				+ "select sum(nvl(d.AMT_SETTLMT,0)) from TBL_PAY_SETTLE_DTL d "
				+ " where d.mcht_no in (select mcht_no from TBL_PAY_SETTLE_MCHT_ERROR ) "
				+ " and d.inst_date<'"+dateList.get(dateList.size()-1)+"' "
				+ " and d.inst_date>='"+dateList.get(0)+"'"
				+ " and d.mcht_no=a.mcht_no "
			+ "),0)");
		errorSql.append("-nvl(a.AMT,0) as error ");
		
		String sql="SELECT a.MCHT_NO,"
				+ "  mcht_nm,"
				+ "(select brh_id||'-'||brh_name from tbl_brh_info where brh_id in (select c.bank_no from tbl_mcht_base_inf c where c.mcht_no=a.mcht_no)) as brh_id_name,"
				+ "nvl(a.AMT,0),"
				+amtSql
				+errorSql
				+" from TBL_PAY_SETTLE_MCHT_ERROR a "
				+ "order by error ";
		return sql;
	}
	
	
	
	
	/*@SuppressWarnings("unchecked")
	private String[] payFormat(String cnapsId,String payFlag){
		String [] foramtRes=new String[3];
		foramtRes[0]="小额支付".equals(payFlag)?"网银跨行支付":payFlag;
		
		String sql="select cnaps_id,cnaps_name from tbl_cnaps_for_zx_pay "
				+ " where substr(cnaps_id,1,3)='"+cnapsId.substring(0, 3)+"' "
				+ " and substr(cnaps_id,1,3)!='302' and cnaps_flag='1' ";
		List<Object[]> dataList=commQueryDAO.findBySQLQuery(sql);
		if(dataList!=null&&dataList.size()>0){
			foramtRes[1]=dataList.get(0)[0].toString();
			foramtRes[2]=dataList.get(0)[1].toString();
		}else{
			foramtRes[0]=payFlag;
			foramtRes[1]="";
			foramtRes[2]="";
		}
		return foramtRes;
	}*/
	
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
