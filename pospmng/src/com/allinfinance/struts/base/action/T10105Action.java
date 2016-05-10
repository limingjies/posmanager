package com.allinfinance.struts.base.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.base.T10105BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.po.TblAgentFeeCfg;
import com.allinfinance.po.TblAgentRateInfo;
import com.allinfinance.po.TblAgentRateInfoId;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.base.TblBrhCashInf;
import com.allinfinance.po.base.TblBrhCashInfId;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:合作伙伴维护
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
public class T10105Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	//合作伙伴编号
	private String brhId;
	//合作伙伴级别
	private String brhLvl;
	//上级合作伙伴编号
	private String upBrhId;
	//合作伙伴名称
	private String brhName;
	//合作伙伴地址
	private String brhAddr;
	//合作伙伴联系电话
	private String brhTelNo;
	//合作伙伴邮编
	private String postCode;
	//合作伙伴联系人
	private String brhContName;
	//合作伙伴银联编号
	private String cupBrhId;
	//合作伙伴修改列表
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
	// 结算类型
	private String settleType;
	
	private TblBrhInfo tblBrhInfoUpd;
	private TblBrhSettleInf tblBrhSettleInfUpd;
	private TblAgentFeeCfg tblAgentFeeCfgZlfAdd,tblAgentFeeCfgUpd;
	private String resv1_5Upd;
	private String resv1_6Upd;
	private String resv1_7Upd;
	private String resv1_8Upd;
	private String brhFeeUpd;
	
	//机构信息BO
	private T10105BO t10105BO = (T10105BO) ContextUtil.getBean("T10105BO");
	private T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(getMethod())) {			
					rspCode = add();			
			} else if("delete".equals(getMethod())) {
//				rspCode = delete();
			} else if("update".equals(getMethod())) {
//				rspCode = update();
			} else if("upd".equals(getMethod())) {
				rspCode = upd();
			} else if("accept".equals(getMethod())) {
//				rspCode = accept();
			} else if("refuse".equals(getMethod())) {
//				rspCode = refuse();
			} else if("check".equals(getMethod())) {
				rspCode = check();
			}	
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对机构的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String upd() {
		//判断合作伙伴是否存在
		TblBrhInfo tblBrhInfOld = t10101BO.get(getBrhId());

		HttpServletRequest request = ServletActionContext.getRequest();
		String misc = request.getParameter("misc");
		
		if(tblBrhInfOld == null) {			
			return ErrorCode.T10101_04;
		}
		//*/地区码+第五位：是否系统级签到+第六位：是否生成对账流水+第七位：是否清算到机构账号
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
		tblBrhInfoUpd.setStatus("0");
		tblBrhInfoUpd.setSettleMemProperty(tblBrhInfOld.getSettleMemProperty());
		tblBrhInfoUpd.setCreateNewNo(tblBrhInfOld.getCreateNewNo());
		tblBrhInfoUpd.setSettleOrgNo(tblBrhInfOld.getSettleOrgNo());
		
		tblBrhInfoUpd.setUpBrhId(tblBrhInfOld.getUpBrhId());
		tblBrhInfoUpd.setBrhLevel(tblBrhInfOld.getBrhLevel());
		
		tblBrhSettleInfUpd.setBrhId(brhId);
		tblBrhSettleInfUpd.setMisc(misc);
		//机构秘钥索引
		tblBrhInfoUpd.setResv2(tblBrhInfOld.getResv2());
		tblBrhInfoUpd.setResv3(tblBrhInfOld.getResv3());
		//结算信息
		tblBrhInfoUpd.setResv4(getSettleType());
		//代理分润信息
		List<TblAgentRateInfo> rateList = new ArrayList<TblAgentRateInfo>();	
		jsonBean.parseJSONArrayData(getRequest().getParameter("gridJson"));
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			String json = jsonBean.getArray().get(i).toString();
			jsonBean.setObject(JSONObject.fromObject(json));
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(jsonBean.getObject().getDouble("feeRate"));
			tblAgentRateInfo.setFeeType(jsonBean.getObject().getString("feeType"));
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId(jsonBean.getObject().getString("rateId"));
			id.setDiscId(tblAgentFeeCfgUpd.getDiscId());
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);
		}
		
		//提现费率信息
		List<TblBrhCashInf> brhCashRateList = new ArrayList<TblBrhCashInf>();	
		jsonBean.parseJSONArrayData(getRequest().getParameter("cashRateJson"));
		len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			String json = jsonBean.getArray().get(i).toString();
			jsonBean.setObject(JSONObject.fromObject(json));
			TblBrhCashInf tblBrhCashInf = new TblBrhCashInf();
			TblBrhCashInfId tblBrhCashInfId = new TblBrhCashInfId();
			tblBrhCashInfId.setBrhId(tblBrhInfoUpd.getBrhId());
			tblBrhCashInfId.setRateId(jsonBean.getObject().getString("rateId"));
			tblBrhCashInf.setId(tblBrhCashInfId);
			tblBrhCashInf.setName(jsonBean.getObject().getString("name"));
			tblBrhCashInf.setRate(jsonBean.getObject().getDouble("rate"));
			tblBrhCashInf.setType(jsonBean.getObject().getString("type"));
			tblBrhCashInf.setCrtOpr(operator.getOprId());
			tblBrhCashInf.setCrtTime(CommonFunction.getCurrentDateTime());
			tblBrhCashInf.setUptOpr(operator.getOprId());
			tblBrhCashInf.setUptTime(CommonFunction.getCurrentDateTime());
			brhCashRateList.add(tblBrhCashInf);
		}
		
		//将费率等六个字段改为百分比
		
		if(tblAgentFeeCfgUpd.getPromotionRate()!=null){
			tblAgentFeeCfgUpd.setPromotionRate(tblAgentFeeCfgUpd.getPromotionRate()/100);
		}
		tblAgentFeeCfgUpd.setSpecFeeRate(0.0);
		tblAgentFeeCfgUpd.setAllotRate(tblAgentFeeCfgUpd.getAllotRate()/100);
		tblAgentFeeCfgUpd.setSpecFeeRate(tblAgentFeeCfgUpd.getSpecFeeRate()/100);
		tblAgentFeeCfgUpd.setExtVisaRate(tblAgentFeeCfgUpd.getExtVisaRate()/100);
		tblAgentFeeCfgUpd.setExtMasterRate(tblAgentFeeCfgUpd.getExtMasterRate()/100);
		tblAgentFeeCfgUpd.setExtJcbRate(tblAgentFeeCfgUpd.getExtJcbRate()/100);	
		System.out.println("-------"+tblAgentFeeCfgUpd.getExtJcbRate()/100+"-------"+tblAgentFeeCfgUpd.getExtMasterRate()/100);
		tblAgentFeeCfgUpd.setPromotionBegDate(tblAgentFeeCfgUpd.getPromotionBegDate().replaceAll("-", ""));
		tblAgentFeeCfgUpd.setPromotionEndDate(tblAgentFeeCfgUpd.getPromotionEndDate().replaceAll("-", ""));
