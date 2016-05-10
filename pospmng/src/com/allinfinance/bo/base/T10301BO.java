package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.po.TblRoleFuncMap;
import com.allinfinance.po.TblRoleInf;

/**
 * Title:角色信息BO
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
public interface T10301BO {
	/**
	 * 新增角色信息
	 * @param tblRoleInf    角色信息
	 * @param tblRoleFuncMapList    角色权限信息
	 * @return
	 */
	public String add(TblRoleInf tblRoleInf,List<TblRoleFuncMap> tblRoleFuncMapList);
	/**
	 * 删除角色信息
	 * @param roleId    角色编号
	 * @return
	 */
	public String delete(int roleId);
	/**
	 * 批量更新角色信息
	 * @param tblRoleInfList    角色信息集合
	 * @return
	 */
	public String update(List<TblRoleInf> tblRoleInfList);
	/**
	 * 加载角色权限信息
	 * @param roleId    角色编号
	 * @return
	 */
	public List<TblRoleFuncMap> getMenuList(int roleId);
	/**
	 * 更新角色权限信息
	 * @param tblRoleFuncMapList    待添加角色权限信息
	 * @param tblRoleFuncMapDeleteList    待删除角色权限信息
	 * @return
	 */
	public String updateRoleMenu(List<TblRoleFuncMap> tblRoleFuncMapList,
			List<TblRoleFuncMap> tblRoleFuncMapDeleteList);
	
	/**
	 * 更新授权信息
	 * 
	 * @param menus
	 * @return
	 * 2011-7-8下午03:06:12
	 */
	public String updateAuthorMenu(String menus) throws Exception;
	
	/**
	 * 获取系统中的所有菜单
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getAllMenu();
	
}
