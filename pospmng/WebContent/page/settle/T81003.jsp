<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>准退货报表下载</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/settle/T81003.js"></script>
<style type="text/css">
	.download {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/download_16.png) !important;
	}
</style>
</head>
<body>
<!-- 准退货报表下载 -->
<div id="reports" style="position: absolute;left: 25%;top: 10%"></div>
<form id="reportForm">
</form>
</body>
</html>