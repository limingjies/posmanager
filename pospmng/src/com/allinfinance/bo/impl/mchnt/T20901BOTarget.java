package com.allinfinance.bo.impl.mchnt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntRefuseDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.po.TblMchntRefuse;
import com.allinfinance.po.TblMchntRefusePK;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

public class T20901BOTarget implements T20901BO {

	private ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO;

	private ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO;
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	public TblMchntRefuseDAO tblMchntRefuseDAO;
	/*
	 * 商户审核通过
	 * 
	 * @see
	 * com.allinfinance.bo.impl.mchnt.TblMchntService#accept(java.lang.String)
	 */
	/*
	 * public String accept(String mchntId) throws IllegalAccessException,
	 * InvocationTargetException { // TODO Auto-generated method stub
	 * TblMchtBaseInfTmpTmp tmp = tblMchtBaseInfTmpTmpDAO.get(mchntId);
	 * TblMchtSettleInfTmpTmp tmpSettle =
	 * tblMchtSettleInfTmpTmpDAO.get(mchntId); if (null == tmp || null ==
	 * tmpSettle) { return "没有找到商户的临时信息，请重试"; } //
	 * if(tmp.getMchtLvl().equals("9") || tmp.getMchtLvl() == null){ // return
	 * "商户新增终审前必须先定级，请先给商户评级！"; // }
	 * 
	 * //更新前的状态 String status = tmp.getMchtStatus(); //临时表中的cre时间 String
	 * crtDateTmp= tmp.getRecCrtTs();
	 * 
	 * // 取得原始信息 TblMchtBaseInf inf = tblMchtBaseInfDAO.get(tmp.getMchtNo());
	 * TblMchtSettleInf infSettle = tblMchtSettleInfDAO.get(tmpSettle
	 * .getMchtNo()); //正式表中cre日期 String crtDate= "0";
	 * 
	 * if (null != inf) { crtDate= inf.getRecCrtTs(); } if (null == inf) { inf =
	 * new TblMchtBaseInf(); } if (null == infSettle) { infSettle = new
	 * TblMchtSettleInf(); }
	 * 
	 * // 更新时间和柜员 tmp.setRecUpdTs(CommonFunction.getCurrentDateTime()); Operator
	 * opr = (Operator) ServletActionContext.getRequest()
	 * .getSession().getAttribute(Constants.OPERATOR_INFO);
	 * tmp.setUpdOprId(opr.getOprId());
	 * tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());
	 * 
	 * 
	 * //判断如果为修改待审核并且更新了中文简称就同步到终端信息表 // if
	 * (TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tmp.getMchtStatus()) //
	 * && (inf.getMchtCnAbbr() == null
	 * ||!inf.getMchtCnAbbr().equals(tmp.getMchtCnAbbr()) // ||inf.getEngName()
	 * == null ||!inf.getEngName().equals(tmp.getEngName()))) { //// TODO
	 * 需要优化，暂时修养一下，不改了 // StringBuffer sql0 = new
	 * StringBuffer("update tbl_term_inf set term_para = substr(term_para,1,50)||'"
	 * ) // .append(CommonFunction.fillStringByDB(tmp.getMchtCnAbbr(), ' ', 40,
	 * true)).append("'||substr(term_para,91,20)||'") //
	 * .append(CommonFunction.fillStringByDB
	 * (tmp.getEngName()==null?"":tmp.getEngName(), ' ', 40,
	 * true)).append("'||substr(term_para,151) where MCHT_CD = '") //
	 * .append(mchntId).append("'"); // // StringBuffer sql1 = new StringBuffer(
	 * "update tbl_term_inf_tmp set term_para = substr(term_para,1,53)||'") //
	 * .append(CommonFunction.fillStringByDB(tmp.getMchtCnAbbr(), ' ', 40,
	 * true)).append("'||substr(term_para,94,22)||'") //
	 * .append(CommonFunction.fillStringByDB
	 * (tmp.getEngName()==null?"":tmp.getEngName(), ' ', 40,
	 * true)).append("'||substr(term_para,156) where MCHT_CD = '") //
	 * .append(mchntId).append("'"); // // //同时更新临时表和正式表 //
	 * CommonFunction.getCommQueryDAO().excute(sql0.toString()); //
	 * CommonFunction.getCommQueryDAO().excute(sql1.toString()); // }
	 * 
	 * 
	 * if (tmp.getMchtStatus().equals(TblMchntInfoConstants.MCHNT_ST_DEL_UNCK))
	 * {//注销待审核同步更新到终端表 String update0 =
	 * "update tbl_term_inf set TERM_STA = '7' where MCHT_CD = '" +
	 * tmp.getMchtNo() + "'"; String update1 =
	 * "update tbl_term_inf_tmp set TERM_STA = '7' where MCHT_CD = '" +
	 * tmp.getMchtNo() + "'"; CommonFunction.getCommQueryDAO().excute(update0);
	 * CommonFunction.getCommQueryDAO().excute(update1); } else if
	 * (tmp.getMchtStatus().equals(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK))
	 * {//停用待审核同步更新到终端表 String update0 =
	 * "update tbl_term_inf set TERM_STA = '4' where MCHT_CD = '" +
	 * tmp.getMchtNo() + "'"; String update1 =
	 * "update tbl_term_inf_tmp set TERM_STA = '4' where MCHT_CD = '" +
	 * tmp.getMchtNo() + "'"; CommonFunction.getCommQueryDAO().excute(update0);
	 * CommonFunction.getCommQueryDAO().excute(update1); }
	 * 
	 * 
	 * 
	 * 
	 * // 获得下一状态 tmp.setMchtStatus(StatusUtil.getNextStatus("A." +
	 * tmp.getMchtStatus()));
	 * 
	 * // 复制新的信息，复制的是数据集，与终端审核时clone函数不同，clone复制的是数据库数据
	 * BeanUtils.copyProperties(tmp, inf); BeanUtils.copyProperties(tmpSettle,
	 * infSettle);
	 * 
	 * //如果是新增终审通过，更新创建日期记录入网日期
	 * if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(status) ||
	 * TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(status)){
	 * inf.setRecCrtTs(CommonFunction.getCurrentDateTime());//入网日期 }
	 * 
	 * //如果不是新增，crt日期不变
	 * if(!TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(status) &&
	 * !TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK.equals(status)){
	 * inf.setRecCrtTs(crtDate); }
	 * 
	 * // 更新到数据库 tblMchtBaseInfTmpDAO.update(tmp);
	 * tblMchtBaseInfDAO.saveOrUpdate(inf);
	 * tblMchtSettleInfTmpDAO.update(tmpSettle);
	 * tblMchtSettleInfDAO.saveOrUpdate(infSettle);
	 * 
	 * 
	 * 
	 * return Constants.SUCCESS_CODE; }
	 */

