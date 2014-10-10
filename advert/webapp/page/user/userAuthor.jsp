<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<base href="<%=basePath%>">
<title>用户详细信息</title>
<link rel="stylesheet" href="<%=basePath%>/css/platform.css" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.js"></script>
<script type="text/javascript">
	$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>

</head>
<body>
<form action="" method="post" id="user_list"
	name="adContentMgr_list">
<table cellpadding="0" cellspacing="0" border="0" width="100%">

	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99">
					 <span>广告用户管理</span></td>
			</tr>
			<tr>
				<td class="td_label">用户名称：</td>
				<td class="td_input">${user.username}</td>
				<td class="td_label">登陆名称：</td>
				<td class="td_input">
					${user.loginname}
				</td>
				<td class="td_label">登陆密码：</td>
				<td class="td_input">${user.password}</td>
			</tr>
			<tr>
				<td class="td_label">email：</td>
				<td class="td_input">
				${user.email}
				</td>
				<td class="td_label">创建时间：</td>
				<td class="td_input">
				<fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>
				</td>
				<td class="td_label">修改时间：</td>
				<td class="td_input"> 
					<fmt:formatDate value="${user.modifyTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>
					
				</td>
			</tr>
			<tr>
				<td class="td_label">用户状态：</td>
				<td class="td_input">
					<c:if test="${user.status == '0'}">未登陆</c:if>
					<c:if test="${user.status == '1'}">登陆</c:if>
					<c:if test="${user.status == '2'}">禁用</c:if>
				</td>
				<td class="td_label">所在区域：</td>
				<td class="td_input">
					郑州
				</td>
				<td class="td_label"></td>
				<td class="td_input"> </td>
			</tr>
			<tr >
				<td class="td_label">拥有角色：</td>
			<td colspan="5" class="td_input"> 
				<c:forEach items="${roleList}" var="item" >
							${item.name }、
				</c:forEach>
			</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
	</form>
	<form name="form" method="post">
			<input type="hidden" id="version" value="0"/>
			<input type="hidden" id="flag" value="0"/>
			<input type="hidden" id="num" value="0"/>
			<div id="position">
			<table style="margin-top:0px;">
				<tr>
					<td width="720px" height="310">
					<span style="font-size: 15px;margin-left:25px;">选择角色：</span>
						<div>
							
							<div style="width:250px;float:left;padding-left:25px;">
								<select name="s2" size="21" style="width:250px;" ondblclick="move_right()">
									<s:iterator value="roleList" status="rowstatus" var="item" >
											<option id='${item.id }'  >${item.name }</option>
									</s:iterator>
								</select>
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
								<select name="s3" size="21" style="width:250px;" ondblclick="move_left()">
								
								
								</select>
							</div>
													
						</div>
						
					</td>
				</tr>
			</table>
			</div>
	</form>
	
</body>
</html>


