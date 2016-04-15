<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String mtie = (String) request.getAttribute("mtie");
	String tdev = (String) request.getAttribute("tdev");
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
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function() {
		$('#container').highcharts({
			chart : {
				events: {
	                selection: function (event) {
	                    var text,label;
	                    if (event.xAxis) {
	                        text = 'min: ' + Highcharts.numberFormat(event.xAxis[0].min, 2) + ', max: ' + Highcharts.numberFormat(event.xAxis[0].max, 2);
	                    } else {
	                        text = 'Reset ';
	                    }
	                    label = this.renderer.label(text, 100, 120)
	                        .attr({
	                            fill: Highcharts.getOptions().colors[0],
	                            padding: 10,
	                            r: 5,
	                            zIndex: 8
	                        })
	                        .css({
	                            color: '#FFFFFF'
	                        })
	                        .add();
	
		                    setTimeout(function () {
		                        label.fadeOut();
		                    }, 3000);
	                	}
	            	},
	            	zoomType: 'x'
         	},
			credits : {//右下角显示的文字链接
				text : "",
				style : {
					cursor : 'pointer',
					color : 'black',
					fontSize : '20px'
				},
				href : 'http://www.ebright.com.cn/'
			},
			title : {
				text : 'MTIE/TDEV测试结果'
			},

			xAxis : {
				title : {
					text : '(sec)',
				},
				type: 'logarithmic',
	            min: 0.01,
	            max: 1000,
	            endOnTick: true,
	            tickInterval: 1,
	            minorTickInterval: 0.1,
	            gridLineWidth: 1
			},

			yAxis : {
				title : {
					text : '(ns)'
				},
				type: 'logarithmic',
	            min: 0.001,
	            max: 1000,
	            endOnTick: true,
	            tickInterval: 1,
	            minorTickInterval: 0.1,
	            gridLineWidth: 1
			},

			tooltip : {
				headerFormat : '<b>{series.name}</b><br />',
				pointFormat : 'x = {point.x}, y = {point.y}'
			},

			series : [

			{
				name : 'MTIE',
				data : <%=mtie%>,
				pointStart : 0.02
			}, {
				name : 'TDEV',
				data : <%=tdev%>,
				pointStart : 0.02
			} ]
		});
	});
</script>
</head>

<body>
	<div id="container"></div>
</body>
</html>