//		rspCode=t10101BO.updateSettle(tblBrhInfoUpd,tblBrhSettleInfUpd,operator, tblBrhInfOld);
		rspCode=t10105BO.updateSettle(tblBrhInfoUpd,tblBrhSettleInfUpd,operator, tblBrhInfOld,tblAgentFeeCfgUpd,rateList,brhCashRateList);
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			// 添加审批过程（修改信息）
			t10101BO.approveRecord(tblBrhInfoUpd, "修改记录", "合作伙伴信息修改");
			
			log("更新机构信息成功。操作员编号：" + operator.getOprId());
			//加载操作员所在机构信息
			reloadOprBrhInfo();
		}
		return rspCode;
		
	}
	
	/**
	 * 检查是否有商户使用该费率信息
	 * @author yww
	 * 2016年3月17日  下午4:43:54
	 * @return
	 * @throws IOException 
	 */
	private String check() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rateId = request.getParameter("str");
		String brhidNo = request.getParameter("brhId");//合作伙伴编号
		//20160323 guoyu update begin
//		String sql=" select rate_id from tbl_profit_rate_info ";
		String QUOTA = "'";
		String COMMA = ",";
		StringBuilder sql = new StringBuilder("select ari.rate_id,pri.FEE_NAME from tbl_agent_rate_info ari,tbl_agent_fee_cfg afc, tbl_profit_rate_info pri where ari.disc_id=afc.disc_id and ari.rate_id=pri.RATE_ID");
		sql.append(" and afc.agent_no ='");
		sql.append(brhidNo);
		sql.append("' and ari.rate_id not in (");
		//分解
		String[] rateIds = rateId.split(",");
		sql.append(QUOTA);
		sql.append(rateIds[0]);
		sql.append(QUOTA);
		for (int i = 1; i < rateIds.length; i++) {
			sql.append(COMMA);
			sql.append(QUOTA);
			sql.append(rateIds[i]);
			sql.append(QUOTA);
		}
		sql.append(")");
		//查询取消的分润费率
		List<Object[]> list = commQueryDAO.findBySQLQuery(sql.toString());
		//有取消的
		if(list.size()>0){
			//清空sql
			sql.setLength(0);
			sql.append("select count(b.MCHT_NO) from TBL_MCHT_BASE_INF_TMP_TMP b,TBL_MCHT_SETTLE_INF_TMP_TMP s");
			//不是冻结的商户
			sql.append(" where b.MCHT_NO=s.MCHT_NO and b.MCHT_STATUS !='8'");
			//该合作伙伴下的商户
			sql.append(" and b.BANK_NO='"+ brhidNo + "'");
			String RATE_ID = "RATE_ID";
			//分润费率id唯一，所以判断2~3位，4~5位是否用到取消的分润费率id
			sql.append(" and ( SUBSTR(s.SPE_SETTLE_TP, 2, 2) = :"+RATE_ID+" or SUBSTR(s.SPE_SETTLE_TP, 4, 2) = :"+RATE_ID+" )");
			Map<String, String> params = new HashMap<String,String>();
			
			for (int i = 0; i < list.size(); i++) {
				Object[] Object = list.get(i);
				params.put(RATE_ID, Object[0].toString());
				//查询已经绑定取消的分润费率的商户个数
				String count = commQueryDAO.findCountBySQLQuery(sql.toString(),params);
				if(Integer.parseInt(count)>0){
					writeMessage("{msg:'false"+Object[1].toString()+"费率档已有商户正在使用，不能删除！"+"'}");
					return null;
				}
		    }
		}
		writeMessage("{msg:'true'}");
		return null;
		//20160323 guoyu update end
		
		/*List<String> list = commQueryDAO.findBySQLQuery(sql.toString());
		String str = "";
		for (String string : list) {
			if (!rateId.contains(string)) {
				str+=string+",";
			}
		}
		String hql="from com.allinfinance.po.mchnt.TblMchtBaseInf a where a.BankNo = '"+brhidNo+"' "; 
		List<TblMchtBaseInf> mchtList=commQueryDAO.findByHQLQuery(hql, 0, 500);
		StringBuffer buffer = new StringBuffer();
		for (TblMchtBaseInf tblMchtBaseInf : mchtList) {
			String mchtNo = tblMchtBaseInf.getMchtNo();//商户号
			String setInfoHql="from com.allinfinance.po.mchnt.TblMchtSettleInf b where b.mchtNo='"+mchtNo.trim()+"' ";
			List<TblMchtSettleInf> mchtSettleInfList=commQueryDAO.findByHQLQuery(setInfoHql, 0, 500);
			String setInfoSql=" select SPE_SETTLE_TP from TBL_MCHT_SETTLE_INF where MCHT_NO='"+mchtNo.trim()+"' ";
			List<String> speSettleTp = commQueryDAO.findBySQLQuery(setInfoSql);
			//TblMchtSettleInf mchtSettleInfo = null;
			if (speSettleTp.size()>0) {
				//mchtSettleInfo = mchtSettleInfList.get(0);
				String a=speSettleTp.get(0).toString().substring(1, 3);
				String b=speSettleTp.get(0).toString().substring(3, 5);
				if (str.contains(a)) {
					buffer.append(a).append(",");
					System.out.println("str= "+str+" b= "+b+" str.contains(b)= "+str.contains(b));
				}
				if (str.contains(b)) {
					buffer.append(b).append(",");
				}
			}
		}
		String feeName = "";
		if (buffer.length()>1) {
			String sql2=" select fee_name from tbl_profit_rate_info where rate_id in ("+buffer.substring(0,buffer.length()-1)+")";
			List<String> feeNameList = commQueryDAO.findBySQLQuery(sql2);
			for (String string : feeNameList) {
				  feeName+=string+",";
			}
			//writeSuccessMsg("存在映射关系，不可停用");
			writeMessage("{msg:'false"+feeName.substring(0,feeName.length()-1)+"费率档已有商户正在使用，不能删除！"+"'}");
			return null;
		}
		writeMessage("{msg:'true'}");
		return null;*/
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
	@SuppressWarnings("rawtypes")
	private String add() throws Exception {
		
		TblBrhInfo tblBrhInfo = new TblBrhInfo();
		String sqlStr = "select max(BRH_ID) from TBL_BRH_INFO";
		List idDataList = commQueryDAO.findBySQLQuery(sqlStr);
		
		String agentFeeSqlStr = "select max(DISC_ID) from TBL_agent_FEE_CFG";
		List agentFeeIdDataList = commQueryDAO.findBySQLQuery(agentFeeSqlStr);
		
		if(idDataList!=null && (idDataList.isEmpty()||(idDataList.get(0)==null))){
			setBrhId("00001");
		} else {
			String maxNo=idDataList.get(0).toString();
			Integer max=Integer.parseInt(maxNo);
			max=max+1;
			maxNo=max+"";
			maxNo=compZeroAtF(maxNo,5);
			setBrhId(maxNo);
			//将上级机构号设置为默认值:00001-钱宝，
			this.setUpBrhId("00001");
			//将合作伙伴级别设置为默认值：1
			this.setBrhLvl("1");
		}
		
		String agentFeeId = "";
		if(agentFeeIdDataList!=null && (agentFeeIdDataList.isEmpty()||(agentFeeIdDataList.get(0)==null))){
			agentFeeId = "00001";
		} else {
			String maxNo=agentFeeIdDataList.get(0).toString();
			Integer max=Integer.parseInt(maxNo);
			max=max+1;
			maxNo=max+"";
			maxNo=compZeroAtF(maxNo,5);
			agentFeeId = maxNo;
		}
		//判断合作伙伴号是否存在
		while(t10101BO.get(getBrhId()) != null){
			String maxNo = getBrhId();
			Integer max = Integer.parseInt(maxNo);
			max++;
			maxNo=max+"";
			maxNo=compZeroAtF(maxNo,5);
			setBrhId(maxNo);
		}
		
		if(getOrgNo()!=null&&getOrgNo().length()< 3){
			setOrgNo(compZeroAtF(getOrgNo(),3));
		}
		String tempBrhId=null;
		//合作伙伴号码生成：
		//修改后规则：人员属性（1位）+ 地区编码（4位）+ 顺序码（3位）
		//原规则：人员属性（1位）+机构代码（3位）+ 地区编码（4位）+ 职务类别（1位） + 顺序码（3位）
		//tempBrhId = getMemProperty()+getOrgNo()+getResv1()+ getJobType();	
		tempBrhId = getMemProperty() + getResv1();	
		String sql = "select max(CREATE_NEW_NO) from TBL_BRH_INFO where CREATE_NEW_NO like '"+tempBrhId+"%'";
		List dataList = commQueryDAO.findBySQLQuery(sql);
		if(dataList!=null && (dataList.isEmpty()||(dataList.get(0)==null))){
			tblBrhInfo.setCreateNewNo(tempBrhId + "001" );
		} else {
			String maxNo = dataList.get(0).toString();
			maxNo = maxNo.replace(tempBrhId,"");
			Integer max = Integer.parseInt(maxNo);
			max = max+1;
			maxNo = max+ "";
			if(maxNo.length()<3){
				maxNo=compZeroAtF(maxNo,3);
			}
			tblBrhInfo.setCreateNewNo(tempBrhId+maxNo);
		}
//		BeanUtils.copyProperties(tblBrhInfo, this);			
		//状态
		tblBrhInfo.setStatus("0");
		//合作伙伴编号
		tblBrhInfo.setBrhId(getBrhId());
		//银联机构编号
		tblBrhInfo.setCupBrhId(getCupBrhId());
		//合作伙伴级别
		tblBrhInfo.setBrhLevel(getBrhLvl());
		//机合作伙伴状态，暂不启用
		tblBrhInfo.setBrhSta("0");
		//上级合作伙伴编号
		tblBrhInfo.setUpBrhId(getUpBrhId());
		//合作伙伴注册时间
		tblBrhInfo.setRegDt(CommonFunction.getCurrentDate());
		//合作伙伴所在地邮政编号
		tblBrhInfo.setPostCd("-");
		//合作伙伴所在地址
		tblBrhInfo.setBrhAddr(getBrhAddr());
		//合作伙伴名称
		tblBrhInfo.setBrhName(getBrhName());
		//合作伙伴联系电话
		tblBrhInfo.setBrhTelNo(getBrhTelNo());
		//合作伙伴联系人
		tblBrhInfo.setBrhContName(getBrhContName());
		//结算信息
		tblBrhInfo.setResv4(getSettleType());
		//最后更新操作员
		tblBrhInfo.setLastUpdOprId(operator.getOprId());
		//最后更新时间
		tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
		//最后更新交易码
		tblBrhInfo.setLastUpdTxnId(getTxnId());
		//合作伙伴秘钥索引
		tblBrhInfo.setResv2(SysParamUtil.getParam(SysParamConstants.BRHINFO_KEY));
		
		//机构地区码+第五位：是否系统级签到+第六位：是否生成对账流水+第七位：是否清算到机构账号
		// +第八位：是否配置分润手续费 +5位分润费率代码（不配置则不加）
		tblBrhInfo.setResv1(resv1+resv1_5+resv1_6+resv1_7+resv1_8+(StringUtil.isEmpty(brhFee)?"":brhFee));
		
		tblBrhInfo.setSettleJobType(getJobType());			//职务类型
		tblBrhInfo.setSettleMemProperty(getMemProperty());	//人员属性
		tblBrhInfo.setSettleOrgNo(getOrgNo());				//机构代码

		HttpServletRequest request = ServletActionContext.getRequest();
		String misc = request.getParameter("misc");
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
			tblBrhSettleInf.setMisc(misc);//设置分润封顶
//		}
			
		//代理分润信息
		List<TblAgentRateInfo> rateList = new ArrayList<TblAgentRateInfo>();	
		jsonBean.parseJSONArrayData(getRequest().getParameter("gridJson"));
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			String json = jsonBean.getArray().get(i).toString();
			jsonBean.setObject(JSONObject.fromObject(json));
			TblAgentRateInfo tblAgentRateInfo = new TblAgentRateInfo();
			tblAgentRateInfo.setFeeRate(jsonBean.getObject().getDouble("feeRate"));
			tblAgentRateInfo.setFeeType(jsonBean.getObject().getString("feeType"));
			TblAgentRateInfoId id = new TblAgentRateInfoId();
			id.setRateId(jsonBean.getObject().getString("rateId"));
			id.setDiscId(agentFeeId);
			tblAgentRateInfo.setId(id);
			rateList.add(tblAgentRateInfo);
		}
		
		//提现费率信息
		List<TblBrhCashInf> brhCashRateList = new ArrayList<TblBrhCashInf>();	
		jsonBean.parseJSONArrayData(getRequest().getParameter("cashRateJson"));
		len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			String json = jsonBean.getArray().get(i).toString();
			jsonBean.setObject(JSONObject.fromObject(json));
			TblBrhCashInf tblBrhCashInf = new TblBrhCashInf();
			TblBrhCashInfId tblBrhCashInfId = new TblBrhCashInfId();
			tblBrhCashInfId.setBrhId(tblBrhInfo.getBrhId());
			tblBrhCashInfId.setRateId(jsonBean.getObject().getString("rateId"));
			tblBrhCashInf.setId(tblBrhCashInfId);
			tblBrhCashInf.setName(jsonBean.getObject().getString("name"));
			tblBrhCashInf.setRate(jsonBean.getObject().getDouble("rate"));
			tblBrhCashInf.setType(jsonBean.getObject().getString("type"));
			tblBrhCashInf.setCrtOpr(operator.getOprId());
			tblBrhCashInf.setCrtTime(CommonFunction.getCurrentDateTime());
			tblBrhCashInf.setUptOpr(operator.getOprId());
			tblBrhCashInf.setUptTime(CommonFunction.getCurrentDateTime());
			brhCashRateList.add(tblBrhCashInf);
		}
		
		tblAgentFeeCfgZlfAdd.setSpecFeeRate(0.0);
		tblAgentFeeCfgZlfAdd.setDiscId(agentFeeId);
		tblAgentFeeCfgZlfAdd.setSeq(new BigDecimal(1));
		tblAgentFeeCfgZlfAdd.setAgentNo(getBrhId());
		tblAgentFeeCfgZlfAdd.setPromotionBegDate(tblAgentFeeCfgZlfAdd.getPromotionBegDate().replaceAll("-", ""));
		tblAgentFeeCfgZlfAdd.setPromotionEndDate(tblAgentFeeCfgZlfAdd.getPromotionEndDate().replaceAll("-", ""));
		
		//将费率等六个字段改为百分比
		if(tblAgentFeeCfgZlfAdd.getPromotionRate()!=null){
			tblAgentFeeCfgZlfAdd.setPromotionRate(tblAgentFeeCfgZlfAdd.getPromotionRate()/100);
		}
		tblAgentFeeCfgZlfAdd.setAllotRate(tblAgentFeeCfgZlfAdd.getAllotRate()/100);
		tblAgentFeeCfgZlfAdd.setSpecFeeRate(tblAgentFeeCfgZlfAdd.getSpecFeeRate()/100);

		if(tblAgentFeeCfgZlfAdd.getExtVisaRate()!=null){
			tblAgentFeeCfgZlfAdd.setExtVisaRate(tblAgentFeeCfgZlfAdd.getExtVisaRate()/100);
		}
		if(tblAgentFeeCfgZlfAdd.getExtMasterRate()!=null){
			tblAgentFeeCfgZlfAdd.setExtMasterRate(tblAgentFeeCfgZlfAdd.getExtMasterRate()/100);
		}
		if(tblAgentFeeCfgZlfAdd.getExtJcbRate()!=null){
			tblAgentFeeCfgZlfAdd.setExtJcbRate(tblAgentFeeCfgZlfAdd.getExtJcbRate()/100);
		}
		
		rspCode=t10105BO.add(tblBrhInfo,tblBrhSettleInf,tblAgentFeeCfgZlfAdd,rateList,null,brhCashRateList);
		if(Constants.SUCCESS_CODE.equals(rspCode)){
			// 添加审批过程（新增信息）
			t10101BO.approveRecord(tblBrhInfo, "新增记录", "新增合作伙伴");
			log("添加机构信息成功。操作员编号：" + operator.getOprId()+"，机构编号：" + getBrhId());
			//加载操作员所在机构信息
			reloadOprBrhInfo();
		}
		return rspCode;
	}
	
