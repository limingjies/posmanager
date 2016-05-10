package com.allinfinance.struts.mchnt.action;

import com.allinfinance.bo.mchnt.T20303BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.mchnt.TblInfMchntTp;
import com.allinfinance.po.mchnt.TblInfMchntTpPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T20702Action extends BaseAction {
	
	private T20303BO t20303BO = (T20303BO) ContextUtil.getBean("T20303BO");
	
	@Override
	protected String subExecute(){
		try {
			if("edit".equals(method)) {
				rspCode = edit();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，MCC-费率关联" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	public String edit() {
		
		// 读取计费代码集合，组装成discCd
		StringBuffer buffer = new StringBuffer();
		jsonBean.parseJSONArrayData(getMenuList());
		int len = jsonBean.getArray().size();		
		for(int i=0; i<len; i++) {
			buffer.append(jsonBean.getJSONDataAt(i).getString("valueId"));
			buffer.append(",");
		}
		String discCd = buffer.toString();
		if(discCd.endsWith(",")) {
			discCd = discCd.substring(0, discCd.length()-1);
		}
		
		// 读取MCC信息
		TblInfMchntTpPK pk = new TblInfMchntTpPK();
		pk.setMchntTp(mccId.substring(0,4));
		pk.setFrnIn("0");	
		TblInfMchntTp tblInfMchntTp = t20303BO.get(pk);
		
		// 将计费代码集合存入MCC中
		tblInfMchntTp.setDiscCd(discCd);		
		t20303BO.update(tblInfMchntTp);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String mccId;
	private String menuList;
	
	public String getMccId() {
		return mccId;
	}

	public void setMccId(String mccId) {
		this.mccId = mccId;
	}

	public String getMenuList() {
		return menuList;
	}

	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}
}
