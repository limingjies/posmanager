package com.allinfinance.struts.base.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblOprInfoConstants;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.Encryption;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:机构维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-7
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10101Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	//机构编号
	private String brhId;
	//机构级别
	private String brhLvl;
	//上级机构编号
	private String upBrhId;
	//机构名称
	private String brhName;
	//机构地址
	private String brhAddr;
	//机构联系电话
	private String brhTelNo;
	//机构邮编
	private String postCode;
	//机构联系人
	private String brhContName;
	//机构银联编号
	private String cupBrhId;
	//机构修改列表
	private String brhDataList;
	// 机构地区码
	private String resv1;
	// POS柜员号
	private String resv2;
	// 机构密钥索引
	private String brhTmkIndex;
	//机构代码
	private String orgNo;
	//人员属性
	private String memProperty;
	//职务类型
	private String jobType;
	//拒绝原因
	private String refuseInfo;
	
	private String resv1_5;
	private String resv1_6;
	private String resv1_7;
	private String resv1_8;
	private String brhFee;
	private String settleFlag;
	private String settleAcctNm;
	private String settleAcct;
	private String settleBankNm;
	private String settleBankNo;
	
	private TblBrhInfo tblBrhInfoUpd;
	private TblBrhSettleInf tblBrhSettleInfUpd;
	private String resv1_5Upd;
	private String resv1_6Upd;
	private String resv1_7Upd;
	private String resv1_8Upd;
	private String brhFeeUpd;
	
	//机构信息BO
	private T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception{
		//try {
			if("add".equals(getMethod())) {			
					rspCode = add();			
			} else if("delete".equals(getMethod())) {
				rspCode = delete();
			} else if("update".equals(getMethod())) {
				rspCode = update();
			} else if("upd".equals(getMethod())) {
				rspCode = upd();
			} else if("accept".equals(getMethod())) {
				rspCode = accept();
			} else if("refuse".equals(getMethod())) {
				rspCode = refuse();
			}	
//		} catch (Exception e) {
//			rspCode = e.getMessage();
//			log("操作员编号：" + operator.getOprId()+ "，对机构的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
//		}
		return rspCode;
	}
	
	private String upd() {
		// TODO Auto-generated method stub
		//判断机构是否存在
		TblBrhInfo tblBrhInfOld =t10101BO.get(getBrhId());
		if(tblBrhInfOld == null) {			
			return ErrorCode.T10101_04;
		}
		//机构地区码+第五位：是否系统级签到+第六位：是否生成对账流水+第七位：是否清算到机构账号
		// +第八位：是否配置分润手续费 +5位分润费率代码（不配置则不加）
		tblBrhInfoUpd.setResv1(tblBrhInfoUpd.getResv1()+resv1_5Upd+resv1_6Upd+resv1_7Upd
				+resv1_8Upd+(StringUtil.isEmpty(brhFeeUpd)?"":brhFeeUpd));
		
		tblBrhInfoUpd.setBrhId(brhId);
		tblBrhInfoUpd.setBrhSta(tblBrhInfOld.getBrhSta());
		tblBrhInfoUpd.setPostCd(tblBrhInfOld.getPostCd());
		tblBrhInfoUpd.setRegDt(tblBrhInfOld.getRegDt());
		tblBrhInfoUpd.setLastUpdOprId(operator.getOprId());
		tblBrhInfoUpd.setLastUpdTs(CommonFunction.getCurrentDateTime());
		tblBrhInfoUpd.setLastUpdTxnId(getTxnId());
		
		tblBrhInfoUpd.setSettleJobType(tblBrhInfOld.getSettleJobType());
//		tblBrhInfoUpd.setStatus(tblBrhInfOld.getStatus());
		tblBrhInfoUpd.setStatus("0");
		tblBrhInfoUpd.setSettleMemProperty(tblBrhInfOld.getSettleMemProperty());
		tblBrhInfoUpd.setCreateNewNo(tblBrhInfOld.getCreateNewNo());
		tblBrhInfoUpd.setSettleOrgNo(tblBrhInfOld.getSettleOrgNo());
		tblBrhSettleInfUpd.setBrhId(brhId);
		
		rspCode=t10101BO.updateSettle(tblBrhInfoUpd,tblBrhSettleInfUpd,operator, tblBrhInfOld);
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			log("更新机构信息成功。操作员编号：" + operator.getOprId());
			//加载操作员所在机构信息
			reloadOprBrhInfo();
		}
		return rspCode;
		
	}
	
	private String compZeroAtF(String oldString,int num)
	{
		int len=oldString.length();
		for(int i=0;i<(num-len);i++)
		{
			oldString="0"+oldString;
		}
		return oldString;
	}
	
	
	/**
	 * 添加机构信息
	 * @throws Exception
	 */
	private String add() throws Exception {
		TblBrhInfo tblBrhInfo = new TblBrhInfo();
		String sqlStr = "select max(BRH_ID) from TBL_BRH_INFO";
		List idDataList = commQueryDAO.findBySQLQuery(sqlStr);
		if(idDataList!=null && (idDataList.isEmpty()||(idDataList.get(0)==null)))
		{
			setBrhId("00001");
		}else{
;			String maxNo=idDataList.get(0).toString();
			Integer max=Integer.parseInt(maxNo);
			max=max+1;
			maxNo=max+"";
			maxNo=compZeroAtF(maxNo,5);
			setBrhId(maxNo);
		}
//		//判断机构是否存在
//		if(t10101BO.get(getBrhId()) != null) {		
//			return ErrorCode.T10101_01;
//		}
		while(t10101BO.get(getBrhId()) != null){
			String maxNo=getBrhId();
			Integer max=Integer.parseInt(maxNo);
			max++;
			maxNo=max+"";
			maxNo=compZeroAtF(maxNo,5);
			setBrhId(maxNo);
		}
		
		if(getOrgNo()!=null&&getOrgNo().length()< 3)
		{
			setOrgNo(compZeroAtF(getOrgNo(),3));
		}
		String tempBrhId=null;
		tempBrhId=getMemProperty()+getOrgNo()+getResv1()+getJobType();
		String sql = "select max(CREATE_NEW_NO) from TBL_BRH_INFO where CREATE_NEW_NO like '"+tempBrhId+"%'";
		List dataList = commQueryDAO.findBySQLQuery(sql);
		if(dataList!=null && (dataList.isEmpty()||(dataList.get(0)==null)))
		{
			tblBrhInfo.setCreateNewNo(tempBrhId+"001");
		}else{
;			String maxNo=dataList.get(0).toString();
			maxNo=maxNo.replace(tempBrhId,"");
			Integer max=Integer.parseInt(maxNo);
			max=max+1;
			maxNo=max+"";
			if(maxNo.length()<3){
				maxNo=compZeroAtF(maxNo,3);
			}
			tblBrhInfo.setCreateNewNo(tempBrhId+maxNo);
		}
//		BeanUtils.copyProperties(tblBrhInfo, this);			
		//状态
		tblBrhInfo.setStatus("0");
		//机构编号
		tblBrhInfo.setBrhId(getBrhId());
		//银联机构编号
		tblBrhInfo.setCupBrhId(getCupBrhId());
		//机构级别
		tblBrhInfo.setBrhLevel(getBrhLvl());
		//机构状态，暂不启用
		tblBrhInfo.setBrhSta("0");
		//机构地区码
//		tblBrhInfo.setResv1(resv1);
		
		//上级机构编号
		tblBrhInfo.setUpBrhId(getUpBrhId());
		
		//机构注册时间
		tblBrhInfo.setRegDt(CommonFunction.getCurrentDate());
		//机构所在地邮政编号
		tblBrhInfo.setPostCd("-");
		//机构所在地址
		tblBrhInfo.setBrhAddr(getBrhAddr());
		//机构名称
		tblBrhInfo.setBrhName(getBrhName());
		//机构联系电话
		tblBrhInfo.setBrhTelNo(getBrhTelNo());
		//机构联系人
		tblBrhInfo.setBrhContName(getBrhContName());
		//最后更新操作员
		tblBrhInfo.setLastUpdOprId(operator.getOprId());
		//最后更新时间
		tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
		//最后更新交易码
		tblBrhInfo.setLastUpdTxnId(getTxnId());
//        机构秘钥索引
//		if(brhTmkIndex.length()<=3) {
//			for(int i=1;i<=(3-brhTmkIndex.length());i++){
//				brhTmkIndex=brhTmkIndex+" ";
//			}
//		}
//		resv2=brhTmkIndex+resv2;
		//POS柜员号
		tblBrhInfo.setResv2(getBrhTmkIndex());
		
		//机构地区码+第五位：是否系统级签到+第六位：是否生成对账流水+第七位：是否清算到机构账号
		// +第八位：是否配置分润手续费 +5位分润费率代码（不配置则不加）
		tblBrhInfo.setResv1(resv1+resv1_5+resv1_6+resv1_7
				+resv1_8+(StringUtil.isEmpty(brhFee)?"":brhFee));
		
		tblBrhInfo.setSettleJobType(getJobType());//职务类型
		tblBrhInfo.setSettleMemProperty(getMemProperty());//人员属性
		tblBrhInfo.setSettleOrgNo(getOrgNo());//机构代码
		
		TblBrhSettleInf tblBrhSettleInf=null;
//		if(CommonsConstants.CHECKED.equals(resv1_7)){
			tblBrhSettleInf=new TblBrhSettleInf();
			tblBrhSettleInf.setBrhId(brhId);
			tblBrhSettleInf.setSettleFlag(settleFlag);
			tblBrhSettleInf.setSettleAcctNm(settleAcctNm);
			tblBrhSettleInf.setSettleAcct(settleAcct);
			tblBrhSettleInf.setSettleBankNm(settleBankNm);
			tblBrhSettleInf.setSettleBankNo(settleBankNo);
			tblBrhSettleInf.setCrtOpr(operator.getOprId());
			tblBrhSettleInf.setCrtTs(CommonFunction.getCurrentDateTime());
//		}
		
		rspCode=t10101BO.add(tblBrhInfo,tblBrhSettleInf);
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			log("添加机构信息成功。操作员编号：" + operator.getOprId()+"，机构编号：" + getBrhId());
			//加载操作员所在机构信息
			reloadOprBrhInfo();
		}
		return rspCode;
	}
	
	/**
	 * 删除机构信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String delete() throws Exception {
		
		String sql = "select * from tbl_opr_info where brh_id = '" + getBrhId() + "' ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		//判断是否有操作员在此机构下
		if(dataList.size() > 0) {
			return ErrorCode.T10101_02;
		}
		sql = "select * from TBL_MCHT_BASE_INF_TMP where BANK_NO = '" + getBrhId() + "' ";
		dataList = commQueryDAO.findBySQLQuery(sql);
		//判断是否有商户在此机构下
		if(dataList.size() > 0) {
			return ErrorCode.T10101_03;
		}
		
		
		
		//删除机构信息
		rspCode=t10101BO.delete(getBrhId());
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			log("删除机构信息成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + getBrhId());
			//加载操作员所在机构信息
			reloadOprBrhInfo();
		}
		return rspCode;
	}
	
	/**
	 * 更新机构信息
	 * @return
	 */
	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getBrhDataList());
		int len = jsonBean.getArray().size();
		List<TblBrhInfo> brhInfoList = new ArrayList<TblBrhInfo>();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblBrhInfo tblBrhInfo = new TblBrhInfo();
			BeanUtils.setObjectWithPropertiesValue(tblBrhInfo,jsonBean,true);
			tblBrhInfo.setBrhSta("0");
			tblBrhInfo.setLastUpdOprId(operator.getOprId());
			tblBrhInfo.setLastUpdTxnId(getTxnId());
			tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
			brhInfoList.add(tblBrhInfo);
		}
		t10101BO.update(brhInfoList);
		log("更新机构信息成功。操作员编号：" + operator.getOprId());
		//加载操作员所在机构信息
		reloadOprBrhInfo();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过机构信息
	 * 1、客户在POSP入网时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  0）、交易码：A0100 子交易码：1
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因
	 * 2、客户在POSP结算信息并更时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  0）、交易码：F0100 子交易码：0
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因 
	 * @return
	 */
	private String accept() throws Exception {
		String returnMsg="<font color='green'>审核通过机构信息成功</font><br><font color='green'>管理员信息添加成功</font>(用户名:<font color='red'>admin</font>;密码:<font color='red'>111111</font>)";
		String returnMsg1="<font color='green'>审核通过机构信息成功</font>";
		
		TblBrhInfo tblBrhInfo = t10101BO.get(brhId);
		if (!"0".equals(tblBrhInfo.getStatus())) {
			writeErrorMsg("<font color='red'>本记录已被其他操作员审核过了，请刷新页面后重试。</font>");
			return null;
		}
		rspCode = checkID();
		if(!Constants.SUCCESS_CODE.equals(rspCode)){
			return rspCode;
		}
		//新增商户管理员信息

        ShTblOprInfo shTblOprInfo=new ShTblOprInfo();
		shTblOprInfo.setMchtBrhFlag("1");
		shTblOprInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		shTblOprInfo.setOprPwd(Encryption.encryptadd(SysParamUtil
				.getParam(SysParamConstants.OPR_DEFAULT_PWD)));
		shTblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction
				.getCurrentDate(), SysParamUtil
				.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		shTblOprInfo.setPwdWrTm(TblOprInfoConstants.PWD_WR_TM);
		shTblOprInfo.setRoleId(TblOprInfoConstants.SUP_BRH_ROLE);	
		shTblOprInfo.setPwdWrTmTotal(TblOprInfoConstants.PWD_WR_TM_TOTAL);
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_INIT);
		shTblOprInfo.setCurrentLoginTime(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginIp(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginStatus(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setPwdWrTmContinue(TblOprInfoConstants.PWD_WR_TM_CONTINUE);
		ShTblOprInfoPk id=new ShTblOprInfoPk();
		id.setBrhId(getBrhId());
		id.setOprId("admin");
		id.setMchtNo("-");
		shTblOprInfo.setId(id);
		shTblOprInfo.setOprName("管理员");
		//审核通过机构信息
		rspCode=t10101BO.accept(getBrhId(),operator.getOprId(),getTxnId(),shTblOprInfo);
		
		if(Constants.SUCCESS_CODE.equals(rspCode)||"01".equals(rspCode)){
			log("审核通过信息成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + getBrhId());
			//加载操作员所在机构信息
			reloadOprBrhInfo();
		}
		if("01".equals(rspCode)){
			writeSuccessMsg(returnMsg1);
			return null;
		}else if(Constants.SUCCESS_CODE.equals(rspCode)){
			writeSuccessMsg(returnMsg);
			return null;
		}		
		return rspCode;
	}
	
	/**
	 * 审核通过机构信息
	 * @return
	 */
	private String refuse() throws Exception {
		
		TblBrhInfo tblBrhInfo = t10101BO.get(brhId);
		if (!"0".equals(tblBrhInfo.getStatus())) {
			writeErrorMsg("<font color='red'>本记录已被其他操作员审核过了，请刷新页面后重试。</font>");
			return null;
		}
		
		rspCode = checkID();
		if(!Constants.SUCCESS_CODE.equals(rspCode)){
			return rspCode;
		}
		
		String sql = "select * from tbl_opr_info where brh_id = '" + getBrhId() + "' ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		//判断是否有操作员在此机构下
		if(dataList.size() > 0) {
			return ErrorCode.T10101_02;
		}
		sql = "select * from TBL_MCHT_BASE_INF_TMP where BANK_NO = '" + getBrhId() + "' ";
		dataList = commQueryDAO.findBySQLQuery(sql);
		//判断是否有商户在此机构下
		if(dataList.size() > 0) {
			return ErrorCode.T10101_03;
		}
		//退回通过机构信息
		rspCode=t10101BO.refuse(getBrhId(),operator.getOprId(),getTxnId());
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			log("审核退回信息成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + getBrhId());
			t10101BO.approveRecord(tblBrhInfo, "审核拒绝", refuseInfo);
			//加载操作员所在机构信息
			reloadOprBrhInfo();
		}
		return rspCode;
	}
	
	/**
	 * 申请和审核不能是统一操作员
	 * @return
	 */
	private String checkID(){
		//在新增、修改、冻结、恢复和注销时，CRT_OPR_ID均保存该交易的申请人（发起柜员），UPD_OPR_ID保存该交易的审核人
		String sql = "SELECT LAST_UPD_OPR_ID FROM TBL_BRH_INFO WHERE BRH_ID = '" + getBrhId() + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}
		return Constants.SUCCESS_CODE;
	}
	
	
	
	
	/**
	 * 重新加载操作员相关的机构信息
	 */
	private void reloadOprBrhInfo() {
		Map<String, String> brhMap = new LinkedHashMap<String, String>();
		brhMap.put(operator.getOprBrhId(),operator.getOprBrhName());
		operator.setBrhBelowMap(CommonFunction.getBelowBrhMap(brhMap));
		operator.setBrhBelowId(CommonFunction.getBelowBrhInfo(operator.getBrhBelowMap()));
	}
	
	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getBrhLvl() {
		return brhLvl;
	}

	public void setBrhLvl(String brhLvl) {
		this.brhLvl = brhLvl;
	}

	public String getUpBrhId() {
		return upBrhId;
	}

	public void setUpBrhId(String upBrhId) {
		this.upBrhId = upBrhId;
	}

	public String getBrhName() {
		return brhName;
	}

	public void setBrhName(String brhName) {
		this.brhName = brhName;
	}

	public String getBrhAddr() {
		return brhAddr;
	}

	public void setBrhAddr(String brhAddr) {
		this.brhAddr = brhAddr;
	}

	public String getBrhTelNo() {
		return brhTelNo;
	}

	public void setBrhTelNo(String brhTelNo) {
		this.brhTelNo = brhTelNo;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getBrhContName() {
		return brhContName;
	}

	public void setBrhContName(String brhContName) {
		this.brhContName = brhContName;
	}

	public String getCupBrhId() {
		return cupBrhId;
	}

	public void setCupBrhId(String cupBrhId) {
		this.cupBrhId = cupBrhId;
	}

	public String getBrhDataList() {
		return brhDataList;
	}

	public void setBrhDataList(String brhDataList) {
		this.brhDataList = brhDataList;
	}

	public String getResv1() {
		return resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}
	
	public String getResv2() {
		return resv2;
	}
	
	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}
	
	public String getBrhTmkIndex() {
		return brhTmkIndex;
	}

	public void setBrhTmkIndex(String brhTmkIndex) {
		this.brhTmkIndex = brhTmkIndex;
	}

	public String getResv1_5() {
		return resv1_5;
	}

	public void setResv1_5(String resv1_5) {
		this.resv1_5 = resv1_5;
	}

	public String getResv1_6() {
		return resv1_6;
	}

	public void setResv1_6(String resv1_6) {
		this.resv1_6 = resv1_6;
	}

	public String getResv1_7() {
		return resv1_7;
	}

	public void setResv1_7(String resv1_7) {
		this.resv1_7 = resv1_7;
	}

	public String getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag;
	}

	public String getSettleAcctNm() {
		return settleAcctNm;
	}

	public void setSettleAcctNm(String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}

	public String getSettleAcct() {
		return settleAcct;
	}

	public void setSettleAcct(String settleAcct) {
		this.settleAcct = settleAcct;
	}

	public String getSettleBankNm() {
		return settleBankNm;
	}

	public void setSettleBankNm(String settleBankNm) {
		this.settleBankNm = settleBankNm;
	}

	public String getSettleBankNo() {
		return settleBankNo;
	}

	public void setSettleBankNo(String settleBankNo) {
		this.settleBankNo = settleBankNo;
	}

	public TblBrhInfo getTblBrhInfoUpd() {
		return tblBrhInfoUpd;
	}

	public void setTblBrhInfoUpd(TblBrhInfo tblBrhInfoUpd) {
		this.tblBrhInfoUpd = tblBrhInfoUpd;
	}

	public TblBrhSettleInf getTblBrhSettleInfUpd() {
		return tblBrhSettleInfUpd;
	}

	public void setTblBrhSettleInfUpd(TblBrhSettleInf tblBrhSettleInfUpd) {
		this.tblBrhSettleInfUpd = tblBrhSettleInfUpd;
	}

	public String getResv1_5Upd() {
		return resv1_5Upd;
	}

	public void setResv1_5Upd(String resv1_5Upd) {
		this.resv1_5Upd = resv1_5Upd;
	}

	public String getResv1_6Upd() {
		return resv1_6Upd;
	}

	public void setResv1_6Upd(String resv1_6Upd) {
		this.resv1_6Upd = resv1_6Upd;
	}

	public String getResv1_7Upd() {
		return resv1_7Upd;
	}

	public void setResv1_7Upd(String resv1_7Upd) {
		this.resv1_7Upd = resv1_7Upd;
	}

	public String getResv1_8() {
		return resv1_8;
	}

	public void setResv1_8(String resv1_8) {
		this.resv1_8 = resv1_8;
	}

	public String getBrhFee() {
		return brhFee;
	}

	public void setBrhFee(String brhFee) {
		this.brhFee = brhFee;
	}

	public String getResv1_8Upd() {
		return resv1_8Upd;
	}

	public void setResv1_8Upd(String resv1_8Upd) {
		this.resv1_8Upd = resv1_8Upd;
	}

	public String getBrhFeeUpd() {
		return brhFeeUpd;
	}

	public void setBrhFeeUpd(String brhFeeUpd) {
		this.brhFeeUpd = brhFeeUpd;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getMemProperty() {
		return memProperty;
	}

	public void setMemProperty(String memProperty) {
		this.memProperty = memProperty;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	
	
	
}