<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/role/role.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/role/updateRole.js"></script>
<title>广告系统</title>
<style>
	.easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>

</head>

<body onload="">
<div>
<form action=""   id="updateForm" name="saveForm" method="post">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td style=" padding:4px;">
				<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
					<tr>
						<td class="formTitle" colspan="99">
							<span>·角色详情</span>
						</td>
					</tr>
					<tr>
						<td class="td_label" style="width:270px">角色名称：</td>
						<td class="td_input">
							<input type="hidden" id="role_id" name="role.id" value="${role.id }"/>
							${role.name }
						</td>
						<td class="td_label" style="width:270px">角色描述：</td>
						<td class="td_input">
						   ${role.description }
						</td>
					</tr>
					
					
					
					<tr>
						<td class="td_label" style="width:270px">角色状态：</td>
						<td class="td_input">
									
									<c:if test="${role.status=='0' }">失效</c:if>
									<c:if test="${role.status=='1' }">有效</c:if>
						</td>
						
						<td class="td_label" style="width:270px">角色类型：</td>
						<td class="td_input">
							<c:if test="${role.type=='1' }">广告商</c:if>
							<c:if test="${role.type=='2' }">运营商</c:if>
						</td>
					</tr>					
					<tr>
						<td class="td_label" style="width:270px">选择栏目：</td>
						
						<td class="td_inputz"> 
						<input id="role_columns" 
							name="role_columns"
							value="${columnNames }"
							class="e_input_add"
							onfocus="this.className='e_inputFocus'"
							onclick="fillColumnInfo();" onblur="this.className='e_input_add'" />					
						</td>		
						<td class="td_label" style="width:270px"></td>
						<td class="td_input">
						</td>
					</tr>					
					
					<tr>
						<td class="formBottom" colspan="99">
							<input id="addPositionButton" 
							type="button"
							class="b_edit" 
							onclick="firstSubmit()"
							onfocus="blur()"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</div>
<div id="columnDiv" class="showDiv" style="display: none;">
<table cellpadding="0" cellspacing="0"
	style="margin-top: 0; width: 777px; height: 336px; border: 1px solid #cccccc; font-size: 12px;">
	<tr class="list_title">
		<td style="border: 0;">栏目选择</td>
		<td style="border: 0;" align="right"><input class="e_searchmin"
			type="text" value="栏目检索" /></td>
	</tr>
	<tr>
		<td colspan='2'>
		<div id="columnInfo"></div>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="td_bottom">
		<div class="buttons"><a href="#" onclick="saveBandingColumn();">确认</a>
		<a href="#" onclick="closeSelectDiv('columnDiv');">返回</a></div>
		</td>
	</tr>
</table>
</div>
<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>