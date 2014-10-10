<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<title>编辑视频广告位规格</title>
</head>
<script type="text/javascript">
	function save() {
		if (!validateBeforeSubmit()) {
	        return;
	    }
		$("#editForm").submit();
	}
	
	//表单验证
	function validateBeforeSubmit(){
		var duration = $.trim($("#duration").val());
		if(duration!=''){
			if(!isIntegerNumber(duration)){
				alert("视频规格时长只能是数字！");
				$("#duration").focus();
				return false;
			}
			if(duration.length>10){
				alert("视频规格时长长度最大只能是10位！");
				$("#duration").focus();
				return false;
			}
		}
		var movieDesc = $.trim($("#movieDesc").val());
		if(movieDesc!=''){
			if(movieDesc.length>120){
				alert("视频规格描述长度必须在0-120字之间！");
				$("#movieDesc").focus();
				return false;
			}
		}
		return true;
	}

</script>
<body class="mainBody">
<form action="saveVideoSpecification.do" method="post" id="editForm">
<input type="hidden" name="id" value="${advertPosition.id}"  />
<input type="hidden" id="id" name="videoSpe.id" value="${videoSpe.id}"  />
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">视频规格信息</td>
	</tr>
	<tr>
		<td width="12%" align="right">分辨率：</td>
		<td><input id="resolution" name="videoSpe.resolution" value="${videoSpe.resolution}" type="text" /></td>
		<td width="12%" align="right">时长：</td>
		<td><input id="duration" name="videoSpe.duration" value="${videoSpe.duration}" type="text" />秒</td>
	</tr>
	<tr class="sec">
		<td width="12%" align="right">视频类型：</td>
		<td><input id="type" name="videoSpe.type" value="${videoSpe.type}" type="text" /></td>
		<td width="12%" align="right">最大文件大小：</td>
		<td><input id="fileSize" name="videoSpe.fileSize" value="${videoSpe.fileSize}" type="text" />字节</td>
	</tr>
	<tr>
		<td width="12%" align="right">描述：</td>
		<td colspan="3"><textarea id="movieDesc" name="videoSpe.movieDesc" cols="50" rows="3">${videoSpe.movieDesc}</textarea>
		<span class="required">(长度在0-120字之间)</span></td>
	</tr>
	<tr>
		<td align="center" colspan="4">
			<input type="button" class="btn" value="确定"  onclick="javascript:save();" />
			<input type="button" class="btn" value="返回" onclick="window.location.href='querySpecification.do?id=${advertPosition.id}'" />
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>