/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-20       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.struts.mchnt.action;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.impl.daytrade.FrontMcht;
import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.FrontConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.LogUtil;

/**
 * Title:商户信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-20
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20101Action extends BaseSupport{
	private T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
	
	/**
	 * 修改商户信息
	 * @return
	 */
	public String update() {
		try {
			TblMchtBaseInfTmp tmp = tblMchntService.getBaseInfTmp(mchtNo);
			TblMchtSettleInfTmp settleTmp = tblMchntService.getSettleInfTmp(mchtNo);
			if (null == tmp || null == settleTmp) {
				return returnService("没有找到指定的商户信息，请重试");
			}
			
			//需要检查
			if (!StringUtil.isNull(checkIds) && checkIds.equals("T")) {
				//首先检验营业执照，税务登记证， 商户账户账号，法人身份证是否已经存在licenceNo faxNo settleAcct identityNo
				//商户账户账号
				String sql = "select B.MCHT_NO,trim(MCHT_NM)," +
					"TRIM(LICENCE_NO),TRIM(FAX_NO),TRIM(IDENTITY_NO),substr(TRIM(SETTLE_ACCT),2) " +
					"from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S where " +
					"(substr(TRIM(SETTLE_ACCT),2) = '" + settleAcct.trim() + "' OR " + 
					" TRIM(IDENTITY_NO) = '" + identityNo.trim() + "' OR " + 
					" TRIM(FAX_NO) = '" + faxNo.trim() + "' OR " + 
					" TRIM(LICENCE_NO) = '" + licenceNo.trim() + "') AND B.MCHT_NO = S.MCHT_NO " +
					" AND TRIM(B.MCHT_NO) != '" + mchtNo.trim() + "' AND TRIM(BANK_NO) = '" + bankNo.trim() + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if (null != list && !list.isEmpty() && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					String reMsg = "CZ存在商户[<font color='red'>" + obj[0] + "-" + obj[1] + "</font>]等" + String.valueOf(list.size()) + "家商户与该商户的";
					if (licenceNo.trim().equals(obj[2])) {
						reMsg += "<font color='green'>营业执照编号</font>";
					} else if (faxNo.trim().equals(obj[3])) {
						reMsg += "<font color='green'>税务登记证号码</font>";
					} else if (identityNo.trim().equals(obj[4])) {
						reMsg += "<font color='green'>法人证件编号</font>";
					} else if (settleAcct.trim().equals(obj[5])) {
						reMsg += "<font color='green'>商户账户账号</font>";
					} else {
						reMsg += "<font color='green'>部分关键信息</font>";
					}
					reMsg += "相同,请核实相关商户录入信息.";
					
					return returnService(reMsg);
				}
			}
			
			String sta = tmp.getMchtStatus();
			
			TblMchtBaseInfTmp tblMchtBaseInfTmp = updateTmpMchtBaseInfo(tmp);
			tblMchtBaseInfTmp.setReserved(idOtherNo);
			tblMchtBaseInfTmp.setRislLvl(tblMchtBaseInfTmp.getRislLvl());
//			tblMchtBaseInfTmp.setMchtLvl("0");
			tblMchtBaseInfTmp.setSaAction("0");
			
			tblMchtBaseInfTmp.setCupMchtFlg("0");
			tblMchtBaseInfTmp.setDebMchtFlg("0");
			tblMchtBaseInfTmp.setCreMchtFlg("0");
			tblMchtBaseInfTmp.setCdcMchtFlg("0");
			
			tblMchtBaseInfTmp.setManagerTel("1");
			
			if (TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(sta) || 
					TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(sta)|| 
					TblMchntInfoConstants.MCHNT_BACK_F.equals(sta)||
					TblMchntInfoConstants.MCHNT_BACK_END.equals(sta)) {
				tmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK);
			} else if(TblMchntInfoConstants.MCHNT_ST_BULK_IPT_UNCK.equals(sta)){
				tmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_BULK_IPT_UNCK);
			} else{
				tmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK);
			}
			
			TblMchtSettleInfTmp tblMchtSettleInfTmp = updateTmpMchtSettleInfo(settleTmp);
			
			if (StringUtil.isNull(settleBankNo)) {
				tblMchtSettleInfTmp.setSettleBankNo(" ");
			}
			tblMchtSettleInfTmp.setFeeDiv1("1");
			tblMchtSettleInfTmp.setFeeDiv2("2");
			if(feeDiv3 != null && !feeDiv3.equals(""))
				tblMchtSettleInfTmp.setFeeDiv3(feeDiv3);
			else
				tblMchtSettleInfTmp.setFeeDiv3("3");

			T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
			TblBrhInfo tblBrhInfo = t10101BO.get(tblMchtBaseInfTmp.getBankNo());
			tblMchtBaseInfTmp.setAcqInstId(tblBrhInfo.getCupBrhId());
			//传送页面营业时间
			tblMchtBaseInfTmp.setCloseTime(CommonFunction.timeFormat(tblMchtBaseInfTmp.getCloseTime()));
			tblMchtBaseInfTmp.setOpenTime(CommonFunction.timeFormat(tblMchtBaseInfTmp.getOpenTime()));
			
			rspCode = tblMchntService.updateTmp(tblMchtBaseInfTmp, tblMchtSettleInfTmp);
			
			LogUtil.log(this.getClass().getSimpleName(), getOperator().getOprId(), "save:" + rspCode, tblMchtBaseInfTmp);
			LogUtil.log(this.getClass().getSimpleName(), getOperator().getOprId(), "save:" + rspCode, tblMchtSettleInfTmp);
			
			
			if (Constants.SUCCESS_CODE.equals(rspCode)) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "修改商户信息成功，商户编号[" + mchtNo + "]");
			} else {
				return rspCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
	}
	
	/**
	 * 冻结商户信息
	 * @return
	 */
	public String stop() {
		
		try {
			TblMchtBaseInfTmp inf = tblMchntService.getBaseInfTmp(mchtNo);
						
			if(inf == null) {
				return returnService("没有找到指定的商户信息");
			}
			if(!TblMchntInfoConstants.MCHNT_ST_OK.equals(inf.getMchtStatus())) {
				return returnService("您所冻结的商户当前不是可冻结状态");
			}
			inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK);
			
			// 记录录入人
			inf.setCrtOprId(getOperator().getOprId());
			inf.setPartNum(exMsg);
			rspCode = tblMchntService.updateBaseInfTmp(inf);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
//	
	/**
	 * 恢复商户信息
	 * @return
	 */
	public String recover() {
		
		try {
			TblMchtBaseInfTmp inf = tblMchntService.getBaseInfTmp(mchtNo);
						
			if(inf == null) {
				return returnService("没有找到指定的商户信息");
			}
			if(!TblMchntInfoConstants.MCHNT_ST_STOP.equals(inf.getMchtStatus())) {
				return returnService("您所恢复的商户当前不是可恢复状态");
			}
			inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_RCV_UNCK);

			// 记录录入人
			inf.setCrtOprId(getOperator().getOprId());
			inf.setPartNum(exMsg);
			rspCode = tblMchntService.updateBaseInfTmp(inf);
			
			return returnService(rspCode);
		} catch (Exception e) {
			return returnService(rspCode, e);
		}
	}
	
	
	/**
	 * 商户信息注销
	 * @return
	 */
	public String del() {
		
		try {
			TblMchtBaseInfTmp inf = tblMchntService.getBaseInfTmp(mchtNo);
						
			if(inf == null) {
				return returnService("没有找到指定的商户信息");
			}
			if(!TblMchntInfoConstants.MCHNT_ST_OK.equals(inf.getMchtStatus()) && !TblMchntInfoConstants.MCHNT_ST_STOP.equals(inf.getMchtStatus())) {
				return returnService("您所注销的商户当前不是可注销状态");
			}
			inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_DEL_UNCK);

			
			// 记录录入人
			inf.setCrtOprId(getOperator().getOprId());
			inf.setPartNum(exMsg);
			rspCode = tblMchntService.updateBaseInfTmp(inf);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	/**
	 * 商户信息批量导入
	 * @return
	 */
	/*public String uploadMchntInfo() {
		
		try {
			
			for(File file : files) {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				
				while(reader.ready()){
					
					String tmp = reader.readLine();
					
					if (!StringUtil.isNull(tmp)) {
						
						String[] data = tmp.split("|");
						mchtNo = data[0]; // 商户号
						mchtNm = data[1]; // 中文名称
						manuAuthFlag = data[2]; // 是否支持人工授权
						discConsFlg = data[3]; // 是否支持折扣消费
						passFlag = data[4]; // 是否支持无磁无密
						mchtCnAbbr = data[5]; // 中文名称简写
						engName = data[6]; // 英文名称
						String settleAreaNo = data[7]; // 所在地区码
						addr = data[8]; // 商户地址
						homePage = data[9]; // 公司网址
						mcc = data[10]; // 商户MCC
						etpsAttr = data[11]; // 企业性质
						data[12]; // 商户组别
						data[13]; // 商户类别
						data[14]; // 商户类型
						data[15]; // 营业执照编号
						data[16]; // 机构代发证号码
						data[17]; // 税务登记证号码
						data[18]; // 注册资金
						data[19]; // 企业资质等级
						data[20]; // 联系人姓名
						data[21]; // 邮政编码
						data[22]; // 电子邮箱
						data[23]; // 联系人电话
						data[24]; // 法人代表
						data[25]; // 法人代表证件类型
						data[26]; // 法人证件号码
						data[27]; // 企业传真
						data[28]; // 企业电话
						data[29]; // 注册地址
						data[30]; // 批准人
						data[31]; // 协议编号
						data[32]; // 受理机构银联号
						data[33]; // 签约网点
						data[34]; // 客户经理电话
						data[35]; // 经营单位
						data[36]; // 客户经理号
						data[37]; // 入账凭单打印机构
						data[38]; // 收单机构
						data[39]; // 营业开始时间
						data[40]; // 营业结束时间
						data[41]; // 商户结算周期
						data[42]; // 是否自动清算
						data[43]; // 商户账户开户行代码
						data[44]; // 商户账户开户行名称
						data[45]; // 商户账户名称
						data[46]; // 商户结算账户类型
						data[47]; // 商户账户账号
						data[48]; // 商户开户行同城清算号
						data[49]; // 商户开户行同城交换号
						data[50]; // 特殊计费类型
						data[51]; // 特殊计费档次
						data[52]; // 特殊计费描述
						data[53]; // 退款返还手续费
						data[54]; // 回佣类型
						data[55]; // 回佣值
						data[56]; // 最低交易金额
						data[57]; // 按比例最低收费
						data[58]; // 按比例最高收费
					}
				}
				reader.close();
			}
			rspCode = "";
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}*/
	
	/**
	 * 构造临时商户清算信息
	 * 
	 * @param request
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private TblMchtSettleInfTmp updateTmpMchtSettleInfo(TblMchtSettleInfTmp settleTmp) throws IllegalAccessException, InvocationTargetException {

	
		BeanUtils.copyProperties(settleTmp, this);
		
		settleTmp.setSettleAcct(clearType.substring(0,1) + settleAcct);
//		settleTmp.setSettleType("1");
		settleTmp.setRateFlag("-");
		settleTmp.setFeeType("3"); //分段收费
		settleTmp.setFeeFixed("-");
		settleTmp.setFeeMaxAmt("0");
		settleTmp.setFeeMinAmt("0");
		
		settleTmp.setFeeDiv1("-");
		settleTmp.setFeeDiv2("-");
		settleTmp.setFeeDiv3("-");
		
		//是否自动清算
		if (!StringUtil.isNull(autoStlFlg)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(autoStlFlg)) {
			settleTmp.setAutoStlFlg("1");
		} else {
			settleTmp.setAutoStlFlg("0");
		}
		//退货是否返还手续费
		if (!StringUtil.isNull(feeBackFlg)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(feeBackFlg)) {
			settleTmp.setFeeBackFlg("1");
		} else {
			settleTmp.setFeeBackFlg("1");
		}
		
		
		settleTmp.setFeeRate(discCode);

		// 记录修改时间
		settleTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());

		settleTmp.setSettleAcctMid(settleAcctMid);
		
		return settleTmp;
	}
	
	/**
	 * 构造临时商户信息
	 * 
	 * @param request
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private TblMchtBaseInfTmp updateTmpMchtBaseInfo(TblMchtBaseInfTmp tmp) throws IllegalAccessException, InvocationTargetException {

//		String mf = updateMchtFunction(tmp.getMchtFunction());
		BeanUtils.copyProperties(tmp, this);
//		tmp.setMchtFunction(mf);
		
		tmp.setBankNo(bankNo);
		//是否支持无磁无密交易
		if (!StringUtil.isNull(passFlag) 
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(passFlag)) {
			tmp.setPassFlag("1");
		} else {
			tmp.setPassFlag("0");
		}
		//是否支持人工授权交易
		if (!StringUtil.isNull(manuAuthFlag) 
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(manuAuthFlag)) {
			tmp.setManuAuthFlag("1");
		} else {
			tmp.setManuAuthFlag("0");
		}
		//是否支持折扣消费
		if (!StringUtil.isNull(discConsFlg) 
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(discConsFlg)) {
			tmp.setDiscConsFlg("1");
		} else {
			tmp.setDiscConsFlg("0");
		}
		//是否仅营业时间内交易
		if (!StringUtil.isNull(mchtMngMode)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(mchtMngMode)) {
			tmp.setMchtMngMode("1");
		} else {
			tmp.setMchtMngMode("0");
		}
		
		//申请日期
		tmp.setApplyDate(CommonFunction.getCurrentDate());
		
		// 记录修改时间
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录录入人
		tmp.setCrtOprId(getOperator().getOprId());
		
		//tmp.setLicenceEndDate("-");
		
		tmp.setEngName(engName.length()>40?"":engName);
		
		return tmp;
		
	}
	
	/**
	 * 更新账户相关信息
	 * @param tmp
	 * @return
	 
	private String updateMchtFunction(String mf){
		// 判断商户是否在账户系统开户
		if(mf == null || "".equals(mf)){ // 无账户相关信息
			mf = FrontMcht.ACCT_CREATE_N + ((mchtFunction == null || "".equals(mchtFunction))?FrontMcht.ACCT_TYPE_1:mchtFunction);
		}else{
			mf = mf.substring(0, 1) + ((mchtFunction == null || "".equals(mchtFunction))?mf.substring(1, 2):mchtFunction);
		}
		return mf;
	}*/
	
	
	public String upload(){
		
		
		rspCode=t20901BO.upload(imgFile,imgFileFileName,imagesId,mchtNo,null);
		return returnService(rspCode);
		/*FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;
		
		try {
			String fileName = "";
			int fileNameIndex = 0;
			
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			
			basePath = basePath.replace("\\", "/");
			
			Random random = new Random();
			
			for(File file : imgFile) {
				is = new FileInputStream(file);
				fileName = imgFileFileName.get(fileNameIndex);
				String fileType ="";
				if (fileName.indexOf(".") != -1) {
					fileType = fileName.substring(fileName.lastIndexOf("."));
				}
				
				File writeFile = new File(basePath + imagesId + random.nextInt(999999) + fileType);
				
				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdir();
				}
				if (writeFile.exists()) {
					writeFile.delete();
				}
				dis = new DataInputStream(is);
				out = new DataOutputStream(new FileOutputStream(writeFile));

				int temp;
				while((temp = dis.read()) != -1){
					out.write(temp);
				}

				out.flush();
				out.close();
				dis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("上传文件失败！", e);
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
		return returnService(Constants.SUCCESS_CODE);*/
	}
	
	/**
	 * @return the imgFile
	 */
	public List<File> getImgFile() {
		return imgFile;
	}

	/**
	 * @param imgFile the imgFile to set
	 */
	public void setImgFile(List<File> imgFile) {
		this.imgFile = imgFile;
	}

	/**
	 * @return the imgFileFileName
	 */
	public List<String> getImgFileFileName() {
		return imgFileFileName;
	}

	/**
	 * @param imgFileFileName the imgFileFileName to set
	 */
	public void setImgFileFileName(List<String> imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	/**
	 * @return the imagesId
	 */
	public String getImagesId() {
		return imagesId;
	}

	/**
	 * @param imagesId the imagesId to set
	 */
	public void setImagesId(String imagesId) {
		this.imagesId = imagesId;
	}
	
	// 文件集合
	private List<File> imgFile;
	// 文件名称集合
	private List<String> imgFileFileName;
	private String imagesId;
	
	// primary key
	private java.lang.String mchtNo;

	// fields
	private java.lang.String mchtNm;
	private java.lang.String rislLvl;
//	private java.lang.String mchtLvl;
	private java.lang.String mchtStatus;
	private java.lang.String manuAuthFlag;
	private java.lang.String partNum;
	private java.lang.String discConsFlg;
	private java.lang.String discConsRebate;
	private java.lang.String passFlag;
	private java.lang.String openDays;
	private java.lang.String sleepDays;
	private java.lang.String mchtCnAbbr;
	private java.lang.String spellName;
	private java.lang.String engName;
	private java.lang.String mchtEnAbbr;
//	因修改时不需修改此字段，故注掉
//	private java.lang.String areaNo;
//	private java.lang.String settleAreaNo;
	private java.lang.String addr;
	private java.lang.String homePage;
	private java.lang.String mcc;
	private java.lang.String tcc;
	private java.lang.String etpsAttr;
	private java.lang.String mngMchtId;
	private java.lang.String mchtGrp;
	private java.lang.String mchtAttr;
//	private java.lang.String mchtGroupFlag;
	private java.lang.String mchtGroupId;
	private java.lang.String mchtEngNm;
	private java.lang.String mchtEngAddr;
	private java.lang.String mchtEngCityName;
	private java.lang.String saLimitAmt;
	private java.lang.String saAction;
	private java.lang.String psamNum;
	private java.lang.String cdMacNum;
	private java.lang.String posNum;
	private java.lang.String connType;
	private java.lang.String mchtMngMode;
	private java.lang.String mchtFunction;
	private java.lang.String licenceNo;
	private java.lang.String licenceEndDate;
	private java.lang.String bankLicenceNo;
	private java.lang.String busType;
	private java.lang.String faxNo;
	private java.lang.String busAmt;
	private java.lang.String mchtCreLvl;
	private java.lang.String contact;
	private java.lang.String postCode;
	private java.lang.String commEmail;
	private java.lang.String commMobil;
	private java.lang.String commTel;
	private java.lang.String manager;
	private java.lang.String artifCertifTp;
	private java.lang.String identityNo;
	private java.lang.String managerTel;
	private java.lang.String fax;
	private java.lang.String electrofax;
	private java.lang.String regAddr;
	private java.lang.String applyDate;
	private java.lang.String enableDate;
	private java.lang.String preAudNm;
	private java.lang.String confirmNm;
	private java.lang.String protocalId;
	private java.lang.String signInstId;
	private java.lang.String netNm;
	private java.lang.String agrBr;
	private java.lang.String netTel;
	private java.lang.String prolDate;
	private java.lang.String prolTlr;
	private java.lang.String closeDate;
	private java.lang.String closeTlr;
	private java.lang.String mainTlr;
	private java.lang.String checkTlr;
	private java.lang.String operNo;
	private java.lang.String operNm;
	private java.lang.String procFlag;
	private java.lang.String setCur;
	private java.lang.String printInstId;
	private java.lang.String acqInstId;
	private java.lang.String acqBkName;
	private java.lang.String bankNo;
	private java.lang.String orgnNo;
	private java.lang.String subbrhNo;
	private java.lang.String subbrhNm;
	private java.lang.String openTime;
	private java.lang.String closeTime;
	private java.lang.String visActFlg;
	private java.lang.String visMchtId;
	private java.lang.String mstActFlg;
	private java.lang.String mstMchtId;
	private java.lang.String amxActFlg;
	private java.lang.String amxMchtId;
	private java.lang.String dnrActFlg;
	private java.lang.String dnrMchtId;
	private java.lang.String jcbActFlg;
	private java.lang.String jcbMchtId;
	private java.lang.String cupMchtFlg;
	private java.lang.String debMchtFlg;
	private java.lang.String creMchtFlg;
	private java.lang.String cdcMchtFlg;
	
//	private java.lang.String settleType;
	private java.lang.String clearType;
	/**
	 * @return the clearType
	 */
	public java.lang.String getClearType() {
		return clearType;
	}

	/**
	 * @param clearType the clearType to set
	 */
	public void setClearType(java.lang.String clearType) {
		this.clearType = clearType;
	}

	private java.lang.String rateFlag;
	private java.lang.String settleChn;
	private java.lang.String batTime;
	private java.lang.String autoStlFlg;
	private java.lang.String feeType;
	private java.lang.String feeFixed;
	private java.lang.String feeMaxAmt;
	private java.lang.String feeMinAmt;
	private java.lang.String feeRate;
	private java.lang.String feeDiv1;
	private java.lang.String feeDiv2;
	private java.lang.String feeDiv3;


	public java.lang.String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public java.lang.String getMchtNm() {
		return mchtNm;
	}

	public void setMchtNm(java.lang.String mchtNm) {
		this.mchtNm = mchtNm;
	}

	public java.lang.String getRislLvl() {
		return rislLvl;
	}

	public void setRislLvl(java.lang.String rislLvl) {
		this.rislLvl = rislLvl;
	}

//	public java.lang.String getMchtLvl() {
//		return mchtLvl;
//	}
//
//	public void setMchtLvl(java.lang.String mchtLvl) {
//		this.mchtLvl = mchtLvl;
//	}

	public java.lang.String getMchtStatus() {
		return mchtStatus;
	}

	public void setMchtStatus(java.lang.String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}

	public java.lang.String getManuAuthFlag() {
		return manuAuthFlag;
	}

	public void setManuAuthFlag(java.lang.String manuAuthFlag) {
		this.manuAuthFlag = manuAuthFlag;
	}

	public java.lang.String getDiscConsFlg() {
		return discConsFlg;
	}

	public void setDiscConsFlg(java.lang.String discConsFlg) {
		this.discConsFlg = discConsFlg;
	}

	public java.lang.String getDiscConsRebate() {
		return discConsRebate;
	}

	public void setDiscConsRebate(java.lang.String discConsRebate) {
		this.discConsRebate = discConsRebate;
	}

	public java.lang.String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(java.lang.String passFlag) {
		this.passFlag = passFlag;
	}

	public java.lang.String getOpenDays() {
		return openDays;
	}

	public void setOpenDays(java.lang.String openDays) {
		this.openDays = openDays;
	}

	public java.lang.String getSleepDays() {
		return sleepDays;
	}

	public void setSleepDays(java.lang.String sleepDays) {
		this.sleepDays = sleepDays;
	}

	public java.lang.String getMchtCnAbbr() {
		return mchtCnAbbr;
	}

	public void setMchtCnAbbr(java.lang.String mchtCnAbbr) {
		this.mchtCnAbbr = mchtCnAbbr;
	}

	public java.lang.String getSpellName() {
		return spellName;
	}

	public void setSpellName(java.lang.String spellName) {
		this.spellName = spellName;
	}

	public java.lang.String getEngName() {
		return engName;
	}

	public void setEngName(java.lang.String engName) {
		this.engName = engName;
	}

	public java.lang.String getMchtEnAbbr() {
		return mchtEnAbbr;
	}

	public void setMchtEnAbbr(java.lang.String mchtEnAbbr) {
		this.mchtEnAbbr = mchtEnAbbr;
	}

	public java.lang.String getAddr() {
		return addr;
	}

	public void setAddr(java.lang.String addr) {
		this.addr = addr;
	}

	public java.lang.String getHomePage() {
		return homePage;
	}

	public void setHomePage(java.lang.String homePage) {
		this.homePage = homePage;
	}

	public java.lang.String getMcc() {
		return mcc;
	}

	public void setMcc(java.lang.String mcc) {
		this.mcc = mcc;
	}

	public java.lang.String getTcc() {
		return tcc;
	}

	public void setTcc(java.lang.String tcc) {
		this.tcc = tcc;
	}

	public java.lang.String getEtpsAttr() {
		return etpsAttr;
	}

	public void setEtpsAttr(java.lang.String etpsAttr) {
		this.etpsAttr = etpsAttr;
	}

	public java.lang.String getMngMchtId() {
		return mngMchtId;
	}

	public void setMngMchtId(java.lang.String mngMchtId) {
		this.mngMchtId = mngMchtId;
	}

	public java.lang.String getMchtGrp() {
		return mchtGrp;
	}

	public void setMchtGrp(java.lang.String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public java.lang.String getMchtAttr() {
		return mchtAttr;
	}

	public void setMchtAttr(java.lang.String mchtAttr) {
		this.mchtAttr = mchtAttr;
	}

//	public java.lang.String getMchtGroupFlag() {
//		return mchtGroupFlag;
//	}
//
//	public void setMchtGroupFlag(java.lang.String mchtGroupFlag) {
//		this.mchtGroupFlag = mchtGroupFlag;
//	}

	public java.lang.String getMchtGroupId() {
		return mchtGroupId;
	}

	public void setMchtGroupId(java.lang.String mchtGroupId) {
		this.mchtGroupId = mchtGroupId;
	}

	public java.lang.String getMchtEngNm() {
		return mchtEngNm;
	}

	public void setMchtEngNm(java.lang.String mchtEngNm) {
		this.mchtEngNm = mchtEngNm;
	}

	public java.lang.String getMchtEngAddr() {
		return mchtEngAddr;
	}

	public void setMchtEngAddr(java.lang.String mchtEngAddr) {
		this.mchtEngAddr = mchtEngAddr;
	}

	public java.lang.String getMchtEngCityName() {
		return mchtEngCityName;
	}

	public void setMchtEngCityName(java.lang.String mchtEngCityName) {
		this.mchtEngCityName = mchtEngCityName;
	}

	public java.lang.String getSaLimitAmt() {
		return saLimitAmt;
	}

	public void setSaLimitAmt(java.lang.String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	public java.lang.String getSaAction() {
		return saAction;
	}

	public void setSaAction(java.lang.String saAction) {
		this.saAction = saAction;
	}

	public java.lang.String getPsamNum() {
		return psamNum;
	}

	public void setPsamNum(java.lang.String psamNum) {
		this.psamNum = psamNum;
	}

	public java.lang.String getCdMacNum() {
		return cdMacNum;
	}

	public void setCdMacNum(java.lang.String cdMacNum) {
		this.cdMacNum = cdMacNum;
	}

	public java.lang.String getPosNum() {
		return posNum;
	}

	public void setPosNum(java.lang.String posNum) {
		this.posNum = posNum;
	}

	public java.lang.String getConnType() {
		return connType;
	}

	public void setConnType(java.lang.String connType) {
		this.connType = connType;
	}

	public java.lang.String getMchtMngMode() {
		return mchtMngMode;
	}

	public void setMchtMngMode(java.lang.String mchtMngMode) {
		this.mchtMngMode = mchtMngMode;
	}

	public java.lang.String getMchtFunction() {
		return mchtFunction;
	}

	public void setMchtFunction(java.lang.String mchtFunction) {
		this.mchtFunction = mchtFunction;
	}

	public java.lang.String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(java.lang.String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public java.lang.String getLicenceEndDate() {
		return licenceEndDate;
	}

	public void setLicenceEndDate(java.lang.String licenceEndDate) {
		this.licenceEndDate = licenceEndDate;
	}

	public java.lang.String getBankLicenceNo() {
		return bankLicenceNo;
	}

	public void setBankLicenceNo(java.lang.String bankLicenceNo) {
		this.bankLicenceNo = bankLicenceNo;
	}

	public java.lang.String getBusType() {
		return busType;
	}

	public void setBusType(java.lang.String busType) {
		this.busType = busType;
	}

	public java.lang.String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(java.lang.String faxNo) {
		this.faxNo = faxNo;
	}

	public java.lang.String getBusAmt() {
		return busAmt;
	}

	public void setBusAmt(java.lang.String busAmt) {
		this.busAmt = busAmt;
	}

	public java.lang.String getMchtCreLvl() {
		return mchtCreLvl;
	}

	public void setMchtCreLvl(java.lang.String mchtCreLvl) {
		this.mchtCreLvl = mchtCreLvl;
	}

	public java.lang.String getContact() {
		return contact;
	}

	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	public java.lang.String getPostCode() {
		return postCode;
	}

	public void setPostCode(java.lang.String postCode) {
		this.postCode = postCode;
	}

	public java.lang.String getCommEmail() {
		return commEmail;
	}

	public void setCommEmail(java.lang.String commEmail) {
		this.commEmail = commEmail;
	}

	public java.lang.String getCommMobil() {
		return commMobil;
	}

	public void setCommMobil(java.lang.String commMobil) {
		this.commMobil = commMobil;
	}

	public java.lang.String getCommTel() {
		return commTel;
	}

	public void setCommTel(java.lang.String commTel) {
		this.commTel = commTel;
	}

	public java.lang.String getManager() {
		return manager;
	}

	public void setManager(java.lang.String manager) {
		this.manager = manager;
	}

	public java.lang.String getArtifCertifTp() {
		return artifCertifTp;
	}

	public void setArtifCertifTp(java.lang.String artifCertifTp) {
		this.artifCertifTp = artifCertifTp;
	}

	public java.lang.String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(java.lang.String identityNo) {
		this.identityNo = identityNo;
	}

	public java.lang.String getManagerTel() {
		return managerTel;
	}

	public void setManagerTel(java.lang.String managerTel) {
		this.managerTel = managerTel;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getElectrofax() {
		return electrofax;
	}

	public void setElectrofax(java.lang.String electrofax) {
		this.electrofax = electrofax;
	}

	public java.lang.String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(java.lang.String regAddr) {
		this.regAddr = regAddr;
	}

	public java.lang.String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(java.lang.String applyDate) {
		this.applyDate = applyDate;
	}

	public java.lang.String getEnableDate() {
		return enableDate;
	}

	public void setEnableDate(java.lang.String enableDate) {
		this.enableDate = enableDate;
	}

	public java.lang.String getPreAudNm() {
		return preAudNm;
	}

	public void setPreAudNm(java.lang.String preAudNm) {
		this.preAudNm = preAudNm;
	}

	public java.lang.String getConfirmNm() {
		return confirmNm;
	}

	public void setConfirmNm(java.lang.String confirmNm) {
		this.confirmNm = confirmNm;
	}

	public java.lang.String getProtocalId() {
		return protocalId;
	}

	public void setProtocalId(java.lang.String protocalId) {
		this.protocalId = protocalId;
	}

	public java.lang.String getSignInstId() {
		return signInstId;
	}

	public void setSignInstId(java.lang.String signInstId) {
		this.signInstId = signInstId;
	}

	public java.lang.String getNetNm() {
		return netNm;
	}

	public void setNetNm(java.lang.String netNm) {
		this.netNm = netNm;
	}

	public java.lang.String getAgrBr() {
		return agrBr;
	}

	public void setAgrBr(java.lang.String agrBr) {
		this.agrBr = agrBr;
	}

	public java.lang.String getNetTel() {
		return netTel;
	}

	public void setNetTel(java.lang.String netTel) {
		this.netTel = netTel;
	}

	public java.lang.String getProlDate() {
		return prolDate;
	}

	public void setProlDate(java.lang.String prolDate) {
		this.prolDate = prolDate;
	}

	public java.lang.String getProlTlr() {
		return prolTlr;
	}

	public void setProlTlr(java.lang.String prolTlr) {
		this.prolTlr = prolTlr;
	}

	public java.lang.String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.lang.String closeDate) {
		this.closeDate = closeDate;
	}

	public java.lang.String getCloseTlr() {
		return closeTlr;
	}

	public void setCloseTlr(java.lang.String closeTlr) {
		this.closeTlr = closeTlr;
	}

	public java.lang.String getMainTlr() {
		return mainTlr;
	}

	public void setMainTlr(java.lang.String mainTlr) {
		this.mainTlr = mainTlr;
	}

	public java.lang.String getCheckTlr() {
		return checkTlr;
	}

	public void setCheckTlr(java.lang.String checkTlr) {
		this.checkTlr = checkTlr;
	}

	public java.lang.String getOperNo() {
		return operNo;
	}

	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo;
	}

	public java.lang.String getOperNm() {
		return operNm;
	}

	public void setOperNm(java.lang.String operNm) {
		this.operNm = operNm;
	}

	public java.lang.String getProcFlag() {
		return procFlag;
	}

	public void setProcFlag(java.lang.String procFlag) {
		this.procFlag = procFlag;
	}

	public java.lang.String getSetCur() {
		return setCur;
	}

	public void setSetCur(java.lang.String setCur) {
		this.setCur = setCur;
	}

	public java.lang.String getPrintInstId() {
		return printInstId;
	}

	public void setPrintInstId(java.lang.String printInstId) {
		this.printInstId = printInstId;
	}

	public java.lang.String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(java.lang.String acqInstId) {
		this.acqInstId = acqInstId;
	}

	public java.lang.String getAcqBkName() {
		return acqBkName;
	}

	public void setAcqBkName(java.lang.String acqBkName) {
		this.acqBkName = acqBkName;
	}

	public java.lang.String getBankNo() {
		return bankNo;
	}

	public void setBankNo(java.lang.String bankNo) {
		this.bankNo = bankNo;
	}

	public java.lang.String getOrgnNo() {
		return orgnNo;
	}

	public void setOrgnNo(java.lang.String orgnNo) {
		this.orgnNo = orgnNo;
	}

	public java.lang.String getSubbrhNo() {
		return subbrhNo;
	}

	public void setSubbrhNo(java.lang.String subbrhNo) {
		this.subbrhNo = subbrhNo;
	}

	public java.lang.String getSubbrhNm() {
		return subbrhNm;
	}

	public void setSubbrhNm(java.lang.String subbrhNm) {
		this.subbrhNm = subbrhNm;
	}

	public java.lang.String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(java.lang.String openTime) {
		this.openTime = openTime;
	}

	public java.lang.String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(java.lang.String closeTime) {
		this.closeTime = closeTime;
	}

	public java.lang.String getVisActFlg() {
		return visActFlg;
	}

	public void setVisActFlg(java.lang.String visActFlg) {
		this.visActFlg = visActFlg;
	}

	public java.lang.String getVisMchtId() {
		return visMchtId;
	}

	public void setVisMchtId(java.lang.String visMchtId) {
		this.visMchtId = visMchtId;
	}

	public java.lang.String getMstActFlg() {
		return mstActFlg;
	}

	public void setMstActFlg(java.lang.String mstActFlg) {
		this.mstActFlg = mstActFlg;
	}

	public java.lang.String getMstMchtId() {
		return mstMchtId;
	}

	public void setMstMchtId(java.lang.String mstMchtId) {
		this.mstMchtId = mstMchtId;
	}

	public java.lang.String getAmxActFlg() {
		return amxActFlg;
	}

	public void setAmxActFlg(java.lang.String amxActFlg) {
		this.amxActFlg = amxActFlg;
	}

	public java.lang.String getAmxMchtId() {
		return amxMchtId;
	}

	public void setAmxMchtId(java.lang.String amxMchtId) {
		this.amxMchtId = amxMchtId;
	}

	public java.lang.String getDnrActFlg() {
		return dnrActFlg;
	}

	public void setDnrActFlg(java.lang.String dnrActFlg) {
		this.dnrActFlg = dnrActFlg;
	}

	public java.lang.String getDnrMchtId() {
		return dnrMchtId;
	}

	public void setDnrMchtId(java.lang.String dnrMchtId) {
		this.dnrMchtId = dnrMchtId;
	}

	public java.lang.String getJcbActFlg() {
		return jcbActFlg;
	}

	public void setJcbActFlg(java.lang.String jcbActFlg) {
		this.jcbActFlg = jcbActFlg;
	}

	public java.lang.String getJcbMchtId() {
		return jcbMchtId;
	}

	public void setJcbMchtId(java.lang.String jcbMchtId) {
		this.jcbMchtId = jcbMchtId;
	}

	public java.lang.String getCupMchtFlg() {
		return cupMchtFlg;
	}

	public void setCupMchtFlg(java.lang.String cupMchtFlg) {
		this.cupMchtFlg = cupMchtFlg;
	}

	public java.lang.String getDebMchtFlg() {
		return debMchtFlg;
	}

	public void setDebMchtFlg(java.lang.String debMchtFlg) {
		this.debMchtFlg = debMchtFlg;
	}

	public java.lang.String getCreMchtFlg() {
		return creMchtFlg;
	}

	public void setCreMchtFlg(java.lang.String creMchtFlg) {
		this.creMchtFlg = creMchtFlg;
	}

	public java.lang.String getCdcMchtFlg() {
		return cdcMchtFlg;
	}

	public void setCdcMchtFlg(java.lang.String cdcMchtFlg) {
		this.cdcMchtFlg = cdcMchtFlg;
	}


	public java.lang.String getRateFlag() {
		return rateFlag;
	}

	public void setRateFlag(java.lang.String rateFlag) {
		this.rateFlag = rateFlag;
	}

	public java.lang.String getSettleChn() {
		return settleChn;
	}

	public void setSettleChn(java.lang.String settleChn) {
		this.settleChn = settleChn;
	}

	public java.lang.String getBatTime() {
		return batTime;
	}

	public void setBatTime(java.lang.String batTime) {
		this.batTime = batTime;
	}

	public java.lang.String getAutoStlFlg() {
		return autoStlFlg;
	}

	public void setAutoStlFlg(java.lang.String autoStlFlg) {
		this.autoStlFlg = autoStlFlg;
	}

	public java.lang.String getPartNum() {
		return partNum;
	}

	public void setPartNum(java.lang.String partNum) {
		this.partNum = partNum;
	}

	public java.lang.String getFeeType() {
		return feeType;
	}

	public void setFeeType(java.lang.String feeType) {
		this.feeType = feeType;
	}

	public java.lang.String getFeeFixed() {
		return feeFixed;
	}

	public void setFeeFixed(java.lang.String feeFixed) {
		this.feeFixed = feeFixed;
	}

	public java.lang.String getFeeMaxAmt() {
		return feeMaxAmt;
	}

	public void setFeeMaxAmt(java.lang.String feeMaxAmt) {
		this.feeMaxAmt = feeMaxAmt;
	}

	public java.lang.String getFeeMinAmt() {
		return feeMinAmt;
	}

	public void setFeeMinAmt(java.lang.String feeMinAmt) {
		this.feeMinAmt = feeMinAmt;
	}

	public java.lang.String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(java.lang.String feeRate) {
		this.feeRate = feeRate;
	}

	public java.lang.String getFeeDiv1() {
		return feeDiv1;
	}

	public void setFeeDiv1(java.lang.String feeDiv1) {
		this.feeDiv1 = feeDiv1;
	}

	public java.lang.String getFeeDiv2() {
		return feeDiv2;
	}

	public void setFeeDiv2(java.lang.String feeDiv2) {
		this.feeDiv2 = feeDiv2;
	}

	public java.lang.String getFeeDiv3() {
		return feeDiv3;
	}

	public void setFeeDiv3(java.lang.String feeDiv3) {
		this.feeDiv3 = feeDiv3;
	}

	public java.lang.String getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(java.lang.String settleMode) {
		this.settleMode = settleMode;
	}

	public java.lang.String getFeeCycle() {
		return feeCycle;
	}

	public void setFeeCycle(java.lang.String feeCycle) {
		this.feeCycle = feeCycle;
	}

	public java.lang.String getSettleRpt() {
		return settleRpt;
	}

	public void setSettleRpt(java.lang.String settleRpt) {
		this.settleRpt = settleRpt;
	}

	public java.lang.String getSettleBankNo() {
		return settleBankNo;
	}

	public void setSettleBankNo(java.lang.String settleBankNo) {
		this.settleBankNo = settleBankNo;
	}

	public java.lang.String getSettleBankNm() {
		return settleBankNm;
	}

	public void setSettleBankNm(java.lang.String settleBankNm) {
		this.settleBankNm = settleBankNm;
	}

	public java.lang.String getSettleAcctNm() {
		return settleAcctNm;
	}

	public void setSettleAcctNm(java.lang.String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}

	public java.lang.String getSettleAcct() {
		return settleAcct;
	}

	public void setSettleAcct(java.lang.String settleAcct) {
		
		this.settleAcct = settleAcct;
	}

	public java.lang.String getFeeAcctNm() {
		return feeAcctNm;
	}

	public void setFeeAcctNm(java.lang.String feeAcctNm) {
		this.feeAcctNm = feeAcctNm;
	}

	public java.lang.String getFeeAcct() {
		return feeAcct;
	}

	public void setFeeAcct(java.lang.String feeAcct) {
		this.feeAcct = feeAcct;
	}

	public java.lang.String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(java.lang.String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public java.lang.String getOpenStlno() {
		return openStlno;
	}

	public void setOpenStlno(java.lang.String openStlno) {
		this.openStlno = openStlno;
	}

	public java.lang.String getChangeStlno() {
		return changeStlno;
	}

	public void setChangeStlno(java.lang.String changeStlno) {
		this.changeStlno = changeStlno;
	}

	public java.lang.String getReserved() {
		return reserved;
	}

	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}

	private java.lang.String settleMode;
	private java.lang.String feeCycle;
	private java.lang.String settleRpt;
	private java.lang.String settleBankNo;
	private java.lang.String settleBankNm;
	private java.lang.String settleAcctNm;
	private java.lang.String settleAcct;
	private java.lang.String feeAcctNm;
	private java.lang.String feeAcct;
	private java.lang.String groupFlag;
	private java.lang.String openStlno;
	private java.lang.String changeStlno;
	private java.lang.String reserved;
	private java.lang.String exMsg;
	
	private String discCode;
	
	
	public String getDiscCode() {
		return discCode;
	}
	
	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}

	/**
	 * @return the exMsg
	 */
	public java.lang.String getExMsg() {
		return exMsg;
	}

	/**
	 * @param exMsg the exMsg to set
	 */
	public void setExMsg(java.lang.String exMsg) {
		this.exMsg = exMsg;
	}
	private java.lang.String speSettleTp;
	private java.lang.String speSettleLv;
	private java.lang.String speSettleDs;
	private java.lang.String feeBackFlg;

	/**
	 * @return the speSettleTp
	 */
	public java.lang.String getSpeSettleTp() {
		return speSettleTp;
	}

	/**
	 * @param speSettleTp the speSettleTp to set
	 */
	public void setSpeSettleTp(java.lang.String speSettleTp) {
		this.speSettleTp = speSettleTp;
	}

	/**
	 * @return the speSettleLv
	 */
	public java.lang.String getSpeSettleLv() {
		return speSettleLv;
	}

	/**
	 * @param speSettleLv the speSettleLv to set
	 */
	public void setSpeSettleLv(java.lang.String speSettleLv) {
		this.speSettleLv = speSettleLv;
	}

	/**
	 * @return the speSettleDs
	 */
	public java.lang.String getSpeSettleDs() {
		return speSettleDs;
	}

	/**
	 * @param speSettleDs the speSettleDs to set
	 */
	public void setSpeSettleDs(java.lang.String speSettleDs) {
		this.speSettleDs = speSettleDs;
	}

	/**
	 * @return the feeBackFlg
	 */
	public java.lang.String getFeeBackFlg() {
		return feeBackFlg;
	}

	/**
	 * @param feeBackFlg the feeBackFlg to set
	 */
	public void setFeeBackFlg(java.lang.String feeBackFlg) {
		this.feeBackFlg = feeBackFlg;
	}
	
	private String idOtherNo;
	private String feeTypeFlag;
	private String feeSelfGDName;
	private String feeSelfBLName;
	private String feeMost;
	private String feeLeast;

	public String getFeeSelfBLName() {
		return feeSelfBLName;
	}

	public void setFeeSelfBLName(String feeSelfBLName) {
		this.feeSelfBLName = feeSelfBLName;
	}

	public String getFeeMost() {
		return feeMost;
	}

	public void setFeeMost(String feeMost) {
		this.feeMost = feeMost;
	}

	public String getFeeLeast() {
		return feeLeast;
	}

	public void setFeeLeast(String feeLeast) {
		this.feeLeast = feeLeast;
	}

	public String getFeeSelfGDName() {
		return feeSelfGDName;
	}

	public void setFeeSelfGDName(String feeSelfGDName) {
		this.feeSelfGDName = feeSelfGDName;
	}

	public String getFeeTypeFlag() {
		return feeTypeFlag;
	}

	public void setFeeTypeFlag(String feeTypeFlag) {
		this.feeTypeFlag = feeTypeFlag;
	}

	public void setIdOtherNo(String idOtherNo) {
		this.idOtherNo = idOtherNo;
	}
	
	public String getIdOtherNo() {
		return idOtherNo;
	}
	public String checkIds;
	/**
	 * @return the checkIds
	 */
	public String getCheckIds() {
		return checkIds;
	}

	/**
	 * @param checkIds the checkIds to set
	 */
	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}
	
	private String settleAcctMid; // 内部账号
	
	public void setSettleAcctMid(String settleAcctMid) {
		this.settleAcctMid = settleAcctMid;
	}
	
	public String getSettleAcctMid() {
		return settleAcctMid;
	}
	
	// 文件集合
//	private List<File> files;
//
//	public List<File> getFiles() {
//		return files;
//	}
//
//	public void setFiles(List<File> files) {
//		this.files = files;
//	}
}
