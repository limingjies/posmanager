package com.allinfinance.struts.mchnt.action;


import java.io.File;
import java.util.List;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.FileFilter;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.LogUtil;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T20901Action extends BaseSupport{
	
	private String category;
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}



	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}


	private  TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp;
	private  TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp;
	private String clearType;
	private String checkIds;
	private T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
	private T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
	//############################################################################################
	//##################################  渠道商户审核新增处理  ##################################
	//############################################################################################
	public String add(){
		try {
			String idStr = "848" + tblMchtBaseInfTmpTmp.getBankNo() +"AAA";//+ tblMchtBaseInfTmpTmp.getAreaNo();
			tblMchtBaseInfTmpTmp.setMchtNo(GenerateNextId.getMchntTmpId(idStr))  ;
			
			if(tblMchtBaseInfTmpTmp.getMchtGroupFlag().equals("3")){
				tblMchtBaseInfTmpTmp.setMchtGroupId(tblMchtBaseInfTmpTmp.getMchtGroupId());
			}else{
				tblMchtBaseInfTmpTmp.setMchtGroupId("-");
			}

			tblMchtBaseInfTmpTmp.setEngName(tblMchtBaseInfTmpTmp.getEngName().length()>40?"":tblMchtBaseInfTmpTmp.getEngName());
			//tblMchtBaseInfTmpTmp.setLicenceEndDate(Constants.DEFAULT);
			// 日期格式转换后保存
			if (!"".equals(tblMchtBaseInfTmpTmp.getLicenceEndDate())){
				tblMchtBaseInfTmpTmp.setLicenceEndDate(tblMchtBaseInfTmpTmp.getLicenceEndDate().replace("-", ""));
			}
			if (!"".equals(tblMchtBaseInfTmpTmp.getProlDate())){
				tblMchtBaseInfTmpTmp.setProlDate(tblMchtBaseInfTmpTmp.getProlDate().replace("-", ""));
			}
			tblMchtBaseInfTmpTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK);
			
			//修改页面营业时间
			tblMchtBaseInfTmpTmp.setOpenTime(CommonFunction.timeFormat(tblMchtBaseInfTmpTmp.getOpenTime()));
			tblMchtBaseInfTmpTmp.setCloseTime(CommonFunction.timeFormat(tblMchtBaseInfTmpTmp.getCloseTime()));
			
			//是否支持无磁无密交易
			tblMchtBaseInfTmpTmp.setPassFlag("0");
			//是否支持人工授权交易
			tblMchtBaseInfTmpTmp.setManuAuthFlag("0");
			//是否支持折扣消费
			tblMchtBaseInfTmpTmp.setDiscConsFlg("0");
			
			//是否仅营业时间内交易
			tblMchtBaseInfTmpTmp.setMchtMngMode("0");
			//申请日期
			tblMchtBaseInfTmpTmp.setApplyDate(CommonFunction.getCurrentDate());
			// 记录修改时间
			tblMchtBaseInfTmpTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			// 记录创建时间
			tblMchtBaseInfTmpTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
			// 记录修改人
			tblMchtBaseInfTmpTmp.setUpdOprId("");
			// 记录创建人
			tblMchtBaseInfTmpTmp.setCrtOprId(getOperator().getOprId());
//			tblMchtBaseInfTmp.setReserved(idOtherNo);
			tblMchtBaseInfTmpTmp.setRislLvl("0");
			tblMchtBaseInfTmpTmp.setMchtLvl("2");// 2-普通商户
			tblMchtBaseInfTmpTmp.setSaAction("0");
			tblMchtBaseInfTmpTmp.setCupMchtFlg("0");
			tblMchtBaseInfTmpTmp.setDebMchtFlg("0");
			tblMchtBaseInfTmpTmp.setCreMchtFlg("0");
			tblMchtBaseInfTmpTmp.setCdcMchtFlg("0");
			tblMchtBaseInfTmpTmp.setManagerTel("1");
			
			
			tblMchtSettleInfTmpTmp.setMchtNo(tblMchtBaseInfTmpTmp.getMchtNo());

//			tblMchtSettleInfTmpTmp.setSettleAcct(clearType.substring(0,1) + tblMchtSettleInfTmpTmp.getSettleAcct());
			tblMchtSettleInfTmpTmp.setSettleAcct(tblMchtSettleInfTmpTmp.getSettleAcct());
			tblMchtSettleInfTmpTmp.setSettleType("1");
			tblMchtSettleInfTmpTmp.setRateFlag("-");
			tblMchtSettleInfTmpTmp.setFeeType("3"); //分段收费
			tblMchtSettleInfTmpTmp.setFeeFixed("-");
			tblMchtSettleInfTmpTmp.setFeeMaxAmt("0");
			tblMchtSettleInfTmpTmp.setFeeMinAmt("0");

			tblMchtSettleInfTmpTmp.setFeeDiv1("-");
			tblMchtSettleInfTmpTmp.setFeeDiv2("-");
			tblMchtSettleInfTmpTmp.setFeeDiv3("-");

			//是否自动清算
			tblMchtSettleInfTmpTmp.setAutoStlFlg("0");
			//退货是否返还手续费
			tblMchtSettleInfTmpTmp.setFeeBackFlg("1");

			// 记录修改时间
			tblMchtSettleInfTmpTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			// 记录创建时间
			tblMchtSettleInfTmpTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
			// 内部账户号
			tblMchtSettleInfTmpTmp.setSettleBankNo(" ");
			tblMchtSettleInfTmpTmp.setFeeDiv1("1");
			tblMchtSettleInfTmpTmp.setFeeDiv2("2");
			tblMchtSettleInfTmpTmp.setFeeDiv3("3");
			
			// 将ACQ_INST_ID存银联机构号，并把项目里所有以前用到ACQ_INST_ID的地方改成BANK_NO
			
			TblBrhInfo tblBrhInfo = t10101BO.get(tblMchtBaseInfTmpTmp.getBankNo());
			tblMchtBaseInfTmpTmp.setAcqInstId(tblBrhInfo.getCupBrhId());
			tblMchtBaseInfTmpTmp.setSignInstId(tblBrhInfo.getCupBrhId());
			tblMchtBaseInfTmpTmp.setAgrBr(tblMchtBaseInfTmpTmp.getBankNo());

			
			
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			String basePathNew=basePath+tblMchtBaseInfTmpTmp.getMchtNo()+"/";
			File writeFile = new File(basePathNew);
			if (!writeFile.exists()) {
				writeFile.mkdirs();
			}
			//文件改名移动
			if (!StringUtil.isNull(hasUpload) && !"0".equals(hasUpload)) {
				basePath+="addTmp/";
				File fl = new File(basePath);
				FileFilter filter = new FileFilter(imagesId);
				File[] files = fl.listFiles(filter);
				for (File file : files) {
					file.renameTo(new File(basePathNew + file.getName().replaceAll(imagesId, tblMchtBaseInfTmpTmp.getMchtNo())));//文件移动
				}
			}
			
			
			//需要检查
			if (!StringUtil.isNull(checkIds) && checkIds.equals("T")) {
				String ret=t20901BO.checkMchtInfo(tblMchtBaseInfTmpTmp,tblMchtSettleInfTmpTmp);
				if(!Constants.SUCCESS_CODE.equals(ret)){
					return returnService(ret);
				}
				ret=null;
				ret=t20901BO.checkMchtTmpInfo(tblMchtBaseInfTmpTmp,tblMchtSettleInfTmpTmp);
				if(!Constants.SUCCESS_CODE.equals(ret)){
					return returnService(ret);
				}
			}
			
			rspCode = t20901BO.saveTmp(tblMchtBaseInfTmpTmp, tblMchtSettleInfTmpTmp);

			
			LogUtil.log(this.getClass().getSimpleName(), getOperator().getOprId(), "save:" + rspCode, tblMchtBaseInfTmpTmp);
			LogUtil.log(this.getClass().getSimpleName(), getOperator().getOprId(), "save:" + rspCode, tblMchtSettleInfTmpTmp);
			
			if (Constants.SUCCESS_CODE.equals(rspCode)) {
//				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "新增商户信息成功");
				return returnService(Constants.SUCCESS_CODE);
			} else {
				return returnService(rspCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
	}

	
	
	public String upload(){
		String mcht = null;
		if (StringUtil.isNotEmpty(this.mcht)) {
			t20901BO.upload(imgFile, imgFileFileName, imagesId, null,category);
			mcht = this.mcht;
			String basePath = SysParamUtil
					.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			String basePathNew = basePath + mcht + "/";
			File writeFile = new File(basePathNew);
			if (!writeFile.exists()) {
				writeFile.mkdirs();
			}
			// 文件改名移动
			basePath += "addTmp/";
			File fl = new File(basePath);
			FileFilter filter = new FileFilter(imagesId);
			File[] files = fl.listFiles(filter);
			for (File file : files) {
				file.renameTo(new File(basePathNew
						+ file.getName().replaceAll(imagesId, mcht)));// 文件移动
			}
			return Constants.SUCCESS_CODE;
		} else {
			rspCode = t20901BO.upload(imgFile, imgFileFileName, imagesId, null,category);
			return rspCode;
		}

	}
	
	
	//计费代码
		private String discCode;

		public String getDiscCode() {
			return discCode;
		}

		public void setDiscCode(String discCode) {
			this.discCode = discCode;
		}
	
	// 文件集合
	private List<File> imgFile;
	
	// 文件名称集合
	private List<String> imgFileFileName;
	private String imagesId;
	private String mcht;

	/**
	 * @return the mcht
	 */
	public String getMcht() {
		return mcht;
	}

	/**
	 * @param mcht the mcht to set
	 */
	public void setMcht(String mcht) {
		this.mcht = mcht;
	}


	private String hasUpload;
	
	
	/**
	 * @return the hasUpload
	 */
	public String getHasUpload() {
		return hasUpload;
	}

	/**
	 * @param hasUpload the hasUpload to set
	 */
	public void setHasUpload(String hasUpload) {
		this.hasUpload = hasUpload;
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

	/**
	 * @return the tblMchtBaseInfTmpTmp
	 */
	public TblMchtBaseInfTmpTmp getTblMchtBaseInfTmpTmp() {
		return tblMchtBaseInfTmpTmp;
	}
	/**
	 * @param tblMchtBaseInfTmpTmp the tblMchtBaseInfTmpTmp to set
	 */
	public void setTblMchtBaseInfTmpTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp) {
		this.tblMchtBaseInfTmpTmp = tblMchtBaseInfTmpTmp;
	}
	/**
	 * @return the tblMchtSettleInfTmpTmp
	 */
	public TblMchtSettleInfTmpTmp getTblMchtSettleInfTmpTmp() {
		return tblMchtSettleInfTmpTmp;
	}
	/**
	 * @param tblMchtSettleInfTmpTmp the tblMchtSettleInfTmpTmp to set
	 */
	public void setTblMchtSettleInfTmpTmp(
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp) {
		this.tblMchtSettleInfTmpTmp = tblMchtSettleInfTmpTmp;
	}


	/**
	 * @return the clearType
	 */
	public String getClearType() {
		return clearType;
	}


	/**
	 * @param clearType the clearType to set
	 */
	public void setClearType(String clearType) {
		this.clearType = clearType;
	}


	/**
	 * @return the t10101BO
	 */
	public T10101BO getT10101BO() {
		return t10101BO;
	}


	/**
	 * @param t10101bo the t10101BO to set
	 */
	public void setT10101BO(T10101BO t10101bo) {
		t10101BO = t10101bo;
	}


	/**
	 * @return the t20901BO
	 */
	public T20901BO getT20901BO() {
		return t20901BO;
	}


	/**
	 * @param t20901bo the t20901BO to set
	 */
	public void setT20901BO(T20901BO t20901bo) {
		t20901BO = t20901bo;
	}
	


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
	
	
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
