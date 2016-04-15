<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>EverLink</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/highcharts-more.js"></script>
<script type="text/javascript" src="js/modules/exporting.js"></script>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function() {
		$('#container').highcharts(
				{

					chart : {
						type : 'bubble',
						zoomType : 'xy'
					},

					title : {
						text : 'Highcharts Bubbles'
					},

					series : [ {
						data : [ [ 97, 36, 79 ], [ 94, 74, 60 ],
								[ 68, 76, 58 ], [ 64, 87, 56 ], [ 68, 27, 73 ],
								[ 74, 99, 42 ], [ 7, 93, -1 ], [ 51, 69, 40 ],
								[ 38, 23, 33 ], [ 57, 86, 31 ] ]
					} ]

				});
				
				$("circle").each(function(){
				//	alert($(this).attr("r"));
				});

	});
	
</script>
</head>

<body>
	<div id="container"
		style="min-width: 100%; height: 600px; margin: 0 auto"></div>

</body>

</html>
