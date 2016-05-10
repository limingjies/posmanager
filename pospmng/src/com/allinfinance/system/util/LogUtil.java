package com.allinfinance.system.util;

import org.apache.log4j.Logger;


/**
 * 详细日志记录
 * 该类所使用对象均需重写其toString(标准)方法
 * 
 * @author Gavin
 *
 */
public class LogUtil {

	private static final String TXN_LOG = "TxnLogDetail";

	/**
	 * 日志记录
	 * @param txnCode
	 * @param operation
	 * @param operator
	 * @param obj
	 */
	public static void log(String txnCode, String operation, String operator,
			Object obj) {
		try {
			StringBuffer finalBuffer = new StringBuffer();
			finalBuffer.append("TXN_CODE(Class):" + txnCode + "; ");
			finalBuffer.append("OPERATOR:" + operator + "; ");
			finalBuffer.append("OPERATE_DESCRIPTION:" + operation + "; ");
			finalBuffer
					.append("OBJECT_NAME:" + obj.getClass().getName() + "; ");
			finalBuffer.append("OBJECT_DETAIL:" + obj.toString());//需重写所传递对象的toString(标准)方法
			Logger.getLogger(TXN_LOG).info(finalBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(TXN_LOG).info("SYSTEM LOG ERROR", e);
		}
	}

	/**
	 * 含输入异常的日志记录
	 * @param txnCode
	 * @param operation
	 * @param operator
	 * @param obj
	 * @param throwable
	 */
	public static void log(String txnCode, String operation, String operator,
			Object obj, Throwable throwable) {
		try {
			StringBuffer finalBuffer = new StringBuffer();
			finalBuffer.append("TXN_CODE(Class):" + txnCode + "; ");
			finalBuffer.append("OPERATOR:" + operator + "; ");
			finalBuffer.append("OPERATE_DESCRIPTION:" + operation + "; ");
			finalBuffer
					.append("OBJECT_NAME:" + obj.getClass().getName() + "; ");
			finalBuffer.append("OBJECT_DETAIL:" + obj.toString());//需重写所传递对象的toString(标准)方法
			Logger.getLogger(TXN_LOG).info(finalBuffer.toString(), throwable);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(TXN_LOG).info("SYSTEM LOG ERROR", e);
		}
	}
}
