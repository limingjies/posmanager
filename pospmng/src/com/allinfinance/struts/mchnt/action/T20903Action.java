package com.allinfinance.struts.mchnt.action;
import java.io.File;
import java.util.List;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.bo.mchnt.T20903BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.FileFilter;
import com.allinfinance.system.util.LogUtil;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T20903Action extends BaseSupport{

	private  TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp;
	private  TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp;
	private String clearTypeNm;
	private String settleType1;
	private String checkIds;
	private T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
	private T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
	private T20903BO T20903BO = (T20903BO) ContextUtil.getBean("T20903BO");
	public String update(){
		try {
			
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmpUpd=t20901BO.getBaseInfTmp(mchtNo);
			if(tblMchtBaseInfTmpTmpUpd==null){
				return "数据不存在，请重新刷新选择！";
			}
			
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmpUpd=t20901BO.getSettleInfTmp(mchtNo);
			
			if(tblMchtSettleInfTmpTmpUpd==null){
				return "数据不存在，请重新刷新选择！";
			}
			tblMchtBaseInfTmpTmpUpd.setContendDate(tblMchtBaseInfTmpTmp.getContendDate());
			tblMchtBaseInfTmpTmpUpd.setContstartDate(tblMchtBaseInfTmpTmp.getContstartDate());
			tblMchtBaseInfTmpTmpUpd.setSigaveTrans(tblMchtBaseInfTmpTmp.getSigaveTrans());
			tblMchtBaseInfTmpTmpUpd.setMonaveTrans(tblMchtBaseInfTmpTmp.getMonaveTrans());
			tblMchtBaseInfTmpTmpUpd.setBusArea(tblMchtBaseInfTmpTmp.getBusArea());
			tblMchtBaseInfTmpTmpUpd.setBusInfo(tblMchtBaseInfTmpTmp.getBusInfo());
			tblMchtBaseInfTmpTmpUpd.setFinacommEmail(tblMchtBaseInfTmpTmp.getFinacommEmail());
			tblMchtBaseInfTmpTmpUpd.setFinacommTel(tblMchtBaseInfTmpTmp.getFinacommTel());
			tblMchtBaseInfTmpTmpUpd.setFinacontact(tblMchtBaseInfTmpTmp.getFinacontact());
			//修改页面营业时间
			tblMchtBaseInfTmpTmpUpd.setOpenTime(CommonFunction.timeFormat(tblMchtBaseInfTmpTmp.getOpenTime()));
			tblMchtBaseInfTmpTmpUpd.setCloseTime(CommonFunction.timeFormat(tblMchtBaseInfTmpTmp.getCloseTime()));
			tblMchtBaseInfTmpTmpUpd.setCompaddr(tblMchtBaseInfTmpTmp.getCompaddr());
			tblMchtBaseInfTmpTmpUpd.setCompNm(tblMchtBaseInfTmpTmp.getCompNm());
			tblMchtBaseInfTmpTmpUpd.setEngName(tblMchtBaseInfTmpTmp.getEngName());
			tblMchtBaseInfTmpTmpUpd.setAreaNo(tblMchtBaseInfTmpTmp.getAreaNo());
			tblMchtBaseInfTmpTmpUpd.setAcqBkName(tblMchtBaseInfTmpTmp.getAcqBkName());
			tblMchtBaseInfTmpTmpUpd.setConnType(tblMchtBaseInfTmpTmp.getConnType());
			tblMchtBaseInfTmpTmpUpd.setMchtGroupFlag(tblMchtBaseInfTmpTmp.getMchtGroupFlag());
			
			if(tblMchtBaseInfTmpTmp.getMchtGroupFlag().equals("3")){
				tblMchtBaseInfTmpTmpUpd.setMchtGroupId(tblMchtBaseInfTmpTmp.getMchtGroupId());
			}else{
				tblMchtBaseInfTmpTmpUpd.setMchtGroupId("-");
			}
			
			// 日期格式转换后保存
			if (!"".equals(tblMchtBaseInfTmpTmpUpd.getLicenceEndDate())){
				tblMchtBaseInfTmpTmpUpd.setLicenceEndDate(tblMchtBaseInfTmpTmp.getLicenceEndDate().replace("-", ""));
			}
			if (!"".equals(tblMchtBaseInfTmpTmpUpd.getProlDate())){
				tblMchtBaseInfTmpTmpUpd.setProlDate(tblMchtBaseInfTmpTmp.getProlDate().replace("-", ""));
			}
			
			tblMchtBaseInfTmpTmpUpd.setBankNo(tblMchtBaseInfTmpTmp.getBankNo());
			tblMchtBaseInfTmpTmpUpd.setMchtNm(tblMchtBaseInfTmpTmp.getMchtNm());
			tblMchtBaseInfTmpTmpUpd.setAddr(tblMchtBaseInfTmpTmp.getAddr());
			tblMchtBaseInfTmpTmpUpd.setLicenceNo(tblMchtBaseInfTmpTmp.getLicenceNo());
			tblMchtBaseInfTmpTmpUpd.setFaxNo(tblMchtBaseInfTmpTmp.getFaxNo());
			tblMchtBaseInfTmpTmpUpd.setEtpsAttr(tblMchtBaseInfTmpTmp.getEtpsAttr());
			tblMchtBaseInfTmpTmpUpd.setCommEmail(tblMchtBaseInfTmpTmp.getCommEmail());
			tblMchtBaseInfTmpTmpUpd.setHomePage(tblMchtBaseInfTmpTmp.getHomePage());
			tblMchtBaseInfTmpTmpUpd.setPostCode(tblMchtBaseInfTmpTmp.getPostCode());
			tblMchtBaseInfTmpTmpUpd.setManager(tblMchtBaseInfTmpTmp.getManager());
			tblMchtBaseInfTmpTmpUpd.setBusAmt(tblMchtBaseInfTmpTmp.getBusAmt());
			tblMchtBaseInfTmpTmpUpd.setArtifCertifTp(tblMchtBaseInfTmpTmp.getArtifCertifTp());
			tblMchtBaseInfTmpTmpUpd.setIdentityNo(tblMchtBaseInfTmpTmp.getIdentityNo());
			tblMchtBaseInfTmpTmpUpd.setContact(tblMchtBaseInfTmpTmp.getContact());
			tblMchtBaseInfTmpTmpUpd.setCommTel(tblMchtBaseInfTmpTmp.getCommTel());
			tblMchtBaseInfTmpTmpUpd.setElectrofax(tblMchtBaseInfTmpTmp.getElectrofax());
			tblMchtBaseInfTmpTmpUpd.setFax(tblMchtBaseInfTmpTmp.getFax());
//			tblMchtBaseInfTmpTmpUpd.setMchtStatus(tblMchtBaseInfTmpTmp.getMchtStatus());
			
			
			tblMchtSettleInfTmpTmpUpd.setSettleAcct(clearTypeNm.substring(0,1)+tblMchtSettleInfTmpTmp.getSettleAcct());
			tblMchtSettleInfTmpTmpUpd.setSettleBankNo(tblMchtSettleInfTmpTmp.getSettleBankNo());
			tblMchtSettleInfTmpTmpUpd.setSettleBankNm(tblMchtSettleInfTmpTmp.getSettleBankNm());
			tblMchtSettleInfTmpTmpUpd.setSettleAcctNm(tblMchtSettleInfTmpTmp.getSettleAcctNm());
			tblMchtSettleInfTmpTmpUpd.setOpenStlno(tblMchtSettleInfTmpTmp.getOpenStlno());
			tblMchtSettleInfTmpTmpUpd.setReserved(tblMchtSettleInfTmpTmp.getReserved());
			
			tblMchtBaseInfTmpTmpUpd.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK);
			System.out.println(tblMchtBaseInfTmpTmp.getEtpsAttr());
			// 记录修改时间
			tblMchtBaseInfTmpTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			
			// 记录修改人
			tblMchtBaseInfTmpTmp.setUpdOprId(getOperator().getOprId());
			tblMchtBaseInfTmpTmp.setCrtOprId(getOperator().getOprId());
			
			


			// 记录修改时间
			tblMchtSettleInfTmpTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			
			// 将ACQ_INST_ID存银联机构号，并把项目里所有以前用到ACQ_INST_ID的地方改成BANK_NO
			
//			TblBrhInfo tblBrhInfo = t10101BO.get(tblMchtBaseInfTmpTmp.getBankNo());
//			tblMchtBaseInfTmpTmp.setAcqInstId(tblBrhInfo.getCupBrhId());
			tblMchtBaseInfTmpTmp.setAgrBr(tblMchtBaseInfTmpTmp.getBankNo());
			tblMchtBaseInfTmpTmp.setSignInstId(tblMchtBaseInfTmpTmp.getBankNo());

			
			
//			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
//			basePath = basePath.replace("\\", "/");
//			String basePathNew=basePath+mcht+"/";
//			File writeFile = new File(basePathNew);
//			if (!writeFile.exists()) {
//				writeFile.mkdirs();
//			}
//			//文件改名移动
//			if (!StringUtil.isNull(hasUpload) && !"0".equals(hasUpload)) {
//				basePath+="addTmp/";
//				File fl = new File(basePath);
//				FileFilter filter = new FileFilter(imagesId);
//				File[] files = fl.listFiles(filter);
//				for (File file : files) {
//					file.renameTo(new File(basePathNew + file.getName().replaceAll(imagesId, tblMchtBaseInfTmpTmp.getMchtNo())));//文件移动
//				}
//			}
//			
			
//			//需要检查
//			if (!StringUtil.isNull(checkIds) && checkIds.equals("T")) {
//				String ret=t20901BO.checkMchtInfo(tblMchtBaseInfTmpTmp,tblMchtSettleInfTmpTmp);
//				if(!Constants.SUCCESS_CODE.equals(ret)){
//					return returnService(ret);
//				}
//				ret=null;
//				ret=t20901BO.checkMchtTmpInfo(tblMchtBaseInfTmpTmp,tblMchtSettleInfTmpTmp);
//				if(!Constants.SUCCESS_CODE.equals(ret)){
//					return returnService(ret);
//				}
//			}
			
			rspCode = t20901BO.updateTmp(tblMchtBaseInfTmpTmpUpd, tblMchtSettleInfTmpTmpUpd);

			
			LogUtil.log(this.getClass().getSimpleName(), getOperator().getOprId(), "save:" + rspCode, tblMchtBaseInfTmpTmpUpd);
			LogUtil.log(this.getClass().getSimpleName(), getOperator().getOprId(), "save:" + rspCode, tblMchtSettleInfTmpTmpUpd);
			
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
		if(StringUtil.isNotEmpty(this.mcht)){
			t20901BO.upload(imgFile,imgFileFileName,imagesId,null,null);
			mcht = this.mcht;
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			String basePathNew=basePath+mcht+"/";
			File writeFile = new File(basePathNew);
			if (!writeFile.exists()) {
				writeFile.mkdirs();
			}
			//文件改名移动
				basePath+="addTmp/";
				File fl = new File(basePath);
				FileFilter filter = new FileFilter(imagesId);
				File[] files = fl.listFiles(filter);
				for (File file : files) {
					file.renameTo(new File(basePathNew + file.getName().replaceAll(imagesId, mcht)));//文件移动
				}
				return returnService(Constants.SUCCESS_CODE);
		} else {
			rspCode=t20901BO.upload(imgFile,imgFileFileName,imagesId,null,null);
			return returnService(rspCode);
		}
	}
	/**
	 * 商户信息删除
	 * @return
	 */
	public String del() {
		String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
		
		basePath = basePath.replace("\\", "/");
		
		String basePathNew=basePath+mchtNo+"/";
		   File file = new File(basePathNew);  
		   // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
		   if (file.exists()) {  
		    String[] filelist = file.list();  
		    for (int i = 0; i < filelist.length; i++) {  
		     File delfile = new File(basePathNew + "/" + filelist[i]);  
		     if (delfile.exists()) {  
		      delfile.delete();   
		     }  
		}
		    file.delete();
	}
		try {
			
			rspCode = T20903BO.deleteArith(mchtNo);
//			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
//	
//			basePath = basePath.replace("\\", "/");
//			
//			String basePathNew=basePath+mchtNo+"/";
//			//文件改名移动
//		
//				File fl = new File(basePathNew);
//			
//				FileFilter filter = new FileFilter(imagesId);
//			
//				File[] files = fl.listFiles(filter);
//				if(files.length!=0){
//				for (File file : files) {
//					
//					file.delete();
//				}	
//				}
//				fl.delete();
			return returnService(rspCode);
		} catch (Exception e) {
			return returnService(rspCode, e);
		}
	}
	
	
	// 文件集合
	private List<File> imgFile;
	
	// 文件名称集合
	private List<String> imgFileFileName;
	private String imagesId;
	private String mcht;
	public String getMcht() {
		return mcht;
	}

	public void setMcht(String mcht) {
		this.mcht = mcht;
	}


	private String hasUpload;
	private java.lang.String mchtNo;
	private java.lang.String mchtNm;
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
	public String getSettleType1() {
		return settleType1;
	}


	/**
	 * @param clearType the clearType to set
	 */
	public void setSettleType1(String settleType1) {
		this.settleType1 = settleType1;
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

	public String getClearTypeNm() {
		return clearTypeNm;
	}

	public void setClearTypeNm(String clearTypeNm) {
		this.clearTypeNm = clearTypeNm;
	}

}
