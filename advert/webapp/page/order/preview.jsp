<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="<%=path%>/js/order/preview.js"></script>
    <script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
    <title>预览</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	var previewValue='${previewValue}';
	var resourceValue='${resourceValue}';
	var pollIndex = '${pollIndex}';
	//alert(pollIndex);

	window.onload = function(){	
		//preview(previewValue[5],previewValue[6],previewValue[7]);
		previewMaterial(previewValue,pollIndex);
 		viewMaterial(resourceValue);		 
	} 
	
	</script>

  </head>
  
  <body>

    <div
			style="width: 426px; height: 240px; float: left; border: 0px dashed #CCCCCC; margin-left: -10px; color: #CCCCCC; margin-top: -15px; position: relative;">
			<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" /> 
			<img id="mImage" src=""	style="display: none" />
			<div id="video">
				<object type='application/x-vlc-plugin' id='vlc'  classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' width="426" height="240">
			           <param name='mrl'  value=''/>
						<param name='volume' value='50' />
						<param name='autoplay' value='false' />
						<param name='loop' value='false' />
						<param name='fullscreen' value='false' />
			    </object>
			</div>
			<div id="text" style="display: none;">
				<marquee scrollamount="10" id="textContent"></marquee>
			</div>
		</div>
		

  </body>
</html>
