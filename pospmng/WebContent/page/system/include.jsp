<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.allinfinance.common.Operator"%>
<%@ page import="com.allinfinance.common.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate,text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ext-base.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ext-all.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/examples.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/fixbug.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/system.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/SelectOptionsDWR.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/ChartUtilDWR.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/RemoteTransDWR.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/AuthoriseDwr.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/EncryptUtils.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/systemSuport.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/authorise.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/common.js"></script>


<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/main.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/frame.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/ext-all.css">

</head>
<body>
<script type="text/javascript">
Ext.contextPath = '<%= request.getContextPath()%>';
System[QUERY_RECORD_COUNT] = <%= Constants.QUERY_RECORD_COUNT%>;
System[QUERY_SELECT_COUNT] = <%= Constants.QUERY_SELECT_COUNT%>;
System[REPORT_MAX_COUNT] = <%= Constants.REPORT_MAX_COUNT%>;
Ext.BLANK_IMAGE_URL = Ext.contextPath + '/ext/resources/images/default/s.gif';
</script>
<script type="text/javascript">
	<% 
		Operator opr = (Operator)request.getSession().getAttribute("operator");
		if (null != opr) {
			%>
				var BRHID = "<%= opr.getOprBrhId() %>";
			<%
		}
	%>
	javascript:window.history.forward(1);
	<%
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		String yesterday = sdf.format(sdf.parse(String.valueOf(Integer.parseInt(today) - 1)));
		
		//银联编号
		String cupBrhId = (String)request.getSession().getAttribute("CUP_BRH_ID");
	%>
	
	var TORDAY = "<%= today %>";
	var YESTERDAY = "<%= yesterday %>";
	
	var CUPBRHID = "<%= cupBrhId %>";
</script>
</body>
</html>