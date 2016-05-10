package com.allinfinance.common.select;

import com.allinfinance.commquery.dao.ICommQueryDAO;

public class DynamicSqlBean {

	public DynamicSqlBean(String sql, ICommQueryDAO dao) {
		super();
		this.sql = sql;
		this.dao = dao;
	}

	private String sql;	
	private ICommQueryDAO dao;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public ICommQueryDAO getDao() {
		return dao;
	}

	public void setDao(ICommQueryDAO dao) {
		this.dao = dao;
	}
}
