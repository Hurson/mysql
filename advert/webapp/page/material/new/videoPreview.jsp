<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />


<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path%>/js/material/uploadMaterial.js"></script>
<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>


<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
  
<title>广告系统</title>
<script type="text/javascript">

/** 宽度 */
var width = 0;
/** 高度 */
var height = 0;

function init(positionJson){
   var selPosition = eval(positionJson);
	/**为页面预览区域赋值*/
	var size = selPosition.widthHeight.split('*');
	
	width = size[0];
	height = size[1];
	var coordinate = selPosition.coordinate.split('*');
	$("#pImage").attr("width",426).attr("height",240);	
	//$("#pImage").attr("src",getContextPath()+"/"+selPosition.backgroundPath);
	//$("#mImage").attr("width",width).attr("height",height);
	$("#video").css({
		width:width+"px",
		height:height+"px",
		position:'absolute',
		left: coordinate[0]+"px", 
		top: coordinate[1]+"px" 
	});
	
	var videoPath=getContextPath()+"/images/material/"+document.getElementById('videoPreviewName').value;
	setTimeout(showVideo(videoPath), 1500);

}

/**预览视频*/
	function showVideo(path){
		$("#vlc").css({
			width:width+"px",
			height:height+"px"
		});
		var vlc=document.getElementById("vlc");
		vlc.playlist.stop();
		vlc.playlist.clear();
		 // 添加播放地址
		 vlc.playlist.add(path);
		 // 播放
		 vlc.playlist.play();
	}
    /**
 * 获取上下文路径
 */ 
function getContextPath() {
	var contextPath = "/"+document.location.pathname;
	var index = contextPath.substr(1).indexOf("/");
	contextPath = contextPath.substr(0, index + 1);
	delete index;
	return contextPath;
}


</script>

</head>

<body class="mainBody" onload='init(${positionJson});'>

						   
						   <input id="positionJson" name="positionJson" type="hidden" value="${positionJson}"/>
						   <input id="videoPreviewName" name="videoPreviewName" type="hidden" value="${videoPreviewName}"/>
						   <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
				     position: relative;">
						<img id="pImage" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" />
						<div id="video">
							<object type='application/x-vlc-plugin' id='vlc'  classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' width="150" height="150">
						           <param name='mrl'  value=''/>
									<param name='volume' value='50' />
									<param name='autoplay' value='false' />
									<param name='loop' value='false' />
									<param name='fullscreen' value='false' />
						    </object>
						</div>
					</div>



</body>
</html>