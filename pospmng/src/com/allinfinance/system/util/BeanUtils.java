/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-5-28       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2008 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.system.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Title:POJO工具
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-5-28
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	/**
	 * 将POJO属性为NULL的进行重新赋值
	 * @param obj
	 * @param val1 设置字符串属性
	 * @param val2 设置非字符串属性
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Object setNullValueWithValue(Object obj,String val1,int val2) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Method[] methods = obj.getClass().getMethods();
		String propertyName = null;
		String methodName = null;
		for(Method method : methods) {
			methodName = method.getName();
			if(methodName.startsWith("set")) {
				String type = method.getGenericParameterTypes()[0].toString();
				propertyName = methodName.substring(methodName.indexOf("set") + 3);
				propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
				if(getProperty(obj, propertyName) == null)
					if("class java.lang.String".equals(type)) {
						copyProperty(obj, propertyName, val1);
					} else {
						copyProperty(obj, propertyName, val2);
					}
					
			}
		}
		return obj;
	}
	
	
	
	/**
	 * 将obj为null的属性都设置为“-”
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Object setNullValueWithLine(Object obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Method[] methods = obj.getClass().getMethods();
		String propertyName = null;
		String methodName = null;
		for(Method method : methods) {
			methodName = method.getName();
			if(methodName.startsWith("set")) {
				propertyName = methodName.substring(methodName.indexOf("set") + 3);
				propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
				if(getProperty(obj, propertyName) == null)
					copyProperty(obj, propertyName, "-");
			}
		}
		return obj;
	}
	
	/**
	 * 根据属性集合封装对象
	 * @param obj
	 * @param iter
	 * @param jsonBean
	 * @param autoFill
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static Object setObjectWithPropertiesValue(Object obj,JSONBean jsonBean,boolean autoFill) 
						throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Iterator<String> iter = jsonBean.getObject().keys();
		while(iter.hasNext()) {
			String propertyName = iter.next();
			if(isContainProperty(obj, propertyName)) {
				copyProperty(obj, propertyName, 
							jsonBean.getStringElementByKey(propertyName));
			}
		}
		// 自动填充对象属性
		if(autoFill) {
			setNullValueWithValue(obj,"-",0);
		}
		return obj;
	}
	
	
	/**
	 * 检查对象是否包含指定的属性信息
	 * @param bean
	 * @param property
	 * @return
	 */
	public static boolean isContainProperty(Object bean,String property) {
		
		Method[] methods = bean.getClass().getMethods();
		
		String fieldName = null;
		
		for(Method method : methods) {
			
			fieldName = method.getName().substring(3);
			
			fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
			
			if(fieldName.equals(property)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得对象属性
	 * @param bean
	 * @param map
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static void iterateBeanProperties(Object bean, Map<String, String> map) 
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Method[] methods = bean.getClass().getMethods();
		String propertyName = null;
		String methodName = null;
		for(Method method : methods) {
			methodName = method.getName();
			if(methodName.startsWith("set")) {
				propertyName = methodName.substring(methodName.indexOf("set") + 3);
				propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
				map.put(propertyName, getProperty(bean, propertyName));
			}
		}
	}
	
	  /**
	   * 获得传入对象值为null的属性名
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames (Object source) {
	        final BeanWrapper src = new BeanWrapperImpl(source);
	        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	        Set<String> emptyNames = new HashSet<String>();
	        for(java.beans.PropertyDescriptor pd : pds) {
	            Object srcValue = src.getPropertyValue(pd.getName());
	            if (srcValue == null) emptyNames.add(pd.getName());
	        }
	        String[] result = new String[emptyNames.size()];
	        return emptyNames.toArray(result);
	    }

    /**
     * bean拷贝   为null值不拷贝
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
