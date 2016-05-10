<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/risk/T4010401.js"></script>
<script type="text/javascript">
	var alarmId = '<%=request.getParameter("alarmId") %>';
	var alarmSeq= '<%=request.getParameter("alarmSeq") %>';
</script>
</head>
<body>
<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>
<!-- 风险警报 -->
</body>
</html>