	/**
	 * @return the tblMchtBaseInfTmpDAO
	 */
	public TblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpDAO the tblMchtBaseInfTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpDAO(TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	@Override
	public String back(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TblMchtBaseInfTmpTmp getBaseInfTmp(String mchntId) {
		// TODO Auto-generated method stub
		return tblMchtBaseInfTmpTmpDAO.get(mchntId);
	}

	@Override
	public TblMchtSettleInfTmpTmp getSettleInfTmp(String mchntId) {
		// TODO Auto-generated method stub
		return tblMchtSettleInfTmpTmpDAO.get(mchntId);
	}

	@Override
	public String refuse(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp) {
		// TODO Auto-generated method stub
		if (tblMchtBaseInfTmpTmpDAO.get(tblMchtBaseInfTmpTmp.getMchtNo()) != null) {
			return "您自定义的商户号已经存在";
		}

		tblMchtBaseInfTmpTmpDAO.save(tblMchtBaseInfTmpTmp);
		tblMchtSettleInfTmpTmpDAO.save(tblMchtSettleInfTmpTmp);

		return Constants.SUCCESS_CODE;
	}

	@Override
	public String updateBaseInfTmpTmp(TblMchtBaseInfTmpTmp inf)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		tblMchtBaseInfTmpTmpDAO.update(inf);

		return Constants.SUCCESS_CODE;
	}

	@Override
	public String updateTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp) {
		// TODO Auto-generated method stub
		tblMchtBaseInfTmpTmpDAO.update(tblMchtBaseInfTmpTmp);
		tblMchtSettleInfTmpTmpDAO.update(tblMchtSettleInfTmpTmp);

		return Constants.SUCCESS_CODE;

	}

