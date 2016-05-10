package com.allinfinance.bo.agentpay;

import java.util.List;

import com.allinfinance.po.agentpay.TblStlmFileTransInf;
import com.allinfinance.po.agentpay.TblStlmFileTransInfPK;

/**
 * 对账文件传输表BO
 * @author huangjl
 *
 * 2014年4月25日 下午4:20:01
 */

public interface T90701BO {
	

	public TblStlmFileTransInf get(TblStlmFileTransInfPK tblStlmFileTransInfPK);
	public String add(TblStlmFileTransInf tblStlmFileTransInf);
	public String update(List<TblStlmFileTransInf> TblStlmFileTransInfList);
	public String update(TblStlmFileTransInf TblStlmFileTransInf);
	public String delete(TblStlmFileTransInf TblStlmFileTransInf);
	public String delete(TblStlmFileTransInfPK id);
	/**
	 * 修改发送全部对账文件的时候的最近更新柜员
	 *@author huangjl
	 * @return
	 * 2014年4月25日 下午5:10:16
	 */
	public String updateAllTlr(String tlr);

}
