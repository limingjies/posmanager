package com.allinfinance.bo.route;

import java.util.List;

import com.allinfinance.po.route.TblRouteMchtg;

public interface TblRouteMchtgBO {

	void add(TblRouteMchtg routeMchtg);

	void update(TblRouteMchtg routeMchtg);

	void delete(TblRouteMchtg routeMchtg);

	List<TblRouteMchtg> getAll();

	int getMax();

	String getMcht(Integer mchtGid);

}