	/**
	 * @return the tblMchtBaseInfTmpTmpDAO
	 */
	public ITblMchtBaseInfTmpTmpDAO getTblMchtBaseInfTmpTmpDAO() {
		return tblMchtBaseInfTmpTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpTmpDAO
	 *            the tblMchtBaseInfTmpTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpTmpDAO(
			ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO) {
		this.tblMchtBaseInfTmpTmpDAO = tblMchtBaseInfTmpTmpDAO;
	}

	/**
	 * @return the tblMchtSettleInfTmpTmpDAO
	 */
	public ITblMchtSettleInfTmpTmpDAO getTblMchtSettleInfTmpTmpDAO() {
		return tblMchtSettleInfTmpTmpDAO;
	}

	/**
	 * @param tblMchtSettleInfTmpTmpDAO
	 *            the tblMchtSettleInfTmpTmpDAO to set
	 */
	public void setTblMchtSettleInfTmpTmpDAO(
			ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO) {
		this.tblMchtSettleInfTmpTmpDAO = tblMchtSettleInfTmpTmpDAO;
	}

	@SuppressWarnings("unchecked")
	public String checkMchtInfo(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp) {
		// 首先检验营业执照，税务登记证， 法人身份证，商户账户账号是否已经存在licenceNo faxNo identityNo
		// settleAcct
		// 商户账户账号
		String sql = "select B.MCHT_NO,trim(MCHT_NM),"
				+ "TRIM(LICENCE_NO),TRIM(FAX_NO),TRIM(IDENTITY_NO),substr(TRIM(SETTLE_ACCT),2) "
				+ "from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S where "
				+ "(substr(TRIM(SETTLE_ACCT),2) = '"
				+ tblMchtSettleInfTmpTmp.getSettleAcct().trim() + "' OR "
				+ " TRIM(IDENTITY_NO) = '"
				+ tblMchtBaseInfTmpTmp.getIdentityNo().trim() + "' OR "
				+ " TRIM(FAX_NO) = '" + tblMchtBaseInfTmpTmp.getFaxNo().trim()
				+ "' OR " + " TRIM(LICENCE_NO) = '"
				+ tblMchtBaseInfTmpTmp.getLicenceNo().trim()
				+ "') AND B.MCHT_NO = S.MCHT_NO " + " AND TRIM(BANK_NO) = '"
				+ tblMchtBaseInfTmpTmp.getBankNo().trim() + "'"
				+ " and trim(b.mcht_no) != '"+ tblMchtBaseInfTmpTmp.getMchtNo().trim() +"' ";
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(
				sql);
		if (null != list && !list.isEmpty() && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			String reMsg = "CZ存在商户[<font color='red'>" + obj[0] + "-" + obj[1]
					+ "</font>]等" + String.valueOf(list.size()) + "家商户与该商户的";
			if (tblMchtBaseInfTmpTmp.getLicenceNo().trim().equals(obj[2])) {
				reMsg += "<font color='green'>营业执照编号</font>";
			} else if (tblMchtBaseInfTmpTmp.getFaxNo().trim().equals(obj[3])) {
				reMsg += "<font color='green'>税务登记证号码</font>";
			} else if (tblMchtBaseInfTmpTmp.getIdentityNo().trim()
					.equals(obj[4])) {
				reMsg += "<font color='green'>法人证件编号</font>";
			} else if (tblMchtSettleInfTmpTmp.getSettleAcct().trim()
					.equals(obj[5])) {
				reMsg += "<font color='green'>商户账户账号</font>";
			} else {
				reMsg += "<font color='green'>部分关键信息</font>";
			}
			return reMsg += "相同,请核实相关商户录入信息.";
		}
		return Constants.SUCCESS_CODE;
	}

	@SuppressWarnings("unchecked")
	public String checkMchtTmpInfo(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp) {
		// 首先检验营业执照，税务登记证， 法人身份证，商户账户账号是否已经存在licenceNo faxNo identityNo
		// settleAcct
		// 商户账户账号
		String sql = "select B.MCHT_NO,trim(MCHT_NM),"
				+ "TRIM(LICENCE_NO),TRIM(FAX_NO),TRIM(IDENTITY_NO),substr(TRIM(SETTLE_ACCT),2) "
				+ "from TBL_MCHT_BASE_INF_TMP_TMP B,TBL_MCHT_SETTLE_INF_TMP_TMP S where "
				+ "(substr(TRIM(SETTLE_ACCT),2) = '"
				+ tblMchtSettleInfTmpTmp.getSettleAcct().trim() + "' OR "
				+ " TRIM(IDENTITY_NO) = '"
				+ tblMchtBaseInfTmpTmp.getIdentityNo().trim() + "' OR "
				+ " TRIM(FAX_NO) = '" + tblMchtBaseInfTmpTmp.getFaxNo().trim()
				+ "' OR " + " TRIM(LICENCE_NO) = '"
				+ tblMchtBaseInfTmpTmp.getLicenceNo().trim()
				+ "') AND B.MCHT_NO = S.MCHT_NO " + " AND TRIM(BANK_NO) = '"
				+ tblMchtBaseInfTmpTmp.getBankNo().trim() + "'"
				+ " and b.mcht_no !='" + tblMchtBaseInfTmpTmp.getMchtNo() +"'";;
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(
				sql);
		if (null != list && !list.isEmpty() && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			String reMsg = "CZ存在商户[<font color='red'>" + obj[0] + "-" + obj[1]
					+ "</font>]等" + String.valueOf(list.size()) + "家商户与该商户的";
			if (tblMchtBaseInfTmpTmp.getLicenceNo().trim().equals(obj[2])) {
				reMsg += "<font color='green'>营业执照编号</font>";
			} else if (tblMchtBaseInfTmpTmp.getFaxNo().trim().equals(obj[3])) {
				reMsg += "<font color='green'>税务登记证号码</font>";
			} else if (tblMchtBaseInfTmpTmp.getIdentityNo().trim()
					.equals(obj[4])) {
				reMsg += "<font color='green'>法人证件编号</font>";
			} else if (tblMchtSettleInfTmpTmp.getSettleAcct().trim()
					.equals(obj[5])) {
				reMsg += "<font color='green'>商户账户账号</font>";
			} else {
				reMsg += "<font color='green'>部分关键信息</font>";
			}
			return reMsg += "相同,请核实相关商户录入信息.";
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String accept(String mchntId) throws IllegalAccessException,
			InvocationTargetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upload(List<File> imgFile, List<String> imgFileFileName,
			String imagesId, String mcht,String category) {
		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;

		try {
			String fileName = "";
			int fileNameIndex = 0;

			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);

			basePath = basePath.replace("\\", "/");
			if(StringUtil.isNotEmpty(mcht)){
				basePath+=mcht+"/"+category+"/";
			}else{
				basePath+="addTmp/"+category+"/";
			}

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
					writeFile.getParentFile().mkdirs();
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

	@Override
	public void save(TblMchtBaseInfTmp baseInfTmp) {

	}

	@Override
	public String saveTmpPosf(TblMchtBaseInfTmp baseInfTmp) {
		if (tblMchtBaseInfTmpDAO.get(baseInfTmp.getMchtNo()) != null) {
			return "您自定义的商户号已经存在";
		}
		tblMchtBaseInfTmpDAO.save(baseInfTmp);
		return Constants.SUCCESS_CODE;

	}

	@Override
	public void delete(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmpUpd,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmpUpd) {
		tblMchtBaseInfTmpTmpDAO.delete(tblMchtBaseInfTmpTmpUpd);
		tblMchtSettleInfTmpTmpDAO.delete(tblMchtSettleInfTmpTmpUpd);
	}

	@Override
	public void update(String mchntId,String bankNo) {
		TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp = tblMchtBaseInfTmpTmpDAO.get(mchntId);
		tblMchtBaseInfTmpTmp.setMchtStatus("1");
		tblMchtBaseInfTmpTmp.setBankNo(bankNo);
		tblMchtBaseInfTmpTmpDAO.update(tblMchtBaseInfTmpTmp);
		
		TblMchtBaseInfTmp tblMchtBaseInfTmp = tblMchtBaseInfTmpDAO.get(mchntId);
		tblMchtBaseInfTmp.setMchtStatus("B");
		tblMchtBaseInfTmp.setBankNo(bankNo);
		tblMchtBaseInfTmpDAO.update(tblMchtBaseInfTmp);
	}

	@Override
	public void update(TblMchtBaseInfTmp baseInfTmp,
			TblMchtBaseInfTmpTmp baseInfTmpTmp) {
		tblMchtBaseInfTmpTmpDAO.saveOrUpdate(baseInfTmpTmp);
		tblMchtBaseInfTmpDAO.saveOrUpdate(baseInfTmp);
	}

	@Override
	public String approveRecord(String mchntId, String approveInfo,
			String mchtStatus, String bankNo, String oprId)
			throws IllegalAccessException, InvocationTargetException {
		// 记录退回信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,
				CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setBrhId(bankNo);
		refuse.setOprId(oprId);

		// 获得退回信息
		refuse.setRefuseInfo(approveInfo);
		refuse.setRefuseType(approveInfo);

		tblMchntRefuseDAO.save(refuse);
		return null;
	}

	public TblMchntRefuseDAO getTblMchntRefuseDAO() {
		return tblMchntRefuseDAO;
	}

	public void setTblMchntRefuseDAO(TblMchntRefuseDAO tblMchntRefuseDAO) {
		this.tblMchntRefuseDAO = tblMchntRefuseDAO;
	}
}
