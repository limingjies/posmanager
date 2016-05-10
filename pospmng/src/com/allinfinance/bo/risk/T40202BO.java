package com.allinfinance.bo.risk;

import java.io.File;
import java.util.List;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblCtlMchtInf;

/**
 * Title: 商户黑名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-26
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public interface T40202BO {
	/**
	 * 添加商户黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:33:52
	 */
	public String add(TblCtlMchtInf tblCtlMchtInf) throws Exception;
	/**
	 * 更新商户黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:34:46
	 */
	public String update(TblCtlMchtInf tblCtlMchtInf) throws Exception;
	/**
	 * 查询商户黑名单信息
	 * @param key
	 * @return
	 * 2010-8-26下午11:35:16
	 */
	public TblCtlMchtInf get(String key);
	
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception;
	
	/**
	 * 删除商户黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:34:46
	 */
	public void delete(String key) throws Exception;
}
