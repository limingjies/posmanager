<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Pragma" content="No-cache">
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate,text/html; charset=UTF-8">
		<meta http-equiv="Expires" content="0">
		<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ext-all-debug.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ext/examples.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/fixbug.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/system.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/systemSuport.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/common.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ui/system/index.js"></script>
		
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/main.css">
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/icon.css">
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/frame.css">
		<link rel="Stylesheet" type="text/css" href="<%= request.getContextPath()%>/page/system/main/jbview.default.css">
		<link rel="Stylesheet" type="text/css" href="<%= request.getContextPath()%>/page/system/main/main.css">
		<title>金融业务管理平台</title>
		<link rel="Shortcut Icon" href="<%= request.getContextPath()%>/ext/resources/images/top_logo.ico" type="image/x-icon" />
		<style type="text/css">
			body{
				background:url(<%= request.getContextPath()%>/page/system/main/images/topbg.jpg);
				text-align: center;
			}
			.bj {
				background:url(<%= request.getContextPath()%>/page/system/main/images/bg.png);
				width:1024px;
				height:600px;
				margin:auto;
				padding:auto;
			}
			.loginDiv {
				padding:283px 0px 0px 560px;
			}
			.user{
				<%-- background-image:url(<%= request.getContextPath()%>/page/system/main/images/user.png) !important; --%>
				margin-top:9px;
			}
			.pwd{
				background-image:url(<%= request.getContextPath()%>/page/system/main/images/pwd.png) !important;
			}
			.vcode{
				background-image:url(<%= request.getContextPath()%>/page/system/main/images/vcode.png) !important;
			}
			.btn {
				float: left;
				width:265px;
				height:34px;
				cursor: pointer;
			}
		</style>
</head>
<body>
<div class="bj">
<div class="loginDiv">
<div id="loginForm" onkeydown="keydown(event)"></div>
<div style="width:348px;height:34px;">
<div style="width:83px;height:34px;float: left"></div>
<div class="btn" onclick="javascript:login();"></div>
</div>
</div>
</div>
</body>
</html>