//	/**
//	 * 删除机构信息
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	private String delete() throws Exception {
//		
//		String sql = "select * from tbl_opr_info where brh_id = '" + getBrhId() + "' ";
//		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
//		//判断是否有操作员在此机构下
//		if(dataList.size() > 0) {
//			return ErrorCode.T10101_02;
//		}
//		sql = "select * from TBL_MCHT_BASE_INF_TMP where BANK_NO = '" + getBrhId() + "' ";
//		dataList = commQueryDAO.findBySQLQuery(sql);
//		//判断是否有商户在此机构下
//		if(dataList.size() > 0) {
//			return ErrorCode.T10101_03;
//		}
//		
//		
//		
//		//删除机构信息
//		rspCode=t10101BO.delete(getBrhId());
//		if(Constants.SUCCESS_CODE.equals(rspCode)){
//			log("删除机构信息成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + getBrhId());
//			//加载操作员所在机构信息
//			reloadOprBrhInfo();
//		}
//		return rspCode;
//	}
	
//	/**
//	 * 更新机构信息
//	 * @return
//	 */
//	private String update() throws Exception {
//		jsonBean.parseJSONArrayData(getBrhDataList());
//		int len = jsonBean.getArray().size();
//		List<TblBrhInfo> brhInfoList = new ArrayList<TblBrhInfo>();
//		for(int i = 0; i < len; i++) {
//			jsonBean.setObject(jsonBean.getJSONDataAt(i));
//			TblBrhInfo tblBrhInfo = new TblBrhInfo();
//			BeanUtils.setObjectWithPropertiesValue(tblBrhInfo,jsonBean,true);
//			tblBrhInfo.setBrhSta("0");
//			tblBrhInfo.setLastUpdOprId(operator.getOprId());
//			tblBrhInfo.setLastUpdTxnId(getTxnId());
//			tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
//			brhInfoList.add(tblBrhInfo);
//		}
//		t10101BO.update(brhInfoList);
//		log("更新机构信息成功。操作员编号：" + operator.getOprId());
//		//加载操作员所在机构信息
//		reloadOprBrhInfo();
//		return Constants.SUCCESS_CODE;
//	}
	
//	/**
//	 * 审核通过机构信息
//	 * @return
//	 */
//	private String accept() throws Exception {
//		
//		rspCode = checkID();
//		if(!Constants.SUCCESS_CODE.equals(rspCode)){
//			return rspCode;
//		}
//		//审核通过机构信息
//		rspCode=t10101BO.accept(getBrhId(),operator.getOprId(),getTxnId());
//		if(Constants.SUCCESS_CODE.equals(rspCode)){
//			log("审核通过信息成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + getBrhId());
//			//加载操作员所在机构信息
//			reloadOprBrhInfo();
//		}
//		return rspCode;
//	}
//	
//	/**
//	 * 审核通过机构信息
//	 * @return
//	 */
//	private String refuse() throws Exception {
//		
//		rspCode = checkID();
//		if(!Constants.SUCCESS_CODE.equals(rspCode)){
//			return rspCode;
//		}
//		
//		String sql = "select * from tbl_opr_info where brh_id = '" + getBrhId() + "' ";
//		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
//		//判断是否有操作员在此机构下
//		if(dataList.size() > 0) {
//			return ErrorCode.T10101_02;
//		}
//		sql = "select * from TBL_MCHT_BASE_INF_TMP where BANK_NO = '" + getBrhId() + "' ";
//		dataList = commQueryDAO.findBySQLQuery(sql);
//		//判断是否有商户在此机构下
//		if(dataList.size() > 0) {
//			return ErrorCode.T10101_03;
//		}
//		//退回通过机构信息
//		rspCode=t10101BO.refuse(getBrhId(),operator.getOprId(),getTxnId());
//		if(Constants.SUCCESS_CODE.equals(rspCode)){
//			log("审核退回信息成功。操作员编号：" + operator.getOprId()+ "，机构编号：" + getBrhId());
//			//加载操作员所在机构信息
//			reloadOprBrhInfo();
//		}
//		return rspCode;
//	}
	
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

	public TblAgentFeeCfg getTblAgentFeeCfgUpd() {
		return tblAgentFeeCfgUpd;
	}

	public void setTblAgentFeeCfgUpd(TblAgentFeeCfg tblAgentFeeCfgUpd) {
		this.tblAgentFeeCfgUpd = tblAgentFeeCfgUpd;
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

	public TblAgentFeeCfg getTblAgentFeeCfgZlfAdd() {
		return tblAgentFeeCfgZlfAdd;
	}

	public void setTblAgentFeeCfgZlfAdd(TblAgentFeeCfg tblAgentFeeCfgZlfAdd) {
		this.tblAgentFeeCfgZlfAdd = tblAgentFeeCfgZlfAdd;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	
	
	
}