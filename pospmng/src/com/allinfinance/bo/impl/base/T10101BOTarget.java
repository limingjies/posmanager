package com.allinfinance.bo.impl.base;

import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.impl.mchnt.TblMchntServiceImpl;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.dao.iface.base.TblBrhApproveProcessDAO;
import com.allinfinance.dao.iface.base.TblBrhInfoDAO;
import com.allinfinance.dao.iface.base.TblBrhInfoHisDAO;
import com.allinfinance.dao.iface.base.TblBrhSettleInfDAO;
import com.allinfinance.dao.iface.base.TblBrhSettleInfHisDAO;
import com.allinfinance.dao.iface.mchnt.ShTblOprInfoDAO;
import com.allinfinance.exception.AllinfinanceException;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;
import com.allinfinance.po.TblBrhApproveProcess;
import com.allinfinance.po.TblBrhApproveProcessPK;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblBrhInfoHis;
import com.allinfinance.po.TblBrhInfoHisPK;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.po.base.TblBrhSettleInfHis;
import com.allinfinance.po.base.TblBrhSettleInfHisPK;
import com.allinfinance.system.util.CommonFunction;

/**
 * Title:机构信息BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10101BOTarget implements T10101BO {
	
	private TblBrhInfoDAO tblBrhInfoDAO;
	private TblBrhSettleInfDAO tblBrhSettleInfDAO;
	private ShTblOprInfoDAO shTblOprInfoDAO;
	private TblBrhApproveProcessDAO tblBrhApproveProcessDAO;
	private TblBrhInfoHisDAO tblBrhInfoHisDAO;
	private TblBrhSettleInfHisDAO tblBrhSettleInfHisDAO;
	
	private static Logger log = Logger.getLogger(T10101BOTarget.class);
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10101BO#add(com.allinfinance.po.TblBrhInfo)
	 */
	public String add(TblBrhInfo tblBrhInfo,TblBrhSettleInf tblBrhSettleInf) {
		tblBrhInfoDAO.save(tblBrhInfo);
		if(tblBrhSettleInf!=null){
			tblBrhSettleInfDAO.save(tblBrhSettleInf);
		}
		approveRecord(tblBrhInfo, "新增记录", "新增合作伙伴");
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10101BO#delete(com.allinfinance.po.TblBrhInfo)
	 */
	public String delete(TblBrhInfo tblBrhInfo) {
		tblBrhInfoDAO.delete(tblBrhInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10101BO#update(java.util.List)
	 */
	public String update(List<TblBrhInfo> tblBrhInfoList) {
		for(TblBrhInfo tblBrhInfo : tblBrhInfoList) {
			tblBrhInfoDAO.update(tblBrhInfo);
			approveRecord(tblBrhInfo, "修改记录", "合作伙伴信息修改");
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblBrhInfoDAO
	 */
	public TblBrhInfoDAO getTblBrhInfoDAO() {
		return tblBrhInfoDAO;
	}

	/**
	 * @param tblBrhInfoDAO the tblBrhInfoDAO to set
	 */
	public void setTblBrhInfoDAO(TblBrhInfoDAO tblBrhInfoDAO) {
		this.tblBrhInfoDAO = tblBrhInfoDAO;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10101BO#get(java.lang.String)
	 */
	public TblBrhInfo get(String brhId) {
		return this.tblBrhInfoDAO.get(brhId);
	}

	@Override
	public TblBrhInfoHis getBrhHis(String brhId, String seqId) {
		TblBrhInfoHisPK id = new TblBrhInfoHisPK(brhId, seqId);
		return this.tblBrhInfoHisDAO.get(id);
	}

	@Override
	public TblBrhSettleInfHis getSettleHis(String brhId, String seqId) {
		TblBrhSettleInfHisPK id = new TblBrhSettleInfHisPK(brhId, seqId);
		return tblBrhSettleInfHisDAO.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10101BO#delete(java.lang.String)
	 */
	public String delete(String brhId) {
		TblBrhInfo tblBrhInfo=tblBrhInfoDAO.get(brhId);
		String resv1_7_flag=tblBrhInfo.getResv1().length()>6?tblBrhInfo.getResv1().substring(6,7):"";
		if(CommonsConstants.CHECKED.equals(resv1_7_flag)&&tblBrhSettleInfDAO.get(brhId)!=null){
			tblBrhSettleInfDAO.delete(brhId);
		}
		this.tblBrhInfoDAO.delete(brhId);
		tblBrhInfoHisDAO.delete(brhId);
		tblBrhSettleInfHisDAO.delete(brhId);
		tblBrhApproveProcessDAO.delete(brhId);
		return Constants.SUCCESS_CODE;
	}
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10101BO#delete(java.lang.String)
	 */
	public String accept(String brhId,String oprId,String txnId,ShTblOprInfo shTblOprInfo) throws Exception {
		TblBrhInfo tblBrhInfo=tblBrhInfoDAO.get(brhId);
		ShTblOprInfoPk key=shTblOprInfo.getId();	
		ShTblOprInfo tblOprInfo = shTblOprInfoDAO.get(key);

		//==========虚拟账户开户============
		log.info("虚拟账户：合作伙伴开户及信息变更接口调用开始。");
		String retMsg = this.sendMessage(tblBrhInfo, tblBrhSettleInfDAO.get(brhId),tblOprInfo);
//		if(retMsg == "00"){
//			throw new Exception("异常测试");
//		}
		if(!Constants.SUCCESS_CODE.equals(retMsg)){
			log.info(retMsg);
			throw new AllinfinanceException(retMsg);
		}
		log.info("虚拟账户：合作伙伴开户及信息变更接口调用结束。");
		//=============================
		
		if(tblOprInfo==null){		//设置开户成功标志
			tblBrhInfo.setResv3("1");
		}
		tblBrhInfo.setStatus("1");
		//最后更新操作员
		tblBrhInfo.setLastUpdOprId(oprId);
		//最后更新时间
		tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
		//最后更新交易码
		tblBrhInfo.setLastUpdTxnId(txnId);
		tblBrhInfoDAO.update(tblBrhInfo);	
		approveRecord(tblBrhInfo, "审核通过", "合作伙伴信息审核通过");
		if(tblOprInfo==null){
			shTblOprInfoDAO.saveOrUpdate(shTblOprInfo);
		}
		
		if(tblOprInfo==null){
			return Constants.SUCCESS_CODE;
		}else {
			return "01";
		}
		
	}
	
	public String refuse(String brhId,String oprId,String txnId) {
		TblBrhInfo tblBrhInfo=tblBrhInfoDAO.get(brhId);
		tblBrhInfo.setStatus("2");
		//最后更新操作员
		tblBrhInfo.setLastUpdOprId(oprId);
		//最后更新时间
		tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
		//最后更新交易码
		tblBrhInfo.setLastUpdTxnId(txnId);
		tblBrhInfoDAO.update(tblBrhInfo);
		return Constants.SUCCESS_CODE;
	}
	
	public TblBrhSettleInfDAO getTblBrhSettleInfDAO() {
		return tblBrhSettleInfDAO;
	}

	public void setTblBrhSettleInfDAO(TblBrhSettleInfDAO tblBrhSettleInfDAO) {
		this.tblBrhSettleInfDAO = tblBrhSettleInfDAO;
	}

	@Override
	public TblBrhSettleInf getSettle(String brhId) {
		// TODO Auto-generated method stub
		return tblBrhSettleInfDAO.get(brhId);
	}

	@Override
	public String updateSettle(TblBrhInfo tblBrhInfo,
			TblBrhSettleInf tblBrhSettleInf,Operator operator,TblBrhInfo tblBrhInfOld) {
		// TODO Auto-generated method stub
//		String rsv1_7_old=tblBrhInfOld.getResv1().length()>6?tblBrhInfOld.getResv1().substring(6, 7):"";
//		String rsv1_7_new=tblBrhInfo.getResv1().length()>6?tblBrhInfo.getResv1().substring(6, 7):"";
//		if(CommonsConstants.UNCHECKED.equals(rsv1_7_old)&&CommonsConstants.CHECKED.equals(rsv1_7_new)){
//			tblBrhSettleInf.setCrtOpr(operator.getOprId());
//			tblBrhSettleInf.setCrtTs(CommonFunction.getCurrentDateTime());
//			tblBrhSettleInfDAO.save(tblBrhSettleInf);
//		}else if(CommonsConstants.CHECKED.equals(rsv1_7_old)&&CommonsConstants.UNCHECKED.equals(rsv1_7_new)){
//			tblBrhSettleInfDAO.delete(tblBrhSettleInf.getBrhId());
//		}else if(CommonsConstants.CHECKED.equals(rsv1_7_old)&&CommonsConstants.CHECKED.equals(rsv1_7_new)){
		String sql1="insert into tbl_brh_info_his (brh_id,seq_id,cup_brh_id,brh_level,brh_sta,up_brh_id,reg_dt,post_cd,brh_addr,brh_name,brh_tel_no,brh_cont_name,"
			+"	open_rate_id,resv1,resv2,resv3,resv4,resv5,last_upd_opr_id,last_upd_txn_id,last_upd_ts,settle_org_no,settle_job_type,settle_mem_property,status,create_new_no)"
			+"	select brh_id,to_char(to_number(nvl((select max(t1.seq_id) from tbl_brh_info_his t1 where t1.brh_id='&brh_id'),'0'))+1),cup_brh_id,brh_level,brh_sta,up_brh_id,reg_dt,post_cd,brh_addr,brh_name,brh_tel_no,brh_cont_name," 
			+"	open_rate_id,resv1,resv2,resv3,resv4,resv5,last_upd_opr_id,last_upd_txn_id,last_upd_ts,settle_org_no,settle_job_type,settle_mem_property,status,create_new_no "
			+"	from tbl_brh_info s "
			+"	where s.brh_id='&brh_id'";
		String sql2="insert into tbl_brh_settle_inf_his(brh_id, seq_id, settle_flag, settle_bank_no, settle_bank_nm, settle_acct_nm, settle_acct, "
			+"	settle_acct_mid, settle_acct_mid_nm, crt_opr, crt_ts, upd_opr, upd_ts, misc, misc1)"
			+"	select brh_id, to_char(to_number(nvl((select max(t1.seq_id) from tbl_brh_settle_inf_his t1 where t1.brh_id='&brh_id'),'0'))+1), settle_flag,"
			+"	settle_bank_no, settle_bank_nm, settle_acct_nm, settle_acct,settle_acct_mid, settle_acct_mid_nm, crt_opr, crt_ts, upd_opr, upd_ts, misc, misc1"
			+"	from tbl_brh_settle_inf s where s.brh_id='&brh_id'";
		
		CommonFunction.getCommQueryDAO().excute(sql1.replaceAll("&brh_id", tblBrhInfo.getBrhId()));;
		CommonFunction.getCommQueryDAO().excute(sql2.replaceAll("&brh_id", tblBrhSettleInf.getBrhId()));
			tblBrhSettleInf.setUpdOpr(operator.getOprId());
			tblBrhSettleInf.setUpdTs(CommonFunction.getCurrentDateTime());
			tblBrhSettleInfDAO.update(tblBrhSettleInf);
//		}
		tblBrhInfoDAO.update(tblBrhInfo);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 1、客户在POSP入网时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  0）、交易码：A0100 子交易码：1
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因
	 * 2、客户在POSP结算信息并更时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  0）、交易码：F0100 子交易码：1
	 *	1）、虚拟账户回复成功：终审通过
	 *	2）、虚拟账户回复失败：终审不通过，并页面提示原因 
	 *
	 * @param brhInfo
	 * @param settleInfo
	 * @param oprInfo
	 * @return
	 * @throws Exception 
	 */
	public String sendMessage(TblBrhInfo brhInfo,TblBrhSettleInf settleInfo,ShTblOprInfo oprInfo) throws Exception{
		String retMsg = "00";	//返回结果信息，00-表示成功
		String transCode = "";
		String settleType = "10" ; //T+1
		String tnN = "1";
		String weekDay = "";
		String monthDay = "";
		//ShTblOprInfo tblOprInfo = shTblOprInfoDAO.get(oprInfo.getId());
		if(null ==oprInfo ){
			transCode = "A0100";
			log.info("合作伙伴开户。");
		} else {
			transCode = "F0100";
			log.info("合作伙伴信息变更。");
		}
		// 取得结算类型---开始
		if (!"".equals(brhInfo.getResv4())) {
			String mixStr = brhInfo.getResv4();
			String type = mixStr.substring(0,1);
			if ("0".equals(type)) {
				// T+N
				settleType = "10";
				tnN = String.valueOf(Integer.parseInt(mixStr.substring(2, 5)));
				if("0".equals(tnN)){ //T+0
					settleType = "01";
					tnN = "";
				}
			} else if ("1".equals(type)) {
				String periodType = mixStr.substring(1, 2);
				// 周结
				if ("0".equals(periodType)) {
					settleType = "11";
					weekDay = "1";
				// 月结
				} else if ("1".equals(periodType)) {
					settleType = "12";
					monthDay = "01";
				// 季结
				} else if ("2".equals(periodType)) {
					settleType = "13";
				}
			}
		}
		// 取得结算类型---结束
		Msg reqBody = MsgEntity.genPartnerRequestBodyMsg();
		Msg reqHead = MsgEntity.genCommonRequestHeadMsg(transCode,"1");
//		reqHead.getField(1).setValue("00");					//版本号
//		reqHead.getField(2).setValue(transCode);			//交易类型码
		reqHead.getField(3).setValue("1");					//子交易码
//		reqHead.getField(4).setValue(institution); 			//接入机构号;固定值，钱宝机构号
//		reqHead.getField(5).setValue(CommonFunction.getCurrentDate());			//接入方交易日期
//		reqHead.getField(6).setValue(CommonFunction.getRandomNum(reqHead.getField(6).getLength()));		//接入方交易流水号
//		reqHead.getField(7).setValue(CommonFunction.getCurrentTime());			//接入方交易时间
		reqHead.getField(8).setValue("0000000000");			//接入方交易码
		reqHead.getField(9).setValue(transCode.substring(0, 4) + "");	//交易类型+接入方检索参考号
//		reqHead.getField(10).setValue(institution);			//请求方机构号
//		reqHead.getField(11).setValue("");					//请求方交易日期
//		reqHead.getField(12).setValue("");					//请求方交易流水号
//		reqHead.getField(13).setValue(institution);			//开户机构号;固定值，钱宝机构号
//		reqHead.getField(14).setValue("");					//开户机构交易日期
//		reqHead.getField(15).setValue("");					//开户机构交易流水号
//		reqHead.getField(16).setValue("");					//开户机构交易时间
		reqHead.getField(17).setValue(brhInfo.getCreateNewNo());		//外部账号
//		reqHead.getField(18).setValue("");					//外部账号类型
//		reqHead.getField(19).setValue("1");					//内部账号验证标志
//		reqHead.getField(20).setValue("");					//内部账号
//		reqHead.getField(21).setValue("");					//内部账号类型
//		reqHead.getField(22).setValue("");					//密码域1
//		reqHead.getField(23).setValue("");					//密码域2
//		reqHead.getField(24).setValue("");					//客户号
//		reqHead.getField(25).setValue("");					//金额符号
//		reqHead.getField(26).setValue("");					//交易额
//		reqHead.getField(27).setValue("");					//用户支付手续费
//		reqHead.getField(28).setValue("");					//应答码
		
		String sql = "select b.subbranch_id,b.bank_name,b.subbranch_name,b.province,b.city from tbl_subbranch_info b where b.subbranch_id = '"+ settleInfo.getSettleBankNo() +"'";
		List retList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Object[] objBank = null;
		if(null == retList || retList != null && retList.size() < 1){
			retMsg = "获取清算账户信息失败。";
			return retMsg;
		} else{
			objBank = (Object[]) retList.get(0);
		}
		reqBody.getField(1).setValue(brhInfo.getCreateNewNo());	//合作伙伴号	
		reqBody.getField(2).setValue(brhInfo.getBrhName());		//合作伙伴名称
		reqBody.getField(3).setValue("3");						//合作伙伴风险级别:低风险
		reqBody.getField(4).setValue("0");						//合作伙伴状态
		reqBody.getField(5).setValue(brhInfo.getBrhAddr());		//地址
		reqBody.getField(6).setValue(brhInfo.getPostCd());		//邮编
		reqBody.getField(7).setValue(brhInfo.getBrhContName());	//联系人姓名
		reqBody.getField(8).setValue("");						//联系人移动电话
		reqBody.getField(9).setValue(brhInfo.getBrhTelNo());	//联系人固定电话
		reqBody.getField(10).setValue("");						//联系人电子信箱
		reqBody.getField(11).setValue("");						//联系人传真
		reqBody.getField(12).setValue("1");						//申请类别
		reqBody.getField(13).setValue((String)objBank[1]);				//结算银行名称
		reqBody.getField(14).setValue((String)objBank[0]);				//结算账号开户行行号	
		reqBody.getField(15).setValue((String)objBank[2]);				//结算账号开户行名称
		reqBody.getField(16).setValue(settleInfo.getSettleFlag());		//结算账号类型
		reqBody.getField(17).setValue(settleInfo.getSettleAcct());		//结算账号
		reqBody.getField(18).setValue(settleInfo.getSettleAcctNm());	//结算账号户名
		reqBody.getField(19).setValue(settleType);						//结算方式
		reqBody.getField(20).setValue(tnN);								//只填写N的值。如T+1填写1结算方式为10时必填
		reqBody.getField(21).setValue(weekDay);								//周一填写1。。。周日填写7结算方式为11时必填
		reqBody.getField(22).setValue(monthDay);								//介于01-28之间。不可填写29.30.31结算方式为12时必填
		reqBody.getField(23).setValue((String)objBank[3]);				//户省份（和结算账号开户行对应）
		reqBody.getField(24).setValue((String)objBank[4]);				//开户城市（和结算账号开户行对应）
		String secretKey = "1111111111111111";
		
		String reqStr = MsgEntity.genCompleteRequestMsg(reqHead, reqBody, secretKey);
		
		Msg respHead = MsgEntity.genCommonResponseHeadMsg();
		Msg respBody = MsgEntity.genCommonResponseBodyMsg();
		
		log.info(reqStr);
		byte[] bufMsg = MsgEntity.sendMessage(reqStr);
		String strRet = new String(bufMsg,"gb2312");
		log.info(strRet);
		MsgEntity.parseResponseMsg(bufMsg, respHead, respBody);
		
		String respCode = respHead.getField(28).getRealValue();
		if("0000".equals(respCode)) { //交易成功
			retMsg = "00";
		} else{
			retMsg = respBody.getField(1).getRealValue();
		}			
		return retMsg;
	}
	
	public ShTblOprInfoDAO getShTblOprInfoDAO() {
		return shTblOprInfoDAO;
	}

	public void setShTblOprInfoDAO(ShTblOprInfoDAO shTblOprInfoDAO) {
		this.shTblOprInfoDAO = shTblOprInfoDAO;
	}

	@Override
	public String approveRecord(TblBrhInfo tblBrhInfo, String oprType, String oprInfo) {
		TblBrhApproveProcess tblBrhApproveProcess = new TblBrhApproveProcess();
		TblBrhApproveProcessPK id = new TblBrhApproveProcessPK();
		id.setBrhId(tblBrhInfo.getBrhId());
		id.setTxnTime(CommonFunction.getCurrentDateTime());
		tblBrhApproveProcess.setId(id);
		tblBrhApproveProcess.setOprType(oprType);
		tblBrhApproveProcess.setOprInfo(oprInfo);
		tblBrhApproveProcess.setOprId(tblBrhInfo.getLastUpdOprId());
		
		tblBrhApproveProcessDAO.save(tblBrhApproveProcess);
		return null;
	}

	public TblBrhApproveProcessDAO getTblBrhApproveProcessDAO() {
		return tblBrhApproveProcessDAO;
	}

	public void setTblBrhApproveProcessDAO(
			TblBrhApproveProcessDAO tblBrhApproveProcessDAO) {
		this.tblBrhApproveProcessDAO = tblBrhApproveProcessDAO;
	}
	
	public TblBrhInfoHisDAO getTblBrhInfoHisDAO() {
		return tblBrhInfoHisDAO;
	}

	public void setTblBrhInfoHisDAO(TblBrhInfoHisDAO tblBrhInfoHisDAO) {
		this.tblBrhInfoHisDAO = tblBrhInfoHisDAO;
	}

	public TblBrhSettleInfHisDAO getTblBrhSettleInfHisDAO() {
		return tblBrhSettleInfHisDAO;
	}

	public void setTblBrhSettleInfHisDAO(TblBrhSettleInfHisDAO tblBrhSettleInfHisDAO) {
		this.tblBrhSettleInfHisDAO = tblBrhSettleInfHisDAO;
	}
}
