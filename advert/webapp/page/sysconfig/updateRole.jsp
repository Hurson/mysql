<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js"
			type="text/javascript">
</script>

<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
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

<!-- 树的结构   -->
<link href="<%=path %>/common/tree/TreePanel.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=path %>/common/tree/common-min.js"></script>
<script type="text/javascript" src="<%=path %>/common/tree/TreePanel.js"></script>
<script type="text/javascript" src="<%=path %>/common/tree/china_2.js"></script>
<!-- 树的结构 end -->
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
	<script>
			//返回
function goBack() {
		window.location.href="queryRoleList.do";
}
</script>

</head>

<body class="mainBody" onload="">
  <input id="projetPath" type="hidden" value="<%=path%>" />
		<form action="updateRole.do"  id="updateForm" method="post">
			<input type="hidden" id="roleId" name="role.roleId" value="${role.roleId}"/>
			<input type="hidden" id="createTime" name="role.createTime" value="${role.createTime}"/>
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 系统管理 >> 角色修改
				</div>
				<div class="searchContent" >
			<div class="listDetail">
		    <div style="position: relative">
		    	<table>
		    		<tr>
		    			<td>
		        		<table cellspacing="1" class="content" align="left" style="margin-bottom: 30px">
			            <tr class="title">
			                <td colspan="4">角色信息</td>
			            </tr>
			            <tr>
			                <td width="15%" align="right"><span class="required">*</span>角色名称：</td>
			                <td width="35%">
			                	<input id="name" name="role.name"  value="${role.name}" type="text" maxlength="12"/>
			                </td>
			                <td width="15%" align="right">角色类型：</td>
			                <td width="35%">
				              	<select id="type" name="role.type">
									<option value="">请选择...</option>
									<option value="1" <c:if test="${role.type=='1' }">selected="selected"</c:if>>广告商</option>
									<option value="2" <c:if test="${role.type=='2' }">selected="selected"</c:if>>运营商</option>
							    </select>	
			                </td>
			            </tr>		     
						<tr>
							<td width="15%" align="right">选择栏目：</td>
							<td cwidth="35%"> 
								<input id="sel_columns" name="sel_columns"  type="hidden" value="${role.columnIds}" />
								<input id="sel_p_columns" name="sel_p_columns" type="hidden" />
								<textarea rows="14" cols="24" id="role_columns" readonly="readonly" onclick="fillColumnInfo_2();">${role.columnNames}</textarea>					
							</td>		
							<td width="15%" align="right">角色描述：</td>
							<td width="35%">
								<textarea id="description" rows="14" cols="24" name="role.description">${role.description}</textarea>				
							</td>
						</tr>					
						<tr>
						<td width="20%" colspan="4">
							<input type="button" value="修改" onclick="firstSubmit();" class="btn" />
						    <input type="button" value="返回" onclick="goBack();" class="btn" />
						</td>
						</tr>
				</table>
			</td>
		</tr>
	</table>
	</div>
</div>
</div>
</div>
</form>

<div id="columnDiv" class="showDiv_2" style="display: none;">
<table cellpadding="0" cellspacing="0"
	style="margin-top: 0; width: 750px; height: 350px; border: 1px solid #cccccc; font-size: 12px;background-color: #e7f2fc;">
	<tr class="list_title">
		<th style="border: 0;"  align="left">&nbsp;&nbsp;· 栏目选择</th>
		<td style="border: 0; padding-right: 5px;" align="right"  >
		<a href="#" onclick="saveBandingColumn_2();">确认</a>&nbsp;
		<a href="#" onclick="closeDiv('columnDiv');">返回</a>
		</td>
	</tr>
	<tr>
		<td colspan='2'  id="treeList">
		</td>
	</tr>
</table>
</div>


<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>