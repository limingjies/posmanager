<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache,no-store">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/Radiogroup.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/T30101.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/ux/animated-dataview.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/DataViewTransition.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/UploadDialog.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/UploadDialog.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/T20905.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/pos/TermInfoNew.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/MchntDetailSS_detail.js"></script>
</head>
<body>
<!-- 系统参数信息 -->
<div id="prize" style="position: absolute;left: 25%;top: 10%"></div>
<form id="prizeForm">
</form>

</body>
</html>