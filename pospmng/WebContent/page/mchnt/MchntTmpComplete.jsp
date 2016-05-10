<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<%@page import="java.util.Random"%>
<%@page import="com.allinfinance.system.util.CommonFunction"%>
<%@page import="com.allinfinance.common.Operator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>

<%@page import="java.util.List"%>
<%@page import="com.allinfinance.po.TblDivMchntTmp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache,no-store">
<title>商户临时完善信息</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/UploadDialog.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/UploadDialog.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/pos/TermInfoNew.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/MchtTmpDealInfoUpd.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/MchntTmpCompletes.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/Radiogroup.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/T30101.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/T20100.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/showApproveInfo.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/ux/animated-dataview.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/DataViewTransition.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/base/ImageView.js"></script>

<script type="text/javascript">
	<%
		Operator operator = (Operator)request.getSession().getAttribute("operator");
	%>
	
	var brhId = "<%= operator.getOprBrhId() %>";
	
	var cupBrhId = "<%= request.getSession().getAttribute("cupBrhId") %>";
	
	var imagesId;
	
	var mchntId = '<%=request.getParameter("mchntId") %>';
	
	var opr = "<%= operator.getOprId()%>";
	
	function resetImagesId(){
		var date = new Date();
		var full = date.getFullYear().toString() 
			+ date.getMonth().toString() 
			+ date.getDay().toString() 
			+ date.getHours().toString() 
			+ date.getMinutes().toString() 
			+ date.getSeconds().toString() 
			+ date.getUTCMilliseconds();
		imagesId = opr + full;
	}
	
	resetImagesId();
</script>
</head>
<body>
<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>	
<!-- 商户信息维护 -->
</body>
</html>