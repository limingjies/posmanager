/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-6-30       first release
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
package com.allinfinance.system.util;

import java.io.File;
import java.io.FilenameFilter;


public class FileFilter implements FilenameFilter{

	/**
	 * 
	 */
	private String name;
	
	
	public FileFilter(String name) {
		this.name = name;
	}
	
	
	public boolean accept(File file, String path) {
		if (path.indexOf(name) != -1) {
			return true;
		}
		return false;
	}
	
	

}
