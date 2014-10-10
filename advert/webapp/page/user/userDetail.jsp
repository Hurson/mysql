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
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/test.js"
			type="text/javascript">
</script>

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

<script language="javascript" type="text/javascript" src="<%=path %>/js/user/addUser.js"></script>

<script language="javascript" type="text/javascript" src="<%=path %>/js/user/updateUser.js"></script>

			<script>
			//返回
function goBack() {
		window.location.href="userList.do?method=getAllUserList";
}
</script>

</head>

<body class="mainBody" onload="loadRole()">
  <input id="projetPath" type="hidden" value="<%=path%>" />
		<form id="saveForm" action="" method="post" name="saveForm">
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 系统管理 >> 用户详情
				</div>
				<div class="searchContent" >
			<div class="listDetail">
		    <div style="position: relative">	
		    	<table>
		    		<tr>
		    			<td>
		        		<table cellspacing="1" class="content" align="left" style="margin-bottom: 30px">
			            <tr class="title">
			                <td colspan="4">用户详情</td>
			            </tr>
			            <tr>
			                <td width="15%" align="right">用户名称：<input type="hidden" id="user_id" name="user.id" value="${user.id }"/> </td>
			                <td width="35%">
			                	${user.username}
			                </td>
			                <td width="15%" align="right">登陆名称：</td>
			                <td width="35%">
				              	${user.loginname }
			                </td>
			            </tr>		     
			             <tr>
			                <td width="15%" align="right">用户密码：</td>
			                <td width="35%">
			                	${user.password }
			                </td>
			                <td width="15%" align="right">电子邮箱：</td>
			                <td width="35%">
				              	${user.email }
			                </td>
			            </tr>	
			             <tr>
			                <td width="15%" align="right">角色：</td>
			                <td width="35%">
			                  ${role.name }
						    </td>
						    <td width="15%" align="right">区域：</td>
			                <td width="35%">
				              	<c:forEach items="${uLocation}" var="item"  varStatus="statu">
								   ${item.areaName}|
							    </c:forEach>	
			                </td>
			            </tr>  
					<tr>
						<td colspan="4" style="padding-left: -10px">
						<div  id="customer_div_id"    <c:choose>
						   <c:when test="${role.type != 0 }">style="display:block;"</c:when>
						   <c:otherwise>style="display:none;"</c:otherwise></c:choose>>
						
							<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
							<tr>
							<td width="15%" align="right">广告商：</td>
							<td width="35%"> 
								<c:forEach items="${customerL}" var="item"  varStatus="statu">
									${item.advertisersName}|
								</c:forEach>			
							</td>		
							</tr>	
							</table>
						</div>	
						</td>
					</tr>					
					<tr>
						<td class="formBottom" colspan="4">
							<input id="" type="button" value="返回" onclick="goBack();" class="btn" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>

<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>