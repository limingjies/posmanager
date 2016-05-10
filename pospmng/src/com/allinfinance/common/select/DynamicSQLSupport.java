package com.allinfinance.common.select;

import com.allinfinance.common.StringUtil;

public class DynamicSQLSupport {

	/**
	 * 组装适用于like关键字的sql
	 * @param srcSql
	 * @param field
	 * @param inputValue
	 * @return
	 * 2011-10-26下午05:48:25
	 */
	protected static String provideSqlLike(String srcSql, String field,
			String inputValue) {
		if (!StringUtil.isNull(inputValue)) {
			if (srcSql.toLowerCase().indexOf("where") != -1) {
				return " and " + field + " like '" + inputValue.trim() + "%'";
			} else {
				return " where " + field + " like '" + inputValue.trim() + "%'";
			}
		} else {
			return "";
		}
	}
	
	/**
	 * 模糊查询
	 * @param srcSql
	 * @param field
	 * @param inputValue
	 * @return
	 */
	protected static String provideSql(String srcSql, String field,
			String inputValue) {
		if (!StringUtil.isNull(inputValue) && !inputValue.trim().equals("-")) {
			if (srcSql.toLowerCase().indexOf("where") != -1) {
				return " and "+field+" like '"+ '%' + inputValue.trim() + '%' +"' ";
			} else {
				return " where "+field+" like '"+ '%'+ inputValue.trim() + '%' +"' ";
			}
		} else {
			return "";
		}
	}
	
	/**
	 * 组装适用于like关键字的sql,前后匹配
	 * @param srcSql
	 * @param field
	 * @param inputValue
	 * @return
	 * 2011-10-26下午05:48:25
	 */
	protected static String provideSqlDouLike(String srcSql, String field,
			String inputValue) {
		if (!StringUtil.isNull(inputValue)) {
			if (srcSql.toLowerCase().indexOf("where") != -1) {
				return " and " + field + " like '%" + inputValue.trim() + "%'";
			} else {
				return " where " + field + " like '%" + inputValue.trim() + "%'";
			}
		} else {
			return "";
		}
	}
	
	/**
	 * 组装适用于in关键字的sql
	 * @param srcSql
	 * @param field
	 * @param inputValue
	 * @return
	 * 2011-10-26下午05:48:43
	 */
	protected static String provideSqlIn(String srcSql, String field,
			String inputValue) {
		if (!StringUtil.isNull(inputValue)) {
			if (srcSql.toLowerCase().indexOf("where") != -1) {
				return " and " + field + " in " + inputValue.trim() + "";
			} else {
				return " where " + field + " in " + inputValue.trim() + "";
			}
		} else {
			return "";
		}
	}
}
