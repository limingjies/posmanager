package com.allinfinance.bo.route;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.allinfinance.po.route.TblRouteMchtg;

public interface T110301BO {


	String addMchtToGroup(HttpServletRequest request);
	String bacthAddMchtUpbrh(int i, HttpServletRequest request, Integer newMchtgId,
			String oldGid, Integer oldMchtgId, String mchtId,
			String mchtUpbrhId, String propertyId, String termId);

}
