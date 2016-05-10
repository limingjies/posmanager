<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/common.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/T20401.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/Radiogroup.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/DataViewTransition.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/T20401.js"></script>

<script type="text/javascript">
	<%
		Operator operator = (Operator)request.getSession().getAttribute("operator");
	%>
	
	var brhId = "<%= operator.getOprBrhId() %>";
	
	var cupBrhId = "<%= request.getSession().getAttribute("cupBrhId") %>";
	
	var imagesId;
	
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

<script type="text/javascript">
</script>

</head>
<body>
<!-- 商户信息维护 -->
</body>
</html>