package com.allinfinance.struts.settle.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataIntegrityViolationException;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

public class T80806Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String dateSettlmt;
	
	@Override
	protected String subExecute() throws Exception {
		try {
			if ("getFileNotice".equals(method)) {
				rspCode = getFileNotice();
			}else if("queryDate".equals(method)){
				rspCode = queryDate();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId() + "，发起入账申请操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
		}
		return rspCode;
	}


	/**
	 * 取文件通知（posp和渠道对账后）
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String getFileNotice() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		
		//依据清算日期判断入账表TBL_ALGO_CONFIRM_HIS中是否已存在数据，存在则代表该日期数据已入账；不存在则没进行入账申请操作
		String sql = "SELECT STATUS,DATE_SETTLMT FROM TBL_ALGO_CONFIRM_HIS where DATE_SETTLMT = '"+dateSettlmt+"' ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (dataList.size()<=0) {
			String sql2 = "SELECT a.DATE_SETTLMT,a.BRH_INS_ID_CD, c.CREATE_NEW_NO||'-'||c.BRH_NAME, a.MCHT_CD, a.MCHT_CD||'-'||d.MCHT_NM ,a.TRANS_AMT ,(a.MCHT_FEE_D - a.MCHT_FEE_C) as MCHT_FEE, (a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,a.trans_date,a.trans_date_time,a.txn_ssn, b.retrivl_ref from TBL_ALGO_DTL a left join TBL_BRH_INFO c  on trim(a.BRH_INS_ID_CD)=c.BRH_ID left join TBL_MCHT_BASE_INF d on a.MCHT_CD = d.MCHT_NO left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref where a.DATE_SETTLMT = '"+dateSettlmt+"' ";
			List<Object[]> dataList2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
			if (dataList2.size()<=0) {
				return "-1";
			}
			//2016/02/16 郭宇 修改 先插入数据再处理状态
			//申请入账交易成功， 则往TBL_ALGO_CONFIRM_HIS表里插一条入账清算日期的数据
			String insertSql = "INSERT INTO TBL_ALGO_CONFIRM_HIS (DATE_SETTLMT, REC_OPR_ID, CREATE_TIME, STATUS) VALUES ('"+dateSettlmt+"', '"+operator.getOprId()+"', '"+CommonFunction.getCurrentDateTime()+"','1')";
			try {
				CommonFunction.getCommQueryDAO().excute(insertSql);
			} catch (Exception e) {
				//主键重复，已经发起入账申请
				if (e instanceof DataIntegrityViolationException){
					return "02";
				}
				throw e;
			}
		}else {
			//2016/02/16 郭宇 修改 先插入数据再处理状态
			Object[] data = dataList.get(0);
			//正常,正在处理
			if ("0".equals(data[0])||"1".equals(data[0])){
				return "02";
			}
			String sql2 = "SELECT a.DATE_SETTLMT,a.BRH_INS_ID_CD, c.CREATE_NEW_NO||'-'||c.BRH_NAME, a.MCHT_CD, a.MCHT_CD||'-'||d.MCHT_NM ,a.TRANS_AMT ,(a.MCHT_FEE_D - a.MCHT_FEE_C) as MCHT_FEE, (a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,a.trans_date,a.trans_date_time,a.txn_ssn, b.retrivl_ref from TBL_ALGO_DTL a left join TBL_BRH_INFO c  on trim(a.BRH_INS_ID_CD)=c.BRH_ID left join TBL_MCHT_BASE_INF d on a.MCHT_CD = d.MCHT_NO left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref where a.DATE_SETTLMT = '"+dateSettlmt+"' ";
			List<Object[]> dataList2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
			if (dataList2.size()<=0) {
				return "-1";
			}
		}
		
		String retMsg = "00";	//返回结果信息，00-表示成功
		String transCode = "M0010";
		//String institution = "100000000000000";

		Msg reqHead = MsgEntity.genCommonRequestHeadMsg(transCode,"1");
		Msg reqBody = MsgEntity.getFileNoticeMsg();
		//reqHead.getField(1).setValue("00");					//版本号
//		reqHead.getField(2).setValue(transCode);			//交易类型码
		reqHead.getField(3).setValue("1");					//子交易码
		reqHead.getField(8).setValue("0000000000");			//接入方交易码
		/*reqHead.getField(4).setValue(institution); 			//接入机构号;固定值，钱宝机构号
		reqHead.getField(5).setValue(CommonFunction.getCurrentDate());			//接入方交易日期
		reqHead.getField(6).setValue(CommonFunction.getRandomNum(reqHead.getField(6).getLength()));		//接入方交易流水号
		reqHead.getField(7).setValue(CommonFunction.getCurrentTime());			//接入方交易时间
		reqHead.getField(9).setValue("");	//交易类型+接入方检索参考号
		reqHead.getField(10).setValue(institution);			//请求方机构号
		reqHead.getField(11).setValue("");					//请求方交易日期
		reqHead.getField(12).setValue("");					//请求方交易流水号
		reqHead.getField(13).setValue(institution);			//开户机构号;固定值，钱宝机构号
		reqHead.getField(14).setValue("");					//开户机构交易日期
		reqHead.getField(15).setValue("");					//开户机构交易流水号
		reqHead.getField(16).setValue("");	*/				//开户机构交易时间

		reqHead.getField(17).setValue(operator.getOprId());	//外部账号"848451052716000"
		
		/*reqHead.getField(18).setValue("");					//外部账号类型
		reqHead.getField(19).setValue("1");					//内部账号验证标志
		reqHead.getField(20).setValue("");					//内部账号
		reqHead.getField(21).setValue("");					//内部账号类型
		reqHead.getField(22).setValue("");					//密码域1
		reqHead.getField(23).setValue("");					//密码域2
		reqHead.getField(24).setValue("");					//客户号
		reqHead.getField(25).setValue("");					//金额符号
		reqHead.getField(26).setValue("");					//交易额
		reqHead.getField(27).setValue("");					//用户支付手续费
		reqHead.getField(28).setValue("");*/				//应答码
		
		reqBody.getField(1).setValue(SysParamUtil.getParam("PATH_VC_AC_1"));	//ftp路径
		reqBody.getField(2).setValue(SysParamUtil.getParam("FILE_NAME_VC_AC_1")+dateSettlmt+SysParamUtil.getParam("FILE_SUFFIX_VC_AC_1"));//ftp文件名
		//下面这行代码对应接口文档的更改，如确定M0010 1 交易加“对账流水日期”字段，则打开下面的注释
		//reqBody.getField(3).setValue(dateSettlmt);//对账流水日期

		String secretKey = "1111111111111111";
		String reqStr = MsgEntity.genCompleteRequestMsg(reqHead, reqBody, secretKey);

		Msg respHead = MsgEntity.genCommonResponseHeadMsg();
		Msg respBody = MsgEntity.genCommonResponseBodyMsg();
		
		log(reqStr);
		byte[] bufMsg = MsgEntity.sendMessage(reqStr);
		String strRet = new String(bufMsg,"gb2312");
		log(strRet);
		MsgEntity.parseResponseMsg(bufMsg, respHead, respBody);
		
		String respCode = respHead.getField(28).getRealValue();
		if("0000".equals(respCode)) {
			//2016/02/16 郭宇 修改 先插入数据再处理状态
			//申请入账交易成功， 则往TBL_ALGO_CONFIRM_HIS表里插一条入账清算日期的数据
//			String insertSql = "INSERT INTO TBL_ALGO_CONFIRM_HIS (DATE_SETTLMT, REC_OPR_ID, CREATE_TIME) VALUES ('"+dateSettlmt+"', '"+operator.getOprId()+"', '"+CommonFunction.getCurrentDateTime()+"')";
			//申请入账交易成功，更新状态为正常。
			String updSql = "UPDATE TBL_ALGO_CONFIRM_HIS SET STATUS='0' WHERE DATE_SETTLMT='"+dateSettlmt+"'";
			CommonFunction.getCommQueryDAO().excute(updSql);
			retMsg = respCode;
		} else{
			//申请入账交易成功，更新状态为失败。
			String updSql = "UPDATE TBL_ALGO_CONFIRM_HIS SET STATUS='9' WHERE DATE_SETTLMT='"+dateSettlmt+"'";
			CommonFunction.getCommQueryDAO().excute(updSql);
			retMsg = respBody.getField(1).getRealValue();
			log("处理 "+dateSettlmt+" 日的入账申请失败，接口返回码为："+retMsg+"。 ");
		}
		return retMsg;
	}
	
	/**
	 * 查询时先判断该清算日期是否已经入账
	 */
	@SuppressWarnings({ "unchecked" })
	private String queryDate() throws Exception {
		//依据清算日期判断入账表TBL_ALGO_CONFIRM_HIS中是否已存在数据，存在则代表该日期数据已入账；不存在则没进行入账申请操作
		String sql = "SELECT * FROM TBL_ALGO_CONFIRM_HIS where DATE_SETTLMT = '"+dateSettlmt+"' ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (dataList.size()>0) {
			return "02";
		}
		/*if (dataList.size()<=0) {
			String sql2 = "SELECT a.DATE_SETTLMT,a.BRH_INS_ID_CD, a.BRH_INS_ID_CD||'-'||c.BRH_NAME, a.MCHT_CD, a.MCHT_CD||'-'||d.MCHT_NM ,a.TRANS_AMT ,(a.MCHT_FEE_D - a.MCHT_FEE_C) as MCHT_FEE, (a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,a.trans_date,a.trans_date_time,a.txn_ssn, b.retrivl_ref from TBL_ALGO_DTL a left join TBL_BRH_INFO c  on trim(a.BRH_INS_ID_CD)=c.BRH_ID left join TBL_MCHT_BASE_INF d on a.MCHT_CD = d.MCHT_NO left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref where a.DATE_SETTLMT = '"+dateSettlmt+"' ";
			List<Object[]> dataList2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
			if (dataList2.size()>0) {
				return "02";
			}
		}*/
		return "0000";
	}

	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		if (dateSettlmt.length()>8) {
			this.dateSettlmt = dateSettlmt.replace("-", "").substring(0,8);
		}else {
			this.dateSettlmt = dateSettlmt.replace("-", "");
		}
	}

}
