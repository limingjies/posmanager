package com.allinfinance.bo.impl.settle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.allinfinance.bo.settle.T80732BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblInstBalanceAjustDAO;
import com.allinfinance.po.settle.TblInstBalanceAjust;
import com.allinfinance.po.settle.TblInstBalanceAjustPK;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.FileFilter;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.vo.T8073201VO;

/**
 * 差错调账处理业务对象实现类
 * 
 * @author luhq
 *
 */
public class T80732BOTarget implements T80732BO {
	private static Logger log = Logger.getLogger(T80732BOTarget.class);

	/**
	 * 调账记录表操作对象
	 */
	private TblInstBalanceAjustDAO tblInstBalanceAjustDAO;

	/**
	 * 通用表操作对象
	 */
	private ICommQueryDAO commQueryDAO;



	public void setTblInstBalanceAjustDAO(
			TblInstBalanceAjustDAO tblInstBalanceAjustDAO) {
		this.tblInstBalanceAjustDAO = tblInstBalanceAjustDAO;
	}

	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	/**
	 * @param commQueryDAO
	 *            the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	/* 
	 * 手工调账
	 */
	@Override
	public int ajustByHand(T8073201VO t8073201vo,String imgBatchId) {
		TblInstBalanceAjust ajustData = new TblInstBalanceAjust();
		ajustData.setDateSettlmt(t8073201vo.getSettlDate());
		ajustData.setKeyInst(t8073201vo.getKeyInst());
		TblInstBalanceAjustPK ajustPk = new TblInstBalanceAjustPK();
		ajustPk.setDateSettlmt(ajustData.getDateSettlmt());
		ajustPk.setKeyInst(ajustData.getKeyInst());
		ajustData.setTblInstBalanceAjustPK(ajustPk);
		ajustData.setSolveMode("1");	//调账类型：人工调账
		ajustData.setSolveMsg(t8073201vo.getSolveMsg());
		//检查是否已经调账处理过
		TblInstBalanceAjust ajusted = tblInstBalanceAjustDAO.get(ajustPk);
		if (ajusted != null) {
			return 1;
		}
		// 查询获取商户信息
		List list = commQueryDAO.findBySQLQuery("select MCHT_NO,ETPS_ATTR,MCHT_FUNCTION from tbl_mcht_base_inf where MCHT_NO = '"+ t8073201vo.getMerchId()+"' ");
		//add by yww 20160330--手工调账，没有商户信息的也可以操作
		if (list == null || list.isEmpty()) {
			log.error("商户信息不存在，商户号：" + t8073201vo.getMerchId());
			//return 99;
		}else {
			Object[] dataMcht = (Object[]) list.get(0);
			ajustData.setMchtId(StringUtil.null2String(dataMcht[0]));
			ajustData.setMchtType(StringUtil.null2String(dataMcht[1]));
			String funcVal = StringUtil.null2String(dataMcht[2]);	//结算类型
			String funcFlg = funcVal.substring(0, 1);
			if ("0".equals(funcFlg)) {
				ajustData.setSettlDate(funcVal.substring(2, 5));
			} else if ("1".equals(funcFlg)) {
				if (funcVal.startsWith("10")) {//周结
					ajustData.setSettlDate("B01");
				} else if (funcVal.startsWith("11")) {//月结
					ajustData.setSettlDate("C01");
				} else if (funcVal.startsWith("12")) {//季结
					ajustData.setSettlDate("D01");
				} else {
					log.error("非法数据，未知交易类型：" + funcFlg);
					return 99;
				}
			} else {
				log.error("非法数据，未知交易类型：" + funcFlg);
				return 99;
			}
		}
		ajustData.setAcctCurr("156");//人民币
		ajustData.setBrhId(SysParamUtil.getParam("BRH_ID"));//钱宝机构号
		//查询获取对账差错信息
		list = commQueryDAO.findBySQLQuery("select INSERT_DATE,INSERT_TIME,SYS_SEQ_NUM,TXN_NUM, RETRIVL_REF,INST_CODE from BTH_ERR_DTL where DATE_SETTLMT = '"
						+ t8073201vo.getSettlDate()
						+ "' and KEY_INST = '"
						+ t8073201vo.getKeyInst() + "'");
		if (list == null || list.isEmpty()) {
			log.error("对账差错信息不存在，清算日期：" + t8073201vo.getSettlDate() + ",主键:" + t8073201vo.getKeyInst());
			return 99;
		}
		Object[] dataErrDtl = (Object[]) list.get(0);
		ajustData.setTransDate(StringUtil.null2String(dataErrDtl[0]));
		ajustData.setTransTime(StringUtil.null2String(dataErrDtl[1]));
		ajustData.setSysSeqNum(StringUtil.null2String(dataErrDtl[2]));
		ajustData.setTransType(StringUtil.null2String(dataErrDtl[3]));
		ajustData.setTransRef(StringUtil.null2String(dataErrDtl[4]));
		ajustData.setInstCode(StringUtil.null2String(dataErrDtl[5]));
		ajustData.setRecUpdUsrId(t8073201vo.getOprId());
		ajustData.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblInstBalanceAjustDAO.save(ajustData);
		//迁移上传的临时文件
		String basePath = SysParamUtil.getParam(SysParamConstants.ADJUST_OFFLINE_FILE_DISK).replace("\\", "/");
		String filePath = basePath + t8073201vo.getSettlDate() + "_" + t8073201vo.getKeyInst()+ "/";

		// 文件改名移动
		String tmpPath = basePath + "temp/";
		File fl = new File(tmpPath);
		String imagesId = t8073201vo.getSettlDate() + "_" + t8073201vo.getKeyInst() + "_" + t8073201vo.getOprId() + "_" + imgBatchId;
		FileFilter filter = new FileFilter(imagesId);
		File[] files = fl.listFiles(filter);
		if(files != null && files.length > 0){
			File writeFile = new File(filePath);
			if (!writeFile.exists()) {
				writeFile.mkdirs();
			}
			for (File file : files) {
				file.renameTo(new File(filePath + file.getName().replaceAll(imagesId , imgBatchId)));// 文件移动
			}
		}
		return 0;
	}
	
