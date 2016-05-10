package com.allinfinance.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.util.Logger;

/**
 * 
 * 
 * @author kchen
 * 
 */
public class ProgressData extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ProgressData.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		double progress = (Double) (request.getSession().getAttribute("progressNumber")==null?0.001:request.getSession().getAttribute("progressNumber"));
		response.getWriter().write((progress+"").substring(0, (progress+"").lastIndexOf(".")));
	}


}
