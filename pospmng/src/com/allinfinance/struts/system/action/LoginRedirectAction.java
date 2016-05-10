package com.allinfinance.struts.system.action;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblTxnInfoConstants;
import com.allinfinance.dao.iface.TblTxnInfoDAO;
import com.allinfinance.dao.iface.base.TblOprInfoDAO;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblOprInfo;
import com.allinfinance.po.TblTxnInfo;
import com.allinfinance.po.TblTxnInfoPK;
import com.allinfinance.startup.init.MenuInfoUtil;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.TxnInfoUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Title:登录后跳转
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-3
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class LoginRedirectAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LoginRedirectAction.class);
	public String execute() throws Exception {
		String oprId = (String) getSessionAttribute("oprId");
		log(oprId);
		if(oprId == null)
		{
			return LOGIN;
		}
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		
			
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(oprId);
		
		//判断操作员是否存在
		if(tblOprInfo == null) {
			ServletActionContext.getRequest().getSession().removeAttribute("oprId");
			log("登录失败，操作员不存在。编号[ " + oprId + " ]");
			return LOGIN;
		}
		//验证通过
		T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
		TblBrhInfo tblBrhInfo = t10101BO.get(tblOprInfo.getBrhId());
		Operator operator = new Operator();
		operator.setOprId(oprId);
		operator.setOprName(StringUtils.trim(tblOprInfo.getOprName()));
		operator.setOprBrhId(tblBrhInfo.getBrhId());
		operator.setOprBrhName(StringUtils.trim(tblBrhInfo.getBrhName()));
		operator.setOprDegree(tblOprInfo.getOprDegree());
		operator.setOprBrhLvl(tblBrhInfo.getBrhLevel());
		// 操作员历史备注信息
		operator.setResvInfo(tblOprInfo.getResvInfo());
		operator.setLastLoginTime(tblOprInfo.getLastLoginTime());
		operator.setLastLoginIp(tblOprInfo.getLastLoginIp());
		operator.setLastLoginStatus(tblOprInfo.getLastLoginStatus());
		//本行及下属机构信息MAP
		Map<String, String> brhMap = new LinkedHashMap<String, String>();
		brhMap.put(operator.getOprBrhId(),operator.getOprBrhName());
		operator.setBrhBelowMap(CommonFunction.getBelowBrhMap(brhMap));
		operator.setBrhBelowId(CommonFunction.getBelowBrhInfo(operator.getBrhBelowMap()));
		operator.setMchtBelowId(CommonFunction.getBelowMchtByBrhId(operator.getOprBrhId()));
		log("本行及下属机构编号：" + operator.getBrhBelowId());
		setSessionAttribute(Constants.OPERATOR_INFO, operator);
		//银联编号
		setSessionAttribute("CUP_BRH_ID", tblBrhInfo.getCupBrhId());

		
		String sql = "select cup_brh_id from tbl_brh_info where trim(brh_id) = '" + operator.getOprBrhId().trim() + "'";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				String cup = (String) list.get(0);
				setSessionAttribute("cupBrhId", cup);
			} else {
				setSessionAttribute("cupBrhId", "-");
			}
		}
		LinkedHashMap<String,Object> menuMap = MenuInfoUtil.setOperatorMenuWithDegree(operator.getOprDegree(),
						ServletActionContext.getRequest().getContextPath());
		//LinkedHashMap<String, Object> menuMap = new LinkedHashMap<String, Object>();
		Iterator<String> iter = menuMap.keySet().iterator();
		LinkedList<Object> toolList = new LinkedList<Object>();
		while(iter.hasNext()) {
			toolList.add(menuMap.get(iter.next()));
		}
		String toolBarStr = JSONArray.fromObject(toolList).toString();
		//转换JSON格式中的方法名称
		toolBarStr = toolBarStr.replaceAll(Constants.MENU_LVL1_JSON_FUNC, Constants.MENU_LVL1_FUNC).
									replaceAll(Constants.MENU_LVL3_JSON_FUNC, Constants.MENU_LVL3_FUNC);
		//设置操作员
		setSessionAttribute(Constants.OPERATOR_INFO, operator);
		//去掉菜单树图标样式
		removeTreeCls(menuMap);
		//设置菜单树
		setSessionAttribute(Constants.TREE_MENU_MAP, menuMap);
		//设置工具栏
		setSessionAttribute(Constants.TOOL_BAR_STR, toolBarStr);
		//设置交易权限
		setSessionAttribute(Constants.USER_AUTH_SET, MenuInfoUtil.getAuthSet(operator.getOprDegree()));
		
		
		saveLogInfo(operator);
		
		return SUCCESS;
	}
	
	/**
	 * 记录登陆日志
	 * @param menuMap
	 * @return
	 */
	private void saveLogInfo(Operator operator){
		TblTxnInfoDAO txnInfoDAO = (TblTxnInfoDAO) ContextUtil.getBean("TxnInfoDAO");
		TblTxnInfo tblTxnInfo = new TblTxnInfo();
		TblTxnInfoPK tblTxnInfoPK = new TblTxnInfoPK();				
		String acctTxnDate = CommonFunction.getCurrentDate();				
		tblTxnInfoPK.setAcctDate(acctTxnDate);
		tblTxnInfoPK.setTxnSeqNo(GenerateNextId.getTxnSeq());				
		tblTxnInfo.setId(tblTxnInfoPK);
		
		String currentTime = CommonFunction.getCurrentDateTime();				
		tblTxnInfo.setTxnDate(currentTime.substring(0, 8));
		tblTxnInfo.setTxnTime(currentTime.substring(8, 14));				
		tblTxnInfo.setTxnCode("10000");
		tblTxnInfo.setSubTxnCode("01");
		tblTxnInfo.setOprId(operator.getOprId());
		tblTxnInfo.setOrgCode(operator.getOprBrhId());
		tblTxnInfo.setIpAddr(getRequest().getRemoteHost());
		tblTxnInfo.setTxnName(TxnInfoUtil.getTxnInfo(tblTxnInfo.getTxnCode() + "." + tblTxnInfo.getSubTxnCode()));
		tblTxnInfo.setTxnSta(TblTxnInfoConstants.TXN_STA_SUCCESS);
		tblTxnInfo.setErrMsg("-");
		
		txnInfoDAO.save(tblTxnInfo);
	}
	
	/**
	 * 去掉菜单树图标样式
	 * @param menuMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map removeTreeCls(Map<String,Object> menuMap) {
		Iterator<String> iter = menuMap.keySet().iterator();
		String key;
		Map map;
		List list;
		while(iter.hasNext()) {
			key = iter.next();
			if(menuMap.get(key) instanceof String && 
						Constants.TOOLBAR_ICON.equals(key)) {
				menuMap.remove(key);
				return menuMap;
			} else if(menuMap.get(key) instanceof Map) {
				map = (Map) menuMap.get(key);
				if(map.get(Constants.TOOLBAR_ICON) != null) {
					map.remove(Constants.TOOLBAR_ICON);
				}
				map = removeTreeCls(map);
				menuMap.put(key, map);
			}else if(menuMap.get(key) instanceof List) {
				list = (List)menuMap.get(key);
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i) instanceof Map) {
						list.set(i, removeTreeCls((Map)list.get(i)));
					}
				}
				menuMap.put(key, list);
			}
		}
		return menuMap;
	}
	/**
	 * 记录系统日志
	 * @param info
	 */
	protected void log(String info) {
		log.info(info);
	}
	/**
	 * 设置session的attribute
	 * @param name
	 * @param value
	 */
	protected void setSessionAttribute(String name,Object value) {
		ServletActionContext.getRequest().getSession().setAttribute(name, value);
	}
	/**
	 * 获得session的attribute
	 * @param name
	 */
	protected Object getSessionAttribute(String name) {
		return ServletActionContext.getRequest().getSession().getAttribute(name);
	}
	
	/**
	 * 获得request对象
	 * @return
	 * 2010-12-9 上午10:24:32
	 * Shuang.Pan
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
}
