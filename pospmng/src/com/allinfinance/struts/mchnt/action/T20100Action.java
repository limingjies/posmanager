package com.allinfinance.struts.mchnt.action;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.mchnt.T20401BO;
import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.bo.term.TblTermKeyBO;
import com.allinfinance.bo.term.TblTermZmkInfBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.TblTermKeyPK;
import com.allinfinance.po.TblTermZmkInf;
import com.allinfinance.po.TblTermZmkInfPK;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.FileFilter;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.JSONBean;
import com.allinfinance.system.util.LogUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T20100Action extends BaseSupport{

	private static final long serialVersionUID = 1L;
	public ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	T20401BO t20401BO = (T20401BO) ContextUtil.getBean("T20401BO");
	private T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
	public String add() {

		try {
			String idStr = "848" + areaNo + mcc;
			if(StringUtil.isNull(mchtNoBySelf)) {
				mchtNo = GenerateNextId.getMchntId(idStr);
			} else {
				if(tblMchtBaseInfTmpDAO.get(mchtNoBySelf) != null){
					return returnService("该商户号在间联表已存在");
				}
				if(t20401BO.get(mchtNoBySelf) != null){
					return returnService("该商户号与直联商户重复");
				}
				if (mchtNoBySelf.length() != 15) {
					return returnService("自定义商户编号应为15位的数字");
				}
				if (!mchtNoBySelf.startsWith(idStr)){
					return returnService("自定义商户号录入错误，编号规则应为["+idStr+"xxxx]");
				}
				mchtNo = mchtNoBySelf;
			}
			
			
			
			
			//需要检查
			if (!StringUtil.isNull(checkIds) && checkIds.equals("T")) {
				//首先检验营业执照，税务登记证， 法人身份证，商户账户账号是否已经存在licenceNo faxNo  identityNo settleAcct
				//商户账户账号
				String sql = "select B.MCHT_NO,trim(MCHT_NM)," +
					"TRIM(LICENCE_NO),TRIM(FAX_NO),TRIM(IDENTITY_NO),substr(TRIM(SETTLE_ACCT),2) " +
					"from TBL_MCHT_BASE_INF_TMP B,TBL_MCHT_SETTLE_INF_TMP S where " +
					"(substr(TRIM(SETTLE_ACCT),2) = '" + settleAcct.trim() + "' OR " + 
					" TRIM(IDENTITY_NO) = '" + identityNo.trim() + "' OR " + 
					" TRIM(FAX_NO) = '" + faxNo.trim() + "' OR " + 
					" TRIM(LICENCE_NO) = '" + licenceNo.trim() + "') AND B.MCHT_NO = S.MCHT_NO " +
					" AND TRIM(BANK_NO) = '" + bankNo.trim() + "'";
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

			TblMchtBaseInfTmp tblMchtBaseInfTmp = buildTmpMchtBaseInfo();
			tblMchtBaseInfTmp.setReserved(idOtherNo);
			tblMchtBaseInfTmp.setRislLvl(tblMchtBaseInfTmp.getRislLvl());
			
			if(tblMchtBaseInfTmp.getMchtGroupFlag().equals("3")){
				tblMchtBaseInfTmp.setMchtGroupId(tblMchtBaseInfTmp.getMchtGroupId());
			}else{
				tblMchtBaseInfTmp.setMchtGroupId("-");
			}
			
			tblMchtBaseInfTmp.setMchtLvl("2");// 2-普通商户
			tblMchtBaseInfTmp.setSaAction("0");

			tblMchtBaseInfTmp.setCupMchtFlg("0");
			tblMchtBaseInfTmp.setDebMchtFlg("0");
			tblMchtBaseInfTmp.setCreMchtFlg("0");
			tblMchtBaseInfTmp.setCdcMchtFlg("0");

			tblMchtBaseInfTmp.setManagerTel("1");

			TblMchtSettleInfTmp tblMchtSettleInfTmp = buildTmpMchtSettleInfo();
			if (StringUtil.isNull(settleBankNo)) {
				tblMchtSettleInfTmp.setSettleBankNo(" ");
			}
			tblMchtSettleInfTmp.setFeeDiv1("1");
			tblMchtSettleInfTmp.setFeeDiv2("2");
			if(feeDiv3 != null && !feeDiv3.equals(""))
				tblMchtSettleInfTmp.setFeeDiv3(feeDiv3);
			else
				tblMchtSettleInfTmp.setFeeDiv3("3");
			
			//这里新加的终端添加
			buildTermInfo(tblMchtBaseInfTmp.getMchtNo());
			
			// 将ACQ_INST_ID存银联机构号，并把项目里所有以前用到ACQ_INST_ID的地方改成BANK_NO
			T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
			TblBrhInfo tblBrhInfo = t10101BO.get(tblMchtBaseInfTmp.getBankNo());
			tblMchtBaseInfTmp.setAcqInstId(tblBrhInfo.getCupBrhId());
			
			//传送页面营业时间
			tblMchtBaseInfTmp.setCloseTime(CommonFunction.timeFormat(tblMchtBaseInfTmp.getCloseTime()));
			tblMchtBaseInfTmp.setOpenTime(CommonFunction.timeFormat(tblMchtBaseInfTmp.getOpenTime()));
			
			
			//by ctz   商户黑名单校验
			if(checkBlackMcht(tblMchtBaseInfTmp,tblMchtSettleInfTmp)){
				return returnService("该部分商户关键信息与黑名单商户信息一致！");
			}
			
			
			rspCode = tblMchntService.saveTmp(tblMchtBaseInfTmp, tblMchtSettleInfTmp);

//			//文件改名
//			if (!StringUtil.isNull(hasUpload) && !"0".equals(hasUpload)) {
//				String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
//				basePath = basePath.replace("\\", "/");
//				File fl = new File(basePath);
//				FileFilter filter = new FileFilter(imagesId);
//				File[] files = fl.listFiles(filter);
//				for (File file : files) {
//					file.renameTo(new File(basePath + file.getName().replaceAll(imagesId, mchtNo)));
//				}
//			}
			
			
			
			
			
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);
			basePath = basePath.replace("\\", "/");
			String basePathNew=basePath+tblMchtBaseInfTmp.getMchtNo()+"/";
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
					file.renameTo(new File(basePathNew + file.getName().replaceAll(imagesId, tblMchtBaseInfTmp.getMchtNo())));//文件移动
				}
			}
			
			
			
			
			LogUtil.log(this.getClass().getSimpleName(), getOperator().getOprId(), "save:" + rspCode, tblMchtBaseInfTmp);
			LogUtil.log(this.getClass().getSimpleName(), getOperator().getOprId(), "save:" + rspCode, tblMchtSettleInfTmp);
			
			if (Constants.SUCCESS_CODE.equals(rspCode)) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "新增商户信息成功，商户编号[" + mchtNo + "]");
			} else {
				return returnService(rspCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
	}

	/**
	 * 构造临时商户清算信息
	 */
	private TblMchtSettleInfTmp buildTmpMchtSettleInfo() throws IllegalAccessException, InvocationTargetException {
		TblMchtSettleInfTmp tblMchtSettleInfTmp = new TblMchtSettleInfTmp();
		BeanUtils.copyProperties(tblMchtSettleInfTmp, this);
		tblMchtSettleInfTmp.setMchtNo(mchtNo);

		tblMchtSettleInfTmp.setSettleAcct(clearType.substring(0,1) + settleAcct);
		
		tblMchtSettleInfTmp.setOpenStlno(openStlno);
		
		tblMchtSettleInfTmp.setSettleType("1");
		tblMchtSettleInfTmp.setRateFlag("-");
		tblMchtSettleInfTmp.setFeeType("3"); //分段收费
		tblMchtSettleInfTmp.setFeeFixed("-");
		tblMchtSettleInfTmp.setFeeMaxAmt("0");
		tblMchtSettleInfTmp.setFeeMinAmt("0");

		tblMchtSettleInfTmp.setFeeDiv1("-");
		tblMchtSettleInfTmp.setFeeDiv2("-");
		tblMchtSettleInfTmp.setFeeDiv3("-");

		//是否自动清算
		if (!StringUtil.isNull(autoStlFlg)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(autoStlFlg)) {
			tblMchtSettleInfTmp.setAutoStlFlg("1");
		} else {
			tblMchtSettleInfTmp.setAutoStlFlg("0");
		}
		//退货是否返还手续费
		if (StringUtil.isNull(feeBackFlg)
				|| TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(feeBackFlg)) {
			tblMchtSettleInfTmp.setFeeBackFlg("1");
		} else {
			tblMchtSettleInfTmp.setFeeBackFlg("1");
		}


		tblMchtSettleInfTmp.setFeeRate(discCode);

		// 记录修改时间
		tblMchtSettleInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录创建时间
		tblMchtSettleInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		// 内部账户号
		tblMchtSettleInfTmp.setSettleAcctMid(settleAcctMid);
				
		return tblMchtSettleInfTmp;
	}

	/**
	 * 构造临时商户基本信息
	 */
	private TblMchtBaseInfTmp buildTmpMchtBaseInfo() throws IllegalAccessException, InvocationTargetException {
		TblMchtBaseInfTmp tblMchntInfoTmp = new TblMchtBaseInfTmp();
		BeanUtils.copyProperties(tblMchntInfoTmp, this);
		tblMchntInfoTmp.setEngName(engName.length()>40?"":engName);
		//tblMchntInfoTmp.setLicenceEndDate(Constants.DEFAULT);
		tblMchntInfoTmp.setMchtNo(mchtNo);
		tblMchntInfoTmp.setBankNo(bankNo);
		tblMchntInfoTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK);
		tblMchntInfoTmp.setSettleAreaNo(areaNo);
		// 已经在账户系统开户
		tblMchntInfoTmp.setMchtFunction(mchtFunction);
		
		//是否支持无磁无密交易
		if (!StringUtil.isNull(passFlag)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(passFlag)) {
			tblMchntInfoTmp.setPassFlag("1");
		} else {
			tblMchntInfoTmp.setPassFlag("0");
		}
		//是否支持人工授权交易
		if (!StringUtil.isNull(manuAuthFlag)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(manuAuthFlag)) {
			tblMchntInfoTmp.setManuAuthFlag("1");
		} else {
			tblMchntInfoTmp.setManuAuthFlag("0");
		}
		//是否支持折扣消费
		if (!StringUtil.isNull(discConsFlg)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(discConsFlg)) {
			tblMchntInfoTmp.setDiscConsFlg("1");
		} else {
			tblMchntInfoTmp.setDiscConsFlg("0");
		}
		
		//是否仅营业时间内交易
		if (!StringUtil.isNull(mchtMngMode)
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(mchtMngMode)) {
			tblMchntInfoTmp.setMchtMngMode("1");
		} else {
			tblMchntInfoTmp.setMchtMngMode("0");
		}
		//申请日期
		tblMchntInfoTmp.setApplyDate(CommonFunction.getCurrentDate());

		// 记录修改时间
		tblMchntInfoTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录创建时间
		tblMchntInfoTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		// 记录修改人
		tblMchntInfoTmp.setUpdOprId("");
		// 记录创建人
		tblMchntInfoTmp.setCrtOprId(getOperator().getOprId());
		return tblMchntInfoTmp;

	}

	private boolean checkBlackMcht(TblMchtBaseInfTmp tmp,
			TblMchtSettleInfTmp settleTmp) {

		StringBuffer whereSql = new StringBuffer(" where 1=1 and ( ");
	    whereSql.append("  b.mcht_nm").append(" = ").append("'").append(tmp.getMchtNm()).append("' ");
	    if(StringUtil.isNotEmpty(tmp.getLicenceNo())&&(!"无".equals(tmp.getLicenceNo()))&&(!"0".equals(tmp.getLicenceNo()))) {
	    	whereSql.append(" or b.licence_no").append(" = ").append("'").append(tmp.getLicenceNo()).append("' ");
	    }
	    if(StringUtil.isNotEmpty(tmp.getFaxNo())&&(!"无".equals(tmp.getFaxNo()))&&(!"0".equals(tmp.getFaxNo()))) {
	    	whereSql.append(" or b.fax_no").append(" = ").append("'").append(tmp.getFaxNo()).append("' ");
	    }
	    if(StringUtil.isNotEmpty(tmp.getManager())&&StringUtil.isNotEmpty(tmp.getArtifCertifTp())&&StringUtil.isNotEmpty(tmp.getIdentityNo())) {
	    	whereSql.append(" or (b.manager ='").append(tmp.getManager()).
	    	append("' and b.artif_certif_tp ='").append(tmp.getArtifCertifTp()).
	    	append("' and b.identity_no ='").append(tmp.getIdentityNo()).
	    	append("' ) ");
	    }
	    if(StringUtil.isNotEmpty(settleTmp.getSettleAcct())) {
	    	whereSql.append(" or c.settle_acct").append(" = ").append("'").append(settleTmp.getSettleAcct()).append("' ");
	    }
	    whereSql.append(" ) "); 
	    String countSql="select count(1) from TBL_RISK_BLACK_MCHT a "
	    		+ " left join TBL_MCHT_BASE_INF b on a.mcht_no=b.MCHT_NO  "
				+ " left join TBL_MCHT_SETTLE_INF c on a.mcht_no=c.MCHT_NO "+whereSql;
	    String count=CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
	    if(Integer.parseInt(count)>0){
	    	return true;
	    }
		return false;
	}
	public String upload(){

		rspCode=t20901BO.upload(imgFile,imgFileFileName,imagesId,null,null);
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

				out.flush();
				out.close();
				dis.close();
				is.close();
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

//	用户要求录入商户同时录入终端-----copy终端的代码-----从这里开始-----

	private void buildTermInfo(String mchnNoNew) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		T3010BO t3010BO = (T3010BO)ContextUtil.getBean("t3010BO");
		Operator operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		
		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(data);
		int len = jsonBean.getArray().size();
		
		for(int i = 0; i < len; i++) {
			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
//			String termID = jsonBean.getJSONDataAt(i).getString("termID"); //终端临时编号
			String txn22New = jsonBean.getJSONDataAt(i).getString("txn22New"); //商户名
			String txn27New = jsonBean.getJSONDataAt(i).getString("txn27New"); //商户英文名
			String termMccNew = jsonBean.getJSONDataAt(i).getString("termMccNew"); //终端MCC码
			String brhIdNew = jsonBean.getJSONDataAt(i).getString("brhIdNew"); //终端所属分行
			String contTelNew = jsonBean.getJSONDataAt(i).getString("contTelNew"); //联系电话
			String propTpNew = jsonBean.getJSONDataAt(i).getString("propTpNew"); //产权属性
			String propInsNmNew = jsonBean.getJSONDataAt(i).getString("propInsNmNew"); //收单服务机构
			String termPara1New = jsonBean.getJSONDataAt(i).getString("termPara1New"); //第三方分成比例
			String connectModeNew = jsonBean.getJSONDataAt(i).getString("connectModeNew"); //连接类型
			String termTpNew = jsonBean.getJSONDataAt(i).getString("termTpNew"); //终端类型
			String financeCard1New = "";
			String financeCard2New = "";
			String financeCard3New = "";
			
			String termVerNew = jsonBean.getJSONDataAt(i).getString("termVerNew"); //固话POS版本号
			String termAddrNew = jsonBean.getJSONDataAt(i).getString("termAddrNew"); //终端安装地址
			String txn14New = " "; //NAC电话1
			String txn15New = " "; //NAC电话2
			String txn16New = " "; //NAC电话3
			String bindTel1New = jsonBean.getJSONDataAt(i).getString("bindTel1New"); //绑定电话1
			String bindTel2New = jsonBean.getJSONDataAt(i).getString("bindTel2New"); //绑定电话2
			String bindTel3New = jsonBean.getJSONDataAt(i).getString("bindTel3New"); //绑定电话3
//			String keyDownSignNew = jsonBean.getJSONDataAt(i).getString("keyDownSignNew"); //CA公钥下载标志
//			String paramDownSignNew = jsonBean.getJSONDataAt(i).getString("paramDownSignNew"); //终端参数下载标志
			String icDownSignNew = jsonBean.getJSONDataAt(i).getString("icDownSignNew"); //IC卡参数下载标志
			
			
			String reserveFlag1New = "0";
			String reserveFlag1Temp=jsonBean.getJSONDataAt(i).getString("reserveFlag1New");
			if("true".equals(reserveFlag1Temp))
			{
				reserveFlag1New="1";
			}else{
				reserveFlag1New="0";
			}
			//String reserveFlag1New = jsonBean.getJSONDataAt(i).getString("reserveFlag1New"); //绑定电话
			
			
			String txn35New = jsonBean.getJSONDataAt(i).getString("txn35New"); //分期付款期数
			String txn36New = jsonBean.getJSONDataAt(i).getString("txn36New"); //分期付款限额
			String txn37New = jsonBean.getJSONDataAt(i).getString("txn37New"); //消费单笔上限
			if("".equals(txn37New)) {
				txn37New = "10000000"; // 如果没输入消费单笔上限，则默认1千万。
			}
			String txn38New = jsonBean.getJSONDataAt(i).getString("txn38New"); //退货单笔上限
			String txn39New = jsonBean.getJSONDataAt(i).getString("txn39New"); //转账单笔上限
			String txn40New = jsonBean.getJSONDataAt(i).getString("txn40New"); //退货时间跨度
			String param1New = jsonBean.getJSONDataAt(i).getString("param1New"); //查询
			String param2New = jsonBean.getJSONDataAt(i).getString("param2New"); //预授权
			String param3New = jsonBean.getJSONDataAt(i).getString("param3New"); //预授权撤销
			String param4New = jsonBean.getJSONDataAt(i).getString("param4New"); //预授权完成联机
			String param5New = jsonBean.getJSONDataAt(i).getString("param5New"); //预授权完成撤销
			String param6New = jsonBean.getJSONDataAt(i).getString("param6New"); //消费
			String param7New = jsonBean.getJSONDataAt(i).getString("param7New"); //消费撤销
			String param8New = jsonBean.getJSONDataAt(i).getString("param8New"); //退货
			String param9New = jsonBean.getJSONDataAt(i).getString("param9New"); //离线结算
			String param10New = jsonBean.getJSONDataAt(i).getString("param10New"); //结算调整
			String param11New = jsonBean.getJSONDataAt(i).getString("param11New"); //公司卡转个人卡（财务POS）
			String param12New = jsonBean.getJSONDataAt(i).getString("param12New"); //个人卡转公司卡（财务POS）
			String param13New = jsonBean.getJSONDataAt(i).getString("param13New"); //卡卡转帐
			String param14New = jsonBean.getJSONDataAt(i).getString("param14New"); //上笔交易查询（财务POS）
			String param15New = jsonBean.getJSONDataAt(i).getString("param15New"); //交易查询（财务POS）
			String param16New = jsonBean.getJSONDataAt(i).getString("param16New"); //定向汇款
			String param17New = jsonBean.getJSONDataAt(i).getString("param17New"); //分期付款
			String param18New = jsonBean.getJSONDataAt(i).getString("param18New"); //分期付款撤销
			String param19New = jsonBean.getJSONDataAt(i).getString("param19New"); //代缴费
			String param20New = jsonBean.getJSONDataAt(i).getString("param20New"); //电子现金
			String param21New = jsonBean.getJSONDataAt(i).getString("param21New"); //IC现金充值
			String param22New = jsonBean.getJSONDataAt(i).getString("param22New"); //指定账户圈存
			String param23New = jsonBean.getJSONDataAt(i).getString("param23New"); //非指定账户圈存
			String param24New = jsonBean.getJSONDataAt(i).getString("param24New"); //非接快速支付
			
			TblTermInfTmp tblTermInf = new TblTermInfTmp();
			TblTermInfTmpPK pk = new TblTermInfTmpPK();
			tblTermInf.setId(pk);
			
			if(brhIdNew != null && !brhIdNew.trim().equals("")) {
				pk.setTermId(t3010BO.getTermId(brhIdNew));
			} else {
				continue;
			}
	
			tblTermInf.setTermBranch(brhIdNew);
			tblTermInf.setTermAddr(termAddrNew);
			tblTermInf.setMchtCd(mchnNoNew);
			tblTermInf.setTermSta(TblTermInfConstants.TERM_STA_INIT);
			tblTermInf.setTermSignSta(TblTermInfConstants.TERM_SIGN_DEFAULT);
			tblTermInf.setTermMcc(termMccNew);
			tblTermInf.setKeyDownSign(TblTermInfConstants.CHECKED);
			tblTermInf.setParamDownSign(TblTermInfConstants.CHECKED);
			tblTermInf.setSupportIc(TblTermInfConstants.CHECKED);
			tblTermInf.setIcDownSign(TblTermInfConstants.CHECKED);
			tblTermInf.setReserveFlag1(isChecked(reserveFlag1New));
			tblTermInf.setConnectMode(connectModeNew);
			tblTermInf.setContTel(contTelNew);
//			tblTermInf.setOprNm(oprNmNew);
//			tblTermInf.setEquipInvId(equipInvIdNew);
//			tblTermInf.setEquipInvNm(equipInvNmNew);
			tblTermInf.setOprNm(null);
			tblTermInf.setEquipInvId("NN");//机具及密码键盘绑定标识，第一位机具，第二位密码键盘，Y表示绑定，N表示未绑定
			tblTermInf.setEquipInvNm(null);
			tblTermInf.setPropTp(propTpNew);
			tblTermInf.setPropInsNm(propInsNmNew);
			tblTermInf.setTermPara1(termPara1New);
			tblTermInf.setTermBatchNm("000001");
			tblTermInf.setTermTp(termTpNew);
			if(termVerNew != null && !termVerNew.equals("")) { tblTermInf.setMisc2(termVerNew); }
			if(bindTel1New!=null) {
				tblTermInf.setBindTel1(bindTel1New);
			} else {
				bindTel1New = " ";
				tblTermInf.setBindTel1(bindTel1New);
			}
			if(bindTel2New!=null) {
				tblTermInf.setBindTel2(bindTel2New);
			} else {
				bindTel2New = " ";
				tblTermInf.setBindTel2(bindTel2New);
			}
			if(bindTel3New!=null) {
				tblTermInf.setBindTel3(bindTel3New);
			} else {
				bindTel3New = " ";
				tblTermInf.setBindTel3(bindTel3New);
			}
//			if(txn15New == null) { txn15New = " "; }				
//			if(txn16New == null) { txn16New = " "; }
			if(termTpNew.equals(TblTermInfConstants.TERM_TP_1)) {
				
				tblTermInf.setFinanceCard1(financeCard1New);
				if(financeCard2New!=null) {
					tblTermInf.setFinanceCard2(financeCard2New);
//				} else {
//					financeCard2New = " ";
				}
				if(financeCard3New!=null) {
					tblTermInf.setFinanceCard3(financeCard3New);
//				} else {
//					financeCard3New = " ";
				}	
			} else {
				financeCard1New = " ";
				financeCard2New = " ";
				financeCard3New = " ";
			}
			if(termTpNew.equals(TblTermInfConstants.TERM_TP_3)) {
				param1New = TblTermInfConstants.CHECKED;
				param2New = TblTermInfConstants.CHECKED;
				param3New = TblTermInfConstants.CHECKED;
				param4New = TblTermInfConstants.CHECKED;
				param5New = TblTermInfConstants.CHECKED;
				param6New = TblTermInfConstants.CHECKED;
				param7New = TblTermInfConstants.CHECKED;
				param8New = TblTermInfConstants.CHECKED;
				param9New = TblTermInfConstants.CHECKED;
				param10New = TblTermInfConstants.CHECKED;
				param11New = TblTermInfConstants.CHECKED;
				param12New = TblTermInfConstants.CHECKED;
				param13New = TblTermInfConstants.CHECKED;
				param14New = TblTermInfConstants.CHECKED;
				param15New = TblTermInfConstants.CHECKED;
				param16New = TblTermInfConstants.CHECKED;
				param17New = TblTermInfConstants.CHECKED;
				param18New = TblTermInfConstants.CHECKED;
				param19New = TblTermInfConstants.CHECKED;
				param20New = TblTermInfConstants.CHECKED;
				param21New = TblTermInfConstants.CHECKED;
				param22New = TblTermInfConstants.CHECKED;
				param23New = TblTermInfConstants.CHECKED;
				param24New = TblTermInfConstants.CHECKED;
			}			
			tblTermInf.setRecUpdOpr(operator.getOprId());
			tblTermInf.setRecCrtOpr(operator.getOprId());
			tblTermInf.setTermStlmDt(CommonFunction.getCurrentDate());
			
			StringBuffer result = new StringBuffer();
			result.append("14").append(CommonFunction.fillString(txn14New.trim(), ' ', 14, true)).append("|")
				.append("15").append(CommonFunction.fillString(txn15New.trim(), ' ', 14, true)).append("|")
				.append("16").append(CommonFunction.fillString(txn16New.trim(), ' ', 14, true)).append("|")
				.append("22").append(CommonFunction.fillString(txn22New.trim(), ' ', 40, true)).append("|").append("26");			
			StringBuffer txnCode = new StringBuffer();
			String txnCode1=isChecked(param1New)+isChecked(param2New)+isChecked(param3New)+isChecked(param4New);
			String txnCode2=isChecked(param5New)+isChecked(param6New)+isChecked(param7New)+isChecked(param8New);
			String txnCode3=isChecked(param9New)+isChecked(param10New)+isChecked(param11New)+isChecked(param12New);
			String txnCode4=isChecked(param13New)+isChecked(param14New)+isChecked(param15New)+isChecked(param16New);
			String txnCode5=isChecked(param17New)+isChecked(param18New)+isChecked(param19New)+isChecked(param20New);
			String txnCode6=isChecked(param21New)+isChecked(param22New)+isChecked(param23New)+isChecked(param24New);
//			String txnCode7=isChecked(param25New)+isChecked(param26New)+isChecked(param27New)+isChecked(param28New);
//			String txnCode8=isChecked(param29New)+isChecked(param30New)+isChecked(param31New)+isChecked(param32New);
			String txnCode7=isChecked(param21New)+isChecked(param22New)+isChecked(param23New)+isChecked(param24New);
			String txnCode8=isChecked("0")+isChecked("0")+isChecked("0")+isChecked("0");
			txnCode.append(Integer.toHexString(Integer.valueOf(txnCode1,2)))
			.append(Integer.toHexString(Integer.valueOf(txnCode2,2)))
			.append(Integer.toHexString(Integer.valueOf(txnCode3,2)))
			.append(Integer.toHexString(Integer.valueOf(txnCode4,2)))
			.append(Integer.toHexString(Integer.valueOf(txnCode5,2)))
			.append(Integer.toHexString(Integer.valueOf(txnCode6,2)))
			.append(Integer.toHexString(Integer.valueOf(txnCode7,2)))
			.append(Integer.toHexString(Integer.valueOf(txnCode8,2)));
			result.append(CommonFunction.fillString(txnCode.toString(), '0', 16, true));
			
			result.append("|").append("27").append(CommonFunction.fillString(txn27New==null?"":txn27New.trim(), ' ', 40, true)).append("|")
				.append("28").append(CommonFunction.fillString(mchnNoNew, ' ', 15, true)).append("|")
				.append("29").append(CommonFunction.fillString(pk.getTermId(), ' ', 8, true)).append("|")
				.append("31").append("000001").append("|")
				.append("32").append(CommonFunction.fillString(financeCard1New, ' ', 19, true)).append("|")
				.append("33").append(CommonFunction.fillString(financeCard2New, ' ', 19, true)).append("|")
				.append("34").append(CommonFunction.fillString(financeCard3New, ' ', 19, true)).append("|")
				.append("35").append(CommonFunction.fillString(txn35New==null?" ":txn35New, ' ', 2, true)).append("|")
				.append("36").append(txn36New==null?CommonFunction.fillString(txn36New, ' ', 12, true):translate(txn36New)).append("|")
				.append("37").append(txn37New==null?CommonFunction.fillString(txn37New, ' ', 12, true):translate(txn37New)).append("|")
				.append("38").append(txn38New==null?CommonFunction.fillString(txn38New, ' ', 12, true):translate(txn38New)).append("|")
				.append("39").append(txn39New==null?CommonFunction.fillString(txn39New, ' ', 12, true):translate(txn39New)).append("|")
				.append("40").append(CommonFunction.fillString(txn40New==null?" ":txn40New, ' ', 2, true));
			
			String termPara = result.toString();
			tblTermInf.setTermPara(termPara);
			
			//判断是否勾选交易
			int value = checkTxn(param1New)+checkTxn(param2New)+checkTxn(param3New)+checkTxn(param4New)
				+checkTxn(param5New)+checkTxn(param6New)+checkTxn(param7New)+checkTxn(param8New)
				+checkTxn(param9New)+checkTxn(param10New)+checkTxn(param11New)+checkTxn(param12New)
				+checkTxn(param13New)+checkTxn(param14New)+checkTxn(param15New)+checkTxn(param16New)
				+checkTxn(param17New)+checkTxn(param18New)+checkTxn(param19New)+checkTxn(param20New)
				+checkTxn(param21New)+checkTxn(param22New)+checkTxn(param23New)+checkTxn(param24New);
			if(value == 0) continue;
			
			//判断财务POS交易勾选
//			int fposValue = checkTxn(param1New)+checkTxn(param11New)+checkTxn(param12New)+checkTxn(param13New)
//			+checkTxn(param14New)+checkTxn(param15New);
//			if((fposValue == 0 || value > 6 || value - fposValue != 0) && termTpNew.equals(TblTermInfConstants.CHECKED)) continue;
			
			//判断是否支持IC卡
			if(checkTxn(icDownSignNew) != 1) {
				int value1 = checkTxn(param20New)+checkTxn(param21New)+checkTxn(param22New)+checkTxn(param23New)+checkTxn(param24New);
				if(value1 > 0) continue;
			}
			
			//判断是否电子现金是否勾选
			if(checkTxn(param20New) != 1) {
				int value2 = checkTxn(param21New)+checkTxn(param22New)+checkTxn(param23New)+checkTxn(param24New);
				if(value2 > 0) continue;
			}
			
			t3010BO.add(tblTermInf);
			
			TblTermZmkInfBO tblTermZmkInfBO = (TblTermZmkInfBO) ContextUtil.getBean("TblTermZmkInfBO");
			TblTermKeyBO tblTermKeyBO = (TblTermKeyBO) ContextUtil.getBean("TblTermKeyBO");
			// 只加入商户号和终端号字段，其他字段后台C程序更新
			TblTermKey tblTermKey = new TblTermKey();
			TblTermKeyPK keyPK = new TblTermKeyPK();
			keyPK.setTermId(tblTermInf.getId().getTermId());
			keyPK.setMchtCd(mchnNoNew);
			tblTermKey.setId(keyPK);
			tblTermKey.setKeyIndex(SysParamUtil.getParam("KEY_INDEX"));
			BeanUtils.setNullValueWithLine(tblTermKey);
			tblTermKeyBO.saveOrUpdate(tblTermKey);
			log("插入POS终端密钥表成功");
			
			// 终端密钥下载时更新该条记录
			TblTermZmkInf tblTermZmkInf = new TblTermZmkInf();
			TblTermZmkInfPK zmkPK = new TblTermZmkInfPK();
			zmkPK.setMchtNo(mchnNoNew);
			zmkPK.setTermId(tblTermInf.getId().getTermId());
			tblTermZmkInf.setId(zmkPK);
			tblTermZmkInf.setKeyIndex(SysParamUtil.getParam("ZMK_KEY_INDEX"));
			tblTermZmkInf.setRandom(com.allinfinance.system.util.CommonFunction.getRandom(6));
			BeanUtils.setNullValueWithLine(tblTermZmkInf);
			tblTermZmkInfBO.saveOrUpdate(tblTermZmkInf);
			log("插入POS终端密钥索引表成功");
		}
	}

	public String isChecked(String param) {
		
		return param;
//		return param==null?TblTermInfConstants.UNCHECKED:TblTermInfConstants.CHECKED;
	}
	
	public String translate(String money) {
		
		if(money.contains("."))
			return CommonFunction.fillString(money.replaceAll("\\.", ""), ' ', 12, true);
		else
			money = CommonFunction.fillString(money, '0', money.length()+2, true);
		return CommonFunction.fillString(money, ' ', 12, true);
	}
	
	public int checkTxn(String param) {
		
		return param==null?0:1;
	}

	//这里装的是终端信息
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
//	用户要求录入商户同时录入终端-----copy终端的代码-----到这里结束-----
	

	public List<File> getImgFile() {
		return imgFile;
	}

	public void setImgFile(List<File> imgFile) {
		this.imgFile = imgFile;
	}

	public List<String> getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(List<String> imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	// 文件集合
	private List<File> imgFile;
	
	// 文件名称集合
	private List<String> imgFileFileName;
	private String imagesId;

	private String hasUpload;

	public String getHasUpload() {
		return hasUpload;
	}

	public void setHasUpload(String hasUpload) {
		this.hasUpload = hasUpload;
	}

	public String getImagesId() {
		return imagesId;
	}

	public void setImagesId(String imagesId) {
		this.imagesId = imagesId;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isSuccess() {
		return success;
	}


	// primary key
	private java.lang.String mchtNo;

	// fields
	private java.lang.String mchtNm;
	private java.lang.String rislLvl;
	private java.lang.String mchtLvl;
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
	private java.lang.String areaNo;
	private java.lang.String settleAreaNo;
	private java.lang.String addr;
	private java.lang.String homePage;
	private java.lang.String mcc;
	private java.lang.String tcc;
	private java.lang.String etpsAttr;
	private java.lang.String mngMchtId;
	private java.lang.String mchtGrp;
	private java.lang.String mchtAttr;
	private java.lang.String mchtGroupFlag;
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

	private java.lang.String settleType;
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

	private java.lang.String speSettleTp;
	private java.lang.String speSettleLv;
	private java.lang.String speSettleDs;
	private java.lang.String feeBackFlg;

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

	public java.lang.String getMchtLvl() {
		return mchtLvl;
	}

	public void setMchtLvl(java.lang.String mchtLvl) {
		this.mchtLvl = mchtLvl;
	}

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

	public java.lang.String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(java.lang.String areaNo) {
		this.areaNo = areaNo;
	}

	public java.lang.String getSettleAreaNo() {
		return settleAreaNo;
	}

	public void setSettleAreaNo(java.lang.String settleAreaNo) {
		this.settleAreaNo = settleAreaNo;
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

	public java.lang.String getMchtGroupFlag() {
		return mchtGroupFlag;
	}

	public void setMchtGroupFlag(java.lang.String mchtGroupFlag) {
		this.mchtGroupFlag = mchtGroupFlag;
	}

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

	public java.lang.String getSettleType() {
		return settleType;
	}

	public void setSettleType(java.lang.String settleType) {
		this.settleType = settleType;
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

	//计费代码
	private String discCode;

	public String getDiscCode() {
		return discCode;
	}

	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	private String mchtNoBySelf;
	private String idOtherNo;
	private String feeTypeFlag;
	private String feeSelfGDName;
	private String feeSelfBLName;
	private String feeMost;
	private String feeLeast;
	private String clearType;

	public String getClearType() {
		return clearType;
	}

	public void setClearType(String clearType) {
		this.clearType = clearType;
	}

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
	public String getMchtNoBySelf() {
		return mchtNoBySelf;
	}

	public void setMchtNoBySelf(String mchtNoBySelf) {
		this.mchtNoBySelf = mchtNoBySelf;
	}

	public java.lang.String getSpeSettleTp() {
		return speSettleTp;
	}

	public void setSpeSettleTp(java.lang.String speSettleTp) {
		this.speSettleTp = speSettleTp;
	}

	public java.lang.String getSpeSettleLv() {
		return speSettleLv;
	}

	public void setSpeSettleLv(java.lang.String speSettleLv) {
		this.speSettleLv = speSettleLv;
	}

	public java.lang.String getSpeSettleDs() {
		return speSettleDs;
	}

	public void setSpeSettleDs(java.lang.String speSettleDs) {
		this.speSettleDs = speSettleDs;
	}

	public java.lang.String getFeeBackFlg() {
		return feeBackFlg;
	}

	public void setFeeBackFlg(java.lang.String feeBackFlg) {
		this.feeBackFlg = feeBackFlg;
	}
	
	public String checkIds;

	public String getCheckIds() {
		return checkIds;
	}

	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}
	
	private String settleAcctMid;
	
	public void setSettleAcctMid(String settleAcctMid) {
		this.settleAcctMid = settleAcctMid;
	}
	
	public String getSettleAcctMid() {
		return settleAcctMid;
	}

	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}
	
}