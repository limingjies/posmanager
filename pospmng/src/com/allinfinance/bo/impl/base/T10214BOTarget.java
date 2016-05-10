package com.allinfinance.bo.impl.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.base.T10214BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

public class T10214BOTarget implements T10214BO {

	@Override
	public String formatFile(List<File> fileList, List<String> fileNameList,
			String fileType, String amtUp, String amtRes)throws Exception {
		// TODO Auto-generated method stub

		List<String[]> data;
		String res;
		if("01".equals(fileType)){
			data= readFileGH(fileList, amtUp, amtRes);
			res= GHfile(fileNameList.get(0),data); 
		}else if("02".equals(fileType)){
			data= readFileZX_old(fileList, amtUp, amtRes);
			res=ZXfileOld(fileNameList.get(0),data);
		}else if("03".equals(fileType)){
			data= readFileZX_new(fileList, amtUp, amtRes);
			res=ZXfileNew(fileNameList.get(0),data);
		}else{
			return "格式不存在！";
		}
		
		return res;
	}

/**	==================================读取工行格式文件========================================================*/
	private List<String[]> readFileGH(List<File> fileList,String amtUp,String amtRes) throws Exception {

		DecimalFormat df = new DecimalFormat("0.00");
		double amtUpLimit=Double.parseDouble(amtUp);
		double amtResLimit=Double.parseDouble(amtRes);
		
		List<String[]> data = new ArrayList<String[]>();
		for (File file : fileList) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "GBK"));
			int begin = 0;
			while (reader.ready()) {
				String str = reader.readLine();
				if (!StringUtil.isNull(str) && begin > 0) {
					String[] acct = str.split(",");
					double amt = Double.parseDouble(acct[6]);
					
					double temp = amt;
					String[] acctTmp;
					while (temp >= amtUpLimit) {
						temp = temp - amtResLimit;
						acctTmp = new String[9];
						System.arraycopy(acct, 0, acctTmp, 0,acctTmp.length);
						acctTmp[6] = df.format(amtResLimit);
						data.add(acctTmp);
					}
					acctTmp = new String[9];
					System.arraycopy(acct, 0, acctTmp, 0,acctTmp.length);
					acctTmp[6] = df.format(temp);
					data.add(acctTmp);
				}
				begin++;
			}
			reader.close();
		}
		return data;
	}
	
/**	==================================读取中信——旧划付格式文件========================================================*/
	private List<String[]> readFileZX_old(List<File> fileList,String amtUp,String amtRes) throws Exception {

		DecimalFormat df = new DecimalFormat("0.00");
		double amtUpLimit=Double.parseDouble(amtUp);
		double amtResLimit=Double.parseDouble(amtRes);
		
// 7315010182400003251|人民币|其他银行账户|2001489745000172|聚财通科技（北京）有限公司||大额支付|22826071.63|318110000014|渤海银行北京亚运村支行|||立即支付|||Pos交易结算款||||
		List<String[]> data = new ArrayList<String[]>();
		for (File file : fileList) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "GBK"));
			int begin = 0;
			while (reader.ready()) {
				String str = reader.readLine()+"temp";
				if (!StringUtil.isNull(str) && begin >7) {
					String[] acct = str.split("\\|");
					acct[acct.length-1]="";
					double amt = Double.parseDouble(acct[7]);
					
					double temp = amt;
					String[] acctTmp;
					while (temp >= amtUpLimit) {
						temp = temp - amtResLimit;
						acctTmp = new String[acct.length];
						System.arraycopy(acct, 0, acctTmp, 0,acctTmp.length);
						acctTmp[7] = df.format(amtResLimit);
						data.add(acctTmp);
					}
					acctTmp = new String[acct.length];
					System.arraycopy(acct, 0, acctTmp, 0,acctTmp.length);
					acctTmp[7] = df.format(temp);
					data.add(acctTmp);
				}
				begin++;
			}
			reader.close();
		}
		return data;
	}
	
	/**	==================================读取中信——新划付格式文件========================================================*/
	private List<String[]> readFileZX_new(List<File> fileList,String amtUp,String amtRes) throws Exception {

		DecimalFormat df = new DecimalFormat("0.00");
		double amtUpLimit=Double.parseDouble(amtUp);
		double amtResLimit=Double.parseDouble(amtRes);
		
// 7315010182400003251|人民币|其他银行账户||2001489745000172|聚财通科技（北京）有限公司|318110000014|渤海银行北京亚运村支行|||大额支付|25637287.25|立即支付|||结算款|318110000014|渤海银行股份有限公司||||
		List<String[]> data = new ArrayList<String[]>();
		for (File file : fileList) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "GBK"));
			int begin = 0;
			while (reader.ready()) {	
				String str = reader.readLine()+"temp";
				if (!StringUtil.isNull(str) && begin > 7) {
					String[] acct = str.split("\\|");
					acct[acct.length-1]="";
					double amt = Double.parseDouble(acct[11]);
					
					double temp = amt;
					String[] acctTmp;
					while (temp >= amtUpLimit) {
						temp = temp - amtResLimit;
						acctTmp = new String[acct.length];
						System.arraycopy(acct, 0, acctTmp, 0,acctTmp.length);
						acctTmp[11] = df.format(amtResLimit);
						data.add(acctTmp);
					}
					acctTmp = new String[acct.length];
					System.arraycopy(acct, 0, acctTmp, 0,acctTmp.length);
					acctTmp[11] = df.format(temp);
					data.add(acctTmp);
				}
				begin++;
			}
			reader.close();
		}
		return data;
	}
	
	
