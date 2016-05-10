<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>路由商户组维护</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/UploadDialog.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/UploadDialog.js"></script>

<script type="text/javascript" src="<%= request.getContextPath()%>/ui/route/T110301.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/route/MchntDetail.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/route/MchntTransferOut.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/route/MchntTransferIn.js"></script>
</head>
<style type="text/css">
	.upload {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/upload_16.png) !important;
	}
</style>
<body>
<!-- 规则商户映射维护 -->
</body>
</html>