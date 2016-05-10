
package com.allinfinance.struts.settle.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.grid.GridConfigMethod;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

/**
 * 差错交易调账导出功能Action
 * 
 * @author luhq
 *
 */
public class T80731Action extends BaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected String subExecute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("CUSTOMER_QUERY_RECORD_COUNT", 65536);
		Object[] rst = (Object[])GridConfigMethod.getBthChkInfo(0, request);
		List<Object[]> datas = (List<Object[]>)rst[0];
		List<Object[]> ouputdatas = new ArrayList<Object[]>();
		
		for (Object[] row:datas) {
			Object[] newRow = new Object[row.length -1];
			for (int i=0; i<newRow.length; i++) {
				if (i < 20) {
					newRow[i] = row[i];
				} else {
					newRow[i] = row[i+1];
				}
			}
			ouputdatas.add(newRow);
		}
		String[] title={"清算日期","合作伙伴编号","合作伙伴号","合作伙伴名称","商户号","商户名称","收单卡号","通道卡号","收单交易金额","通道交易金额","商户手续费","合作伙伴分润佣金","交易日期","交易时间","渠道交易日期","渠道交易时间","流水号","参考号","渠道参考号","对账状态","不平（存疑）类型","调账状态","调账方式","调账人","调账时间","交易流水号"};
        String head="差错交易调账汇总报表";
        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + head+"_" + operator.getOprId() + ".xls";
       
        HashMap<String, Object> map=new HashMap<String, Object>();
        
        map.put("dataList", ouputdatas);
        map.put("title", title);
        map.put("fileName", fileName);
        map.put("head", head);
        map.put("isCount", false);
        //map.put("mapParm", mapParm);
        
        excelReport.reportDownloadTxnNew(map);
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}
	
}
