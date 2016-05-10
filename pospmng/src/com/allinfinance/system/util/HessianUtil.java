package com.allinfinance.system.util;

import com.alibaba.fastjson.JSON;

/**
 *  HessianUtil.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年6月1日	  徐鹏飞          created this class.
 *    
 *  
 *  Copyright Notice:
 *   Copyright (c) 2009-2015   Allinpay Financial Services Co., Ltd. 
 *    All rights reserved.
 *
 *   This software is the confidential and proprietary information of
 *   allinfinance.com  Inc. ('Confidential Information').  You shall not
 *   disclose such Confidential Information and shall use it only in
 *   accordance with the terms of the license agreement you entered
 *   into with Allinpay Financial.
 *
 *  Warning:
 *  =========================================================================
 *  
 */
public class HessianUtil {

    /**
     * 
     * 将待发送的数据转换为json格式
     * @param txnCode 交易码
     * @param obj 待处理对象
     * @return json格式数据
     */
	public static String jsonCreator(String txnCode, Object obj){
		String ret = JSON.toJSONString(obj);
		if(txnCode != null||!"".equals(txnCode)){
			ret = txnCode + ret;
		}
    	return ret;
    }
    
    /**
     * 将接受的json格式数据转换为对象
     * @param txnCode 交易码
     * @param msg 待处理信息
     * @param targetObj 目标对象
     * @return 返回信息对象
     */
	public static Object objCreator(String txnCode, String msg, Object targetObj){
		if(txnCode != null||!"".equals(txnCode)){
			msg = msg.replaceFirst(txnCode, "");
		}
		targetObj = JSON.toJavaObject(JSON.parseObject(msg), targetObj.getClass());
    	return targetObj;
    }
}
