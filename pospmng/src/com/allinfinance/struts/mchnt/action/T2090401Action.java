package com.allinfinance.struts.mchnt.action;

import java.io.File;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.bo.mchnt.T2090401BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.dao.iface.mchnt.ITblMchtLimitDateDAO;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblMchtLimitDate;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.FileFilter;
//import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T2090401Action extends BaseSupport {

	private TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp;
	private TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp;
	private String limitdate;
	private String reserve;

	private String clearType;
	private String checkIds;

	private T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
	private T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
	private T2090401BO t2090401BO = (T2090401BO) ContextUtil
			.getBean("T2090401BO");
	private ITblMchtLimitDateDAO iTblMchtLimitDateDAO = (ITblMchtLimitDateDAO) ContextUtil
			.getBean("iTblMchtLimitDateDAO");

	// ################################## 渠道商户审核通过处理
	public String accept() {
		try {
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmpUpd = t20901BO
					.getBaseInfTmp(mcht);
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmpUpd = t20901BO
					.getSettleInfTmp(mcht);
			if (tblMchtBaseInfTmpTmpUpd == null
					|| tblMchtSettleInfTmpTmpUpd == null) {
				return "审核商户号不存在";
			}
			if(tblMchtBaseInfTmpTmp!=null&&tblMchtSettleInfTmpTmp!=null){
				tblMchtSettleInfTmpTmpUpd.setCompliance(tblMchtSettleInfTmpTmp
						.getCompliance());
				tblMchtSettleInfTmpTmpUpd.setCountry(tblMchtSettleInfTmpTmp
						.getCountry());
				tblMchtBaseInfTmpTmpUpd.setMchtGrp(tblMchtBaseInfTmpTmp
						.getMchtGrp());
				tblMchtBaseInfTmpTmpUpd.setMcc(tblMchtBaseInfTmpTmp.getMcc());
				tblMchtBaseInfTmpTmpUpd.setRislLvl(tblMchtBaseInfTmpTmp
						.getRislLvl());// 商户风险等级
			}
			tblMchtBaseInfTmpTmpUpd.setSignInstId(tblMchtBaseInfTmpTmpUpd.getBankNo());
			// 如果是修改待初审，改为修改待终审
			if (!mcht.contains("AAA")) {
				tblMchtBaseInfTmpTmpUpd
						.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK_SECOND);
			} else {
				tblMchtBaseInfTmpTmpUpd
						.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK);
			}
			// 记录修改时间
			tblMchtBaseInfTmpTmpUpd.setRecUpdTs(CommonFunction
					.getCurrentDateTime());
			// 记录修改人
			tblMchtBaseInfTmpTmpUpd.setUpdOprId(getOperator().getOprId());
			// 记录修改时间
			tblMchtSettleInfTmpTmpUpd.setRecUpdTs(CommonFunction
					.getCurrentDateTime());
			// 将审核完的渠道商户信息新增到商户信息表，并且状态设置为新增待初审
			TblMchtSettleInfTmp tblMchtSettleInfTmpadd = new TblMchtSettleInfTmp();
			TblMchtBaseInfTmp tblMchntInfoTmpadd = new TblMchtBaseInfTmp();
			BeanUtils.copyProperties(tblMchtSettleInfTmpadd,
					tblMchtSettleInfTmpTmpUpd);
			BeanUtils.copyProperties(tblMchntInfoTmpadd,
					tblMchtBaseInfTmpTmpUpd);
			// 虚拟账户开户标志
			tblMchntInfoTmpadd.setOpenVirtualAcctFlag(tblMchtBaseInfTmpTmpUpd
					.getOpenVirtualAcctFlag());

			if (!mcht.contains("AAA")) {
				tblMchntInfoTmpadd
						.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK_SECOND);
				tblMchtBaseInfTmpTmpUpd.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK_SECOND);
			} else {
				tblMchntInfoTmpadd
						.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK);
				tblMchtBaseInfTmpTmpUpd.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK);
			}
			// 申请日期
			tblMchntInfoTmpadd.setApplyDate(CommonFunction.getCurrentDate());
			// 记录修改时间
			tblMchntInfoTmpadd.setRecUpdTs(CommonFunction.getCurrentDateTime());
			// 记录创建时间
			tblMchntInfoTmpadd.setRecCrtTs(CommonFunction.getCurrentDateTime());
			// 记录修改人
			tblMchntInfoTmpadd.setUpdOprId(getOperator().getOprId());
			// 记录创建人
			tblMchntInfoTmpadd.setCrtOprId(getOperator().getOprId());
			// 记录修改时间
			tblMchtSettleInfTmpadd.setRecUpdTs(CommonFunction
					.getCurrentDateTime());
			// 记录创建时间
			tblMchtSettleInfTmpadd.setRecCrtTs(CommonFunction
					.getCurrentDateTime());
			// by ctz 商户黑名单校验
			if (checkBlackMcht(tblMchtBaseInfTmpTmpUpd, tblMchtSettleInfTmpTmpUpd)) {
				return returnService("该部分商户关键信息与黑名单商户信息一致！");
			}
			// 将ACQ_INST_ID存银联机构号，并把项目里所有以前用到ACQ_INST_ID的地方改成BANK_NO
			TblBrhInfo tblBrhInfo = t10101BO.get(tblMchtBaseInfTmpTmpUpd
					.getBankNo());
			tblMchntInfoTmpadd.setAcqInstId(tblBrhInfo.getCupBrhId());
			rspCode = t2090401BO.updateTmp(tblMchtBaseInfTmpTmpUpd,
					tblMchtSettleInfTmpTmpUpd, tblMchntInfoTmpadd,
					tblMchtSettleInfTmpadd);

			if (Constants.SUCCESS_CODE.equals(rspCode)) {

				// 保存提醒信息------------
				if (null != limitdate && !"".equals(limitdate)) {
					TblMchtLimitDate tblMchtLimitDate = new TblMchtLimitDate();
					tblMchtLimitDate.setLimitdate(limitdate);
					tblMchtLimitDate.setReserved(reserve);
					tblMchtLimitDate.setMchtno(tblMchntInfoTmpadd.getMchtNo());
					tblMchtLimitDate.setCrtopr(getOperator().getOprId());
					tblMchtLimitDate.setCrttime(CommonFunction
							.getCurrentDateTime());
					tblMchtLimitDate.setStatus("1");
					iTblMchtLimitDateDAO.saveOrUpdate(tblMchtLimitDate);
				}
				// 记录退回信息
				t20901BO.approveRecord(tblMchntInfoTmpadd.getMchtNo(), "初审通过",
						tblMchntInfoTmpadd.getMchtStatus(), tblMchntInfoTmpadd
								.getBankNo(), getOperator().getOprId());

				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE
						+ "审核商户信息通过，商户编号[" + tblMchntInfoTmpadd.getMchtNo()
						+ "]");
			} else {
				return returnService(rspCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}

	private boolean checkBlackMcht(TblMchtBaseInfTmpTmp tmp,
			TblMchtSettleInfTmpTmp settleTmp) {

		StringBuffer whereSql = new StringBuffer(" where 1=1 and ( ");
		whereSql.append("  b.mcht_nm").append(" = ").append("'")
				.append(tmp.getMchtNm()).append("' ");

		if (StringUtil.isNotEmpty(tmp.getLicenceNo())
				&& (!"无".equals(tmp.getLicenceNo()))
				&& (!"0".equals(tmp.getLicenceNo()))) {
			whereSql.append(" or b.licence_no").append(" = ").append("'")
					.append(tmp.getLicenceNo()).append("' ");
		}
		if (StringUtil.isNotEmpty(tmp.getFaxNo())
				&& (!"无".equals(tmp.getFaxNo()))
				&& (!"0".equals(tmp.getFaxNo()))) {
			whereSql.append(" or b.fax_no").append(" = ").append("'")
					.append(tmp.getFaxNo()).append("' ");
		}
		if (StringUtil.isNotEmpty(tmp.getManager())
				&& StringUtil.isNotEmpty(tmp.getArtifCertifTp())
				&& StringUtil.isNotEmpty(tmp.getIdentityNo())) {
			whereSql.append(" or (b.manager ='").append(tmp.getManager())
					.append("' and b.artif_certif_tp ='")
					.append(tmp.getArtifCertifTp())
					.append("' and b.identity_no ='")
					.append(tmp.getIdentityNo()).append("' ) ");
		}
		if (StringUtil.isNotEmpty(settleTmp.getSettleAcct())) {
			whereSql.append(" or c.settle_acct").append(" = ").append("'")
					.append(settleTmp.getSettleAcct()).append("' ");
		}
		whereSql.append(" ) ");
		String countSql = "select count(1) from TBL_RISK_BLACK_MCHT a "
				+ " left join TBL_MCHT_BASE_INF b on a.mcht_no=b.MCHT_NO  "
				+ " left join TBL_MCHT_SETTLE_INF c on a.mcht_no=c.MCHT_NO "
				+ whereSql;
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		if (Integer.parseInt(count) > 0) {
			return true;
		}
		return false;
	}

	public String upload() {
		String mcht = null;
		if (StringUtil.isNotEmpty(this.mcht)) {
			t20901BO.upload(imgFile, imgFileFileName, imagesId, null, null);
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
			return returnService(Constants.SUCCESS_CODE);
		} else {
			rspCode = t20901BO.upload(imgFile, imgFileFileName, imagesId, null,
					null);
			return returnService(rspCode);
		}

	}

	// 计费代码
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
	 * @param mcht
	 *            the mcht to set
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
	 * @param hasUpload
	 *            the hasUpload to set
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
	 * @param imgFile
	 *            the imgFile to set
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
	 * @param imgFileFileName
	 *            the imgFileFileName to set
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
	 * @param imagesId
	 *            the imagesId to set
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
	 * @param tblMchtBaseInfTmpTmp
	 *            the tblMchtBaseInfTmpTmp to set
	 */
	public void setTblMchtBaseInfTmpTmp(
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp) {
		this.tblMchtBaseInfTmpTmp = tblMchtBaseInfTmpTmp;
	}

	/**
	 * @return the tblMchtSettleInfTmpTmp
	 */
	public TblMchtSettleInfTmpTmp getTblMchtSettleInfTmpTmp() {
		return tblMchtSettleInfTmpTmp;
	}

	/**
	 * @param tblMchtSettleInfTmpTmp
	 *            the tblMchtSettleInfTmpTmp to set
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
	 * @param clearType
	 *            the clearType to set
	 */
	public void setClearType(String clearType) {
		this.clearType = clearType;
	}

	/**
	 * @return the checkIds
	 */
	public String getCheckIds() {
		return checkIds;
	}

	/**
	 * @param checkIds
	 *            the checkIds to set
	 */
	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}

	public String getLimitdate() {
		return limitdate;
	}

	public void setLimitdate(String limitdate) {
		this.limitdate = limitdate;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
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
