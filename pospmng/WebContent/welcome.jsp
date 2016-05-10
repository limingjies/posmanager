<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>金融业务管理平台</title>
<%
	String oprId = (String)request.getSession().getAttribute("oprId");
	if(oprId != null&&!oprId.equals("")){
		response.sendRedirect(request.getContextPath()+"/redirect.asp");
	}else{
		response.sendRedirect(request.getContextPath()+"/page/system/errorNoLogin.jsp");
	}
 %>
<link rel="Shortcut Icon" href="<%= request.getContextPath()%>/ext/resources/images/top_logo.ico" type="image/x-icon" />
</head>
<body>

</body>
</html>