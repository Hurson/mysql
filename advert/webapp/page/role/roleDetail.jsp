<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
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
<script language="javascript" type="text/javascript" src="<%=path %>/js/role/updateRole.js"></script>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});

</script>
<title>·角色详情：</title>
</head>
<body>
<li>系统角色管理--->>角色详情 </li>
	<table style="width:720px;height:0px;">
		<tr>
			<td>
				<form id="updateForm" action="" method="post">
					
					<table>
						<tr><td><div><table>   
						<tr>
					
							<td style="width:50px">
							</td>
							<td>
								角色名称：
							</td>
							<td>
								<input type="hidden" name="role.id" value="${role.id }"/>
								<span style="color: gray; size: 14px">${role.name }</span>
							</td>
							<td id="usernameText">
							</td>
						</tr>
						<tr>
							<td style="width:200px">
							</td>
							<td>
								状态：
							</td>
							<td ><c:if test="${role.status=='1' }"> 启用</c:if><c:if test="${role.status=='0' }">禁用</c:if></td>
							<td id="passwdText">
							</td>
						</tr>
						<tr>
							<td style="width:200px">
							</td>
							<td align="justify">
								角色类型：
							</td>
							<td ><c:if test="${role.type=='0'}">超级管理员</c:if><c:if test="${role.type=='2'}">运营商</c:if><c:if test="${role.type=='1'}">广告商</c:if></td>
							<td id="addressText">
							</td>
						</tr>
						
						</table></div> </td>
						<td>
						<div>
							<table> <tr>
								<td style="width:200px">
								</td>
								<td>
									角色描述：
								</td>
								<td>
									<textarea  style="border-left: 0px" rows="10" cols="20"  name="role.description" readonly="readonly">
									${role.description }
									</textarea>
								</td>
								<td id="passwdText">
									
								</td>
							</tr>
							</table></div></td>
						</tr>
					</table>
				</form>	
			</td>
		</tr>
	</table>

<div style="padding:5px 0 0 0;">
<table cellpadding="0" cellspacing="1" class="servicesList">
	<tr>
		<th class="formTitle" colspan="98">
			<span>·栏目：
			<!-- 
				<select id="selPosition" onchange="loadVersion();">
					<option value="0">———请选择栏目—————</option>
					<c:forEach items="${positions }" var="position">
						<option value="${position.id }">${position.positionNameCn }</option>
					</c:forEach>
				</select>
				 -->
			</span>			
		</th>
	</tr>
	<tr>
	<td>
		<c:forEach items="${positions }" var="position">
			<input type="hidden" name="positionInfo" value="${position.id },${position.material_type}"/>
		</c:forEach>
		<script>
			loadData('<%=basePath%>','${version}');
		</script>
		<form name="form" method="post">
			<input type="hidden" id="version" value="0"/>
			<input type="hidden" id="flag" value="0"/>
			<input type="hidden" id="num" value="0"/>
			<div id="position">
			<table style="margin-top:0px;">
				<tr>
					<td width="720px" height="310">
					<span style="font-size: 15px;margin-left:25px;">选择栏目：</span>
						<div>
							<div style="width:250px;float:left;padding-left:25px;">
								<select name="s2" size="21" style="width:250px;" ondblclick="move_right()"></select>
							</div>
							<div style="width:30px;float:left;padding-left:32px;">
								<br/><br/><br/><br/>
								<input type="button" value="==>" onclick="move_right()"/><br/><br/>
								<input type="button" value="<==" onclick="move_left()"/><br/><br/>
								<input type="button" value="全选" onclick="move_right_all()"/><br/><br/>
								<input type="button" value="全删" onclick="move_left_all()"/><br/><br/>
								<input type="button" value="确定" onclick="save()"/>
							</div>
							<div style="width:250px;float:right;margin-right:25px;">
								<select name="s3" size="21" style="width:250px;" ondblclick="move_left()"></select>
							</div>
													
						</div>
						
					</td>
				</tr>
			</table>
			</div>
			</form>
		</td>
	</tr>
</table>
</div>
</body>
</html>