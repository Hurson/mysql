<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type='text/javascript' src='<%=path %>/js/position/delPreview.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title></title>
<script type="text/javascript">
	function showResource(positionPackageId,adId){
		var url = "queryDefResourceList.do?id="+adId;
		var modelWin = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
        if (modelWin) {
    		window.location="queryADList.do?id="+positionPackageId;
    	}
	}
</script>
<style>
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
</style>
</head>

<body class="mainBody">

<div class="search">
<div class="path">首页 >> 广告位管理 >> 默认素材配置 >> 设置素材</div>
<div class="searchContent" >
    <table cellspacing="1" class="searchList" align="left">
    
        <tr class="title">
            <td colspan="2">${pPackage.positionPackageName}</td>
        </tr>
        
        <c:forEach items="${adList}" var="ad" varStatus="pl">
        <tr>
        	<td width="30%" align="right"><span class="required"></span>${ad.positionName}素材：</td>
            <td width="70%">
                <input id="resource" name="resource" class="e_input_add" value="" readonly="readonly" type="text" 
                onclick="showResource('${ad.positionPackageId}','${ad.id}');"/>&nbsp;&nbsp;&nbsp;&nbsp;${ad.resourceNames}
            </td>
        </tr>
        </c:forEach>
        
        <tr id="preview" style="display: none">
		     <td align="right" >素材预览效果：</td>
		     <td>
			    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
			     position: relative;">
					<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" /> 
					<img id="mImage" src=""	style="display: none" />
					<div id="video" style="display: none">
						<object type='application/x-vlc-plugin' id='vlc'  classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' width="150" height="150">
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
		     </td>						
         </tr> 
    </table>
    <br />
    <div align="center" style="position: relative;top: 10px;">
        <input type="button" class="btn" value="返回" onclick="window.location.href='queryResourceList.do'" />
    </div>
</div>
</div>
</div>
</div>
</body>	
</html>