//	==================================工行划付文件生成========================================================
	public String GHfile( String fileName,List<String[]> dataList) throws Exception {
		// TODO Auto-generated method stub
		String path=SysParamUtil.getParam("TEMP_FILE_DISK")
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, "CF_"+fileName);
		
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        if((payFile).exists()){
        	payFile.delete();
        }
        if(payFile.createNewFile()){
        	FileWriter fw=null;
        	try {
	        	fw=new FileWriter(payFile) ;
	        	StringBuffer row=null;
	        	fw.write("付款账号,付款账号名称,收款账号开户行,收款账号地市,收款账号,收款账号名称,金额,汇款用途,汇款方式\n");
					
	        	for (String[] strings : dataList) {
	        		row=new StringBuffer();
	        		row.append(strings[0]).append(",")	//1.付款账号
	        		.append(strings[1]).append(",")		//2.付款账号名称
	        		.append(strings[2]).append(",")		//3.收款账号开户行
	        		.append(strings[3]).append(",")			//4.收款账号地市
	        		.append(strings[4]).append(",")	//5.收款账号
	        		.append(strings[5]).append(",")	//6.收款账号名称
	        		.append(strings[6]).append(",")					//7.清算净额
	        		.append(strings[7]).append(",")							//8.汇款用途
//	        		.append(strings[8])				//9.汇款方式
	        		.append("普通")				//9.汇款方式
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
	
	
//	==================================中信划付文件（旧）生成========================================================
	public String ZXfileOld( String fileName,List<String[]> dataList) throws Exception {
		// TODO Auto-generated method stub
		String path=SysParamUtil.getParam("TEMP_FILE_DISK")
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, "CF_"+fileName);
		
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        if((payFile).exists()){
        	payFile.delete();
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
//  7315010182400003251|人民币|其他银行账户|905000120190005849|上海商赢商务有限公司||大额支付|259623.95|313290038018|温州银行股份有限公司上海分行|||立即支付|||Pos交易结算款||||
	        	for (String[] strings : dataList) {
	        		row=new StringBuffer();
	        		strings[6]="小额支付";
	        		for (int i = 0; i < strings.length; i++) {
	        			row.append(strings[i]);
	        			if((i+1)==strings.length){
	        				row.append("\n");
	        			}else{
	        				row.append("|");
	        			}
					}
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
	
	
//	==================================中信划付文件（新）生成========================================================
	public String ZXfileNew( String fileName,List<String[]> dataList) throws Exception {
		// TODO Auto-generated method stub
		String path=SysParamUtil.getParam("TEMP_FILE_DISK")
				+CommonFunction.getCurrentDate().substring(0, 6)+"/";
		File payFile = new File(path, "CF_"+fileName);
		
        if (!new File(path).isDirectory()) {// 判断本地存放文件的文件夹是否存在
			new File(path).mkdirs();
		}
        if((payFile).exists()){
        	payFile.delete();
        }
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
//  7315010182400003251|人民币|其他银行账户||2001489745000172|聚财通科技（北京）有限公司|318110000014|渤海银行北京亚运村支行|||大额支付|25637287.25|立即支付|||结算款|318110000014|渤海银行股份有限公司||||
	        	for (String[] strings : dataList) {
	        		row=new StringBuffer();
	        		if("大额支付".equals(strings[10])){
	        			strings[10]=StringUtil.isEmpty(strings[16])?"小额支付":"网银跨行支付";
	        		}
	        		for (int i = 0; i < strings.length; i++) {
	        			row.append(strings[i]);
	        			if((i+1)==strings.length){
	        				row.append("\n");
	        			}else{
	        				row.append("|");
	        			}
					}
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
}
