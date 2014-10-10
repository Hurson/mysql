<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>



<title>修改频道</title>
<script>
	function subData(){
		var v = $("#remindValue").val();
		if(v == null || v == ''){
			alert("配置项的值必须填写");
			return;
		}
		
		if(!chineseVaildate($("#remindValue").val())){
			alert("用户行业编码不能包括中文！");
			$("#remindValue").focus();
			return;
		}
		$("#updateForm").submit();
	}
</script>
</head>

<body class="mainBody">
<form action="updateConfig.do" method="post" id="updateForm">
<input type="hidden" id="id" name="baseConfig.id" value="${baseConfig.id}"  />
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">系统参数配置信息</td>
	</tr>
	<tr>
		<td width="15%" align="right">配置项名称：</td>
		<td width="85%"  colspan="3">
		<input id="remindName" maxlength="50" name="baseConfig.remindName"  type="hidden"  style='width:70%' value="${baseConfig.remindName}"/>
		${baseConfig.remindName}
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">配置项编码：</td>
		<td width="85%"  colspan="3">
		<input id="remindKey" maxlength="50" name="baseConfig.remindKey"  type="hidden"  style='width:70%' value="${baseConfig.remindKey}"/>
		${baseConfig.remindKey}
		</td>
	</tr>
	
	<tr>
		<td width="15%" align="right"><span class="required"></span>配置项值：</td>
		<td width="85%"  colspan="3">
		<input id="remindValue" maxlength="50" name="baseConfig.remindValue"  type="text"  style='width:70%' value="${baseConfig.remindValue}"/>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">配置项描述：</td>
		<td width="85%"  colspan="3">
		<input id="remindDesc" maxlength="90" name="baseConfig.remindDesc"  type="text"  style='width:70%' value="${baseConfig.remindDesc}"/>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4">
			<input type="button" class="btn" value="保存" onclick="subData();"/>
			<input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)" />
		</td>
	</tr>
	
</table>
</div>
</form>
</body>
</html>