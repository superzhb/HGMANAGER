<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int maxpage = Integer.valueOf((String) request
			.getAttribute("maxpage"));
	int curpage = Integer.valueOf((String) request
			.getAttribute("curpage"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>EverLink</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link href="css/index.css" rel="stylesheet" type="text/css" />
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/hgjs/index.js"></script>
<script type="text/javascript">
	$(function() {
		$("#detaile").click(function() {
			window.location.href = "detaile";
		});

		$("#to").click(function() {
			window.location.href = "main?curpage=" + $("#page").val();
		});

		//选择文件之后执行上传
		$('#fileToUpload').live('change', function() {
			loading();
			$.ajaxFileUpload({
				url : 'uploadfile',
				secureuri : false,
				fileElementId : 'fileToUpload',//file标签的id  
				dataType : 'json',//返回数据的类型  
				success : function(data, status) {
					if (data.msg == "rdf") {
						alert("只能上传rdf文件 ");
						return;
					}
					alert("上传成功");
					var maxpage = $("#maxpage").val();
					window.location.href = "main?curpage=" + maxpage;
				},
				error : function(data, status, e) {
					alert(data.msg);
				}
			});
		});

		function loading() {
			$("#fileToUpload ").ajaxStart(function() {
				$("#wait").show();
			}).ajaxComplete(function() {
				$("#wait").hide();
			});
		}

		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) {
				var curpage = $("#page").val();
				var maxpage = $("#maxpage").val();
				var r = /^[0-9]+.?[0-9]*$/;
				if (!r.test(curpage)) {
					alert('只能输入数字');
				}
				if (curpage > maxpage) {
					curpage = maxpage;
				}
				window.location.href = "main?curpage=" + curpage;
			}
		};
	});
</script>
</head>

<body>
	<h1 id="title">测试数据处理平台</h1>
	<input id="fileToUpload" type="file" name="fileToUpload">
	<div id="bottom">
		<table id="tab" class="bordered">
			<thead>
				<tr>
					<th>序列号</th>
					<th>文件名称</th>
					<th>名称</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>操作</th>
				<tr />
			</thead>
			<tbody>
				<c:forEach items="${list}" var="record">
					<tr>
						<td width="5%">${record.id}</td>
						<td width="20%">${record.filename}</td>
						<td width="20%">${record.name}</td>
						<td width="20%">${record.startdata}</td>
						<td width="20%">${record.enddata}</td>
						<td width="20%"><a href="detaile?filename=${record.filename}">详细</a><a
							href="delrecord?name=${record.filename}">删除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagebox" style="margin-top: 20px;width: 100%;">
			当前页：${curpage} <input id="maxpage" type="hidden" value="${maxpage}">
			<c:choose>
				<c:when test="${curpage > 1}">
					<a href="main?curpage=${curpage-1}" style="margin-left: 20px">上一页</a>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<input id="page" style="width: 30px"></input> <input id="to"
				type="button" value="跳转">
			<c:choose>
				<c:when test="${curpage == maxpage}">
				</c:when>
				<c:otherwise>
					<a href="main?curpage=${curpage+1}">下一页</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div id="wait" style="visibility: hidden;">ssss</div>
</body>
</html>
