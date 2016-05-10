package com.allinfinance.bo.impl.settle;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;
























import com.allinfinance.bo.settle.T80803BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.select.SelectMethod;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblPayChannelInfoDAO;
import com.allinfinance.dao.iface.settle.TblPaySettleDtlDAO;
import com.allinfinance.po.settle.TblPayChannelInfo;
import com.allinfinance.po.settle.TblPaySettleDtl;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TarZipUtils;

/**
 * @author caotz
 *
 */
public class T80803BOTarget implements T80803BO {
	


	private ICommQueryDAO commQueryDAO;
	private TblPaySettleDtlDAO tblPaySettleDtlDAO;
	private TblPayChannelInfoDAO tblPayChannelInfoDAO;
//	private static Logger log = Logger.getLogger(T80803BOTarget.class);
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String allFile(String instDate) throws Exception {
		// TODO Auto-generated method stub
		List<TblPaySettleDtl> dataListZX =getDataList(instDate,SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZX));
		List<TblPaySettleDtl> dataListGH =getDataList(instDate,SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_GH));
		List<TblPaySettleDtl> dataListYC =getDataList(instDate,SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_YC));
//		List<TblPaySettleDtl> dataListCJ =getDataList(instDate,SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ));
		List<Object[]> dataListCJ =commQueryDAO.findBySQLQuery(getPayFileSqlCJ(instDate, SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ)));
		
		List<File> fileList=new ArrayList<File>();
		
		if(dataListZX!=null&&dataListZX.size()>0){
			fileList.add(new File(ZXfile(instDate,dataListZX).substring(2)));
			fileList.add(new File(ZXWYfile(instDate,dataListZX).substring(2)));
		}
		if(dataListGH!=null&&dataListGH.size()>0){
			fileList.add(new File(GHfile(instDate,dataListGH).substring(2)));
		}
		if(dataListYC!=null&&dataListYC.size()>0){
			fileList.add(new File(YCfile(instDate,dataListYC,SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_YC)).substring(2)));
		}
		if(dataListCJ!=null&&dataListCJ.size()>0){
			fileList.add(new File(CJfile(instDate,dataListCJ).substring(2)));
		}
		
		fileList.add(new File(CJbackfile(instDate).substring(2)));
		

		File[] srcfile =new File[fileList.size()];
		for (int i = 0; i < srcfile.length; i++) {
			srcfile[i]=fileList.get(i);
		}
		
		//将文件打包压缩
		File zipfile=new File(SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK) 
				+CommonFunction.getCurrentDate().substring(0, 6)+"/"+ "allPayFile_"
				+instDate+"_"+CommonFunction.getCurrentTime()+".zip");
		
		TarZipUtils.zipCompress(srcfile , zipfile);
		
		return Constants.SUCCESS_CODE_CUSTOMIZE+zipfile.getPath();
	}

	

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80803BO#makeFile(java.lang.String, java.lang.String, java.lang.String)
	 * 下载指定文件
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String makeFile(String instDate, String channelId, String aimChnlId)
			throws Exception {
		// TODO Auto-generated method stub
		
		List<TblPaySettleDtl> dataList =getDataList(instDate,channelId);
		if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZX).equals(aimChnlId)){
			if(SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ).equals(channelId)){
				return "由于准退货原因，畅捷付格式不能转其他格式划付文件";
			}
			return ZXfile(instDate, dataList);
		}if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZXWY).equals(aimChnlId)){
			if(SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ).equals(channelId)){
				return "由于准退货原因，畅捷付格式不能转其他格式划付文件";
			}
			return ZXWYfile(instDate, dataList);
		}else if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_GH).equals(aimChnlId)){
			if(SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ).equals(channelId)){
				return "由于准退货原因，畅捷付格式不能转其他格式划付文件";
			}
			return GHfile(instDate, dataList);
		}else if(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_YC).equals(aimChnlId)){
			if(SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ).equals(channelId)){
				return "由于准退货原因，畅捷付格式不能转其他格式划付文件";
			}
			return YCfile(instDate, dataList,channelId);
		}else if(SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ).equals(aimChnlId)){
			if(!SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ).equals(channelId)){
				return "由于准退货原因，其他通道格式不能转畅捷格式划付文件";
			}
			List<Object[]> dataListCJ =commQueryDAO.findBySQLQuery(getPayFileSqlCJ(instDate, SysParamUtil.getParam(SysParamConstants.TXN_CODE_CJ)));
			return CJfile(instDate, dataListCJ);
		}else {
			return "结算通道未配置！";
		}
	}


	
	
	/* (non-Javadoc)  
	 * @see com.allinfinance.bo.settle.T80803BO#ZXfile(java.lang.String, java.lang.String)
	 * 中信划付文件生成
	 */
	public String ZXfile(String instDate,List<TblPaySettleDtl> dataList) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, 
				SysParamConstants.DF_FILE_NAME_ZXFK 
				+ instDate
				+"_"+getBatchNo(instDate)+".txt");
		TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO
				.get(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZX));
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        
        if(payFile.createNewFile()){
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
	        	for (TblPaySettleDtl tblPaySettleDtl : dataList) {
	        		row=new StringBuffer();
	        		row.append(tblPayChannelInfo.getAcctNo()).append("|")	//1.划款账户
	        		.append("人民币").append("|")							//2.人民币
	        		.append("其他银行账户").append("|")						//3.其他银行账户
	        		.append(tblPaySettleDtl.getMchtAcctNo()).append("|")	//4.划款账户
	        		.append(tblPaySettleDtl.getMchtAcctNm()).append("|")	//5.划款账户名称
	        		.append("|")											//6.空
	        		.append(SelectMethod.getPayTypeZX(null)
	        				.get(tblPaySettleDtl.getPayType()))
	        				.append("|")									//7.大小额标识
	        		.append(formatMoney(tblPaySettleDtl
	        				.getAmtSettlmt())).append("|")					//8.清算净额
	        		.append(tblPaySettleDtl.getCnapsId()).append("|")		//9.开户行号
	        		.append(tblPaySettleDtl.getCnapsName()).append("|")		//10.开户行名称
	        		.append("|")											//11.空
	        		.append("|")											//12.空
	        		.append("立即支付").append("|")							//13.立即支付
	        		.append("|")											//14.空
	        		.append("|")											//15.空
	        		.append("POS交易结算款").append("|")						//16.pos交易结算款
	        		.append("|")											//17.空
	        		.append("|")											//18.空
	        		.append("|")											//19.空
	        		.append("\n");											//换行
	        		
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
		return Constants.SUCCESS_CODE_CUSTOMIZE+payFile.getPath();
	}

	
	
	
	/* (non-Javadoc)  
	 * @see com.allinfinance.bo.settle.T80803BO#ZXWYfile(java.lang.String, java.lang.String)
	 * 中信划付文件生成
	 */
	@SuppressWarnings("unchecked")
	public String ZXWYfile(String instDate, List<TblPaySettleDtl> dataList) throws Exception {
		// TODO Auto-generated method stub
		
		
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, 
				SysParamConstants.DF_FILE_NAME_ZXWYFK 
				+ instDate
				+"_"+getBatchNo(instDate)+".txt");
		TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO
				.get(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_ZX));
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        
        String[] payFormat;
		
		String smallFlagAcct="select acct_no,acct_nm from TBL_PAY_TYPE_SMALL where 1=1 ";
    	String cnapsId="select misc,cnaps_id,cnaps_name from tbl_cnaps_for_zx_pay  where 1=1 and substr(cnaps_id,1,3)!='848' and cnaps_flag='1'";
		List<Object[]> payAcctList=commQueryDAO.findBySQLQuery(smallFlagAcct);
		List<Object[]> cnapsIdList=commQueryDAO.findBySQLQuery(cnapsId);
		
        if(payFile.createNewFile()){
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
	        	for (TblPaySettleDtl tblPaySettleDtl : dataList) {

	        		payFormat=payFormat(tblPaySettleDtl,payAcctList,cnapsIdList);
	        		row=new StringBuffer();
	        		
	        		row.append(tblPayChannelInfo.getAcctNo()).append("|")	//1.划款账户
	        		.append("人民币").append("|")							//2.人民币
	        		.append("其他银行账户").append("|")						//3.其他银行账户
	        		.append("|")											//空
	        		.append(tblPaySettleDtl.getMchtAcctNo()).append("|")	//4.划款账户
	        		.append(tblPaySettleDtl.getMchtAcctNm()).append("|")	//5.划款账户名称
	        		.append(tblPaySettleDtl.getCnapsId()).append("|")		//6.开户行号
	        		.append(tblPaySettleDtl.getCnapsName()).append("|")		//7.开户行名称
	        		.append("|")											//8.空
	        		.append("|")											//9.空
	        		.append(payFormat[0]).append("|")						//10.大小额标识
	        		.append(formatMoney(tblPaySettleDtl
	        				.getAmtSettlmt())).append("|")					//11.清算净额
	        		.append("立即支付").append("|")							//12.立即支付
	        		.append("|")											//13.空
	        		.append("|")											//14.空
	        		.append("结算款").append("|")							//15.pos交易结算款
	        		.append(payFormat[1]).append("|")						//16.空
	        		.append(payFormat[2]).append("|")						//17.空
	        		.append("|")											//18.空
	        		.append("|")											//19.空
	        		.append("|")											//20.空
	        		.append("\n");											//换行
	        		
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
		return Constants.SUCCESS_CODE_CUSTOMIZE+payFile.getPath();
	}
	
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80803BO#GHfile(java.lang.String, java.lang.String)
	 * 工行划付文件生成
	 */
	public String GHfile(String instDate, List<TblPaySettleDtl> dataList) throws Exception {
		// TODO Auto-generated method stub
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, 
				SysParamConstants.DF_FILE_NAME_GSFK 
				+ instDate
				+"_"+getBatchNo(instDate)+".txt");
		
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO
				.get(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_GH));
        if(payFile.createNewFile()){
        	FileWriter fw=null;
        	try {
	        	fw=new FileWriter(payFile) ;
	        	StringBuffer row=null;
	        	fw.write("付款账号,付款账号名称,收款账号开户行,收款账号地市,收款账号,收款账号名称,金额,汇款用途,汇款方式\n");
	        	for (TblPaySettleDtl tblPaySettleDtl : dataList) {
	        		row=new StringBuffer();
	        		row.append(tblPayChannelInfo.getAcctNo()).append(",")	//1.付款账号
	        		.append(tblPayChannelInfo.getAcctNm()).append(",")		//2.付款账号名称
	        		.append(tblPaySettleDtl.getCnapsName()).append(",")		//3.收款账号开户行
	        		.append(tblPaySettleDtl.getMisc1()).append(",")			//4.收款账号地市
	        		.append(tblPaySettleDtl.getMchtAcctNo()).append(",")	//5.收款账号
	        		.append(tblPaySettleDtl.getMchtAcctNm()).append(",")	//6.收款账号名称
	        		.append(formatMoney(tblPaySettleDtl
	        				.getAmtSettlmt())).append(",")					//7.清算净额
	        		.append("Pos结算").append(",")							//8.汇款用途
	        		.append(SelectMethod.getPayTypeGH(null)
	        				.get(tblPaySettleDtl.getPayType()))				//9.汇款方式
	        		.append("\n");											//换行
	        		
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
		return Constants.SUCCESS_CODE_CUSTOMIZE+payFile.getPath();
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80803BO#YCfile(java.lang.String, java.lang.String)
	 * 邮储-银联划付文件生成
	 */
	public String YCfile(String instDate,List<TblPaySettleDtl> dataList,String channelId) throws Exception {
		// TODO Auto-generated method stub
		 TblPayChannelInfo tblPayChannelInfo =tblPayChannelInfoDAO
					.get(SysParamUtil.getParam(SysParamConstants.CHANNEL_CODE_YC));
		
		String batchNo=getBatchNo(instDate);
		String date=instDate;
		
		String path=SysParamUtil.getParam(SysParamConstants.PAY_FILE_DISK)
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, 
				SysParamConstants.DF_FILE_NAME_YCFK 
				+ tblPayChannelInfo.getMchtNo()
				+ date
				+ batchNo
				+ "I"
				+".txt"
				);
		
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        
        if(payFile.createNewFile()){
        	FileWriter fw=null;
        	try {
	        	fw=new FileWriter(payFile) ;
	        	StringBuffer row=null;
	        	row=new StringBuffer();
	        	row.append(tblPayChannelInfo.getMchtNo()).append("|")		//a.商户号
	        	.append(batchNo).append("|")								//b.批次号
	        	.append(date).append("|")									//c.上传日期
	        	.append(getSumAmt(instDate,channelId)
	        			.replace(".", "")).append("|")						//d.总金额
	        	.append(dataList.size()).append("|")						//e.总笔数
	        	.append("|")												//f.保留域1
	        	.append("\n");												//g.保留域2  换行
	        	fw.write(row.toString());
	        	for (TblPaySettleDtl tblPaySettleDtl : dataList) {
	        		row=new StringBuffer();
	        		row.append(tblPayChannelInfo.getMchtNo()).append("|")	//1.商户代码
	        		.append(tblPaySettleDtl.getTblPaySettleDtlPK().getMchtNo().length()==15?
	        				tblPaySettleDtl.getTblPaySettleDtlPK()
	        				.getInstDate().substring(4)+tblPaySettleDtl
	        				.getTblPaySettleDtlPK().getMchtNo()
	        				.substring(3)
	        			:tblPaySettleDtl.getTblPaySettleDtlPK()
	        				.getInstDate().substring(4)+
	        				CommonFunction.fillString(tblPaySettleDtl
	    	        		.getTblPaySettleDtlPK().getMchtNo().trim(), '0', 12, true))
	    	        		.append("|")									//2.订单号
	        		.append("156").append("|")								//3.交易币种
	        		.append(formatMoney(tblPaySettleDtl.getAmtSettlmt())
	        				.replace(".", "")).append("|")					//4.金额
	        		.append("|")											//5.产品类型
	        		.append("01").append("|")								//6.账号类型
	        		.append(tblPaySettleDtl.getMchtAcctNo()).append("|")	//7.账号
	        		.append(tblPaySettleDtl.getMchtAcctNm()).append("|")	//8.户名
	        		.append("|")											//9.开户行省
	        		.append("|")											//10.开户行市
	        		.append(tblPaySettleDtl.getCnapsName()).append("|")		//11.开户行名称
	        		.append("|")											//12.证件类型
	        		.append("|")											//13.证件号码
	        		.append("|")											//14.手机号
	        		.append("|")											//15.账单类型
	        		.append("POS交易结算款").append("|")						//16.附言 pos交易结算款
	        		.append("|")											//17.空
	        		.append("\n");											//18.空 换行
	        		
	        		fw.write(row.toString());
				}
	        	fw.flush();
        	} catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
    			return "邮储代付文件生成失败!";
			}finally{
	            if(fw!=null){
	            	fw.close();
	            }
	        }
        	payFile.setWritable (false);
        }
		return Constants.SUCCESS_CODE_CUSTOMIZE+payFile.getPath();
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
	private String getPayFileSqlCJ(String instDate,String channelId) throws Exception {
		// TODO Auto-generated method stub
		
		StringBuffer backAmt = new StringBuffer(); 
		backAmt.append("nvl(("
				+ "select sum(nvl(m.AMT,0)) from TBL_ZTH_DTL m "
				+ " where 1=1 "
				+ " and m.SETTLMT_DATE='"+instDate+"' "
//				+ " and d.CHANNEL_ID in(select CHANNEL_ID from TBL_PAY_CHANNEL_INFO where CHANNEL_FLAG ='1') "
//				+ " and m.mcht_no=a.mcht_no "
				+ " and m.mcht_no in ("
					+ " select mcht_no "
					+ " from tbl_mcht_base_inf "
					+ " where bank_no =("
						+ "case when trim(a.mcht_no)  in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001') "
						+ "then trim(a.mcht_no) "
						+ "else '' "
						+ "end )"
					+ "or mcht_no=("
						+ "case when trim(a.mcht_no)  in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001') "
						+ "then '' "
						+ "else a.mcht_no "
						+ "end )"
				+ ") "
			+ "),0)");
		
		
		StringBuffer factErrorAmt = new StringBuffer();
		factErrorAmt.append(" nvl(a.AMT_SETTLMT,0)-"+backAmt);
		
		
		
		String sql="SELECT a.MCHT_NO ,"
				+ "a.MCHT_ACCT_NO ,"
				+ "a.MCHT_ACCT_NM ,"
				+ "a.CNAPS_NAME,"
				
				+ "(select AREA_NAME from CST_PROV_CODE where BEG_CODE<=substr(a.CNAPS_ID,4,2) and END_CODE>=substr(a.CNAPS_ID,4,2) ) as pro_name,"
				+ "a.misc1,"
//				+ "nvl(a.AMT_SETTLMT,0) as amt, "
//				+ error+" as error,"
//				+ backAmt+" as back,"
				+ factErrorAmt+" as facterror, "
				+ "a.CNAPS_ID "
				+" from TBL_PAY_SETTLE_DTL a where a.inst_date='"+instDate+"' "
				+ " and a.CHANNEL_ID ='"+channelId+"' ";
//				+ " order by facterror desc ";
		
		String retSql="select * from ("+sql+") where facterror>0 order by facterror desc ";
		return retSql;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80803BO#GHfile(java.lang.String, java.lang.String)
	 * 畅捷退款明细文件生成
	 */
	public String CJbackfile(String instDate) throws Exception {
		// TODO Auto-generated method stub
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
				+" from TBL_ZTH a "
				+ "where a.rspcode='00' "
				+ "and a.eflag='1' "
				+ "and a.ddate='"+instDate+"' "
				+ " order by f42 ";
		
		return sql;
	}
	
	
	/**
	 * @param instDate
	 * @param channelId
	 * @return 获取 TblPaySettleDtl List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<TblPaySettleDtl> getDataList(String instDate,String channelId) throws Exception {
		// TODO Auto-generated method stub
		String hql=" from com.allinfinance.po.settle.TblPaySettleDtl t "
				+ "where  t.tblPaySettleDtlPK.instDate='"+instDate+"' "
				+ "and t.tblPaySettleDtlPK.channelId='"+channelId+"' "
				+ " order by t.amtSettlmt desc ";
		List<TblPaySettleDtl> dataList =commQueryDAO.findByHQLQuery(hql);
		return dataList;
	}
	
	/**
	 * @param instDate
	 * @return 获取批次号，并且在原基础上update加1
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String getBatchNo(String instDate) throws Exception {
		// TODO Auto-generated method stub
		String sql=" select distinct max(to_number(BATCH_NO))+1 from TBL_PAY_SETTLE_DTL "
				+ "where INST_DATE='"+instDate+"' ";
		List<BigDecimal> resultSet= commQueryDAO.findBySQLQuery(sql);
		String	batchNo= CommonFunction.fillString(resultSet.get(0).toString(), '0', 4, false);
		String updateSql="update TBL_PAY_SETTLE_DTL set BATCH_NO='"+batchNo+"' "
				+ "where INST_DATE='"+instDate+"' ";
		commQueryDAO.excute(updateSql);
		return batchNo;
	}
	
	

	/**
	 * @param instDate
	 * @return 中信网银跨行支付格式转换
	 * @throws Exception
	 */
	private String[] payFormat(TblPaySettleDtl tblPaySettleDtl,List<Object[]> payAcctList,List<Object[]> cnapsIdList){
		
		String [] foramtRes=new String[3];
		
		if(payAcctList!=null&&payAcctList.size()>0){
			for (Object[] payAcct : payAcctList) {
				if(tblPaySettleDtl.getMchtAcctNo().equals(payAcct[0].toString())
						&&tblPaySettleDtl.getMchtAcctNm().equals(payAcct[1].toString())){
					foramtRes[0]="1".equals(tblPaySettleDtl.getPayType())?"小额支付":"大额支付";
					foramtRes[1]="";
					foramtRes[2]="";
					return foramtRes;
				}
			}
		}
		
		if(cnapsIdList!=null&&cnapsIdList.size()>0){
			for (Object[] cnapsId : cnapsIdList) {
				if(tblPaySettleDtl.getCnapsId().substring(0,3).equals(cnapsId[0].toString())){
					foramtRes[0]="1".equals(tblPaySettleDtl.getPayType())?"网银跨行支付":"大额支付";
					foramtRes[1]=cnapsId[1].toString();
					foramtRes[2]=cnapsId[2].toString();
					return foramtRes;
				}
			}
		}
		
		foramtRes[0]="1".equals(tblPaySettleDtl.getPayType())?"小额支付":"大额支付";
		foramtRes[1]="";
		foramtRes[2]="";
		
		return foramtRes;
	}

	/**
	 * @param instDate
	 * @param channelId
	 * @return 计算此划付文件总金额
	 * @throws Exception
	 */
	private String  getSumAmt(String instDate,String channelId) throws Exception {
		// TODO Auto-generated method stub
		String sql=" select  to_char(nvl(sum(to_number(AMT_SETTLMT)),0)) as amt_sum from TBL_PAY_SETTLE_DTL "
				+ "where INST_DATE='"+instDate+"' and CHANNEL_ID='"+channelId+"'";
		String  ret = commQueryDAO.findCountBySQLQuery(sql);
		return ret;
	}
	
	
	/**
	 * @param instDate
	 * @param channelId
	 * @return 获取开户行所在省份
	 * @throws Exception
	 */
	/*private String  getProv(String cnapsId) throws Exception {
		// TODO Auto-generated method stub
		String proCode=cnapsId.substring(3, 5);
		String sql=" select  AREA_NAME  from CST_PROV_CODE "
				+ "where BEG_CODE<='"+proCode+"' and END_CODE>='"+proCode+"'";
		String  ret = commQueryDAO.findCountBySQLQuery(sql);
		return ret;
	}*/
	
	
	private String  formatMoney(BigDecimal money) {
		// TODO Auto-generated method stub
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(money.doubleValue());
		
	}
	
	private String  formatMoneyStr(String money) {
		// TODO Auto-generated method stub
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(Double.parseDouble(money.toString()));
		
	}
	
	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}
	
	
	
	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public TblPaySettleDtlDAO getTblPaySettleDtlDAO() {
		return tblPaySettleDtlDAO;
	}

	public void setTblPaySettleDtlDAO(TblPaySettleDtlDAO tblPaySettleDtlDAO) {
		this.tblPaySettleDtlDAO = tblPaySettleDtlDAO;
	}



	public TblPayChannelInfoDAO getTblPayChannelInfoDAO() {
		return tblPayChannelInfoDAO;
	}



	public void setTblPayChannelInfoDAO(TblPayChannelInfoDAO tblPayChannelInfoDAO) {
		this.tblPayChannelInfoDAO = tblPayChannelInfoDAO;
	}

	

	
	
	
}