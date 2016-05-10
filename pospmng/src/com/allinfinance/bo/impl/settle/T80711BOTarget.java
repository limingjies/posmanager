package com.allinfinance.bo.impl.settle;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

import sun.util.logging.resources.logging;

import com.allinfinance.bo.settle.T80711BO;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblMchtErrAdjustDAO;
import com.allinfinance.log.Log;
import com.allinfinance.po.settle.TblMchtErrAdjust;
import com.allinfinance.system.util.ContextUtil;

/**
 * @author 
 *
 */
public class T80711BOTarget implements T80711BO {
	

	private TblMchtErrAdjustDAO tblMchtErrAdjustDAO;//=(TblMchtErrAdjustDAO) ContextUtil.getBean("TblMchtErrAdjustDAO"); 
	private ICommQueryDAO commQueryDAO;
	
	/**
	 * @return the tblMchtErrAdjustDAO
	 */
	public TblMchtErrAdjustDAO getTblMchtErrAdjustDAO() {
		return tblMchtErrAdjustDAO;
	}



	/**
	 * @param tblMchtErrAdjustDAO the tblMchtErrAdjustDAO to set
	 */
	public void setTblMchtErrAdjustDAO(TblMchtErrAdjustDAO tblMchtErrAdjustDAO) {
		this.tblMchtErrAdjustDAO = tblMchtErrAdjustDAO;
	}
	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}
	
	
	
	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	/**
	 * 商户手工差错调整审核
	 */
	@Override
	public String check(TblMchtErrAdjust tblMchtErrAdjust) {
		TblMchtErrAdjust mchtErrAdjust = tblMchtErrAdjustDAO.get(tblMchtErrAdjust.getId());
		if(mchtErrAdjust==null){
			return "审核的数据不存在！";
		}
		if(!"0".equals(mchtErrAdjust.getApproveStatus())){
			return "该记录已经被审核过，请选择其他记录！";
		}
		if(mchtErrAdjust.getCrtOpr().trim().split("-")[0].equals(tblMchtErrAdjust.getApproveOpr().trim().split("-")[0])){
			return "同一操作员不能审核！";
		}
		try {
			
//			double money=mchtErrAdjust.getMoney()*100;
			BigDecimal big=new BigDecimal(mchtErrAdjust.getMoney()).multiply(new BigDecimal(100));
			big = big.setScale(2, RoundingMode.HALF_UP);  
			Long longValue = big.longValue();
			String mon = longValue.toString();
			
			// 发送报文
			Log.log("发送商户手工差错调整审核报文");
			Msg reqBody = MsgEntity.genMchtErrAdjustRequestBodyMsg();
			Msg reqHead = MsgEntity.genCommonRequestHeadMsg("E0010","0");
//			reqHead.getField(1).setValue("00");// 版本号
//			reqHead.getField(2).setValue("E0010");// 交易码
			reqHead.getField(3).setValue("0");// 子交易码
			reqHead.getField(8).setValue("0000000000");// 接入方交易码
			reqHead.getField(9).setValue("");// 交易类型+接入方检索参考号
			reqHead.getField(11).setValue("");// 请求方交易日期
			reqHead.getField(12).setValue("");
			reqHead.getField(14).setValue("");
			reqHead.getField(15).setValue("");
			reqHead.getField(16).setValue("");
			reqHead.getField(17).setValue(mchtErrAdjust.getMchtNo());// 外部账号
			reqHead.getField(18).setValue("00000002");// 外部账号类型
			reqHead.getField(20).setValue("");
			reqHead.getField(21).setValue("");
			reqHead.getField(22).setValue("");
			reqHead.getField(23).setValue("");
			reqHead.getField(24).setValue("");
			reqHead.getField(25).setValue("+");
			reqHead.getField(26).setValue(mon);// 交易额
			reqHead.getField(27).setValue("");
			 reqHead.getField(28).setValue("");
			reqHead.getField(28).setValue("");

			reqBody.getField(1).setValue(mchtErrAdjust.getMchtNo());//商户号
			reqBody.getField(2).setValue(mchtErrAdjust.getTradeType());//交易类型

			String secretKey = "1111111111111111";

			String reqStr = MsgEntity.genCompleteRequestMsg(reqHead, reqBody,
					secretKey);
			Msg respHead = MsgEntity.genCommonResponseHeadMsg();
			Msg respBody = MsgEntity.genCommonResponseBodyMsg();

			Log.log(reqStr);
			byte[] bufMsg = MsgEntity.sendMessage(reqStr);
			String strRet = new String(bufMsg, "gb2312");
			Log.log(strRet);
			MsgEntity.parseResponseMsg(bufMsg, respHead, respBody);
			String respCode = respHead.getField(28).getRealValue();
			String responseResult="";
			if("0000".equals(respCode)) { //交易成功
				//审核
				Log.log("商户手工差错调整审核");
				mchtErrAdjust.setApproveAdvice(tblMchtErrAdjust.getApproveAdvice());
				mchtErrAdjust.setApproveOpr(tblMchtErrAdjust.getApproveOpr());
				mchtErrAdjust.setApproveStatus(tblMchtErrAdjust.getApproveStatus());
				mchtErrAdjust.setApproveTime(tblMchtErrAdjust.getApproveTime());
				mchtErrAdjust.setPostStatus(tblMchtErrAdjust.getPostStatus());
				mchtErrAdjust.setPostTime(tblMchtErrAdjust.getPostTime());
				mchtErrAdjust.setUpdOpr(tblMchtErrAdjust.getUpdOpr());
				mchtErrAdjust.setUpdTime(tblMchtErrAdjust.getUpdTime());
				mchtErrAdjust.setVcTranNo(reqHead.getField(6).getValue());
				tblMchtErrAdjustDAO.update(mchtErrAdjust);
				Log.log("审核通过，发送报文成功，数据更新成功！");
				return "审核通过成功！";
			} else if("1105".equals(respCode)){
				responseResult = respBody.getField(1).getRealValue();
				Log.log("报文异常信息："+responseResult);
				return "审核通过失败，备付金余额不足！";
			}else{
				responseResult = respBody.getField(1).getRealValue();
				Log.log("报文异常信息："+responseResult);
				return "审核通过失败！";
			}
			
		} catch (Exception e) {
			Log.log("异常信息："+e.getMessage());
			return "审核通过失败！";
		}

		// 报文结束
		
	}

	

	
	
	
}