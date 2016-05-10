package com.allinfinance.bo.impl.base;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.allinfinance.bo.base.T10301BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.TblRoleFuncMapDAO;
import com.allinfinance.dao.iface.TblRoleInfDAO;
import com.allinfinance.po.TblFuncInf;
import com.allinfinance.po.TblRoleFuncMap;
import com.allinfinance.po.TblRoleInf;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.JSONBean;

/**
 * Title:角色菜单BO实现
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10301BOTarget implements T10301BO {
	
	private TblRoleInfDAO tblRoleInfDAO;
	
	private TblRoleFuncMapDAO tblRoleFuncMapDAO;
	
	private ICommQueryDAO commQueryDAO;
	

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10301BO#add(com.allinfinance.po.TblRoleInf, java.util.List)
	 */
	public String add(TblRoleInf tblRoleInf,
			List<TblRoleFuncMap> tblRoleFuncMapList) {
		
		//先删除TBL_ROLE_FUNC_MAP
		if (!StringUtil.isNull(tblRoleInf.getRoleId())) {
			String sql = "delete from tbl_role_func_map where KEY_ID = " + tblRoleInf.getRoleId();
			commQueryDAO.excute(sql);
		}
		
		for(TblRoleFuncMap tblRoleFuncMap : tblRoleFuncMapList) {
			tblRoleFuncMapDAO.save(tblRoleFuncMap);
		}
		tblRoleInfDAO.save(tblRoleInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10301BO#delete(int)
	 */
	public String delete(int roleId) {
		for(TblRoleFuncMap tblRoleFuncMap : getMenuList(roleId)) {
			tblRoleFuncMapDAO.delete(tblRoleFuncMap);
		}
		tblRoleInfDAO.delete(roleId);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10301BO#getMenuList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<TblRoleFuncMap> getMenuList(int roleId) {
		
		String hql = "from com.allinfinance.po.TblRoleFuncMap t where t.Id.KeyId = " + roleId;
		
		return commQueryDAO.findByHQLQuery(hql);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10301BO#update(java.util.List)
	 */
	public String update(List<TblRoleInf> tblRoleInfList) {
		for(TblRoleInf tblRoleInf : tblRoleInfList) {
			tblRoleInfDAO.update(tblRoleInf);
		}
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10301BO#updateRoleMenu(java.util.List, java.util.List)
	 */
	public String updateRoleMenu(List<TblRoleFuncMap> tblRoleFuncMapList,
			List<TblRoleFuncMap> tblRoleFuncMapDeleteList) {
		for(TblRoleFuncMap tblRoleFuncMap : tblRoleFuncMapDeleteList) {
			tblRoleFuncMapDAO.delete(tblRoleFuncMap);
		}
		
		for(TblRoleFuncMap tblRoleFuncMap : tblRoleFuncMapList) {
			tblRoleFuncMapDAO.save(tblRoleFuncMap);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblRoleInfDAO
	 */
	public TblRoleInfDAO getTblRoleInfDAO() {
		return tblRoleInfDAO;
	}

	/**
	 * @param tblRoleInfDAO the tblRoleInfDAO to set
	 */
	public void setTblRoleInfDAO(TblRoleInfDAO tblRoleInfDAO) {
		this.tblRoleInfDAO = tblRoleInfDAO;
	}

	/**
	 * @return the tblRoleFuncMapDAO
	 */
	public TblRoleFuncMapDAO getTblRoleFuncMapDAO() {
		return tblRoleFuncMapDAO;
	}

	/**
	 * @param tblRoleFuncMapDAO the tblRoleFuncMapDAO to set
	 */
	public void setTblRoleFuncMapDAO(TblRoleFuncMapDAO tblRoleFuncMapDAO) {
		this.tblRoleFuncMapDAO = tblRoleFuncMapDAO;
	}

	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	/* 
	 * 更新授权信息
	 * 
	 * @see com.allinfinance.bo.base.T10301BO#updateAuthorMenu(java.lang.String)
	 */
	public String updateAuthorMenu(String menus) throws Exception{

		//全部重置
		String sql = "update TBL_FUNC_INF set misc1 = '2' where trim(misc1) in ('1','2')";
		commQueryDAO.excute(sql);
		if (menus.length() >= 5) {//该判断保证in条件中的数据是有效的
			//需授权的交易
			sql = "update TBL_FUNC_INF set misc1 = '1' where func_id in " + menus;
			commQueryDAO.excute(sql);
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAllMenu(){
		JSONBean allMenuBean = new JSONBean();
		String hql = "from com.allinfinance.po.TblFuncInf t where t.FuncType in ('0','1','2') order by t.FuncId";
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List<TblFuncInf> funcInfList = commQueryDAO.findByHQLQuery(hql);
		for(int i = 0,n = funcInfList.size(); i < n; i++) {
			TblFuncInf tblFuncInf = funcInfList.get(i);
			Map<String, Object> menuBean = new LinkedHashMap<String, Object>();
			menuBean.put("checked", "false");
			if(Constants.MENU_LVL_1.equals(tblFuncInf.getFuncType())) {//一级菜单
//				Map<String, Object> menuBean = new LinkedHashMap<String, Object>();
				menuBean.put(Constants.MENU_ID, tblFuncInf.getFuncId().toString().trim());
				menuBean.put(Constants.MENU_TEXT, tblFuncInf.getFuncName().trim());
				menuBean.put(Constants.MENU_CLS, Constants.MENU_FOLDER);
				allMenuBean.addJSONArrayElement(menuBean);
			} else if(Constants.MENU_LVL_2.equals(tblFuncInf.getFuncType())) {//二级菜单
//				Map<String, Object> menuBean = new LinkedHashMap<String, Object>();
				menuBean.put(Constants.MENU_ID, tblFuncInf.getFuncId().toString().trim());
				menuBean.put(Constants.MENU_TEXT, tblFuncInf.getFuncName().trim());
				menuBean.put(Constants.MENU_PARENT_ID, tblFuncInf.getFuncParentId().toString().trim());
				menuBean.put(Constants.MENU_CLS, Constants.MENU_FOLDER);
				addLvl2Menu(allMenuBean,menuBean);
			} else if(Constants.MENU_LVL_3.equals(tblFuncInf.getFuncType())) {
//				Map<String, Object> menuBean = new LinkedHashMap<String, Object>();
				menuBean.put(Constants.MENU_ID, tblFuncInf.getFuncId().toString().trim());
				menuBean.put(Constants.MENU_TEXT, tblFuncInf.getFuncName().trim());
				menuBean.put(Constants.MENU_PARENT_ID, tblFuncInf.getFuncParentId().toString().trim());
				menuBean.put(Constants.MENU_LEAF, true);
				//menuBean.put(Constants.MENU_URL, tblFuncInf.getPageUrl().trim());
				menuBean.put(Constants.MENU_CLS, Constants.MENU_FILE);	
				
				addLvl3Menu(allMenuBean,menuBean);			
			}
		}
		
		//删除没有用的菜单
		List<Object> menuLvl1List = allMenuBean.getDataList();
		for(int i = 0; i < menuLvl1List.size(); i++) {
			Map<String, Object> menuLvl1Bean = (Map<String, Object>) menuLvl1List.get(i);
			if(!menuLvl1Bean.containsKey(Constants.MENU_CHILDREN)) {
				menuLvl1List.remove(i);
				i--;
				continue;
			}
			List<Object> menuLvl2List = (List<Object>) menuLvl1Bean.get(Constants.MENU_CHILDREN);
			for(int j = 0; j < menuLvl2List.size(); j++) {
				Map<String, Object> menuLvl2Bean = (Map<String, Object>) menuLvl2List.get(j);
				if(!menuLvl2Bean.containsKey(Constants.MENU_CHILDREN)) {
					menuLvl2List.remove(j);
					menuLvl1Bean.put(Constants.MENU_CHILDREN, menuLvl2List);
					menuLvl1List.set(i, menuLvl1Bean);
					allMenuBean.setDataList(menuLvl1List);
					j--;
				}
			}
		}
		return allMenuBean.getDataList();
	}
	
	/**
	 * 将二级菜单添加到一级菜单中
	 * @param menuBean
	 */
	@SuppressWarnings("unchecked")
	public static void addLvl2Menu(JSONBean allMenuBean,Map<String, Object> menuBean) {
		List<Object> menuLvl1List = allMenuBean.getDataList();
		for(int i = 0,n = menuLvl1List.size(); i < n; i++) {
			Map<String, Object> tmpMenuBean = (Map<String, Object>) menuLvl1List.get(i);
			//如果二级菜单的父菜单编号和一级菜单编号一致，则加入到该一级菜单中
			if(tmpMenuBean.get(Constants.MENU_ID).toString().trim().equals(
					menuBean.get(Constants.MENU_PARENT_ID).toString().trim())) {
				if(!tmpMenuBean.containsKey(Constants.MENU_CHILDREN)) {
					LinkedList<Object> menuLvl2List = new LinkedList<Object>();
					menuLvl2List.add(menuBean);
					tmpMenuBean.put(Constants.MENU_CHILDREN,menuLvl2List);
				} else {
					LinkedList<Object> menuLvl2List = (LinkedList<Object>) tmpMenuBean.get(Constants.MENU_CHILDREN);
					menuLvl2List.add(menuBean);
					tmpMenuBean.put(Constants.MENU_CHILDREN,menuLvl2List);
				}
				menuLvl1List.set(i, tmpMenuBean);
			}
		}
		allMenuBean.setDataList(menuLvl1List);
	}
	
	/**
	 * 将三级菜单添加到二级菜单中
	 * @param menuBean
	 */
	@SuppressWarnings("unchecked")
	public static void addLvl3Menu(JSONBean allMenuBean,Map<String, Object> menuBean) {
		List<Object> menuLvl1List = allMenuBean.getDataList();
		for(int i = 0,n = menuLvl1List.size(); i < n; i++) {
			Map<String, Object> tmpMenuBeanLvl1 = (Map<String, Object>) menuLvl1List.get(i);
			LinkedList<Object> menuLvl2List = (LinkedList<Object>) tmpMenuBeanLvl1.get(Constants.MENU_CHILDREN);
			if(menuLvl2List == null) {
				continue;
			}
			for(int j = 0,m = menuLvl2List.size(); j < m; j++) {
				Map<String, Object> tmpMenuBeanLvl2 = (Map<String, Object>) menuLvl2List.get(j);
				//如果三级菜单的父菜单编号和 二级菜单编号一致，则加入到该一级菜单中
				if(tmpMenuBeanLvl2.get(Constants.MENU_ID).toString().trim().equals(
						menuBean.get(Constants.MENU_PARENT_ID).toString().trim())) {
					if(!tmpMenuBeanLvl2.containsKey(Constants.MENU_CHILDREN)) {
						LinkedList<Object> menuLvl3List = new LinkedList<Object>();
						menuLvl3List.add(menuBean);
						tmpMenuBeanLvl2.put(Constants.MENU_CHILDREN, menuLvl3List);
					} else {
						LinkedList<Object> menuLvl3List = (LinkedList<Object>) tmpMenuBeanLvl2.get(Constants.MENU_CHILDREN);
						menuLvl3List.add(menuBean);
						tmpMenuBeanLvl2.put(Constants.MENU_CHILDREN, menuLvl3List);
					}
					menuLvl2List.set(j, tmpMenuBeanLvl2);
				}
			}
			tmpMenuBeanLvl1.put(Constants.MENU_CHILDREN,menuLvl2List);
			menuLvl1List.set(i, tmpMenuBeanLvl1);
		}
		allMenuBean.setDataList(menuLvl1List);
	}
	
	
}
