package com.allinfinance.bo.risk;

import java.io.File;
import java.util.List;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblCtlCardInf;

/**
 * Title: 卡黑名单信息管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-24
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public interface T40201BO {
	/**
	 * 添加卡黑名单信息
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:19:30
	 */
	public String add(TblCtlCardInf tblCtlCardInf) throws Exception;
	/**
	 * 修改卡黑名单信息
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:20:26
	 */
	public String update(TblCtlCardInf tblCtlCardInf) throws Exception;
	/**
	 * 黑名单文件导入
	 * @param fileList
	 * @return
	 * @throws Exception
	 * 2010-8-24下午06:23:33
	 */
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception;
	/**
	 * 查询卡黑名单信息
	 * @param key
	 * @return
	 * 2010-8-24下午11:09:53
	 */
	public TblCtlCardInf get(String key);
	/**
	 * 删除卡黑名单信息
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:20:26
	 */
	public void delete(String key) throws Exception;
}
