package com.allinfinance.system.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import org.apache.log4j.Logger;

import com.allinfinance.common.StringUtil;



public class ExcelCreator {

	/**sheetName */
	private String sheetName;
	/**titleName */
	private String titleName;
	/**param */
	private String[] param;
	/**coulmHeader[列名]  list*/
	private String[] coulmHeader;
	/**data[]	 数据结果集 list*/
	private List<Object[]> dataList;
	/**sumData[] sum数据结果集 list*/
	private Object[] sumData;
	
	private WritableCellFormat format1,format2,format3,format4;
	
	/**数据左对齐、右对齐格式*/
	private WritableCellFormat leftDataFormat;
	/**需要左对齐列的数组   例{0,3}*/
	private int[] leftFormat;
	
	private static Logger log = Logger.getLogger(ExcelCreator.class);
	
	private static String font="宋体";
	private static int ROW_HEIGH=402;
//	private static int COLUM_WIDTH=30;

	
	
	public ExcelCreator(String titleName, String[] coulmHeader,
			List<Object[]> dataList) {
		this.titleName = titleName;
		this.coulmHeader = coulmHeader;
		this.dataList = dataList;
	}
	
	/**
	 * 生成报表模板
	 * @param 
	 * sheetName:		sheet[]:{sheetName,titleName}
	 * paramList:		param<参数名 ,参数值>
	 * coulmHeaderList:	coulmHeader[列名]
	 * dataList:		数据集
	 * sumList：			统计数据集（dataList下方）
	 * @return
	 * 2015-06-14
	 * by caotz
	 */
	public  void exportReport(String filePathName) throws Exception {
		
		FileOutputStream outputStream=null; 
		WritableWorkbook workbook=null;

		File pastFile=new  File(filePathName);
		if (pastFile.exists()) {
			pastFile.getAbsoluteFile().delete();
		}
		
		if(!pastFile.getParentFile().exists()){
			pastFile.getParentFile().getAbsoluteFile().mkdirs();
		}
		

		try {
			outputStream= new FileOutputStream(filePathName);
			workbook= Workbook.createWorkbook(outputStream);
			setFormatFont();
			WritableSheet sheet = workbook.createSheet(StringUtil.isEmpty(sheetName)?"Sheet1":sheetName, 0);
			createSheet(sheet);
			workbook.write();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			throw e;
		}finally{
			if(workbook!=null){
				workbook.close();
				workbook=null;
			}
			if(outputStream!=null){
				outputStream.close();
				outputStream=null;
			}
		}
	}
	
	

	private void createSheet(WritableSheet sheet)throws Exception{
		
		/**列表宽度*/
		int coumWidth[]=new int[coulmHeader.length];
		
		/** title */
		int count = 0;
		if(titleName!=null){
			sheet.addCell(new Label(0, count, titleName, format2));
			sheet.mergeCells(0, count, coulmHeader.length-1, count);
			count += 1;
		}
		
		/** param */
		if(param!=null){
//			Iterator<String> iter = mapParm.keySet().iterator();
			int m=0;	
			int l=coulmHeader.length;
			int n;
			for (int i = 0; i < param.length; i++) {
				n=l/(param.length-i)+m;
			    sheet.addCell(new Label(m, count, param[i], format3));
				sheet.mergeCells(m, count, n-1, count);
				l=coulmHeader.length-n;
				m=n;
			}
			sheet.setRowView(count, ROW_HEIGH);
			count += 1;
		}
		
		
		/** coulmHeader */
		for (int j = 0; j < coulmHeader.length; j++) {
			coumWidth[j]=coulmHeader[j].length()+getChineseNum(coulmHeader[j]);
			sheet.addCell(new Label(j, count ,  coulmHeader[j], format4));
		}
		count += 1;
		
		
		/** data */
		Boolean LeftBool=null;
		for (int i = 0; i < dataList.size(); i++) {
			for (int j = 0; j < coulmHeader.length; j++) {
				dataList.get(i)[j]=StringUtil.isNull(dataList.get(i)[j])?"":dataList.get(i)[j].toString();
				/**判断此列是否需要左对齐*/
				LeftBool=false;
				if(leftFormat!=null&&leftFormat.length>0){
					for (int leftCoulm : leftFormat) {
						if(leftCoulm==j){
							LeftBool=true;
							break;
						}
					}
				}
				
				if(LeftBool){
					sheet.addCell(new Label(j, count,dataList.get(i)[j].toString(), leftDataFormat));
				}else{
					sheet.addCell(new Label(j, count,dataList.get(i)[j].toString(), format1));
				}
				
				/**计算此列最长宽度*/
				coumWidth[j]=(dataList.get(i)[j].toString().length()+getChineseNum(dataList.get(i)[j].toString()))>coumWidth[j]?(dataList.get(i)[j].toString().length()+getChineseNum(dataList.get(i)[j].toString())):coumWidth[j];
			}
			count++;
		}
		
		/** sumData */
		if(sumData!=null){
			for (int i = 0; i < sumData.length; i++) {
				sumData[i]=StringUtil.isNull(sumData[i])?"":sumData[i];
				sheet.addCell(new Label(i, count,sumData[i].toString(), format4));
				/**计算此列最长宽度*/
				coumWidth[i]=(sumData[i].toString().length()+getChineseNum(sumData[i].toString()))>coumWidth[i]?(sumData[i].toString().length()+getChineseNum(sumData[i].toString())):coumWidth[i];
			}
			count++;
		}
		
		/**设置宽度*/
		for (int i = 0; i < coumWidth.length; i++) {
//			sheet.setColumnView(i, COLUM_WIDTH);
			if(coumWidth[i]>100){
				sheet.setColumnView(i, 100);
			}else{
				sheet.setColumnView(i, coumWidth[i]+5);
			}
		}
	}
	
