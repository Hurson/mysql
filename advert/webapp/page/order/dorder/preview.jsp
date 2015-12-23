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
	var position = ${map.position};
	var type = ${map.type};
	var mate= type==2?JSON.parse('${map.res}'):'${map.res}';
		
	window.onload = function(){
		preMaterial();
		viewDtmbMaterial();
	} 
	function viewDtmbMaterial(){
		switch(type){
			case 0:showImage(mate);break;
			case 1:showVideo(mate);break;
			case 2:showDText(mate);break;
			case 4:showZip(mate);break;
		}
	}
	function preMaterial(){
		var backgroundPath = (typeof(position.bgImagePath)=='undefined'?"":position.bgImagePath);
		var widthHeight = (typeof(position.domain)=='undefined'?"":position.domain);
		var coordinate = (typeof(position.coordinate)=='undefined'?"":position.coordinate);
		
		/**为页面预览区域赋值*/
		//alert("come");
		var size = widthHeight.split('*');
		width = size[0];
		height = size[1];
		$("#pImage").attr("width",426).attr("height",240);
		$("#pImage").attr("src",getContextPath()+"/"+backgroundPath);
		$("#mImage").attr("width",width).attr("height",height);
		$("#mImage,#video").css({
			width:width+"px",
			height:height+"px",
			position:'absolute',
			left: coordinate[0]+"px", 
			top: coordinate[1]+"px" 
		});
		$("#video").hide();
		
		$("#text").css({
			position:'absolute',
			width:width+"px",
			height:height+"px",
			left:coordinate[0]+"px",
			top:coordinate[1]+"px",
			'z-index':1
		});
	}
	/**显示文字*/
function showDText(material){
	$("#video").hide();
	$("#mImage").hide();
	$("#textContent").css({
		'color':material.fontColor,
		'font-size':material.fontSize+"px"
	});
	if(material.rollSpeed!=''){
		$("#textContent").attr({"scrollamount":material.rollSpeed});
	}
	
	var coordinates = material.positionVertexCoordinates.split("*");
	var size = material.positionWidthHeight.split("*");
	
	var left = coordinates[0]/1280*426+"px";
	var bottom = coordinates[1]/720*240+"px";					 				  
	var width = size[0]/1280*426+"px";
	var height = size[1]/720*240+"px";
	
	$('#text').css('width',width);
	$('#text').css('height',height*3);
	$('#text').css('left',left);
	$('#text').css('top',bottom);
	$('#text').css('bgcolor',material.bkgColor);
	
	$("#text").show();
	var content = material.content.split("@_@");
	var words = "";
	for(var i = 0; i < content.length; i++){
		words += content[i];
	}
	
	$("#textContent").html(words);
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
