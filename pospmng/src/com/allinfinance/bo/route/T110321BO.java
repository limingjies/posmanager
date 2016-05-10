package com.allinfinance.bo.route;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.allinfinance.po.route.TblRouteMchtg;

public interface T110321BO {


	String setNewMchtConfig(HttpServletRequest request);

	String bacthAddMchtUpbrh(String oprId, int newMchtgId,
			String mchtId, String mchtUpbrhId, String propertyId, String termId);

	

}
