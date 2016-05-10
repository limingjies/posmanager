/*
 * 业务性质维护
 * zhaofachun
 * 2015-10-27
 */
package com.allinfinance.bo.route;

import com.allinfinance.po.route.TblRouteUpbrh;

public interface TblRouteUpbrhBO {
	//新增性质
	String addCharacter(TblRouteUpbrh tblRouteUpbrh);
	//更新性质
	String updateCharacter(TblRouteUpbrh tblRouteUpbrh);
    //删除性质
	String deleteCharacter(TblRouteUpbrh tblRouteUpbrh);
    //更新业务性质状态
	String updateStatus(String status,String brhId);
}
