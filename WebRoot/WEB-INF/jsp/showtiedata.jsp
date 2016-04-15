<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String tie = (String) request.getAttribute("tie");
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
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/modules/exporting.js"></script>
<style type="text/css" dir="ltr"></style>
<script type="text/javascript">
	$(function() {
		//修改下载按钮的名称
		//Highcharts.setOptions({
		//lang: {downloadJPEG : 'DOWN JPEG'}
		//});
		$('#container')
				.highcharts(
						{
							plotOptions : {
								series : {
									animation : false
								}
							},
							chart : {
								events : {
									selection : function(event) {
										var text, label;
										if (event.xAxis) {
											text = 'min: '
													+ Highcharts.numberFormat(
															event.xAxis[0].min,
															2)
													+ ', max: '
													+ Highcharts.numberFormat(
															event.xAxis[0].max,
															2);
										} else {
											text = 'Reset ';
										}
										label = this.renderer
												.label(text, 100, 120)
												.attr(
														{
															fill : Highcharts
																	.getOptions().colors[0],
															padding : 10,
															r : 5,
															zIndex : 8
														}).css({
													color : '#FFFFFF'
												}).add();

										setTimeout(function() {
											label.fadeOut();
										}, 3000);
									}
								},
								zoomType : 'x'
							},
							credits : {
								text : "",
								style : {
									cursor : 'pointer',
									color : 'black',
									fontSize : '20px'
								},
								href : 'http://www.ebright.com.cn/'
							},
							title : {
								text : 'TIE测试结果',
								x : -20
							},
							subtitle : {//二级标题
								text : 'Source: from',
								x : -20
							},
							xAxis : {
								title : {
									text : '(sec)'
								}
							},
							yAxis : {
								title : {
									text : '(ns)'
								},
								plotLines : [ {
									value : 0,
									width : 1,
									color : '#808080'
								} ]
							},
							plotOptions : {
								line : {
									lineWidth : 1
								}
							},
							tooltip : {
								headerFormat : '<b>{series.name}</b><br />',
								pointFormat : 'x = {point.x}, y = {point.y}'
							},
							series : [ {
								name : 'TIE',
								data :<%=tie%>,
								pointStart : 0.02
							} ]
						});
	});
</script>
</head>

<body>
	<div id="container" style="width:100%; height: 600px; margin: 0 auto"></div>
</body>
</html>
