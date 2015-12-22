<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="<%=path%>/js/order/preview.js"></script>
    <script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
    <title>预览</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	var dd = "{'position':'2343434','res':{'1':'2322222'},'type':'2'}";
	alert(dd);
	var data = eval('('+dd+')');
	alert(data.type);
	
	//alert(pollIndex);

	window.onload = function(){	
		
	} 
	function viewDtmbMaterial(){
		
	}
	
	</script>

  </head>
  
  <body class="content">
    <div style="width: 426px; height: 240px; float: left; border: 0px dashed #CCCCCC; margin-left: 0px; color: #CCCCCC; margin-top: 0px; position: relative;">
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
			<div id="text" style="display: none;overflow:hidden">
				<marquee scrollamount="10" id="textContent"></marquee>
			</div>
		</div>
		

  </body>
</html>
