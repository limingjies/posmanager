
package com.allinfinance.system.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;



public class excelReport {

	private static String font="宋体";
//	public static void reportDownloadTxn(List<Object[]> dataList, String[] title,String  fileName,String head,Boolean boo, String amt) throws Exception {
	@SuppressWarnings("unchecked")
	public static void reportDownloadTxn(HashMap<String, Object> map,
			Map<String,String> titleNameMap,Map<String,String[]> titleListMap,Map<String,List<Object[]>> dataListMap) throws Exception {
		String downUrl=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK);
		if (!new File(downUrl).isDirectory()) {// 判断本地存放文件的文件夹是否存在
				new File(downUrl).mkdir();
			}
		/** 标题字体格式 */
		WritableFont font1 = new WritableFont(WritableFont.createFont(font), 10, // 字体大小
				WritableFont.NO_BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线
		/** 内容字体格式 */
		WritableFont font2 = new WritableFont(WritableFont.createFont(font), 15, // 字体大小
				WritableFont.BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线

		WritableCellFormat format1 = new WritableCellFormat(font1);
		WritableCellFormat format2 = new WritableCellFormat(font2);
		/** 加边框 */
		format1.setBorder(Border.ALL, BorderLineStyle.THIN);
		format2.setBorder(Border.ALL, BorderLineStyle.THIN);
		/** 设置居中 */
		format1.setAlignment(Alignment.CENTRE);
		format2.setAlignment(Alignment.CENTRE);
		
		
		FileOutputStream os = new FileOutputStream(map.get("fileName").toString());
		WritableWorkbook workbook = Workbook.createWorkbook(os);
//		String head=map.get("head").toString();
		List<String> list=(List<String>) map.get("list");
		
		for (int m = 0; m < list.size(); m++) {
			WritableSheet sheet = workbook.createSheet(list.get(m).toString(), m);
			for (int i = 0; i < titleListMap.get(list.get(m).toString()).length; i++) {
				sheet.setColumnView(i, 20);
			}
			
			/** 列，行，名，格式 */
			int count = 0;//list.get(m).toString()+"_"+
			sheet.addCell(new Label(0, count, titleNameMap.get(list.get(m).toString()), format2));
			sheet.mergeCells(0, count, titleListMap.get(list.get(m).toString()).length-1, count);
			count += 1;
			
			for (int j = 0; j < titleListMap.get(list.get(m).toString()).length; j++) {
				sheet.addCell(new Label(j, count ,  titleListMap.get(list.get(m).toString())[j], format1));
			}
			
			
			count += 1;
			
			for (int i = 0; i < dataListMap.get(list.get(m).toString()).size(); i++) {
//				sheet.addCell(new Label(0, count, (j + 1) + "", format2));
				for (int j = 0; j < titleListMap.get(list.get(m).toString()).length; j++) {
					sheet.addCell(new Label(j, count,dataListMap.get(list.get(m).toString()).get(i)[j].toString().trim(), format1));
				}
				count++;
			}
		}
		
		
		workbook.write();
		workbook.close();
		os.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void reportDownloadTxnNew(HashMap<String, Object> map) throws Exception {
		String downUrl=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK);
		if (!new File(downUrl).isDirectory()) {// 判断本地存放文件的文件夹是否存在
				new File(downUrl).mkdir();
			}
		FileOutputStream os = new FileOutputStream(map.get("fileName").toString());
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		String head=map.get("head").toString();
		String[] title=(String[]) map.get("title");
		List<Object[]> dataList= (List<Object[]>) map.get("dataList");

		WritableSheet sheet =null;
		if (null!=map.get("sheet") && !"".equals(map.get("sheet"))) {
			String sheetName=map.get("sheet").toString();
			sheet = workbook.createSheet(sheetName, 0);
		}else {
			sheet = workbook.createSheet(head, 0);
		}
		
		
		/** 标题字体格式 */
		WritableFont font1 = new WritableFont(WritableFont.createFont(font), 10, // 字体大小
				WritableFont.NO_BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线
		/** 内容字体格式 */
		WritableFont font2 = new WritableFont(WritableFont.createFont(font), 15, // 字体大小
				WritableFont.BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线

		WritableCellFormat format1 = new WritableCellFormat(font1);
		WritableCellFormat format2 = new WritableCellFormat(font2);
		/** 加边框 */
		format1.setBorder(Border.ALL, BorderLineStyle.THIN);
		format2.setBorder(Border.ALL, BorderLineStyle.THIN);
		/** 设置居中 */
		format1.setAlignment(Alignment.CENTRE);
		format2.setAlignment(Alignment.CENTRE);
		

		for (int i = 0; i < title.length; i++) {
			sheet.setColumnView(i, 20);
		}
		
		/** 列，行，名，格式 */
		int count = 0;
		sheet.addCell(new Label(0, count, head, format2));
		sheet.mergeCells(0, count, title.length-1, count);
		count += 1;
		
		for (int j = 0; j < title.length; j++) {
			sheet.addCell(new Label(j, count , title[j], format1));
		}
		count += 1;
		for (int i = 0; i < dataList.size(); i++) {
			for (int j = 0; j < title.length; j++) {
				sheet.addCell(new Label(j, count,StringUtil.isNotEmpty(dataList.get(i)[j])?dataList.get(i)[j].toString().trim():"", format1));
			}
			count++;
		}
		
		if((Boolean) map.get("isCount")){
			sheet.addCell(new Label(0, count, "交易总笔数：", format1));
			sheet.addCell(new Label(1, count, dataList.size() + "笔", format1));
			count += 1;
			sheet.addCell(new Label(0, count, "交易总金额：", format1));
			sheet.addCell(new Label(1, count, map.get("amtTotal").toString() + "元", format1));
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	// 针对批量录入生成的反馈文件
	@SuppressWarnings("unchecked")
	public static void bulkIptDownload(HashMap<String, Object> map,String downUrl) throws Exception {
		if (!new File(downUrl).isDirectory()) {// 判断本地存放文件的文件夹是否存在
				new File(downUrl).mkdirs();
			}
		FileOutputStream os = new FileOutputStream(map.get("fileName").toString());
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		String head=map.get("head").toString();
		String[] title=(String[]) map.get("title");
		List<String[]> dataList= (List<String[]>) map.get("dataList");
		
		WritableSheet sheet = workbook.createSheet(head, 0);
		
		/** 标题字体格式 */
		WritableFont font1 = new WritableFont(WritableFont.createFont(font), 15, // 字体大小
				WritableFont.BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线
		/** 内容字体格式 */
		WritableFont font2 = new WritableFont(WritableFont.createFont(font), 10, // 字体大小
				WritableFont.NO_BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线
		/** 列名字体 */
		WritableFont font3 = new WritableFont(WritableFont.createFont(font), 10, // 字体大小
				WritableFont.BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线

		WritableCellFormat format1 = new WritableCellFormat(font1);
		WritableCellFormat format2 = new WritableCellFormat(font2);
		WritableCellFormat format3 = new WritableCellFormat(font3);
		/** 加边框 */
		format1.setBorder(Border.ALL, BorderLineStyle.THIN);
		format2.setBorder(Border.ALL, BorderLineStyle.THIN);
		format3.setBorder(Border.ALL, BorderLineStyle.THIN);
		format3.setBackground(Colour.GRAY_25);
		/** 设置居中 */
		format1.setAlignment(Alignment.CENTRE);
		format2.setAlignment(Alignment.CENTRE);
		format3.setAlignment(Alignment.CENTRE);
		format1.setVerticalAlignment(VerticalAlignment.CENTRE);
		format2.setVerticalAlignment(VerticalAlignment.CENTRE);
		format3.setVerticalAlignment(VerticalAlignment.CENTRE);
		/** 设置是否自动换行 */
		format2.setWrap(true);
		
		/** 列，行，名，格式 */
		/**列表宽度*/
		int coumWidth[]=new int[title.length];
		
		/** 设置 head */
		int count = 0;
		sheet.addCell(new Label(0, count, head, format1));
		sheet.mergeCells(0, count, title.length-1, count);
		count += 1;

		/** 设置 title */
		for (int j = 0; j < title.length; j++) {
			coumWidth[j]=title[j].length()+getChineseNum(title[j]);
			sheet.addCell(new Label(j, count , title[j], format3));
		}
		count += 1;

		/** 设置 data */
		String dataString=null;
		String[] retData;
		for (int i = 0; i < dataList.size(); i++) {
			for (int j = 0; j < title.length; j++) {
				dataString=StringUtil.isNotEmpty(dataList.get(i)[j])?dataList.get(i)[j].toString().trim():"";
				if(j == 2||j == 7){
					retData=getFoldData(dataString,"，");
					dataString=retData[0];
					coumWidth[j]=Integer.parseInt(retData[1])>coumWidth[j]?Integer.parseInt(retData[1]):coumWidth[j];
				}else{
					coumWidth[j]=(dataString.length()+getChineseNum(dataString))>coumWidth[j]?(dataString.length()+getChineseNum(dataString)):coumWidth[j];
				}
				sheet.addCell(new Label(j, count,dataString, format2));
			}
			count++;
		}

		/**设置宽度*/
		for (int i = 0; i < title.length; i++) {
			sheet.setColumnView(i, coumWidth[i]+5);
		}
		
		workbook.write();
		workbook.close();
		os.close();
	}
	
	
	/**
	 * 统计context中是汉字的个数
	 * @param context 字符串
	 * @return
	 */
	public static int getChineseNum(String context){    
        int lenOfChinese=0;
        /**汉字的Unicode编码范围*/
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(context);
        while(m.find()){
            lenOfChinese++;
        }
        return lenOfChinese;
    }
	
	/**
	 * 将字符串分行，同时返回长度最大的一行的长度
	 * @param data 等待处理字符串
	 * @param regex 规则符
	 * @return
	 */
	public static String[] getFoldData(String data,String regex){
		String[] retData = new String[2];
		int maxLength = 0;
		if(data.contains(regex)){
			String[] dataList = data.split(regex);
			for(String dataCount : dataList){
				maxLength = (dataCount.length()+getChineseNum(dataCount))>maxLength?(dataCount.length()+getChineseNum(dataCount)):maxLength;
			}
			data=data.replace(regex, "\012");
		}else{
			maxLength = data.length()+getChineseNum(data);
		}
		retData[0] = data;
		retData[1] = String.valueOf(maxLength);
        return retData;
    }
}
