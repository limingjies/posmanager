package com.allinfinance.dwr.daytrade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.allinfinance.bo.impl.daytrade.HessianFront;
import com.allinfinance.common.Constants;
import com.allinfinance.common.FrontConstants;
import com.allinfinance.frontend.dto.acctmanager.AcctDtlRtnJsonDto;
import com.allinfinance.frontend.dto.acctmanager.TransConsumptionDto;
import com.allinfinance.frontend.dto.withdraw.WithDrawItem;
import com.allinfinance.frontend.dto.withdraw.WithDrawJsonDto;
import com.allinfinance.frontend.dto.withdraw.WithDrawRtnJsonDto;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.po.daytrade.WebFrontTxnLogPK;
import com.allinfinance.system.util.JSONBean;

/**
 * FrontT70404.java
 * 
 * Project: T+0
 * 
 * Description:
 * =========================================================================
 * 
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容. 序号 时间 作者 修改内容 ========== =============
 * ============= ============================= 1. 2015年6月10日 尹志强 created this
 * class.
 * 
 * 
 * Copyright Notice: Copyright (c) 2009-2015 Allinpay Financial Services Co.,
 * Ltd. All rights reserved.
 * 
 * This software is the confidential and proprietary information of
 * allinfinance.com Inc. ('Confidential Information'). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with Allinpay Financial.
 * 
 * Warning:
 * =========================================================================
 * 
 */
public class FrontT70404 extends HessianFront {
	/**
	 * 业务交易码——消费调账
	 */
	public String FRONT_CODE;

	public String doTxn(String frontCode,String withDrawInfo) {
		TransConsumptionDto transConsumptionDto = sendMsgCreator(withDrawInfo);
		Object[] get = sendMsg(frontCode,transConsumptionDto); // 正式使用
		// Object[] get = test(withDrawJson); // 测试使用
		String retJson = analyMsg(frontCode,get);
		return retJson;
	}

	/**
	 * 组装发送信息
	 * 
	 * @param withDrawInfo
	 * @param operator
	 * @return
	 */
	private TransConsumptionDto sendMsgCreator(String withDrawInfo) {
		// 组装发送信息
		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(withDrawInfo);
		TransConsumptionDto item = new TransConsumptionDto();
		JSONObject obj = jsonBean.getJSONDataAt(0);
		item.setCardNo(obj.get("cardNo").toString());
		item.setCardType(obj.get("cardType").toString());
		item.setMerchantId(obj.get("merchantId").toString());
		item.setPospInstDate(obj.get("pospInstDate").toString());
		item.setPospSysSeqNum(obj.get("pospSysSeqNum").toString());
		item.setTransFee(getBigDecimal(obj.get("transFee")));
		item.setWebTime(obj.get("webTime").toString());
		item.setWebSeqNum(obj.get("pospSysSeqNum").toString());
		item.setTransAmount(getBigDecimal(obj.get("transAmount")));
		item.setSettleAmount(getBigDecimal(obj.get("settleAmount")));

		return item;
	}

	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal ret = null;
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else if (value instanceof Number) {
				ret = new BigDecimal(((Number) value).doubleValue());
			} else {
				throw new ClassCastException("Not possible to coerce [" + value
						+ "] from class " + value.getClass()
						+ " into a BigDecimal.");
			}
		}
		return ret;
	}

	/**
	 * 发送信息
	 * 
	 * @param withDrawJson
	 * @return
	 */
	private Object[] sendMsg(String frontCode,TransConsumptionDto transConsumptionDto) {
		// 发送信息
		Object[] send = new Object[2];
		send[0] = transConsumptionDto;
		send[1] = new WithDrawRtnJsonDto();

		// 返回获取信息
		return super.doTxn(frontCode, send);
	}

	/**
	 * 分析信息
	 * 
	 * @param obj
	 * @return
	 */
	private String analyMsg(String frontCode,Object[] obj) {
		JSONBean jsonBean = new JSONBean();
		// 分析信息
		if (Constants.SUCCESS_CODE.equals(obj[0])) {
			// 前置系统返回信息
			WithDrawRtnJsonDto withDrawRtn = (WithDrawRtnJsonDto) obj[1];
			if (FrontConstants.SYS_CODE_SUCCESS
					.equals(withDrawRtn.getRtnCode())) {
				// 组装返回前置系统成功信息
				jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
				jsonBean.addJSONElement("rtnCode", withDrawRtn.getRtnCode()); // 返回码
				jsonBean.addJSONElement("amount", withDrawRtn.getAmount()); // 申请提现的金额
				jsonBean.addJSONElement("fee", withDrawRtn.getFee()); // 提现手续费
				jsonBean.addJSONElement("wdAmount",
						withDrawRtn.getWithDrawAmount()); // 提现金额
			} else {
				// 组装返回前置系统失败信息
				jsonBean.addJSONElement(Constants.SUCCESS_HEADER, false);
				jsonBean.addJSONElement("rtnCode", withDrawRtn.getRtnCode()); // 返回码
				if (frontCode.equals(withDrawRtn.getRtnCode())) {
					List<WithDrawItem> withDrawItems = withDrawRtn.getItems();
					List<Object> jsonDataList = new ArrayList<Object>();
					for (WithDrawItem item : withDrawItems) {
						if (isNotEmpty(item.getRetCode())) {
							Map<String, String> jsonMap = new HashMap<String, String>();
							jsonMap.put("instDate", item.getPospInstDate()); // 原交易流水时间
							jsonMap.put("sysSeqNum", item.getPospSysSeqNum()); // 原交易流水流水号
							jsonMap.put("retDesc", item.getRetDesc()); // 此交易错误描述
							jsonDataList.add(jsonMap);
						}
					}
					jsonBean.addChild(Constants.JSON_HEANDER_DATA, jsonDataList);
				}
			}
		} else {
			// 返回异常
			jsonBean.addJSONElement(Constants.SUCCESS_HEADER, false);
			jsonBean.addJSONElement("rtnCode", (String) obj[1]); // 返回异常
		}
		return jsonBean.toString();
	}

	public Object[] doTxn(String frontCode,WithDrawJsonDto withDrawJson) {

		// 定义返回信息
		Object[] ret;

		// 发送信息
		Object[] send = new Object[2];
		send[0] = withDrawJson;
		send[1] = new WithDrawRtnJsonDto();

		// 获取信息
		Object[] get = super.doTxn(frontCode, send);

		// 分析信息
		if (Constants.SUCCESS_CODE.equals(get[0])) {
			ret = new Object[2];
			// 前置系统返回信息
			WithDrawRtnJsonDto withDrawRtn = (WithDrawRtnJsonDto) get[1];
			if (FrontConstants.SYS_CODE_SUCCESS
					.equals(withDrawRtn.getRtnCode())) {
				// 组装流水信息
				WebFrontTxnLogPK id = new WebFrontTxnLogPK();
				id.setWebTime(withDrawJson.getWebTime());
				id.setWebSeqNum(withDrawJson.getWebSeqNum());
				WebFrontTxnLog webFrontTxnLog = new WebFrontTxnLog();
				webFrontTxnLog.setId(id);
				webFrontTxnLog.setTxnCode(frontCode);
				webFrontTxnLog.setTxnLog(withDrawRtn);
				// 组装返回前置系统成功信息
				ret[0] = Constants.SUCCESS_CODE;
				ret[1] = webFrontTxnLog;
			} else {
				// 组装返回前置系统失败信息
				ret[0] = Constants.FAILURE_CODE;
				ret[1] = withDrawRtn.getRtnCode();
			}
		} else {
			// 返回异常
			ret = get;
		}
		return ret;
	}

}
