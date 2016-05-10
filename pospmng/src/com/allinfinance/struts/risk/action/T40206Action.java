package com.allinfinance.struts.risk.action;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.risk.T40206BO;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40206Action extends BaseAction{

	private T40206BO t40206BO = (T40206BO) ContextUtil.getBean("T40206BO");
	
	private String riskLvl;
	private String resved;
	private String menuList;
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("add".equals(getMethod())) {			
			rspCode = add();			
		} else if("update".equals(getMethod())) {
			rspCode = update();
		}else if("delete".equals(getMethod())) {
			rspCode = delete();
		}
		return rspCode;
	}

	private String add() {
		if(!t40206BO.query(riskLvl).isEmpty()){
			return "该风控级别信息已经存在，请刷新重新确认！";
		}
		jsonBean.parseJSONArrayData(getMenuList());
		int len = jsonBean.getArray().size();
		List<String> addList = new ArrayList<String>();
		for(int i = 0; i < len; i++) {
			addList.add(jsonBean.getJSONDataAt(i).get("saModelKind").toString());
		}
		return t40206BO.add(riskLvl,resved,addList);
	}

	private String update() {
		if(t40206BO.query(riskLvl).isEmpty()){
			return "该风控级别信息不存在，请刷新重新确认！";
		}
		jsonBean.parseJSONArrayData(getMenuList());
		int len = jsonBean.getArray().size();
		List<String> keepList = new ArrayList<String>();
		List<String> deleteList = t40206BO.query(riskLvl);
		List<String> addList = new ArrayList<String>();
		for(int i = 0; i < len; i++) {
			String riskIdCheck = jsonBean.getJSONDataAt(i).get("saModelKind").toString();
			if(deleteList.contains(riskIdCheck)){
				keepList.add(riskIdCheck);
				deleteList.remove(riskIdCheck);
			}else{
				addList.add(riskIdCheck);
			}
		}
		return t40206BO.update(riskLvl,resved,keepList,deleteList,addList);
	}

	private String delete() {
		if(t40206BO.query(riskLvl).isEmpty()){
			return "该风控级别信息不存在，请刷新重新确认！";
		}
		return t40206BO.delete(riskLvl,t40206BO.query(riskLvl));
	}

	/**
	 * @return the riskLvl
	 */
	public String getRiskLvl() {
		return riskLvl;
	}

	/**
	 * @param riskLvl the riskLvl to set
	 */
	public void setRiskLvl(String riskLvl) {
		this.riskLvl = riskLvl;
	}

	/**
	 * @return the resved
	 */
	public String getResved() {
		return resved;
	}

	/**
	 * @param resved the resved to set
	 */
	public void setResved(String resved) {
		this.resved = resved;
	}

	/**
	 * @return the menuList
	 */
	public String getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList the menuList to set
	 */
	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}
	
}
