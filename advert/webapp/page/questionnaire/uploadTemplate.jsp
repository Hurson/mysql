<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/questionnaire/uploadTemplate.js"></script>

<title>调查问卷管理</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});

</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
<td>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tab_right">
<tr>
<td><a href="listQuestionnaire.do">查看调查问卷</a></td>
<td><a href="uploadTemplate.jsp">导入模板</a></td>
<td><a href="listTemplate.do">查看模板</a></td>
</tr>
</table>
</td>
</tr>
<tr>
<td style=" padding:4px;">
<form id="uploadForm" enctype="multipart/form-data">
<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
	<tr>
		<td class="formTitle" colspan="99">
			<span>·导入调查问卷模板</span>
			
		</td>
	</tr>
	<tr>
		<td class="td_label">模板名称：</td>
	 	<td class="td_input">
	 	<input id="saveFlag" name="saveFlag" type="hidden" value="0"/>
			<input id="templateName" name="templateName" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
		</td> 
		<td class="td_label">模板文件：</td>
		<td class="td_input">
			<input id="templateFile" name="upload" type="file" class="e_inputfile" onfocus="this.className='e_inputFocusfile'" onblur="this.className='e_inputfile'" />
		</td>
	</tr>
	<tr>
		<td class="formBottom" colspan="99">
			<input name="Submit" 
			type="button" 
			title="添加" 
			class="b_add" 
			value="" 
			onfocus=blur() 
			onclick="firstSubmit();"/>
			<input name="Submit" 
			type="reset" 
			title="重置"
			class="b_edit" 
			value="" 
			onfocus=blur() />
		</td>
	</tr>
</table>
</form>
</td>
</tr>
</table>
</td>
</tr>
</table>
<div style="position:absolute; width:100%; left:0px; bottom:0px;">
<table cellpadding="0" cellspacing="0" border="0" class="footer">
<tr><td>22</td></tr>
</table>
</div>
</body>
</html>
