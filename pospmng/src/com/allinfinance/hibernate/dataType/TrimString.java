/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-8-16       first release
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
package com.allinfinance.hibernate.dataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.type.StringType;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-16
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class TrimString extends StringType {
		public TrimString(){
		}
		private static final long serialVersionUID = 6370987663636950322L;
		public Object get(ResultSet rs, String name) throws SQLException { 
	    	String result = (String)super.get(rs, name) ;
	    	if ( result == null || result.trim().equals(""))
	    		return result;
	    	else
	    		return result.trim(); 
	    }
}
