/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-6-23       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 allinfinance, Inc. All rights reserved.
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
package com.allinfinance.dwr.mchnt;

import java.util.LinkedHashMap;

import com.allinfinance.common.StringUtil;

/**
 * Title: 远程获取字段的翻译
 * 
 * File: RemoteTransDWR.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-23
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class RemoteTransDWR {
	
	
	public static RemoteTransMethod remoteTransMethod = new RemoteTransMethod();
	
	public LinkedHashMap<String, String> getTrans(String id, String val){
		
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		try {
			String str = (String) RemoteTransMethod.class.getMethod(id, String.class)
					.invoke(remoteTransMethod, new Object[] {val});

			if(!StringUtil.isNull(str)){
				dataMap.put(id + val, str);
				return dataMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataMap.put(id + val, "-");
		return dataMap;
	}

}