	/**
	 * 人工处理上传文件
	 */	
	public String upload(List<File> imgFile, List<String> imgFileFileName, String settleDate, String keyInst,String oprId,String imgBatchId) {
		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;
		try {
			String fileName = "";
			int fileNameIndex = 0;

			String basePath = SysParamUtil.getParam(SysParamConstants.ADJUST_OFFLINE_FILE_DISK);
			basePath = basePath.replace("\\", "/");
			basePath = basePath + "temp/";
			
			File tempFile = new File(basePath);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar date = Calendar.getInstance();
			date.add(Calendar.DATE, -30);	
			String strMon = sdf.format(date.getTime());
			if(!tempFile.exists()){
				tempFile.mkdirs();
			} else {
				File[] tfiles = tempFile.listFiles();
				if(tfiles != null){
					for(File file: tfiles){
						Date d = new Date(file.lastModified());
						String mon = sdf.format(d);
						if(mon.compareTo(strMon) <= 0){
							file.delete();
						}
					}
				}
			}
			settleDate = settleDate.replaceAll("-", "");
			String preFileName = settleDate + "_" + keyInst + "_" + oprId + "_" + imgBatchId ;
			
			Random random = new Random();
			for (File file : imgFile) {
				is = new FileInputStream(file);
				fileName = imgFileFileName.get(fileNameIndex);
				String fileType = "";
				if (fileName.indexOf(".") != -1) {
					fileType = fileName.substring(fileName.lastIndexOf("."));
				}

				File writeFile = new File(basePath + preFileName + "_" + random.nextInt(999999) + fileType);

				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdirs();
				}
				if (writeFile.exists()) {
					writeFile.delete();
				}
				dis = new DataInputStream(is);
				out = new DataOutputStream(new FileOutputStream(writeFile));

				int temp;
				while ((temp = dis.read()) != -1) {
					out.write(temp);
				}
				fileNameIndex++;
				out.flush();
				out.close();
				dis.close();
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "上传文件失败！";
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != is) {
					is.close();
				}
				if (null != dis) {
					dis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Constants.SUCCESS_CODE;
	}
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.settle.T80732BO#ajust(com.allinfinance.vo.T8073201VO)
	 */
	@Override
	public int ajust(T8073201VO t8073201vo) {
		TblInstBalanceAjust ajustData = new TblInstBalanceAjust();
		ajustData.setDateSettlmt(t8073201vo.getSettlDate());
		ajustData.setKeyInst(t8073201vo.getKeyInst());
		TblInstBalanceAjustPK ajustPk = new TblInstBalanceAjustPK();
		ajustPk.setDateSettlmt(ajustData.getDateSettlmt());
		ajustPk.setKeyInst(ajustData.getKeyInst());
		ajustData.setTblInstBalanceAjustPK(ajustPk);
		ajustData.setSolveMode("0");	//调账类型：系统调账
		//检查是否已经调账处理过
		TblInstBalanceAjust ajusted = tblInstBalanceAjustDAO.get(ajustPk);
		if (ajusted != null) {
			return 1;
		}
		// 查询获取商户信息
		List list = commQueryDAO
				.findBySQLQuery("select MCHT_NO,ETPS_ATTR,MCHT_FUNCTION from tbl_mcht_base_inf where MCHT_NO = "
						+ t8073201vo.getMerchId());
		if (list == null || list.isEmpty()) {
			log.error("商户信息不存在，商户号：" + t8073201vo.getMerchId());
			return 99;
		}
		Object[] dataMcht = (Object[]) list.get(0);
		ajustData.setMchtId(StringUtil.null2String(dataMcht[0]));
		ajustData.setMchtType(StringUtil.null2String(dataMcht[1]));
		String funcVal = StringUtil.null2String(dataMcht[2]);
		String funcFlg = funcVal.substring(0, 1);
		if ("0".equals(funcFlg)) {
			ajustData.setSettlDate(funcVal.substring(2, 5));
		} else if ("1".equals(funcFlg)) {
			if (funcVal.startsWith("10")) {//周结
				ajustData.setSettlDate("B01");
			} else if (funcVal.startsWith("11")) {//月结
				ajustData.setSettlDate("C01");
			} else if (funcVal.startsWith("12")) {//季结
				ajustData.setSettlDate("D01");
			} else {
				log.error("非法数据，未知交易类型：" + funcFlg);
				return 99;
			}
		} else {
			log.error("非法数据，未知交易类型：" + funcFlg);
			return 99;
		}
		ajustData.setAcctCurr("156");//人民币
		ajustData.setBrhId(SysParamUtil.getParam("BRH_ID"));//钱宝机构号
		//查询获取对账差错信息
		list = commQueryDAO
				.findBySQLQuery("select INSERT_DATE,INSERT_TIME,SYS_SEQ_NUM,TXN_NUM, RETRIVL_REF,INST_CODE from BTH_ERR_DTL where DATE_SETTLMT = '"
						+ t8073201vo.getSettlDate()
						+ "' and KEY_INST = '"
						+ t8073201vo.getKeyInst() + "'");
		if (list == null || list.isEmpty()) {
			log.error("对账差错信息不存在，清算日期：" + t8073201vo.getSettlDate() + ",主键:" + t8073201vo.getKeyInst());
			return 99;
		}
		Object[] dataErrDtl = (Object[]) list.get(0);
		ajustData.setTransDate(StringUtil.null2String(dataErrDtl[0]));
		ajustData.setTransTime(StringUtil.null2String(dataErrDtl[1]));
		ajustData.setSysSeqNum(StringUtil.null2String(dataErrDtl[2]));
		ajustData.setTransType(StringUtil.null2String(dataErrDtl[3]));
		ajustData.setTransRef(StringUtil.null2String(dataErrDtl[4]));
		ajustData.setInstCode(StringUtil.null2String(dataErrDtl[5]));
		ajustData.setRecUpdUsrId(t8073201vo.getOprId());
		ajustData.setRecCrtTs(CommonFunction.getCurrentDateTime());
		ajustData.setTransCode("E0030");
		ajustData.setSubTransCode("0");
		
		boolean successRst = false;
		try {
			successRst = sendMessage(ajustData);
		} catch (Exception e) {
			log.error("接口调用失败：" + e);
			return 99;
		}
		
		if (successRst) {
			tblInstBalanceAjustDAO.save(ajustData);
			return 0;
		} else {
			return 99;
		}
	}

	/**
	 * 使用方法：
	 * 1.根据接口请求报文体在MsgEntity中定义工厂方法，返回包含指定字段的报文对象；参照：MsgEntity.genMchtRequestBodyMsg()。
	 * 2.在定义工厂方法时，如果对字段值有特殊约束：（1）、可在DataType中扩展元数据类型；（2）、在Filed类中完成对字段约束的扩展。
	 * 3.完成1和2之后，可以参照如下完成接口的参数设值并调用；
	 * 4.所有的报文字段再工厂方法中都设置了初值，在使用需根据接口报文的具体要求对响应字段设值
	 * 5.异常相关：报文的处理过程中产生的异常信息没有处理，请注意。 
	 * @throws Exception
	 */
	private boolean sendMessage(TblInstBalanceAjust ajustData) throws Exception{
		/*
		 * 1.获取请求报文头，报文体对象；
		 * 2.对报文的响应字段设值；
		 */
		String transCode = "E0030";
		Msg reqBody = MsgEntity.getBalanceAjustMsg();
		Msg reqHead = MsgEntity.genCommonRequestHeadMsg(transCode,"0");
		
		reqHead.getField(1).setValue("00");					//版本号
//		reqHead.getField(2).setValue(transCode);			//交易类型码
		reqHead.getField(3).setValue("0");					//子交易码
//		reqHead.getField(4).setValue(ajustData.getBrhId()); 			//接入机构号;固定值，钱宝机构号
//		reqHead.getField(5).setValue(ajustData.getTransDate());			//接入方交易日期
//		reqHead.getField(6).setValue(ajustData.getSysSeqNum());		//接入方交易流水号
//		reqHead.getField(7).setValue(ajustData.getTransTime());			//接入方交易时间

		reqHead.getField(8).setValue("0000002606");			//接入方交易码
		
//		reqHead.getField(9).setValue(transCode.substring(0, 4) + "123123123123");	//交易类型+接入方检索参考号
		reqHead.getField(10).setValue(ajustData.getBrhId());			//请求方机构号
//		reqHead.getField(11).setValue("");					//请求方交易日期
//		reqHead.getField(12).setValue("");					//请求方交易流水号
		reqHead.getField(13).setValue(ajustData.getBrhId());//开户机构号;固定值，钱宝机构号
//		reqHead.getField(14).setValue("");					//开户机构交易日期
//		reqHead.getField(15).setValue("");					//开户机构交易流水号
//		reqHead.getField(16).setValue("");					//开户机构交易时间
		reqHead.getField(17).setValue(ajustData.getMchtId());	//外部账号
		reqHead.getField(18).setValue("00000002");			//外部账号类型
		reqHead.getField(19).setValue("1");					//内部账号验证标志
//		reqHead.getField(20).setValue("");					//内部账号
//		reqHead.getField(21).setValue("");					//内部账号类型
		reqHead.getField(22).setValue("111111");			//密码域1
		reqHead.getField(23).setValue("111111");			//密码域2
//		reqHead.getField(24).setValue("");					//客户号
		reqHead.getField(25).setValue("+");					//金额符号
//		reqHead.getField(26).setValue("000000100120");		//交	易额
//		reqHead.getField(27).setValue("000000013");			//用户支付手续费
//		reqHead.getField(28).setValue("");					//应答码
		
		
		reqBody.getField(1).setValue(ajustData.getMchtId());//商户号
		reqBody.getField(2).setValue(ajustData.getMchtType());//商户类型
		reqBody.getField(3).setValue(ajustData.getAcctCurr());//交易币种
		reqBody.getField(4).setValue(ajustData.getSettlDate());//结算日
		reqBody.getField(5).setValue(ajustData.getBrhId());//原交易接入方机构号
		reqBody.getField(6).setValue(ajustData.getTransDate());//原交易接入方日期
		reqBody.getField(7).setValue(ajustData.getSysSeqNum());//原交易接入方交易流水号
		reqBody.getField(8).setValue(ajustData.getTransType() + ajustData.getTransRef());//原交易接入方交易类型+原交易接入方检索参考号
		
		/*
		 * 秘钥内容处理：
		 * 暂时不做处理
		 */
		String secretKey = "1111111111111111";
		
		/*
		 * 将报文头，报文体。秘钥组装成一个完整的报文字符串
		 */
		String reqStr = MsgEntity.genCompleteRequestMsg(reqHead, reqBody, secretKey);
		
		/*
		 * 1.记录发送报文日志；
		 * 2.发送报文，并获取响应信息；
		 * 3.记录响应报文日志；
		 */
		if (log.isInfoEnabled()) {
			log.info("发送报文信息：" + reqStr);
		}
		byte[] bufMsg = MsgEntity.sendMessage(reqStr);
		String strRet = new String(bufMsg,"gb2312");
		if (log.isInfoEnabled()) {
			log.info("接收报文信息：" + strRet);
		}
		
		
		/*
		 * 1.获取响应报文头，所有的接口的报文头格式都是相同的，已经定义好了工厂方法。
		 * 2.获取响应报文体，已定义好只有一个字段“响应内容”的报文体；如果响应报文体还有其他字段，需额外定义响应报文体。
		 */
		Msg respHead = MsgEntity.genCommonResponseHeadMsg();
		Msg respBody = MsgEntity.genCommonResponseBodyMsg();
		
		/*
		 * 1. 解析响应报文：将相应报文信息解析后存贮在响应报文头与报文体对象中；
		 * 2. 目前对响应秘钥没有做处理；
		 */
		MsgEntity.parseResponseMsg(bufMsg, respHead, respBody);
		String respCode = respHead.getField(28).getRealValue();
		//将调账交易流水号返回保存
		ajustData.setVcTranNo(reqHead.getField(6).getValue());
		if("0000".equals(respCode)) { //交易成功
			return true;
		} else{
			log.info("调账处理失败：" + respBody.getField(1).getRealValue());
			return false;
		}			

	}	
}