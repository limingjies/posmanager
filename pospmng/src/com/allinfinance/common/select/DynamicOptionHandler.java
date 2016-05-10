package com.allinfinance.common.select;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.allinfinance.common.Operator;
import com.allinfinance.system.util.JSONBean;

public class DynamicOptionHandler {

	static String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";

	public static String handle(int start, int limit, String inputValue,
			String methodName, Operator operator, HttpServletRequest request) {

		try {
			
			DynamicSqlBean bean = (DynamicSqlBean) DynamicSQL.class.getMethod(
					methodName,
					new Class[] { String.class, Operator.class,
							HttpServletRequest.class }).invoke(
					DynamicSQL.class,
					new Object[] { inputValue, operator, request });

			List<Object[]> dataList = bean.getDao().findBySQLQuery(bean.getSql(), start, limit);
			
			//没有数据直接返回
			if (null == dataList || dataList.size() == 0) {
				return jsonData;
			}

			@SuppressWarnings("unchecked")
			List<Object[]> allDataList = bean.getDao().findBySQLQuery(bean.getSql());
			String count = String.valueOf(allDataList.size());
			
			Iterator<Object[]> it = dataList.iterator();

			Map<String, Object> jsonDataMap = new HashMap<String, Object>();
			LinkedList<Object> jsonDataList = new LinkedList<Object>();
			Map<String, String> tmpMap = null;
			while (it.hasNext()) {
				tmpMap = new LinkedHashMap<String, String>();
				Object[] obj = it.next();
				tmpMap.put("valueField", obj[0].toString());
				tmpMap.put("displayField", obj[1].toString());
				jsonDataList.add(tmpMap);
			}
			jsonDataMap.put("data", jsonDataList);
			jsonDataMap.put("totalCount", count);
			return JSONBean.genMapToJSON(jsonDataMap);

		} catch (Exception e) {
			e.printStackTrace();
			return jsonData;
		}
	}
}