	private void setFormatFont()throws Exception{
		/** 内容字体格式 */
		WritableFont font1 = new WritableFont(WritableFont.createFont(font), 10, // 字体大小
				WritableFont.NO_BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线
		/** 标题字体格式 */
		WritableFont font2 = new WritableFont(WritableFont.createFont(font), 16, // 字体大小
				WritableFont.BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线
		/** 参数字体 */
		WritableFont font3 = new WritableFont(WritableFont.createFont(font), 10, // 字体大小
				WritableFont.NO_BOLD, // 加粗
				false, UnderlineStyle.SINGLE);// NO下划线
		/** 列名、统计字体 */
		WritableFont font4 = new WritableFont(WritableFont.createFont(font), 10, // 字体大小
				WritableFont.BOLD, // 加粗
				false, UnderlineStyle.NO_UNDERLINE);// NO下划线

		format1 = new WritableCellFormat(font1);
		format2 = new WritableCellFormat(font2);
		format3 = new WritableCellFormat(font3);
		format4 = new WritableCellFormat(font4);
		/** 加边框 */
		format1.setBorder(Border.ALL, BorderLineStyle.THIN);
		format2.setBorder(Border.ALL, BorderLineStyle.THIN);
		format3.setBorder(Border.ALL, BorderLineStyle.THIN);
		format4.setBorder(Border.ALL, BorderLineStyle.THIN);
		format4.setBackground(Colour.GRAY_25);
		/** 设置居中 */
		format1.setAlignment(Alignment.CENTRE);
		format2.setAlignment(Alignment.CENTRE);
		format3.setAlignment(Alignment.CENTRE);
		format3.setVerticalAlignment(VerticalAlignment.CENTRE);
		format4.setAlignment(Alignment.CENTRE);
		
		/** 左对齐格式，适用数据列*/
		leftDataFormat=new WritableCellFormat(font1);
		leftDataFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		leftDataFormat.setWrap(true);// 自动换行
		leftDataFormat.setAlignment(Alignment.LEFT);
		
		/** 右对齐格式，适用数据列
		rightDataFormat=new WritableCellFormat(font1);
		rightDataFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		leftDataFormat.setWrap(true);// 自动换行
		rightDataFormat.setAlignment(Alignment.LEFT);*/
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

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String[] getParam() {
		return param;
	}

	public void setParam(String[] param) {
		this.param = param;
	}

	public String[] getCoulmHeader() {
		return coulmHeader;
	}

	public void setCoulmHeader(String[] coulmHeader) {
		this.coulmHeader = coulmHeader;
	}

	public List<Object[]> getDataList() {
		return dataList;
	}

	public void setDataList(List<Object[]> dataList) {
		this.dataList = dataList;
	}

	public Object[] getSumData() {
		return sumData;
	}

	public void setSumData(Object[] sumData) {
		this.sumData = sumData;
	}

	public int[] getLeftFormat() {
		return leftFormat;
	}

	public void setLeftFormat(int[] leftFormat) {
		this.leftFormat = leftFormat;
	}


	


	

	
}
