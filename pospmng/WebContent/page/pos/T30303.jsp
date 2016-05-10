<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>终端库存下发审核</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/edit-grid/grid-examples.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/edit-grid/examples.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/edit-grid/RowEditor.css" />
<style type="text/css">
		.x-grid3 .x-window-ml{
			padding-left: 0;	
		} 
		.x-grid3 .x-window-mr {
			padding-right: 0;
		} 
		.x-grid3 .x-window-tl {
			padding-left: 0;
		} 
		.x-grid3 .x-window-tr {
			padding-right: 0;
		} 
		.x-grid3 .x-window-tc .x-window-header {
			height: 3px;
			padding:0;
			overflow:hidden;
		} 
		.x-grid3 .x-window-mc {
			border-width: 0;
			background: #cdd9e8;
		} 
		.x-grid3 .x-window-bl {
			padding-left: 0;
		} 
		.x-grid3 .x-window-br {
			padding-right: 0;
		}
		.x-grid3 .x-panel-btns {
			padding:0;
		}
		.x-grid3 .x-panel-btns td.x-toolbar-cell {
			padding:3px 3px 0;
		}
		.x-box-inner {
			zoom:1;
		}
        .icon-user-add {
            background-image: url(../shared/icons/fam/user_add.gif) !important;
        }
        .icon-user-delete {
            background-image: url(../shared/icons/fam/user_delete.gif) !important;
        }
    </style>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/edit-grid/RowEditor.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/edit-grid/gen-names.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/pos/T30303.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/common.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/systemSuport.js"></script>
<style type="text/css">
	.download {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/upload/icons/download_.png) !important;
	}
</style>
</head>
<body>
<!-- 终端下发审核 -->
</body>
</html>