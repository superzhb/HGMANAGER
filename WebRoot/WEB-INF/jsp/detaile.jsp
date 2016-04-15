<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>EverLink</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/hgjs/index.js"></script>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function() {
		$("#mtie").click(function() {
			window.location.href="<%=basePath%>showmtiedata?filename="+$("#nm").val();
		});
		
		$("#tie").click(function() {
			window.location.href="<%=basePath%>showtiedata?filename="+$("#nm").val();
		});
		
		$("#rtie").click(function() {
			//window.location.href="<%=basePath%>showmtiedata";
			alert("todo");
		});
		
		$("#mrtie").click(function() {
			alert("todo");
			//window.location.href="<%=basePath%>showmtiedata";
		});
			
	});
</script>
</head>

<body>
	<div id="top">
		<h1 id="title">详细信息</h1>

		<div id="tie">TIE</div>

		<div id="mtie">MTIE/TDEV</div>

		<div id="rtie">RTIE</div>

		<div id="mrtie">MRTIE</div>
		<input id="nm" type="hidden" value="${filename}">
	</div>

</body>

</html>
