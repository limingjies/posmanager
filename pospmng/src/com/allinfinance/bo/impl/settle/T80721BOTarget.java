package com.allinfinance.bo.impl.settle;


import java.math.BigDecimal;

import com.allinfinance.bo.settle.T80721BO;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblBrhErrAdjustDAO;
import com.allinfinance.log.Log;
import com.allinfinance.po.settle.TblBrhErrAdjust;
import com.allinfinance.system.util.ContextUtil;

/**
 * @author 
 *
 */
public class T80721BOTarget implements T80721BO {
	

	private TblBrhErrAdjustDAO tblBrhErrAdjustDAO;//=(TblMchtErrAdjustDAO) ContextUtil.getBean("TblMchtErrAdjustDAO"); 
	private ICommQueryDAO commQueryDAO;
	
	/**
	 * @return the tblMchtErrAdjustDAO
	 */
	public TblBrhErrAdjustDAO getTblBrhErrAdjustDAO() {
		return tblBrhErrAdjustDAO;
	}



	/**
	 * @param tblMchtErrAdjustDAO the tblMchtErrAdjustDAO to set
	 */
	public void setTblBrhErrAdjustDAO(TblBrhErrAdjustDAO tblBrhErrAdjustDAO) {
		this.tblBrhErrAdjustDAO = tblBrhErrAdjustDAO;
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
	 * 合作伙伴手工差错调整审核
	 */
	private String approve(TblBrhErrAdjust tblBrhErrAdjust, int type) {
		TblBrhErrAdjust brhErrAdjust = tblBrhErrAdjustDAO.get(tblBrhErrAdjust.getId());
		
		brhErrAdjust.setApproveAdvice(tblBrhErrAdjust.getApproveAdvice());
		brhErrAdjust.setApproveOpr(tblBrhErrAdjust.getApproveOpr());
		brhErrAdjust.setApproveStatus(tblBrhErrAdjust.getApproveStatus());
		brhErrAdjust.setApproveTime(tblBrhErrAdjust.getApproveTime());
		brhErrAdjust.setPostStatus(tblBrhErrAdjust.getPostStatus());
		brhErrAdjust.setPostTime(tblBrhErrAdjust.getPostTime());
		brhErrAdjust.setVcTranNo(tblBrhErrAdjust.getVcTranNo());
		brhErrAdjust.setUpdOpr(tblBrhErrAdjust.getUpdOpr());
		brhErrAdjust.setUpdTime(tblBrhErrAdjust.getUpdTime());
		tblBrhErrAdjustDAO.update(brhErrAdjust);
		
		return "";
	}
	
	private String approveChk(TblBrhErrAdjust tblBrhErrAdjust) {
		TblBrhErrAdjust brhErrAdjust = tblBrhErrAdjustDAO.get(tblBrhErrAdjust.getId());
		if(brhErrAdjust==null){
			return "审核的数据不存在！";
		}
		if(!"0".equals(brhErrAdjust.getApproveStatus())){
			return "该记录已经被审核过，请选择其他记录！";
		}
		if(tblBrhErrAdjust.getApproveOpr() != null && brhErrAdjust.getCrtOpr().trim().equals(tblBrhErrAdjust.getApproveOpr().trim())){
			return "同一操作员不能审核！";
		}
		return "";
	}

	@Override
	public String save(TblBrhErrAdjust tblBrhErrAdjust) {
		tblBrhErrAdjustDAO.save(tblBrhErrAdjust);
		//发送报文
		return "保存成功";
	}

	@Override
	public String update(TblBrhErrAdjust tblBrhErrAdjust) {
		tblBrhErrAdjustDAO.update(tblBrhErrAdjust);
		//发送报文
		return "保存成功";
	}

	@Override
	public TblBrhErrAdjust get(String id) {
		return tblBrhErrAdjustDAO.get(id);
	}
	
	/**
	 * 合作伙伴手工差错调整审核通过
	 */
	@Override
	public String accept(TblBrhErrAdjust tblBrhErrAdjust) {

		String result = approveChk(tblBrhErrAdjust);
		if (!"".equals(result)) {
			return result;
		}
		
		try {
			
			Double money=tblBrhErrAdjust.getMoney()*100;
			BigDecimal bdMoney=new BigDecimal(money);
			Long lMoney = bdMoney.divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP).longValue();
			
			// 发送报文
			Log.log("发送合作伙伴手工差错调整审核报文");
			Msg reqBody = MsgEntity.genBrhErrAdjustRequestBodyMsg();
			Msg reqHead = MsgEntity.genCommonRequestHeadMsg("E0020","0");
//			reqHead.getField(1).setValue("00");// 版本号
//			reqHead.getField(2).setValue("E0020");// 交易码
			reqHead.getField(3).setValue("0");// 子交易码
			reqHead.getField(8).setValue("0000000000");// 接入方交易码
			reqHead.getField(9).setValue("");// 交易类型+接入方检索参考号
			reqHead.getField(11).setValue("");// 请求方交易日期
			reqHead.getField(12).setValue("");
			reqHead.getField(14).setValue("");
			reqHead.getField(15).setValue("");
			reqHead.getField(16).setValue("");
			reqHead.getField(17).setValue(tblBrhErrAdjust.getBrhNo());// 外部账号
			reqHead.getField(18).setValue("00000002");// 外部账号类型
			reqHead.getField(20).setValue("");
			reqHead.getField(21).setValue("");
			reqHead.getField(22).setValue("");
			reqHead.getField(23).setValue("");
			reqHead.getField(24).setValue("");
			reqHead.getField(25).setValue("+");
			reqHead.getField(26).setValue(lMoney.toString());// 交易额
			reqHead.getField(27).setValue("");
			reqHead.getField(28).setValue("");
			reqHead.getField(28).setValue("");

			reqBody.getField(1).setValue(tblBrhErrAdjust.getBrhNo());//合作伙伴号
			reqBody.getField(2).setValue(tblBrhErrAdjust.getTradeType());//交易类型

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
				Log.log("合作伙伴手工差错调整审核");
				// 记入‘交易流水号’
				tblBrhErrAdjust.setVcTranNo(reqHead.getField(6).getRealValue());
				//审核
				approve(tblBrhErrAdjust, 0);
				Log.log("审核通过，发送报文成功，数据更新成功！");
				return "审核通过成功！";
			} else{
				responseResult = respBody.getField(1).getRealValue();
				Log.log("报文异常信息："+responseResult);
				return "审核通过失败！"+responseResult;
			}
			
		} catch (Exception e) {
			Log.log("异常信息："+e.getMessage());
			return "审核通过失败！";
		}

		// 报文结束
	}

	/**
	 * 合作伙伴手工差错调整审核拒绝
	 */
	@Override
	public String refuse(TblBrhErrAdjust tblBrhErrAdjust) {
		String result = approveChk(tblBrhErrAdjust);
		if (!"".equals(result)) {
			return result;
		}
		approve(tblBrhErrAdjust, 1);
		return "审核拒绝成功！";
	}
	
}